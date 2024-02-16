/*
 * SearchConsignmentForm.java Created on Dec 28, 2011
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.struts.form.mail.operations;

import com.ibsplc.icargo.framework.client.daterange.notation.DateFieldId;
import com.ibsplc.icargo.framework.model.ScreenModel;
/**
 * @author A-4826
 *
 */
public class SearchConsignmentForm extends ScreenModel {


	private static final String SCREEN_ID = "mailtracking.defaults.searchconsignment";
	private static final String PRODUCT_NAME = "mail";
	private static final String SUBPRODUCT_NAME = "operations";
	private static final String BUNDLE = "searchconsignmentResources";

	
	private String carrierCode ;
    private String flightNumber;
    private String departurePort;
    private String flightType;
    private String flightDate;
    private String searchMode;
    private String resdit;
    
    private String consignmentDocument;
    private String fromDate;
    private String toDate;
    private String ooe;
    private String doe;
	private String mailCategoryCode;
	private String mailSubclass;
	private String year;
    private String despatchSerialNumber;
    private String receptacleSerialNumber;
    private String[] selectedRows;
    private String duplicateFlightStatus;
    private String mailbagId;//Added for ICRD-205027
	
    private String lastPageNum;
	private String displayPage;
	private String status;
	private String fromScreen;
	private String assignToFlight;
	private String destination;
	private String destinationCity;
	private String carrierInv;
	private String pol;
	private String mailClass;
	private String pao;
	private String notAccepted;
	private String screenMode;
	private String[] selectMail;
	private String lookupClose;
	
	
	//for sendresdits popup
	private String event;
	private String port;
	private String pou;
	private String eventTime;
	private String flightNo;
	private String carrierCod;
	private String flightDat;
	private String eventDate;
	private String hideTabFlag;
	//Added For Send resdits popup
	private String[] containerFlag;
	private String dupFlightStatus;
	private String selCont;
	private String popUpClose;
	private String deliveredPa;
	private String[] despatchChk;
	private String disableButton="";
	
	private String density;
	
	private String uldNumber;
	
	private String select;
	
	private String fromButton;
	
	private String invokingScreen;
	
	//ADDED by indu for 50217
	private String[] filterPACode;
	private String[] filterCondocNum;
	//ADDED by indu for 50204
	private String mailStatus;
	
	private String paCode;
	private String conDocNo;
	private String screenFlag;
	private String[] consignmentNumber;
		
	//Added by A-7531 for icrd-192536
		private String shipmentPrefix;
		private String documentNumber;
		 private String[] selectedMailbagId;
		 private String[] awbNumber;
	//Added by A-5220 for ICRD-21098 starts
	public static final String NAV_MOD_LIST = "list";
	public static final String NAV_MOD_PAGINATION = "navigation";
	private String navigationMode;
	//Added for ICRD-214795 starts
	private String reqDeliveryDate;
	private String reqDeliveryTime;
	//Added for ICRD-214795 ends
	
	//A-8061 Added for ICRD-82434 starts
	//private String flightType;
	private String consignmentDate;	
	private String isPendingResditChecked;	
	//A-8061 Added for  ICRD-82434 end
	private String isAwbAttached;	
	private String mailCount;
	private String currentDialogOption;
	  private String currentDialogId;
	
	
	public String getCurrentDialogOption() {
		return currentDialogOption;
	}
	public void setCurrentDialogOption(String currentDialogOption) {
		this.currentDialogOption = currentDialogOption;
	}
	public String getCurrentDialogId() {
		return currentDialogId;
	}
	public void setCurrentDialogId(String currentDialogId) {
		this.currentDialogId = currentDialogId;
	}
	public String getMailCount() {
		return mailCount;
	}
	public void setMailCount(String mailCount) {
		this.mailCount = mailCount;
	}
	public String getIsAwbAttached() {
		return isAwbAttached;
	}
	public void setIsAwbAttached(String isAwbAttached) {
		this.isAwbAttached = isAwbAttached;
	}
	/**
	 * @return the navigationMode
	 */
	public String getNavigationMode() {
		return navigationMode;
	}
	/**
	 * @param navigationMode the navigationMode to set
	 */
	public void setNavigationMode(String navigationMode) {
		this.navigationMode = navigationMode;
	}
	//Added by A-5220 for ICRD-21098 ends
	/**
	 * @return Returns the maiStatus.
	 */
	public String getMailStatus() {
		return mailStatus;
	}



	/**
	 * @param maiStatus The maiStatus to set.
	 */
	public void setMailStatus(String mailStatus) {
		this.mailStatus = mailStatus;
	}



	/**
	 * @return Returns the filterCondocNum.
	 */
	public String[] getFilterCondocNum() {
		return filterCondocNum;
	}



	/**
	 * @param filterCondocNum The filterCondocNum to set.
	 */
	public void setFilterCondocNum(String[] filterCondocNum) {
		this.filterCondocNum = filterCondocNum;
	}



	/**
	 * @return Returns the filterPACode.
	 */
	public String[] getFilterPACode() {
		return filterPACode;
	}



	/**
	 * @param filterPACode The filterPACode to set.
	 */
	public void setFilterPACode(String[] filterPACode) {
		this.filterPACode = filterPACode;
	}

	

	/**
	 * @return the invokingScreen
	 */
	public String getInvokingScreen() {
		return invokingScreen;
	}

	/**
	 * @param invokingScreen the invokingScreen to set
	 */
	public void setInvokingScreen(String invokingScreen) {
		this.invokingScreen = invokingScreen;
	}

	public String getDensity() {
		return density;
	}

	public void setDensity(String density) {
		this.density = density;
	}

	public String getAssignToFlight() {
		return assignToFlight;
	}

	public void setAssignToFlight(String assignToFlight) {
		this.assignToFlight = assignToFlight;
	}

	public String getCarrierInv() {
		return carrierInv;
	}

	public void setCarrierInv(String carrierInv) {
		this.carrierInv = carrierInv;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public String getDestinationCity() {
		return destinationCity;
	}

	public void setDestinationCity(String destinationCity) {
		this.destinationCity = destinationCity;
	}

	public String getDisplayPage() {
		return displayPage;
	}

	public void setDisplayPage(String displayPage) {
		this.displayPage = displayPage;
	}

	public String getFromScreen() {
		return fromScreen;
	}

	public void setFromScreen(String fromScreen) {
		this.fromScreen = fromScreen;
	}

	public String getLastPageNum() {
		return lastPageNum;
	}

	public void setLastPageNum(String lastPageNum) {
		this.lastPageNum = lastPageNum;
	}

	public String getMailClass() {
		return mailClass;
	}

	public void setMailClass(String mailClass) {
		this.mailClass = mailClass;
	}

	public String getPao() {
		return pao;
	}

	public void setPao(String pao) {
		this.pao = pao;
	}

	public String getPol() {
		return pol;
	}

	public void setPol(String pol) {
		this.pol = pol;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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
	 * @return Returns the carrierCode.
	 */
	public String getCarrierCode() {
		return carrierCode;
	}

	/**
	 * @param carrierCode The carrierCode to set.
	 */
	public void setCarrierCode(String carrierCode) {
		this.carrierCode = carrierCode;
	}

	/**
	 * @return Returns the consignmentDocument.
	 */
	public String getConsignmentDocument() {
		return consignmentDocument;
	}

	/**
	 * @param consignmentDocument The consignmentDocument to set.
	 */
	public void setConsignmentDocument(String consignmentDocument) {
		this.consignmentDocument = consignmentDocument;
	}

	/**
	 * @return Returns the despatchSerialNumber.
	 */
	public String getDespatchSerialNumber() {
		return despatchSerialNumber;
	}

	/**
	 * @param despatchSerialNumber The despatchSerialNumber to set.
	 */
	public void setDespatchSerialNumber(String despatchSerialNumber) {
		this.despatchSerialNumber = despatchSerialNumber;
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
	 * @return Returns the flightDate.
	 */
	public String getFlightDate() {
		return flightDate;
	}

	/**
	 * @param flightDate The flightDate to set.
	 */
	public void setFlightDate(String flightDate) {
		this.flightDate = flightDate;
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
	 * @return Returns the fromDate.
	 */
	@DateFieldId(id="ListCarditsDateRange",fieldType="from")//Added By T-1925 for ICRD-9704
	public String getFromDate() {
		return fromDate;
	}

	/**
	 * @param fromDate The fromDate to set.
	 */
	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}

	/**
	 * @return Returns the mailCategoryCode.
	 */
	public String getMailCategoryCode() {
		return mailCategoryCode;
	}

	/**
	 * @param mailCategoryCode The mailCategoryCode to set.
	 */
	public void setMailCategoryCode(String mailCategoryCode) {
		this.mailCategoryCode = mailCategoryCode;
	}

	/**
	 * @return Returns the mailSubclass.
	 */
	public String getMailSubclass() {
		return mailSubclass;
	}

	/**
	 * @param mailSubclass The mailSubclass to set.
	 */
	public void setMailSubclass(String mailSubclass) {
		this.mailSubclass = mailSubclass;
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
	 * @return Returns the receptacleSerialNumber.
	 */
	public String getReceptacleSerialNumber() {
		return receptacleSerialNumber;
	}

	/**
	 * @param receptacleSerialNumber The receptacleSerialNumber to set.
	 */
	public void setReceptacleSerialNumber(String receptacleSerialNumber) {
		this.receptacleSerialNumber = receptacleSerialNumber;
	}

	/**
	 * @return Returns the resdit.
	 */
	public String getResdit() {
		return resdit;
	}

	/**
	 * @param resdit The resdit to set.
	 */
	public void setResdit(String resdit) {
		this.resdit = resdit;
	}

	/**
	 * @return Returns the searchMode.
	 */
	public String getSearchMode() {
		return searchMode;
	}

	/**
	 * @param searchMode The searchMode to set.
	 */
	public void setSearchMode(String searchMode) {
		this.searchMode = searchMode;
	}

	/**
	 * @return Returns the toDate.
	 */
	@DateFieldId(id="ListCarditsDateRange",fieldType="to")//Added By T-1925 for ICRD-9704
	public String getToDate() {
		return toDate;
	}

	/**
	 * @param toDate The toDate to set.
	 */
	public void setToDate(String toDate) {
		this.toDate = toDate;
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
	 * @return Returns the departurePort.
	 */
	public String getDeparturePort() {
		return departurePort;
	}

	/**
	 * @param departurePort The departurePort to set.
	 */
	public void setDeparturePort(String departurePort) {
		this.departurePort = departurePort;
	}

	/**
	 * @return Returns the flightType.
	 */
	public String getFlightType() {
		return flightType;
	}

	/**
	 * @param flightType The flightType to set.
	 */
	public void setFlightType(String flightType) {
		this.flightType = flightType;
	}

	/**
	 * @return Returns the duplicateFlightStatus.
	 */
	public String getDuplicateFlightStatus() {
		return duplicateFlightStatus;
	}

	/**
	 * @param duplicateFlightStatus The duplicateFlightStatus to set.
	 */
	public void setDuplicateFlightStatus(String duplicateFlightStatus) {
		this.duplicateFlightStatus = duplicateFlightStatus;
	}

	public String getScreenMode() {
		return screenMode;
	}

	public void setScreenMode(String screenMode) {
		this.screenMode = screenMode;
	}

	public String getNotAccepted() {
		return notAccepted;
	}

	public void setNotAccepted(String notAccepted) {
		this.notAccepted = notAccepted;
	}

	public String[] getSelectMail() {
		return selectMail;
	}

	public void setSelectMail(String[] selectMail) {
		this.selectMail = selectMail;
	}

	public String getLookupClose() {
		return lookupClose;
	}

	public void setLookupClose(String lookupClose) {
		this.lookupClose = lookupClose;
	}

	public String getEvent() {
		return event;
	}

	public void setEvent(String event) {
		this.event = event;
	}

	public String getEventTime() {
		return eventTime;
	}

	public void setEventTime(String eventTime) {
		this.eventTime = eventTime;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getFlightDat() {
		return flightDat;
	}

	public void setFlightDat(String flightDat) {
		this.flightDat = flightDat;
	}

	public String getFlightNo() {
		return flightNo;
	}

	public void setFlightNo(String flightNo) {
		this.flightNo = flightNo;
	}

	public String getCarrierCod() {
		return carrierCod;
	}

	public void setCarrierCod(String carrierCod) {
		this.carrierCod = carrierCod;
	}

	public String getEventDate() {
		return eventDate;
	}

	public void setEventDate(String eventDate) {
		this.eventDate = eventDate;
	}

	public String getHideTabFlag() {
		return hideTabFlag;
	}

	public void setHideTabFlag(String hideTabFlag) {
		this.hideTabFlag = hideTabFlag;
	}

	public String[] getContainerFlag() {
		return containerFlag;
	}

	public void setContainerFlag(String[] containerFlag) {
		this.containerFlag = containerFlag;
	}

	public String getPou() {
		return pou;
	}

	public void setPou(String pou) {
		this.pou = pou;
	}

	public String getDupFlightStatus() {
		return dupFlightStatus;
	}

	public void setDupFlightStatus(String dupFlightStatus) {
		this.dupFlightStatus = dupFlightStatus;
	}

	public String getSelCont() {
		return selCont;
	}

	public void setSelCont(String selCont) {
		this.selCont = selCont;
	}

	public String getPopUpClose() {
		return popUpClose;
	}

	public void setPopUpClose(String popUpClose) {
		this.popUpClose = popUpClose;
	}

	public String getDeliveredPa() {
		return deliveredPa;
	}

	public void setDeliveredPa(String deliveredPa) {
		this.deliveredPa = deliveredPa;
	}

	public String[] getDespatchChk() {
		return despatchChk;
	}

	public void setDespatchChk(String[] despatchChk) {
		this.despatchChk = despatchChk;
	}

	public String getDisableButton() {
		return disableButton;
	}

	public void setDisableButton(String disableButton) {
		this.disableButton = disableButton;
	}

	/**
	 * @return the fromButton
	 */
	public String getFromButton() {
		return fromButton;
	}

	/**
	 * @param fromButton the fromButton to set
	 */
	public void setFromButton(String fromButton) {
		this.fromButton = fromButton;
	}

	/**
	 * @return the select
	 */
	public String getSelect() {
		return select;
	}

	/**
	 * @param select the select to set
	 */
	public void setSelect(String select) {
		this.select = select;
	}

	/**
	 * @return the uldNumber
	 */
	public String getUldNumber() {
		return uldNumber;
	}

	/**
	 * @param uldNumber the uldNumber to set
	 */
	public void setUldNumber(String uldNumber) {
		this.uldNumber = uldNumber;
	}



	/**
	 * @return the conDocNo
	 */
	public String getConDocNo() {
		return conDocNo;
	}



	/**
	 * @param conDocNo the conDocNo to set
	 */
	public void setConDocNo(String conDocNo) {
		this.conDocNo = conDocNo;
	}



	/**
	 * @return the paCode
	 */
	public String getPaCode() {
		return paCode;
	}



	/**
	 * @param paCode the paCode to set
	 */
	public void setPaCode(String paCode) {
		this.paCode = paCode;
	}



	/**
	 * @return the screenFlag
	 */
	public String getScreenFlag() {
		return screenFlag;
	}



	/**
	 * @param screenFlag the screenFlag to set
	 */
	public void setScreenFlag(String screenFlag) {
		this.screenFlag = screenFlag;
	}
	/**
	 * 	Getter for mailbagId 
	 *	Added by : a-6245 on 14-Jun-2017
	 * 	Used for :
	 */
	public String getMailbagId() {
		return mailbagId;
	}
	/**
	 *  @param mailbagId the mailbagId to set
	 * 	Setter for mailbagId 
	 *	Added by : a-6245 on 14-Jun-2017
	 * 	Used for :
	 */
	public void setMailbagId(String mailbagId) {
		this.mailbagId = mailbagId;
	}
	/**
	 * @param csgDocNum the csgDocNum to set
	 */
	public void setConsignmentNumber(String[] consignmentNumber) {
		this.consignmentNumber = consignmentNumber;
	}
	/**
	 * @return the csgDocNum
	 */
	public String[] getConsignmentNumber() {
		return consignmentNumber;
	}

	/**
	 * 	Getter for reqDeliveryDate 
	 *	Added by : A-6245 on 26-Jul-2017
	 * 	Used for :
	 */
	public String getReqDeliveryDate() {
		return reqDeliveryDate;
	}
	/**
	 *  @param reqDeliveryDate the reqDeliveryDate to set
	 * 	Setter for reqDeliveryDate 
	 *	Added by : A-6245 on 26-Jul-2017
	 * 	Used for :
	 */
	public void setReqDeliveryDate(String reqDeliveryDate) {
		this.reqDeliveryDate = reqDeliveryDate;
	}
	/**
	 * 	Getter for reqDeliveryTime 
	 *	Added by : A-6245 on 26-Jul-2017
	 * 	Used for :
	 */
	public String getReqDeliveryTime() {
		return reqDeliveryTime;
	}
	/**
	 *  @param reqDeliveryTime the reqDeliveryTime to set
	 * 	Setter for reqDeliveryTime 
	 *	Added by : A-6245 on 26-Jul-2017
	 * 	Used for :
	 */
	public void setReqDeliveryTime(String reqDeliveryTime) {
		this.reqDeliveryTime = reqDeliveryTime;
	}
   /**
	 *	Added by 	:	A-7531 on 02-Aug-2017
	 *	Parameters	:	@return shipmentPrefix
	 *	Return type	: 	String
	 */
	public String getShipmentPrefix() {
		return shipmentPrefix;
	}
	/**
	 *	Parameters	:	@param shipmentPrefix 
	 *	Return type	: 	void
	 */
	public void setShipmentPrefix(String shipmentPrefix) {
		this.shipmentPrefix = shipmentPrefix;
	}
	/**
	 *	Added by 	:	A-7531 on 02-Aug-2017
	 *	Parameters	:	@return documentNumber
	 *	Return type	: 	String
	 */
	public String getDocumentNumber() {
		return documentNumber;
	}
	/**
	 *	Parameters	:	@param documentNumber 
	 *	Return type	: 	void
	 */
	public void setDocumentNumber(String documentNumber) {
		this.documentNumber = documentNumber;
	}
	public String[] getSelectedMailbagId() {
		return selectedMailbagId;
	}
	public void setSelectedMailbagId(String[] selectedMailbagId) {
		this.selectedMailbagId = selectedMailbagId;
	}
	public String[] getAwbNumber() {
		return awbNumber;
	}
	public void setAwbNumber(String[] awbNumber) {
		this.awbNumber = awbNumber;
	}
	/**
	 * @author A-8061
	 * Getter for consignmentDate
	 * @return
	 */
	public String getConsignmentDate() {
		return consignmentDate;
	}
	/**
	 * @author A-8061
	 * Setter for consignmentDate
	 * @param consignmentDate
	 */
	public void setConsignmentDate(String consignmentDate) {
		this.consignmentDate = consignmentDate;
	}
	/**
	 * @author A-8061
	 * Getter for isPendingesditChecked
	 * @return
	 */
	public String getIsPendingResditChecked() {
		return isPendingResditChecked;
	}
	/**
	 * @author A-8061
	 * Setter for isPendingesditChecked
	 * @param isPendingesditChecked
	 */
	public void setIsPendingResditChecked(String isPendingResditChecked) {
		this.isPendingResditChecked = isPendingResditChecked;
	}

	





}
