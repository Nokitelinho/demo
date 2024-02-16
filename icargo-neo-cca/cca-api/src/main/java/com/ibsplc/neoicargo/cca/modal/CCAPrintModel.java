package com.ibsplc.neoicargo.cca.modal;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CCAPrintModel {

    @JsonProperty("file_data")
    private byte[] generatedReport;

    @JsonProperty("file_name")
    private String fileName;

}
