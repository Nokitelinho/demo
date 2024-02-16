/*
 * PartnerCarrierVO.java Created on Jun 30, 2016
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
 * @author a-3109
 *
 */
public class PartnerCarrierVO extends AbstractVO {
    private String companyCode;    
    private String ownCarrierCode;    
    private String partnerCarrierCode;    
    private String airportCode;
    private String partnerCarrierId;
    private String partnerCarrierName;
    private String operationFlag;
    private LocalDate lastUpdateTime;
    private String lastUpdateUser;
    private String mldTfdReq; 
    
	/**
	 * @return Returns the airportCode.
	 */
	public String getAirportCode() {
		return airportCode;
	}
	/**
	 * @param airportCode The airportCode to set.
	 */
	public void setAirportCode(String airportCode) {
		this.airportCode = airportCode;
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
	 * @return Returns the operationFlag.
	 */
	public String getOperationFlag() {
		return operationFlag;
	}
	/**
	 * @param operationFlag The operationFlag to set.
	 */
	public void setOperationFlag(String operationFlag) {
		this.operationFlag = operationFlag;
	}
	/**
	 * @return Returns the ownCarrierCode.
	 */
	public String getOwnCarrierCode() {
		return ownCarrierCode;
	}
	/**
	 * @param ownCarrierCode The ownCarrierCode to set.
	 */
	public void setOwnCarrierCode(String ownCarrierCode) {
		this.ownCarrierCode = ownCarrierCode;
	}
	/**
	 * @return Returns the partnerCarrierCode.
	 */
	public String getPartnerCarrierCode() {
		return partnerCarrierCode;
	}
	/**
	 * @param partnerCarrierCode The partnerCarrierCode to set.
	 */
	public void setPartnerCarrierCode(String partnerCarrierCode) {
		this.partnerCarrierCode = partnerCarrierCode;
	}
	/**
	 * @return Returns the partnerCarrierId.
	 */
	public String getPartnerCarrierId() {
		return partnerCarrierId;
	}
	/**
	 * @param partnerCarrierId The partnerCarrierId to set.
	 */
	public void setPartnerCarrierId(String partnerCarrierId) {
		this.partnerCarrierId = partnerCarrierId;
	}
	/**
	 * @return Returns the partnerCarrierName.
	 */
	public String getPartnerCarrierName() {
		return partnerCarrierName;
	}
	/**
	 * @param partnerCarrierName The partnerCarrierName to set.
	 */
	public void setPartnerCarrierName(String partnerCarrierName) {
		this.partnerCarrierName = partnerCarrierName;
	}
	public LocalDate getLastUpdateTime() {
		return lastUpdateTime;
	}
	public void setLastUpdateTime(LocalDate lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}
	public String getLastUpdateUser() {
		return lastUpdateUser;
	}
	public void setLastUpdateUser(String lastUpdateUser) {
		this.lastUpdateUser = lastUpdateUser;
	}

	public String getMldTfdReq() {
		return mldTfdReq;
	}

	public void setMldTfdReq(String mldTfdReq) {
		this.mldTfdReq = mldTfdReq;
	}
}
