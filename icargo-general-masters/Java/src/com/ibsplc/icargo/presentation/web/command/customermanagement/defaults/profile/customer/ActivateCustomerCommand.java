/*
 * Created on : 15-May-2018 
 * Name       : ActivateCustomerCommand.java
 * Created by : A-8154
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * 
 */
package com.ibsplc.icargo.presentation.web.command.customermanagement.defaults.profile.customer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import com.ibsplc.icargo.business.shared.customer.vo.CustomerVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.customermanagement.defaults.CustomerMgmntDefaultsDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.customermanagement.defaults.profile.MaintainCustomerRegistrationSession;
import com.ibsplc.icargo.presentation.web.struts.form.customermanagement.defaults.profile.MaintainRegCustomerForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

public class ActivateCustomerCommand extends BaseCommand{
	
	private static final String MODULENAME = "customermanagement.defaults";
	private static final String SCREENID = "customermanagement.defaults.maintainregcustomer";
	private static final String ACTIVATE = "A";
	private static final String DEACTIVATE = "I";
	private static final String DRAFT = "D";
	private static final String ACTIVATION_SUCCESS="activate_success";
	private static final String DUPLICATE_STOCK_HOLDER = "customermanagement.defaults.duplicate.stockholder";
	private static final String STOCKAUTOMATIONREQUIRED = "stockcontrol.defaults.stockautomationrequired";
	private static final String STOCK_HOLDER_SUCESSFULL ="customermanagement.defaults.stockholder.info.create";
	private static final String STOCK_HOLDER_ACTIVATIONSUCESSFULL ="customermanagement.defaults.stockholder.info.active";
	/**
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		Log log = LogFactory.getLogger("ActivatingCustomer");
		log.entering("customer", "Entering ActivateCustomerCommand ");
		MaintainRegCustomerForm form = (MaintainRegCustomerForm) invocationContext.screenModel;
		MaintainCustomerRegistrationSession session = getScreenSession(MODULENAME,SCREENID);
		CustomerMgmntDefaultsDelegate delegate = new CustomerMgmntDefaultsDelegate();
		CustomerVO customerVO = session.getCustomerVO();
		Boolean isStockHolderAvailable=true; 
		Boolean stockHolderValidation = false; 
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
 		Collection<String> systemCodes = new ArrayList<String>(); 
 		systemCodes.add(STOCKAUTOMATIONREQUIRED);
 		Map<String, String> systemParametersMap = null;
		try {
			systemParametersMap = new SharedDefaultsDelegate().findSystemParameterByCodes(systemCodes);
		} catch (BusinessDelegateException e) {
			log.log(Log.ALL,"*******BusinessDelegateException *********"); 
		}
		if(systemParametersMap!=null && systemParametersMap.size() > 0){
			if(CustomerVO.FLAG_YES.equals(systemParametersMap.get(STOCKAUTOMATIONREQUIRED)) && 
					CustomerVO.FLAG_YES.equals(form.getStockAutomationUserConfirmed())){
				customerVO.setStockAutomationRequired(true);
			} else{
				customerVO.setStockAutomationRequired(false);
			}
		}
		if(customerVO.isStockAutomationRequired()){
			try {
				stockHolderValidation = delegate.validateStockHolderFromCustomerActivation(customerVO);
				if(!"Y".equalsIgnoreCase(form.getDuplicateStockHolderOverride())
						&& stockHolderValidation){
					form.setDuplicateStockHolder("Y");
					ErrorVO errorVO = new ErrorVO(DUPLICATE_STOCK_HOLDER);
					errorVO.setErrorDisplayType(ErrorDisplayType.WARNING);
					errors.add(errorVO);
					invocationContext.addAllError(errors);
					invocationContext.target ="duplicate_stockholder"; 
					return;
				}
				//form.setDuplicateStockHolderOverride("N");
			} catch (BusinessDelegateException e) {
				log.log(Log.ALL,"NO STOCKHOLDER PRESENT. NEED TO CREATE NEW");
				isStockHolderAvailable=false;
			}
			
			if(isStockHolderAvailable){
				customerVO.setStockAutomationRequired(false);
			} else{
				customerVO.setStockAutomationRequired(true);
			}
		}
		if (DEACTIVATE.equals(form.getStatus())) {
			customerVO.setStatus(ACTIVATE);
		}
		if(DRAFT.equals(form.getStatus())){
			customerVO.setStatus(ACTIVATE); 
		}
		customerVO.setIsHistoryPopulated("N");
		Collection<CustomerVO> customerVOs = new ArrayList<CustomerVO>();
		customerVOs.add(customerVO);
		try {
			delegate.changeStatusOfCustomers(customerVOs); 
		} catch (BusinessDelegateException e) {
			log.log(Log.ALL,"********BusinessDelegateException *********");
		}
		log.exiting("Customer", "Exiting ActivateCustomerCommand");
		
		 if ((errors == null) || (errors.size() == 0) && !stockHolderValidation && ("Y") .equals(systemParametersMap.get(STOCKAUTOMATIONREQUIRED))
				 && ("Y").equals(form.getStockAutomationUserConfirmed())) {
		      ErrorVO errorVO = new ErrorVO(STOCK_HOLDER_SUCESSFULL);
		      errorVO.setErrorDisplayType(ErrorDisplayType.INFO);
		      errors.add(errorVO);
		      invocationContext.addAllError(errors);
		      invocationContext.target =ACTIVATION_SUCCESS;
		      return;
		 }
		 else{
			  ErrorVO errorVO = new ErrorVO(STOCK_HOLDER_ACTIVATIONSUCESSFULL);
		      errorVO.setErrorDisplayType(ErrorDisplayType.INFO);
		      errors.add(errorVO);
		      invocationContext.addAllError(errors);
		      invocationContext.target =ACTIVATION_SUCCESS;
		      return;
		 }
	}
}
