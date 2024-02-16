/*
 * OffloadFilterVO.java Created on Jun 30, 2016
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
 * @author a-3109
 *
 */
public class OffloadFilterVO extends AbstractVO {
	private String companyCode;

	private String pol;

	private int carrierId;

	private String flightNumber;

	private int legSerialNumber;

	private long flightSequenceNumber;

	private String carrierCode;

	private LocalDate flightDate;

	/*
	 * U - ULD , D- DSN , M- Mailbag
	 */
	private String offloadType;

	private String containerNumber;

	private String dsn;

	private String dsnOriginExchangeOffice;

	private String dsnDestinationExchangeOffice;

	private String dsnMailClass;

	private String dsnYear;

	/*
	 *
	 */
	private String mailbagOriginExchangeOffice;

	private String mailbagDestinationExchangeOffice;

	private String mailbagCategoryCode;

	private String mailbagSubclass;

	private int mailbagYear;

	private String mailbagDsn;

	private String mailbagRsn;
	
	private String mailbagId;//Added for ICRD-205027

	private int pageNumber;


	private String containerType;

	private int defaultPageSize;  //Added by A-7929
	
	private int totalRecords;   //Added by A-7929

    private boolean remove;


	public int getTotalRecords() {
		return totalRecords;
	}

	public void setTotalRecords(int totalRecords) {
		this.totalRecords = totalRecords;
	}

	public int getDefaultPageSize() {
		return defaultPageSize;
	}

	public void setDefaultPageSize(int defaultPageSize) {
		this.defaultPageSize = defaultPageSize;
	}

	/**
	 * @return Returns the containerType.
	 */
	public String getContainerType() {
		return containerType;
	}

	/**
	 * @param containerType
	 *            The containerType to set.
	 */
	public void setContainerType(String containerType) {
		this.containerType = containerType;
	}

	/**
	 * @return Returns the carrierCode.
	 */
	public String getCarrierCode() {
		return carrierCode;
	}

	/**
	 * @param carrierCode
	 *            The carrierCode to set.
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
	 * @param carrierId
	 *            The carrierId to set.
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
	 * @param companyCode
	 *            The companyCode to set.
	 */
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	/**
	 * @return Returns the containerNumber.
	 */
	public String getContainerNumber() {
		return containerNumber;
	}

	/**
	 * @param containerNumber
	 *            The containerNumber to set.
	 */
	public void setContainerNumber(String containerNumber) {
		this.containerNumber = containerNumber;
	}

	/**
	 * @return Returns the dsn.
	 */
	public String getDsn() {
		return dsn;
	}

	/**
	 * @param dsn
	 *            The dsn to set.
	 */
	public void setDsn(String dsn) {
		this.dsn = dsn;
	}

	/**
	 * @return Returns the dsnDestinationExchangeOffice.
	 */
	public String getDsnDestinationExchangeOffice() {
		return dsnDestinationExchangeOffice;
	}

	/**
	 * @param dsnDestinationExchangeOffice
	 *            The dsnDestinationExchangeOffice to set.
	 */
	public void setDsnDestinationExchangeOffice(
			String dsnDestinationExchangeOffice) {
		this.dsnDestinationExchangeOffice = dsnDestinationExchangeOffice;
	}

	/**
	 * @return Returns the dsnMailClass.
	 */
	public String getDsnMailClass() {
		return dsnMailClass;
	}

	/**
	 * @param dsnMailClass
	 *            The dsnMailClass to set.
	 */
	public void setDsnMailClass(String dsnMailClass) {
		this.dsnMailClass = dsnMailClass;
	}

	/**
	 * @return Returns the dsnOriginExchangeOffice.
	 */
	public String getDsnOriginExchangeOffice() {
		return dsnOriginExchangeOffice;
	}

	/**
	 * @param dsnOriginExchangeOffice
	 *            The dsnOriginExchangeOffice to set.
	 */
	public void setDsnOriginExchangeOffice(String dsnOriginExchangeOffice) {
		this.dsnOriginExchangeOffice = dsnOriginExchangeOffice;
	}

	/**
	 * @return Returns the dsnYear.
	 */
	public String getDsnYear() {
		return dsnYear;
	}

	/**
	 * @param dsnYear
	 *            The dsnYear to set.
	 */
	public void setDsnYear(String dsnYear) {
		this.dsnYear = dsnYear;
	}

	/**
	 * @return Returns the flightDate.
	 */
	public LocalDate getFlightDate() {
		return flightDate;
	}

	/**
	 * @param flightDate
	 *            The flightDate to set.
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
	 * @param flightNumber
	 *            The flightNumber to set.
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
	 * @param flightSequenceNumber
	 *            The flightSequenceNumber to set.
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
	 * @param legSerialNumber
	 *            The legSerialNumber to set.
	 */
	public void setLegSerialNumber(int legSerialNumber) {
		this.legSerialNumber = legSerialNumber;
	}

	/**
	 * @return Returns the mailbagCategoryCode.
	 */
	public String getMailbagCategoryCode() {
		return mailbagCategoryCode;
	}

	/**
	 * @param mailbagCategoryCode
	 *            The mailbagCategoryCode to set.
	 */
	public void setMailbagCategoryCode(String mailbagCategoryCode) {
		this.mailbagCategoryCode = mailbagCategoryCode;
	}

	/**
	 * @return Returns the mailbagDestinationExchangeOffice.
	 */
	public String getMailbagDestinationExchangeOffice() {
		return mailbagDestinationExchangeOffice;
	}

	/**
	 * @param mailbagDestinationExchangeOffice
	 *            The mailbagDestinationExchangeOffice to set.
	 */
	public void setMailbagDestinationExchangeOffice(
			String mailbagDestinationExchangeOffice) {
		this.mailbagDestinationExchangeOffice = mailbagDestinationExchangeOffice;
	}

	/**
	 * @return the pageNumber
	 */
	public int getPageNumber() {
		return pageNumber;
	}


	/**
	 * @param pageNumber
	 *            the pageNumber to set
	 */
	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	/**
	 * @return Returns the mailbagDsn.
	 */
	public String getMailbagDsn() {
		return mailbagDsn;
	}

	/**
	 * @param mailbagDsn
	 *            The mailbagDsn to set.
	 */
	public void setMailbagDsn(String mailbagDsn) {
		this.mailbagDsn = mailbagDsn;
	}

	/**
	 * @return Returns the mailbagOriginExchangeOffice.
	 */
	public String getMailbagOriginExchangeOffice() {
		return mailbagOriginExchangeOffice;
	}

	/**
	 * @param mailbagOriginExchangeOffice
	 *            The mailbagOriginExchangeOffice to set.
	 */
	public void setMailbagOriginExchangeOffice(
			String mailbagOriginExchangeOffice) {
		this.mailbagOriginExchangeOffice = mailbagOriginExchangeOffice;
	}

	/**
	 * @return Returns the mailbagRsn.
	 */
	public String getMailbagRsn() {
		return mailbagRsn;
	}

	/**
	 * @param mailbagRsn
	 *            The mailbagRsn to set.
	 */
	public void setMailbagRsn(String mailbagRsn) {
		this.mailbagRsn = mailbagRsn;
	}

	/**
	 * @return Returns the mailbagSubclass.
	 */
	public String getMailbagSubclass() {
		return mailbagSubclass;
	}

	/**
	 * @param mailbagSubclass
	 *            The mailbagSubclass to set.
	 */
	public void setMailbagSubclass(String mailbagSubclass) {
		this.mailbagSubclass = mailbagSubclass;
	}

	/**
	 * @return Returns the mailbagYear.
	 */
	public int getMailbagYear() {
		return mailbagYear;
	}

	/**
	 * @param mailbagYear
	 *            The mailbagYear to set.
	 */
	public void setMailbagYear(int mailbagYear) {
		this.mailbagYear = mailbagYear;
	}

	/**
	 * @return Returns the offloadType.
	 */
	public String getOffloadType() {
		return offloadType;
	}

	/**
	 * @param offloadType
	 *            The offloadType to set.
	 */
	public void setOffloadType(String offloadType) {
		this.offloadType = offloadType;
	}

	/**
	 * @return Returns the pol.
	 */
	public String getPol() {
		return pol;
	}

	/**
	 * @param pol
	 *            The pol to set.
	 */
	public void setPol(String pol) {
		this.pol = pol;
	}

	/**
	 * 	Getter for mailbagId 
	 *	Added by : a-6245 on 22-Jun-2017
	 * 	Used for :
	 */
	public String getMailbagId() {
		return mailbagId;
	}

	/**
	 *  @param mailbagId the mailbagId to set
	 * 	Setter for mailbagId 
	 *	Added by : a-6245 on 22-Jun-2017
	 * 	Used for :
	 */
	public void setMailbagId(String mailbagId) {
		this.mailbagId = mailbagId;
	}

	public boolean isRemove() {
		return remove;
	}

	public void setRemove(boolean remove) {
		this.remove = remove;
	}
}
