/*
 * UDPriceDepreciationJobSchedulerVO.java Created on Jun 01, 2017
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
 * @author A-3791
 *
 */
public class ULDStockNotificationJobSchedulerVO extends JobScheduleVO {


	private static final String ULD_CMPCOD = "ULD_NTFSTK_CMPCOD";
	private static final String ULD_ARLIDR = "ULD_NTFSTK_ARLIDR";

	private String companyCode;
	private String airlineId;

	private static HashMap<String, Integer> map;

		static {
			map = new HashMap<String, Integer>();
			map.put(ULD_CMPCOD, 1);
			map.put(ULD_ARLIDR, 2);
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
				return airlineId;
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
					break;
				}
			case 2:
				{
					setAirlineId(value);
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
		 * @return the airlineId
		 */
		public String getAirlineId() {
			return airlineId;
		}

		/**
		 * @param airlineId the airlineId to set
		 */
		public void setAirlineId(String airlineId) {
			this.airlineId = airlineId;
		}

}
          
