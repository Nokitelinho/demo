package com.ibsplc.neoicargo.cca.events;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ibsplc.neoicargo.framework.core.lang.event.DomainEvent;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class CcaInvoicedEvent implements DomainEvent, Serializable {

    private static final long serialVersionUID = 1L;

    @JsonProperty("shipment_prefix")
    private String shipmentPrefix;

    @JsonProperty("master_document_number")
    private String masterDocumentNumber;

    @JsonProperty("company_code")
    private String companyCode;

    @JsonProperty("owner_id")
    private int ownerId;

    @JsonProperty("duplicate_number")
    private int duplicateNumber;

    @JsonProperty("sequence_number")
    private int sequenceNumber;

    @JsonProperty("exp_billing_status")
    private String exportBillingStatus;

    @JsonProperty("imp_billing_status")
    private String importBillingStatus;

    @JsonProperty("awb_indicator")
    private String awbIndicator;

    @JsonProperty("exp_imp_flag")
    private String exportImportFlag;

    @JsonProperty("cca_ref_number")
    private String ccaRefNumber;

    @JsonProperty("billing_status")
    private String billingStatus;

}
