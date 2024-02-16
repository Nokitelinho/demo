/**
 * ScreenLoadCommand.java Created on January 16, 2012
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.national.offload;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.ibsplc.icargo.business.mail.operations.vo.DSNVO;
import com.ibsplc.icargo.business.mail.operations.vo.DespatchDetailsVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.national.OffloadDispatchSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.national.OffloadDispatchForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author a-4823
 *
 */
public class ScreenLoadCommand extends BaseCommand {

	private Log log = LogFactory.getLogger("MAILTRACKING DEFAULTS");

	private static final String SCREENLOAD_SUCCESS = "screenload_success";

	private static final String MODULE_NAME = "mail.operations";
	private static final String ASG_MAIL_BAG = "ASG_MAIL_BAG";
	private static final String DSN_ENQUIRY = "DSN_ENQUIRY";
	private static final String SCREEN_ID = "mailtracking.defaults.national.offload";
	private static final String OFFLOAD_REASONCODE = "mailtracking.defaults.offload.reasoncode";
	/**
	 * @param invocationContext
	 * @throws CommandInvocationException
	 * 
	 */
	public void execute(InvocationContext invocationContext)
	throws CommandInvocationException {
		log.entering("ScreenLoadOffloadCommand", "execute");

		OffloadDispatchSession offloadSession  = getScreenSession(MODULE_NAME, SCREEN_ID);
		OffloadDispatchForm offloadForm = (OffloadDispatchForm)invocationContext.screenModel;
		LogonAttributes logonAttributes = getApplicationSession().getLogonVO();	
		String fromScreen = offloadForm.getFromScreen();
		Map<String, Collection<OneTimeVO>> oneTimeValues = null;
		Collection<String> fieldTypes 		= new ArrayList<String>();
		fieldTypes.add(OFFLOAD_REASONCODE);
		try {
			oneTimeValues = new SharedDefaultsDelegate().findOneTimeValues(logonAttributes.getCompanyCode(), fieldTypes);
		} catch (BusinessDelegateException businessDelegateException) {
			log.log(Log.FINE, "*****in the exception");
			businessDelegateException.getMessageVO().getErrors();
			handleDelegateException(businessDelegateException);
		}
		if(ASG_MAIL_BAG.equals(fromScreen)){
			Collection<DSNVO> dsnvos = offloadSession.getSelectedDSNVO();
			for( DSNVO dsnvo: dsnvos){
				offloadForm.setPieces(String.valueOf(dsnvo.getBags()));
				offloadForm.setWeight(String.valueOf(dsnvo.getWeight()));
				//offloadForm.setOffloadRemarks(dsnvo.getRemarks());
			}
		}
		else if(DSN_ENQUIRY.equals(fromScreen)){
			Collection<DespatchDetailsVO> despatchDetailsVOs = offloadSession.getDespatchDetailsVOs();
			for(DespatchDetailsVO despatchDetailsVO : despatchDetailsVOs){
				offloadForm.setPieces(String.valueOf(despatchDetailsVO.getAcceptedBags()));
				offloadForm.setWeight(String.valueOf(despatchDetailsVO.getAcceptedWeight()));
				//offloadForm.setOffloadRemarks(despatchDetailsVO.getRemarks());
			}	
		}

		offloadForm
		.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);

		offloadSession.setOneTimeVOs((HashMap<String, Collection<OneTimeVO>>) oneTimeValues);
		invocationContext.target = SCREENLOAD_SUCCESS;	
		log.exiting("ScreenLoadOffloadCommand", "execute");

	}

}
