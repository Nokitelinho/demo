/*
 * MonitorMailSLAForm.java Created on Mar 30,2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.struts.form.mail.operations;


import com.ibsplc.icargo.framework.model.ScreenModel;


/**
 * @author Ruby Abraham
 * Form for MonitorMailSLA screen.
 *
 * Revision History
 *
 * Version        Date           		  Author          		    Description
 *
 *  0.1        Mar 30, 2007    			Ruby Abraham   	    	   Initial draft
 */

public class MonitorMailSLAForm extends ScreenModel {

    private static final String BUNDLE = "monitormailslaresources";

	//private String bundle;

	private static final String PRODUCT = "mail";
	private static final String SUBPRODUCT = "operations";
	private static final String SCREENID = "mailtracking.defaults.monitormailsla";



	private String airlineCode;

	private String paCode;
	
	private String carrierCode;	
	
	private String flightNo;
	
	private String category;	
	
	private String activity;
	
	private String slaStatus;

	private String[] slaId;
	
	private boolean isSelectAll;

	private String[] select;

	private String displayPage = "1";
	
	private String  lastPageNum = "0";

	private String[] selectedElements;

	//Variable for checking from which screen this Form One screen is coming

	private String closeFlag;



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
	 * @return Returns the activity.
	 */
	public String getActivity() {
		return activity;
	}

	/**
	 * @param activity The activity to set.
	 */
	public void setActivity(String activity) {
		this.activity = activity;
	}

	/**
	 * @return Returns the carrierCode.
	 */
	public String getCarrierCode() {
		return carrierCode;
	}

	/**
	 * @param carrierCode The carrierCode to set.
	 */
	public void setCarrierCode(String carrierCode) {
		this.carrierCode = carrierCode;
	}

	/**
	 * @return Returns the flightNo.
	 */
	public String getFlightNo() {
		return flightNo;
	}

	/**
	 * @param flightNo The flightNo to set.
	 */
	public void setFlightNo(String flightNo) {
		this.flightNo = flightNo;
	}

	/**
	 * @return Returns the category.
	 */
	public String getCategory() {
		return category;
	}

	/**
	 * @param category The category to set.
	 */
	public void setCategory(String category) {
		this.category = category;
	}

	/**
	 * @return Returns the paCode.
	 */
	public String getPaCode() {
		return paCode;
	}

	/**
	 * @param paCode The paCode to set.
	 */
	public void setPaCode(String paCode) {
		this.paCode = paCode;
	}

	/**
	 * @return Returns the slaStatus.
	 */
	public String getSlaStatus() {
		return slaStatus;
	}

	/**
	 * @param slaStatus The slaStatus to set.
	 */
	public void setSlaStatus(String slaStatus) {
		this.slaStatus = slaStatus;
	}

	
	
	
	/**
	 * @return Returns the slaId.
	 */
	public String[] getSlaId() {
		return slaId;
	}

	/**
	 * @param slaId The slaId to set.
	 */
	public void setSlaId(String[] slaId) {
		this.slaId = slaId;
	}

	/**
	 * @return Returns the isSelectAll.
	 */
	public boolean isSelectAll() {
		return this.isSelectAll;
	}

	/**
	 * @param isSelectAll The isSelectAll to set.
	 */
	public void setSelectAll(boolean isSelectAll) {
		this.isSelectAll = isSelectAll;
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
	 * @return Returns the selectedElements.
	 */
	public String[] getSelectedElements() {
		return selectedElements;
	}

	/**
	 * @param selectedElements The selectedElements to set.
	 */
	public void setSelectedElements(String[] selectedElements) {
		this.selectedElements = selectedElements;
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

}
