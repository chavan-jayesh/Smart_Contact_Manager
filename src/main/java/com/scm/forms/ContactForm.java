package com.scm.forms;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ContactForm {

    @NotBlank(message = "*Name cannnot me Empty!")
    private String name;

    @NotBlank(message = "*Email cannot be Empty!")
    @Email(message = "*Invalid Email Address!")
    private String email;

    @NotBlank(message = "*Phone Number cannot be Empty!")
    @Pattern(regexp = "^[0-9]{10}$", message = "*Invalid Phone Number")
    private String phoneNumber;

    private String description;
    private String address;
    private boolean favorite;
    private String websiteLink;
    private String linkedinLink;
    private MultipartFile contactImage;
    private String picture;

}
