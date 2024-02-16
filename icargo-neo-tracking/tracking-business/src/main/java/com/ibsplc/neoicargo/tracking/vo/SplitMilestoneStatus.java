package com.ibsplc.neoicargo.tracking.vo;

public enum SplitMilestoneStatus {
    DELIVERED("Delivered"),
    IN_PROGRESS("In progress");

    private final String label;

    SplitMilestoneStatus(String label){
        this.label = label;
    }

    public String getLabel(){
        return this.label;
    }
}
