/*
 * ViewSurchargeDetailsCommand.java Created on Jun 11, 2015
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.defaults.viewbillingline;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.BillingLineVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.BillingLineDetailVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.ViewBillingLineSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.ViewBillingLineForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-6385
 *
 */
public class ViewSurchargeDetailsCommand extends BaseCommand {

	private  Log log = LogFactory.getLogger("MRA DEFAULTS");

	private static final String CLASS_NAME = "ViewSurchargeDetailsCommand";

	private static final String MODULE = "mailtracking.mra.defaults";

	private static final String SCREENID = "mailtracking.mra.defaults.viewbillingline";

	private static final String SCREEN_SUCCESS = "open_surcharge";
	private static final String KEY_BLG_SURCHARGECHARGEHEAD = "mailtracking.mra.surchargeChargeHead";
	
	/**
	 * 	 *
	 * 
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {

    	log.entering(CLASS_NAME,"execute ");    	
			
    	ViewBillingLineForm form = (ViewBillingLineForm) invocationContext.screenModel;
    	ViewBillingLineSession session = (ViewBillingLineSession) getScreenSession(
				MODULE, SCREENID);
		ApplicationSessionImpl applicationSessionImpl = getApplicationSession();
		LogonAttributes logonAttributes = applicationSessionImpl.getLogonVO();
		int selectedIndex = 0;
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		Map<String, Collection<OneTimeVO>> oneTimeHashMap = null;
		ArrayList<BillingLineVO> list = null;
		Page<BillingLineVO> billingLineList = null;
		Collection<String> oneTimeActiveStatusList = new ArrayList<String>();
		String companyCode = logonAttributes.getCompanyCode().toUpperCase();
		oneTimeActiveStatusList.add(KEY_BLG_SURCHARGECHARGEHEAD);
		try {
			/** getting collections of OneTimeVOs */
			oneTimeHashMap = new SharedDefaultsDelegate().findOneTimeValues(
					companyCode, oneTimeActiveStatusList);
		} catch (BusinessDelegateException e) {
			e.getMessage();
			errors = handleDelegateException(e);
		}

		if (form.getSelectedIndex() != null) {
			selectedIndex = Integer.parseInt(form.getSelectedIndex());
		BillingLineVO billingLineVO = null;
		billingLineList= session.getBillingLineDetails();
		billingLineVO = billingLineList.get(selectedIndex);
			if (billingLineVO != null) {
				getChargeTypeDescription(billingLineVO, oneTimeHashMap);
			}
		session.setSelectedBillingLine(billingLineVO);
		}		
    	if(errors.size() > 0) {
			invocationContext.addAllError(errors);
		}    	
    	invocationContext.target = SCREEN_SUCCESS;		
	}
	/**
	 * 
	 * @author A-5255
	 * @param billingLineVO
	 * @param oneTimeHashMap
	 */
	private void getChargeTypeDescription(BillingLineVO billingLineVO,
			Map<String, Collection<OneTimeVO>> oneTimeHashMap) {
		Collection<OneTimeVO> OneTimes = (Collection<OneTimeVO>) oneTimeHashMap
				.get(KEY_BLG_SURCHARGECHARGEHEAD);
		if (billingLineVO.getBillingLineDetails() != null
				&& billingLineVO.getBillingLineDetails().size() > 0) {
			for (BillingLineDetailVO billingLineDetailVO : billingLineVO
					.getBillingLineDetails()) {

				if (OneTimes != null) {
					for (OneTimeVO oneTimeVo : OneTimes) {
						if (billingLineDetailVO.getChargeType().equals(
								oneTimeVo.getFieldValue())) {
							billingLineDetailVO.setChargeTypeDesc(oneTimeVo
									.getFieldDescription());
						}
					}
				}
			}
		}
    }

}
