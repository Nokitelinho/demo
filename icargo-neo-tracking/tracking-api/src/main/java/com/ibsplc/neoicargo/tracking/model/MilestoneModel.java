package com.ibsplc.neoicargo.tracking.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MilestoneModel {

    @JsonProperty("milestone")
    private String milestone;

    @JsonProperty("status")
    private String status;
}
