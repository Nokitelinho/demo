/*
 * RateLineErrorVO.java Created on Jan 22, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.mra.defaults.vo;

import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;

/**
 * @author A-1556
 *
 */
public class RateLineErrorVO extends AbstractVO {

    private String companyCode;
    private String currentRateCardID;
    private String newRateCardID;
    private String origin;
    private String destination;
    private LocalDate currentValidityStartDate;
    private LocalDate currentValidityEndDate;
    private LocalDate newValidityStartDate;
    private LocalDate newValidityEndDate;

    /**
     *
     */
    public RateLineErrorVO() {
        super();

    }

	/**
	 * @return Returns the companyCode.
	 */
	public String getCompanyCode() {
		return companyCode;
	}

	/**
	 * @param companyCode The companyCode to set.
	 */
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	/**
	 * @return Returns the currentRateCardID.
	 */
	public String getCurrentRateCardID() {
		return currentRateCardID;
	}

	/**
	 * @param currentRateCardID The currentRateCardID to set.
	 */
	public void setCurrentRateCardID(String currentRateCardID) {
		this.currentRateCardID = currentRateCardID;
	}

	/**
	 * @return Returns the currentValidityEndDate.
	 */
	public LocalDate getCurrentValidityEndDate() {
		return currentValidityEndDate;
	}

	/**
	 * @param currentValidityEndDate The currentValidityEndDate to set.
	 */
	public void setCurrentValidityEndDate(LocalDate currentValidityEndDate) {
		this.currentValidityEndDate = currentValidityEndDate;
	}

	/**
	 * @return Returns the currentValidityStartDate.
	 */
	public LocalDate getCurrentValidityStartDate() {
		return currentValidityStartDate;
	}

	/**
	 * @param currentValidityStartDate The currentValidityStartDate to set.
	 */
	public void setCurrentValidityStartDate(LocalDate currentValidityStartDate) {
		this.currentValidityStartDate = currentValidityStartDate;
	}

	/**
	 * @return Returns the destination.
	 */
	public String getDestination() {
		return destination;
	}

	/**
	 * @param destination The destination to set.
	 */
	public void setDestination(String destination) {
		this.destination = destination;
	}

	/**
	 * @return Returns the newRateCardID.
	 */
	public String getNewRateCardID() {
		return newRateCardID;
	}

	/**
	 * @param newRateCardID The newRateCardID to set.
	 */
	public void setNewRateCardID(String newRateCardID) {
		this.newRateCardID = newRateCardID;
	}

	/**
	 * @return Returns the newValidityEndDate.
	 */
	public LocalDate getNewValidityEndDate() {
		return newValidityEndDate;
	}

	/**
	 * @param newValidityEndDate The newValidityEndDate to set.
	 */
	public void setNewValidityEndDate(LocalDate newValidityEndDate) {
		this.newValidityEndDate = newValidityEndDate;
	}

	/**
	 * @return Returns the newValidityStartDate.
	 */
	public LocalDate getNewValidityStartDate() {
		return newValidityStartDate;
	}

	/**
	 * @param newValidityStartDate The newValidityStartDate to set.
	 */
	public void setNewValidityStartDate(LocalDate newValidityStartDate) {
		this.newValidityStartDate = newValidityStartDate;
	}

	/**
	 * @return Returns the origin.
	 */
	public String getOrigin() {
		return origin;
	}

	/**
	 * @param origin The origin to set.
	 */
	public void setOrigin(String origin) {
		this.origin = origin;
	}






}
