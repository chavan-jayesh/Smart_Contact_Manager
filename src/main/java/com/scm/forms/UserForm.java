package com.scm.forms;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class UserForm {
    @NotBlank(message = "Name Cannnot me Empty!")
    private String name;

    @NotBlank(message = "Email cannot be Empty!")
    @Email(message = "Invalid Email Address!")
    private String email;

    @NotBlank(message = "Password cannot be Empty!")
    @Size(min = 6,  message = "Min 6 characters required!")
    private String password;

    @NotBlank(message = "Contact Number cannot be Empty!")
    @Size(min = 8, max = 12,  message = "Invalid Phone Number")
    private String phoneNumber;

    @NotBlank(message = "About cannot be Empty!")
    private String about;

    private MultipartFile profileImage;
    private String profilePic;
}
