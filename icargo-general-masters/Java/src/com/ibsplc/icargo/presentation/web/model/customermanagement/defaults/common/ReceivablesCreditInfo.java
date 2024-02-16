package com.ibsplc.icargo.presentation.web.model.customermanagement.defaults.common;

public class ReceivablesCreditInfo {
	public String getCustomerType() {
		return customerType;
	}
	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}
	public String getVatRegNumber() {
		return vatRegNumber;
	}
	public void setVatRegNumber(String vatRegNumber) {
		this.vatRegNumber = vatRegNumber;
	}
	public double getCreditLimit() {
		return creditLimit;
	}
	public void setCreditLimit(double creditLimit) {
		this.creditLimit = creditLimit;
	}
	public double getAvailableCreditLimit() {
		return availableCreditLimit;
	}
	public void setAvailableCreditLimit(double availableCreditLimit) {
		this.availableCreditLimit = availableCreditLimit;
	}
	public int getDaysToClose() {
		return daysToClose;
	}
	public void setDaysToClose(int daysToClose) {
		this.daysToClose = daysToClose;
	}
	public String getRestrictedFOP() {
		return restrictedFOP;
	}
	public void setRestrictedFOP(String restrictedFOP) {
		this.restrictedFOP = restrictedFOP;
	}
	public String getCollectionAgency() {
		return collectionAgency;
	}
	public void setCollectionAgency(String collectionAgency) {
		this.collectionAgency = collectionAgency;
	}
	private String customerType;
	private String vatRegNumber;
	private double creditLimit;
	private double availableCreditLimit;
	private int daysToClose;
	private String restrictedFOP;
	public double getOutstandingReceivableValue() {
		return outstandingReceivableValue;
	}
	public void setOutstandingReceivableValue(double outstandingReceivableValue) {
		this.outstandingReceivableValue = outstandingReceivableValue;
	}
	public int getOutstandingReceivableCount() {
		return outstandingReceivableCount;
	}
	public void setOutstandingReceivableCount(int outstandingReceivableCount) {
		this.outstandingReceivableCount = outstandingReceivableCount;
	}
	public double getUnbilledReceivableValue() {
		return unbilledReceivableValue;
	}
	public void setUnbilledReceivableValue(double unbilledReceivableValue) {
		this.unbilledReceivableValue = unbilledReceivableValue;
	}
	public int getUnbilledReceivableCount() {
		return unbilledReceivableCount;
	}
	public void setUnbilledReceivableCount(int unbilledReceivableCount) {
		this.unbilledReceivableCount = unbilledReceivableCount;
	}
	private String collectionAgency;
	private double outstandingReceivableValue;
	private int outstandingReceivableCount;
	private double unbilledReceivableValue;
	private int unbilledReceivableCount;
	

}
