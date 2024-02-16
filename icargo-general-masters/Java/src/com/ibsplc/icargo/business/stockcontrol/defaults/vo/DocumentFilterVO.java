/*
 * DocumentFilterVO.java Created on Jul 31, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
/**
 *
 */
package com.ibsplc.icargo.business.stockcontrol.defaults.vo;

import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;

/**
 * @author A-1954
 *
 */
public class DocumentFilterVO extends AbstractVO {

	private String companyCode;

	private String documentType;

	private String documentSubType;

	private String documentNumber;

	private int airlineIdentifier;

	private String stockOwner;

	private boolean isOtherAirline;

	private String status;

	private boolean skipStockLocking;
	private boolean isExecutionAfterReopen;

	private LocalDate executionDate;

	private String stockHolderCode;
	
	private String lastUpdateUser;
	private String shipmentPrefix;
	//Added by A-6858 for ICRD-234889
	private String awbDestination;
	private String awbOrigin;
	private String awbViaPoints;
	public String getAwbViaPoints() {
		return awbViaPoints;
	}

	public void setAwbViaPoints(String awbViaPoints) {
		this.awbViaPoints = awbViaPoints;
	}
	/**
     * System parameter to override error
     */
    public static final String RESOLVE_SYSTEMPARAMETER = "stockcontrol.defaults.executionerroroverride";
	/**
	 * @return Returns the documentNumber.
	 */
	public String getDocumentNumber() {
		return documentNumber;
	}

	/**
	 * @param documentNumber
	 *            The documentNumber to set.
	 */
	public void setDocumentNumber(String documentNumber) {
		this.documentNumber = documentNumber;
	}

	/**
	 * @return Returns the airlineIdentifier.
	 */
	public int getAirlineIdentifier() {
		return airlineIdentifier;
	}

	/**
	 * @param airlineIdentifier
	 *            The airlineIdentifier to set.
	 */
	public void setAirlineIdentifier(int airlineIdentifier) {
		this.airlineIdentifier = airlineIdentifier;
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
	 * @return Returns the documentSubType.
	 */
	public String getDocumentSubType() {
		return documentSubType;
	}

	/**
	 * @param documentSubType
	 *            The documentSubType to set.
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
	 * @param documentType
	 *            The documentType to set.
	 */
	public void setDocumentType(String documentType) {
		this.documentType = documentType;
	}

	/**
	 * @return Returns the stockOwner.
	 */
	public String getStockOwner() {
		return stockOwner;
	}

	/**
	 * @param stockOwner
	 *            The stockOwner to set.
	 */
	public void setStockOwner(String stockOwner) {
		this.stockOwner = stockOwner;
	}

	/**
	 * @return Returns the isOtherAirline.
	 */
	public boolean isOtherAirline() {
		return isOtherAirline;
	}

	/**
	 * @param isOtherAirline The isOtherAirline to set.
	 */
	public void setOtherAirline(boolean isOtherAirline) {
		this.isOtherAirline = isOtherAirline;
	}

	/**
	 * @return Returns the status.
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status The status to set.
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the executionDate
	 */
	public LocalDate getExecutionDate() {
		return executionDate;
	}

	/**
	 * @param executionDate the executionDate to set
	 */
	public void setExecutionDate(LocalDate executionDate) {
		this.executionDate = executionDate;
	}

	/**
	 * @return the isExecutionAfterReopen
	 */
	public boolean isExecutionAfterReopen() {
		return isExecutionAfterReopen;
	}

	/**
	 * @param isExecutionAfterReopen the isExecutionAfterReopen to set
	 */
	public void setExecutionAfterReopen(boolean isExecutionAfterReopen) {
		this.isExecutionAfterReopen = isExecutionAfterReopen;
	}

	/**
	 * @return the lastUpdateUser
	 */
	public String getLastUpdateUser() {
		return lastUpdateUser;
	}

	/**
	 * @param lastUpdateUser the lastUpdateUser to set
	 */
	public void setLastUpdateUser(String lastUpdateUser) {
		this.lastUpdateUser = lastUpdateUser;
	}

	/**
	 * @return the stockHolderCode
	 */
	public String getStockHolderCode() {
		return stockHolderCode;
	}

	/**
	 * @param stockHolderCode the stockHolderCode to set
	 */
	public void setStockHolderCode(String stockHolderCode) {
		this.stockHolderCode = stockHolderCode;
	}

	/**
	 * @return the shipmentPrefix
	 */
	public String getShipmentPrefix() {
		return shipmentPrefix;
	}

	/**
	 * @param shipmentPrefix the shipmentPrefix to set
	 */
	public void setShipmentPrefix(String shipmentPrefix) {
		this.shipmentPrefix = shipmentPrefix;
	}
	/**
	 * 
	 * 	Method		:	DocumentFilterVO.getAwbDestination
	 *	Added by 	:	A-6858 on 13-Mar-2018
	 * 	Used for 	:
	 *	Parameters	:	@return 
	 *	Return type	: 	String
	 */
	public String getAwbDestination() {
		return awbDestination;
	}
	/**
	 * 
	 * 	Method		:	DocumentFilterVO.setAwbDestination
	 *	Added by 	:	A-6858 on 13-Mar-2018
	 * 	Used for 	:
	 *	Parameters	:	@param awbDestination 
	 *	Return type	: 	void
	 */
	public void setAwbDestination(String awbDestination) {
		this.awbDestination = awbDestination;
	}
	/**
	 * @return the awbOrigin
	 */
	public String getAwbOrigin() {
		return awbOrigin;
	}
	/**
	 * @param awbOrigin the awbOrigin to set
	 */
	public void setAwbOrigin(String awbOrigin) {
		this.awbOrigin = awbOrigin;
	}
	public boolean isSkipStockLocking() {
		return skipStockLocking;
	}
	public void setSkipStockLocking(boolean skipStockLocking) {
		this.skipStockLocking = skipStockLocking;
	} 

}
