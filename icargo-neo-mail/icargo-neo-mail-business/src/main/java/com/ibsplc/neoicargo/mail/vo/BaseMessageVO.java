package com.ibsplc.neoicargo.mail.vo;

import com.ibsplc.neoicargo.framework.orchestration.vo.AbstractVO;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BaseMessageVO extends AbstractVO {
    private String companyCode;
    private String messageType;
    private String messageStandard;
}
