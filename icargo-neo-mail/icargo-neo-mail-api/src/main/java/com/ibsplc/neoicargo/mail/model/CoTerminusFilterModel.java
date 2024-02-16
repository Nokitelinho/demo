package com.ibsplc.neoicargo.mail.model;

import com.ibsplc.xibase.server.framework.vo.AbstractVO;
import lombok.Getter;
import lombok.Setter;
import com.ibsplc.neoicargo.common.modal.BaseModel;

@Setter
@Getter
public class CoTerminusFilterModel extends BaseModel {
	private String gpaCode;
	private String airportCodes;
	private String resditModes;
	private String receivedfromTruck;
	private String companyCode;
	private String operationFlag;
	private String triggerPoint;
	private boolean ignoreWarnings;
}
