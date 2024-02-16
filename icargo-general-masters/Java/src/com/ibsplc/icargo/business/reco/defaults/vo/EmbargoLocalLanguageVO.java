/**
 *	Java file	: 	com.ibsplc.icargo.business.reco.defaults.vo.EmbargoLocalLanguageVO.java
 *
 *	Created by	:	a-7815
 *	Created on	:	05-Sep-2017
 *
 *  Copyright 2017 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 *
 * 	This software is the proprietary information of Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
 * 	Use is subject to license terms.
 */
package com.ibsplc.icargo.business.reco.defaults.vo;

import com.ibsplc.xibase.server.framework.vo.AbstractVO;

/**
 *	Java file	: 	com.ibsplc.icargo.business.reco.defaults.vo.EmbargoLocalLanguageVO.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	a-7815	:	05-Sep-2017	:	Draft
 */
public class EmbargoLocalLanguageVO extends AbstractVO {
	private String companyCode;
	private int embargoVersion;
	private String embargoReferenceNumber;
	private String embargoDescription;
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
	 * 	Getter for embargoDescription 
	 *	Added by : a-7815 on 05-Sep-2017
	 * 	Used for :
	 */
	public String getEmbargoDescription() {
		return embargoDescription;
	}
	/**
	 *  @param embargoDescription the embargoDescription to set
	 * 	Setter for embargoDescription 
	 *	Added by : a-7815 on 05-Sep-2017
	 * 	Used for :
	 */
	public void setEmbargoDescription(String embargoDescription) {
		this.embargoDescription = embargoDescription;
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
	
}
