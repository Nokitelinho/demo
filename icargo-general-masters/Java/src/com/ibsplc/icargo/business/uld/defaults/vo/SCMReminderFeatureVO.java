package com.ibsplc.icargo.business.uld.defaults.vo;

import com.ibsplc.xibase.server.framework.vo.AbstractVO;

public class SCMReminderFeatureVO extends AbstractVO {
	
	private String companyCode;
	
	private String airportCode;
	
	public String getCompanyCode() {
		return companyCode;
	}
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}
	public String getAirportCode() {
		return airportCode;
	}
	public void setAirportCode(String airportCode) {
		this.airportCode = airportCode;
	}

}
