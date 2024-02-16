/*
 * ScreenLoadCommand.java Created on Aug 30, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.presentation.web.command.stockcontrol.defaults.approverlov;

import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.stockcontrol.defaults.MaintainStockHolderSession;
import com.ibsplc.icargo.presentation.web.struts.form.stockcontrol.defaults.ApproverLovForm;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockHolderPriorityVO;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.delegate.stockcontrol.defaults.StockControlDefaultsDelegate;

/**
 * @author kirupakaran
 * 
 * This is the form class that represents the Screen Load Command for Approver
 * Lov
 */

public class ScreenLoadCommand extends BaseCommand {
	//private static String COMPANY_CODE;

	private Log log = LogFactory.getLogger("SYSTEM ENVIRONMENT");

	/**
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */

	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		log.entering("ScreenLoadCommand", "execute");
		ApplicationSessionImpl applicationSessionImpl = getApplicationSession();
		LogonAttributes logonAttributes = applicationSessionImpl.getLogonVO();
		//COMPANY_CODE=logonAttributes.getCompanyCode();
		ApproverLovForm approverLovForm = (ApproverLovForm) invocationContext.screenModel;
		/**
		 * Added by A-4772 for ICRD-9882.Changed the 
		 * Screen id value as per standard for UISK009
		 */
		MaintainStockHolderSession session = getScreenSession(
				"stockcontrol.defaults", "stockcontrol.defaults.maintainstockholder");
		session.setId(approverLovForm.getId());
		session.setPageStockLovVO(null);
		session.setDocType(approverLovForm.getDocType());
		session.setDocSubType(approverLovForm.getDocSubType());
		approverLovForm.setCode(session.getApproverCode());
		try {
			// One times
			Collection<String> fieldTypes = new ArrayList<String>();
			fieldTypes.add("stockcontrol.defaults.stockholdertypes");
			Map<String, Collection<OneTimeVO>> oneTimes = new SharedDefaultsDelegate()
					.findOneTimeValues(logonAttributes.getCompanyCode(), fieldTypes);
			Collection<OneTimeVO> stockHolder = oneTimes
					.get("stockcontrol.defaults.stockholdertypes");
			session.setOneTimeStock(stockHolder);

			// Setting stock holders priority
			Collection<StockHolderPriorityVO> stockHolderpriorityVos = new StockControlDefaultsDelegate()
					.findStockHolderTypes(logonAttributes.getCompanyCode());
			populatePriorityStockHolders(stockHolderpriorityVos, stockHolder,
					session);

		} catch (BusinessDelegateException businessDelegateException) {
//printStackTrrace()();
		}

		invocationContext.target = "screenload_success";

	}

	/**
	 * 
	 * @param stockHolderpriorityVos
	 * @param stockHolder
	 * @param session
	 */
	private void populatePriorityStockHolders(
			Collection<StockHolderPriorityVO> stockHolderpriorityVos,
			Collection<OneTimeVO> stockHolder,
			MaintainStockHolderSession session) {
		// log.entering("ScreenLoadCommand","populatePriorityStockHolders");
		if (stockHolderpriorityVos != null) {
			for (StockHolderPriorityVO priorityVO : stockHolderpriorityVos) {
				for (OneTimeVO onetime : stockHolder) {
					if (onetime.getFieldValue().equals(
							priorityVO.getStockHolderType())) {
						priorityVO.setStockHolderDescription(onetime
								.getFieldDescription());
						priorityVO.setStockHolderCode("");
					}
				}
			}
			session.setPrioritizedStockHolders(stockHolderpriorityVos);
			// log.exiting("ScreenLoadCommand","populatePriorityStockHolders");
		}
	}

}