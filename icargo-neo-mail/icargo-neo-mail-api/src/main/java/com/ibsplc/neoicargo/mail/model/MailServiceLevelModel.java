package com.ibsplc.neoicargo.mail.model;

import com.ibsplc.xibase.server.framework.vo.AbstractVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import lombok.Getter;
import lombok.Setter;
import com.ibsplc.neoicargo.common.modal.BaseModel;

@Setter
@Getter
public class MailServiceLevelModel extends BaseModel {
	private String companyCode;
	private String poaCode;
	private String mailCategory;
	private String mailClass;
	private String mailSubClass;
	private String mailServiceLevel;
	private LocalDate lastUpdatedTime;
	private String lastUpdatedUser;
	private String operationFlag;
	private String triggerPoint;
	private boolean ignoreWarnings;
}
