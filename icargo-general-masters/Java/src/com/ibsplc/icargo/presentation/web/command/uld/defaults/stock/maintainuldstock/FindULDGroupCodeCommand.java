/*
 * FindULDGroupCodeCommand.java Created on Apr 1, 2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.stock.maintainuldstock;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.ibsplc.icargo.business.shared.uld.vo.ULDTypeValidationVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.uld.ULDDelegate;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.stock.MaintainULDStockForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-2412
 * 
 */
public class FindULDGroupCodeCommand extends BaseCommand {
	private Log log = LogFactory.getLogger("FindULDGroupCodeCommand");

	private static final String FIND_SUCCESS = "find_success";

	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		log.log(Log.INFO, "entered FindULDGroupCodeCommand");
		MaintainULDStockForm actionForm = (MaintainULDStockForm) invocationContext.screenModel;
		Map<String, ULDTypeValidationVO> uldGroupCodes = new HashMap<String, ULDTypeValidationVO>();
		String uldGroupCode = null;

		Collection<String> uldTypes = new ArrayList<String>();
		String uldType = actionForm.getUldTypeCode().toUpperCase();
		uldTypes.add(actionForm.getUldTypeCode().toUpperCase());
		Collection<ErrorVO> errorsUldType = null;
		try {
			uldGroupCodes = new ULDDelegate().validateULDTypeCodes(
					logonAttributes.getCompanyCode(), uldTypes);
			log.log(Log.INFO, "uldGroupCodes returned- --->>", uldGroupCodes);

		} catch (BusinessDelegateException businessDelegateException) {
			errorsUldType = handleDelegateException(businessDelegateException);
		}

		if (uldGroupCodes != null && uldGroupCodes.size() > 0) {
			ULDTypeValidationVO vo = uldGroupCodes.get(uldType);
			uldGroupCode = vo.getUldGroupCode();
			actionForm.setUldGroupCode(uldGroupCode);
		} else {
			actionForm.setUldGroupCode("");
		}

		log.log(Log.INFO, "actionForm.getUldGroupCode()----->", actionForm.getUldGroupCode());
		invocationContext.target = FIND_SUCCESS;
	}

}
