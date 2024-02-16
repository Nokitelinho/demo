/*
 * ScreenLoadCommand.java Created on Dec 1, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.airlinebilling.defaults.memolov;



import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.MemoLovVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.mra.MailTrackingMRADelegate;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.airlinebilling.defaults.MemoLOVForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-2524
 *
 */
public class ScreenLoadCommand extends BaseCommand {

	private Log log = LogFactory.getLogger("MRA_AIRLINEBILLING");

	private static final String SCREENLOAD_SUCCESS = "screenload_success";
		
	private static final String CLASS_NAME = "ScreenLoadCommand";
	
	/**
	 * execute method
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		log.log(Log.FINE, "ScreenLoadCommand");
		log.entering(CLASS_NAME, "execute");
		MemoLOVForm memoLovForm =
			(MemoLOVForm)invocationContext.screenModel;
		
		
		String memoCode = null;
		String companyCode = 
			getApplicationSession().getLogonVO().getCompanyCode();
		String displayPage = memoLovForm.getDisplayPage();	
		if(memoLovForm.getCode()!=null && memoLovForm.getCode().trim().length()>0) {
			memoCode = memoLovForm.getCode().toUpperCase();
		} 
		
		Page<MemoLovVO> memoLovVos = null;
		
		try {			 
			memoLovVos =
				new MailTrackingMRADelegate()
				.displayMemoLOV(companyCode,memoCode,Integer.parseInt(displayPage));
			
		} catch(BusinessDelegateException businessDelegateException) {			
			businessDelegateException.getMessage();
			handleDelegateException(businessDelegateException);
		}
		memoLovForm.setMemoLovVos(memoLovVos);
		log.exiting(CLASS_NAME,"execute");
    	invocationContext.target=SCREENLOAD_SUCCESS;

	}

}
