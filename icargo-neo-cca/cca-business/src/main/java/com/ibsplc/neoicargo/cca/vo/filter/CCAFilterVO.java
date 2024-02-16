package com.ibsplc.neoicargo.cca.vo.filter;

import com.ibsplc.neoicargo.framework.orchestration.vo.AbstractVO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CCAFilterVO extends AbstractVO {

    private static final long serialVersionUID = 1L;

    public CCAFilterVO(String shipmentPrefix, String masterDocumentNumber, String ccaRefNumber) {
        this.shipmentPrefix = shipmentPrefix;
        this.masterDocumentNumber = masterDocumentNumber;
        this.ccaRefNumber = ccaRefNumber;
    }

    private String shipmentPrefix;

    private String masterDocumentNumber;

    private String ccaRefNumber;

    private String exportBillingStatus;

    private String importBillingStatus;

    private String awbIndicator;

    private String billingStatus;

}
