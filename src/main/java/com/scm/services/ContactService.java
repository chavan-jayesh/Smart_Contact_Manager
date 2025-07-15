package com.scm.services;

import java.util.List;

import org.springframework.data.domain.Page;

import com.scm.entities.Contact;

public interface ContactService {

    Contact save(Contact contact);

    Contact update(Contact contact);

    List<Contact> getAllContacts();

    Contact getContactById(String id);
    
    Page<Contact> getContactsByUserId(String userId, int page, int size, String sortBy, String direction);

    void delete(String id);

}
