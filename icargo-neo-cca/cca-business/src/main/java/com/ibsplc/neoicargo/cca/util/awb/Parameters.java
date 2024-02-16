package com.ibsplc.neoicargo.cca.util.awb;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Parameters {

    private List<Parameter> awbParameters = new ArrayList<>();
    private List<Parameter> firstFlightParameters = new ArrayList<>();

    @Getter
    @Setter
    public static class Parameter {
        private String parameterCode;
        private String parameterValue;
        private String condition;
        private String optionalParValue1;
        private String optionalParValue2;
        private String parameterType;
        private String description;
        private int priority;
    }

}