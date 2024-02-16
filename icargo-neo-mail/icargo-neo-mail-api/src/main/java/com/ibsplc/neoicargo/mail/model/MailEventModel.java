package com.ibsplc.neoicargo.mail.model;

import com.ibsplc.xibase.server.framework.vo.AbstractVO;
import lombok.Getter;
import lombok.Setter;
import com.ibsplc.neoicargo.common.modal.BaseModel;

@Setter
@Getter
public class MailEventModel extends BaseModel {
	private String companyCode;
	private String mailboxId;
	private String paCode;
	private String mailCategory;
	private String mailClass;
	private boolean isReceived;
	private boolean isUplifted;
	private boolean isAssigned;
	private boolean isPending;
	private boolean isReadyForDelivery;
	private boolean isTransportationCompleted;
	private boolean isArrived;
	private boolean isHandedOver;
	private boolean isReturned;
	private boolean isDelivered;
	private String operationFlag;
	private boolean isLoadedResditFlag;
	private boolean isOnlineHandedOverResditFlag;
	private boolean isHandedOverReceivedResditFlag;
	private boolean isLostFlag;
	private boolean isFoundFlag;
	private String triggerPoint;
	private boolean ignoreWarnings;
}
