package com.ibsplc.icargo.presentation.web.model.mail.mra.defaults;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.BillingParameterVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.web.json.PageResult;
import com.ibsplc.icargo.framework.web.spring.model.AbstractScreenModel;
import com.ibsplc.icargo.presentation.web.model.mail.mra.common.BillingScheduleFilter;
import com.ibsplc.icargo.presentation.web.model.mail.mra.common.BillingParameterDetails;
import com.ibsplc.icargo.presentation.web.model.mail.mra.common.BillingScheduleDetails;

public class BillingScheduleMasterModel extends AbstractScreenModel {


	private Map<String, Collection<OneTimeVO>> oneTimeValues;

	private static final String PRODUCT = "mail";
	private static final String SUBPRODUCT = "mra";
	private static final String SCREENID = "mail.mra.ux.mailbillingschedulemaster";
	
	private List<BillingScheduleDetails> billingScheduleDetailList;
	private PageResult<BillingScheduleDetails> billingScheduleMasterPage;
	private BillingScheduleFilter billingScheduleFilter;

	private Map<Integer, Collection<BillingParameterDetails>> parameterMap;
	private Collection<BillingParameterDetails> parametersList;

	

	public Collection<BillingParameterDetails> getParametersList() {
		return parametersList;
	}

	public void setParametersList(Collection<BillingParameterDetails> parametersList) {
		this.parametersList = parametersList;
	}

	@Override
	public String getProduct() {
		return PRODUCT;
	}

	@Override
	public String getScreenId() {
		return SCREENID;

	}

	@Override
	public String getSubProduct() {
		return SUBPRODUCT;
	}

	public Map<String, Collection<OneTimeVO>> getOneTimeValues() {
		return oneTimeValues;
	}

	public void setOneTimeValues(Map<String, Collection<OneTimeVO>> oneTimeValues) {
		this.oneTimeValues = oneTimeValues;
	}

	public List<BillingScheduleDetails> getBillingScheduleDetailList() {
		return billingScheduleDetailList;
	}

	public void setBillingScheduleDetailList(List<BillingScheduleDetails> billingScheduleDetailList) {
		this.billingScheduleDetailList = billingScheduleDetailList;
	}

	public PageResult<BillingScheduleDetails> getBillingScheduleMasterPage() {
		return billingScheduleMasterPage;
	}

	public void setBillingScheduleMasterPage(PageResult<BillingScheduleDetails> billingScheduleMasterPage) {
		this.billingScheduleMasterPage = billingScheduleMasterPage;
	}

	public BillingScheduleFilter getBillingScheduleFilter() {
		return billingScheduleFilter;
	}

	public void setBillingScheduleFilter(BillingScheduleFilter billingScheduleFilter) {
		this.billingScheduleFilter = billingScheduleFilter;
	}

	public Map<Integer, Collection<BillingParameterDetails>> getParameterMap() {
		return parameterMap;
	}

	public void setParameterMap(Map<Integer, Collection<BillingParameterDetails>> parameterMap) {
		this.parameterMap = parameterMap;
	}

	
}