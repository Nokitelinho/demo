package com.ibsplc.neoicargo.mail.model;

import com.ibsplc.icargo.framework.jobscheduler.vo.JobScheduleVO;
import java.util.HashMap;
import lombok.Getter;
import lombok.Setter;
import com.ibsplc.neoicargo.common.modal.BaseModel;

@Setter
@Getter
public class AutoAttachAWBJobScheduleModel extends BaseModel {
	private String companyCode;
	private int carrierId;
	private String carrierCode;
	private String flightNumber;
	private long flightSequenceNumber;
	private String pol;
	private String actualTimeOfDeparture;
	private String operationFlag;
	private String triggerPoint;
	private boolean ignoreWarnings;
}
