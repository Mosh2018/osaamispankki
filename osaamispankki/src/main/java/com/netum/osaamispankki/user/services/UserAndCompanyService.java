package com.netum.osaamispankki.user.services;

import com.netum.osaamispankki.user.common.GenericHelper;
import com.netum.osaamispankki.user.domain.User;
import com.netum.osaamispankki.user.domain.UserCompany;
import com.netum.osaamispankki.user.exceptions.OsaamispankkiException;
import com.netum.osaamispankki.user.modals.EmploymentType;
import com.netum.osaamispankki.user.repository.UserCompanyRepository;
import com.netum.osaamispankki.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static com.netum.osaamispankki.user.common.GenericHelper.isNull;
import static com.netum.osaamispankki.user.common.GenericHelper.notNull;
import static com.netum.osaamispankki.user.common.UtilsMethods.setExceptionMessage;

@Service
public class UserAndCompanyService extends HeadService {

    @Autowired
    private UserCompanyRepository userCompanyRepository;

    @Autowired
    private UserRepository userRepository;

    public Set<UserCompany> getUserCompanies() {
        try {
            Set<UserCompany> companies = userCompanyRepository.getAllByUser(getUser());
            if (companies.isEmpty()) {
                return null;
            }
            return companies;
        } catch (Exception e) {
            throw new OsaamispankkiException(setExceptionMessage("companies", "something wrong happened"));
        }
    }

    public UserCompany addOrSaveCompany(UserCompany userCompany) {

        Set<UserCompany> userCompanies = getUserCompanies();
        // check if Companies not same
        // check if companies has one Full time
        if (GenericHelper.isNull(userCompanies)) {
            userCompanies = new HashSet<>();
        }

        if (userCompany.getEmploymentType().equals(EmploymentType.FULL_TIME) &&
                userCompanies.stream().anyMatch(x -> x.getEmploymentType().equals(EmploymentType.FULL_TIME))) {
            throw new OsaamispankkiException(setExceptionMessage("company", "Can't add more than one full time contract"));
        }


        if (notNull(userCompany.getId())) {
            Map<Long, String> companiesMap = userCompanies.stream()
                    .collect(Collectors.toMap(UserCompany::getId, UserCompany::getCompany));

            if (!companiesMap.get(userCompany.getId()).equals(userCompany.getCompany())) {
                if (userCompanies.stream().anyMatch(x -> x.getCompany().equals(userCompany.getCompany()))) {
                    throw new OsaamispankkiException(setExceptionMessage("company", "Company is exist"));
                }
            }

        } else {
            if (userCompanies.stream().anyMatch(x -> x.getCompany().equals(userCompany.getCompany()))) {
                throw new OsaamispankkiException(setExceptionMessage("company", "Company is exist"));
            }
        }

        if (userCompanies.size() >= BEValidations().getMAX_COMPANIES() && isNull(userCompany.getId())) {
            throw new OsaamispankkiException(setExceptionMessage("company", "Can't add company they are already 3 companies"));
        } else {
            User user = getUser();
            userCompany.setUser(user);
            UserCompany savedUserCompany = userCompanyRepository.save(userCompany);
            userCompanies.add(savedUserCompany);
            user.setUserCompanies(userCompanies);
            userRepository.save(user);
            return savedUserCompany;
        }

    }

    public boolean deleteCompany(Long id) {
        UserCompany company = this.userCompanyRepository.findById(id).get();
        if (notNull(company) && userSafe(company.getUser().getId())) {
            try {
                userCompanyRepository.deleteById(company.getId());
                return this.userCompanyRepository.existsById(id) == false;
            } catch (Exception e) {
                return false;
            }

        } else {
            throw new OsaamispankkiException(setExceptionMessage("company", "company not found"));
        }
    }
}
