/**
 *	Java file	: 	com.ibsplc.icargo.business.mail.operations.vo.MailOperationDataFilterVO.java
 *
 *	Created by	:	204082
 *	Created on	:	26-Jul-2023
 *
 *  Copyright 2022 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 *
 * 	This software is the proprietary information of Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
 * 	Use is subject to license terms.
 */

package com.ibsplc.icargo.business.mail.operations.vo;

import com.ibsplc.xibase.server.framework.vo.AbstractVO;

public class MailOperationDataFilterVO extends AbstractVO {
	
	private Integer noOfDaysToConsider;
	private String triggerPoints;
	private Integer tolerance;
	private String postalAuthorityCode;
	private String carrierCode;
	private String mailbagOrigin;
	private String companyCode;
	
	public Integer getNoOfDaysToConsider() {
		return noOfDaysToConsider;
	}
	public void setNoOfDaysToConsider(Integer noOfDaysToConsider) {
		this.noOfDaysToConsider = noOfDaysToConsider;
	}
	public String getTriggerPoints() {
		return triggerPoints;
	}
	public void setTriggerPoints(String triggerPoints) {
		this.triggerPoints = triggerPoints;
	}
	public Integer getTolerance() {
		return tolerance;
	}
	public void setTolerance(Integer tolerance) {
		this.tolerance = tolerance;
	}
	public String getPostalAuthorityCode() {
		return postalAuthorityCode;
	}
	public void setPostalAuthorityCode(String postalAuthorityCode) {
		this.postalAuthorityCode = postalAuthorityCode;
	}
	public String getCarrierCode() {
		return carrierCode;
	}
	public void setCarrierCode(String carrierCode) {
		this.carrierCode = carrierCode;
	}
	public String getMailbagOrigin() {
		return mailbagOrigin;
	}
	public void setMailbagOrigin(String mailbagOrigin) {
		this.mailbagOrigin = mailbagOrigin;
	}
	public String getCompanyCode() {
		return companyCode;
	}
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}
	
	

}
