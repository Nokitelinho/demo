package com.ibsplc.icargo.presentation.web.model.customermanagement.defaults.ux.brokermapping;

public class PoaHistoryDetails {
	
	private String transactionCode;
	private String userId;
	private String deletionDate;
	private String adlInfo;
	private String station;
	private String triggerPoint;

	public String getTransactionCode() {
		return transactionCode;
	}

	public void setTransactionCode(String transactionCode) {
		this.transactionCode = transactionCode;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getDeletionDate() {
		return deletionDate;
	}

	public void setDeletionDate(String deletionDate) {
		this.deletionDate = deletionDate;
	}

	public String getAdlInfo() {
		return adlInfo;
	}

	public void setAdlInfo(String adlInfo) {
		this.adlInfo = adlInfo;
	}

	public String getStation() {
		return station;
	}

	public void setStation(String station) {
		this.station = station;
	}

	public String getTriggerPoint() {
		return triggerPoint;
	}

	public void setTriggerPoint(String triggerPoint) {
		this.triggerPoint = triggerPoint;
	}
	
}
