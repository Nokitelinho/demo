/*
 * ResiditRestrictonVO.java Created on Jun 30, 2016
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.operations.vo;

import com.ibsplc.xibase.server.framework.vo.AbstractVO;

/**
 * @author a-3109
 * 
 */
public class ResiditRestrictonFilterVO extends AbstractVO {

	private String companyCode;
	private String airportCodeFilter;

	private String carrierCodeFilter;
	private String paCodeFilter;

	/**
	 * @return Returns the airportCodeFilter.
	 */
	public String getAirportCodeFilter() {
		return airportCodeFilter;
	}

	/**
	 * @param airportCodeFilter
	 *            The airportCodeFilter to set.
	 */
	public void setAirportCodeFilter(String airportCodeFilter) {
		this.airportCodeFilter = airportCodeFilter;
	}

	/**
	 * @return Returns the carrierCodeFilter.
	 */
	public String getCarrierCodeFilter() {
		return carrierCodeFilter;
	}

	/**
	 * @param carrierCodeFilter
	 *            The carrierCodeFilter to set.
	 */
	public void setCarrierCodeFilter(String carrierCodeFilter) {
		this.carrierCodeFilter = carrierCodeFilter;
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
	 * @return Returns the paCodeFilter.
	 */
	public String getPaCodeFilter() {
		return paCodeFilter;
	}

	/**
	 * @param paCodeFilter
	 *            The paCodeFilter to set.
	 */
	public void setPaCodeFilter(String paCodeFilter) {
		this.paCodeFilter = paCodeFilter;
	}

}
