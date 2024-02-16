package com.ibsplc.neoicargo.cca.modal;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class CcaRatingUldModal implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long serialNumber;

    @JsonProperty("uld_type")
    private String type;

    @JsonProperty("number_of_uld")
    private long numberOfUld;

    @JsonProperty("uld_weight")
    private double weight;

    @JsonProperty("uld_volume")
    private double volume;

    @JsonProperty("uld_contour")
    private String contour;
}
