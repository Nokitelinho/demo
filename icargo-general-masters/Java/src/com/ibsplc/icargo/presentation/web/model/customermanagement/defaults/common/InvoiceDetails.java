package com.ibsplc.icargo.presentation.web.model.customermanagement.defaults.common;


public class InvoiceDetails {
	
	private String invoiceNumber;
	private String invoiceDate;
	private String periodFromDate;
	private String periodToDate;
	private double orgBilledAmount;
	private double adjBilledAmount;
	private double netBilledAmount;
	private double paidAmount;
	private double outstandingAmount;
	private int age;
	private String lastActedDate;
	private String status;
	private String billingType;
	private String adjustmentFlag;
	private String countryCode;
	//Added by A-8169 for ICRD-308533 starts
	private String cassFlag;
	//Added by A-8169 for ICRD-308533 ends
	
	public String getAdjustmentFlag() {
		return adjustmentFlag;
	}
	public void setAdjustmentFlag(String adjustmentFlag) {
		this.adjustmentFlag = adjustmentFlag;
	}
	public String getCountryCode() {
		return countryCode;
	}
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}
	public String getBillingType() {
		return billingType;
	}
	public void setBillingType(String billingType) {
		this.billingType = billingType;
	}
	public String getInvoiceNumber() {
		return invoiceNumber;
	}
	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}
	public String getInvoiceDate() {
		return invoiceDate;
	}
	public void setInvoiceDate(String invoiceDate) {
		this.invoiceDate = invoiceDate;
	}
	public String getPeriodFromDate() {
		return periodFromDate;
	}
	public void setPeriodFromDate(String periodFromDate) {
		this.periodFromDate = periodFromDate;
	}
	public String getPeriodToDate() {
		return periodToDate;
	}
	public void setPeriodToDate(String periodToDate) {
		this.periodToDate = periodToDate;
	}
	public double getOrgBilledAmount() {
		return orgBilledAmount;
	}
	public void setOrgBilledAmount(double orgBilledAmount) {
		this.orgBilledAmount = orgBilledAmount;
	}
	public double getAdjBilledAmount() {
		return adjBilledAmount;
	}
	public void setAdjBilledAmount(double adjBilledAmount) {
		this.adjBilledAmount = adjBilledAmount;
	}
	public double getNetBilledAmount() {
		return netBilledAmount;
	}
	public void setNetBilledAmount(double netBilledAmount) {
		this.netBilledAmount = netBilledAmount;
	}
	public double getPaidAmount() {
		return paidAmount;
	}
	public void setPaidAmount(double paidAmount) {
		this.paidAmount = paidAmount;
	}
	public double getOutstandingAmount() {
		return outstandingAmount;
	}
	public void setOutstandingAmount(double outstandingAmount) {
		this.outstandingAmount = outstandingAmount;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getLastActedDate() {
		return lastActedDate;
	}
	public void setLastActedDate(String lastActedDate) {
		this.lastActedDate = lastActedDate;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * @return the cassFlag
	 */
	public String getCassFlag() {
		return cassFlag;
	}
	/**
	 * @param cassFlag the cassFlag to set
	 */
	public void setCassFlag(String cassFlag) {
		this.cassFlag = cassFlag;
	}
}
