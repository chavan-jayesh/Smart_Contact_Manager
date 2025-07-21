package com.scm.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.scm.entities.Contact;
import com.scm.entities.User;
import com.scm.forms.ContactForm;
import com.scm.forms.ContactSearchForm;
import com.scm.helper.AppConstants;
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

        Contact contact = new Contact();

        contact.setName(contactForm.getName());
        contact.setEmail(contactForm.getEmail());
        contact.setPhoneNumber(contactForm.getPhoneNumber());
        contact.setAddress(contactForm.getAddress());

        if(contactForm.getContactImage() != null && !contactForm.getContactImage().isEmpty()){
            String filename = UUID.randomUUID().toString();
            String fileURL = imageService.uploadImage(contactForm.getContactImage(), filename);
            contact.setPicture(fileURL);
            contact.setCloudinaryImagePublicId(filename);
        }

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

    @GetMapping
    public String viewContact(@RequestParam(value = "page", defaultValue = "0") int page, 
                              @RequestParam(value = "size", defaultValue = AppConstants.PAGE_SIZE+"") int size,
                              @RequestParam(value = "sortBy", defaultValue = "name") String sortBy, 
                              @RequestParam(value = "direction", defaultValue = "asc") String direction,
                              Model model, Authentication authentication){

        String username = Helper.getEmailOfLoggedInUser(authentication);

        User user = userService.getUserByEmail(username);

        Page<Contact> pageContacts = contactService.getContactsByUserId(user.getUserId(), page, size, sortBy, direction);

        model.addAttribute("pageContacts", pageContacts);

        return "user/contacts";
    }

    //Search Handler
    @GetMapping("/search")
    public String searchHandler(@ModelAttribute ContactSearchForm contactSearchForm,
                                @RequestParam(value = "page", defaultValue = "0") int page, 
                                @RequestParam(value = "size", defaultValue = AppConstants.PAGE_SIZE+"") int size,
                                @RequestParam(value = "sortBy", defaultValue = "name") String sortBy, 
                                @RequestParam(value = "direction", defaultValue = "asc") String direction,
                                Model model, Authentication authentication){
        
                                System.out.println("Field - "+ contactSearchForm.getField() + ", Value - "+ contactSearchForm.getValue());

        Page<Contact> pageContacts = null;

        String username = Helper.getEmailOfLoggedInUser(authentication);

        User user = userService.getUserByEmail(username);
        
        if(contactSearchForm.getField().equalsIgnoreCase("Name")){
            pageContacts = contactService.searchContactByName(contactSearchForm.getValue(), page, size, sortBy, direction, user);
        }
        else if(contactSearchForm.getField().equalsIgnoreCase("Email")){
            pageContacts = contactService.searchContactByEmail(contactSearchForm.getValue(), page, size, sortBy, direction, user);
        }
        else if(contactSearchForm.getField().equalsIgnoreCase("Phone")){
            pageContacts = contactService.searchContactByPhone(contactSearchForm.getValue(), page, size, sortBy, direction, user);
        }

        model.addAttribute("pageContacts", pageContacts);

        return "user/search";
    }

    @GetMapping("/delete/{contactId}")
    public String deleteContact(@PathVariable String contactId, HttpSession session){
        contactService.delete(contactId);
        session.setAttribute("message", 
                             Message.builder()
                                    .content("Contact Deleted Successfully!")
                                    .messageType(MessageType.green).build());

        return "redirect:/user/contacts"; 
    }

    @GetMapping("/view/{contactId}")
    public String updateContactFormView(@PathVariable("contactId") String contactId, Model model){
        
        Contact contact = contactService.getContactById(contactId);

        ContactForm contactForm = new ContactForm();
        contactForm.setName(contact.getName());
        contactForm.setEmail(contact.getEmail());
        contactForm.setPhoneNumber(contact.getPhoneNumber());
        contactForm.setAddress(contact.getAddress());
        contactForm.setDescription(contact.getDescription());
        contactForm.setFavorite(contact.isFavorite());
        contactForm.setWebsiteLink(contact.getWebsiteLink());
        contactForm.setLinkedinLink(contact.getLinkedinLink());
        contactForm.setPicture(contact.getPicture());

        model.addAttribute("contactForm", contactForm);
        model.addAttribute("contactId", contactId);

        return "user/update_contact_view";

    }

    @PostMapping("/update/{contactId}")
    public String updateContact(@PathVariable("contactId") String contactId, @Valid @ModelAttribute("contactForm") ContactForm contactForm, 
                                BindingResult result, Model model, HttpSession session){
        
        if(result.hasErrors()){
            session.setAttribute("message", Message.builder()
                                .content("Please fill in all required fields correctly!")
                                .messageType(MessageType.red).build());

            return "user/update_contact_view";
        }
        
        Contact contact = contactService.getContactById(contactId);
        
        contact.setId(contactId);
        contact.setName(contactForm.getName());
        contact.setEmail(contactForm.getEmail());
        contact.setPhoneNumber(contactForm.getPhoneNumber());
        contact.setAddress(contactForm.getAddress());
        contact.setDescription(contactForm.getDescription());
        contact.setFavorite(contactForm.isFavorite());
        contact.setWebsiteLink(contactForm.getWebsiteLink());
        contact.setLinkedinLink(contactForm.getLinkedinLink());
        
        if(contactForm.getContactImage() != null && !contactForm.getContactImage().isEmpty()){
            //Process image
            String filename = UUID.randomUUID().toString();
            String fileURL = imageService.uploadImage(contactForm.getContactImage(), filename);
            contact.setCloudinaryImagePublicId(filename);
            contact.setPicture(fileURL);
            contactForm.setPicture(fileURL);
        }

        Contact updatedContact = contactService.update(contact);

        Message message = Message.builder()
                                 .content("Contact Updated successfully!")
                                 .messageType(MessageType.green).build();

        session.setAttribute("message", message);
        
        return "redirect:/user/contacts/view/" + contactId;
    }

}
