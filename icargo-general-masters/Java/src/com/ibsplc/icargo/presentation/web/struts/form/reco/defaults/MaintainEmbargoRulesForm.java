/*
 * MaintainEmbargoRulesForm.java Created on Jun 20, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.struts.form.reco.defaults;

import java.util.Objects;

import com.ibsplc.icargo.framework.model.ScreenModel;

/**
 * Form bean for maintainembargoform
 * */
public class MaintainEmbargoRulesForm extends ScreenModel {

	private static final String BUNDLE
		= "embargorulesresources";
	private String bundle;
	private String refNumber;
	private String embargoLevel;
	private String status;
	private String startDate;
	private String endDate;
	private boolean isSuspended;
	private boolean isCool;
	private boolean isFrozen;
	private boolean isCC;
	private String[] parameterCode;
	private String[] isIncluded;
	private String[] rowId;
	private String[] values;
	private String[] valuesDesc;
	private String[] operationalFlag;
	private String originType="";
	private String origin="";
	private String destinationType="";
	private String destination="";	
	private String embargoDesc;
	private String remarks;
	private String mode;
	private String canSave;
	private String primaryKey;
	private String isScreenload ="true";
	private String isDisplayDetails="false";
	private String isSaved="";
	private String isButtonClicked;
	private String fromList;
	private String refNumberFlag="false";
	private String hiddenParameter;
	private String[] carrierCode;
	private String[] flightNumber;
	private String daysOfOperation;
	private String daysOfOperationApplicableOn;
	private String viaPointType;
	private String viaPoint;
	
	private String[] geographicLevel;
	private String[] geographicLevelType;
	private String[] geographicLevelApplicableOn;
	private String[] geographicLevelValues;
	private String[] glOperationFlag;
	private String[] index;
	private String  masterCheckbox;
	
	private String[] check;
	private String allCheck;
	private String[] paramOperationalFlag;

	private String[] defaultText;
	private String[] packingInstruction;
	private String[] flightType;
	private String[] uldPos;
	private String[] uldTyp;	
	private String[] flightOwner;
	private String[] splitIndicator;
	private String[] daysOfWeek;
	private String[] carrier;
	private String[] commodity;
	private String[] productCode;
	private String[] time;
	private String[] dimension;
	private String[] dates;
	private String[] weight;
	private String[] awbPrefix;
	private String[] unNumber;
	private String[] parameterLength;
	private String[] width;
	private String[] height;
	private String isApproveFlag;
	//Added for MAIL EMBARGO
	private String[] mailCategory;
	private String[] mailClass;
	private String[] mailSubClass;
	private String[] mailSubClassGrp;
	private String isModifyFlag;
	//added for ICRD-213193 by A-7815
	private String prevEmbargoLang;
	private String prevEmbargoDesc;
	private String currentEmbargoLang;
	private String currentEmbargoDesc;
	private String[] unWeight;
	private String[] dvForCustoms;
	private String[] dvForCarriage;
	private String[] unIds;//Added for ICRD-254555
	private String[] volume;
	
	//added by A-5799 for IASCB-23507 Starts
	private String[] serviceCargoClass;
	private String[] aircraftClassification;
	private String[] aircraftClassificationDesc;
	private String[] shipperCode;
	private String[] shipperGroup;
	private String[] consigneeCode;
	private String[] consigneeGroup;
	private String[] shipmentType;
	private String[] consol;
	//added by 202766
	private String[] unknownShipper;
	//added by A-5799 for IASCB-23507 ends
	public String[] getUnknownShipper() {
		if (Objects.nonNull(this.unknownShipper)) {
		return this.unknownShipper.clone();
		}
		return new String[0];
	}
	public void setUnknownShipper(String[] unknownShipper) {
		this.unknownShipper = unknownShipper.clone();
	}
	//ended
	private String[] serviceType;
	private String[] serviceTypeForTechnicalStop;
	
	private String[] unPg;
	private String[] subRisk;

	public String[] getSubRisk() {
		if (Objects.nonNull(this.subRisk)) {
			return this.subRisk.clone();
		}
		return new String[0];
	}

	public void setSubRisk(String[] subRisk) {
		this.subRisk = subRisk.clone();
	}
	
	public String[] getUnPg() {
		if (Objects.nonNull(this.unPg)) {
			return this.unPg.clone();
		}
		return new String[0];
	}

	public void setUnPg(String[] unPg) {
		this.unPg = unPg.clone();
	}
	
	public String[] getServiceTypeForTechnicalStop() {
		 if(Objects.nonNull(this.serviceTypeForTechnicalStop)) {
		return this.serviceTypeForTechnicalStop.clone();
	}
		 return new String[0];
	}
	public void setServiceTypeForTechnicalStop(String[] serviceTypeForTechnicalStop) {
		this.serviceTypeForTechnicalStop = serviceTypeForTechnicalStop.clone();
	}
	public String[] getServiceType() {
		 if(Objects.nonNull(this.serviceType)) {
		return this.serviceType.clone();
		
	}
		 return new String[0];
	}
	public void setServiceType(String[] serviceType) {
		this.serviceType = serviceType.clone();
	}
	private String[] numberOfStops;
	public String getIsApproveFlag() {
		return isApproveFlag;
	}
	public void setIsApproveFlag(String isApproveFlag) {
		this.isApproveFlag = isApproveFlag;
	}


	private String isDuplicatePresent;
	public String getIsDuplicatePresent() {
		return isDuplicatePresent;
	}
	public void setIsDuplicatePresent(String isDuplicatePresent) {
		this.isDuplicatePresent = isDuplicatePresent;
	}
	public String[] getParameterLength() {
		return parameterLength;
	}
	public void setParameterLength(String[] parameterLength) {
		this.parameterLength = parameterLength;
	}
	public String[] getWidth() {
		return width;
	}
	public void setWidth(String[] width) {
		this.width = width;
	}
	public String[] getHeight() {
		return height;
	}
	public void setHeight(String[] height) {
		this.height = height;
	}
	public String[] getUnNumber() {
		return unNumber;
	}
	public void setUnNumber(String[] unNumber) {
		this.unNumber = unNumber;
	}
	public String[] getWeight() {
		return weight;
	}
	public void setWeight(String[] weight) {
		this.weight = weight;
	}
	public String[] getAwbPrefix() {
		return awbPrefix;
	}
	public void setAwbPrefix(String[] awbPrefix) {
		this.awbPrefix = awbPrefix;
	}
	public String[] getDates() {
		return dates;
	}
	public void setDates(String[] dates) {
		this.dates = dates;
	}
	public String[] getDimension() {
		return dimension;
	}
	public void setDimension(String[] dimension) {
		this.dimension = dimension;
	}

	
	private String embargoVersion;
	
	private String isPrivilegedUser;
	
	public String getIsPrivilegedUser() {
		return isPrivilegedUser;
	}
	public void setIsPrivilegedUser(String isPrivilegedUser) {
		this.isPrivilegedUser = isPrivilegedUser;
	}
	public String getEmbargoVersion() {
		return embargoVersion;
	}
	public void setEmbargoVersion(String embargoVersion) {
		this.embargoVersion = embargoVersion;
	}
	public String[] getProductCode() {
		return productCode;
	}
	public void setProductCode(String[] productCode) {
		this.productCode = productCode;
	}


	public String[] getTime() {
		return time;
	}
	public void setTime(String[] time) {
		this.time = time;
	}


	private String[] scc;
	private String[] sccGroup;
	private String[] paymentType;
	private String[] applicableOn;
	private String[] applicableOnParameter;
	public String[] getApplicableOnParameter() {
		return applicableOnParameter;
	}
	public void setApplicableOnParameter(String[] applicableOnParameter) {
		this.applicableOnParameter = applicableOnParameter;
	}


	private String[] airlineGroup;
	private String[] aircraftType;
	private String[] aircraftGroupType;
	private String[] uldType;
	public String[] getAirlineGroup() {
		return airlineGroup;
	}
	public void setAirlineGroup(String[] airlineGroup) {
		this.airlineGroup = airlineGroup;
	}
	public String[] getParamOperationalFlag() {
		return paramOperationalFlag;
	}
	public void setParamOperationalFlag(String[] paramOperationalFlag) {
		this.paramOperationalFlag = paramOperationalFlag;
	}
	public String[] getIndex() {
		return index;
	}
	public void setIndex(String[] index) {
		this.index = index;
	}
	public String getMasterCheckbox() {
		return masterCheckbox;
	}
	public void setMasterCheckbox(String masterCheckbox) {
		this.masterCheckbox = masterCheckbox;
	}


	private String category;
	private String complianceType;
	public String[] getGlOperationFlag() {
		return glOperationFlag;
	}
	public void setGlOperationFlag(String[] glOperationFlag) {
		this.glOperationFlag = glOperationFlag;
	}


	private String applicableTransactions;
	
	public String getApplicableTransactions() {
		return applicableTransactions;
	}
	public void setApplicableTransactions(String applicableTransactions) {
		this.applicableTransactions = applicableTransactions;
	}
	public String[] getGeographicLevelValues() {
		return geographicLevelValues;
	}
	public void setGeographicLevelValues(String[] geographicLevelValues) {
		this.geographicLevelValues = geographicLevelValues;
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
	public String[] getGeographicLevel() {
		return geographicLevel;
	}
	public void setGeographicLevel(String[] geographicLevel) {
		this.geographicLevel = geographicLevel;
	}
	public String[] getGeographicLevelType() {
		return geographicLevelType;
	}
	public void setGeographicLevelType(String[] geographicLevelType) {
		this.geographicLevelType = geographicLevelType;
	}
	public String[] getGeographicLevelApplicableOn() {
		return geographicLevelApplicableOn;
	}
	public void setGeographicLevelApplicableOn(String[] geographicLevelApplicableOn) {
		this.geographicLevelApplicableOn = geographicLevelApplicableOn;
	}
	

	public String[] getCheck() {
		return check;
	}
	public void setCheck(String[] check) {
		this.check = check;
	}
	public String getAllCheck() {
		return allCheck;
	}
	public void setAllCheck(String allCheck) {
		this.allCheck = allCheck;
	}
	/**
	 * @return Returns the hiddenParameter.
	 */
	public String getHiddenParameter() {
		return hiddenParameter;
	}
	/**
	 * @param hiddenParameter The hiddenParameter to set.
	 */
	public void setHiddenParameter(String hiddenParameter) {
		this.hiddenParameter = hiddenParameter;
	}
	/**
	 * @return Returns the refNumberFlag.
	 */
	public String getRefNumberFlag() {
		return refNumberFlag;
	}
	/**
	 * @param refNumberFlag The refNumberFlag to set.
	 */
	public void setRefNumberFlag(String refNumberFlag) {
		this.refNumberFlag = refNumberFlag;
	}
	public String getFromList() {
		return fromList;
	}
	public void setFromList(String fromList) {
		this.fromList = fromList;
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
	 * Getter method for canSave
	 * @return Returns the canSave.
	 */
	public String getCanSave() {
		return canSave;
	}
	/**
	 * Setter method for canSave
	 * @param canSave The canSave to set.
	 */
	public void setCanSave(String canSave) {
		this.canSave = canSave;
	}
	/**
	 * Getter method for mode
	 * @return Returns the mode.
	 */
	public String getMode() {
		return mode;
	}
	/**
	 * Setter method for mode
	 * @param mode The mode to set.
	 */
	public void setMode(String mode) {
		this.mode = mode;
	}
	/**
	 * Getter method for embargoDesc.
	 * @return Returns the embargoDesc.
	 */
	public String getEmbargoDesc() {
		return embargoDesc;
	}
	/**
	 * Setter method for embargoDesc
	 * @param embargoDesc The embargoDesc to set.
	 */
	public void setEmbargoDesc(String embargoDesc) {
		this.embargoDesc = embargoDesc;
	}
	/**
	 * Getter method for remarks
	 * @return Returns the remarks.
	 */
	public String getRemarks() {
		return remarks;
	}
	/**
	 * Setter method for remarks
	 * @param remarks The remarks to set.
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	/**
	 * Getter method for status.
	 * @return Returns the status.
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * Setter method for status.
	 * @param status The status to set.
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * Getter method for embargoLevel.
	 * @return Returns the embargoLevel.
	 */
	public String getEmbargoLevel() {
		return embargoLevel;
	}
	/**
	 * Getter method for rowId.
	 * @return Returns the rowId.
	 */
	public String[] getRowId() {
		return rowId;
	}
	/**
	 * Setter method for rowId
	 * @param rowId The rowId to set.
	 */
	public void setRowId(String[] rowId) {
		this.rowId = rowId;
	}
	/**
	 * Getter method for values.
	 * @return Returns the values.
	 */
	public String[] getValues() {
		return values;
	}
	/**
	 * Setter method for values.
	 * @param values The values to set.
	 */
	public void setValues(String[] values) {
		this.values = values;
	}
	/**
	 * Getter method for startDate.
	 * @return Returns the startDate.
	 */
	public String getStartDate() {
		return startDate;
	}
	/**
	 * Getter method for isIncluded
	 * @return Returns the isIncluded
	 */
	public String[] getIsIncluded() {
		return isIncluded;
	}
	/**
	 * Setter method for isIncluded
	 * @param isIncluded The isIncluded to set
	 */
	public void setIsIncluded(String[] isIncluded) {
		this.isIncluded = isIncluded;
	}
	/**
	 * Getter method for parameterCode.
	 * @return Returns the parameterCode
	 */
	public String[] getParameterCode() {
		return parameterCode;
	}
	/**
	 * Getter method for parameterCode.
	 * @param parameterCode The parameterCode to set
	 */
	public void setParameterCode(String[] parameterCode) {
		this.parameterCode = parameterCode;
	}
	/**
	 * Setter method for startDate
	 * @param startDate The startDate to set
	 */
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	/**
	 * Getter method for endDate.
	 * @return Returns the endDate.
	 */
	public String getEndDate() {
		return endDate;
	}
	/**
	 * Setter method for endDate.
	 * @param endDate The endDate to set.
	 */
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	/**
	 * Getter method for isSuspended.
	 * @return Returns the isSuspended.
	 */
	public boolean getIsSuspended() {
		return isSuspended;
	}
	/**
	 * Setter method for isSuspended.
	 * @param isSuspended The isSuspended to set.
	 */
	public void setIsSuspended(boolean isSuspended) {
		this.isSuspended = isSuspended;
	}
	/**
	 * Getter method for isCool.
	 * @return Returns the isCool.
	 */
	public boolean getIsCool() {
		return isCool;
	}
	/**
	 * Setter method for isCool.
	 * @param isCool The isCool to set.
	 */
	public void setIsCool(boolean isCool) {
		this.isCool = isCool;
	}
	/**
	 * Getter method for isFrozen.
	 * @return Returns the isFrozen.
	 */
	public boolean getIsFrozen() {
		return isFrozen;
	}
	/**
	 * Setter method for isFrozen.
	 * @param isFrozen The isFrozen to set.
	 */
	public void setIsFrozen(boolean isFrozen) {
		this.isFrozen = isFrozen;
	}
	/**
	 * Getter method for isCC.
	 * @return Returns the isCC.
	 */
	public boolean getIsCC() {
		return isCC;
	}
	/**
	 * Setter method for isCC.
	 * @param isCC The isCC to set.
	 */
	public void setIsCC(boolean isCC) {
		this.isCC = isCC;
	}		
	/**
	 * Setter method for embargoLevel
	 * @param embargoLevel The embargoLevel to set.
	 */
	public void setEmbargoLevel(String embargoLevel) {
		this.embargoLevel = embargoLevel;
	}
	/**
	 * Getter method for refNumber.
	 * @return Returns the refNumber.
	 */
	public String getRefNumber() {
		return refNumber;
	}
	/**
	 * Setter method for refNumber.
	 * @param refNumber The refNumber to set.
	 */
	public void setRefNumber(String refNumber) {
		this.refNumber = refNumber;
	}

	/**
	 * Getter method for screenid
	 * @return screenid
	 */
		public String getScreenId() {
			return "reco.defaults.maintainembargo";
		}
	 /**
	  * Getter method for product
	  * @return product
	  */
		public String getProduct() {
			return "reco";
		}
	/**
	 * Getter method for subproduct
	 *  @return SubProduct
	 */
		public String getSubProduct() {
			return "defaults";
		}
	/**
	 * Getter method for operationalFlag
	 * @return Returns the operationalFlag.
	 */
	public String[] getOperationalFlag() {
		return operationalFlag;
	}
	/**
	 * Setter method for operationalFlag
	 * @param operationalFlag The operationalFlag to set.
	 */
	public void setOperationalFlag(String[] operationalFlag) {
		this.operationalFlag = operationalFlag;
	}
	/**
	 * Getter method for primaryKey.
	 * @return Returns the primaryKey.
	 */
	public String getPrimaryKey() {
		return primaryKey;
	}
	/**
	 * Setter method for primaryKey.
	 * @param primaryKey The primaryKey to set.
	 */
	public void setPrimaryKey(String primaryKey) {
		this.primaryKey = primaryKey;
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
	 * @return Returns the carrierCode.
	 */
	public String[] getCarrierCode() {
		return carrierCode;
	}
	
	/**
	 * @param carrierCode The carrierCode to set.
	 */
	public void setCarrierCode(String[] carrierCode) {
		this.carrierCode = carrierCode;
	}
	
	/**
	 * @return Returns the flightNumber.
	 */
	public String[] getFlightNumber() {
		return flightNumber;
	}
	
	/**
	 * @param flightNumber The flightNumber to set.
	 */
	public void setFlightNumber(String[] flightNumber) {
		this.flightNumber = flightNumber;
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
	 * @return Returns the valuesDesc.
	 */
	public String[] getValuesDesc() {
		return valuesDesc;
	}
	

	/**
	 * @param valuesDesc The valuesDesc to set.
	 */
	public void setValuesDesc(String[] valuesDesc) {
		this.valuesDesc = valuesDesc;
	}
	public String[] getDefaultText() {
		return defaultText;
	}
	public void setDefaultText(String[] defaultText) {
		this.defaultText = defaultText;
	}
	public String[] getFlightType() {
		return flightType;
	}
	public void setFlightType(String[] flightType) {
		this.flightType = flightType;
	}
	public String[] getFlightOwner() {
		return flightOwner;
	}
	public void setFlightOwner(String[] flightOwner) {
		this.flightOwner = flightOwner;
	}
	public String[] getSplitIndicator() {
		return splitIndicator;
	}
	public void setSplitIndicator(String[] splitIndicator) {
		this.splitIndicator = splitIndicator;
	}
	public String[] getDaysOfWeek() {
		return daysOfWeek;
	}
	public void setDaysOfWeek(String[] daysOfWeek) {
		this.daysOfWeek = daysOfWeek;
	}
	public String[] getCarrier() {
		return carrier;
	}
	public void setCarrier(String[] carrier) {
		this.carrier = carrier;
	}
	public String[] getCommodity() {
		return commodity;
	}
	public void setCommodity(String[] commodity) {
		this.commodity = commodity;
	}
	public String[] getScc() {
		return scc;
	}
	public void setScc(String[] scc) {
		this.scc = scc;
	}
	public String[] getSccGroup() {
		return sccGroup;
	}
	public void setSccGroup(String[] sccGroup) {
		this.sccGroup = sccGroup;
	}
	public String[] getPaymentType() {
		return paymentType;
	}
	public void setPaymentType(String[] paymentType) {
		this.paymentType = paymentType;
	}
	public String[] getApplicableOn() {
		return applicableOn;
	}
	public void setApplicableOn(String[] applicableOn) {
		this.applicableOn = applicableOn;
	}
	/**
	 * @return the mailCategory
	 */
	public String[] getMailCategory() {
		return mailCategory;
	}
	/**
	 * @param mailCategory the mailCategory to set
	 */
	public void setMailCategory(String[] mailCategory) {
		this.mailCategory = mailCategory;
	}
	/**
	 * @return the mailClass
	 */
	public String[] getMailClass() {
		return mailClass;
	}
	/**
	 * @param mailClass the mailClass to set
	 */
	public void setMailClass(String[] mailClass) {
		this.mailClass = mailClass;
	}
	/**
	 * @return the mailSubClass
	 */
	public String[] getMailSubClass() {
		return mailSubClass;
	}
	/**
	 * @param mailSubClass the mailSubClass to set
	 */
	public void setMailSubClass(String[] mailSubClass) {
		this.mailSubClass = mailSubClass;
	}
	/**
	 * @return the mailSubClassGrp
	 */
	public String[] getMailSubClassGrp() {
		return mailSubClassGrp;
	}
	/**
	 * @param mailSubClassGrp the mailSubClassGrp to set
	 */
	public void setMailSubClassGrp(String[] mailSubClassGrp) {
		this.mailSubClassGrp = mailSubClassGrp;
	}
	/**
	 * @return the isModifyFlag
	 */
	public String getIsModifyFlag() {
		return isModifyFlag;
	}
	/**
	 * @param isModifyFlag the isModifyFlag to set
	 */
	public void setIsModifyFlag(String isModifyFlag) {
		this.isModifyFlag = isModifyFlag;
	}
	/**
	 * 	Getter for prevEmbargoLang 
	 *	Added by : a-7815 on 31-Aug-2017
	 * 	Used for :
	 */
	public String getPrevEmbargoLang() {
		return prevEmbargoLang;
	}
	/**
	 *  @param prevEmbargoLang the prevEmbargoLang to set
	 * 	Setter for prevEmbargoLang 
	 *	Added by : a-7815 on 31-Aug-2017
	 * 	Used for :
	 */
	public void setPrevEmbargoLang(String prevEmbargoLang) {
		this.prevEmbargoLang = prevEmbargoLang;
	}
	/**
	 * 	Getter for prevEmbargoDesc 
	 *	Added by : a-7815 on 31-Aug-2017
	 * 	Used for :
	 */
	public String getPrevEmbargoDesc() {
		return prevEmbargoDesc;
	}
	/**
	 *  @param prevEmbargoDesc the prevEmbargoDesc to set
	 * 	Setter for prevEmbargoDesc 
	 *	Added by : a-7815 on 31-Aug-2017
	 * 	Used for :
	 */
	public void setPrevEmbargoDesc(String prevEmbargoDesc) {
		this.prevEmbargoDesc = prevEmbargoDesc;
	}
	/**
	 * 	Getter for currentEmbargoLang 
	 *	Added by : a-7815 on 31-Aug-2017
	 * 	Used for :
	 */
	public String getCurrentEmbargoLang() {
		return currentEmbargoLang;
	}
	/**
	 *  @param currentEmbargoLang the currentEmbargoLang to set
	 * 	Setter for currentEmbargoLang 
	 *	Added by : a-7815 on 31-Aug-2017
	 * 	Used for :
	 */
	public void setCurrentEmbargoLang(String currentEmbargoLang) {
		this.currentEmbargoLang = currentEmbargoLang;
	}
	/**
	 * 	Getter for currentEmbargoDesc 
	 *	Added by : a-7815 on 31-Aug-2017
	 * 	Used for :
	 */
	public String getCurrentEmbargoDesc() {
		return currentEmbargoDesc;
	}
	/**
	 *  @param currentEmbargoDesc the currentEmbargoDesc to set
	 * 	Setter for currentEmbargoDesc 
	 *	Added by : a-7815 on 31-Aug-2017
	 * 	Used for :
	 */
	public void setCurrentEmbargoDesc(String currentEmbargoDesc) {
		this.currentEmbargoDesc = currentEmbargoDesc;
	}
	/**
	 * 
	 * 	Method		:	MaintainEmbargoRulesForm.getPackingInstruction
	 *	Added by 	:	A-7534 on 27-Mar-2018
	 * 	Used for 	:
	 *	Parameters	:	@return 
	 *	Return type	: 	String[]
	 */
	public String[] getPackingInstruction() {
		return packingInstruction;
	}
	/**
	 * 
	 * 	Method		:	MaintainEmbargoRulesForm.setPackingInstruction
	 *	Added by 	:	A-7534 on 27-Mar-2018
	 * 	Used for 	:
	 *	Parameters	:	@param packingInstruction 
	 *	Return type	: 	void
	 */
	public void setPackingInstruction(String[] packingInstruction) {
		this.packingInstruction = packingInstruction;
	}
	public String[] getUnWeight() {
		return unWeight;
	}
	public void setUnWeight(String[] unWeight) {
		this.unWeight = unWeight;
	}
	public String[] getDvForCustoms() {
		return dvForCustoms;
	}
	public void setDvForCustoms(String[] dvForCustoms) {
		this.dvForCustoms = dvForCustoms;
	}
	public String[] getDvForCarriage() {
		return dvForCarriage;
	}
	public void setDvForCarriage(String[] dvForCarriage) {
		this.dvForCarriage = dvForCarriage;
	}
	/**
	 * @return the UNIDs
	 */
	public String[] getUnIds() {
		return unIds;
	}
	/**
	 * @param UNIDs set
	 */
	public void setUnIds(String[] unIds) {
		this.unIds = unIds;
	}
	/**
	 * Field 	: agent	of type : String[]
	 * Used for :
	 */
	private String[] agentCode;
	/**
	 * 	Method		:	MaintainEmbargoRulesForm.getAgentCode
	 *	Added by 	:	A-8041 on 26-Sep-2018
	 * 	Used for 	:
	 *	Parameters	:	@return 
	 *	Return type	: 	String[]
	 */
	public String[] getAgentCode() {
		return agentCode;
	}
	/**
	 * 	Method		:	MaintainEmbargoRulesForm.setAgentCode
	 *	Added by 	:	A-8041 on 26-Sep-2018
	 * 	Used for 	:
	 *	Parameters	:	@param agentCode 
	 *	Return type	: 	void
	 */
	public void setAgentCode(String[] agentCode) {
		this.agentCode = agentCode;
	}
	/**
	 * Field 	: agentGroup	of type : String[]
	 * Used for :
	 */
	private String[] agentGroup;
	
	/**
	 * 	Method		:	MaintainEmbargoRulesForm.getAgentGroup
	 *	Added by 	:	A-8041 on 25-Sep-2018
	 * 	Used for 	:
	 *	Parameters	:	@return 
	 *	Return type	: 	String[]
	 */
	public String[] getAgentGroup() {
		return agentGroup;
	}
	/**
	 * 	Method		:	MaintainEmbargoRulesForm.setAgentGroup
	 *	Added by 	:	A-8041 on 25-Sep-2018
	 * 	Used for 	:
	 *	Parameters	:	@param agentGroup 
	 *	Return type	: 	void
	 */
	public void setAgentGroup(String[] agentGroup) {
		this.agentGroup = agentGroup;
	}
	/**
	 * 	Getter for volume 
	 *	Added by : A-8146 on 02-Aug-2019
	 * 	Used for :
	 */
	public String[] getVolume() {
		return volume;
	}
	/**
	 *  @param volume the volume to set
	 * 	Setter for volume 
	 *	Added by : A-8146 on 02-Aug-2019
	 * 	Used for :
	 */
	public void setVolume(String[] volume) {
		this.volume = volume;
	}
	/**
	 * 	Getter for aircraftType 
	 *	Added by : A-8146 on 02-Aug-2019
	 * 	Used for :
	 */
	public String[] getAircraftType() {
		return aircraftType;
	}
	/**
	 *  @param aircraftType the aircraftType to set
	 * 	Setter for aircraftType 
	 *	Added by : A-8146 on 02-Aug-2019
	 * 	Used for :
	 */
	public void setAircraftType(String[] aircraftType) {
		this.aircraftType = aircraftType;
	}
	/**
	 * 	Getter for aircraftGroupType 
	 *	Added by : A-8146 on 02-Aug-2019
	 * 	Used for :
	 */
	public String[] getAircraftGroupType() {
		return aircraftGroupType;
	}
	/**
	 *  @param aircraftGroupType the aircraftGroupType to set
	 * 	Setter for aircraftGroupType 
	 *	Added by : A-8146 on 02-Aug-2019
	 * 	Used for :
	 */
	public void setAircraftGroupType(String[] aircraftGroupType) {
		this.aircraftGroupType = aircraftGroupType;
	}
	/**
	 * 	Getter for uldType 
	 *	Added by : A-8146 on 02-Aug-2019
	 * 	Used for :
	 */
	public String[] getUldType() {
		return uldType;
	}
	/**
	 *  @param uldType the uldType to set
	 * 	Setter for uldType 
	 *	Added by : A-8146 on 02-Aug-2019
	 * 	Used for :
	 */
	public void setUldType(String[] uldType) {
		this.uldType = uldType;
	}
	
	public String[] getUldPos() {
		return uldPos;
	}
	public void setUldPos(String[] uldPos) {
		this.uldPos = uldPos;
	}
	/**
	 * @return the serviceCargoClass
	 */
	public String[] getServiceCargoClass() {
		return serviceCargoClass;
	}
	/**
	 * @param serviceCargoClass the serviceCargoClass to set
	 */
	public void setServiceCargoClass(String[] serviceCargoClass) {
		this.serviceCargoClass = serviceCargoClass;
	}
	
	/**
	 * @return the aircraftClassification
	 */
	public String[] getAircraftClassification() {
		return aircraftClassification;
	}
	
	/**
	 * @param aircraftClassification the aircraftClassification to set
	 */
	public void setAircraftClassification(String[] aircraftClassification) {
		this.aircraftClassification = aircraftClassification;
	}
	
	
	/**
	 * @return the shipperCode
	 */
	public String[] getShipperCode() {
		return shipperCode;
	}
	
	/**
	 * @param shipperCode the shipperCode to set
	 */
	public void setShipperCode(String[] shipperCode) {
		this.shipperCode = shipperCode;
	}
	
	/**
	 * @return the shipperGroup
	 */
	public String[] getShipperGroup() {
		return shipperGroup;
	}
	
	/**
	 * @param shipperGroup the shipperGroup to set
	 */
	public void setShipperGroup(String[] shipperGroup) {
		this.shipperGroup = shipperGroup;
	}
	
	/**
	 * @return the consigneeCode
	 */
	public String[] getConsigneeCode() {
		return consigneeCode;
	}
	
	/**
	 * @param consigneeCode the consigneeCode to set
	 */
	public void setConsigneeCode(String[] consigneeCode) {
		this.consigneeCode = consigneeCode;
	}
	
	/**
	 * @return the consigneeGroup
	 */
	public String[] getConsigneeGroup() {
		return consigneeGroup;
	}
	
	/**
	 * @param consigneeGroup the consigneeGroup to set
	 */
	public void setConsigneeGroup(String[] consigneeGroup) {
		this.consigneeGroup = consigneeGroup;
	}
	
	/**
	 * @return the shipmentType
	 */
	public String[] getShipmentType() {
		return shipmentType;
	}
	/**
	 * @param shipmentType the shipmentType to set
	 */
	public void setShipmentType(String[] shipmentType) {
		this.shipmentType = shipmentType;
	}
	/**
	 * @return the consol
	 */
	public String[] getConsol() {
		return consol;
	}
	/**
	 * @param consol the consol to set
	 */
	public void setConsol(String[] consol) {
		this.consol = consol;
	}
	/**
	 * @return the aircraftClassificationDesc
	 */
	public String[] getAircraftClassificationDesc() {
		return aircraftClassificationDesc;
	}
	/**
	 * @param aircraftClassificationDesc the aircraftClassificationDesc to set
	 */
	public void setAircraftClassificationDesc(String[] aircraftClassificationDesc) {
		this.aircraftClassificationDesc = aircraftClassificationDesc;
	}


	/**
	 * A-8146@BLR 25-09-2019
	 * @return
	 */
	public String[] getNumberOfStops() {
		return numberOfStops;
	}
	/**
	 * A-8146@BLR 25-09-2019
	 * @param numberOfStops
	 */
	public void setNumberOfStops(String[] numberOfStops) {
		this.numberOfStops = numberOfStops;
	}
	
}
