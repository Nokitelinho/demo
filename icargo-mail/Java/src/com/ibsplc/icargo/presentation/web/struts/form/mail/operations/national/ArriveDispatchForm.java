/* ArriveDispatchForm.java Created on Feb 2, 2012
*
* Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
*
* This software is the proprietary information of IBS Software Services (P) Ltd.
* Use is subject to license terms.
*/

package com.ibsplc.icargo.presentation.web.struts.form.mail.operations.national;


import com.ibsplc.icargo.framework.model.ScreenModel;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.icargo.framework.util.unit.MeasureAnnotation;

/**
 * @author A-4810
 *
 */
public class ArriveDispatchForm extends ScreenModel {

	private static final String SCREEN_ID = "mailtracking.defaults.national.mailarrival";
	private static final String PRODUCT_NAME = "mail";
	private static final String SUBPRODUCT_NAME = "operations";
	private static final String BUNDLE = "arrivingMailResources";

	
	private String flightNumber;
	private String flightCarrierCode;
	private String arrivalDate;
	private String arrivalPort;
	private String[] selectContainer;
	private String duplicateFlightStatus;
	private String initialFocus;
	private String checkFlight;
	
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
	private String[] remarks;
	
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
	
	@MeasureAnnotation(mappedValue="mailWtMeasure",unitType="MWT")
	private String[] mailWt;
	private Measure[] mailWtMeasure;
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
	
	
	private String arrivedStatus;
	private String deliveredStatus;
	private String operationFlag;
	
	
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
	
	private String newRoutingFlag;
	private String attachRouting;
	private String hiddenRoutingAvl;
	private String[] csgDocNum;
	private String[] paCod;
	private String parentContainer;
	private String selectChild;
	private String unsavedDataFlag;
	
	private String status;
	private String selectedRow;
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	public String getSelectedRow() {
		return selectedRow;
	}

	public void setSelectedRow(String selectedRow) {
		this.selectedRow = selectedRow;
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
	public String[] getRemarks() {
		return this.remarks;
	}

	/**
	 * @param remarks The remarks to set.
	 */
	public void setRemarks(String[] remarks) {
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

	public String[] getReceivedBags() {
		return receivedBags;
	}

	public void setReceivedBags(String[] receivedBags) {
		this.receivedBags = receivedBags;
	}

	public String[] getReceivedWt() {
		return receivedWt;
	}

	public void setReceivedWt(String[] receivedWt) {
		this.receivedWt = receivedWt;
	}

	public String[] getDeliveredBags() {
		return deliveredBags;
	}

	public void setDeliveredBags(String[] deliveredBags) {
		this.deliveredBags = deliveredBags;
	}

	public String[] getDeliveredWt() {
		return deliveredWt;
	}

	public void setDeliveredWt(String[] deliveredWt) {
		this.deliveredWt = deliveredWt;
	}	

}
