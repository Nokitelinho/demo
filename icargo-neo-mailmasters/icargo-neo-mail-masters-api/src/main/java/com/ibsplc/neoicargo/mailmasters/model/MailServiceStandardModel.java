package com.ibsplc.neoicargo.mailmasters.model;

import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;
import lombok.Getter;
import lombok.Setter;
import com.ibsplc.neoicargo.common.modal.BaseModel;

@Setter
@Getter
public class MailServiceStandardModel extends BaseModel {
	private String gpaCode;
	private String companyCode;
	private String originCode;
	private String destinationCode;
	private String servicelevel;
	private String servicestandard;
	private String contractid;
	private String scanWaived;
	private String lastUpdateUser;
	private LocalDate lastUpdateTime;
	private String operationFlag;
	private String triggerPoint;
	private boolean ignoreWarnings;
}
