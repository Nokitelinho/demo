/*
 * MailProrationForm.java Created on Mar 06,2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults;




import com.ibsplc.icargo.framework.model.ScreenModel;


/**
 * Form for Mail Proration screen.
 *
 * Revision History
 *
 * Version        Date           		  Author          		    Description
 *
 *  0.1        Mar 06, 2007    			Ruby Abraham   	    	   Initial draft
 */

/**
 * @author a-2408
 *
 */
public class MailProrationForm extends ScreenModel {

    private static final String BUNDLE = "mailprorationresources";

	//private String bundle;

	private static final String PRODUCT = "mail";
	private static final String SUBPRODUCT = "mra";
	private static final String SCREENID = "mailtracking.mra.defaults.mailproration";



	private String despatchSerialNo;

	private String consigneeDocNo;
	
	private String flightCarrierCode;
	
	private int flightCarrierId;
	
	private String flightNumber;


	private String flightDate;
	
	
	private String orgExgOffice;
	
	private String destExgOffice;
	
	private String postalAuthorityCode;
	
	private String postalAuthorityName;
	
	private String mailCategorycode;
	
	private String mailSubclass;
	
	private boolean lovFlag;
	
	private boolean moreValuesFlag;
	
	private String billingBasis;
	


	private String[] select;
// added for displaying dsn
private String dsn;
	/**
	 * @return Returns the SCREENID.
	 */
    public String getScreenId() {
        return SCREENID;
    }

    /**
	 * @return Returns the PRODUCT.
	 */
    public String getProduct() {
        return PRODUCT;
    }
    /**
	 * @return Returns the SUBPRODUCT.
	 */
    public String getSubProduct() {
        return SUBPRODUCT;
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
	//	this.bundle = bundle;
	}



	/**
	 * @return Returns the flightCarrierId.
	 */
	public int getFlightCarrierId() {
		return flightCarrierId;
	}

	/**
	 * @param flightCarrierId The flightCarrierId to set.
	 */
	public void setFlightCarrierId(int flightCarrierId) {
		this.flightCarrierId = flightCarrierId;
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
	 * @return Returns the despatchSerialNo.
	 */
	public String getDespatchSerialNo() {
		return despatchSerialNo;
	}

	/**
	 * @param despatchSerialNo The despatchSerialNo to set.
	 */
	public void setDespatchSerialNo(String despatchSerialNo) {
		this.despatchSerialNo = despatchSerialNo;
	}


	/**
	 * @return Returns the consigneeDocNo.
	 */
	public String getConsigneeDocNo() {
		return consigneeDocNo;
	}

	/**
	 * @param consigneeDocNo The consigneeDocNo to set.
	 */
	public void setConsigneeDocNo(String consigneeDocNo) {
		this.consigneeDocNo = consigneeDocNo;
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
	 * @return Returns the flightDate.
	 */
	public String getFlightDate() {
		return flightDate;
	}

	/**
	 * @param flightDate The flightDate to set.
	 */
	public void setFlightDate(String flightDate) {
		this.flightDate = flightDate;
	}

		
	/**
	 * @return Returns the destExgOffice.
	 */
	public String getDestExgOffice() {
		return destExgOffice;
	}

	/**
	 * @param destExgOffice The destExgOffice to set.
	 */
	public void setDestExgOffice(String destExgOffice) {
		this.destExgOffice = destExgOffice;
	}

	/**
	 * @return Returns the mailCategorycode.
	 */
	public String getMailCategorycode() {
		return mailCategorycode;
	}

	/**
	 * @param mailCategorycode The mailCategorycode to set.
	 */
	public void setMailCategorycode(String mailCategorycode) {
		this.mailCategorycode = mailCategorycode;
	}

	/**
	 * @return Returns the mailSubclass.
	 */
	public String getMailSubclass() {
		return mailSubclass;
	}

	/**
	 * @param mailSubclass The mailSubclass to set.
	 */
	public void setMailSubclass(String mailSubclass) {
		this.mailSubclass = mailSubclass;
	}

	/**
	 * @return Returns the orgExgOffice.
	 */
	public String getOrgExgOffice() {
		return orgExgOffice;
	}

	/**
	 * @param orgExgOffice The orgExgOffice to set.
	 */
	public void setOrgExgOffice(String orgExgOffice) {
		this.orgExgOffice = orgExgOffice;
	}

	/**
	 * @return Returns the postalAuthorityCode.
	 */
	public String getPostalAuthorityCode() {
		return postalAuthorityCode;
	}

	/**
	 * @param postalAuthorityCode The postalAuthorityCode to set.
	 */
	public void setPostalAuthorityCode(String postalAuthorityCode) {
		this.postalAuthorityCode = postalAuthorityCode;
	}

	/**
	 * @return Returns the postalAuthorityName.
	 */
	public String getPostalAuthorityName() {
		return postalAuthorityName;
	}

	/**
	 * @param postalAuthorityName The postalAuthorityName to set.
	 */
	public void setPostalAuthorityName(String postalAuthorityName) {
		this.postalAuthorityName = postalAuthorityName;
	}

	/**
	 * @return Returns the select.
	 */
	public String[] getSelect() {
		return this.select;
	}

	/**
	 * @param select The select to set.
	 */
	public void setSelect(String[] select) {
		this.select = select;
	}

	/**
	 * @return Returns the dsn.
	 */
	public String getDsn() {
		return dsn;
	}

	/**
	 * @param dsn The dsn to set.
	 */
	public void setDsn(String dsn) {
		this.dsn = dsn;
	}

	/**
	 * @return Returns the lovFlag.
	 */
	public boolean getLovFlag() {
		return lovFlag;
	}

	/**
	 * @param lovFlag The lovFlag to set.
	 */
	public void setLovFlag(boolean lovFlag) {
		this.lovFlag = lovFlag;
	}

	/**
	 * @return Returns the moreValuesFlag.
	 */
	public boolean getMoreValuesFlag() {
		return moreValuesFlag;
	}

	/**
	 * @param moreValuesFlag The moreValuesFlag to set.
	 */
	public void setMoreValuesFlag(boolean moreValuesFlag) {
		this.moreValuesFlag = moreValuesFlag;
	}

	/**
	 * @return Returns the billingBasis.
	 */
	public String getBillingBasis() {
		return billingBasis;
	}

	/**
	 * @param billingBasis The billingBasis to set.
	 */
	public void setBillingBasis(String billingBasis) {
		this.billingBasis = billingBasis;
	}

	
	

}
