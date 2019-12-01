package com.netum.osaamispankki.user.services;

import com.netum.osaamispankki.user.common.GenericHelper;
import com.netum.osaamispankki.user.domain.User;
import com.netum.osaamispankki.user.domain.UserCompany;
import com.netum.osaamispankki.user.exceptions.OsaamispankkiException;
import com.netum.osaamispankki.user.modals.EmploymentType;
import com.netum.osaamispankki.user.modals.Role;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static com.netum.osaamispankki.user.common.GenericHelper.isNull;
import static com.netum.osaamispankki.user.common.GenericHelper.notNull;
import static com.netum.osaamispankki.user.common.UtilsMethods.setExceptionMessage;
import static com.netum.osaamispankki.user.modals.Role.ROLE_GUEST;

@Service
public class UserAndCompanyService extends HeadService {

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
        if (GenericHelper.isNull(userCompanies)) {
            userCompanies = new HashSet<>();
        }

        // check that contract tye is full time for only one company
        if (userCompany.getEmploymentType().equals(EmploymentType.FULL_TIME) &&
                userCompanies.stream().anyMatch(x -> x.getEmploymentType().equals(EmploymentType.FULL_TIME))) {
            throw new OsaamispankkiException(setExceptionMessage("company", "Can't add more than one full time contract"));
        }


        if (notNull(userCompany.getId())) {
            Map<Long, String> companiesMap = userCompanies.stream()
                    .collect(Collectors.toMap(UserCompany::getId, UserCompany::getCompany_name));

            if (!companiesMap.get(userCompany.getId()).equals(userCompany.getCompany_name())) {
                if (userCompanies.stream().anyMatch(x -> x.getCompany_name().equals(userCompany.getCompany_name()))) {
                    throw new OsaamispankkiException(setExceptionMessage("company", "Company is exist"));
                }
            }

        } else {
            if (userCompanies.stream().anyMatch(x -> x.getCompany_name().equals(userCompany.getCompany_name()))) {
                throw new OsaamispankkiException(setExceptionMessage("company", "Company is exist"));
            }
        }

        if (userCompanies.size() >= BEValidations().getMAX_COMPANIES() && isNull(userCompany.getId())) {
            throw new OsaamispankkiException(setExceptionMessage("company", "Can't add company they are already 3 companies"));
        } else {
            User user = getUser();
            userCompany.setUser(user);
            // get companyBy CompanyCode
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
                if (company.getRole().equals(Role.ROLE_COMPANY_ADMIN)) {
                    throw new OsaamispankkiException(setExceptionMessage("company", "company can not delete, you are company admin"));
                }
                userCompanyRepository.deleteById(company.getId());
                return this.userCompanyRepository.existsById(id) == false;
            } catch (Exception e) {
                throw new OsaamispankkiException(setExceptionMessage("company", e.getMessage()));
            }

        } else {
            throw new OsaamispankkiException(setExceptionMessage("company", "company not found"));
        }
    }
}
