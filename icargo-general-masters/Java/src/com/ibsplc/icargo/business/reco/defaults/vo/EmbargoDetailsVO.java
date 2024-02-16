/*
 * EmbargoDetailsVO.java Created on Jul 20, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary 
 * information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.reco.defaults.vo;

import java.util.Map;
import java.util.Set;

import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;

/**
 * @author A-1358
 *
 */
public class EmbargoDetailsVO extends AbstractVO {
    
	public static final String EMBARGOEXPIRY_ALERT_WORKFLOW = "reco.defaults.embargoexpiryalerteworkflowname";
	
	public static final String EMBARGOEXPIRY_ALERT_REQUIRED = "reco.defaults.embargoexpiryalertrequired";
	
	public static final String NUMBEROFDAYS_PRIOR_EMBARGOEXPIRY = "reco.defaults.numberofdayspriorembargoexpiry";
	
	public static final String EMBARGOCREATION_ALERT_WORKFLOW = "reco.defaults.embargocreationalertworkflowname";
    
	private Set<EmbargoParameterVO> params;
	
	private Set<EmbargoGeographicLevelVO> geographicLevels;

	private String embargoLevel;
    
	private String embargoParameters;
    
	private String companyCode;
    
    private String embargoReferenceNumber;
    
    private LocalDate startDate;
    
    private LocalDate endDate;    
    
    private String origin;
    
    private String originType;
    
    private String destination;        
    
    private String destinationType;
    
    private String status;
    
    private String embargoDescription;
    
    private String remarks;

    private Map<String,String> map;
    
    private boolean isSuspended;
    
// for ANA CR Coo/Frozen/CC Embargo added by Sinish
	private boolean isCool;
	private boolean isFrozen;
	private boolean isCC;
	//Added by A-7941 as part of ICRD-335522
	public boolean approveFlag;
    /**
	 * @return the approveFlag
	 */
	public boolean isApproveFlag() {
		return approveFlag;
	}
	/**
	 * @param approveFlag the approveFlag to set
	 */
	public void setApproveFlag(boolean approveFlag) {
		this.approveFlag = approveFlag;
	}
    private LocalDate lastUpdateTime;
    
	private  String viaPoint;
	
	private  String viaPointType;
	
	private String daysOfOperation;
	
	private String daysOfOperationApplicableOn;
	
	//Added by A-5160 for ICRD-27155
	private String airportCode;
	
	private int embargoVersion;


	private String ruleType;
	private String category;
	private String complianceType;
	private String applicableTransactions;
	
	//Added by A-5867 for ICRD-68630
	private String processType;
	private String categoryDescription;
	private String complianceTypeDescription;
	private String flightTypeDescription;
	private String originAirportCodeInc;
	private String originAirportCodeExc;
	private String originAirportGroupInc;
	private String originAirportGroupExc;
	private String originCountryCodeInc;
	private String originCountryCodeExc;
	private String originCountryGroupInc;
	private String originCountryGroupExc;
	private String viaPointAirportCodeInc;
	private String viaPointAirportCodeExc;
	private String viaPointAirportGroupInc;
	private String viaPointAirportGroupExc;
	private String viaPointCountryCodeInc;
	private String viaPointCountryCodeExc;
	private String viaPointCountryGroupInc;
	private String viaPointCountryGroupExc;
	private String destinationAirportCodeInc;
	private String destinationAirportCodeExc;
	private String destinationAirportGroupInc;
	private String destinationAirportGroupExc;
	private String destinationCountryCodeInc;
	private String destinationCountryCodeExc;
	private String destinationCountryGroupInc;
	private String destinationCountryGroupExc;
	private String productInc;
	private String productExc;
	private String sccInc;
	private String sccExc;
	private String sccGroupInc;
	private String sccGroupExc;
	private String unNumberInc;
	private String unNumberExc;
	private String pkgInsInc;//Added by A-7534 for ICRD-226601
	private String pkgInsExc;//Added by A-7534 for ICRD-226601
	private String awbPrefixInc;
	private String awbPrefixExc;
	private String airlineCodeInc;
	private String airlineCodeExc;
	private String flightOwnerInc;
	private String flightOwnerExc;
	private String originDayOfOperation;
	private String viaPointDayOfOperation;
	private String destinationDayOfOperation;
	private String originDateInc;
	private String originDateExc;
	private String viaPointDateInc;
	private String viaPointDateExc;
	private String destinationDateInc;
	private String destinationDateExc;
	private String natureOfGoods;
	private String commodity;
	private String flightNumber;
	private String paymentType;
	private String flightType;
	private String splitIndicator;
	private String originStartTime;
	private String originEndTime;
	private String viaPointStartTime;
	private String viaPointEndTime;
	private String destinationStartTime;
	private String destinationEndTime;
	private String weightStart;
	private String weightEnd;
	private String lengthStart;
	private String lengthEnd;
	private String widthStart;
	private String widthEnd;
	private String heightStart;
	private String heightEnd;

	//added by A-4823
	private String shipmentID;
	//Added for embargo publish
	private EmbargoPublishVO embargoPublishVO; 
	private String airlineGroupInc;
	private String airlineGroupExc;
	private String paymentTypeInc;
	private String paymentTypeExc;
	private String flightNumberInc;
	private String flightNumberExc;
	private String natureOfGoodsInc;
	private String natureOfGoodsExc;
	private String commodityInc;
	private String commodityExc;
	//Added by A-8130 for ICRD-232462
	private String unWeightStart;
	private String unWeightEnd;
	private String dvForCustomsStart;
	private String dvForCustomsEnd;
	private String dvForCarriageStart;
	private String dvForCarriageEnd;
	private String agtCodeInc;
	private String agtCodeExc;
	private String agtGroupInc;
	private String agtGroupExc;
	private String uldInc;
	private String uldExc;
	private String volumeStart;
	private String volumeEnd;
	private String aircraftTypeOrgInc;
	private String aircraftTypeDstInc;
	private String aircraftTypeViaInc;
    private String aircraftTypeGrpOrgInc;
	private String aircraftTypeGrpDstInc;
	private String aircraftTypeGrpViaInc;
	private String aircraftTypeOrgExc;
	private String aircraftTypeDstExc;
	private String aircraftTypeViaExc;
    private String aircraftTypeGrpOrgExc;
	private String aircraftTypeGrpDstExc;
	private String aircraftTypeGrpViaExc;
	
	//added by A-5799 for IASCB-23507 Starts
  	private String serviceCargoClassInc;
  	private String serviceCargoClassExc;
  	private String aircraftClassOriginInc;
  	private String aircraftClassDestInc;
  	private String aircraftClassViaInc;
	private String aircraftClassOriginExc;
  	private String aircraftClassDestExc;
  	private String aircraftClassViaExc;
  	private String shipperCodeInc;
  	private String shipperGroupInc;
  	private String consigneeCodeInc;
  	private String consigneeGroupInc;
  	private String shipmentTypeInc;
  	private String serviceTypeInc;
  	private String consolInc;
	private String shipperCodeExc;
  	private String shipperGroupExc;
  	private String consigneeCodeExc;
  	private String consigneeGroupExc;
  	private String shipmentTypeExc;
  	private String serviceTypeExc;
	private String consolExc;
  	//added by A-5799 for IASCB-23507 ends
	private String packagingGroupInc;
	private String packagingGroupExc;
	
	private String subRiskInc;
	private String subRiskExc;
	
	//added by 202766
	private String flightTypeInc;
	private String flightTypeExc;
	// Added by A-8374 for ICRD-340405 starts
	private String dateString;
	private String timeString;
	private String sourceId;
	
	/**
	 * @return the sourceId
	 */
	public String getSourceId() {
		return sourceId;
	}
	/**
	 * @param sourceId the sourceId to set
	 */
	public void setSourceId(String sourceId) {
		this.sourceId = sourceId;
	}
	public String getDateString() {
		return dateString;
	}

	public void setDateString(String dateString) {
		this.dateString = dateString;
	}

	public String getTimeString() {
		return timeString;
	}

	public void setTimeString(String timeString) {
		this.timeString = timeString;
	}
	// Added by A-8374 for ICRD-340405 ends
	public EmbargoPublishVO getEmbargoPublishVO() {
		return embargoPublishVO;
	}
	public void setEmbargoPublishVO(EmbargoPublishVO embargoPublishVO) {
		this.embargoPublishVO = embargoPublishVO;
	}
	public int getEmbargoVersion() {
		return embargoVersion;
	}

	public void setEmbargoVersion(int embargoVersion) {
		this.embargoVersion = embargoVersion;
	}
	
	public String getRuleType() {
		return ruleType;
	}

	public void setRuleType(String ruleType) {
		this.ruleType = ruleType;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getComplianceType() {
		return complianceType;
	}

	public void setComplianceType(String complianceType) {
		this.complianceType = complianceType;
	}

	public String getApplicableTransactions() {
		return applicableTransactions;
	}

	public void setApplicableTransactions(String applicableTransactions) {
		this.applicableTransactions = applicableTransactions;
	}

	public Set<EmbargoGeographicLevelVO> getGeographicLevels() {
		return geographicLevels;
	}

	public void setGeographicLevels(Set<EmbargoGeographicLevelVO> geographicLevels) {
		this.geographicLevels = geographicLevels;
	}
	

	private String lastUpdatedUser;

	public String getLastUpdatedUser() {
		return lastUpdatedUser;
	}

	public void setLastUpdatedUser(String lastUpdatedUser) {
		this.lastUpdatedUser = lastUpdatedUser;
	}

	public String getAirportCode() {
		return airportCode;
	}

	public void setAirportCode(String airportCode) {
		this.airportCode = airportCode;
	}

	/**
	 * @return Returns the companyCode.
	 */
	public String getCompanyCode() {
		return companyCode;
	}

	/**
	 * @param companyCode The companyCode to set.
	 */
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	/**
	 * @return Returns the destination.
	 */
	public String getDestination() {
		return destination;
	}

	/**
	 * @param destination The destination to set.
	 */
	public void setDestination(String destination) {
		this.destination = destination;
	}

	/**
	 * @return Returns the destinationType.
	 */
	public String getDestinationType() {
		return destinationType;
	}

	/**
	 * @param destinationType The destinationType to set.
	 */
	public void setDestinationType(String destinationType) {
		this.destinationType = destinationType;
	}

	/**
	 * @return Returns the embargoDescription.
	 */
	public String getEmbargoDescription() {
		return embargoDescription;
	}

	/**
	 * @param embargoDescription The embargoDescription to set.
	 */
	public void setEmbargoDescription(String embargoDescription) {
		this.embargoDescription = embargoDescription;
	}

	/**
	 * @return Returns the embargoLevel.
	 */
	public String getEmbargoLevel() {
		return embargoLevel;
	}

	/**
	 * @param embargoLevel The embargoLevel to set.
	 */
	public void setEmbargoLevel(String embargoLevel) {
		this.embargoLevel = embargoLevel;
	}

	/**
	 * @return Returns the embargoParameters.
	 */
	public String getEmbargoParameters() {
		return embargoParameters;
	}

	/**
	 * @param embargoParameters The embargoParameters to set.
	 */
	public void setEmbargoParameters(String embargoParameters) {
		this.embargoParameters = embargoParameters;
	}

	/**
	 * @return Returns the embargoReferenceNumber.
	 */
	public String getEmbargoReferenceNumber() {
		return embargoReferenceNumber;
	}

	/**
	 * @param embargoReferenceNumber The embargoReferenceNumber to set.
	 */
	public void setEmbargoReferenceNumber(String embargoReferenceNumber) {
		this.embargoReferenceNumber = embargoReferenceNumber;
	}

	/**
	 * @return Returns the endDate.
	 */
	public LocalDate getEndDate() {
		return endDate;
	}

	/**
	 * @param endDate The endDate to set.
	 */
	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	/**
	 * @return Returns the map.
	 */
	public Map<String, String> getMap() {
		return map;
	}

	/**
	 * @param map The map to set.
	 */
	public void setMap(Map<String, String> map) {
		this.map = map;
	}

	/**
	 * @return Returns the origin.
	 */
	public String getOrigin() {
		return origin;
	}

	/**
	 * @param origin The origin to set.
	 */
	public void setOrigin(String origin) {
		this.origin = origin;
	}

	/**
	 * @return Returns the originType.
	 */
	public String getOriginType() {
		return originType;
	}

	/**
	 * @param originType The originType to set.
	 */
	public void setOriginType(String originType) {
		this.originType = originType;
	}

	/**
	 * @return Returns the params.
	 */
	public Set<EmbargoParameterVO> getParams() {
		return params;
	}

	/**
	 * @param params The params to set.
	 */
	public void setParams(Set<EmbargoParameterVO> params) {
		this.params = params;
	}

	/**
	 * @return Returns the remarks.
	 */
	public String getRemarks() {
		return remarks;
	}

	/**
	 * @param remarks The remarks to set.
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	/**
	 * @return Returns the startDate.
	 */
	public LocalDate getStartDate() {
		return startDate;
	}

	/**
	 * @param startDate The startDate to set.
	 */
	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	/**
	 * @return Returns the status.
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status The status to set.
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return Returns the isSuspended.
	 */
	public boolean getIsSuspended() {
		return isSuspended;
	}

	/**
	 * @param isSuspended The isSuspended to set.
	 */
	public void setIsSuspended(boolean isSuspended) {
		this.isSuspended = isSuspended;
	}

	/**
	 * @return Returns the lastUpdateTime.
	 */
	public LocalDate getLastUpdateTime() {
		return lastUpdateTime;
	}

	/**
	 * @param lastUpdateTime The lastUpdateTime to set.
	 */
	public void setLastUpdateTime(LocalDate lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}
	/**
	 * @return Returns the isCool.
	 */
	public boolean getIsCool() {
		return isCool;
	}
	/**
	 * @param isCool The isCool to set.
	 */
	public void setIsCool(boolean isCool) {
		this.isCool = isCool;
	}

	/**
	 * @return Returns the isFrozen.
	 */
	public boolean getIsFrozen() {
		return isFrozen;
	}
	/**
	 * @param isFrozen The isFrozen to set.
	 */
	public void setIsFrozen(boolean isFrozen) {
		this.isFrozen = isFrozen;
	}

	/**
	 * @return Returns the isCC.
	 */
	public boolean getIsCC() {
		return isCC;
	}
	/**
	 * @param isCC The isCC to set.
	 */
	public void setIsCC(boolean isCC) {
		this.isCC = isCC;
	}

	/**
	 * @return Returns the viaPointType.
	 */
	public String getViaPoint() {
		return viaPoint;
	}

	/**
	 * @param viaPointType The viaPointType to set.
	 */
	public void setViaPoint(String viaPoint) {
		this.viaPoint = viaPoint;
	}

	/**
	 * @return Returns the viaPointType.
	 */
	public String getViaPointType() {
		return viaPointType;
	}

	/**
	 * @param viaPointType The viaPointType to set.
	 */
	public void setViaPointType(String viaPointType) {
		this.viaPointType = viaPointType;
	}

	/**
	 * @return Returns the daysOfOperation.
	 */
	public String getDaysOfOperation() {
		return daysOfOperation;
	}

	/**
	 * @param daysOfOperation The daysOfOperation to set.
	 */
	public void setDaysOfOperation(String daysOfOperation) {
		this.daysOfOperation = daysOfOperation;
	}

	/**
	 * @return Returns the daysOfOperationApplicableOn.
	 */
	public String getDaysOfOperationApplicableOn() {
		return daysOfOperationApplicableOn;
	}

	/**
	 * @param daysOfOperationApplicableOn The daysOfOperationApplicableOn to set.
	 */
	public void setDaysOfOperationApplicableOn(String daysOfOperationApplicableOn) {
		this.daysOfOperationApplicableOn = daysOfOperationApplicableOn;
	}

	public void setProcessType(String processType) {
		this.processType = processType;
	}

	public String getProcessType() {
		return processType;
	}

	public String getCategoryDescription() {
		return categoryDescription;
	}

	public void setCategoryDescription(String categoryDescription) {
		this.categoryDescription = categoryDescription;
	}

	public String getComplianceTypeDescription() {
		return complianceTypeDescription;
	}

	public void setComplianceTypeDescription(String complianceTypeDescription) {
		this.complianceTypeDescription = complianceTypeDescription;
	}

	public String getOriginAirportCodeInc() {
		return originAirportCodeInc;
	}

	public void setOriginAirportCodeInc(String originAirportCodeInc) {
		this.originAirportCodeInc = originAirportCodeInc;
	}

	public String getOriginAirportCodeExc() {
		return originAirportCodeExc;
	}

	public void setOriginAirportCodeExc(String originAirportCodeExc) {
		this.originAirportCodeExc = originAirportCodeExc;
	}

	public String getOriginAirportGroupInc() {
		return originAirportGroupInc;
	}

	public void setOriginAirportGroupInc(String originAirportGroupInc) {
		this.originAirportGroupInc = originAirportGroupInc;
	}

	public String getOriginAirportGroupExc() {
		return originAirportGroupExc;
	}

	public void setOriginAirportGroupExc(String originAirportGroupExc) {
		this.originAirportGroupExc = originAirportGroupExc;
	}

	public String getOriginCountryCodeInc() {
		return originCountryCodeInc;
	}

	public void setOriginCountryCodeInc(String originCountryCodeInc) {
		this.originCountryCodeInc = originCountryCodeInc;
	}

	public String getOriginCountryCodeExc() {
		return originCountryCodeExc;
	}

	public void setOriginCountryCodeExc(String originCountryCodeExc) {
		this.originCountryCodeExc = originCountryCodeExc;
	}

	public String getOriginCountryGroupInc() {
		return originCountryGroupInc;
	}

	public void setOriginCountryGroupInc(String originCountryGroupInc) {
		this.originCountryGroupInc = originCountryGroupInc;
	}

	public String getOriginCountryGroupExc() {
		return originCountryGroupExc;
	}

	public void setOriginCountryGroupExc(String originCountryGroupExc) {
		this.originCountryGroupExc = originCountryGroupExc;
	}

	public String getViaPointAirportCodeInc() {
		return viaPointAirportCodeInc;
	}

	public void setViaPointAirportCodeInc(String viaPointAirportCodeInc) {
		this.viaPointAirportCodeInc = viaPointAirportCodeInc;
	}

	public String getViaPointAirportCodeExc() {
		return viaPointAirportCodeExc;
	}

	public void setViaPointAirportCodeExc(String viaPointAirportCodeExc) {
		this.viaPointAirportCodeExc = viaPointAirportCodeExc;
	}

	public String getViaPointAirportGroupInc() {
		return viaPointAirportGroupInc;
	}

	public void setViaPointAirportGroupInc(String viaPointAirportGroupInc) {
		this.viaPointAirportGroupInc = viaPointAirportGroupInc;
	}

	public String getViaPointAirportGroupExc() {
		return viaPointAirportGroupExc;
	}

	public void setViaPointAirportGroupExc(String viaPointAirportGroupExc) {
		this.viaPointAirportGroupExc = viaPointAirportGroupExc;
	}

	public String getViaPointCountryCodeInc() {
		return viaPointCountryCodeInc;
	}

	public void setViaPointCountryCodeInc(String viaPointCountryCodeInc) {
		this.viaPointCountryCodeInc = viaPointCountryCodeInc;
	}

	public String getViaPointCountryCodeExc() {
		return viaPointCountryCodeExc;
	}

	public void setViaPointCountryCodeExc(String viaPointCountryCodeExc) {
		this.viaPointCountryCodeExc = viaPointCountryCodeExc;
	}

	public String getViaPointCountryGroupInc() {
		return viaPointCountryGroupInc;
	}

	public void setViaPointCountryGroupInc(String viaPointCountryGroupInc) {
		this.viaPointCountryGroupInc = viaPointCountryGroupInc;
	}

	public String getViaPointCountryGroupExc() {
		return viaPointCountryGroupExc;
	}

	public void setViaPointCountryGroupExc(String viaPointCountryGroupExc) {
		this.viaPointCountryGroupExc = viaPointCountryGroupExc;
	}

	public String getDestinationAirportCodeInc() {
		return destinationAirportCodeInc;
	}

	public void setDestinationAirportCodeInc(String destinationAirportCodeInc) {
		this.destinationAirportCodeInc = destinationAirportCodeInc;
	}

	public String getDestinationAirportCodeExc() {
		return destinationAirportCodeExc;
	}

	public void setDestinationAirportCodeExc(String destinationAirportCodeExc) {
		this.destinationAirportCodeExc = destinationAirportCodeExc;
	}

	public String getDestinationAirportGroupInc() {
		return destinationAirportGroupInc;
	}

	public void setDestinationAirportGroupInc(String destinationAirportGroupInc) {
		this.destinationAirportGroupInc = destinationAirportGroupInc;
	}

	public String getDestinationAirportGroupExc() {
		return destinationAirportGroupExc;
	}

	public void setDestinationAirportGroupExc(String destinationAirportGroupExc) {
		this.destinationAirportGroupExc = destinationAirportGroupExc;
	}

	public String getDestinationCountryCodeInc() {
		return destinationCountryCodeInc;
	}

	public void setDestinationCountryCodeInc(String destinationCountryCodeInc) {
		this.destinationCountryCodeInc = destinationCountryCodeInc;
	}

	public String getDestinationCountryCodeExc() {
		return destinationCountryCodeExc;
	}

	public void setDestinationCountryCodeExc(String destinationCountryCodeExc) {
		this.destinationCountryCodeExc = destinationCountryCodeExc;
	}

	public String getDestinationCountryGroupInc() {
		return destinationCountryGroupInc;
	}

	public void setDestinationCountryGroupInc(String destinationCountryGroupInc) {
		this.destinationCountryGroupInc = destinationCountryGroupInc;
	}

	public String getDestinationCountryGroupExc() {
		return destinationCountryGroupExc;
	}

	public void setDestinationCountryGroupExc(String destinationCountryGroupExc) {
		this.destinationCountryGroupExc = destinationCountryGroupExc;
	}

	public String getProductInc() {
		return productInc;
	}

	public void setProductInc(String productInc) {
		this.productInc = productInc;
	}

	public String getProductExc() {
		return productExc;
	}

	public void setProductExc(String productExc) {
		this.productExc = productExc;
	}

	public String getSccInc() {
		return sccInc;
	}

	public void setSccInc(String sccInc) {
		this.sccInc = sccInc;
	}

	public String getSccExc() {
		return sccExc;
	}

	public void setSccExc(String sccExc) {
		this.sccExc = sccExc;
	}

	public String getSccGroupInc() {
		return sccGroupInc;
	}

	public void setSccGroupInc(String sccGroupInc) {
		this.sccGroupInc = sccGroupInc;
	}

	public String getSccGroupExc() {
		return sccGroupExc;
	}

	public void setSccGroupExc(String sccGroupExc) {
		this.sccGroupExc = sccGroupExc;
	}

	public String getUnNumberInc() {
		return unNumberInc;
	}

	public void setUnNumberInc(String unNumberInc) {
		this.unNumberInc = unNumberInc;
	}

	public String getUnNumberExc() {
		return unNumberExc;
	}

	public void setUnNumberExc(String unNumberExc) {
		this.unNumberExc = unNumberExc;
	}

	public String getAwbPrefixInc() {
		return awbPrefixInc;
	}

	public void setAwbPrefixInc(String awbPrefixInc) {
		this.awbPrefixInc = awbPrefixInc;
	}

	public String getAwbPrefixExc() {
		return awbPrefixExc;
	}

	public void setAwbPrefixExc(String awbPrefixExc) {
		this.awbPrefixExc = awbPrefixExc;
	}

	public String getAirlineCodeInc() {
		return airlineCodeInc;
	}

	public void setAirlineCodeInc(String airlineCodeInc) {
		this.airlineCodeInc = airlineCodeInc;
	}

	public String getAirlineCodeExc() {
		return airlineCodeExc;
	}

	public void setAirlineCodeExc(String airlineCodeExc) {
		this.airlineCodeExc = airlineCodeExc;
	}

	public String getFlightOwnerInc() {
		return flightOwnerInc;
	}

	public void setFlightOwnerInc(String flightOwnerInc) {
		this.flightOwnerInc = flightOwnerInc;
	}

	public String getFlightOwnerExc() {
		return flightOwnerExc;
	}

	public void setFlightOwnerExc(String flightOwnerExc) {
		this.flightOwnerExc = flightOwnerExc;
	}

	public String getOriginDayOfOperation() {
		return originDayOfOperation;
	}

	public void setOriginDayOfOperation(String originDayOfOperation) {
		this.originDayOfOperation = originDayOfOperation;
	}

	public String getViaPointDayOfOperation() {
		return viaPointDayOfOperation;
	}

	public void setViaPointDayOfOperation(String viaPointDayOfOperation) {
		this.viaPointDayOfOperation = viaPointDayOfOperation;
	}

	public String getDestinationDayOfOperation() {
		return destinationDayOfOperation;
	}

	public void setDestinationDayOfOperation(String destinationDayOfOperation) {
		this.destinationDayOfOperation = destinationDayOfOperation;
	}

	public String getOriginDateInc() {
		return originDateInc;
	}

	public void setOriginDateInc(String originDateInc) {
		this.originDateInc = originDateInc;
	}

	public String getOriginDateExc() {
		return originDateExc;
	}

	public void setOriginDateExc(String originDateExc) {
		this.originDateExc = originDateExc;
	}

	public String getViaPointDateInc() {
		return viaPointDateInc;
	}

	public void setViaPointDateInc(String viaPointDateInc) {
		this.viaPointDateInc = viaPointDateInc;
	}

	public String getViaPointDateExc() {
		return viaPointDateExc;
	}

	public void setViaPointDateExc(String viaPointDateExc) {
		this.viaPointDateExc = viaPointDateExc;
	}

	public String getDestinationDateInc() {
		return destinationDateInc;
	}

	public void setDestinationDateInc(String destinationDateInc) {
		this.destinationDateInc = destinationDateInc;
	}

	public String getDestinationDateExc() {
		return destinationDateExc;
	}

	public void setDestinationDateExc(String destinationDateExc) {
		this.destinationDateExc = destinationDateExc;
	}

	public String getNatureOfGoods() {
		return natureOfGoods;
	}

	public void setNatureOfGoods(String natureOfGoods) {
		this.natureOfGoods = natureOfGoods;
	}

	public String getCommodity() {
		return commodity;
	}

	public void setCommodity(String commodity) {
		this.commodity = commodity;
	}

	public String getFlightNumber() {
		return flightNumber;
	}

	public void setFlightNumber(String flightNumber) {
		this.flightNumber = flightNumber;
	}

	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	public String getFlightType() {
		return flightType;
	}

	public void setFlightType(String flightType) {
		this.flightType = flightType;
	}

	public String getSplitIndicator() {
		return splitIndicator;
	}

	public void setSplitIndicator(String splitIndicator) {
		this.splitIndicator = splitIndicator;
	}

	public String getOriginStartTime() {
		return originStartTime;
	}

	public void setOriginStartTime(String originStartTime) {
		this.originStartTime = originStartTime;
	}

	public String getOriginEndTime() {
		return originEndTime;
	}

	public void setOriginEndTime(String originEndTime) {
		this.originEndTime = originEndTime;
	}

	public String getViaPointStartTime() {
		return viaPointStartTime;
	}

	public void setViaPointStartTime(String viaPointStartTime) {
		this.viaPointStartTime = viaPointStartTime;
	}

	public String getViaPointEndTime() {
		return viaPointEndTime;
	}

	public void setViaPointEndTime(String viaPointEndTime) {
		this.viaPointEndTime = viaPointEndTime;
	}

	public String getDestinationStartTime() {
		return destinationStartTime;
	}

	public void setDestinationStartTime(String destinationStartTime) {
		this.destinationStartTime = destinationStartTime;
	}

	public String getDestinationEndTime() {
		return destinationEndTime;
	}

	public void setDestinationEndTime(String destinationEndTime) {
		this.destinationEndTime = destinationEndTime;
	}

	public String getWeightStart() {
		return weightStart;
	}

	public void setWeightStart(String weightStart) {
		this.weightStart = weightStart;
	}

	public String getWeightEnd() {
		return weightEnd;
	}

	public void setWeightEnd(String weightEnd) {
		this.weightEnd = weightEnd;
	}

	public String getLengthStart() {
		return lengthStart;
	}

	public void setLengthStart(String lengthStart) {
		this.lengthStart = lengthStart;
	}

	public String getLengthEnd() {
		return lengthEnd;
	}

	public void setLengthEnd(String lengthEnd) {
		this.lengthEnd = lengthEnd;
	}

	public String getWidthStart() {
		return widthStart;
	}

	public void setWidthStart(String widthStart) {
		this.widthStart = widthStart;
	}

	public String getWidthEnd() {
		return widthEnd;
	}

	public void setWidthEnd(String widthEnd) {
		this.widthEnd = widthEnd;
	}

	public String getHeightStart() {
		return heightStart;
	}

	public void setHeightStart(String heightStart) {
		this.heightStart = heightStart;
	}

	public String getHeightEnd() {
		return heightEnd;
	}

	public void setHeightEnd(String heightEnd) {
		this.heightEnd = heightEnd;
	}

	public void setFlightTypeDescription(String flightTypeDescription) {
		this.flightTypeDescription = flightTypeDescription;
	}

	public String getFlightTypeDescription() {
		return flightTypeDescription;
	}
	/**
	 * @return the shipmentID
	 */
	public String getShipmentID() {
		return shipmentID;
	}
	/**
	 * @param shipmentID the shipmentID to set
	 */
	public void setShipmentID(String shipmentID) {
		this.shipmentID = shipmentID;
	}
	/**
	 * @return the airlineGroupInc
	 */
	public String getAirlineGroupInc() {
		return airlineGroupInc;
	}
	/**
	 * @param airlineGroupInc the airlineGroupInc to set
	 */
	public void setAirlineGroupInc(String airlineGroupInc) {
		this.airlineGroupInc = airlineGroupInc;
	}
	/**
	 * @return the airlineGroupExc
	 */
	public String getAirlineGroupExc() {
		return airlineGroupExc;
	}
	/**
	 * @param airlineGroupExc the airlineGroupExc to set
	 */
	public void setAirlineGroupExc(String airlineGroupExc) {
		this.airlineGroupExc = airlineGroupExc;
	}
	/**
	 * @return the paymentTypeInc
	 */
	public String getPaymentTypeInc() {
		return paymentTypeInc;
	}
	/**
	 * @param paymentTypeInc the paymentTypeInc to set
	 */
	public void setPaymentTypeInc(String paymentTypeInc) {
		this.paymentTypeInc = paymentTypeInc;
	}
	/**
	 * @return the paymentTypeExc
	 */
	public String getPaymentTypeExc() {
		return paymentTypeExc;
	}
	/**
	 * @param paymentTypeExc the paymentTypeExc to set
	 */
	public void setPaymentTypeExc(String paymentTypeExc) {
		this.paymentTypeExc = paymentTypeExc;
	}
	/**
	 * @return the flightNumberInc
	 */
	public String getFlightNumberInc() {
		return flightNumberInc;
	}
	/**
	 * @param flightNumberInc the flightNumberInc to set
	 */
	public void setFlightNumberInc(String flightNumberInc) {
		this.flightNumberInc = flightNumberInc;
	}
	/**
	 * @return the flightNumberExc
	 */
	public String getFlightNumberExc() {
		return flightNumberExc;
	}
	/**
	 * @param flightNumberExc the flightNumberExc to set
	 */
	public void setFlightNumberExc(String flightNumberExc) {
		this.flightNumberExc = flightNumberExc;
	}
	/**
	 * @return the natureOfGoodsInc
	 */
	public String getNatureOfGoodsInc() {
		return natureOfGoodsInc;
	}
	/**
	 * @param natureOfGoodsInc the natureOfGoodsInc to set
	 */
	public void setNatureOfGoodsInc(String natureOfGoodsInc) {
		this.natureOfGoodsInc = natureOfGoodsInc;
	}
	/**
	 * @return the natureOfGoodsExc
	 */
	public String getNatureOfGoodsExc() {
		return natureOfGoodsExc;
	}
	/**
	 * @param natureOfGoodsExc the natureOfGoodsExc to set
	 */
	public void setNatureOfGoodsExc(String natureOfGoodsExc) {
		this.natureOfGoodsExc = natureOfGoodsExc;
	}
	/**
	 * @return the commodityInc
	 */
	public String getCommodityInc() {
		return commodityInc;
	}
	/**
	 * @param commodityInc the commodityInc to set
	 */
	public void setCommodityInc(String commodityInc) {
		this.commodityInc = commodityInc;
	}
	/**
	 * @return the commodityExc
	 */
	public String getCommodityExc() {
		return commodityExc;
	}
	/**
	 * @param commodityExc the commodityExc to set
	 */
	public void setCommodityExc(String commodityExc) {
		this.commodityExc = commodityExc;
	}
	/**
	 * 
	 * 	Method		:	EmbargoDetailsVO.getPkgInsInc
	 *	Added by 	:	A-7534 on 03-Apr-2018
	 * 	Used for 	:
	 *	Parameters	:	@return 
	 *	Return type	: 	String
	 */
	public String getPkgInsInc() {
		return pkgInsInc;
	}
	/**
	 * 
	 * 	Method		:	EmbargoDetailsVO.setPkgInsInc
	 *	Added by 	:	A-7534 on 03-Apr-2018
	 * 	Used for 	:
	 *	Parameters	:	@param pkgInsInc 
	 *	Return type	: 	void
	 */
	public void setPkgInsInc(String pkgInsInc) {
		this.pkgInsInc = pkgInsInc;
	}
	/**
	 * 
	 * 	Method		:	EmbargoDetailsVO.getPkgInsExc
	 *	Added by 	:	A-7534 on 03-Apr-2018
	 * 	Used for 	:
	 *	Parameters	:	@return 
	 *	Return type	: 	String
	 */
	public String getPkgInsExc() {
		return pkgInsExc;
	}
	/**
	 * 
	 * 	Method		:	EmbargoDetailsVO.setPkgInsExc
	 *	Added by 	:	A-7534 on 03-Apr-2018
	 * 	Used for 	:
	 *	Parameters	:	@param pkgInsExc 
	 *	Return type	: 	void
	 */
	public void setPkgInsExc(String pkgInsExc) {
		this.pkgInsExc = pkgInsExc;
	}
	public String getUnWeightStart() {
		return unWeightStart;
	}
	public void setUnWeightStart(String unWeightStart) {
		this.unWeightStart = unWeightStart;
	}
	public String getUnWeightEnd() {
		return unWeightEnd;
	}
	public void setUnWeightEnd(String unWeightEnd) {
		this.unWeightEnd = unWeightEnd;
	}
	public String getDvForCustomsStart() {
		return dvForCustomsStart;
	}
	public void setDvForCustomsStart(String dvForCustomsStart) {
		this.dvForCustomsStart = dvForCustomsStart;
	}
	public String getDvForCustomsEnd() {
		return dvForCustomsEnd;
	}
	public void setDvForCustomsEnd(String dvForCustomsEnd) {
		this.dvForCustomsEnd = dvForCustomsEnd;
	}
	public String getDvForCarriageStart() {
		return dvForCarriageStart;
	}
	public void setDvForCarriageStart(String dvForCarriageStart) {
		this.dvForCarriageStart = dvForCarriageStart;
	}
	public String getDvForCarriageEnd() {
		return dvForCarriageEnd;
	}
	public void setDvForCarriageEnd(String dvForCarriageEnd) {
		this.dvForCarriageEnd = dvForCarriageEnd;
	}
	public String getAgtCodeInc() {
		return agtCodeInc;
	}
	public void setAgtCodeInc(String agtCodeInc) {
		this.agtCodeInc = agtCodeInc;
	}
	public String getAgtCodeExc() {
		return agtCodeExc;
	}
	public void setAgtCodeExc(String agtCodeExc) {
		this.agtCodeExc = agtCodeExc;
	}
	public String getAgtGroupInc() {
		return agtGroupInc;
	}
	public void setAgtGroupInc(String agtGroupInc) {
		this.agtGroupInc = agtGroupInc;
	}
	public String getAgtGroupExc() {
		return agtGroupExc;
	}
	public void setAgtGroupExc(String agtGroupExc) {
		this.agtGroupExc = agtGroupExc;
	}
	/**
	 * 	Getter for uldInc 
	 *	Added by : A-8146 on 04-Aug-2019
	 * 	Used for :
	 */
	public String getUldInc() {
		return uldInc;
	}
	/**
	 *  @param uldInc the uldInc to set
	 * 	Setter for uldInc 
	 *	Added by : A-8146 on 04-Aug-2019
	 * 	Used for :
	 */
	public void setUldInc(String uldInc) {
		this.uldInc = uldInc;
	}
	/**
	 * 	Getter for uldExc 
	 *	Added by : A-8146 on 04-Aug-2019
	 * 	Used for :
	 */
	public String getUldExc() {
		return uldExc;
	}
	/**
	 *  @param uldExc the uldExc to set
	 * 	Setter for uldExc 
	 *	Added by : A-8146 on 04-Aug-2019
	 * 	Used for :
	 */
	public void setUldExc(String uldExc) {
		this.uldExc = uldExc;
	}
	/**
	 * 	Getter for volumeStart 
	 *	Added by : A-8146 on 04-Aug-2019
	 * 	Used for :
	 */
	public String getVolumeStart() {
		return volumeStart;
	}
	/**
	 *  @param volumeStart the volumeStart to set
	 * 	Setter for volumeStart 
	 *	Added by : A-8146 on 04-Aug-2019
	 * 	Used for :
	 */
	public void setVolumeStart(String volumeStart) {
		this.volumeStart = volumeStart;
	}
	/**
	 * 	Getter for volumeEnd 
	 *	Added by : A-8146 on 04-Aug-2019
	 * 	Used for :
	 */
	public String getVolumeEnd() {
		return volumeEnd;
	}
	/**
	 *  @param volumeEnd the volumeEnd to set
	 * 	Setter for volumeEnd 
	 *	Added by : A-8146 on 04-Aug-2019
	 * 	Used for :
	 */
	public void setVolumeEnd(String volumeEnd) {
		this.volumeEnd = volumeEnd;
	}
	/**
	 * 	Getter for aircraftTypeOrgInc 
	 *	Added by : A-8146 on 04-Aug-2019
	 * 	Used for :
	 */
	public String getAircraftTypeOrgInc() {
		return aircraftTypeOrgInc;
	}
	/**
	 *  @param aircraftTypeOrgInc the aircraftTypeOrgInc to set
	 * 	Setter for aircraftTypeOrgInc 
	 *	Added by : A-8146 on 04-Aug-2019
	 * 	Used for :
	 */
	public void setAircraftTypeOrgInc(String aircraftTypeOrgInc) {
		this.aircraftTypeOrgInc = aircraftTypeOrgInc;
	}
	/**
	 * 	Getter for aircraftTypeDstInc 
	 *	Added by : A-8146 on 04-Aug-2019
	 * 	Used for :
	 */
	public String getAircraftTypeDstInc() {
		return aircraftTypeDstInc;
	}
	/**
	 *  @param aircraftTypeDstInc the aircraftTypeDstInc to set
	 * 	Setter for aircraftTypeDstInc 
	 *	Added by : A-8146 on 04-Aug-2019
	 * 	Used for :
	 */
	public void setAircraftTypeDstInc(String aircraftTypeDstInc) {
		this.aircraftTypeDstInc = aircraftTypeDstInc;
	}
	/**
	 * 	Getter for aircraftTypeViaInc 
	 *	Added by : A-8146 on 04-Aug-2019
	 * 	Used for :
	 */
	public String getAircraftTypeViaInc() {
		return aircraftTypeViaInc;
	}
	/**
	 *  @param aircraftTypeViaInc the aircraftTypeViaInc to set
	 * 	Setter for aircraftTypeViaInc 
	 *	Added by : A-8146 on 04-Aug-2019
	 * 	Used for :
	 */
	public void setAircraftTypeViaInc(String aircraftTypeViaInc) {
		this.aircraftTypeViaInc = aircraftTypeViaInc;
	}
	/**
	 * 	Getter for aircraftTypeGrpOrgInc 
	 *	Added by : A-8146 on 04-Aug-2019
	 * 	Used for :
	 */
	public String getAircraftTypeGrpOrgInc() {
		return aircraftTypeGrpOrgInc;
	}
	/**
	 *  @param aircraftTypeGrpOrgInc the aircraftTypeGrpOrgInc to set
	 * 	Setter for aircraftTypeGrpOrgInc 
	 *	Added by : A-8146 on 04-Aug-2019
	 * 	Used for :
	 */
	public void setAircraftTypeGrpOrgInc(String aircraftTypeGrpOrgInc) {
		this.aircraftTypeGrpOrgInc = aircraftTypeGrpOrgInc;
	}
	/**
	 * 	Getter for aircraftTypeGrpDstInc 
	 *	Added by : A-8146 on 04-Aug-2019
	 * 	Used for :
	 */
	public String getAircraftTypeGrpDstInc() {
		return aircraftTypeGrpDstInc;
	}
	/**
	 *  @param aircraftTypeGrpDstInc the aircraftTypeGrpDstInc to set
	 * 	Setter for aircraftTypeGrpDstInc 
	 *	Added by : A-8146 on 04-Aug-2019
	 * 	Used for :
	 */
	public void setAircraftTypeGrpDstInc(String aircraftTypeGrpDstInc) {
		this.aircraftTypeGrpDstInc = aircraftTypeGrpDstInc;
	}
	/**
	 * 	Getter for aircraftTypeGrpViaInc 
	 *	Added by : A-8146 on 04-Aug-2019
	 * 	Used for :
	 */
	public String getAircraftTypeGrpViaInc() {
		return aircraftTypeGrpViaInc;
	}
	/**
	 *  @param aircraftTypeGrpViaInc the aircraftTypeGrpViaInc to set
	 * 	Setter for aircraftTypeGrpViaInc 
	 *	Added by : A-8146 on 04-Aug-2019
	 * 	Used for :
	 */
	public void setAircraftTypeGrpViaInc(String aircraftTypeGrpViaInc) {
		this.aircraftTypeGrpViaInc = aircraftTypeGrpViaInc;
	}
	/**
	 * 	Getter for aircraftTypeOrgExc 
	 *	Added by : A-8146 on 04-Aug-2019
	 * 	Used for :
	 */
	public String getAircraftTypeOrgExc() {
		return aircraftTypeOrgExc;
	}
	/**
	 *  @param aircraftTypeOrgExc the aircraftTypeOrgExc to set
	 * 	Setter for aircraftTypeOrgExc 
	 *	Added by : A-8146 on 04-Aug-2019
	 * 	Used for :
	 */
	public void setAircraftTypeOrgExc(String aircraftTypeOrgExc) {
		this.aircraftTypeOrgExc = aircraftTypeOrgExc;
	}
	/**
	 * 	Getter for aircraftTypeDstExc 
	 *	Added by : A-8146 on 04-Aug-2019
	 * 	Used for :
	 */
	public String getAircraftTypeDstExc() {
		return aircraftTypeDstExc;
	}
	/**
	 *  @param aircraftTypeDstExc the aircraftTypeDstExc to set
	 * 	Setter for aircraftTypeDstExc 
	 *	Added by : A-8146 on 04-Aug-2019
	 * 	Used for :
	 */
	public void setAircraftTypeDstExc(String aircraftTypeDstExc) {
		this.aircraftTypeDstExc = aircraftTypeDstExc;
	}
	/**
	 * 	Getter for aircraftTypeViaExc 
	 *	Added by : A-8146 on 04-Aug-2019
	 * 	Used for :
	 */
	public String getAircraftTypeViaExc() {
		return aircraftTypeViaExc;
	}
	/**
	 *  @param aircraftTypeViaExc the aircraftTypeViaExc to set
	 * 	Setter for aircraftTypeViaExc 
	 *	Added by : A-8146 on 04-Aug-2019
	 * 	Used for :
	 */
	public void setAircraftTypeViaExc(String aircraftTypeViaExc) {
		this.aircraftTypeViaExc = aircraftTypeViaExc;
	}
	/**
	 * 	Getter for aircraftTypeGrpOrgExc 
	 *	Added by : A-8146 on 04-Aug-2019
	 * 	Used for :
	 */
	public String getAircraftTypeGrpOrgExc() {
		return aircraftTypeGrpOrgExc;
	}
	/**
	 *  @param aircraftTypeGrpOrgExc the aircraftTypeGrpOrgExc to set
	 * 	Setter for aircraftTypeGrpOrgExc 
	 *	Added by : A-8146 on 04-Aug-2019
	 * 	Used for :
	 */
	public void setAircraftTypeGrpOrgExc(String aircraftTypeGrpOrgExc) {
		this.aircraftTypeGrpOrgExc = aircraftTypeGrpOrgExc;
	}
	/**
	 * 	Getter for aircraftTypeGrpDstExc 
	 *	Added by : A-8146 on 04-Aug-2019
	 * 	Used for :
	 */
	public String getAircraftTypeGrpDstExc() {
		return aircraftTypeGrpDstExc;
	}
	/**
	 *  @param aircraftTypeGrpDstExc the aircraftTypeGrpDstExc to set
	 * 	Setter for aircraftTypeGrpDstExc 
	 *	Added by : A-8146 on 04-Aug-2019
	 * 	Used for :
	 */
	public void setAircraftTypeGrpDstExc(String aircraftTypeGrpDstExc) {
		this.aircraftTypeGrpDstExc = aircraftTypeGrpDstExc;
	}
	/**
	 * 	Getter for aircraftTypeGrpViaExc 
	 *	Added by : A-8146 on 04-Aug-2019
	 * 	Used for :
	 */
	public String getAircraftTypeGrpViaExc() {
		return aircraftTypeGrpViaExc;
	}
	/**
	 *  @param aircraftTypeGrpViaExc the aircraftTypeGrpViaExc to set
	 * 	Setter for aircraftTypeGrpViaExc 
	 *	Added by : A-8146 on 04-Aug-2019
	 * 	Used for :
	 */
	public void setAircraftTypeGrpViaExc(String aircraftTypeGrpViaExc) {
		this.aircraftTypeGrpViaExc = aircraftTypeGrpViaExc;
	}
	public String getServiceCargoClassInc() {
		return serviceCargoClassInc;
	}
	public void setServiceCargoClassInc(String serviceCargoClassInc) {
		this.serviceCargoClassInc = serviceCargoClassInc;
	}
	public String getServiceCargoClassExc() {
		return serviceCargoClassExc;
	}
	public void setServiceCargoClassExc(String serviceCargoClassExc) {
		this.serviceCargoClassExc = serviceCargoClassExc;
	}
	public String getAircraftClassOriginInc() {
		return aircraftClassOriginInc;
	}
	public void setAircraftClassOriginInc(String aircraftClassOriginInc) {
		this.aircraftClassOriginInc = aircraftClassOriginInc;
	}
	public String getAircraftClassDestInc() {
		return aircraftClassDestInc;
	}
	public void setAircraftClassDestInc(String aircraftClassDestInc) {
		this.aircraftClassDestInc = aircraftClassDestInc;
	}
	public String getAircraftClassViaInc() {
		return aircraftClassViaInc;
	}
	public void setAircraftClassViaInc(String aircraftClassViaInc) {
		this.aircraftClassViaInc = aircraftClassViaInc;
	}
	public String getAircraftClassOriginExc() {
		return aircraftClassOriginExc;
	}
	public void setAircraftClassOriginExc(String aircraftClassOriginExc) {
		this.aircraftClassOriginExc = aircraftClassOriginExc;
	}
	public String getAircraftClassDestExc() {
		return aircraftClassDestExc;
	}
	public void setAircraftClassDestExc(String aircraftClassDestExc) {
		this.aircraftClassDestExc = aircraftClassDestExc;
	}
	public String getAircraftClassViaExc() {
		return aircraftClassViaExc;
	}
	public void setAircraftClassViaExc(String aircraftClassViaExc) {
		this.aircraftClassViaExc = aircraftClassViaExc;
	}
	public String getShipperCodeInc() {
		return shipperCodeInc;
	}
	public void setShipperCodeInc(String shipperCodeInc) {
		this.shipperCodeInc = shipperCodeInc;
	}
	public String getShipperGroupInc() {
		return shipperGroupInc;
	}
	public void setShipperGroupInc(String shipperGroupInc) {
		this.shipperGroupInc = shipperGroupInc;
	}
	public String getConsigneeCodeInc() {
		return consigneeCodeInc;
	}
	public void setConsigneeCodeInc(String consigneeCodeInc) {
		this.consigneeCodeInc = consigneeCodeInc;
	}
	public String getConsigneeGroupInc() {
		return consigneeGroupInc;
	}
	public void setConsigneeGroupInc(String consigneeGroupInc) {
		this.consigneeGroupInc = consigneeGroupInc;
	}
	public String getShipmentTypeInc() {
		return shipmentTypeInc;
	}
	public void setShipmentTypeInc(String shipmentTypeInc) {
		this.shipmentTypeInc = shipmentTypeInc;
	}
	public String getConsolInc() {
		return consolInc;
	}
	public void setConsolInc(String consolInc) {
		this.consolInc = consolInc;
	}
	public String getShipperCodeExc() {
		return shipperCodeExc;
	}
	public void setShipperCodeExc(String shipperCodeExc) {
		this.shipperCodeExc = shipperCodeExc;
	}
	public String getShipperGroupExc() {
		return shipperGroupExc;
	}
	public void setShipperGroupExc(String shipperGroupExc) {
		this.shipperGroupExc = shipperGroupExc;
	}
	public String getConsigneeCodeExc() {
		return consigneeCodeExc;
	}
	public void setConsigneeCodeExc(String consigneeCodeExc) {
		this.consigneeCodeExc = consigneeCodeExc;
	}
	public String getConsigneeGroupExc() {
		return consigneeGroupExc;
	}
	public void setConsigneeGroupExc(String consigneeGroupExc) {
		this.consigneeGroupExc = consigneeGroupExc;
	}
	public String getShipmentTypeExc() {
		return shipmentTypeExc;
	}
	public void setShipmentTypeExc(String shipmentTypeExc) {
		this.shipmentTypeExc = shipmentTypeExc;
	}
	public String getConsolExc() {
		return consolExc;
	}
	public void setConsolExc(String consolExc) {
		this.consolExc = consolExc;
	}
	public String getServiceTypeInc() {
		return serviceTypeInc;
	}
	public void setServiceTypeInc(String serviceTypeInc) {
		this.serviceTypeInc = serviceTypeInc;
	}
	public String getServiceTypeExc() {
		return serviceTypeExc;
	}
	public void setServiceTypeExc(String serviceTypeExc) {
		this.serviceTypeExc = serviceTypeExc;
	}
	public String getPackagingGroupInc() {
		return packagingGroupInc;
	}
	public void setPackagingGroupInc(String packagingGroupInc) {
		this.packagingGroupInc = packagingGroupInc;
	}
	public String getPackagingGroupExc() {
		return packagingGroupExc;
	}
	public void setPackagingGroupExc(String packagingGroupExc) {
		this.packagingGroupExc = packagingGroupExc;
	}
	public String getSubRiskInc() {
		return subRiskInc;
	}
	public void setSubRiskInc(String subRiskInc) {
		this.subRiskInc = subRiskInc;
	}
	public String getSubRiskExc() {
		return subRiskExc;
	}
	public void setSubRiskExc(String subRiskExc) {
		this.subRiskExc = subRiskExc;
	}
	//added by 202766
	public String getFlightTypeInc() {
		return flightTypeInc;
	}
	public void setFlightTypeInc(String flightTypeInc) {
		this.flightTypeInc = flightTypeInc;
	}
	public String getFlightTypeExc() {
		return flightTypeExc;
	}
	public void setFlightTypeExc(String flightTypeExc) {
		this.flightTypeExc = flightTypeExc;
	}
	
}
