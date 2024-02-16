package com.ibsplc.neoicargo.cca.modal;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
public class CcaNumbersNode implements Serializable {

    private static final long serialVersionUID = 1L;

    @JsonProperty("cca_number_id")
    private Long ccaNumberId;

    @JsonProperty("cca_number")
    private String ccaNumber;
}
