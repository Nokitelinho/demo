/*
 * ImportBillingInterfaceFileJobScheduleVO.java Created on June 4, 2018
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.business.mail.mra.defaults.vo;

import java.util.HashMap;
import com.ibsplc.icargo.framework.jobscheduler.vo.JobScheduleVO;

/**
 *	Java file	: 	com.ibsplc.icargo.business.mail.mra.worker.defaults.vo.MailBillingInterfaceFileJobScheduleVO.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-7929	:	04-06-2018	:	Draft
 */


public class MailBillingInterfaceFileJobScheduleVO extends JobScheduleVO {

	private static final String CMP_COD = "COMPANY_CODE";
	private static final String JOB_NAME = "JOB_NAME";
	
	private static final String JOB_EVENT = "JOB_EVENT";//A-8061 added for ICRD-245594
	
	
	private String companyCode;
	private String jobName;
	private String event;

	

	private static HashMap<String, Integer> map;

	static {
		map = new HashMap<String, Integer>();
		map.put(CMP_COD, 1);
		map.put(JOB_NAME, 2);
		map.put(JOB_EVENT, 3);
		
		
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
			return companyCode;
		}
		case 2: {
			return jobName;
		}
		
		case 3: {
			return event;
		}
		
		default: {
			return null;
		}
		}
	}

	public void setValue(int index, String value) {
		switch (index) {
		case 1: {
			setCompanyCode((String) value);
			break;
		}
		case 2: {
			setJobName((String) value);
			break;
		}
		case 3: {
			setEvent((String) value);
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

	public String getEvent() {
		return event;
	}

	public void setEvent(String event) {
		this.event = event;
	}
	

}
