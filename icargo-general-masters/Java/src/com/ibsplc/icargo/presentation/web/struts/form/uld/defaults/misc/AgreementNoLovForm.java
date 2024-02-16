/*
 * AgreementNoLovForm.java Created on Jun 28, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.misc;

import com.ibsplc.icargo.framework.model.ScreenModel;


import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDAgreementVO;

import com.ibsplc.xibase.server.framework.persistence.query.Page;

/**
 * @author
 *
 */
public class AgreementNoLovForm extends ScreenModel {
	
	private String agreementNo = "";
	private Page<ULDAgreementVO> pageAgreementLov=null;
	
	private String lastPageNumber="0";

	private String displayPage="1";

	private String[] numChecked;
	

	//private String productCodeField="";

	private static final String BUNDLE = "agreementnolov"; // The key attribute specified in struts_config.xml file.

	private String bundle;
	private static final String PRODUCT = "uld";
	private static final String SUBPRODUCT = "defaults";
	private static final String SCREENID = "uld.defaults.agreementnolov";
	//added by a-3045 for CR QF1154 starts
	private String partyCode;
	
	private String partyName;
	//added by a-3045 for CR QF1154 ends
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
 *
 * @return displayPage
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
 * @return lastPageNumber
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

/**
 *
 * @return productChecked
 */
	public String[] getNumChecked() {
		return numChecked;
	}
/**
 *
 * @param numChecked
 */
	public void setNumChecked(String[] numChecked) {
		this.numChecked = numChecked;
	}
/**
 *
 * @return 
 */
	public String getAgreementNo() {
		return agreementNo;
	}
/**
 *
 * @param agreementNo
 */
	public void setAgreementNo(String agreementNo) {
		this.agreementNo = agreementNo;
	}


	/**
	 * 
	 * @return
	 */
	public Page<ULDAgreementVO> getPageAgreementLov() {
		return pageAgreementLov;
	}

	/**
	 * @param pageAgreementLov
	 */
	public void setPageAgreementLov(Page<ULDAgreementVO> pageAgreementLov) {
		this.pageAgreementLov = pageAgreementLov;
	}

	/**
	 * @return the partyCode
	 */
	public String getPartyCode() {
		return partyCode;
	}

	/**
	 * @param partyCode the partyCode to set
	 */
	public void setPartyCode(String partyCode) {
		this.partyCode = partyCode;
	}

	/**
	 * @return the partyName
	 */
	public String getPartyName() {
		return partyName;
	}

	/**
	 * @param partyName the partyName to set
	 */
	public void setPartyName(String partyName) {
		this.partyName = partyName;
	}

	
}
