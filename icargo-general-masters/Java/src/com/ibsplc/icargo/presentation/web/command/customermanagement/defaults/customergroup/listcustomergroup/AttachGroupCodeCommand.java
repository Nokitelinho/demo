/*
 * AttachGroupCodeCommand.java Created on Dec 19, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.customermanagement.defaults.customergroup.listcustomergroup;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.customermanagement.defaults.profile.vo.CustomerGroupLoyaltyProgrammeVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.customermanagement.defaults.CustomerMgmntDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.customermanagement.defaults.customergroup.ListCustomerGroupSession;
import com.ibsplc.icargo.presentation.web.struts.form.customermanagement.defaults.customergroup.ListCustomerGroupForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-2052
 * 
 */
public class AttachGroupCodeCommand extends BaseCommand {

	private static final String SCREENLOAD_SUCCESS = "screenload_success";

	private static final String SCREENID = "customermanagement.defaults.listcustomergroup";

	private static final String MODULE = "customermanagement.defaults";

	private Log log = LogFactory.getLogger("AttachGroupCodeCommand");

/**
 * @param invocationContext
 * @throws CommandInvocationException
 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		log.entering("AttachGroupCodeCommand---------------->>>>", "Entering");
		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		String compCode = logonAttributes.getCompanyCode();

		ListCustomerGroupForm listCustomerGroupForm = (ListCustomerGroupForm) invocationContext.screenModel;
		ListCustomerGroupSession listCustomerGroupSession = 
			getScreenSession(MODULE, SCREENID);
        
		listCustomerGroupForm
				.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
		listCustomerGroupSession.removeCustomerGroupLoyaltyProgrammeVOs();
		String groupCode = listCustomerGroupForm.getGroupCodeAttach();
		log.log(Log.FINE, "groupCode-------->>>>>", groupCode);
		CustomerMgmntDefaultsDelegate delegate = new CustomerMgmntDefaultsDelegate();
		Collection<CustomerGroupLoyaltyProgrammeVO> vos = new ArrayList<CustomerGroupLoyaltyProgrammeVO>();
		try {
			vos = delegate.listGroupLoyaltyPgm(compCode, groupCode);
		} catch (BusinessDelegateException e) {
			// To be reviewed Auto-generated catch block
			handleDelegateException( e);
		}
		listCustomerGroupSession.setCustomerGroupLoyaltyProgrammeVOs(vos);
		log
				.log(
						Log.FINE,
						"session.getCustomerGroupLoyaltyProgrammeVOs()------------>>>>>>",
						listCustomerGroupSession.getCustomerGroupLoyaltyProgrammeVOs());
		log.exiting("AttachGroupCodeCommand---------------->>>>", "Exiting");
		invocationContext.target = SCREENLOAD_SUCCESS;
	}
}
