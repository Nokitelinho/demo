package com.ibsplc.neoicargo.mail.model;

import com.ibsplc.xibase.server.framework.vo.AbstractVO;
import lombok.Getter;
import lombok.Setter;
import com.ibsplc.neoicargo.common.modal.BaseModel;

@Setter
@Getter
public class AWBFilterModel extends BaseModel {
	private String companyCode;
	private int documentOwnerIdentifier;
	private String masterDocumentNumber;
	private String uldNumber;
	private String origin;
	private String destination;
	private String agentCode;
	private String operationFlag;
	private String triggerPoint;
	private boolean ignoreWarnings;
}
