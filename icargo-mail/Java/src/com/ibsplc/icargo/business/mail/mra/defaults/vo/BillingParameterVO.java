package com.ibsplc.icargo.business.mail.mra.defaults.vo;

import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;
/**
 * @author A-9498
 *
 */
public class BillingParameterVO extends AbstractVO {
	private String paramterCode;
	private String parameterDescription;
	private String excludeFlag;
	private String lastUpdatedUser;
	private LocalDate lastUpdatedTime;
	private int tagId;
	private String companyCode;
	private String functionPoint;
	private String functionName;
	private Long serialNumber;
	private String parameterValue;
	
	
	
	public String getParamterCode() {
		return paramterCode;
	}
	public void setParamterCode(String paramterCode) {
		this.paramterCode = paramterCode;
	}
	public String getParameterDescription() {
		return parameterDescription;
	}
	public void setParameterDescription(String parameterDescription) {
		this.parameterDescription = parameterDescription;
	}
	public String getExcludeFlag() {
		return excludeFlag;
	}
	public void setExcludeFlag(String excludeFlag) {
		this.excludeFlag = excludeFlag;
	}
	public String getLastUpdatedUser() {
		return lastUpdatedUser;
	}
	public void setLastUpdatedUser(String lastUpdatedUser) {
		this.lastUpdatedUser = lastUpdatedUser;
	}
	public LocalDate getLastUpdatedTime() {
		return lastUpdatedTime;
	}
	public void setLastUpdatedTime(LocalDate lastUpdatedTime) {
		this.lastUpdatedTime = lastUpdatedTime;
	}
	public int getTagId() {
		return tagId;
	}
	public void setTagId(int tagId) {
		this.tagId = tagId;
	}
	public String getCompanyCode() {
		return companyCode;
	}
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}
	public String getFunctionPoint() {
		return functionPoint;
	}
	public void setFunctionPoint(String functionPoint) {
		this.functionPoint = functionPoint;
	}
	public String getFunctionName() {
		return functionName;
	}
	public void setFunctionName(String functionName) {
		this.functionName = functionName;
	}
	public Long getSerialNumber() {
		return serialNumber;
	}
	public void setSerialNumber(Long serialNumber) {
		this.serialNumber = serialNumber;
	}
	public String getParameterValue() {
		return parameterValue;
	}
	public void setParameterValue(String parameterValue) {
		this.parameterValue = parameterValue;
	}
	
	
}
