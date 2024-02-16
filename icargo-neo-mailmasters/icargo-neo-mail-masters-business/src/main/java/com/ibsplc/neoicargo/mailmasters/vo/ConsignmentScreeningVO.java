package com.ibsplc.neoicargo.mailmasters.vo;

import lombok.Getter;
import lombok.Setter;
import java.time.ZonedDateTime;
import com.ibsplc.neoicargo.framework.util.unit.Quantity;
import com.ibsplc.neoicargo.framework.orchestration.vo.AbstractVO;

@Setter
@Getter
public class ConsignmentScreeningVO extends AbstractVO {
	private boolean ignoreWarnings;
	private String triggerPoint;
	private String operationFlag;
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
	private Quantity statedWeight;
	private String remarks;
	private ZonedDateTime securityStatusDate;
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
}
