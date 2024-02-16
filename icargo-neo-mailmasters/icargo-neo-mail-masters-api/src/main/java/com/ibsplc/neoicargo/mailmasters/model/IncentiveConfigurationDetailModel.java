package com.ibsplc.neoicargo.mailmasters.model;

import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;
import lombok.Getter;
import lombok.Setter;
import com.ibsplc.neoicargo.common.modal.BaseModel;

@Setter
@Getter
public class IncentiveConfigurationDetailModel extends BaseModel {
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
	private LocalDate lastUpdatedTime;
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
	private String operationFlag;
	private String triggerPoint;
	private boolean ignoreWarnings;
}
