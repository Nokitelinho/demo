package com.ibsplc.neoicargo.tracking.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.ALWAYS)
public class SplitModel  implements Serializable {

    @JsonProperty("split_number")
    private Integer splitNumber;

    @JsonProperty("pieces")
    private Integer pieces;

    @JsonProperty("transit_stations")
    private TransitStationModel transitStations;

    @JsonProperty("milestone_status")
    private String milestoneStatus;

    @JsonProperty("split_details")
    private List<SplitDetailsItemModel> splitDetails;

}
