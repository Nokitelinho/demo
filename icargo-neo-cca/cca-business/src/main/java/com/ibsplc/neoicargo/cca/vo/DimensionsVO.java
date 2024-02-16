package com.ibsplc.neoicargo.cca.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ibsplc.neoicargo.framework.core.lang.modal.Units;
import com.ibsplc.neoicargo.framework.orchestration.vo.AbstractVO;
import com.ibsplc.neoicargo.framework.util.unit.Quantity;
import com.ibsplc.neoicargo.framework.util.unit.quantity.types.Dimension;
import com.ibsplc.neoicargo.framework.util.unit.quantity.types.Volume;
import com.ibsplc.neoicargo.framework.util.unit.quantity.types.Weight;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DimensionsVO extends AbstractVO {

    private static final long serialVersionUID = 1L;

    private Long serialNumber;

    private Integer pieces;

    private Quantity<Weight> weight;

    private Quantity<Dimension> length;

    private Quantity<Dimension> width;

    private Quantity<Dimension> height;

    private Quantity<Volume> volume;

    @JsonIgnore
    private Units unitOfMeasure;

}
