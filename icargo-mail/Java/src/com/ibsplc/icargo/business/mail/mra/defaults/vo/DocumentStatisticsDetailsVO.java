/* DocumentStatisticsDetailsVO.java created on Sep 02-2008,
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.  
 * This software is the proprietary information of
 *  IBS Software Services (P) Ltd.    
 * Use is subject to license terms. 
 *  
 */
package com.ibsplc.icargo.business.mail.mra.defaults.vo;

import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;

/**
 * @author a-3429
 * 
 */
public class DocumentStatisticsDetailsVO extends AbstractVO {

	private String carrierCode;

	private String flightNo;

	private LocalDate flightDate;

	private String noOfDocuments;

	private String toBeRateAudited;

	private String rateAudited;

	private String accounted;

	private String flightStatus;

	/**
	 * @return the accounted
	 */
	public String getAccounted() {
		return accounted;
	}

	/**
	 * @param accounted
	 *            the accounted to set
	 */
	public void setAccounted(String accounted) {
		this.accounted = accounted;
	}

	/**
	 * @return the flightDate
	 */
	public LocalDate getFlightDate() {
		return flightDate;
	}

	/**
	 * @param flightDate
	 *            the flightDate to set
	 */
	public void setFlightDate(LocalDate flightDate) {
		this.flightDate = flightDate;
	}

	/**
	 * @return the flightNo
	 */
	public String getFlightNo() {
		return flightNo;
	}

	/**
	 * @param flightNo
	 *            the flightNo to set
	 */
	public void setFlightNo(String flightNo) {
		this.flightNo = flightNo;
	}

	/**
	 * @return the flightStatus
	 */
	public String getFlightStatus() {
		return flightStatus;
	}

	/**
	 * @param flightStatus
	 *            the flightStatus to set
	 */
	public void setFlightStatus(String flightStatus) {
		this.flightStatus = flightStatus;
	}

	/**
	 * @return the noOfDocuments
	 */
	public String getNoOfDocuments() {
		return noOfDocuments;
	}

	/**
	 * @param noOfDocuments
	 *            the noOfDocuments to set
	 */
	public void setNoOfDocuments(String noOfDocuments) {
		this.noOfDocuments = noOfDocuments;
	}

	/**
	 * @return the rateAudited
	 */
	public String getRateAudited() {
		return rateAudited;
	}

	/**
	 * @param rateAudited
	 *            the rateAudited to set
	 */
	public void setRateAudited(String rateAudited) {
		this.rateAudited = rateAudited;
	}

	/**
	 * @return the toBeRateAudited
	 */
	public String getToBeRateAudited() {
		return toBeRateAudited;
	}

	/**
	 * @param toBeRateAudited
	 *            the toBeRateAudited to set
	 */
	public void setToBeRateAudited(String toBeRateAudited) {
		this.toBeRateAudited = toBeRateAudited;
	}

	/**
	 * @return the carrierCode
	 */
	public String getCarrierCode() {
		return carrierCode;
	}

	/**
	 * @param carrierCode
	 *            the carrierCode to set
	 */
	public void setCarrierCode(String carrierCode) {
		this.carrierCode = carrierCode;
	}

}
