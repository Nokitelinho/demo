package com.ibsplc.neoicargo.cca.modal;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class CcaAssigneeData implements Serializable {

    private static final long serialVersionUID = 1L;

    @JsonProperty("shipment_prefix")
    private String shipmentPrefix;

    @JsonProperty("master_document_number")
    private String masterDocumentNumber;

    @JsonProperty("cca_number")
    private String ccaReferenceNumber;

    @JsonProperty("assignee")
    private String assignee;

}