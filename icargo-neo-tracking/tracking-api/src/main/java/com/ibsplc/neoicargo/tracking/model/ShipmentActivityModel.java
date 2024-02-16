package com.ibsplc.neoicargo.tracking.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ShipmentActivityModel {

    @JsonProperty("event")
    private String event;

    @JsonProperty("pieces")
    private Integer pieces;

    @JsonProperty("airport_code")
    private String airportCode;

    @JsonProperty("event_time")
    private String eventTime;

    @JsonProperty("event_time_utc")
    private String eventTimeUTC;

    @JsonProperty("reason")
    private String reason;

    @JsonProperty("flight")
    private ShipmentActivityFlightModel flightData;

    @JsonProperty("weight")
    private Double weight;

    @JsonProperty("weight_unit")
    private String weightUnit;

    @JsonProperty("from_carrier")
    private String fromCarrier;

    @JsonProperty("to_carrier")
    private String toCarrier;

}