/*
 * FormOneFilterVO.java Created on Nov 3, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.mra.airlinebilling.vo;

import com.ibsplc.xibase.server.framework.vo.AbstractVO;

/**
 * @author A-2524
 *
 */
public class FormOneFilterVO extends AbstractVO {
	private String companyCode;	
	private String airlineCode;
	private String airlineNumber;
	private String clearancePeriod;	
	private String classType;
	private int pageNumber;
	
	private int airlineId;
	
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
	 * @return Returns the airlineNumber.
	 */
	public String getAirlineNumber() {
		return airlineNumber;
	}
	/**
	 * @param airlineNumber The airlineNumber to set.
	 */
	public void setAirlineNumber(String airlineNumber) {
		this.airlineNumber = airlineNumber;
	}
	/**
	 * @return Returns the classType.
	 */
	public String getClassType() {
		return classType;
	}
	/**
	 * @param classType The classType to set.
	 */
	public void setClassType(String classType) {
		this.classType = classType;
	}
	/**
	 * @return Returns the clearancePeriod.
	 */
	public String getClearancePeriod() {
		return clearancePeriod;
	}
	/**
	 * @param clearancePeriod The clearancePeriod to set.
	 */
	public void setClearancePeriod(String clearancePeriod) {
		this.clearancePeriod = clearancePeriod;
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
	

}
