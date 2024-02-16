package com.ibsplc.neoicargo.awb.vo;

import com.ibsplc.neoicargo.framework.orchestration.vo.AbstractVO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AwbUserNotificationKeyVO extends AbstractVO {
    private Long trackingAwbSerialNumber;

    private String userId;

}
