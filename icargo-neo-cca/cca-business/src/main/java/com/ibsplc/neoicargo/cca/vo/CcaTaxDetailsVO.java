package com.ibsplc.neoicargo.cca.vo;

import com.ibsplc.neoicargo.framework.orchestration.vo.AbstractVO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CcaTaxDetailsVO extends AbstractVO {

    private static final long serialVersionUID = 3900985531261916933L;

    private Long serialNumber;

    private String configurationType;

    private Object taxDetails;

}
