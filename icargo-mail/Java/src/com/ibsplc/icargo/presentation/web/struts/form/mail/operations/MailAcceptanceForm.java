/*
 * MailAcceptanceForm.java Created on Jun 20, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.struts.form.mail.operations;

import com.ibsplc.icargo.framework.model.ScreenModel;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.icargo.framework.util.unit.MeasureAnnotation;

/**
 * @author A-1876
 *
 */
public class MailAcceptanceForm extends ScreenModel {

	private static final String SCREEN_ID = "mailtracking.defaults.mailacceptance";
	private static final String PRODUCT_NAME = "mail";
	private static final String SUBPRODUCT_NAME = "operations";
	private static final String BUNDLE = "mailAcceptanceResources";

	
	private String assignToFlight;
	private String flightNo;
	private String flightCarrierCode;
	private String flightNumber;
	private String depDate;
	private String carrierCode;
	private String destination;
	private String departurePort;
	private String[] selectMail;
	private String closeFlag;
	private String duplicateFlightStatus;
	private String disableDestnFlag;
	private String disableSaveFlag;
	private String closeflight;
	private String initialFocus;
	private String fromScreen;
	// Added as part of CRQ ICRD-118163 by A-5526 
	private String deleteAgreeFlag;
	
	private String preassignFlag;
	private String selCont;
	
	private String currentDialogOption;
	private String currentDialogId;

	private String warningFlag;
	
	/*
	 * Capture ULD Damage
	 */
	private String captureULDDamageFlag;

   /**
    * For Accept Mail popup
    */
	private String containerNo;
	
	private String containerType;
	private String pou;
	private String destn;
	private String carrier;
	private String suggestValue;
	private String disableFlag;
	private String hiddenContainer;
	
	private String paBuilt;
	private String onwardFlights;
	private String warehouse;
	private String location;
	private String remarks;
	private String density;
	
	private String[] selectDespatch;
	private String[] conDocNo;
	private String[] despatchDate;
	private String[] despatchPA;
	private String[] despatchOOE;
	private String[] despatchDOE;
	private String[] despatchCat;
	private String[] despatchClass;
	private String[] despatchDSN;
	private String[] despatchYear;
	private String[] statedNoBags;
	private String  defWeightUnit;//added by A_8353 for ICRD-274933
	
	@MeasureAnnotation(mappedValue="statedWtMeasure",unitType="MWT")
	private String[] statedWt;
	private Measure[] statedWtMeasure;
	
	

	private String[] accNoBags;
	@MeasureAnnotation(mappedValue="accWtMeasure",unitType="MWT")
	private String[] accWt;
	private Measure[] accWtMeasure;	
	

	private String[] despatchSC;
	private String[] stdVolume;
	/**
	 * @author a-2107
	 * For BUGID :- 28977
	 */
	
	private String[] accVolume;
	
	private String[] selectMailTag;
	private String[] mailOOE;
	private String[] mailDOE;
	private String[] mailCat;
	private String[] mailSC;
	private String[] mailYr;
	private String[] mailDSN;
	private String[] mailRSN;
	private String[] mailHNI;
	private String[] mailRI;
	//added by A-7371
	@MeasureAnnotation(mappedValue="mailWtMeasure",unit="weightUnit",unitType="MWT")
	private String[] mailWt;
	private Measure[] mailWtMeasure;
	private String [] weightUnit;//added by A_8353 for ICRD-274933 
	@MeasureAnnotation(mappedValue="mailVolumeMeasure",unitType="VOL")//added by A_8353 for ICRD-274933 
	private String[] mailVolume;
	private Measure[] mailVolumeMeasure;
	
	private String[] mailScanDate;
	private String[] mailScanTime;
	private String[] mailCarrier;
	private String[] mailDamaged;
	private String[] mailCartId;
	private String[] mailbagId;
	
	private String[] despatchOpFlag;
	private String[] mailOpFlag;
	
	private String popupCloseFlag;
	private String warningOveride;
	private String overrideUMSFlag;
	private String[] mailCompanyCode;
	
	/**
	 * NotAcceptedULDs
	 */
	private String[] selectULDs;
	private String uldsSelectedFlag;
	private String uldsPopupCloseFlag;
	
	private String reassignScreenFlag; 
	
	/**
	 * PreAdvice
	 */
	private String preAdviceFlag;
	
	/**
	 * For Damage Details Popup
	 */
	private String[] damageCode;
	private String[] damageRemarks;
	private String damageCheckAll;
	private String[] damageSubCheck;
	private String selectedMailBag;
	private String damageFromScreen;
	
	private String operationalStatus="";
	private String resultAction="";
	
	/**
	 * For Lookuo Document Popup
	 */
	private String consignNum;
	private String consignPa;
	private String consignFlag;
	
	/**
	 * For ChangeScanTime Popup
	 */
	private String scanDate;
	private String scanTime;
	private String scanTimeFlag;
	private String scanTimeFromScreen;
	private String strToDelivery;
	
	
	private String hiddenScanDate;
	private String hiddenScanTime;
	private String currentStation;
	private String[] uldnos;
	private String[] reassign;
	private String existingMailbagFlag;
	/**
	 * AirNZ-985
	 */
	private String bellyCarditId;
	private String containerJnyId;
	private String paCode;
	
	/**
	 * For BUG 36983
	 */
	private String consignmentDocNum;
	
	/**
	 * FOR CaptureMailTagDetails Pop up
	 * @return
	 */
	private String lastPageNum="0";
	private String displayPage="1";
	private String totalRecords;
	private String CurrentPageNum="1";
	private String selectedRow;
	
	private String displayPageForCardit;
	
	private String[] sealNo;    
	private String disableButtons;
	private String embargoFlag;
	
	//Added for ICRD-128804
	private boolean barrowCheck;
	 private boolean canCheckEmbargo = true;
	//Added by A-5160 for ICRD-92105  
	 private String tbaTbcWarningFlag;
	 private String disableAddModifyDeleteLinks;
	
	 //Added for ICRD-134007
	 private String saveSuccessFlag;
	 //Added by A-5945 
	 private String duplicateAndTbaTbc;
	//Added for ICRD-205027 starts 
	private String originOE;
	private String destinationOE;
	private String category;
	private String subClass;
	private String year;
	private String dsn;
	private String rsn;
	private String hni;
	private String ri;
   @MeasureAnnotation(mappedValue="wgtMeasure",unitType="MWT")
	private String wgt;
    private Measure wgtMeasure;//added by A-7371
	
    

	
	private String vol;
	private String mailId;
	//Added for ICRD-205027 ends
	 
	 private String modify;
	 private boolean inValidId;//added by A-7371 for ICRD-224610 
	 
	 //Added by A-7794 as part of ICRD-197439
	 private String disableButtonsForAirport;
	 //Added as a part of ICRD-197419
	 private String[] mailRemarks;
	 //added by A-7371 as part of ICRD-271301
	 private String addRowEnableFlag;
	 
	 // Added by A-7371 for ICRD-133987
	 private String transferContainerFlag;
	 private String selectedContainer;
	
	 private boolean canDiscardLATValidation = false;
	 //added by A-7371 as part of ICRD-273840
	 private boolean canDiscardCoterminus = false;
	 private boolean canDiscardUldValidation = false; /*added by A-8149 for ICRD-276070*/
	 private String warningStatus;
	 private String[] uldType; /*added by A-8149 for ICRD-270524*/

	public boolean isCanDiscardLATValidation() {
		return canDiscardLATValidation;
	}

	public void setCanDiscardLATValidation(boolean canDiscardLATValidation) {
		this.canDiscardLATValidation = canDiscardLATValidation;
	}

	 
	public boolean isCanDiscardUldValidation() {
		return canDiscardUldValidation;
	}

	public void setCanDiscardUldValidation(boolean canDiscardUldValidation) {
		this.canDiscardUldValidation = canDiscardUldValidation;
	}

	/**
	 * 	Getter for mailRemarks 
	 *	Added by : a-7540 on 19-Jul-2017
	 * 	Used for :ICRD-197419 for new field 'remarks'
	 */
	 
	public String[] getMailRemarks() {
		return mailRemarks;
	}

	/**
	 *  @param mailRemarks the mailRemarks to set
	 * 	Setter for mailRemarks 
	 *	Added by : a-7540 on 19-Jul-2017
	 * 	Used for :ICRD-197419 for new field 'remarks'
	 */
	public void setMailRemarks(String[] mailRemarks) {
		this.mailRemarks = mailRemarks;
	}

	/**
	 * @return the statedWtMeasure
	 */
	public Measure[] getStatedWtMeasure() {
		return statedWtMeasure;
	}
	
	/**
	 * @param wgtMeasure the wgtMeasure to set
	 */
	public Measure getWgtMeasure() {
		return wgtMeasure;
	}
	/**
	 * @return the wgtMeasure
	 */
	public void setWgtMeasure(Measure wgtMeasure) {
		this.wgtMeasure = wgtMeasure;
	}

	/**
	 * @param statedWtMeasure the statedWtMeasure to set
	 */
	public void setStatedWtMeasure(Measure[] statedWtMeasure) {
		this.statedWtMeasure = statedWtMeasure;
	}
	
	/**
	 * @return the accWtMeasure
	 */
	public Measure[] getAccWtMeasure() {
		return accWtMeasure;
	}

	/**
	 * @param accWtMeasure the accWtMeasure to set
	 */
	public void setAccWtMeasure(Measure[] accWtMeasure) {
		this.accWtMeasure = accWtMeasure;
	}

	 
	public String getDisableAddModifyDeleteLinks() {
		return disableAddModifyDeleteLinks;
	}

	public void setDisableAddModifyDeleteLinks(String disableAddModifyDeleteLinks) {
		this.disableAddModifyDeleteLinks = disableAddModifyDeleteLinks;
	}

	public String getTbaTbcWarningFlag() {
		return tbaTbcWarningFlag;
	}

	public void setTbaTbcWarningFlag(String tbaTbcWarningFlag) {
		this.tbaTbcWarningFlag = tbaTbcWarningFlag;
	}
	
	/**
	 * @return Returns the selectedRow.
	 */
	public String getSelectedRow() {
		return selectedRow;
	}

	/**
	 * @param selectedRow The selectedRow to set.
	 */
	public void setSelectedRow(String selectedRow) {
		this.selectedRow = selectedRow;
	}

	/**
	 * @return Returns the currentPageNum.
	 */
	public String getCurrentPageNum() {
		return CurrentPageNum;
	}

	/**
	 * @param currentPageNum The currentPageNum to set.
	 */
	public void setCurrentPageNum(String currentPageNum) {
		CurrentPageNum = currentPageNum;
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
	 * @return Returns the totalRecords.
	 */
	public String getTotalRecords() {
		return totalRecords;
	}

	/**
	 * @param totalRecords The totalRecords to set.
	 */
	public void setTotalRecords(String totalRecords) {
		this.totalRecords = totalRecords;
	}

	public String getContainerJnyId() {
		return containerJnyId;
	}

	public void setContainerJnyId(String containerJnyId) {
		this.containerJnyId = containerJnyId;
	}

	/**
	 * @return the reassign
	 */
	public String[] getReassign() {
		return reassign;
	}

	/**
	 * @param reassign the reassign to set
	 */
	public void setReassign(String[] reassign) {
		this.reassign = reassign;
	}

	public String getHiddenScanDate() {
		return hiddenScanDate;
	}

	public void setHiddenScanDate(String hiddenScanDate) {
		this.hiddenScanDate = hiddenScanDate;
	}

	public String getHiddenScanTime() {
		return hiddenScanTime;
	}

	public void setHiddenScanTime(String hiddenScanTime) {
		this.hiddenScanTime = hiddenScanTime;
	}

	/**
	 * @return Returns the resultAction.
	 */
	public String getResultAction() {
		return resultAction;
	}

	/**
	 * @param resultAction The resultAction to set.
	 */
	public void setResultAction(String resultAction) {
		this.resultAction = resultAction;
	}

	/**
	 * @return Returns the operationalStatus.
	 */
	public String getOperationalStatus() {
		return operationalStatus;
	}

	/**
	 * @param operationalStatus The operationalStatus to set.
	 */
	public void setOperationalStatus(String operationalStatus) {
		this.operationalStatus = operationalStatus;
	}

	/**
	 * @return Returns the damageFromScreen.
	 */
	public String getDamageFromScreen() {
		return damageFromScreen;
	}

	/**
	 * @param damageFromScreen The damageFromScreen to set.
	 */
	public void setDamageFromScreen(String damageFromScreen) {
		this.damageFromScreen = damageFromScreen;
	}

	/**
	 * @return Returns the selectedMailBag.
	 */
	public String getSelectedMailBag() {
		return selectedMailBag;
	}

	/**
	 * @param selectedMailBag The selectedMailBag to set.
	 */
	public void setSelectedMailBag(String selectedMailBag) {
		this.selectedMailBag = selectedMailBag;
	}

	/**
	 * @return Returns the damageCheckAll.
	 */
	public String getDamageCheckAll() {
		return damageCheckAll;
	}

	/**
	 * @param damageCheckAll The damageCheckAll to set.
	 */
	public void setDamageCheckAll(String damageCheckAll) {
		this.damageCheckAll = damageCheckAll;
	}

	/**
	 * @return Returns the damageSubCheck.
	 */
	public String[] getDamageSubCheck() {
		return damageSubCheck;
	}

	/**
	 * @param damageSubCheck The damageSubCheck to set.
	 */
	public void setDamageSubCheck(String[] damageSubCheck) {
		this.damageSubCheck = damageSubCheck;
	}

	/**
	 * @return Returns the damageCode.
	 */
	public String[] getDamageCode() {
		return damageCode;
	}

	/**
	 * @param damageCode The damageCode to set.
	 */
	public void setDamageCode(String[] damageCode) {
		this.damageCode = damageCode;
	}

	/**
	 * @return Returns the damageRemarks.
	 */
	public String[] getDamageRemarks() {
		return damageRemarks;
	}

	/**
	 * @param damageRemarks The damageRemarks to set.
	 */
	public void setDamageRemarks(String[] damageRemarks) {
		this.damageRemarks = damageRemarks;
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
	 * @return Returns the departurePort.
	 */
	public String getDeparturePort() {
		return departurePort;
	}

	/**
	 * @param departurePort The departurePort to set.
	 */
	public void setDeparturePort(String departurePort) {
		this.departurePort = departurePort;
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
	 * @return Returns the flightCarrierCode.
	 */
	public String getFlightCarrierCode() {
		return flightCarrierCode;
	}

	/**
	 * @param flightCarrierCode The flightCarrierCode to set.
	 */
	public void setFlightCarrierCode(String flightCarrierCode) {
		this.flightCarrierCode = flightCarrierCode;
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
     * @return SCREEN_ID - String
     */
    public String getScreenId() {
        return SCREEN_ID;
    }

    /**
     * @return PRODUCT_NAME - String
     */
    public String getProduct() {
        return PRODUCT_NAME;
    }

    /**
     * @return SUBPRODUCT_NAME - String
     */
    public String getSubProduct() {
        return SUBPRODUCT_NAME;
    }

	/**
	 * @return Returns the bundle.
	 */
	public String getBundle() {
		return BUNDLE;
	}

	/**
	 * @return Returns the closeFlag.
	 */
	public String getCloseFlag() {
		return this.closeFlag;
	}

	/**
	 * @param closeFlag The closeFlag to set.
	 */
	public void setCloseFlag(String closeFlag) {
		this.closeFlag = closeFlag;
	}
	
	/**
	 * @return Returns the flightNo.
	 */
	public String getFlightNo() {
		return this.flightNo;
	}

	/**
	 * @param flightNo The flightNo to set.
	 */
	public void setFlightNo(String flightNo) {
		this.flightNo = flightNo;
	}


	/**
	 * @return Returns the assignToFlight.
	 */
	public String getAssignToFlight() {
		return this.assignToFlight;
	}

	/**
	 * @param assignToFlight The assignToFlight to set.
	 */
	public void setAssignToFlight(String assignToFlight) {
		this.assignToFlight = assignToFlight;
	}

	/**
	 * @return Returns the carrierCode.
	 */
	public String getCarrierCode() {
		return this.carrierCode;
	}

	/**
	 * @param carrierCode The carrierCode to set.
	 */
	public void setCarrierCode(String carrierCode) {
		this.carrierCode = carrierCode;
	}

	/**
	 * @return Returns the depDate.
	 */
	public String getDepDate() {
		return this.depDate;
	}

	/**
	 * @param depDate The depDate to set.
	 */
	public void setDepDate(String depDate) {
		this.depDate = depDate;
	}

	/**
	 * @return Returns the selectMail.
	 */
	public String[] getSelectMail() {
		return this.selectMail;
	}

	/**
	 * @param selectMail The selectMail to set.
	 */
	public void setSelectMail(String[] selectMail) {
		this.selectMail = selectMail;
	}

	/**
	 * @return Returns the duplicateFlightStatus.
	 */
	public String getDuplicateFlightStatus() {
		return this.duplicateFlightStatus;
	}

	/**
	 * @param duplicateFlightStatus The duplicateFlightStatus to set.
	 */
	public void setDuplicateFlightStatus(String duplicateFlightStatus) {
		this.duplicateFlightStatus = duplicateFlightStatus;
	}

	/**
	 * @return Returns the accNoBags.
	 */
	public String[] getAccNoBags() {
		return this.accNoBags;
	}

	/**
	 * @param accNoBags The accNoBags to set.
	 */
	public void setAccNoBags(String[] accNoBags) {
		this.accNoBags = accNoBags;
	}

	/**
	 * @return Returns the accWt.
	 */
	public String[] getAccWt() {
		return this.accWt;
	}

	/**
	 * @param accWt The accWt to set.
	 */
	public void setAccWt(String[] accWt) {
		this.accWt = accWt;
	}

	/**
	 * @return Returns the conDocNo.
	 */
	public String[] getConDocNo() {
		return this.conDocNo;
	}

	/**
	 * @param conDocNo The conDocNo to set.
	 */
	public void setConDocNo(String[] conDocNo) {
		this.conDocNo = conDocNo;
	}

	/**
	 * @return Returns the containerNo.
	 */
	public String getContainerNo() {
		return this.containerNo;
	}

	/**
	 * @param containerNo The containerNo to set.
	 */
	public void setContainerNo(String containerNo) {
		this.containerNo = containerNo;
	}

	/**
	 * @return Returns the despatchClass.
	 */
	public String[] getDespatchClass() {
		return this.despatchClass;
	}

	/**
	 * @param despatchClass The despatchClass to set.
	 */
	public void setDespatchClass(String[] despatchClass) {
		this.despatchClass = despatchClass;
	}

	/**
	 * @return Returns the despatchDate.
	 */
	public String[] getDespatchDate() {
		return this.despatchDate;
	}

	/**
	 * @param despatchDate The despatchDate to set.
	 */
	public void setDespatchDate(String[] despatchDate) {
		this.despatchDate = despatchDate;
	}

	/**
	 * @return Returns the despatchDOE.
	 */
	public String[] getDespatchDOE() {
		return this.despatchDOE;
	}

	/**
	 * @param despatchDOE The despatchDOE to set.
	 */
	public void setDespatchDOE(String[] despatchDOE) {
		this.despatchDOE = despatchDOE;
	}

	/**
	 * @return Returns the despatchDSN.
	 */
	public String[] getDespatchDSN() {
		return this.despatchDSN;
	}

	/**
	 * @param despatchDSN The despatchDSN to set.
	 */
	public void setDespatchDSN(String[] despatchDSN) {
		this.despatchDSN = despatchDSN;
	}

	/**
	 * @return Returns the despatchOOE.
	 */
	public String[] getDespatchOOE() {
		return this.despatchOOE;
	}

	/**
	 * @param despatchOOE The despatchOOE to set.
	 */
	public void setDespatchOOE(String[] despatchOOE) {
		this.despatchOOE = despatchOOE;
	}

	/**
	 * @return Returns the location.
	 */
	public String getLocation() {
		return this.location;
	}

	/**
	 * @param location The location to set.
	 */
	public void setLocation(String location) {
		this.location = location;
	}

	/**
	 * @return Returns the mailCat.
	 */
	public String[] getMailCat() {
		return this.mailCat;
	}

	/**
	 * @param mailCat The mailCat to set.
	 */
	public void setMailCat(String[] mailCat) {
		this.mailCat = mailCat;
	}

	/**
	 * @return Returns the mailDamaged.
	 */
	public String[] getMailDamaged() {
		return this.mailDamaged;
	}

	/**
	 * @param mailDamaged The mailDamaged to set.
	 */
	public void setMailDamaged(String[] mailDamaged) {
		this.mailDamaged = mailDamaged;
	}

	/**
	 * @return Returns the mailDOE.
	 */
	public String[] getMailDOE() {
		return this.mailDOE;
	}

	/**
	 * @param mailDOE The mailDOE to set.
	 */
	public void setMailDOE(String[] mailDOE) {
		this.mailDOE = mailDOE;
	}

	/**
	 * @return Returns the mailDSN.
	 */
	public String[] getMailDSN() {
		return this.mailDSN;
	}

	/**
	 * @param mailDSN The mailDSN to set.
	 */
	public void setMailDSN(String[] mailDSN) {
		this.mailDSN = mailDSN;
	}

	/**
	 * @return Returns the mailHNI.
	 */
	public String[] getMailHNI() {
		return this.mailHNI;
	}

	/**
	 * @param mailHNI The mailHNI to set.
	 */
	public void setMailHNI(String[] mailHNI) {
		this.mailHNI = mailHNI;
	}

	/**
	 * @return Returns the mailOOE.
	 */
	public String[] getMailOOE() {
		return this.mailOOE;
	}

	/**
	 * @param mailOOE The mailOOE to set.
	 */
	public void setMailOOE(String[] mailOOE) {
		this.mailOOE = mailOOE;
	}

	/**
	 * @return Returns the mailRI.
	 */
	public String[] getMailRI() {
		return this.mailRI;
	}

	/**
	 * @param mailRI The mailRI to set.
	 */
	public void setMailRI(String[] mailRI) {
		this.mailRI = mailRI;
	}

	/**
	 * @return Returns the mailRSN.
	 */
	public String[] getMailRSN() {
		return this.mailRSN;
	}

	/**
	 * @param mailRSN The mailRSN to set.
	 */
	public void setMailRSN(String[] mailRSN) {
		this.mailRSN = mailRSN;
	}

	/**
	 * @return Returns the mailSC.
	 */
	public String[] getMailSC() {
		return this.mailSC;
	}

	/**
	 * @param mailSC The mailSC to set.
	 */
	public void setMailSC(String[] mailSC) {
		this.mailSC = mailSC;
	}

	/**
	 * @return Returns the mailScanDate.
	 */
	public String[] getMailScanDate() {
		return this.mailScanDate;
	}

	/**
	 * @param mailScanDate The mailScanDate to set.
	 */
	public void setMailScanDate(String[] mailScanDate) {
		this.mailScanDate = mailScanDate;
	}
	/**
	 * 
	 * 	Method		:	MailAcceptanceForm.getMailWtMeasure
	 *	Added by 	:	A-7371 on 04-Aug-2017
	 * 	Used for 	:
	 *	Parameters	:	@return 
	 *	Return type	: 	Measure[]
	 */
	public Measure[] getMailWtMeasure() {
		return mailWtMeasure;
	}
/**
 * 
 * 	Method		:	MailAcceptanceForm.setMailWtMeasure
 *	Added by 	:	A-7371 on 04-Aug-2017
 * 	Used for 	:
 *	Parameters	:	@param mailWtMeasure 
 *	Return type	: 	void
 */
	public void setMailWtMeasure(Measure[] mailWtMeasure) {
		this.mailWtMeasure = mailWtMeasure;
	}

	/**
	 * @return Returns the mailWt.
	 */
	public String[] getMailWt() {
		return this.mailWt;
	}

	/**
	 * @param mailWt The mailWt to set.
	 */
	public void setMailWt(String[] mailWt) {
		this.mailWt = mailWt;
	}

	/**
	 * @return Returns the mailYr.
	 */
	public String[] getMailYr() {
		return this.mailYr;
	}

	/**
	 * @param mailYr The mailYr to set.
	 */
	public void setMailYr(String[] mailYr) {
		this.mailYr = mailYr;
	}

	/**
	 * @return Returns the onwardFlights.
	 */
	public String getOnwardFlights() {
		return this.onwardFlights;
	}

	/**
	 * @param onwardFlights The onwardFlights to set.
	 */
	public void setOnwardFlights(String onwardFlights) {
		this.onwardFlights = onwardFlights;
	}

	/**
	 * @return Returns the paBuilt.
	 */
	public String getPaBuilt() {
		return this.paBuilt;
	}

	/**
	 * @param paBuilt The paBuilt to set.
	 */
	public void setPaBuilt(String paBuilt) {
		this.paBuilt = paBuilt;
	}

	/**
	 * @return Returns the popupCloseFlag.
	 */
	public String getPopupCloseFlag() {
		return this.popupCloseFlag;
	}

	/**
	 * @param popupCloseFlag The popupCloseFlag to set.
	 */
	public void setPopupCloseFlag(String popupCloseFlag) {
		this.popupCloseFlag = popupCloseFlag;
	}

	/**
	 * @return Returns the remarks.
	 */
	public String getRemarks() {
		return this.remarks;
	}

	/**
	 * @param remarks The remarks to set.
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	/**
	 * @return Returns the selectDespatch.
	 */
	public String[] getSelectDespatch() {
		return this.selectDespatch;
	}

	/**
	 * @param selectDespatch The selectDespatch to set.
	 */
	public void setSelectDespatch(String[] selectDespatch) {
		this.selectDespatch = selectDespatch;
	}

	/**
	 * @return Returns the selectMailTag.
	 */
	public String[] getSelectMailTag() {
		return this.selectMailTag;
	}

	/**
	 * @param selectMailTag The selectMailTag to set.
	 */
	public void setSelectMailTag(String[] selectMailTag) {
		this.selectMailTag = selectMailTag;
	}

	/**
	 * @return Returns the statedNoBags.
	 */
	public String[] getStatedNoBags() {
		return this.statedNoBags;
	}

	/**
	 * @param statedNoBags The statedNoBags to set.
	 */
	public void setStatedNoBags(String[] statedNoBags) {
		this.statedNoBags = statedNoBags;
	}

	/**
	 * @return Returns the statedWt.
	 */
	public String[] getStatedWt() {
		return this.statedWt;
	}

	/**
	 * @param statedWt The statedWt to set.
	 */
	public void setStatedWt(String[] statedWt) {
		this.statedWt = statedWt;
	}

	/**
	 * @return Returns the warehouse.
	 */
	public String getWarehouse() {
		return this.warehouse;
	}

	/**
	 * @param warehouse The warehouse to set.
	 */
	public void setWarehouse(String warehouse) {
		this.warehouse = warehouse;
	}

	/**
	 * @return Returns the disableDestnFlag.
	 */
	public String getDisableDestnFlag() {
		return this.disableDestnFlag;
	}

	/**
	 * @param disableDestnFlag The disableDestnFlag to set.
	 */
	public void setDisableDestnFlag(String disableDestnFlag) {
		this.disableDestnFlag = disableDestnFlag;
	}

	/**
	 * @return Returns the disableSaveFlag.
	 */
	public String getDisableSaveFlag() {
		return this.disableSaveFlag;
	}

	/**
	 * @param disableSaveFlag The disableSaveFlag to set.
	 */
	public void setDisableSaveFlag(String disableSaveFlag) {
		this.disableSaveFlag = disableSaveFlag;
	}

	/**
	 * @return Returns the selectULDs.
	 */
	public String[] getSelectULDs() {
		return selectULDs;
	}

	/**
	 * @param selectULDs The selectULDs to set.
	 */
	public void setSelectULDs(String[] selectULDs) {
		this.selectULDs = selectULDs;
	}

	/**
	 * @return Returns the uldsSelectedFlag.
	 */
	public String getUldsSelectedFlag() {
		return uldsSelectedFlag;
	}

	/**
	 * @param uldsSelectedFlag The uldsSelectedFlag to set.
	 */
	public void setUldsSelectedFlag(String uldsSelectedFlag) {
		this.uldsSelectedFlag = uldsSelectedFlag;
	}

	/**
	 * @return Returns the despatchYear.
	 */
	public String[] getDespatchYear() {
		return this.despatchYear;
	}

	/**
	 * @param despatchYear The despatchYear to set.
	 */
	public void setDespatchYear(String[] despatchYear) {
		this.despatchYear = despatchYear;
	}

	/**
	 * @return Returns the preAdviceFlag.
	 */
	public String getPreAdviceFlag() {
		return preAdviceFlag;
	}

	/**
	 * @param preAdviceFlag The preAdviceFlag to set.
	 */
	public void setPreAdviceFlag(String preAdviceFlag) {
		this.preAdviceFlag = preAdviceFlag;
	}

	/**
	 * @return Returns the initialFocus.
	 */
	public String getInitialFocus() {
		return this.initialFocus;
	}

	/**
	 * @param initialFocus The initialFocus to set.
	 */
	public void setInitialFocus(String initialFocus) {
		this.initialFocus = initialFocus;
	}

	/**
	 * @return Returns the uldsPopupCloseFlag.
	 */
	public String getUldsPopupCloseFlag() {
		return uldsPopupCloseFlag;
	}

	/**
	 * @param uldsPopupCloseFlag The uldsPopupCloseFlag to set.
	 */
	public void setUldsPopupCloseFlag(String uldsPopupCloseFlag) {
		this.uldsPopupCloseFlag = uldsPopupCloseFlag;
	}

	/**
	 * @return Returns the fromScreen.
	 */
	public String getFromScreen() {
		return this.fromScreen;
	}

	/**
	 * @param fromScreen The fromScreen to set.
	 */
	public void setFromScreen(String fromScreen) {
		this.fromScreen = fromScreen;
	}

	/**
	 * @return Returns the despatchCat.
	 */
	public String[] getDespatchCat() {
		return this.despatchCat;
	}

	/**
	 * @param despatchCat The despatchCat to set.
	 */
	public void setDespatchCat(String[] despatchCat) {
		this.despatchCat = despatchCat;
	}

	/**
	 * @return Returns the closeflight.
	 */
	public String getCloseflight() {
		return this.closeflight;
	}

	/**
	 * @param closeflight The closeflight to set.
	 */
	public void setCloseflight(String closeflight) {
		this.closeflight = closeflight;
	}

	/**
	 * @return Returns the despatchPA.
	 */
	public String[] getDespatchPA() {
		return this.despatchPA;
	}

	/**
	 * @param despatchPA The despatchPA to set.
	 */
	public void setDespatchPA(String[] despatchPA) {
		this.despatchPA = despatchPA;
	}

	/**
	 * @return Returns the despatchSC.
	 */
	public String[] getDespatchSC() {
		return despatchSC;
	}

	/**
	 * @param despatchSC The despatchSC to set.
	 */
	public void setDespatchSC(String[] despatchSC) {
		this.despatchSC = despatchSC;
	}

	/**
	 * @return Returns the consignNum.
	 */
	public String getConsignNum() {
		return this.consignNum;
	}

	/**
	 * @param consignNum The consignNum to set.
	 */
	public void setConsignNum(String consignNum) {
		this.consignNum = consignNum;
	}

	/**
	 * @return Returns the consignPa.
	 */
	public String getConsignPa() {
		return this.consignPa;
	}

	/**
	 * @param consignPa The consignPa to set.
	 */
	public void setConsignPa(String consignPa) {
		this.consignPa = consignPa;
	}

	/**
	 * @return Returns the consignFlag.
	 */
	public String getConsignFlag() {
		return this.consignFlag;
	}

	/**
	 * @param consignFlag The consignFlag to set.
	 */
	public void setConsignFlag(String consignFlag) {
		this.consignFlag = consignFlag;
	}

	/**
	 * @return Returns the scanTimeFlag.
	 */
	public String getScanTimeFlag() {
		return this.scanTimeFlag;
	}

	/**
	 * @param scanTimeFlag The scanTimeFlag to set.
	 */
	public void setScanTimeFlag(String scanTimeFlag) {
		this.scanTimeFlag = scanTimeFlag;
	}

	/**
	 * @return Returns the scanDate.
	 */
	public String getScanDate() {
		return this.scanDate;
	}

	/**
	 * @param scanDate The scanDate to set.
	 */
	public void setScanDate(String scanDate) {
		this.scanDate = scanDate;
	}

	/**
	 * @return Returns the scanTime.
	 */
	public String getScanTime() {
		return this.scanTime;
	}

	/**
	 * @param scanTime The scanTime to set.
	 */
	public void setScanTime(String scanTime) {
		this.scanTime = scanTime;
	}

	/**
	 * @return Returns the mailScanTime.
	 */
	public String[] getMailScanTime() {
		return this.mailScanTime;
	}

	/**
	 * @param mailScanTime The mailScanTime to set.
	 */
	public void setMailScanTime(String[] mailScanTime) {
		this.mailScanTime = mailScanTime;
	}

	/**
	 * @return Returns the carrier.
	 */
	public String getCarrier() {
		return this.carrier;
	}

	/**
	 * @param carrier The carrier to set.
	 */
	public void setCarrier(String carrier) {
		this.carrier = carrier;
	}

	/**
	 * @return Returns the containerType.
	 */
	public String getContainerType() {
		return this.containerType;
	}

	/**
	 * @param containerType The containerType to set.
	 */
	public void setContainerType(String containerType) {
		this.containerType = containerType;
	}

	/**
	 * @return Returns the destn.
	 */
	public String getDestn() {
		return this.destn;
	}

	/**
	 * @param destn The destn to set.
	 */
	public void setDestn(String destn) {
		this.destn = destn;
	}

	/**
	 * @return Returns the pou.
	 */
	public String getPou() {
		return this.pou;
	}

	/**
	 * @param pou The pou to set.
	 */
	public void setPou(String pou) {
		this.pou = pou;
	}

	/**
	 * @return Returns the suggestValue.
	 */
	public String getSuggestValue() {
		return this.suggestValue;
	}

	/**
	 * @param suggestValue The suggestValue to set.
	 */
	public void setSuggestValue(String suggestValue) {
		this.suggestValue = suggestValue;
	}

	/**
	 * @return Returns the preassignFlag.
	 */
	public String getPreassignFlag() {
		return this.preassignFlag;
	}

	/**
	 * @param preassignFlag The preassignFlag to set.
	 */
	public void setPreassignFlag(String preassignFlag) {
		this.preassignFlag = preassignFlag;
	}

	/**
	 * @return Returns the disableFlag.
	 */
	public String getDisableFlag() {
		return this.disableFlag;
	}

	/**
	 * @param disableFlag The disableFlag to set.
	 */
	public void setDisableFlag(String disableFlag) {
		this.disableFlag = disableFlag;
	}

	/**
	 * @return Returns the hiddenContainer.
	 */
	public String getHiddenContainer() {
		return this.hiddenContainer;
	}

	/**
	 * @param hiddenContainer The hiddenContainer to set.
	 */
	public void setHiddenContainer(String hiddenContainer) {
		this.hiddenContainer = hiddenContainer;
	}

	/**
	 * @return Returns the mailCarrier.
	 */
	public String[] getMailCarrier() {
		return this.mailCarrier;
	}

	/**
	 * @param mailCarrier The mailCarrier to set.
	 */
	public void setMailCarrier(String[] mailCarrier) {
		this.mailCarrier = mailCarrier;
	}

	/**
	 * @return Returns the warningOveride.
	 */
	public String getWarningOveride() {
		return this.warningOveride;
	}

	/**
	 * @param warningOveride The warningOveride to set.
	 */
	public void setWarningOveride(String warningOveride) {
		this.warningOveride = warningOveride;
	}

	/**
	 * @return Returns the overrideUMSFlag.
	 */
	public String getOverrideUMSFlag() {
		return this.overrideUMSFlag;
	}

	/**
	 * @param overrideUMSFlag The overrideUMSFlag to set.
	 */
	public void setOverrideUMSFlag(String overrideUMSFlag) {
		this.overrideUMSFlag = overrideUMSFlag;
	}

	public String[] getDespatchOpFlag() {
		return despatchOpFlag;
	}

	public void setDespatchOpFlag(String[] despatchOpFlag) {
		this.despatchOpFlag = despatchOpFlag;
	}

	public String[] getMailOpFlag() {
		return mailOpFlag;
	}

	public void setMailOpFlag(String[] mailOpFlag) {
		this.mailOpFlag = mailOpFlag;
	}

	public String getWarningFlag() {
		return warningFlag;
	}

	public void setWarningFlag(String warningFlag) {
		this.warningFlag = warningFlag;
	}

	public String getScanTimeFromScreen() {
		return scanTimeFromScreen;
	}

	public void setScanTimeFromScreen(String scanTimeFromScreen) {
		this.scanTimeFromScreen = scanTimeFromScreen;
	}

	public String getReassignScreenFlag() {
		return reassignScreenFlag;
	}

	public void setReassignScreenFlag(String reassignScreenFlag) {
		this.reassignScreenFlag = reassignScreenFlag;
	}

	public String getStrToDelivery() {
		return strToDelivery;
	}

	public void setStrToDelivery(String strToDelivery) {
		this.strToDelivery = strToDelivery;
	}

	public String getSelCont() {
		return selCont;
	}

	public void setSelCont(String selCont) {
		this.selCont = selCont;
	}

	/**
	 * @return the uldnos
	 */
	public String[] getUldnos() {
		return uldnos;
	}

	/**
	 * @param uldnos the uldnos to set
	 */
	public void setUldnos(String[] uldnos) {
		this.uldnos = uldnos;
	}

	/**
	 * @return the existingMailbagFlag
	 */
	public String getExistingMailbagFlag() {
		return existingMailbagFlag;
	}

	/**
	 * @param existingMailbagFlag the existingMailbagFlag to set
	 */
	public void setExistingMailbagFlag(String existingMailbagFlag) {
		this.existingMailbagFlag = existingMailbagFlag;
	}

	/**
	 * @return the currentAirport
	 */
	public String getCurrentStation() {
		return currentStation;
	}

	/**
	 * @param currentAirport the currentAirport to set
	 */
	public void setCurrentStation(String currentStation) {
		this.currentStation = currentStation;
	}

	public String[] getMailVolume() {
		return mailVolume;
	}

	public void setMailVolume(String[] mailVolume) {
		this.mailVolume = mailVolume;
	}

	public String[] getStdVolume() {
		return stdVolume;
	}

	public void setStdVolume(String[] stdVolume) {
		this.stdVolume = stdVolume;
	}

	public String getDensity() {
		return density;
	}

	public void setDensity(String density) {
		this.density = density;
	}

	/**
	 * @return the captureULDDamageFlag
	 */
	public String getCaptureULDDamageFlag() {
		return captureULDDamageFlag;
	}

	/**
	 * @param captureULDDamageFlag the captureULDDamageFlag to set
	 */
	public void setCaptureULDDamageFlag(String captureULDDamageFlag) {
		this.captureULDDamageFlag = captureULDDamageFlag;
	}

	public String[] getAccVolume() {
		return accVolume;
	}

	public void setAccVolume(String[] accVolume) {
		this.accVolume = accVolume;
	}

	public String getBellyCarditId() {
		return bellyCarditId;
	}

	public void setBellyCarditId(String bellyCarditId) {
		this.bellyCarditId = bellyCarditId;
	}

	public String[] getMailCartId() {
		return mailCartId;
	}

	public void setMailCartId(String[] mailCartId) {
		this.mailCartId = mailCartId;
	}

	public String getPaCode() {
		return paCode;
	}

	public void setPaCode(String paCode) {
		this.paCode = paCode;
	}

	/**
	 * @return the consignmentDocNum
	 */
	public String getConsignmentDocNum() {
		return consignmentDocNum;
	}

	/**
	 * @param consignmentDocNum the consignmentDocNum to set
	 */
	public void setConsignmentDocNum(String consignmentDocNum) {
		this.consignmentDocNum = consignmentDocNum;
	}

	/**
	 * @return the displayPageForCardit
	 */
	public String getDisplayPageForCardit() {
		return displayPageForCardit;
	}

	/**
	 * @param displayPageForCardit the displayPageForCardit to set
	 */
	public void setDisplayPageForCardit(String displayPageForCardit) {
		this.displayPageForCardit = displayPageForCardit;
	}

	/**
	 * @return the sealNo
	 */
	public String[] getSealNo() {
		return sealNo;
	}

	/**
	 * @param sealNo the sealNo to set
	 */
	public void setSealNo(String[] sealNo) {
		this.sealNo = sealNo;
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
	  public String getEmbargoFlag() {
		return embargoFlag;
	}

	public void setEmbargoFlag(String embargoFlag) {
		this.embargoFlag = embargoFlag;
	}

	public boolean isCanCheckEmbargo() {
		return canCheckEmbargo;
	}

	public void setCanCheckEmbargo(boolean canCheckEmbargo) {
		this.canCheckEmbargo = canCheckEmbargo;
	}
	public String getDuplicateAndTbaTbc() {
		return duplicateAndTbaTbc;
	}
	public void setDuplicateAndTbaTbc(String duplicateAndTbaTbc) {
		this.duplicateAndTbaTbc = duplicateAndTbaTbc;
	}
	/**
	 * Method to retrieve the value of mailCompanyCode
	 */
	public String[] getMailCompanyCode() {
		return mailCompanyCode;
	}
	/**
	 * Method to set the value of mailCompanyCode
	 * @param mailCompanyCode
	 */
	public void setMailCompanyCode(String[] mailCompanyCode) {
		this.mailCompanyCode = mailCompanyCode;
	}
	// Added as part of CRQ ICRD-118163 by A-5526 starts
	/**
	 * Method to retrieve the value of deleteAgreeFlag
	 */
	public String getDeleteAgreeFlag() {
		return deleteAgreeFlag;
	}
/**
 * Method to set the value of deleteAgreeFlag
 * @param deleteAgreeFlag
 */
	public void setDeleteAgreeFlag(String deleteAgreeFlag) {
		this.deleteAgreeFlag = deleteAgreeFlag;
	}
	// Added as part of CRQ ICRD-118163 by A-5526 ends
/**
 * @return the barrowCheck
 */
public boolean isBarrowCheck() {
	return barrowCheck;
}
/**
 * @param barrowCheck the barrowCheck to set
 */
public void setBarrowCheck(boolean barrowCheck) {
	this.barrowCheck = barrowCheck;
}
/**
 * @return the saveSuccessFlag
 */
public String getSaveSuccessFlag() {
	return saveSuccessFlag;
}
/**
 * @param saveSuccessFlag the saveSuccessFlag to set
 */
public void setSaveSuccessFlag(String saveSuccessFlag) {
	this.saveSuccessFlag = saveSuccessFlag;
}

public void setModify(String modify) {
	this.modify = modify;
}

public String getModify() {
	return modify;
}

/**
 * 	Getter for mailbagId 
 *	Added by : a-6245 on 07-Jun-2017
 * 	Used for :
 */
public String[] getMailbagId() {
	return mailbagId;
}

/**
 *  @param mailbagId the mailbagId to set
 * 	Setter for mailbagId 
 *	Added by : a-6245 on 07-Jun-2017
 * 	Used for :
 */
public void setMailbagId(String[] mailbagId) {
	this.mailbagId = mailbagId;
}

/**
 * 	Getter for originOE 
 *	Added by : a-6245 on 07-Jun-2017
 * 	Used for :
 */
public String getOriginOE() {
	return originOE;
}

/**
 *  @param originOE the originOE to set
 * 	Setter for originOE 
 *	Added by : a-6245 on 07-Jun-2017
 * 	Used for :
 */
public void setOriginOE(String originOE) {
	this.originOE = originOE;
}

/**
 * 	Getter for destinationOE 
 *	Added by : a-6245 on 07-Jun-2017
 * 	Used for :
 */
public String getDestinationOE() {
	return destinationOE;
}

/**
 *  @param destinationOE the destinationOE to set
 * 	Setter for destinationOE 
 *	Added by : a-6245 on 07-Jun-2017
 * 	Used for :
 */
public void setDestinationOE(String destinationOE) {
	this.destinationOE = destinationOE;
}

/**
 * 	Getter for category 
 *	Added by : a-6245 on 07-Jun-2017
 * 	Used for :
 */
public String getCategory() {
	return category;
}

/**
 *  @param category the category to set
 * 	Setter for category 
 *	Added by : a-6245 on 07-Jun-2017
 * 	Used for :
 */
public void setCategory(String category) {
	this.category = category;
}

/**
 * 	Getter for subClass 
 *	Added by : a-6245 on 07-Jun-2017
 * 	Used for :
 */
public String getSubClass() {
	return subClass;
}

/**
 *  @param subClass the subClass to set
 * 	Setter for subClass 
 *	Added by : a-6245 on 07-Jun-2017
 * 	Used for :
 */
public void setSubClass(String subClass) {
	this.subClass = subClass;
}

/**
 * 	Getter for year 
 *	Added by : a-6245 on 07-Jun-2017
 * 	Used for :
 */
public String getYear() {
	return year;
}

/**
 *  @param year the year to set
 * 	Setter for year 
 *	Added by : a-6245 on 07-Jun-2017
 * 	Used for :
 */
public void setYear(String year) {
	this.year = year;
}

/**
 * 	Getter for dsn 
 *	Added by : a-6245 on 07-Jun-2017
 * 	Used for :
 */
public String getDsn() {
	return dsn;
}

/**
 *  @param dsn the dsn to set
 * 	Setter for dsn 
 *	Added by : a-6245 on 07-Jun-2017
 * 	Used for :
 */
public void setDsn(String dsn) {
	this.dsn = dsn;
}

/**
 * 	Getter for rsn 
 *	Added by : a-6245 on 07-Jun-2017
 * 	Used for :
 */
public String getRsn() {
	return rsn;
}

/**
 *  @param rsn the rsn to set
 * 	Setter for rsn 
 *	Added by : a-6245 on 07-Jun-2017
 * 	Used for :
 */
public void setRsn(String rsn) {
	this.rsn = rsn;
}

/**
 * 	Getter for hni 
 *	Added by : a-6245 on 07-Jun-2017
 * 	Used for :
 */
public String getHni() {
	return hni;
}

/**
 *  @param hni the hni to set
 * 	Setter for hni 
 *	Added by : a-6245 on 07-Jun-2017
 * 	Used for :
 */
public void setHni(String hni) {
	this.hni = hni;
}

/**
 * 	Getter for ri 
 *	Added by : a-6245 on 07-Jun-2017
 * 	Used for :
 */
public String getRi() {
	return ri;
}

/**
 *  @param ri the ri to set
 * 	Setter for ri 
 *	Added by : a-6245 on 07-Jun-2017
 * 	Used for :
 */
public void setRi(String ri) {
	this.ri = ri;
}

/**
 * 	Getter for wgt 
 *	Added by : a-6245 on 07-Jun-2017
 * 	Used for :
 */
public String getWgt() {
	return wgt;
}

/**
 *  @param wgt the wgt to set
 * 	Setter for wgt 
 *	Added by : a-6245 on 07-Jun-2017
 * 	Used for :
 */
public void setWgt(String wgt) {
	this.wgt = wgt;
}

/**
 * 	Getter for vol 
 *	Added by : a-6245 on 08-Jun-2017
 * 	Used for :
 */
public String getVol() {
	return vol;
}

/**
 *  @param vol the vol to set
 * 	Setter for vol 
 *	Added by : a-6245 on 08-Jun-2017
 * 	Used for :
 */
public void setVol(String vol) {
	this.vol = vol;
}

/**
 * 	Getter for mailId 
 *	Added by : a-6245 on 22-Jun-2017
 * 	Used for :
 */
public String getMailId() {
	return mailId;
}

/**
 *  @param mailId the mailId to set
 * 	Setter for mailId 
 *	Added by : a-6245 on 22-Jun-2017
 * 	Used for :
 */
public void setMailId(String mailId) {
	this.mailId = mailId;
}
/**
 * 
 * 	Method		:	MailAcceptanceForm.isInValidId
 *	Added by 	:	A-7371 on 11-Oct-2017
 * 	Used for 	:
 *	Parameters	:	@return 
 *	Return type	: 	boolean
 */
public boolean isInValidId() {
	return inValidId;
}
/**
 * 
 * 	Method		:	MailAcceptanceForm.setInValidId
 *	Added by 	:	A-7371 on 11-Oct-2017
 * 	Used for 	:
 *	Parameters	:	@param inValidId 
 *	Return type	: 	void
 */
public void setInValidId(boolean inValidId) {
	this.inValidId = inValidId;
}

/**
 * @return the disableButtonsForAirport
 * Added by A-7794 as part of ICRD-197439
 */
public String getDisableButtonsForAirport() {
	return disableButtonsForAirport;
}

/**
 * @param disableButtonsForAirport the disableButtonsForAirport to set
 * Added by A-7794 as part of ICRD-197439
 */
public void setDisableButtonsForAirport(String disableButtonsForAirport) {
	this.disableButtonsForAirport = disableButtonsForAirport;
}
/**
 * @author A-7371
 * @return
 */
public String getAddRowEnableFlag() {
	return addRowEnableFlag;
}
/**
 * @author A-7371
 * @param addRowEnableFlag
 */
public void setAddRowEnableFlag(String addRowEnableFlag) {
	this.addRowEnableFlag = addRowEnableFlag;
}

/**
 * 
 * @return transferContainerFlag
 * Added by A-7371 for ICRD-133987
 */
public String getTransferContainerFlag() {
	return transferContainerFlag;
}
/**
 * 
 * @param transferContainerFlag the transferContainerFlag to set
 * Added by A-7371 for ICRD-133987
 */
public void setTransferContainerFlag(String transferContainerFlag) {
	this.transferContainerFlag = transferContainerFlag;
}
/**
 * 
 * @return selectedContainer
 * Added by A-7371 for ICRD-133987
 */
public String getSelectedContainer() {
	return selectedContainer;
}
/**
 * 
 * @param selTransferContainer the selectedContainer to set
 * Added by A-7371 for ICRD-133987
 */
public void setSelectedContainer(String selectedContainer) {
	this.selectedContainer = selectedContainer;
}

/**
 * @author A-7371
 * @return canDiscardCoterminus
 */
public boolean isCanDiscardCoterminus() {
	return canDiscardCoterminus;
}
/**
* @author A-7371
* @param canDiscardCoterminus
*/
public void setCanDiscardCoterminus(boolean canDiscardCoterminus) {
	this.canDiscardCoterminus = canDiscardCoterminus;
}
/**
 * @author A-7371
 * @return warningStatus
 */
public String getWarningStatus() {
	return warningStatus;
}
/**
 * @author A-7371
 * @param warningStatus 
 */
public void setWarningStatus(String warningStatus) {
	this.warningStatus = warningStatus;
}

/**
 * @author A-8149
 * @return  uldType
 */
public String[] getUldType() {
	return uldType;
}
/**
 * @author A-8149
 * @param uldType
 */
public void setUldType(String[] uldType) {
	this.uldType = uldType;
}


/**
 * Getter for weightUnit
 * Added by:A-8353
 * @return weightUnit
 */
public String[] getWeightUnit() {
	return weightUnit;
}

/**
 * @param weightUnit
 * Setter for weightUnit
 *  Added by:A-8353
 */
public void setWeightUnit(String[] weightUnit) {
	this.weightUnit = weightUnit;
}
/**
 * Getter for defWeightUnit
 * Added by:A-8353
 * @return defWeightUnit
 */
public String getDefWeightUnit() {
	return defWeightUnit;
}
/**
 * @param defWeightUnit
 * Setter for defWeightUnit
 *  Added by:A-8353
 */
public void setDefWeightUnit(String defWeightUnit) {
	this.defWeightUnit = defWeightUnit;
}
/**
 * Getter for mailVolumeMeasure
 * Added by:A-8353
 * @return mailVolumeMeasure
 */

public Measure[] getMailVolumeMeasure() {
	return mailVolumeMeasure;
}
/**
 * @param mailVolumeMeasure
 * Setter for defWeightUnit
 *  Added by:A-8353
 */
public void setMailVolumeMeasure(Measure[] mailVolumeMeasure) {
	this.mailVolumeMeasure = mailVolumeMeasure;
}


}
