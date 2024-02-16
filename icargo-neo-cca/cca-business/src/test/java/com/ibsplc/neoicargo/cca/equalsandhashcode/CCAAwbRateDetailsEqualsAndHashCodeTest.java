package com.ibsplc.neoicargo.cca.equalsandhashcode;

import com.ibsplc.neoicargo.cca.dao.entity.CCAAwbRateDetail;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

/**
 * These tests are required to pass quality gate
 */
@RunWith(JUnitPlatform.class)
public class CCAAwbRateDetailsEqualsAndHashCodeTest {

    /**
     * an object must equal itself
     */
    @Test
    public void reflexive_equals_contract() {
        // Given
        final var x = new CCAAwbRateDetail();
        x.setSerialNumber(1L);

        // Then
        assertEquals(x, x);
    }

    /**
     * x.equals(y) must return the same result as y.equals(x)
     */
    @Test
    public void symmetric_equals_contract_with_equals_objects() {
        // Given
        final var x = new CCAAwbRateDetail();
        x.setSerialNumber(1L);

        final var y = new CCAAwbRateDetail();
        y.setSerialNumber(1L);

        // Then
        assertEquals(x, y);
        assertEquals(y, x);
    }

    /**
     * x.equals(y) must return the same result as y.equals(x)
     */
    @Test
    public void symmetric_equals_contract_with_not_equals_objects() {
        // Given
        final var x = new CCAAwbRateDetail();
        x.setSerialNumber(1L);

        final var y = new CCAAwbRateDetail();
        y.setSerialNumber(2L);

        // Then
        assertNotEquals(x, y);
        assertNotEquals(y, x);
    }

    /**
     * if x.equals(y) and y.equals(z), then also x.equals(z)
     */
    @Test
    public void transitive_equals_contract_with_equals_objects() {
        // Given
        final var x = new CCAAwbRateDetail();
        x.setSerialNumber(1L);

        final var y = new CCAAwbRateDetail();
        y.setSerialNumber(1L);

        final var z = new CCAAwbRateDetail();
        z.setSerialNumber(1L);

        // Then
        assertEquals(x, y);
        assertEquals(y, z);
        assertEquals(x, z);
    }

    /**
     * if x.equals(y) and y.equals(z), then also x.equals(z)
     */
    @Test
    public void transitive_equals_contract_with_not_equals_objects() {
        // Given
        final var x = new CCAAwbRateDetail();
        x.setSerialNumber(1L);

        final var y = new CCAAwbRateDetail();
        y.setSerialNumber(2L);

        final var z = new CCAAwbRateDetail();
        z.setSerialNumber(3L);

        // Then
        assertNotEquals(x, y);
        assertNotEquals(y, z);
        assertNotEquals(x, z);
    }

    /**
     * the value of equals() should change only if a property that is contained in equals() changes (no randomness allowed)
     */
    @Test
    public void consistent_equals_contract_with_equals_objects() {
        // Given
        final var x = new CCAAwbRateDetail();
        x.setSerialNumber(1L);

        final var y = new CCAAwbRateDetail();
        y.setSerialNumber(1L);

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
    public void consistent_equals_contract_with_not_equals_objects() {
        // Given
        final var x = new CCAAwbRateDetail();
        x.setSerialNumber(1L);

        final var y = new CCAAwbRateDetail();
        y.setSerialNumber(2L);

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
    public void comparison_null_equals_contract() {
        // Given
        final var x = new CCAAwbRateDetail();
        x.setSerialNumber(1L);

        // Then
        assertFalse(x.equals(null));
    }

    /**
     * object must be checked for null
     */
    @Test
    public void comparison_null_equals_contract_with_serial_number_null() {
        // Given
        final var x = new CCAAwbRateDetail();
        x.setSerialNumber(1L);

        final var y = new CCAAwbRateDetail();
        y.setSerialNumber(null);

        // Then
        assertFalse(x.equals(y));
        assertFalse(y.equals(x));

        // And
        x.setSerialNumber(null);
        y.setSerialNumber(1L);

        // Then
        assertFalse(y.equals(x));
        assertFalse(x.equals(y));
    }

    /**
     * if (getClass() != o.getClass()) return false;
     */
    @Test
    public void comparison_equals_contract() {
        // Given
        final var x = new CCAAwbRateDetail();
        x.setSerialNumber(1L);

        // Then
        assertFalse(x.equals(1));
    }

    /**
     * objects that are equal to each other must return the same hashCode
     */
    @Test
    public void equals_consistency_hash_code_contract() {
        // Given
        final var x = new CCAAwbRateDetail();
        x.setSerialNumber(1L);

        final var y = new CCAAwbRateDetail();
        y.setSerialNumber(1L);

        // Then
        assertEquals(y, x);
        assertEquals(y.hashCode(), x.hashCode());
    }

    /**
     * unequal objects may have the same hashCode
     */
    @Test
    public void collision_hash_code_contract() {
        // Given
        final var x = new CCAAwbRateDetail();
        x.setSerialNumber(1L);

        final var y = new CCAAwbRateDetail();
        y.setSerialNumber(2L);

        // Then
        assertNotEquals(y, x);
        assertEquals(y.hashCode(), x.hashCode());
    }

}