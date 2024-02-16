package com.ibsplc.neoicargo.cca.modal;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class CcaAssigneesPage  implements Serializable {

    private static final long serialVersionUID = 1L;

    @JsonProperty("edges")
    private List<CcaAssigneesEdge> edges;
}
