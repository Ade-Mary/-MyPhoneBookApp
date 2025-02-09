package com.app.myphonebookapp.repository;


import com.app.myphonebookapp.model.Contact;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Long> {
    Page<Contact> findByFirstNameContainingOrLastNameContainingOrEmailContainingOrPhoneNumberContaining(
            String firstName, String lastName, String email, String phoneNumber, Pageable pageable
    );


    void deleteAllByIdIn(List<Long> ids);
    Page<Contact> findByGroup(String group, Pageable pageable);
    @Query("SELECT DISTINCT c.group FROM Contact c")
    List<String> findAllDistinctGroups();

    Page<Contact> findByFavoriteTrue(Pageable pageable);


}
