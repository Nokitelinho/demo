package com.ibsplc.neoicargo.cca.vo;

import com.ibsplc.neoicargo.framework.core.bean.BusinessKey;
import com.ibsplc.neoicargo.framework.orchestration.vo.AbstractVO;
import com.ibsplc.neoicargo.framework.util.currency.Money;
import com.ibsplc.neoicargo.pricing.annotations.PricingFact;
import com.ibsplc.neoicargo.pricing.annotations.PricingParameters;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CcaChargeDetailsVO extends AbstractVO implements BusinessKey {

    private static final long serialVersionUID = 1L;

    private Long serialNumber;

    @PricingFact(code = PricingParameters.PAYMENT_TYPE)
    private String paymentType;

    private boolean dueCarrier;

    private boolean dueAgent;

    private String chargeHead;

    @PricingFact(code = PricingParameters.CHARGE_CODE, group = PricingParameters.CHARGE_CODE_GROUP)
    private String chargeHeadCode;

    private Money charge;

    @Override
    public String businessKey() {
        final var agentFlag = dueAgent ? "Y" : "N";
        final var carrierFlag = dueCarrier ? "Y" : "N";
        return chargeHeadCode + "-" + chargeHead + "-" + agentFlag + "-" + carrierFlag;
    }

}
