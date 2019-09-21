package com.netum.osaamispankki.user.service;

import com.netum.osaamispankki.security.JWTProvider;
import com.netum.osaamispankki.security.JWTRsponseToFrontend;
import com.netum.osaamispankki.security.UserLoginRequest;
import com.netum.osaamispankki.user.domain.TokenConfirmation;
import com.netum.osaamispankki.user.domain.User;
import com.netum.osaamispankki.user.domain.UserCompany;
import com.netum.osaamispankki.user.exceptions.OsaamispankkiException;
import com.netum.osaamispankki.user.modals.PublicUser;
import com.netum.osaamispankki.user.repository.UserRepository;
import com.netum.osaamispankki.user.services.EmailSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

import static com.netum.osaamispankki.security.SecurityConstants.TOKEN_PREFIX;
import static com.netum.osaamispankki.user.common.GenericHelper.isNull;
import static com.netum.osaamispankki.user.common.ReadyMadeExceptions.userNotFoundException;
import static com.netum.osaamispankki.user.common.UtilsMethods.setExceptionMessage;

@Service
public class LoginService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JWTProvider jwtProvider;

    @Autowired
    private EmailSenderService emailSenderService;

    public User save(User user) {
        return userRepository.save(user);
    }

    public boolean isExist(String username) {
        return userRepository.existsByUsername(username);
    }

    public boolean isExist(Long id) {
        return userRepository.existsById(id);
    }

    public User get(String username) {
        return userRepository.findByUsername(username);
    }
    // Ends

    public PublicUser getUser(String username) {
        if (username == null || !isExist(username)) {
            throw userNotFoundException();
        }
        User user = get(username);
        return new PublicUser(user.getFirstName(), user.getSurname(), user.isEnabled());
    }

    public User getUser() {
        Long id = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
        if (isNull(id) || !isExist(id)) {
            throw userNotFoundException();
        }
        return userRepository.findById(id).get();
    }

    public JWTRsponseToFrontend loginJwt(UserLoginRequest loginRequest) {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        return new JWTRsponseToFrontend(TOKEN_PREFIX + jwtProvider.generateToken(authentication), true);
    }

    public JWTRsponseToFrontend confirmUserAccount(String confirmId) {
        TokenConfirmation confirmation = emailSenderService.confirmUser(confirmId);
        try {
            User user = confirmation.getUser();
            user.setEnabled(true);
            save(user);

            return new JWTRsponseToFrontend("Account gas been confirmed", true);
        } catch (Exception e){
            throw new OsaamispankkiException(setExceptionMessage("confirmation_id", e.getMessage()));
        }
    }

}
