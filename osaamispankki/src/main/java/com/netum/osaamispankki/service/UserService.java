package com.netum.osaamispankki.service;

import com.netum.osaamispankki.domain.Role;
import com.netum.osaamispankki.domain.User;
import com.netum.osaamispankki.exceptions.PasswordsDoesNotMatch;
import com.netum.osaamispankki.exceptions.UserFoundCanNotAddAsNewUser;
import com.netum.osaamispankki.modals.Roles;
import com.netum.osaamispankki.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

import static com.netum.osaamispankki.common.UtilsMethods.setExceptionMessage;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;


    public User addNewUser(User newUser) {
        // add password strength validations
        if (newUser.getId() == null) {
            if (userRepository.existsByUsername(newUser.getUsername())) {
                throw new UserFoundCanNotAddAsNewUser(setExceptionMessage("username", "username has been used"));
            }
            if (newUser.getPassword().equals(newUser.getPassword2()) == false) {
                throw new PasswordsDoesNotMatch(setExceptionMessage("password2", "passwords does not match"));
            }
            addRoleLevel3(newUser);
        }
        return userRepository.save(newUser);
    }

    public User getUser(String username) {
        return userRepository.findByUsername(username);
    }

    public User updateUser(User user) {
        return userRepository.save(user);
    }

    private void addRoleLevel3(User newUser) {
        Role role  = new Role();
        role.setConfirmed(false);
        role.setRole(Roles.LEVEL_3);
        Set<Role> roles = new HashSet<>();
        roles.add(role);
        newUser.setRoles(roles);
    }


}
