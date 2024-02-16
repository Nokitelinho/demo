package com.ibsplc.neoicargo.cca.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OutboundBillingStatus {
    CASS_BILLED("A", "CASS Billed"),
    CASS_BILLABLE("C", "CASS Billable"),
    CUSTOMER_BILLED("M", "Customer Billed"),
    NON_BILLABLE("N", "Non-Billable"),
    ON_HOLD("O", "On hold"),
    CUSTOMER_BILLABLE("S", "Customer Billable"),
    WITHDRAWN("W", "Withdrawn");

    private final String statusValueInDb;
    private final String statusValueInFrontend;
}
