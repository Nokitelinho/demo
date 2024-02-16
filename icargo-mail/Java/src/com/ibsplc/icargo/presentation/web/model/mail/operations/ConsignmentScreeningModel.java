package com.ibsplc.icargo.presentation.web.model.mail.operations;

import java.util.Collection;
import java.util.Map;

import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.web.spring.model.AbstractScreenModel;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.ApplicableRegulation;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.ConsignerDetails;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.ConsignmentScreeningDetail;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.RoutingInConsignment;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.ScreeningDetails;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.SecurityExemption;

public class ConsignmentScreeningModel extends AbstractScreenModel{
	
	private static final String PRODUCT = "mail";
	private static final String SUBPRODUCT = "operations";
	private static final String SCREENID = "mail.operations.ux.consignmentsecuritydeclaration";
	
	private String conDocNo;
	private String consignmentDate;
	private String consignmentOrigin;
	private String consigmentDest;
	private Collection<ConsignmentScreeningDetail> consignmentScreeningDetail;
	private String companyCode;
	private String consignmentNumber;
	private String paCode;
	private int consignmentSequenceNumber;
	private String applicableRegTransportDirection;
	private String applicableRegBorderAgencyAuthority;
	private String applicableRegReferenceID;
	private String applicableRegFlag;
	private String countryCode;
	private String securityStatusCode;
	private String consignDate;
	private Collection<RoutingInConsignment> routingInConsignmentVO;
	private Map<String, Collection<OneTimeVO>> oneTimeValues;
	private String mailCategory;
	private int statedBags;
	private double statedWeight;
	private String airportCode;
	private String loginUser;
	private String issuerName;
	private String csgIssuerName;
	private String mstAddionalSecurityInfo;
	public String getMstAddionalSecurityInfo() {
		return mstAddionalSecurityInfo;
	}
	public void setMstAddionalSecurityInfo(String mstAddionalSecurityInfo) {
		this.mstAddionalSecurityInfo = mstAddionalSecurityInfo;
	}
	public String getCsgIssuerName() {
		return csgIssuerName;
	}
	public void setCsgIssuerName(String csgIssuerName) {
		this.csgIssuerName = csgIssuerName;
	}
	public String getLoginUser() {
		return loginUser;
	}

	public void setLoginUser(String loginUser) {
		this.loginUser = loginUser;
	}
	private ConsignerDetails consignerDetails;
	
	public ConsignerDetails getConsignerDetails() {
		return consignerDetails;
	}

	public void setConsignerDetails(ConsignerDetails consignerDetails) {
		this.consignerDetails = consignerDetails;
	}
	private ScreeningDetails screeningDetails;

	public ScreeningDetails getScreeningDetails() {
		return screeningDetails;
	}

	public void setScreeningDetails(ScreeningDetails screeningDetails) {
		this.screeningDetails = screeningDetails;
	}
	
	private SecurityExemption securityExemption;
	public SecurityExemption getSecurityExemption() {
		return securityExemption;
	}
	public void setSecurityExemption(SecurityExemption securityExemption) {
		this.securityExemption = securityExemption;
	}
	private ApplicableRegulation applicableRegulation;
	public ApplicableRegulation getApplicableRegulation() {
		return applicableRegulation;
	}
	public void setApplicableRegulation(ApplicableRegulation applicableRegulation) {
		this.applicableRegulation = applicableRegulation;
	}
	public String getConDocNo() {
		return conDocNo;
	}

	public void setConDocNo(String conDocNo) {
		this.conDocNo = conDocNo;
	}

	
	public Collection<ConsignmentScreeningDetail> getConsignmentScreeningDetail() {
		return consignmentScreeningDetail;
	}

	public void setConsignmentScreeningDetail(Collection<ConsignmentScreeningDetail> consignmentScreeningDetail) {
		this.consignmentScreeningDetail = consignmentScreeningDetail;
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

	public Map<String, Collection<OneTimeVO>> getOneTimeValues() {
		return oneTimeValues;
	}

	public void setOneTimeValues(Map<String, Collection<OneTimeVO>> oneTimeValues) {
		this.oneTimeValues = oneTimeValues;
	}
	
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

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public String getSecurityStatusCode() {
		return securityStatusCode;
	}

	public void setSecurityStatusCode(String securityStatusCode) {
		this.securityStatusCode = securityStatusCode;
	}

	public String getConsignDate() {
		return consignDate;
	}

	public void setConsignDate(String consignDate) {
		this.consignDate = consignDate;
	}

	public Collection<RoutingInConsignment> getRoutingInConsignmentVO() {
		return routingInConsignmentVO;
	}

	public void setRoutingInConsignmentVO(Collection<RoutingInConsignment> routingInConsignmentVO) {
		this.routingInConsignmentVO = routingInConsignmentVO;
	}

	public String getMailCategory() {
		return mailCategory;
	}

	public void setMailCategory(String mailCategory) {
		this.mailCategory = mailCategory;
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

	public String getAirportCode() {
		return airportCode;
	}

	public void setAirportCode(String airportCode) {
		this.airportCode = airportCode;
	}

	public static String getSubproduct() {
		return SUBPRODUCT;
	}

	public static String getScreenid() {
		return SCREENID;
	}

	@Override
	public String getProduct() {
		return null;
	}
	
	@Override
	public String getScreenId() {
		return null;
	}
	
	@Override
	public String getSubProduct() {
		return null;
	}
	public String getIssuerName() {
		return issuerName;
	}
	public void setIssuerName(String issuerName) {
		this.issuerName = issuerName;
	}   
private String timeZone;
	public String getTimeZone() {
		return timeZone;
	}

	public void setTimeZone(String timeZone) {
		this.timeZone = timeZone;
	}


}
