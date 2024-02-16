package com.ibsplc.neoicargo.mail.model;

import com.ibsplc.icargo.business.msgbroker.config.handling.vo.MessageAddressFilterVO;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;
import lombok.Getter;
import lombok.Setter;
import com.ibsplc.neoicargo.common.modal.BaseModel;

@Setter
@Getter
public class SecurityScreeningValidationFilterModel extends BaseModel {
	private String companyCode;
	private String originAirport;
	private String originCountry;
	private String destinationAirport;
	private String destinationCountry;
	private String transactionAirport;
	private String transactionCountry;
	private String securityStatusCode;
	private String applicableTransaction;
	private String flightType;
	private String originAirportCountryGroup;
	private String destAirportCountryGroup;
	private String txnAirportCountryGroup;
	private String txnAirportGroup;
	private String securityStatusCodeGroup;
	private String mailbagId;
	private boolean appRegValReq;
	private String appRegOriginCountryGroup;
	private String appRegDestCountryGroup;
	private String subClass;
	private String appRegFlg;
	private String appRegDestArp;
	private boolean transferInTxn;
	private boolean securityValNotReq;
	private String securityValNotRequired;
	private String appRegTransistCountryGroup;
	private String appRegTransistCountry;
	private String transistAirport;
	private String operationFlag;
	private String triggerPoint;
	private boolean ignoreWarnings;
}
