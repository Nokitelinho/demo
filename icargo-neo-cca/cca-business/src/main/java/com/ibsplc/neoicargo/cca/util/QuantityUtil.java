package com.ibsplc.neoicargo.cca.util;

import com.ibsplc.neoicargo.framework.util.unit.Quantity;

public final class QuantityUtil {

    private QuantityUtil() {
    }

    public static double getValue(Quantity<?> quantity) {
        return quantity != null ? quantity.getValue().doubleValue() : 0.0;
    }

    public static double getDisplayValue(Quantity<?> quantity) {
        return quantity != null ? quantity.getDisplayValue().doubleValue() : 0.0;
    }
}
