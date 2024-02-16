package com.ibsplc.neoicargo.tracking.vo;


public enum MilestoneStatusEnum {
    TO_DO("to do", "Not Achieved"),
    IN_PROGRESS("in progress", "Partially Achieved"),
    DONE("done", "Achieved");

    private final String label;
    private final String description;

    MilestoneStatusEnum(String label, String description){
        this.label = label;
        this.description = description;
    }

    public String getLabel(){
        return this.label;
    }

}
