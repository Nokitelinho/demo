/*
 * InformAgentJobScheduleVO.java Created on Jun 30, 2016
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.business.mail.operations.vo;

import java.util.HashMap;

import com.ibsplc.icargo.framework.jobscheduler.vo.JobScheduleVO;
/**
 *
 * @author A-3109
 *
 */
public class MailFlightClosureJobScheduleVO extends JobScheduleVO {
	
	private static final String MTK_DEF_CMPCOD = "MTK_DEF_CMPCOD";
	
	private static final String MTK_DEF_TIM	= "MTK_DEF_TIME";
	
	private static final String MTK_DEF_ARPCOD	= "MTK_DEF_ARPCOD";


	private String companyCode;
	
	private Integer time;
	
	private String airportCode;


	private static HashMap<String,Integer> map;

	static {
		map = new HashMap<String,Integer>();
		map.put(MTK_DEF_CMPCOD,1);
		map.put(MTK_DEF_TIM, 2);
		map.put(MTK_DEF_ARPCOD, 3);
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
				return time.toString();
			}
		case 3:
			{
			return airportCode;
			}
		default:
			{
				return null;
			}
		}
		
	}

	public String getAirportCode() {
		return airportCode;
	}

	public void setAirportCode(String airportCode) {
		this.airportCode = airportCode;
	}

	/**
	 * @param index
	 * @param value
	 */
	public void setValue(int index, String value) {
		switch(index) {
			case 1:
					
				{
				setCompanyCode(value);	
				break;
				}
			case 2:
								
				
					
					{   if(value!=null){
						setTime(Integer.parseInt(value));
					}
					else
					{
						setTime(0);
					}
						break;
					}
				
			case 3:
				
					
					{
						setAirportCode(value);
						break;
					}
				
			default:
				
					
					{
						setAirportCode(null);
						break;
					}
				
		}			
	}

	
	
	public Integer getTime() {
		return time;
	}

	public void setTime(Integer time) {
		this.time = time;
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
