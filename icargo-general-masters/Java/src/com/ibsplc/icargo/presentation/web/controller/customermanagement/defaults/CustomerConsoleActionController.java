/**
 *	Java file	: 	com.ibsplc.icargo.presentation.web.model.customermanagement.agentbilling.ListSettlementbatchsActionController.java
 *
 *	Created by	:	A-5791
 *	Created on	:	Jun 4, 2018
 *
 *  Copyright 2018 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 *
 * 	This software is the proprietary information of Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
 * 	Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.controller.customermanagement.defaults;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ibsplc.icargo.framework.web.json.vo.ResponseVO;
import com.ibsplc.icargo.framework.web.spring.controller.AbstractActionController;
import com.ibsplc.icargo.framework.web.spring.resource.ICargoResourceBundle;
import com.ibsplc.icargo.presentation.web.model.customermanagement.defaults.CustomerConsoleModel;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;

/**
 *	Java file	: 	com.ibsplc.icargo.presentation.web.controller.customermanagement.defaults.CustomerConsoleActionController.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-8227	:	Sep 4, 2018	:	Draft
 */
@Controller
@RequestMapping("customermanagement/defaults/customerconsole")
public class CustomerConsoleActionController extends AbstractActionController<CustomerConsoleModel>{
	
	@Resource(name="customerConsoleResourceBundle")
	ICargoResourceBundle customerConsoleResourceBundle;
	/**
	 *	Overriding Method	:	@see com.ibsplc.icargo.framework.web.spring.controller.AbstractActionController#getResourceBundle()
	 *	Added by 			: A-8227 on Sep 4, 2018
	 * 	Used for 	:
	 *	Parameters	:	@return 
	 */
	@Override
	public ICargoResourceBundle getResourceBundle() {
		return customerConsoleResourceBundle;
	}
	@RequestMapping("/getCustomerDetails")
	public @ResponseBody ResponseVO getCustomerDetails(@RequestBody CustomerConsoleModel customerConsoleModel)
		throws BusinessDelegateException, SystemException{
		return performAction("customermanagement.defaults.ux.customerconsole.getCustomerDetails",customerConsoleModel);
	}
	@RequestMapping("/getBillingInvoiceDetails")
	public @ResponseBody ResponseVO getBillingInvoiceDetails(@RequestBody CustomerConsoleModel customerConsoleModel)
		throws BusinessDelegateException, SystemException{
		return performAction("customermanagement.defaults.ux.customerconsole.getBillingInvoiceDetails",customerConsoleModel);
	}
	@RequestMapping("/getCCADetails")
	public @ResponseBody ResponseVO getCCADetails(@RequestBody CustomerConsoleModel customerConsoleModel)
		throws BusinessDelegateException, SystemException{
		return performAction("customermanagement.defaults.ux.customerconsole.getCCADetails",customerConsoleModel);
	}
	@RequestMapping("/getPaymentDetails")
	public @ResponseBody ResponseVO getPaymentDetails(@RequestBody CustomerConsoleModel customerConsoleModel)
		throws BusinessDelegateException, SystemException{
		return performAction("customermanagement.defaults.ux.customerconsole.getPaymentDetails",customerConsoleModel);
	}
	@RequestMapping("/reminderList")
	public @ResponseBody ResponseVO updateReminderListSession(@RequestBody CustomerConsoleModel customerConsoleModel)
		throws BusinessDelegateException, SystemException{
		return performAction("customermanagement.defaults.ux.customerconsole.updateReminderListSession",customerConsoleModel);
	}
	@RequestMapping("/getAccountStatement")
	public @ResponseBody ResponseVO getAccountStatement(@RequestBody CustomerConsoleModel customerConsoleModel)
		throws BusinessDelegateException, SystemException{
		return performAction("customermanagement.defaults.ux.customerconsole.printAccountStatement",customerConsoleModel);
	}

	@RequestMapping("/emailaccountstatement")
	public @ResponseBody ResponseVO emailAccountStatement(@RequestBody CustomerConsoleModel model)
			throws BusinessDelegateException, SystemException {
		return performAction("customermanagement.defaults.ux.customerconsole.emailaccountstatement", model);
	}
	
}
