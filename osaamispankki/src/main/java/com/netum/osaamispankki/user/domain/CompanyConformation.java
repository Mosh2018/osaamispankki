package com.netum.osaamispankki.user.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @Column(unique = true)
    private Long companyId;

    @ManyToMany(
            fetch = FetchType.LAZY,
            cascade = { CascadeType.MERGE, CascadeType.PERSIST},
            mappedBy = "companyConformations")
    @JsonIgnore
    private Set<User> companyUsers = new HashSet<>();
}
