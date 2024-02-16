package com.ibsplc.neoicargo.cca.modal;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
public class CcaAssigneesNode implements Serializable {

    private static final long serialVersionUID = 1L;

    @JsonProperty("user_name")
    private String username;

    @JsonProperty("user_code")
    private String userCode;
}
