/*
 * ScreenloadCommand.java Created on Aug 14, 2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.defaults.unaccounteddispatches;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.UnaccountedDispatchesFilterVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.UnaccountedDispatchesVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.UnaccountedDispatchesForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.UnaccountedDispatchesSession;


/**
 * @author A-2107
 *
 */
public class ScreenloadCommand extends BaseCommand {

	private Log log = LogFactory.getLogger("MRA_DEFAULTS");

	private static final String LIST_SUCCESS = "screenload_success";

	private static final String CLASS_NAME = "ScreenloadCommand";

	private static final String MODULE_NAME = "mailtracking.mra.defaults";

	private static final String SCREENID = "mailtracking.mra.defaults.unaccounteddispatches";

	/**
	 * Key value for onetime values of Billing line status
	 */
	private static final String KEY_REASON_CODE_ONETIME ="mailtracking.mra.reasoncode";
	
	private static final String KEY_MAIL_CATEGORY_ONETIME ="mailtracking.defaults.mailcategory";

	private static final String BLANK = "";
	/**
	 * execute method
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {

		log.entering(CLASS_NAME, "execute");

		UnaccountedDispatchesSession session =
			(UnaccountedDispatchesSession) getScreenSession(
				MODULE_NAME, SCREENID);

		UnaccountedDispatchesForm unaccountedDispatchesForm =
			(UnaccountedDispatchesForm)invocationContext.screenModel;

		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		String companyCode = logonAttributes.getCompanyCode();

		UnaccountedDispatchesFilterVO unaccountedDispatchesFilterVO = null;
		UnaccountedDispatchesVO unaccountedDispatchesVO = null;

		session.setUnaccountedDispatchesVO(unaccountedDispatchesVO);
		session.setUnaccountedDispatchesFilterVO(unaccountedDispatchesFilterVO);


		/**
		 * for taking one times
		 */
		Map oneTimeValues = null;
		Collection<String> oneTimeActiveStatusList 		= new ArrayList<String>();
		List<OneTimeVO> oneTimeVOs = new ArrayList<OneTimeVO>();
		
		oneTimeActiveStatusList.add(KEY_REASON_CODE_ONETIME);
		oneTimeActiveStatusList.add(KEY_MAIL_CATEGORY_ONETIME);
		
		try {
			oneTimeValues = new SharedDefaultsDelegate().findOneTimeValues(companyCode, oneTimeActiveStatusList);
		} catch (BusinessDelegateException businessDelegateException) {
			businessDelegateException.getMessageVO().getErrors();
			handleDelegateException(businessDelegateException);
		}
		List<OneTimeVO> reasoncodes = (List<OneTimeVO>) oneTimeValues.get(KEY_REASON_CODE_ONETIME);
		Collections.sort(reasoncodes, new DataComparator());
		session.setOneTimeVOs(reasoncodes);
		session.setOneTimeMap((HashMap<String, Collection<OneTimeVO>>) oneTimeValues);
		unaccountedDispatchesForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
		log.exiting(CLASS_NAME,"execute");
    	invocationContext.target = LIST_SUCCESS;

	}



	/**
	 * @author a-2107
	 * Comparator to sort ReasonCode
	 */
	public static class DataComparator implements Comparator<OneTimeVO> {

	    public int compare(OneTimeVO onetimevo1, OneTimeVO onetimevo2) {
	        return onetimevo1.getFieldValue().compareTo(onetimevo2.getFieldValue());
	    }
	}

}






