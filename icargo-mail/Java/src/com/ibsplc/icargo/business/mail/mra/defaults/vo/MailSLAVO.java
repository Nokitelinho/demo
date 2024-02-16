/*
 * MailSLAVO.java Created on Mar 30, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.mra.defaults.vo;

import java.util.Collection;

import com.ibsplc.xibase.server.framework.vo.AbstractVO;

/**
 * @author a-2524
 *
 */
public class MailSLAVO extends AbstractVO {
	
	private String companyCode;
	
	private String slaId;
	
	private String description;
	
	private String currency;
	
	private Collection<MailSLADetailsVO> mailSLADetailsVos;
	
	private String operationFlag;
	
	
	/**
	 * @return Returns the currency.
	 */
	public String getCurrency() {
		return currency;
	}
	/**
	 * @param currency The currency to set.
	 */
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	/**
	 * @return Returns the description.
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * @param description The description to set.
	 */
	public void setDescription(String description) {
		this.description = description;
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
	 * @return Returns the slaId.
	 */
	public String getSlaId() {
		return slaId;
	}
	/**
	 * @param slaId The slaId to set.
	 */
	public void setSlaId(String slaId) {
		this.slaId = slaId;
	}
	/**
	 * @return Returns the mailSLADetailsVos.
	 */
	public Collection<MailSLADetailsVO> getMailSLADetailsVos() {
		return mailSLADetailsVos;
	}
	/**
	 * @param mailSLADetailsVos The mailSLADetailsVos to set.
	 */
	public void setMailSLADetailsVos(Collection<MailSLADetailsVO> mailSLADetailsVos) {
		this.mailSLADetailsVos = mailSLADetailsVos;
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

}
