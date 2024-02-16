package com.ibsplc.neoicargo.cca.modal;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CcaTaxValueData {

    @JsonProperty("tax_name")
    private String taxName;

    @JsonProperty("tax_value")
    private String taxValue;

}
