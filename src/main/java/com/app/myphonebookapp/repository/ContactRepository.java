package com.app.myphonebookapp.repository;

import com.app.myphonebookapp.model.Contact;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContactRepository extends JpaRepository<Contact, Long> {
    boolean existsByPhoneNumber(String phoneNumber);
    boolean existsByEmail(String email);
    Page<Contact> findByFirstNameContainingOrLastNameContainingOrEmailContainingOrPhoneNumberContaining(
            String firstName, String lastName, String email, String phoneNumber, Pageable pageable);
    Page<Contact> findByGroup(String group, Pageable pageable);
    Page<Contact> findByFavoriteTrue(Pageable pageable);
    List<String> findAllDistinctGroups();
}
