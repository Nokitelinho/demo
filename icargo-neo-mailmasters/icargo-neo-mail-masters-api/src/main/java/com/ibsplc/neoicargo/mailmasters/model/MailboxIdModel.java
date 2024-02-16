package com.ibsplc.neoicargo.mailmasters.model;

import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.neoicargo.common.modal.BaseModel;
import lombok.Getter;
import lombok.Setter;

import java.util.Collection;

@Setter
@Getter
public class MailboxIdModel extends BaseModel {
	private String mailboxID;
	private String mailboxName;
	private String ownerCode;
	private int resditTriggerPeriod;
	private boolean partialResdit;
	private boolean msgEventLocationNeeded;
	private String messagingEnabled;
	private String residtversion;
	private String mailboxOwner;
	private String resditversion;
	private String companyCode;
	private String mailboxStatus;
	private Collection<MailEventModel> mailEventVOs;
	private LocalDate lastUpdateTime;
	private String lastUpdateUser;
	private String status;
	private String operationFlag;
	private String AutoEmailReqd;
	private String remarks;
	private String triggerPoint;
	private boolean ignoreWarnings;
}
