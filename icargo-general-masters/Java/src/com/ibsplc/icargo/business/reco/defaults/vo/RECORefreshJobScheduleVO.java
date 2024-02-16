/*
 * RECORefreshJobScheduleVO.java Created on Apr 19, 2016
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.business.reco.defaults.vo;

import java.util.HashMap;

import com.ibsplc.icargo.framework.jobscheduler.vo.JobScheduleVO;
/**
 *
 * @author A-4823
 *
 */
public class RECORefreshJobScheduleVO extends JobScheduleVO {
	private static final String REC_REF_CMPCOD = "REC_VW_CMPCOD";	

	private String companyCode;
	


	private static HashMap<String,Integer> map;

	static {
		map = new HashMap<String,Integer>();
		map.put(REC_REF_CMPCOD,1);
		
	}

	public int getIndex(String key) {
		return map.get(key);
	}

	public String getValue(int index) {
//		System.out.println( " getValue Index -------->  "+index);
		switch(index) {
		case 1:
//			System.out.println( " getValue CompanyCode --------> " + companyCode);
			{
			return companyCode;
			}
	
	    default:{
	     return null;
	    }
		}
	}

	public void setValue(int index, String value) {
		switch(index) {
			case 1:
				{
				setCompanyCode((String)value);
				break;
				}
		
			default:
		}
	}

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