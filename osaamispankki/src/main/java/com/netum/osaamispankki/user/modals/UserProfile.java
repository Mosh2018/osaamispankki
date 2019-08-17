package com.netum.osaamispankki.user.modals;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.netum.osaamispankki.user.common.GenericHelper;
import com.netum.osaamispankki.user.domain.HomeAddress;
import com.netum.osaamispankki.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserProfile {
    @NotBlank(message = "firstName is required")
    private String firstName;
    @NotBlank(message = "surname is required")
    private String surname;
    @Temporal(TemporalType.DATE)
    @JsonFormat(pattern = "dd.MM.yyyy")
    private Date birthday;
    @NotBlank(message = "phoneNo is required")
    private String phoneNo;

    private Address address;

    public UserProfile(User user) {
        this.firstName = user.getFirstName();
        this.surname = user.getSurname();
        if (GenericHelper.notNull(user.getBirthday())) {
            this.birthday = new Date(user.getBirthday().getTime());
        }
        this.phoneNo = user.getPhoneNo();
        HomeAddress homeAddress = user.getHomeAddress();
        if (GenericHelper.notNull(homeAddress))   {
            this.address = new Address(
                    homeAddress.getStreet(),
                    homeAddress.getPostcode(),
                    homeAddress.getCity(),
                    homeAddress.getCountry());
        }
    }
}
