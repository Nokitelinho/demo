/*
 * MailActivityDetailVO.java Created on JUN 30, 2016
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
 * @author a-5991
 * 
 */
public class MailActivityDetailVO extends AbstractVO {
	/**
	 * SLA status - Success
	 */
	public static final String SLASTATUS_SUCCESS = "S";

	/**
	 * SLA status - Failure
	 */
	public static final String SLASTATUS_FAILURE = "F";

	/**
	 * Monitoring mode - Alarm
	 */
	public static final String MONITORING_MODE_ALARM = "ALARM";

	/**
	 * Monitoring mode - Chaser
	 */
	public static final String MONITORING_MODE_CHASER = "CHASER";

	/**
	 * Monitoring mode - Next Chaser
	 */
	public static final String MONITORING_MODE_NEXT_CHASER = "NEXT_CHASER";

	/**
	 * Company code
	 */
	private String companyCode;

	/**
	 * Mail bag number
	 */
	private String mailBagNumber;

	/**
	 * Service level activity. Possible values can be A-Acceptance to Departure,
	 * D-Arrival to Delivery
	 */
	private String serviceLevelActivity;

	/**
	 * Airline identifier
	 */
	private int airlineIdentifier;

	/**
	 * Airline code
	 */
	private String airlineCode;

	/**
	 * GPA code
	 */
	private String gpaCode;

	/**
	 * SLA identifier
	 */
	private String slaIdentifier;

	/**
	 * Flight carrier identifier
	 */
	private int flightCarrierId;

	/**
	 * Flight number
	 */
	private String flightNumber;

	/**
	 * Flight carrier code
	 */
	private String flightCarrierCode;

	/**
	 * Planned date and time
	 */
	private LocalDate plannedTime;

	/**
	 * Actual date and time
	 */
	private LocalDate actualTime;

	/**
	 * SLA status. Possible values can be S-Success, F-Failure
	 */
	private String slaStatus;

	/**
	 * Mail category code
	 */
	private String mailCategory;

	/**
	 * Service time in Hours
	 */
	private int serviceTime;

	/**
	 * Alert status. Possible values can be Y - Alert message sent, N - Alert
	 * message not sent
	 */
	private String alertStatus;

	/**
	 * Number of chasers sent
	 */
	private int numberOfChasers;

	private int alertTime;

	private int chaserTime;

	private int chaserFrequency;

	private int maximumNumberOfChasers;

	/**
	 * @return Returns the actualTime.
	 */
	public LocalDate getActualTime() {
		return actualTime;
	}

	/**
	 * @param actualTime
	 *            The actualTime to set.
	 */
	public void setActualTime(LocalDate actualTime) {
		this.actualTime = actualTime;
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
	 * @return Returns the flightCarrierId.
	 */
	public int getFlightCarrierId() {
		return flightCarrierId;
	}

	/**
	 * @param flightCarrierId
	 *            The flightCarrierId to set.
	 */
	public void setFlightCarrierId(int flightCarrierId) {
		this.flightCarrierId = flightCarrierId;
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
	 * @return Returns the gpaCode.
	 */
	public String getGpaCode() {
		return gpaCode;
	}

	/**
	 * @param gpaCode
	 *            The gpaCode to set.
	 */
	public void setGpaCode(String gpaCode) {
		this.gpaCode = gpaCode;
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
	 * @return Returns the plannedTime.
	 */
	public LocalDate getPlannedTime() {
		return plannedTime;
	}

	/**
	 * @param plannedTime
	 *            The plannedTime to set.
	 */
	public void setPlannedTime(LocalDate plannedTime) {
		this.plannedTime = plannedTime;
	}

	/**
	 * @return Returns the serviceLevelActivity.
	 */
	public String getServiceLevelActivity() {
		return serviceLevelActivity;
	}

	/**
	 * @param serviceLevelActivity
	 *            The serviceLevelActivity to set.
	 */
	public void setServiceLevelActivity(String serviceLevelActivity) {
		this.serviceLevelActivity = serviceLevelActivity;
	}

	/**
	 * @return Returns the slaIdentifier.
	 */
	public String getSlaIdentifier() {
		return slaIdentifier;
	}

	/**
	 * @param slaIdentifier
	 *            The slaIdentifier to set.
	 */
	public void setSlaIdentifier(String slaIdentifier) {
		this.slaIdentifier = slaIdentifier;
	}

	/**
	 * @return Returns the slaStatus.
	 */
	public String getSlaStatus() {
		return slaStatus;
	}

	/**
	 * @param slaStatus
	 *            The slaStatus to set.
	 */
	public void setSlaStatus(String slaStatus) {
		this.slaStatus = slaStatus;
	}

	/**
	 * @return Returns the mailCategory.
	 */
	public String getMailCategory() {
		return mailCategory;
	}

	/**
	 * @param mailCategory
	 *            The mailCategory to set.
	 */
	public void setMailCategory(String mailCategory) {
		this.mailCategory = mailCategory;
	}

	/**
	 * @return Returns the serviceTime.
	 */
	public int getServiceTime() {
		return serviceTime;
	}

	/**
	 * @param serviceTime
	 *            The serviceTime to set.
	 */
	public void setServiceTime(int serviceTime) {
		this.serviceTime = serviceTime;
	}

	/**
	 * @return Returns the alertStatus.
	 */
	public String getAlertStatus() {
		return alertStatus;
	}

	/**
	 * @param alertStatus
	 *            The alertStatus to set.
	 */
	public void setAlertStatus(String alertStatus) {
		this.alertStatus = alertStatus;
	}

	/**
	 * @return Returns the numberOfChasers.
	 */
	public int getNumberOfChasers() {
		return numberOfChasers;
	}

	/**
	 * @param numberOfChasers
	 *            The numberOfChasers to set.
	 */
	public void setNumberOfChasers(int numberOfChasers) {
		this.numberOfChasers = numberOfChasers;
	}

	/**
	 * @return Returns the alertTime.
	 */
	public int getAlertTime() {
		return alertTime;
	}

	/**
	 * @param alertTime
	 *            The alertTime to set.
	 */
	public void setAlertTime(int alertTime) {
		this.alertTime = alertTime;
	}

	/**
	 * @return Returns the chaserFrequency.
	 */
	public int getChaserFrequency() {
		return chaserFrequency;
	}

	/**
	 * @param chaserFrequency
	 *            The chaserFrequency to set.
	 */
	public void setChaserFrequency(int chaserFrequency) {
		this.chaserFrequency = chaserFrequency;
	}

	/**
	 * @return Returns the chaserTime.
	 */
	public int getChaserTime() {
		return chaserTime;
	}

	/**
	 * @param chaserTime
	 *            The chaserTime to set.
	 */
	public void setChaserTime(int chaserTime) {
		this.chaserTime = chaserTime;
	}

	/**
	 * @return Returns the maximumNumberOfChasers.
	 */
	public int getMaximumNumberOfChasers() {
		return maximumNumberOfChasers;
	}

	/**
	 * @param maximumNumberOfChasers
	 *            The maximumNumberOfChasers to set.
	 */
	public void setMaximumNumberOfChasers(int maximumNumberOfChasers) {
		this.maximumNumberOfChasers = maximumNumberOfChasers;
	}

}
