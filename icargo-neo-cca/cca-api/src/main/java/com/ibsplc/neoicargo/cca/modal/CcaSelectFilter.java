package com.ibsplc.neoicargo.cca.modal;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
public class CcaSelectFilter implements Serializable {

    private static final long serialVersionUID = 1L;

    @JsonProperty("first")
    private Integer first;

    @JsonProperty("cursor")
    private String cursor;

    @JsonProperty("filter")
    private List<String> filter;

    @JsonProperty("search")
    private String search;

    @JsonProperty("load_from_filter")
    private boolean loadFromFilter;
}
