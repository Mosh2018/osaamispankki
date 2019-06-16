package com.netum.osaamispankki.domain;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Username is required")
    @Column(unique = true)
    private String username;

    @NotBlank
    @Size(min = 8, message = "Password most be 8 digits long or longer")
    private String password;

    @NotBlank
    private String password2;

    private boolean enabled;

    private boolean tokenExpired;

    @OneToMany(mappedBy = "user_user", cascade = CascadeType.ALL)
    private Set<Role> roles;

    @OneToOne
    private Profile profile;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
        private Set<UserCompany> userCompanies = new HashSet<>();

    @JsonFormat(pattern = "dd.MM.yyyy")
    private Date created_At;

    @JsonFormat(pattern = "dd.MM.yyyy")
    private Date update_At;

    @PrePersist
    public void onCreated() {
        this.created_At = new Date();
    }

    @PreUpdate
    public void onUpdated() {
        this.update_At = new Date();
    }


}
