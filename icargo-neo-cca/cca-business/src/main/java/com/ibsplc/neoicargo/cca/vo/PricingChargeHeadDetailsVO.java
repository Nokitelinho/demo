package com.ibsplc.neoicargo.cca.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ibsplc.neoicargo.framework.orchestration.vo.AbstractVO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PricingChargeHeadDetailsVO extends AbstractVO {

    private static final long serialVersionUID = 1L;

    @JsonProperty("charge_head_code")
    private String chargeHeadCode;

    @JsonProperty("charge_head_amount")
    private Double chargeHeadAmount;
}
