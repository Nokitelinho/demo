package com.ibsplc.neoicargo.cca.vo.filter;

import com.ibsplc.neoicargo.framework.orchestration.vo.AbstractVO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CCAPrintFilterVO extends AbstractVO {

    private static final long serialVersionUID = 3944265716308212026L;

    private String ccaReferenceNumber;

    private String reportName;

    private String shipmentPrefix;

    private String masterDocumentNumber;

    private String companyCode;

}
