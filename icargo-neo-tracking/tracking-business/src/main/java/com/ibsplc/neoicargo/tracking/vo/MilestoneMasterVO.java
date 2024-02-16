package com.ibsplc.neoicargo.tracking.vo;

import com.ibsplc.neoicargo.framework.orchestration.vo.AbstractVO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MilestoneMasterVO extends AbstractVO {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;   
    private Long serialNumber;
    private String companyCode;
    private String milestoneCode;
    private String milestoneDescription;
    private String milestoneType;
    private String milestoneShipmentType;
    private boolean activityViewFlag;
    private boolean emailNotificationFlag;
    
}
