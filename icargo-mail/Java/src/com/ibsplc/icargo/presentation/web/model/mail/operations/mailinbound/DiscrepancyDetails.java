package com.ibsplc.icargo.presentation.web.model.mail.operations.mailinbound;

/**
 * 
 *	Java file	: 	com.ibsplc.icargo.presentation.web.model.mail.operations.mailinbound.DiscrepancyDetails.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-8164	:	24-Oct-2018		:	Draft
 */
public class DiscrepancyDetails {

private String companyCode;
	
	private String mailIdentifier;
	
	private String uldNumber;
	
	private String discrepancyType;

	public String getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	public String getMailIdentifier() {
		return mailIdentifier;
	}

	public void setMailIdentifier(String mailIdentifier) {
		this.mailIdentifier = mailIdentifier;
	}

	public String getUldNumber() {
		return uldNumber;
	}

	public void setUldNumber(String uldNumber) {
		this.uldNumber = uldNumber;
	}

	public String getDiscrepancyType() {
		return discrepancyType;
	}

	public void setDiscrepancyType(String discrepancyType) {
		this.discrepancyType = discrepancyType;
	}
	
	
}
