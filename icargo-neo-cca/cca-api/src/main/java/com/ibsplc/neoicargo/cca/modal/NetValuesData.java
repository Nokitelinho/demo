package com.ibsplc.neoicargo.cca.modal;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NetValuesData implements Serializable {

    private static final long serialVersionUID = 1L;

    @JsonProperty("net_value_export")
    private Double netValueExport;

    @JsonProperty("net_value_import")
    private Double netValueImport;
}
