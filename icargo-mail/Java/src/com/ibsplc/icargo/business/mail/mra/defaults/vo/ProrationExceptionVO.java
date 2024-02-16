
/*

 *

 * ProrationExceptionVO.java Created on Sep 2008

 *

 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.

 *

 * This software is the proprietary information of IBS
 *  Software Services (P) Ltd.

 * Use is subject to license terms.

 */
package com.ibsplc.icargo.business.mail.mra.defaults.vo;


import com.ibsplc.xibase.server.framework.vo.AbstractVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;


/**
 * @author A-3108
 */
public class ProrationExceptionVO extends AbstractVO {

	
	
	/*
	 * companyCode
	 */
	private String companyCode;
	private long mailSequenceNumber;
	
	/*
	 * route
	 */

	private String route;

	
	/*
	 * carrierCode
	 */
	private String carrierCode;
	

	/*
	 * sector
	 */
	private String sector;
	
	/*
	 * 
	 */
	private String consDocNo;
	
	private int consSeqNo;
	
	private int noOfBags;
	
	
	private String poaCode;
	
	/*
	 * exceptionSerialNumber
	 */
	private int exceptionSerialNumber;


	/*
	 * flightCarrierIdentifier
	 */
	private int flightCarrierIdentifier;


	/*
	 * flightNumber
	 */
	private String flightNumber;


	/*
	 * flightSequenceNumber
	 */
	private int flightSequenceNumber;


	/*
	 * segmentSerialNumber
	 */
	private int segmentSerialNumber;


	/*
	 * masterDocumentNumber
	 */
	private String masterDocumentNumber;



	/*
	 * shipmentPrefix
	 */
	private String shipmentPrefix;



	/*
	 * status
	 */
	private String status;



	/*
	 * assignedUser
	 */
	private String assignedUser;



	/*
	 * segmentOrigin
	 */
	private String segmentOrigin;



	/*
	 * segmentDestination
	 */
	private String segmentDestination;


	/*
	 * exceptionCode
	 */
	private String exceptionCode;



	/*
	 * exceptionRemarks
	 */
	private String exceptionRemarks;



	/*
	 * lastUpdatedUser
	 */
	private String lastUpdatedUser;


	/*
	 * triggerPoint
	 */
	private String triggerPoint;


	/*
	 * prorateFactor
	 */
	private double  prorateFactor;

	/*
	 * assignedTime
	 */
	private LocalDate assignedTime;


	/*
	 * resolvedTime
	 */
	private LocalDate resolvedTime;



	/*
	 * flightDate
	 */
	private LocalDate flightDate;



	/*
	 * lastUpdatedTime
	 */
	private LocalDate lastUpdatedTime;


	/*
	 * date
	 */
	private LocalDate date;
	/**
	 * consignmentNotePrefix
	 */
	private String consignmentNotePrefix;


	/*
	 * ownerId
	 */

	private int ownerId;

	/*
	 * duplicateNumber
	 */

	private int duplicateNumber;

	/*
	 * sequenceNumber
	 */

    private int sequenceNumber;


    /*
	 * companyCode
	 */
	private String procedureStatus;

	
	/*
	 * companyCode
	 */
	private String remarks;
	
	
	private String dispatchNo;
	private String mailbagId;//Added for ICRD-205027
	private String destinationOfficeOfExchange;
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
	public String getDispatchNo() {
		return dispatchNo;
	}

	public void setDispatchNo(String dispatchNo) {
		this.dispatchNo = dispatchNo;
	}

	/**
	 * Getter of the property <tt>companyCode</tt>
	 *
	 * @return Returns the companyCode.
	 *
	 */
	public String getCompanyCode()
	{
		return companyCode;
	}

	/**
	 * Setter of the property <tt>companyCode</tt>
	 *
	 * @param companyCode The companyCode to set.
	 *
	 */
	public void setCompanyCode(String companyCode ){
		this.companyCode = companyCode;
	}

	/**
	 * Getter of the property <tt>exceptionSerialNumber</tt>
	 *
	 * @return Returns the exceptionSerialNumber.
	 *
	 */
	public int getExceptionSerialNumber()
	{
		return exceptionSerialNumber;
	}

	/**
	 * Setter of the property <tt>exceptionSerialNumber</tt>
	 *
	 * @param exceptionSerialNumber The exceptionSerialNumber to set.
	 *
	 */
	public void setExceptionSerialNumber(int exceptionSerialNumber ){
		this.exceptionSerialNumber = exceptionSerialNumber;
	}

	/**
	 * Getter of the property <tt>flightCarrierIdentifier</tt>
	 *
	 * @return Returns the flightCarrierIdentifier.
	 *
	 */
	public int getFlightCarrierIdentifier()
	{
		return flightCarrierIdentifier;
	}

	/**
	 * Setter of the property <tt>flightCarrierIdentifier</tt>
	 *
	 * @param flightCarrierIdentifier The flightCarrierIdentifier to set.
	 *
	 */
	public void setFlightCarrierIdentifier(int flightCarrierIdentifier ){
		this.flightCarrierIdentifier = flightCarrierIdentifier;
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
	 * Getter of the property <tt>segmentSerialNumber</tt>
	 *
	 * @return Returns the segmentSerialNumber.
	 *
	 */
	public int getSegmentSerialNumber()
	{
		return segmentSerialNumber;
	}

	/**
	 * Setter of the property <tt>segmentSerialNumber</tt>
	 *
	 * @param segmentSerialNumber The segmentSerialNumber to set.
	 *
	 */
	public void setSegmentSerialNumber(int segmentSerialNumber ){
		this.segmentSerialNumber = segmentSerialNumber;
	}

	

	/**
	 * Getter of the property <tt>shipmentPrefix</tt>
	 *
	 * @return Returns the shipmentPrefix.
	 *
	 */
	public String getShipmentPrefix()
	{
		return shipmentPrefix;
	}

	/**
	 * Setter of the property <tt>shipmentPrefix</tt>
	 *
	 * @param shipmentPrefix The shipmentPrefix to set.
	 *
	 */
	public void setShipmentPrefix(String shipmentPrefix ){
		this.shipmentPrefix = shipmentPrefix;
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
	 * Getter of the property <tt>assignedUser</tt>
	 *
	 * @return Returns the assignedUser.
	 *
	 */
	public String getAssignedUser()
	{
		return assignedUser;
	}

	/**
	 * Setter of the property <tt>assignedUser</tt>
	 *
	 * @param assignedUser The assignedUser to set.
	 *
	 */
	public void setAssignedUser(String assignedUser ){
		this.assignedUser = assignedUser;
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
	 * Getter of the property <tt>exceptionRemarks</tt>
	 *
	 * @return Returns the exceptionRemarks.
	 *
	 */
	public String getExceptionRemarks()
	{
		return exceptionRemarks;
	}

	/**
	 * Setter of the property <tt>exceptionRemarks</tt>
	 *
	 * @param exceptionRemarks The exceptionRemarks to set.
	 *
	 */
	public void setExceptionRemarks(String exceptionRemarks ){
		this.exceptionRemarks = exceptionRemarks;
	}

	/**
	 * Getter of the property <tt>lastUpdatedUser</tt>
	 *
	 * @return Returns the lastUpdatedUser.
	 *
	 */
	public String getLastUpdatedUser()
	{
		return lastUpdatedUser;
	}

	/**
	 * Setter of the property <tt>lastUpdatedUser</tt>
	 *
	 * @param lastUpdatedUser The lastUpdatedUser to set.
	 *
	 */
	public void setLastUpdatedUser(String lastUpdatedUser ){
		this.lastUpdatedUser = lastUpdatedUser;
	}

	/**
	 * Getter of the property <tt>triggerPoint</tt>
	 *
	 * @return Returns the triggerPoint.
	 *
	 */
	public String getTriggerPoint()
	{
		return triggerPoint;
	}

	/**
	 * Setter of the property <tt>triggerPoint</tt>
	 *
	 * @param triggerPoint The triggerPoint to set.
	 *
	 */
	public void setTriggerPoint(String triggerPoint ){
		this.triggerPoint = triggerPoint;
	}


	


	/**
	 * Getter of the property <tt>assignedTime</tt>
	 *
	 * @return Returns the assignedTime.
	 *
	 */
	public LocalDate getAssignedTime() {
			return assignedTime;
	}

	/**
	 * Setter of the property <tt>assignedTime</tt>
	 *
	 * @param assignedTime The assignedTime to set.
	 *
	 */
	public void setAssignedTime(LocalDate assignedTime) {
			this.assignedTime = assignedTime;
	}


	/**
	 * Getter of the property <tt>resolvedTime</tt>
	 *
	 * @return Returns the resolvedTime.
	 *
	 */
	public LocalDate getResolvedTime() {
			return resolvedTime;
	}

	/**
	 * Setter of the property <tt>resolvedTime</tt>
	 *
	 * @param resolvedTime The resolvedTime to set.
	 *
	 */
	public void setResolvedTime(LocalDate resolvedTime) {
			this.resolvedTime = resolvedTime;
	}


	/**
	 * Getter of the property <tt>flightDate</tt>
	 *
	 * @return Returns the flightDate.
	 *
	 */
	public LocalDate getFlightDate() {
			return flightDate;
	}

	/**
	 * Setter of the property <tt>flightDate</tt>
	 *
	 * @param flightDate The flightDate to set.
	 *
	 */
	public void setFlightDate(LocalDate flightDate) {
			this.flightDate = flightDate;
	}



	/**
	 * Getter of the property <tt>lastUpdatedTime</tt>
	 *
	 * @return Returns the lastUpdatedTime.
	 *
	 */
	public LocalDate getLastUpdatedTime() {
			return lastUpdatedTime;
	}

	/**
	 * Setter of the property <tt>lastUpdatedTime</tt>
	 *
	 * @param lastUpdatedTime The lastUpdatedTime to set.
	 *
	 */
	public void setLastUpdatedTime(LocalDate lastUpdatedTime) {
			this.lastUpdatedTime = lastUpdatedTime;
	}



	/**
	 * Getter of the property <tt>date</tt>
	 *
	 * @return Returns the date.
	 *
	 */
	public LocalDate getDate() {
			return date;
	}

	/**
	 * Setter of the property <tt>date</tt>
	 *
	 * @param date The date to set.
	 *
	 */
	public void setDate(LocalDate date) {
			this.date = date;
	}

	/**
	 * Getter of the property <tt>duplicateNumber</tt>
	 *
	 * @return Returns the duplicateNumber.
	 *
	 */
	public int getDuplicateNumber() {
		return duplicateNumber;
	}

	/**
	 * Setter of the property <tt>duplicateNumber</tt>
	 *
	 * @param duplicateNumber The duplicateNumber to set.
	 *
	 */
	public void setDuplicateNumber(int duplicateNumber) {
		this.duplicateNumber = duplicateNumber;
	}


	/**
	 * Getter of the property <tt>ownerId</tt>
	 *
	 * @return Returns the ownerId.
	 *
	 */
	public int getOwnerId() {
		return ownerId;
	}

	/**
	 * Setter of the property <tt>ownerId</tt>
	 *
	 * @param ownerId The ownerId to set.
	 *
	 */
	public void setOwnerId(int ownerId) {
		this.ownerId = ownerId;
	}

	/**
	 * Getter of the property <tt>sequenceNumber</tt>
	 *
	 * @return Returns the sequenceNumber.
	 *
	 */
	public int getSequenceNumber() {
		return sequenceNumber;
	}

	/**
	 * Setter of the property <tt>sequenceNumber</tt>
	 *
	 * @param sequenceNumber The sequenceNumber to set.
	 *
	 */
	public void setSequenceNumber(int sequenceNumber) {
		this.sequenceNumber = sequenceNumber;
	}
	/**
	 * Setter of the property <tt>sector</tt>
	 *
	 * @param sector The sector to set.
	 *
	 */
	public String getSector() {
		return sector;
	}

	/**
	 * @param sector
	 */
	public void setSector(String sector) {
		this.sector = sector;
	}


	
	public String getConsDocNo() {
		return consDocNo;
	}

	public void setConsDocNo(String consDocNo) {
		this.consDocNo = consDocNo;
	}

	public int getNoOfBags() {
		return noOfBags;
	}

	public void setNoOfBags(int noOfBags) {
		this.noOfBags = noOfBags;
	}

	/**
	 * Setter of the property <tt>carrierCode</tt>
	 *
	 * @return carrierCode The carrierCode to set.
	 *
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
	 * Setter of the property <tt>route</tt>
	 *
	 * @return route The route to set.
	 *
	 */
	public String getRoute() {
		return route;
	}

	/**
	 * @param route
	 */
	public void setRoute(String route) {
		this.route = route;
	}

	/**
	 * @return consignmentNotePrefix
	 */
	public String getConsignmentNotePrefix() {
		return consignmentNotePrefix;
	}

	/**
	 * @param consignmentNotePrefix
	 */
	public void setConsignmentNotePrefix(String consignmentNotePrefix) {
		this.consignmentNotePrefix = consignmentNotePrefix;
	}

	/**
	 * @return
	 */
	public String getProcedureStatus() {
		return procedureStatus;
	}

	/**
	 * @param procedureStatus
	 */
	public void setProcedureStatus(String procedureStatus) {
		this.procedureStatus = procedureStatus;
	}

	/**
	 * @return
	 */
	public String getRemarks() {
		return remarks;
	}

	/**
	 * @param remarks
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	/**
	 * @return
	 */
	public String getMasterDocumentNumber() {
		return masterDocumentNumber;
	}

	/**
	 * @param masterDocumentNumber
	 */
	public void setMasterDocumentNumber(String masterDocumentNumber) {
		this.masterDocumentNumber = masterDocumentNumber;
	}

	public int getConsSeqNo() {
		return consSeqNo;
	}

	public void setConsSeqNo(int consSeqNo) {
		this.consSeqNo = consSeqNo;
	}

	public String getPoaCode() {
		return poaCode;
	}

	public void setPoaCode(String poaCode) {
		this.poaCode = poaCode;
	}

	/**
	 * @return the prorateFactor
	 */
	public double getProrateFactor() {
		return prorateFactor;
	}

	/**
	 * @param prorateFactor the prorateFactor to set
	 */
	public void setProrateFactor(double prorateFactor) {
		this.prorateFactor = prorateFactor;
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

	/**
	 * @return the mailSequenceNumber
	 */
	public long getMailSequenceNumber() {
		return mailSequenceNumber;
	}

	/**
	 * @param mailSequenceNumber the mailSequenceNumber to set
	 */
	public void setMailSequenceNumber(long mailSequenceNumber) {
		this.mailSequenceNumber = mailSequenceNumber;
	}


}
