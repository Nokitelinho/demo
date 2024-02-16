package com.ibsplc.neoicargo.cca.modal;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class BulkActionEdge  implements Serializable {

    private static final long serialVersionUID = -4198966895200365665L;

    @JsonProperty("shipment_prefix")
    private String shipmentPrefix;

    @JsonProperty("master_document_number")
    private String masterDocumentNumber;

    @JsonProperty("cca_number")
    private String ccaReferenceNumber;

    @JsonProperty("cca_status")
    private String ccaStatus;

    @JsonProperty("status_message")
    private String statusMessage;

}
