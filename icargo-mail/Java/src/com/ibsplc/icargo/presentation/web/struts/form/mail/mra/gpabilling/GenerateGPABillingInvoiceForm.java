/*
 * GenerateGPABillingInvoiceForm.java Created on Jan 20, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.struts.form.mail.mra.gpabilling;

import com.ibsplc.icargo.framework.client.daterange.notation.DateFieldId;
import com.ibsplc.icargo.framework.model.ScreenModel;

/**
 * @author Kiran S P
 * Revision History
 *
 * Version      Date           Author          		    Description
 *
 *  0.1         Jun 20, 2006   Kiran S P 				Initial draft , Added method execute
 */
public class GenerateGPABillingInvoiceForm extends ScreenModel {

	private static final String BUNDLE = "geninvresources";

	private static final String PRODUCT = "mail";

	private static final String SUBPRODUCT = "mra";

	private static final String SCREENID ="mailtracking.mra.gpabilling.generateinvoice";

	public static final String STATUS_GENERATE_SUCCESS = "GENERATE_SUCCESS";

	private String blgPeriodFrom;

	private String blgPeriodTo;

	private String gpacode;

	private String gpaname;

	private String country;

	private String screenStatusFlag;
	
	private String screenSuccessFlag;

	private String turkishSpecificFlag;

	private String invoiceType;
	
	private boolean addNew;

	public String getScreenStatusFlag() {
		return screenStatusFlag;
	}

	public void setScreenStatusFlag(String screenStatusFlag) {
		this.screenStatusFlag = screenStatusFlag;
	}

	/**
	 * @return
	 */
	public String getBundle() {
		return BUNDLE;
	}

	/**
	 * return the screen id
	 */
	public String getScreenId() {

		return SCREENID;
	}

	/**
	 * return the product
	 */
	public String getProduct() {

		return PRODUCT;
	}

	/**
	 * return the subproduct name
	 */
	public String getSubProduct() {

		return SUBPRODUCT;
	}


	/**
	 * @return Returns the blgPeriod.
	 */
	@DateFieldId(id="GenerateInvoiceDateRange",fieldType="from")//Added By T-1925 for ICRD-9704
	public String getBlgPeriodFrom() {
		return blgPeriodFrom;
	}

	/**
	 * @param blgPeriodFrom The blgPeriod to set.
	 */
	public void setBlgPeriodFrom(String blgPeriodFrom) {
		this.blgPeriodFrom = blgPeriodFrom;
	}




	/**
	 * @return Returns the blgPeriodTo.
	 */
	@DateFieldId(id="GenerateInvoiceDateRange",fieldType="to")//Added By T-1925 for ICRD-9704
	public String getBlgPeriodTo() {
		return blgPeriodTo;
	}


	/**
	 * @param blgPeriodTo The blgPeriodTo to set.
	 */
	public void setBlgPeriodTo(String blgPeriodTo) {
		this.blgPeriodTo = blgPeriodTo;
	}


	/**
	 * @return Returns the country.
	 */
	public String getCountry() {
		return country;
	}

	/**
	 * @param country The country to set.
	 */
	public void setCountry(String country) {
		this.country = country;
	}

	/**
	 * @return Returns the gpacode.
	 */
	public String getGpacode() {
		return gpacode;
	}

	/**
	 * @param gpacode The gpacode to set.
	 */
	public void setGpacode(String gpacode) {
		this.gpacode = gpacode;
	}

	/**
	 * @return Returns the gpaname.
	 */
	public String getGpaname() {
		return gpaname;
	}

	/**
	 * @param gpaname The gpaname to set.
	 */
	public void setGpaname(String gpaname) {
		this.gpaname = gpaname;
	}

	public String getScreenSuccessFlag() {
		return screenSuccessFlag;
	}

	public void setScreenSuccessFlag(String screenSuccessFlag) {
		this.screenSuccessFlag = screenSuccessFlag;
	}
	/**
	 * 	Getter for turkishSpecificFlag 
	 *	Added by : A-4809 on 02-Jan-2014
	 * 	Used for : ICRD-42160
	 */
	public String getTurkishSpecificFlag() {
		return turkishSpecificFlag;
	}
	/**
	 *  @param turkishSpecificFlag the turkishSpecificFlag to set
	 * 	Setter for turkishSpecificFlag 
	 *	Added by : A-4809 on 02-Jan-2014
	 * 	Used for : ICRD-42160
	 */
	public void setTurkishSpecificFlag(String turkishSpecificFlag) {
		this.turkishSpecificFlag = turkishSpecificFlag;
	}

	/**
	 * 	Getter for invoiceType 
	 *	Added by : A-6991 on 29-Aug-2017
	 * 	Used for : ICRD-211662
	 */
	public String getInvoiceType() {
		return invoiceType;
	}

	/**
	 *  @param invoiceType the invoiceType to set
	 * 	Setter for invoiceType 
	 *	Added by : A-6991 on 29-Aug-2017
	 * 	Used for : ICRD-211662
	 */
	public void setInvoiceType(String invoiceType) {
		this.invoiceType = invoiceType;
	}

	/**
	 * 	Getter for addNew 
	 *	Added by : A-6991 on 29-Aug-2017
	 * 	Used for : ICRD-211662
	 */
	public boolean isAddNew() {
		return addNew;
	}

	/**
	 *  @param addNew the addNew to set
	 * 	Setter for addNew 
	 *	Added by : A-6991 on 29-Aug-2017
	 * 	Used for : ICRD-211662
	 */
	public void setAddNew(boolean addNew) {
		this.addNew = addNew;
	}
}
