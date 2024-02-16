package com.ibsplc.neoicargo.cca.vo;

import com.ibsplc.neoicargo.framework.orchestration.vo.AbstractVO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CCAPrintVO extends AbstractVO {

    private static final long serialVersionUID = -3678135014990556407L;

    private byte[] generatedReport;

    private String fileName;

}