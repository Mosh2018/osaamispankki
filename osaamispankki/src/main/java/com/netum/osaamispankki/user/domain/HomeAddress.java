package com.netum.osaamispankki.user.domain;

import com.netum.osaamispankki.user.modals.Address;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class HomeAddress {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "street is required")
    private String street;
    @NotBlank(message = "postcode is required")
    private String postcode;
    @NotBlank(message = "city is required")
    private String city;
    @NotBlank(message = "country is required")
    private String country;

    @OneToOne(mappedBy = "homeAddress")
    private User user;

    public HomeAddress(Address address, User user) {
        this.street = address.getStreet();
        this.postcode = address.getPostcode();
        this.city = address.getCity();
        this.country = address.getCountry();
        this.user = user;
    }
}
