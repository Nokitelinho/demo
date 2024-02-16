package com.ibsplc.neoicargo.cca.modal;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Getter
@Setter
public class CcaDimension implements Serializable {

    private static final long serialVersionUID = 1L;

    @JsonProperty("dimension_serial_number")
    private Long serialNumber;

    /**
     * Length of a piece
     **/
    @NotNull
    @JsonProperty("length")
    private Double length = 0.0;

    /**
     * Width of a piece
     **/
    @NotNull
    @JsonProperty("width")
    private Double width = 0.0;

    /**
     * Height of a piece
     **/
    @NotNull
    @JsonProperty("height")
    private Double height = 0.0;

    /**
     * Number of pieces, with same length,width and height
     **/
    @NotNull
    @JsonProperty("pieces")
    private Integer pieces = 0;

    /**
     * Weight of each pieces in the group
     **/
    @JsonProperty("weight")
    private Double weight = 0.0;

    /**
     * Volume of each pieces in the group
     **/
    @JsonProperty("volume")
    private Double volume = 0.0;

}