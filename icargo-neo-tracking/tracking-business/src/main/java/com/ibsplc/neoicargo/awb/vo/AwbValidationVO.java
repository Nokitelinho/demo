package com.ibsplc.neoicargo.awb.vo;

import com.ibsplc.neoicargo.framework.orchestration.vo.AbstractVO;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AwbValidationVO extends AbstractVO {
    private String shipmentPrefix;
    private String masterDocumentNumber;
}
