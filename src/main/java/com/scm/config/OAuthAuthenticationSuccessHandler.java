package com.scm.config;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.scm.entities.Providers;
import com.scm.entities.User;
import com.scm.helper.AppConstants;
import com.scm.repositories.UserRepo;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class OAuthAuthenticationSuccessHandler implements AuthenticationSuccessHandler{

    Logger logger = LoggerFactory.getLogger(OAuthAuthenticationSuccessHandler.class);
    
    @Autowired
    private UserRepo userRepo;


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {

        logger.info("OAuthAuthenticationSuccessHandler");

        //Identifying the provider

        OAuth2AuthenticationToken oAuth2AuthenticationToken = (OAuth2AuthenticationToken) authentication;

        String authorizedClientRegistrationId = oAuth2AuthenticationToken.getAuthorizedClientRegistrationId();

        logger.info(authorizedClientRegistrationId);

        DefaultOAuth2User oauthuser = (DefaultOAuth2User) authentication.getPrincipal();

        // oauthuser.getAttributes().forEach((key, value)->{
        //     logger.info(key+" : "+value);
        // });

        // logger.info(user.getAuthorities().toString());

        User user = new User();
        
        //defaults
        user.setUserId(UUID.randomUUID().toString());
        user.setRoleList(List.of(AppConstants.ROLE_USER));
        user.setEmailVerified(true);
        user.setEnabled(true);

        if(authorizedClientRegistrationId.equalsIgnoreCase("google")){
            //Setting google user values

            String email = oauthuser.getAttribute("email").toString();
            String name = oauthuser.getAttribute("name").toString();
            String picture = oauthuser.getAttribute("picture").toString();

            user.setEmail(email);
            user.setName(name);
            user.setProfilePic(picture);
            user.setPassword("password");
            user.setProvider(Providers.GOOGLE);
            user.setProviderUserId(oauthuser.getName());
            user.setAbout("This is account is created using google!");

        }
        else if(authorizedClientRegistrationId.equalsIgnoreCase("github")){
            //Setting github user values

            String email = oauthuser.getAttribute("email")!=null ?
                           oauthuser.getAttribute("email").toString() :
                           oauthuser.getAttribute("login").toString()+"@gmail.com";
            
            String name = oauthuser.getAttribute("login").toString();

            String picture = oauthuser.getAttribute("avatar_url").toString();

            user.setEmail(email);
            user.setName(name);
            user.setProfilePic(picture);
            user.setPassword("password");
            user.setProvider(Providers.GITHUB);
            user.setProviderUserId(oauthuser.getName());
            user.setAbout("This is account is created using github!");

        }
        else if(authorizedClientRegistrationId.equalsIgnoreCase("linkedin")){
            //linkedin
        }
        else{
            logger.info("OAuthAuthenticationSuccessHandler: Unknown Provider!");
        }

        //Saving user in database

        User user1 = userRepo.findByEmail(user.getEmail()).orElse(null);

        if(user1==null){
            userRepo.save(user);
            logger.info("User Saved: "+ user.getEmail());
        }

        new DefaultRedirectStrategy().sendRedirect(request, response, "/user/profile");
        
    }

}
