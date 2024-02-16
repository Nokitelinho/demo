package com.ibsplc.neoicargo.mailmasters.vo;

import java.util.Collection;
import lombok.Getter;
import lombok.Setter;
import java.time.ZonedDateTime;
import com.ibsplc.neoicargo.framework.orchestration.vo.AbstractVO;

@Setter
@Getter
public class MailboxIdVO extends AbstractVO {
	private boolean ignoreWarnings;
	private String triggerPoint;
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
	private Collection<MailEventVO> mailEventVOs;
	private ZonedDateTime lastUpdateTime;
	private String lastUpdateUser;
	private String status;
	private String operationFlag;
	private String AutoEmailReqd;
	private String remarks;
}
