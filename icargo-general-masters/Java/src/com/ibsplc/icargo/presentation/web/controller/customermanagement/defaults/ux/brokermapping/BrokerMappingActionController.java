package com.ibsplc.icargo.presentation.web.controller.customermanagement.defaults.ux.brokermapping;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ibsplc.icargo.framework.web.json.vo.ResponseVO;
import com.ibsplc.icargo.framework.web.spring.controller.AbstractActionController;
import com.ibsplc.icargo.framework.web.spring.resource.ICargoResourceBundle;
import com.ibsplc.icargo.presentation.web.model.customermanagement.defaults.ux.brokermapping.BrokerMappingModel;
import com.ibsplc.icargo.presentation.web.model.customermanagement.defaults.ux.brokermapping.ListCustomerModel;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;

@Controller
@RequestMapping("customermanagement/defaults/brokermapping")
public class BrokerMappingActionController extends AbstractActionController<BrokerMappingModel>{
	
	@Resource(name="brokerMappingResourceBundle")
	ICargoResourceBundle brokerMappingResourceBundle;
	
	@Override
	public ICargoResourceBundle getResourceBundle() {
		return brokerMappingResourceBundle;
	}
	@RequestMapping("/screenLoadBrokerMapping")
	public @ResponseBody ResponseVO screenLoad(@RequestBody BrokerMappingModel brokerMappingModel)
			throws BusinessDelegateException, SystemException {
		return performAction("customermanagement.defaults.ux.brokermapping.screenload", brokerMappingModel);
	}
	@RequestMapping("/searchCustomer")
	public @ResponseBody ResponseVO searchCustomer(@RequestBody ListCustomerModel listCustomerModel)
			throws BusinessDelegateException, SystemException {
		return performAction("customermanagement.defaults.ux.brokermapping.searchcustomer", listCustomerModel);
	}
	@RequestMapping("/list")
	public @ResponseBody ResponseVO findBrokerMappingDetails(@RequestBody BrokerMappingModel brokerMappingModel)
			throws BusinessDelegateException, SystemException {
		return performAction("customermanagement.defaults.ux.brokermapping.findBrokerMappingDetails", brokerMappingModel);
	}
	@RequestMapping("/save")
	public @ResponseBody ResponseVO saveBrokerMappingDetails(@RequestBody BrokerMappingModel brokerMappingModel)
			throws BusinessDelegateException, SystemException {
		return performAction("customermanagement.defaults.ux.brokermapping.saveBrokerMappingDetails", brokerMappingModel);
	}
	@RequestMapping("/delete")
	public @ResponseBody ResponseVO deleteCustomerDetails(@RequestBody BrokerMappingModel brokerMappingModel)
			throws BusinessDelegateException, SystemException {
		return performAction("customermanagement.defaults.ux.brokermapping.deleteCustomerDetails", brokerMappingModel);
	}
	@RequestMapping("/validatePOA")
	public @ResponseBody ResponseVO validateConsigneeDetails(@RequestBody BrokerMappingModel brokerMappingModel)
			throws BusinessDelegateException, SystemException {
		return performAction("customermanagement.defaults.ux.brokermapping.validateConsigneeDetails", brokerMappingModel);
	}
	@RequestMapping("/validateAwbDetails")
	public @ResponseBody ResponseVO validateAwbDetails(@RequestBody BrokerMappingModel brokerMappingModel)
			throws BusinessDelegateException, SystemException {
		return performAction("customermanagement.defaults.ux.brokermapping.validateAwbDetails", brokerMappingModel);
	}
	@RequestMapping("/validateCustomerCode")
	public @ResponseBody ResponseVO validateCustomerCode(@RequestBody BrokerMappingModel brokerMappingModel)
			throws BusinessDelegateException, SystemException {
		return performAction("customermanagement.defaults.ux.brokermapping.validateCustomerCode", brokerMappingModel);
	}
	@RequestMapping("/findPoaAuditHistory")
	public @ResponseBody ResponseVO findPoaAuditHistory(@RequestBody BrokerMappingModel brokerMappingModel)
			throws BusinessDelegateException, SystemException {
		return performAction("customermanagement.defaults.ux.brokermapping.findPoaAuditHistory", brokerMappingModel);
	}
	@RequestMapping("/fetchAdditionalDetails")
	public @ResponseBody ResponseVO fetchAdditionalDetails(@RequestBody BrokerMappingModel brokerMappingModel)
			throws BusinessDelegateException,SystemException{
		return performAction("customermanagement.defaults.ux.brokermapping.fetchAdditionalDetails", brokerMappingModel);
		
	}
}
