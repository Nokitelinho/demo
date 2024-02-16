
package com.ibsplc.icargo.business.mail.operations.vo;

import com.ibsplc.xibase.server.framework.vo.AbstractVO;


public class CoTerminusFilterVO extends AbstractVO {
	
	private String gpaCode;
	private String airportCodes; 
	private String resditModes;
	private String receivedfromTruck;
	private String companyCode;  
	
	public String getCompanyCode() {
		return companyCode;
	}
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}
	public String getGpaCode() {
		return gpaCode;
	}
	public void setGpaCode(String gpaCode) {
		this.gpaCode = gpaCode;
	}
	public String getAirportCodes() {
		return airportCodes;
	}
	public void setAirportCodes(String airportCodes) {
		this.airportCodes = airportCodes;
	}
	public String getResditModes() {
		return resditModes;
	}
	public void setResditModes(String resditModes) {
		this.resditModes = resditModes;
	}
	public String getReceivedfromTruck() {
		return receivedfromTruck;
	}
	public void setReceivedfromTruck(String receivedfromTruck) {
		this.receivedfromTruck = receivedfromTruck;
	}
	 
}
