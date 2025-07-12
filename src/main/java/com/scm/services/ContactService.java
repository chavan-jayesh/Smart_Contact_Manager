package com.scm.services;

import java.util.List;

import com.scm.entities.Contact;

public interface ContactService {

    Contact save(Contact contact);

    Contact update(Contact contact);

    List<Contact> getAllContacts();

    Contact getContactById(String id);
    
    List<Contact> getContactsByUserId(String userId);

    void delete(String id);

}
