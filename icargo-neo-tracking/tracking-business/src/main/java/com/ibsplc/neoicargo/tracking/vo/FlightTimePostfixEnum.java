package com.ibsplc.neoicargo.tracking.vo;


public enum FlightTimePostfixEnum {
    ACTUAL("A"),
    SCHEDULED("S");

    private final String label;

    FlightTimePostfixEnum(String label){
        this.label = label;
    }

    public String getLabel(){
        return this.label;
    }

}
