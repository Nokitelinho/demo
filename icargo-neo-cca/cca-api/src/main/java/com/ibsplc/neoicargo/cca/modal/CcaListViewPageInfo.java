package com.ibsplc.neoicargo.cca.modal;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
public class CcaListViewPageInfo implements Serializable {

    private static final long serialVersionUID = 2836898940603088895L;

    private List<CcaListViewModal> edges;

    @JsonProperty("total_elements")
    private long totalElements;

    @JsonProperty("total_pages")
    private int totalPages;

}
