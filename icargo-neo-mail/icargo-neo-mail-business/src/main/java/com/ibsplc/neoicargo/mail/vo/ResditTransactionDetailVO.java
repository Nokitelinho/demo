package com.ibsplc.neoicargo.mail.vo;

import lombok.Getter;
import lombok.Setter;
import java.time.ZonedDateTime;
import com.ibsplc.neoicargo.framework.orchestration.vo.AbstractVO;

/** 
 * @author a-3109
 */
@Setter
@Getter
public class ResditTransactionDetailVO extends AbstractVO {
	private boolean ignoreWarnings;
	private String triggerPoint;
	private String transaction;
	private String receivedResditFlag;
	private String assignedResditFlag;
	private String upliftedResditFlag;
	private String handedOverResditFlag;
	private String loadedResditFlag;
	private String handedOverReceivedResditFlag;
	private String operationFlag;
	private ZonedDateTime lastUpdateTime;
	private String lastUpdateUser;
	private String deliveredResditFlag;
	private String readyForDeliveryFlag;
	private String transportationCompletedResditFlag;
	private String arrivedResditFlag;
	private String returnedResditFlag;
}
