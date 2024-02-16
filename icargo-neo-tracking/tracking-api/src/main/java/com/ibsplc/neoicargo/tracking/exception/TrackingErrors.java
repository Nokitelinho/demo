/**
 *
 */
package com.ibsplc.neoicargo.tracking.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum TrackingErrors {

    AWB_NOT_FOUND("NEO_TRK_001", "AWB not found"),
    USR_AWB_NTF_MILESTONE_EMAIL_NOT_PROVIDED("NEO_TRK_002", "Email addresses and notification milestones shouldn't be empty!"),
    USR_AWB_NTF_EMAIL_NOT_VALID("NEO_TRK_003", "There are no valid emails provided");

    private final String errorCode;

    private final String errorMessage;

}
