package com.ibsplc.neoicargo.awb.vo;

import com.ibsplc.neoicargo.framework.orchestration.vo.AbstractVO;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class AwbContactDetailsVO extends AbstractVO {
    private String companyCode;
    private String shipperCode;
    private Object shipperDetails;
    private String consigneeCode;
    private Object consigneeDetails;
    private LocalDateTime lastUpdatedTime;
    private String lastUpdatedUser;
}
