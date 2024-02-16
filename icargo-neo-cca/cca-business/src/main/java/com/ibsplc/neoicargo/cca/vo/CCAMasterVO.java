package com.ibsplc.neoicargo.cca.vo;

import com.ibsplc.neoicargo.cca.status.CcaStatus;
import com.ibsplc.neoicargo.framework.core.lang.modal.Units;
import com.ibsplc.neoicargo.framework.orchestration.vo.AbstractVO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class CCAMasterVO extends AbstractVO {

    private static final long serialVersionUID = 1L;

    private Long ccaSerialNumber;

    private String shipmentPrefix;

    private String masterDocumentNumber;

    private String autoCCAFlag;

    private String companyCode;

    private String ccaReferenceNumber;

    private LocalDate ccaIssueDate;
    private LocalDateTime ccaIssueDateTimeInUTC;

    private String ccaType;

    private CcaStatus ccaStatus;

    private String ccaRemarks;

    private String ccaReason;

    private String assignee;

    private Collection<CcaAwbVO> shipmentDetailVOs;

    private int documentOwnerId;

    private CcaAwbVO originalShipmentVO;

    private CcaAwbVO revisedShipmentVO;

    private LocalDateTime lastUpdatedTime;

    private Units unitOfMeasure;

    private String updateAgentFlag;

    private String ccaSource;

    private double ccaValue;

    private String exportBillingStatus;

    private String importBillingStatus;

    private String nonAwbChargesBillingStatus;

    private String autoCalculateTax;

    private LocalDate billingPeriodTo;

    private LocalDate billingPeriodFrom;

    private List<CCAWorkflowVO> ccaWorkFlows;

    private Object ccaAttachments;

    private String cassIndicator;

    private String triggerPoint;

    private LocalDate executionDate;

    private LocalDate firstFlightDate;

    private String executionStation;

    private double netValueExport;

    private double netValueImport;

    private String currencyCode;

    @Override
    public String getBusinessId() {
        return shipmentPrefix + "-" + masterDocumentNumber + "-" + ccaReferenceNumber;
    }
}
