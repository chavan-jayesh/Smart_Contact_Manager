package com.scm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.scm.entities.Contact;
import com.scm.services.ContactService;

@RestController
@RequestMapping("/api")
public class ApiController {

    @Autowired
    private ContactService contactService;

    // Get Contact
    @GetMapping("/contacts/{contactId}")
    public Contact getContact(@PathVariable String contactId){
        Contact contact = contactService.getContactById(contactId);
        contact.setUser(null);
        return contact;
    }

}
