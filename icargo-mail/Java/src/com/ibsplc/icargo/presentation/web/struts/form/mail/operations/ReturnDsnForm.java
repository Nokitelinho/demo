/*
 * ReturnDsnForm.java Created on July 03, 2006
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
 * @author A-1861 
 *
 */
public class ReturnDsnForm extends ScreenModel {

	private static final String SCREEN_ID = "mailtracking.defaults.returndsn";
	private static final String PRODUCT_NAME = "mail";
	private static final String SUBPRODUCT_NAME = "operations";
	private static final String BUNDLE = "returnDsnResources";

	private String dsn;
	private String originOE;
	private String destnOE;
	private String mailClass;
	private String year;
	
	private String[] damageCode;
	private String[] damageRemarks;
	private String[] damagedBags;
	
	@MeasureAnnotation(mappedValue="damagedWeightMeasure",unitType="MWT")
	private String[] damagedWeight;
	private Measure[] damagedWeightMeasure;
	
	private String[] returnedBags;
	
	@MeasureAnnotation(mappedValue="returnedWeightMeasure",unitType="MWT")
	private String[] returnedWeight;
	private Measure[] returnedWeightMeasure;
	
	private String[] operationFlag;
	private String returnCheckAll;
	private String[] returnSubCheck;
		
	private String postalAdmin;
	
	private String fromScreen;
	private String selectedDsns;
	
	private int currentPage;
	private int displayPage;
	private int lastPage;
	
	private int dmgNOB;
	
	@MeasureAnnotation(mappedValue="dmgWeightMeasure",unitType="MWT")
	private double dmgWeight;
	private Measure dmgWeightMeasure;
	
	//added by a-4810 for icrd-21867
	private String actionStatusFlag;
	

	/**
	 * @return Returns the operationFlag.
	 */
	public String[] getOperationFlag() {
		return operationFlag;
	}

	/**
	 * @param operationFlag The operationFlag to set.
	 */
	public void setOperationFlag(String[] operationFlag) {
		this.operationFlag = operationFlag;
	}

	/**
	 * @return the damagedWeightMeasure
	 */
	public Measure[] getDamagedWeightMeasure() {
		return damagedWeightMeasure;
	}

	/**
	 * @param damagedWeightMeasure the damagedWeightMeasure to set
	 */
	public void setDamagedWeightMeasure(Measure[] damagedWeightMeasure) {
		this.damagedWeightMeasure = damagedWeightMeasure;
	}

	/**
	 * @return the returnedWeightMeasure
	 */
	public Measure[] getReturnedWeightMeasure() {
		return returnedWeightMeasure;
	}

	/**
	 * @param returnedWeightMeasure the returnedWeightMeasure to set
	 */
	public void setReturnedWeightMeasure(Measure[] returnedWeightMeasure) {
		this.returnedWeightMeasure = returnedWeightMeasure;
	}

	/**
	 * @return the dmgWeightMeasure
	 */
	public Measure getDmgWeightMeasure() {
		return dmgWeightMeasure;
	}

	/**
	 * @param dmgWeightMeasure the dmgWeightMeasure to set
	 */
	public void setDmgWeightMeasure(Measure dmgWeightMeasure) {
		this.dmgWeightMeasure = dmgWeightMeasure;
	}

	/**
	 * @return Returns the damagedBags.
	 */
	public String[] getDamagedBags() {
		return damagedBags;
	}

	/**
	 * @param damagedBags The damagedBags to set.
	 */
	public void setDamagedBags(String[] damagedBags) {
		this.damagedBags = damagedBags;
	}

	/**
	 * @return Returns the damagedWeight.
	 */
	public String[] getDamagedWeight() {
		return damagedWeight;
	}

	/**
	 * @param damagedWeight The damagedWeight to set.
	 */
	public void setDamagedWeight(String[] damagedWeight) {
		this.damagedWeight = damagedWeight;
	}

	/**
	 * @return Returns the returnedBags.
	 */
	public String[] getReturnedBags() {
		return returnedBags;
	}

	/**
	 * @param returnedBags The returnedBags to set.
	 */
	public void setReturnedBags(String[] returnedBags) {
		this.returnedBags = returnedBags;
	}

	/**
	 * @return Returns the returnedWeight.
	 */
	public String[] getReturnedWeight() {
		return returnedWeight;
	}

	/**
	 * @param returnedWeight The returnedWeight to set.
	 */
	public void setReturnedWeight(String[] returnedWeight) {
		this.returnedWeight = returnedWeight;
	}

	/**
	 * @return Returns the currentPage.
	 */
	public int getCurrentPage() {
		return currentPage;
	}

	/**
	 * @param currentPage The currentPage to set.
	 */
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	/**
	 * @return Returns the displayPage.
	 */
	public int getDisplayPage() {
		return displayPage;
	}

	/**
	 * @param displayPage The displayPage to set.
	 */
	public void setDisplayPage(int displayPage) {
		this.displayPage = displayPage;
	}

	/**
	 * @return Returns the lastPage.
	 */
	public int getLastPage() {
		return lastPage;
	}

	/**
	 * @param lastPage The lastPage to set.
	 */
	public void setLastPage(int lastPage) {
		this.lastPage = lastPage;
	}

	/**
	 * @return Returns the selectedDsns.
	 */
	public String getSelectedDsns() {
		return selectedDsns;
	}

	/**
	 * @param selectedDsns The selectedDsns to set.
	 */
	public void setSelectedDsns(String selectedDsns) {
		this.selectedDsns = selectedDsns;
	}
	
	/**
	 * @return Returns the destnOE.
	 */
	public String getDestnOE() {
		return destnOE;
	}

	/**
	 * @param destnOE The destnOE to set.
	 */
	public void setDestnOE(String destnOE) {
		this.destnOE = destnOE;
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
	 * @return Returns the originOE.
	 */
	public String getOriginOE() {
		return originOE;
	}

	/**
	 * @param originOE The originOE to set.
	 */
	public void setOriginOE(String originOE) {
		this.originOE = originOE;
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
	 * @return Returns the fromScreen.
	 */
	public String getFromScreen() {
		return fromScreen;
	}

	public String getActionStatusFlag() {
		return actionStatusFlag;
	}

	public void setActionStatusFlag(String actionStatusFlag) {
		this.actionStatusFlag = actionStatusFlag;
	}

	/**
	 * @param fromScreen The fromScreen to set.
	 */
	public void setFromScreen(String fromScreen) {
		this.fromScreen = fromScreen;
	}

	/**
	 * @return Returns the damageCode.
	 */
	public String[] getDamageCode() {
		return damageCode;
	}

	/**
	 * @param damageCode The damageCode to set.
	 */
	public void setDamageCode(String[] damageCode) {
		this.damageCode = damageCode;
	}

	/**
	 * @return Returns the damageRemarks.
	 */
	public String[] getDamageRemarks() {
		return damageRemarks;
	}

	/**
	 * @param damageRemarks The damageRemarks to set.
	 */
	public void setDamageRemarks(String[] damageRemarks) {
		this.damageRemarks = damageRemarks;
	}

	/**
	 * @return Returns the postalAdmin.
	 */
	public String getPostalAdmin() {
		return postalAdmin;
	}

	/**
	 * @param postalAdmin The postalAdmin to set.
	 */
	public void setPostalAdmin(String postalAdmin) {
		this.postalAdmin = postalAdmin;
	}

	/**
	 * @return Returns the returnCheckAll.
	 */
	public String getReturnCheckAll() {
		return returnCheckAll;
	}

	/**
	 * @param returnCheckAll The returnCheckAll to set.
	 */
	public void setReturnCheckAll(String returnCheckAll) {
		this.returnCheckAll = returnCheckAll;
	}
	
	/**
	 * @return Returns the returnSubCheck.
	 */
	public String[] getReturnSubCheck() {
		return returnSubCheck;
	}

	/**
	 * @param returnSubCheck The returnSubCheck to set.
	 */
	public void setReturnSubCheck(String[] returnSubCheck) {
		this.returnSubCheck = returnSubCheck;
	}

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

	/**
	 * @return the dmgNOB
	 */
	public int getDmgNOB() {
		return dmgNOB;
	}

	/**
	 * @param dmgNOB the dmgNOB to set
	 */
	public void setDmgNOB(int dmgNOB) {
		this.dmgNOB = dmgNOB;
	}

	/**
	 * @return the dmgweight
	 */
	public double getDmgWeight() {
		return dmgWeight;
	}

	/**
	 * @param dmgweight the dmgweight to set
	 */
	public void setDmgWeight(double dmgWeight) {
		this.dmgWeight = dmgWeight;
	}
	
	
}
