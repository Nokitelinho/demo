/*
 * ListULDAgreementForm.java Created on Dec 19, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.misc;

import com.ibsplc.icargo.framework.model.ScreenModel;
import com.ibsplc.icargo.framework.client.daterange.notation.DateFieldId;

/**
 * @author A-1496
 *
 */
public class ListULDAgreementForm extends ScreenModel {


	private static final String BUNDLE = "listuldagreement";
	private static final String PRODUCT = "uld";
	private static final String SUBPRODUCT = "defaults";
	private static final String SCREENID = "uld.defaults.listuldagreement";


	private String agreementNumber;
	 // added as part of ICRD-232684 by A-4393 starts 
    private String fromPartyName;
	private String fromPartyType;
    private String fromPartyCode;
    //added as part of ICRD-232684 by A-4393 ends 
	private String partyType;
	private String partyCode;
	private String agreementListDate="";
	private String transactionType;
	private String agreementStatus;
	private String agreementFromDate;
	private String agreementToDate;
	private String[] agreementNo;
	private String partyTypes;
	private String partyCodes;
	private String agreementDates;
	private String transactionTypes;
	private String validFromDate;
	private String validToDate;
	private String[] statusvalues;
	private String lastPageNumber = "0";
	private String displayPageNum = "1";
	private String[] check;
	private String statusChanged;
	private String flag;
	private String bundle;
	private String closeStatus;
	private String listStatus;
	//Added By Manaf for BUG 16236 starts
	private String comboFlag;
	//Added By Manaf for BUG 16236 ends	
	
	// Added by A-5183 for < ICRD-22824 > Starts	
	public static final String PAGINATION_MODE_FROM_LIST = "LIST";
	public static final String PAGINATION_MODE_FROM_NAVIGATION_LINK = "NAVIGATION";
	private String navigationMode;	
	public String getNavigationMode() {
		return navigationMode;
	}
	public void setNavigationMode(String navigationMode) {
		this.navigationMode = navigationMode;
	}		
	// Added by A-5183 for < ICRD-22824> Ends
	
	/**
	 * @return the comboFlag
	 */
	public String getComboFlag() {
		return comboFlag;
	}
	/**
	 * @param comboFlag the comboFlag to set
	 */
	public void setComboFlag(String comboFlag) {
		this.comboFlag = comboFlag;
	}
	public String getListStatus() {
		return listStatus;
	}
	public void setListStatus(String listStatus) {
		this.listStatus = listStatus;
	}
	/**
	 *
	 * @return
	 */
	public String getCloseStatus() {
		return closeStatus;
	}
	/**
	 *
	 * @param closeStatus
	 */
	public void setCloseStatus(String closeStatus) {
		this.closeStatus = closeStatus;
	}
	/**
	 *
	 */
	public String getBundle() {
		return BUNDLE;
	}
	/**
	 *
	 * @param bundle
	 */
	public void setBundle(String bundle) {
		this.bundle = bundle;
	}
	/**
	 *
	 * @return
	 */
	public String getFlag() {
		return flag;
	}
	/**
	 *
	 * @param flag
	 */
	public void setFlag(String flag) {
		this.flag = flag;
	}
	/**
	 *
	 * @return
	 */
	public String getStatusChanged() {
		return statusChanged;
	}
	/**
	 *
	 * @param statusChanged
	 */
	public void setStatusChanged(String statusChanged) {
		this.statusChanged = statusChanged;
	}
	/**
	 *
	 * @return
	 */
    public String[] getCheck() {
		return check;
	}
    /**
     *
     * @param check
     */
	public void setCheck(String[] check) {
		this.check = check;
	}

	/**
     *
     * @return
     */
    public String getAgreementListDate() {
		return agreementListDate;
	}
    /**
     *
     * @param  agreementListDate
     */
	public void setAgreementListDate(String agreementListDate) {
		this.agreementListDate = agreementListDate;
	}
	/**
	 *
	 * @return
	 */
	public String getAgreementDates() {
		return agreementDates;
	}
	/**
	 *
	 * @param agreementDates
	 */
	public void setAgreementDates(String agreementDates) {
		this.agreementDates = agreementDates;
	}
	/**
	 *
	 * @return
	 */
	@DateFieldId(id="ListAgreementDateRange",fieldType="from") //Added by T-1927 for ICRD-9704
	public String getAgreementFromDate() {
		return agreementFromDate;
	}
	/**
	 *
	 * @param agreementFromDate
	 */
	public void setAgreementFromDate(String agreementFromDate) {
		this.agreementFromDate = agreementFromDate;
	}
	/**
	 *
	 * @return
	 */
	public String[] getAgreementNo() {
		return agreementNo;
	}
	/**
	 *
	 * @param agreementNo
	 */
	public void setAgreementNo(String[] agreementNo) {
		this.agreementNo = agreementNo;
	}
	/**
	 *
	 * @return
	 */
	public String getAgreementNumber() {
		return agreementNumber;
	}
	/**
	 *
	 * @param agreementNumber
	 */
	public void setAgreementNumber(String agreementNumber) {
		this.agreementNumber = agreementNumber;
	}
	/**
	 *
	 * @return
	 */
	public String getAgreementStatus() {
		return agreementStatus;
	}
	/**
	 *
	 * @param agreementStatus
	 */
	public void setAgreementStatus(String agreementStatus) {
		this.agreementStatus = agreementStatus;
	}
	/**
	 *
	 * @return
	 */
	@DateFieldId(id="ListAgreementDateRange",fieldType="to") //Added by T-1927 for ICRD-9704
	public String getAgreementToDate() {
		return agreementToDate;
	}
/**
 *
 * @param agreementToDate
 */
	public void setAgreementToDate(String agreementToDate) {
		this.agreementToDate = agreementToDate;
	}
/**
 *
 * @return
 */


	public String getPartyCode() {
		return partyCode;
	}
/**
 *
 * @param partyCode
 */
	public void setPartyCode(String partyCode) {
		this.partyCode = partyCode;
	}
/**
 *
 * @return
 */
	public String getPartyCodes() {
		return partyCodes;
	}
/**
 *
 * @param partyCodes
 */
	public void setPartyCodes(String partyCodes) {
		this.partyCodes = partyCodes;
	}
/**
 *
 * @return
 */
	public String getPartyType() {
		return partyType;
	}
/**
 *
 * @param partyType
 */
	public void setPartyType(String partyType) {
		this.partyType = partyType;
	}
/**
 *
 * @return
 */
	public String getPartyTypes() {
		return partyTypes;
	}
/**
 *
 * @param partyTypes
 */
	public void setPartyTypes(String partyTypes) {
		this.partyTypes = partyTypes;
	}
/**
 *
 * @return
 */
	public String[] getStatusvalues() {
		return statusvalues;
	}
/**
 *
 * @param statusvalues
 */
	public void setStatusvalues(String[] statusvalues) {
		this.statusvalues = statusvalues;
	}
/**
 *
 * @return
 */
	public String getTransactionType() {
		return transactionType;
	}
/**
 *
 * @param transactionType
 */
	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}
/**
 *
 * @return
 */
	public String getTransactionTypes() {
		return transactionTypes;
	}
/**
 *
 * @param transactionTypes
 */
	public void setTransactionTypes(String transactionTypes) {
		this.transactionTypes = transactionTypes;
	}
/**
 *
 * @return
 */
	public String getValidFromDate() {
		return validFromDate;
	}
/**
 *
 * @param validFromDate
 */
	public void setValidFromDate(String validFromDate) {
		this.validFromDate = validFromDate;
	}
/**
 *
 * @return
 */
	public String getValidToDate() {
		return validToDate;
	}
/**
 *
 * @param validToDate
 */
	public void setValidToDate(String validToDate) {
		this.validToDate = validToDate;
	}

	/**
	 * 
	 */
    public String getScreenId() {
        return SCREENID;
    }

    /**
     * 
     */
    public String getProduct() {
        return PRODUCT;
    }

   /**
    * 
    */
    public String getSubProduct() {
        return SUBPRODUCT;
    }
/**
 *
 * @return
 */
	public String getDisplayPageNum() {
		return displayPageNum;
	}
/**
 *
 * @param displayPageNum
 */
	public void setDisplayPageNum(String displayPageNum) {
		this.displayPageNum = displayPageNum;
	}
/**
 *
 * @return
 */
	public String getLastPageNumber() {
		return lastPageNumber;
	}
/**
 *
 * @param lastPageNumber
 */
	public void setLastPageNumber(String lastPageNumber) {
		this.lastPageNumber = lastPageNumber;
	}
	public String getFromPartyName() {
		return fromPartyName;
	}
	public void setFromPartyName(String fromPartyName) {
		this.fromPartyName = fromPartyName;
	}
	public String getFromPartyType() {
		return fromPartyType;
	}
	public void setFromPartyType(String fromPartyType) {
		this.fromPartyType = fromPartyType;
	}
	public String getFromPartyCode() {
		return fromPartyCode;
	}
	public void setFromPartyCode(String fromPartyCode) {
		this.fromPartyCode = fromPartyCode;
	}

}
