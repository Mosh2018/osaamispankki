package com.netum.osaamispankki.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class CompanyConformation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long companyId;
    private Boolean conformation;

    @OneToMany(mappedBy = "companyConformation", cascade = CascadeType.ALL)
    private Set<UserCompany> companyUsers = new HashSet<>();
}
