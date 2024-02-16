/*
 * MailDiscrepancyVO.java Created on Jun 30, 2016
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.operations.vo;

import com.ibsplc.xibase.server.framework.vo.AbstractVO;


/**
 * @author A-3109
 */
public class MailDiscrepancyVO extends AbstractVO {

	private String companyCode;
	
	private String mailIdentifier;
	
	private String uldNumber;
	
	private String discrepancyType;

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
	 * @return Returns the discrepancyType.
	 */
	public String getDiscrepancyType() {
		return discrepancyType;
	}

	/**
	 * @param discrepancyType The discrepancyType to set.
	 */
	public void setDiscrepancyType(String discrepancyType) {
		this.discrepancyType = discrepancyType;
	}

	/**
	 * @return Returns the mailIdentifier.
	 */
	public String getMailIdentifier() {
		return mailIdentifier;
	}

	/**
	 * @param mailIdentifier The mailIdentifier to set.
	 */
	public void setMailIdentifier(String mailIdentifier) {
		this.mailIdentifier = mailIdentifier;
	}

	/**
	 * @return Returns the uldNumber.
	 */
	public String getUldNumber() {
		return uldNumber;
	}

	/**
	 * @param uldNumber The uldNumber to set.
	 */
	public void setUldNumber(String uldNumber) {
		this.uldNumber = uldNumber;
	}
	
}
