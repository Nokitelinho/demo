package com.ibsplc.icargo.presentation.web.command.customermanagement.defaults.ux.brokermapping;

import java.util.ArrayList;
import java.util.List;

import com.ibsplc.icargo.business.shared.customer.vo.CustomerFilterVO;
import com.ibsplc.icargo.business.shared.customer.vo.CustomerVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.json.vo.ResponseVO;
import com.ibsplc.icargo.framework.web.spring.command.AbstractCommand;
import com.ibsplc.icargo.framework.web.spring.controller.ActionContext;
import com.ibsplc.icargo.presentation.delegate.customermanagement.defaults.CustomerMgmntDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.model.customermanagement.defaults.ux.brokermapping.BrokerMappingFilter;
import com.ibsplc.icargo.presentation.web.model.customermanagement.defaults.ux.brokermapping.BrokerMappingModel;
import com.ibsplc.icargo.presentation.web.model.customermanagement.defaults.ux.brokermapping.BrokerMappingModelConverter;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;

public class ListCommand extends AbstractCommand{

	@Override
	public void execute(ActionContext actionContext) throws BusinessDelegateException, CommandInvocationException {
		
		ResponseVO responseVO= new ResponseVO();
		BrokerMappingModel brokerMappingModel =(BrokerMappingModel) actionContext.getScreenModel();
		LogonAttributes logonAttributes = getLogonAttribute();
		BrokerMappingFilter brokerMappingFilter = brokerMappingModel.getBrokerMappingFilter();
		CustomerFilterVO filterVO = new CustomerFilterVO();
		CustomerVO customerVO = new CustomerVO();
    	filterVO.setCustomerCode(brokerMappingFilter.getCustomerCode());
    	filterVO.setCompanyCode(logonAttributes.getCompanyCode());
    	filterVO.setSource("MNGPOA");
    	CustomerMgmntDefaultsDelegate delegate  = new CustomerMgmntDefaultsDelegate();
    	try{
    		customerVO = delegate.listCustomer(filterVO);
    		
    	}catch(BusinessDelegateException ex){
    		handleDelegateException(ex);
    	}
    	brokerMappingModel=BrokerMappingModelConverter.convertToBrokerMappingModel(customerVO);
		brokerMappingModel.setBrokerMappingFilter(brokerMappingFilter);

		List<BrokerMappingModel> results = new ArrayList<>();
		results.add(brokerMappingModel);
		responseVO.setResults(results);
		actionContext.setResponseVO(responseVO);
	}

}
