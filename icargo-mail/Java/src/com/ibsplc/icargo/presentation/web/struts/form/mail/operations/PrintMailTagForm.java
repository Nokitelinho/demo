/*
 * PrintMailTagForm.java Created on Oct 22, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.struts.form.mail.operations;

import com.ibsplc.icargo.framework.model.ScreenModel;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.icargo.framework.util.unit.MeasureAnnotation;

/**
 * @author A-1876
 *
 */
public class PrintMailTagForm extends ScreenModel {

	private static final String SCREEN_ID = "mailtracking.defaults.printmailtag";
	private static final String PRODUCT_NAME = "mail";
	private static final String SUBPRODUCT_NAME = "operations";
	private static final String BUNDLE = "printMailTagResources";

	private String[] originOE;
	private String[] destnOE;
	private String[] category;
	private String[] subClass;
	private String[] year;
	private String[] dsn;
	private String[] rsn;
	private String[] hni;
	private String[] ri;
	private String flag;
	@MeasureAnnotation(mappedValue="mailWtMeasure",unitType="MWT")
	private String[] weight;
	
	/**
	 * @return the wgtMeasure
	 */
	public Measure getWgtMeasure() {
		return wgtMeasure;
	}

	/**
	 * @param wgtMeasure the wgtMeasure to set
	 */
	public void setWgtMeasure(Measure wgtMeasure) {
		this.wgtMeasure = wgtMeasure;
	}

	private String[] selectMail;
	private String[] opFlag; 
	//Added for ICRD-205027 starts 
	private String mailOOE;
	private String mailDOE;
	private String mailCat;
	private String mailSC;
	private String mailYr;
	private String mailDSN;
	private String mailRSN;
	private String mailHNI;
	private String mailRI;
	@MeasureAnnotation(mappedValue="mailWtMeasure",unitType="MWT")
	private String mailWt;
	private Measure[] mailWtMeasure;
	private boolean validPrintRequest; 
	/**
	 * @return the mailWtMeasure
	 */
	public Measure[] getMailWtMeasure() {
		return mailWtMeasure;
	}

	/**
	 * @param mailWtMeasure the mailWtMeasure to set
	 */
	public void setMailWtMeasure(Measure[] mailWtMeasure) {
		this.mailWtMeasure = mailWtMeasure;
	}

	/**
	 * @return the wgt
	 */
	public String getWgt() {
		return wgt;
	}

	/**
	 * @param wgt the wgt to set
	 */
	public void setWgt(String wgt) {
		this.wgt = wgt;
	}

	private String mailId;
	private String[] mailbagId;
	//Added for ICRD-205027 ends
	
	@MeasureAnnotation(mappedValue="wgtMeasure",unitType="MWT")
	private String wgt;
    private Measure wgtMeasure;//added by A-7871 for ICRD-263254


	
	/**
     * @return SCREEN_ID - String
     */
    public String getScreenId() {
        return SCREEN_ID;
    }

    /**
     * @return PRODUCT_NAME - String
     */
    public String getProduct() {
        return PRODUCT_NAME;
    }

    /**
     * @return SUBPRODUCT_NAME - String
     */
    public String getSubProduct() {
        return SUBPRODUCT_NAME;
    }

	/**
	 * @return Returns the bundle.
	 */
	public String getBundle() {
		return BUNDLE;
	}

	public String[] getCategory() {
		return category;
	}

	public void setCategory(String[] category) {
		this.category = category;
	}

	public String[] getDestnOE() {
		return destnOE;
	}

	public void setDestnOE(String[] destnOE) {
		this.destnOE = destnOE;
	}

	public String[] getDsn() {
		return dsn;
	}

	public void setDsn(String[] dsn) {
		this.dsn = dsn;
	}

	public String[] getHni() {
		return hni;
	}

	public void setHni(String[] hni) {
		this.hni = hni;
	}

	public String[] getOriginOE() {
		return originOE;
	}

	public void setOriginOE(String[] originOE) {
		this.originOE = originOE;
	}

	public String[] getRi() {
		return ri;
	}

	public void setRi(String[] ri) {
		this.ri = ri;
	}

	public String[] getRsn() {
		return rsn;
	}

	public void setRsn(String[] rsn) {
		this.rsn = rsn;
	}

	public String[] getSubClass() {
		return subClass;
	}

	public void setSubClass(String[] subClass) {
		this.subClass = subClass;
	}

	public String[] getWeight() {
		return weight;
	}

	public void setWeight(String[] weight) {
		this.weight = weight;
	}

	public String[] getYear() {
		return year;
	}

	public void setYear(String[] year) {
		this.year = year;
	}

	public String[] getOpFlag() {
		return opFlag;
	}

	public void setOpFlag(String[] opFlag) {
		this.opFlag = opFlag;
	}

	public String[] getSelectMail() {
		return selectMail;
	}

	public void setSelectMail(String[] selectMail) {
		this.selectMail = selectMail;
	}

	/**
	 * 	Getter for mailOOE 
	 *	Added by : a-6245 on 22-Jun-2017
	 * 	Used for :
	 */
	public String getMailOOE() {
		return mailOOE;
	}

	/**
	 *  @param mailOOE the mailOOE to set
	 * 	Setter for mailOOE 
	 *	Added by : a-6245 on 22-Jun-2017
	 * 	Used for :
	 */
	public void setMailOOE(String mailOOE) {
		this.mailOOE = mailOOE;
	}

	/**
	 * 	Getter for mailDOE 
	 *	Added by : a-6245 on 22-Jun-2017
	 * 	Used for :
	 */
	public String getMailDOE() {
		return mailDOE;
	}

	/**
	 *  @param mailDOE the mailDOE to set
	 * 	Setter for mailDOE 
	 *	Added by : a-6245 on 22-Jun-2017
	 * 	Used for :
	 */
	public void setMailDOE(String mailDOE) {
		this.mailDOE = mailDOE;
	}

	/**
	 * 	Getter for mailCat 
	 *	Added by : a-6245 on 22-Jun-2017
	 * 	Used for :
	 */
	public String getMailCat() {
		return mailCat;
	}

	/**
	 *  @param mailCat the mailCat to set
	 * 	Setter for mailCat 
	 *	Added by : a-6245 on 22-Jun-2017
	 * 	Used for :
	 */
	public void setMailCat(String mailCat) {
		this.mailCat = mailCat;
	}

	/**
	 * 	Getter for mailSC 
	 *	Added by : a-6245 on 22-Jun-2017
	 * 	Used for :
	 */
	public String getMailSC() {
		return mailSC;
	}

	/**
	 *  @param mailSC the mailSC to set
	 * 	Setter for mailSC 
	 *	Added by : a-6245 on 22-Jun-2017
	 * 	Used for :
	 */
	public void setMailSC(String mailSC) {
		this.mailSC = mailSC;
	}

	/**
	 * 	Getter for mailYr 
	 *	Added by : a-6245 on 22-Jun-2017
	 * 	Used for :
	 */
	public String getMailYr() {
		return mailYr;
	}

	/**
	 *  @param mailYr the mailYr to set
	 * 	Setter for mailYr 
	 *	Added by : a-6245 on 22-Jun-2017
	 * 	Used for :
	 */
	public void setMailYr(String mailYr) {
		this.mailYr = mailYr;
	}

	/**
	 * 	Getter for mailDSN 
	 *	Added by : a-6245 on 22-Jun-2017
	 * 	Used for :
	 */
	public String getMailDSN() {
		return mailDSN;
	}

	/**
	 *  @param mailDSN the mailDSN to set
	 * 	Setter for mailDSN 
	 *	Added by : a-6245 on 22-Jun-2017
	 * 	Used for :
	 */
	public void setMailDSN(String mailDSN) {
		this.mailDSN = mailDSN;
	}

	/**
	 * 	Getter for mailRSN 
	 *	Added by : a-6245 on 22-Jun-2017
	 * 	Used for :
	 */
	public String getMailRSN() {
		return mailRSN;
	}

	/**
	 *  @param mailRSN the mailRSN to set
	 * 	Setter for mailRSN 
	 *	Added by : a-6245 on 22-Jun-2017
	 * 	Used for :
	 */
	public void setMailRSN(String mailRSN) {
		this.mailRSN = mailRSN;
	}

	/**
	 * 	Getter for mailHNI 
	 *	Added by : a-6245 on 22-Jun-2017
	 * 	Used for :
	 */
	public String getMailHNI() {
		return mailHNI;
	}

	/**
	 *  @param mailHNI the mailHNI to set
	 * 	Setter for mailHNI 
	 *	Added by : a-6245 on 22-Jun-2017
	 * 	Used for :
	 */
	public void setMailHNI(String mailHNI) {
		this.mailHNI = mailHNI;
	}

	/**
	 * 	Getter for mailRI 
	 *	Added by : a-6245 on 22-Jun-2017
	 * 	Used for :
	 */
	public String getMailRI() {
		return mailRI;
	}

	/**
	 *  @param mailRI the mailRI to set
	 * 	Setter for mailRI 
	 *	Added by : a-6245 on 22-Jun-2017
	 * 	Used for :
	 */
	public void setMailRI(String mailRI) {
		this.mailRI = mailRI;
	}

	/**
	 * 	Getter for mailWt 
	 *	Added by : a-6245 on 22-Jun-2017
	 * 	Used for :
	 */
	public String getMailWt() {
		return mailWt;
	}

	/**
	 *  @param mailWt the mailWt to set
	 * 	Setter for mailWt 
	 *	Added by : a-6245 on 22-Jun-2017
	 * 	Used for :
	 */
	public void setMailWt(String mailWt) {
		this.mailWt = mailWt;
	}

	/**
	 * 	Getter for mailId 
	 *	Added by : a-6245 on 22-Jun-2017
	 * 	Used for :
	 */
	public String getMailId() {
		return mailId;
	}

	/**
	 *  @param mailId the mailId to set
	 * 	Setter for mailId 
	 *	Added by : a-6245 on 22-Jun-2017
	 * 	Used for :
	 */
	public void setMailId(String mailId) {
		this.mailId = mailId;
	}

	/**
	 * 	Getter for mailbagId 
	 *	Added by : a-6245 on 22-Jun-2017
	 * 	Used for :
	 */
	public String[] getMailbagId() {
		return mailbagId;
	}

	/**
	 *  @param mailbagId the mailbagId to set
	 * 	Setter for mailbagId 
	 *	Added by : a-6245 on 22-Jun-2017
	 * 	Used for :
	 */
	public void setMailbagId(String[] mailbagId) {
		this.mailbagId = mailbagId;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}
	public String getSelectedMailBagId() {
		return selectedMailBagId;
	}

	public void setSelectedMailBagId(String selectedMailBagId) {
		this.selectedMailBagId = selectedMailBagId;
	}

	public boolean isValidPrintRequest() {
		return validPrintRequest;
	}

	public void setValidPrintRequest(boolean validPrintRequest) {
		this.validPrintRequest = validPrintRequest;
	}

	private String selectedMailBagId;
}
