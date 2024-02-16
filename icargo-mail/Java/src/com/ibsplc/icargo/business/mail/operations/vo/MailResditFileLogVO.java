/*
 * MailResditFileLogVO.java Created on Jun 30, 2016
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
 * @author A-3109
 *
 */

public class MailResditFileLogVO extends AbstractVO {

 
    private String companyCode;
    
    private String interchangeControlReference;
    
    private String fileName;

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
	 * @return the interchangeControlReference
	 */
	public String getInterchangeControlReference() {
		return interchangeControlReference;
	}

	/**
	 * @param interchangeControlReference the interchangeControlReference to set
	 */
	public void setInterchangeControlReference(String interchangeControlReference) {
		this.interchangeControlReference = interchangeControlReference;
	}

	/**
	 * @return the fileName
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * @param fileName the fileName to set
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
    
    

}
