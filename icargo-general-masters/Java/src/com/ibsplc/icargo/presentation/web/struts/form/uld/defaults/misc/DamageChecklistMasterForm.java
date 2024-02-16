/*
 * DamageChecklistMasterForm.java Created on May 5, 2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.misc;

import com.ibsplc.icargo.framework.model.ScreenModel;

/**
 * @author A-3459
 *
 */
 public class DamageChecklistMasterForm extends ScreenModel{
	private static final String BUNDLE = "DamageChkMaster";

	private static final String PRODUCT = "uld";

	private static final String SUBPRODUCT = "defaults";

	private static final String SCREENID = "uld.defaults.damagechecklistmaster";
	
	private String section;
	private String[] description;
	private String[] tableSection;
	private String[] noOfPoints;
	private String totalPoints;
	private String[] hiddenOperationFlag;
	private String statusFlag;
	private String disableButtons;
	
	private String[] sequenceNumber;
	
	
	
	/**
	 * @return the sequenceNumber
	 */
	public String[] getSequenceNumber() {
		return sequenceNumber;
	}
	/**
	 * @param sequenceNumber the sequenceNumber to set
	 */
	public void setSequenceNumber(String[] sequenceNumber) {
		this.sequenceNumber = sequenceNumber;
	}
	/**
	 * @return the disableButtons
	 */
	public String getDisableButtons() {
		return disableButtons;
	}
	/**
	 * @param disableButtons the disableButtons to set
	 */
	public void setDisableButtons(String disableButtons) {
		this.disableButtons = disableButtons;
	}
	/**
	 * @return the statusFlag
	 */
	public String getStatusFlag() {
		return statusFlag;
	}
	/**
	 * @param statusFlag the statusFlag to set
	 */
	public void setStatusFlag(String statusFlag) {
		this.statusFlag = statusFlag;
	}
	/**
	 * @return the hiddenOperationFlag
	 */
	public String[] getHiddenOperationFlag() {
		return hiddenOperationFlag;
	}
	/**
	 * @param hiddenOperationFlag the hiddenOperationFlag to set
	 */
	public void setHiddenOperationFlag(String[] hiddenOperationFlag) {
		this.hiddenOperationFlag = hiddenOperationFlag;
	}
	/**
	 * @return the description
	 */
	public String[] getDescription() {
		return description;
	}
	/**
	 * @param description the description to set
	 */
	public void setDescription(String[] description) {
		this.description = description;
	}
	/**
	 * @return the hiddenEventOperationFlag
	 */
	

	/**
	 * @return the section
	 */
	public String getSection() {
		return section;
	}
	/**
	 * @param section the section to set
	 */
	public void setSection(String section) {
		this.section = section;
	}
	/**
	 * @return the tableSection
	 */
	public String[] getTableSection() {
		return tableSection;
	}
	/**
	 * @param tableSection the tableSection to set
	 */
	public void setTableSection(String[] tableSection) {
		this.tableSection = tableSection;
	}
	
	/**
	 * @return the bUNDLE
	 */
	public String getBundle() {
		return BUNDLE;
	}
	/**
	 * @return the pRODUCT
	 */
	public  String getProduct() {
		return PRODUCT;
	}
	/**
	 * @return the sUBPRODUCT
	 */
	public  String getSubProduct() {
		return SUBPRODUCT;
	}
	/**
	 * @return the sCREENID
	 */
	public  String getScreenId() {
		return SCREENID;
	}
	
	/**
	 * @return the totalPoints
	 */
	public String getTotalPoints() {
		return totalPoints;
	}
	/**
	 * @param totalPoints the totalPoints to set
	 */
	public void setTotalPoints(String totalPoints) {
		this.totalPoints = totalPoints;
	}
	/**
	 * @return the noOfPoints
	 */
	public String[] getNoOfPoints() {
		return noOfPoints;
	}
	/**
	 * @param noOfPoints the noOfPoints to set
	 */
	public void setNoOfPoints(String[] noOfPoints) {
		this.noOfPoints = noOfPoints;
	}


}
