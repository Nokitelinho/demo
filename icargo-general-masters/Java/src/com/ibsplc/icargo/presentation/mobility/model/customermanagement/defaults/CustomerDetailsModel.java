package com.ibsplc.icargo.presentation.mobility.model.customermanagement.defaults;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
public class CustomerDetailsModel implements Serializable{

	private String responseSentTime;
	private List<CustomerModel> customerDetails = new ArrayList<CustomerModel>();

	public void setResponseSentTime(String responseTime) {
		this.responseSentTime = responseTime;
	}

	public List<CustomerModel> getCustomerDetails() {
		return customerDetails;
	}

	public String getResponseSentTime() {
		return responseSentTime;
	}

	public void setCustomerDetails(List<CustomerModel> customerDetails) {
		this.customerDetails = customerDetails;
	}

}
