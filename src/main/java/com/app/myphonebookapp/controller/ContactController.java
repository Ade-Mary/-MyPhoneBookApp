package com.app.myphonebookapp.controller;

import com.app.myphonebookapp.dto.ContactRequestDTO;
import com.app.myphonebookapp.dto.ContactResponseDTO;
import com.app.myphonebookapp.service.ContactService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
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



}