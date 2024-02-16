/*
 * OpenBillingMatrixCommand.java Created on Feb 28, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.defaults.maintainbillingmatrix;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.BillingMatrixVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.MaintainBillingMatrixSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.BillingMatrixForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-2391
 * 
 */
public class OpenBillingMatrixCommand extends BaseCommand {
	private Log log = LogFactory.getLogger("MRA DEFAULTS COPYBLGLINE");

	private static final String CLASS_NAME = "ScreenLoadCommand";

	private static final String STATUS_ONETIME = "mra.gpabilling.ratestatus";

	private static final String MODULE_NAME = "mailtracking.mra.defaults";

	private static final String SCREENID = "mailtracking.mra.defaults.maintainbillingmatrix";

	private static final String OPEN_COPYBLGLINE = "open_copyblgline";

	private static final String CONST_ENABLE = "ENABLE";

	private static final String KEY_RATE_STATUS = "mra.gpabilling.ratestatus";

	private static final String KEY_BILLED_SECTOR = "mailtracking.mra.billingSector";

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibsplc.icargo.framework.web.command.Command#execute(com.ibsplc.icargo.framework.web.command.InvocationContext)
	 */
	/**
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		log.entering(CLASS_NAME, "execute");
		BillingMatrixForm form = (BillingMatrixForm) invocationContext.screenModel;
		log.log(Log.INFO, "form obtained");
		MaintainBillingMatrixSession session = (MaintainBillingMatrixSession) getScreenSession(
				MODULE_NAME, SCREENID);
		Map<String, String> billingMatrixStatusMap = new HashMap<String, String>();		
		Collection<String> oneTimeActiveStatusList = new ArrayList<String>();
		ApplicationSessionImpl applicationSessionImpl = getApplicationSession();
		LogonAttributes logonAttributes = applicationSessionImpl.getLogonVO();
		String companyCode = logonAttributes.getCompanyCode().toUpperCase();
		Map oneTimeHashMap = null;
		/** adding attributes to map for passing to SharedDefaultsDelegate */
		oneTimeActiveStatusList.add(KEY_RATE_STATUS);
		oneTimeActiveStatusList.add(KEY_BILLED_SECTOR);
		try {
			/** getting collections of OneTimeVOs */
			oneTimeHashMap = new SharedDefaultsDelegate().findOneTimeValues(
					companyCode, oneTimeActiveStatusList);
		} catch (BusinessDelegateException e) {
			e.getMessage();
			handleDelegateException(e);
		}
		/** setting OneTime hashmap to session */
		session
				.setOneTimeVOs((HashMap<String, Collection<OneTimeVO>>) oneTimeHashMap);
		for (Collection<OneTimeVO> oneTimeVos : session.getOneTimeVOs()
				.values()) {
			for (OneTimeVO oneTimeVo : oneTimeVos) {
				if (STATUS_ONETIME.equals(oneTimeVo.getFieldType())) {
					billingMatrixStatusMap.put(oneTimeVo.getFieldValue(),
							oneTimeVo.getFieldDescription());
				}
			}
		}
		if (session.getBillingMatrixVO() != null) {
			BillingMatrixVO billingMatrixVO = session.getBillingMatrixVO();
			billingMatrixVO.setBillingMatrixStatus(billingMatrixStatusMap
					.get(billingMatrixVO.getBillingMatrixStatus()));
			form.setBlgMatrixID(billingMatrixVO.getBillingMatrixId());
			form.setDescription(billingMatrixVO.getDescription());
			form.setStatus(billingMatrixStatusMap.get(billingMatrixVO
					.getBillingMatrixStatus()));
			form.setValidFrom(billingMatrixVO.getValidityStartDate()
					.toDisplayDateOnlyFormat());
			form.setValidTo(billingMatrixVO.getValidityEndDate()
					.toDisplayDateOnlyFormat());
			form.setIdValue(billingMatrixVO.getBillingMatrixId());
			form.setFormStatusFlag(CONST_ENABLE);
		}
		log.exiting(CLASS_NAME, "execute");
		invocationContext.target = OPEN_COPYBLGLINE;
	}
}
