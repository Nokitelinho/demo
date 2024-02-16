package com.ibsplc.neoicargo.cca.util;

import java.util.Map;
import java.util.Optional;

import static com.ibsplc.neoicargo.cca.constants.CcaScreenIdConstants.LIST_CCA_SCREEN_ID;
import static com.ibsplc.neoicargo.cca.constants.CcaScreenIdConstants.LIST_CCA_SCREEN_NAME;
import static com.ibsplc.neoicargo.cca.constants.CcaScreenIdConstants.MAINTAIN_CCA_SCREEN_ID;
import static com.ibsplc.neoicargo.cca.constants.CcaScreenIdConstants.MAINTAIN_CCA_SCREEN_NAME;
import static com.ibsplc.neoicargo.cca.constants.CcaScreenIdConstants.UNKNOWN_SCREEN_NAME;

public final class TriggerPointUtil {

    private TriggerPointUtil() {
    }

    private static final Map<String, String> TRIGGER_POINT_MAPPING = Map.of(
            MAINTAIN_CCA_SCREEN_ID, MAINTAIN_CCA_SCREEN_NAME,
            LIST_CCA_SCREEN_ID, LIST_CCA_SCREEN_NAME
    );

    public static String getTriggerPoint(String screenId) {
        return Optional.ofNullable(screenId)
                .map(String::toUpperCase)
                .map(TRIGGER_POINT_MAPPING::get)
                .orElse(UNKNOWN_SCREEN_NAME);
    }
}
