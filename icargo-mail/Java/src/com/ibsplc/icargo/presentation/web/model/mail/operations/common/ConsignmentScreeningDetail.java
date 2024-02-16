package com.ibsplc.icargo.presentation.web.model.mail.operations.common;


import java.util.Collection;

import com.ibsplc.icargo.business.mail.operations.vo.RoutingInConsignmentVO;

public class ConsignmentScreeningDetail{

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
		
		private double statedWeight;
		
		private String remarks;
		
		private String consignmentDate;
		
		private String consignmentOrigin;
		
		private String consigmentDest;
		
		private String securityStatusDate;
		
		private String opFlag;

		private String countryCode;
		
		private String defaultReason;
		
		private String category;
		
		private String result;
		
		private String agenttype;

		private String isoCountryCode;

		private String agentId;

		private String expiryDate;
		
		private String screenDetailType;
		
		private String time;
		
		private String applicableRegTransportDirection;
		private String applicableRegBorderAgencyAuthority;
		private String applicableRegReferenceID;
		private String applicableRegFlag;
		private String seScreeningAuthority;
		private String seScreeningReasonCode;
		private String seScreeningRegulation;
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
		public String getAgenttype() {
			return agenttype;
		}

		public void setAgenttype(String agenttype) {
			this.agenttype = agenttype;
		}

		public String getIsoCountryCode() {
			return isoCountryCode;
		}

		public void setIsoCountryCode(String isoCountryCode) {
			this.isoCountryCode = isoCountryCode;
		}

		public String getAgentId() {
			return agentId;
		}

		public void setAgentId(String agentId) {
			this.agentId = agentId;
		}


		public String getScreenDetailType() {
			return screenDetailType;
		}

		public void setScreenDetailType(String screenDetailType) {
			this.screenDetailType = screenDetailType;
		}

		private Collection<RoutingInConsignmentVO> routingInConsignmentVOs;
		
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

		public double getStatedWeight() {
			return statedWeight;
		}

		public void setStatedWeight(double statedWeight) {
			this.statedWeight = statedWeight;
		}

		public String getRemarks() {
			return remarks;
		}

		public void setRemarks(String remarks) {
			this.remarks = remarks;
		}

		public String getConsignmentDate() {
			return consignmentDate;
		}

		public void setConsignmentDate(String consignmentDate) {
			this.consignmentDate = consignmentDate;
		}

		public String getConsignmentOrigin() {
			return consignmentOrigin;
		}

		public void setConsignmentOrigin(String consignmentOrigin) {
			this.consignmentOrigin = consignmentOrigin;
		}

		public String getConsigmentDest() {
			return consigmentDest;
		}

		public void setConsigmentDest(String consigmentDest) {
			this.consigmentDest = consigmentDest;
		}

		public String getSecurityStatusDate() {
			return securityStatusDate;
		}

		public void setSecurityStatusDate(String securityStatusDate) {
			this.securityStatusDate = securityStatusDate;
		}

		public String getOpFlag() {
			return opFlag;
		}

		public void setOpFlag(String opFlag) {
			this.opFlag = opFlag;
		}

		public String getCountryCode() {
			return countryCode;
		}

		public void setCountryCode(String countryCode) {
			this.countryCode = countryCode;
		}

		public Collection<RoutingInConsignmentVO> getRoutingInConsignmentVOs() {
			return routingInConsignmentVOs;
		}

		public void setRoutingInConsignmentVOs(Collection<RoutingInConsignmentVO> routingInConsignmentVOs) {
			this.routingInConsignmentVOs = routingInConsignmentVOs;
		}

		public String getDefaultReason() {
			return defaultReason;
		}

		public void setDefaultReason(String defaultReason) {
			this.defaultReason = defaultReason;
		}

		public String getCategory() {
			return category;
		}

		public void setCategory(String category) {
			this.category = category;
		}

		public String getResult() {
			return result;
		}

		public void setResult(String result) {
			this.result = result;
		}
		
		public String getTime() {
			return time;
		}

		public void setTime(String time) {
			this.time = time;
		}

		
		public String getExpiryDate() {
			return expiryDate;
		}

		public void setExpiryDate(String expiryDate) {
			this.expiryDate = expiryDate;
		}

}
