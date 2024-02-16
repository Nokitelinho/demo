package com.ibsplc.neoicargo.cca.equalsandhashcode;

import com.ibsplc.neoicargo.cca.constants.CcaConstants;
import com.ibsplc.neoicargo.cca.dao.entity.CcaAwbTaxDetails;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@RunWith(JUnitPlatform.class)
class CcaAwbTaxDetailsEqualsAndHashCodeTest {

    /**
     * an object must equal itself
     */
    @Test
    void reflexive_equals_contract() {
        // Given
        final var x = getCcaAwbTaxDetails(1L);

        // Then
        assertEquals(x, x);
    }

    /**
     * x.equals(y) must return the same result as y.equals(x)
     */
    @Test
    void symmetric_equals_contract_with_equals_objects() {
        // Given
        final var x = getCcaAwbTaxDetails(1L);

        final var y = getCcaAwbTaxDetails(1L);

        // Then
        assertEquals(x, y);
        assertEquals(y, x);
    }

    /**
     * x.equals(y) must return the same result as y.equals(x)
     */
    @Test
    void symmetric_equals_contract_with_not_equals_objects() {
        // Given
        final var x = getCcaAwbTaxDetails(1L);

        final var y = getCcaAwbTaxDetails(2L);

        // Then
        assertNotEquals(x, y);
        assertNotEquals(y, x);
    }

    /**
     * if x.equals(y) and y.equals(z), then also x.equals(z)
     */
    @Test
    void transitive_equals_contract_with_equals_objects() {
        // Given
        final var x = getCcaAwbTaxDetails(1L);

        final var y = getCcaAwbTaxDetails(1L);

        final var z = getCcaAwbTaxDetails(1L);

        // Then
        assertEquals(x, y);
        assertEquals(y, z);
        assertEquals(x, z);
    }

    /**
     * if x.equals(y) and y.equals(z), then also x.equals(z)
     */
    @Test
    void transitive_equals_contract_with_not_equals_objects() {
        // Given
        final var x = getCcaAwbTaxDetails(1L);

        final var y = getCcaAwbTaxDetails(2L);

        final var z = getCcaAwbTaxDetails(3L);

        // Then
        assertNotEquals(x, y);
        assertNotEquals(y, z);
        assertNotEquals(x, z);
    }

    /**
     * the value of equals() should change only if a property that is contained in equals() changes (no randomness allowed)
     */
    @Test
    void consistent_equals_contract_with_equals_objects() {
        // Given
        final var x = getCcaAwbTaxDetails(1L);

        final var y = getCcaAwbTaxDetails(1L);

        // Then
        assertEquals(x, y);

        // And
        y.setSerialNumber(2L);

        // Then
        assertNotEquals(x, y);
    }

    /**
     * the value of equals() should change only if a property that is contained in equals() changes (no randomness allowed)
     */
    @Test
    void consistent_equals_contract_with_not_equals_objects() {
        // Given
        final var x = getCcaAwbTaxDetails(1L);

        final var y = getCcaAwbTaxDetails(2L);

        // Then
        assertNotEquals(x, y);

        // And
        y.setSerialNumber(1L);

        // Then
        assertEquals(x, y);
    }

    /**
     * object must be checked for null
     */
    @Test
    void comparison_null_equals_contract() {
        // Given
        final var x = getCcaAwbTaxDetails(1L);

        // Then
        assertNotEquals(null, x);
    }

    /**
     * object must be checked for null
     */
    @Test
    void comparison_null_equals_contract_with_serial_number_null() {
        // Given
        final var x = getCcaAwbTaxDetails(1L);

        final var y = new CcaAwbTaxDetails();
        y.setSerialNumber(null);

        // Then
        assertNotEquals(x, y);
        assertNotEquals(y, x);

        // And
        x.setSerialNumber(null);
        y.setSerialNumber(1L);

        // Then
        assertNotEquals(y, x);
        assertNotEquals(x, y);
    }

    /**
     * if (getClass() != o.getClass()) return false;
     */
    @Test
    void comparison_equals_contract() {
        // Given
        final var x = getCcaAwbTaxDetails(1L);

        // Then
        assertNotEquals(1, x);
    }

    /**
     * objects that are equal to each other must return the same hashCode
     */
    @Test
    void equals_consistency_hash_code_contract() {
        // Given
        final var x = getCcaAwbTaxDetails(1L);

        final var y = getCcaAwbTaxDetails(1L);

        // Then
        assertEquals(y, x);
        assertEquals(y.hashCode(), x.hashCode());
    }

    private CcaAwbTaxDetails getCcaAwbTaxDetails(Long serialNumber) {
        final var x = new CcaAwbTaxDetails();
        x.setSerialNumber(serialNumber);
        x.setRecordType(CcaConstants.CCA_RECORD_TYPE_REVISED);
        return x;
    }
}