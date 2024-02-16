package com.ibsplc.neoicargo.mailmasters.model;

import java.util.Collection;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;
import lombok.Getter;
import lombok.Setter;
import com.ibsplc.neoicargo.common.modal.BaseModel;

@Setter
@Getter
public class MailRuleConfigModel extends BaseModel {
	private String mailboxId;
	private Collection<MailRuleConfigParameterModel> mailRuleConfigParameters;
	private long messageConfigurationSequenceNumber;
	private String companyCode;
	private LocalDate fromDate;
	private LocalDate toDate;
	private String operationFlag;
	private String triggerPoint;
	private boolean ignoreWarnings;
}
