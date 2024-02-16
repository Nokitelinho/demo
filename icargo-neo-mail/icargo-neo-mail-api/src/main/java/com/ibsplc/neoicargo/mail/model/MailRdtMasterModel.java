package com.ibsplc.neoicargo.mail.model;

import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;
import lombok.Getter;
import lombok.Setter;
import com.ibsplc.neoicargo.common.modal.BaseModel;

@Setter
@Getter
public class MailRdtMasterModel extends BaseModel {
	private String companyCode;
	private String gpaCode;
	private String originAirportCodes;
	private String airportCodes;
	private String operationFlag;
	private LocalDate lastUpdateTime;
	private String lastUpdateUser;
	private long seqnum;
	private int rdtOffset;
	private int rdtDay;
	private String rdtRule;
	private String mailServiceLevel;
	private String mailClass;
	private String mailType;
	private String rdtCfgType;
	private String triggerPoint;
	private boolean ignoreWarnings;
}
