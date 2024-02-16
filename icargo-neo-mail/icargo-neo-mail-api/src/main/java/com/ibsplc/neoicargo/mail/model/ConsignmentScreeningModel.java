package com.ibsplc.neoicargo.mail.model;

import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;
import lombok.Getter;
import lombok.Setter;
import com.ibsplc.neoicargo.common.modal.BaseModel;

@Setter
@Getter
public class ConsignmentScreeningModel extends BaseModel {
	private String companyCode;
	private String consignmentNumber;
	private String paCode;
	private int consignmentSequenceNumber;
	private long serialNumber;
	private String sourceIndicator;
	private String screeningLocation;
	private String securityReasonCode;
	private String screeningMethodCode;
	private String screeningAuthority;
	private String screeningRegulation;
	private String securityStatusParty;
	private String securityStatusCode;
	private String additionalSecurityInfo;
	private int statedBags;
	private Measure statedWeight;
	private String remarks;
	private LocalDate securityStatusDate;
	private long agentSerialNumber;
	private int mappingId;
	private String seScreeningAuthority;
	private String seScreeningReasonCode;
	private String seScreeningRegulation;
	private String opFlag;
	private String source;
	private String countryCode;
	private String airportCode;
	private String result;
	private String screenLevelValue;
	private String agentType;
	private String agentID;
	private String expiryDate;
	private String applicableRegTransportDirection;
	private String applicableRegBorderAgencyAuthority;
	private String applicableRegReferenceID;
	private String applicableRegFlag;
	private long malseqnum;
	private String isoCountryCode;
	private String screenDetailType;
	private String securityMethod;
	private String issuedBy;
	private String operationFlag;
	private String triggerPoint;
	private boolean ignoreWarnings;
}
