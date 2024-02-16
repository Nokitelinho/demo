package com.ibsplc.neoicargo.cca.modal;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ibsplc.neoicargo.framework.core.lang.error.ErrorVO;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
public class BulkActionData implements Serializable {

    private static final long serialVersionUID = 7107110035827120314L;

    @JsonProperty("edges")
    private List<BulkActionEdge> edges;

    @JsonProperty("errors")
    private List<ErrorVO> errors;

}
