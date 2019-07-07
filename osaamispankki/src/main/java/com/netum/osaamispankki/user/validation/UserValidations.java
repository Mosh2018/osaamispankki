package com.netum.osaamispankki.user.validation;

import com.netum.osaamispankki.user.domain.User;
import com.netum.osaamispankki.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import static com.netum.osaamispankki.user.common.UtilsMethods._false;
import static com.netum.osaamispankki.user.common.UtilsMethods.calculatePasswordStrength;

@Component
public class UserValidations implements Validator {

    @Autowired
    private UserRepository userRepository;
    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {

        User user = (User) o;
        if (userRepository.existsByUsername(user.getUsername())) {
            errors.rejectValue("username", "Exist", "Username is exist choose another username");
        }
        if(calculatePasswordStrength(user.getPassword()) < 8) {
            errors.rejectValue("password", "Weak password",
                    "Password must be 8 characters length, at least one number and special mark");
        }

        if(_false(user.getPassword().equals(user.getPassword2()))) {
            errors.rejectValue("password2", "Match",
                    "Password must match");
        }
    }
}
