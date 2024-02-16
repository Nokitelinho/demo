package com.ibsplc.icargo.presentation.web.model.customermanagement.defaults.ux.brokermapping;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.shared.customer.vo.CustomerVO;

public class ListCustomerModelConverter {

	public static final String SUCCESS = "success";
	public static final String FAILED = "failed";
	public static final String ERROR = "error";
	public static final String NO_DATA = "nodata";

	public static ListCustomerModel convertToListCustomerModel(Collection<CustomerVO> customerListCollection) {
		ListCustomerModel listCustomerModel = new ListCustomerModel();
		listCustomerModel.setCustomerListDetails(convertToCustomerListDetailModel(customerListCollection));
		return listCustomerModel;

	}

	private static Collection<CustomerListDetails> convertToCustomerListDetailModel(
			Collection<CustomerVO> customerListCollection) {
		Collection<CustomerListDetails> customerListDetails = new ArrayList<>();
		CustomerListDetails customerList =null;
		for (CustomerVO customerListVO : customerListCollection) {
			customerList = new CustomerListDetails();
			customerList.setCustomerCode(customerListVO.getCustomerCode());
			customerList.setCustomerName(customerListVO.getCustomerName());
			customerList.setCustomerType(customerListVO.getCustomerType());
			customerList.setCityCode(customerListVO.getCityCode());
			customerList.setCountryCode(customerListVO.getCountry());
			customerList.setZipCode(customerListVO.getZipCode());
			customerList.setStationCode(customerListVO.getStationCode()); 
			customerList.setAddress(customerListVO.getAddress1());
			customerList.setAdditionalNames(customerListVO.getAdditionalNames());
			customerListDetails.add(customerList);
		}
		return customerListDetails;
	}

}
