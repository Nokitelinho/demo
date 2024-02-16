package com.ibsplc.neoicargo.cca.status;

import lombok.AllArgsConstructor;
import lombok.Getter;

import static com.ibsplc.neoicargo.cca.status.CcaStatusConstants.APPROVED;
import static com.ibsplc.neoicargo.cca.status.CcaStatusConstants.DELETED;
import static com.ibsplc.neoicargo.cca.status.CcaStatusConstants.INITIATED;
import static com.ibsplc.neoicargo.cca.status.CcaStatusConstants.NEW;
import static com.ibsplc.neoicargo.cca.status.CcaStatusConstants.RECOMMENDED;
import static com.ibsplc.neoicargo.cca.status.CcaStatusConstants.REJECTED;
import static com.ibsplc.neoicargo.cca.status.CcaStatusConstants.SAVED;
import static com.ibsplc.neoicargo.cca.status.CcaStatusConstants.SYSTEM_REJECTED;

@Getter
@AllArgsConstructor
public enum CcaStatus {
    N(NEW, SAVED),
    A(APPROVED, APPROVED),
    R(REJECTED, REJECTED),
    C(RECOMMENDED, RECOMMENDED),
    I(INITIATED, INITIATED),
    D(DELETED, DELETED),
    S(SYSTEM_REJECTED, SYSTEM_REJECTED);

    private final String descriptionInfo;
    private final String statusMessage;

    public static CcaStatus getCcaStatusByName(String statusName) {
        if (statusName == null) {
            return null;
        }
        switch (statusName) {
            case "N":
                return N;
            case "A":
                return A;
            case "R":
                return R;
            case "C":
                return C;
            case "I":
                return I;
            case "D":
                return D;
            case "S":
                return S;
            default:
                return null;
        }
    }
}
