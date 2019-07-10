package com.netum.osaamispankki.security;

import com.netum.osaamispankki.user.domain.User;
import com.netum.osaamispankki.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.netum.osaamispankki.user.common.GenericHelper.isNull;
import static com.netum.osaamispankki.user.common.UtilsMethods.setExceptionMessage;

@Service
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    private UserService userService;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.getUser(username);
        if (isNull(user)) {
            throw new UsernameNotFoundException(
                    setExceptionMessage("System_error", "username not found"));
        }
        return user;
    }

    @Transactional
    public UserDetails loadUserById(Long id) {
        User user = userService.getUser(id);
        if (isNull(user)) {
            throw new UsernameNotFoundException(
                    setExceptionMessage("System_error", "username not found"));
        }
        return user;
    }
}
