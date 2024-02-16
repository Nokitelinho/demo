/*
 * XXResditJobScheduleVO.java Created on Jun 30, 2016
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.operations.vo;

import java.util.HashMap;
import java.util.Map;

import com.ibsplc.icargo.framework.jobscheduler.vo.JobScheduleVO;

/**
 * @author A-3109
 */
public class XXResditJobScheduleVO extends JobScheduleVO {

	private static final String MTK_DEF_CMPCOD = "MTK_DEF_CMPCOD";

	private String companyCode;

	private static Map<String, Integer> map;

	static {
		map = new HashMap<String, Integer>();
		map.put(MTK_DEF_CMPCOD, 1);
	}

	/**
	 * 
	 * @see com.ibsplc.icargo.framework.jobscheduler.vo.JobScheduleVO#getPropertyCount()
	 */
	@Override
	public int getPropertyCount() {
		return map.size();
	}

	/**
	 * @see com.ibsplc.icargo.framework.jobscheduler.vo.JobScheduleVO#getIndex(java.lang.String)
	 */
	@Override
	public int getIndex(String key) {
		return map.get(key);
	}

	/**
	 * @see com.ibsplc.icargo.framework.jobscheduler.vo.JobScheduleVO#getValue(int)
	 */
	@Override
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
	 * @see com.ibsplc.icargo.framework.jobscheduler.vo.JobScheduleVO#setValue(int,
	 *      java.lang.String)
	 */
	@Override
	public void setValue(int index, String key) {
		switch (index) {
		case 1:
			{
			setCompanyCode(key);
			break;
			}
		default:
		{
			break;
		}
		}
	}

	/**
	 * @return Returns the companyCode.
	 */
	public String getCompanyCode() {
		return this.companyCode;
	}

	/**
	 * @param companyCode
	 *            The companyCode to set.
	 */
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

}
