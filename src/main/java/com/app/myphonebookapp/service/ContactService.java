package com.app.myphonebookapp.service;

import com.app.myphonebookapp.dto.ContactRequestDTO;
import com.app.myphonebookapp.dto.ContactResponseDTO;
import com.app.myphonebookapp.exception.DuplicateEntryException;
import com.app.myphonebookapp.exception.NoContactsFoundException;
import com.app.myphonebookapp.exception.ResourceNotFoundException;
import com.app.myphonebookapp.model.Contact;
import com.app.myphonebookapp.model.ContactGroup;
import com.app.myphonebookapp.repository.ContactRepository;
import com.app.myphonebookapp.util.CSVUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ContactService {

    private final ContactRepository contactRepository;

    public ContactResponseDTO createContact(ContactRequestDTO dto) {
        if (contactRepository.existsByPhoneNumber(dto.getPhoneNumber())) {
            throw new DuplicateEntryException("Phone number already exists");
        }

        if (dto.getEmail() != null && !dto.getEmail().isEmpty()) {
            if (contactRepository.existsByEmail(dto.getEmail())) {
                throw new DuplicateEntryException("Email already exists");
            }
        }

        Contact contact = mapToEntity(dto);
        contact = contactRepository.save(contact);
        return mapToDTO(contact);
    }

    // Read (Paginated)
    public Page<ContactResponseDTO> getAllContacts(int page, int size, String sortBy, String sortDir) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortDir), sortBy);
        PageRequest pageable = PageRequest.of(page, size, sort);
        return contactRepository.findAll(pageable).map(this::mapToDTO);
    }

    // Update
    public ContactResponseDTO updateContact(Long id, ContactRequestDTO dto) {
        Contact contact = contactRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Contact not found with id: " + id));
        updateEntityFromDTO(dto, contact);
        contact = contactRepository.save(contact);
        return mapToDTO(contact);
    }

    // Delete
    public void deleteContact(Long id) {
        if (!contactRepository.existsById(id)) {
            throw new ResourceNotFoundException("Contact not found with id: " + id);
        }
        contactRepository.deleteById(id);
    }

    public ContactResponseDTO getContactById(Long id) {
        Contact contact = contactRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Contact not found with id: " + id));
        return mapToDTO(contact);
    }

    public Page<ContactResponseDTO> searchContacts(
            String query,
            int page,
            int size,
            String sortBy,
            String sortDir
    ) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortDir), sortBy);
        PageRequest pageable = PageRequest.of(page, size, sort);
        Page<Contact> contacts = contactRepository.findByFirstNameContainingOrLastNameContainingOrEmailContainingOrPhoneNumberContaining(
                query, query, query, query, pageable
        );

        if (contacts.isEmpty()) {
            throw new NoContactsFoundException("No contacts found matching the search query.");
        }

        return contacts.map(this::mapToDTO);
    }

    // Filter Contacts by Group (Paginated)
    public Page<ContactResponseDTO> getContactsByGroup(
            String group,
            int page,
            int size,
            String sortBy,
            String sortDir
    ) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortDir), sortBy);
        PageRequest pageable = PageRequest.of(page, size, sort);
        return contactRepository.findByGroup(group, pageable).map(this::mapToDTO);
    }

    // Get All Unique Groups
    public List<String> getAllGroups () {
        return contactRepository.findAllDistinctGroups();
    }

    public Page<ContactResponseDTO> getFavoriteContacts(
            int page,
            int size,
            String sortBy,
            String sortDir
    ) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortDir), sortBy);
        PageRequest pageable = PageRequest.of(page, size, sort);
        return contactRepository.findByFavoriteTrue(pageable).map(this::mapToDTO);
    }

    public void toggleFavorite(Long id) {
        Contact contact = contactRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Contact not found with id: " + id));
        contact.setFavorite(!contact.isFavorite());
        contactRepository.save(contact);
    }

    // 12. Export Contacts to CSV
    public byte[] exportContactsToCSV() {
        List<Contact> contacts = contactRepository.findAll();
        return CSVUtil.exportToCSV(contacts);
    }

    // 13. Import Contacts from CSV
    public void importContactsFromCSV(MultipartFile file) throws IOException {
        List<Contact> contacts = CSVUtil.parseCSV(file);
        contactRepository.saveAll(contacts);
    }

    // Helper methods
    private Contact mapToEntity(ContactRequestDTO dto) {
        return Contact.builder()
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .email(dto.getEmail())
                .phoneNumber(dto.getPhoneNumber())
                .contactImage(dto.getContactImage())
                .physicalAddress(dto.getPhysicalAddress())
                .group(ContactGroup.valueOf(dto.getGroup().toUpperCase()))
                .favorite(dto.isFavorite())
                .build();
    }

    private ContactResponseDTO mapToDTO(Contact contact) {
        return ContactResponseDTO.builder()
                .id(contact.getId())
                .firstName(contact.getFirstName())
                .lastName(contact.getLastName())
                .email(contact.getEmail())
                .phoneNumber(contact.getPhoneNumber())
                .contactImage(contact.getContactImage())
                .physicalAddress(contact.getPhysicalAddress())
                .group(contact.getGroup().name())
                .isFavorite(contact.isFavorite())
                .build();
    }

    private void updateEntityFromDTO(ContactRequestDTO dto, Contact contact) {
        contact.setFirstName(dto.getFirstName());
        contact.setLastName(dto.getLastName());
        contact.setEmail(dto.getEmail());
        contact.setPhoneNumber(dto.getPhoneNumber());
        contact.setContactImage(dto.getContactImage());
        contact.setPhysicalAddress(dto.getPhysicalAddress());
        contact.setGroup(ContactGroup.valueOf(dto.getGroup().toUpperCase()));
        contact.setFavorite(dto.isFavorite());
    }
}
