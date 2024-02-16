package com.ibsplc.icargo.presentation.web.model.customermanagement.defaults.common;

public class PaymentDetails {
	private String awb;
	private String billingIndicator;
	private String ccaNumber;
	private double totalBilledAmount;
	private double totalSettledAmount;
	private double internalCCAAmount;
	private double dueAmount;
	private String paymentStatus;
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	private String caseClosed;
	private String remarks;
	public String getAwb() {
		return awb;
	}
	public void setAwb(String awb) {
		this.awb = awb;
	}
	public String getBillingIndicator() {
		return billingIndicator;
	}
	public void setBillingIndicator(String billingIndicator) {
		this.billingIndicator = billingIndicator;
	}
	public String getCcaNumber() {
		return ccaNumber;
	}
	public void setCcaNumber(String ccaNumber) {
		this.ccaNumber = ccaNumber;
	}
	public double getTotalBilledAmount() {
		return totalBilledAmount;
	}
	public void setTotalBilledAmount(double totalBilledAmount) {
		this.totalBilledAmount = totalBilledAmount;
	}
	public double getTotalSettledAmount() {
		return totalSettledAmount;
	}
	public void setTotalSettledAmount(double totalSettledAmount) {
		this.totalSettledAmount = totalSettledAmount;
	}
	public double getInternalCCAAmount() {
		return internalCCAAmount;
	}
	public void setInternalCCAAmount(double internalCCAAmount) {
		this.internalCCAAmount = internalCCAAmount;
	}
	public double getDueAmount() {
		return dueAmount;
	}
	public void setDueAmount(double dueAmount) {
		this.dueAmount = dueAmount;
	}
	public String getPaymentStatus() {
		return paymentStatus;
	}
	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}
	public String getCaseClosed() {
		return caseClosed;
	}
	public void setCaseClosed(String caseClosed) {
		this.caseClosed = caseClosed;
	}
	

}
