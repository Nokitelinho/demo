package com.ibsplc.neoicargo.cca.vo;

import com.ibsplc.neoicargo.framework.orchestration.vo.AbstractVO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class CcaDetailVO extends AbstractVO {

    private static final long serialVersionUID = 1L;

    private Long serialNumber;

    private Long awbSequenceNumber;

    private String shipmentPrefix;

    private String masterDocumentNumber;

    private String qualityAuditVerified;

    private String bookmarkedFlag;

    private Integer awbVersionNumber;

    private Object dataPropertyObject;

    private LocalDate firstFlightDate;

    private String firstFlightNumber;

    private double awbFinalRevenue;

    private double netRevenue;

    private double totalPayableFreight;

    private double totalReceivableFreight;

    private double totalPayableOthers;

    private double totalReceivableOthers;

    private double totalPayableISC;

    private double totalReceivableISC;

    private double totalRetentionFreight;

    private double totalRetentionISC;

    private double totalTruckCost;

    private double totalNonAWBCharge;

    private String latestAwbRemarks;

    private Object displayTaxDetails;

}
