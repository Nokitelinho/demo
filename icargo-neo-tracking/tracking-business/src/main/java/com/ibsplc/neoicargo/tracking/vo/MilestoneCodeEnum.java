package com.ibsplc.neoicargo.tracking.vo;


import lombok.Getter;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.function.Function.identity;

@Getter
public enum MilestoneCodeEnum {
    ARR("ARR", "Arrival"),
    AWD("AWD", "Documents Delivered"),
    AWR("AWR", "Documents Arrived"),
    CPT("CPT", "AWB Data Capture"),
    DEP("DEP", "Departure"),
    DLV("DLV", "Delivered"),
    E_DIS("E-DIS", "Export Discrepancy"),
    EXP_FLN("EXP-FLN", "Export Flight Finalization"),
    EXP_PLN("EXP-PLN", "Export Plan"),
    FBL("FBL", "FBL Sent"),
    FIW("FIW", "Handover from Ramp"),
    FOH("FOH", "Freight On Hand"),
    FOW("FOW", "Handover to Ramp"),
    I_DIS("I-DIS", "Import Discrepancy"),
    IMP_FLG("IMP-FLG", "Import Flag Flight"),
    MAN("MAN", "Manifest"),
    NFD("NFD", "Notified To Customer"),
    PRE("PRE", "Build up"),
    RCF("RCF", "Check in"),
    RCS("RCS", "Received from Agent"),
    RCT("RCT", "Received from OAL"),
    TFD("TFD", "Transferred"),
    TFM("TFM", "Transfer Manifest"),
    BKD("BKD", "Booked"),
    BKG("BKG", "Description to be provided"),
    TRM("TRM", "Description to be provided"),
    DIS("DIS", "Offloaded"),
    DRN("DRN", "Delivery return");

    private final String label;
    private final String description;

    MilestoneCodeEnum(String label, String description) {
        this.label = label;
        this.description = description;
    }

    public static MilestoneCodeEnum valueOfLabel(String label) {
        return MILESTONE_CODE_MAP.get(label);
    }

    private static final Map<String, MilestoneCodeEnum> MILESTONE_CODE_MAP = Arrays.stream(values())
            .collect(Collectors.toMap(MilestoneCodeEnum::getLabel, identity()));

}
