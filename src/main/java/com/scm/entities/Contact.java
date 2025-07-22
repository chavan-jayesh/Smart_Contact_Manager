package com.scm.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Contact {
    @Id
    private String id;
    private String name;
    private String email;
    private String phoneNumber;
    private String address;
    private String picture;
    @Column(columnDefinition = "TEXT")
    private String description;
    private boolean favorite = false;

    private String websiteLink;
    private String linkedinLink;

    private String cloudinaryImagePublicId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private User user;

    // @OneToMany(mappedBy = "contact", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    // private List<SocialLink> socialLinks = new ArrayList<>();
}
