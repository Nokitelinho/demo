package com.ibsplc.neoicargo.cca.mapper;

import com.ibsplc.neoicargo.common.converters.CentralConfig;
import com.ibsplc.neoicargo.framework.core.lang.modal.Units;
import com.ibsplc.neoicargo.framework.util.unit.EUnitOfMeasure;
import com.ibsplc.neoicargo.framework.util.unit.EUnitOfQuantity;
import com.ibsplc.neoicargo.framework.util.unit.Quantity;
import com.ibsplc.neoicargo.framework.util.unit.mapper.QuantityMapper;
import com.ibsplc.neoicargo.framework.util.unit.quantity.types.Volume;
import com.ibsplc.neoicargo.framework.util.unit.quantity.types.Weight;
import org.mapstruct.Mapper;
import org.mapstruct.Named;
import org.springframework.lang.Nullable;

@Mapper(config = CentralConfig.class,
        uses = {QuantityMapper.class},
        imports = {Weight.class, Volume.class})
public interface UnitQuantityMapper {

    EUnitOfQuantity unitOfQuantity(Units units);

    EUnitOfMeasure unitOfMeasure(Units units);

    @Named("getValue")
    default double getValue(Quantity<?> quantity) {
        return quantity == null ? 0.0 : quantity.getValue().doubleValue();
    }

    @Named("getDisplayValue")
    default double getDisplayValue(Quantity<?> quantity) {
        return quantity == null ? 0.0 : quantity.getDisplayValue().doubleValue();
    }

    @Nullable
    @Named("getDisplayUnit")
    default String getDisplayUnit(Quantity<?> quantity) {
        return quantity == null ? null : quantity.getDisplayUnit().getSymbol();
    }

}