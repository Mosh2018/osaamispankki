package com.netum.osaamispankki.user.service;

import com.netum.osaamispankki.user.domain.Company;
import com.netum.osaamispankki.user.domain.CompanyConformation;
import com.netum.osaamispankki.user.domain.Role;
import com.netum.osaamispankki.user.domain.User;
import com.netum.osaamispankki.user.exceptions.OsaamispankkiException;
import com.netum.osaamispankki.user.repository.CompanyConformationRepository;
import com.netum.osaamispankki.user.repository.UserRepository;
import com.netum.osaamispankki.user.validation.UserValidations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import static com.netum.osaamispankki.user.common.GenericHelper.*;
import static com.netum.osaamispankki.user.common.ReadyMadeExceptions.userNotFoundException;
import static com.netum.osaamispankki.user.common.UtilsMethods.*;
import static com.netum.osaamispankki.user.modals.Roles.COMPANY_WORKER;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CompanyService companyService;

    @Autowired
    private CompanyConformationRepository companyConformationRepository;

    @Autowired
    private BCryptPasswordEncoder cryptPasswordEncoder;

    public User save(User user) {
        return userRepository.save(user);
    }

    public User saveNewCompany(String username, String companyName) {
        return save(addCompanyToUser(username,companyName));
    }

    public boolean isExist(String username) {
        return userRepository.existsByUsername(username);
    }

    public User get(String username) {
        return userRepository.findByUsername(username);
    }
    // Ends

    public User getUser(String username) {
        if (username == null || !isExist(username)) {
            throw userNotFoundException();
        }
        return get(username);
    }

    public User createUser(User user) {
        user.setPassword(cryptPasswordEncoder.encode(user.getPassword()));
        addCompanyToNewUser(user);
        addNewRoleToNewUser(user);
        return save(user);
    }

    protected User addCompanyToUser(String username, String companyName) {
        User user = getUser(username);
        user.setCompanyName(companyName);
        addCompanyToExistUser(user);
        addRoleToExistUser(user);
        return user;
    }

    private void addRoleToExistUser(User user) {

        Company company = companyService.getCompany(user.getCompanyName());
        if (notNull(company)) {
            boolean hasRoleInThisCompany = user.getRoles()
                    .stream().anyMatch(role -> role.getCompanyId().equals(company.getId()));
            if (_false(hasRoleInThisCompany)) {
                CompanyConformation conformation = user.getCompanyConformations().stream().findFirst().get();
                Role role = new Role();
                role.setRole(COMPANY_WORKER);
                role.setCompanyId(conformation.getCompanyId());
                user.getRoles().add(role);
            }
        }
    }


    private void addCompanyToExistUser(User user) {
        Company company = companyService.getCompany(user.getCompanyName());
        if(notNull(company)) {
            boolean hasBeenAddedCompany = user.getCompanyConformations()
                    .stream()
                    .anyMatch( companyConformation -> companyConformation.getCompanyId().equals(company.getId()));
            if (!hasBeenAddedCompany) {
                CompanyConformation companyConformation = companyConformationRepository.findByCompanyId(company.getId());
                companyConformation.getCompanyUsers().add(user);
                user.getCompanyConformations().add(companyConformation);
            }
        }

    }

    private void addCompanyToNewUser(User user) {
        if (notBlank(user.getCompanyName())) {
            Company company = companyService.getCompany(user.getCompanyName());
            if (!isNull(company)) {
                CompanyConformation companyConformation = companyConformationRepository.findByCompanyId(company.getId());
                companyConformation.setCompanyUsers(tSet(user));
                user.setCompanyConformations(tSet(companyConformation));
            }
        }
    }

    private void addNewRoleToNewUser(User user) {
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
    }
}
