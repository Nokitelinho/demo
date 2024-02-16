package com.ibsplc.neoicargo.mailmasters.model;

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
	private boolean received;
	private boolean uplifted;
	private boolean assigned;
	private boolean pending;
	private boolean readyForDelivery;
	private boolean transportationCompleted;
	private boolean arrived;
	private boolean handedOver;
	private boolean returned;
	private boolean delivered;
	private String operationFlag;
	private boolean loadedResditFlag;
	private boolean onlineHandedOverResditFlag;
	private boolean handedOverReceivedResditFlag;
	private boolean lostFlag;
	private boolean foundFlag;
	private String triggerPoint;
	private boolean ignoreWarnings;
}
