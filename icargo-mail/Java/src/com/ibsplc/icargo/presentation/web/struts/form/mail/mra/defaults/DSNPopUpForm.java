/*
 * DSNPopUpForm.java Created on Aug 22, 2008
 *
 * Copyright 2008 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults;

import com.ibsplc.icargo.framework.model.ScreenModel;


/**
 * @author A-2391
 *
 */
public class DSNPopUpForm extends ScreenModel {

	private static final String BUNDLE ="despatchselectpopup";
	
	private static final String PRODUCT = "mail";

	private static final String SUBPRODUCT = "mra";

	private static final String SCREENID = "mailtracking.mra.defaults.dsnselectpopup";

	private String code;
	
	private String dsnFilterDate="";
	
	private String[] check;
	
	private String[] companyCode;

	private String[] dsn;

	private String[] gpaCode;
	
	private String[] blgBasis;
	
	private String[] dsnDate;	
	
    private String[] csgdocnum;

	private String lastPageNum="0";

	private String displayPage="1";
	
	private String okFlag="";
	
	private String fromPage="";
    
   
	/**
	 * @return Returns the okFlag.
	 */
	public String getOkFlag() {
		return okFlag;
	}

	/**
	 * @param okFlag The okFlag to set.
	 */
	public void setOkFlag(String okFlag) {
		this.okFlag = okFlag;
	}

	/**
	 * @return Returns the csgdocnum.
	 */
	public String[] getCsgdocnum() {
		return csgdocnum;
	}

	/**
	 * @param csgdocnum The csgdocnum to set.
	 */
	public void setCsgdocnum(String[] csgdocnum) {
		this.csgdocnum = csgdocnum;
	}

	

	/**
	 * @return Returns the blgBasis.
	 */
	public String[] getBlgBasis() {
		return blgBasis;
	}

	/**
	 * @param blgBasis The blgBasis to set.
	 */
	public void setBlgBasis(String[] blgBasis) {
		this.blgBasis = blgBasis;
	}

	/**
	 * @return Returns the dsnDate.
	 */
	public String[] getDsnDate() {
		return dsnDate;
	}

	/**
	 * @param dsnDate The dsnDate to set.
	 */
	public void setDsnDate(String[] dsnDate) {
		this.dsnDate = dsnDate;
	}

	/**
	 * @return Returns the screenid.
	 */
    public String getScreenId() {
        return SCREENID;
    }

    /**
	 * @return Returns the product.
	 */
    public String getProduct() {
        return PRODUCT;
    }

    /**
	 * @return Returns the subproduct.
	 */
    public String getSubProduct() {
        return SUBPRODUCT;
    }
    /**
	 * @return Returns the bundle.
	 */
	public static String getBUNDLE() {
		return BUNDLE;
	}
	/**
	 * @param bundle The bundle to set.
	 */
	public void setBundle(String bundle) {
		//this.bundle = bundle;
	}
	/**
	 * @return Returns the bundle.
	 */
	public String getBundle() {
		return BUNDLE;
	}
	/**
	 * @return Returns the screenid.
	 */
	public static String getSCREENID() {
		return SCREENID;
	}

	/**
	 * @return Returns the companyCode.
	 */
	public String[] getCompanyCode() {
		return companyCode;
	}

	/**
	 * @param companyCode The companyCode to set.
	 */
	public void setCompanyCode(String[] companyCode) {
		this.companyCode = companyCode;
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

	

	/**
	 * @return code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code
	 */
	public void setCode(String code) {
		this.code = code;
	}


	/**
	 * @return Returns the dsn.
	 */
	public String[] getDsn() {
		return dsn;
	}

	/**
	 * @param dsn The dsn to set.
	 */
	public void setDsn(String[] dsn) {
		this.dsn = dsn;
	}

	/**
	 * @return Returns the gpaCode.
	 */
	public String[] getGpaCode() {
		return gpaCode;
	}

	/**
	 * @param gpaCode The gpaCode to set.
	 */
	public void setGpaCode(String[] gpaCode) {
		this.gpaCode = gpaCode;
	}

	

	/**
	 * @return Returns the check.
	 */
	public String[] getCheck() {
		return check;
	}

	/**
	 * @param check The check to set.
	 */
	public void setCheck(String[] check) {
		this.check = check;
	}

	/**
	 * @return Returns the dsnFilterDate.
	 */
	public String getDsnFilterDate() {
		return dsnFilterDate;
	}

	/**
	 * @param dsnFilterDate The dsnFilterDate to set.
	 */
	public void setDsnFilterDate(String dsnFilterDate) {
		this.dsnFilterDate = dsnFilterDate;
	}

	/**
	 * @return Returns the fromPage.
	 */
	public String getFromPage() {
		return fromPage;
	}

	/**
	 * @param fromPage The fromPage to set.
	 */
	public void setFromPage(String fromPage) {
		this.fromPage = fromPage;
	}

	

	

}
