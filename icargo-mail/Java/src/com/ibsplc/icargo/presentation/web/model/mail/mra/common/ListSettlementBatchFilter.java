package com.ibsplc.icargo.presentation.web.model.mail.mra.common;

public class ListSettlementBatchFilter {
	private String fromDate;
	private String toDate;
	private String batchStatus;
	private String batchId;
	private boolean exportToExcel=false;
	
	public boolean isExportToExcel() {
		return exportToExcel;
	}
	public void setExportToExcel(boolean exportToExcel) {
		this.exportToExcel = exportToExcel;
	}
	public String getFromDate() {
		return fromDate;
	}
	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}
	public String getToDate() {
		return toDate;
	}
	public void setToDate(String toDate) {
		this.toDate = toDate;
	}
	public String getBatchStatus() {
		return batchStatus;
	}
	public void setBatchStatus(String batchStatus) {
		this.batchStatus = batchStatus;
	}
	public String getBatchId() {
		return batchId;
	}
	public void setBatchId(String batchId) {
		this.batchId = batchId;
	}

}
