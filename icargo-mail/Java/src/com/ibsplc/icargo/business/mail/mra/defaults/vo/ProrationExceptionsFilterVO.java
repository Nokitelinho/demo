
/*

 *

 * ProrationExceptionsFilterVO.java Created on Jun 2008

 *

 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.

 *

 * This software is the proprietary information of IBS
 *  Software Services (P) Ltd.

 * Use is subject to license terms.

 */
package com.ibsplc.icargo.business.mail.mra.defaults.vo;

import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;

/**
 * @author A-3044
 *
 */
public class ProrationExceptionsFilterVO extends AbstractVO {

	/*
	 * exceptionCode
	 */
	private String exceptionCode;

	
	/*
	 * carrierCode
	 */
	private String carrierCode;
	

	/*
	 * flightNumber
	 */
	private String flightNumber;


	/*
	 * flightCarrierId is the own airline identifier Eg'1086' for NZ
	 * 
	 */
	private int flightCarrierId;


	/*
	 * flightCarrierCode
	 */
	private String flightCarrierCode;


	/*
	 * flightSequenceNumber
	 */
	private int flightSequenceNumber;

	/*
	 * flightDate
	 */
	private LocalDate flightDate;

	/*
	 * segmentOrigin
	 */
	private String segmentOrigin;


	/*
	 * segmentDestination
	 */
	private String segmentDestination;


	/*
	 * status
	 */
	private String status;


	/*
	 * fromDate
	 */
	private LocalDate fromDate;


	/*
	 * toDate
	 */
	private LocalDate toDate;
	
	/*
	 * displayPage
	 */
	private int displayPage;
	
	/*
	 * dispatchNo
	 */
	private String dispatchNo;
	
	/*
	 * companyCode
	 */
	private String companyCode;
	/*
     * Duplicate Number
     */
    private int duplicateNumber;
    /*
     * Sequence Number
     */
    private int sequenceNumber;
    /*
     * Owner Id
     */
    private int ownerId;
    
    /**
     * screenID
     */
    private String screenID;

    /**
     * asignee
     */
    private String asignee;
    
    private String assignedStatus;
    
    //Added By Deepthi for 27213
    /**
	 * Billing basis
	 */
	private String blgbas;
	/**
	 * consignment Document Number
	 */
	private String csgdocnum;
	/**
	 * consignment Sequence Number
	 */
	private int csgseqnum;
	/**
	 * poaCode
	 */
	private String poaCode;
	//Added as Part of CR ID:ICRD-241594
	private String gpaCode;
	
	//added by A-5223 for ICRD-21098 starts
	private int totalRecords;
	//added by A-5223 for ICRD-21098 ends
	
	private String mailbagId;//Added for ICRD-205027
	private String destinationOfficeOfExchange;
	
	public String getGpaCode() {
		return gpaCode;
	}

	public void setGpaCode(String gpaCode) {
		this.gpaCode = gpaCode;
	}

	public String getDestinationOfficeOfExchange() {
		return destinationOfficeOfExchange;
	}

	public void setDestinationOfficeOfExchange(String destinationOfficeOfExchange) {
		this.destinationOfficeOfExchange = destinationOfficeOfExchange;
	}

	public String getOriginOfficeOfExchange() {
		return originOfficeOfExchange;
	}

	public void setOriginOfficeOfExchange(String originOfficeOfExchange) {
		this.originOfficeOfExchange = originOfficeOfExchange;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getSubClass() {
		return subClass;
	}

	public void setSubClass(String subClass) {
		this.subClass = subClass;
	}

	public String getMailCategory() {
		return mailCategory;
	}

	public void setMailCategory(String mailCategory) {
		this.mailCategory = mailCategory;
	}

	public String getHighestNumberIndicator() {
		return highestNumberIndicator;
	}

	public void setHighestNumberIndicator(String highestNumberIndicator) {
		this.highestNumberIndicator = highestNumberIndicator;
	}

	public String getRegisteredIndicator() {
		return registeredIndicator;
	}

	public void setRegisteredIndicator(String registeredIndicator) {
		this.registeredIndicator = registeredIndicator;
	}

	public String getReceptacleSerialNumber() {
		return receptacleSerialNumber;
	}

	public void setReceptacleSerialNumber(String receptacleSerialNumber) {
		this.receptacleSerialNumber = receptacleSerialNumber;
	}

	private String originOfficeOfExchange;
	private String year;
	private String subClass;
	private String mailCategory;
	private String highestNumberIndicator;
	private String registeredIndicator;
	private String receptacleSerialNumber;
	/**
	 * @return the blgbas
	 */
	public String getBlgbas() {
		return blgbas;
	}

	/**
	 * @param blgbas the blgbas to set
	 */
	public void setBlgbas(String blgbas) {
		this.blgbas = blgbas;
	}

	/**
	 * @return the csgdocnum
	 */
	public String getCsgdocnum() {
		return csgdocnum;
	}

	/**
	 * @param csgdocnum the csgdocnum to set
	 */
	public void setCsgdocnum(String csgdocnum) {
		this.csgdocnum = csgdocnum;
	}

	/**
	 * @return the csgseqnum
	 */
	public int getCsgseqnum() {
		return csgseqnum;
	}

	/**
	 * @param csgseqnum the csgseqnum to set
	 */
	public void setCsgseqnum(int csgseqnum) {
		this.csgseqnum = csgseqnum;
	}

	/**
	 * @return the poaCode
	 */
	public String getPoaCode() {
		return poaCode;
	}

	/**
	 * @param poaCode the poaCode to set
	 */
	public void setPoaCode(String poaCode) {
		this.poaCode = poaCode;
	}

	public String getAssignedStatus() {
		return assignedStatus;
	}

	public void setAssignedStatus(String assignedStatus) {
		this.assignedStatus = assignedStatus;
	}

	/**
	 * @return
	 */
	public int getDuplicateNumber() {
		return duplicateNumber;
	}

	/**
	 * @param duplicateNumber
	 */
	public void setDuplicateNumber(int duplicateNumber) {
		this.duplicateNumber = duplicateNumber;
	}

	/**
	 * @return
	 */
	public int getSequenceNumber() {
		return sequenceNumber;
	}

	/**
	 * @param sequenceNumber
	 */
	public void setSequenceNumber(int sequenceNumber) {
		this.sequenceNumber = sequenceNumber;
	}

	/**
	 * @return
	 */
	public int getOwnerId() {
		return ownerId;
	}

	/**
	 * @param ownerId
	 */
	public void setOwnerId(int ownerId) {
		this.ownerId = ownerId;
	}

	/**
	 * Getter of the property <tt>exceptionCode</tt>
	 *
	 * @return Returns the exceptionCode.
	 *
	 */
	public String getExceptionCode()
	{
		return exceptionCode;
	}

	/**
	 * Setter of the property <tt>exceptionCode</tt>
	 *
	 * @param exceptionCode The exceptionCode to set.
	 *
	 */
	public void setExceptionCode(String exceptionCode ){
		this.exceptionCode = exceptionCode;
	}



	/**
	 * Getter of the property <tt>flightNumber</tt>
	 *
	 * @return Returns the flightNumber.
	 *
	 */
	public String getFlightNumber()
	{
		return flightNumber;
	}

	/**
	 * Setter of the property <tt>flightNumber</tt>
	 *
	 * @param flightNumber The flightNumber to set.
	 *
	 */
	public void setFlightNumber(String flightNumber ){
		this.flightNumber = flightNumber;
	}


	/**
	 * Getter of the property <tt>flightCarrierId</tt>
	 *
	 * @return Returns the flightCarrierId.
	 *
	 */
	public int getFlightCarrierId()
	{
		return flightCarrierId;
	}

	/**
	 * Setter of the property <tt>flightCarrierId</tt>
	 *
	 * @param flightCarrierId The flightCarrierId to set.
	 *
	 */
	public void setFlightCarrierId(int flightCarrierId ){
		this.flightCarrierId = flightCarrierId;
	}



	/**
	 * Getter of the property <tt>flightCarrierCode</tt>
	 *
	 * @return Returns the flightCarrierCode.
	 *
	 */
	public String getFlightCarrierCode()
	{
		return flightCarrierCode;
	}

	/**
	 * Setter of the property <tt>flightCarrierCode</tt>
	 *
	 * @param flightCarrierCode The flightCarrierCode to set.
	 *
	 */
	public void setFlightCarrierCode(String flightCarrierCode ){
		this.flightCarrierCode = flightCarrierCode;
	}



	/**
	 * Getter of the property <tt>flightSequenceNumber</tt>
	 *
	 * @return Returns the flightSequenceNumber.
	 *
	 */
	public int getFlightSequenceNumber()
	{
		return flightSequenceNumber;
	}

	/**
	 * Setter of the property <tt>flightSequenceNumber</tt>
	 *
	 * @param flightSequenceNumber The flightSequenceNumber to set.
	 *
	 */
	public void setFlightSequenceNumber(int flightSequenceNumber ){
		this.flightSequenceNumber = flightSequenceNumber;
	}



	/**
	 * Getter of the property <tt>flightDate</tt>
	 *
	 * @return Returns the flightDate.
	 *
	 */
	public LocalDate getFlightDate()
	{
		return flightDate;
	}

	/**
	 * Setter of the property <tt>flightDate</tt>
	 *
	 * @param flightDate The flightDate to set.
	 *
	 */
	public void setFlightDate(LocalDate flightDate ){
		this.flightDate = flightDate;
	}



	/**
	 * Getter of the property <tt>segmentOrigin</tt>
	 *
	 * @return Returns the segmentOrigin.
	 *
	 */
	public String getSegmentOrigin()
	{
		return segmentOrigin;
	}

	/**
	 * Setter of the property <tt>segmentOrigin</tt>
	 *
	 * @param segmentOrigin The segmentOrigin to set.
	 *
	 */
	public void setSegmentOrigin(String segmentOrigin ){
		this.segmentOrigin = segmentOrigin;
	}



	/**
	 * Getter of the property <tt>segmentDestination</tt>
	 *
	 * @return Returns the segmentDestination.
	 *
	 */
	public String getSegmentDestination()
	{
		return segmentDestination;
	}

	/**
	 * Setter of the property <tt>segmentDestination</tt>
	 *
	 * @param segmentDestination The segmentDestination to set.
	 *
	 */
	public void setSegmentDestination(String segmentDestination ){
		this.segmentDestination = segmentDestination;
	}



	/**
	 * Getter of the property <tt>status</tt>
	 *
	 * @return Returns the status.
	 *
	 */
	public String getStatus()
	{
		return status;
	}

	/**
	 * Setter of the property <tt>status</tt>
	 *
	 * @param status The status to set.
	 *
	 */
	public void setStatus(String status ){
		this.status = status;
	}



	/**
	 * Getter of the property <tt>fromDate</tt>
	 *
	 * @return Returns the fromDate.
	 *
	 */
	public LocalDate getFromDate()
	{
		return fromDate;
	}

	/**
	 * Setter of the property <tt>fromDate</tt>
	 *
	 * @param fromDate The fromDate to set.
	 *
	 */
	public void setFromDate(LocalDate fromDate ){
		this.fromDate = fromDate;
	}

	/**
	 * Getter of the property <tt>toDate</tt>
	 *
	 * @return Returns the toDate.
	 *
	 */
	public LocalDate getToDate()
	{
		return toDate;
	}

	/**
	 * Setter of the property <tt>toDate</tt>
	 *
	 * @param toDate The toDate to set.
	 *
	 */
	public void setToDate(LocalDate toDate ){
		this.toDate = toDate;
	}

	/**
	 * @return
	 */
	public int getDisplayPage() {
		return displayPage;
	}

	/**
	 * @param displayPage
	 */
	public void setDisplayPage(int displayPage) {
		this.displayPage = displayPage;
	}

	

	/**
	 * @return
	 */
	public String getCompanyCode() {
		return companyCode;
	}

	/**
	 * @param companyCode
	 */
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	/**
	 * @return
	 */
	public String getCarrierCode() {
		return carrierCode;
	}

	/**
	 * @param carrierCode
	 */
	public void setCarrierCode(String carrierCode) {
		this.carrierCode = carrierCode;
	}

	/**
	 * @return
	 */
	public String getScreenID() {
		return screenID;
	}

	/**
	 * @param screenID
	 */
	public void setScreenID(String screenID) {
		this.screenID = screenID;
	}

	

	public String getDispatchNo() {
		return dispatchNo;
	}

	public void setDispatchNo(String dispatchNo) {
		this.dispatchNo = dispatchNo;
	}

	/**
	 * @return the asignee
	 */
	public String getAsignee() {
		return asignee;
	}

	/**
	 * @param asignee the asignee to set
	 */
	public void setAsignee(String asignee) {
		this.asignee = asignee;
	}

	/**
	 * 	Getter for totalRecords 
	 *	Added by : a-5223 on 17-Oct-2012
	 * 	Used for : getting total records
	 */
	public int getTotalRecords() {
		return totalRecords;
	}

	/**
	 *  @param totalRecords the totalRecords to set
	 * 	Setter for totalRecords 
	 *	Added by : a-5223 on 17-Oct-2012
	 * 	Used for : setting total records
	 */
	public void setTotalRecords(int totalRecords) {
		this.totalRecords = totalRecords;
	}

	/**
	 * 	Getter for mailbagId 
	 *	Added by : a-6245 on 23-Jun-2017
	 * 	Used for :
	 */
	public String getMailbagId() {
		return mailbagId;
	}

	/**
	 *  @param mailbagId the mailbagId to set
	 * 	Setter for mailbagId 
	 *	Added by : a-6245 on 23-Jun-2017
	 * 	Used for :
	 */
	public void setMailbagId(String mailbagId) {
		this.mailbagId = mailbagId;
	}

	

}
