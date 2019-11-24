package com.netum.osaamispankki.user.services;

import com.netum.osaamispankki.user.domain.UserCompany;
import org.springframework.stereotype.Service;

@Service
public class EmployeesService {

    public UserCompany confirmUserToCompany(Long usedId) {


        return new UserCompany();
    }
}
