package com.netum.osaamispankki.user.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TokenConfirmation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String token;

    @OneToOne(mappedBy = "tokenConfirmation")
    private User user;

    public TokenConfirmation(User user) {
        setUser(user);
        setToken(UUID.randomUUID().toString());
    }
}
