package com.ibsplc.icargo.business.reco.defaults.vo;

import com.ibsplc.icargo.framework.util.time.GMTDate;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;

public class RegulatoryMessageVO extends AbstractVO{

	private String companyCode;
	
	private int serialNumber;;
	
	private String rolGroup;
	
	private String message;
	
	private LocalDate startDate;
	
	private LocalDate endDate;

	private LocalDate lastUpdateTime;
	
	private String lastUpdateUser;
	
	private String operationFlag;
	
	private GMTDate updatedTransactionTime;
	
	private LocalDate updatedTransactionTimeView;
	
	public String getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	public int getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(int serialNumber) {
		this.serialNumber = serialNumber;
	}

	public String getRolGroup() {
		return rolGroup;
	}

	public void setRolGroup(String rolGroup) {
		this.rolGroup = rolGroup;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public LocalDate getLastUpdateTime() {
		return lastUpdateTime;
	}

	public void setLastUpdateTime(LocalDate lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

	public String getLastUpdateUser() {
		return lastUpdateUser;
	}

	public void setLastUpdateUser(String lastUpdateUser) {
		this.lastUpdateUser = lastUpdateUser;
	}
	public String getOperationFlag() {
		return operationFlag;
	}

	public void setOperationFlag(String operationFlag) {
		this.operationFlag = operationFlag;
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

	public void setUpdatedTransactionTime(GMTDate updatedTransactionTime) {
		this.updatedTransactionTime = updatedTransactionTime;
	}

	public GMTDate getUpdatedTransactionTime() {
		return updatedTransactionTime;
	}

	public void setUpdatedTransactionTimeView(LocalDate updatedTransactionTimeView) {
		this.updatedTransactionTimeView = updatedTransactionTimeView;
	}

	public LocalDate getUpdatedTransactionTimeView() {
		return updatedTransactionTimeView;
	}
	
}
