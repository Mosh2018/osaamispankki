package com.netum.osaamispankki.user.domain;

import com.netum.osaamispankki.user.modals.Roles;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "varchar(255) default 'GUEST'")
    private Roles role = Roles.GUEST;

    @Column(columnDefinition = "tinyint(1) default 0")
    private Boolean admittedCompanyRole = false;

    private Long companyId;

    @Column(columnDefinition = "tinyint(1) default 0")
    private Boolean admittedCompany = false;

}
