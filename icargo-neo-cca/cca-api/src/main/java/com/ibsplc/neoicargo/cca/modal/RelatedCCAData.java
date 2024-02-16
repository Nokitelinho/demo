package com.ibsplc.neoicargo.cca.modal;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ibsplc.neoicargo.cca.status.CcaStatus;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Setter
@Getter
public class RelatedCCAData implements Serializable {

    private static final long serialVersionUID = -8264416072297952045L;

    @JsonProperty("shipment_prefix")
    private String shipmentPrefix;

    @JsonProperty("master_document_number")
    private String masterDocumentNumber;

    @JsonProperty("cca_number")
    private String ccaReferenceNumber;

    @JsonProperty("cca_status")
    private CcaStatus ccaStatus;

    @JsonProperty("cca_issue_date")
    private LocalDate ccaIssueDate;

    @JsonProperty("cca_issue_datetime")
    private LocalDateTime ccaIssueDateTimeInUTC;

    @JsonProperty("cca_type")
    private String ccaType;

    @JsonProperty("cca_source")
    private String ccaSource;

    @JsonProperty("net_export")
    private Double netExport;

    @JsonProperty("net_import")
    private Double netImport;

    @JsonProperty("export_billing_status")
    private String exportBillingStatus;

    @JsonProperty("import_billing_status")
    private String importBillingStatus;

    @JsonProperty("net_value_export")
    private double netValueExport;

    @JsonProperty("net_value_import")
    private double netValueImport;

    @JsonProperty("currency_code")
    private String currencyCode;

}
