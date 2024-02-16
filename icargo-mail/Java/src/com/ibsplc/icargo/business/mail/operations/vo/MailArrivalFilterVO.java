/*
 * MailArrivalFilterVO.java Created on JUN 30, 2016
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.operations.vo;

import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;

/**
 *  
 * @author A-5991
 * 
 */
/*
 * Revision History
 * -------------------------------------------------------------------------
 * Revision 		Date 					Author 		Description
 * ------------------------------------------------------------------------- 
 * 0.1     		  JUN 30, 2016			A-5991		Created
 */
public class MailArrivalFilterVO extends AbstractVO {
	
    private String companyCode;
    private String pou;
    private int carrierId;
    private String flightNumber;
    private int legSerialNumber;
    private long flightSequenceNumber;
    private String carrierCode ;
    private LocalDate flightDate ;
    
    private String mailStatus;
    
    private String paCode;
    
    private int nextCarrierId; 
    
    private String nextCarrierCode;
    
    private String containerNumber;
    
    private String containerType;
    
    private int defaultPageSize;
    
    private int pageNumber;
    
    private MailbagEnquiryFilterVO additionalFilter;
    
    private String pol;
    
    private String destination;
    
    
    public String getNextCarrierCode() {
		return nextCarrierCode;
	}
	public void setNextCarrierCode(String nextCarrierCode) {
		this.nextCarrierCode = nextCarrierCode;
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
	 * @return Returns the carrierId.
	 */
	public int getCarrierId() {
		return carrierId;
	}
	/**
	 * @param carrierId The carrierId to set.
	 */
	public void setCarrierId(int carrierId) {
		this.carrierId = carrierId;
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
	 * @return Returns the flightDate.
	 */
	public LocalDate getFlightDate() {
		return flightDate;
	}
	/**
	 * @param flightDate The flightDate to set.
	 */
	public void setFlightDate(LocalDate flightDate) {
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
	 * @return Returns the flightSequenceNumber.
	 */
	public long getFlightSequenceNumber() {
		return flightSequenceNumber;
	}
	/**
	 * @param flightSequenceNumber The flightSequenceNumber to set.
	 */
	public void setFlightSequenceNumber(long flightSequenceNumber) {
		this.flightSequenceNumber = flightSequenceNumber;
	}
	/**
	 * @return Returns the legSerialNumber.
	 */
	public int getLegSerialNumber() {
		return legSerialNumber;
	}
	/**
	 * @param legSerialNumber The legSerialNumber to set.
	 */
	public void setLegSerialNumber(int legSerialNumber) {
		this.legSerialNumber = legSerialNumber;
	}
	/**
	 * @return Returns the pou.
	 */
	public String getPou() {
		return pou;
	}
	/**
	 * @param pou The pou to set.
	 */
	public void setPou(String pou) {
		this.pou = pou;
	}
	/**
	 * @return Returns the mailStatus.
	 */
	public String getMailStatus() {
		return mailStatus;
	}
	/**
	 * @param mailStatus The mailStatus to set.
	 */
	public void setMailStatus(String mailStatus) {
		this.mailStatus = mailStatus;
	}
	/**
	 * @return Returns the nextCarrierCode.
	 */
	public int getNextCarrierId() {
		return nextCarrierId;
	}
	/**
	 * @param nextCarrierCode The nextCarrierCode to set.
	 */
	public void setNextCarrierId(int nextCarrierCode) {
		this.nextCarrierId = nextCarrierCode;
	}
	/**
	 * @return Returns the paCode.
	 */
	public String getPaCode() {
		return paCode;
	}
	/**
	 * @param paCode The paCode to set.
	 */
	public void setPaCode(String paCode) {
		this.paCode = paCode;
	}
	public String getContainerNumber() {
		return containerNumber;
	}
	public void setContainerNumber(String containerNumber) {
		this.containerNumber = containerNumber;
	}
	public String getContainerType() {
		return containerType;
	}
	public void setContainerType(String containerType) {
		this.containerType = containerType;
	}
	public int getDefaultPageSize() {
		return defaultPageSize;
	}
	public void setDefaultPageSize(int defaultPageSize) {
		this.defaultPageSize = defaultPageSize;
	}
	public int getPageNumber() {
		return pageNumber;
	}
	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}
	public MailbagEnquiryFilterVO getAdditionalFilter() {
		return additionalFilter;
	}
	public void setAdditionalFilter(MailbagEnquiryFilterVO additionalFilter) {
		this.additionalFilter = additionalFilter;
	}
	public String getPol() {
		return pol;
	}
	public void setPol(String pol) {
		this.pol = pol;
	}
	public String getDestination() {
		return destination;
	}
	public void setDestination(String destination) {
		this.destination = destination;
	}
	
	
    
}
