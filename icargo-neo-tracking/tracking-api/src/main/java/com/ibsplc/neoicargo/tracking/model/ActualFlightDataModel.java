package com.ibsplc.neoicargo.tracking.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ActualFlightDataModel {
    @JsonProperty("carrier_code")
    private String carrierCode;

    @JsonProperty("flight_number")
    private String flightNumber;

    @JsonProperty("milestone_time")
    private String milestoneTime;
}
