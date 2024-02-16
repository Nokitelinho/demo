package com.ibsplc.neoicargo.cca.vo;

import com.ibsplc.neoicargo.framework.orchestration.vo.AbstractVO;
import com.ibsplc.neoicargo.framework.util.currency.Money;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CcaRateDetailsVO extends AbstractVO {

    private static final long serialVersionUID = -6690934299300857641L;

    private Long serialNumber;

    private Object rateDetails;

    private double rate;

    private Money charge;

    private String rateType;

    private String commodityCode;

    private String companyCode;

    private double chargeableWeight;

    private double displayChargeableWeight;

    private String allInAttribute;

    private boolean minimumChargeAppliedFlag;

    private String discrepancyFlag;

    private String ratedSccCode;

}
