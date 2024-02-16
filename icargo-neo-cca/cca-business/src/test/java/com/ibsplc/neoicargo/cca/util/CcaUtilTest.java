package com.ibsplc.neoicargo.cca.util;

import com.ibsplc.neoicargo.framework.util.currency.Money;
import com.ibsplc.neoicargo.framework.util.currency.vo.MoneyVO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;

import static com.ibsplc.neoicargo.cca.util.CcaUtil.getCcaTypeDescription;
import static com.ibsplc.neoicargo.cca.util.CcaUtil.getRoundedMoneyAmount;
import static com.ibsplc.neoicargo.cca.util.CcaUtil.isNullOrEmpty;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@RunWith(JUnitPlatform.class)
class CcaUtilTest {

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldReturnTrueIsNullOrEmptyForNullObject() {
        // Then
        assertTrue(() -> isNullOrEmpty(null));
    }

    @Test
    void shouldReturnTrueIsNullOrEmptyForEmptyCharSequence() {
        // When
        final var testString = "";

        // Then
        assertTrue(() -> isNullOrEmpty(testString));
    }

    @Test
    void shouldReturnTrueIsNullOrEmptyForEmptyArray() {
        // Then
        assertTrue(() -> isNullOrEmpty(new String[0]));
    }

    @Test
    void shouldReturnTrueIsNullOrEmptyForEmptyCollection() {
        // Then
        assertTrue(() -> isNullOrEmpty(new ArrayList<>()));
    }

    @Test
    void shouldReturnTrueIsNullOrEmptyForEmptyMap() {
        // Then
        assertTrue(() -> isNullOrEmpty(new HashMap<>()));
    }

    @Test
    void shouldReturnFalseIsNullOrEmptyForCharSequence() {
        // When
        final var testChar = "a";

        // Then
        assertFalse(() -> isNullOrEmpty(testChar));
    }

    @Test
    void shouldReturnFalseIsNullOrEmptyForArray() {
        // Then
        assertFalse(() -> isNullOrEmpty(new String[]{"a"}));
    }

    @Test
    void shouldReturnFalseIsNullOrEmptyForNullArray() {
        // Then
        assertTrue(() -> isNullOrEmpty(new String[]{}));
    }

    @Test
    void shouldReturnFalseIsNullOrEmptyForCollection() {
        // When
        final var list = new ArrayList<String>();
        list.add("test");

        // Then
        assertFalse(() -> isNullOrEmpty(list));
    }

    @Test
    void shouldReturnFalseIsNullOrEmptyForMap() {
        // Given
        final var map = new HashMap<String, String>();
        map.put("test", "test");

        // Then
        assertFalse(() -> isNullOrEmpty(map));
    }

    @Test
    void shouldReturn0GetRoundedMoneyAmountForNull() {
        assertEquals(0, getRoundedMoneyAmount(() -> null));
    }

    @Test
    void shouldReturn10GetRoundedMoneyAmountForMoneyWitAmount10() {
        // Given
        final var money = createDollars(BigDecimal.TEN);

        // Then
        assertEquals(10, getRoundedMoneyAmount(() -> money));
    }

    @Test
    void shouldReturn0GetRoundedMoneyAmountForMoneyWithAmount0() {
        // Given
        final var money = createDollars(BigDecimal.ZERO);

        // Then
        assertEquals(0, getRoundedMoneyAmount(() -> money));
    }

    private Money createDollars(BigDecimal amount) {
        final var moneyVO = new MoneyVO();
        moneyVO.setAlternateCurrencyCode("USD");
        moneyVO.setCurrencyCode("USD");
        moneyVO.setCurrencyName("US DOLLAR");
        moneyVO.setPrecisionType("R");
        moneyVO.setPrecisionValue(2);
        moneyVO.setWeekCurrencyIndicator("Y");
        moneyVO.setRoundingType("H");
        moneyVO.setRoundingUnit(0.05);

        final var money = new Money(moneyVO);
        money.setAmount(amount);

        return money;
    }

    @Test
    void getCcaTypeDescriptionShouldThrowExceptionWhenNull() {
        // When + Then
        assertThrows(
                Exception.class,
                () -> getCcaTypeDescription(null)
        );
    }

    @Test
    void getCcaTypeDescriptionShouldReturnTheSameWhenTypeUnknown() {
        // Given
        var status = "unknown type";

        // When
        var actual = getCcaTypeDescription(status);

        // Then
        assertEquals(status, actual);
    }

    @Test
    void getCcaTypeDescriptionShouldReturnInternalWhenStatusI() {
        // When
        var actual = getCcaTypeDescription("I");

        // Then
        assertEquals("Internal", actual);
    }

    @Test
    void getCcaTypeDescriptionShouldReturnActualWhenStatusA() {
        // When
        var actual = getCcaTypeDescription("A");

        // Then
        assertEquals("Actual", actual);
    }

}
