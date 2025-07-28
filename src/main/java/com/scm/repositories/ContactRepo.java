package com.scm.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.scm.entities.Contact;
import com.scm.entities.User;

@Repository
public interface ContactRepo extends JpaRepository<Contact, String>{

    Page<Contact> findByUser(User user, Pageable pageable);

    @Query("SELECT c FROM Contact c WHERE c.user.email = :email")
    List<Contact> findByUserEmail(@Param("email") String email);


    @Query(value="SELECT c.* FROM contact c INNER JOIN users u ON c.user_user_id = u.user_id WHERE u.email = :email ORDER BY c.created_at DESC LIMIT 5", nativeQuery=true)
    List<Contact> findRecentContactsByEmail(@Param("email") String email);

    @Query("SELECT c FROM Contact c WHERE c.user.id = :userId")
    Page<Contact> findByUserId(@Param("userId") String userId, Pageable pageable);

    @Query("SELECT c FROM Contact c WHERE c.user.id = :userId and c.favorite = true")
    Page<Contact> findByUserIdandIsFavorite(@Param("userId") String userId, Pageable pageable);

    Page<Contact> findByUserAndNameContaining(User user, String nameKeyword, Pageable pageable);
    Page<Contact> findByUserAndEmailContaining(User user, String emailKeyword, Pageable pageable);
    Page<Contact> findByUserAndPhoneNumberContaining(User user, String phoneKeyword, Pageable pageable);

}
