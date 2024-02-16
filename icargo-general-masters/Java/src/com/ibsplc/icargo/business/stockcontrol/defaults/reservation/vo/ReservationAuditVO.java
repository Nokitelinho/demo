/*
 * ReservationAuditVO.java Created on Mar 03, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.stockcontrol.defaults.reservation.vo;

import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.audit.vo.AuditVO;

/**
 * @author A-1954
 * 
 */
public class ReservationAuditVO extends AuditVO {

	/**
	 * constant for stock holder audit product name
	 */
	public static final String MODULENAME = "stockcontrol";

	/**
	 * constant for stock holder audit module name
	 */
	public static final String SUBMODULENAME = "defaults";

	/**
	 * constant for stock holder entity name
	 */
	public static final String ENTITYNAME = "stockcontrol.defaults.reservation.Reservation";

	/**
	 * 
	 */
	public static final String ACTION_RESERVE= "RESERVE AWB";
	
	/**
	 * 
	 */
	public static final String ACTION_EXTEND = "EXTEND RESERVATION";
	
	/**
	 * 
	 */
	public static final String ACTION_CANCEL = "CANCEL RESERVATION";
	
	/**
	 * 
	 */
	public static final String RESERVE_AWB = "Reserve AWB";
	
	/**
	 * 
	 */
	public static final String CANCEL_AWB = "Cancel Reserved AWB";
	
	/**
	 * 
	 */
	public static final String EXTEND_AWB = "Extend Reservation";
	
	private String airportCode;

	private int airlineIdentifier;

	private String documentNumber;

	private int sequenceNumber;

	private String shipmentPrefix;

	private LocalDate lastUpdateTime;

	private String lastUpdateUser;

	/**
	 * @param module
	 * @param submodule
	 * @param entity
	 */
	public ReservationAuditVO(String module, String submodule, String entity) {
		super(module, submodule, entity);
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
	 * @return Returns the airportCode.
	 */
	public String getAirportCode() {
		return airportCode;
	}

	/**
	 * @param airportCode
	 *            The airportCode to set.
	 */
	public void setAirportCode(String airportCode) {
		this.airportCode = airportCode;
	}

	/**
	 * @return Returns the documentNumber.
	 */
	public String getDocumentNumber() {
		return documentNumber;
	}

	/**
	 * @param documentNumber
	 *            The documentNumber to set.
	 */
	public void setDocumentNumber(String documentNumber) {
		this.documentNumber = documentNumber;
	}

	/**
	 * @return Returns the lastUpdateTime.
	 */
	public LocalDate getLastUpdateTime() {
		return lastUpdateTime;
	}

	/**
	 * @param lastUpdateTime
	 *            The lastUpdateTime to set.
	 */
	public void setLastUpdateTime(LocalDate lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

	/**
	 * @return Returns the lastUpdateUser.
	 */
	public String getLastUpdateUser() {
		return lastUpdateUser;
	}

	/**
	 * @param lastUpdateUser
	 *            The lastUpdateUser to set.
	 */
	public void setLastUpdateUser(String lastUpdateUser) {
		this.lastUpdateUser = lastUpdateUser;
	}

	/**
	 * @return Returns the sequenceNumber.
	 */
	public int getSequenceNumber() {
		return sequenceNumber;
	}

	/**
	 * @param sequenceNumber
	 *            The sequenceNumber to set.
	 */
	public void setSequenceNumber(int sequenceNumber) {
		this.sequenceNumber = sequenceNumber;
	}

	/**
	 * @return Returns the shipmentPrefix.
	 */
	public String getShipmentPrefix() {
		return shipmentPrefix;
	}

	/**
	 * @param shipmentPrefix
	 *            The shipmentPrefix to set.
	 */
	public void setShipmentPrefix(String shipmentPrefix) {
		this.shipmentPrefix = shipmentPrefix;
	}
}
