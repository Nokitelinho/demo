package com.ibsplc.icargo.business.reco.defaults.vo;

import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;

/**
 * @author A-6843
 *
 */
public class ExceptionEmbargoDetailsVO extends AbstractVO{
	
	private String shipmentPrefix;
	private String masterDocumentNumber;
	private LocalDate startDate;
	private LocalDate endDate;
	private String remarks;
	private String operationFlag;
	private String companyCode;
	private int serialNumbers;
	
	public String getShipmentPrefix() {
		return shipmentPrefix;
	}
	public void setShipmentPrefix(String shipmentPrefix) {
		this.shipmentPrefix = shipmentPrefix;
	}
	public String getMasterDocumentNumber() {
		return masterDocumentNumber;
	}
	public void setMasterDocumentNumber(String masterDocumentNumber) {
		this.masterDocumentNumber = masterDocumentNumber;
	}
	public LocalDate getStartDate() {
		return startDate;
	}
	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}
	public LocalDate getEndDate() {
		return endDate;
	}
	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getOperationFlag() {
		return operationFlag;
	}
	public void setOperationFlag(String operationFlag) {
		this.operationFlag = operationFlag;
	}
	public String getCompanyCode() {
		return companyCode;
	}
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}
	public int getSerialNumbers() {
		return serialNumbers;
	}
	public void setSerialNumbers(int serialNumbers) {
		this.serialNumbers = serialNumbers;
	}
}
