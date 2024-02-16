package com.ibsplc.neoicargo.cca.modal;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class CcaValidationData implements Serializable {

    private static final long serialVersionUID = 4360513328273650354L;

    @JsonProperty("shipment_prefix")
    private String shipmentPrefix;

    @JsonProperty("master_document_number")
    private String masterDocumentNumber;

    private String remarks;

    @JsonProperty("cca_ref_number")
    private String ccaReferenceNumber;

    @JsonProperty("status_message")
    private String statusMessage;

}
