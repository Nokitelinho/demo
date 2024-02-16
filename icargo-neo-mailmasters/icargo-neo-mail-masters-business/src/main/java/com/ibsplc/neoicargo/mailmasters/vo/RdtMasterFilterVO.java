package com.ibsplc.neoicargo.mailmasters.vo;

import lombok.Getter;
import lombok.Setter;
import com.ibsplc.neoicargo.framework.orchestration.vo.AbstractVO;

@Setter
@Getter
public class RdtMasterFilterVO extends AbstractVO {
	private boolean ignoreWarnings;
	private String triggerPoint;
	private String operationFlag;
	private String gpaCode;
	private String airportCodes;
	private String companyCode;
	private String mailType;
	private String mailClass;
	private String originAirportCode;
}
