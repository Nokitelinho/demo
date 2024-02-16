package com.ibsplc.icargo.presentation.mobility.model.mail.operations.maildamagecapture;

import java.io.Serializable;

public class MailDamageCaptureModel implements Serializable {

	private String mailBagId;
	private String damageCode;
	private String remarks;
	private String isReturn;
	private String companyCode;
	private String airportCode;
	private String screeningStaff;
	
	public String getScreeningStaff() {
		return screeningStaff;
	}
	public void setScreeningStaff(String screeningStaff) {
		this.screeningStaff = screeningStaff;
	}
	public String getMailBagId() {
		return mailBagId;
	}
	public void setMailBagId(String mailBagId) {
		this.mailBagId = mailBagId;
	}
	public String getDamageCode() {
		return damageCode;
	}
	public void setDamageCode(String damageCode) {
		this.damageCode = damageCode;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getIsReturn() {
		return isReturn;
	}
	public void setIsReturn(String isReturn) {
		this.isReturn = isReturn;
	}
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
