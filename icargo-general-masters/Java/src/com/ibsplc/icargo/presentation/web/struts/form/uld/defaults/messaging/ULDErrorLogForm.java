/*
 * ULDErrorLogForm.java Created on Jul 20, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.messaging;

import com.ibsplc.icargo.framework.model.ScreenModel;


/**
 * @author A-1862
 *
 */

public class ULDErrorLogForm extends ScreenModel {
    
	private static final String BUNDLE = "ulderrorlogResources";
	
	private String bundle;    

	private static final String PRODUCT = "uld";
	
	private static final String SUBPRODUCT = "defaults";
	
	private static final String SCREENID = "uld.defaults.ulderrorlog";
	
	private String displayPage="1";
	private String lastPageNumber="0";
	private String totalRecords;
	private String currentPageNum;
    private String pageURL="";    
    private String ulderrorlogAirport="";
    private String uldDisableStat="";
    private String ulderrorlogULDNo="";
    private String[] selectedULDErrorLog;    
    private String absIdx="";
    private String carrierCode="";
	private String flightDate="";
	private String flightNo="";
    private String dupStat="";
    private String messageType="";
    private String uldrowindex="";   
    private String returnTxn=""; 
    private String errorCode=""; 
    private String gha=""; 
    private String airlinegha=""; 
	private String screenStatusValue="";
	private String[] sequenceNumber;
	
	private String[]pou;
	
	private String[] uldNumber;	
	
	private String[] content;
		
	private String ucmNo;
	
	private String screenFlag;
	/**
	 * @return Returns the errorCode.
	 */
	public String getErrorCode() {
		return errorCode;
	}

	/**
	 * @param errorCode The errorCode to set.
	 */
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	/**
	 * @return Returns the uldrowindex.
	 */
	public String getUldrowindex() {
		return uldrowindex;
	}

	/**
	 * @param uldrowindex The uldrowindex to set.
	 */
	public void setUldrowindex(String uldrowindex) {
		this.uldrowindex = uldrowindex;
	}

	/**
	 * @return Returns the messageType.
	 */
	public String getMessageType() {
		return messageType;
	}

	/**
	 * @param messageType The messageType to set.
	 */
	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}

	/**
	 * @return Returns the ulderrorlogAirport.
	 */
	public String getUlderrorlogAirport() {
		return ulderrorlogAirport;
	}

	/**
	 * @param ulderrorlogAirport The ulderrorlogAirport to set.
	 */
	public void setUlderrorlogAirport(String ulderrorlogAirport) {
		this.ulderrorlogAirport = ulderrorlogAirport;
	}

	/**
	 * @return Returns the ulderrorlogULDNo.
	 */
	public String getUlderrorlogULDNo() {
		return ulderrorlogULDNo;
	}

	/**
	 * @param ulderrorlogULDNo The ulderrorlogULDNo to set.
	 */
	public void setUlderrorlogULDNo(String ulderrorlogULDNo) {
		this.ulderrorlogULDNo = ulderrorlogULDNo;
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
	 * @return Returns the currentPageNum.
	 */
	public String getCurrentPageNum() {
		return currentPageNum;
	}

	/**
	 * @param currentPageNum The currentPageNum to set.
	 */
	public void setCurrentPageNum(String currentPageNum) {
		this.currentPageNum = currentPageNum;
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
	 * @return Returns the lastPageNumber.
	 */
	public String getLastPageNumber() {
		return lastPageNumber;
	}

	/**
	 * @param lastPageNumber The lastPageNumber to set.
	 */
	public void setLastPageNumber(String lastPageNumber) {
		this.lastPageNumber = lastPageNumber;
	}

	/**
	 * @return Returns the pageURL.
	 */
	public String getPageURL() {
		return pageURL;
	}

	/**
	 * @param pageURL The pageURL to set.
	 */
	public void setPageURL(String pageURL) {
		this.pageURL = pageURL;
	}

	/**
	 * @return Returns the totalRecords.
	 */
	public String getTotalRecords() {
		return totalRecords;
	}

	/**
	 * @param totalRecords The totalRecords to set.
	 */
	public void setTotalRecords(String totalRecords) {
		this.totalRecords = totalRecords;
	}

	

	/**
	 * @return Returns the dupStat.
	 */
	public String getDupStat() {
		return dupStat;
	}

	/**
	 * @param dupStat The dupStat to set.
	 */
	public void setDupStat(String dupStat) {
		this.dupStat = dupStat;
	}

	/**
	 * @return Returns the absIdx.
	 */
	public String getAbsIdx() {
		return absIdx;
	}

	/**
	 * @param absIdx The absIdx to set.
	 */
	public void setAbsIdx(String absIdx) {
		this.absIdx = absIdx;
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
	 * @return Returns the flightNo.
	 */
	public String getFlightNo() {
		return flightNo;
	}

	/**
	 * @param flightNo The flightNo to set.
	 */
	public void setFlightNo(String flightNo) {
		this.flightNo = flightNo;
	}

	/**
	 * @return Returns the returnTxn.
	 */
	public String getReturnTxn() {
		return returnTxn;
	}

	/**
	 * @param returnTxn The returnTxn to set.
	 */
	public void setReturnTxn(String returnTxn) {
		this.returnTxn = returnTxn;
	}

	/**
	 * @return Returns the selectedULDErrorLog.
	 */
	public String[] getSelectedULDErrorLog() {
		return selectedULDErrorLog;
	}

	/**
	 * @param selectedULDErrorLog The selectedULDErrorLog to set.
	 */
	public void setSelectedULDErrorLog(String[] selectedULDErrorLog) {
		this.selectedULDErrorLog = selectedULDErrorLog;
	}

	/**
	 * @return Returns the uldDisableStat.
	 */
	public String getUldDisableStat() {
		return uldDisableStat;
	}

	/**
	 * @param uldDisableStat The uldDisableStat to set.
	 */
	public void setUldDisableStat(String uldDisableStat) {
		this.uldDisableStat = uldDisableStat;
	}

	/**
	 * @return Returns the airlinegha.
	 */
	public String getAirlinegha() {
		return airlinegha;
	}

	/**
	 * @param airlinegha The airlinegha to set.
	 */
	public void setAirlinegha(String airlinegha) {
		this.airlinegha = airlinegha;
	}

	/**
	 * @return Returns the gha.
	 */
	public String getGha() {
		return gha;
	}

	/**
	 * @param gha The gha to set.
	 */
	public void setGha(String gha) {
		this.gha = gha;
	}

	public String getScreenStatusValue() {
		return screenStatusValue;
	}

	public void setScreenStatusValue(String screenStatusValue) {
		this.screenStatusValue = screenStatusValue;
	}

	/**
	 * @return Returns the pou.
	 */
	public String[] getPou() {
		return pou;
	}

	/**
	 * @param pou The pou to set.
	 */
	public void setPou(String[] pou) {
		this.pou = pou;
	}

	/**
	 * @return Returns the sequenceNumber.
	 */
	public String[] getSequenceNumber() {
		return sequenceNumber;
	}

	/**
	 * @param sequenceNumber The sequenceNumber to set.
	 */
	public void setSequenceNumber(String[] sequenceNumber) {
		this.sequenceNumber = sequenceNumber;
	}

	/**
	 * @return Returns the ucmNo.
	 */
	public String getUcmNo() {
		return ucmNo;
	}

	/**
	 * @param ucmNo The ucmNo to set.
	 */
	public void setUcmNo(String ucmNo) {
		this.ucmNo = ucmNo;
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
	 * @return Returns the screenFlag.
	 */
	public String getScreenFlag() {
		return screenFlag;
	}

	/**
	 * @param screenFlag The screenFlag to set.
	 */
	public void setScreenFlag(String screenFlag) {
		this.screenFlag = screenFlag;
	}

	/**
	 * @return the content
	 */
	public String[] getContent() {
		return content;
	}

	/**
	 * @param content the content to set
	 */
	public void setContent(String[] content) {
		this.content = content;
	} 
    
 }
