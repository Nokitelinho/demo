package com.ibsplc.icargo.business.mail.operations.vo;


import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;

public class ConsignmentScreeningVO extends AbstractVO {

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
	private String mailIdr;
	private String dateTime;
	
	public String getDateTime() {
		return dateTime;
	}
	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}
	public String getSeScreeningAuthority() {
		return seScreeningAuthority;
	}
	public void setSeScreeningAuthority(String seScreeningAuthority) {
		this.seScreeningAuthority = seScreeningAuthority;
	}
	public String getSeScreeningReasonCode() {
		return seScreeningReasonCode;
	}
	public void setSeScreeningReasonCode(String seScreeningReasonCode) {
		this.seScreeningReasonCode = seScreeningReasonCode;
	}
	public String getSeScreeningRegulation() {
		return seScreeningRegulation;
	}
	public void setSeScreeningRegulation(String seScreeningRegulation) {
		this.seScreeningRegulation = seScreeningRegulation;
	}
	public String getIsoCountryCode() {
		return isoCountryCode;
	}

	public void setIsoCountryCode(String isoCountryCode) {
		this.isoCountryCode = isoCountryCode;
	}

	public String getScreenDetailType() {
		return screenDetailType;
	}

	public void setScreenDetailType(String screenDetailType) {
		this.screenDetailType = screenDetailType;
	}

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

    public String getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    public String getConsignmentNumber() {
        return consignmentNumber;
    }

    public void setConsignmentNumber(String consignmentNumber) {
        this.consignmentNumber = consignmentNumber;
    }

    public String getPaCode() {
        return paCode;
    }

    public void setPaCode(String paCode) {
        this.paCode = paCode;
    }

    public int getConsignmentSequenceNumber() {
        return consignmentSequenceNumber;
    }

    public void setConsignmentSequenceNumber(int consignmentSequenceNumber) {
        this.consignmentSequenceNumber = consignmentSequenceNumber;
    }

    public long getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(long serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getSourceIndicator() {
        return sourceIndicator;
    }

    public void setSourceIndicator(String sourceIndicator) {
        this.sourceIndicator = sourceIndicator;
    }

    public String getScreeningLocation() {
        return screeningLocation;
    }

    public void setScreeningLocation(String screeningLocation) {
        this.screeningLocation = screeningLocation;
    }

    public String getSecurityReasonCode() {
        return securityReasonCode;
    }

    public void setSecurityReasonCode(String securityReasonCode) {
        this.securityReasonCode = securityReasonCode;
    }

    public String getScreeningMethodCode() {
        return screeningMethodCode;
    }

    public void setScreeningMethodCode(String screeningMethodCode) {
        this.screeningMethodCode = screeningMethodCode;
    }

    public String getScreeningAuthority() {
        return screeningAuthority;
    }

    public void setScreeningAuthority(String screeningAuthority) {
        this.screeningAuthority = screeningAuthority;
    }

    public String getScreeningRegulation() {
        return screeningRegulation;
    }

    public void setScreeningRegulation(String screeningRegulation) {
        this.screeningRegulation = screeningRegulation;
    }

	public String getSecurityStatusParty() {
		return securityStatusParty;
	}

	public void setSecurityStatusParty(String securityStatusParty) {
		this.securityStatusParty = securityStatusParty;
	}

	public String getSecurityStatusCode() {
		return securityStatusCode;
	}

	public void setSecurityStatusCode(String securityStatusCode) {
		this.securityStatusCode = securityStatusCode;
	}

	public String getAdditionalSecurityInfo() {
		return additionalSecurityInfo;
	}

	public void setAdditionalSecurityInfo(String additionalSecurityInfo) {
		this.additionalSecurityInfo = additionalSecurityInfo;
	}

	public int getStatedBags() {
		return statedBags;
	}

	public void setStatedBags(int statedBags) {
		this.statedBags = statedBags;
	}

	public Measure getStatedWeight() {
		return statedWeight;
	}

	public void setStatedWeight(Measure statedWeight) {
		this.statedWeight = statedWeight;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public LocalDate getSecurityStatusDate() {
		return securityStatusDate;
	}

	public void setSecurityStatusDate(LocalDate securityStatusDate) {
		this.securityStatusDate = securityStatusDate;
	}

	public String getOpFlag() {
		return opFlag;
	}

	public void setOpFlag(String opFlag) {
		this.opFlag = opFlag;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public String getAirportCode() {
		return airportCode;
	}

	public void setAirportCode(String airportCode) {
		this.airportCode = airportCode;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}
	public String getScreenLevelValue() {
		return screenLevelValue;
	}
	public void setScreenLevelValue(String screenLevelValue) {
		this.screenLevelValue = screenLevelValue;
	}

	

	public String getAgentType() {
		return agentType;
	}
	public void setAgentType(String agentType) {
		this.agentType = agentType;
	}
	public String getAgentID() {
		return agentID;
	}
	public void setAgentID(String agentID) {
		this.agentID = agentID;
	}
	public String getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(String expiryDate) {
		this.expiryDate = expiryDate;
	}
	public String getApplicableRegTransportDirection() {
		return applicableRegTransportDirection;
	}
	public void setApplicableRegTransportDirection(String applicableRegTransportDirection) {
		this.applicableRegTransportDirection = applicableRegTransportDirection;
	}
	public String getApplicableRegBorderAgencyAuthority() {
		return applicableRegBorderAgencyAuthority;
	}
	public void setApplicableRegBorderAgencyAuthority(String applicableRegBorderAgencyAuthority) {
		this.applicableRegBorderAgencyAuthority = applicableRegBorderAgencyAuthority;
	}
	public String getApplicableRegReferenceID() {
		return applicableRegReferenceID;
	}
	public void setApplicableRegReferenceID(String applicableRegReferenceID) {
		this.applicableRegReferenceID = applicableRegReferenceID;
	}
	public String getApplicableRegFlag() {
		return applicableRegFlag;
	}
	public void setApplicableRegFlag(String applicableRegFlag) {
		this.applicableRegFlag = applicableRegFlag;
	}
	public long getMalseqnum() {
		return malseqnum;
	}

	public void setMalseqnum(long malseqnum) {
		this.malseqnum = malseqnum;
	}
	private String securityMethod;
	private String issuedBy;
	public String getSecurityMethod() {
		return securityMethod;
	}
	public String getIssuedBy() {
		return issuedBy;
	}
	public void setSecurityMethod(String securityMethod) {
		this.securityMethod = securityMethod;
	}
	public void setIssuedBy(String issuedBy) {
		this.issuedBy = issuedBy;
	}
	public long getAgentSerialNumber() {
		return agentSerialNumber;
	}
	public void setAgentSerialNumber(long agentSerialNumber) {
		this.agentSerialNumber = agentSerialNumber;
	}
	public int getMappingId() {
		return mappingId;
	}
	public void setMappingId(int mappingId) {
		this.mappingId = mappingId;
	}
	public String getMailIdr() {
		return mailIdr;
	}
	public void setMailIdr(String mailIdr) {
		this.mailIdr = mailIdr;
	}
	
	
}
