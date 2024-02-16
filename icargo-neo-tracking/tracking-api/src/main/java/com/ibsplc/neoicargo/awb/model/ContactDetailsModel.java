package com.ibsplc.neoicargo.awb.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ContactDetailsModel {

    private String name;
    private String code;
    private String city;
    private String country;
    private String state;
    @JsonProperty("zip_code")
    private String zipCode;
    private String address;

}
