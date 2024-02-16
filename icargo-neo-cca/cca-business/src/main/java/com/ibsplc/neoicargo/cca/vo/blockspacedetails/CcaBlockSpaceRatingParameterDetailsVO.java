package com.ibsplc.neoicargo.cca.vo.blockspacedetails;

import com.ibsplc.neoicargo.framework.orchestration.vo.AbstractVO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
class CcaBlockSpaceRatingParameterDetailsVO extends AbstractVO {

    private static final long serialVersionUID = 1L;

    private String companyCode;

    private int airlineId;

    private String airlineCode;

    private String blockSpaceId;

    private int rateSequenceNumber;

    private String parameterCode;

    private boolean excludeFlag;

    private String parameterValue;

}
