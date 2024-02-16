package com.ibsplc.neoicargo.mailmasters.vo;

import lombok.Getter;
import lombok.Setter;
import java.time.ZonedDateTime;
import com.ibsplc.neoicargo.framework.orchestration.vo.AbstractVO;

/** 
 * @author A-8149
 */
@Setter
@Getter
public class MailServiceStandardVO extends AbstractVO {
	private boolean ignoreWarnings;
	private String triggerPoint;
	private String gpaCode;
	private String companyCode;
	private String originCode;
	private String destinationCode;
	private String servicelevel;
	private String servicestandard;
	private String contractid;
	private String scanWaived;
	private String lastUpdateUser;
	private ZonedDateTime lastUpdateTime;
	private String operationFlag;
}
