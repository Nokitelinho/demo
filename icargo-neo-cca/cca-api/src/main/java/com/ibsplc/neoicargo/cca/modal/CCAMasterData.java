package com.ibsplc.neoicargo.cca.modal;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.ibsplc.neoicargo.cca.status.CcaStatus;
import com.ibsplc.neoicargo.common.modal.BaseModel;
import com.ibsplc.neoicargo.framework.core.lang.modal.Units;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class CCAMasterData extends BaseModel {

    private static final long serialVersionUID = 1L;

    @JsonProperty("cca_master_serial_number")
    private Long ccaSerialNumber;

    /**
     * Company Name, e.g. AV
     */
    @JsonProperty("company_code")
    private String companyCode;

    /**
     * Document Owner Id, e.g. 1134
     */
    @JsonProperty("document_owner_id")
    private Integer documentOwnerId;

    /**
     * Shipment Prefix, e.g. 134
     */
    @JsonProperty("shipment_prefix")
    private String shipmentPrefix;

    /**
     * Master Document Number, e.g. 11112200
     */
    @JsonProperty("master_document_number")
    private String masterDocumentNumber;

    /**
     * Contains the Original Shipment Details
     */
    @JsonProperty("original_awbdata")
    private CCAAWBDetailData originalAWBData;

    /**
     * Contains the Revised Shipment Details
     */
    @JsonProperty("revised_awbdata")
    private CCAAWBDetailData revisedAWBData;

    /**
     * Signifies that CCA is auto-corrected: Y or N
     */
    @JsonProperty("auto_cca_flag")
    private String autoCCAFlag;

    /**
     * CCA Reference Number, e.g. CCA00001
     */
    @JsonProperty("cca_number")
    private String ccaReferenceNumber;

    /**
     * CCA Issue Date, e.g. 18-08-2021
     */
    @JsonProperty("cca_issue_date")
    private String ccaIssueDate;

    /**
     * Denotes CCA Type. Can be (A)Actual or (I)Internal
     */
    @JsonProperty("cca_type")
    private String ccaType;

    /**
     * Denotes CCA Status. Can be R(Rejected),N(New),D(Deleted),A(Approved)
     */
    @JsonProperty("cca_status")
    private CcaStatus ccaStatus;

    /**
     * CCA Remarks, e.g. Creating CCA
     */
    @JsonProperty("cca_remarks")
    private String ccaRemarks;

    @JsonProperty("cca_reason_codes")
    private Collection<ReasonCodeModel> reasonCodes;

    /**
     * Assignee Name
     */
    @JsonProperty("assignee")
    private String assignee;

    @JsonProperty("unit_of_measure")
    private Units unitOfMeasure;

    /**
     * Indicates to update Agent: Y or N
     */
    @JsonProperty("update_agent_flag")
    private String updateAgentFlag;

    /**
     * Indicates to Auto Calculate Tax: Y or N
     */
    @JsonProperty("auto_calculate_tax")
    private String autoCalculateTax;

    @JsonProperty("operation_flag")
    private String operation;

    @JsonProperty("cca_source")
    private String ccaSource;

    @JsonProperty("cca_value")
    private double ccaValue;

    /**
     * Execution Station, e.g. CDG
     */
    @JsonProperty("execution_station")
    private String executionStation;

    /**
     * Execution Date, e.g. 01 Aug 2021
     */
    @JsonProperty("execution_date")
    private String executionDate;

    /**
     * Outbound Billing Period, e.g. 01 Aug 2021 - 30 Aug 2021
     */
    @JsonProperty("outbound_billing_period")
    private String outboundBillingPeriod;

    /**
     * Indicates Outbound Billing Status, e.g. A
     */
    @JsonProperty("outbound_billing_status")
    private String outboundBillingStatus;

    /**
     * Inbound Billing Period, e.g. 01 Aug 2021 - 30 Aug 2021
     */
    @JsonProperty("inbound_billing_period")
    private String inboundBillingPeriod;

    /**
     * Indicates Inbound Billing Status, e.g. A
     */
    @JsonProperty("inbound_billing_status")
    private String inboundBillingStatus;

    /**
     * Non Awb Charges Billing Period, e.g. 01 Aug 2021 - 30 Aug 2021
     */
    @JsonProperty("non_awb_charges_billing_period")
    private String nonAWBChargesBillingPeriod;

    /**
     * Indicates Non Awb Charges Billing Status, e.g. A
     */
    @JsonProperty("non_awb_charges_billing_status")
    private String nonAwbChargesBillingStatus;

    @JsonProperty("cca_workflows")
    private List<CCAWorkflowData> ccaWorkFlows;

    @JsonProperty("total_non_awb_charges")
    private double totalNonAWBCharge;

    @JsonProperty("billing_period_to")
    private String billingPeriodTo;

    @JsonProperty("billing_period_From")
    private String billingPeriodFrom;

    @JsonProperty("cca_attachments")
    private Object ccaAttachments;

    @JsonProperty("cca_screen_id")
    private String ccaScreenId;

    @JsonProperty("is_agent_cass")
    private Boolean isAgentCass;

    @JsonIgnore
    private String cassIndicator;

    @JsonIgnore
    private String originAwbAirportCode;

    @JsonIgnore
    private String destinationAwbAirportCode;
    /**
     * CCA Issue Date and time in utc, e.g. 18-08-2021 04:32:35.12
     */
    @JsonProperty("cca_issue_datetime_utc")
    private LocalDateTime ccaIssueDateTimeInUTC;
}
