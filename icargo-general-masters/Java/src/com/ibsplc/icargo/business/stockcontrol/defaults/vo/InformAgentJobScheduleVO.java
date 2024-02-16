/*
 * InformAgentJobScheduleVO.java Created on Apr 26, 2006
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.business.stockcontrol.defaults.vo;

import java.util.HashMap;

import com.ibsplc.icargo.framework.jobscheduler.vo.JobScheduleVO;
/**
 *
 * @author A-1958
 *
 */
public class InformAgentJobScheduleVO extends JobScheduleVO {
	private static final String STK_DEF_CMPCOD = "STK_DEF_CMPCOD";


	private String companyCode;


	private static HashMap<String,Integer> map;

	static {
		map = new HashMap<String,Integer>();
		map.put(STK_DEF_CMPCOD,1);
	}

	/**
	 * @param key
	 * @return
	 */
	public int getIndex(String key) {
		return map.get(key);
	}

	/**
	 * @param index
	 * @return
	 */
	public String getValue(int index) {
		switch(index) {
		case 1:
			{
			return companyCode;
			}
		default:{
			return null;
		}
		}
		
	}

	/**
	 * @param index
	 * @param value
	 */
	public void setValue(int index, String value) {
		switch(index) {
			case 1:
				{
				setCompanyCode((String)value);
				break;
				}
			default:

			}
	}

	/**
	 * @return
	 */
	public int getPropertyCount() {
		return map.size();
	}

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

}