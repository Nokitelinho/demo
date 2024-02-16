package com.ibsplc.neoicargo.cca.util;

import com.ibsplc.neoicargo.framework.tests.security.utils.MockQuantity;
import com.ibsplc.neoicargo.framework.util.unit.Quantity;
import com.ibsplc.neoicargo.framework.util.unit.quantity.Quantities;
import com.ibsplc.neoicargo.framework.util.unit.quantity.types.Weight;
import org.junit.jupiter.api.Test;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

import static com.ibsplc.neoicargo.cca.MockModelsGeneratorUtils.LOCATION_CODE;
import static org.junit.jupiter.api.Assertions.assertEquals;

class QuantityUtilTest {

    private static final Quantities QUANTITIES = MockQuantity.performInitialisation("K", null, LOCATION_CODE, "USD");

    @Test
    void getValueShouldReturnValue() {
        // Given
        final var value = 100.0;
        final var weightQuantity = getQuantity(value);
        final var expected = weightQuantity.getValue().doubleValue();

        // When
        final var actual = QuantityUtil.getValue(weightQuantity);

        // Then
        assertEquals(expected, actual);
    }

    @Test
    void getValueShouldReturnZeroWhenNull() {
        // When
        final var actual = QuantityUtil.getValue(null);

        // Then
        assertEquals(0.0, actual);
    }

    @Test
    void getDisplayValue() {
        // Given
        final var value = 1000.0;
        final var weightQuantity = getQuantity(value);
        final var expected = weightQuantity.getDisplayValue().doubleValue();

        // When
        final var actual = QuantityUtil.getDisplayValue(weightQuantity);

        // Then
        assertEquals(expected, actual);
    }

    @Test
    void getDisplayValueShouldReturnZeroWhenNull() {
        // When
        final var actual = QuantityUtil.getDisplayValue(null);

        // Then
        assertEquals(0.0, actual);
    }

    @NotNull
    private static Quantity<Weight> getQuantity(final double systemValue) {
        return QUANTITIES.getQuantity(Quantities.WEIGHT, BigDecimal.valueOf(systemValue));
    }
}