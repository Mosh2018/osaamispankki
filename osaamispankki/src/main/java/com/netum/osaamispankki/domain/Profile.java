package com.netum.osaamispankki.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Profile {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Long id;

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
    @NotBlank(message = "First name is required")
    private String phoneNo;

    @OneToOne(fetch = FetchType.EAGER)
    @PrimaryKeyJoinColumn
    @JsonIgnore
    private User user;

    private String town;

    @NotBlank(message = "County is required")
    private String country;

    @NotBlank(message = "Nationality is required")
    private String nationality;

    public void setBirthday(Date birthday) {
        this.birthday = new Date(birthday.getTime());
    }

}
