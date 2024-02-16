/**
 *	Java file	: 	com.ibsplc.icargo.business.reco.defaults.EmbargoRulesLocalLanguagePK.java
 *
 *	Created by	:	a-7815
 *	Created on	:	05-Sep-2017
 *
 *  Copyright 2017 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 *
 * 	This software is the proprietary information of Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
 * 	Use is subject to license terms.
 */
package com.ibsplc.icargo.business.reco.defaults;

import java.io.Serializable;

import javax.persistence.Embeddable;

/**
 *	Java file	: 	com.ibsplc.icargo.business.reco.defaults.EmbargoRulesLocalLanguagePK.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	a-7815	:	05-Sep-2017	:	Draft
 */
@Embeddable
public class EmbargoRulesLocalLanguagePK implements Serializable{
	
	private String companyCode;
	private String embargoReferenceNumber; 
	private int embargoVersion;
	private String embargoLocalLanguage;
	/**
	 * 	Getter for companyCode 
	 *	Added by : a-7815 on 05-Sep-2017
	 * 	Used for :
	 */
	public String getCompanyCode() {
		return companyCode;
	}
	/**
	 *  @param companyCode the companyCode to set
	 * 	Setter for companyCode 
	 *	Added by : a-7815 on 05-Sep-2017
	 * 	Used for :
	 */
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}
	/**
	 * 	Getter for embargoReferenceNumber 
	 *	Added by : a-7815 on 05-Sep-2017
	 * 	Used for :
	 */
	public String getEmbargoReferenceNumber() {
		return embargoReferenceNumber;
	}
	/**
	 *  @param embargoReferenceNumber the embargoReferenceNumber to set
	 * 	Setter for embargoReferenceNumber 
	 *	Added by : a-7815 on 05-Sep-2017
	 * 	Used for :
	 */
	public void setEmbargoReferenceNumber(String embargoReferenceNumber) {
		this.embargoReferenceNumber = embargoReferenceNumber;
	}
	/**
	 * 	Getter for embargoVersion 
	 *	Added by : a-7815 on 05-Sep-2017
	 * 	Used for :
	 */
	public int getEmbargoVersion() {
		return embargoVersion;
	}
	/**
	 *  @param embargoVersion the embargoVersion to set
	 * 	Setter for embargoVersion 
	 *	Added by : a-7815 on 05-Sep-2017
	 * 	Used for :
	 */
	public void setEmbargoVersion(int embargoVersion) {
		this.embargoVersion = embargoVersion;
	}
	/**
	 * 	Getter for embargoLocalLanguage 
	 *	Added by : a-7815 on 05-Sep-2017
	 * 	Used for :
	 */
	public String getEmbargoLocalLanguage() {
		return embargoLocalLanguage;
	}
	/**
	 *  @param embargoLocalLanguage the embargoLocalLanguage to set
	 * 	Setter for embargoLocalLanguage 
	 *	Added by : a-7815 on 05-Sep-2017
	 * 	Used for :
	 */
	public void setEmbargoLocalLanguage(String embargoLocalLanguage) {
		this.embargoLocalLanguage = embargoLocalLanguage;
	}
	/**
     * Checks whether to objects are equal 
     *@param other
     *@return boolean 
     *
     */
	public boolean equals(Object other) {
		return (other != null) && ((hashCode() == other.hashCode()));
	}

	 /**
     * Returns HashCode 
     * @return int
     */
	public int hashCode() {
		return new StringBuffer(companyCode).append(embargoReferenceNumber).append(embargoVersion)
				.append(embargoLocalLanguage).toString().hashCode();
	}
	
	@Override
	public String toString() {
		StringBuilder sbul = new StringBuilder(180);
		sbul.append("EmbargoRulesLocalLanguagePK [ ");
		sbul.append("embargoLocalLanguage '").append(this.embargoLocalLanguage);
		sbul.append("', companyCode '").append(this.companyCode);
		sbul.append("', embargoReferenceNumber '").append(this.embargoReferenceNumber);
		sbul.append("', embargoVersion '").append(this.embargoVersion);
		sbul.append("' ]");
		return sbul.toString();
	}
	
}
