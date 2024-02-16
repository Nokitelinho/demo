package com.ibsplc.neoicargo.mail.model;

import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;
import lombok.Getter;
import lombok.Setter;
import com.ibsplc.neoicargo.common.modal.BaseModel;

@Setter
@Getter
public class ResditTransactionDetailModel extends BaseModel {
	private String transaction;
	private String receivedResditFlag;
	private String assignedResditFlag;
	private String upliftedResditFlag;
	private String handedOverResditFlag;
	private String loadedResditFlag;
	private String handedOverReceivedResditFlag;
	private String operationFlag;
	private LocalDate lastUpdateTime;
	private String lastUpdateUser;
	private String deliveredResditFlag;
	private String readyForDeliveryFlag;
	private String transportationCompletedResditFlag;
	private String arrivedResditFlag;
	private String returnedResditFlag;
	private String triggerPoint;
	private boolean ignoreWarnings;
}
