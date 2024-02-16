package com.ibsplc.icargo.presentation.web.model.mail.mra.common;

import java.util.Collection;
import java.util.List;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.BillingParameterVO;

public class BillingScheduleDetails  {

	private static final String PRODUCT = "mail";
	private static final String SUBPRODUCT = "mra";
	private static final String SCREENID = "mail.mra.ux.mailbillingschedulemaster";
	private String companyCode;
	private String periodNumber;
	private int serialNumber;
	private String billingPeriodFromDate;
	private String billingPeriodToDate;
	private String gpaInvoiceGenarateDate;
	private String passFileGenerateDate;
	private String masterCutDataDate;
	private String airLineUploadCutDate;
	private String invoiceAvailableDate;
	private String postalOperatorUploadDate;
	private String billingPeriod;
	private String lastUpdatedUser;
	private String lastUpdatedTime;
	private String billingType;
	private int tagId;
	private int year;
	private String __opFlag;
	private String paramterCode;
	private String parameterDescription;
	private String excludeFlag;
	private String functionPoint;
	private String functionName;
	private String parameterValue;
	private Collection<BillingParameterVO> parameterList;
	private Collection<BillingParameterDetails> parametersList;
	public String getCompanyCode() {
		return companyCode;
	}
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}
	public Collection<BillingParameterDetails> getParametersList() {
		return parametersList;
	}
	public void setParametersList(Collection<BillingParameterDetails> parametersList) {
		this.parametersList = parametersList;
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
	public String getBillingPeriodFromDate() {
		return billingPeriodFromDate;
	}
	public void setBillingPeriodFromDate(String billingPeriodFromDate) {
		this.billingPeriodFromDate = billingPeriodFromDate;
	}
	public String getBillingPeriodToDate() {
		return billingPeriodToDate;
	}
	public void setBillingPeriodToDate(String billingPeriodToDate) {
		this.billingPeriodToDate = billingPeriodToDate;
	}
	public String getGpaInvoiceGenarateDate() {
		return gpaInvoiceGenarateDate;
	}
	public void setGpaInvoiceGenarateDate(String gpaInvoiceGenarateDate) {
		this.gpaInvoiceGenarateDate = gpaInvoiceGenarateDate;
	}
	public String getPassFileGenerateDate() {
		return passFileGenerateDate;
	}
	public void setPassFileGenerateDate(String passFileGenerateDate) {
		this.passFileGenerateDate = passFileGenerateDate;
	}
	public String getMasterCutDataDate() {
		return masterCutDataDate;
	}
	public void setMasterCutDataDate(String masterCutDataDate) {
		this.masterCutDataDate = masterCutDataDate;
	}
	public String getAirLineUploadCutDate() {
		return airLineUploadCutDate;
	}
	public void setAirLineUploadCutDate(String airLineUploadCutDate) {
		this.airLineUploadCutDate = airLineUploadCutDate;
	}
	public String getInvoiceAvailableDate() {
		return invoiceAvailableDate;
	}
	public void setInvoiceAvailableDate(String invoiceAvailableDate) {
		this.invoiceAvailableDate = invoiceAvailableDate;
	}
	public String getPostalOperatorUploadDate() {
		return postalOperatorUploadDate;
	}
	public void setPostalOperatorUploadDate(String postalOperatorUploadDate) {
		this.postalOperatorUploadDate = postalOperatorUploadDate;
	}
	public String getBillingPeriod() {
		return billingPeriod;
	}
	public void setBillingPeriod(String billingPeriod) {
		this.billingPeriod = billingPeriod;
	}
	public String getLastUpdatedUser() {
		return lastUpdatedUser;
	}
	public void setLastUpdatedUser(String lastUpdatedUser) {
		this.lastUpdatedUser = lastUpdatedUser;
	}
	public String getLastUpdatedTime() {
		return lastUpdatedTime;
	}
	public void setLastUpdatedTime(String lastUpdatedTime) {
		this.lastUpdatedTime = lastUpdatedTime;
	}
	public String getBillingType() {
		return billingType;
	}
	public void setBillingType(String billingType) {
		this.billingType = billingType;
	}
	public int getTagId() {
		return tagId;
	}
	public void setTagId(int tagId) {
		this.tagId = tagId;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public String get__opFlag() {
		return __opFlag;
	}
	public void set__opFlag(String __opFlag) {
		this.__opFlag = __opFlag;
	}
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
	public static String getProduct() {
		return PRODUCT;
	}
	public static String getSubproduct() {
		return SUBPRODUCT;
	}
	public static String getScreenid() {
		return SCREENID;
	}
	public String getParameterValue() {
		return parameterValue;
	}
	public void setParameterValue(String parameterValue) {
		this.parameterValue = parameterValue;
	}
	public Collection<BillingParameterVO> getParameterList() {
		return parameterList;
	}
	public void setParameterList(Collection<BillingParameterVO> parameterList) {
		this.parameterList = parameterList;
	}
	
	
	
}
