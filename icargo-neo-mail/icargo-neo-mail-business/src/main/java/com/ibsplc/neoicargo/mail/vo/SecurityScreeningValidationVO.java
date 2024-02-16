package com.ibsplc.neoicargo.mail.vo;

import lombok.Getter;
import lombok.Setter;
import com.ibsplc.neoicargo.framework.orchestration.vo.AbstractVO;

@Setter
@Getter
public class SecurityScreeningValidationVO extends AbstractVO {
	private boolean ignoreWarnings;
	private String triggerPoint;
	private String operationFlag;
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
}
