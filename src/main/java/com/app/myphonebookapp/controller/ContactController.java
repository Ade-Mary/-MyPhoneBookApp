package com.app.myphonebookapp.controller;

import com.app.myphonebookapp.dto.ContactRequestDTO;
import com.app.myphonebookapp.dto.ContactResponseDTO;
import com.app.myphonebookapp.model.ContactGroup;
import com.app.myphonebookapp.service.ContactService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/contacts")
@RequiredArgsConstructor
public class ContactController {

    private final ContactService contactService;

    // 1. Create a Contact
    @PostMapping
    public ResponseEntity<ContactResponseDTO> createContact(@Valid @RequestBody ContactRequestDTO dto) {
        ContactResponseDTO response = contactService.createContact(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // 2. List All Contacts (Paginated)
    @GetMapping
    public ResponseEntity<Page<ContactResponseDTO>> getAllContacts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "firstName") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir
    ) {
        return ResponseEntity.ok(contactService.getAllContacts(page, size, sortBy, sortDir));
    }

    // 4. Update a Contact
    @PutMapping("/{id}")
    public ResponseEntity<ContactResponseDTO> updateContact(
            @PathVariable Long id,
            @Valid @RequestBody ContactRequestDTO dto
    ) {
        return ResponseEntity.ok(contactService.updateContact(id, dto));
    }

    // 5. Delete a Contact
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteContact(@PathVariable Long id) {
        contactService.deleteContact(id);
        return ResponseEntity.noContent().build();
    }

    // 6. Get Single Contact by ID
    @GetMapping("/{id}")
    public ResponseEntity<ContactResponseDTO> getContactById(@PathVariable Long id) {
        return ResponseEntity.ok(contactService.getContactById(id));
    }

    @GetMapping("/search")
    public ResponseEntity<Page<ContactResponseDTO>> searchContacts(
            @RequestParam String query,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "firstName") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir
    ) {
        return ResponseEntity.ok(contactService.searchContacts(query, page, size, sortBy, sortDir));
    }

    // 9. Filter Contacts by Group (Paginated)
    @GetMapping(params = "group")
    public ResponseEntity<Page<ContactResponseDTO>> getContactsByGroup(
            @RequestParam ContactGroup group,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "firstName") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir
    ) {
        return ResponseEntity.ok(contactService.getContactsByGroup(group.name(), page, size, sortBy, sortDir));
    }

    // Get All Unique Groups
    @GetMapping("/groups")
    public ResponseEntity<List<String>> getAllGroups() {
        List<String> groups = contactService.getAllGroups();
        return ResponseEntity.ok(groups);
    }

    // 10. Get Favorite Contacts
    @GetMapping("/favorites")
    public ResponseEntity<Page<ContactResponseDTO>> getFavoriteContacts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "firstName") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir
    ) {
        return ResponseEntity.ok(contactService.getFavoriteContacts(page, size, sortBy, sortDir));
    }

    //Toggle Favorite Status
    @PutMapping("/{id}/favorite")
    public ResponseEntity<Void> toggleFavorite(@PathVariable Long id) {
        contactService.toggleFavorite(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/import")
    public ResponseEntity<Void> importContacts(@RequestParam("file") MultipartFile file) throws IOException {
        contactService.importContactsFromCSV(file);
        return ResponseEntity.ok().build();
    }

    // 13. Export Contacts to CSV
    @GetMapping("/export")
    public ResponseEntity<byte[]> exportContacts() {
        byte[] csvBytes = contactService.exportContactsToCSV();
        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=contacts.csv")
                .body(csvBytes);
    }
}
