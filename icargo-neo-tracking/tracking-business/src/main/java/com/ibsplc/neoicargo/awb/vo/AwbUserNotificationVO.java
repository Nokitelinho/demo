package com.ibsplc.neoicargo.awb.vo;

import com.ibsplc.neoicargo.awb.dao.entity.AWBUserNotificationKey;
import com.ibsplc.neoicargo.awb.dao.entity.ShipmentKey;
import com.ibsplc.neoicargo.framework.orchestration.vo.AbstractVO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AwbUserNotificationVO extends AbstractVO {
    private ShipmentKey shipmentKey;
    private AWBUserNotificationKey notificationsKey;
    private List<String> notificationMilestones;
    private List<String> emails;
    private String companyCode;
}
