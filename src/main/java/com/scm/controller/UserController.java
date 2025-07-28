package com.scm.controller;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestMapping;

import com.scm.entities.Contact;
import com.scm.entities.User;
import com.scm.forms.UserForm;
import com.scm.helper.Helper;
import com.scm.helper.Message;
import com.scm.helper.MessageType;
import com.scm.services.ContactService;
import com.scm.services.ImageService;
import com.scm.services.UserService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
@RequestMapping("/user")
public class UserController {
    
    @Autowired
    private ContactService contactService;

    @Autowired
    private UserService userService;

    @Autowired
    private ImageService imageService;

    @GetMapping("/dashboard")
    public String userDashboard(Authentication authentication, Model model) {
        String username = Helper.getEmailOfLoggedInUser(authentication);
        List<Contact> contacts = contactService.getContactByUsername(username);
        model.addAttribute("contactsCount", contacts.size());
        model.addAttribute("favoriteCount", contacts.stream()
                                                                  .filter(contact -> contact.isFavorite())
                                                                  .count());
        model.addAttribute("recentCount", contactService.getRecentContacts(username).size());
        model.addAttribute("recentContacts", contactService.getRecentContacts(username));
        return "user/dashboard";
    }

    @GetMapping("/profile")
    public String userProfile(Authentication authentication, Model model) {
        String username = Helper.getEmailOfLoggedInUser(authentication);
        List<Contact> contacts = contactService.getContactByUsername(username);
        model.addAttribute("contacts", contacts);

        return "user/profile";
    }

    @GetMapping("/update-profile/{userId}")
    public String viewUserForm(@PathVariable("userId") String userId, Model model){
        
        User user = userService.getUserbyId(userId);

        UserForm userForm = new UserForm();

        userForm.setName(user.getName());
        userForm.setPhoneNumber(user.getPhoneNumber());
        userForm.setAbout(user.getAbout());
        userForm.setProfilePic(user.getProfilePic());

        model.addAttribute("userForm", userForm);
        model.addAttribute("userId", userId);

        return "user/update_profile_view";

    }

    
    @PostMapping("/update-profile/{userId}")
    public String updateUser(@PathVariable("userId") String userId, @Valid @ModelAttribute("userForm") UserForm userForm, 
                              BindingResult result, HttpSession session, Model model){
        
       List<FieldError> errors = result.getFieldErrors().stream()
                                       .filter(err -> !err.getField().equals("email") && !err.getField().equals("password")) 
                                       .collect(Collectors.toList());


        if(!errors.isEmpty()){
            for (FieldError error : errors) {
                System.out.println("Field: " + error.getField());
                System.out.println("Message: " + error.getDefaultMessage());
            }
            session.setAttribute("message", Message.builder()
                                .content("Please fill in all required fields correctly!")
                                .messageType(MessageType.red).build());

            return "user/update_profile_view";
        }

        User user = userService.getUserbyId(userId);

        user.setName(userForm.getName());
        user.setPhoneNumber(userForm.getPhoneNumber());
        user.setAbout(userForm.getAbout());

        if(userForm.getProfileImage() != null && !userForm.getProfileImage().isEmpty()){
            //Process image
            String filename = UUID.randomUUID().toString();
            String fileURL = imageService.uploadImage(userForm.getProfileImage(), filename);
            user.setCloudinaryImagePublicId(filename);
            user.setProfilePic(fileURL);
            userForm.setProfilePic(fileURL);
        }

        userService.updateUser(user);

        Message message = Message.builder()
                                 .content("Profile Updated successfully!")
                                 .messageType(MessageType.green).build();

        session.setAttribute("message", message);

        return "redirect:/user/update-profile/" + userId;
    }
    
}
