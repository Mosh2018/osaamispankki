package com.netum.osaamispankki.security;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class InvalidUserRequest {

    private String username = "Invalid username";
    private String password = "Invalid password";
}
