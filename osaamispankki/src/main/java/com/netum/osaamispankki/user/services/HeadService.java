package com.netum.osaamispankki.user.services;

import com.netum.osaamispankki.user.domain.User;
import com.netum.osaamispankki.user.domain.UserCompany;
import com.netum.osaamispankki.user.repository.CompanyRepository;
import com.netum.osaamispankki.user.repository.UserCompanyRepository;
import com.netum.osaamispankki.user.repository.UserRepository;
import com.netum.osaamispankki.user.validation.FrontendValidations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Set;

import static com.netum.osaamispankki.user.common.GenericHelper.isNull;
import static com.netum.osaamispankki.user.common.ReadyMadeExceptions.userNotFoundException;

@Service
public class HeadService {

    @Autowired
    protected UserCompanyRepository userCompanyRepository;

    @Autowired
    protected UserRepository userRepository;

    @Autowired
    protected CompanyRepository companyRepository;

    public User getUser() {
        User user = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        if (isNull(user)) {
            throw userNotFoundException();
        }
        return user;
    }

    public Boolean isMasterRole() {
        Set<UserCompany> user = getUser().getUserCompanies();
        return false;
    }

    public boolean userSafe( Long componentId) {
        return getUser().getId().equals(componentId);
    }

    public static FrontendValidations BEValidations() {
        return new FrontendValidations();
    }
}
