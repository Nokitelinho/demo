package com.ibsplc.neoicargo.mailmasters.model;

import com.ibsplc.xibase.server.framework.vo.AbstractVO;
import lombok.Getter;
import lombok.Setter;
import com.ibsplc.neoicargo.common.modal.BaseModel;

@Setter
@Getter
public class MailBoxIdLovModel extends BaseModel {
	private String companyCode;
	private String mailboxCode;
	private String mailboxDescription;
	private String operationFlag;
	private String triggerPoint;
	private boolean ignoreWarnings;
}
