/*
 * MailServiceLevelVO.java Created on Apr 09 2018
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.operations.vo;

import com.ibsplc.xibase.server.framework.vo.AbstractVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
/**
 * 
 * @author A-6986
 *
 */
public class MailServiceLevelVO extends AbstractVO{

	private String companyCode;
	
	private String	poaCode;
	
	private String	mailCategory;	
	
	private String	mailClass;
	
	private String	mailSubClass;
	
	private String	mailServiceLevel;
	
	private LocalDate lastUpdatedTime;
	
	private String	lastUpdatedUser;
	

	/**
	 * @return the companyCode
	 */
	public String getCompanyCode() {
		return companyCode;
	}

	/**
	 * @param companyCode the companyCode to set
	 */
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
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

	/**
	 * @return the mailCategory
	 */
	public String getMailCategory() {
		return mailCategory;
	}

	/**
	 * @param mailCategory the mailCategory to set
	 */
	public void setMailCategory(String mailCategory) {
		this.mailCategory = mailCategory;
	}

	/**
	 * @return the mailClass
	 */
	public String getMailClass() {
		return mailClass;
	}

	/**
	 * @param mailClass the mailClass to set
	 */
	public void setMailClass(String mailClass) {
		this.mailClass = mailClass;
	}

	/**
	 * @return the mailSubClass
	 */
	public String getMailSubClass() {
		return mailSubClass;
	}

	/**
	 * @param mailSubClass the mailSubClass to set
	 */
	public void setMailSubClass(String mailSubClass) {
		this.mailSubClass = mailSubClass;
	}

	/**
	 * @return the mailServiceLevel
	 */
	public String getMailServiceLevel() {
		return mailServiceLevel;
	}

	/**
	 * @param mailServiceLevel the mailServiceLevel to set
	 */
	public void setMailServiceLevel(String mailServiceLevel) {
		this.mailServiceLevel = mailServiceLevel;
	}

	/**
	 * @return the lastUpdatedTime
	 */
	public LocalDate getLastUpdatedTime() {
		return lastUpdatedTime;
	}

	/**
	 * @param lastUpdatedTime the lastUpdatedTime to set
	 */
	public void setLastUpdatedTime(LocalDate lastUpdatedTime) {
		this.lastUpdatedTime = lastUpdatedTime;
	}

	/**
	 * @return the lastUpdatedUser
	 */
	public String getLastUpdatedUser() {
		return lastUpdatedUser;
	}

	/**
	 * @param lastUpdatedUser the lastUpdatedUser to set
	 */
	public void setLastUpdatedUser(String lastUpdatedUser) {
		this.lastUpdatedUser = lastUpdatedUser;
	}
}
