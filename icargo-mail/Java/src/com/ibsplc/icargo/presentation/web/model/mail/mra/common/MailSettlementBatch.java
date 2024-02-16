package com.ibsplc.icargo.presentation.web.model.mail.mra.common;

public class MailSettlementBatch {
	
	private String companyCode;
	
	private String batchId;
	
	private long batchSequenceNum;	 
	
    private String source;
    
    private String batchStatus;
    
    private String batchDate;
    
    private double batchamount;
    
    private double appliedAmount;
    
    private double unAppliedAmount;
    
    private int numberofrecords;
    
    private String currencyCode;
    
    private String gpaCode;

    private int recordCount;	

	public String getGpaCode() {
		return gpaCode;
	}

	public void setGpaCode(String gpaCode) {
		this.gpaCode = gpaCode;
	}

	public String getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	public String getBatchId() {
		return batchId;
	}

	public void setBatchId(String batchId) {
		this.batchId = batchId;
	}
	
	public long getBatchSequenceNum() {
		return batchSequenceNum;
	}

	public void setBatchSequenceNum(long batchSequenceNum) {
		this.batchSequenceNum = batchSequenceNum;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getBatchStatus() {
		return batchStatus;
	}

	public void setBatchStatus(String batchStatus) {
		this.batchStatus = batchStatus;
	}

	public String getBatchDate() {
		return batchDate;
	}

	public void setBatchDate(String batchDate) {
		this.batchDate = batchDate;
	}

	public double getBatchamount() {
		return batchamount;
	}

	public void setBatchamount(double batchamount) {
		this.batchamount = batchamount;
	}

	public double getAppliedAmount() {
		return appliedAmount;
	}

	public void setAppliedAmount(double appliedAmount) {
		this.appliedAmount = appliedAmount;
	}

	public double getUnAppliedAmount() {
		return unAppliedAmount;
	}

	public void setUnAppliedAmount(double unAppliedAmount) {
		this.unAppliedAmount = unAppliedAmount;
	}

	public int getNumberofrecords() {
		return numberofrecords;
	}

	public void setNumberofrecords(int numberofrecords) {
		this.numberofrecords = numberofrecords;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public int getRecordCount() {
		return recordCount;
	}

	public void setRecordCount(int recordCount) {
		this.recordCount = recordCount;
	}
	

}
