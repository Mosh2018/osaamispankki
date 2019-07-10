package com.netum.osaamispankki.security;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class JWTRsponseToFrontend {

    private String jwt;
    private boolean isSuccess;

    @Override
    public String toString() {
        return "JWTloginSuccessPerponse{" +
                "success= " + isSuccess() +
                "," +
                "token= " + getJwt() +
                "}";
    }


}
