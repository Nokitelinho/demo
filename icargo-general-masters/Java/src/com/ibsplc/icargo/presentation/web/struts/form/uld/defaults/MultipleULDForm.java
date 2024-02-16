/*
 * MultipleULDForm.java Created on Aug 1, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.struts.form.uld.defaults;

import com.ibsplc.icargo.framework.model.ScreenModel;


/**
 * @author A-2001
 *
 */
public class MultipleULDForm extends ScreenModel {
    
	private static final String BUNDLE = "maintainuldResources";
	
	private String bundle;    

	private static final String PRODUCT = "uld";
	
	private static final String SUBPRODUCT = "defaults";
	
	private static final String SCREENID = "uld.defaults.maintainuld";

	 private String uldType;
   
	 private String ownerAirlineCode;
	 
	 private String uldOwnerCode;
	 private String startNo;
	  
	 private String noOfUnits;
	 
	 private String[] uldNos;
	 
	 private String[] selectedRows;
	 
	 private String onloadStatusFlag;

	 private String structuralFlag;
	 
	 private String[] uldOpFlag;
	 public String getUldOwnerCode() {
			return uldOwnerCode;
	}
	public void setUldOwnerCode(String uldOwnerCode) {
		this.uldOwnerCode = uldOwnerCode;
	}
	
	/**
	 * @return Returns the structuralFlag.
	 */
	public String getStructuralFlag() {
		return structuralFlag;
	}

	/**
	 * @param structuralFlag The structuralFlag to set.
	 */
	public void setStructuralFlag(String structuralFlag) {
		this.structuralFlag = structuralFlag;
	}

	/**
	 * @return Returns the noOfUnits.
	 */
	public String getNoOfUnits() {
		return noOfUnits;
	}

	/**
	 * @param noOfUnits The noOfUnits to set.
	 */
	public void setNoOfUnits(String noOfUnits) {
		this.noOfUnits = noOfUnits;
	}

	
	/**
	 * @return Returns the selectedRows.
	 */
	public String[] getSelectedRows() {
		return selectedRows;
	}

	/**
	 * @param selectedRows The selectedRows to set.
	 */
	public void setSelectedRows(String[] selectedRows) {
		this.selectedRows = selectedRows;
	}

	/**
	 * @return Returns the startNo.
	 */
	public String getStartNo() {
		return startNo;
	}

	/**
	 * @param startNo The startNo to set.
	 */
	public void setStartNo(String startNo) {
		this.startNo = startNo;
	}

	/**
	 * @return Returns the uldType.
	 */
	public String getUldType() {
		return uldType;
	}

	/**
	 * @param uldType The uldType to set.
	 */
	public void setUldType(String uldType) {
		this.uldType = uldType;
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
	 * @return Returns the uldNos.
	 */
	public String[] getUldNos() {
		return uldNos;
	}

	/**
	 * @param uldNos The uldNos to set.
	 */
	public void setUldNos(String[] uldNos) {
		this.uldNos = uldNos;
	}

	/**
	 * @return Returns the ownerAirlineCode.
	 */
	public String getOwnerAirlineCode() {
		return ownerAirlineCode;
	}

	/**
	 * @param ownerAirlineCode The ownerAirlineCode to set.
	 */
	public void setOwnerAirlineCode(String ownerAirlineCode) {
		this.ownerAirlineCode = ownerAirlineCode;
	}

	/**
	 * @return Returns the onloadStatusFlag.
	 */
	public String getOnloadStatusFlag() {
		return onloadStatusFlag;
	}

	/**
	 * @param onloadStatusFlag The onloadStatusFlag to set.
	 */
	public void setOnloadStatusFlag(String onloadStatusFlag) {
		this.onloadStatusFlag = onloadStatusFlag;
	}

	/**
	 * 
	 * @return
	 */
	public String[] getUldOpFlag() {
		return uldOpFlag;
	}

	/**
	 * 
	 * @param uldOpFlag
	 */
	public void setUldOpFlag(String[] uldOpFlag) {
		this.uldOpFlag = uldOpFlag;
	}

	

	  
    
 }
