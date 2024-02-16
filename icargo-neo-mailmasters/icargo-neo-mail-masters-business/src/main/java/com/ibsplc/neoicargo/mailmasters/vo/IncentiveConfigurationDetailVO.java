package com.ibsplc.neoicargo.mailmasters.vo;

import lombok.Getter;
import lombok.Setter;
import java.time.ZonedDateTime;
import com.ibsplc.neoicargo.framework.orchestration.vo.AbstractVO;

/** 
 * @author A-6986
 */
@Setter
@Getter
public class IncentiveConfigurationDetailVO extends AbstractVO {
	private boolean ignoreWarnings;
	private String triggerPoint;
	private String operationFlag;
	private String companyCode;
	private int incentiveSerialNumber;
	private String sequenceNumber;
	private String disIncParameterType;
	private String disIncParameterCode;
	private String disIncParameterValue;
	private String incParameterType;
	private String incParameterCode;
	private String incParameterValue;
	private String excludeFlag;
	private ZonedDateTime lastUpdatedTime;
	private String lastUpdatedUser;
	private String disIncSrvParameterType;
	private String disIncSrvParameterCode;
	private String disIncSrvParameterValue;
	private String srvExcludeFlag;
	private String disIncNonSrvParameterType;
	private String disIncNonSrvParameterCode;
	private String disIncNonSrvParameterValue;
	private String nonSrvExcludeFlag;
	private String disIncBothSrvParameterType;
	private String disIncBothSrvParameterCode;
	private String disIncBothSrvParameterValue;
	private String bothSrvExcludeFlag;
}
