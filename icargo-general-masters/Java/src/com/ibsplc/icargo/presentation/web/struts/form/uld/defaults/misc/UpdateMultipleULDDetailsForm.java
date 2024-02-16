/*
 * UpdateMultipleULDDetailsForm.java Created on March 13, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.misc;
import org.apache.struts.upload.FormFile;

import com.ibsplc.icargo.framework.model.ScreenModel;


public class UpdateMultipleULDDetailsForm extends ScreenModel{

	private static final String BUNDLE = "updatemultipledamageuldresources";
	private static final String PRODUCT = "uld";
	private static final String SUBPRODUCT = "defaults";
	private static final String SCREENID = "uld.defaults.updatemultipleulddetails";
	@Override
	public String getProduct() {
		return PRODUCT;
	}

	@Override
	public String getScreenId() {
		return SCREENID;
	}

	@Override
	public String getSubProduct() {
		return SUBPRODUCT;
	}
    
	private String[] uldNumbers;
	private String[] operationalStatus;
	private String[] damagedStatus;
	private String[] selectedElements;
	private String[] damageDetails;
	private boolean operationalStatusCopyAll;
	private boolean damagedStatusCopyAll;
	
	private String[] rowId;
	private boolean[] selectedDmgRowId;
	private FormFile dmgPicture;
	private String [] section;
	private String [] severity;
	private String[] description;
	private String[] partyType;
	private String[] party;
	private String[] repStn;
	private String[] reportedDate;
	private String[] facilityType;
	private String []location;
	private String []remarks;
	private String selectedRow;
	private String newOperationalStatus;
	private String newDamagedStatus;
	private String[] ratingUldOperationFlag;
	private String totalPoints;
	private FormFile[] dmgPictureArr;
	/** The status flag. */
	private String statusFlag = "";
	private String bundle;
	private String seqNum;
	private String selectedDmgRow;
	//Added by A-8368 as part of user story -  IASCB-35533
	private String[] damageNoticePoint;
	public String getBundle() {
		return BUNDLE;
	}
	public void setBundle(String bundle) {
		this.bundle = bundle;
	}
	public String getStatusFlag() {
		return statusFlag;
	}

	public void setStatusFlag(String statusFlag) {
		this.statusFlag = statusFlag;
	}

	public String[] getRatingUldOperationFlag() {
		return ratingUldOperationFlag;
	}

	public void setRatingUldOperationFlag(String[] ratingUldOperationFlag) {
		this.ratingUldOperationFlag = ratingUldOperationFlag;
	}

	public String getNewOperationalStatus() {
		return newOperationalStatus;
	}

	public void setNewOperationalStatus(String newOperationalStatus) {
		this.newOperationalStatus = newOperationalStatus;
	}

	public String getNewDamagedStatus() {
		return newDamagedStatus;
	}

	public void setNewDamagedStatus(String newDamagedStatus) {
		this.newDamagedStatus = newDamagedStatus;
	}

	public boolean getDamagedStatusCopyAll() {
		return damagedStatusCopyAll;
	}

	public void setDamagedStatusCopyAll(boolean damagedStatusCopyAll) {
		this.damagedStatusCopyAll = damagedStatusCopyAll;
	}

	public String[] getUldNumbers() {
		return uldNumbers;
	}

	public void setUldNumbers(String[] uldNumbers) {
		this.uldNumbers = uldNumbers;
	}

	public String[] getOperationalStatus() {
		return operationalStatus;
	}

	public void setOperationalStatus(String[] operationalStatus) {
		this.operationalStatus = operationalStatus;
	}

	public String[] getDamagedStatus() {
		return damagedStatus;
	}

	public void setDamagedStatus(String[] damagedStatus) {
		this.damagedStatus = damagedStatus;
	}

	public String[] getSelectedElements() {
		return selectedElements;
	}

	public void setSelectedElements(String[] selectedElements) {
		this.selectedElements = selectedElements;
	}

	public String[] getDamageDetails() {
		return damageDetails;
	}

	public void setDamageDetails(String[] damageDetails) {
		this.damageDetails = damageDetails;
	}

	public boolean getOperationalStatusCopyAll() {
		return operationalStatusCopyAll;
	}

	public void setOperationalStatusCopyAll(boolean operationalStatusCopyAll) {
		this.operationalStatusCopyAll = operationalStatusCopyAll;
	}

	public String[] getRowId() {
		return rowId;
	}

	public void setRowId(String[] rowId) {
		this.rowId = rowId;
	}

	public boolean[] getSelectedDmgRowId() {
		return selectedDmgRowId;
	}

	public void setSelectedDmgRowId(boolean[] selectedDmgRowId) {
		this.selectedDmgRowId = selectedDmgRowId;
	}

	public FormFile getDmgPicture() {
		return dmgPicture;
	}

	public void setDmgPicture(FormFile dmgPicture) {
		this.dmgPicture = dmgPicture;
	}

	

	public String[] getSection() {
		return section;
	}

	public void setSection(String[] section) {
		this.section = section;
	}

	public String[] getSeverity() {
		return severity;
	}

	public void setSeverity(String[] severity) {
		this.severity = severity;
	}

	public void setLocation(String[] location) {
		this.location = location;
	}
	public String[] getLocation() {
		return location;
	}

	public String[] getRepStn() {
		return repStn;
	}

	public void setRepStn(String[] repStn) {
		this.repStn = repStn;
	}

	public String[] getReportedDate() {
		return reportedDate;
	}

	public void setReportedDate(String[] reportedDate) {
		this.reportedDate = reportedDate;
	}

	public String[] getFacilityType() {
		return facilityType;
	}

	public void setFacilityType(String[] facilityType) {
		this.facilityType = facilityType;
	}



	public String[] getRemarks() {
		return remarks;
	}

	public void setRemarks(String[] remarks) {
		this.remarks = remarks;
	}

	public String getSelectedRow() {
		return selectedRow;
	}

	public void setSelectedRow(String selectedRow) {
		this.selectedRow = selectedRow;
	}
	public String[] getDescription() {
		return description;
	}
	public void setDescription(String[] description) {
		this.description = description;
	}
	public String[] getPartyType() {
		return partyType;
	}
	public void setPartyType(String[] partyType) {
		this.partyType = partyType;
	}
	public String[] getParty() {
		return party;
	}
	public void setParty(String[] party) {
		this.party = party;
	}
	public String getTotalPoints() {
		return totalPoints;
	}
	public void setTotalPoints(String totalPoints) {
		this.totalPoints = totalPoints;
	}
	public FormFile[] getDmgPictureArr() {
		return dmgPictureArr;
	}
	public void setDmgPictureArr(FormFile[] dmgPictureArr) {
		this.dmgPictureArr = dmgPictureArr;
	}
	public String getSeqNum() {
		return seqNum;
	}
	public void setSeqNum(String seqNum) {
		this.seqNum = seqNum;
	}
	public String getSelectedDmgRow() {
		return selectedDmgRow;
	}
	public void setSelectedDmgRow(String selectedDmgRow) {
		this.selectedDmgRow = selectedDmgRow;
	}
	public String[] getDamageNoticePoint() {
		return damageNoticePoint;
	}
	public void setDamageNoticePoint(String[] damageNoticePoint) {
		this.damageNoticePoint = damageNoticePoint;
	}
	
	
	

	
	
}
