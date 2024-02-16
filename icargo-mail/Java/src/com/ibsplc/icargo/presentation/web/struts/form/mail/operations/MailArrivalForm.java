/*
 * MailArrivalForm.java Created on Aug 3, 2006
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
public class MailArrivalForm extends ScreenModel {

	private static final String SCREEN_ID = "mailtracking.defaults.mailarrival";
	private static final String PRODUCT_NAME = "mail";
	private static final String SUBPRODUCT_NAME = "operations";
	private static final String BUNDLE = "mailArrivalResources";
	private String[] damageCode;
	private String[] damageRemarks;
	private String damageCheckAll;
	private String[] damageSubCheck;
	private String selectedMailBag;
	private String damageFromScreen;	
	private String flightNumber;
	private String flightCarrierCode;
	private String arrivalDate;
	private String arrivalPort;
	private String[] selectContainer;
	private String duplicateFlightStatus;
	private String initialFocus;
	private String checkFlight;
	private String isContainerValidFlag = "";
	private String mailStatus;
	private String transferCarrier;
	private String arrivalPA;

	/**For Arrival Report*/
	private String printType;
	private int carrierId;
	private long flightSequenceNumber;
	private int legSerialNumber;

   /**
    * For Arrive Mail popup
    */
	private String containerNo;
	private String paBuilt;
	private String containerType;
	private String pol;
	private String remarks;
	
	private String[] selectDespatch;
	private String[] despOprFlag;
	private String[] conDocNo;
	private String[] despatchDate;
	private String[] despatchPA;
	private String[] despatchOOE;
	private String[] despatchDOE;
	private String[] despatchCat;
	private String[] despatchClass;
	private String[] despatchDSN;
	private String[] despatchYear;
	private String[] manifestedBags;
	
	private String fromScreen;
	
	@MeasureAnnotation(mappedValue="manifestedWtMeasure",unitType="MWT")
	private String[] manifestedWt;
	private Measure[] manifestedWtMeasure;
	
	private String[] receivedBags;
	
	@MeasureAnnotation(mappedValue="receivedWtMeasure",unitType="MWT")
	private String[] receivedWt;
	private Measure[] receivedWtMeasure;
	
	private String[] deliveredBags;
	
	@MeasureAnnotation(mappedValue="deliveredWtMeasure",unitType="MWT")
	private String[] deliveredWt;
	private Measure[] deliveredWtMeasure;
	
	private String[] despatchSC;
	
	private String[] selectMailTag;
	private String[] mailOOE;
	private String[] mailDOE;
	private String[] mailCat;
	private String[] mailSC;
	private String[] mailYr;
	private String[] mailDSN;
	private String[] mailRSN;
	private String[] mailHNI;
	private String[] mailRI;
	
	@MeasureAnnotation(mappedValue="mailWtMeasure",unit="weightUnit",unitType="MWT")
	private String[] mailWt;
	private Measure[] mailWtMeasure;
	private String[] weightUnit; //Added by A-7540
	
	private String[] mailScanDate;
	private String[] mailScanTime;
	private String[] mailDamaged;
	private String[] mailReceived;
	private String[] mailDelivered;
	
	private String[] despatchOpFlag;
	private String[] mailOpFlag;
	
	private String hiddenScanDate;
	private String hiddenScanTime;
	
	private String[] contOpFlag;
	private String[] contTransferFlag;
	private String[] uldType;
	private String[] dsnTransferFlag;
	
	private String popupCloseFlag;
	private String suggestContainerValue;
	private String disableFlag;
	private String listFlag="";
	private String operationalStatus="";
	private String[] childContainer;
	private String container="";
	private String chkFlag="";
	
	/**
	 * For BulkContainer popup
	 */
	private String containerNumber;
	private String bulkFromScreen;
	private String selectMainContainer;
	private String containerTypes;
	private String overrideBulkFlag;
	private String selContainer;
	private String showAssignContainer;
	private String childBulkContainer;
	/**
     * For Transfer popup
     */
	private String carrier;
	private String selectToTransfer;
	private String transferFlag;
	private String hiddenMailTag;	

	private String chkboxStatus;
	
	/**
	 * Added By RENO K ABRAHAM FOR SB
	 */
	private String arrivedStatus;
	private String deliveredStatus;
	private String operationFlag;
	
	/**
	 * Added by RENO K ABRAHAM FOR INTACT 
	 */
	private String intact;
	
	private String selectMode;
	
	private String[] paBuiltFlag;
	/**
	 * Added for delivery Validation
	 */
	private String selectCont;
	private String childCont;
	private String selectMainCont;
	private String[] sealNo;  
	private String[] arrivalSealNo;   
	private String[] contReleasedFlag;
	
	private String csgDocNumForRouting;
	private String paCodeForRouting;
	// ADDED FOR BUG 82870 
	private String newRoutingFlag;
	private String attachRouting;
	private String hiddenRoutingAvl;
	private String[] csgDocNum;
	private String[] paCod;
	private String parentContainer;
	private String selectChild;
	private String unsavedDataFlag;
	//Added for CR ICMN-2345
	private String[] operationalFlag;
	
	//Added by A-5153 for BUG_ICRD-90139
	private String warningFlag;
	private String warningOveride;
	private String disableButtonsForTBA;
	//Added by A-5945 for ICRD-104487
	
	@MeasureAnnotation(mappedValue="mailVolumeMeasure",unitType="VOL")//added by A-7540 for ICRD-274933 
	private String[] mailVolume;
	private Measure[] mailVolumeMeasure;
	
	private String density;
	
	private String[] mailCompanyCode;
	
	//Added for ICRD-128804
	private boolean barrowCheck;
	//Added for ICRD-127521
	private String addLinkFlag;
	private String fltNumber;
	private String fltCarrierCode;
	private String arrDate;
	private String flightScanDate;
	private String flightScanTime;
	private String[] selectMail;
	private String newChildCont;
	private String changePopUpFlag;	
	//Added for ICRD-134007
	private String saveSuccessFlag;
	private String deleteFlag;
	//Added for ICRD-205027 starts 
	private String originOE;
	private String destinationOE;
	private String category;
	private String subClass;
	private String year;
	private String dsn;
	private String rsn;
	private String hni;
	private String ri;
	
	@MeasureAnnotation(mappedValue="wgtMeasure",unitType="MWT")
	private String wgt;
	private Measure wgtMeasure;

	private String vol;
	private String[] mailbagId;
	private String mailId;
	//Added for ICRD-205027 ends
	
	 private boolean inValidId;//added by A-7371 for ICRD-224610 
	//Added by A-7794 as part of ICRD-197439
	private String disableButtonsForAirport;
		

	
	//Added as a part of ICRD-197419 by a-7540
	private String[] mailRemarks;
	
	//Added as a part of ICRD-114630 by a-7871 starts
	private String embargoFlag;
	
	private boolean canCheckEmbargo = true;
	
	//Added by A-7540
	private String defWeightUnit;
	
	/**
	 * @return the canCheckEmbargo
	 */
	public boolean isCanCheckEmbargo() {
	    return this.canCheckEmbargo;
	  }
	/**
	 * @param canCheckEmbargo the canCheckEmbargo to set
	 */
	  public void setCanCheckEmbargo(boolean canCheckEmbargo) {
	    this.canCheckEmbargo = canCheckEmbargo;
	  }
	  /**
		 * @return the embargoFlag
		 */
	public String getEmbargoFlag() {
		return embargoFlag;
	}
	/**
	 * @param embargoFlag the embargoFlag to set
	 */
	public void setEmbargoFlag(String embargoFlag) {
		this.embargoFlag = embargoFlag;
	}
	//Added as a part of ICRD-114630 by a-7871 ends
	/**
	 * 	Getter for mailRemarks 
	 *	Added by : a-7540 on 19-Jul-2017
	 * 	Used for :
	 */
	public String[] getMailRemarks() {
		return mailRemarks;
	}
	/**
	 *  @param mailRemarks the mailRemarks to set
	 * 	Setter for mailRemarks 
	 *	Added by : a-7540 on 19-Jul-2017
	 * 	Used for :
	 */
	public void setMailRemarks(String[] mailRemarks) {
		this.mailRemarks = mailRemarks;
	}
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
	 * @return the manifestedWtMeasure
	 */
	public Measure[] getManifestedWtMeasure() {
		return manifestedWtMeasure;
	}
	/**
	 * @param manifestedWtMeasure the manifestedWtMeasure to set
	 */
	public void setManifestedWtMeasure(Measure[] manifestedWtMeasure) {
		this.manifestedWtMeasure = manifestedWtMeasure;
	}
	/**
	 * @return the receivedWtMeasure
	 */
	public Measure[] getReceivedWtMeasure() {
		return receivedWtMeasure;
	}
	/**
	 * @param receivedWtMeasure the receivedWtMeasure to set
	 */
	public void setReceivedWtMeasure(Measure[] receivedWtMeasure) {
		this.receivedWtMeasure = receivedWtMeasure;
	}
	/**
	 * @return the deliveredWtMeasure
	 */
	public Measure[] getDeliveredWtMeasure() {
		return deliveredWtMeasure;
	}
	/**
	 * @param deliveredWtMeasure the deliveredWtMeasure to set
	 */
	public void setDeliveredWtMeasure(Measure[] deliveredWtMeasure) {
		this.deliveredWtMeasure = deliveredWtMeasure;
	}
	/**
	 * @return the operationalFlag
	 */
	public String[] getOperationalFlag() {
		return operationalFlag;
	}
	/**
	 * @param operationalFlag the operationalFlag to set
	 */
	public void setOperationalFlag(String[] operationalFlag) {
		this.operationalFlag = operationalFlag;
	}
	/**
	 * @return Returns the chkFlag.
	 */
	public String getChkFlag() {
		return chkFlag;
	}

	/**
	 * @param chkFlag The chkFlag to set.
	 */
	public void setChkFlag(String chkFlag) {
		this.chkFlag = chkFlag;
	}

	/**
	 * @return Returns the container.
	 */
	public String getContainer() {
		return container;
	}

	/**
	 * @param container The container to set.
	 */
	public void setContainer(String container) {
		this.container = container;
	}

	/**
	 * @return Returns the operationalStatus.
	 */
	public String getOperationalStatus() {
		return operationalStatus;
	}

	/**
	 * @param operationalStatus The operationalStatus to set.
	 */
	public void setOperationalStatus(String operationalStatus) {
		this.operationalStatus = operationalStatus;
	}

	/**
	 * @return Returns the arrivalPort.
	 */
	public String getArrivalPort() {
		return arrivalPort;
	}

	/**
	 * @param arrivalPort The arrivalPort to set.
	 */
	public void setArrivalPort(String arrivalPort) {
		this.arrivalPort = arrivalPort;
	}

	/**
	 * @return Returns the flightCarrierCode.
	 */
	public String getFlightCarrierCode() {
		return flightCarrierCode;
	}

	/**
	 * @param flightCarrierCode The flightCarrierCode to set.
	 */
	public void setFlightCarrierCode(String flightCarrierCode) {
		this.flightCarrierCode = flightCarrierCode;
	}

	/**
	 * @return Returns the flightNumber.
	 */
	public String getFlightNumber() {
		return flightNumber;
	}

	/**
	 * @param flightNumber The flightNumber to set.
	 */
	public void setFlightNumber(String flightNumber) {
		this.flightNumber = flightNumber;
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
	 * @return Returns the arrivalDate.
	 */
	public String getArrivalDate() {
		return this.arrivalDate;
	}

	/**
	 * @param arrivalDate The arrivalDate to set.
	 */
	public void setArrivalDate(String arrivalDate) {
		this.arrivalDate = arrivalDate;
	}

	/**
	 * @return Returns the selectContainer.
	 */
	public String[] getSelectContainer() {
		return this.selectContainer;
	}

	/**
	 * @param selectContainer The selectContainer to set.
	 */
	public void setSelectContainer(String[] selectContainer) {
		this.selectContainer = selectContainer;
	}

	/**
	 * @return Returns the duplicateFlightStatus.
	 */
	public String getDuplicateFlightStatus() {
		return this.duplicateFlightStatus;
	}

	/**
	 * @param duplicateFlightStatus The duplicateFlightStatus to set.
	 */
	public void setDuplicateFlightStatus(String duplicateFlightStatus) {
		this.duplicateFlightStatus = duplicateFlightStatus;
	}

	/**
	 * @return Returns the conDocNo.
	 */
	public String[] getConDocNo() {
		return this.conDocNo;
	}

	/**
	 * @param conDocNo The conDocNo to set.
	 */
	public void setConDocNo(String[] conDocNo) {
		this.conDocNo = conDocNo;
	}

	/**
	 * @return Returns the containerNo.
	 */
	public String getContainerNo() {
		return this.containerNo;
	}

	/**
	 * @param containerNo The containerNo to set.
	 */
	public void setContainerNo(String containerNo) {
		this.containerNo = containerNo;
	}

	/**
	 * @return Returns the despatchClass.
	 */
	public String[] getDespatchClass() {
		return this.despatchClass;
	}

	/**
	 * @param despatchClass The despatchClass to set.
	 */
	public void setDespatchClass(String[] despatchClass) {
		this.despatchClass = despatchClass;
	}

	/**
	 * @return Returns the despatchDate.
	 */
	public String[] getDespatchDate() {
		return this.despatchDate;
	}

	/**
	 * @param despatchDate The despatchDate to set.
	 */
	public void setDespatchDate(String[] despatchDate) {
		this.despatchDate = despatchDate;
	}

	/**
	 * @return Returns the despatchDOE.
	 */
	public String[] getDespatchDOE() {
		return this.despatchDOE;
	}

	/**
	 * @param despatchDOE The despatchDOE to set.
	 */
	public void setDespatchDOE(String[] despatchDOE) {
		this.despatchDOE = despatchDOE;
	}

	/**
	 * @return Returns the despatchDSN.
	 */
	public String[] getDespatchDSN() {
		return this.despatchDSN;
	}

	/**
	 * @param despatchDSN The despatchDSN to set.
	 */
	public void setDespatchDSN(String[] despatchDSN) {
		this.despatchDSN = despatchDSN;
	}

	/**
	 * @return Returns the despatchOOE.
	 */
	public String[] getDespatchOOE() {
		return this.despatchOOE;
	}

	/**
	 * @param despatchOOE The despatchOOE to set.
	 */
	public void setDespatchOOE(String[] despatchOOE) {
		this.despatchOOE = despatchOOE;
	}

	/**
	 * @return Returns the mailCat.
	 */
	public String[] getMailCat() {
		return this.mailCat;
	}

	/**
	 * @param mailCat The mailCat to set.
	 */
	public void setMailCat(String[] mailCat) {
		this.mailCat = mailCat;
	}

	/**
	 * @return Returns the mailDamaged.
	 */
	public String[] getMailDamaged() {
		return this.mailDamaged;
	}

	/**
	 * @param mailDamaged The mailDamaged to set.
	 */
	public void setMailDamaged(String[] mailDamaged) {
		this.mailDamaged = mailDamaged;
	}

	/**
	 * @return Returns the mailDOE.
	 */
	public String[] getMailDOE() {
		return this.mailDOE;
	}

	/**
	 * @param mailDOE The mailDOE to set.
	 */
	public void setMailDOE(String[] mailDOE) {
		this.mailDOE = mailDOE;
	}

	/**
	 * @return Returns the mailDSN.
	 */
	public String[] getMailDSN() {
		return this.mailDSN;
	}

	/**
	 * @param mailDSN The mailDSN to set.
	 */
	public void setMailDSN(String[] mailDSN) {
		this.mailDSN = mailDSN;
	}

	/**
	 * @return Returns the mailHNI.
	 */
	public String[] getMailHNI() {
		return this.mailHNI;
	}

	/**
	 * @param mailHNI The mailHNI to set.
	 */
	public void setMailHNI(String[] mailHNI) {
		this.mailHNI = mailHNI;
	}

	/**
	 * @return Returns the mailOOE.
	 */
	public String[] getMailOOE() {
		return this.mailOOE;
	}

	/**
	 * @param mailOOE The mailOOE to set.
	 */
	public void setMailOOE(String[] mailOOE) {
		this.mailOOE = mailOOE;
	}

	/**
	 * @return Returns the mailRI.
	 */
	public String[] getMailRI() {
		return this.mailRI;
	}

	/**
	 * @param mailRI The mailRI to set.
	 */
	public void setMailRI(String[] mailRI) {
		this.mailRI = mailRI;
	}

	/**
	 * @return Returns the mailRSN.
	 */
	public String[] getMailRSN() {
		return this.mailRSN;
	}

	/**
	 * @param mailRSN The mailRSN to set.
	 */
	public void setMailRSN(String[] mailRSN) {
		this.mailRSN = mailRSN;
	}

	/**
	 * @return Returns the mailSC.
	 */
	public String[] getMailSC() {
		return this.mailSC;
	}

	/**
	 * @param mailSC The mailSC to set.
	 */
	public void setMailSC(String[] mailSC) {
		this.mailSC = mailSC;
	}

	/**
	 * @return Returns the mailScanDate.
	 */
	public String[] getMailScanDate() {
		return this.mailScanDate;
	}

	/**
	 * @param mailScanDate The mailScanDate to set.
	 */
	public void setMailScanDate(String[] mailScanDate) {
		this.mailScanDate = mailScanDate;
	}

	/**
	 * @return Returns the mailWt.
	 */
	public String[] getMailWt() {
		return this.mailWt;
	}

	/**
	 * @param mailWt The mailWt to set.
	 */
	public void setMailWt(String[] mailWt) {
		this.mailWt = mailWt;
	}

	/**
	 * @return Returns the mailYr.
	 */
	public String[] getMailYr() {
		return this.mailYr;
	}

	/**
	 * @param mailYr The mailYr to set.
	 */
	public void setMailYr(String[] mailYr) {
		this.mailYr = mailYr;
	}
	
	/**
	 * @return Returns the paBuilt.
	 */
	public String getPaBuilt() {
		return this.paBuilt;
	}

	/**
	 * @param paBuilt The paBuilt to set.
	 */
	public void setPaBuilt(String paBuilt) {
		this.paBuilt = paBuilt;
	}

	/**
	 * @return Returns the popupCloseFlag.
	 */
	public String getPopupCloseFlag() {
		return this.popupCloseFlag;
	}

	/**
	 * @param popupCloseFlag The popupCloseFlag to set.
	 */
	public void setPopupCloseFlag(String popupCloseFlag) {
		this.popupCloseFlag = popupCloseFlag;
	}

	/**
	 * @return Returns the remarks.
	 */
	public String getRemarks() {
		return this.remarks;
	}

	/**
	 * @param remarks The remarks to set.
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	/**
	 * @return Returns the selectDespatch.
	 */
	public String[] getSelectDespatch() {
		return this.selectDespatch;
	}

	/**
	 * @param selectDespatch The selectDespatch to set.
	 */
	public void setSelectDespatch(String[] selectDespatch) {
		this.selectDespatch = selectDespatch;
	}

	/**
	 * @return Returns the selectMailTag.
	 */
	public String[] getSelectMailTag() {
		return this.selectMailTag;
	}

	/**
	 * @param selectMailTag The selectMailTag to set.
	 */
	public void setSelectMailTag(String[] selectMailTag) {
		this.selectMailTag = selectMailTag;
	}

	/**
	 * @return Returns the despatchYear.
	 */
	public String[] getDespatchYear() {
		return this.despatchYear;
	}

	/**
	 * @param despatchYear The despatchYear to set.
	 */
	public void setDespatchYear(String[] despatchYear) {
		this.despatchYear = despatchYear;
	}

	/**
	 * @return Returns the initialFocus.
	 */
	public String getInitialFocus() {
		return this.initialFocus;
	}

	/**
	 * @param initialFocus The initialFocus to set.
	 */
	public void setInitialFocus(String initialFocus) {
		this.initialFocus = initialFocus;
	}

	/**
	 * @return Returns the despatchCat.
	 */
	public String[] getDespatchCat() {
		return this.despatchCat;
	}

	/**
	 * @param despatchCat The despatchCat to set.
	 */
	public void setDespatchCat(String[] despatchCat) {
		this.despatchCat = despatchCat;
	}

	/**
	 * @return Returns the despatchPA.
	 */
	public String[] getDespatchPA() {
		return this.despatchPA;
	}

	/**
	 * @param despatchPA The despatchPA to set.
	 */
	public void setDespatchPA(String[] despatchPA) {
		this.despatchPA = despatchPA;
	}

	/**
	 * @return Returns the deliveredBags.
	 */
	public String[] getDeliveredBags() {
		return this.deliveredBags;
	}

	/**
	 * @param deliveredBags The deliveredBags to set.
	 */
	public void setDeliveredBags(String[] deliveredBags) {
		this.deliveredBags = deliveredBags;
	}

	/**
	 * @return Returns the deliveredWt.
	 */
	public String[] getDeliveredWt() {
		return this.deliveredWt;
	}

	/**
	 * @param deliveredWt The deliveredWt to set.
	 */
	public void setDeliveredWt(String[] deliveredWt) {
		this.deliveredWt = deliveredWt;
	}

	/**
	 * @return Returns the mailDelivered.
	 */
	public String[] getMailDelivered() {
		return this.mailDelivered;
	}

	/**
	 * @param mailDelivered The mailDelivered to set.
	 */
	public void setMailDelivered(String[] mailDelivered) {
		this.mailDelivered = mailDelivered;
	}

	/**
	 * @return Returns the mailReceived.
	 */
	public String[] getMailReceived() {
		return this.mailReceived;
	}

	/**
	 * @param mailReceived The mailReceived to set.
	 */
	public void setMailReceived(String[] mailReceived) {
		this.mailReceived = mailReceived;
	}

	/**
	 * @return Returns the manifestedBags.
	 */
	public String[] getManifestedBags() {
		return this.manifestedBags;
	}

	/**
	 * @param manifestedBags The manifestedBags to set.
	 */
	public void setManifestedBags(String[] manifestedBags) {
		this.manifestedBags = manifestedBags;
	}

	/**
	 * @return Returns the manifestedWt.
	 */
	public String[] getManifestedWt() {
		return this.manifestedWt;
	}

	/**
	 * @param manifestedWt The manifestedWt to set.
	 */
	public void setManifestedWt(String[] manifestedWt) {
		this.manifestedWt = manifestedWt;
	}

	/**
	 * @return Returns the receivedBags.
	 */
	public String[] getReceivedBags() {
		return this.receivedBags;
	}

	/**
	 * @param receivedBags The receivedBags to set.
	 */
	public void setReceivedBags(String[] receivedBags) {
		this.receivedBags = receivedBags;
	}

	/**
	 * @return Returns the receivedWt.
	 */
	public String[] getReceivedWt() {
		return this.receivedWt;
	}

	/**
	 * @param receivedWt The receivedWt to set.
	 */
	public void setReceivedWt(String[] receivedWt) {
		this.receivedWt = receivedWt;
	}

	/**
	 * @return Returns the suggestContainerValue.
	 */
	public String getSuggestContainerValue() {
		return this.suggestContainerValue;
	}

	/**
	 * @param suggestContainerValue The suggestContainerValue to set.
	 */
	public void setSuggestContainerValue(String suggestContainerValue) {
		this.suggestContainerValue = suggestContainerValue;
	}

	/**
	 * @return Returns the disableFlag.
	 */
	public String getDisableFlag() {
		return this.disableFlag;
	}

	/**
	 * @param disableFlag The disableFlag to set.
	 */
	public void setDisableFlag(String disableFlag) {
		this.disableFlag = disableFlag;
	}

	/**
	 * @return Returns the pol.
	 */
	public String getPol() {
		return this.pol;
	}

	/**
	 * @param pol The pol to set.
	 */
	public void setPol(String pol) {
		this.pol = pol;
	}

	/**
	 * @return Returns the containerType.
	 */
	public String getContainerType() {
		return this.containerType;
	}

	/**
	 * @param containerType The containerType to set.
	 */
	public void setContainerType(String containerType) {
		this.containerType = containerType;
	}

	/**
	 * @return Returns the checkFlight.
	 */
	public String getCheckFlight() {
		return this.checkFlight;
	}

	/**
	 * @param checkFlight The checkFlight to set.
	 */
	public void setCheckFlight(String checkFlight) {
		this.checkFlight = checkFlight;
	}

	/**
	 * @return Returns the listFlag.
	 */
	public String getListFlag() {
		return listFlag;
	}

	/**
	 * @param listFlag The listFlag to set.
	 */
	public void setListFlag(String listFlag) {
		this.listFlag = listFlag;
	}

	/**
	 * @return Returns the despatchSC.
	 */
	public String[] getDespatchSC() {
		return despatchSC;
	}

	/**
	 * @param despatchSC The despatchSC to set.
	 */
	public void setDespatchSC(String[] despatchSC) {
		this.despatchSC = despatchSC;
	}

	/**
	 * @return Returns the despOprFlag.
	 */
	public String[] getDespOprFlag() {
		return despOprFlag;
	}

	/**
	 * @param despOprFlag The despOprFlag to set.
	 */
	public void setDespOprFlag(String[] despOprFlag) {
		this.despOprFlag = despOprFlag;
	}

	/**
	 * @return Returns the childContainer.
	 */
	public String[] getChildContainer() {
		return childContainer;
	}

	/**
	 * @param childContainer The childContainer to set.
	 */
	public void setChildContainer(String[] childContainer) {
		this.childContainer = childContainer;
	}

	/**
	 * @return Returns the arrivalPA.
	 */
	public String getArrivalPA() {
		return this.arrivalPA;
	}

	/**
	 * @param arrivalPA The arrivalPA to set.
	 */
	public void setArrivalPA(String arrivalPA) {
		this.arrivalPA = arrivalPA;
	}

	/**
	 * @return Returns the mailStatus.
	 */
	public String getMailStatus() {
		return this.mailStatus;
	}

	/**
	 * @param mailStatus The mailStatus to set.
	 */
	public void setMailStatus(String mailStatus) {
		this.mailStatus = mailStatus;
	}

	/**
	 * @return Returns the transferCarrier.
	 */
	public String getTransferCarrier() {
		return this.transferCarrier;
	}

	/**
	 * @param transferCarrier The transferCarrier to set.
	 */
	public void setTransferCarrier(String transferCarrier) {
		this.transferCarrier = transferCarrier;
	}

	/**
	 * @return Returns the contTransferFlag.
	 */
	public String[] getContTransferFlag() {
		return contTransferFlag;
	}

	/**
	 * @param contTransferFlag The contTransferFlag to set.
	 */
	public void setContTransferFlag(String[] contTransferFlag) {
		this.contTransferFlag = contTransferFlag;
	}

	/**
	 * @return Returns the uldType.
	 */
	public String[] getUldType() {
		return uldType;
	}

	/**
	 * @param uldType The uldType to set.
	 */
	public void setUldType(String[] uldType) {
		this.uldType = uldType;
	}

	/**
	 * @return Returns the contOpFlag.
	 */
	public String[] getContOpFlag() {
		return contOpFlag;
	}

	/**
	 * @param contOpFlag The contOpFlag to set.
	 */
	public void setContOpFlag(String[] contOpFlag) {
		this.contOpFlag = contOpFlag;
	}

	/**
	 * @return Returns the mailScanTime.
	 */
	public String[] getMailScanTime() {
		return this.mailScanTime;
	}

	/**
	 * @param mailScanTime The mailScanTime to set.
	 */
	public void setMailScanTime(String[] mailScanTime) {
		this.mailScanTime = mailScanTime;
	}

	public String[] getDsnTransferFlag() {
		return dsnTransferFlag;
	}

	public void setDsnTransferFlag(String[] dsnTransferFlag) {
		this.dsnTransferFlag = dsnTransferFlag;
	}

	public String getHiddenScanDate() {
		return hiddenScanDate;
	}

	public void setHiddenScanDate(String hiddenScanDate) {
		this.hiddenScanDate = hiddenScanDate;
	}

	public String getHiddenScanTime() {
		return hiddenScanTime;
	}

	public void setHiddenScanTime(String hiddenScanTime) {
		this.hiddenScanTime = hiddenScanTime;
	}

	public String[] getDespatchOpFlag() {
		return despatchOpFlag;
	}

	public void setDespatchOpFlag(String[] despatchOpFlag) {
		this.despatchOpFlag = despatchOpFlag;
	}

	public String[] getMailOpFlag() {
		return mailOpFlag;
	}

	public void setMailOpFlag(String[] mailOpFlag) {
		this.mailOpFlag = mailOpFlag;
	}

	public String getPrintType() {
		return printType;
	}

	public void setPrintType(String printType) {
		this.printType = printType;
	}

	public int getCarrierId() {
		return carrierId;
	}

	public void setCarrierId(int carrierId) {
		this.carrierId = carrierId;
	}

	public long getFlightSequenceNumber() {
		return flightSequenceNumber;
	}

	public void setFlightSequenceNumber(long flightSequenceNumber) {
		this.flightSequenceNumber = flightSequenceNumber;
	}

	public int getLegSerialNumber() {
		return legSerialNumber;
	}

	public void setLegSerialNumber(int legSerialNumber) {
		this.legSerialNumber = legSerialNumber;
	}
	public String getContainerNumber() {
		return containerNumber;
	}

	public void setContainerNumber(String containerNumber) {
		this.containerNumber = containerNumber;
	}

	public String getCarrier() {
		return carrier;
	}

	public void setCarrier(String carrier) {
		this.carrier = carrier;
	}

	public String getSelectToTransfer() {
		return selectToTransfer;
	}

	public void setSelectToTransfer(String selectToTransfer) {
		this.selectToTransfer = selectToTransfer;
	}

	public String getTransferFlag() {
		return transferFlag;
	}

	public void setTransferFlag(String transferFlag) {
		this.transferFlag = transferFlag;
	}

	public String getBulkFromScreen() {
		return bulkFromScreen;
	}

	public void setBulkFromScreen(String bulkFromScreen) {
		this.bulkFromScreen = bulkFromScreen;
	}

	public String getHiddenMailTag() {
		return hiddenMailTag;
	}

	public void setHiddenMailTag(String hiddenMailTag) {
		this.hiddenMailTag = hiddenMailTag;
	}

	public String getSelectMainContainer() {
		return selectMainContainer;
	}

	public void setSelectMainContainer(String selectMainContainer) {
		this.selectMainContainer = selectMainContainer;
	}

	public String getContainerTypes() {
		return containerTypes;
	}

	public void setContainerTypes(String containerTypes) {
		this.containerTypes = containerTypes;
	}

	public String getChkboxStatus() {
		return chkboxStatus;
	}

	public void setChkboxStatus(String chkboxStatus) {
		this.chkboxStatus = chkboxStatus;
	}

	public String getOverrideBulkFlag() {
		return overrideBulkFlag;
	}

	public void setOverrideBulkFlag(String overrideBulkFlag) {
		this.overrideBulkFlag = overrideBulkFlag;
	}

	public String getSelContainer() {
		return selContainer;
	}

	public void setSelContainer(String selContainer) {
		this.selContainer = selContainer;
	}

	public String getShowAssignContainer() {
		return showAssignContainer;
	}

	public void setShowAssignContainer(String showAssignContainer) {
		this.showAssignContainer = showAssignContainer;
	}

	public String getChildBulkContainer() {
		return childBulkContainer;
	}

	public void setChildBulkContainer(String childBulkContainer) {
		this.childBulkContainer = childBulkContainer;
	}

	/**
	 * @return the arrivedStatus
	 */
	public String getArrivedStatus() {
		return arrivedStatus;
	}

	/**
	 * @param arrivedStatus the arrivedStatus to set
	 */
	public void setArrivedStatus(String arrivedStatus) {
		this.arrivedStatus = arrivedStatus;
	}

	/**
	 * @return the deliveredStatus
	 */
	public String getDeliveredStatus() {
		return deliveredStatus;
	}

	/**
	 * @param deliveredStatus the deliveredStatus to set
	 */
	public void setDeliveredStatus(String deliveredStatus) {
		this.deliveredStatus = deliveredStatus;
	}

	/**
	 * @return the operationFlag
	 */
	public String getOperationFlag() {
		return operationFlag;
	}

	/**
	 * @param operationFlag the operationFlag to set
	 */
	public void setOperationFlag(String operationFlag) {
		this.operationFlag = operationFlag;
	}

	/**
	 * @return the intact
	 */
	public String getIntact() {
		return intact;
	}

	/**
	 * @param intact the intact to set
	 */
	public void setIntact(String intact) {
		this.intact = intact;
	}

	/**
	 * @return the childCont
	 */
	public String getChildCont() {
		return childCont;
	}

	/**
	 * @param childCont the childCont to set
	 */
	public void setChildCont(String childCont) {
		this.childCont = childCont;
	}

	/**
	 * @return the selectCont
	 */
	public String getSelectCont() {
		return selectCont;
	}

	/**
	 * @param selectCont the selectCont to set
	 */
	public void setSelectCont(String selectCont) {
		this.selectCont = selectCont;
	}

	/**
	 * @return the selectMainCont
	 */
	public String getSelectMainCont() {
		return selectMainCont;
	}

	/**
	 * @param selectMainCont the selectMainCont to set
	 */
	public void setSelectMainCont(String selectMainCont) {
		this.selectMainCont = selectMainCont;
	}

	public String[] getPaBuiltFlag() {
		return paBuiltFlag;
	}

	public void setPaBuiltFlag(String[] paBuiltFlag) {
		this.paBuiltFlag = paBuiltFlag;
	}

	public String getSelectMode() {
		return selectMode;
	}

	public void setSelectMode(String selectMode) {
		this.selectMode = selectMode;
	}

	/**
	 * @return the arrivalSealNo
	 */
	public String[] getArrivalSealNo() {
		return arrivalSealNo;
	}

	/**
	 * @param arrivalSealNo the arrivalSealNo to set
	 */
	public void setArrivalSealNo(String[] arrivalSealNo) {
		this.arrivalSealNo = arrivalSealNo;
	}

	/**
	 * @return the sealNo
	 */
	public String[] getSealNo() {
		return sealNo;
	}

	/**
	 * @param sealNo the sealNo to set
	 */
	public void setSealNo(String[] sealNo) {
		this.sealNo = sealNo;
	}

	public String[] getContReleasedFlag() {
		return contReleasedFlag;
	}

	public void setContReleasedFlag(String[] contReleasedFlag) {
		this.contReleasedFlag = contReleasedFlag;
	}

	/**
	 * @return the csgDocNumForRouting
	 */
	public String getCsgDocNumForRouting() {
		return csgDocNumForRouting;
	}

	/**
	 * @param csgDocNumForRouting the csgDocNumForRouting to set
	 */
	public void setCsgDocNumForRouting(String csgDocNumForRouting) {
		this.csgDocNumForRouting = csgDocNumForRouting;
	}

	/**
	 * @return the newRoutingFlag
	 */
	public String getNewRoutingFlag() {
		return newRoutingFlag;
	}

	/**
	 * @param newRoutingFlag the newRoutingFlag to set
	 */
	public void setNewRoutingFlag(String newRoutingFlag) {
		this.newRoutingFlag = newRoutingFlag;
	}

	/**
	 * @return the paCodeForRouting
	 */
	public String getPaCodeForRouting() {
		return paCodeForRouting;
	}

	/**
	 * @param paCodeForRouting the paCodeForRouting to set
	 */
	public void setPaCodeForRouting(String paCodeForRouting) {
		this.paCodeForRouting = paCodeForRouting;
	}

	/**
	 * @return the attachRouting
	 */
	public String getAttachRouting() {
		return attachRouting;
	}

	/**
	 * @param attachRouting the attachRouting to set
	 */
	public void setAttachRouting(String attachRouting) {
		this.attachRouting = attachRouting;
	}

	/**
	 * @return the hiddenRoutingAvl
	 */
	public String getHiddenRoutingAvl() {
		return hiddenRoutingAvl;
	}

	/**
	 * @param hiddenRoutingAvl the hiddenRoutingAvl to set
	 */
	public void setHiddenRoutingAvl(String hiddenRoutingAvl) {
		this.hiddenRoutingAvl = hiddenRoutingAvl;
	}

	/**
	 * @return the csgDocNum
	 */
	public String[] getCsgDocNum() {
		return csgDocNum;
	}

	/**
	 * @param csgDocNum the csgDocNum to set
	 */
	public void setCsgDocNum(String[] csgDocNum) {
		this.csgDocNum = csgDocNum;
	}

	/**
	 * @return the paCod
	 */
	public String[] getPaCod() {
		return paCod;
	}

	/**
	 * @param paCod the paCod to set
	 */
	public void setPaCod(String[] paCod) {
		this.paCod = paCod;
	}

	/**
	 * @return the parentContainer
	 */
	public String getParentContainer() {
		return parentContainer;
	}

	/**
	 * @param parentContainer the parentContainer to set
	 */
	public void setParentContainer(String parentContainer) {
		this.parentContainer = parentContainer;
	}

	/**
	 * @return the selectChild
	 */
	public String getSelectChild() {
		return selectChild;
	}

	/**
	 * @param selectChild the selectChild to set
	 */
	public void setSelectChild(String selectChild) {
		this.selectChild = selectChild;
	}

	/**
	 * @return the unsavedDataFlag
	 */
	public String getUnsavedDataFlag() {
		return unsavedDataFlag;
	}

	/**
	 * @param unsavedDataFlag the unsavedDataFlag to set
	 */
	public void setUnsavedDataFlag(String unsavedDataFlag) {
		this.unsavedDataFlag = unsavedDataFlag;
	}
	/**
	 * @return the warningFlag
	 */
	public String getWarningFlag() {
		return warningFlag;
	}
	/**
	 * @param warningFlag the warningFlag to set
	 */
	public void setWarningFlag(String warningFlag) {
		this.warningFlag = warningFlag;
	}
	/**
	 * @return the warningOveride
	 */
	public String getWarningOveride() {
		return warningOveride;
	}
	/**
	 * @param warningOveride the warningOveride to set
	 */
	public void setWarningOveride(String warningOveride) {
		this.warningOveride = warningOveride;
	}
	/**
	 * @return the disableButtonsForTBA
	 */
	public String getDisableButtonsForTBA() {
		return disableButtonsForTBA;
	}
	/**
	 * @param disableButtonsForTBA the disableButtonsForTBA to set
	 */
	public void setDisableButtonsForTBA(String disableButtonsForTBA) {
		this.disableButtonsForTBA = disableButtonsForTBA;
	}
	/**
	 * 	Getter for damageCode 
	 *	Added by : A-4803 on 06-May-2015
	 * 	Used for :
	 */
	public String[] getDamageCode() {
		return damageCode;
	}
	/**
	 *  @param damageCode the damageCode to set
	 * 	Setter for damageCode 
	 *	Added by : A-4803 on 06-May-2015
	 * 	Used for :
	 */
	public void setDamageCode(String[] damageCode) {
		this.damageCode = damageCode;
	}
	/**
	 * 	Getter for damageRemarks 
	 *	Added by : A-4803 on 06-May-2015
	 * 	Used for :
	 */
	public String[] getDamageRemarks() {
		return damageRemarks;
	}
	/**
	 *  @param damageRemarks the damageRemarks to set
	 * 	Setter for damageRemarks 
	 *	Added by : A-4803 on 06-May-2015
	 * 	Used for :
	 */
	public void setDamageRemarks(String[] damageRemarks) {
		this.damageRemarks = damageRemarks;
	}
	/**
	 * 	Getter for damageCheckAll 
	 *	Added by : A-4803 on 06-May-2015
	 * 	Used for :
	 */
	public String getDamageCheckAll() {
		return damageCheckAll;
	}
	/**
	 *  @param damageCheckAll the damageCheckAll to set
	 * 	Setter for damageCheckAll 
	 *	Added by : A-4803 on 06-May-2015
	 * 	Used for :
	 */
	public void setDamageCheckAll(String damageCheckAll) {
		this.damageCheckAll = damageCheckAll;
	}
	/**
	 * 	Getter for damageSubCheck 
	 *	Added by : A-4803 on 06-May-2015
	 * 	Used for :
	 */
	public String[] getDamageSubCheck() {
		return damageSubCheck;
	}
	/**
	 *  @param damageSubCheck the damageSubCheck to set
	 * 	Setter for damageSubCheck 
	 *	Added by : A-4803 on 06-May-2015
	 * 	Used for :
	 */
	public void setDamageSubCheck(String[] damageSubCheck) {
		this.damageSubCheck = damageSubCheck;
	}
	/**
	 * 	Getter for selectedMailBag 
	 *	Added by : A-4803 on 06-May-2015
	 * 	Used for :
	 */
	public String getSelectedMailBag() {
		return selectedMailBag;
	}
	/**
	 *  @param selectedMailBag the selectedMailBag to set
	 * 	Setter for selectedMailBag 
	 *	Added by : A-4803 on 06-May-2015
	 * 	Used for :
	 */
	public void setSelectedMailBag(String selectedMailBag) {
		this.selectedMailBag = selectedMailBag;
	}
	/**
	 * 	Getter for damageFromScreen 
	 *	Added by : A-4803 on 06-May-2015
	 * 	Used for :
	 */
	public String getDamageFromScreen() {
		return damageFromScreen;
	}
	/**
	 *  @param damageFromScreen the damageFromScreen to set
	 * 	Setter for damageFromScreen 
	 *	Added by : A-4803 on 06-May-2015
	 * 	Used for :
	 */
	public void setDamageFromScreen(String damageFromScreen) {
		this.damageFromScreen = damageFromScreen;
	}
	/**
	 * 	Getter for isContainerValidFlag 
	 *	Added by : A-4803 on 08-May-2015
	 * 	Used for :
	 */
	public String getIsContainerValidFlag() {
		return isContainerValidFlag;
	}
	/**
	 *  @param isContainerValidFlag the isContainerValidFlag to set
	 * 	Setter for isContainerValidFlag 
	 *	Added by : A-4803 on 08-May-2015
	 * 	Used for :
	 */
	public void setIsContainerValidFlag(String isContainerValidFlag) {
		this.isContainerValidFlag = isContainerValidFlag;
	}	
	public void setMailVolume(String[] mailVolume) {
		this.mailVolume = mailVolume;
	}
	public String[] getMailVolume() {
		return mailVolume;
	}
	public void setDensity(String density) {
		this.density = density;
	}
	public String getDensity() {
		return density;
	}	
	public String[] getMailCompanyCode() {
		return mailCompanyCode;
	}
	public void setMailCompanyCode(String[] mailCompanyCode) {
		this.mailCompanyCode = mailCompanyCode;
	}
	/**
	 * @return the barrowCheck
	 */
	public boolean isBarrowCheck() {
		return barrowCheck;
	}
	/**
	 * @param barrowCheck the barrowCheck to set
	 */
	public void setBarrowCheck(boolean barrowCheck) {
		this.barrowCheck = barrowCheck;
	}
	/**
	 * @return the addLinkFlag
	 */
	public String getAddLinkFlag() {
		return addLinkFlag;
	}
	/**
	 * @param addLinkFlag the addLinkFlag to set
	 */
	public void setAddLinkFlag(String addLinkFlag) {
		this.addLinkFlag = addLinkFlag;
	}
	/**
	 * @return the fltNumber
	 */
	public String getFltNumber() {
		return fltNumber;
	}
	/**
	 * @param fltNumber the fltNumber to set
	 */
	public void setFltNumber(String fltNumber) {
		this.fltNumber = fltNumber;
	}
	/**
	 * @return the fltCarrierCode
	 */
	public String getFltCarrierCode() {
		return fltCarrierCode;
	}
	/**
	 * @param fltCarrierCode the fltCarrierCode to set
	 */
	public void setFltCarrierCode(String fltCarrierCode) {
		this.fltCarrierCode = fltCarrierCode;
	}
	/**
	 * @return the arrDate
	 */
	public String getArrDate() {
		return arrDate;
	}
	/**
	 * @param arrDate the arrDate to set
	 */
	public void setArrDate(String arrDate) {
		this.arrDate = arrDate;
	}
	/**
	 * @return the flightScanDate
	 */
	public String getFlightScanDate() {
		return flightScanDate;
	}
	/**
	 * @param flightScanDate the flightScanDate to set
	 */
	public void setFlightScanDate(String flightScanDate) {
		this.flightScanDate = flightScanDate;
	}
	/**
	 * @return the flightScanTime
	 */
	public String getFlightScanTime() {
		return flightScanTime;
	}
	/**
	 * @param flightScanTime the flightScanTime to set
	 */
	public void setFlightScanTime(String flightScanTime) {
		this.flightScanTime = flightScanTime;
	}
	/**
	 * @return the selectMail
	 */
	public String[] getSelectMail() {
		return selectMail;
	}
	/**
	 * @param selectMail the selectMail to set
	 */
	public void setSelectMail(String[] selectMail) {
		this.selectMail = selectMail;
	}
	/**
	 * @return the newChildCont
	 */
	public String getNewChildCont() {
		return newChildCont;
	}
	/**
	 * @param newChildCont the newChildCont to set
	 */
	public void setNewChildCont(String newChildCont) {
		this.newChildCont = newChildCont;
	}
	/**
	 * @return the changePopUpFlag
	 */
	public String getChangePopUpFlag() {
		return changePopUpFlag;
	}
	/**
	 * @param changePopUpFlag the changePopUpFlag to set
	 */
	public void setChangePopUpFlag(String changePopUpFlag) {
		this.changePopUpFlag = changePopUpFlag;
	}
	/**
	 * @return the saveSuccessFlag
	 */
	public String getSaveSuccessFlag() {
		return saveSuccessFlag;
	}
	/**
	 * @param saveSuccessFlag the saveSuccessFlag to set
	 */
	public void setSaveSuccessFlag(String saveSuccessFlag) {
		this.saveSuccessFlag = saveSuccessFlag;
	}
	/**
	 * @param deleteFlag the deleteFlag to set
	 */
	public void setDeleteFlag(String deleteFlag) {
		this.deleteFlag = deleteFlag;
	}
	/**
	 * @return the deleteFlag
	 */
	public String getDeleteFlag() {
		return deleteFlag;
	}
	/**
	 * 	Getter for originOE 
	 *	Added by : a-6245 on 13-Jun-2017
	 * 	Used for :
	 */
	public String getOriginOE() {
		return originOE;
	}
	/**
	 *  @param originOE the originOE to set
	 * 	Setter for originOE 
	 *	Added by : a-6245 on 13-Jun-2017
	 * 	Used for :
	 */
	public void setOriginOE(String originOE) {
		this.originOE = originOE;
	}
	/**
	 * 	Getter for destinationOE 
	 *	Added by : a-6245 on 13-Jun-2017
	 * 	Used for :
	 */
	public String getDestinationOE() {
		return destinationOE;
	}
	/**
	 *  @param destinationOE the destinationOE to set
	 * 	Setter for destinationOE 
	 *	Added by : a-6245 on 13-Jun-2017
	 * 	Used for :
	 */
	public void setDestinationOE(String destinationOE) {
		this.destinationOE = destinationOE;
	}
	/**
	 * 	Getter for category 
	 *	Added by : a-6245 on 13-Jun-2017
	 * 	Used for :
	 */
	public String getCategory() {
		return category;
	}
	/**
	 *  @param category the category to set
	 * 	Setter for category 
	 *	Added by : a-6245 on 13-Jun-2017
	 * 	Used for :
	 */
	public void setCategory(String category) {
		this.category = category;
	}
	/**
	 * 	Getter for subClass 
	 *	Added by : a-6245 on 13-Jun-2017
	 * 	Used for :
	 */
	public String getSubClass() {
		return subClass;
	}
	/**
	 *  @param subClass the subClass to set
	 * 	Setter for subClass 
	 *	Added by : a-6245 on 13-Jun-2017
	 * 	Used for :
	 */
	public void setSubClass(String subClass) {
		this.subClass = subClass;
	}
	/**
	 * 	Getter for year 
	 *	Added by : a-6245 on 13-Jun-2017
	 * 	Used for :
	 */
	public String getYear() {
		return year;
	}
	/**
	 *  @param year the year to set
	 * 	Setter for year 
	 *	Added by : a-6245 on 13-Jun-2017
	 * 	Used for :
	 */
	public void setYear(String year) {
		this.year = year;
	}
	/**
	 * 	Getter for dsn 
	 *	Added by : a-6245 on 13-Jun-2017
	 * 	Used for :
	 */
	public String getDsn() {
		return dsn;
	}
	/**
	 *  @param dsn the dsn to set
	 * 	Setter for dsn 
	 *	Added by : a-6245 on 13-Jun-2017
	 * 	Used for :
	 */
	public void setDsn(String dsn) {
		this.dsn = dsn;
	}
	/**
	 * 	Getter for rsn 
	 *	Added by : a-6245 on 13-Jun-2017
	 * 	Used for :
	 */
	public String getRsn() {
		return rsn;
	}
	/**
	 *  @param rsn the rsn to set
	 * 	Setter for rsn 
	 *	Added by : a-6245 on 13-Jun-2017
	 * 	Used for :
	 */
	public void setRsn(String rsn) {
		this.rsn = rsn;
	}
	/**
	 * 	Getter for hni 
	 *	Added by : a-6245 on 13-Jun-2017
	 * 	Used for :
	 */
	public String getHni() {
		return hni;
	}
	/**
	 *  @param hni the hni to set
	 * 	Setter for hni 
	 *	Added by : a-6245 on 13-Jun-2017
	 * 	Used for :
	 */
	public void setHni(String hni) {
		this.hni = hni;
	}
	/**
	 * 	Getter for ri 
	 *	Added by : a-6245 on 13-Jun-2017
	 * 	Used for :
	 */
	public String getRi() {
		return ri;
	}
	/**
	 *  @param ri the ri to set
	 * 	Setter for ri 
	 *	Added by : a-6245 on 13-Jun-2017
	 * 	Used for :
	 */
	public void setRi(String ri) {
		this.ri = ri;
	}
	/**
	 * 	Getter for wgt 
	 *	Added by : a-6245 on 13-Jun-2017
	 * 	Used for :
	 */
	public String getWgt() {
		return wgt;
	}
	/**
	 *  @param wgt the wgt to set
	 * 	Setter for wgt 
	 *	Added by : a-6245 on 13-Jun-2017
	 * 	Used for :
	 */
	public void setWgt(String wgt) {
		this.wgt = wgt;
	}
	/**
	 * 	Getter for vol 
	 *	Added by : a-6245 on 13-Jun-2017
	 * 	Used for :
	 */
	public String getVol() {
		return vol;
	}
	/**
	 *  @param vol the vol to set
	 * 	Setter for vol 
	 *	Added by : a-6245 on 13-Jun-2017
	 * 	Used for :
	 */
	public void setVol(String vol) {
		this.vol = vol;
	}
	/**
	 * 	Getter for mailbagId 
	 *	Added by : a-6245 on 13-Jun-2017
	 * 	Used for :
	 */
	public String[] getMailbagId() {
		return mailbagId;
	}
	/**
	 *  @param mailbagId the mailbagId to set
	 * 	Setter for mailbagId 
	 *	Added by : a-6245 on 13-Jun-2017
	 * 	Used for :
	 */
	public void setMailbagId(String[] mailbagId) {
		this.mailbagId = mailbagId;
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
	 * 
	 * 	Method		:	MailArrivalForm.isInValidId
	 *	Added by 	:	A-7371 on 11-Oct-2017
	 * 	Used for 	:
	 *	Parameters	:	@return 
	 *	Return type	: 	boolean
	 */
	public boolean isInValidId() {
		return inValidId;
	}
    /**
     * 
     * 	Method		:	MailArrivalForm.setInValidId
     *	Added by 	:	A-7371 on 11-Oct-2017
     * 	Used for 	:
     *	Parameters	:	@param inValidId 
     *	Return type	: 	void
     */
	public void setInValidId(boolean inValidId) {
		this.inValidId = inValidId;
	}
	/**
	 * @return the disableButtonsForAirport
	 * Added by A-7794 as part of ICRD-197439
	 */
	public String getDisableButtonsForAirport() {
		return disableButtonsForAirport;
	}
	/**
	 * @param disableButtonsForAirport the disableButtonsForAirport to set
	 * Added by A-7794 as part of ICRD-197439
	 */
	public void setDisableButtonsForAirport(String disableButtonsForAirport) {
		this.disableButtonsForAirport = disableButtonsForAirport;
	}
	public String getFromScreen() {
		return fromScreen;
	}
	public void setFromScreen(String fromScreen) {
		this.fromScreen = fromScreen;
	}
	/**
	 * @return the weightUnit
	 */
	public String[] getWeightUnit() {
		return weightUnit;
	}
	/**
	 * @param weightUnit the weightUnit to set
	 */
	public void setWeightUnit(String[] weightUnit) {
		this.weightUnit = weightUnit;
	}
	/**
	 * @return the defWeightUnit
	 */
	public String getDefWeightUnit() {
		return defWeightUnit;
	}
	/**
	 * @param defWeightUnit the defWeightUnit to set
	 */
	public void setDefWeightUnit(String defWeightUnit) {
		this.defWeightUnit = defWeightUnit;
	}
	/**
	 * @return the mailVolumeMeasure
	 */
	public Measure[] getMailVolumeMeasure() {
		return mailVolumeMeasure;
	}
	/**
	 * @param mailVolumeMeasure the mailVolumeMeasure to set
	 */
	public void setMailVolumeMeasure(Measure[] mailVolumeMeasure) {
		this.mailVolumeMeasure = mailVolumeMeasure;
	}
	
}
