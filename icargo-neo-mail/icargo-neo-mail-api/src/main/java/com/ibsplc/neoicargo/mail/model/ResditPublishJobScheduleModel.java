package com.ibsplc.neoicargo.mail.model;

import java.util.HashMap;
import com.ibsplc.icargo.framework.jobscheduler.vo.JobScheduleVO;
import lombok.Getter;
import lombok.Setter;
import com.ibsplc.neoicargo.common.modal.BaseModel;

@Setter
@Getter
public class ResditPublishJobScheduleModel extends BaseModel {
	private String companyCode;
	private String paCode;
	private int days;
	private String airportCode;
	private String reportID;
	private String scheduleId;
	private String operationFlag;
	private String triggerPoint;
	private boolean ignoreWarnings;
}
