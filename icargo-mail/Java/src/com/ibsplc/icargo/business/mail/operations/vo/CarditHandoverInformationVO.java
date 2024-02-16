/*
 * CarditHandoverInformationVO.java Created on Jun 30, 2016
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
 * @author A-5991
 *
 */
/*
 *  Revision History
 *--------------------------------------------------------------------------
 *  Revision    Date                           Author           Description
 * -------------------------------------------------------------------------
 *  0.1         JUN 30, 2016                   A-5991           First Draft
 */
 public class CarditHandoverInformationVO extends AbstractVO {

	/**
	 * This field is the unique key identifying a CARDIT
	 * cardit key is a combination of sender ID, consignment-document-number,
	 * despatch year
	 */
	private String carditKey;
	/**
	 * Cardit Type
	 */
	private String carditType;
	
	/* Handover Origin (Acceptance) */
	/**
	 * This field indicates acceptance(Handover Origin) location qualifier
	 * eg: 84 - place of Acceptance(Handover Origin)
	 */
	private String handoverOriginLocationQualifier;

	/**
	 * Handover Origin Place 
	 */
	private String handoverOriginLocationIdentifier;
	
	/**
	 * Handover Origin Location 
	 */
	private String handoverOriginLocationName;
	
	/**
	 * This field indicates code list responsible agency for place of Handover Origin(Acceptance)
	 */
	private String handoverOriginCodeListAgency;

	/**
	 * This field indicates code list qualifier of  place of 
	 */
	private String handoverOriginCodeListQualifier;

	/* Handover Destination (Delivery) */
	/**
	 * This field indicates delivery(Handover Destination) location qualifier
	 * eg: 1 - place of Delivery(Handover Destination
	 */
	private String handoverDestnLocationQualifier;

	/**
	 * Handover destination Location Identifier
	 */
	private String handoverDestnLocationIdentifier;
	
	/**
	 * Handover destination Location Name 
	 */
	private String handoverDestnLocationName;
	
	/**
	 * This field indicates code list responsible agency for place of Handover Destination(Delivery)
	 */
	private String handoverDestnCodeListAgency;

	/**
	 * This field indicates code list qualifier of  place of Handover Destination(Delivery)
	 */
	private String handoverDestnCodeListQualifier;

	/**
	 * This field indicates transport stage qualifier
	 * eg: Z90
	 */
	private String transportStageQualifier;

	/**
	 * This field is the qualifier for handOver date/time
	 * eg: 234,63
	 */
	private String dateTimePeriodQualifier;

	/**
	 * This field is for Origin Cut Off(Collection) date/time Period
	 */
	private LocalDate originCutOffPeriod;

	/**
	 * This field is for Destination Cut Off Period (Delivery) date/time Period
	 */
	private LocalDate destinationCutOffPeriod;

	/**
	 * This field is format qualifier of date and time
	 * eg: 201 indicates the format YYMMDDHHMM
	 * eg: 101 indicates the format YYMMDD
	 */
	private String dateTimeFormatQualifier;
	
	/**
	 * The following methods are the setters and getters of Transport Information fields
	 */
	public final void setCarditKey(String carditKey){
		this.carditKey = carditKey;
	}

	public final String getCarditKey(){
		return this.carditKey;
	}

	/**
	 * @return the handoverDestnCodeListAgency
	 */
	public String getHandoverDestnCodeListAgency() {
		return handoverDestnCodeListAgency;
	}

	/**
	 * @param handoverDestnCodeListAgency the handoverDestnCodeListAgency to set
	 */
	public void setHandoverDestnCodeListAgency(String handoverDestnCodeListAgency) {
		this.handoverDestnCodeListAgency = handoverDestnCodeListAgency;
	}

	/**
	 * @return the handoverDestnCodeListQualifier
	 */
	public String getHandoverDestnCodeListQualifier() {
		return handoverDestnCodeListQualifier;
	}

	/**
	 * @param handoverDestnCodeListQualifier the handoverDestnCodeListQualifier to set
	 */
	public void setHandoverDestnCodeListQualifier(
			String handoverDestnCodeListQualifier) {
		this.handoverDestnCodeListQualifier = handoverDestnCodeListQualifier;
	}

	/**
	 * @return the handoverDestnLocationQualifier
	 */
	public String getHandoverDestnLocationQualifier() {
		return handoverDestnLocationQualifier;
	}

	/**
	 * @param handoverDestnLocationQualifier the handoverDestnLocationQualifier to set
	 */
	public void setHandoverDestnLocationQualifier(
			String handoverDestnLocationQualifier) {
		this.handoverDestnLocationQualifier = handoverDestnLocationQualifier;
	}

	/**
	 * @return the handoverOriginCodeListAgency
	 */
	public String getHandoverOriginCodeListAgency() {
		return handoverOriginCodeListAgency;
	}

	/**
	 * @param handoverOriginCodeListAgency the handoverOriginCodeListAgency to set
	 */
	public void setHandoverOriginCodeListAgency(String handoverOriginCodeListAgency) {
		this.handoverOriginCodeListAgency = handoverOriginCodeListAgency;
	}

	/**
	 * @return the handoverOriginCodeListQualifier
	 */
	public String getHandoverOriginCodeListQualifier() {
		return handoverOriginCodeListQualifier;
	}

	/**
	 * @param handoverOriginCodeListQualifier the handoverOriginCodeListQualifier to set
	 */
	public void setHandoverOriginCodeListQualifier(
			String handoverOriginCodeListQualifier) {
		this.handoverOriginCodeListQualifier = handoverOriginCodeListQualifier;
	}

	/**
	 * @return the handoverOriginLocationQualifier
	 */
	public String getHandoverOriginLocationQualifier() {
		return handoverOriginLocationQualifier;
	}

	/**
	 * @param handoverOriginLocationQualifier the handoverOriginLocationQualifier to set
	 */
	public void setHandoverOriginLocationQualifier(
			String handoverOriginLocationQualifier) {
		this.handoverOriginLocationQualifier = handoverOriginLocationQualifier;
	}

	/**
	 * @return the handoverDestnLocationIdentifier
	 */
	public String getHandoverDestnLocationIdentifier() {
		return handoverDestnLocationIdentifier;
	}

	/**
	 * @param handoverDestnLocationIdentifier the handoverDestnLocationIdentifier to set
	 */
	public void setHandoverDestnLocationIdentifier(
			String handoverDestnLocationIdentifier) {
		this.handoverDestnLocationIdentifier = handoverDestnLocationIdentifier;
	}

	/**
	 * @return the handoverDestnLocationName
	 */
	public String getHandoverDestnLocationName() {
		return handoverDestnLocationName;
	}

	/**
	 * @param handoverDestnLocationName the handoverDestnLocationName to set
	 */
	public void setHandoverDestnLocationName(String handoverDestnLocationName) {
		this.handoverDestnLocationName = handoverDestnLocationName;
	}

	/**
	 * @return the handoverOriginLocationIdentifier
	 */
	public String getHandoverOriginLocationIdentifier() {
		return handoverOriginLocationIdentifier;
	}

	/**
	 * @param handoverOriginLocationIdentifier the handoverOriginLocationIdentifier to set
	 */
	public void setHandoverOriginLocationIdentifier(
			String handoverOriginLocationIdentifier) {
		this.handoverOriginLocationIdentifier = handoverOriginLocationIdentifier;
	}

	/**
	 * @return the handoverOriginLocationName
	 */
	public String getHandoverOriginLocationName() {
		return handoverOriginLocationName;
	}

	/**
	 * @param handoverOriginLocationName the handoverOriginLocationName to set
	 */
	public void setHandoverOriginLocationName(String handoverOriginLocationName) {
		this.handoverOriginLocationName = handoverOriginLocationName;
	}

	/**
	 * @return the transportStageQualifier
	 */
	public String getTransportStageQualifier() {
		return transportStageQualifier;
	}

	/**
	 * @param transportStageQualifier the transportStageQualifier to set
	 */
	public void setTransportStageQualifier(String transportStageQualifier) {
		this.transportStageQualifier = transportStageQualifier;
	}

	/**
	 * @return the dateTimeFormatQualifier
	 */
	public String getDateTimeFormatQualifier() {
		return dateTimeFormatQualifier;
	}

	/**
	 * @param dateTimeFormatQualifier the dateTimeFormatQualifier to set
	 */
	public void setDateTimeFormatQualifier(String dateTimeFormatQualifier) {
		this.dateTimeFormatQualifier = dateTimeFormatQualifier;
	}

	/**
	 * @return the dateTimePeriodQualifier
	 */
	public String getDateTimePeriodQualifier() {
		return dateTimePeriodQualifier;
	}

	/**
	 * @param dateTimePeriodQualifier the dateTimePeriodQualifier to set
	 */
	public void setDateTimePeriodQualifier(String dateTimePeriodQualifier) {
		this.dateTimePeriodQualifier = dateTimePeriodQualifier;
	}

	/**
	 * @return the destinationCutOffPeriod
	 */
	public LocalDate getDestinationCutOffPeriod() {
		return destinationCutOffPeriod;
	}

	/**
	 * @param destinationCutOffPeriod the destinationCutOffPeriod to set
	 */
	public void setDestinationCutOffPeriod(LocalDate destinationCutOffPeriod) {
		this.destinationCutOffPeriod = destinationCutOffPeriod;
	}

	/**
	 * @return the originCutOffPeriod
	 */
	public LocalDate getOriginCutOffPeriod() {
		return originCutOffPeriod;
	}

	/**
	 * @param originCutOffPeriod the originCutOffPeriod to set
	 */
	public void setOriginCutOffPeriod(LocalDate originCutOffPeriod) {
		this.originCutOffPeriod = originCutOffPeriod;
	}

	/**
	 * @return the carditType
	 */
	public String getCarditType() {
		return carditType;
	}

	/**
	 * @param carditType the carditType to set
	 */
	public void setCarditType(String carditType) {
		this.carditType = carditType;
	}

}