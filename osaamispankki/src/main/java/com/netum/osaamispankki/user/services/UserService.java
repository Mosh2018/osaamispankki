package com.netum.osaamispankki.user.services;

import com.netum.osaamispankki.security.JWTRsponseToFrontend;
import com.netum.osaamispankki.user.domain.*;
import com.netum.osaamispankki.user.exceptions.OsaamispankkiException;
import com.netum.osaamispankki.user.repository.UserAndCompanyRepository;
import com.netum.osaamispankki.user.repository.UserRepository;
import com.netum.osaamispankki.user.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import static com.netum.osaamispankki.user.common.GenericHelper.*;
import static com.netum.osaamispankki.user.common.UtilsMethods.notBlank;
import static com.netum.osaamispankki.user.common.UtilsMethods.setExceptionMessage;
import static com.netum.osaamispankki.user.modals.Roles.COMPANY_WORKER;

@Service
public class UserService extends HeadService {

    @Autowired private BCryptPasswordEncoder cryptPasswordEncoder;
    @Autowired private EmailSenderService emailSenderService;
    @Autowired private UserRepository userRepository;
    @Autowired private CompanyService companyService;
    @Autowired private UserAndCompanyRepository userAndCompanyRepository;


    public JWTRsponseToFrontend createUser(User user) {
        user.setPassword(cryptPasswordEncoder.encode(user.getPassword()));

        /* adding company to user */
        if (notBlank(user.getCompanyName())) {
            Company company = companyService.getCompany(user.getCompanyName());
            if (!isNull(company)) {
                UserAndCompany userAndCompany = userAndCompanyRepository.findByCompanyId(company.getId());
                userAndCompany.setCompanyUsers(tSet(user));
                user.setUserAndCompanies(tSet(userAndCompany));
            }
        }

        /* adding role to user */
        if (notNull(user.getId())) {
            throw new OsaamispankkiException(setExceptionMessage("System_error", "Can not add role to existed user use another function for that"));
        }
        if (user.getUserAndCompanies().isEmpty()) {
            user.setRoles(tSet(new Role()));
        } else {
            UserAndCompany conformation = user.getUserAndCompanies().stream().findFirst().get();

            Role role = new Role();
            role.setRole(COMPANY_WORKER);
            role.setCompanyId(conformation.getCompanyId());
            user.setRoles(tSet(role));
        }

        try {
            userRepository.save(user);
        } catch (Exception e) {
            throw new OsaamispankkiException(setExceptionMessage("user", e.getMessage()));
        }

        /* send a email conformation */
        TokenConfirmation tokenConfirmation =
                emailSenderService.sendConfirmationEmail(userRepository.save(user));
        user.setTokenConfirmation(tokenConfirmation);

        userRepository.save(user);
        return new JWTRsponseToFrontend(tokenConfirmation.getToken(), true);
    }

}
