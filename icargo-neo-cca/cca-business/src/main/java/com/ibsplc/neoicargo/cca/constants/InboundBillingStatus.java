package com.ibsplc.neoicargo.cca.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum InboundBillingStatus {
    IMPORT_BILLED("A", "Import Billed"),
    IMPORT_BILLABLE("B", "Import Billable"),
    CUSTOMER_BILLED("C", "Customer Billed"),
    CASS_IMPORT_BILLED("D", "CASS Import Billed"),
    CASS_IMPORT_BILLABLE("E", "CASS Import Billable"),
    NON_BILLABLE("N", "Non-Billable"),
    ON_HOLD("O", "On hold"),
    CUSTOMER_BILLABLE("S", "Customer Billable"),
    WITHDRAWN("W", "Withdrawn");

    private final String statusValueInDb;
    private final String statusValueInFrontend;
}
