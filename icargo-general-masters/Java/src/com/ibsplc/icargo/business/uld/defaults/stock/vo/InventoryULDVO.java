/*
 * InventoryULDVO.java Created on May 27, 2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.business.uld.defaults.stock.vo;

import java.io.Serializable;
import java.util.Collection;

import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;
/**
 * 
 * @author a-2883
 *
 */
public class InventoryULDVO extends AbstractVO implements Serializable{
	
	private String airportCode;
	private String uldType;
	private String requiredULD;
	private String remarks;
	private String companyCode;
	private String sequenceNumber;
	private LocalDate fromDate;
	private LocalDate toDate;
	private LocalDate inventoryDate;
	private int displayPage;
	private int absoluteIndex;
	private Collection<ULDInventoryDetailsVO> uldInventoryDetailsVOs;
	private String displayDate;
	private String parentPrimaryKey;
	private String serialNumber;
	private String childPrimaryKey;
	private String opFlag;
	private String detailsFlag;
	
	
	
	
	/**
	 * @return the detailsFlag
	 */
	public String getDetailsFlag() {
		return detailsFlag;
	}
	/**
	 * @param detailsFlag the detailsFlag to set
	 */
	public void setDetailsFlag(String detailsFlag) {
		this.detailsFlag = detailsFlag;
	}
	/**
	 * @return the uldInventoryDetailsVOs
	 */
	public Collection<ULDInventoryDetailsVO> getUldInventoryDetailsVOs() {
		return uldInventoryDetailsVOs;
	}
	/**
	 * @param uldInventoryDetailsVOs the uldInventoryDetailsVOs to set
	 */
	public void setUldInventoryDetailsVOs(
			Collection<ULDInventoryDetailsVO> uldInventoryDetailsVOs) {
		this.uldInventoryDetailsVOs = uldInventoryDetailsVOs;
	}
	/**
	 * @return the opFlag
	 */
	public String getOpFlag() {
		return opFlag;
	}
	/**
	 * @param opFlag the opFlag to set
	 */
	public void setOpFlag(String opFlag) {
		this.opFlag = opFlag;
	}
	/**
	 * @return the childPrimaryKey
	 */
	public String getChildPrimaryKey() {
		return childPrimaryKey;
	}
	/**
	 * @param childPrimaryKey the childPrimaryKey to set
	 */
	public void setChildPrimaryKey(String childPrimaryKey) {
		this.childPrimaryKey = childPrimaryKey;
	}
	/**
	 * @return the parentPrimaryKey
	 */
	public String getParentPrimaryKey() {
		return parentPrimaryKey;
	}
	/**
	 * @param parentPrimaryKey the parentPrimaryKey to set
	 */
	public void setParentPrimaryKey(String parentPrimaryKey) {
		this.parentPrimaryKey = parentPrimaryKey;
	}
	/**
	 * @return the serialNumber
	 */
	public String getSerialNumber() {
		return serialNumber;
	}
	/**
	 * @param serialNumber the serialNumber to set
	 */
	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}
	/**
	 * @return the displayDate
	 */
	public String getDisplayDate() {
		return displayDate;
	}
	/**
	 * @param displayDate the displayDate to set
	 */
	public void setDisplayDate(String displayDate) {
		this.displayDate = displayDate;
	}
	/**
	 * @return the airportCode
	 */
	public String getAirportCode() {
		return airportCode;
	}
	/**
	 * @param airportCode the airportCode to set
	 */
	public void setAirportCode(String airportCode) {
		this.airportCode = airportCode;
	}
	
	
	
	/**
	 * @return the remarks
	 */
	public String getRemarks() {
		return remarks;
	}
	/**
	 * @param remarks the remarks to set
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	/**
	 * @return the requiredULD
	 */
	public String getRequiredULD() {
		return requiredULD;
	}
	/**
	 * @param requiredULD the requiredULD to set
	 */
	public void setRequiredULD(String requiredULD) {
		this.requiredULD = requiredULD;
	}
	
	/**
	 * @return the uldType
	 */
	public String getUldType() {
		return uldType;
	}
	/**
	 * @param uldType the uldType to set
	 */
	public void setUldType(String uldType) {
		this.uldType = uldType;
	}
	/**
	 * @return the companyCode
	 */
	public String getCompanyCode() {
		return companyCode;
	}
	/**
	 * @param companyCode the companyCode to set
	 */
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}
	/**
	 * @return the sequenceNumber
	 */
	public String getSequenceNumber() {
		return sequenceNumber;
	}
	/**
	 * @param sequenceNumber the sequenceNumber to set
	 */
	public void setSequenceNumber(String sequenceNumber) {
		this.sequenceNumber = sequenceNumber;
	}
	/**
	 * @return the fromDate
	 */
	public LocalDate getFromDate() {
		return fromDate;
	}
	/**
	 * @param fromDate the fromDate to set
	 */
	public void setFromDate(LocalDate fromDate) {
		this.fromDate = fromDate;
	}
	/**
	 * @return the toDate
	 */
	public LocalDate getToDate() {
		return toDate;
	}
	/**
	 * @param toDate the toDate to set
	 */
	public void setToDate(LocalDate toDate) {
		this.toDate = toDate;
	}
	/**
	 * @return the inventoryDate
	 */
	public LocalDate getInventoryDate() {
		return inventoryDate;
	}
	/**
	 * @param inventoryDate the inventoryDate to set
	 */
	public void setInventoryDate(LocalDate inventoryDate) {
		this.inventoryDate = inventoryDate;
	}
	/**
	 * @return the displayPage
	 */
	public int getDisplayPage() {
		return displayPage;
	}
	/**
	 * @param displayPage the displayPage to set
	 */
	public void setDisplayPage(int displayPage) {
		this.displayPage = displayPage;
	}
	/**
	 * @return the absoluteIndex
	 */
	public int getAbsoluteIndex() {
		return absoluteIndex;
	}
	/**
	 * @param absoluteIndex the absoluteIndex to set
	 */
	public void setAbsoluteIndex(int absoluteIndex) {
		this.absoluteIndex = absoluteIndex;
	}

	
	
	

}
