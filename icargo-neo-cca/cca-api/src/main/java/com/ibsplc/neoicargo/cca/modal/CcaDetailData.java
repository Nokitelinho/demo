package com.ibsplc.neoicargo.cca.modal;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
public class CcaDetailData implements Serializable {

    private static final long serialVersionUID = 1L;

    @JsonProperty("cca_awb_detail_serial_number")
    private Long serialNumber;

    @JsonProperty("number_of_pieces")
    private long numberOfPieces;

    @JsonProperty("commodity_code")
    private String commodityCode;

    @JsonProperty("weight_of_shipment")
    private double weightOfShipment;

    @JsonProperty("volume_of_shipment")
    private double volumeOfShipment;

    @JsonProperty("chargeable_weight")
    private double chargeableWeight;

    @JsonProperty("currency")
    private String currency;

    @JsonProperty("shipment_description")
    private String shipmentDescription;

    @JsonProperty("display_weight_unit")
    private String displayWeightUnit;

    @JsonProperty("display_volume_unit")
    private String displayVolumeUnit;

    @JsonProperty("volumetric_weight")
    private double volumetricWeight;

    @JsonProperty("adjusted_weight")
    private double adjustedWeight;

    @JsonProperty("key")
    private String key;

    @JsonProperty("dimensions")
    private List<CcaDimension> dimensions;

    @JsonProperty("ulds")
    private List<CcaRatingUldModal> ulds;
}
