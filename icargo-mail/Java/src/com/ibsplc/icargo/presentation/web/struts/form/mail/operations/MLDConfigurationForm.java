/*
 * MLDConfigurationForm.java Created on DEC 15, 2015
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.presentation.web.struts.form.mail.operations;

import com.ibsplc.icargo.framework.model.ScreenModel;
/**
 * 
 * @author A-5526
 *
 */
public class MLDConfigurationForm extends ScreenModel {

	private static final String SCREEN_ID = "mailtracking.defaults.mldconfiguration";
	private static final String PRODUCT_NAME = "mail";
	private static final String SUBPRODUCT_NAME = "operations";
	private static final String BUNDLE = "MLDConfigurationResources";

	private String carrier;

    private String airport;
    
	private String[] carrierCode;

    private String[] airportCode;

    private String[] allocatedRequired;

	private String[] receivedRequired;

	private String[] upliftedRequired;

	private String[] hndRequired;

	private String[] deliveredRequired;
	private String rowId;
	private String isAllocatedCheck;
	private String isUpliftedCheck;
	private String isdlvCheck;
	private String isReceivedCheck;
	private String isHndcheck;
	
	
	//Added for CRQ ICRD-135130 by A-8061 starts
	
	  private String mldversion;
	  private String[] stagedRequired;
	  private String[] nestedRequired;
	  private String[] receivedFromFightRequired;
	  private String[] transferredFromOALRequired;
	  private String[] receivedFromOALRequired;
	  private String[] returnedRequired;
	   private String isStagedCheck;
	   private String isNestedCheck;
	   private String isReceivedFromFightCheck;
	   private String isTransferredFromOALcheck;
	   private String isReceivedFromOALcheck;
	   private String isReturnedCheck;
	   private String listFlag;
	
	

	/**
	 *  Method to get mldversion
	 * @return
	 */
	public String getMldversion() {
		return mldversion;
	}

/**
 *  Method to set mldversion
 * @param mldversion
 */
	public void setMldversion(String mldversion) {
		this.mldversion = mldversion;
	}

/**
 *  Method to get stagedRequired
 * @return
 */
	public String[] getStagedRequired() {
		return stagedRequired;
	}

/**
 *  Method to set  stagedRequired
 * @param stagedRequired
 */
	public void setStagedRequired(String[] stagedRequired) {
		this.stagedRequired = stagedRequired;
	}

/**
 *  Method to get nestedRequired
 * @return
 */
	public String[] getNestedRequired() {
		return nestedRequired;
	}

/**
 *  Method to set nestedRequired
 * @param nestedRequired
 */
	public void setNestedRequired(String[] nestedRequired) {
		this.nestedRequired = nestedRequired;
	}

/**
 *  Method to get receivedFromFightRequired
 * @return
 */
	public String[] getReceivedFromFightRequired() {
		return receivedFromFightRequired;
	}

/**
 *  Method to set receivedFromFightRequired
 * @param receivedFromFightRequired
 */
	public void setReceivedFromFightRequired(String[] receivedFromFightRequired) {
		this.receivedFromFightRequired = receivedFromFightRequired;
	}

/**
 *  Method to get transferredFromOALRequired
 * @return
 */
	public String[] getTransferredFromOALRequired() {
		return transferredFromOALRequired;
	}

/**
 *  Method to set transferredFromOALRequired
 * @param transferredFromOALRequired
 */
	public void setTransferredFromOALRequired(String[] transferredFromOALRequired) {
		this.transferredFromOALRequired = transferredFromOALRequired;
	}

/**
 *  Method to get receivedFromOALRequired
 * @return
 */
	public String[] getReceivedFromOALRequired() {
		return receivedFromOALRequired;
	}

/**
 *  Method to set receivedFromOALRequired
 * @param receivedFromOALRequired
 */
	public void setReceivedFromOALRequired(String[] receivedFromOALRequired) {
		this.receivedFromOALRequired = receivedFromOALRequired;
	}

/**
 *  Method to get returnedRequired
 * @return
 */
	public String[] getReturnedRequired() {
		return returnedRequired;
	}

/**
 *  Method to set returnedRequired
 * @param returnedRequired
 */
	public void setReturnedRequired(String[] returnedRequired) {
		this.returnedRequired = returnedRequired;
	}

/**
 *  Method to get isStagedCheck
 * @return
 */
	public String getIsStagedCheck() {
		return isStagedCheck;
	}

/**
 *  Method to set  isStagedCheck
 * @param isStagedCheck
 */
	public void setIsStagedCheck(String isStagedCheck) {
		this.isStagedCheck = isStagedCheck;
	}

/**
 *  Method to get isNestedCheck
 * @return
 */
	public String getIsNestedCheck() {
		return isNestedCheck;
	}

/**
 *  Method to set  isNestedCheck
 * @param isNestedCheck
 */
	public void setIsNestedCheck(String isNestedCheck) {
		this.isNestedCheck = isNestedCheck;
	}

/**
 *  Method to get isReceivedFromFightCheck
 * @return
 */
	public String getIsReceivedFromFightCheck() {
		return isReceivedFromFightCheck;
	}

/**
 *  Method to set isReceivedFromFightCheck
 * @param isReceivedFromFightCheck
 */
	public void setIsReceivedFromFightCheck(String isReceivedFromFightCheck) {
		this.isReceivedFromFightCheck = isReceivedFromFightCheck;
	}

/**
 *  Method to get isTransferredFromOALcheck
 * @return
 */
	public String getIsTransferredFromOALcheck() {
		return isTransferredFromOALcheck;
	}

/**
 *  Method to set  isTransferredFromOALcheck
 * @param isTransferredFromOALcheck
 */
	public void setIsTransferredFromOALcheck(String isTransferredFromOALcheck) {
		this.isTransferredFromOALcheck = isTransferredFromOALcheck;
	}

/**
 *  Method to get isReceivedFromOALcheck
 * @return
 */
	public String getIsReceivedFromOALcheck() {
		return isReceivedFromOALcheck;
	}

/**
 *  Method to set  isReceivedFromOALcheck
 * @param isReceivedFromOALcheck
 */
	public void setIsReceivedFromOALcheck(String isReceivedFromOALcheck) {
		this.isReceivedFromOALcheck = isReceivedFromOALcheck;
	}

/**
 *  Method to get isReturnedCheck
 * @return
 */
	public String getIsReturnedCheck() {
		return isReturnedCheck;
	}

	/**
	 * 
	 * @param isReturnedCheck
	 */
	public void setIsReturnedCheck(String isReturnedCheck) {
		this.isReturnedCheck = isReturnedCheck;
	}

	//Added for CRQ ICRD-135130 by A-8061 end

	/**
	 * Method to get isAllocatedCheck
	 * @return
	 */
	public String getIsAllocatedCheck() {
		return isAllocatedCheck;
	}

	
/**
 * Method to set isAllocatedCheck
 * @param isAllocatedCheck
 */
	public void setIsAllocatedCheck(String isAllocatedCheck) {
		this.isAllocatedCheck = isAllocatedCheck;
	}


/**
 * Method to get isUpliftedCheck
 * @return
 */
	public String getIsUpliftedCheck() {
		return isUpliftedCheck;
	}

	/**
	 * Method to set isUpliftedCheck
	 * @param isUpliftedCheck
	 */

	public void setIsUpliftedCheck(String isUpliftedCheck) {
		this.isUpliftedCheck = isUpliftedCheck;
	}

	/**
	 * Method to get isdlvCheck
	 * @return
	 */

	public String getIsdlvCheck() {
		return isdlvCheck;
	}


	/**
	 * Method to set isdlvCheck
	 * @param isdlvCheck
	 */
	public void setIsdlvCheck(String isdlvCheck) {
		this.isdlvCheck = isdlvCheck;
	}


/**
 * Method to get isReceivedCheck
 * @return
 */
	public String getIsReceivedCheck() {
		return isReceivedCheck;
	}

/**
 * Method to set isReceivedCheck
 * @param isReceivedCheck
 */

	public void setIsReceivedCheck(String isReceivedCheck) {
		this.isReceivedCheck = isReceivedCheck;
	}

/**
 * Method to get isHndcheck
 * @return
 */

	public String getIsHndcheck() {
		return isHndcheck;
	}

/**
 * Method to set isHndcheck
 * @param isHndcheck
 */

	public void setIsHndcheck(String isHndcheck) {
		this.isHndcheck = isHndcheck;
	}


/**
 *  Method to get rowId
 * @return
 */
	public String getRowId() {
		return rowId;
	}


/**
 * Method to set rowId
 * @param rowId
 */
	public void setRowId(String rowId) {
		this.rowId = rowId;
	}

/**
 * Method to get operationFlags
 * @return
 */

	public String[] getOperationFlag() {
		return operationFlag;
	}

/**
 * Method to set operationFlags
 * @param operationFlag
 */

	public void setOperationFlag(String[] operationFlag) {
		this.operationFlag = operationFlag;
	}

	private String[] operationFlag=null;

	/**
	 * @return SCREEN_ID - String
	 */
	public String getScreenId() {
		return SCREEN_ID;
	}


/**
 * Method to get carrier
 * @return
 */
	public String getCarrier() {
		return carrier;
	}

/**
 * Method to set carrier
 * @param carrier
 */

	public void setCarrier(String carrier) {
		this.carrier = carrier;
	}

/**
 * Method to get airport
 * @return
 */

	public String getAirport() {
		return airport;
	}


/**
 * Method to set airport
 * @param airport
 */
	public void setAirport(String airport) {
		this.airport = airport;
	}


/**
 * Method to get carrierCode
 * @return
 */
	public String[] getCarrierCode() {
		return carrierCode;
	}


/**
 * Method to set carrierCode
 * @param carrierCode
 */
	public void setCarrierCode(String[] carrierCode) {
		this.carrierCode = carrierCode;
	}


/**
 * Method to get airportCodes
 * @return
 */
	public String[] getAirportCode() {
		return airportCode;
	}

/**
 * Method to set airportCodes
 * @param airportCode
 */

	public void setAirportCode(String[] airportCode) {
		this.airportCode = airportCode;
	}

/**
 * Method to get allocatedRequired flags
 * @return
 */

	public String[] getAllocatedRequired() {
		return allocatedRequired;
	}

/**
 * Method to set allocatedRequired flags
 * @param allocatedRequired
 */

	public void setAllocatedRequired(String[] allocatedRequired) {
		this.allocatedRequired = allocatedRequired;
	}

/**
 * Method to get receivedRequired flags
 * @return
 */

	public String[] getReceivedRequired() {
		return receivedRequired;
	}

/**
 * Method to set receivedRequired flags
 * @param receivedRequired
 */

	public void setReceivedRequired(String[] receivedRequired) {
		this.receivedRequired = receivedRequired;
	}

/**
 * Method to get upliftedRequired flags
 * @return
 */

	public String[] getUpliftedRequired() {
		return upliftedRequired;
	}


/**
 * Method to set upliftedRequired flags
 * @param upliftedRequired
 */
	public void setUpliftedRequired(String[] upliftedRequired) {
		this.upliftedRequired = upliftedRequired;
	}


/**
 * Method to get hndRequired flags
 * @return
 */
	public String[] getHndRequired() {
		return hndRequired;
	}

/**
 * Method to set hndRequired flags
 * @param hndRequired
 */

	public void setHndRequired(String[] hndRequired) {
		this.hndRequired = hndRequired;
	}


/**
 * Method to get deliveredRequired flags
 * @return
 */
	public String[] getDeliveredRequired() {
		return deliveredRequired;
	}


/**
 * Method to set deliveredRequired flags
 * @param deliveredRequired
 */
	public void setDeliveredRequired(String[] deliveredRequired) {
		this.deliveredRequired = deliveredRequired;
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


	public String getListFlag() {
		return listFlag;
	}


	public void setListFlag(String listFlag) {
		this.listFlag = listFlag;
	}

}
