package com.netum.osaamispankki.security;

import com.netum.osaamispankki.user.domain.User;
import com.netum.osaamispankki.user.domain.UserCompany;
import com.netum.osaamispankki.user.modals.CompanyRole;
import io.jsonwebtoken.*;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

import static com.netum.osaamispankki.security.SecurityConstants.EXPIRATION_TIME;
import static com.netum.osaamispankki.security.SecurityConstants.SECRET;
import static com.netum.osaamispankki.user.common.GenericHelper.notNull;

@Component
public class JWTProvider {

    public String generateToken(Authentication authentication) {

        User user = (User) authentication.getPrincipal();
        String userId = Long.toString(user.getId());
        Date now = new Date();
        Date expirationTime = new Date(now.getTime() + EXPIRATION_TIME);
        String userFullName = "No profile!";
        if (notNull(user.getFirstName()) || notNull(user.getSurname())) {
            userFullName = user.getFirstName() + " " + user.getSurname();
        }
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", userId);
        claims.put("username", user.getUsername());
        claims.put("name", userFullName);
        claims.put("roles", getCompanyRoles(user.getUserCompanies()));


        String jwt = Jwts
                .builder()
                .setSubject(userId)
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expirationTime)
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();

        return jwt;
    }

    public boolean validateJWT(String token) {
        try {
            Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token);
            return true;
        } catch (MalformedJwtException ex) {
            System.out.println("Invalid JWT exception");
        } catch (ExpiredJwtException ex) {
            System.out.println("expired JWT exception");
        } catch (UnsupportedJwtException ex) {
            System.out.println("unsupported JWT exception");
        } catch (IllegalArgumentException ex) {
            System.out.println("JWT claims string is empty");
        }
        return false;
    }

    public Long getUserIdFromToken(String token ) {
        Claims  claims = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody();
        String idAsString = claims.get("id").toString();
        return Long.parseLong(idAsString);
    }

    private List<CompanyRole> getCompanyRoles(Set<UserCompany> userCompanies) {
        return userCompanies.stream()
                .filter( x -> notNull(x.getRole()))
                .map( y -> new CompanyRole(y.getCompany_name(), y.getId(), y.getRole())).collect(Collectors.toList());
    }
}
