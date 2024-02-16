/*
 * ReturnULDTransactionForm.java Created on Dec 19, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P)Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.transaction;

import com.ibsplc.icargo.framework.model.ScreenModel;

/**
 * @author A-1496
 *
 */
public class ReturnULDTransactionForm extends ScreenModel {
    
    
	/**
	 * BUNDLE
	 */
	private static final String BUNDLE = "loanBorrowDetailsEnquiryResources";
	
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
	private static final String SCREENID = "uld.defaults.loanborrowdetailsenquiry";

	private String disableSave;
	/**
	 * bundle
	 */
	private String bundle;    

	
	/**
	 * txnNatureDesc
	 */
	private String txnNatureDesc;
	
	/**
	 * returnDate
	 */
	private String returnDate;
	/**
	 * returnTime
	 */
	private String returnTime;
	
	/**
	 * returnStation
	 */
	private String returnStation;
	
	private String pageurl;
	
	/**
	 * returnBy
	 */
	private String[] returnBy;
	
	/**
	 * waived
	 */
	private String[] waived;
	
	/**
	 * taxes
	 */
	private String[] taxes;
	
	/**
	 * waived
	 */
	private String[] damaged;
	
	/**
	 * otherCharges
	 */
	private String[] otherCharges;
	
	/**
	 * returnRemarks
	 */
	private String[] returnRemarks;
	
	/**
	 * quantity
	 */
	private String[] quantity;
	
	/**
	 * damageULD
	 */
	private String damageULD;
		
	/**
	 * closeFlag
	 */
	private String closeFlag;
	
	private String damageReportFlag;
	/**
	 * 
	 * @return
	 */
	private String txnType;
	private String printUCR;
	
	//Added by Preet on 5 th Jan for newly added column CRN in Return Loan/Borrow screen --starts
	private String[] crnToDisplay;
	private String[] controlReceiptNumberPrefix;
	//Added by Preet on 5 th Jan for newly added column CRN in Return Loan/Borrow screen --starts
	
	public String getPrintUCR() {
		return printUCR;
	}
	public void setPrintUCR(String printUCR) {
		this.printUCR = printUCR;
	}
	public String getTxnType() {
		return txnType;
	}
	public void setTxnType(String txnType) {
		this.txnType = txnType;
	}
	public String getDamageReportFlag() {
		return damageReportFlag;
	}
	/**
	 * 
	 * @param damageReportFlag
	 */

	public void setDamageReportFlag(String damageReportFlag) {
		this.damageReportFlag = damageReportFlag;
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
	 * @return Returns the damaged.
	 */
	public String[] getDamaged() {
		return this.damaged;
	}

	/**
	 * @param damaged The damaged to set.
	 */
	public void setDamaged(String[] damaged) {
		this.damaged = damaged;
	}

	/**
	 * @return Returns the otherCharges.
	 */
	public String[] getOtherCharges() {
		return this.otherCharges;
	}

	/**
	 * @param otherCharges The otherCharges to set.
	 */
	public void setOtherCharges(String[] otherCharges) {
		this.otherCharges = otherCharges;
	}

	/**
	 * @return Returns the quantity.
	 */
	public String[] getQuantity() {
		return this.quantity;
	}

	/**
	 * @param quantity The quantity to set.
	 */
	public void setQuantity(String[] quantity) {
		this.quantity = quantity;
	}

	/**
	 * @return Returns the returnBy.
	 */
	public String[] getReturnBy() {
		return this.returnBy;
	}

	/**
	 * @param returnBy The returnBy to set.
	 */
	public void setReturnBy(String[] returnBy) {
		this.returnBy = returnBy;
	}

	/**
	 * @return Returns the returnDate.
	 */
	public String getReturnDate() {
		return this.returnDate;
	}

	/**
	 * @param returnDate The returnDate to set.
	 */
	public void setReturnDate(String returnDate) {
		this.returnDate = returnDate;
	}

	/**
	 * @return Returns the returnStation.
	 */
	public String getReturnStation() {
		return this.returnStation;
	}

	/**
	 * @param returnStation The returnStation to set.
	 */
	public void setReturnStation(String returnStation) {
		this.returnStation = returnStation;
	}

	/**
	 * @return Returns the taxes.
	 */
	public String[] getTaxes() {
		return this.taxes;
	}

	/**
	 * @param taxes The taxes to set.
	 */
	public void setTaxes(String[] taxes) {
		this.taxes = taxes;
	}

	/**
	 * @return Returns the txnNatureDesc.
	 */
	public String getTxnNatureDesc() {
		return this.txnNatureDesc;
	}

	/**
	 * @param txnNatureDesc The txnNatureDesc to set.
	 */
	public void setTxnNatureDesc(String txnNatureDesc) {
		this.txnNatureDesc = txnNatureDesc;
	}

	/**
	 * @return Returns the waived.
	 */
	public String[] getWaived() {
		return this.waived;
	}

	/**
	 * @param waived The waived to set.
	 */
	public void setWaived(String[] waived) {
		this.waived = waived;
	}

	/**
	 * @return Returns the returnRemarks.
	 */
	public String[] getReturnRemarks() {
		return this.returnRemarks;
	}

	/**
	 * @param returnRemarks The returnRemarks to set.
	 */
	public void setReturnRemarks(String[] returnRemarks) {
		this.returnRemarks = returnRemarks;
	}

	/**
	 * @return Returns the damageULD.
	 */
	public String getDamageULD() {
		return this.damageULD;
	}

	/**
	 * @param damageULD The damageULD to set.
	 */
	public void setDamageULD(String damageULD) {
		this.damageULD = damageULD;
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
	public String getReturnTime() {
		return returnTime;
	}
	public void setReturnTime(String returnTime) {
		this.returnTime = returnTime;
	}
	/**
	 * @return Returns the disableSave.
	 */
	public String getDisableSave() {
		return this.disableSave;
	}
	/**
	 * @param disableSave The disableSave to set.
	 */
	public void setDisableSave(String disableSave) {
		this.disableSave = disableSave;
	}
	public String[] getControlReceiptNumberPrefix() {
		return controlReceiptNumberPrefix;
	}
	public void setControlReceiptNumberPrefix(String[] controlReceiptNumberPrefix) {
		this.controlReceiptNumberPrefix = controlReceiptNumberPrefix;
	}
	public String[] getCrnToDisplay() {
		return crnToDisplay;
	}
	public void setCrnToDisplay(String[] crnToDisplay) {
		this.crnToDisplay = crnToDisplay;
	}

}
