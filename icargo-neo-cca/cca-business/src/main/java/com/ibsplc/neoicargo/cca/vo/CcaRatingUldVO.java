package com.ibsplc.neoicargo.cca.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ibsplc.neoicargo.framework.core.lang.modal.Units;
import com.ibsplc.neoicargo.framework.util.unit.Quantity;
import com.ibsplc.neoicargo.framework.util.unit.quantity.types.Volume;
import com.ibsplc.neoicargo.framework.util.unit.quantity.types.Weight;
import com.ibsplc.neoicargo.pricing.annotations.PricingFact;
import com.ibsplc.neoicargo.pricing.annotations.PricingParameters;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class CcaRatingUldVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long serialNumber;

    @PricingFact(code = PricingParameters.ULD_TYPE, group = PricingParameters.ULD_GROUP)
    private String type;

    private long numberOfUld;

    private Quantity<Weight> weight;

    private Quantity<Volume> volume;

    private String contour;

    @JsonIgnore
    private Units unitOfMeasure;
}
