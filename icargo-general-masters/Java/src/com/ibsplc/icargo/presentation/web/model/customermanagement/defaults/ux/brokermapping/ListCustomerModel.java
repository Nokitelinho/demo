package com.ibsplc.icargo.presentation.web.model.customermanagement.defaults.ux.brokermapping;

import java.util.Collection;

import com.ibsplc.icargo.framework.web.spring.model.AbstractScreenModel;

public class ListCustomerModel extends AbstractScreenModel{
	
	private static final long serialVersionUID = 1L;
	/*
	 * The constant variable for product customermanagement
	 */
	private static final String PRODUCT = "customermanagement";
	/*
	 * The constant for sub product defaults
	 */
	private static final String SUBPRODUCT = "defaults";
	/*
	 * The constant for screen id
	 */
	private static final String SCREENID = "customermanagement.defaults.ux.brokermapping";
	
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
	
	private CustomerListFilter customerListFilter;
	
	private Collection<CustomerListDetails> customerListDetails;
	
	public CustomerListFilter getCustomerListFilter() {
		return customerListFilter;
	}

	public void setCustomerListFilter(CustomerListFilter customerListFilter) {
		this.customerListFilter = customerListFilter;
	}

	public Collection<CustomerListDetails> getCustomerListDetails() {
		return customerListDetails;
	}

	public void setCustomerListDetails(Collection<CustomerListDetails> customerListDetails) {
		this.customerListDetails = customerListDetails;
	}

	

}
