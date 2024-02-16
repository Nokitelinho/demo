package com.ibsplc.neoicargo.cca.modal;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ibsplc.neoicargo.cca.status.CcaStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CCAWorkflowData {

    @JsonProperty("cca_status")
    private CcaStatus ccaStatus;

    @JsonProperty("user_name")
    private String userName;

    @JsonProperty("requested_date")
    private String requestedDate;

}
