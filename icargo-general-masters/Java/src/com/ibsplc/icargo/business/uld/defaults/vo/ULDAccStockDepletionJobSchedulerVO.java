/*
 * ULDAccStockDepletionJobSchedulerVO.java Created on Apr 05, 2008
 *
 * Copyright 2008 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.business.uld.defaults.vo;

import java.util.HashMap;

import com.ibsplc.icargo.framework.jobscheduler.vo.JobScheduleVO;

/**
 * @author A-1950
 *
 */
public class ULDAccStockDepletionJobSchedulerVO extends JobScheduleVO {


	private static final String ULD_CMPCOD = "ULD_CMPCOD";

	private String companyCode;

	private static HashMap<String, Integer> map;

		static {
			map = new HashMap<String, Integer>();
			map.put(ULD_CMPCOD, 1);
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
		switch (index) {
		case 1:
			{
			return companyCode;
		}
		}
		return null;
	}


	/**
	 * @return
	 */
	public int getPropertyCount() {
		return map.size();
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
				}
			}
	}

		/**
		 * @return Returns the companyCode.
		 */
		public String getCompanyCode() {
			return companyCode;
		}

		/**
		 * @param companyCode
		 *            The companyCode to set.
		 */
		public void setCompanyCode(String companyCode) {
			this.companyCode = companyCode;
	}

}
