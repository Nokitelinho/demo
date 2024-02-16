/*
 * DSNPopUpFilterVO.java Created on FEB 19, 2008
 *
 * Copyright 2008 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.mra.defaults.vo;

import com.ibsplc.xibase.server.framework.vo.AbstractVO;

/**
 * @author A-3227 RENO K ABRAHAM
 *
 */
public class DSNPopUpFilterVO extends AbstractVO {
	
	private String companyCode;
	
	private String dsn;
	
	private String dsnDate;
	
	private String fromScreen;
	
	//for pagination
	private int absoluteIndex;

	private int pageNumber;
	

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
	 * @return the dsn
	 */
	public String getDsn() {
		return dsn;
	}

	/**
	 * @param dsn the dsn to set
	 */
	public void setDsn(String dsn) {
		this.dsn = dsn;
	}

	/**
	 * @return the dsnDate
	 */
	public String getDsnDate() {
		return dsnDate;
	}

	/**
	 * @param dsnDate the dsnDate to set
	 */
	public void setDsnDate(String dsnDate) {
		this.dsnDate = dsnDate;
	}

	/**
	 * @return the pageNumber
	 */
	public int getPageNumber() {
		return pageNumber;
	}

	/**
	 * @param pageNumber the pageNumber to set
	 */
	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	/**
	 * @return the fromScreen
	 */
	public String getFromScreen() {
		return fromScreen;
	}

	/**
	 * @param fromScreen the fromScreen to set
	 */
	public void setFromScreen(String fromScreen) {
		this.fromScreen = fromScreen;
	}

}
