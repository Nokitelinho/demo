package com.ibsplc.neoicargo.tracking.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.ALWAYS)
public class SplitDetailsItemModel {

    @JsonProperty("item_id")
    private String itemId;

    @JsonProperty("next_item_id")
    private String nextItemId;

    @JsonProperty("origin_airport_code")
    private String originAirportCode;

    @JsonInclude(value = JsonInclude.Include.ALWAYS, content = JsonInclude.Include.ALWAYS)
    @JsonProperty("milestone_status")
    private String milestoneStatus;

    @JsonProperty("milestone_time")
    private String milestoneTime;

    @JsonProperty("milestone_time_postfix")
    private String milestoneTimePostfix;

    @JsonProperty("pieces")
    private Integer pieces;

    @JsonProperty("carrier_code")
    private String carrierCode;

    @JsonProperty("flight_number")
    private String flightNumber;

    @JsonProperty("actual_flight_data")
    private ActualFlightDataModel actualFlightData;

    @JsonProperty("sub_splits")
    private List<SplitDetailsItemModel> subSplits;
}
