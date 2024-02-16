package com.ibsplc.icargo.presentation.web.model.customermanagement.defaults.ux.brokermapping;

import java.util.Collection;
import java.util.Map;

import com.ibsplc.icargo.framework.web.spring.model.AbstractScreenModel;
import com.ibsplc.icargo.presentation.web.model.shared.defaults.common.OneTime;

public class BrokerMappingModel extends AbstractScreenModel{
	
	private static final String PRODUCT = "customermanagement";
	private static final String SUBPRODUCT = "defaults";
	private static final String SCREENID = "customermanagement.defaults.ux.brokermapping";
	
	private boolean warningFlag;
	
	private BrokerMappingFilter brokerMappingFilter;
	
	private CustomerDetails customerDetails;
	
	private Collection<PoaMappingDetails> brokerDetails;
	
	private Collection<PoaMappingDetails> consigneeDetails;
	
	private Collection<PoaMappingDetails> awbDetails;
	
	private PoaMappingDetails validateConsigneeDetails;
	
	private AwbFilter awbFilter;
	
	private CustomerCodeValidation customerCodeValidation;
	
	private Map<String, Collection<OneTime>> oneTimeValues;
	
	private Collection<PoaHistoryDetails> poaHistoryDetails;
	
	public BrokerMappingFilter getBrokerMappingFilter() {
		return brokerMappingFilter;
	}
	
	public void setBrokerMappingFilter(BrokerMappingFilter brokerMappingFilter) {
		this.brokerMappingFilter = brokerMappingFilter;
	}
	
	public CustomerDetails getCustomerDetails() {
		return customerDetails;
	}

	public void setCustomerDetails(CustomerDetails customerDetails) {
		this.customerDetails = customerDetails;
	}

	public Collection<PoaMappingDetails> getBrokerDetails() {
		return brokerDetails;
	}

	public void setBrokerDetails(Collection<PoaMappingDetails> brokerDetails) {
		this.brokerDetails = brokerDetails;
	}

	public Collection<PoaMappingDetails> getConsigneeDetails() {
		return consigneeDetails;
	}

	public void setConsigneeDetails(Collection<PoaMappingDetails> consigneeDetails) {
		this.consigneeDetails = consigneeDetails;
	}
	
	public boolean isWarningFlag() {
		return warningFlag;
	}

	public void setWarningFlag(boolean warningFlag) {
		this.warningFlag = warningFlag;
	}
	
	public Collection<PoaMappingDetails> getAwbDetails() {
		return awbDetails;
	}

	public void setAwbDetails(Collection<PoaMappingDetails> awbDetails) {
		this.awbDetails = awbDetails;
	}

	public Map<String, Collection<OneTime>> getOneTimeValues() {
		return oneTimeValues;
	}

	public void setOneTimeValues(Map<String, Collection<OneTime>> oneTimeValues) {
		this.oneTimeValues = oneTimeValues;
	}
	
	public PoaMappingDetails getValidateConsigneeDetails() {
		return validateConsigneeDetails;
	}

	public void setValidateConsigneeDetails(PoaMappingDetails validateConsigneeDetails) {
		this.validateConsigneeDetails = validateConsigneeDetails;
	}
	
	public AwbFilter getAwbFilter() {
		return awbFilter;
	}

	public void setAwbFilter(AwbFilter awbFilter) {
		this.awbFilter = awbFilter;
	}
	
	public CustomerCodeValidation getCustomerCodeValidation() {
		return customerCodeValidation;
	}

	public void setCustomerCodeValidation(CustomerCodeValidation customerCodeValidation) {
		this.customerCodeValidation = customerCodeValidation;
	}

	public Collection<PoaHistoryDetails> getPoaHistoryDetails() {
		return poaHistoryDetails;
	}

	public void setPoaHistoryDetails(Collection<PoaHistoryDetails> poaHistoryDetails) {
		this.poaHistoryDetails = poaHistoryDetails;
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

}
