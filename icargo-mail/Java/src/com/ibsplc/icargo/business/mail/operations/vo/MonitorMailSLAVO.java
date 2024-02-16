/*
 * MonitorMailSLAVO.java Created on Jun 30, 2016
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
 * 
 * @author a-3109
 * 
 */
public class MonitorMailSLAVO extends AbstractVO {
	/**
	 * Mail status - Accepted
	 */
	public static final String MAILSTATUS_ACCEPTED = "ACP";

	/**
	 * Mail status - Arrived
	 */
	public static final String MAILSTATUS_ARRIVED = "ARR";

	/**
	 * Mail status - Delivered
	 */
	public static final String MAILSTATUS_DELIVERED = "DLV";

	/**
	 * Mail status - Manifested
	 */
	public static final String MAILSTATUS_MANIFESTED = "MFT";

	/**
	 * Mail status - Offloaded
	 */
	public static final String MAILSTATUS_OFFLOADED = "OFL";

	/**
	 * Service level activity - Acceptance to departure
	 */
	public static final String SLAACTIVITY_ACCEPTANCE_TO_DEPARTURE = "A";

	/**
	 * Service level activity - Arrival to delivery
	 */
	public static final String SLAACTIVITY_ARRIVAL_TO_DELIVERY = "D";

	/**
	 * Company code
	 */
	private String companyCode;

	/**
	 * Mail bag number
	 */
	private String mailBagNumber;

	/**
	 * Service level activity(SLA). Possible values can be ACP-Accepted,
	 * ARR-Arrived, DLV-Delivered,OFL-Offloaded, MFT-Manifested
	 */
	private String activity;

	/**
	 * Operation flag
	 */
	private String operationFlag;

	/**
	 * Scan date and time
	 */
	private LocalDate scanTime;

	/**
	 * Airline identifier
	 */
	private int airlineIdentifier;

	/**
	 * Airline code
	 */
	private String airlineCode;

	/**
	 * Flight carrier identifier
	 */
	private int flightCarrierIdentifier;

	/**
	 * Flight carrier code
	 */
	private String flightCarrierCode;

	/**
	 * Flight number
	 */
	private String flightNumber;

	/**
	 * @return Returns the activity.
	 */
	public String getActivity() {
		return activity;
	}

	/**
	 * @param activity
	 *            The activity to set.
	 */
	public void setActivity(String activity) {
		this.activity = activity;
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
	 * @return Returns the mailBagNumber.
	 */
	public String getMailBagNumber() {
		return mailBagNumber;
	}

	/**
	 * @param mailBagNumber
	 *            The mailBagNumber to set.
	 */
	public void setMailBagNumber(String mailBagNumber) {
		this.mailBagNumber = mailBagNumber;
	}

	/**
	 * @return Returns the operationFlag.
	 */
	public String getOperationFlag() {
		return operationFlag;
	}

	/**
	 * @param operationFlag
	 *            The operationFlag to set.
	 */
	public void setOperationFlag(String operationFlag) {
		this.operationFlag = operationFlag;
	}

	/**
	 * @return Returns the scanTime.
	 */
	public LocalDate getScanTime() {
		return scanTime;
	}

	/**
	 * @param scanTime
	 *            The scanTime to set.
	 */
	public void setScanTime(LocalDate scanTime) {
		this.scanTime = scanTime;
	}

	/**
	 * @return Returns the airlineCode.
	 */
	public String getAirlineCode() {
		return airlineCode;
	}

	/**
	 * @param airlineCode
	 *            The airlineCode to set.
	 */
	public void setAirlineCode(String airlineCode) {
		this.airlineCode = airlineCode;
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
	 * @return Returns the flightCarrierCode.
	 */
	public String getFlightCarrierCode() {
		return flightCarrierCode;
	}

	/**
	 * @param flightCarrierCode
	 *            The flightCarrierCode to set.
	 */
	public void setFlightCarrierCode(String flightCarrierCode) {
		this.flightCarrierCode = flightCarrierCode;
	}

	/**
	 * @return Returns the flightCarrierIdentifier.
	 */
	public int getFlightCarrierIdentifier() {
		return flightCarrierIdentifier;
	}

	/**
	 * @param flightCarrierIdentifier
	 *            The flightCarrierIdentifier to set.
	 */
	public void setFlightCarrierIdentifier(int flightCarrierIdentifier) {
		this.flightCarrierIdentifier = flightCarrierIdentifier;
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

}
