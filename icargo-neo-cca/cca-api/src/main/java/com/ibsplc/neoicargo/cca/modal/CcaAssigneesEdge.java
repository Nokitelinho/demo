package com.ibsplc.neoicargo.cca.modal;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
public class CcaAssigneesEdge implements Serializable {

    private static final long serialVersionUID = 1243715989942997273L;

    @JsonProperty("node")
    private CcaAssigneesNode node;
}
