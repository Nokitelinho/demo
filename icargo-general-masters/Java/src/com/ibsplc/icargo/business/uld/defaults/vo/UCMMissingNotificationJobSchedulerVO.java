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
public class UCMMissingNotificationJobSchedulerVO extends JobScheduleVO {


	private static final String UCM_CMPCOD = "UCMMSG_CMPCOD";
	private static final String UCM_ARLIDR = "UCMMSG_ARLIDR";
	private static final String UCM_ATD_OFFSET = "UCMMSG_ATD_OFFSET_MINUTES";//X - After ATD+X minutes
	private static final String UCM_MONITOR_PERIOD = "UCMMSG_MONITOR_PERIOD";//Y - For last Y minutes

	private String companyCode;
	private String airlineId;
	private String atdOffset;
	private String ucmMonitorPeriod;

	private static HashMap<String, Integer> map;

		static {
			map = new HashMap<String, Integer>();
			map.put(UCM_CMPCOD, 1);
			map.put(UCM_ARLIDR, 2);
			map.put(UCM_ATD_OFFSET, 3);
			map.put(UCM_MONITOR_PERIOD, 4);
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
		case 3:
			{
				return atdOffset;
			}
		case 4:
			{
				return ucmMonitorPeriod;
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
			case 3:
				
					{
						setAtdOffset(value);
						break;
					
				}
			case 4:
				
					{
						setUcmMonitorPeriod(value);
						break;
					}
				
				default:
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
		
		/**
		 * @return the atdOffset
		 */
		public String getAtdOffset() {
			return atdOffset;
		}

		/**
		 * @param atdOffset the atdOffset to set
		 */
		public void setAtdOffset(String atdOffset) {
			this.atdOffset = atdOffset;
		}
		
		/**
		 * @return the triggerPeriod
		 */
		public String getUcmMonitorPeriod() {
			return ucmMonitorPeriod;
		}

		/**
		 * @param triggerPeriod the triggerPeriod to set
		 */
		public void setUcmMonitorPeriod(String ucmMonitorPeriod) {
			this.ucmMonitorPeriod = ucmMonitorPeriod;
		}

}
          
