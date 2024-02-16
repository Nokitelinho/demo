package com.ibsplc.neoicargo.cca.modal;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class CcaCustomerDetailData implements Serializable {

    private static final long serialVersionUID = 1L;

    @JsonProperty("serial_number")
    private Long serialNumber;

    @JsonProperty("customer_name")
    private String customerName;

    @JsonProperty("station_code")
    private String stationCode;

    @JsonProperty("city_code")
    private String cityCode;

    @JsonProperty("country_code")
    private String countryCode;

    @JsonProperty("account_number")
    private String accountNumber;

    @JsonProperty("iata_code")
    private String iataCode;

    @JsonProperty("cass_indicator")
    private String cassIndicator;

}
