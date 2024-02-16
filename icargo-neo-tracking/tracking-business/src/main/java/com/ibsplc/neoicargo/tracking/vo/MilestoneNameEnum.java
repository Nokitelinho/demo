package com.ibsplc.neoicargo.tracking.vo;

public enum MilestoneNameEnum {
    ACCEPTED("Accepted"),
    PARTIALLY_ARRIVED("Partially Arrived"),
    ARRIVED("Arrived"),
    PARTIALLY_DEPARTED("Partially Departed"),
    DEPARTED("Departed"),
    PARTIALLY_DELIVERED("Partially Delivered"),
    DELIVERED("Delivered");

    private final String label;

    MilestoneNameEnum(String label){
        this.label = label;
    }

    public String getLabel(){
        return this.label;
    }
}
