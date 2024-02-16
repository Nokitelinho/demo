
/**
 *	Java file	: 	com.ibsplc.icargo.presentation.web.command.customermanagement.defaults.ux.customerconsole.CCADetailsCommand.java
 *
 *	Created by	:	A-8227
 *	Created on	:	Sep 05, 2018
 *
 *  Copyright 2018 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 *
 * 	This software is the proprietary information of Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
 * 	Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.customermanagement.defaults.ux.customerconsole;

import java.util.ArrayList;
import java.util.List;

import com.ibsplc.icargo.business.cra.agentbilling.defaults.vo.CCADetailsVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.json.vo.ResponseVO;
import com.ibsplc.icargo.framework.web.spring.command.AbstractCommand;
import com.ibsplc.icargo.framework.web.spring.controller.ActionContext;
import com.ibsplc.icargo.presentation.delegate.customermanagement.defaults.CustomerMgmntDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.model.customermanagement.defaults.CustomerConsoleModel;
import com.ibsplc.icargo.presentation.web.model.customermanagement.defaults.common.CCADetails;
import com.ibsplc.icargo.presentation.web.model.customermanagement.defaults.common.CustomerConsoleModelConverter;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * Java file :
 * com.ibsplc.icargo.presentation.web.command.customermanagement.defaults.ux.customerconsole.CCADetailsCommand.java
 * Version : Name : Date : Updation
 * --------------------------------------------------- 
 * 0.1 : A-8227 : Sep 05, 2018 : Draft
 */
public class CCADetailsCommand extends AbstractCommand {

	private Log log = LogFactory.getLogger("CUSTOMERMANAGEMENT DEFAULTS");
	private static final String CLASS_NAME = "CCADetailsCommand";
	private static final String ERR_CCA_DETAILS="customermanagement.defaults.customerconsole.error.ccadetails";

	/**
	 * Overriding Method : @see	com.ibsplc.icargo.framework.web.spring.command.AbstractCommand#execute(com.ibsplc.icargo.framework.web.spring.controller.ActionContext)
	 * Added by : A-8227 on Sep 05, 2018 
	 * Used for : Parameters : @param
	 * actionContext Parameters : @throws BusinessDelegateException 
	 * Parameters : @throws CommandInvocationException
	 */
	@Override
	public void execute(ActionContext actionContext) throws BusinessDelegateException, CommandInvocationException {
		log.entering(CLASS_NAME, "execute");
		CustomerConsoleModel customerConsoleModel = (CustomerConsoleModel) actionContext.getScreenModel();
		LogonAttributes logonAttribute = getLogonAttribute();
		CustomerMgmntDefaultsDelegate delegate = new CustomerMgmntDefaultsDelegate();
		try {

			List<CCADetailsVO> ccaDetailsVOs = delegate.getCCADetails(customerConsoleModel.getInvoiceNumber(),logonAttribute.getCompanyCode());
			if(null!=ccaDetailsVOs && ccaDetailsVOs.size()>0){
				List<CCADetails>ccaDetails =CustomerConsoleModelConverter.constructCCADetails(ccaDetailsVOs);
				customerConsoleModel.setCcaDetails(ccaDetails);
			}			

		} catch (BusinessDelegateException e) {
			actionContext.addError(new ErrorVO(ERR_CCA_DETAILS));
		}

		ResponseVO responseVO = new ResponseVO();
		List<CustomerConsoleModel> results = new ArrayList<CustomerConsoleModel>();
		results.add(customerConsoleModel);
		responseVO.setResults(results);
		actionContext.setResponseVO(responseVO);
		log.exiting(CLASS_NAME, "execute");

	}

	

}
