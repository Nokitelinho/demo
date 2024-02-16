package com.ibsplc.neoicargo.tracking.vo;

import com.ibsplc.neoicargo.awb.dao.entity.AWBUserNotification;
import com.ibsplc.neoicargo.framework.orchestration.vo.AbstractVO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmailMilestoneNotificationVO extends AbstractVO {
    private String shipmentKey;
    private MilestoneCodeEnum milestoneCode;
    private String airline;
    private Integer pieces;
    private String airportCode;
    private String milestoneTime;
    private List<AWBUserNotification> notifications;
}
