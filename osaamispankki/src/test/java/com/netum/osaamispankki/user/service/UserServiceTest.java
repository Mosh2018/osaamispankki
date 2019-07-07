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

}
