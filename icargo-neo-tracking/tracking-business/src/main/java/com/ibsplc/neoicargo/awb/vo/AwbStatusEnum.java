package com.ibsplc.neoicargo.awb.vo;


public enum AwbStatusEnum {
    NEW("N"),
    REOPENED("R"),
    EXECUTED("E");

    private final String value;

    AwbStatusEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

}
