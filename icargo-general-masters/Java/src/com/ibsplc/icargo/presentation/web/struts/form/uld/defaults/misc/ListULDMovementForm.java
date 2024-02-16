/* ListULDMovementForm.java Created on Jan 31, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services(P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.misc;

import com.ibsplc.icargo.framework.model.ScreenModel;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.client.daterange.notation.DateFieldId;
/**
 * 
 * @author A-1862
 *
 */

public class ListULDMovementForm extends ScreenModel {
   
	  
 	 private static final String BUNDLE = "listuldmovementResources";
	 
	 private String bundle;    
     
	 private static final String PRODUCT = "uld";
	 
	 private static final String SUBPRODUCT = "defaults";
     
	 private static final String SCREENID = "uld.defaults.misc.listuldmovement";
     
         
     private boolean selectAll;
        
       
     private String uldNumber;
     
     private String fromDate;
     
     private String toDate;
     
     private LocalDate frmDate;
     
     private LocalDate tdate;
     
     
     private String ownerCode;
     
     private String ownerStation;
     
     private String currentStn;
     
     private String currentStatus;
     
        
     private String[] carrierCode;
     
     private String[] flightNumbers;
     
     private String[] flightDate;
     
     private String[] contents;
     
     private String[] fromStation;
     
     private String[] toStation;
     
     private String[] movementType; 
     
     private String displayPage = "1";
 	
     private String  lastPageNum = "0";
     
     private int selectFlag;
     
     private String screenloadstatus;
     
     private String statusFlag;
     
     private String displayPageFlag;
          
     
     private String[] movementDate;

  	 private String[] remarks;
  	
  	 private String reason;
 
     private String  movement;
      
     private String afterList;
     
     private int noOfMovements;
     
     private int noOfLoanTxns;
     
     private int noOfTimesDmged;
     
     private int noOfTimesRepaired;
     
     private String   transactionFlag;
     
    private String pageUrl;
    
    private String movementStatusFlag;
	public String getMovementStatusFlag() {
		return movementStatusFlag;
	}
	public void setMovementStatusFlag(String movementStatusFlag) {
		this.movementStatusFlag = movementStatusFlag;
	}
 	/**
 	 * uldNum
 	 */
 	private String uldNum;

 	/**
 	 * uldTypeCode
 	 */
 	private String uldTypeCode;

 	/**
 	 * accessoryCode
 	 */
 	private String accessoryCode;

 	/**
 	 * txnType
 	 */
 	private String txnType;

 	/**
 	 * txnStatus
 	 */
 	private String txnStatus;

 	/**
 	 * partyType
 	 */
 	private String partyType;

 	/**
 	 * partyCode
 	 */
 	private String fromPartyCode;

 	private String toPartyCode;

 	/**
 	 * txnStation
 	 */
 	private String txnStation;

 	/**
 	 * returnStation
 	 */
 	private String returnStation;

 	private String enquiryDisableStatus;

 	/**
 	 * txnFromDate
 	 */
 	private String txnFromDate;

 	/**
 	 * returnStation
 	 */
 	private String txnToDate;

 	/**
 	 * returnFromDate
 	 */
 	private String returnFromDate;

 	/**
 	 * returnToDate
 	 */
 	private String returnToDate;

 	/**
 	 * uldDetails
 	 */
 	private String[] uldDetails;

 	/**
 	 * accessoryQuantity
 	 */
 	private String[] accessoryDetails;

 	/**
 	 * closeFlag
 	 */
 	private String closeFlag;

 	/**
 	 * for modify txn details pop up
 	 * 
 	 * modULDNo
 	 */
 	private String modULDNo;

 	/**
 	 * modTxnType
 	 */
 	private String modTxnType;

 	/**
 	 * modPartyType
 	 */
 	private String modPartyType;

 	/**
 	 * modPartyCode
 	 */
 	private String modPartyCode;

 	/**
 	 * modTxnDate
 	 */
 	private String modTxnDate;
 	private String modTxnTime;

 	/**
 	 * modDuration
 	 */
 	private String modDuration;

 	/**
 	 * modTxnRemarks
 	 */
 	private String modTxnRemarks;

 	/**
 	 * closeModifyFlag
 	 */
 	private String closeModifyFlag;

 	/**
 	 * mode
 	 */
 	private String mode;

 	 
 	 private String lastPageNumber="0";
 	 private String totalRecords;
 	 private String currentPageNum;
 	 private String listMode="";
 	 private String showTab="";

 	/**
 	 * invoiceId
 	 */
 	private String invoiceId;
 /**
  * 
  */
 	private String popupflag;


 	private String comboFlag;

 	private String txnFrmTime;

 	private String txnToTime;

 	private String returnFrmTime;

 	private String returnToTime;

 	private String listStatus;
 	
 	//Added by Preet on 4th Jan for newly added fileds --CRN and ULD condition starts
 	private String modUldCondition;	
 	
 	private String modCRN; 
 	
 	private String modCrnPrefix; 
 	
 	//Added by Preet for newly added fileds --CRN and ULD condition ends	
 	private String uldNumbersSelected;
 	private String txnNumbersSelected;
 	
 	
 	// ends
 	//added by nisha for CR-15 on 21-11-07...indicates whether to open the msgbroker popup or not
 	private String msgFlag;
// 	added by nisha ends
 	
 	private String chkTransaction;
 	
 	
 	private String dmgSts;

	private String rprDate;

	private String damageCode;

	private String dmgSection;
	
	private String damage;
	
	private String dmgDate;

	private String position;

	private String damgeremarks;

	private String rprSts;

	private String rprAmt;

	private String invceSts;

	private String invceAmt;
	
	
	private String rprCode;
	
	private String date;
	
	private String status;
	
	private String carrier;
	
	private String no;
	
	private String regNo;
	
	
	private String isUldValid;

  	private String systemCurrency;		
 	
     
     
     /**
	 * @return the isUldValid
	 */
	public String getIsUldValid() {
		return isUldValid;
	}
	/**
	 * @param isUldValid the isUldValid to set
	 */
	public void setIsUldValid(String isUldValid) {
		this.isUldValid = isUldValid;
	}
	/**
	 * @return Returns the afterList.
	 */
	public String getAfterList() {
		return afterList;
	}
	/**
	 * @param afterList The afterList to set.
	 */
	public void setAfterList(String afterList) {
		this.afterList = afterList;
	}
	/**
	 * @return Returns the movement.
	 */
	public String getMovement() {
		return movement;
	}
	/**
	 * @param movement The movement to set.
	 */
	public void setMovement(String movement) {
		this.movement = movement;
	}
	/**
	 * @return Returns the movementDate.
	 */
	public String[] getMovementDate() {
		return movementDate;
	}
	/**
	 * @param movementDate The movementDate to set.
	 */
	public void setMovementDate(String[] movementDate) {
		this.movementDate = movementDate;
	}
	/**
	 * @return Returns the reason.
	 */
	public String getReason() {
		return reason;
	}
	/**
	 * @param reason The reason to set.
	 */
	public void setReason(String reason) {
		this.reason = reason;
	}
	/**
	 * @return Returns the remarks.
	 */
	public String[] getRemarks() {
		return remarks;
	}
	/**
	 * @param remarks The remarks to set.
	 */
	public void setRemarks(String[] remarks) {
		this.remarks = remarks;
	}
	public String getListStatus() {
		return listStatus;
	}
	public void setListStatus(String listStatus) {
		this.listStatus = listStatus;
	}
     //Added by a-2412 for DeleteULDMovement
     private String selectedRows[];
     
     /**
      * 
      * @return String
      */
     public String getStatusFlag() {
		return statusFlag;
	}
     /**
      * 
      * @param statusFlag
      */
	public void setStatusFlag(String statusFlag) {
		this.statusFlag = statusFlag;
	}
	/**
      * 
      * @return Returns the CarrierCode
      */
     public String[] getCarrierCode() {
		return carrierCode;
	 }
    /**
     * 
     * @param carrierCode The CarrierCode to be set
     */
	public void setCarrierCode(String[] carrierCode) {
		this.carrierCode = carrierCode;
	}
    /**
     * 
     * @return The contents to be set
     */
	public String[] getContents() {
		return contents;
	}
    /**
     * 
     * @param contents Returns the contents
     */
	public void setContent(String[] contents) {
		this.contents = contents;
	}
	
	
    /**
     * 
     * @return Returns the fromDate
     */
	@DateFieldId(id="ULDMovementHistoryDateRange",fieldType="from") //Added by T-1927 for ICRD-9704
	public String getFromDate() {
		return fromDate;
	}
    /**
     * 
     * @param fromDate The fromDate to be set
     */
	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}
	
	 /**
     * 
     * @return Returns the ownerStation
     */
	@DateFieldId(id="ULDMovementHistoryDateRange",fieldType="to") //Added by T-1927 for ICRD-9704
	public String getToDate() {
		return toDate;
	}
    /**
     * 
     * @param toDate
     */
	public void setToDate(String toDate) {
		this.toDate = toDate;
	}
	
	
	
	/**
     * 
     * @return Returns the ownerCode
     */
	public String getOwnerCode() {
		return ownerCode;
	}
    /**
     * 
     * @param ownerCode The ownerCode to be set
     */
	public void setOwnerCode(String ownerCode) {
		this.ownerCode = ownerCode;
	}	
	
	/**
     * 
     * @return Returns the ownerStation
     */
	public String getOwnerStation() {
		return ownerStation;
	}
    /**
     * 
     * @param ownerStation The ownerStation to be set
     */
	public void setOwnerStation(String ownerStation) {
		this.ownerStation = ownerStation;
	}
	
	
	
	/**
     * 
     * @return Returns the currentStn
     */
	public String getCurrentStn() {
		return currentStn;
	}
    /**
     * 
     * @param currentStn The currentStn to be set
     */
	public void setCurrentStn(String currentStn) {
		this.currentStn = currentStn;
	}
	
	
	
	
	/**
     * 
     * @return Returns the currentStatus
     */
	public String getCurrentStatus() {
		return currentStatus;
	}
    /**
     * 
     * @param currentStatus The currentStatus to be set
     */
	public void setCurrentStatus(String currentStatus) {
		this.currentStatus = currentStatus;
	}
    /**
     * 
     * @return Returns the FlightDate
     */
	public String[] getFlightDate() {
		return flightDate;
	}
    /**
     * 
     * @param flightDate The FlightDate to be set
     */
	public void setFlightDate(String[] flightDate) {
		this.flightDate = flightDate;
	}
    /**
     * 
     * @return Returns the FlightNumber
     */
	public String[] getFlightNumbers() {
		return flightNumbers;
	}
    /**
     * 
     * @param flightNumbers
     */
	public void setFlightNumbers(String[] flightNumbers) {
		this.flightNumbers = flightNumbers;
	}

    /**
     * 
     * @return Returns  the fromStation
     */
	public String[] getFromStation() {
		return fromStation;
	}
    /**
     * 
     * @param fromStation The fromStation to be set
     */
	public void setFromStation(String[] fromStation) {
		this.fromStation = fromStation;
	}
    /**
     * 
     * @return Returns the toStation
     */
	public String[] getToStation() {
		return toStation;
	}
    /**
      * 
      * @param toStation The toStation  to be set
      */
	public void setToStation(String[] toStation) {
		this.toStation = toStation;
	}
   
    /**
     * 
     * @return Returns the uldNumbers
     */
	public String getUldNumber() {
		return uldNumber;
	}
    /**
     * 
     * @param uldNumber
     */
	public void setUldNumber(String uldNumber) {
		this.uldNumber = uldNumber;
	}
   
	
	 /**
     * 
     * @return Returns the selectAll
     */
	public boolean getSelectAll() {
		return selectAll;
	}
	
	/**
     * 
     * @param selectAll
     */

	public void setSelectAll(boolean selectAll) {
		this.selectAll = selectAll;
	}
	

	/**
      * 
      * @param bundle The bundle to be set
      */
     public void setBundle(String bundle){
    	 this.bundle = bundle;
     }
     
     /**
  	 * @return Returns the bundle.
  	 */
  	public String getBundle() {
  		return BUNDLE;
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
       *  
       * @param movementType The movementType to be set
       */
     public void setMovementType(String[] movementType){
    	 this.movementType=movementType;
     }
 	 
      
     /**
      * 
      * @return Returns the movementType
      */
     public String[] getMovementType(){
    	 return movementType;
     }
     /**
      * 
      * @return
      */
     public String getDisplayPage() {
 		return displayPage;
 	}
/**
 * 
 * @param displayPage
 */
 	public void setDisplayPage(String displayPage) {
 		this.displayPage = displayPage;
 	}
/**
 * 
 * @return
 */
 	public String getLastPageNum() {
 		return lastPageNum;
 	}
/**
 * 
 * @param lastPageNum
 */
 	public void setLastPageNum(String lastPageNum) {
 		this.lastPageNum = lastPageNum;
 	}
 	/**
 	 * 
 	 * @return
 	 */
 	
 	public int getSelectFlag() {
		return selectFlag;
	}
/**
 * 
 * @param selectFlag
 */
	public void setSelectFlag(int selectFlag) {
		this.selectFlag = selectFlag;
	}
	/**
	 * @return Returns the screenloadStatus.
	 */
	public String getScreenloadstatus() {
		return screenloadstatus;
	}
	/**
	 * @param screenloadstatus The screenloadstatus to set.
	 */
	public void setScreenloadstatus(String screenloadstatus) {
		this.screenloadstatus = screenloadstatus;
	}
	public String[] getSelectedRows() {
		return selectedRows;
	}
	public void setSelectedRows(String[] selectedRows) {
		this.selectedRows = selectedRows;
	}
	/**
	 * @return Returns the displayPageFlag.
	 */
	public String getDisplayPageFlag() {
		return displayPageFlag;
	}
	/**
	 * @param displayPageFlag The displayPageFlag to set.
	 */
	public void setDisplayPageFlag(String displayPageFlag) {
		this.displayPageFlag = displayPageFlag;
	}
	/**
	 * @return Returns the accessoryCode.
	 */
	public String getAccessoryCode() {
		return accessoryCode;
	}
	/**
	 * @param accessoryCode The accessoryCode to set.
	 */
	public void setAccessoryCode(String accessoryCode) {
		this.accessoryCode = accessoryCode;
	}
	/**
	 * @return Returns the accessoryDetails.
	 */
	public String[] getAccessoryDetails() {
		return accessoryDetails;
	}
	/**
	 * @param accessoryDetails The accessoryDetails to set.
	 */
	public void setAccessoryDetails(String[] accessoryDetails) {
		this.accessoryDetails = accessoryDetails;
	}
	/**
	 * @return Returns the chkTransaction.
	 */
	public String getChkTransaction() {
		return chkTransaction;
	}
	/**
	 * @param chkTransaction The chkTransaction to set.
	 */
	public void setChkTransaction(String chkTransaction) {
		this.chkTransaction = chkTransaction;
	}
	/**
	 * @return Returns the closeFlag.
	 */
	public String getCloseFlag() {
		return closeFlag;
	}
	/**
	 * @param closeFlag The closeFlag to set.
	 */
	public void setCloseFlag(String closeFlag) {
		this.closeFlag = closeFlag;
	}
	/**
	 * @return Returns the closeModifyFlag.
	 */
	public String getCloseModifyFlag() {
		return closeModifyFlag;
	}
	/**
	 * @param closeModifyFlag The closeModifyFlag to set.
	 */
	public void setCloseModifyFlag(String closeModifyFlag) {
		this.closeModifyFlag = closeModifyFlag;
	}
	/**
	 * @return Returns the comboFlag.
	 */
	public String getComboFlag() {
		return comboFlag;
	}
	/**
	 * @param comboFlag The comboFlag to set.
	 */
	public void setComboFlag(String comboFlag) {
		this.comboFlag = comboFlag;
	}
	/**
	 * @return Returns the currentPageNum.
	 */
	public String getCurrentPageNum() {
		return currentPageNum;
	}
	/**
	 * @param currentPageNum The currentPageNum to set.
	 */
	public void setCurrentPageNum(String currentPageNum) {
		this.currentPageNum = currentPageNum;
	}
	/**
	 * @return Returns the enquiryDisableStatus.
	 */
	public String getEnquiryDisableStatus() {
		return enquiryDisableStatus;
	}
	/**
	 * @param enquiryDisableStatus The enquiryDisableStatus to set.
	 */
	public void setEnquiryDisableStatus(String enquiryDisableStatus) {
		this.enquiryDisableStatus = enquiryDisableStatus;
	}
	/**
	 * @return Returns the fromPartyCode.
	 */
	public String getFromPartyCode() {
		return fromPartyCode;
	}
	/**
	 * @param fromPartyCode The fromPartyCode to set.
	 */
	public void setFromPartyCode(String fromPartyCode) {
		this.fromPartyCode = fromPartyCode;
	}
	/**
	 * @return Returns the invoiceId.
	 */
	public String getInvoiceId() {
		return invoiceId;
	}
	/**
	 * @param invoiceId The invoiceId to set.
	 */
	public void setInvoiceId(String invoiceId) {
		this.invoiceId = invoiceId;
	}
	/**
	 * @return Returns the lastPageNumber.
	 */
	public String getLastPageNumber() {
		return lastPageNumber;
	}
	/**
	 * @param lastPageNumber The lastPageNumber to set.
	 */
	public void setLastPageNumber(String lastPageNumber) {
		this.lastPageNumber = lastPageNumber;
	}
	/**
	 * @return Returns the listMode.
	 */
	public String getListMode() {
		return listMode;
	}
	/**
	 * @param listMode The listMode to set.
	 */
	public void setListMode(String listMode) {
		this.listMode = listMode;
	}
	/**
	 * @return Returns the modCRN.
	 */
	public String getModCRN() {
		return modCRN;
	}
	/**
	 * @param modCRN The modCRN to set.
	 */
	public void setModCRN(String modCRN) {
		this.modCRN = modCRN;
	}
	/**
	 * @return Returns the modCrnPrefix.
	 */
	public String getModCrnPrefix() {
		return modCrnPrefix;
	}
	/**
	 * @param modCrnPrefix The modCrnPrefix to set.
	 */
	public void setModCrnPrefix(String modCrnPrefix) {
		this.modCrnPrefix = modCrnPrefix;
	}
	/**
	 * @return Returns the modDuration.
	 */
	public String getModDuration() {
		return modDuration;
	}
	/**
	 * @param modDuration The modDuration to set.
	 */
	public void setModDuration(String modDuration) {
		this.modDuration = modDuration;
	}
	/**
	 * @return Returns the mode.
	 */
	public String getMode() {
		return mode;
	}
	/**
	 * @param mode The mode to set.
	 */
	public void setMode(String mode) {
		this.mode = mode;
	}
	/**
	 * @return Returns the modPartyCode.
	 */
	public String getModPartyCode() {
		return modPartyCode;
	}
	/**
	 * @param modPartyCode The modPartyCode to set.
	 */
	public void setModPartyCode(String modPartyCode) {
		this.modPartyCode = modPartyCode;
	}
	/**
	 * @return Returns the modPartyType.
	 */
	public String getModPartyType() {
		return modPartyType;
	}
	/**
	 * @param modPartyType The modPartyType to set.
	 */
	public void setModPartyType(String modPartyType) {
		this.modPartyType = modPartyType;
	}
	/**
	 * @return Returns the modTxnDate.
	 */
	public String getModTxnDate() {
		return modTxnDate;
	}
	/**
	 * @param modTxnDate The modTxnDate to set.
	 */
	public void setModTxnDate(String modTxnDate) {
		this.modTxnDate = modTxnDate;
	}
	/**
	 * @return Returns the modTxnRemarks.
	 */
	public String getModTxnRemarks() {
		return modTxnRemarks;
	}
	/**
	 * @param modTxnRemarks The modTxnRemarks to set.
	 */
	public void setModTxnRemarks(String modTxnRemarks) {
		this.modTxnRemarks = modTxnRemarks;
	}
	/**
	 * @return Returns the modTxnTime.
	 */
	public String getModTxnTime() {
		return modTxnTime;
	}
	/**
	 * @param modTxnTime The modTxnTime to set.
	 */
	public void setModTxnTime(String modTxnTime) {
		this.modTxnTime = modTxnTime;
	}
	/**
	 * @return Returns the modTxnType.
	 */
	public String getModTxnType() {
		return modTxnType;
	}
	/**
	 * @param modTxnType The modTxnType to set.
	 */
	public void setModTxnType(String modTxnType) {
		this.modTxnType = modTxnType;
	}
	/**
	 * @return Returns the modUldCondition.
	 */
	public String getModUldCondition() {
		return modUldCondition;
	}
	/**
	 * @param modUldCondition The modUldCondition to set.
	 */
	public void setModUldCondition(String modUldCondition) {
		this.modUldCondition = modUldCondition;
	}
	/**
	 * @return Returns the modULDNo.
	 */
	public String getModULDNo() {
		return modULDNo;
	}
	/**
	 * @param modULDNo The modULDNo to set.
	 */
	public void setModULDNo(String modULDNo) {
		this.modULDNo = modULDNo;
	}
	/**
	 * @return Returns the msgFlag.
	 */
	public String getMsgFlag() {
		return msgFlag;
	}
	/**
	 * @param msgFlag The msgFlag to set.
	 */
	public void setMsgFlag(String msgFlag) {
		this.msgFlag = msgFlag;
	}
	/**
	 * @return Returns the partyType.
	 */
	public String getPartyType() {
		return partyType;
	}
	/**
	 * @param partyType The partyType to set.
	 */
	public void setPartyType(String partyType) {
		this.partyType = partyType;
	}
	/**
	 * @return Returns the popupflag.
	 */
	public String getPopupflag() {
		return popupflag;
	}
	/**
	 * @param popupflag The popupflag to set.
	 */
	public void setPopupflag(String popupflag) {
		this.popupflag = popupflag;
	}
	/**
	 * @return Returns the pRODUCT.
	 */
	public String getPRODUCT() {
		return PRODUCT;
	}
	/**
	 * @return Returns the returnFrmTime.
	 */
	public String getReturnFrmTime() {
		return returnFrmTime;
	}
	/**
	 * @param returnFrmTime The returnFrmTime to set.
	 */
	public void setReturnFrmTime(String returnFrmTime) {
		this.returnFrmTime = returnFrmTime;
	}
	/**
	 * @return Returns the returnFromDate.
	 */
	public String getReturnFromDate() {
		return returnFromDate;
	}
	/**
	 * @param returnFromDate The returnFromDate to set.
	 */
	public void setReturnFromDate(String returnFromDate) {
		this.returnFromDate = returnFromDate;
	}
	/**
	 * @return Returns the returnStation.
	 */
	public String getReturnStation() {
		return returnStation;
	}
	/**
	 * @param returnStation The returnStation to set.
	 */
	public void setReturnStation(String returnStation) {
		this.returnStation = returnStation;
	}
	/**
	 * @return Returns the returnToDate.
	 */
	public String getReturnToDate() {
		return returnToDate;
	}
	/**
	 * @param returnToDate The returnToDate to set.
	 */
	public void setReturnToDate(String returnToDate) {
		this.returnToDate = returnToDate;
	}
	/**
	 * @return Returns the returnToTime.
	 */
	public String getReturnToTime() {
		return returnToTime;
	}
	/**
	 * @param returnToTime The returnToTime to set.
	 */
	public void setReturnToTime(String returnToTime) {
		this.returnToTime = returnToTime;
	}
	/**
	 * @return Returns the showTab.
	 */
	public String getShowTab() {
		return showTab;
	}
	/**
	 * @param showTab The showTab to set.
	 */
	public void setShowTab(String showTab) {
		this.showTab = showTab;
	}
	/**
	 * @return Returns the toPartyCode.
	 */
	public String getToPartyCode() {
		return toPartyCode;
	}
	/**
	 * @param toPartyCode The toPartyCode to set.
	 */
	public void setToPartyCode(String toPartyCode) {
		this.toPartyCode = toPartyCode;
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
	/**
	 * @return Returns the txnFrmTime.
	 */
	public String getTxnFrmTime() {
		return txnFrmTime;
	}
	/**
	 * @param txnFrmTime The txnFrmTime to set.
	 */
	public void setTxnFrmTime(String txnFrmTime) {
		this.txnFrmTime = txnFrmTime;
	}
	/**
	 * @return Returns the txnFromDate.
	 */
	public String getTxnFromDate() {
		return txnFromDate;
	}
	/**
	 * @param txnFromDate The txnFromDate to set.
	 */
	public void setTxnFromDate(String txnFromDate) {
		this.txnFromDate = txnFromDate;
	}
	/**
	 * @return Returns the txnNumbersSelected.
	 */
	public String getTxnNumbersSelected() {
		return txnNumbersSelected;
	}
	/**
	 * @param txnNumbersSelected The txnNumbersSelected to set.
	 */
	public void setTxnNumbersSelected(String txnNumbersSelected) {
		this.txnNumbersSelected = txnNumbersSelected;
	}
	/**
	 * @return Returns the txnStation.
	 */
	public String getTxnStation() {
		return txnStation;
	}
	/**
	 * @param txnStation The txnStation to set.
	 */
	public void setTxnStation(String txnStation) {
		this.txnStation = txnStation;
	}
	/**
	 * @return Returns the txnStatus.
	 */
	public String getTxnStatus() {
		return txnStatus;
	}
	/**
	 * @param txnStatus The txnStatus to set.
	 */
	public void setTxnStatus(String txnStatus) {
		this.txnStatus = txnStatus;
	}
	/**
	 * @return Returns the txnToDate.
	 */
	public String getTxnToDate() {
		return txnToDate;
	}
	/**
	 * @param txnToDate The txnToDate to set.
	 */
	public void setTxnToDate(String txnToDate) {
		this.txnToDate = txnToDate;
	}
	/**
	 * @return Returns the txnToTime.
	 */
	public String getTxnToTime() {
		return txnToTime;
	}
	/**
	 * @param txnToTime The txnToTime to set.
	 */
	public void setTxnToTime(String txnToTime) {
		this.txnToTime = txnToTime;
	}
	/**
	 * @return Returns the txnType.
	 */
	public String getTxnType() {
		return txnType;
	}
	/**
	 * @param txnType The txnType to set.
	 */
	public void setTxnType(String txnType) {
		this.txnType = txnType;
	}
	/**
	 * @return Returns the uldDetails.
	 */
	public String[] getUldDetails() {
		return uldDetails;
	}
	/**
	 * @param uldDetails The uldDetails to set.
	 */
	public void setUldDetails(String[] uldDetails) {
		this.uldDetails = uldDetails;
	}
	/**
	 * @return Returns the uldNum.
	 */
	public String getUldNum() {
		return uldNum;
	}
	/**
	 * @param uldNum The uldNum to set.
	 */
	public void setUldNum(String uldNum) {
		this.uldNum = uldNum;
	}
	/**
	 * @return Returns the uldNumbersSelected.
	 */
	public String getUldNumbersSelected() {
		return uldNumbersSelected;
	}
	/**
	 * @param uldNumbersSelected The uldNumbersSelected to set.
	 */
	public void setUldNumbersSelected(String uldNumbersSelected) {
		this.uldNumbersSelected = uldNumbersSelected;
	}
	/**
	 * @return Returns the uldTypeCode.
	 */
	public String getUldTypeCode() {
		return uldTypeCode;
	}
	/**
	 * @param uldTypeCode The uldTypeCode to set.
	 */
	public void setUldTypeCode(String uldTypeCode) {
		this.uldTypeCode = uldTypeCode;
	}
	/**
	 * @param contents The contents to set.
	 */
	public void setContents(String[] contents) {
		this.contents = contents;
	}
	
	/**
	 * @return Returns the dmgDate.
	 */
	public String getDmgDate() {
		return dmgDate;
	}
	/**
	 * @param dmgDate The dmgDate to set.
	 */
	public void setDmgDate(String dmgDate) {
		this.dmgDate = dmgDate;
	}
	/**
	 * @return Returns the dmgSection.
	 */
	public String getDmgSection() {
		return dmgSection;
	}
	/**
	 * @param dmgSection The dmgSection to set.
	 */
	public void setDmgSection(String dmgSection) {
		this.dmgSection = dmgSection;
	}
	/**
	 * @return Returns the dmgSts.
	 */
	public String getDmgSts() {
		return dmgSts;
	}
	/**
	 * @param dmgSts The dmgSts to set.
	 */
	public void setDmgSts(String dmgSts) {
		this.dmgSts = dmgSts;
	}
	/**
	 * @return Returns the invceAmt.
	 */
	public String getInvceAmt() {
		return invceAmt;
	}
	/**
	 * @param invceAmt The invceAmt to set.
	 */
	public void setInvceAmt(String invceAmt) {
		this.invceAmt = invceAmt;
	}
	/**
	 * @return Returns the invceSts.
	 */
	public String getInvceSts() {
		return invceSts;
	}
	/**
	 * @param invceSts The invceSts to set.
	 */
	public void setInvceSts(String invceSts) {
		this.invceSts = invceSts;
	}
	/**
	 * @return Returns the rprAmt.
	 */
	public String getRprAmt() {
		return rprAmt;
	}
	/**
	 * @param rprAmt The rprAmt to set.
	 */
	public void setRprAmt(String rprAmt) {
		this.rprAmt = rprAmt;
	}
	/**
	 * @return Returns the rprCode.
	 */
	public String getRprCode() {
		return rprCode;
	}
	/**
	 * @param rprCode The rprCode to set.
	 */
	public void setRprCode(String rprCode) {
		this.rprCode = rprCode;
	}
	/**
	 * @return Returns the rprDate.
	 */
	public String getRprDate() {
		return rprDate;
	}
	/**
	 * @param rprDate The rprDate to set.
	 */
	public void setRprDate(String rprDate) {
		this.rprDate = rprDate;
	}
	/**
	 * @return Returns the rprSts.
	 */
	public String getRprSts() {
		return rprSts;
	}
	/**
	 * @param rprSts The rprSts to set.
	 */
	public void setRprSts(String rprSts) {
		this.rprSts = rprSts;
	}
	/**
	 * @return Returns the damage.
	 */
	public String getDamage() {
		return damage;
	}
	/**
	 * @param damage The damage to set.
	 */
	public void setDamage(String damage) {
		this.damage = damage;
	}
	/**
	 * @return Returns the damageCode.
	 */
	public String getDamageCode() {
		return damageCode;
	}
	/**
	 * @param damageCode The damageCode to set.
	 */
	public void setDamageCode(String damageCode) {
		this.damageCode = damageCode;
	}
	
	
	/**
	 * @return Returns the damgeremarks.
	 */
	public String getDamgeremarks() {
		return damgeremarks;
	}
	/**
	 * @param damgeremarks The damgeremarks to set.
	 */
	public void setDamgeremarks(String damgeremarks) {
		this.damgeremarks = damgeremarks;
	}
	
	public String getPosition() {
		return position;
	}
	/**
	 * @param position The position to set.
	 */
	public void setPosition(String position) {
		this.position = position;
	}
	/**
	 * @return Returns the carrier.
	 */
	public String getCarrier() {
		return carrier;
	}
	/**
	 * @param carrier The carrier to set.
	 */
	public void setCarrier(String carrier) {
		this.carrier = carrier;
	}
	/**
	 * @return Returns the date.
	 */
	public String getDate() {
		return date;
	}
	/**
	 * @param date The date to set.
	 */
	public void setDate(String date) {
		this.date = date;
	}
	/**
	 * @return Returns the no.
	 */
	public String getNo() {
		return no;
	}
	/**
	 * @param no The no to set.
	 */
	public void setNo(String no) {
		this.no = no;
	}
	/**
	 * @return Returns the regNo.
	 */
	public String getRegNo() {
		return regNo;
	}
	/**
	 * @param regNo The regNo to set.
	 */
	public void setRegNo(String regNo) {
		this.regNo = regNo;
	}
	/**
	 * @return Returns the status.
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * @param status The status to set.
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * @return Returns the frmDate.
	 */
	public LocalDate getFrmDate() {
		return frmDate;
	}
	/**
	 * @param frmDate The frmDate to set.
	 */
	public void setFrmDate(LocalDate frmDate) {
		this.frmDate = frmDate;
	}
	/**
	 * @return Returns the tdate.
	 */
	public LocalDate getTdate() {
		return tdate;
	}
	/**
	 * @param tdate The tdate to set.
	 */
	public void setTdate(LocalDate tdate) {
		this.tdate = tdate;
	}
	/**
	 * @return Returns the noOfLoanTxns.
	 */
	public int getNoOfLoanTxns() {
		return noOfLoanTxns;
	}
	/**
	 * @param noOfLoanTxns The noOfLoanTxns to set.
	 */
	public void setNoOfLoanTxns(int noOfLoanTxns) {
		this.noOfLoanTxns = noOfLoanTxns;
	}
	/**
	 * @return Returns the noOfMovements.
	 */
	public int getNoOfMovements() {
		return noOfMovements;
	}
	/**
	 * @param noOfMovements The noOfMovements to set.
	 */
	public void setNoOfMovements(int noOfMovements) {
		this.noOfMovements = noOfMovements;
	}
	/**
	 * @return Returns the noOfTimesDmged.
	 */
	public int getNoOfTimesDmged() {
		return noOfTimesDmged;
	}
	/**
	 * @param noOfTimesDmged The noOfTimesDmged to set.
	 */
	public void setNoOfTimesDmged(int noOfTimesDmged) {
		this.noOfTimesDmged = noOfTimesDmged;
	}
	/**
	 * @return Returns the noOfTimesRepaired.
	 */
	public int getNoOfTimesRepaired() {
		return noOfTimesRepaired;
	}
	/**
	 * @param noOfTimesRepaired The noOfTimesRepaired to set.
	 */
	public void setNoOfTimesRepaired(int noOfTimesRepaired) {
		this.noOfTimesRepaired = noOfTimesRepaired;
	}
	/**
	 * @return Returns the transactionFlag.
	 */
	public String getTransactionFlag() {
		return transactionFlag;
	}
	/**
	 * @param transactionFlag The transactionFlag to set.
	 */
	public void setTransactionFlag(String transactionFlag) {
		this.transactionFlag = transactionFlag;
	}
	/**
	 * @return the pageUrl
	 */
	public String getPageUrl() {
		return pageUrl;
	}
	/**
	 * @param pageUrl the pageUrl to set
	 */
	public void setPageUrl(String pageUrl) {
		this.pageUrl = pageUrl;
	}
	public String getSystemCurrency() {
		return systemCurrency;
	}
	public void setSystemCurrency(String systemCurrency) {
		this.systemCurrency = systemCurrency;
	}
	
	
		
     
}
