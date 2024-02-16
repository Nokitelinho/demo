/*
 * MaintainDamageReportForm.java Created on Dec 19, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.misc;

import org.apache.struts.upload.FormFile;

import com.ibsplc.icargo.framework.model.ScreenModel;

/**
 * @author A-1496
 *
 */
public class MaintainDamageReportForm extends ScreenModel {


	private static final String BUNDLE = "maintainDamageReportResources";
	private static final String PRODUCT = "uld";
	private static final String SUBPRODUCT = "defaults";
	private static final String SCREENID = "uld.defaults.maintaindamagereport";

	private String selectedULDNos="";
	private String selectedULDNum="";

	private String damageStatus="";
	private String uldNumber="";
	private String overallStatus="";
	private String repairStatus="";
	private String supervisor="";
	private String totAmt="";
	private String invRep="";
	private String picturePresent="";

	private String[] rowId;

	private String repHead="";
	private String repairStn="";
	private String repairDate="";
	private String dmgRepairRefNo="";
	private String amount="";
	private String currency="";
	private String repRemarks="";
	private String[] selectedRepRowId;

	private String damageCode="";
	private String dmgRefNo="";
	private String position="";
	private String severity="";
	private String repStn="";
	private String closed="";
	private String remarks="";
	private String flag="";
	private String statusFlag="";
	private String[] selectedDmgRowId;

	private String dmgdisplayPage = "1";
	private String dmglastPageNum =  "0";
	private String dmgtotalRecords = "0";
	private String dmgcurrentPageNum = "1";

	private String displayPage = "1";
	private String lastPageNum =  "0";
	private String totalRecords = "0";
	private String currentPageNum = "1";

	private String repdisplayPage = "1";
	private String replastPageNum =  "0";
	private String reptotalRecords = "0";
	private String repcurrentPageNum = "1";
	private String screenStatusValue ;

	private FormFile dmgPicture;

	private String bundle;

	private String uldNumbersSelected;

	private String fromScreen="";

	private String saveStatus="";

	private String pageURL="";

	private String seqNum="";

	private String oprFlag="";

	private String screenReloadStatus;

	//added by saritha
	private String screenMode;

	private String damageStatusFlag;

	//Added by Sreekumar S
	private String allChecked ="";


	//Added by Tarun for CRQ AirNZ418
	private String facilityType;
	private String location;
	private String partyType;
	private String party;
	private String currentStation;


	//Added by Tarun for INT ULD 370
	private String reportedDate;

	private String[] description;
	private String section;
	private String noOfPoints;
	private String totalPoints;
	private boolean[] checkbx;
	private String[] checkBxVal;

	private String overStatus;
	private int totalDamagePoints;

	private String sections;
	private String damageDescription;

	private String partyName;
	private String  ajaxPartyCode;
	private String  ajaxPartyType;
	private String  ajaxErrorStatusFlag;
	private String  ajaxPartyName;

	/**
	 * @return the partyName
	 */
	public String getPartyName() {
		return partyName;
	}

	/**
	 * @param partyName the partyName to set
	 */
	public void setPartyName(String partyName) {
		this.partyName = partyName;
	}

/**
	 * @return the ajaxErrorStatusFlag
	 */
	public String getAjaxErrorStatusFlag() {
		return ajaxErrorStatusFlag;
	}

	/**
	 * @param ajaxErrorStatusFlag the ajaxErrorStatusFlag to set
	 */
	public void setAjaxErrorStatusFlag(String ajaxErrorStatusFlag) {
		this.ajaxErrorStatusFlag = ajaxErrorStatusFlag;
	}

	/**
	 * @return the ajaxPartyCode
	 */
	public String getAjaxPartyCode() {
		return ajaxPartyCode;
	}

	/**
	 * @param ajaxPartyCode the ajaxPartyCode to set
	 */
	public void setAjaxPartyCode(String ajaxPartyCode) {
		this.ajaxPartyCode = ajaxPartyCode;
	}

	/**
	 * @return the ajaxPartyName
	 */
	public String getAjaxPartyName() {
		return ajaxPartyName;
	}

	/**
	 * @param ajaxPartyName the ajaxPartyName to set
	 */
	public void setAjaxPartyName(String ajaxPartyName) {
		this.ajaxPartyName = ajaxPartyName;
	}

	/**
	 * @return the ajaxPartyType
	 */
	public String getAjaxPartyType() {
		return ajaxPartyType;
	}

	/**
	 * @param ajaxPartyType the ajaxPartyType to set
	 */
	public void setAjaxPartyType(String ajaxPartyType) {
		this.ajaxPartyType = ajaxPartyType;
	}



	/**
	 * @return the reportedDate
	 */
	public String getReportedDate() {
		return reportedDate;
	}

	/**
	 * @param reportedDate the reportedDate to set
	 */
	public void setReportedDate(String reportedDate) {
		this.reportedDate = reportedDate;
	}

	/**
	 * @return the currentStation
	 */
	public String getCurrentStation() {
		return currentStation;
	}

	/**
	 * @param currentStation the currentStation to set
	 */
	public void setCurrentStation(String currentStation) {
		this.currentStation = currentStation;
	}

	/**
	 * @return the facilityType
	 */
	public String getFacilityType() {
		return facilityType;
	}

	/**
	 * @param facilityType the facilityType to set
	 */
	public void setFacilityType(String facilityType) {
		this.facilityType = facilityType;
	}

	/**
	 * @return the location
	 */
	public String getLocation() {
		return location;
	}

	/**
	 * @param location the location to set
	 */
	public void setLocation(String location) {
		this.location = location;
	}

	/**
	 * @return the partyType
	 */
	public String getPartyType() {
		return partyType;
	}

	/**
	 * @param partyType the partyType to set
	 */
	public void setPartyType(String partyType) {
		this.partyType = partyType;
	}



	/**
	 * @return the party
	 */
	public String getParty() {
		return party;
	}

	/**
	 * @param party the party to set
	 */
	public void setParty(String party) {
		this.party = party;
	}

	public String getAllChecked() {
		return allChecked;
	}

	public void setAllChecked(String allChecked) {
		this.allChecked = allChecked;
	}

	public String getDamageStatusFlag() {
		return damageStatusFlag;
	}

	public void setDamageStatusFlag(String damageStatusFlag) {
		this.damageStatusFlag = damageStatusFlag;
	}

	/**
	 * @return Returns the oprFlag.
	 */
	public String getOprFlag() {
		return oprFlag;
	}

	/**
	 * @param oprFlag The oprFlag to set.
	 */
	public void setOprFlag(String oprFlag) {
		this.oprFlag = oprFlag;
	}

	/**
	 * @return Returns the seqNum.
	 */
	public String getSeqNum() {
		return seqNum;
	}

	/**
	 * @param seqNum The seqNum to set.
	 */
	public void setSeqNum(String seqNum) {
		this.seqNum = seqNum;
	}

	/**
     * Method to return the product the screen is associated with
     *
     * @return String
     */
    public String getProduct() {
        return PRODUCT;
    }

    /**
     * Method to return the sub product the screen is associated with
     *
     * @return String
     */
    public String getSubProduct() {
        return SUBPRODUCT;
    }

    /**
     * Method to return the id the screen is associated with
     *
     * @return String
     */
    public String getScreenId() {
        return SCREENID;
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
	 * @return Returns the uldNumber.
	 */
	public String getUldNumber() {
		return uldNumber;
	}

	/**
	 * @param uldNumber The uldNumber to set.
	 */
	public void setUldNumber(String uldNumber) {
		this.uldNumber = uldNumber;
	}
	/**
	 * @return Returns the damageStatus.
	 */
	public String getDamageStatus() {
		return damageStatus;
	}

	/**
	 * @param damageStatus The damageStatus to set.
	 */
	public void setDamageStatus(String damageStatus) {
		this.damageStatus = damageStatus;
	}

	/**
	 * @return Returns the overallStatus.
	 */
	public String getOverallStatus() {
		return overallStatus;
	}

	/**
	 * @param overallStatus The overallStatus to set.
	 */
	public void setOverallStatus(String overallStatus) {
		this.overallStatus = overallStatus;
	}

	/**
	 * @return Returns the repairStatus.
	 */
	public String getRepairStatus() {
		return repairStatus;
	}

	/**
	 * @param repairStatus The repairStatus to set.
	 */
	public void setRepairStatus(String repairStatus) {
		this.repairStatus = repairStatus;
	}

	/**
	 * @return Returns the supervisor.
	 */
	public String getSupervisor() {
		return supervisor;
	}

	/**
	 * @param supervisor The supervisor to set.
	 */
	public void setSupervisor(String supervisor) {
		this.supervisor = supervisor;
	}
	/**
	 * @return Returns the totAmt.
	 */
	public String getTotAmt() {
		return totAmt;
	}

	/**
	 * @param totAmt The totAmt to set.
	 */
	public void setTotAmt(String totAmt) {
		this.totAmt = totAmt;
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
	 * @return Returns the statusFlag.
	 */
	public String getStatusFlag() {
		return statusFlag;
	}

	/**
	 * @param statusFlag The statusFlag to set.
	 */
	public void setStatusFlag(String statusFlag) {
		this.statusFlag = statusFlag;
	}
	/**
	 * @return Returns the statusFlag.
	 */
	public String getFlag() {
		return flag;
	}

	/**
	 * @param flag
	 */
	public void setFlag(String flag) {
		this.flag = flag;
	}

	/**
	 * @return Returns the dmgRefNo.
	 */
	public String getDmgRefNo() {
		return dmgRefNo;
	}

	/**
	 * @param dmgRefNo
	 */
	public void setDmgRefNo(String dmgRefNo) {
		this.dmgRefNo = dmgRefNo;
	}
	/**
	 * @return Returns the damageCode.
	 */
	public String getDamageCode() {
		return damageCode;
	}

	/**
	 * @param damageCode
	 */
	public void setDamageCode(String damageCode) {
		this.damageCode = damageCode;
	}
	/**
	 * @return Returns the invRep.
	 */
	public String getInvRep() {
		return invRep;
	}

	/**
	 * @param invRep
	 */
	public void setInvRep(String invRep) {
		this.invRep = invRep;
	}
	/**
	 * @return Returns the dmgRepairRefNo.
	 */
	public String getDmgRepairRefNo() {
		return dmgRepairRefNo;
	}

	/**
	 * @param dmgRepairRefNo
	 */
	public void setDmgRepairRefNo(String dmgRepairRefNo) {
		this.dmgRepairRefNo = dmgRepairRefNo;
	}
	/**
	 * @return Returns the amount.
	 */
	public String getAmount() {
		return amount;
	}

	/**
	 * @param amount
	 */
	public void setAmount(String amount) {
		this.amount = amount;
	}
	/**
	 * @return Returns the position.
	 */
	public String getPosition() {
		return position;
	}

	/**
	 * @param position
	 */
	public void setPosition(String position) {
		this.position = position;
	}
	/**
	 * @return Returns the closed.
	 */
	public String getClosed() {
		return closed;
	}

	/**
	 * @param closed
	 */
	public void setClosed(String closed) {
		this.closed = closed;
	}
	/**
	 * @return Returns the selectedRowId.
	 */
	public String[] getSelectedDmgRowId() {
		return selectedDmgRowId;
	}

	/**
	 * @param selectedDmgRowId
	 */
	public void setSelectedDmgRowId(String[] selectedDmgRowId) {
		this.selectedDmgRowId = selectedDmgRowId;
	}
	/**
	 * @return Returns the repStn.
	 */
	public String getRepStn() {
		return repStn;
	}

	/**
	 * @param repStn
	 */
	public void setRepStn(String repStn) {
		this.repStn = repStn;
	}
	/**
	 * @return Returns the severity.
	 */
	public String getSeverity() {
		return severity;
	}

	/**
	 * @param severity
	 */
	public void setSeverity(String severity) {
		this.severity = severity;
	}
	/**
	 * @return Returns the currency.
	 */
	public String getCurrency() {
		return currency;
	}

	/**
	 * @param currency
	 */
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	/**
	 * @return Returns the repRemarks.
	 */
	public String getRepRemarks() {
		return repRemarks;
	}

	/**
	 * @param repRemarks
	 */
	public void setRepRemarks(String repRemarks) {
		this.repRemarks = repRemarks;
	}
	/**
	 * @return Returns the selectedRepRowId.
	 */
	public String[] getSelectedRepRowId() {
		return selectedRepRowId;
	}

	/**
	 * @param selectedRepRowId The selectedRepRowId to set.
	 */
	public void setSelectedRepRowId(String[] selectedRepRowId) {
		this.selectedRepRowId = selectedRepRowId;
	}
	/**
	 * @return Returns the repHead.
	 */
	public String getRepHead() {
		return repHead;
	}

	/**
	 * @param repHead The repHead to set.
	 */
	public void setRepHead(String repHead) {
		this.repHead = repHead;
	}
	/**
	 * @return Returns the repairStn.
	 */
	public String getRepairStn() {
		return repairStn;
	}

	/**
	 * @param repairStn The repairStn to set.
	 */
	public void setRepairStn(String repairStn) {
		this.repairStn = repairStn;
	}
	/**
	 * @return Returns the repairDate.
	 */
	public String getRepairDate() {
		return repairDate;
	}

	/**
	 * @param repairDate
	 */
	public void setRepairDate(String repairDate) {
		this.repairDate = repairDate;
	}
	/**
	 * Method to get the dmgtotalRecords
	 * @return dmgtotalRecords
	 */
	public String getDmgtotalRecords() {
		return dmgtotalRecords;
	}
	/**
	 * Method to set the dmgtotalRecords
	 * @param dmgtotalRecords
	 */
	public void setDmgtotalRecords(String dmgtotalRecords) {
		this.dmgtotalRecords = dmgtotalRecords;
	}
	/**
	 * Method to get the current Page Number
	 * @return dmgcurrentPageNum
	 */
	public String getDmgcurrentPageNum() {
		return dmgcurrentPageNum;
	}
	/**
	 * Method to set the current Page Number
	 * @param dmgcurrentPageNum
	 */
	public void setDmgcurrentPageNum(String dmgcurrentPageNum) {
		this.dmgcurrentPageNum = dmgcurrentPageNum;
	}

	/**
	 *
	 * @return
	 */
	public String getDmglastPageNum() {
		return dmglastPageNum;
	}

	/**
	 * @return Returns the dmgdisplayPage.
	 */
	public String getDmgdisplayPage() {
		return dmgdisplayPage;
	}


	/**
	 * @param dmgdisplayPage The dmgdisplayPage to set.
	 */
	public void setDmgdisplayPage(String dmgdisplayPage) {
		this.dmgdisplayPage = dmgdisplayPage;
	}
	/**
	 *
	 * @param dmglastPageNum
	 */
	public void setDmglastPageNum(String dmglastPageNum) {
		this.dmglastPageNum = dmglastPageNum;
	}

	/**
	 * Method to get the reptotalRecords
	 * @return reptotalRecords
	 */
	public String getReptotalRecords() {
		return reptotalRecords;
	}
	/**
	 * Method to set the reptotalRecords
	 * @param reptotalRecords
	 */
	public void setReptotalRecords(String reptotalRecords) {
		this.reptotalRecords = reptotalRecords;
	}
	/**
	 * Method to get the current Page Number
	 * @return repcurrentPageNum
	 */
	public String getRepcurrentPageNum() {
		return repcurrentPageNum;
	}
	/**
	 * Method to set the current Page Number
	 * @param repcurrentPageNum
	 */
	public void setRepcurrentPageNum(String repcurrentPageNum) {
		this.repcurrentPageNum = repcurrentPageNum;
	}

	/**
	 *
	 * @return
	 */
	public String getReplastPageNum() {
		return replastPageNum;
	}

	/**
	 * @return Returns the repdisplayPage.
	 */
	public String getRepdisplayPage() {
		return repdisplayPage;
	}


	/**
	 * @param repdisplayPage The repdisplayPage to set.
	 */
	public void setRepdisplayPage(String repdisplayPage) {
		this.repdisplayPage = repdisplayPage;
	}
	/**
	 *
	 * @param replastPageNum
	 */
	public void setReplastPageNum(String replastPageNum) {
		this.replastPageNum = replastPageNum;
	}

	/**
	* @return image
	*/
//	public FormFile getImage(){
//		return image;
//	}

	/**
	* @param image
	*/
//	public void setImage(FormFile image){
//		this.image = image;
//	}


	/**
	 * Method to get the dmgtotalRecords
	 * @return dmgtotalRecords
	 */
	public String getTotalRecords() {
		return totalRecords;
	}
	/**
	 * Method to set the dmgtotalRecords
	 * @param totalRecords
	 */
	public void setTotalRecords(String totalRecords) {
		this.totalRecords = totalRecords;
	}
	/**
	 * Method to get the current Page Number
	 * @return dmgcurrentPageNum
	 */
	public String getCurrentPageNum() {
		return currentPageNum;
	}
	/**
	 * Method to set the current Page Number
	 * @param currentPageNum
	 */
	public void setCurrentPageNum(String currentPageNum) {
		this.currentPageNum = currentPageNum;
	}
	/**
	 *
	 * @return
	 */

	public String getLastPageNum() {
		return lastPageNum;
	}

	/**
	 * @return Returns the dmgdisplayPage.
	 */
	public String getDisplayPage() {
		return displayPage;
	}


	/**
	 * @param displayPage
	 */
	public void setDisplayPage(String displayPage) {
		this.displayPage = displayPage;
	}
	/**
	 *
	 * @param lastPageNum
	 */
	public void setLastPageNum(String lastPageNum) {
		this.lastPageNum = lastPageNum;
	}
	/**
	 * @return Returns the statusFlag.
	 */
	public String getUldNumbersSelected() {
		return uldNumbersSelected;
	}

	/**
	 * @param uldNumbersSelected
	 */
	public void setUldNumbersSelected(String uldNumbersSelected) {
		this.uldNumbersSelected = uldNumbersSelected;
	}

	/**
	 * @return Returns the saveStatus.
	 */
	public String getSaveStatus() {
		return saveStatus;
	}

	/**
	 * @param saveStatus The saveStatus to set.
	 */
	public void setSaveStatus(String saveStatus) {
		this.saveStatus = saveStatus;
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
	 * @return Returns the pageURL.
	 */
	public String getPageURL() {
		return pageURL;
	}
	/**
	 * @param pageURL The pageURL to set.
	 */
	public void setPageURL(String pageURL) {
		this.pageURL = pageURL;
	}

	/**
	 * @return Returns the selectedULDNos.
	 */
	public String getSelectedULDNos() {
		return selectedULDNos;
	}

	/**
	 * @param selectedULDNos The selectedULDNos to set.
	 */
	public void setSelectedULDNos(String selectedULDNos) {
		this.selectedULDNos = selectedULDNos;
	}



	/**
	 * @return Returns the picturePresent.
	 */
	public String getPicturePresent() {
		return picturePresent;
	}

	/**
	 * @param picturePresent The picturePresent to set.
	 */
	public void setPicturePresent(String picturePresent) {
		this.picturePresent = picturePresent;
	}



	/**
	 * @return Returns the selectedULDNum.
	 */
	public String getSelectedULDNum() {
		return selectedULDNum;
	}

	/**
	 * @param selectedULDNum The selectedULDNum to set.
	 */
	public void setSelectedULDNum(String selectedULDNum) {
		this.selectedULDNum = selectedULDNum;
	}

	/**
	 * @return Returns the dmgPicture.
	 */
	public FormFile getDmgPicture() {
		return dmgPicture;
	}

	/**
	 * @param dmgPicture The dmgPicture to set.
	 */
	public void setDmgPicture(FormFile dmgPicture) {
		this.dmgPicture = dmgPicture;
	}

	public String getScreenStatusValue() {
		return screenStatusValue;
	}

	public void setScreenStatusValue(String screenStatusValue) {
		this.screenStatusValue = screenStatusValue;
	}

	public String getFromScreen() {
		return fromScreen;
	}

	public void setFromScreen(String fromScreen) {
		this.fromScreen = fromScreen;
	}

	/**
	 * @return Returns the screenReloadStatus.
	 */
	public String getScreenReloadStatus() {
		return screenReloadStatus;
	}

	/**
	 * @param screenReloadStatus The screenReloadStatus to set.
	 */
	public void setScreenReloadStatus(String screenReloadStatus) {
		this.screenReloadStatus = screenReloadStatus;
	}

	public String getScreenMode() {
		return screenMode;
	}

	public void setScreenMode(String screenMode) {
		this.screenMode = screenMode;
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
	 * @return the noOfPoints
	 */
	public String getNoOfPoints() {
		return noOfPoints;
	}

	/**
	 * @param noOfPoints the noOfPoints to set
	 */
	public void setNoOfPoints(String noOfPoints) {
		this.noOfPoints = noOfPoints;
	}

	/**
	 * @return the tableSection
	 */
	public String getSection() {
		return section;
	}

	/**
	 * @param tableSection the tableSection to set
	 */
	public void setSection(String section) {
		this.section = section;
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
	 * @return the checkbx
	 */
	public boolean[] isCheckbx() {
		return checkbx;
	}

	/**
	 * @param checkbx the checkbx to set
	 */
	public void setCheckbx(boolean[] checkbx) {
		this.checkbx = checkbx;
	}

	/**
	 * @return the overStatus
	 */
	public String getOverStatus() {
		return overStatus;
	}

	/**
	 * @param overStatus the overStatus to set
	 */
	public void setOverStatus(String overStatus) {
		this.overStatus = overStatus;
	}

	/**
	 * @return the totalDamagePoints
	 */
	public int getTotalDamagePoints() {
		return totalDamagePoints;
	}

	/**
	 * @param totalDamagePoints the totalDamagePoints to set
	 */
	public void setTotalDamagePoints(int totalDamagePoints) {
		this.totalDamagePoints = totalDamagePoints;
	}

	/**
	 * @return the damageDescription
	 */
	public String getDamageDescription() {
		return damageDescription;
	}

	/**
	 * @param damageDescription the damageDescription to set
	 */
	public void setDamageDescription(String damageDescription) {
		this.damageDescription = damageDescription;
	}

	/**
	 * @return the sections
	 */
	public String getSections() {
		return sections;
	}

	/**
	 * @param sections the sections to set
	 */
	public void setSections(String sections) {
		this.sections = sections;
	}

	/**
	 * @return the checkBxVal
	 */
	public String[] getCheckBxVal() {
		return checkBxVal;
	}

	/**
	 * @param checkBxVal the checkBxVal to set
	 */
	public void setCheckBxVal(String[] checkBxVal) {
		this.checkBxVal = checkBxVal;
	}





}
