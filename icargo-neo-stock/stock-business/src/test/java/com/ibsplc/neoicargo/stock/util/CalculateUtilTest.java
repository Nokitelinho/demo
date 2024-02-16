package com.ibsplc.neoicargo.stock.util;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

@RunWith(JUnitPlatform.class)
class CalculateUtilTest {

  @Test
  void toLong() {
    long actual = CalculationUtil.toLong("1");
    assertEquals(1, actual);
  }

  @Test
  void countNumberOfDocuments() {
    long actual = CalculationUtil.countNumberOfDocuments(2, 3);
    assertEquals(2, actual);
  }

  @Test
  void stockHolderLovFilterReplaceTest() {
    // Given
    var testSring = "*TEST*";
    var resultString = "%TEST%";

    // Then
    var result = CalculationUtil.stockHolderLovFilterReplace(testSring);
    assertThat(result).isEqualTo(resultString);
  }

  @Test
  void shouldConvertChars() {
    // Given
    var rangeValue = "1000000";
    var offset = 1;

    // When test for char 47
    var result = CalculationUtil.subtract(rangeValue, offset);

    // Then
    assertThat("0999999").isEqualTo(result);

    // Given
    rangeValue = "1000000A";

    // When test for char 64
    result = CalculationUtil.subtract(rangeValue, offset);

    // Then
    assertThat("0999999Z").isEqualTo(result);
  }

  @Test
  void toLongNullableTest() {
    // Given
    var val = "10000";

    // When
    var result = CalculationUtil.toLongNullable(val);

    // Then
    assertThat(10000L).isEqualTo(result);

    // Given
    val = " ";

    // When
    result = CalculationUtil.toLongNullable(val);

    // Then
    assertThat(result).isNull();

    // Given
    val = null;

    // When
    result = CalculationUtil.toLongNullable(val);

    // Then
    assertThat(result).isNull();
  }

  @Test
  void toIntegerNullableTest() {
    // Given
    var val = "10000";

    // When
    var result = CalculationUtil.toIntegerNullable(val);

    // Then
    assertThat(10000).isEqualTo(result);

    // Given
    val = " ";

    // When
    result = CalculationUtil.toIntegerNullable(val);

    // Then
    assertThat(result).isNull();

    // Given
    val = null;

    // When
    result = CalculationUtil.toIntegerNullable(val);

    // Then
    assertThat(result).isNull();
  }
}
