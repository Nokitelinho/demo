package com.ibsplc.neoicargo.cca.modal;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
public class CcaListViewModal implements Serializable {

    private static final long serialVersionUID = -1025220834528417491L;

    @JsonProperty("cca_master_serial_number")
    private Long ccaSerialNumber;

    @JsonProperty("cca_number")
    private String ccaReferenceNumber;

    @JsonProperty("shipment_prefix")
    private String shipmentPrefix;

    @JsonProperty("master_document_number")
    private String masterDocumentNumber;

    @JsonProperty("cca_status")
    private String ccaStatus;

    @JsonProperty("cca_type")
    private String ccaType;

    @JsonProperty("total_non_awb_charge")
    private double totalNonAWBCharge;

    private String currency;

    @JsonProperty("cca_issue_date")
    private String ccaIssueDate;

    @JsonProperty("cca_issue_datetime_utc")
    private String ccaIssueDateTimeInUTC;

    @JsonProperty("cca_source")
    private String ccaSource;

    @JsonProperty("agent_code")
    private String agentCode;

    @JsonProperty("inbound_customer_code")
    private String inboundCustomerCode;

    @JsonProperty("outbound_customer_code")
    private String outboundCustomerCode;

    @JsonProperty("agent_details")
    private CcaCustomerDetailData agentDetails;

    @JsonProperty("inbound_customer_details")
    private CcaCustomerDetailData inboundCustomerDetails;

    @JsonProperty("outbound_customer_details")
    private CcaCustomerDetailData outboundCustomerDetails;

    @JsonProperty("export_billing_status")
    private String exportBillingStatus;

    @JsonProperty("import_billing_status")
    private String importBillingStatus;

    @JsonProperty("net_value_export")
    private Double netValueExport;

    @JsonProperty("net_value_import")
    private Double netValueImport;

    @JsonProperty("cca_reason")
    private List<ReasonCodeModel> ccaReason;

    @JsonProperty("cca_assignee")
    private String ccaAssignee;

    @JsonProperty("cca_attachments")
    private Object ccaAttachments;

}
