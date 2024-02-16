package com.ibsplc.neoicargo.cca.modal;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.Nullable;

import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
public class CcaRoutingData implements Serializable {

    private static final long serialVersionUID = 1L;

    @Nullable
    @JsonProperty("routing_serial_number")
    private Long serialNumber;

    @JsonProperty("flight_carrier_code")
    private String flightCarrierCode;

    @JsonProperty("flight_number")
    private String flightNumber;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MMM/yyyy")
    @JsonProperty("flight_date")
    private LocalDate flightDate;

    @JsonProperty("flight_type")
    private String flightType;

    @JsonProperty("segment_origin_code")
    private String segOrgCod;

    @JsonProperty("segment_destination_code")
    private String segDstCod;

    @JsonProperty("pieces")
    private int pieces;

    @JsonProperty("chg_weight")
    private double chgWeight;

    @JsonProperty("weight")
    private double weight;

    @JsonProperty("routing_source")
    private String routingSource;

    @JsonProperty("agreement_type")
    private String agreementType;

    @JsonProperty("first_carrier_code")
    private String firstCarrierCode;

    @JsonProperty("origin")
    private String origin;

    @JsonProperty("destination")
    private String destination;

    @JsonProperty("argument_type")
    private String argumentType;

    @JsonProperty("source")
    private String source;

}
