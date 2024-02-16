/*
 * GPAContractVO.java Created on JUL 23, 2018
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
 * @author A-6986
 *
 */
public class GPAContractVO extends AbstractVO{

	private String companyCode;
	private String paCode;
	private String originAirports;
	private String destinationAirports;
	private String contractIDs;
	private String regions;
	private String cidFromDates;
	private String cidToDates;
	private LocalDate lastUpdatedTime;
	private String lastUpdatedUser;
	private String conOperationFlags;
	//Added by A-8527 for BUG ICRD-308683
	private int sernum;
	private String amot;
	public static final String OPERATION_FLAG_INSERT = "I";

	public static final String OPERATION_FLAG_DELETE = "D";

	public static final String OPERATION_FLAG_UPDATE = "U";

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
	 * @return the origin
	 */
	public String getOriginAirports() {
		return originAirports;
	}

	/**
	 * @param origin the origin to set
	 */
	public void setOriginAirports(String originAirports) {
		this.originAirports = originAirports;
	}

	/**
	 * @return the destination
	 */
	public String getDestinationAirports() {
		return destinationAirports;
	}

	/**
	 * @param destination the destination to set
	 */
	public void setDestinationAirports(String destinationAirports) {
		this.destinationAirports = destinationAirports;
	}

	/**
	 * @return the contractId
	 */
	public String getContractIDs() {
		return contractIDs;
	}

	/**
	 * @param contractId the contractId to set
	 */
	public void setContractIDs(String contractIDs) {
		this.contractIDs = contractIDs;
	}



	/**
	 * @return the cidFromDates
	 */
	public String getCidFromDates() {
		return cidFromDates;
	}

	/**
	 * @param cidFromDates the cidFromDates to set
	 */
	public void setCidFromDates(String cidFromDates) {
		this.cidFromDates = cidFromDates;
	}

	/**
	 * @return the cidToDates
	 */
	public String getCidToDates() {
		return cidToDates;
	}

	/**
	 * @param cidToDates the cidToDates to set
	 */
	public void setCidToDates(String cidToDates) {
		this.cidToDates = cidToDates;
	}

	/**
	 * @return the lastUpdatedTime
	 */
	public LocalDate getLastUpdatedTime() {
		return lastUpdatedTime;
	}

	/**
	 * @param lastUpdatedTime the lastUpdatedTime to set
	 */
	public void setLastUpdatedTime(LocalDate lastUpdatedTime) {
		this.lastUpdatedTime = lastUpdatedTime;
	}

	/**
	 * @return the lastUpdatedUser
	 */
	public String getLastUpdatedUser() {
		return lastUpdatedUser;
	}

	/**
	 * @param lastUpdatedUser the lastUpdatedUser to set
	 */
	public void setLastUpdatedUser(String lastUpdatedUser) {
		this.lastUpdatedUser = lastUpdatedUser;
	}

	/**
	 * @return the operationFlag
	 */
	public String getConOperationFlags() {
		return conOperationFlags;
	}

	/**
	 * @param operationFlag the operationFlag to set
	 */
	public void setConOperationFlags(String conOperationFlags) {
		this.conOperationFlags = conOperationFlags;
	}
	public int getSernum() {
		return sernum;
	}
	public void setSernum(int sernum) {
		this.sernum = sernum;
	}
	/**
	 * @return the region
	 */
	public String getRegions() {
		return regions;
	}
	/**
	 * @param region the region to set
	 */
	public void setRegions(String regions) {
		this.regions = regions;
	}
	/**
	 * @return the amot
	 */
	public String getAmot() {
		return amot;
	}
	/**
	 * @param amot the amot to set
	 */
	public void setAmot(String amot) {
		this.amot = amot;
	}

}
