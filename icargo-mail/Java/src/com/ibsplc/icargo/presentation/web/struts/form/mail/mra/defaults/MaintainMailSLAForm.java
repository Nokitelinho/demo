/*
 * MaintainMailSLAForm.java Created on Mar 30, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults;

import java.util.Collection;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.SLADetailsVO;
import com.ibsplc.icargo.framework.model.ScreenModel;

/**
 * @author a-2524
 *
 */
public class MaintainMailSLAForm extends ScreenModel {

	private static final String BUNDLE ="maintainmailslaresources";
	
	private static final String PRODUCT = "mail";

	private static final String SUBPRODUCT = "mra";

	private static final String SCREENID = "mailtracking.mra.defaults.maintainmailsla";

	private String slaId;
	
	private String description;
	
	private String currency;
	
	private String[] mailCategory;
	
	private String[] serviceTime;
	
	private String[] alertTime;
	
	private String[] chaserTime;
	
	private String[] chaserFrequency;
	
	private String[] maxNumberOfChasers;
	
	private String[] claimRate;

	private String[] rowId; 
	
	private String[] serialNumber;
	
	//	operation flag for child vos
	private String[] operationFlag; 
		
	//	operation flag for parent vo
	private String operationFlagParent; 
	
	private String newSLAOptionFlag;
	
	private String currentDialogOption;
	
	private String currentDialogId;
	
	// Flags for adddelete limks
	private String linkStatus;
	
	
	// for copySLA popup
	private String selectedValues;	
	private String lastPageNum;
	private String displayPage;
	private String multiselect;
	private String slaIdrForPopup;
	private String[] slaID; 
	private String[] checkIdChild;
	private String callPopup;
	private String reloadParent;
	
	// Added for integration with Monitor Mail SLA
	
	private String closeStatusFlag;
	
	
	private Collection<SLADetailsVO> slaDetailsVos;
	
	
	/**
	 * Constructor
	 */
	public MaintainMailSLAForm() {
		super();
		displayPage="1";
		lastPageNum="0";
		multiselect = "Y";
		selectedValues="";
	}
	

	/**
	 * @return Returns the BUNDLE.
	 */
	public String getBundle() {
		return BUNDLE;
	}

	/**
	 * @return Returns the chaserFrequency.
	 */
	public String[] getChaserFrequency() {
		return chaserFrequency;
	}

	/**
	 * @param chaserFrequency The chaserFrequency to set.
	 */
	public void setChaserFrequency(String[] chaserFrequency) {
		this.chaserFrequency = chaserFrequency;
	}

	/**
	 * @return Returns the claimRate.
	 */
	public String[] getClaimRate() {
		return claimRate;
	}

	/**
	 * @param claimRate The claimRate to set.
	 */
	public void setClaimRate(String[] claimRate) {
		this.claimRate = claimRate;
	}

	/**
	 * @return Returns the currency.
	 */
	public String getCurrency() {
		return currency;
	}

	/**
	 * @param currency The currency to set.
	 */
	public void setCurrency(String currency) {
		this.currency = currency;
	}

	/**
	 * @return Returns the description.
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description The description to set.
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return Returns the mailCategory.
	 */
	public String[] getMailCategory() {
		return mailCategory;
	}

	/**
	 * @param mailCategory The mailCategory to set.
	 */
	public void setMailCategory(String[] mailCategory) {
		this.mailCategory = mailCategory;
	}

	/**
	 * @return Returns the maxNumberOfChasers.
	 */
	public String[] getMaxNumberOfChasers() {
		return maxNumberOfChasers;
	}

	/**
	 * @param maxNumberOfChasers The maxNumberOfChasers to set.
	 */
	public void setMaxNumberOfChasers(String[] maxNumberOfChasers) {
		this.maxNumberOfChasers = maxNumberOfChasers;
	}

	/**
	 * @return Returns the slaId.
	 */
	public String getSlaId() {
		return slaId;
	}

	/**
	 * @param slaId The slaId to set.
	 */
	public void setSlaId(String slaId) {
		this.slaId = slaId;
	}


	/**
	 * @return Returns the sCREENID.
	 */
	public String getScreenId() {
		return SCREENID;
	}

	/**
	 * @return Returns the pRODUCT.
	 */
	public String getProduct() {
		return PRODUCT;
	}

	/**
	 * @return Returns the sUBPRODUCT.
	 */
	public String getSubProduct() {
		return SUBPRODUCT;
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

	/**
	 * @return Returns the operationFlag.
	 */
	public String[] getOperationFlag() {
		return operationFlag;
	}

	/**
	 * @param operationFlag The operationFlag to set.
	 */
	public void setOperationFlag(String[] operationFlag) {
		this.operationFlag = operationFlag;
	}

	/**
	 * @return Returns the serialNumber.
	 */
	public String[] getSerialNumber() {
		return serialNumber;
	}

	/**
	 * @param serialNumber The serialNumber to set.
	 */
	public void setSerialNumber(String[] serialNumber) {
		this.serialNumber = serialNumber;
	}

	/**
	 * @return Returns the alertTime.
	 */
	public String[] getAlertTime() {
		return alertTime;
	}

	/**
	 * @param alertTime The alertTime to set.
	 */
	public void setAlertTime(String[] alertTime) {
		this.alertTime = alertTime;
	}

	/**
	 * @return Returns the chaserTime.
	 */
	public String[] getChaserTime() {
		return chaserTime;
	}

	/**
	 * @param chaserTime The chaserTime to set.
	 */
	public void setChaserTime(String[] chaserTime) {
		this.chaserTime = chaserTime;
	}

	/**
	 * @return Returns the serviceTime.
	 */
	public String[] getServiceTime() {
		return serviceTime;
	}

	/**
	 * @param serviceTime The serviceTime to set.
	 */
	public void setServiceTime(String[] serviceTime) {
		this.serviceTime = serviceTime;
	}

	/**
	 * @return Returns the operationFlagParent.
	 */
	public String getOperationFlagParent() {
		return operationFlagParent;
	}

	/**
	 * @param operationFlagParent The operationFlagParent to set.
	 */
	public void setOperationFlagParent(String operationFlagParent) {
		this.operationFlagParent = operationFlagParent;
	}

	/**
	 * @return Returns the newSLAOptionFlag.
	 */
	public String getNewSLAOptionFlag() {
		return newSLAOptionFlag;
	}

	/**
	 * @param newSLAOptionFlag The newSLAOptionFlag to set.
	 */
	public void setNewSLAOptionFlag(String newSLAOptionFlag) {
		this.newSLAOptionFlag = newSLAOptionFlag;
	}

	/**
	 * @return Returns the currentDialogId.
	 */
	public String getCurrentDialogId() {
		return currentDialogId;
	}

	/**
	 * @param currentDialogId The currentDialogId to set.
	 */
	public void setCurrentDialogId(String currentDialogId) {
		this.currentDialogId = currentDialogId;
	}

	/**
	 * @return Returns the currentDialogOption.
	 */
	public String getCurrentDialogOption() {
		return currentDialogOption;
	}

	/**
	 * @param currentDialogOption The currentDialogOption to set.
	 */
	public void setCurrentDialogOption(String currentDialogOption) {
		this.currentDialogOption = currentDialogOption;
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
	 * @return Returns the multiselect.
	 */
	public String getMultiselect() {
		return multiselect;
	}


	/**
	 * @param multiselect The multiselect to set.
	 */
	public void setMultiselect(String multiselect) {
		this.multiselect = multiselect;
	}


	/**
	 * @return Returns the selectedValues.
	 */
	public String getSelectedValues() {
		return selectedValues;
	}


	/**
	 * @param selectedValues The selectedValues to set.
	 */
	public void setSelectedValues(String selectedValues) {
		this.selectedValues = selectedValues;
	}


	/**
	 * @return Returns the slaIdrForPopup.
	 */
	public String getSlaIdrForPopup() {
		return slaIdrForPopup;
	}


	/**
	 * @param slaIdrForPopup The slaIdrForPopup to set.
	 */
	public void setSlaIdrForPopup(String slaIdrForPopup) {
		this.slaIdrForPopup = slaIdrForPopup;
	}

	/**
	 * @return Returns the callPopup.
	 */
	public String getCallPopup() {
		return callPopup;
	}


	/**
	 * @param callPopup The callPopup to set.
	 */
	public void setCallPopup(String callPopup) {
		this.callPopup = callPopup;
	}

	/**
	 * @return Returns the checkIdChild.
	 */
	public String[] getCheckIdChild() {
		return checkIdChild;
	}


	/**
	 * @param checkIdChild The checkIdChild to set.
	 */
	public void setCheckIdChild(String[] checkIdChild) {
		this.checkIdChild = checkIdChild;
	}


	/**
	 * @return Returns the slaID.
	 */
	public String[] getSlaID() {
		return slaID;
	}


	/**
	 * @param slaID The slaID to set.
	 */
	public void setSlaID(String[] slaID) {
		this.slaID = slaID;
	}




	/**
	 * @return Returns the reloadParent.
	 */
	public String getReloadParent() {
		return reloadParent;
	}


	/**
	 * @param reloadParent The reloadParent to set.
	 */
	public void setReloadParent(String reloadParent) {
		this.reloadParent = reloadParent;
	}


	/**
	 * @return Returns the closeStatusFlag.
	 */
	public String getCloseStatusFlag() {
		return closeStatusFlag;
	}


	/**
	 * @param closeStatusFlag The closeStatusFlag to set.
	 */
	public void setCloseStatusFlag(String closeStatusFlag) {
		this.closeStatusFlag = closeStatusFlag;
	}


	/**
	 * @return Returns the linkStatus.
	 */
	public String getLinkStatus() {
		return linkStatus;
	}


	/**
	 * @param linkStatus The linkStatus to set.
	 */
	public void setLinkStatus(String linkStatus) {
		this.linkStatus = linkStatus;
	}


	/**
	 * @return Returns the slaDetailsVos.
	 */
	public Collection<SLADetailsVO> getSlaDetailsVos() {
		return slaDetailsVos;
	}


	/**
	 * @param slaDetailsVos The slaDetailsVos to set.
	 */
	public void setSlaDetailsVos(Collection<SLADetailsVO> slaDetailsVos) {
		this.slaDetailsVos = slaDetailsVos;
	}

	
	

}
