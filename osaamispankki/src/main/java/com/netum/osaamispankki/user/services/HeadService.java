package com.netum.osaamispankki.user.services;

import com.netum.osaamispankki.user.domain.User;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import static com.netum.osaamispankki.user.common.GenericHelper.isNull;
import static com.netum.osaamispankki.user.common.ReadyMadeExceptions.userNotFoundException;

@Service
public class HeadService {

    public User getUser() {
        User user = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        if (isNull(user)) {
            throw userNotFoundException();
        }
        return user;
    }
}
