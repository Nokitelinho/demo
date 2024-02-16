package com.ibsplc.icargo.presentation.mobility.model.mail.operations.mailoffload;

import java.io.Serializable;
import java.util.Collection;

import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;


public class MailOffloadModel implements Serializable{
	

	private String mailBagId;
	private String isMailBag;
	private String offloadReason;
	private String remarks;
	private String companyCode;
	private String airportCode;
	private Collection<MailbagVO> mailDetails;
	
	public String getMailBagId() {
		return mailBagId;
	}
	public void setMailBagId(String mailBagId) {
		this.mailBagId = mailBagId;
	}
	
	public String getIsMailBag() {
		return isMailBag;
	}
	public void setIsMailBag(String isMailBag) {
		this.isMailBag = isMailBag;
	}
	public String getOffloadReason() {
		return offloadReason;
	}
	public void setOffloadReason(String offloadReason) {
		this.offloadReason = offloadReason;
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
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public Collection<MailbagVO> getMailDetails() {
		return mailDetails;
	}
	public void setMailDetails(Collection<MailbagVO> mailDetails) {
		this.mailDetails = mailDetails;
	}
	
	
	
	
	
	
	
	

	

}