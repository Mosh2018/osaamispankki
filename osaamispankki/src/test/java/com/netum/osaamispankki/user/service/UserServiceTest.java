package com.netum.osaamispankki.user.service;

import com.netum.osaamispankki.user.common.GenericHelper;
import com.netum.osaamispankki.user.domain.Company;
import com.netum.osaamispankki.user.domain.CompanyConformation;
import com.netum.osaamispankki.user.domain.Role;
import com.netum.osaamispankki.user.domain.User;
import com.netum.osaamispankki.user.modals.Roles;
import com.netum.osaamispankki.user.repository.CompanyConformationRepository;
import com.netum.osaamispankki.user.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Set;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private CompanyService companyService;

    @Mock
    private CompanyConformationRepository companyConformationRepository;

    private Long userId = 1L;
    private String username = "Baghdad12";
    private String companyName = "Baghdad University";
    private Long companyId = 1996L;
    private GenericHelper helper = new GenericHelper();

    @Test
    public void addNewUserWithoutCompanyName() {
        when(userRepository.findByUsername(anyString()))
                .thenReturn(getUser());
        when(companyService.getCompany(anyString()))
                .thenReturn(getCompany());
        when(companyConformationRepository.findByCompanyId(anyLong()))
                .thenReturn(getCompanyConformations().stream().findFirst().get());
        User user = userService.addNewUser(getNewUserNoCompanyName());

        assertEquals(Roles.GUEST, user.getRoles().stream().findFirst().get().getRole());
        assertTrue(user.getCompanyConformations().isEmpty());
    }

    @Test
    public void addNewUserWithCompanyHasNotBeenRegistered() {
        when(userRepository.findByUsername(anyString()))
                .thenReturn(getUser());
        when(companyService.getCompany(anyString()))
                .thenReturn(null);
        when(companyConformationRepository.findByCompanyId(anyLong()))
                .thenReturn(getCompanyConformations().stream().findFirst().get());
        User user = userService.addNewUser(getNewUserWithCompanyName());

        assertEquals(Roles.GUEST, user.getRoles().stream().findFirst().get().getRole());
        assertTrue(user.getCompanyConformations().isEmpty());
    }

    @Test
    public void addNewUserWithCompanyRegisteredCompany() {
        when(userRepository.findByUsername(anyString()))
                .thenReturn(getUser());
        when(companyService.getCompany(anyString()))
                .thenReturn(getCompany());
        when(companyConformationRepository.findByCompanyId(anyLong()))
                .thenReturn(getCompanyConformations().stream().findFirst().get());
        User user = userService.addNewUser(getNewUserWithCompanyName());

        assertEquals(Roles.COMPANY_WORKER, user.getRoles().stream().findFirst().get().getRole());
        assertEquals(1, user.getCompanyConformations().size());
        assertEquals(companyId, user.getCompanyConformations().stream().findFirst().get().getCompanyId());
        assertEquals(username,
                user.getCompanyConformations()
                        .stream().findFirst().get()
                        .getCompanyUsers().stream().findFirst().get().getUsername());
    }

    @Test
    public void addNewNotRegisteredCompanyToUser() {
        // user has one Guest role -> company id = null;
        // if added new company that not registered so no need to add another GUEST role
        User user = getUser();
        when(userRepository.findByUsername(anyString()))
                .thenReturn(getUser());

        assertEquals(1, user.getRoles().size());
        assertTrue(hasOneRoleForThisCompany(user, Roles.GUEST));
        when(userRepository.existsByUsername(anyString())).thenReturn(true);
        User updatedUser = userService.addCompanyToUser(username, companyName);

        assertEquals(1, updatedUser.getRoles().size());
        assertTrue(hasOneRoleForThisCompany(updatedUser, Roles.GUEST));
    }

    private boolean hasOneRoleForThisCompany(User user, Roles role) {
        int wantedRole = 0;
        for (Role xRole: user.getRoles()){
            if(xRole.getRole() == role) {
                wantedRole = wantedRole +1;
            }
        }
        return wantedRole > 0;
    }


    private Company getCompany() {
        Company company = new Company(
                companyId,
                "20001212",
                companyName, "Teaching", "2000", "any thing");

        return company;
    }


    private User getUser() {
        User user = new User();
        user.setId(userId);
        user.setUsername(username);
        user.setPassword("12345678");
        user.setPassword2("12345678");
        user.setRoles(getRolesGuest());
        user.setCompanyConformations(null);

        return user;
    }


    private User getNewUserNoCompanyName() {
        User user = new User();
        user.setUsername(username);
        user.setPassword("12345678");
        user.setPassword2("12345678");
        return user;
    }

    private User getNewUserWithCompanyName() {
        User user = new User();
        user.setUsername(username);
        user.setPassword("12345678");
        user.setPassword2("12345678");
        user.setCompanyName("test");
        return user;
    }

    private Set<CompanyConformation> getCompanyConformations() {
        CompanyConformation companyConformation = new CompanyConformation();
        companyConformation.setId(90L);
        companyConformation.setCompanyId(companyId);
        return helper.tSet(companyConformation);
    }

    private Set<Role> getRolesCompanyWorker() {
        Role role1 = new Role(1, Roles.COMPANY_WORKER, false, companyId, false);
        Set<Role> roleSet = helper.tSet(role1);

        return roleSet;
    }

    private Set<Role> getRolesGuest() {
        Role role1 = new Role(1, Roles.GUEST, false, null, false);
        Set<Role> roleSet = helper.tSet(role1);

        return roleSet;
    }
}
