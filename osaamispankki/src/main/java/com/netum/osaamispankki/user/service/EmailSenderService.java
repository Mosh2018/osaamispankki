package com.netum.osaamispankki.user.service;

import com.netum.osaamispankki.security.JWTRsponseToFrontend;
import com.netum.osaamispankki.user.domain.TokenConfirmation;
import com.netum.osaamispankki.user.domain.User;
import com.netum.osaamispankki.user.exceptions.OsaamispankkiException;
import com.netum.osaamispankki.user.repository.TokenConfirmationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import static com.netum.osaamispankki.user.common.GenericHelper.notNull;
import static com.netum.osaamispankki.user.common.UtilsMethods.setExceptionMessage;

@Service
public class EmailSenderService {

    private JavaMailSender javaMailSender;

    @Autowired
    public EmailSenderService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Value("${spring.mail.username}")
    private String email;

    @Autowired
    private TokenConfirmationRepository repository;

    @Async
    public void sendEmail(SimpleMailMessage  simpleMailMessage) {
        try {
            javaMailSender.send(simpleMailMessage);
        } catch (Exception e) {
            throw new OsaamispankkiException(setExceptionMessage("confirmation_id", e.getMessage()));
        }
    }

    public TokenConfirmation sendConfirmationEmail(User user){


        TokenConfirmation confirmationToken = new TokenConfirmation(user);

        repository.save(confirmationToken);

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(user.getEmail());
        mailMessage.setSubject("Complete Registration!");
        mailMessage.setFrom(email);
        mailMessage.setText("To confirm your account, please click here : "
                +"http://localhost:8080/api/user/confirm-account?token="+confirmationToken.getToken());

        sendEmail(mailMessage);

        return confirmationToken;
    }


    public TokenConfirmation confirmUser(String confirmId) {
        TokenConfirmation confirmation = repository.findByToken(confirmId);
        if (notNull(confirmation)) {
            return confirmation;
        }

        throw new OsaamispankkiException(setExceptionMessage("confirmation_id", "can not confirm this account"));
    }
}
