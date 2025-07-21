package com.scm.services.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.scm.entities.User;
import com.scm.helper.AppConstants;
import com.scm.helper.Helper;
import com.scm.helper.ResourceNotFoundException;
import com.scm.repositories.UserRepo;
import com.scm.services.EmailService;
import com.scm.services.UserService;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EmailService emailService;

    @Override
    public User saveUser(User user) {
        String userId = UUID.randomUUID().toString();
        user.setUserId(userId);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoleList(List.of(AppConstants.ROLE_USER));

        String emailToken = UUID.randomUUID().toString();

        user.setEmailToken(emailToken);

        User savedUser =  userRepo.save(user);

        String emailLink = Helper.getLinkForEmailVerification(emailToken);

        emailService.sendEmail(savedUser.getEmail(), "Verify Account : Smart Contact Manager", emailLink);

        return savedUser;
    }

    @Override
    public User getUserbyId(String id) {
        return userRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("User not Found!"));
    }

    @Override
    public User updateUser(User user) {
       User currentUser = userRepo.findById(user.getUserId()).orElseThrow(()-> new ResourceNotFoundException("User not Found!"));
       currentUser.setName(user.getName());
       currentUser.setEmail(user.getEmail());
       currentUser.setPassword(user.getPassword());
       currentUser.setPhoneNumber(user.getPhoneNumber());
       currentUser.setAbout(user.getAbout());
       currentUser.setProfilePic(user.getProfilePic());;
       currentUser.setEnabled(user.isEnabled());
       currentUser.setEmailVerified(user.isEmailVerified());
       currentUser.setPhoneVerified(user.isPhoneVerified());
       currentUser.setProvider(user.getProvider());
       currentUser.setProviderUserId(user.getProviderUserId());

       return userRepo.save(currentUser);
    }

    @Override
    public void deleteUser(String id) {
        User currentUser = userRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("User not Found!"));
        userRepo.delete(currentUser);
    }

    @Override
    public boolean isUserExists(String id) {
        User currentUser = userRepo.findById(id).orElse(null);
        return currentUser!=null;
    }
        

    @Override
    public boolean isUserExistsByEmail(String email) {
        User currentUser = userRepo.findByEmail(email).orElse(null);
        return currentUser!=null;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepo.findAll();
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepo.findByEmail(email).orElse(null);

    }

    @Override
    public User getUserByToken(String token) {
        return userRepo.findByEmailToken(token).orElse(null);
    }

}
