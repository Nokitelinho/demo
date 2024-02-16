package com.ibsplc.neoicargo.mailmasters.model;

import com.ibsplc.xibase.server.framework.vo.AbstractVO;
import lombok.Getter;
import lombok.Setter;
import com.ibsplc.neoicargo.common.modal.BaseModel;

@Setter
@Getter
public class RdtMasterFilterModel extends BaseModel {
	private String gpaCode;
	private String airportCodes;
	private String companyCode;
	private String mailType;
	private String mailClass;
	private String originAirportCode;
	private String operationFlag;
	private String triggerPoint;
	private boolean ignoreWarnings;
}
