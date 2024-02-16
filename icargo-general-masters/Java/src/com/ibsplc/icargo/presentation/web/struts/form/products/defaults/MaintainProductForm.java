/*
 * MaintainProductForm.java Created on Jul 7, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.struts.form.products.defaults;

import org.apache.struts.upload.FormFile;

import com.ibsplc.icargo.framework.model.ScreenModel;

/**
 *
 * @author A-1754
 *
 */
public class MaintainProductForm extends ScreenModel {


	private static final String RESTRICT = "Restrict";

	private String tab;

	private String companyCode;

	private String mode;
	
	private FormFile productIcon;

	private String canSave = "N";

	private String productCode;

	private String hiddenProductCode;

	private String[] mileStoneRowId;

	private String productName = "";

	private String productStatus = "";

	private String productDesc = "";

	private String startDate = "";

	private String endDate = "";

	private FormFile icon = null;

	private String detailDesc = "";

	private String handlingInfo = "";

	private String remarks = "";

	private String minWeight = "";

	private String maxWeight = "";

	private String maxVolume = "";

	private String minVolume = "";

	private String weightUnit = "";

	private String volumeUnit = "";

	private String addRestriction = "";

	// Added as part of CR ICRD-237928 by A-8154
	/** The is override capacity. */
	private String overrideCapacity;

	/** The is rate defined. */
	private boolean isRateDefined;
	
	private boolean isBookingMand;
	private boolean coolProduct;
	
	private boolean displayInPortal;//Added for ICRD-352832
	private String productPriority;//Added for ICRD-350746
	private String[] serviceDescription;

	private String[] milestone;

	private String[] segmentRowId;

	private String[] isSegmentRowModified;

	private String[] segmentOperationFlag;

	private String[] productServiceCheck; // property for the services
											// checkbox

	private String[] transportModeCheck;

	private String[] priorityCheck;

	private String[] sccCheck;

	private String[] commodityCheck;

	private String[] segmentCheck;

	private String[] originCheck;

	private String[] destinationCheck;

	private String[] custGroupCheck;

	private String[] restrictedTermsCheck;

	private String[] subProductChecked;

	// The variables to be set as hidden field for table data
	private String[] transportMode;

	private String[] priority;

	private String[] sccCode;

	private String[] commodity;

	private String[] origin;

	private String[] destination;

	private String[] segment;

	private String[] custGroup;

	private String[] paymentRestriction;

	private String[] productServices;

	// For radio buttons
	private String commodityStatus = RESTRICT; // for radion button placed on
												// top of commodity

	private String originStatus = RESTRICT;

	private String destinationStatus = RESTRICT;

	private String segmentStatus = RESTRICT;

	private String custGroupStatus = RESTRICT;

	// variables for milestone tab

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

	// variables for CheckAll Check boxes
	private String checkAllTransportmode;

	// The variable for ProductListing

	private String[] productChecked;

	private String nextAction = "";

	private String parentSession = "";

	private String lovAction = "";

	private String[] services;

	private static final String BUNDLE = "MaintainProduct"; // The key attribute specified in struts_config.xml file.

	private String bundle;
	
	private FormFile popupIcon=null;
	
	private String iconStr="";
	
	private String TABPANE_ID_FLD;
	
	private String buttonStatusFlag="";

	private String fromListProduct="";
	
	/*
	 * Added for Document type and sub type
	 */
	private String docType;
	
	private String subType;
	
	private String maxDimension = "";
	
	private String minDimension = "";
	
	private String dimensionUnit = "";
	
	private String[] paramCode;
	private String[] paramValue;
	private String[] paramDesc;
	/*
	 * Added for checkbox isProactiveMilestoneEnabled 
	 */
	private boolean isProactiveMilestoneEnabled;
	/**the productCategory**/
	private String productCategory; //Added for ICRD-166985 by A-5117

	
	/**
	 * @return the productCategory
	 */
	public String getProductCategory() {
		return productCategory;
	}
	/**
	 * @param productCategory the productCategory to set
	 */
	public void setProductCategory(String productCategory) {
		this.productCategory = productCategory;
	}
		//Added now
		/**
	 * @return Returns the fromListProduct.
	 */
	public String getFromListProduct() {
		return fromListProduct;
	}
	/**
	 * @param fromListProduct The fromListProduct to set.
	 */
	public void setFromListProduct(String fromListProduct) {
		this.fromListProduct = fromListProduct;
	}
		/**
	 * @return Returns the iconStr.
	 */
	public String getIconStr() {
		return iconStr;
	}
	/**
	 * @param iconStr The iconStr to set.
	 */
	public void setIconStr(String iconStr) {
		this.iconStr = iconStr;
	}
		/**
		 * @return Returns the productIcon.
		 */
		public FormFile getProductIcon() {
			return productIcon;
		}
		/**
		 * @param productIcon The productIcon to set.
		 */
		public void setProductIcon(FormFile productIcon) {
			this.productIcon = productIcon;
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
	 * @return Returns the services.
	 */
	public String[] getServices() {
		return services;
	}

	/**
	 * @param services
	 *            The services to set.
	 */
	public void setServices(String[] services) {
		this.services = services;
	}

	private String[] selectedComdty;

	/**
	 * @return Returns the services.
	 */
	public String[] getSelectedComdty() {
		return selectedComdty;
	}

	/**
	 * @param selectedComdty
	 */
	public void setSelectedComdty(String[] selectedComdty) {
		this.selectedComdty = selectedComdty;
	}

	/**
	 * @return Returns the addRestriction.
	 */
	public String getAddRestriction() {
		return addRestriction;
	}

	/**
	 * @param addRestriction
	 *            The addRestriction to set.
	 */
	public void setAddRestriction(String addRestriction) {
		this.addRestriction = addRestriction;
	}

	/**
	 * Gets the checks if override capacity.
	 *
	 * @return the checks if override capacity
	 */
	public String getOverrideCapacity() {
		return overrideCapacity;
	}
	/**
	 * Sets the checks if is override capacity.
	 *
	 * @param overrideCapacity the new checks if is override capacity
	 */
	public void setOverrideCapacity(String overrideCapacity) {
		this.overrideCapacity = overrideCapacity;
	}
	/**
	 * Gets the detail desc.
	 *
	 * @return Returns the detailDesc.
	 */
	public String getDetailDesc() {
		return detailDesc;
	}

	/**
	 * @param detailDesc
	 *            The detailDesc to set.
	 */
	public void setDetailDesc(String detailDesc) {
		this.detailDesc = detailDesc;
	}

	/**
	 * @return Returns the endDate.
	 */
	public String getEndDate() {
		return endDate;
	}

	/**
	 * @param endDate
	 *            The endDate to set.
	 */
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	/**
	 * @return Returns the handlingInfo.
	 */
	public String getHandlingInfo() {
		return handlingInfo;
	}

	/**
	 * @param handlingInfo
	 *            The handlingInfo to set.
	 */
	public void setHandlingInfo(String handlingInfo) {
		this.handlingInfo = handlingInfo;
	}

	/**
	 * @return Returns the icon.
	 */
	public FormFile getIcon() {
		return icon;
	}

	/**
	 * @param icon
	 *            The icon to set.
	 */
	public void setIcon(FormFile icon) {
		this.icon = icon;
	}

	/**
	 * @return Returns the maxVolume.
	 */
	public String getMaxVolume() {
		return maxVolume;
	}

	/**
	 *            The maxVolume to set.
	 */
	public void setMaxVolume(String maxVolume) {
		this.maxVolume = maxVolume;
	}

	/**
	 * @return Returns the maxWeight.
	 */
	public String getMaxWeight() {
		return maxWeight;
	}

	/**
	 * @param maxWeight
	 *            The maxWeight to set.
	 */
	public void setMaxWeight(String maxWeight) {
		this.maxWeight = maxWeight;
	}

	/**
	 * @return Returns the minVolume.
	 */
	public String getMinVolume() {
		return minVolume;
	}

	/**
	 * @param minVolume
	 *            The minVolume to set.
	 */
	public void setMinVolume(String minVolume) {
		this.minVolume = minVolume;
	}

	/**
	 * @return Returns the minWeight.
	 */
	public String getMinWeight() {
		return minWeight;
	}

	/**
	 * @param minWeight
	 *            The minWeight to set.
	 */
	public void setMinWeight(String minWeight) {
		this.minWeight = minWeight;
	}

	/**
	 * @return Returns the productDesc.
	 */
	public String getProductDesc() {
		return productDesc;
	}

	/**
	 * @param productDesc
	 *            The productDesc to set.
	 */
	public void setProductDesc(String productDesc) {
		this.productDesc = productDesc;
	}

	/**
	 * @return Returns the productName.
	 */
	public String getProductName() {
		return productName;
	}

	/**
	 * @param productName
	 *            The productName to set.
	 */
	public void setProductName(String productName) {
		this.productName = productName;
	}

	/**
	 * @return Returns the productStatus.
	 */
	public String getProductStatus() {
		return productStatus;
	}

	/**
	 * @param productStatus
	 *            The productStatus to set.
	 */
	public void setProductStatus(String productStatus) {
		this.productStatus = productStatus;
	}

	/**
	 * @return Returns the remarks.
	 */
	public String getRemarks() {
		return remarks;
	}

	/**
	 * @param remarks
	 *            The remarks to set.
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	/**
	 * @return Returns the startDate.
	 */
	public String getStartDate() {
		return startDate;
	}

	/**
	 * @param startDate
	 *            The startDate to set.
	 */
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	/**
	 * @return Returns the volumeUnit.
	 */
	public String getVolumeUnit() {
		return volumeUnit;
	}

	/**
	 * @param volumeUnit
	 *            The volumeUnit to set.
	 */
	public void setVolumeUnit(String volumeUnit) {
		this.volumeUnit = volumeUnit;
	}

	/**
	 * @return Returns the weightUnit.
	 */
	public String getWeightUnit() {
		return weightUnit;
	}

	/**
	 * @param weightUnit
	 *            The weightUnit to set.
	 */
	public void setWeightUnit(String weightUnit) {
		this.weightUnit = weightUnit;
	}

	/**
	 * @return Returns the productService.
	 */
	public String[] getProductServiceCheck() {
		return productServiceCheck;
	}

	/**
	 * @param productServiceCheck
	 *            The productService to set.
	 */
	public void setProductServiceCheck(String[] productServiceCheck) {
		this.productServiceCheck = productServiceCheck;
	}

	/**
	 * @return Returns the commodity.
	 */
	public String[] getCommodity() {
		return commodity;
	}

	/**
	 * @param commodity
	 *            The commodity to set.
	 */
	public void setCommodity(String[] commodity) {
		this.commodity = commodity;
	}

	/**
	 * @return Returns the custGroup.
	 */
	public String[] getCustGroup() {
		return custGroup;
	}

	/**
	 * @param custGroup
	 *            The custGroup to set.
	 */
	public void setCustGroup(String[] custGroup) {
		this.custGroup = custGroup;
	}

	/**
	 * @return Returns the deatination.
	 */
	public String[] getDestination() {
		return destination;
	}

	/**
	 * @param destination
	 *            The deatination to set.
	 */
	public void setDestination(String[] destination) {
		this.destination = destination;
	}

	/**
	 * @return Returns the origin.
	 */
	public String[] getOrigin() {
		return origin;
	}

	/**
	 * @param origin
	 *            The origin to set.
	 */
	public void setOrigin(String[] origin) {
		this.origin = origin;
	}

	/**
	 * @return Returns the paymentRestriction.
	 */
	public String[] getPaymentRestriction() {
		return paymentRestriction;
	}

	/**
	 * @param paymentRestriction
	 *            The paymentRestriction to set.
	 */
	public void setPaymentRestriction(String[] paymentRestriction) {
		this.paymentRestriction = paymentRestriction;
	}

	/**
	 * @return Returns the priority.
	 */
	public String[] getPriority() {
		return priority;
	}

	/**
	 * @param priority
	 *            The priority to set.
	 */
	public void setPriority(String[] priority) {
		this.priority = priority;
	}

	/**
	 * @return Returns the sccCode.
	 */
	public String[] getSccCode() {
		return sccCode;
	}

	/**
	 * @param sccCode
	 *            The sccCode to set.
	 */
	public void setSccCode(String[] sccCode) {
		this.sccCode = sccCode;
	}

	/**
	 * @return Returns the segment.
	 */
	public String[] getSegment() {
		return segment;
	}

	/**
	 * @param segment
	 *            The segment to set.
	 */
	public void setSegment(String[] segment) {
		this.segment = segment;
	}

	/**
	 * @return Returns the transportMode.
	 */
	public String[] getTransportMode() {
		return transportMode;
	}

	/**
	 * @param transportMode
	 *            The transportMode to set.
	 */
	public void setTransportMode(String[] transportMode) {
		this.transportMode = transportMode;
	}

	/**
	 * @return Returns the commodityStatus.
	 */
	public String getCommodityStatus() {
		return commodityStatus;
	}

	/**
	 * @param commodityStatus
	 *            The commodityStatus to set.
	 */
	public void setCommodityStatus(String commodityStatus) {
		this.commodityStatus = commodityStatus;
	}

	/**
	 * @return Returns the originStatus.
	 */
	public String getOriginStatus() {
		return originStatus;
	}

	/**
	 * @param originStatus
	 *            The originStatus to set.
	 */
	public void setOriginStatus(String originStatus) {
		this.originStatus = originStatus;
	}

	/**
	 * @return Returns the custGroupStatus.
	 */
	public String getCustGroupStatus() {
		return custGroupStatus;
	}

	/**
	 * @param custGroupStatus
	 *            The custGroupStatus to set.
	 */
	public void setCustGroupStatus(String custGroupStatus) {
		this.custGroupStatus = custGroupStatus;
	}

	/**
	 * @return Returns the destinationStatus.
	 */
	public String getDestinationStatus() {
		return destinationStatus;
	}

	/**
	 * @param destinationStatus
	 *            The destinationStatus to set.
	 */
	public void setDestinationStatus(String destinationStatus) {
		this.destinationStatus = destinationStatus;
	}

	/**
	 * @return Returns the segmentStatus.
	 */
	public String getSegmentStatus() {
		return segmentStatus;
	}

	/**
	 * @param segmentStatus
	 *            The segmentStatus to set.
	 */
	public void setSegmentStatus(String segmentStatus) {
		this.segmentStatus = segmentStatus;
	}

	/**
	 * @return Returns the transportModeCheck.
	 */
	public String[] getTransportModeCheck() {
		return transportModeCheck;
	}

	/**
	 * @param transportModeCheck
	 *            The transportModeCheck to set.
	 */
	public void setTransportModeCheck(String[] transportModeCheck) {
		this.transportModeCheck = transportModeCheck;
	}

	/**
	 * @return Returns the priorityCheck.
	 */
	public String[] getPriorityCheck() {
		return priorityCheck;
	}

	/**
	 * @param priorityCheck
	 *            The priorityCheck to set.
	 */
	public void setPriorityCheck(String[] priorityCheck) {
		this.priorityCheck = priorityCheck;
	}

	/**
	 * @return Returns the commodityCheck.
	 */
	public String[] getCommodityCheck() {
		return commodityCheck;
	}

	/**
	 * @param commodityCheck
	 *            The commodityCheck to set.
	 */
	public void setCommodityCheck(String[] commodityCheck) {
		this.commodityCheck = commodityCheck;
	}

	/**
	 * @return Returns the custGroupCheck.
	 */
	public String[] getCustGroupCheck() {
		return custGroupCheck;
	}

	/**
	 * @param custGroupCheck
	 *            The custGroupCheck to set.
	 */
	public void setCustGroupCheck(String[] custGroupCheck) {
		this.custGroupCheck = custGroupCheck;
	}

	/**
	 * @return Returns the destinationcCheck.
	 */
	public String[] getDestinationCheck() {
		return destinationCheck;
	}

	/**
	 * @param destinationCheck
	 *            The destinationcCheck to set.
	 */
	public void setDestinationCheck(String[] destinationCheck) {
		this.destinationCheck = destinationCheck;
	}

	/**
	 * @return Returns the originCheck.
	 */
	public String[] getOriginCheck() {
		return originCheck;
	}

	/**
	 * @param originCheck
	 *            The originCheck to set.
	 */
	public void setOriginCheck(String[] originCheck) {
		this.originCheck = originCheck;
	}

	/**
	 * @return Returns the restrictedTermsCheck.
	 */
	public String[] getRestrictedTermsCheck() {
		return restrictedTermsCheck;
	}

	/**
	 * @param restrictedTermsCheck
	 *            The restrictedTermsCheck to set.
	 */
	public void setRestrictedTermsCheck(String[] restrictedTermsCheck) {
		this.restrictedTermsCheck = restrictedTermsCheck;
	}

	/**
	 * @return Returns the sccCheck.
	 */
	public String[] getSccCheck() {
		return sccCheck;
	}

	/**
	 * @param sccCheck
	 *            The sccCheck to set.
	 */
	public void setSccCheck(String[] sccCheck) {
		this.sccCheck = sccCheck;
	}

	/**
	 * @return Returns the segmentCheck.
	 */
	public String[] getSegmentCheck() {
		return segmentCheck;
	}

	/**
	 * @param segmentCheck
	 *            The segmentCheck to set.
	 */
	public void setSegmentCheck(String[] segmentCheck) {
		this.segmentCheck = segmentCheck;
	}

	/**
	 *
	 * @return mode
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
	 * @return alert time
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
	 * @return Returns the milestoneOpFlag.
	 */
	public String[] getMilestoneOpFlag() {
		return milestoneOpFlag;
	}

	/**
	 * @param milestoneOpFlag
	 *            The milestoneOpFlag to set.
	 */
	public void setMilestoneOpFlag(String[] milestoneOpFlag) {
		this.milestoneOpFlag = milestoneOpFlag;
	}

	/**
	 * @return Returns the isRowModified.
	 */
	public String[] getIsRowModified() {
		return isRowModified;
	}

	/**
	 * @param isRowModified
	 *            The isRowModified to set.
	 */
	public void setIsRowModified(String[] isRowModified) {
		this.isRowModified = isRowModified;
	}

	/**
	 * @return Returns the subProductChecked.
	 */
	public String[] getSubProductChecked() {
		return subProductChecked;
	}

	/**
	 * @param subProductChecked
	 *            The subProductChecked to set.
	 */
	public void setSubProductChecked(String[] subProductChecked) {
		this.subProductChecked = subProductChecked;
	}

	/**
	 * @return Returns the productServices.
	 */
	public String[] getProductServices() {
		return productServices;
	}

	/**
	 * @param productServices
	 *            The productServices to set.
	 */
	public void setProductServices(String[] productServices) {
		this.productServices = productServices;
	}

	/**
	 * @return Returns the checkAllTransportmode.
	 */
	public String getCheckAllTransportmode() {
		return checkAllTransportmode;
	}

	/**
	 * @param checkAllTransportmode
	 *            The checkAllTransportmode to set.
	 */
	public void setCheckAllTransportmode(String checkAllTransportmode) {
		this.checkAllTransportmode = checkAllTransportmode;
	}

	/**
	 * @return Returns the serviceDescription.
	 */
	public String[] getServiceDescription() {
		return serviceDescription;
	}

	/**
	 * @param serviceDescription
	 *            The serviceDescription to set.
	 */
	public void setServiceDescription(String[] serviceDescription) {
		this.serviceDescription = serviceDescription;
	}

	/**
	 * @return Returns the milestone.
	 */
	public String[] getMilestone() {
		return milestone;
	}

	/**
	 * @param milestone
	 *            The milestone to set.
	 */
	public void setMilestone(String[] milestone) {
		this.milestone = milestone;
	}

	/**
	 * @return Returns the segmentRowId.
	 */
	public String[] getSegmentRowId() {
		return segmentRowId;
	}

	/**
	 * @param segmentRowId
	 *            The segmentRowId to set.
	 */
	public void setSegmentRowId(String[] segmentRowId) {
		this.segmentRowId = segmentRowId;
	}

	/**
	 * @return Returns the isSegmentRowModified.
	 */
	public String[] getIsSegmentRowModified() {
		return isSegmentRowModified;
	}

	/**
	 * @param isSegmentRowModified
	 *            The isSegmentRowModified to set.
	 */
	public void setIsSegmentRowModified(String[] isSegmentRowModified) {
		this.isSegmentRowModified = isSegmentRowModified;
	}

	/**
	 * @return Returns the segmentOperationFlag.
	 */
	public String[] getSegmentOperationFlag() {
		return segmentOperationFlag;
	}

	/**
	 * @param segmentOperationFlag
	 *            The segmentOperationFlag to set.
	 */
	public void setSegmentOperationFlag(String[] segmentOperationFlag) {
		this.segmentOperationFlag = segmentOperationFlag;
	}

	/**
	 * @return Returns the productChecked.
	 */
	public String[] getProductChecked() {
		return productChecked;
	}

	/**
	 * @param productChecked
	 *            The productChecked to set.
	 */
	public void setProductChecked(String[] productChecked) {
		this.productChecked = productChecked;
	}

	/**
	 * @return Returns the lovAction.
	 */
	public String getLovAction() {
		return lovAction;
	}

	/**
	 * @param lovAction
	 *            The lovAction to set.
	 */
	public void setLovAction(String lovAction) {
		this.lovAction = lovAction;
	}

	/**
	 * @return Returns the nextAction.
	 */
	public String getNextAction() {
		return nextAction;
	}

	/**
	 * @param nextAction
	 *            The nextAction to set.
	 */
	public void setNextAction(String nextAction) {
		this.nextAction = nextAction;
	}

	/**
	 * @return Returns the parentSession.
	 */
	public String getParentSession() {
		return parentSession;
	}

	/**
	 * @param parentSession
	 *            The parentSession to set.
	 */
	public void setParentSession(String parentSession) {
		this.parentSession = parentSession;
	}

	/**
	 * @return Returns the isNewRowModified.
	 */
	public String[] getIsNewRowModified() {
		return isNewRowModified;
	}

	/**
	 * @param isNewRowModified
	 *            The isNewRowModified to set.
	 */
	public void setIsNewRowModified(String[] isNewRowModified) {
		this.isNewRowModified = isNewRowModified;
	}

	/**
	 * The method to validate the form fields (non-Javadoc)
	 *
	 * @see com.ibsplc.icargo.framework.struts.form.AbstractActionForm#validateForm()
	 */
	/*
	 * public ActionMessages validateForm(){ ActionMessages actionMessages;
	 * boolean isValid = true; actionMessages = new ActionMessages();
	 * if(productName==""){ isValid = false;
	 * actionMessages.add(GLOBAL_MESSAGE,new ActionMessage("products.defaults.enterproductname"));
	 * }if(startDate==""){ isValid = false;
	 * actionMessages.add(GLOBAL_MESSAGE,new ActionMessage("products.defaults.inavlidstartdate"));
	 * }if(endDate==""){ isValid = false; actionMessages.add(GLOBAL_MESSAGE,new
	 * ActionMessage("products.defaults.inavlidenddate")); }if(transportMode==null){ isValid = false;
	 * actionMessages.add(GLOBAL_MESSAGE,new ActionMessage("products.defaults.selecttramode"));
	 * }if(priority==null){ isValid = false;
	 * actionMessages.add(GLOBAL_MESSAGE,new ActionMessage("products.defaults.selectpriority"));
	 * }if(sccCode==null){ isValid = false;
	 * actionMessages.add(GLOBAL_MESSAGE,new ActionMessage("products.defaults.selectscc"));
	 * }if(productServices==null){ isValid = false;
	 * actionMessages.add(GLOBAL_MESSAGE,new ActionMessage("products.defaults.selectservice"));
	 * }if(!DateUtilities.isLessThan(startDate,endDate,"dd-MMM-yyyy")){
	 * System.out.println("Inside isLessthan start date"); isValid = false;
	 * actionMessages.add(GLOBAL_MESSAGE,new ActionMessage("products.defaults.startdategreaterthancurrentdate")); }
	 *
	 * if(segment!=null){ if(RESTRICT.equals(segmentStatus)){
	 * if(ALLOW.equals(originStatus)){ if(origin!=null){ for(int j=0;j<origin.length;j++){
	 * for(int i=0;i<segment.length;i++){ StringTokenizer tok = new
	 * StringTokenizer(segment[i],"-"); int count=0; String[] seperateToken =
	 * new String[2]; seperateToken[0]=""; seperateToken[1]="";
	 * while(tok.hasMoreTokens()){ seperateToken[count] = tok.nextToken();
	 * System.out.println("Seperating hidden Fields External::" +count+"---->"+
	 * seperateToken[count]); count++; } if(origin[j].equals(seperateToken[0])){
	 * isValid = false; actionMessages.add(GLOBAL_MESSAGE,new
	 * ActionMessage("products.defaults.segviolorgallow")); break; } } } } }
	 *
	 * if(ALLOW.equals(destinationStatus)){ if(destination!=null){ for(int j=0;j<destination.length;j++){
	 * for(int i=0;i<segment.length;i++){ StringTokenizer tok = new
	 * StringTokenizer(segment[i],"-"); int count=0; String[] seperateToken =
	 * new String[2]; seperateToken[0]=""; seperateToken[1]="";
	 * while(tok.hasMoreTokens()){ seperateToken[count] = tok.nextToken();
	 * System.out.println("Seperating hidden Fields External::" +count+"---->"+
	 * seperateToken[count]); count++; }
	 * if(destination[j].equals(seperateToken[1])){ isValid = false;
	 * actionMessages.add(GLOBAL_MESSAGE,new ActionMessage("products.defaults.segvioldstallow")); break; } } } } }
	 *
	 * }else if(ALLOW.equals(segmentStatus)){ if(RESTRICT.equals(originStatus)){
	 * if(origin!=null){ for(int j=0;j<origin.length;j++){ for(int i=0;i<segment.length;i++){
	 * StringTokenizer tok = new StringTokenizer(segment[i],"-"); int count=0;
	 * String[] seperateToken = new String[2]; seperateToken[0]="";
	 * seperateToken[1]=""; while(tok.hasMoreTokens()){ seperateToken[count] =
	 * tok.nextToken(); System.out.println("Seperating hidden Fields External::"
	 * +count+"---->"+ seperateToken[count]); count++; }
	 * if(origin[j].equals(seperateToken[0])){ isValid = false;
	 * actionMessages.add(GLOBAL_MESSAGE,new ActionMessage("products.defaults.segallowvilolorgrst")); break; } } } } }
	 *
	 * if(RESTRICT.equals(destinationStatus)){ if(destination!=null){ for(int
	 * j=0;j<destination.length;j++){ for(int i=0;i<segment.length;i++){
	 * StringTokenizer tok = new StringTokenizer(segment[i],"-"); int count=0;
	 * String[] seperateToken = new String[2]; seperateToken[0]="";
	 * seperateToken[1]=""; while(tok.hasMoreTokens()){ seperateToken[count] =
	 * tok.nextToken(); System.out.println("Seperating hidden Fields External::"
	 * +count+"---->"+ seperateToken[count]); count++; }
	 * if(destination[j].equals(seperateToken[1])){ isValid = false;
	 * actionMessages.add(GLOBAL_MESSAGE,new ActionMessage("products.defaults.segallowviloldstrst")); break; } } } } }
	 *  } }if(!"".equals(minWeight.trim()) && !"".equals(maxWeight.trim())){
	 * if(Double.parseDouble(minWeight)> Double.parseDouble(maxWeight)){ isValid =
	 * false; actionMessages.add(GLOBAL_MESSAGE,new ActionMessage("products.defaults.minweightexceedsmaxweight")); }
	 *
	 * }if(!"".equals(minVolume) && !"".equals(maxVolume)){
	 * if(Double.parseDouble(minVolume)> Double.parseDouble(maxVolume)){ isValid =
	 * false; actionMessages.add(GLOBAL_MESSAGE,new ActionMessage("products.defaults.minvolexceedsmaxvol")); }
	 *  } if(minTime!=null && maxTime!=null){ for(int i=0;i<minTime.length;i++){
	 * if("".equals(minTime[i].trim())){ isValid = false;
	 * actionMessages.add(GLOBAL_MESSAGE,new ActionMessage("products.defaults.enterminormaxtim")); }else
	 * if("".equals(minTime[i].trim())){ isValid = false;
	 * actionMessages.add(GLOBAL_MESSAGE,new ActionMessage("ERR_1024")); }else{
	 * if(Double.parseDouble(minTime[i]) > Double.parseDouble(maxTime[i])){
	 * isValid = false; actionMessages.add(GLOBAL_MESSAGE,new
	 * ActionMessage("products.defaults.mintimexceedsmaxtim")); } } } }
	 *
	 * if(isValid &&
	 * DateUtilities.isLessThan(startDate,DateUtilities.getCurrentDate("dd-MMM-yyyy"),"dd-MMM-yyyy")){
	 * if(!MODIFY_MODE.equals(mode)){ if("N".equals(canSave)){
	 * System.out.println("Inside isLessthan current");
	 * actionMessages.add("WARNING",new ActionMessage("products.defaults.prdstagrtthancurrentdate")); } } }
	 *
	 * return actionMessages; }
	 */

	/**
	 * @return Returns the isRateDefined.
	 */
	public boolean isRateDefined() {
		return isRateDefined;
	}

	/**
	 * @param isRateDefined
	 *            The isRateDefined to set.
	 */
	public void setRateDefined(boolean isRateDefined) {
		this.isRateDefined = isRateDefined;
	}

	/**
	 * @return Returns the chaserFrequency.
	 */
	public String[] getChaserFrequency() {
		return chaserFrequency;
	}

	/**
	 * @param chaserFrequency
	 *            The chaserFrequency to set.
	 */
	public void setChaserFrequency(String[] chaserFrequency) {
		this.chaserFrequency = chaserFrequency;
	}

	/**
	 * @return Returns the maxNoOfChasers.
	 */
	public String[] getMaxNoOfChasers() {
		return maxNoOfChasers;
	}

	/**
	 * @param maxNoOfChasers
	 *            The maxNoOfChasers to set.
	 */
	public void setMaxNoOfChasers(String[] maxNoOfChasers) {
		this.maxNoOfChasers = maxNoOfChasers;
	}

	/**
	 * @return Returns the productCode.
	 */
	public String getProductCode() {
		return productCode;
	}

	/**
	 * @param productCode
	 *            The productCode to set.
	 */
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	/**
	 * @return Returns the hiddenProductCode.
	 */
	public String getHiddenProductCode() {
		return hiddenProductCode;
	}

	/**
	 * @param hiddenProductCode
	 *            The hiddenProductCode to set.
	 */
	public void setHiddenProductCode(String hiddenProductCode) {
		this.hiddenProductCode = hiddenProductCode;
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
	 * @return Returns the canSave.
	 */
	public String getCanSave() {
		return canSave;
	}

	/**
	 * @param canSave
	 *            The canSave to set.
	 */
	public void setCanSave(String canSave) {
		this.canSave = canSave;
	}

	/**
	 * The overiden function to return the screen id
	 *
	 * @return string
	 */
	public String getScreenId() {
		return "products.defaults.maintainproduct";
	}

	/**
	 * The overriden function to return the product name
	 *
	 * @return products
	 */
	public String getProduct() {
		return "products";
	}

	/**
	 * The overriden function to return the sub product name
	 *
	 * @return defaults
	 */
	public String getSubProduct() {
		return "defaults";
	}

	/**
	 * @return Returns the tab.
	 */
	public String getTab() {
		return tab;
	}

	/**
	 * @param tab
	 *            The tab to set.
	 */
	public void setTab(String tab) {
		this.tab = tab;
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
	 * @return Returns the popupIcon.
	 */
	public FormFile getPopupIcon() {
		return popupIcon;
	}
	/**
	 * @param popupIcon The popupIcon to set.
	 */
	public void setPopupIcon(FormFile popupIcon) {
		this.popupIcon = popupIcon;
	}
	
	/**
	 * 
	 * @return
	 */
	public boolean isBookingMand() {
		return isBookingMand;
	}
	
	/**
	 * 
	 * @param isBookingMand
	 */
	public void setBookingMand(boolean isBookingMand) {
		this.isBookingMand = isBookingMand;
	}
	/**
	 * @return Returns the tABPANE_ID_FLD.
	 */
	public String getTABPANE_ID_FLD() {
		return TABPANE_ID_FLD;
	}
	/**
	 * @param tabpane_id_fld The tABPANE_ID_FLD to set.
	 */
	public void setTABPANE_ID_FLD(String tabpane_id_fld) {
		TABPANE_ID_FLD = tabpane_id_fld;
	}
	/**
	 * @return Returns the buttonStatusFlag.
	 */
	public String getButtonStatusFlag() {
		return buttonStatusFlag;
	}
	/**
	 * @param buttonStatusFlag The buttonStatusFlag to set.
	 */
	public void setButtonStatusFlag(String buttonStatusFlag) {
		this.buttonStatusFlag = buttonStatusFlag;
	}
	/**
	 * 
	 * @return String doctype
	 */
	public String getDocType() {
		return docType;
	}
	
	/**
	 * 
	 * @param docType The docType to set
	 */
	public void setDocType(String docType) {
		this.docType = docType;
	}
	
	/**
	 * 
	 * @return String
	 */
	public String getSubType() {
		return subType;
	}
	
	/**
	 * 
	 * @param subType The subType to set
	 */
	public void setSubType(String subType) {
		this.subType = subType;
	}
	/**
	 * 
	 * @return
	 */
	public boolean isProactiveMilestoneEnabled() {
		return isProactiveMilestoneEnabled;
	}	
	/**
	 * 
	 * @param isProactiveMilestoneEnabled
	 */
	public void setProactiveMilestoneEnabled(boolean isProactiveMilestoneEnabled) {
		this.isProactiveMilestoneEnabled = isProactiveMilestoneEnabled;
	}
	
	public boolean isDisplayInPortal() {
		return displayInPortal;
	}
	public void setDisplayInPortal(boolean displayInPortal) {
		this.displayInPortal = displayInPortal;
	}
	
	public String getProductPriority() {
		return productPriority;
	}
	public void setProductPriority(String productPriority) {
		this.productPriority = productPriority;
	}
	/**
	 * @return the coolProduct
	 */
	public boolean isCoolProduct() {
		return coolProduct;
	}
	/**
	 * @param coolProduct the coolProduct to set
	 */
	public void setCoolProduct(boolean coolProduct) {
		this.coolProduct = coolProduct;
	}
	/**
	 * 	Getter for maxDimension 
	 *	Added by : A-7896 on 05-Sep-2018
	 * 	Used for :
	 */
	public String getMaxDimension() {
		return maxDimension;
	}
	/**
	 *  @param maxDimension the maxDimension to set
	 * 	Setter for maxDimension 
	 *	Added by : A-7896 on 05-Sep-2018
	 * 	Used for :ICRD-232462
	 */
	public void setMaxDimension(String maxDimension) {
		this.maxDimension = maxDimension;
	}
	/**
	 * 	Getter for minDimension 
	 *	Added by : A-7896 on 05-Sep-2018
	 * 	Used for :
	 */
	public String getMinDimension() {
		return minDimension;
	}
	/**
	 *  @param minDimension the minDimension to set
	 * 	Setter for minDimension 
	 *	Added by : A-7896 on 05-Sep-2018
	 * 	Used for :ICRD-232462
	 */
	public void setMinDimension(String minDimension) {
		this.minDimension = minDimension;
	}
	/**
	 * 	Getter for dimensionUnit 
	 *	Added by : A-7896 on 05-Sep-2018
	 * 	Used for :
	 */
	public String getDimensionUnit() {
		return dimensionUnit;
	}
	/**
	 *  @param dimensionUnit the dimensionUnit to set
	 * 	Setter for dimensionUnit 
	 *	Added by : A-7896 on 05-Sep-2018
	 * 	Used for :
	 */
	public void setDimensionUnit(String dimensionUnit) {
		this.dimensionUnit = dimensionUnit;
	}
	/**
	 * 	Getter for paramCode 
	 *	Added by : A-7740 on 04-Oct-2018
	 * 	Used for :
	 */
	public String[] getParamCode() {
		return paramCode;
	}
	/**
	 *  @param paramCode the paramCode to set
	 * 	Setter for paramCode 
	 *	Added by : A-7740 on 04-Oct-2018
	 * 	Used for :
	 */
	public void setParamCode(String[] paramCode) {
		this.paramCode = paramCode;
	}
	/**
	 * 	Getter for paramValue 
	 *	Added by : A-7740 on 04-Oct-2018
	 * 	Used for :
	 */
	public String[] getParamValue() {
		return paramValue;
	}
	/**
	 *  @param paramValue the paramValue to set
	 * 	Setter for paramValue 
	 *	Added by : A-7740 on 04-Oct-2018
	 * 	Used for :
	 */
	public void setParamValue(String[] paramValue) {
		this.paramValue = paramValue;
	}
	/**
	 * 	Getter for paramDesc 
	 *	Added by : A-7740 on 04-Oct-2018
	 * 	Used for :
	 */
	public String[] getParamDesc() {
		return paramDesc;
	}
	/**
	 *  @param paramDesc the paramDesc to set
	 * 	Setter for paramDesc 
	 *	Added by : A-7740 on 04-Oct-2018
	 * 	Used for :
	 */
	public void setParamDesc(String[] paramDesc) {
		this.paramDesc = paramDesc;
	}
}
