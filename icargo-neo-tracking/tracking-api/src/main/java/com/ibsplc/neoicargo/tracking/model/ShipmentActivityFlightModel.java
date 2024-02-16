package com.ibsplc.neoicargo.tracking.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ShipmentActivityFlightModel {

    @JsonProperty("flight_carrier_code")
    private String flightCarrierCode;

    @JsonProperty("flight_number")
    private String flightNumber;

    @JsonProperty("origin")
    private String originAirportCode;

    @JsonProperty("destination")
    private String destinationAirportCode;

}