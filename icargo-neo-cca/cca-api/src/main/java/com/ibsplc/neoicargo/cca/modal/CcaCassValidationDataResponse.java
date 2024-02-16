package com.ibsplc.neoicargo.cca.modal;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class CcaCassValidationDataResponse implements Serializable {

    private static final long serialVersionUID = -4345396832506729383L;

    @JsonProperty("is_agent_cass")
    private Boolean isAgentCass = false;

}
