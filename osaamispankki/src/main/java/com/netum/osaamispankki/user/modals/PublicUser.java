package com.netum.osaamispankki.user.modals;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PublicUser {
    private String username;
    private String surname;
    private boolean isActive;
}
