package com.ibsplc.neoicargo.cca.modal;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AvailableReasonCodeData implements Serializable {

    private static final long serialVersionUID = 5838391599852284089L;

    @JsonProperty("parameter_code")
    private String parameterCode;

    @JsonProperty("parameter_description")
    private String parameterDescription;

}
