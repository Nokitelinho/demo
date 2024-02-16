package com.ibsplc.neoicargo.mail.model;

import com.ibsplc.xibase.server.framework.vo.AbstractVO;
import lombok.Getter;
import lombok.Setter;
import com.ibsplc.neoicargo.common.modal.BaseModel;

@Setter
@Getter
public class SecurityScreeningValidationModel extends BaseModel {
	private String companyCode;
	private String validationType;
	private String errorType;
	private String originAirport;
	private String orgArpExcFlg;
	private String destinationAirport;
	private String destArpExcFlg;
	private String transactionAirport;
	private String trnArpExcFlg;
	private String securityStatusCode;
	private String securityStatusCodeExcFlg;
	private String applicableTransaction;
	private String flightType;
	private String flightTypeExcFlg;
	private String mailbagID;
	private String operationFlag;
	private String triggerPoint;
	private boolean ignoreWarnings;
}
