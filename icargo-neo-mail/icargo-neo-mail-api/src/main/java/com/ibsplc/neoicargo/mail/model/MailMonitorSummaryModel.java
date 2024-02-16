package com.ibsplc.neoicargo.mail.model;

import com.ibsplc.xibase.server.framework.vo.AbstractVO;
import lombok.Getter;
import lombok.Setter;
import com.ibsplc.neoicargo.common.modal.BaseModel;

@Setter
@Getter
public class MailMonitorSummaryModel extends BaseModel {
	private String monitoringType;
	private String sector;
	private double value;
	private String operationFlag;
	private String triggerPoint;
	private boolean ignoreWarnings;
}