package com.netum.osaamispankki.user.service;

import com.netum.osaamispankki.security.JWTProvider;
import com.netum.osaamispankki.security.JWTRsponseToFrontend;
import com.netum.osaamispankki.security.UserLoginRequest;
import com.netum.osaamispankki.user.domain.*;
import com.netum.osaamispankki.user.exceptions.OsaamispankkiException;
import com.netum.osaamispankki.user.modals.PublicUser;
import com.netum.osaamispankki.user.repository.UserAndCompanyRepository;
import com.netum.osaamispankki.user.repository.UserRepository;
import com.netum.osaamispankki.user.services.EmailSenderService;
import com.netum.osaamispankki.user.services.HeadService;
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
public class LoginService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CompanyService companyService;

    @Autowired
    private UserAndCompanyRepository userAndCompanyRepository;

    @Autowired
    private BCryptPasswordEncoder cryptPasswordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JWTProvider jwtProvider;

    @Autowired
    private EmailSenderService emailSenderService;

    @Autowired
    private HeadService headService;

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

    protected User addCompanyToUser(String companyName) {
        User user = headService.getUser();
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
                UserAndCompany conformation = user.getUserAndCompanies().stream().findFirst().get();
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
            boolean hasBeenAddedCompany = user.getUserAndCompanies()
                    .stream()
                    .anyMatch( companyConformation -> companyConformation.getCompanyId().equals(company.getId()));
            if (!hasBeenAddedCompany) {
                UserAndCompany userAndCompany = userAndCompanyRepository.findByCompanyId(company.getId());
                userAndCompany.getCompanyUsers().add(user);
                user.getUserAndCompanies().add(userAndCompany);
            }
        }

    }
}
