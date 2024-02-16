/*
 * MailServiceLevelPK.java Created on Apr 09, 2010
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.operations;

import java.io.Serializable;

import javax.persistence.Embeddable;

/**
 * @author A-6986
 *  
 */

@Embeddable
public class MailServiceLevelPK implements Serializable{

	private String companyCode;
	
	private String	poaCode;
	
	private String	mailCategory;	
	
	private String	mailClass;
	
	private String	mailSubClass;

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
	 * @return Returns the hashCode.
	 */
	public int hashCode() {
		return new StringBuffer().append(companyCode).append(poaCode)
				.append(mailCategory).append(mailClass).append(mailSubClass).toString().hashCode();
	}
	/**
	 * @param other 
	 * @return 
	 * 
	 */
	@Override
	public boolean equals(Object other) {
		return (hashCode() == other.hashCode());
	}

}
