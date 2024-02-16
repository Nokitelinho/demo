package com.ibsplc.neoicargo.mail.model;

import com.ibsplc.xibase.server.framework.vo.AbstractVO;
import lombok.Getter;
import lombok.Setter;
import com.ibsplc.neoicargo.common.modal.BaseModel;

@Setter
@Getter
public class MLDConfigurationModel extends BaseModel {
	private String companyCode;
	private String carrierCode;
	private int carrierIdentifier;
	private String airportCode;
	private String allocatedRequired;
	private String upliftedRequired;
	private String deliveredRequired;
	private String hNDRequired;
	private String receivedRequired;
	private String operationFlag;
	private String mldversion;
	private String stagedRequired;
	private String nestedRequired;
	private String receivedFromFightRequired;
	private String transferredFromOALRequired;
	private String receivedFromOALRequired;
	private String returnedRequired;
	private String triggerPoint;
	private boolean ignoreWarnings;
}
