package com.ibsplc.icargo.business.mail.mra.gpareporting.vo;

import java.util.HashMap;

import com.ibsplc.icargo.framework.jobscheduler.vo.JobScheduleVO;


public class USPSIncentiveJobSchedulerVO extends JobScheduleVO {
	
	private static final String EXCLUDEAMOT = "EXCLUDEAMOT";
	
	private static final String ACCREQD= "ACCREQD";
	
	private String excAmot;
	
	private String accountingRequired;
	
	private static HashMap<String, Integer> map;

	static {
		map = new HashMap<>();
		map.put(EXCLUDEAMOT, 1);
		map.put(ACCREQD, 2);	
		} 

	@Override
	public int getPropertyCount() {
		return map.size();
	}

	@Override
	public int getIndex(String key) {
		return map.get(key);
	}
	
	public String getValue(int index) {
		switch(index) {
		case 1:{
			return excAmot; 
		}
		case 2:{
			return accountingRequired;
		}
		default :{
			return null;
		}
		}
				
	}

	@Override
	public void setValue(int index, String key) {
		switch(index) {
		case 1: {
			setExcludeAmot( key);
			break;
		}
		case 2: {
			setAccountingRequired(key);
			break;
		}
		default: {
			break;
		}
		}
		  
	}


	public String getExcludeAmot() {
		return excAmot;
	}

	public void setExcludeAmot(String excludeAmot) {
		this.excAmot = excludeAmot;
	}

	public String getAccountingRequired() {
		return accountingRequired;
	}

	public void setAccountingRequired(String accountingRequired) {
		this.accountingRequired = accountingRequired;
	}

	
}
