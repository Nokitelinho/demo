package com.ibsplc.icargo.presentation.web.command.customermanagement.defaults.ux.brokermapping;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

import com.ibsplc.icargo.business.shared.customer.vo.CustomerFilterVO;
import com.ibsplc.icargo.business.shared.customer.vo.CustomerVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.spring.command.AbstractCommand;
import com.ibsplc.icargo.framework.web.spring.controller.ActionContext;
import com.ibsplc.icargo.framework.web.json.vo.ResponseVO;
import com.ibsplc.icargo.presentation.delegate.shared.customer.CustomerDelegate;
import com.ibsplc.icargo.presentation.web.model.customermanagement.defaults.ux.brokermapping.CustomerListFilter;
import com.ibsplc.icargo.presentation.web.model.customermanagement.defaults.ux.brokermapping.ListCustomerModel;
import com.ibsplc.icargo.presentation.web.model.customermanagement.defaults.ux.brokermapping.ListCustomerModelConverter;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;


public class ListCustomerCommand extends  AbstractCommand {

	private static final Log LOGGER = LogFactory.getLogger("LIST CUSTOMER DETAILS");
	
	private static final String LIST_SUCCESS = "list_success";

	@Override
	public void execute(ActionContext actionContext) throws BusinessDelegateException, CommandInvocationException {
		LOGGER.entering("ListCustomerCommand-BrokerMapping", "execute");

		ResponseVO responseVO = new ResponseVO();

		ListCustomerModel customerListModel = (ListCustomerModel) actionContext.getScreenModel();
		LogonAttributes logonAttributes = (LogonAttributes) getLogonAttribute(); 

		CustomerListFilter customerListFilter=customerListModel.getCustomerListFilter();
		Collection<ListCustomerModel> results = new ArrayList<>();
		Collection<CustomerVO> customerlist=new ArrayList<>();
		
		CustomerFilterVO filterVO = populateFilterVO(customerListFilter);
		filterVO.setCompanyCode(logonAttributes.getCompanyCode());
		
		CustomerDelegate delegate = new CustomerDelegate();
		
		try {
			customerlist = delegate.validateCustomerName(filterVO);
			customerListModel=ListCustomerModelConverter.convertToListCustomerModel(customerlist);
		} catch (BusinessDelegateException e) {
			handleDelegateException(e);
		}
		results.add(customerListModel);
		responseVO.setResults(results);
		responseVO.setStatus(LIST_SUCCESS);
		actionContext.setResponseVO(responseVO);
		LOGGER.exiting("ListBrokerConsigneeCommand-BrokerConsignee", "execute");
		
	}

	private CustomerFilterVO populateFilterVO(CustomerListFilter customerListFilter) {

		CustomerFilterVO customerFilterVO=new CustomerFilterVO();
		String types = "";
		customerFilterVO.setCustomerCode(customerListFilter.getCustomerCode());
		if (Objects.nonNull(customerListFilter.getCustomerType()) && customerListFilter.getCustomerType().length > 0) {
			types=String.join(",", customerListFilter.getCustomerType());
			customerFilterVO.setCustomerType(types);
		}
		customerFilterVO.setCustomerName(customerListFilter.getCustomerName());
		customerFilterVO.setCityCode(customerListFilter.getCityCode());
		customerFilterVO.setCountryCodes(customerListFilter.getCountryCode());
		customerFilterVO.setZipCode(customerListFilter.getZipCode());
		customerFilterVO.setStationCode(customerListFilter.getStationCode());
		if(null!=customerListFilter.getAdditionalNames()){
		customerFilterVO.setAdditionalNames(customerListFilter.getAdditionalNames().toString());
		}
		customerFilterVO.setSource("MNGPOA");
		customerFilterVO.setStationCode(customerListFilter.getStationCode());
		customerFilterVO.setAddress(customerListFilter.getAddress()); 
		return customerFilterVO;
	}
	
}
