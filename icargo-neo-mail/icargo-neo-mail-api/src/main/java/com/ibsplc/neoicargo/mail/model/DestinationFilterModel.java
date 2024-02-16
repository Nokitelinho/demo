package com.ibsplc.neoicargo.mail.model;

import com.ibsplc.xibase.server.framework.vo.AbstractVO;
import lombok.Getter;
import lombok.Setter;
import com.ibsplc.neoicargo.common.modal.BaseModel;

@Setter
@Getter
public class DestinationFilterModel extends BaseModel {
	private String companyCode;
	private String destination;
	private String carrierCode;
	private int carrierId;
	private String airportCode;
	private String operationFlag;
	private String triggerPoint;
	private boolean ignoreWarnings;
}
