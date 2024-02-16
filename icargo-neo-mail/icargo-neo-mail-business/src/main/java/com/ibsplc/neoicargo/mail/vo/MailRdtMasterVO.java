package com.ibsplc.neoicargo.mail.vo;

import lombok.Getter;
import lombok.Setter;
import java.time.ZonedDateTime;
import com.ibsplc.neoicargo.framework.orchestration.vo.AbstractVO;

@Setter
@Getter
public class MailRdtMasterVO extends AbstractVO {
	private boolean ignoreWarnings;
	private String triggerPoint;
	private String companyCode;
	private String gpaCode;
	private String originAirportCodes;
	private String airportCodes;
	private String operationFlag;
	private ZonedDateTime lastUpdateTime;
	private String lastUpdateUser;
	private long seqnum;
	private int rdtOffset;
	private int rdtDay;
	private String rdtRule;
	private String mailServiceLevel;
	private String mailClass;
	private String mailType;
	private String rdtCfgType;
}
