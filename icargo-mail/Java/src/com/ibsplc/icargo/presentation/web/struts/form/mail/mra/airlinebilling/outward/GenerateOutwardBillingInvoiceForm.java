/*
 * GenerateOutwardBillingInvoiceForm.java Created on Feb 14, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.struts.form.mail.mra.airlinebilling.outward;



import com.ibsplc.icargo.framework.model.ScreenModel;


/**
 * @author Rani Rose John
 * Form for GenerateOutwardBillingInvoice screen.
 *
 * Revision History
 *
 * Version      Date           Author          		    Description
 *
 *  0.1         Feb 14, 2007   Rani Rose John    		Initial draft
 */

public class GenerateOutwardBillingInvoiceForm extends ScreenModel {

    private static final String BUNDLE = "generateoutwardbillinginvoice";

	private static final String PRODUCT = "mail";
	private static final String SUBPRODUCT = "mra";
	private static final String SCREENID 
		= "mailtracking.mra.airlinebilling.outward.generateinvoice";
	
	private String clearingHouse;
	private String clearancePeriod;
	private String airlineCode;
	private boolean hasGenerated;
	private String fromDate;
	private String ownAirline;
	
	
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
	 * @return Returns the airlineCode.
	 */
	public String getAirlineCode() {
		return airlineCode;
	}

	/**
	 * @param airlineCode The airlineCode to set.
	 */
	public void setAirlineCode(String airlineCode) {
		this.airlineCode = airlineCode;
	}

	/**
	 * @return Returns the clearancePeriod.
	 */
	public String getClearancePeriod() {
		return clearancePeriod;
	}

	/**
	 * @param clearancePeriod The clearancePeriod to set.
	 */
	public void setClearancePeriod(String clearancePeriod) {
		this.clearancePeriod = clearancePeriod;
	}

	/**
	 * @return Returns the clearingHouse.
	 */
	public String getClearingHouse() {
		return clearingHouse;
	}

	/**
	 * @param clearingHouse The clearingHouse to set.
	 */
	public void setClearingHouse(String clearingHouse) {
		this.clearingHouse = clearingHouse;
	}

	/**
	 * @return Returns the hasGenerated.
	 */
	public boolean isHasGenerated() {
		return hasGenerated;
	}

	/**
	 * @param hasGenerated The hasGenerated to set.
	 */
	public void setHasGenerated(boolean hasGenerated) {
		this.hasGenerated = hasGenerated;
	}

	/**
	 * @return Returns the fromDate.
	 */
	public String getFromDate() {
		return fromDate;
	}

	/**
	 * @param fromDate The fromDate to set.
	 */
	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}

	/**
	 * @return Returns the ownAirline.
	 */
	public String getOwnAirline() {
		return ownAirline;
	}

	/**
	 * @param ownAirline The ownAirline to set.
	 */
	public void setOwnAirline(String ownAirline) {
		this.ownAirline = ownAirline;
	}

	

}
