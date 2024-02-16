package com.ibsplc.neoicargo.cca.modal;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class CcaTaxDetailsData implements Serializable {

    private static final long serialVersionUID = -7814031422248997176L;

    @JsonProperty("serial_number")
    private Long serialNumber;

    @JsonProperty("configuration_type")
    private String configurationType;

    @JsonProperty("tax_details")
    private Object taxDetails;

}
