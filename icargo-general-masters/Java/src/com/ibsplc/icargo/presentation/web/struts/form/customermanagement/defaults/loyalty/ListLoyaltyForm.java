/*
 * ListLoyaltyForm.java Created on Apr 23, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.struts.form.customermanagement.defaults.loyalty;

import com.ibsplc.icargo.framework.client.daterange.notation.DateFieldId;
import com.ibsplc.icargo.framework.model.ScreenModel;

/*
 * @author A-1496
 *
 */
public class ListLoyaltyForm extends ScreenModel {


	private static final String BUNDLE = "listloyaltyResources";
	private static final String PRODUCT = "customermanagement";
	private static final String SUBPRODUCT = "defaults";
	private static final String SCREENID = "customermanagement.defaults.listloyalty";
	
	
	private String statusFlag="";
	
	

	private String loyaltyName="";
	private String fromDate="";
	private String toDate="";
	
	private String status="";
			
	private String bundle;    
	
	
	private String displayPage = "1";
	private String lastPageNum =  "0";
	
	private String[] selectedProgramme;
	
	
	private String fromList = "";

	 //added by a-5203
	 private String countTotalFlag;
	 public String getCountTotalFlag()
	    {
	        return countTotalFlag;
	    }

	 public void setCountTotalFlag(String countTotalFlag)
	    {
	        this.countTotalFlag = countTotalFlag;
	    }

	    //end
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
	 * @return Returns the statusFlag.
	 */
	public String getStatusFlag() {
		return statusFlag;
	}

	/**
	 * @param statusFlag The statusFlag to set.
	 */
	public void setStatusFlag(String statusFlag) {
		this.statusFlag = statusFlag;
	}

	



	/**
	 * @return Returns the fromDate.
	 */
	@ DateFieldId(id="ListLoyalityProgramsDateRange",fieldType="from")/*Added By A-5131 for ICRD-9704*/
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
	 * @return Returns the loyaltyName.
	 */
	public String getLoyaltyName() {
		return loyaltyName;
	}

	/**
	 * @param loyaltyName The loyaltyName to set.
	 */
	public void setLoyaltyName(String loyaltyName) {
		this.loyaltyName = loyaltyName;
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
	 * @return Returns the toDate.
	 */
	@ DateFieldId(id="ListLoyalityProgramsDateRange",fieldType="to")/*Added By A-5131 for ICRD-9704*/
	public String getToDate() {
		return toDate;
	}

	/**
	 * @param toDate The toDate to set.
	 */
	public void setToDate(String toDate) {
		this.toDate = toDate;
	}

	/**
	 * @return Returns the fromList.
	 */
	public String getFromList() {
		return fromList;
	}

	/**
	 * @param fromList The fromList to set.
	 */
	public void setFromList(String fromList) {
		this.fromList = fromList;
	}

	/**
	 * @return Returns the selectedProgramme.
	 */
	public String[] getSelectedProgramme() {
		return selectedProgramme;
	}

	/**
	 * @param selectedProgramme The selectedProgramme to set.
	 */
	public void setSelectedProgramme(String[] selectedProgramme) {
		this.selectedProgramme = selectedProgramme;
	}

	
}
