package com.ibsplc.neoicargo.mail.model;

import java.util.HashMap;
import com.ibsplc.icargo.framework.jobscheduler.vo.JobScheduleVO;
import lombok.Getter;
import lombok.Setter;
import com.ibsplc.neoicargo.common.modal.BaseModel;

@Setter
@Getter
public class MailInboundAutoAttachAWBJobScheduleModel extends BaseModel {
	private String companyCode;
	private String carrierCodes;
	private String pointOfLadingCountries;
	private String pointOfUnladingCountries;
	private String operationFlag;
	private String triggerPoint;
	private boolean ignoreWarnings;
}
