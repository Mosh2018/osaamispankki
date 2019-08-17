package com.netum.osaamispankki.user.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.MappedSuperclass;
import java.io.Serializable;

@MappedSuperclass
@Getter
@Setter
public class UserId implements Serializable {
    private Long userId;

    private Boolean hide = false;
}
