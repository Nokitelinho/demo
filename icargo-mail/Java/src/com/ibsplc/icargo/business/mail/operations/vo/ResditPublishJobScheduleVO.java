/**
 *	Java file	: 	com.ibsplc.icargo.business.mail.operations.worker.ResditPublishWorker.java
 *
 *	Created by	:	A-7540
 *	Created on	:	Jan 16, 2018
 *
 *  Copyright 2016 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 *
 * 	This software is the proprietary information of Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
 * 	Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.operations.vo;

import java.util.HashMap;

/**
 * @author A-7540
 */

import com.ibsplc.icargo.framework.jobscheduler.vo.JobScheduleVO;

public class ResditPublishJobScheduleVO extends JobScheduleVO{
	
private static final String COMPANY_CODE = "COMPANY_CODE";
	
	private static final String PA_CODE	= "PA_CODE";
	
	public static final String NO_OF_DAYS	= "NO_OF_DAYS";
	
	public static final String REPORT_ID="RESPUBRPT";
	
	public static final String REPORT_NAME="RESDIT_PUBLISH";
	
	
	private String companyCode;
	private String paCode;
	private int days;
	private String airportCode;
	private String reportID;
	private String scheduleId;
	
	private static HashMap<String,Integer> map;

	static {
		map = new HashMap<String,Integer>();
		map.put(COMPANY_CODE,1);
		map.put(NO_OF_DAYS, 2);
		map.put(PA_CODE, 3);
		
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
			return String.valueOf(days);
		}
		case 3:
			{
			return paCode;
			}
		case 4:
		{
			return airportCode;
		}
		case 5:
			{
			return reportID;
			}
		case 6:
		{
			return scheduleId;
		}
		default:
			{
			return null;
		}
		}
		
	}
	public void setValue(int index, String value) {
		switch(index) {
			case 1:
					
				{
				setCompanyCode(value);	
				break;
				}
			case 2:
				
				{
					setDays(Integer.parseInt(value));
				break;
				}
			case 3:
								
				{
					setPaCode(value);
				break;
				}
			case 4:
			{
				setAirportCode(value);	
			}
			case 5:
				{
				setReportID(value);
				}
			case 6:
				{
				setScheduleId(value);
				}
			default:
				{
				setAirportCode(null);
				break;
				}
		}			
	}
	
	/**
	 * @return Returns the days.
	 */
	
	public int getDays() {
		return days;
	}

	/**
	 * @param days The days to set.
	 */
	public void setDays(int days) {
		this.days = days;
	}

	/**
	 * @return Returns the airportCode.
	 */
	public String getAirportCode() {
		return airportCode;
	}
	
	/**
	 * @param airportCode The airportCode to set.
	 */
	public void setAirportCode(String airportCode) {
		this.airportCode = airportCode;
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
	
	/**
	 * @return Returns the paCode.
	 */
	public String getPaCode() {
		return paCode;
	}
	/**
	 * @param paCode The paCode to set.
	 */
	public void setPaCode(String paCode) {
		this.paCode = paCode;
	}

	/**
	 * @return Returns the reportID.
	 */
	public String getReportID() {
		return reportID;
	}
	/**
	 * @param reportID The reportID to set.
	 */
	public void setReportID(String reportID) {
		this.reportID = reportID;
	}
	/**
	 * @return
	 */
	public int getPropertyCount() {
		return map.size();
	}

	/**
	 * @return Returns the scheduleId.
	 */
	public String getScheduleId() {
		return scheduleId;
	}
    
	/**
	 * @param scheduleId The scheduleId to set.
	 */
	public void setScheduleId(String scheduleId) {
		this.scheduleId = scheduleId;
	}
}
