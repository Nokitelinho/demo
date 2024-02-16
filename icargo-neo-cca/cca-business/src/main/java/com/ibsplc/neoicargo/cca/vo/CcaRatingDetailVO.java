package com.ibsplc.neoicargo.cca.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ibsplc.neoicargo.framework.core.bean.BusinessKey;
import com.ibsplc.neoicargo.framework.core.lang.modal.Units;
import com.ibsplc.neoicargo.framework.orchestration.vo.AbstractVO;
import com.ibsplc.neoicargo.framework.util.unit.Quantity;
import com.ibsplc.neoicargo.framework.util.unit.quantity.types.Volume;
import com.ibsplc.neoicargo.framework.util.unit.quantity.types.Weight;
import com.ibsplc.neoicargo.pricing.annotations.PricingFact;
import com.ibsplc.neoicargo.pricing.annotations.PricingParameters;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class CcaRatingDetailVO extends AbstractVO implements BusinessKey {

    private static final long serialVersionUID = 1L;

    private Long serialNumber;

    @PricingFact(code = PricingParameters.COMMODITY, group = PricingParameters.COMMODITY_GROUP)
    private String commodityCode;

    private Quantity<Volume> volumeOfShipment;

    private Quantity<Weight> chargeableWeight;

    private Quantity<Weight> volumetricWeight;

    private Quantity<Weight> adjustedWeight;

    private Quantity<Weight> weightOfShipment;

    private int numberOfPieces;

    private String shipmentDescription;

    private Set<DimensionsVO> dimensions;

    private Set<CcaRatingUldVO> ulds;

    private String currency;

    @JsonIgnore
    private Units unitOfMeasure;

    private String sccCode;

    @Override
    public String businessKey() {
        return commodityCode;
    }

}
