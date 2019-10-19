package com.netum.osaamispankki.user.modals;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CompanyRole {
    private String company;
    private Long companyId;
    private Role role;
}
