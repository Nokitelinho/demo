/*
 * UDPriceDepreciationJobSchedulerVO.java Created on Jul 18, 2006
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
public class ULDPriceDepreciationJobSchedulerVO extends JobScheduleVO {


	private static final String ULD_CMPCOD = "ULD_CMPCOD";
	private static final String ULD_DEPRECIATION_PERIOD = "ULD_DEPRECIATION_PERIOD";

	private String companyCode;
	//Added by A-7359 for ICRD-233082 
	private String period;

	private static HashMap<String, Integer> map;

		static {
			map = new HashMap<String, Integer>();
			map.put(ULD_CMPCOD, 1);
			map.put(ULD_DEPRECIATION_PERIOD, 2);
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
			return companyCode;
		case 2:
			return period;
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
		 * 
		 * 	Method		:	ULDPriceDepreciationJobSchedulerVO.getPeriod
		 *	Added by 	:	A-7359 on 02-May-2018
		 * 	Used for 	:	ICRD-233082
		 *	Parameters	:	@return 
		 *	Return type	: 	String
		 */
		public String getPeriod() {
			return period;
		}
		/**
		 * 
		 * 	Method		:	ULDPriceDepreciationJobSchedulerVO.setPeriod
		 *	Added by 	:	A-7359 on 02-May-2018
		 * 	Used for 	:	ICRD-233082 
		 *	Parameters	:	@param period 
		 *	Return type	: 	void
		 */
		public void setPeriod(String period) {
			this.period = period;
	}

}
          