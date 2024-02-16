/*
 * ULDAcquittalJobScheduleVO.java Created on Jun 30, 2016
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.operations.vo;

import java.util.HashMap;
import java.util.Map;

import com.ibsplc.icargo.framework.jobscheduler.vo.JobScheduleVO;

/**
 * @author A-3109
 */
public class ULDAcquittalJobScheduleVO extends JobScheduleVO {

	private static final String MTK_DEF_CMPCOD = "MTK_DEF_CMPCOD";
	private static final String MTK_FLIGHT_CARRIER = "MTK_DEF_FLIGHT_CARRIER";
	private static final String MTK_FLIGHT_NUMBER = "MTK_DEF_FLIGHT_NUMBER";
	private static final String MTK_FLIGHT_SEQUENCE = "MTK_DEF_FLIGHT_SEQUENCE";
	private static final String MTK_FLIGHT_DATE = "MTK_DEF_FLIGHT_DATE";
	private static final String MTK_FLIGHT_LEG_SERIAL = "MTK_DEF_FLIGHT_LEG_SERIAL";
	private static final String MTK_FLIGHT_LEG_ORIGIN = "MTK_DEF_FLIGHT_LEG_ORIGIN";
	private static final String MTK_FLIGHT_LEG_DESTINATION = "MTK_DEF_FLIGHT_LEG_DESTINATION";

	public static final String JOB_ID = "404";
	public static final String JOB_NAME = "MTK_DEF_ULD_ACQUITTAL_JOB";

	private String companyCode;
	private String flightNumber;
	private String carrierId;
	private String flightSequenceNumber;
	private String flightDate;
	private String legSerialNumber;
	private String legOrigin;
	private String legDestination;

	private static Map<String, Integer> map;

	static {
		map = new HashMap<String, Integer>();
		map.put(MTK_DEF_CMPCOD, 1);
		map.put(MTK_FLIGHT_CARRIER, 2);
		map.put(MTK_FLIGHT_NUMBER, 3);
		map.put(MTK_FLIGHT_SEQUENCE, 4);
		map.put(MTK_FLIGHT_DATE, 5);
		map.put(MTK_FLIGHT_LEG_SERIAL, 6);
		map.put(MTK_FLIGHT_LEG_ORIGIN, 7);
		map.put(MTK_FLIGHT_LEG_DESTINATION, 8);
	}

	/**
	 * @see com.ibsplc.icargo.framework.jobscheduler.vo.JobScheduleVO#getPropertyCount()
	 */
	@Override
	public int getPropertyCount() {
		return map.size();
	}

	/**
	 * @see com.ibsplc.icargo.framework.jobscheduler.vo.JobScheduleVO#getIndex(java.lang.String)
	 */
	@Override
	public int getIndex(String key) {
		return map.get(key);
	}

	/**
	 * @see com.ibsplc.icargo.framework.jobscheduler.vo.JobScheduleVO#getValue(int)
	 */
	@Override
	public String getValue(int index) {
		switch (index) {
		case 1:
			{
			return companyCode;
			}
		case 2:
		{
			return carrierId;
		}
		case 3:
			{
			return flightNumber;
			}
		case 4:
		{
			return flightSequenceNumber;
		}
		case 5:
			{
			return flightDate;
			}
		case 6:
		{
			return legSerialNumber;
		}
		case 7:
			{
			return legOrigin;
			}
		case 8:
		{
			return legDestination;
		}
		default:
			{
			return null;
		}
		}

	}

	/**
	 * @see com.ibsplc.icargo.framework.jobscheduler.vo.JobScheduleVO#setValue(int,
	 *      java.lang.String)
	 */
	@Override
	public void setValue(int index, String key) {
		switch (index) {
		case 1:
			{
			setCompanyCode(key);
			break;
			}
		case 2:
		{
			setCarrierId(key);
			break;
		}
		case 3:
			{
			setFlightNumber(key);
			break;
			}
		case 4:
		{
			setFlightSequenceNumber(key);
			break;
		}
		case 5:
			{
			setFlightDate(key);
			break;
			}
		case 6:
		{
			setLegSerialNumber(key);
			break;
		}
		case 7:
			{
			setLegOrigin(key);
			break;
			}
		case 8:
		{
			setLegDestination(key);
			break;
		}
		default:
			{
			break;
			}
		}
	}

	/**
	 * @return Returns the companyCode.
	 */
	public String getCompanyCode() {
		return this.companyCode;
	}

	/**
	 * @param companyCode
	 *            The companyCode to set.
	 */
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	/**
	 * @return the carrierId
	 */
	public String getCarrierId() {
		return carrierId;
	}

	/**
	 * @param carrierId
	 *            the carrierId to set
	 */
	public void setCarrierId(String carrierId) {
		this.carrierId = carrierId;
	}

	/**
	 * @return the flightNumber
	 */
	public String getFlightNumber() {
		return flightNumber;
	}

	/**
	 * @param flightNumber
	 *            the flightNumber to set
	 */
	public void setFlightNumber(String flightNumber) {
		this.flightNumber = flightNumber;
	}

	/**
	 * @return the flightSequenceNumber
	 */
	public String getFlightSequenceNumber() {
		return flightSequenceNumber;
	}

	/**
	 * @param flightSequenceNumber
	 *            the flightSequenceNumber to set
	 */
	public void setFlightSequenceNumber(String flightSequenceNumber) {
		this.flightSequenceNumber = flightSequenceNumber;
	}

	/**
	 * @return the legDestination
	 */
	public String getLegDestination() {
		return legDestination;
	}

	/**
	 * @param legDestination
	 *            the legDestination to set
	 */
	public void setLegDestination(String legDestination) {
		this.legDestination = legDestination;
	}

	/**
	 * @return the legOrigin
	 */
	public String getLegOrigin() {
		return legOrigin;
	}

	/**
	 * @param legOrigin
	 *            the legOrigin to set
	 */
	public void setLegOrigin(String legOrigin) {
		this.legOrigin = legOrigin;
	}

	/**
	 * @return the legSerialNumber
	 */
	public String getLegSerialNumber() {
		return legSerialNumber;
	}

	/**
	 * @param legSerialNumber
	 *            the legSerialNumber to set
	 */
	public void setLegSerialNumber(String legSerialNumber) {
		this.legSerialNumber = legSerialNumber;
	}

	/**
	 * @return the flightDate
	 */
	public String getFlightDate() {
		return flightDate;
	}

	/**
	 * @param flightDate
	 *            the flightDate to set
	 */
	public void setFlightDate(String flightDate) {
		this.flightDate = flightDate;
	}

}
