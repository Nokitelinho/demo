package com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.ux.misc;

import org.apache.struts.upload.FormFile;

import com.ibsplc.icargo.framework.model.ScreenModel;

/**
 * 
 *	Java file	: 	com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.ux.misc.MaintainDamageReportForm.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-7627	:	25-Oct-2017	:	Draft
 */
public class MaintainDamageReportForm extends ScreenModel {

	private static final String BUNDLE = "maintainDamageReportUXResources";
	private static final String PRODUCT = "uld";
	private static final String SUBPRODUCT = "defaults";
	private static final String SCREENID = "uld.defaults.ux.maintaindamagereport";
	private String selectedULDNos = "";
	private String selectedULDNum = "";

	private String damageStatus = "";
	private String uldNumber = "";
	private String overallStatus = "";
	private String repairStatus = "";
	private String supervisor = "";
	private String totAmt = "";
	private String invRep = "";
	private String picturePresent = "";

	private String[] rowId;

	private String[] repHead;
	private String[] repairStn;
	private String[] repairDate;
	private String[] dmgRepairRefNo;
	private String[] amount;
	private String[] currency;
	private String[] repRemarks;
	private String[] selectedRepRowId;
	private String[] tempRepairOpFlag;

	private String damageCode = "";
	private String[]  dmgRefNo;
	private String position = "";
	private String[]  severity;
	private String[]  repStn;
	private String[]  closed;
	private String[]  remarks;
	private String flag = "";
	private String statusFlag = "";
	private String[] selectedDmgRowId;
	private String[] tempOperationFlag;
	private String[] imageCounts;

	private String dmgdisplayPage = "1";
	private String dmglastPageNum = "0";
	private String dmgtotalRecords = "0";
	private String dmgcurrentPageNum = "1";

	private String displayPage = "1";
	private String lastPageNum = "0";
	private String totalRecords = "0";
	private String currentPageNum = "1";

	private String repdisplayPage = "1";
	private String replastPageNum = "0";
	private String reptotalRecords = "0";
	private String repcurrentPageNum = "1";
	private String screenStatusValue;

	private FormFile dmgPicture;

	private String bundle;

	private String uldNumbersSelected;

	private String fromScreen = "";

	private String saveStatus = "";

	private String pageURL = "";

	private String seqNum = "";

	private String oprFlag="";

	private String screenReloadStatus;

	// added by saritha
	private String screenMode;

	private String damageStatusFlag;

	// Added by Sreekumar S
	private String allChecked = "";

	// Added by Tarun for CRQ AirNZ418
	private String[] facilityType;
	private String[] location;
	private String[] partyType;
	private String[] party;
	private String currentStation;
	
	//added by A-7553 for ICRD-241353
	private String defaultCurrency;

	// Added by Tarun for INT ULD 370
	private String[] reportedDate;

	private String[] description;
	private String [] section;
	private String noOfPoints;
	private String totalPoints;
	private boolean[] checkbx;
	private String[] checkBxVal;

	private String overStatus;
	private int totalDamagePoints;

	private String sections;
	private String damageDescription;

	private String partyName;
	private String ajaxPartyCode;
	private String ajaxPartyType;
	private String ajaxErrorStatusFlag;
	private String ajaxPartyName;
	private String selectedDamageRowId;
	private String selectedRepairRowId;

	private String fromSave;
	
	/*Added by A-7636 as part of ICRD-245031*/
	private String imageIndex;
	private String dmgIndex;
	/*Added by A-7636 as part of user story - IASCB-35533*/
	private String[] damageNoticePoint;
	/**
	 * @return the partyName
	 */
	public String getPartyName() {
		return partyName;
	}

	/**
	 * @param partyName
	 *            the partyName to set
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
	 * @param ajaxErrorStatusFlag
	 *            the ajaxErrorStatusFlag to set
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
	 * @param ajaxPartyCode
	 *            the ajaxPartyCode to set
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
	 * @param ajaxPartyName
	 *            the ajaxPartyName to set
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
	 * @param ajaxPartyType
	 *            the ajaxPartyType to set
	 */
	public void setAjaxPartyType(String ajaxPartyType) {
		this.ajaxPartyType = ajaxPartyType;
	}

	public void setReportedDate(String[] reportedDate) {
		this.reportedDate = reportedDate;
	}
	
	public String[] getReportedDate() {
		return reportedDate;
	}

	/**
	 * @return the currentStation
	 */
	public String getCurrentStation() {
		return currentStation;
	}

	/**
	 * @param currentStation
	 *            the currentStation to set
	 */
	public void setCurrentStation(String currentStation) {
		this.currentStation = currentStation;
	}
	/**
	 * 
	 * 	Method		:	MaintainDamageReportForm.setFacilityType
	 *	Added by 	:	A-5258 on Nov 17, 2017
	 * 	Used for 	:
	 *	Parameters	:	@param facilityType 
	 *	Return type	: 	void
	 */
	public void setFacilityType(String[] facilityType) {
		this.facilityType = facilityType;
	}
	/**
	 * 
	 * 	Method		:	MaintainDamageReportForm.getFacilityType
	 *	Added by 	:	A-5258 on Nov 17, 2017
	 * 	Used for 	:
	 *	Parameters	:	@return 
	 *	Return type	: 	String[]
	 */
	public String[] getFacilityType() {
		return facilityType;
	}


	/**
	 * 	Getter for location 
	 *	Added by : A-5258 on Nov 16, 2017
	 * 	Used for :
	 */
	public String[] getLocation() {
		return location;
	}

	/**
	 *  @param location the location to set
	 * 	Setter for location 
	 *	Added by : A-5258 on Nov 16, 2017
	 * 	Used for :
	 */
	public void setLocation(String[] location) {
		this.location = location;
	}

	/**
	 * 	Getter for partyType 
	 *	Added by : A-5258 on Nov 16, 2017
	 * 	Used for :
	 */
	public String[] getPartyType() {
		return partyType;
	}

	/**
	 *  @param partyType the partyType to set
	 * 	Setter for partyType 
	 *	Added by : A-5258 on Nov 16, 2017
	 * 	Used for :
	 */
	public void setPartyType(String[] partyType) {
		this.partyType = partyType;
	}

	public void setParty(String[] party) {
		this.party = party;
	}
	public String[] getParty() {
		return party;
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
	 * @param oprFlag
	 *            The oprFlag to set.
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
	 * @param seqNum
	 *            The seqNum to set.
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
	 * @param bundle
	 *            The bundle to set.
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
	 * @param uldNumber
	 *            The uldNumber to set.
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
	 * @param damageStatus
	 *            The damageStatus to set.
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
	 * @param overallStatus
	 *            The overallStatus to set.
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
	 * @param repairStatus
	 *            The repairStatus to set.
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
	 * @param supervisor
	 *            The supervisor to set.
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
	 * @param totAmt
	 *            The totAmt to set.
	 */
	public void setTotAmt(String totAmt) {
		this.totAmt = totAmt;
	}



	/**
	 * @return Returns the statusFlag.
	 */
	public String getStatusFlag() {
		return statusFlag;
	}

	/**
	 * @param statusFlag
	 *            The statusFlag to set.
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

	public String getSelectedDamageRowId() {
		return selectedDamageRowId;
	}

	public void setSelectedDamageRowId(String selectedDamageRowId) {
		this.selectedDamageRowId = selectedDamageRowId;
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

	public String getSelectedRepairRowId() {
		return selectedRepairRowId;
	}

	public void setSelectedRepairRowId(String selectedRepairRowId) {
		this.selectedRepairRowId = selectedRepairRowId;
	}

	/**
	 * 	Getter for repHead 
	 *	Added by : A-5258 on Nov 16, 2017
	 * 	Used for :
	 */
	public String[] getRepHead() {
		return repHead;
	}

	/**
	 *  @param repHead the repHead to set
	 * 	Setter for repHead 
	 *	Added by : A-5258 on Nov 16, 2017
	 * 	Used for :
	 */
	public void setRepHead(String[] repHead) {
		this.repHead = repHead;
	}

	/**
	 * 	Getter for repairStn 
	 *	Added by : A-5258 on Nov 16, 2017
	 * 	Used for :
	 */
	public String[] getRepairStn() {
		return repairStn;
	}

	/**
	 *  @param repairStn the repairStn to set
	 * 	Setter for repairStn 
	 *	Added by : A-5258 on Nov 16, 2017
	 * 	Used for :
	 */
	public void setRepairStn(String[] repairStn) {
		this.repairStn = repairStn;
	}

	/**
	 * 	Getter for repairDate 
	 *	Added by : A-5258 on Nov 16, 2017
	 * 	Used for :
	 */
	public String[] getRepairDate() {
		return repairDate;
	}

	/**
	 *  @param repairDate the repairDate to set
	 * 	Setter for repairDate 
	 *	Added by : A-5258 on Nov 16, 2017
	 * 	Used for :
	 */
	public void setRepairDate(String[] repairDate) {
		this.repairDate = repairDate;
	}

	/**
	 * 	Getter for dmgRepairRefNo 
	 *	Added by : A-5258 on Nov 16, 2017
	 * 	Used for :
	 */
	public String[] getDmgRepairRefNo() {
		return dmgRepairRefNo;
	}

	/**
	 *  @param dmgRepairRefNo the dmgRepairRefNo to set
	 * 	Setter for dmgRepairRefNo 
	 *	Added by : A-5258 on Nov 16, 2017
	 * 	Used for :
	 */
	public void setDmgRepairRefNo(String[] dmgRepairRefNo) {
		this.dmgRepairRefNo = dmgRepairRefNo;
	}

	/**
	 * 	Getter for amount 
	 *	Added by : A-5258 on Nov 16, 2017
	 * 	Used for :
	 */
	public String[] getAmount() {
		return amount;
	}

	/**
	 *  @param amount the amount to set
	 * 	Setter for amount 
	 *	Added by : A-5258 on Nov 16, 2017
	 * 	Used for :
	 */
	public void setAmount(String[] amount) {
		this.amount = amount;
	}

	/**
	 * 	Getter for currency 
	 *	Added by : A-5258 on Nov 16, 2017
	 * 	Used for :
	 */
	public String[] getCurrency() {
		return currency;
	}

	/**
	 *  @param currency the currency to set
	 * 	Setter for currency 
	 *	Added by : A-5258 on Nov 16, 2017
	 * 	Used for :
	 */
	public void setCurrency(String[] currency) {
		this.currency = currency;
	}

	/**
	 * 	Getter for repRemarks 
	 *	Added by : A-5258 on Nov 16, 2017
	 * 	Used for :
	 */
	public String[] getRepRemarks() {
		return repRemarks;
	}

	/**
	 *  @param repRemarks the repRemarks to set
	 * 	Setter for repRemarks 
	 *	Added by : A-5258 on Nov 16, 2017
	 * 	Used for :
	 */
	public void setRepRemarks(String[] repRemarks) {
		this.repRemarks = repRemarks;
	}

	/**
	 * 	Getter for dmgRefNo 
	 *	Added by : A-5258 on Nov 16, 2017
	 * 	Used for :
	 */
	public String[] getDmgRefNo() {
		return dmgRefNo;
	}

	/**
	 *  @param dmgRefNo the dmgRefNo to set
	 * 	Setter for dmgRefNo 
	 *	Added by : A-5258 on Nov 16, 2017
	 * 	Used for :
	 */
	public void setDmgRefNo(String[] dmgRefNo) {
		this.dmgRefNo = dmgRefNo;
	}

	/**
	 * 	Getter for severity 
	 *	Added by : A-5258 on Nov 16, 2017
	 * 	Used for :
	 */
	public String[] getSeverity() {
		return severity;
	}

	/**
	 *  @param severity the severity to set
	 * 	Setter for severity 
	 *	Added by : A-5258 on Nov 16, 2017
	 * 	Used for :
	 */
	public void setSeverity(String[] severity) {
		this.severity = severity;
	}

	/**
	 * 	Getter for repStn 
	 *	Added by : A-5258 on Nov 16, 2017
	 * 	Used for :
	 */
	public String[] getRepStn() {
		return repStn;
	}

	/**
	 *  @param repStn the repStn to set
	 * 	Setter for repStn 
	 *	Added by : A-5258 on Nov 16, 2017
	 * 	Used for :
	 */
	public void setRepStn(String[] repStn) {
		this.repStn = repStn;
	}

	/**
	 * 	Getter for closed 
	 *	Added by : A-5258 on Nov 16, 2017
	 * 	Used for :
	 */
	public String[] getClosed() {
		return closed;
	}

	/**
	 *  @param closed the closed to set
	 * 	Setter for closed 
	 *	Added by : A-5258 on Nov 16, 2017
	 * 	Used for :
	 */
	public void setClosed(String[] closed) {
		this.closed = closed;
	}

	/**
	 * 	Getter for remarks 
	 *	Added by : A-5258 on Nov 16, 2017
	 * 	Used for :
	 */
	public String[] getRemarks() {
		return remarks;
	}

	/**
	 *  @param remarks the remarks to set
	 * 	Setter for remarks 
	 *	Added by : A-5258 on Nov 16, 2017
	 * 	Used for :
	 */
	public void setRemarks(String[] remarks) {
		this.remarks = remarks;
	}


	public void setTempOperationFlag(String[] tempOperationFlag) {
		this.tempOperationFlag = tempOperationFlag;
	}
	
	public String[] getTempOperationFlag() {
		return tempOperationFlag;
	}
	public void setSection(String[] section) {
		this.section = section;
	}
	
	public String[] getSection() {
		return section;
	}
	public void setTempRepairOpFlag(String[] tempRepairOpFlag) {
		this.tempRepairOpFlag = tempRepairOpFlag;
	}
	public String[] getTempRepairOpFlag() {
		return tempRepairOpFlag;
	}

	public String getDefaultCurrency() {
		return defaultCurrency;
	}

	public void setDefaultCurrency(String defaultCurrency) {
		this.defaultCurrency = defaultCurrency;
	}

	public String getFromSave() {
		return fromSave;
	}

	public void setFromSave(String fromSave) {
		this.fromSave = fromSave;
	}
	
	/*Added by A-7636 as part of ICRD-245031*/
	public String getImageIndex() {
		return imageIndex;
	}

	public void setImageIndex(String imageIndex) {
		this.imageIndex = imageIndex;
	}
	public String getDmgIndex() {
		return dmgIndex;
	}

	public void setDmgIndex(String dmgIndex) {
		this.dmgIndex = dmgIndex;
	}
	public String[] getImageCounts() {
		return imageCounts;
	}

	public void setImageCounts(String[] imageCounts) {
		this.imageCounts = imageCounts;
	}
	public String[] getDamageNoticePoint() {
		return damageNoticePoint;
	}
	public void setDamageNoticePoint(String[] damageNoticePoint) {
		this.damageNoticePoint = damageNoticePoint;
	}
}
