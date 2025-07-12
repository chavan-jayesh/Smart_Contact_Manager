package com.scm.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.scm.entities.Contact;
import com.scm.entities.User;
import com.scm.forms.ContactForm;
import com.scm.helper.Helper;
import com.scm.helper.Message;
import com.scm.helper.MessageType;
import com.scm.services.ContactService;
import com.scm.services.ImageService;
import com.scm.services.UserService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/user/contacts")
public class ContactController {

    @Autowired
    private ContactService contactService;

    @Autowired
    private ImageService imageService;

    @Autowired
    private UserService userService;

    @GetMapping("/add")
    public String addContact(Model model){
        ContactForm contactForm = new ContactForm();
        // ContactForm contactForm = new ContactForm("Jayesh Chavan","jayesh123@gmail.com", "123456789", "This is my contact", "Mharal, Kalyan-421301", true, "www.google.com", "www.linkedin/in/jayesh");
        
        model.addAttribute("contactForm", contactForm);
        return "user/add_contact";
    }

    @PostMapping("/add")
    public String saveContact(@Valid ContactForm contactForm, BindingResult result,Authentication authentication, HttpSession session){
        
        if(result.hasErrors()){
            session.setAttribute("message", Message.builder()
                                .content("Please fill in all required fields correctly!")
                                .messageType(MessageType.red).build());

            return "/user/add_contact";
        }

        String username = Helper.getEmailOfLoggedInUser(authentication);

        User currentUser = userService.getUserByEmail(username);

        String filename = UUID.randomUUID().toString();

        String fileURL = imageService.uploadImage(contactForm.getContactImage(), filename);

        Contact contact = new Contact();

        contact.setName(contactForm.getName());
        contact.setEmail(contactForm.getEmail());
        contact.setPhoneNumber(contactForm.getPhoneNumber());
        contact.setAddress(contactForm.getAddress());
        contact.setPicture(fileURL);
        contact.setCloudinaryImagePublicId(fileURL);
        contact.setDescription(contactForm.getDescription());
        contact.setFavorite(contactForm.isFavorite());
        contact.setWebsiteLink(contactForm.getWebsiteLink());
        contact.setLinkedinLink(contactForm.getLinkedinLink());
        contact.setUser(currentUser);

        contactService.save(contact);

        Message message = Message.builder()
                                .content("New Contact added successfully!")
                                .messageType(MessageType.green).build();

        session.setAttribute("message", message);

        return "redirect:/user/contacts/add";
    }
}
