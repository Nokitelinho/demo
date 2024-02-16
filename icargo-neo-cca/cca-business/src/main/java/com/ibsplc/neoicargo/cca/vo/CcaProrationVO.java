package com.ibsplc.neoicargo.cca.vo;

import com.ibsplc.neoicargo.common.modal.BaseModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Collection;

@Getter
@Setter
@NoArgsConstructor
public class CcaProrationVO extends BaseModel {

    private static final long serialVersionUID = 1L;

    private String shipmentPrefix;

    private String masterDocumentNumber;

    private String origin;

    private String destination;

    private Collection<CcaProrationDetailVO> freightChgPrimaryDetails;

    private Collection<CcaProrationDetailVO> freightChgSecondaryDetails;

    private Collection<CcaProrationDetailVO> otherChgPrimaryDetails;

    private Collection<CcaProrationDetailVO> otherChgSecondaryDetails;

    private double exchangerateInUSD;

    private String exchangeDate;

}
