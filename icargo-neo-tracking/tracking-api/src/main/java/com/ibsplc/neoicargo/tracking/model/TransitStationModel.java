package com.ibsplc.neoicargo.tracking.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class TransitStationModel {

    @JsonProperty("number_of_flights")
    private Integer numberOfFlights;

    @JsonProperty("stops")
    private List<String> stops;
}
