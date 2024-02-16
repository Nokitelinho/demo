package com.ibsplc.neoicargo.cca.util;

import com.ibsplc.neoicargo.framework.util.currency.Money;

import javax.validation.constraints.NotNull;
import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Supplier;

import static com.ibsplc.neoicargo.cca.constants.CcaConstants.ACTUAL;
import static com.ibsplc.neoicargo.cca.constants.CcaConstants.CCA_TYPE_ACTUAL;
import static com.ibsplc.neoicargo.cca.constants.CcaConstants.CCA_TYPE_INTERNAL;
import static com.ibsplc.neoicargo.cca.constants.CcaConstants.INTERNAL;

public final class CcaUtil {

    private CcaUtil() {
    }

    private static final Map<String, String> CCA_TYPE_DESCRIPTION_INFO = Map.of(
            CCA_TYPE_INTERNAL, INTERNAL,
            CCA_TYPE_ACTUAL, ACTUAL
    );

    public static String getCcaTypeDescription(@NotNull String type) {
        return Optional.ofNullable(CCA_TYPE_DESCRIPTION_INFO.get(type))
                .orElse(type);
    }

    public static boolean isNullOrEmpty(Object object) {
        if (Objects.isNull(object)) {
            return true;
        }
        if (object instanceof CharSequence) {
            return ((CharSequence) object).length() == 0;
        }
        if (object.getClass().isArray()) {
            return Array.getLength(object) == 0;
        }
        if (object instanceof Collection<?>) {
            return ((Collection<?>) object).isEmpty();
        }
        if (object instanceof Map<?, ?>) {
            return ((Map<?, ?>) object).isEmpty();
        }
        return false;
    }

    public static double getRoundedMoneyAmount(Supplier<Money> getter) {
        double roundedAmt = 0;
        if (!isNullOrEmpty(getter.get())) {
            roundedAmt = getter.get().getRoundedAmount().doubleValue();
        }
        return roundedAmt;
    }

}
