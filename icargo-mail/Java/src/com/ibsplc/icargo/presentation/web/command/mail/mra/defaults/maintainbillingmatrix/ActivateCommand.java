/*
 * ActivateCommand.java Created on Jul 14, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.defaults.maintainbillingmatrix;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.BillingLineVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.mra.MailTrackingMRADelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.MaintainBillingMatrixSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.BillingMatrixForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * 
 * @author a-2518
 * 
 */
public class ActivateCommand extends BaseCommand {

	private Log log = LogFactory.getLogger("MRA DEFAULTS");

	private static final String CLASS_NAME = "ActivateCommand";

	private static final String MODULE_NAME = "mailtracking.mra.defaults";

	private static final String SCREENID = "mailtracking.mra.defaults.maintainbillingmatrix";

	private static final String SCREEN_SUCCESS = "screenload_success";

	private static final String DUPLICATE_EXISTS = "duplicate_exists";

	private static final String DUP_BILLINGLINE = "mailtracking.mra.defaults.duplicatebillingline";
	private static final String DUP_BILLINGPARAMETER = "mailtracking.mra.defaults.duplicateparameter";

	
	private static final String   UNSAVED_BLG_LINE= "mailtracking.mra.defaults.maintainbillingmatrix.savebillingline";
	/**
	 * *
	 * 
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		log.entering(CLASS_NAME, "execute");
		BillingMatrixForm form = (BillingMatrixForm) invocationContext.screenModel;
		MaintainBillingMatrixSession session = (MaintainBillingMatrixSession) getScreenSession(
				MODULE_NAME, SCREENID);
		String[] selectedIndexes = form.getSelectedIndexes().split("-");
		Collection<BillingLineVO> billingLineVos = new ArrayList<BillingLineVO>();
		for (String index : selectedIndexes) {
			billingLineVos.add(session.getBillingLineDetails().get(
					Integer.parseInt(index)));
		}
		/***
		 * @author a-3447 for Bug 28490 Starts 
		 */
		
		
		Collection<ErrorVO> errors=new ArrayList<ErrorVO>();
		for (BillingLineVO billingLineVo : billingLineVos) {
			//Added check for opflag U for solving ICRD-15904
			if("I".equals(billingLineVo.getOperationFlag()) || "U".equals(billingLineVo.getOperationFlag())){
				log.log(log.FINE, "inside errors");
				errors.add(new ErrorVO(UNSAVED_BLG_LINE));
				
			}
		}
		log.log(Log.INFO, "errors", errors.size());
		if(errors!=null&&errors.size()>0){
			log.log(log.FINE, "Errors");
			invocationContext.addAllError(errors);
			invocationContext.target = DUPLICATE_EXISTS;
			return;
			
		}
		/***
		 * @author a-3447 for Bug 28490 Ends 
		 */
		else{
		try {
			new MailTrackingMRADelegate().activateBillingLines(billingLineVos);
		} catch (BusinessDelegateException businessDelegateException) {
			/* change added by indu */
			for (String index : selectedIndexes) {
				session.getBillingLineDetails().get(Integer.parseInt(index))
						.setBillingLineStatus("N");
				log.log(Log.INFO, "linestatus not changed ", session.getBillingLineDetails().get(
						Integer.parseInt(index))
						.getBillingLineStatus());
				billingLineVos.add(session.getBillingLineDetails().get(
						Integer.parseInt(index)));
			}
			log.log(Log.INFO, "before handleDelegateException");

			errors = handleDelegateException(businessDelegateException);
			log.log(Log.INFO, "after handleDelegateException");
			if (errors != null && errors.size() > 0) {
				log.log(Log.INFO, "inside errors not equal to null");
				for (ErrorVO error : errors) {
					log.log(Log.INFO, "errorcode", error.getErrorCode());
					if ((
					"mailtracking.mra.defaults.duplicatebillingline").equals(error.getErrorCode())){

						invocationContext
								.addError(new ErrorVO(DUP_BILLINGLINE));
					}
					if ((
					"mailtracking.mra.defaults.duplicateparameter").equals(error.getErrorCode())){

						invocationContext
						.addError(new ErrorVO(DUP_BILLINGPARAMETER));
					}
					invocationContext.target = DUPLICATE_EXISTS;
					return;
				}

			} else {
				log.log(Log.INFO, "else part");
			}

			/* change added by indu ends */
		}

		invocationContext.target = SCREEN_SUCCESS;
		log.exiting(CLASS_NAME, "execute");
	}
	}
}
