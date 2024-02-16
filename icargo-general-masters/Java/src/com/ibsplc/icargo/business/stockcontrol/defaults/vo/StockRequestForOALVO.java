/*
 * StockRequestForOALVO.java Created on Jan 17, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.stockcontrol.defaults.vo;

import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;

/**
 * @author A-1619
 *
 */
public class StockRequestForOALVO extends AbstractVO {

    private String companyCode;
    
    private String airportCode;
    
    private int airlineId;
    
    private String documentType;
    
    private String documentSubType;
    
    private int serialNumber;
    
    private String modeOfCommunication;
    
    private String content;
    
    private LocalDate requestedDate;
    
    private boolean isRequestCompleted;
        
    private String airlineCode;
    
    private LocalDate actionTime;
    
    private String address;
    
    private String operationFlag;
    
    public StockRequestForOALVO(StockRequestForOALVO stockRequestOALVO) {
		// TODO Auto-generated constructor stub
	}
    public StockRequestForOALVO() {
		// TODO Auto-generated constructor stub
	}
    /**
     * @return Returns the airlineId.
     */
    public int getAirlineId() {
        return airlineId;
    }
    /**
     * @param airlineId The airlineId to set.
     */
    public void setAirlineId(int airlineId) {
        this.airlineId = airlineId;
    }
    /**
     * @return Returns the airportCode.
     */
    public String getAirportCode() {
        return airportCode;
    }
    /**
     * @param airportCode The airportCode to set.
     */
    public void setAirportCode(String airportCode) {
        this.airportCode = airportCode;
    }
    /**
     * @return Returns the companyCode.
     */
    public String getCompanyCode() {
        return companyCode;
    }
    /**
     * @param companyCode The companyCode to set.
     */
    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }
    /**
     * @return Returns the content.
     */
    public String getContent() {
        return content;
    }
    /**
     * @param content The content to set.
     */
    public void setContent(String content) {
        this.content = content;
    }
    /**
     * @return Returns the documentSubType.
     */
    public String getDocumentSubType() {
        return documentSubType;
    }
    /**
     * @param documentSubType The documentSubType to set.
     */
    public void setDocumentSubType(String documentSubType) {
        this.documentSubType = documentSubType;
    }
    /**
     * @return Returns the documentType.
     */
    public String getDocumentType() {
        return documentType;
    }
    /**
     * @param documentType The documentType to set.
     */
    public void setDocumentType(String documentType) {
        this.documentType = documentType;
    }
    /**
     * @return Returns the isRequestCompleted.
     */
    public boolean isRequestCompleted() {
        return isRequestCompleted;
    }
    /**
     * @param isRequestCompleted The isRequestCompleted to set.
     */
    public void setRequestCompleted(boolean isRequestCompleted) {
        this.isRequestCompleted = isRequestCompleted;
    }
    /**
     * @return Returns the modeOfCommunication.
     */
    public String getModeOfCommunication() {
        return modeOfCommunication;
    }
    /**
     * @param modeOfCommunication The modeOfCommunication to set.
     */
    public void setModeOfCommunication(String modeOfCommunication) {
        this.modeOfCommunication = modeOfCommunication;
    }
    /**
     * @return Returns the requestedDate.
     */
    public LocalDate getRequestedDate() {
        return requestedDate;
    }
    /**
     * @param requestedDate The requestedDate to set.
     */
    public void setRequestedDate(LocalDate requestedDate) {
        this.requestedDate = requestedDate;
    }
    /**
     * @return Returns the serialNumber.
     */
    public int getSerialNumber() {
        return serialNumber;
    }
    /**
     * @param serialNumber The serialNumber to set.
     */
    public void setSerialNumber(int serialNumber) {
        this.serialNumber = serialNumber;
    }

    
    /**
     * @return Returns the operationFlag.
     */
    public String getOperationFlag() {
        return operationFlag;
    }
    /**
     * @param operationFlag The operationFlag to set.
     */
    public void setOperationFlag(String operationFlag) {
        this.operationFlag = operationFlag;
    }
    
    
    /**
     * @return Returns the airlineCode.
     */
    public String getAirlineCode() {
        return airlineCode;
    }
    /**
     * @param airlineCode The airlineCode to set.
     */
    public void setAirlineCode(String airlineCode) {
        this.airlineCode = airlineCode;
    }
    
    
    /**
     * @return Returns the actionTime.
     */
    public LocalDate getActionTime() {
        return actionTime;
    }
    /**
     * @param actionTime The actionTime to set.
     */
    public void setActionTime(LocalDate actionTime) {
        this.actionTime = actionTime;
    }
    /**
     * @return Returns the address.
     */
    public String getAddress() {
        return address;
    }
    /**
     * @param address The address to set.
     */
    public void setAddress(String address) {
        this.address = address;
    }
}
