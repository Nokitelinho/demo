/*
 * RateAuditFilterVO.java Created on July 14, 2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.mra.defaults.vo;

import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;

/**
 * @author A-3108
 *
 */
public class RateAuditFilterVO extends AbstractVO {

	
	private String companyCode;
	private String dsn;
	
	private LocalDate dsnDate;
	private String gpaCode;
	private String dsnStatus;
	private String carrierCode;
	private int carrierId;
	private String flightNumber;

	private LocalDate flightDate; 
	private String subClass;
	private LocalDate fromDate;
	private LocalDate toDate;
	
	private String billingBasis;
	private String csgDocNum;
	private int csgSeqNum;
	
	private int pageNumber;
	private int absoluteIndex; 
	
	public String getDsn() {
		return dsn;
	}
	public void setDsn(String dsn) {
		this.dsn = dsn;
	}
	
	public LocalDate getDsnDate() {
		return dsnDate;
	}
	public void setDsnDate(LocalDate dsnDate) {
		this.dsnDate = dsnDate;
	}
	public String getDsnStatus() {
		return dsnStatus;
	}
	public void setDsnStatus(String dsnStatus) {
		this.dsnStatus = dsnStatus;
	}
	
	public String getCarrierCode() {
		return carrierCode;
	}
	public void setCarrierCode(String carrierCode) {
		this.carrierCode = carrierCode;
	}
	public LocalDate getFlightDate() {
		return flightDate;
	}
	public void setFlightDate(LocalDate flightDate) {
		this.flightDate = flightDate;
	}
	public String getFlightNumber() {
		return flightNumber;
	}
	public void setFlightNumber(String flightNumber) {
		this.flightNumber = flightNumber;
	}
	public LocalDate getFromDate() {
		return fromDate;
	}
	public void setFromDate(LocalDate fromDate) {
		this.fromDate = fromDate;
	}
	public String getGpaCode() {
		return gpaCode;
	}
	public void setGpaCode(String gpaCode) {
		this.gpaCode = gpaCode;
	}
	public String getSubClass() {
		return subClass;
	}
	public void setSubClass(String subClass) {
		this.subClass = subClass;
	}
	public LocalDate getToDate() {
		return toDate;
	}
	public void setToDate(LocalDate toDate) {
		this.toDate = toDate;
	}
	public String getCompanyCode() {
		return companyCode;
	}
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}
	public int getCarrierId() {
		return carrierId;
	}
	public void setCarrierId(int carrierId) {
		this.carrierId = carrierId;
	}
	/**
	 * @return the billingBasis
	 */
	public String getBillingBasis() {
		return billingBasis;
	}
	/**
	 * @param billingBasis the billingBasis to set
	 */
	public void setBillingBasis(String billingBasis) {
		this.billingBasis = billingBasis;
	}
	/**
	 * @return the csgDocNum
	 */
	public String getCsgDocNum() {
		return csgDocNum;
	}
	/**
	 * @param csgDocNum the csgDocNum to set
	 */
	public void setCsgDocNum(String csgDocNum) {
		this.csgDocNum = csgDocNum;
	}
	/**
	 * @return the csgSeqNum
	 */
	public int getCsgSeqNum() {
		return csgSeqNum;
	}
	/**
	 * @param csgSeqNum the csgSeqNum to set
	 */
	public void setCsgSeqNum(int csgSeqNum) {
		this.csgSeqNum = csgSeqNum;
	}
	/**
	 * @return Returns the absoluteIndex.
	 */
	public int getAbsoluteIndex() {
		return absoluteIndex;
	}
	/**
	 * @param absoluteIndex The absoluteIndex to set.
	 */
	public void setAbsoluteIndex(int absoluteIndex) {
		this.absoluteIndex = absoluteIndex;
	}
	/**
	 * @return Returns the pageNumber.
	 */
	public int getPageNumber() {
		return pageNumber;
	}
	/**
	 * @param pageNumber The pageNumber to set.
	 */
	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}
	
	
	
	
}
