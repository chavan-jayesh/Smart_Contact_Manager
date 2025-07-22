package com.scm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.scm.entities.User;
import com.scm.helper.Message;
import com.scm.helper.MessageType;
import com.scm.services.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @GetMapping("/verify-email")
    public String verifyEmail(@RequestParam("token") String token, HttpSession session){

        User user = userService.getUserByToken(token);

        if(user!=null){
            if(user.getEmailToken().equals(token)){
                user.setEmailVerified(true);
                user.setEnabled(true);
                userService.updateUser(user);
                Message message = Message.builder()
                                 .content("Email is Verified! Now Your Account is Enabled!")
                                 .messageType(MessageType.green).build();
                session.setAttribute("message", message);
                return "success_page";
            }
        }

        Message message = Message.builder()
                                 .content("Email Not Verified! Token is not associated with User!")
                                 .messageType(MessageType.red).build();
        
        session.setAttribute("message", message);

        return "error_page";
    }
}
