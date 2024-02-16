package com.ibsplc.icargo.presentation.web.model.mail.mra.defaults;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.web.spring.model.AbstractScreenModel;

public class BillingScheduleFilterModel extends AbstractScreenModel {
	
	private static final String PRODUCT = "mail";
	private static final String SUBPRODUCT = "mra";
	private static final String SCREENID = "mail.mra.ux.mailbillingschedulemaster";
	private String companyCode;
	private String periodNumber;
	private String billingPeriod;
	private String billingType;
	private int year;
	private String displayPage;
	private String pageSize;
	private Map<String, Collection<OneTimeVO>> oneTimeValues;
	private List<BillingScheduleMasterModel> billingScheduleMasterList; 

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

	public String getBillingPeriod() {
		return billingPeriod;
	}

	public void setBillingPeriod(String billingPeriod) {
		this.billingPeriod = billingPeriod;
	}

	public String getBillingType() {
		return billingType;
	}

	public void setBillingType(String billingType) {
		this.billingType = billingType;
	}

	
	public String getDisplayPage() {
		return displayPage;
	}

	public void setDisplayPage(String displayPage) {
		this.displayPage = displayPage;
	}

	public String getPageSize() {
		return pageSize;
	}

	public void setPageSize(String pageSize) {
		this.pageSize = pageSize;
	}

	public Map<String, Collection<OneTimeVO>> getOneTimeValues() {
		return oneTimeValues;
	}

	public void setOneTimeValues(Map<String, Collection<OneTimeVO>> oneTimeValues) {
		this.oneTimeValues = oneTimeValues;
	}

	public List<BillingScheduleMasterModel> getBillingScheduleMasterList() {
		return billingScheduleMasterList;
	}

	public void setBillingScheduleMasterList(List<BillingScheduleMasterModel> billingScheduleMasterList) {
		this.billingScheduleMasterList = billingScheduleMasterList;
	}

	@Override
	public String getProduct() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getScreenId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getSubProduct() {
		// TODO Auto-generated method stub
		return null;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}
	
	

	
}
