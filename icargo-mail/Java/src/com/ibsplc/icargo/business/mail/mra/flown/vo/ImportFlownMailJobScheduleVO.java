/*
 * ImportFlownMailJobScheduleVO.java Created on Apr 1, 2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.mra.flown.vo;

import java.util.HashMap;
import java.util.Map;

import com.ibsplc.icargo.framework.jobscheduler.vo.JobScheduleVO;


/**
 * @author A-2246
 * Revision History
 * -------------------------------------------------------------------------
 * Revision 		Date 					Author 		Description
 * ------------------------------------------------------------------------- 
 * 0.1     		   Apr 1, 2008			  	 A-2246	Created
 */
public class ImportFlownMailJobScheduleVO extends JobScheduleVO {

	private static final String MTK_DEF_CMPCOD = "MTK_DEF_CMPCOD";
	private static final String STRDAT = "STRDAT";
	private static final String ENDDAT = "ENDDAT";
	
	private String companyCode;
	
	private String startDate;
	
	private String endDate;
	
	private static Map<String, Integer> map;
	
	static{
		map = new HashMap<String, Integer>();
		map.put(MTK_DEF_CMPCOD, 1);
		map.put(STRDAT, 2);
		map.put(ENDDAT, 3);
	}
	/**
	 * 
	 * @see com.ibsplc.icargo.framework.jobscheduler.vo.JobScheduleVO#getPropertyCount()
	 */
	@Override
	/**
	 * @return int
	 */
	public int getPropertyCount() {
		return map.size();
	}

	/**
	 * @see com.ibsplc.icargo.framework.jobscheduler.vo.JobScheduleVO#getIndex(java.lang.String)
	 */
	@Override
	/**
	 * 
	 * @param key
	 * @return int
	 * @exception
	 */
	public int getIndex(String key) {		
		return map.get(key);
	}

	/**
	 * @see com.ibsplc.icargo.framework.jobscheduler.vo.JobScheduleVO#getValue(int)
	 */
	@Override
	/**
	 * @param index
	 * @return String
	 * @exception
	 */
	public String getValue(int index) {
		switch(index) {
		case 1 : 
				{
				return companyCode;
				}
		case 2 :
			{
			return startDate;
			}
		case 3 :
			{
			return endDate;
			}
		default :
			{
			return null;
		}
		}
		
	}

	/**
	 * @see com.ibsplc.icargo.framework.jobscheduler.vo.JobScheduleVO#setValue(int, java.lang.String)
	 */
	@Override
	/**
	 * @param index
	 * @param key
	 * @return 
	 * @exception
	 */
	public void setValue(int index, String key) {
		switch(index) {
		case 1 : 
				{
				setCompanyCode(key);
				break;
				}
		case 2 :
		{
			setStartDate(key);
			break;
		}
		case 3 :
		{
			setEndDate(key);
			break;
		}
		default : {
				int temp = 0;
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
	 * @param companyCode The companyCode to set.
	 */
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	
}
