package com.scm.services;

import java.util.List;

import org.springframework.data.domain.Page;

import com.scm.entities.Contact;
import com.scm.entities.User;

public interface ContactService {

    Contact save(Contact contact);

    Contact update(Contact contact);

    List<Contact> getAllContacts();

    Contact getContactById(String id);
    
    Page<Contact> getContactsByUserId(String userId, int page, int size, String sortBy, String direction);
    Page<Contact> getFavoriteContactsByUserId(String userId, int page, int size, String sortBy, String direction);

    void delete(String id);

    List<Contact> getContactByUsername(String username);

    List<Contact> getRecentContacts(String username);

    //Search Contact
    Page<Contact> searchContactByName(String nameKeyword, int page, int size, String sortBy, String direction, User user);

    Page<Contact> searchContactByEmail(String emailKeyword, int page, int size, String sortBy, String direction, User user);

    Page<Contact> searchContactByPhone(String phoneKeyword, int page, int size, String sortBy, String direction, User user);

}
