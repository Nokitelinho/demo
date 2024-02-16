/**
 * RecordUldMovementForm.java Created on jan 24, 2006
 *
 * Copyright 2006 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of
 *  IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.misc;


import com.ibsplc.icargo.framework.model.ScreenModel;
/**
 *
 * @author A-1936
 *
 */
public class RecordULDMovementForm extends ScreenModel {


 	 private static final String BUNDLE = "recordULDMovementResources";

 	private static final String PRODUCT = "uld";

	 private String uldNumbers;

	 private String bundle;

	 private String screenName;

	 private String pointOfLadings;

     private String pointOfUnLadings;

     private String pageurl;

     private static final String SUBPRODUCT = "defaults";

     private static final String SCREENID = "uld.defaults.misc.recorduldmovement";

     private boolean updateCurrentStation;

     private String currentStation="";

     private String messageStatus="";

     private String remarks="";

     private String[]  uldNumber;

     private String[] pointOfLading;

     private String[] pointOfUnLading;

     private String[] carrierCode;

     private String[] flightNumber;

     private String[] flightDate;

     private String[] content;

     private String[] dummyMovement;

     private String[] checkForDelete;

     private String[] endTime;

     private String[] flightDateString;

     private String[] hiddenCount;

     private String[] chkValue;

     private String[] checkForUld;

     private String flagForCheck;

     private String flagForUldDiscrepancy;

     private String loadStatus;

     private String discrepancyStatus;

     private String discrepancyDate;

     private String discrepancyCode;

     private String[] hiddenOpFlag;

     private String[] hiddenOpFlagForULD;

     //Added by A-4421 
     
     private String dummyCheckedIndex;
     
     private String errorFlag="N";
     //Added by A-3415 for ICRD-114051
     private String overrideError;
	/**
	 * @return Returns the hiddenOpFlagForULD.
	 */
	public String[] getHiddenOpFlagForULD() {
		return hiddenOpFlagForULD;
	}
	/**
	 * @param hiddenOpFlagForULD The hiddenOpFlagForULD to set.
	 */
	public void setHiddenOpFlagForULD(String[] hiddenOpFlagForULD) {
		this.hiddenOpFlagForULD = hiddenOpFlagForULD;
	}
	/**
	 * @return Returns the hiddenOpFlag.
	 */
	public String[] getHiddenOpFlag() {
		return hiddenOpFlag;
	}
	/**
	 * @param hiddenOpFlag The hiddenOpFlag to set.
	 */
	public void setHiddenOpFlag(String[] hiddenOpFlag) {
		this.hiddenOpFlag = hiddenOpFlag;
	}

     /**
	 * @return Returns the screenName.
	 */
	public  String getScreenName() {
		return screenName;
	}

	/**
	 * @param screenName The screenName to set.
	 */
	public  void setScreenName(String screenName) {
		this.screenName = screenName;
	}

	/**
	 * @return Returns the loadStatus.
	 */
	public String getLoadStatus() {
		return loadStatus;
	}

	/**
	 * @param loadStatus The loadStatus to set.
	 */
	public void setLoadStatus(String loadStatus) {
		this.loadStatus = loadStatus;
	}

	/**
	 * @return Returns the flag.
	 */
	public String getFlagForCheck() {
		return flagForCheck;
	}

	/**
	 * @param flagForCheck
	 */
	public void setFlagForCheck(String flagForCheck) {
		this.flagForCheck = flagForCheck;
	}

	/**
	 * @return Returns the uldNumbers.
	 */
	public String getUldNumbers() {
		return uldNumbers;
	}

	/**
	 * @param uldNumbers The uldNumbers to set.
	 */
	public void setUldNumbers(String uldNumbers) {
		this.uldNumbers = uldNumbers;
	}

	/**
	 * @return Returns the checkForUld.
	 */
	public String[] getCheckForUld() {
		return checkForUld;
	}

	/**
	 * @param checkForUld The checkForUld to set.
	 */
	public void setCheckForUld(String[] checkForUld) {
		this.checkForUld = checkForUld;
	}

	/**
	 * @return Returns the uldNumber.
	 */
	public String[] getUldNumber() {
		return uldNumber;
	}

	/**
	 * @param uldNumber The uldNumber to set.
	 */
	public void setUldNumber(String[] uldNumber) {
		this.uldNumber = uldNumber;
	}
    /**
     *
     * @return Rreturns the HiddenCount
     */

	public String[] getHiddenCount() {
		return hiddenCount;
	}
     /**
      *
      * @param hiddenCount Sets the HiddenCount
      */
	public void setHiddenCount(String[] hiddenCount) {
		this.hiddenCount = hiddenCount;
	}
	/**
      *
      * @return Sets the FlightDateString
      */
     public String[] getFlightDateString() {
		return flightDateString;
	}
    /**
     *
     * @param flightDateString  Returns the FlightDateString
     */
	public void setFlightDateString(String[] flightDateString) {
		this.flightDateString = flightDateString;
	}

	/**
      *
      * @return Returns the EndTime
      */
     public String[] getEndTime() {
		return endTime;
	}

     /**
      *
      * @param endTime Sets the EndTime
      */
	public void setEndTime(String[] endTime) {
		this.endTime = endTime;
	}

	/**
      *
      * @return  Returns the CheckForDelete
      */
     public String[] getCheckForDelete() {
		return checkForDelete;
	}
     /**
      *
      * @param checkForDelete Sets the CheckForDelete
      */
	public void setCheckForDelete(String[] checkForDelete) {
		this.checkForDelete = checkForDelete;
	}
	/**
      *
      * @return Returns the CarrierCode
      */
     public String[] getCarrierCode() {
		return carrierCode;
	 }
    /**
     *
     * @param carrierCode The CarrierCode to be set
     */
	public void setCarrierCode(String[] carrierCode) {
		this.carrierCode = carrierCode;
	}
    /**
     *
     * @return The content to be set
     */
	public String[] getContent() {
		return content;
	}
    /**
     *
     * @param content Returns the content
     */
	public void setContent(String[] content) {
		this.content = content;
	}
    /**
     *
     * @return Returns the Currentstation
     */
	public String getCurrentStation() {
		return currentStation;
	}
    /**
     *
     * @param currentStation The currentStation to be set
     */
	public void setCurrentStation(String currentStation) {
		this.currentStation = currentStation;
	}
    /**
     *
     * @return Returns the FlightDate
     */
	public String[] getFlightDate() {
		return flightDate;
	}
    /**
     *
     * @param flightDate The FlightDate to be set
     */
	public void setFlightDate(String[] flightDate) {
		this.flightDate = flightDate;
	}
    /**
     *
     * @return  Returns the FlightNumber
     */
	public String[] getFlightNumber() {
		return flightNumber;
	}
    /**
     *
     * @param flightNumber The flight number to be set
     */
	public void setFlightNumber(String[] flightNumber) {
		this.flightNumber = flightNumber;
	}

    /**
     *
     * @return Returns  the PointOfLoading
     */
	public String[] getPointOfLading() {
		return pointOfLading;
	}
    /**
     *
     * @param pointOfLading  The PointOfLoading to be set
     */
	public void setPointOfLading(String[] pointOfLading) {
		this.pointOfLading = pointOfLading;
	}
    /**
     *
     * @return  Returns the pointOfUnloading
     */
	public String[] getPointOfUnLading() {
		return pointOfUnLading;
	}
    /**
      *
      * @param pointOfUnLading  The PointOfUnLoading  to be set
      */
	public void setPointOfUnLading(String[] pointOfUnLading) {
		this.pointOfUnLading = pointOfUnLading;
	}
    /**
     *
     * @return Returns the Remarks
     */
	public String getRemarks() {
		return remarks;
	}
    /**
     *
     * @param remarks The Remarks to be set
     */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

    /**
     *
     * @return Returns the updateCurrentStation
     */
	public boolean getUpdateCurrentStation() {
		return updateCurrentStation;
	}
    /**
     *
     * @param updateCurrentStation
     */
	public void setUpdateCurrentStation(boolean updateCurrentStation){
		  this.updateCurrentStation = updateCurrentStation;
	}


	/**
	 *
	 * @param bundle
	 */
  	public void setBundle(String bundle) {
  		this.bundle=bundle;
  	}
     /**
  	 * @return Returns the bundle.
  	 */
  	public String getBundle() {
  		return BUNDLE;
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
       *
       * @param dummyMovement The dummymovement to be set
       */
     public void setDummyMovement(String[] dummyMovement){
    	 this.dummyMovement=dummyMovement;
     }


     /**
      *
      * @return Returns the DummyMovement
      */
     public String[] getDummyMovement(){
    	 return dummyMovement;
     }

	/**
	 * @return Returns the chkValue.
	 */
	public String[] getChkValue() {
		return chkValue;
	}

	/**
	 * @param chkValue The chkValue to set.
	 */
	public void setChkValue(String[] chkValue) {
		this.chkValue = chkValue;
	}

	/**
	 * @return Returns the discrepancyStatus.
	 */
	public String getDiscrepancyStatus() {
		return discrepancyStatus;
	}

	/**
	 * @param discrepancyStatus The discrepancyStatus to set.
	 */
	public void setDiscrepancyStatus(String discrepancyStatus) {
		this.discrepancyStatus = discrepancyStatus;
	}

	/**
	 * @return Returns the pointOfLadings.
	 */
	public String getPointOfLadings() {
		return pointOfLadings;
	}

	/**
	 * @param pointOfLadings The pointOfLadings to set.
	 */
	public void setPointOfLadings(String pointOfLadings) {
		this.pointOfLadings = pointOfLadings;
	}

	/**
	 * @return Returns the pointOfUnLadings.
	 */
	public String getPointOfUnLadings() {
		return pointOfUnLadings;
	}

	/**
	 * @param pointOfUnLadings The pointOfUnLadings to set.
	 */
	public void setPointOfUnLadings(String pointOfUnLadings) {
		this.pointOfUnLadings = pointOfUnLadings;
	}

	/**
	 * @return Returns the flagForUldDiscrepancy.
	 */
	public String getFlagForUldDiscrepancy() {
		return flagForUldDiscrepancy;
	}

	/**
	 * @param flagForUldDiscrepancy The flagForUldDiscrepancy to set.
	 */
	public void setFlagForUldDiscrepancy(String flagForUldDiscrepancy) {
		this.flagForUldDiscrepancy = flagForUldDiscrepancy;
	}

	/**
	 * @return Returns the discrepancyCode.
	 */
	public String getDiscrepancyCode() {
		return discrepancyCode;
	}

	/**
	 * @param discrepancyCode The discrepancyCode to set.
	 */
	public void setDiscrepancyCode(String discrepancyCode) {
		this.discrepancyCode = discrepancyCode;
	}

	/**
	 * @return Returns the discrepancyDate.
	 */
	public String getDiscrepancyDate() {
		return discrepancyDate;
	}

	/**
	 * @param discrepancyDate The discrepancyDate to set.
	 */
	public void setDiscrepancyDate(String discrepancyDate) {
		this.discrepancyDate = discrepancyDate;
	}

	/**
	 * @return Returns the pageurl.
	 */
	public String getPageurl() {
		return pageurl;
	}

	/**
	 * @param pageurl The pageurl to set.
	 */
	public void setPageurl(String pageurl) {
		this.pageurl = pageurl;
	}

	/**
	 * @return Returns the messageStatus.
	 */
	public String getMessageStatus() {
		return messageStatus;
	}

	/**
	 * @param messageStatus The messageStatus to set.
	 */
	public void setMessageStatus(String messageStatus) {
		this.messageStatus = messageStatus;
	}
	/**
	 * @return Returns the errorFlag.
	 */
	public String getDummyCheckedIndex() {
		return dummyCheckedIndex;
	}

	/**
	 * @param dummyCheckedIndex The dummyCheckedIndex to set.
	 */
	public void setDummyCheckedIndex(String dummyCheckedIndex) {
		this.dummyCheckedIndex = dummyCheckedIndex;
	}
	/**
	 * @return Returns the errorFlag.
	 */
	public String getErrorFlag() {
		return errorFlag;
	}

	/**
	 * @param errorFlag The errorFlag to set.
	 */
	public void setErrorFlag(String errorFlag) {
		this.errorFlag = errorFlag;
	}
	public String getOverrideError() {
		return overrideError;
	}
	public void setOverrideError(String overrideError) {
		this.overrideError = overrideError;
	}

}
