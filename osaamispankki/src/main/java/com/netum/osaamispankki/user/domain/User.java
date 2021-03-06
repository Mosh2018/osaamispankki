package com.netum.osaamispankki.user.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.netum.osaamispankki.user.common.GenericHelper;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@EqualsAndHashCode(exclude = "userCompanies")

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Username is required")
    @Column(unique = true)
    private String username;

    @NotBlank(message = "First name is required")
    private String firstName;

    @NotBlank(message = "Last name is required")
    private String surname;

    @Temporal(TemporalType.DATE)
    @JsonFormat(pattern = "dd.MM.yyyy")
    private Date birthday;

    @Column(unique = true)
    @Email(message = "Input a valid email")
    @NotBlank(message = "Email is required")
    private String email;

    @Column(unique = true)
    @NotBlank(message = "Phone number is required")
    private String phoneNo;

    @NotBlank
    @Size(min = 8, message = "Password most be 8 digits long or longer")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @Transient
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password2;

    private boolean enabled;

    private boolean tokenExpired;

    @OneToMany(mappedBy = "user",
            fetch = FetchType.EAGER,
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private Set<UserCompany> userCompanies;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(referencedColumnName = "id")
    private TokenConfirmation tokenConfirmation;

    @OneToOne(cascade = CascadeType.ALL) //todo move it, and create new table with userID
    @JoinColumn(referencedColumnName = "id")
    private HomeAddress homeAddress;

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

    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (GenericHelper.isNull(getUserCompanies())) {
            return Collections.EMPTY_LIST;
        }
        return getUserCompanies().stream().map(x -> {
            return new SimpleGrantedAuthority(x.getRole().toString());
        }).collect(Collectors.toList());
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return true;
    }
}
