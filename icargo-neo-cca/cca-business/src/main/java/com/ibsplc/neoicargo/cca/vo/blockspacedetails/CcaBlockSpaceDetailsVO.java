package com.ibsplc.neoicargo.cca.vo.blockspacedetails;

import com.ibsplc.neoicargo.framework.orchestration.vo.AbstractVO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Collection;

@NoArgsConstructor
@Getter
@Setter
public class CcaBlockSpaceDetailsVO extends AbstractVO {

    private static final long serialVersionUID = 1L;

    private String companyCode;

    private int airlineId;

    private String airlineCode;

    private String blockSpaceId;

    private String blockSpaceDescription;

    private Collection<CcaBlockSpaceRatingDetailsVO> blockSpaceRatingDetailVOs;

}
