package com.ibsplc.neoicargo.cca.util;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

import java.util.stream.Stream;

import static com.ibsplc.neoicargo.cca.constants.CcaScreenIdConstants.LIST_CCA_SCREEN_ID;
import static com.ibsplc.neoicargo.cca.constants.CcaScreenIdConstants.LIST_CCA_SCREEN_NAME;
import static com.ibsplc.neoicargo.cca.constants.CcaScreenIdConstants.MAINTAIN_CCA_SCREEN_ID;
import static com.ibsplc.neoicargo.cca.constants.CcaScreenIdConstants.MAINTAIN_CCA_SCREEN_NAME;
import static com.ibsplc.neoicargo.cca.constants.CcaScreenIdConstants.UNKNOWN_SCREEN_NAME;
import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(JUnitPlatform.class)
class TriggerPointUtilTest {

    @ParameterizedTest
    @MethodSource("provideTestParameters")
    void getTriggerPointShouldReturnRelatedTriggerPointOrUnknown(String screenId, String triggerPoint) {
        // When
        var actual = TriggerPointUtil.getTriggerPoint(screenId);

        // Then
        assertEquals(triggerPoint, actual);
    }

    private static Stream<Arguments> provideTestParameters() {
        return Stream.of(
                Arguments.of(MAINTAIN_CCA_SCREEN_ID, MAINTAIN_CCA_SCREEN_NAME),
                Arguments.of(LIST_CCA_SCREEN_ID, LIST_CCA_SCREEN_NAME),
                Arguments.of("WRONG_SCREEN_ID", UNKNOWN_SCREEN_NAME),
                Arguments.of("", UNKNOWN_SCREEN_NAME),
                Arguments.of(null, UNKNOWN_SCREEN_NAME)
        );
    }

    @Test
    void getTriggerPointShouldIgnoreCase() {
        // Given
        var lowCaseScreenId = MAINTAIN_CCA_SCREEN_ID.toLowerCase();

        // When
        var actual = TriggerPointUtil.getTriggerPoint(lowCaseScreenId);

        // Then
        assertEquals(MAINTAIN_CCA_SCREEN_NAME, actual);
    }
}