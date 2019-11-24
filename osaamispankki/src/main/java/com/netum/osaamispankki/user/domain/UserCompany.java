package com.netum.osaamispankki.user.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.netum.osaamispankki.user.modals.EmploymentType;
import com.netum.osaamispankki.user.modals.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class UserCompany {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "company is required")
    private String company_name;

    @NotBlank(message = "position is required")
    private String position;

    @Enumerated(EnumType.STRING)
    private EmploymentType employmentType;

    @JsonFormat(pattern = "dd.MM.yyyy")
    private Date startingTime;
    @JsonFormat(pattern = "dd.MM.yyyy")
    private Date endingTime;
    private int salary;

    @ManyToOne()
    @JoinColumn
    @JsonIgnore
    private User user;

    @ManyToOne()
    @JoinColumn
    @JsonIgnore
    private Company company;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "varchar(255) default 'GUEST'")
    private Role role = Role.ROLE_GUEST;

    private String companyCode;

    @Column(columnDefinition = "tinyint(1) default 0")
    private Boolean admittedCompanyRole = false;

    @Column()
    private String admittedCompany;

}
