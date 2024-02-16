package com.ibsplc.icargo.business.mail.mra.gpareporting.vo;

import java.util.HashMap;

import com.ibsplc.icargo.framework.jobscheduler.vo.JobScheduleVO;


public class USPSClaimMessageJobScheduleVO extends JobScheduleVO {
	
	private static final String CMP_COD = "COMPANY_CODE";
	
	private String companyCode;
	
	
	private static HashMap<String, Integer> map;

	static {
		map = new HashMap<String, Integer>();
		map.put(CMP_COD, 1);	} 

	@Override
	public int getPropertyCount() {
		return map.size();
	}

	@Override
	public int getIndex(String key) {
		return map.get(key);
	}

	@Override
	public String getValue(int index) {
		switch (index) {
		case 1: {
			return companyCode;
		}	
		default: {
			return null;
		}
		
		}
	}

	@Override
	public void setValue(int index, String key) {
		// TODO Auto-generated method stub
		switch (index) {
		case 1: {
			setCompanyCode((String) key);
			break;
		}
		default: {
			int temp = 0;

		}
		}
	}

	public String getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}
}
