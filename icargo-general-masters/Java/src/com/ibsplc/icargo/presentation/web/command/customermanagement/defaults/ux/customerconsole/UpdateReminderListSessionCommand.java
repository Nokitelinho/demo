
/**
 *	Java file	: 	com.ibsplc.icargo.presentation.web.command.customermanagement.defaults.ux.customerconsole.UpdateReminderListSessionCommand.java
 *
 *	Created by	:	A-8227
 *	Created on	:	Sep 19, 2018
 *
 *  Copyright 2018 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 *
 * 	This software is the proprietary information of Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
 * 	Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.customermanagement.defaults.ux.customerconsole;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.json.vo.ResponseVO;
import com.ibsplc.icargo.framework.web.spring.command.AbstractCommand;
import com.ibsplc.icargo.framework.web.spring.controller.ActionContext;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.model.customermanagement.defaults.CustomerConsoleModel;
import com.ibsplc.icargo.presentation.web.session.interfaces.cra.agentbilling.defaults.ReminderListSession;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * Java file :
 * com.ibsplc.icargo.presentation.web.command.customermanagement.defaults.ux.customerconsole.UpdateReminderListSessionCommand.java
 * Version : Name : Date : Updation
 * --------------------------------------------------- 
 * 0.1 : A-8227 : Sep 19, 2018 : Draft
 */
public class UpdateReminderListSessionCommand extends AbstractCommand {

	private Log log = LogFactory.getLogger("CUSTOMERMANAGEMENT DEFAULTS");
	private static final String CLASS_NAME = "UpdateReminderListSessionCommand";
	private static final String REMINDERLIST_MODULE_NAME = "cra.agentbilling";
	private static final String REMINDERLIST_SCREEN_ID = "cra.agentbilling.defaults.reminderlist";

	private static final String ONETIMECODE_STATUS = "cra.agentbilling.reminderstatus";
	private static final String ONETIMECODE_FILTERBASIS = "cra.agentbilling.reminderdatefilterbasis";
	private static final String ONETIMECODE_BILLINGTYPES = "cra.agentbilling.reminderbillingtypes";
	private static final String ONETIMECODE_PAYMENTSTATUS = "cra.agentbilling.paymentstatus";
	private static final String ONETIMECODE_REBILLROUND="cra.agentbilling.rebillround";
	private static final String NON_CASS_REBILL_ROUND="cra.agentbilling.noofroundsfornoncassrebilling";
	private static final String SYSTEM_PARAM_APPROVAL_WORKFLOW = "cra.defaults.approvalworkflowforreminders";
	private static final String PARENT_SCREEN= "customermanagement.defaults.customerconsole";

	/**
	 * Overriding Method : @see	  com.ibsplc.icargo.framework.web.spring.command.AbstractCommand#execute(com.ibsplc.icargo.framework.web.spring.controller.ActionContext)
	 * Added by : A-8227 on Sep 19, 2018 
	 * Used for : Parameters : @param
	 * actionContext Parameters : @throws BusinessDelegateException 
	 * Parameters
	 * : @throws CommandInvocationException
	 */
	@Override
	public void execute(ActionContext actionContext) throws BusinessDelegateException, CommandInvocationException {
		log.entering(CLASS_NAME, "execute");
		CustomerConsoleModel customerConsoleModel = (CustomerConsoleModel)actionContext.getScreenModel();
		ReminderListSession reminderListSession = getScreenSession(REMINDERLIST_MODULE_NAME,
				REMINDERLIST_SCREEN_ID);
		reminderListSession.removeReminderDetailsVOs();
		LogonAttributes logonAttributes = getLogonAttribute();
		getOneTimeValues(reminderListSession, logonAttributes);
		
		Collection<String> sysParam = new ArrayList<String>();
		Map<String, String> paramResult=  new HashMap<String,String>();
		sysParam.add(SYSTEM_PARAM_APPROVAL_WORKFLOW);
		try{
			paramResult = new SharedDefaultsDelegate().findSystemParameterByCodes(sysParam);
		}catch(BusinessDelegateException businessDelegateException){
			handleDelegateException(businessDelegateException);
		}
		String parval = paramResult.get(SYSTEM_PARAM_APPROVAL_WORKFLOW);
		customerConsoleModel.setReminderListApprovalParameter(parval);
		reminderListSession.setParentScreen(PARENT_SCREEN);
		
		ResponseVO responseVO = new ResponseVO();
		List<CustomerConsoleModel> results = new ArrayList<CustomerConsoleModel>();	
		results.add(customerConsoleModel);
		responseVO.setResults(results);
		actionContext.setResponseVO(responseVO);
		log.exiting(CLASS_NAME, "execute");

	}
	/**
	 * Method to get OneTimeValues into session
	 * 
	 * @param reminderListSession
	 * @param logonAttributes
	 */
	void getOneTimeValues(ReminderListSession reminderListSession,
			LogonAttributes logonAttributes) {
		
		SharedDefaultsDelegate sharedDefaultsDelegate = new SharedDefaultsDelegate();
		Map<String, Collection<OneTimeVO>> hashMap = null;
		Collection<String> oneTimeList = new ArrayList<String>();
		oneTimeList.add(ONETIMECODE_STATUS);
		oneTimeList.add(ONETIMECODE_FILTERBASIS);
		oneTimeList.add(ONETIMECODE_BILLINGTYPES);
		oneTimeList.add(ONETIMECODE_PAYMENTSTATUS);
		

		Collection<String> rebillRound = new ArrayList<String>();
		Collection<OneTimeVO> oneTimeListCollection = new ArrayList<OneTimeVO>();
		Map<String, String> parameters = getSystemParameters();
		if(parameters != null){
		String rebill=parameters.get(NON_CASS_REBILL_ROUND);
		if(rebill !=null){
			for (int i = 1; i <= Integer.parseInt(rebill) ; i++) {
				rebillRound.add(Integer.toString(i));
			}
			}
		}
		if (rebillRound != null) {

			for (String round : rebillRound) {
				OneTimeVO oneTimeVO = new OneTimeVO();
				oneTimeVO.setCompanyCode(logonAttributes.getCompanyCode());
				oneTimeVO.setFieldType(ONETIMECODE_REBILLROUND);
				oneTimeVO.setFieldValue(round);
				oneTimeVO.setFieldDescription(round);
				oneTimeListCollection.add(oneTimeVO);
			}
		}

		try {
			hashMap = sharedDefaultsDelegate.findOneTimeValues(
					logonAttributes.getCompanyCode(), oneTimeList);
		} catch (BusinessDelegateException businessDelegateException) {
			handleDelegateException(businessDelegateException);

		}
		hashMap.put(ONETIMECODE_REBILLROUND, oneTimeListCollection);
		if (hashMap != null) {
			for (Collection<OneTimeVO> oneTimeVos : hashMap.values()) {
				log.log(Log.FINEST, oneTimeVos.toString());
				for (OneTimeVO oneTimeVo : oneTimeVos) {
					if (ONETIMECODE_STATUS.equals(oneTimeVo.getFieldType())) {
						reminderListSession
								.setOneTimeValues((HashMap<String, Collection<OneTimeVO>>) hashMap);
						break;
					} else if (ONETIMECODE_FILTERBASIS.equals(oneTimeVo
							.getFieldType())) {
						reminderListSession
								.setOneTimeValues((HashMap<String, Collection<OneTimeVO>>) hashMap);
						break;
					} else if (ONETIMECODE_BILLINGTYPES.equals(oneTimeVo
							.getFieldType())) {
						reminderListSession
								.setOneTimeValues((HashMap<String, Collection<OneTimeVO>>) hashMap);
						break;
					}
				 else if (ONETIMECODE_PAYMENTSTATUS.equals(oneTimeVo
						.getFieldType())) {
					reminderListSession
							.setOneTimeValues((HashMap<String, Collection<OneTimeVO>>) hashMap);
					break;
				}
				 else if (ONETIMECODE_REBILLROUND.equals(oneTimeVo
							.getFieldType())) {
						reminderListSession
								.setOneTimeValues((HashMap<String, Collection<OneTimeVO>>) hashMap);
						break;
					}
				}
			}
		}
		log.exiting(CLASS_NAME, "getOneTimeValues");
	}
	
	private Map<String, String> getSystemParameters() {
		log.entering("UpdateReminderListSessionCommand", "getSystemParameters");

		SharedDefaultsDelegate sharedDefaultsDelegate = new SharedDefaultsDelegate();
		Map<String, String> map = null;
		Collection<String> parameterCodes = new ArrayList<String>();

		parameterCodes.add(NON_CASS_REBILL_ROUND);

		try {
			log
					.log(Log.FINEST,
							"***********************************inside try");
			map = sharedDefaultsDelegate
					.findSystemParameterByCodes(parameterCodes);
			log.log(Log.FINEST, "hash map*****************************", map);

		} catch (BusinessDelegateException businessDelegateException) {
			handleDelegateException(businessDelegateException);
			log.log(Log.SEVERE, "status fetch exception");
		}
		log.exiting("UpdateReminderListSessionCommand", "getSystemParameters");
		return map;
	}

}


	


