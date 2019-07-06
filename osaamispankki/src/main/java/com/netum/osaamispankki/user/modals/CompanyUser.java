package com.netum.osaamispankki.user.modals;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CompanyUser {
    private Long userId;
    private String username;
    private Boolean conformed;
}
