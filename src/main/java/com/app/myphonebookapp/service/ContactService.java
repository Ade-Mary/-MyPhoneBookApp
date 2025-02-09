package com.app.myphonebookapp.service;

import com.app.myphonebookapp.dto.ContactRequestDTO;
import com.app.myphonebookapp.dto.ContactResponseDTO;
import com.app.myphonebookapp.exception.ResourceNotFoundException;
import com.app.myphonebookapp.model.Contact;
import com.app.myphonebookapp.repository.ContactRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ContactService {

    private final ContactRepository contactRepository;

    // Create
    public ContactResponseDTO createContact(ContactRequestDTO dto) {
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
        return contactRepository.findByFirstNameContainingOrLastNameContainingOrEmailContainingOrPhoneNumberContaining(
                query, query, query, query, pageable
        ).map(this::mapToDTO);
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





    // Helper methods
    private Contact mapToEntity(ContactRequestDTO dto) {
        return Contact.builder()
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .email(dto.getEmail())
                .phoneNumber(dto.getPhoneNumber())
                .contactImage(dto.getContactImage())
                .physicalAddress(dto.getPhysicalAddress())
                .group(dto.getGroup())
                .isFavorite(dto.isFavorite())
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
                .group(contact.getGroup())
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
        contact.setGroup(dto.getGroup());
        contact.setFavorite(dto.isFavorite());
    }

}