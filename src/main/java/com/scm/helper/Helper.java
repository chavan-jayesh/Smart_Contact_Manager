package com.scm.helper;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;

public class Helper {

    public static String getEmailOfLoggedInUser(Authentication authentication){
        
        if(authentication instanceof OAuth2AuthenticationToken){

            OAuth2AuthenticationToken oAuth2AuthenticationToken = (OAuth2AuthenticationToken) authentication;
            String clientId = oAuth2AuthenticationToken.getAuthorizedClientRegistrationId();

            OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
            String username = "";
            
            if(clientId.equalsIgnoreCase("google")){
                System.out.println("Getting email from google!");
                username = oAuth2User.getAttribute("email").toString();
            }
            else if(clientId.equalsIgnoreCase("github")){
                System.out.println("Getting email from github!");
                username = oAuth2User.getAttribute("email")!=null ?
                           oAuth2User.getAttribute("email").toString() :
                           oAuth2User.getAttribute("login").toString()+"@gmail.com";
            }
            return username;
        }
        else{
            System.out.println("Getting data from local database!");
            return authentication.getName();
        }
    }

    public static String getLinkForEmailVerification(String emailToken){

        String link = "http://localhost:8080/auth/verify-email?token=" + emailToken;

        return link;
    }

}
