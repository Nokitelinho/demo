package com.ibsplc.neoicargo.cca.mapper;

import com.ibsplc.neoicargo.framework.tests.security.utils.MockQuantity;
import com.ibsplc.neoicargo.framework.util.unit.Quantity;
import com.ibsplc.neoicargo.framework.util.unit.quantity.Quantities;
import com.ibsplc.neoicargo.framework.util.unit.quantity.types.Weight;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mapstruct.factory.Mappers;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

import static com.ibsplc.neoicargo.cca.MockModelsGeneratorUtils.LOCATION_CODE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@RunWith(JUnitPlatform.class)
class UnitQuantityMapperTest {

    private static final String DISPLAY_UNIT = "K";
    private static final String SYSTEM_UNIT = null;
    private static final Quantities QUANTITIES = MockQuantity.performInitialisation(SYSTEM_UNIT, DISPLAY_UNIT, LOCATION_CODE, null);

    private final UnitQuantityMapper unitQuantityMapper = Mappers.getMapper(UnitQuantityMapper.class);

    @Test
    void getValue_shouldReturnValue() {
        // Given
        final var value = 100.0;
        final var weightQuantity = getQuantity(value);
        final var expected = weightQuantity.getValue().doubleValue();

        // When
        final var actual = unitQuantityMapper.getValue(weightQuantity);

        // Then
        assertEquals(expected, actual);
    }

    @Test
    void getValue_shouldReturnZero_whenNull() {
        // When
        final var actual = unitQuantityMapper.getValue(null);

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
        final var actual = unitQuantityMapper.getDisplayValue(weightQuantity);

        // Then
        assertEquals(expected, actual);
    }

    @Test
    void getDisplayValue_shouldReturnZero_whenNull() {
        // When
        final var actual = unitQuantityMapper.getDisplayValue(null);

        // Then
        assertEquals(0.0, actual);
    }

    @Test
    void getDisplayUnit_shouldReturnUnitSymbol() {
        // Given
        final var value = 100.0;
        final var weightQuantity = getQuantity(value);

        // When
        final var actual = unitQuantityMapper.getDisplayUnit(weightQuantity);

        // Then
        assertEquals(DISPLAY_UNIT, actual);
    }

    @Test
    void getDisplayUnit_shouldReturnNull_whenNull() {
        // When
        final var actual = unitQuantityMapper.getDisplayUnit(null);

        // Then
        assertNull(actual);
    }

    @NotNull
    private static Quantity<Weight> getQuantity(final double systemValue) {
        return QUANTITIES.getQuantity(Quantities.WEIGHT, BigDecimal.valueOf(systemValue));
    }
}