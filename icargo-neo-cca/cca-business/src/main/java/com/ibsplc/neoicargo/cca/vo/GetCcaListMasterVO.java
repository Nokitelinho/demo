package com.ibsplc.neoicargo.cca.vo;

import com.ibsplc.neoicargo.cca.status.CcaStatus;
import com.ibsplc.neoicargo.framework.orchestration.vo.AbstractVO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class GetCcaListMasterVO extends AbstractVO {

    private static final long serialVersionUID = 1L;

    private Long ccaSerialNumber;

    private String ccaReferenceNumber;

    private String shipmentPrefix;

    private String masterDocumentNumber;

    private LocalDate ccaIssueDate;

    private String ccaSource;

    private CcaStatus ccaStatus;

    private double ccaValue;

    private String ccaType;

    private String autoCCAFlag;

    private String cassIndicator;

    private List<CcaAwbVO> shipmentDetailVOs;

    private String triggerPoint;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        var that = (GetCcaListMasterVO) o;
        return that.ccaSerialNumber != null && that.ccaSerialNumber.equals(ccaSerialNumber);
    }

    @Override
    public int hashCode() {
        // should be fixed since ccaSerialNumber can be null
        // and there`s no other unique fields
        return 31;
    }

    @Override
    public String getBusinessId() {
        return shipmentPrefix + "-" + masterDocumentNumber + "-" + ccaReferenceNumber;
    }
}
