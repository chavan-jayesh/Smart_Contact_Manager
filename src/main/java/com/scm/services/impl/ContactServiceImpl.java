package com.scm.services.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scm.entities.Contact;
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
    public List<Contact> getContactsByUserId(String userId) {
        
        return contactRepo.findByUserId(userId);
    }

    @Override
    public void delete(String id) {
        Contact currentContact = contactRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("Contact not Found!"));
        contactRepo.delete(currentContact);
    }

}
