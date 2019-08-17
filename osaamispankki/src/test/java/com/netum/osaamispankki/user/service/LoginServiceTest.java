package com.netum.osaamispankki.user.service;

import com.netum.osaamispankki.user.repository.CompanyConformationRepository;
import com.netum.osaamispankki.user.repository.UserRepository;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class LoginServiceTest {

    @InjectMocks
    private LoginService loginService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private CompanyService companyService;

    @Mock
    private CompanyConformationRepository companyConformationRepository;

}
