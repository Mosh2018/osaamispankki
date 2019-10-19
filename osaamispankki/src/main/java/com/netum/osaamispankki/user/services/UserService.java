package com.netum.osaamispankki.user.services;

import com.netum.osaamispankki.security.JWTRsponseToFrontend;
import com.netum.osaamispankki.user.domain.TokenConfirmation;
import com.netum.osaamispankki.user.domain.User;
import com.netum.osaamispankki.user.exceptions.OsaamispankkiException;
import com.netum.osaamispankki.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import static com.netum.osaamispankki.user.common.UtilsMethods.setExceptionMessage;

@Service
public class UserService extends HeadService {

    @Autowired private BCryptPasswordEncoder cryptPasswordEncoder;
    @Autowired private EmailSenderService emailSenderService;
    @Autowired private UserRepository userRepository;


    public JWTRsponseToFrontend createUser(User user) {
        user.setPassword(cryptPasswordEncoder.encode(user.getPassword()));
        TokenConfirmation tokenConfirmation;


        try {
            /* send a email conformation */
             tokenConfirmation = emailSenderService.sendConfirmationEmail(userRepository.save(user));
             user.setTokenConfirmation(tokenConfirmation);

            userRepository.save(user);
        } catch (Exception e) {
            userRepository.delete(user);
            throw new OsaamispankkiException(setExceptionMessage("user", e.getMessage()));
        }
        return new JWTRsponseToFrontend(tokenConfirmation.getToken(), true);
    }

}
