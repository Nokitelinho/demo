package com.ibsplc.neoicargo.cca.modal;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
public class RerateCCAResponse implements Serializable {

    private static final long serialVersionUID = 1023170884432366463L;

    @JsonProperty("shipment_prefix")
    private String shipmentPrefix;

    @JsonProperty("master_document_number")
    private String masterDocumentNumber;

    @JsonProperty("cca_awb_rating_details")
    private List<CcaRateDetailData> awbRateDetails;

    @JsonProperty("cca_awb_charge_details")
    private List<CcaChargeDetailData> awbChargeDetails;

}
