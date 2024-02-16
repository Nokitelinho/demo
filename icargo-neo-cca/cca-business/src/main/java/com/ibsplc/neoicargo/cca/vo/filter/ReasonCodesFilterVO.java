package com.ibsplc.neoicargo.cca.vo.filter;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ibsplc.neoicargo.framework.orchestration.vo.AbstractVO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ReasonCodesFilterVO extends AbstractVO {

    private static final long serialVersionUID = -7018936324716284667L;

    @JsonProperty("parameter_type")
    private String parameterType;

    @JsonProperty("company_code")
    private String companyCode;

}
