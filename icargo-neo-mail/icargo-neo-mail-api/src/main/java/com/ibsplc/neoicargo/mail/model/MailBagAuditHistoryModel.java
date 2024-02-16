package com.ibsplc.neoicargo.mail.model;

import java.util.Calendar;
import com.ibsplc.xibase.server.framework.audit.vo.AuditHistoryVO;
import lombok.Getter;
import lombok.Setter;
import com.ibsplc.neoicargo.common.modal.BaseModel;

@Setter
@Getter
public class MailBagAuditHistoryModel extends BaseModel {
	private String mailbagId;
	private String dsn;
	private String originExchangeOffice;
	private String destinationExchangeOffice;
	private String mailSubclass;
	private String mailCategoryCode;
	private int year;
	private long serialNumber;
	private long historySequenceNumber;
	private String lastUpdateUser;
	private String auditField;
	private String oldValue;
	private String newValue;
	private String usercode;
	private String station;
	private Calendar lastUpdateTime;
	private long mailSequenceNumber;
	private String operationFlag;
	private String triggerPoint;
	private boolean ignoreWarnings;
}
