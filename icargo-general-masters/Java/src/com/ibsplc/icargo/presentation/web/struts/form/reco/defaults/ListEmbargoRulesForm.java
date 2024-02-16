/*
 * ListEmbargoRulesForm.java Created on Jun 20, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.struts.form.reco.defaults;

import com.ibsplc.icargo.framework.model.ScreenModel;
/**
 * @author A-1747
 * Form class for ListEmbargo
 *
 */
public class ListEmbargoRulesForm extends ScreenModel{

//	 The key attribute specified in struts_config.xml file.

	private static final String BUNDLE
				= "embargorulesresources";
	//added by A-5175 for  icrd-21634 starts
	public static final String PAGINATION_MODE_FROM_FILTER="FILTER";
	
	public static final String PAGINATION_MODE_FROM_NAVIGATION="NAVIGATION";
	//ends

	private String bundle;

	private String refNumber="";
	private String level="";
	private String status="";
	private String startDate="";
	private String endDate="";
	private String enabled;
	private String[] parameter;
	private String[] applicable;
	private String[] rowId;
	private String values;
	private String embargoDesc="";
	private String remarks="";
	private String mode;
	private String canSave;
	private String parameterCode;
	private String parameterValue;
	private String parameterDescValue;
	private String paraValue = "";
	private String isButtonClicked ;
	private String isScreenload;
	private String isDisplayDetails;
	private String isSaved;
	private  String originType="";
	private  String destinationType="";
	private  String origin="";
	private  String destination="";
	//Added by A-7924 as part of ICRD-313966 starts
	private  String segmentOriginType="";
	private  String segmentDestinationType="";
	private  String segmentOrigin="";
	private  String segmentDestination="";
	//Added by A-7924 as part of ICRD-313966 ends
	private  String viaPoint="";
	private  String viaPointType="";
	private String daysOfOperation;
	private String daysOfOperationApplicableOn;
	private String carrierCode;
	private String flightNumber;
	private String ruleType;
	private String category;
	private String complianceType;
	private String applicableTransactions;
	
	private String defaultText;
	private String flightType;
	private String uldPos;
	private String uldTyp;
	private String flightOwner;
	private String splitIndicator;
	//added by 202766
	private String unknownShipper;
	private String daysOfWeek;
	private String carrier;
	private String commodity;
	private String productCode;
	private String time;
	private String scc;
	private String sccGroup;
	private String dates;
	private String weight;
	private String awbPrefix;
	private String isPrivilegedUser;
	private String parameterLength;
	private String width;
	private String height;
	private String natureOfGoods;
	private String packingInstruction;
	private String unNumber;
	private String mailCategory;
	private String mailClass;
	private String mailSubClsGrp;
	private String mailSubClass;
	private String airlineGroup;
	private String groupName;
	private String groupType;
    private boolean autoCollapse;
    private String filterSummaryDetails;
	private String unWeight;
	private String dvForCustoms;
	private String dvForCarriage;
    private String unIds;//Added for ICRD-254555
    
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
    private String numberOfstops;
    
    private String serviceType;
    private String serviceTypeForTechnicalStop;
    
    private String unPg;
    private String subRisk;
    
	public boolean isAutoCollapse() {
		return autoCollapse;
	}
	public void setAutoCollapse(boolean autoCollapse) {
		this.autoCollapse = autoCollapse;
	}
	public String getFilterSummaryDetails() {
		return filterSummaryDetails;
	}
	public void setFilterSummaryDetails(String filterSummaryDetails) {
		this.filterSummaryDetails = filterSummaryDetails;
	}
	public String getNatureOfGoods() {
		return natureOfGoods;
	}
	public void setNatureOfGoods(String natureOfGoods) {
		this.natureOfGoods = natureOfGoods;
	}
	public String getUnNumber() {
		return unNumber;
	}
	public void setUnNumber(String unNumber) {
		this.unNumber = unNumber;
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
	public String getIsPrivilegedUser() {
		return isPrivilegedUser;
	}
	public void setIsPrivilegedUser(String isPrivilegedUser) {
		this.isPrivilegedUser = isPrivilegedUser;
	}
	
	public String getWeight() {
		return weight;
	}
	public void setWeight(String weight) {
		this.weight = weight;
	}
	public String getAwbPrefix() {
		return awbPrefix;
	}
	public void setAwbPrefix(String awbPrefix) {
		this.awbPrefix = awbPrefix;
	}
	public String getDates() {
		return dates;
	}
	public void setDates(String dates) {
		this.dates = dates;
	}
	public String getDimension() {
		return dimension;
	}
	public void setDimension(String dimension) {
		this.dimension = dimension;
	}

	private String dimension;

	private String paymentType;
	public String getDefaultText() {
		return defaultText;
	}
	public void setDefaultText(String defaultText) {
		this.defaultText = defaultText;
	}
	public String getFlightType() {
		return flightType;
	}
	public void setFlightType(String flightType) {
		this.flightType = flightType;
	}
	public String getFlightOwner() {
		return flightOwner;
	}
	public void setFlightOwner(String flightOwner) {
		this.flightOwner = flightOwner;
	}
	public String getSplitIndicator() {
		return splitIndicator;
	}
	public void setSplitIndicator(String splitIndicator) {
		this.splitIndicator = splitIndicator;
	}
	public String getDaysOfWeek() {
		return daysOfWeek;
	}
	public void setDaysOfWeek(String daysOfWeek) {
		this.daysOfWeek = daysOfWeek;
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
	public String getProductCode() {
		return productCode;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
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
	public String getPaymentType() {
		return paymentType;
	}
	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	private String[] applicableOn;
	
	
	
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
	
	private String navigationMode;

	/**
	 * 	Getter for navigationMode 
	 *	Added by : A-5175 on 30-Oct-2012
	 * 	Used for : icrd-21634
	 */
	public String getNavigationMode() {
		return navigationMode;
	}
	/**
	 *  @param navigationMode the navigationMode to set
	 * 	Setter for navigationMode 
	 *	Added by : A-5175 on 30-Oct-2012
	 * 	Used for : icrd-21634
	 */
	public void setNavigationMode(String navigationMode) {
		this.navigationMode = navigationMode;
	}
	/**
	 * @return Returns the bundle.
	 */
	public String getBundle() {
		return BUNDLE;
	}
	/**
	 * @param bundle The bundle to set.
	 */
	public void setBundle(String bundle) {
		this.bundle = bundle;
	}

	//FOR PAGINATION
	private String lastPageNum = "0";

	private String displayPage = "1";



	/**
	 * @return Returns the isSaved.
	 */
	public String getIsSaved() {
		return isSaved;
	}
	/**
	 * @param isSaved The isSaved to set.
	 */
	public void setIsSaved(String isSaved) {
		this.isSaved = isSaved;
	}
	/**
	 * @return Returns the isDisplayDetails.
	 */
	public String getIsDisplayDetails() {
		return isDisplayDetails;
	}
	/**
	 * @param isDisplayDetails The isDisplayDetails to set.
	 */
	public void setIsDisplayDetails(String isDisplayDetails) {
		this.isDisplayDetails = isDisplayDetails;
	}
	/**
	 * @return Returns the isScreenload.
	 */
	public String getIsScreenload() {
		return isScreenload;
	}
	/**
	 * @param isScreenload The isScreenload to set.
	 */
	public void setIsScreenload(String isScreenload) {
		this.isScreenload = isScreenload;
	}
	/**
	 * @return Returns the isButtonClicked.
	 */
	public String getIsButtonClicked() {
		return isButtonClicked;
	}
	/**
	 * @param isButtonClicked The isButtonClicked to set.
	 */
	public void setIsButtonClicked(String isButtonClicked) {
		this.isButtonClicked = isButtonClicked;
	}
	/**
	 * @return Returns the displayPage.
	 */
	public String getDisplayPage() {
		return displayPage;
	}
	/**
	 * @param displayPage The displayPage to set.
	 */
	public void setDisplayPage(String displayPage) {
		this.displayPage = displayPage;
	}

	/**
	 * @return Returns the lastPageNum.
	 */
	public String getLastPageNum() {
		return lastPageNum;
	}
	/**
	 * @param lastPageNum The lastPageNum to set.
	 */
	public void setLastPageNum(String lastPageNum) {
		this.lastPageNum = lastPageNum;
	}


	/**
	 * @return Returns the paraValue.
	 */
	public String getParaValue() {
		return paraValue;
	}
	/**
	 * @param paraValue The paraValue to set.
	 */
	public void setParaValue(String paraValue) {
		this.paraValue = paraValue;
	}
	/**
	 * @return Returns the canSave.
	 */
	public String getCanSave() {
		return canSave;
	}
	/**
	 * @param canSave The canSave to set.
	 */
	public void setCanSave(String canSave) {
		this.canSave = canSave;
	}
	/**
	 * @return Returns the mode.
	 */
	public String getMode() {
		return mode;
	}
	/**
	 * @return Returns the parameterCode.
	 */
	public String getParameterCode() {
		return parameterCode;
	}
	/**
	 * @param parameterCode The parameterCode to set.
	 */
	public void setParameterCode(String parameterCode) {
		this.parameterCode = parameterCode;
	}
	/**
	 * @return Returns the parameterValue.
	 */
	public String getParameterValue() {
		return parameterValue;
	}
	/**
	 * @param parameterValue The parameterValue to set.
	 */
	public void setParameterValue(String parameterValue) {
		this.parameterValue = parameterValue;
	}
	/**
	 * @param mode The mode to set.
	 */
	public void setMode(String mode) {
		this.mode = mode;
	}
	/**
	 * @return Returns the embargoDesc.
	 */
	public String getEmbargoDesc() {
		return embargoDesc;
	}
	/**
	 * @param embargoDesc The embargoDesc to set.
	 */
	public void setEmbargoDesc(String embargoDesc) {
		this.embargoDesc = embargoDesc;
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
	 * @return Returns the level.
	 */
	public String getLevel() {
		return level;
	}
	/**
	 * @return Returns the rowId.
	 */
	public String[] getRowId() {
		return rowId;
	}
	/**
	 * @param rowId The rowId to set.
	 */
	public void setRowId(String[] rowId) {
		this.rowId = rowId;
	}
	
	public String getValues() {
		return values;
	}
	public void setValues(String values) {
		this.values = values;
	}
	/**
	 * @return Returns the startDate.
	 */
	public String getStartDate() {
		return startDate;
	}
	/**
	 * @return Returns the applicable.
	 */
	public String[] getApplicable() {
		return applicable;
	}
	/**
	 * @param applicable The applicable to set.
	 */
	public void setApplicable(String[] applicable) {
		this.applicable = applicable;
	}
	/**
	 * @return Returns the parameter.
	 */
	public String[] getParameter() {
		return parameter;
	}
	/**
	 * @param parameter The parameter to set.
	 */
	public void setParameter(String[] parameter) {
		this.parameter = parameter;
	}
	/**
	 * @param startDate The startDate to set.
	 */
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	/**
	 * @return Returns the endDate.
	 */
	public String getEndDate() {
		return endDate;
	}
	/**
	 * @param endDate The endDate to set.
	 */
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	/**
	 * @return Returns the enabled.
	 */
	public String getEnabled() {
		return enabled;
	}
	/**
	 * @param enabled The enabled to set.
	 */
	public void setEnabled(String enabled) {
		this.enabled = enabled;
	}
	/**
	 * @param level The level to set.
	 */
	public void setLevel(String level) {
		this.level = level;
	}
	/**
	 * @return Returns the refNumber.
	 */
	public String getRefNumber() {
		return refNumber;
	}
	/**
	 * @param refNumber The refNumber to set.
	 */
	public void setRefNumber(String refNumber) {
		this.refNumber = refNumber;
	}

	/**
	 * @return screenid
	 */
		public String getScreenId() {
			return "reco.defaults.listembargo";
		}
	 /**
	  * @return
	  */
		public String getProduct() {
			return "reco";
		}
	/**
	 *  @return defaults
	 */
		public String getSubProduct() {
			return "defaults";
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
	 * @return Returns the viaPoint.
	 */
	public String getViaPoint() {
		return viaPoint;
	}
	
	/**
	 * @param viaPoint The viaPoint to set.
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
	/**
	 * @return Returns the carrierCode.
	 */
	public String getCarrierCode() {
		return carrierCode;
	}
	
	/**
	 * @param carrierCode The carrierCode to set.
	 */
	public void setCarrierCode(String carrierCode) {
		this.carrierCode = carrierCode;
	}
	
	/**
	 * @return Returns the flightNumber.
	 */
	public String getFlightNumber() {
		return flightNumber;
	}
	
	/**
	 * @param flightNumber The flightNumber to set.
	 */
	public void setFlightNumber(String flightNumber) {
		this.flightNumber = flightNumber;
	}
	
	/**
	 * @return Returns the parameterDescValue.
	 */
	public String getParameterDescValue() {
		return parameterDescValue;
	}
	
	/**
	 * @param parameterDescValue The parameterDescValue to set.
	 */
	public void setParameterDescValue(String parameterDescValue) {
		this.parameterDescValue = parameterDescValue;
	}
	/**
	 * @return the mailCategory
	 */
	public String getMailCategory() {
		return mailCategory;
	}
	/**
	 * @param mailCategory the mailCategory to set
	 */
	public void setMailCategory(String mailCategory) {
		this.mailCategory = mailCategory;
	}
	/**
	 * @return the mailClass
	 */
	public String getMailClass() {
		return mailClass;
	}
	/**
	 * @param mailClass the mailClass to set
	 */
	public void setMailClass(String mailClass) {
		this.mailClass = mailClass;
	}
	/**
	 * @return the mailSubClsGrp
	 */
	public String getMailSubClsGrp() {
		return mailSubClsGrp;
	}
	/**
	 * @param mailSubClsGrp the mailSubClsGrp to set
	 */
	public void setMailSubClsGrp(String mailSubClsGrp) {
		this.mailSubClsGrp = mailSubClsGrp;
	}
	/**
	 * @return the mailSubClass
	 */
	public String getMailSubClass() {
		return mailSubClass;
	}
	/**
	 * @param mailSubClass the mailSubClass to set
	 */
	//Added by A-7924 as part of ICRD-313966 starts
	public void setMailSubClass(String mailSubClass) {
		this.mailSubClass = mailSubClass;
	}	
public String getSegmentOriginType() {
		return segmentOriginType;
	}
	public void setSegmentOriginType(String segmentOriginType) {
		this.segmentOriginType = segmentOriginType;
	}
	public String getSegmentDestinationType() {
		return segmentDestinationType;
	}
	public void setSegmentDestinationType(String segmentDestinationType) {
		this.segmentDestinationType = segmentDestinationType;
	}
	public String getSegmentOrigin() {
		return segmentOrigin;
	}
	public void setSegmentOrigin(String segmentOrigin) {
		this.segmentOrigin = segmentOrigin;
	}
	public String getSegmentDestination() {
		return segmentDestination;
	}
	public void setSegmentDestination(String segmentDestination) {
		this.segmentDestination = segmentDestination;
	}
	//Added by A-7924 as part of ICRD-313966 ends
	/**
	 * @return the airlineGroup
	 */
	public String getAirlineGroup() {
		return airlineGroup;
	}
	/**
	 * @param airlineGroup
	 */
	public void setAirlineGroup(String airlineGroup) {
		this.airlineGroup = airlineGroup;
	}
	/**
	 * 
	 * 	Method		:	ListEmbargoRulesForm.getPackingInstruction
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
	 * 	Method		:	ListEmbargoRulesForm.setPackingInstruction
	 *	Added by 	:	A-7534 on 27-Mar-2018
	 * 	Used for 	:
	 *	Parameters	:	@param packingInstruction 
	 *	Return type	: 	void
	 */
	public void setPackingInstruction(String packingInstruction) {
		this.packingInstruction = packingInstruction;
	}
	/**
	 * @return groupName
	 */
	public String getGroupName() {
		return groupName;
	}
	/**
	 * @param groupName
	 */
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	/**
	 * @return groupType
	 */
	public String getGroupType() {
		return groupType;
	}
	/**
	 * @param groupType
	 */
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
	private String agentCode;
	
	private String agentGroup;
	
	public String getAgentCode() {
		return agentCode;
	}
	public void setAgentCode(String agentCode) {
		this.agentCode = agentCode;
	}
	public String getAgentGroup() {
		return agentGroup;
	}
	public void setAgentGroup(String agentGroup) {
		this.agentGroup = agentGroup;
	}
	public String getUnIds() {
		return unIds;
	}
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
	
	 public String getServiceType() {
			return serviceType;
		}
		public void setServiceType(String serviceType) {
			this.serviceType = serviceType;
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
	 * @return
	 */
	public String getNumberOfstops() {
		return numberOfstops;
	}
	/**
	 * 	A-8146@BLR 25-09-2019
	 * @param numberOfstops
	 */
	public void setNumberOfstops(String numberOfstops) {
		this.numberOfstops = numberOfstops;
	}
	public String getServiceTypeForTechnicalStop() {
		return serviceTypeForTechnicalStop;
	}
	public void setServiceTypeForTechnicalStop(String serviceTypeForTechnicalStop) {
		this.serviceTypeForTechnicalStop = serviceTypeForTechnicalStop;
	}
	
	public String getUnPg() {
		return unPg;
	}
	public void setUnPg(String unPg) {
		this.unPg = unPg;
	}
	
	public String getSubRisk() {
		return subRisk;
	}
	public void setSubRisk(String subRisk) {
		this.subRisk = subRisk;
	}
	
	//added by 202766
	public String getUnknownShipper() {
		return unknownShipper;
	}
	public void setUnknownShipper(String unknownShipper) {
		this.unknownShipper = unknownShipper;
	}
	
}
