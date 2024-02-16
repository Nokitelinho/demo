package com.ibsplc.icargo.presentation.web.model.mail.operations.mailinbound;

public class DeliverMailDetails {
	
	private String date;
	
	private String time;
	
	private String partialDeliveryFlag;
	
	private String dsnFlag;

	private String remarks;
	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getPartialDeliveryFlag() {
		return partialDeliveryFlag;
	}

	public void setPartialDeliveryFlag(String partialDeliveryFlag) {
		this.partialDeliveryFlag = partialDeliveryFlag;
	}

	public String getDsnFlag() {
		return dsnFlag;
	}

	public void setDsnFlag(String dsnFlag) {
		this.dsnFlag = dsnFlag;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
	

}
