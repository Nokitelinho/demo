package com.ibsplc.icargo.business.mail.mra.defaults.vo;

import java.util.Collection;
import java.util.List;

import com.ibsplc.icargo.framework.util.time.LocalDate;
/** import com.ibsplc.icargo.presentation.web.model.mail.mra.defaults.BillingScheduleMasterModel;*/
import com.ibsplc.xibase.server.framework.vo.AbstractVO;
/**
 * @author A-9498
 *
 */
public class BillingScheduleDetailsVO extends AbstractVO {
	private String companyCode;
	private String periodNumber;
	private int serialNumber;
	private LocalDate billingPeriodFromDate;
	private LocalDate billingPeriodToDate;
	private LocalDate gpaInvoiceGenarateDate;
	private LocalDate passFileGenerateDate;
	private LocalDate masterCutDataDate;
	private LocalDate airLineUploadCutDate;
	private LocalDate invoiceAvailableDate;
	private LocalDate postalOperatorUploadDate;
	private String lastUpdatedUser;
	private LocalDate lastUpdatedTime;
	private int tagId;
	private String billingType;
	private String billingPeriod;
	private String opearationFlag;
	/** private List<BillingScheduleMasterModel> billingScheduleMasterList; */
	private String paramterCode;
	private String parameterDescription;
	private String excludeFlag;
	private String functionPoint;
	private String functionName;
	private String parameterValue;
	private Collection<BillingParameterVO> paramsList;
	public Collection<BillingParameterVO> getParamsList() {
		return paramsList;
	}
	public void setParamsList(Collection<BillingParameterVO> paramsList) {
		this.paramsList = paramsList;
	}
	public String getOpearationFlag() {
		return opearationFlag;
	}
	public void setOpearationFlag(String opearationFlag) {
		this.opearationFlag = opearationFlag;
	}
	public String getBillingType() {
		return billingType;
	}
	public void setBillingType(String billingType) {
		this.billingType = billingType;
	}
	public String getBillingPeriod() {
		return billingPeriod;
	}
	public void setBillingPeriod(String billingPeriod) {
		this.billingPeriod = billingPeriod;
	}
	public String getCompanyCode() {
		return companyCode;
	}
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}
	public String getPeriodNumber() {
		return periodNumber;
	}
	public void setPeriodNumber(String periodNumber) {
		this.periodNumber = periodNumber;
	}
	public int getSerialNumber() {
		return serialNumber;
	}
	public void setSerialNumber(int serialNumber) {
		this.serialNumber = serialNumber;
	}
	public LocalDate getBillingPeriodFromDate() {
		return billingPeriodFromDate;
	}
	public void setBillingPeriodFromDate(LocalDate billingPeriodFromDate) {
		this.billingPeriodFromDate = billingPeriodFromDate;
	}
	public LocalDate getBillingPeriodToDate() {
		return billingPeriodToDate;
	}
	public void setBillingPeriodToDate(LocalDate billingPeriodToDate) {
		this.billingPeriodToDate = billingPeriodToDate;
	}
	public LocalDate getGpaInvoiceGenarateDate() {
		return gpaInvoiceGenarateDate;
	}
	public void setGpaInvoiceGenarateDate(LocalDate gpaInvoiceGenarateDate) {
		this.gpaInvoiceGenarateDate = gpaInvoiceGenarateDate;
	}
	public LocalDate getPassFileGenerateDate() {
		return passFileGenerateDate;
	}
	public void setPassFileGenerateDate(LocalDate passFileGenerateDate) {
		this.passFileGenerateDate = passFileGenerateDate;
	}
	public LocalDate getMasterCutDataDate() {
		return masterCutDataDate;
	}
	public void setMasterCutDataDate(LocalDate masterCutDataDate) {
		this.masterCutDataDate = masterCutDataDate;
	}
	public LocalDate getAirLineUploadCutDate() {
		return airLineUploadCutDate;
	}
	public void setAirLineUploadCutDate(LocalDate airLineUploadCutDate) {
		this.airLineUploadCutDate = airLineUploadCutDate;
	}
	public LocalDate getInvoiceAvailableDate() {
		return invoiceAvailableDate;
	}
	public void setInvoiceAvailableDate(LocalDate invoiceAvailableDate) {
		this.invoiceAvailableDate = invoiceAvailableDate;
	}
	public LocalDate getPostalOperatorUploadDate() {
		return postalOperatorUploadDate;
	}
	public void setPostalOperatorUploadDate(LocalDate postalOperatorUploadDate) {
		this.postalOperatorUploadDate = postalOperatorUploadDate;
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
	/** public List<BillingScheduleMasterModel> getBillingScheduleMasterList() {
		return billingScheduleMasterList;
	}
	public void setBillingScheduleMasterList(List<BillingScheduleMasterModel> billingScheduleMasterList) {
		this.billingScheduleMasterList = billingScheduleMasterList;
	} */
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
	public String getParameterValue() {
		return parameterValue;
	}
	public void setParameterValue(String parameterValue) {
		this.parameterValue = parameterValue;
	}
	
	
}
