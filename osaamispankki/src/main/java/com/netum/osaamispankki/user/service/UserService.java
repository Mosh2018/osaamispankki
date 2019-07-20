package com.netum.osaamispankki.user.service;

import com.netum.osaamispankki.security.JWTProvider;
import com.netum.osaamispankki.security.JWTRsponseToFrontend;
import com.netum.osaamispankki.security.UserLoginRequest;
import com.netum.osaamispankki.user.domain.Company;
import com.netum.osaamispankki.user.domain.CompanyConformation;
import com.netum.osaamispankki.user.domain.Role;
import com.netum.osaamispankki.user.domain.User;
import com.netum.osaamispankki.user.exceptions.OsaamispankkiException;
import com.netum.osaamispankki.user.modals.PublicUser;
import com.netum.osaamispankki.user.repository.CompanyConformationRepository;
import com.netum.osaamispankki.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import static com.netum.osaamispankki.security.SecurityConstants.TOKEN_PREFIX;
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

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JWTProvider jwtProvider;

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

    public User createUser(User user) {
        user.setPassword(cryptPasswordEncoder.encode(user.getPassword()));
        addCompanyToNewUser(user);
        addNewRoleToNewUser(user);
        return save(user);
    }

    protected User addCompanyToUser(String companyName) {
        User user = getUser();
        user.setCompanyName(companyName);
        addCompanyToExistUser(user);
        addRoleToExistUser(user);
        return save(user);
    }

    private void addRoleToExistUser(User user) {

        Company company = companyService.getCompany(user.getCompanyName());
        if (notNull(company)) {
            boolean hasRoleInThisCompany = user.getRoles()
                    .stream()
                    .anyMatch(role ->
                            role.getCompanyId() == company.getId());
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
