/**
 *	Java file	: 	com.ibsplc.icargo.presentation.web.command.customermanagement.defaults.profile.customer.ValidateCustomerGroupCommand.java
 *
 *	Created by	:	A-7604
 *	Created on	:	28-Feb-2018
 *
 *  Copyright 2018 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 *
 * 	This software is the proprietary information of Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
 * 	Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.customermanagement.defaults.profile.customer;

import java.util.ArrayList;

import com.ibsplc.icargo.business.shared.customer.vo.CustomerGroupVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.customer.CustomerDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.customermanagement.defaults.profile.MaintainCustomerRegistrationSession;
import com.ibsplc.icargo.presentation.web.struts.form.customermanagement.defaults.profile.MaintainRegCustomerForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * 
 *	Java file	: 	com.ibsplc.icargo.presentation.web.command.customermanagement.defaults.profile.customer.ValidateCustomerGroupCommand.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-7604	:	06-Mar-2018	:	Draft
 */
public class ValidateCustomerGroupCommand extends BaseCommand {
	private Log log = LogFactory.getLogger("ValidateCustomerGroupCommand");
	private static final String SAVE_FAILURE = "save_failure";
	private static final String MODULENAME = "customermanagement.defaults";
	private static final String SCREENID = "customermanagement.defaults.maintainregcustomer";
	public static final String CLASS_NAME = "ValidateCustomerGroupCommand";

	/**
	 * 
	 *	Overriding Method	:	@see com.ibsplc.icargo.framework.web.command.Command#execute(com.ibsplc.icargo.framework.web.command.InvocationContext)
	 *	Added by 			: A-7604 on 06-Mar-2018
	 * 	Used for 	:
	 *	Parameters	:	@param invocationContext
	 *	Parameters	:	@throws CommandInvocationException
	 */
	@Override
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {

		log.entering(MODULENAME, CLASS_NAME);
		MaintainRegCustomerForm form = (MaintainRegCustomerForm) invocationContext.screenModel;
		MaintainCustomerRegistrationSession session = (MaintainCustomerRegistrationSession) getScreenSession(
				MODULENAME, SCREENID);
		ArrayList<ErrorVO> errors = new ArrayList<ErrorVO>();
		if ((form.getCustomerGroup() != null)
				&& (form.getCustomerGroup().trim().length() > 0)) {
			errors = validateCustomerGroup(form);
		}
		if ((errors != null) && (errors.size() > 0)) {
			form.setSourcePage("SAVE");
			session.setSourcePage("SAVE");
			session.setValidationErrors(errors);
			invocationContext.addAllError(errors);
			invocationContext.target = SAVE_FAILURE;
			return;
		}
		log.exiting(MODULENAME, CLASS_NAME);

	}

	/**
	 * 
	 * 	Method		:	ValidateCustomerGroupCommand.validateCustomerGroup
	 *	Added by 	:	A-7604 on 06-Mar-2018
	 * 	Used for 	:
	 *	Parameters	:	@param form
	 *	Parameters	:	@return 
	 *	Return type	: 	ArrayList<ErrorVO>
	 */
	private ArrayList<ErrorVO> validateCustomerGroup(
			MaintainRegCustomerForm form) {

		CustomerDelegate delegate = new CustomerDelegate();
		CustomerGroupVO groupVO = null;
		ArrayList<ErrorVO> errors = new ArrayList<ErrorVO>();

		if ((form.getCustomerGroup() != null)
				&& (form.getCustomerGroup().trim().length() > 0)) {
			try {
				groupVO = delegate.listCustomerGroupDetails(
						getApplicationSession().getLogonVO().getCompanyCode(),
						form.getCustomerGroup().trim().toUpperCase());
			} catch (BusinessDelegateException ex) {
				handleDelegateException(ex);
			}
			log.log(Log.FINE, "group vo from server------------>", groupVO);
			if ((groupVO == null) || (groupVO.getGroupCode() == null)) {
				ErrorVO error = new ErrorVO(
						"customermanagement.defaults.regcustomer.invalidcustomergroup");
				errors.add(error);
			}
		}

		return errors;
	}
}
