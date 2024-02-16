package com.ibsplc.neoicargo.cca.modal;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CcaDataFilterList implements Serializable {

    private static final long serialVersionUID = 1L;

    @JsonProperty("cca_data_filters")
    private List<CcaDataFilter> ccaDataFilters;

    @JsonProperty("cca_screen_id")
    private String ccaScreenId;

}
