package com.netum.osaamispankki.user.modals;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Address {
    @NotBlank(message = "street is required")
    private String street;
    @NotBlank(message = "postcode is required")
    private String postcode;
    @NotBlank(message = "city is required")
    private String city;
    @NotBlank(message = "country is required")
    private String country;
}
