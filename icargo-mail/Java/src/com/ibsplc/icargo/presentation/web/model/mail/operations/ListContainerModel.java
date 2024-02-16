/*
 * ListContainerModel.java Created on Jul 04, 2018
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.presentation.web.model.mail.operations;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.web.json.PageResult;
import com.ibsplc.icargo.framework.web.spring.model.AbstractScreenModel;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.ContainerDetails;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.ContainerFilter;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.ReassignContainer;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.TransferForm;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.OnwardRouting;

/**
 * Revision History
 * Revision 	 Date      	     Author			Description
 * 0.1		Jul 04, 2018	     A-5437		First draft
 */

public class ListContainerModel extends AbstractScreenModel{

	/*
	 * The constant variable for product operations
	 */
	private static final String PRODUCT = "mail";
	/*
	 * The constant for sub product flthandling
	 */
	private static final String SUBPRODUCT = "operations";
	/*
	 * The constant for screen id
	 */
	private static final String SCREENID = "mail.operations.ux.listcontainer";	
	
	private HashMap<String, Collection<OneTimeVO>> oneTimeValues;
	
    private ContainerFilter containerFilter; 
	
	private PageResult<ContainerDetails> containerDetails;	
	//Added by A-7929 as part of ICRD-269984 starts...
    private TransferForm transferForm;
    private boolean weightScaleAvailable;//added by A-8353 for IASCB-33559
	
    private String carrierCode;
    private boolean containerModify;
    
    private String uldToBarrowAllow;
	
    //added by A-8672 as part of IASCB-50071
  	private String defaultOperatingReference;
  	
  	private String fromDate;
  	private String toDate;
  	private String mode;
  	private boolean screenWarning;
  	 private boolean estimatedChargePrivilage;	
  	public boolean isEstimatedChargePrivilage() {
  		return estimatedChargePrivilage;
  	}
  	public void setEstimatedChargePrivilage(boolean estimatedChargePrivilage) {
  		this.estimatedChargePrivilage = estimatedChargePrivilage;
  	}
	public TransferForm getTransferForm() {
		return transferForm;
	}

	public void setTransferForm(TransferForm transferForm) {
		this.transferForm = transferForm;
	}
     //Added by A-7929 as part of ICRD-269984 ends...
	
	//Added by A-8672 as part of ICRD-269984 starts...
    private Collection<OnwardRouting> onwardRouting;
    
    public Collection<OnwardRouting> getOnwardRouting() {
		return onwardRouting;
	}

	public void setOnwardRouting(Collection<OnwardRouting> onwardRouting) {
		this.onwardRouting = onwardRouting;
	}
    
	
     //Added by A-8672 as part of ICRD-269984 ends...
	
	//private ContainerActionData containerActionData; 

	private Collection<ContainerDetails> containerActionData; 
	
	private Collection<ContainerDetails> selectedContainerData; 
	
	private ReassignContainer reassignContainer; 
	private Map<String, String> warningMessagesStatus;
	
	public Collection<ContainerDetails> getSelectedContainerData() {
		return selectedContainerData;
	}

	public void setSelectedContainerData(
			Collection<ContainerDetails> selectedContainerData) {
		this.selectedContainerData = selectedContainerData;
	}

	public Collection<ContainerDetails> getContainerActionData() {
		return containerActionData;
	}

	public void setContainerActionData(
			Collection<ContainerDetails> containerActionData) {
		this.containerActionData = containerActionData;
	}

	public HashMap<String, Collection<OneTimeVO>> getOneTimeValues() {
		return oneTimeValues;
	}

	public void setOneTimeValues(HashMap<String, Collection<OneTimeVO>> oneTimeValues) {
		this.oneTimeValues = oneTimeValues;
	}	

	

	public PageResult<ContainerDetails> getContainerDetails() {
		return containerDetails;
	}

	public void setContainerDetails(PageResult<ContainerDetails> containerDetails) {
		this.containerDetails = containerDetails;
	}

	public ContainerFilter getContainerFilter() {
		return containerFilter;
	}

	public void setContainerFilter(ContainerFilter containerFilter) {
		this.containerFilter = containerFilter;
	}

	/**
	 * (non-Javadoc)
	 * @see com.ibsplc.icargo.framework.web.ScreenData#getScreenId()
	 * @return screenId
	 */
	public String getScreenId() {
		return SCREENID;
	}

	/**
	 * (non-Javadoc)
	 * @see com.ibsplc.icargo.framework.web.ScreenData#getProduct()
	 * @return product name
	 */
	public String getProduct() {
		return PRODUCT;
	}

	/**
	 * (non-Javadoc)
	 * @see com.ibsplc.icargo.framework.web.ScreenData#getSubProduct()
	 * @return subproduct name
	 */
	public String getSubProduct() {
		return SUBPRODUCT;
	}

	public ReassignContainer getReassignContainer() {
		return reassignContainer;
	}

	public void setReassignContainer(ReassignContainer reassignContainer) {
		this.reassignContainer = reassignContainer;
	}
	public boolean isWeightScaleAvailable() {
		return weightScaleAvailable;
	}
	public void setWeightScaleAvailable(boolean weightScaleAvailable) {
		this.weightScaleAvailable = weightScaleAvailable;
	}

	public String getCarrierCode() {
		return carrierCode;
	}

	public void setCarrierCode(String carrierCode) {
		this.carrierCode = carrierCode;
	}
	public boolean isContainerModify() {
		return containerModify;
	}
	public void setContainerModify(boolean containerModify) {
		this.containerModify = containerModify;
	}
	public String getUldToBarrowAllow() {
		return uldToBarrowAllow;
	}
	public void setUldToBarrowAllow(String uldToBarrowAllow) {
		this.uldToBarrowAllow = uldToBarrowAllow;
	}
	
	//added by A-8672 as part of IASCB-50071
	/**
	 * @return the defaultOperatingReference
	 */
	public String getDefaultOperatingReference() {
		return defaultOperatingReference;
	}
	/**
	 * @param defaultOperatingReference the defaultOperatingReference to set
	 */
	public void setDefaultOperatingReference(String defaultOperatingReference) {
		this.defaultOperatingReference = defaultOperatingReference;
	}

	/**
	 * @return the fromDate
	 */
	public String getFromDate() {
		return fromDate;
	}

	/**
	 * @param fromDate the fromDate to set
	 */
	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}

	/**
	 * @return the toDate
	 */
	public String getToDate() {
		return toDate;
	}

	/**
	 * @param toDate the toDate to set
	 */
	public void setToDate(String toDate) {
		this.toDate = toDate;
	}
	public String getMode() {
		return mode;
	}
	public void setMode(String mode) {
		this.mode = mode;
	}

	public Map<String, String> getWarningMessagesStatus() {
		return warningMessagesStatus;
	}

	public void setWarningMessagesStatus(Map<String, String> warningMessagesStatus) {
		this.warningMessagesStatus = warningMessagesStatus;
	}

	public boolean isScreenWarning() {
		return screenWarning;
	}

	public void setScreenWarning(boolean screenWarning) {
		this.screenWarning = screenWarning;
	}

	
	
	
}
