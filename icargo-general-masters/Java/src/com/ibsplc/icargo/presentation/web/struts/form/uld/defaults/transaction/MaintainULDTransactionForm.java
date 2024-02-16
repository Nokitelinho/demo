/*
 * MaintainULDTransactionForm.java Created on Dec 19, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P)Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.transaction;

import com.ibsplc.icargo.framework.model.ScreenModel;

/**
 * @author A-1876
 * 
 */
public class MaintainULDTransactionForm extends ScreenModel {

	/**
	 * BUNDLE for resources
	 */
	private static final String BUNDLE = "loanBorrowULDResources";

	/**
	 * PRODUCT
	 */
	private static final String PRODUCT = "uld";

	/**
	 * SUBPRODUCT
	 */
	private static final String SUBPRODUCT = "defaults";

	/**
	 * SCREENID
	 */
	private static final String SCREENID = "uld.defaults.loanborrowuld";

	/**
	 * bundle
	 */
	private String bundle;

	/**
	 * transactionType
	 */
	private String transactionType;
	
	private String disableStatus;	
	
	private String transactionTime;
	
	private String airlineCode;
	
	private String airlineName;

	/**
	 * transactionNature
	 */
	private String transactionNature;
	
	private String saveStatus;

	/**
	 * transactionStation
	 */
	private String transactionStation;
	
	/**
	 * mode
	 */
	private String modifyMode;
	
	/**
	 * mode
	 */
	private String modifyUldNum;
	/**
	 * mode
	 */
	private String modifyAcc;

	/**
	 * forDamage
	 */
	private String forDamage;

	/**
	 * transactionDate
	 */
	private String transactionDate;

	private String leaseEndDate;
	/**
	 * partyType
	 */
	private String partyType;

	private String fromPartyCode;
	private String toPartyCode;
	private String fromPartyName;
	private String toPartyName;
	
	private String[] uldReciept;
	private String[] recieptNum;
	private String lucPopup="";

	/**
	 * transactionRemarks
	 */
	private String transactionRemarks;

	/**
	 * uldDetails
	 */
	private String[] uldDetails;

	/**
	 * accessoryDetails
	 */
	private String[] accessoryDetails;

	/**
	 * damageCheck
	 */
	private String[] damageCheck;

	/**
	 * uldNum
	 */
	private String[] uldNum;

	/**
	 * txnRefNum
	 */
	private String originatorName;
	
	private String[] txnRefNum;

	//Added by A-4072 for CR ICRD-192300 starts
	private boolean[] isDamaged;
	
	private String[] damagedFlag;
	
	private String[] damageRemark;
	
	private String[] odlnCode;
	
	private String[] odlnDescription;
	//Added by A-4072 for CR ICRD-192300 end
	/**
	 * acessoryCode
	 */
	private String[] acessoryCode;

	/**
	 * accessoryQuantity
	 */
	private String[] accessoryQuantity;

	/**
	 * damageULD
	 */
	private String damageULD;

	/**
	 * agreementFlag
	 */
	private String agreementFlag;

	/***************************************************************************
	 * for add Loan details Pop-up
	 */

	/**
	 * loanUldNum
	 */
	private String loanUldNum;

	/**
	 * loanTxnNum
	 */
	private String loanTxnNum;

	// added for LUC message implementation
	
	private String loanTransactionStation;
	
	private String borrowTransactionStation;

	/**
	 * loanDamage
	 */
	private String loanDamage;

	/**
	 * loanPopupClose to close popup
	 */
	private String loanPopupClose;

	/***************************************************************************
	 * for add uld Borrow details Pop-up
	 */

	/**
	 * borrowUldNum
	 */
	private String borrowUldNum;

	/**
	 * borrowTxnNum
	 */
	private String borrowTxnNum;

	/**
	 * borrowDamage
	 */
	private String borrowDamage;

	/**
	 * borrowPopupClose to close popup
	 */
	private String borrowPopupClose;

	/***************************************************************************
	 * for add accessory details Pop-up
	 */

	/**
	 * accessCode
	 */
	private String accessCode;

	/**
	 * quantity
	 */
	private String quantity;

	/**
	 * accPopupClose to close popup
	 */
	private String accPopupClose;

	/**
	 * txnTypeDisable to disable txnType in screen
	 */
	private String txnTypeDisable;

	/**
	 * closeStatus
	 */
	private String closeStatus;
	
	private String lucEnable;
	
	/**
	 * mode
	 */
	private String ispopup;
	
	private String comboFlag;
	
	private String pageurl;
	
	private String borrowPopupFlag;
	
	private String borrowULDNature;
	
	private String loanPopupFlag;
	
	private String loanULDNature;
	
	private String addUldDisable;
	
	//Added by A-2412
	private String uldConditionCode;
	
	private String[] hiddenUldConditionCode;
	
//	added by a-3045 for CR QF1016 starts
	/**
	 * crnPrefix
	 */
	private String[] crnPrefix;
	/**
	 * crn
	 */
	private String[] crn;
	/**
	 * uldNature
	 */
	private String[] uldNature;
	/**
	 * destnAirport
	 */
	private String[] destnAirport;
	
	private String[] uldCondition;
	
	private String showDamage;
	
	private String uldNumbersSelected;
	
	private String agreementFound;
	
	private String[] accOperationFlag; 
	//added by a-3045 for CR QF1016 ends
	
	//added by a-3045 for CR QF starts 
	private boolean dummyLUC;
	private String dummyTxnAirport;
	//added by a-3045 for CR QF ends
	
	private String awbNumber;
	
	private String loaded;
	
	//A-6841
	private String fromPopup;
	
	private String rowIndex;
	private String uldInValidCheck;
	private String isValiduld[];
	private String isInvalidUldsPresent;
	private String screenInfo;
	private String warningMsgStatus;
	
	public String getFromPopup() {
		return fromPopup;
	}
	public void setFromPopup(String fromPopup) {
		this.fromPopup = fromPopup;
	}
	public String getWarningMsgStatus() {
		return warningMsgStatus;
	}
	public void setWarningMsgStatus(String warningMsgStatus) {
		this.warningMsgStatus = warningMsgStatus;
	}
	public String getScreenInfo() {
		return screenInfo;
	}
	public void setScreenInfo(String screenInfo) {
		this.screenInfo = screenInfo;
	}
	public String getIsInvalidUldsPresent() {
		return isInvalidUldsPresent;
	}
	public void setIsInvalidUldsPresent(String isInvalidUldsPresent) {
		this.isInvalidUldsPresent = isInvalidUldsPresent;
	}
	public String[] getIsValiduld() {
		return isValiduld;
	}
	public void setIsValiduld(String[] isValiduld) {
		this.isValiduld = isValiduld;
	}
	public String getUldInValidCheck() {
		return uldInValidCheck;
	}
	public void setUldInValidCheck(String uldInValidCheck) {
		this.uldInValidCheck = uldInValidCheck;
	}
	/**
	 * @return the rowIndex
	 */
	public String getRowIndex() {
		return rowIndex;
	}
	/**
	 * @param rowIndex the rowIndex to set
	 */
	public void setRowIndex(String rowIndex) {
		this.rowIndex = rowIndex;
	}
	/**
	 * @return the accOperationFlag
	 */
	public String[] getAccOperationFlag() {
		return accOperationFlag;
	}
	/**
	 * @param accOperationFlag the accOperationFlag to set
	 */
	public void setAccOperationFlag(String[] accOperationFlag) {
		this.accOperationFlag = accOperationFlag;
	}
	/**
	 * @return the agreementFound
	 */
	public String getAgreementFound() {
		return agreementFound;
	}
	/**
	 * @param agreementFound the agreementFound to set
	 */
	public void setAgreementFound(String agreementFound) {
		this.agreementFound = agreementFound;
	}
	/**
	 * @return the uldNumbersSelected
	 */
	public String getUldNumbersSelected() {
		return uldNumbersSelected;
	}
	/**
	 * @param uldNumbersSelected the uldNumbersSelected to set
	 */
	public void setUldNumbersSelected(String uldNumbersSelected) {
		this.uldNumbersSelected = uldNumbersSelected;
	}
	/**
	 * @return the showDamage
	 */
	public String getShowDamage() {
		return showDamage;
	}
	/**
	 * @param showDamage the showDamage to set
	 */
	public void setShowDamage(String showDamage) {
		this.showDamage = showDamage;
	}
	public String[] getHiddenUldConditionCode() {
		return hiddenUldConditionCode;
	}
	public void setHiddenUldConditionCode(String[] hiddenUldConditionCode) {
		this.hiddenUldConditionCode = hiddenUldConditionCode;
	}
	public String getUldConditionCode() {
		return uldConditionCode;
	}
	public void setUldConditionCode(String uldConditionCode) {
		this.uldConditionCode = uldConditionCode;
	}
	//ends
	
	// Added by A-2412 on 18th OCT for CRN Editable CR
	private String loanTxnNumPrefix;
	private String borrowTxnNumPrefix;	
	private String printUCR;
	
	public String getPrintUCR() {
		return printUCR;
	}
	public void setPrintUCR(String printUCR) {
		this.printUCR = printUCR;
	}
	public String getLoanTxnNumPrefix() {
		return loanTxnNumPrefix;
	}
	public void setLoanTxnNumPrefix(String loanTxnNumPrefix) {
		this.loanTxnNumPrefix = loanTxnNumPrefix;
	}			
	//Addition by A-2412 ends
	
	//Added by A-2408 forCR-15 starts
	private String msgFlag;
	
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
//	Added by A-2408 forCR-15 ends
//	Added by A-2408 0n 28JAN starts
	private String errorStatusFlag;
	private String partyCode;
	private String partyName;
	
	// QF1506
	private String stationCode;
	private String toShortCode;
	private String fromShortCode;
	private String selectecdUlds[];
	public String[] getSelectecdUlds() {
		return selectecdUlds;
	}
	public void setSelectecdUlds(String[] selectecdUlds) {
		this.selectecdUlds = selectecdUlds;
	}
	/**
	 * @return the toShortCode
	 */
	public String getToShortCode() {
		return toShortCode;
	}
	/**
	 * @param toShortCode the toShortCode to set
	 */
	public void setToShortCode(String toShortCode) {
		this.toShortCode = toShortCode;
	}
	/**
	 * @return the fromShortCode
	 */
	public String getFromShortCode() {
		return fromShortCode;
	}
	/**
	 * @param fromShortCode the fromShortCode to set
	 */
	public void setFromShortCode(String fromShortCode) {
		this.fromShortCode = fromShortCode;
	}
	/**
	 * @return the stationCode
	 */
	public String getStationCode() {
		return stationCode;
	}
	/**
	 * @param stationCode the stationCode to set
	 */
	public void setStationCode(String stationCode) {
		this.stationCode = stationCode;
	}
	/**
	 * @return Returns the errorStatusFlag.
	 */
	public String getErrorStatusFlag() {
		return errorStatusFlag;
	}
	/**
	 * @param errorStatusFlag The errorStatusFlag to set.
	 */
	public void setErrorStatusFlag(String errorStatusFlag) {
		this.errorStatusFlag = errorStatusFlag;
	}
	/**
	 * @return Returns the partyCode.
	 */
	public String getPartyCode() {
		return partyCode;
	}
	/**
	 * @param partyCode The partyCode to set.
	 */
	public void setPartyCode(String partyCode) {
		this.partyCode = partyCode;
	}
	/**
	 * @return Returns the partyName.
	 */
	public String getPartyName() {
		return partyName;
	}
	/**
	 * @param partyName The partyName to set.
	 */
	public void setPartyName(String partyName) {
		this.partyName = partyName;
	}
//	Added by A-2408 0n 28JAN ends
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
	 * @return Returns the ispopup.
	 */
	public String getIspopup() {
		return ispopup;
	}
	/**
	 * @param ispopup The ispopup to set.
	 */
	public void setIspopup(String ispopup) {
		this.ispopup = ispopup;
	}
	/**
	 * 
	 * @return
	 */
	public String getLucEnable() {
		return lucEnable;
	}
	/**
	 * 
	 * @param lucEnable
	 */
	public void setLucEnable(String lucEnable) {
		this.lucEnable = lucEnable;
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
	 * @return Returns the accessoryDetails.
	 */
	public String[] getAccessoryDetails() {
		return this.accessoryDetails;
	}

	/**
	 * @param accessoryDetails
	 *            The accessoryDetails to set.
	 */
	public void setAccessoryDetails(String[] accessoryDetails) {
		this.accessoryDetails = accessoryDetails;
	}

	/**
	 * @return Returns the damageCheck.
	 */
	public String[] getDamageCheck() {
		return this.damageCheck;
	}

	/**
	 * @param damageCheck
	 *            The damageCheck to set.
	 */
	public void setDamageCheck(String[] damageCheck) {
		this.damageCheck = damageCheck;
	}

	/**
	 * @return Returns the partyType.
	 */
	public String getPartyType() {
		return this.partyType;
	}

	/**
	 * @param partyType
	 *            The partyType to set.
	 */
	public void setPartyType(String partyType) {
		this.partyType = partyType;
	}

	/**
	 * @return Returns the transactionDate.
	 */
	public String getTransactionDate() {
		return this.transactionDate;
	}

	/**
	 * @param transactionDate
	 *            The transactionDate to set.
	 */
	public void setTransactionDate(String transactionDate) {
		this.transactionDate = transactionDate;
	}

	/**
	 * @return Returns the transactionNature.
	 */
	public String getTransactionNature() {
		return this.transactionNature;
	}

	/**
	 * @param transactionNature
	 *            The transactionNature to set.
	 */
	public void setTransactionNature(String transactionNature) {
		this.transactionNature = transactionNature;
	}

	/**
	 * @return Returns the transactionRemarks.
	 */
	public String getTransactionRemarks() {
		return this.transactionRemarks;
	}

	/**
	 * @param transactionRemarks
	 *            The transactionRemarks to set.
	 */
	public void setTransactionRemarks(String transactionRemarks) {
		this.transactionRemarks = transactionRemarks;
	}

	/**
	 * @return Returns the transactionStation.
	 */
	public String getTransactionStation() {
		return this.transactionStation;
	}

	/**
	 * @param transactionStation
	 *            The transactionStation to set.
	 */
	public void setTransactionStation(String transactionStation) {
		this.transactionStation = transactionStation;
	}

	/**
	 * @return Returns the transactionType.
	 */
	public String getTransactionType() {
		return this.transactionType;
	}

	/**
	 * @param transactionType
	 *            The transactionType to set.
	 */
	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	/**
	 * @return Returns the uldDetails.
	 */
	public String[] getUldDetails() {
		return this.uldDetails;
	}

	/**
	 * @param uldDetails
	 *            The uldDetails to set.
	 */
	public void setUldDetails(String[] uldDetails) {
		this.uldDetails = uldDetails;
	}

	/**
	 * @return Returns the accessoryQuantity.
	 */
	public String[] getAccessoryQuantity() {
		return this.accessoryQuantity;
	}

	/**
	 * @param accessoryQuantity
	 *            The accessoryQuantity to set.
	 */
	public void setAccessoryQuantity(String[] accessoryQuantity) {
		this.accessoryQuantity = accessoryQuantity;
	}

	/**
	 * @return Returns the acessoryCode.
	 */
	public String[] getAcessoryCode() {
		return this.acessoryCode;
	}

	/**
	 * @param acessoryCode
	 *            The acessoryCode to set.
	 */
	public void setAcessoryCode(String[] acessoryCode) {
		this.acessoryCode = acessoryCode;
	}

	/**
	 * @return Returns the txnRefNum.
	 */
	public String[] getTxnRefNum() {
		return this.txnRefNum;
	}

	/**
	 * @param txnRefNum
	 *            The txnRefNum to set.
	 */
	public void setTxnRefNum(String[] txnRefNum) {
		this.txnRefNum = txnRefNum;
	}

	/**
	 * @return Returns the uldNum.
	 */
	public String[] getUldNum() {
		return this.uldNum;
	}

	/**
	 * @param uldNum
	 *            The uldNum to set.
	 */
	public void setUldNum(String[] uldNum) {
		this.uldNum = uldNum;
	}

	/**
	 * @return Returns the accessCode.
	 */
	public String getAccessCode() {
		return this.accessCode;
	}

	/**
	 * @param accessCode
	 *            The accessCode to set.
	 */
	public void setAccessCode(String accessCode) {
		this.accessCode = accessCode;
	}

	/**
	 * @return Returns the borrowDamage.
	 */
	public String getBorrowDamage() {
		return this.borrowDamage;
	}

	/**
	 * @param borrowDamage
	 *            The borrowDamage to set.
	 */
	public void setBorrowDamage(String borrowDamage) {
		this.borrowDamage = borrowDamage;
	}

	/**
	 * @return Returns the borrowTxnNum.
	 */
	public String getBorrowTxnNum() {
		return this.borrowTxnNum;
	}

	/**
	 * @param borrowTxnNum
	 *            The borrowTxnNum to set.
	 */
	public void setBorrowTxnNum(String borrowTxnNum) {
		this.borrowTxnNum = borrowTxnNum;
	}

	/**
	 * @return Returns the borrowUldNum.
	 */
	public String getBorrowUldNum() {
		return this.borrowUldNum;
	}

	/**
	 * @param borrowUldNum
	 *            The borrowUldNum to set.
	 */
	public void setBorrowUldNum(String borrowUldNum) {
		this.borrowUldNum = borrowUldNum;
	}

	/**
	 * @return Returns the loanDamage.
	 */
	public String getLoanDamage() {
		return this.loanDamage;
	}

	/**
	 * @param loanDamage
	 *            The loanDamage to set.
	 */
	public void setLoanDamage(String loanDamage) {
		this.loanDamage = loanDamage;
	}

	/**
	 * @return Returns the loanTxnNum.
	 */
	public String getLoanTxnNum() {
		return this.loanTxnNum;
	}

	/**
	 * @param loanTxnNum
	 *            The loanTxnNum to set.
	 */
	public void setLoanTxnNum(String loanTxnNum) {
		this.loanTxnNum = loanTxnNum;
	}

	/**
	 * @return Returns the loanUldNum.
	 */
	public String getLoanUldNum() {
		return this.loanUldNum;
	}

	/**
	 * @param loanUldNum
	 *            The loanUldNum to set.
	 */
	public void setLoanUldNum(String loanUldNum) {
		this.loanUldNum = loanUldNum;
	}

	/**
	 * @return Returns the quantity.
	 */
	public String getQuantity() {
		return this.quantity;
	}

	/**
	 * @param quantity
	 *            The quantity to set.
	 */
	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

	/**
	 * @return Returns the accPopupClose.
	 */
	public String getAccPopupClose() {
		return this.accPopupClose;
	}

	/**
	 * @param accPopupClose
	 *            The accPopupClose to set.
	 */
	public void setAccPopupClose(String accPopupClose) {
		this.accPopupClose = accPopupClose;
	}

	/**
	 * @return Returns the borrowPopupClose.
	 */
	public String getBorrowPopupClose() {
		return this.borrowPopupClose;
	}

	/**
	 * @param borrowPopupClose
	 *            The borrowPopupClose to set.
	 */
	public void setBorrowPopupClose(String borrowPopupClose) {
		this.borrowPopupClose = borrowPopupClose;
	}

	/**
	 * @return Returns the loanPopupClose.
	 */
	public String getLoanPopupClose() {
		return this.loanPopupClose;
	}

	/**
	 * @param loanPopupClose
	 *            The loanPopupClose to set.
	 */
	public void setLoanPopupClose(String loanPopupClose) {
		this.loanPopupClose = loanPopupClose;
	}

	/**
	 * @return Returns the txnTypeDisable.
	 */
	public String getTxnTypeDisable() {
		return this.txnTypeDisable;
	}

	/**
	 * @param txnTypeDisable
	 *            The txnTypeDisable to set.
	 */
	public void setTxnTypeDisable(String txnTypeDisable) {
		this.txnTypeDisable = txnTypeDisable;
	}

	/**
	 * @return Returns the damageULD.
	 */
	public String getDamageULD() {
		return this.damageULD;
	}

	/**
	 * @param damageULD
	 *            The damageULD to set.
	 */
	public void setDamageULD(String damageULD) {
		this.damageULD = damageULD;
	}

	/**
	 * @return Returns the agreementFlag.
	 */
	public String getAgreementFlag() {
		return this.agreementFlag;
	}

	/**
	 * @param agreementFlag
	 *            The agreementFlag to set.
	 */
	public void setAgreementFlag(String agreementFlag) {
		this.agreementFlag = agreementFlag;
	}

	/**
	 * @return Returns the closeStatus.
	 */
	public String getCloseStatus() {
		return closeStatus;
	}

	/**
	 * @param closeStatus
	 *            The closeStatus to set.
	 */
	public void setCloseStatus(String closeStatus) {
		this.closeStatus = closeStatus;
	}

	/**
	 * @return Returns the forDamage.
	 */
	public String getForDamage() {
		return this.forDamage;
	}

	/**
	 * @param forDamage
	 *            The forDamage to set.
	 */
	public void setForDamage(String forDamage) {
		this.forDamage = forDamage;
	}
/**
 * 
 * @return
 */
	public String getBorrowTransactionStation() {
		return borrowTransactionStation;
	}
/**
 * 
 * @param borrowTransactionStation
 */
	public void setBorrowTransactionStation(String borrowTransactionStation) {
		this.borrowTransactionStation = borrowTransactionStation;
	}
/**
 * 
 * @return
 */
	public String getLoanTransactionStation() {
		return loanTransactionStation;
	}
/**
 * 
 * @param loanTransactionStation
 */
	public void setLoanTransactionStation(String loanTransactionStation) {
		this.loanTransactionStation = loanTransactionStation;
	}
	/**
	 * @return Returns the modifyMode.
	 */
	public String getModifyMode() {
		return modifyMode;
	}
	/**
	 * @param modifyMode The modifyMode to set.
	 */
	public void setModifyMode(String modifyMode) {
		this.modifyMode = modifyMode;
	}
	/**
	 * @return Returns the modifyUldNum.
	 */
	public String getModifyUldNum() {
		return modifyUldNum;
	}
	/**
	 * @param modifyUldNum The modifyUldNum to set.
	 */
	public void setModifyUldNum(String modifyUldNum) {
		this.modifyUldNum = modifyUldNum;
	}
	/**
	 * @return Returns the modifyAcc.
	 */
	public String getModifyAcc() {
		return modifyAcc;
	}
	/**
	 * @param modifyAcc The modifyAcc to set.
	 */
	public void setModifyAcc(String modifyAcc) {
		this.modifyAcc = modifyAcc;
	}
	/**
	 * @return Returns the pageurl.
	 */
	public String getPageurl() {
		return pageurl;
	}
	/**
	 * @param pageurl The pageurl to set.
	 */
	public void setPageurl(String pageurl) {
		this.pageurl = pageurl;
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
	 * @return Returns the fromPartyName.
	 */
	public String getFromPartyName() {
		return fromPartyName;
	}
	/**
	 * @param fromPartyName The fromPartyName to set.
	 */
	public void setFromPartyName(String fromPartyName) {
		this.fromPartyName = fromPartyName;
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
	 * @return Returns the toPartyName.
	 */
	public String getToPartyName() {
		return toPartyName;
	}
	/**
	 * @param toPartyName The toPartyName to set.
	 */
	public void setToPartyName(String toPartyName) {
		this.toPartyName = toPartyName;
	}
	/**
	 * @return Returns the disableStatus.
	 */
	public String getDisableStatus() {
		return disableStatus;
	}
	/**
	 * @param disableStatus The disableStatus to set.
	 */
	public void setDisableStatus(String disableStatus) {
		this.disableStatus = disableStatus;
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
	 * @return Returns the recieptNum.
	 */
	public String[] getRecieptNum() {
		return recieptNum;
	}
	/**
	 * @param recieptNum The recieptNum to set.
	 */
	public void setRecieptNum(String[] recieptNum) {
		this.recieptNum = recieptNum;
	}
	/**
	 * @return Returns the uldReciept.
	 */
	public String[] getUldReciept() {
		return uldReciept;
	}
	/**
	 * @param uldReciept The uldReciept to set.
	 */
	public void setUldReciept(String[] uldReciept) {
		this.uldReciept = uldReciept;
	}
	/**
	 * @return Returns the lucPopup.
	 */
	public String getLucPopup() {
		return lucPopup;
	}
	/**
	 * @param lucPopup The lucPopup to set.
	 */
	public void setLucPopup(String lucPopup) {
		this.lucPopup = lucPopup;
	}
	/**
	 * @return Returns the transactionTime.
	 */
	public String getTransactionTime() {
		return transactionTime;
	}
	/**
	 * @param transactionTime The transactionTime to set.
	 */
	public void setTransactionTime(String transactionTime) {
		this.transactionTime = transactionTime;
	}
	/**
	 * @return Returns the borrowPopupFlag.
	 */
	public String getBorrowPopupFlag() {
		return borrowPopupFlag;
	}
	/**
	 * @param borrowPopupFlag The borrowPopupFlag to set.
	 */
	public void setBorrowPopupFlag(String borrowPopupFlag) {
		this.borrowPopupFlag = borrowPopupFlag;
	}
	/**
	 * @return Returns the borrowULDNature.
	 */
	public String getBorrowULDNature() {
		return borrowULDNature;
	}
	/**
	 * @param borrowULDNature The borrowULDNature to set.
	 */
	public void setBorrowULDNature(String borrowULDNature) {
		this.borrowULDNature = borrowULDNature;
	}
	/**
	 * @return Returns the loanPopupFlag.
	 */
	public String getLoanPopupFlag() {
		return loanPopupFlag;
	}
	/**
	 * @param loanPopupFlag The loanPopupFlag to set.
	 */
	public void setLoanPopupFlag(String loanPopupFlag) {
		this.loanPopupFlag = loanPopupFlag;
	}
	/**
	 * @return Returns the loanULDNature.
	 */
	public String getLoanULDNature() {
		return loanULDNature;
	}
	/**
	 * @param loanULDNature The loanULDNature to set.
	 */
	public void setLoanULDNature(String loanULDNature) {
		this.loanULDNature = loanULDNature;
	}
	public String getAirlineName() {
		return airlineName;
	}
	public void setAirlineName(String airlineName) {
		this.airlineName = airlineName;
	}
	public String getAirlineCode() {
		return airlineCode;
	}
	public void setAirlineCode(String airlineCode) {
		this.airlineCode = airlineCode;
	}
	public String getAddUldDisable() {
		return addUldDisable;
	}
	public void setAddUldDisable(String addUldDisable) {
		this.addUldDisable = addUldDisable;
	}
	
	public String getBorrowTxnNumPrefix() {
		return borrowTxnNumPrefix;
	}
	public void setBorrowTxnNumPrefix(String borrowTxnNumPrefix) {
		this.borrowTxnNumPrefix = borrowTxnNumPrefix;
	}
	/**
	 * @return the crn
	 */
	public String[] getCrn() {
		return crn;
	}
	/**
	 * @param crn the crn to set
	 */
	public void setCrn(String[] crn) {
		this.crn = crn;
	}
	/**
	 * @return the crnPrefix
	 */
	public String[] getCrnPrefix() {
		return crnPrefix;
	}
	/**
	 * @param crnPrefix the crnPrefix to set
	 */
	public void setCrnPrefix(String[] crnPrefix) {
		this.crnPrefix = crnPrefix;
	}
	/**
	 * @return the destnAirport
	 */
	public String[] getDestnAirport() {
		return destnAirport;
	}
	/**
	 * @param destnAirport the destnAirport to set
	 */
	public void setDestnAirport(String[] destnAirport) {
		this.destnAirport = destnAirport;
	}
	/**
	 * @return the uldNature
	 */
	public String[] getUldNature() {
		return uldNature;
	}
	/**
	 * @param uldNature the uldNature to set
	 */
	public void setUldNature(String[] uldNature) {
		this.uldNature = uldNature;
	}
	/**
	 * @return the uldCondition
	 */
	public String[] getUldCondition() {
		return uldCondition;
	}
	/**
	 * @param uldCondition the uldCondition to set
	 */
	public void setUldCondition(String[] uldCondition) {
		this.uldCondition = uldCondition;
	}
	/**
	 * @return the dummyLUC
	 */
	public boolean isDummyLUC() {
		return dummyLUC;
	}
	/**
	 * @param dummyLUC the dummyLUC to set
	 */
	public void setDummyLUC(boolean dummyLUC) {
		this.dummyLUC = dummyLUC;
	}
	/**
	 * @return the dummyTxnAirport
	 */
	public String getDummyTxnAirport() {
		return dummyTxnAirport;
	}
	/**
	 * @param dummyTxnAirport the dummyTxnAirport to set
	 */
	public void setDummyTxnAirport(String dummyTxnAirport) {
		this.dummyTxnAirport = dummyTxnAirport;
	}
	/**
	 * @return the awbNumber
	 */
	public String getAwbNumber() {
		return awbNumber;
	}
	/**
	 * @param awbNumber the awbNumber to set
	 */
	public void setAwbNumber(String awbNumber) {
		this.awbNumber = awbNumber;
	}
	
	/**
	 * @return the loaded
	 */
	public String getLoaded() {
		return loaded;
	}
	/**
	 * @param loaded the loaded to set
	 */
	public void setLoaded(String loaded) {
		this.loaded = loaded;
	}
	
	/**
	 * @return the damageRemark
	 */
	public String[] getDamageRemark() {
		return damageRemark;
	}
	/**
	 * @param damageRemark the damageRemark to set
	 */
	public void setDamageRemark(String[] damageRemark) {
		this.damageRemark = damageRemark;
	}
	/**
	 * @return the odlnCode
	 */
	public String[] getOdlnCode() {
		return odlnCode;
	}
	/**
	 * @param odlnCode the odlnCode to set
	 */
	public void setOdlnCode(String[] odlnCode) {
		
		this.odlnCode = odlnCode;
	}
	/**
	 * @return the originatorName
	 */
	public String getOriginatorName() {
		return originatorName;
	}
	/**
	 * @param originatorName the originatorName to set
	 */
	public void setOriginatorName(String originatorName) {
		this.originatorName = originatorName;
	}
	/**
	 * @return the isDamaged
	 */
	public boolean[] getIsDamaged() {
		return isDamaged;
	}
	/**
	 * @param isDamaged the isDamaged to set
	 */
	public void setIsDamaged(boolean[] isDamaged) {
		this.isDamaged = isDamaged;
	}
	/**
	 * @return the damagedFlag
	 */
	public String[] getDamagedFlag() {
		return damagedFlag;
	}
	/**
	 * @param damagedFlag the damagedFlag to set
	 */
	public void setDamagedFlag(String[] damagedFlag) {
		this.damagedFlag = damagedFlag;
	}
	/**
	 * @return the odlnDescription
	 */
	public String[] getOdlnDescription() {
		return odlnDescription;
	}
	/**
	 * @param odlnDescription the odlnDescription to set
	 */
	public void setOdlnDescription(String[] odlnDescription) {
		this.odlnDescription = odlnDescription;
	}
	public String getLeaseEndDate() {
		return leaseEndDate;
	}
	public void setLeaseEndDate(String leaseEndDate) {
		this.leaseEndDate = leaseEndDate;
	}	
	

}
