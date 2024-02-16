package com.ibsplc.neoicargo.cca.equalsandhashcode;

import com.ibsplc.neoicargo.cca.dao.entity.CCAAwbPk;
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
public class CCAAwbPkEqualsAndHashCodeTest {

    /**
     * an object must equal itself
     */
    @Test
    public void reflexive_equals_contract() {
        // Given
        final var ccaAwbPk = new CCAAwbPk();
        ccaAwbPk.setRecordType("R");

        // Then
        assertEquals(ccaAwbPk, ccaAwbPk);
    }

    @Test
    public void equals_contract() {
        // Then
        assertEquals(new CCAAwbPk(), new CCAAwbPk());
    }

    /**
     * x.equals(y) must return the same result as y.equals(x)
     */
    @Test
    public void symmetric_equals_contract_with_equals_objects() {
        // Given
        final var x = new CCAAwbPk();
        x.setRecordType("R");

        final var y = new CCAAwbPk();
        y.setRecordType("R");

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
        final var x = new CCAAwbPk();
        x.setRecordType("O");

        final var y = new CCAAwbPk();
        y.setRecordType("R");

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
        final var x = new CCAAwbPk();
        x.setRecordType("R");

        final var y = new CCAAwbPk();
        y.setRecordType("R");

        final var z = new CCAAwbPk();
        z.setRecordType("R");

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
        final var x = new CCAAwbPk();
        x.setRecordType("O");

        final var y = new CCAAwbPk();
        y.setRecordType("R");

        final var z = new CCAAwbPk();
        z.setRecordType("Z");

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
        final var x = new CCAAwbPk();
        x.setRecordType("R");

        final var y = new CCAAwbPk();
        y.setRecordType("R");

        // Then
        assertEquals(x, y);

        // And
        x.setRecordType("O");

        // Then
        assertNotEquals(x, y);
    }

    /**
     * the value of equals() should change only if a property that is contained in equals() changes (no randomness allowed)
     */
    @Test
    public void consistent_equals_contract_with_not_equals_objects() {
        // Given
        final var x = new CCAAwbPk();
        x.setRecordType("O");

        final var y = new CCAAwbPk();
        y.setRecordType("R");

        // Then
        assertNotEquals(x, y);

        // And
        y.setRecordType("O");

        // Then
        assertEquals(x, y);
    }

    /**
     * object must be checked for null
     */
    @Test
    public void comparison_null_equals_contract() {
        // Then
        assertFalse(new CCAAwbPk().equals(null));
    }

    /**
     * if (getClass() != o.getClass()) return false;
     */
    @Test
    public void comparison_equals_contract() {
        // Then
        assertFalse(new CCAAwbPk().equals(1));
    }

    /**
     * object must be checked for null
     */
    @Test
    public void comparison_null_equals_contract_with_serial_number_null() {
        // Given
        final var x = new CCAAwbPk();
        x.setRecordType("O");

        final var y = new CCAAwbPk();
        y.setRecordType(null);

        // Then
        assertFalse(x.equals(y));
        assertFalse(y.equals(x));

        // And
        x.setRecordType(null);
        y.setRecordType("O");

        // Then
        assertFalse(y.equals(x));
        assertFalse(x.equals(y));
    }

    /**
     * objects that are equal to each other must return the same hashCode
     */
    @Test
    public void equals_consistency_hash_code_contract() {
        // Given
        final var x = new CCAAwbPk();
        x.setRecordType("O");

        final var y = new CCAAwbPk();
        y.setRecordType("O");

        // Then
        assertEquals(y, x);
        assertEquals(y.hashCode(), x.hashCode());
    }

    /**
     * unequal objects may have different hashCode
     */
    @Test
    public void unequals_consistency_hash_code_contract() {
        // Given
        final var x = new CCAAwbPk();
        x.setRecordType("O");

        final var y = new CCAAwbPk();
        y.setRecordType("R");

        // Then
        assertNotEquals(y, x);
        assertNotEquals(y.hashCode(), x.hashCode());
    }

    /**
     * unequal objects may have the same hashCode
     */
    @Test
    public void collision_hash_code_contract() {
        // Given
        final var x = new CCAAwbPk();
        x.setRecordType("Siblings");

        final var y = new CCAAwbPk();
        y.setRecordType("Teheran");

        // Then
        assertNotEquals(y, x);
        assertEquals(y.hashCode(), x.hashCode());
    }

}