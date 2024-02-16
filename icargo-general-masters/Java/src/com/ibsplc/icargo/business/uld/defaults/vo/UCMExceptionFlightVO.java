/*
 * UCMExceptionFlightVO.java Created on Jan 5, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.business.uld.defaults.vo;

import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;

/**
 * 
 * @author A-1936 
 *  This is the Value Object corresponding to the Entity
 *  UCMExceptionFlight
 */
public class UCMExceptionFlightVO extends AbstractVO {

	/**
	 * The companyCode
	 */
	private String companyCode;

	/**
	 * The carrierId
	 */
	private int carrierId;
	
	
	private String  opeartionalFlag;

	/**
	 * The flightNumber
	 */
	private String flightNumber;

	/**
	 * The legSerialNumber
	 */
	private int legSerialNumber;

	/**
	 * The flightSequenceNumber
	 */
	private long flightSequenceNumber;
	/**
	 * The carrierCode
	 */

	private String carrierCode;
	
	
    /**
     * The FlightDate 
     * 
     */
	private LocalDate flightDate;
    /**
     * The Pol 
     */
	private String pol;

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
	 * @return Returns the opeartionalFlag.
	 */
	public String getOpeartionalFlag() {
		return opeartionalFlag;
	}

	/**
	 * @param opeartionalFlag The opeartionalFlag to set.
	 */
	public void setOpeartionalFlag(String opeartionalFlag) {
		this.opeartionalFlag = opeartionalFlag;
	}

}
