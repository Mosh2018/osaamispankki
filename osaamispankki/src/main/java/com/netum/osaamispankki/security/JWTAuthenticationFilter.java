package com.netum.osaamispankki.security;

import com.netum.osaamispankki.user.exceptions.OsaamispankkiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

import static com.netum.osaamispankki.security.SecurityConstants.HEADER_OF_JWT;
import static com.netum.osaamispankki.security.SecurityConstants.TOKEN_PREFIX;
import static com.netum.osaamispankki.user.common.UtilsMethods.notBlank;
import static com.netum.osaamispankki.user.common.UtilsMethods.setExceptionMessage;

public class JWTAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JWTProvider jwtProvider;

    @Autowired
    private CustomUserDetailService detailService;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest,
                                    HttpServletResponse httpServletResponse,
                                    FilterChain filterChain) throws ServletException, IOException {

        try {
            String token = httpServletRequest.getHeader(HEADER_OF_JWT);
            String jwt = "";
            if (StringUtils.hasText(token) && token.startsWith(TOKEN_PREFIX)) {
                jwt = token.replace(TOKEN_PREFIX, "");
            }
            if (notBlank(jwt) && jwtProvider.validateJWT(jwt)) {
                Long userId = jwtProvider.getUserIdFromToken(jwt);
                UserDetails userDetails = detailService.loadUserById(userId);

                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());

                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);

            }
        } catch (Exception ex) {
            throw new OsaamispankkiException(setExceptionMessage("System_error", ex.getMessage()));
        }

        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}
