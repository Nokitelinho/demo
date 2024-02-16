package com.ibsplc.neoicargo.tracking.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ibsplc.neoicargo.framework.core.lang.modal.Units;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ShipmentDetailsModel {

    @JsonProperty("awb_number")
    private String awbNumber;

    @JsonProperty("pieces")
    private Integer pieces;

    @JsonProperty("stated_weight")
    private Double statedWeight;

    @JsonProperty("stated_volume")
    private Double statedVolume;

    @JsonProperty("units_of_measure")
    private Units unitsOfMeasure;

    @JsonProperty("special_handling_code")
    private String specialHandlingCode;

    @JsonProperty("product_name")
    private String productName;

    @JsonProperty("shipment_description")
    private String shipmentDescription;

    @JsonProperty("origin_airport_code")
    private String originAirportCode;

    @JsonProperty("destination_airport_code")
    private String destinationAirportCode;

    @JsonProperty("milestones")
    private List<MilestoneModel> milestones;

    @JsonProperty("departure_time")
    private String departureTime;

    @JsonProperty("departure_time_postfix")
    private String departureTimePostfix;

    @JsonProperty("arrival_time")
    private String arrivalTime;

    @JsonProperty("arrival_time_postfix")
    private String arrivalTimePostfix;

    @JsonProperty("transit_stations")
    private TransitStationModel transitStations;
}
