package com.ibsplc.icargo.presentation.web.model.mail.mra.gpabilling;

import com.ibsplc.icargo.framework.web.spring.model.AbstractScreenModel;
import com.ibsplc.icargo.presentation.web.model.mail.mra.common.GPAPassFilter;

/**
 * 
 *	Java file	: 	com.ibsplc.icargo.presentation.web.model.mail.mra.gpabilling.GeneratePASSBillingFileModel.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-4809	:	22-Apr-2021	:	Draft
 */
public class GeneratePASSBillingFileModel extends AbstractScreenModel {
	
	private static final String PRODUCT = "mail";
	private static final String SUBPRODUCT = "mra";
	private static final String SCREENID = "mail.mra.gpabillig.ux.generatepassbillingfile";
	
	private String country;
	private String gpaCode;
	private String periodNumber;
	private String fromBillingDate;
	private String toBillingDate;
	private String fileName;
	private boolean includeNewInvoice;
	private GPAPassFilter passFilter;
	private boolean isValidPeriodNumber;
	private boolean isValidBillingPeriod;
	
	private String populateSource;
	
	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getGpaCode() {
		return gpaCode;
	}

	public void setGpaCode(String gpaCode) {
		this.gpaCode = gpaCode;
	}

	public String getPeriodNumber() {
		return periodNumber;
	}

	public void setPeriodNumber(String periodNumber) {
		this.periodNumber = periodNumber;
	}

	public String getFromBillingDate() {
		return fromBillingDate;
	}

	public void setFromBillingDate(String fromBillingDate) {
		this.fromBillingDate = fromBillingDate;
	}

	public String getToBillingDate() {
		return toBillingDate;
	}

	public void setToBillingDate(String toBillingDate) {
		this.toBillingDate = toBillingDate;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public boolean isIncludeNewInvoice() {
		return includeNewInvoice;
	}

	public void setIncludeNewInvoice(boolean includeNewInvoice) {
		this.includeNewInvoice = includeNewInvoice;
	}

	@Override
	public String getProduct() {
		return PRODUCT;
	}

	@Override
	public String getScreenId() {
		return SCREENID;
	}

	@Override
	public String getSubProduct() {
		return SUBPRODUCT;
	}
	/**
	 * 	Getter for passFilter 
	 *	Added by : A-4809 on 22-Apr-2021
	 * 	Used for :
	 */
	public GPAPassFilter getPassFilter() {
		return passFilter;
	}
	/**
	 *  @param passFilter the passFilter to set
	 * 	Setter for passFilter 
	 *	Added by : A-4809 on 22-Apr-2021
	 * 	Used for :
	 */
	public void setPassFilter(GPAPassFilter passFilter) {
		this.passFilter = passFilter;
	}


	public String getPopulateSource() {
		return populateSource;
	}

	public void setPopulateSource(String populateSource) {
		this.populateSource = populateSource;
	}

	public boolean isValidBillingPeriod() {
		return isValidBillingPeriod;
	}

	public void setValidBillingPeriod(boolean isValidBillingPeriod) {
		this.isValidBillingPeriod = isValidBillingPeriod;
	}

	public boolean isValidPeriodNumber() {
		return isValidPeriodNumber;
	}

	public void setValidPeriodNumber(boolean isValidPeriodNumber) {
		this.isValidPeriodNumber = isValidPeriodNumber;
	}

	

}
