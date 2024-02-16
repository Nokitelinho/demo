/*
 * GenerateInvoiceJobScheduleVO.java Created on May 5, 2019
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.business.mail.mra.defaults.vo;

import java.util.HashMap;
import com.ibsplc.icargo.framework.jobscheduler.vo.JobScheduleVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;

/**
 *	Java file	: 	com.ibsplc.icargo.business.mail.mra.worker.defaults.vo.GenerateInvoiceJobScheduleVO.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-8061	:	05-05-2019	:	Draft
 */


public class GenerateInvoiceJobScheduleVO extends JobScheduleVO {

	private static final String CMP_COD = "COMPANY_CODE";
	private static final String GPA_CODE = "GPA_CODE";
	private static final String GPA_NAME = "GPA_NAME";
	private static final String COUNTRY_CODE = "COUNTRY_CODE";
	private static final String BLGPRDFRM = "BLGPRDFRM";
	private static final String BLGPRDTOO = "BLGPRDTOO";
	private static final String BLGFRQ = "BLGFRQ";
	private static final String INVOICE_TYPE = "INVOICE_TYPE";
	private static final String ADDNEW_FLAG = "ADDNEW_FLAG";
	private static final String JOB_NAME = "JOB_NAME";
	
	private static final String INVSERNUM="INVSERNUM";
	private static final String TRNCOD="TRNCOD";
	
	private String companyCode;
    private String gpaCode;
    private String gpaName;
    private String countryCode;
    private String billingPeriodFrom;
    private String billingPeriodTo;
    private String billingFrequency;
    private String invoiceType;
    private String  addNewFlag;
	private String jobName;
	private String invoiceLogSerialNumber;
	private String transactionCode;
	

	private static HashMap<String, Integer> map;

	static {
		map = new HashMap<String, Integer>();
		
		map.put(GPA_CODE, 1);
		map.put(BLGPRDFRM, 2);
		map.put(BLGPRDTOO, 3);
		map.put(BLGFRQ, 4);
		map.put(INVOICE_TYPE, 5);
		map.put(ADDNEW_FLAG, 6);
		map.put(INVSERNUM, 7);
		map.put(TRNCOD, 8);
		map.put(COUNTRY_CODE, 9);
		

	} 

	/**
	 * @param key
	 */
	public int getIndex(String key) {
		return map.get(key);
	}

	/**
	 * @return size
	 */
	public int getPropertyCount() {
		return map.size();
	}

	public String getValue(int index) {
		switch (index) {

		case 1: {
			return gpaCode;
		}


		case 2: {
			return billingPeriodFrom;
		}
		case 3: {
			return billingPeriodTo;
		}
		case 4: {
			return billingFrequency;
		}
		case 5: {
			return invoiceType;
		}
		case 6: {
			return addNewFlag;
		}

		case 7: {
			return invoiceLogSerialNumber;
		}
		case 8: {
			return transactionCode;
		}
		
		case 9: {
			return countryCode;
		}
		
		default: {
			return null;
		}
		}
	}

	public void setValue(int index, String value) {
		
		switch (index) {
	
		case 1: {
			setGpaCode((String) value);
			break;
		}
		case 2: {
			setBillingPeriodFrom((String) value);
			break;
		}
		case 3: {
			setBillingPeriodTo((String) value);
			break;
		}
		case 4: {
			setBillingFrequency((String) value);
			break;
		}
		case 5: {
			setInvoiceType((String) value);
			break;
		}
		case 6: {
			setAddNewFlag((String) value);
			break;
		}

		case 7: {
			setInvoiceLogSerialNumber((String) value);
			break;
			
		}
		case 8: {
			setTransactionCode((String) value);
			break;
		}
		
		case 9: {
			setCountryCode((String) value);
			break;
		}
		
		default: {
			break;
		}
		}
	}

	/**
	 * @return the companyCode
	 */
	public String getCompanyCode() {
		return companyCode;
	}

	/**
	 * @param companyCode
	 *            the companyCode to set
	 */
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	public String getGpaCode() {
		return gpaCode;
	}

	public void setGpaCode(String gpaCode) {
		this.gpaCode = gpaCode;
	}

	public String getGpaName() {
		return gpaName;
	}

	public void setGpaName(String gpaName) {
		this.gpaName = gpaName;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public String getBillingPeriodFrom() {
		return billingPeriodFrom;
	}

	public void setBillingPeriodFrom(String billingPeriodFrom) {
		this.billingPeriodFrom = billingPeriodFrom;
	}

	public String getBillingPeriodTo() {
		return billingPeriodTo;
	}

	public void setBillingPeriodTo(String billingPeriodTo) {
		this.billingPeriodTo = billingPeriodTo;
	}

	public String getBillingFrequency() {
		return billingFrequency;
	}

	public void setBillingFrequency(String billingFrequency) {
		this.billingFrequency = billingFrequency;
	}

	public String getInvoiceType() {
		return invoiceType;
	}

	public void setInvoiceType(String invoiceType) {
		this.invoiceType = invoiceType;
	}

	public String getAddNewFlag() {
		return addNewFlag;
	}

	public void setAddNewFlag(String addNewFlag) {
		this.addNewFlag = addNewFlag;
	}

	public String getInvoiceLogSerialNumber() {
		return invoiceLogSerialNumber;
	}

	public void setInvoiceLogSerialNumber(String invoiceLogSerialNumber) {
		this.invoiceLogSerialNumber = invoiceLogSerialNumber;
	}

	public String getTransactionCode() {
		return transactionCode;
	}

	public void setTransactionCode(String transactionCode) {
		this.transactionCode = transactionCode;
	}

}
