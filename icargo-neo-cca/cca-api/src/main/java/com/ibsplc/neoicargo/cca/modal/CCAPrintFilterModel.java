package com.ibsplc.neoicargo.cca.modal;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CCAPrintFilterModel {

    @JsonProperty("cca_number")
    private String ccaReferenceNumber;

    @JsonProperty("company_code")
    private String companyCode;

    @JsonProperty("report_name")
    private String reportName;

    @JsonProperty("shipment_prefix")
    private String shipmentPrefix;

    @JsonProperty("master_document_number")
    private String masterDocumentNumber;

}