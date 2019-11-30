package com.netum.osaamispankki.user.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.UniqueElements;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "businessId can not be empty")
    @Column(unique = true)
    private String businessId;

    private String companyName;

    private String businessLine;

    private String code;

    private String companyForm;

    private String createdByPerson;

    @Column(unique = true)
    private Long createdBy;

    @JsonFormat(pattern = "dd.MM.yyyy")
    private Date registrationDate;

    @NotBlank(message = "company code is required")
    private String companyCode;

    @OneToMany(mappedBy = "company",
            fetch = FetchType.EAGER,
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private Set<UserCompany> userCompanies;

    private String activationCode;

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
