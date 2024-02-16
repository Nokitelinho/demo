package com.ibsplc.neoicargo.cca.modal;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReasonCodeModel implements Serializable {

    private static final long serialVersionUID = -8715732056699877284L;

    /**
     * CCA Reason Code identifier, e.g. 10
     */
    @JsonProperty("parameter_code")
    private String parameterCode;

}