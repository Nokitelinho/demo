package com.ibsplc.neoicargo.mailmasters.vo;

import java.util.Collection;
import lombok.Getter;
import lombok.Setter;
import java.time.ZonedDateTime;
import com.ibsplc.neoicargo.framework.orchestration.vo.AbstractVO;

@Setter
@Getter
public class MailRuleConfigVO extends AbstractVO {
	private boolean ignoreWarnings;
	private String triggerPoint;
	private String operationFlag;
	private String mailboxId;
	private Collection<MailRuleConfigParameterVO> mailRuleConfigParameters;
	private long messageConfigurationSequenceNumber;
	private String companyCode;
	private ZonedDateTime fromDate;
	private ZonedDateTime toDate;
}
