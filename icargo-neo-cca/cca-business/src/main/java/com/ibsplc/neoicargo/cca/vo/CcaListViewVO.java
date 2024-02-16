package com.ibsplc.neoicargo.cca.vo;

import com.ibsplc.neoicargo.cca.modal.CcaCustomerDetailData;
import com.ibsplc.neoicargo.framework.util.currency.Money;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
public class CcaListViewVO {

    private double totalNonAWBCharge;

    private String currency;

    private String agentCode;

    private String inboundCustomerCode;

    private String outboundCustomerCode;

    private Long ccaSerialNumber;

    private String ccaReferenceNumber;

    private String shipmentPrefix;

    private String masterDocumentNumber;

    private String ccaStatus;

    private String ccaType;

    private LocalDate ccaIssueDate;
    private LocalDateTime ccaIssueDateTimeInUTC;

    private String ccaSource;

    private String exportBillingStatus;

    private String importBillingStatus;

    private Money netValueExport;

    private Money netValueImport;

    private String ccaReason;

    private String ccaAssignee;

    private Object ccaAttachments;

    private Set<CcaCustomerDetailData> agentDetails;

    private Set<CcaCustomerDetailData> inboundCustomerDetails;

    private Set<CcaCustomerDetailData> outboundCustomerDetails;

}


