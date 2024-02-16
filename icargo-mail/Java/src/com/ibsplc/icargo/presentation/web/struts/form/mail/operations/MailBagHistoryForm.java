/*
 * MailBagHistoryForm.java Created on Dec 14, 2005
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
 * @author A-2047
 *
 */
public class MailBagHistoryForm extends ScreenModel {

	private static final String SCREEN_ID = "mailtracking.defaults.mailbaghistory";
	private static final String PRODUCT_NAME = "mail";
	private static final String SUBPRODUCT_NAME = "operations";
	private static final String BUNDLE = "mailbagHistoryResources";

	private String bundle;
	private String mailbagId;
	private String dsn;
	private String ooe;
	private String doe;
	private String catogory;
	private String mailClass;
	private String year;
	private String rsn;
	private String mailSubclass; //Added by A-8164 for ICRD-257677
	private String enquiryFlag; //Added by A-8164 for ICRD-260365
	
	@MeasureAnnotation(mappedValue="weightMeasure",unitType="MWT")
	private String weight;
	private Measure weightMeasure;
	
	private String btnDisableReq;
	private String reqDeliveryTime;
	
	//Added as a part of ICRD-197419
	private String mailRemarks;
	
	//added by A-8149 for ICRD-248207 begins
		private String totalViewRecords;
		private String displayPopupPage;
		
		private boolean mailbagDuplicatePresent;
		
		@MeasureAnnotation(mappedValue="actualWeightMeasure",unitType="MWT")
		private String actualWeight;
		private Measure actualWeightMeasure;
		
		public String getTotalViewRecords() {
			return totalViewRecords;
		}

		public void setTotalViewRecords(String totalViewRecords) {
			this.totalViewRecords = totalViewRecords;
		}

		public String getDisplayPopupPage() {
			return displayPopupPage;
		}

		public void setDisplayPopupPage(String displayPopupPage) {
			this.displayPopupPage = displayPopupPage;
		}
		//added by A-8149 for ICRD-248207 ends
	
	
	/**
	 * 	Getter for mailRemarks 
	 *	Added by : a-7540 on 20-Jul-2017
	 * 	Used for :ICRD-197419 to add new field 'remarks'
	 */
	public String getMailRemarks() {
		return mailRemarks;
	}

	/**
	 *  @param mailRemarks the mailRemarks to set
	 * 	Setter for mailRemarks 
	 *	Added by : a-7540 on 20-Jul-2017
	 * 	Used for :ICRD-197419 to add new field 'remarks'
	 */
	public void setMailRemarks(String mailRemarks) {
		this.mailRemarks = mailRemarks;
	}

	/**
	 * @return the weightMeasure
	 */
	public Measure getWeightMeasure() {
		return weightMeasure;
	}

	/**
	 * @param weightMeasure the weightMeasure to set
	 */
	public void setWeightMeasure(Measure weightMeasure) {
		this.weightMeasure = weightMeasure;
	}

	/**
	 * @return screenId
	 */
	public String getScreenId() {
		return SCREEN_ID;
	}

	/**
	 * @return product
	 */
	public String getProduct() {
		return PRODUCT_NAME;
	}

	/**
	 * @return subProduct
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

	/**
	 * @param bundle The bundle to set.
	 */
	public void setBundle(String bundle) {
		this.bundle = bundle;
	}

	/**
	 * @return Returns the catogory.
	 */
	public String getCatogory() {
		return catogory;
	}

	/**
	 * @param catogory The catogory to set.
	 */
	public void setCatogory(String catogory) {
		this.catogory = catogory;
	}

	/**
	 * @return Returns the doe.
	 */
	public String getDoe() {
		return doe;
	}

	/**
	 * @param doe The doe to set.
	 */
	public void setDoe(String doe) {
		this.doe = doe;
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
	 * @return Returns the mailbagId.
	 */
	public String getMailbagId() {
		return mailbagId;
	}

	/**
	 * @param mailbagId The mailbagId to set.
	 */
	public void setMailbagId(String mailbagId) {
		this.mailbagId = mailbagId;
	}

	/**
	 * @return Returns the mailClass.
	 */
	public String getMailClass() {
		return mailClass;
	}

	/**
	 * @param mailClass The mailClass to set.
	 */
	public void setMailClass(String mailClass) {
		this.mailClass = mailClass;
	}

	/**
	 * @return Returns the ooe.
	 */
	public String getOoe() {
		return ooe;
	}

	/**
	 * @param ooe The ooe to set.
	 */
	public void setOoe(String ooe) {
		this.ooe = ooe;
	}

	/**
	 * @return Returns the rsn.
	 */
	public String getRsn() {
		return rsn;
	}

	/**
	 * @param rsn The rsn to set.
	 */
	public void setRsn(String rsn) {
		this.rsn = rsn;
	}

	/**
	 * @return Returns the weight.
	 */
	public String getWeight() {
		return weight;
	}

	/**
	 * @param weight The weight to set.
	 */
	public void setWeight(String weight) {
		this.weight = weight;
	}

	/**
	 * @return Returns the year.
	 */
	public String getYear() {
		return year;
	}

	/**
	 * @param year The year to set.
	 */
	public void setYear(String year) {
		this.year = year;
	}

	/**
	 * @param btnDisableReq the btnDisableReq to set
	 */
	public void setBtnDisableReq(String btnDisableReq) {
		this.btnDisableReq = btnDisableReq;
	}

	/**
	 * @return the btnDisableReq
	 */
	public String getBtnDisableReq() {
		return btnDisableReq;
	}

	/**
	 * 	Getter for reqDeliveryTime 
	 *	Added by : A-6245 on 31-Jul-2017
	 * 	Used for :
	 */
	public String getReqDeliveryTime() {
		return reqDeliveryTime;
	}

	/**
	 *  @param reqDeliveryTime the reqDeliveryTime to set
	 * 	Setter for reqDeliveryTime 
	 *	Added by : A-6245 on 31-Jul-2017
	 * 	Used for :
	 */
	public void setReqDeliveryTime(String reqDeliveryTime) {
		this.reqDeliveryTime = reqDeliveryTime;
	}

	public String getMailSubclass() {
		return mailSubclass;
	}

	public void setMailSubclass(String mailSubclass) {
		this.mailSubclass = mailSubclass;
	}

	public String getActualWeight() {
		return actualWeight;
	}

	public void setActualWeight(String actualWeight) {
		this.actualWeight = actualWeight;
	}

	public Measure getActualWeightMeasure() {
		return actualWeightMeasure;
	}

	public void setActualWeightMeasure(Measure actualWeightMeasure) {
		this.actualWeightMeasure = actualWeightMeasure;
	}

	public boolean isMailbagDuplicatePresent() {
		return mailbagDuplicatePresent;
	}

	public void setMailbagDuplicatePresent(boolean mailbagDuplicatePresent) {
		this.mailbagDuplicatePresent = mailbagDuplicatePresent;
	}

	

	
	
	

}
