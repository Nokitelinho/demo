/*
 * ULDStatusChangeJobSchedulerVO.java Created on Jul 18, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.business.uld.defaults.vo;

import java.util.HashMap;

import com.ibsplc.icargo.framework.jobscheduler.vo.JobScheduleVO;

/**
 * @author A-1954
 *
 */
public class ULDStatusChangeJobSchedulerVO extends JobScheduleVO {

	private static final String ULD_STATUS_CHANGE_PERIOD = "ULD_STATUS_CHANGE_PERIOD";

	private static final String ULD_CMPCOD = "ULD_CMPCOD";

	private String companyCode;

	private String period;

	private static HashMap<String, Integer> map;

		static {
			map = new HashMap<String, Integer>();
			map.put(ULD_CMPCOD, 1);
			map.put(ULD_STATUS_CHANGE_PERIOD, 2);
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
		case 2:
			{
				return period;
			}
		}
		return null;
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
			case 2:
				{
					setPeriod(value);
						break;
					
				}
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
		 * @param companyCode
		 *            The companyCode to set.
		 */
		public void setCompanyCode(String companyCode) {
			this.companyCode = companyCode;
	}


		/**
		 * @return Returns the period.
		 */
		public String getPeriod() {
			return period;
		}

		/**
		 * @param period
		 *            The period to set.
		 */
		public void setPeriod(String period) {
			this.period = period;
	}
}
