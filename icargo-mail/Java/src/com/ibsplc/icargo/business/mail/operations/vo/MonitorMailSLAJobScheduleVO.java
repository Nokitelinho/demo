/*
 * MonitorMailSLAJobScheduleVO.java Created on Jun 30, 2016
 *
 * Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.business.mail.operations.vo;

import java.util.HashMap;

import com.ibsplc.icargo.framework.jobscheduler.vo.JobScheduleVO;

/**
 * 
 * @author a-3109
 * 
 */
public class MonitorMailSLAJobScheduleVO extends JobScheduleVO {

	private String companyCode;

	private static final String CMPCOD = "MTK_DEF_CMPCOD";

	private static HashMap<String, Integer> propertyMap;

	static {
		propertyMap = new HashMap<String, Integer>();
		propertyMap.put(CMPCOD, 1);
	}

	/**
	 * @return
	 */
	public String getCompanyCode() {
		return companyCode;
	}

	/**
	 * @param companyCode
	 */
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	/**
	 * @return
	 */
	public int getPropertyCount() {
		return propertyMap.size();
	}

	/**
	 * @param key
	 * @return
	 */
	public int getIndex(String key) {
		return propertyMap.get(key);
	}

	/**
	 * @param index
	 * @return
	 */
	public String getValue(int index) {
		switch (index) {
		case 1:
			{
			return companyCode;
			}
		default:
		{
			return null;
		}
	 }
	}

	/**
	 * @param index
	 * @param value
	 */
	public void setValue(int index, String value) {
		switch (index) {
		case 1:
			{
			setCompanyCode(value);
			break;
			}
		default :
		{
			break;
		}
	  }
	}
}
