/**
 * DeleteCommand.java Created on Apr 11,2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services(P)Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.customermanagement.defaults.loyalty.maintainloyalty;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.customermanagement.defaults.loyalty.vo.LoyaltyParameterVO;
import com.ibsplc.icargo.business.customermanagement.defaults.loyalty.vo.LoyaltyProgrammeVO;

import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.customermanagement.defaults.CustomerMgmntDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.customermanagement.defaults.loyalty.MaintainLoyaltySession;
import com.ibsplc.icargo.presentation.web.struts.form.customermanagement.defaults.loyalty.MaintainLoyaltyForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * 
 * @author A-1862
 */
public class DeleteCommand extends BaseCommand {

	private Log log = LogFactory.getLogger("Maintain Loyalty");

	/**
	 * The Module Name
	 */
	private static final String MODULE = "customermanagement.defaults";

	private static final String SCREENID = "customermanagement.defaults.maintainloyalty";

	private static final String SAVE_SUCCESS = "save_success";

	private static final String SAVEFROM_LIST_SUCCESS = "savefrom_list_success";
	
	private static final String DELETE_FAILURE = "delete_failure";

	/**
	 * CUSTOMERS_ALREADY_ATTACHED
	 */
	public static final String CUSTOMERS_ALREADY_ATTACHED = "customermanagement.defaults.customersAlreadyAttached";

	/**
	 * execute method
	 * 
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */

	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		log.entering("DeleteCommand", "execute");

		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		MaintainLoyaltyForm maintainLoyaltyForm = (MaintainLoyaltyForm) invocationContext.screenModel;
		MaintainLoyaltySession maintainLoyaltySession = (MaintainLoyaltySession) getScreenSession(
				MODULE, SCREENID);
		maintainLoyaltyForm.setCloseWindow(false);
		LoyaltyProgrammeVO loyaltyProgrammeVO = new LoyaltyProgrammeVO();

		loyaltyProgrammeVO = maintainLoyaltySession.getLoyaltyProgrammeVO();

		log.log(Log.FINE, "\n\n\n\n loyaltyProgrammeVO BEFORE DELETE ---> ",
				loyaltyProgrammeVO);
		LocalDate fromDate = new LocalDate(logonAttributes.getStationCode (),Location.STN, false);

		loyaltyProgrammeVO.setOperationFlag(AbstractVO.OPERATION_FLAG_DELETE);

		ArrayList<LoyaltyParameterVO> loyaltyParameterVOsMain = new ArrayList<LoyaltyParameterVO>(
				loyaltyProgrammeVO.getLoyaltyParameterVOs());
		for (LoyaltyParameterVO loyaltyParameterVO : loyaltyParameterVOsMain) {
			loyaltyParameterVO.setCompanyCode(loyaltyProgrammeVO
					.getCompanyCode());

		}

		log.log(Log.FINE, "\n\n\n\n CURRENT DATE ---> ", fromDate);
		loyaltyProgrammeVO.setCurrentDate(fromDate);

		log
				.log(
						Log.FINE,
						"\n\n\n\n ***************loyaltyProgrammeVO FOR DELETE ********* ---> ",
						loyaltyProgrammeVO);
		try {
			new CustomerMgmntDefaultsDelegate()
					.createLoyaltyProgramme(loyaltyProgrammeVO);
		} catch (BusinessDelegateException e) {

			errors = handleDelegateException(e);
//printStackTrrace()();
		}

		Collection<ErrorVO> errorVOs = new ArrayList<ErrorVO>();
		log.log(Log.FINE, "\n\n\nerror collection", errors);
		if (errors != null && errors.size() > 0) {
			for (ErrorVO error : errors) {
				if ((error.getErrorCode().toString()
						.equals(CUSTOMERS_ALREADY_ATTACHED))) {
					log
							.log(Log.FINE,
									"<------Failure Response From Server CUSTOMERS_ALREADY_ATTACHED------->");

					errorVOs.add(error);

				}
			}
			if (errorVOs != null && errorVOs.size() > 0) {
				log.log(Log.FINE,"inside errors");
				invocationContext.addAllError(errorVOs);
				invocationContext.target = DELETE_FAILURE;
				return;
			}
		}
		log.log(Log.FINE, "maintainLoyaltySession.getPageURL()",
				maintainLoyaltySession.getPageURL());
		if (maintainLoyaltySession.getPageURL() != null) {

			invocationContext.target = SAVEFROM_LIST_SUCCESS;
			return;
		} else {
			invocationContext.target = SAVE_SUCCESS;
			return;
		}

	}
}
