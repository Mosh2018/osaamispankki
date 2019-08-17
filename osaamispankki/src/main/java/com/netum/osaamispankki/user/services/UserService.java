package com.netum.osaamispankki.user.services;

import com.netum.osaamispankki.security.JWTRsponseToFrontend;
import com.netum.osaamispankki.user.domain.*;
import com.netum.osaamispankki.user.exceptions.OsaamispankkiException;
import com.netum.osaamispankki.user.repository.CompanyConformationRepository;
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
    @Autowired private CompanyConformationRepository companyConformationRepository;


    public JWTRsponseToFrontend createUser(User user) {
        user.setPassword(cryptPasswordEncoder.encode(user.getPassword()));

        /* adding company to user */
        if (notBlank(user.getCompanyName())) {
            Company company = companyService.getCompany(user.getCompanyName());
            if (!isNull(company)) {
                CompanyConformation companyConformation = companyConformationRepository.findByCompanyId(company.getId());
                companyConformation.setCompanyUsers(tSet(user));
                user.setCompanyConformations(tSet(companyConformation));
            }
        }

        /* adding role to user */
        if (notNull(user.getId())) {
            throw new OsaamispankkiException(setExceptionMessage("System_error", "Can not add role to existed user use another function for that"));
        }
        if (user.getCompanyConformations().isEmpty()) {
            user.setRoles(tSet(new Role()));
        } else {
            CompanyConformation conformation = user.getCompanyConformations().stream().findFirst().get();

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
