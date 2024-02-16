package com.ibsplc.neoicargo.mail.model;

import com.ibsplc.xibase.server.framework.audit.vo.AuditVO;
import lombok.Getter;
import lombok.Setter;
import com.ibsplc.neoicargo.common.modal.BaseModel;

@Setter
@Getter
public class MailbagAuditModel extends BaseModel {
	private String dsn;
	private String originExchangeOffice;
	private String destinationExchangeOffice;
	private String mailClass;
	private String mailSubclass;
	private String mailCategoryCode;
	private int year;
	private String mailbagId;
	private String lastUpdateUser;
	private long mailSequenceNumber;
	private String operationFlag;
	private String triggerPoint;
	private boolean ignoreWarnings;
}
