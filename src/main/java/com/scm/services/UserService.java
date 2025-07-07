package com.scm.services;

import java.util.List;

import com.scm.entities.User;

public interface UserService {
    
    User saveUser(User user);

    User getUserbyId(String id);

    User updateUser(User user);

    void deleteUser(String id);
    
    boolean isUserExists(String id);

    boolean isUserExistsByEmail(String email);

    List<User>  getAllUsers();

}
