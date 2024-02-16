/*
 * StockDepleteJobScheduleVO.java Created on Apr 26, 2006
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.business.stockcontrol.defaults.vo;

import java.util.HashMap;

import com.ibsplc.icargo.framework.jobscheduler.vo.JobScheduleVO;
/**
 *
 * @author A-1885
 *
 */
public class StockDepleteJobScheduleVO extends JobScheduleVO {
	private static final String STK_DEF_UTL_CMPCOD = "STK_DEF_UTL_CMPCOD";
	private static final String STK_DEF_UTL_ARLIDR = "STK_DEF_UTL_ARLIDR";
	// Added under BUG_ICRD-23686_AiynaSuresh_28Mar13
	private static final String STK_DEF_UTL_THRESHOLD_MINS = "STK_DEF_UTL_THRESHOLD_MINS";

	private String companyCode;

	private Integer airlineId;
	// Added under BUG_ICRD-23686_AiynaSuresh_28Mar13
	private Integer logClearingThreshold;

	private static HashMap<String,Integer> map;

	static {
		map = new HashMap<String,Integer>();
		map.put(STK_DEF_UTL_CMPCOD,1);
		map.put(STK_DEF_UTL_ARLIDR,2);
		// Added under BUG_ICRD-23686_AiynaSuresh_28Mar13
		map.put(STK_DEF_UTL_THRESHOLD_MINS,3);
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
		case 2:
		{
			return airlineId.toString();
		}
		// Added under BUG_ICRD-23686_AiynaSuresh_28Mar13
		case 3 : 
			{
			return logClearingThreshold.toString();
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
		switch(index) {
			case 1:
				{
				setCompanyCode((String)value);
				break;
				}
			case 2:
				setAirlineId(Integer.parseInt(value));
				{
					setAirlineId(Integer.parseInt(value));
				break;
				}
			// Added under BUG_ICRD-23686_AiynaSuresh_28Mar13
			case 3 :
				{
				setLogClearingThreshold(Integer.parseInt(value));
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

	public Integer getAirlineId(){
		return airlineId;
	}

	public void setAirlineId(Integer airlineId){
		this.airlineId = airlineId;
	}

	/**
	 * @return the logClearingThreshold
	 */
	public Integer getLogClearingThreshold() {
		return logClearingThreshold;
	}

	/**
	 * @param logClearingThreshold the logClearingThreshold to set
	 */
	public void setLogClearingThreshold(Integer logClearingThreshold) {
		this.logClearingThreshold = logClearingThreshold;
	}

}