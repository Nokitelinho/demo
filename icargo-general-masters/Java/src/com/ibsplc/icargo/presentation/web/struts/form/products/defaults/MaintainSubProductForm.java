/*
 * MaintainSubProductForm.java Created on Jul 7, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.struts.form.products.defaults;

import com.ibsplc.icargo.framework.model.ScreenModel;


/**
 * @author A-1358
 *
 */
public class MaintainSubProductForm extends ScreenModel {
	private static final String RESTRICT = "Restrict";
	private static final String ALLOW = "Allow";


	private String mode;
	private String productName;
	private String status;
	private String hiddenStatus;
	private String startDate="";
	private String endDate="";
	private String transportMode;
	private String priority;
	private String hiddenPriority;
	private String productScc;
	private String handlingInfo = "";
	private String remarks = "";
	private String minWeight = "" ;
	private String maxWeight = "";
	private String maxVolume = "";
	private String minVolume = "";
	private String weightUnit = "";
	private String volumeUnit = "";
	private String[] selectedComdty;
	private String productCode;
	private String subProductCode;
	private String addRestriction = "";

	private String versionNumber = "";
//variables for milestone tab
	private String[] mileStoneRowId;

	private String[] minTime;
	private String[] maxTime;
	private String[] alertTime;
	private String[] chaserTime;
	private String[] isExternal;
	private String[] isInternal;
	private String[] isTransit;

	private String checkedExternal;
	private String checkedInternal;
	private String checkedTransit;

	private String[] milestoneOpFlag;
	private String[] isRowModified;
	private String[] isNewRowModified;
	private String[] chaserFrequency;
	private String[] maxNoOfChasers;

//The variable for ProductListing

	private String[] productChecked;

	private String nextAction="";
	private String parentSession="";
	private String lovAction="";

//	For radio buttons
	private String commodityStatus = RESTRICT ; // for radion button placed on top of commodity
	private String originStatus = RESTRICT ;
	private String destinationStatus = RESTRICT ;
	private String segmentStatus = RESTRICT ;
	private String custGroupStatus = RESTRICT ;
	//for checkboxes of tables
	private String[] commodityCheck;
	private String[] segmentCheck;
	private String[] originCheck;
	private String[] destinationCheck;
	private String[] custGroupCheck;
	private String[] restrictedTermsCheck;
	private String companyCode;
	private String[] isSegmentRowModified;
	private String[] segmentRowId;
//	The variables to be set as hidden field for table data


	private String[] commodity;
	private String[] origin;
	private String[] destination;
	private String[] segment;
	private String[] custGroup;
	private String[] paymentRestriction;

	private String[] segmentOperationFlag;
	
	private String saveSuccessful;
	
	
	//Added now
	
	private String fromListSubproduct;
	


private static final String BUNDLE = "MaintainSubProduct"; // The key attribute specified in struts_config.xml file.

	private String bundle;


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
	 * @return Returns the segmentOperationFlag.
	 */
	public String[] getSegmentOperationFlag() {
		return segmentOperationFlag;
	}

	/**
	 * @param segmentOperationFlag The segmentOperationFlag to set.
	 */
	public void setSegmentOperationFlag(String[] segmentOperationFlag) {
		this.segmentOperationFlag = segmentOperationFlag;
	}
/**
 * @return screenid
 */
	public String getScreenId() {
		return "products.defaults.maintainsubproducts";
	}
 /**
  * @return
  */
	public String getProduct() {
		return "products";
	}
/**
 *  @return defaults
 */
	public String getSubProduct() {
		return "defaults";
	}
	/**
	 * @return selectedComdty
	 */
	public String[] getSelectedComdty() {
		return selectedComdty;
	}


	/**
	 * @param selectedComdty The services to set.
	 */
	public void setSelectedComdty(String[] selectedComdty) {
		this.selectedComdty = selectedComdty;
	}
	/**
	 *
	 * @return String
	 */
	public String getMode() {
		return mode;
	}

   /**
    *
    * @param mode
    */

	public void setMode(String mode) {
		this.mode = mode;
	}
    /**
     * @return String
     */
	public String getEndDate() {
		return endDate;
	}
    /**
     *
     * @param endDate
     */
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
    /**
     *
     * @return  String
     */
	public String getPriority() {
		return priority;
	}
/**
 *
 * @param priority
 */
	public void setPriority(String priority) {
		this.priority = priority;
	}
/**
 *
 * @return String
 */
	public String getHiddenPriority() {
			return hiddenPriority;
	}
/**
 *
 * @param hiddenPriority
 */
	public void setHiddenPriority(String hiddenPriority) {
		this.hiddenPriority = hiddenPriority;
	}
/**
 *
 * @return String
 */
	public String getProductName() {
		return productName;
	}
/**
 *
 * @param productName
 */
	public void setProductName(String productName) {
		this.productName = productName;
	}
/**
 *
 * @return String
 */
	public String getProductScc() {
		return productScc;
	}
/**
 *
 * @param productScc
 */
	public void setProductScc(String productScc) {
		this.productScc = productScc;
	}
/**
 *
 * @return String
 */
	public String getStartDate() {
		return startDate;
	}
/**
 *
 * @param startDate
 */
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
/**
 *
 * @return String
 */
	public String getStatus() {
		return status;
	}
/**
 *
 * @param status
 */
	public void setStatus(String status) {
		this.status = status;
	}
/**
 * @return String
 */
	public String getTransportMode() {
		return transportMode;
	}
/**
 *
 * @param transportMode
 */
	public void setTransportMode(String transportMode) {
		this.transportMode = transportMode;
	}
/**
 *
 * @return  String
 */
	public String getMaxVolume() {
		return maxVolume;
	}

/**
 *
 * @param maxVolume
 */
	public void setMaxVolume(String maxVolume) {
		this.maxVolume = maxVolume;
	}

/**
 *
 * @return  String
 */
	public String getMaxWeight() {
		return maxWeight;
	}
/**
 *
 * @param maxWeight
 */

	public void setMaxWeight(String maxWeight) {
		this.maxWeight = maxWeight;
	}
/**
 *
 * @return String
 */

	public String getMinVolume() {
		return minVolume;
	}

/**
 *
 * @param minVolume
 */
	public void setMinVolume(String minVolume) {
		this.minVolume = minVolume;
	}
/**
 *
 * @return String
 */

	public String getMinWeight() {
		return minWeight;
	}

/**
 *
 * @param minWeight
 */
	public void setMinWeight(String minWeight) {
		this.minWeight = minWeight;
	}

/**
 *
 * @return String
 */
	public String getVolumeUnit() {
		return volumeUnit;
	}
/**
 *
 * @param volumeUnit
 */

	public void setVolumeUnit(String volumeUnit) {
		this.volumeUnit = volumeUnit;
	}
/**
 *
 * @return String
 */

	public String getWeightUnit() {
		return weightUnit;
	}

/**
 *
 * @param weightUnit
 */
	public void setWeightUnit(String weightUnit) {
		this.weightUnit = weightUnit;
	}

/**
 *
 * @return String
 */
	public String getProductCode() {
		return productCode;
	}

/**
 *
 * @param productCode
 */

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

/**
 *
 * @return String
 */

	public String getSubProductCode() {
		return subProductCode;
	}
/**
 *
 * @param subProductCode
 */


	public void setSubProductCode(String subProductCode) {
		this.subProductCode = subProductCode;
	}

/**
 *
 * @return String
 */
	public String getAddRestriction() {
		return addRestriction;
	}

/**
 *
 * @param addRestriction
 */

	public void setAddRestriction(String addRestriction) {
		this.addRestriction = addRestriction;
	}
/**
 *
 * @return String
 */

	public String getCommodityStatus() {
		return commodityStatus;
	}

/**
 *
 * @param commodityStatus
 */

	public void setCommodityStatus(String commodityStatus) {
		this.commodityStatus = commodityStatus;
	}

/**
 *
 * @return String
 */

	public String getCustGroupStatus() {
		return custGroupStatus;
	}


/**
 *
 * @param custGroupStatus
 */
	public void setCustGroupStatus(String custGroupStatus) {
		this.custGroupStatus = custGroupStatus;
	}


/**
 *
 * @return String
 */
	public String getDestinationStatus() {
		return destinationStatus;
	}
/**
 *
 * @param destinationStatus
 */


	public void setDestinationStatus(String destinationStatus) {
		this.destinationStatus = destinationStatus;
	}
/**
 *
 * @return String
 */


	public String getOriginStatus() {
		return originStatus;
	}

/**
 *
 * @param originStatus
 */

	public void setOriginStatus(String originStatus) {
		this.originStatus = originStatus;
	}

/**
 *
 * @return String
 */

	public String getSegmentStatus() {
		return segmentStatus;
	}

/**
 *
 * @param segmentStatus
 */

	public void setSegmentStatus(String segmentStatus) {
		this.segmentStatus = segmentStatus;
	}

/**
 *
 * @return String
 */
	public String[] getCommodityCheck() {
		return commodityCheck;
	}

/**
 *
 * @param commodityCheck
 */

	public void setCommodityCheck(String[] commodityCheck) {
		this.commodityCheck = commodityCheck;
	}

/**
 *
 * @return String
 */

	public String[] getCustGroupCheck() {
		return custGroupCheck;
	}

/**
 *
 * @param custGroupCheck
 */

	public void setCustGroupCheck(String[] custGroupCheck) {
		this.custGroupCheck = custGroupCheck;
	}

/**
 *
 * @return String[]
 */

	public String[] getDestinationCheck() {
		return destinationCheck;
	}

/**
 *
 * @param destinationCheck
 */

	public void setDestinationCheck(String[] destinationCheck) {
		this.destinationCheck = destinationCheck;
	}

/**
 *
 * @return String[]
 */

	public String[] getOriginCheck() {
		return originCheck;
	}

/**
 *
 * @param originCheck
 */

	public void setOriginCheck(String[] originCheck) {
		this.originCheck = originCheck;
	}

/**
 *
 * @return String[]
 */

	public String[] getRestrictedTermsCheck() {
		return restrictedTermsCheck;
	}
/**
 *
 * @param restrictedTermsCheck
 */


	public void setRestrictedTermsCheck(String[] restrictedTermsCheck) {
		this.restrictedTermsCheck = restrictedTermsCheck;
	}
/**
 *
 * @return String[]
 */


	public String[] getSegmentCheck() {
		return segmentCheck;
	}

/**
 *
 * @param segmentCheck
 */

	public void setSegmentCheck(String[] segmentCheck) {
		this.segmentCheck = segmentCheck;
	}
/**
 *
 * @return String
 */

	public String getVersionNumber() {
		return versionNumber;
	}

/**
 *
 * @param versionNumber
 */

	public void setVersionNumber(String versionNumber) {
		this.versionNumber = versionNumber;
	}

/**
 *
 * @return  String[]
 */
	public String[] getCommodity() {
		return commodity;
	}

/**
 *
 * @param commodity
 */
	public void setCommodity(String[] commodity) {
		this.commodity = commodity;
	}
/**
 *
 * @return String[]
 */

	public String[] getCustGroup() {
		return custGroup;
	}

/**
 *
 * @param custGroup
 */
	public void setCustGroup(String[] custGroup) {
		this.custGroup = custGroup;
	}
/**
 *
 * @return String[]
 */

	public String[] getDestination() {
		return destination;
	}
/**
 *
 * @param destination
 */

	public void setDestination(String[] destination) {
		this.destination = destination;
	}
/**
 *
 * @return String[]
 */

	public String[] getOrigin() {
		return origin;
	}
/**
 *
 * @param origin
 */

	public void setOrigin(String[] origin) {
		this.origin = origin;
	}

/**
 *
 * @return String[]
 */
	public String[] getPaymentRestriction() {
		return paymentRestriction;
	}

/**
 *
 * @param paymentRestriction
 */
	public void setPaymentRestriction(String[] paymentRestriction) {
		this.paymentRestriction = paymentRestriction;
	}
/**
 *
 * @return String[]
 */

	public String[] getSegment() {
		return segment;
	}

/**
 *
 * @param segment
 */
	public void setSegment(String[] segment) {
		this.segment = segment;
	}
/**
 *
 * @return isRowModified
 */

	public String[] getIsRowModified() {
		return isRowModified;
	}

/**
 *
 * @param isRowModified
 */
	public void setIsRowModified(String[] isRowModified) {
		this.isRowModified = isRowModified;
	}
	/**
	 * @return Returns the isSegmentRowModified.
	 */
	public String[] getIsSegmentRowModified() {
		return isSegmentRowModified;
	}


	/**
	 * @param isSegmentRowModified The isSegmentRowModified to set.
	 */
	public void setIsSegmentRowModified(String[] isSegmentRowModified) {
		this.isSegmentRowModified = isSegmentRowModified;
	}

/**
 *
 * @return segmentRowId
 */
	public String[] getSegmentRowId() {
		return segmentRowId;
	}

/**
 *
 * @param segmentRowId
 */
	public void setSegmentRowId(String[] segmentRowId) {
		this.segmentRowId = segmentRowId;
	}


/**
 *
 * @return alertTime
 */

	public String[] getAlertTime() {
		return alertTime;
	}
/**
 *
 * @param alertTime
 */

	public void setAlertTime(String[] alertTime) {
		this.alertTime = alertTime;
	}
/**
 *
 * @return chaserFrequency
 */

	public String[] getChaserFrequency() {
		return chaserFrequency;
	}

/**
 *
 * @param chaserFrequency
 */
	public void setChaserFrequency(String[] chaserFrequency) {
		this.chaserFrequency = chaserFrequency;
	}
/**
 *
 * @return chaserTime
 */

	public String[] getChaserTime() {
		return chaserTime;
	}

/**
 *
 * @param chaserTime
 */
	public void setChaserTime(String[] chaserTime) {
		this.chaserTime = chaserTime;
	}
/**
 *
 * @return checkedExternal
 */

	public String getCheckedExternal() {
		return checkedExternal;
	}

/**
 *
 * @param checkedExternal
 */
	public void setCheckedExternal(String checkedExternal) {
		this.checkedExternal = checkedExternal;
	}
/**
 *
 * @return checkedInternal
 */

	public String getCheckedInternal() {
		return checkedInternal;
	}

/**
 *
 * @param checkedInternal
 */
	public void setCheckedInternal(String checkedInternal) {
		this.checkedInternal = checkedInternal;
	}
/**
 *
 * @return isExternal
 */

	public String[] getIsExternal() {
		return isExternal;
	}

/**
 *
 * @param isExternal
 */
	public void setIsExternal(String[] isExternal) {
		this.isExternal = isExternal;
	}
/**
 *
 * @return isInternal
 */

	public String[] getIsInternal() {
		return isInternal;
	}

/**
 *
 * @param isInternal
 */
	public void setIsInternal(String[] isInternal) {
		this.isInternal = isInternal;
	}

/**
 *
 * @return isNewRowModified
 */
	public String[] getIsNewRowModified() {
		return isNewRowModified;
	}

/**
 *
 * @param isNewRowModified
 */
	public void setIsNewRowModified(String[] isNewRowModified) {
		this.isNewRowModified = isNewRowModified;
	}
/**
 *
 * @return lovAction
 */

	public String getLovAction() {
		return lovAction;
	}

/**
 *
 * @param lovAction
 */
	public void setLovAction(String lovAction) {
		this.lovAction = lovAction;
	}

/**
 *
 * @return maxNoOfChasers
 */
	public String[] getMaxNoOfChasers() {
		return maxNoOfChasers;
	}
/**
 *
 * @param maxNoOfChasers
 */

	public void setMaxNoOfChasers(String[] maxNoOfChasers) {
		this.maxNoOfChasers = maxNoOfChasers;
	}
/**
 *
 * @return maxTime
 */

	public String[] getMaxTime() {
		return maxTime;
	}

/**
 *
 * @param maxTime
 */
	public void setMaxTime(String[] maxTime) {
		this.maxTime = maxTime;
	}
/**
 *
 * @return milestoneOpFlag
 */

	public String[] getMilestoneOpFlag() {
		return milestoneOpFlag;
	}

/**
 *
 * @param milestoneOpFlag
 */
	public void setMilestoneOpFlag(String[] milestoneOpFlag) {
		this.milestoneOpFlag = milestoneOpFlag;
	}
	/**
	 *
	 * @return mileStoneRowId
	 */


	public String[] getMileStoneRowId() {
		return mileStoneRowId;
	}
/**
 *
 * @param mileStoneRowId
 */

	public void setMileStoneRowId(String[] mileStoneRowId) {
		this.mileStoneRowId = mileStoneRowId;
	}
/**
 *
 * @return minTime
 */

	public String[] getMinTime() {
		return minTime;
	}
/**
 *
 * @param minTime
 */

	public void setMinTime(String[] minTime) {
		this.minTime = minTime;
	}

/**
 *
 * @return nextAction
 */
	public String getNextAction() {
		return nextAction;
	}
/**
 *
 * @param nextAction
 */

	public void setNextAction(String nextAction) {
		this.nextAction = nextAction;
	}
/**
 *
 * @return parentSession
 */

	public String getParentSession() {
		return parentSession;
	}

/**
 *
 * @param parentSession
 */
	public void setParentSession(String parentSession) {
		this.parentSession = parentSession;
	}

/**
 *
 * @return productChecked
 */
	public String[] getProductChecked() {
		return productChecked;
	}

/**
 *
 * @param productChecked
 */
	public void setProductChecked(String[] productChecked) {
		this.productChecked = productChecked;
	}

/**
 *
 * @return companyCode
 */
	public String getCompanyCode() {
		return companyCode;
	}
/**
 *
 * @param companyCode
 */

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}



	/**
	 * @return Returns the hiddenstatus.
	 */
	public String getHiddenStatus() {
		return hiddenStatus;
	}


	/**
	 * @param hiddenStatus The hiddenstatus to set.
	 */
	public void setHiddenStatus(String hiddenStatus) {
		this.hiddenStatus = hiddenStatus;
	}


	/**
	 * @return Returns the handlingInfo.
	 */
	public String getHandlingInfo() {
		return handlingInfo;
	}


	/**
	 * @param handlingInfo The handlingInfo to set.
	 */
	public void setHandlingInfo(String handlingInfo) {
		this.handlingInfo = handlingInfo;
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
	 * @return Returns the isTransit.
	 */
	public String[] getIsTransit() {
		return isTransit;
	}
	/**
	 * @param isTransit The isTransit to set.
	 */
	public void setIsTransit(String[] isTransit) {
		this.isTransit = isTransit;
	}
	/**
	 * @return Returns the checkedTransit.
	 */
	public String getCheckedTransit() {
		return checkedTransit;
	}
	/**
	 * @param checkedTransit The checkedTransit to set.
	 */
	public void setCheckedTransit(String checkedTransit) {
		this.checkedTransit = checkedTransit;
	}
	/**
	 * @return Returns the saveSuccessful.
	 */
	public String getSaveSuccessful() {
		return saveSuccessful;
	}
	/**
	 * @param saveSuccessful The saveSuccessful to set.
	 */
	public void setSaveSuccessful(String saveSuccessful) {
		this.saveSuccessful = saveSuccessful;
	}
	
	//Added now
	
	public String getFromListSubproduct() {
		return fromListSubproduct;
	}
	public void setFromListSubproduct(String fromListSubproduct) {
		this.fromListSubproduct = fromListSubproduct;
	}






}
