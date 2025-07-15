package com.scm.services.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.scm.entities.Contact;
import com.scm.entities.User;
import com.scm.helper.ResourceNotFoundException;
import com.scm.repositories.ContactRepo;
import com.scm.services.ContactService;

@Service
public class ContactServiceImpl implements ContactService{

    @Autowired
    private ContactRepo contactRepo;


    @Override
    public Contact save(Contact contact) {

        String contactId = UUID.randomUUID().toString();
        contact.setId(contactId);

        return contactRepo.save(contact);
    }

    @Override
    public Contact update(Contact contact) {
        Contact currentContact = contactRepo.findById(contact.getId()).orElseThrow(()-> new ResourceNotFoundException("Contact not Found!"));

        currentContact.setId(contact.getId());
        currentContact.setName(contact.getName());
        currentContact.setEmail(contact.getEmail());
        currentContact.setPhoneNumber(contact.getPhoneNumber());
        currentContact.setAddress(contact.getAddress());
        currentContact.setPicture(contact.getPicture());
        currentContact.setDescription(contact.getDescription());
        currentContact.setFavorite(contact.isFavorite());
        currentContact.setWebsiteLink(contact.getWebsiteLink());
        currentContact.setLinkedinLink(contact.getLinkedinLink());
        currentContact.setUser(contact.getUser());

        return currentContact;
    }

    @Override
    public List<Contact> getAllContacts() {
        return contactRepo.findAll();
    }

    @Override
    public Contact getContactById(String id) {
        return contactRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("Contact not Found!"));
    }

    @Override
    public Page<Contact> getContactsByUserId(String userId, int page, int size, String sortBy, String direction) {

        Sort sort = direction.equals("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();

        PageRequest pageRequest = PageRequest.of(page, size);
        
        return contactRepo.findByUserId(userId, pageRequest);
    }

    @Override
    public void delete(String id) {
        Contact currentContact = contactRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("Contact not Found!"));
        contactRepo.delete(currentContact);
    }

    @Override
    public Page<Contact> searchContactByName(String nameKeyword, int page, int size, String sortBy, String direction, User user) {
        Sort sort = direction.equals("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();

        PageRequest pageRequest = PageRequest.of(page, size);
        
        return contactRepo.findByUserAndNameContaining(user, nameKeyword, pageRequest);
    }

    @Override
    public Page<Contact> searchContactByEmail(String emailKeyword, int page, int size, String sortBy, String direction, User user) {
        Sort sort = direction.equals("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();

        PageRequest pageRequest = PageRequest.of(page, size);
        
        return contactRepo.findByUserAndEmailContaining(user, emailKeyword, pageRequest);
    }

    @Override
    public Page<Contact> searchContactByPhone(String phoneKeyword, int page, int size, String sortBy, String direction, User user) {
        Sort sort = direction.equals("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();

        PageRequest pageRequest = PageRequest.of(page, size);
        
        return contactRepo.findByUserAndPhoneNumberContaining(user, phoneKeyword, pageRequest);
    }



}
