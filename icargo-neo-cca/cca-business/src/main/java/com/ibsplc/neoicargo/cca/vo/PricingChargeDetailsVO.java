package com.ibsplc.neoicargo.cca.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ibsplc.neoicargo.framework.orchestration.vo.AbstractVO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Collection;

@Getter
@Setter
@NoArgsConstructor
public class PricingChargeDetailsVO extends AbstractVO {

    private static final long serialVersionUID = 1L;

    @JsonProperty("basis")
    private String basis;

    @JsonProperty("basis_total_amount")
    private Double basisTotalAmount;

    @JsonProperty("payment_type")
    private String paymentType;

    @JsonProperty("charge_head_details")
    private Collection<PricingChargeHeadDetailsVO> chargeHeadDetails;

}
