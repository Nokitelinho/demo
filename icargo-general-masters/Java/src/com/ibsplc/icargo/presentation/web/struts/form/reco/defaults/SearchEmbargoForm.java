package com.ibsplc.icargo.presentation.web.struts.form.reco.defaults;

/*
 * SearchEmbargoForm.java Created on May 13, 2014
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */


import com.ibsplc.icargo.framework.model.ScreenModel;
/**
 * @author A-
 *
 */

public class SearchEmbargoForm extends ScreenModel {



	private static final String BUNDLE="searchembargoresources";
	
	private static final String PRODUCT = "reco";
	
	private static final String SUBPRODUCT = "defaults";
	
	private static final String SCREENID = "reco.defaults.searchembargo";
	
	public static final String PAGINATION_MODE_FROM_FILTER="FILTER";
	
	public static final String PAGINATION_MODE_FROM_NAVIGATION="NAVIGATION";
	
	private String geographicLevelType;
	private String geographicLevel;
	private String applicableTransaction;
	private String embargoDate;
	private String fromDate;
	private String toDate;
	private String originType;
	private String origin;
	private String destinationType;
	private String destination;
	private String viaPointType;
	private String viaPoint;
	private String ruleType;
	private String levelCode;
	private String parameterCode;
	private String parameterValue;
	private String category;
	private String leftPanelKey;
	private String leftPanelValue ;
	private String dayOfOperationApplicableOn;
	
	private String awbPrefix;
	private String carrier;
	private String commodity;
	private String dates;
	private String flightNumber;
	private String carrierCode;
	private String flightOwner;
	private String flightType;
	private String uldPos;
	private String uldTyp;
	private String natureOfGoods;
	private String paymentType;
	private String productCode;
	private String scc;
	private String sccGroup;
	private String splitIndicator;
	private String time;
	private String unNumber;
	private String packingInstruction;
	private String weight;
	private String parameterLength;
	private String width;
	private String height;
	private String airlineGroup;
	private String defaultText;
	
	private String closeBtnFlag;
	private String simpleSearch="1";
	private String navigationMode;
	private String lastPageNum = "0";
	private String displayPage = "1";
	private String groupName;
	private String groupType;
	private String unWeight;
	private String dvForCustoms;
	private String dvForCarriage;
    private String unIds; //Added for ICRD-254555
	
    private String volume;
    private String uldType;
    private String aircraftGroupType;
    private String aircraftType;
    
    //added by A-5799 for IASCB-23507 Starts
  	private String serviceCargoClass;
  	private String aircraftClassification;
  	private String shipperCode;
  	private String shipperGroup;
  	private String consigneeCode;
  	private String consigneeGroup;
  	private String shipmentType;
  	private String consol;
  	//added by A-5799 for IASCB-23507 ends
    
    private String numberOfStops;
    
	//Added by A-6841 for ICRD-241957 
	private String sourceScreen;
	
	public String getSourceScreen() {
		return sourceScreen;
	}
	public void setSourceScreen(String sourceScreen) {
		this.sourceScreen = sourceScreen;
	}
	public String getBundle() {
		return BUNDLE;
	}
	public String getScreenId() {
		return SCREENID;
	}

	public String getProduct() {
		return PRODUCT;
	}
	public String getSubProduct() {
		return SUBPRODUCT;
	}
	
	public String getGeographicLevelType() {
		return geographicLevelType;
	}
	public void setGeographicLevelType(String geographicLevelType) {
		this.geographicLevelType = geographicLevelType;
	}
	public String getGeographicLevel() {
		return geographicLevel;
	}
	public void setGeographicLevel(String geographicLevel) {
		this.geographicLevel = geographicLevel;
	}
	public String getApplicableTransaction() {
		return applicableTransaction;
	}
	public void setApplicableTransaction(String applicableTransaction) {
		this.applicableTransaction = applicableTransaction;
	}
	public String getEmbargoDate() {
		return embargoDate;
	}
	public void setEmbargoDate(String embargoDate) {
		this.embargoDate = embargoDate;
	}
	public String getFromDate() {
		return fromDate;
	}
	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}
	public String getToDate() {
		return toDate;
	}
	public void setToDate(String toDate) {
		this.toDate = toDate;
	}
	public String getOriginType() {
		return originType;
	}
	public void setOriginType(String originType) {
		this.originType = originType;
	}
	public String getOrigin() {
		return origin;
	}
	public void setOrigin(String origin) {
		this.origin = origin;
	}
	public String getDestinationType() {
		return destinationType;
	}
	public void setDestinationType(String destinationType) {
		this.destinationType = destinationType;
	}
	public String getDestination() {
		return destination;
	}
	public void setDestination(String destination) {
		this.destination = destination;
	}
	public String getViaPointType() {
		return viaPointType;
	}
	public void setViaPointType(String viaPointType) {
		this.viaPointType = viaPointType;
	}
	public String getViaPoint() {
		return viaPoint;
	}
	public void setViaPoint(String viaPoint) {
		this.viaPoint = viaPoint;
	}
	
	public String getRuleType() {
		return ruleType;
	}
	public void setRuleType(String ruleType) {
		this.ruleType = ruleType;
	}
	public String getLevelCode() {
		return levelCode;
	}
	public void setLevelCode(String levelCode) {
		this.levelCode = levelCode;
	}
	public String getParameterCode() {
		return parameterCode;
	}
	public void setParameterCode(String parameterCode) {
		this.parameterCode = parameterCode;
	}
	public String getParameterValue() {
		return parameterValue;
	}
	public void setParameterValue(String parameterValue) {
		this.parameterValue = parameterValue;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	
	public String getSimpleSearch() {
		return simpleSearch;
	}
	public void setSimpleSearch(String simpleSearch) {
		this.simpleSearch = simpleSearch;
	}
	public String getNavigationMode() {
		return navigationMode;
	}
	public void setNavigationMode(String navigationMode) {
		this.navigationMode = navigationMode;
	}
	public String getDisplayPage() {
		return displayPage;
	}
	public void setDisplayPage(String displayPage) {
		this.displayPage = displayPage;
	}
	public void setLastPageNum(String lastPageNum) {
		this.lastPageNum = lastPageNum;
	}
	public String getLastPageNum() {
		return lastPageNum;
	}
	public String getLeftPanelKey() {
		return leftPanelKey;
	}
	public void setLeftPanelKey(String leftPanelKey) {
		this.leftPanelKey = leftPanelKey;
	}
	public String getLeftPanelValue() {
		return leftPanelValue;
	}
	public void setLeftPanelValue(String leftPanelValue) {
		this.leftPanelValue = leftPanelValue;
	}
	public String getDayOfOperationApplicableOn() {
		return dayOfOperationApplicableOn;
	}
	public void setDayOfOperationApplicableOn(String dayOfOperationApplicableOn) {
		this.dayOfOperationApplicableOn = dayOfOperationApplicableOn;
	}
	public void setCloseBtnFlag(String closeBtnFlag) {
		this.closeBtnFlag = closeBtnFlag;
	}
	public String getCloseBtnFlag() {
		return closeBtnFlag;
	}
	public String getAwbPrefix() {
		return awbPrefix;
	}
	public void setAwbPrefix(String awbPrefix) {
		this.awbPrefix = awbPrefix;
	}
	public String getCarrier() {
		return carrier;
	}
	public void setCarrier(String carrier) {
		this.carrier = carrier;
	}
	public String getCommodity() {
		return commodity;
	}
	public void setCommodity(String commodity) {
		this.commodity = commodity;
	}
	public String getDates() {
		return dates;
	}
	public void setDates(String dates) {
		this.dates = dates;
	}
	public String getFlightNumber() {
		return flightNumber;
	}
	public void setFlightNumber(String flightNumber) {
		this.flightNumber = flightNumber;
	}
	public String getFlightOwner() {
		return flightOwner;
	}
	public void setFlightOwner(String flightOwner) {
		this.flightOwner = flightOwner;
	}
	public String getFlightType() {
		return flightType;
	}
	public void setFlightType(String flightType) {
		this.flightType = flightType;
	}
	public String getNatureOfGoods() {
		return natureOfGoods;
	}
	public void setNatureOfGoods(String natureOfGoods) {
		this.natureOfGoods = natureOfGoods;
	}
	public String getPaymentType() {
		return paymentType;
	}
	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}
	public String getProductCode() {
		return productCode;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	public String getScc() {
		return scc;
	}
	public void setScc(String scc) {
		this.scc = scc;
	}
	public String getSccGroup() {
		return sccGroup;
	}
	public void setSccGroup(String sccGroup) {
		this.sccGroup = sccGroup;
	}
	public String getSplitIndicator() {
		return splitIndicator;
	}
	public void setSplitIndicator(String splitIndicator) {
		this.splitIndicator = splitIndicator;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getUnNumber() {
		return unNumber;
	}
	public void setUnNumber(String unNumber) {
		this.unNumber = unNumber;
	}
	public String getWeight() {
		return weight;
	}
	public void setWeight(String weight) {
		this.weight = weight;
	}
	public void setDefaultText(String defaultText) {
		this.defaultText = defaultText;
	}
	public String getDefaultText() {
		return defaultText;
	}
	
	public String getParameterLength() {
		return parameterLength;
	}
	public void setParameterLength(String parameterLength) {
		this.parameterLength = parameterLength;
	}
	public String getWidth() {
		return width;
	}
	public void setWidth(String width) {
		this.width = width;
	}
	public String getHeight() {
		return height;
	}
	public void setHeight(String height) {
		this.height = height;
	}
	public String getCarrierCode() {
		return carrierCode;
	}
	public void setCarrierCode(String carrierCode) {
		this.carrierCode = carrierCode;
	}
	public String getAirlineGroup() {
		return airlineGroup;
	}
	public void setAirlineGroup(String airlineGroup) {
		this.airlineGroup = airlineGroup;
	}
	/**
	 * 
	 * 	Method		:	SearchEmbargoForm.getPackingInstruction
	 *	Added by 	:	A-7534 on 27-Mar-2018
	 * 	Used for 	:
	 *	Parameters	:	@return 
	 *	Return type	: 	String
	 */
	public String getPackingInstruction() {
		return packingInstruction;
	}
	/**
	 * 
	 * 	Method		:	SearchEmbargoForm.setPackingInstruction
	 *	Added by 	:	A-7534 on 27-Mar-2018
	 * 	Used for 	:
	 *	Parameters	:	@param packingInstruction 
	 *	Return type	: 	void
	 */
	public void setPackingInstruction(String packingInstruction) {
		this.packingInstruction = packingInstruction;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public String getGroupType() {
		return groupType;
	}
	public void setGroupType(String groupType) {
		this.groupType = groupType;
	}
	public String getUnWeight() {
		return unWeight;
	}
	public void setUnWeight(String unWeight) {
		this.unWeight = unWeight;
	}
	public String getDvForCustoms() {
		return dvForCustoms;
	}
	public void setDvForCustoms(String dvForCustoms) {
		this.dvForCustoms = dvForCustoms;
	}
	public String getDvForCarriage() {
		return dvForCarriage;
	}
	public void setDvForCarriage(String dvForCarriage) {
		this.dvForCarriage = dvForCarriage;
	}
	/**
	 * Field 	: agentCode	of type : String
	 * Used for :
	 */
	private String agentCode;
	/**
	 * Field 	: agentGroup	of type : String
	 * Used for :
	 */
	private String agentGroup;
	/**
	 * 	Method		:	SearchEmbargoForm.getAgentCode
	 *	Added by 	:	A-8041 on 26-Sep-2018
	 * 	Used for 	:
	 *	Parameters	:	@return 
	 *	Return type	: 	String
	 */
	public String getAgentCode() {
		return agentCode;
	}
	/**
	 * 	Method		:	SearchEmbargoForm.setAgentCode
	 *	Added by 	:	A-8041 on 26-Sep-2018
	 * 	Used for 	:
	 *	Parameters	:	@param agentCode 
	 *	Return type	: 	void
	 */
	public void setAgentCode(String agentCode) {
		this.agentCode = agentCode;
	}
	/**
	 * 	Method		:	SearchEmbargoForm.getAgentGroup
	 *	Added by 	:	A-8041 on 26-Sep-2018
	 * 	Used for 	:
	 *	Parameters	:	@return 
	 *	Return type	: 	String
	 */
	public String getAgentGroup() {
		return agentGroup;
	}
	/**
	 * 	Method		:	SearchEmbargoForm.setAgentGroup
	 *	Added by 	:	A-8041 on 26-Sep-2018
	 * 	Used for 	:
	 *	Parameters	:	@param agentGroup 
	 *	Return type	: 	void
	 */
	public void setAgentGroup(String agentGroup) {
		this.agentGroup = agentGroup;
	}
	
	/**
	 * @return the UNIDs
	 */
	public String getUnIds() {
		return unIds;
	}
	/**
	 * @param UNIDs set
	 */
	public void setUnIds(String unIds) {
		this.unIds = unIds;
	}
	/**
	 * 	Getter for volume 
	 *	Added by : A-8146 on 22-Aug-2019
	 * 	Used for :
	 */
	public String getVolume() {
		return volume;
	}
	/**
	 *  @param volume the volume to set
	 * 	Setter for volume 
	 *	Added by : A-8146 on 22-Aug-2019
	 * 	Used for :
	 */
	public void setVolume(String volume) {
		this.volume = volume;
	}
	/**
	 * 	Getter for uldType 
	 *	Added by : A-8146 on 22-Aug-2019
	 * 	Used for :
	 */
	public String getUldType() {
		return uldType;
	}
	/**
	 *  @param uldType the uldType to set
	 * 	Setter for uldType 
	 *	Added by : A-8146 on 22-Aug-2019
	 * 	Used for :
	 */
	public void setUldType(String uldType) {
		this.uldType = uldType;
	}
	/**
	 * 	Getter for aircraftGroupType 
	 *	Added by : A-8146 on 22-Aug-2019
	 * 	Used for :
	 */
	public String getAircraftGroupType() {
		return aircraftGroupType;
	}
	/**
	 *  @param aircraftGroupType the aircraftGroupType to set
	 * 	Setter for aircraftGroupType 
	 *	Added by : A-8146 on 22-Aug-2019
	 * 	Used for :
	 */
	public void setAircraftGroupType(String aircraftGroupType) {
		this.aircraftGroupType = aircraftGroupType;
	}
	/**
	 * 	Getter for aircraftType 
	 *	Added by : A-8146 on 22-Aug-2019
	 * 	Used for :
	 */
	public String getAircraftType() {
		return aircraftType;
	}
	/**
	 *  @param aircraftType the aircraftType to set
	 * 	Setter for aircraftType 
	 *	Added by : A-8146 on 22-Aug-2019
	 * 	Used for :
	 */
	public void setAircraftType(String aircraftType) {
		this.aircraftType = aircraftType;
	}
		
	public String getUldPos() {
		return uldPos;
	}
	public void setUldPos(String uldPos) {
		this.uldPos = uldPos;
	}
	public String getUldTyp() {
		return uldTyp;
	}
	public void setUldTyp(String uldTyp) {
		this.uldTyp = uldTyp;
	}
	
	/**
	 * @return the serviceCargoClass
	 */
	public String getServiceCargoClass() {
		return serviceCargoClass;
	}
	/**
	 * @param serviceCargoClass the serviceCargoClass to set
	 */
	public void setServiceCargoClass(String serviceCargoClass) {
		this.serviceCargoClass = serviceCargoClass;
	}
	
	/**
	 * @return the aircraftClassification
	 */
	public String getAircraftClassification() {
		return aircraftClassification;
	}
	
	/**
	 * @param aircraftClassification the aircraftClassification to set
	 */
	public void setAircraftClassification(String aircraftClassification) {
		this.aircraftClassification = aircraftClassification;
	}
	
	
	/**
	 * @return the shipperCode
	 */
	public String getShipperCode() {
		return shipperCode;
	}
	
	/**
	 * @param shipperCode the shipperCode to set
	 */
	public void setShipperCode(String shipperCode) {
		this.shipperCode = shipperCode;
	}
	
	/**
	 * @return the shipperGroup
	 */
	public String getShipperGroup() {
		return shipperGroup;
	}
	
	/**
	 * @param shipperGroup the shipperGroup to set
	 */
	public void setShipperGroup(String shipperGroup) {
		this.shipperGroup = shipperGroup;
	}
	
	/**
	 * @return the consigneeCode
	 */
	public String getConsigneeCode() {
		return consigneeCode;
	}
	
	/**
	 * @param consigneeCode the consigneeCode to set
	 */
	public void setConsigneeCode(String consigneeCode) {
		this.consigneeCode = consigneeCode;
	}
	
	/**
	 * @return the consigneeGroup
	 */
	public String getConsigneeGroup() {
		return consigneeGroup;
	}
	
	/**
	 * @param consigneeGroup the consigneeGroup to set
	 */
	public void setConsigneeGroup(String consigneeGroup) {
		this.consigneeGroup = consigneeGroup;
	}
	
	/**
	 * @return the shipmentType
	 */
	public String getShipmentType() {
		return shipmentType;
	}
	/**
	 * @param shipmentType the shipmentType to set
	 */
	public void setShipmentType(String shipmentType) {
		this.shipmentType = shipmentType;
	}
	/**
	 * @return the consol
	 */
	public String getConsol() {
		return consol;
	}
	/**
	 * @param consol the consol to set
	 */
	public void setConsol(String consol) {
		this.consol = consol;
	}
	
	
	/**
	 * A-8146@BLR 25-09-2019
	 * @return the numberOfStops
	 */
	public String getNumberOfStops() {
		return numberOfStops;
	}
	/**
	 * A-8146@BLR 25-09-2019
	 * @param numberOfStops the numberOfStops to set
	 */
	public void setNumberOfStops(String numberOfStops) {
		this.numberOfStops = numberOfStops;
	}
}
