/*
 * MailActualDetailFilterVO.java Created on JUN 30, 2016
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.operations.vo;

import com.ibsplc.xibase.server.framework.vo.AbstractVO;

/**
 * TODO Add the purpose of this class
 *
 * @author A-5991
 *
 */
/*
 *  Revision History
 *--------------------------------------------------------------------------
 *  Revision 	Date      	           		   Author			Description
 * -------------------------------------------------------------------------
 *  0.1			JUN 30, 2016				   A-5991			First Draft
 */
public class MailActualDetailFilterVO extends AbstractVO {

	private String companyCode;

	private String airlineCode;

	private String postalAuthorityCode;

	private int carrierIdentifier;

	private String flightCarrierCode;

	private String flightNumber;

	private String mailCategory;

	private String activity;

	private String slaStatus;
	
	private int pageNumber;
	

	/**
	 * @return Returns the activity.
	 */
	public String getActivity() {
		return activity;
	}

	/**
	 * @param activity The activity to set.
	 */
	public void setActivity(String activity) {
		this.activity = activity;
	}

	/**
	 * @return Returns the airlineCode.
	 */
	public String getAirlineCode() {
		return airlineCode;
	}

	/**
	 * @param airlineCode The airlineCode to set.
	 */
	public void setAirlineCode(String airlineCode) {
		this.airlineCode = airlineCode;
	}

	
	/**
	 * @return Returns the carrierIdentifier.
	 */
	public int getCarrierIdentifier() {
		return carrierIdentifier;
	}

	/**
	 * @param carrierIdentifier The carrierIdentifier to set.
	 */
	public void setCarrierIdentifier(int carrierIdentifier) {
		this.carrierIdentifier = carrierIdentifier;
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
	 * @return Returns the flightCarrierCode.
	 */
	public String getFlightCarrierCode() {
		return flightCarrierCode;
	}

	/**
	 * @param flightCarrierCode The flightCarrierCode to set.
	 */
	public void setFlightCarrierCode(String flightCarrierCode) {
		this.flightCarrierCode = flightCarrierCode;
	}

	/**
	 * @return Returns the flightNumber.
	 */
	public String getFlightNumber() {
		return flightNumber;
	}

	/**
	 * @param flightNumber The flightNumber to set.
	 */
	public void setFlightNumber(String flightNumber) {
		this.flightNumber = flightNumber;
	}

	/**
	 * @return Returns the mailCategory.
	 */
	public String getMailCategory() {
		return mailCategory;
	}

	/**
	 * @param mailCategory The mailCategory to set.
	 */
	public void setMailCategory(String mailCategory) {
		this.mailCategory = mailCategory;
	}

	/**
	 * @return Returns the postalAuthorityCode.
	 */
	public String getPostalAuthorityCode() {
		return postalAuthorityCode;
	}

	/**
	 * @param postalAuthorityCode The postalAuthorityCode to set.
	 */
	public void setPostalAuthorityCode(String postalAuthorityCode) {
		this.postalAuthorityCode = postalAuthorityCode;
	}

	/**
	 * @return Returns the slaStatus.
	 */
	public String getSlaStatus() {
		return slaStatus;
	}

	/**
	 * @param slaStatus The slaStatus to set.
	 */
	public void setSlaStatus(String slaStatus) {
		this.slaStatus = slaStatus;
	}

	/**
	 * @return Returns the pageNumber.
	 */
	public int getPageNumber() {
		return pageNumber;
	}

	/**
	 * @param pageNumber The pageNumber to set.
	 */
	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

}
