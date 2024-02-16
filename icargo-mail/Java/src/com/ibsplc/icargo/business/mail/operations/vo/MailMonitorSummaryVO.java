package com.ibsplc.icargo.business.mail.operations.vo;

import com.ibsplc.xibase.server.framework.vo.AbstractVO;

public class MailMonitorSummaryVO extends AbstractVO {
	private String monitoringType;
	private String sector;
	private double value;
	public String getMonitoringType() {
		return monitoringType;
	}
	public void setMonitoringType(String monitoringType) {
		this.monitoringType = monitoringType;
	}
	public String getSector() {
		return sector;
	}
	public void setSector(String sector) {
		this.sector = sector;
	}
	public double getValue() {
		return value;
	}
	public void setValue(double value) {
		this.value = value;
	}
	
	

}
