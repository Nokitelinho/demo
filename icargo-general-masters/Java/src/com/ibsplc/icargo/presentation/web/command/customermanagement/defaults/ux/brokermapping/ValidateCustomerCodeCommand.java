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
import com.ibsplc.icargo.presentation.web.model.customermanagement.defaults.ux.brokermapping.BrokerMappingModel;
import com.ibsplc.icargo.presentation.web.model.customermanagement.defaults.ux.brokermapping.CustomerCodeValidation;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;

public class ValidateCustomerCodeCommand extends AbstractCommand{
	private static final String CUSTOMER_CODE_VALID = "CUSTOMER_CODE_VALID";
	@Override
	public void execute(ActionContext actionContext) throws BusinessDelegateException, CommandInvocationException {
		ResponseVO responseVO = new ResponseVO();
		BrokerMappingModel brokerMappingModel=(BrokerMappingModel) actionContext.getScreenModel();
		LogonAttributes logonAttributes= getLogonAttribute();
		CustomerCodeValidation customerInfo=brokerMappingModel.getCustomerCodeValidation();
		CustomerFilterVO filterVO = new CustomerFilterVO();
		CustomerVO customerVO = new CustomerVO();
		filterVO.setCustomerCode(customerInfo.getCustomerCode());
    	filterVO.setCompanyCode(logonAttributes.getCompanyCode());
    	ErrorVO error=null;
    	CustomerMgmntDefaultsDelegate delegate  = new CustomerMgmntDefaultsDelegate();
    	try{
    		customerVO = delegate.listCustomer(filterVO);
    		
    	}catch(BusinessDelegateException ex){
    		handleDelegateException(ex);
    	}
    	if(customerVO!=null){
    		customerInfo.setCustomerName(customerVO.getCustomerName());
    		brokerMappingModel.setCustomerCodeValidation(customerInfo);
    		List<BrokerMappingModel> results = new ArrayList<>();
    		results.add(brokerMappingModel);
    		responseVO.setResults(results);
    		responseVO.setStatus(CUSTOMER_CODE_VALID);
    	}
    	else{
    		error=new ErrorVO("Invalid Customer Code");
			List<ErrorVO> results = new ArrayList<>();
    		results.add(error);
    		responseVO.setResults(results);
    	}
    	actionContext.setResponseVO(responseVO);
	}

}
