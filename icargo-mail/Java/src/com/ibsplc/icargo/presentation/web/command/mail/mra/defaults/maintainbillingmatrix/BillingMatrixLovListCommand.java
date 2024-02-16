/*
 * BillingMatrixLovListCommand.java Created on Mar 06, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.presentation.web.command.mail.mra.defaults.maintainbillingmatrix;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.BillingMatrixLovVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.mra.MailTrackingMRADelegate;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.BillingMatrixLovForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-2408
 *
 */
public class BillingMatrixLovListCommand extends BaseCommand{
	private static final String SCREENLOAD_SUCCESS = "screenload_success";
	private Log log = LogFactory.getLogger(	"MRA RateCardLOV ListCommand");

	/**
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
     throws CommandInvocationException {
		 log.entering("LISTBillingMatrixLov","execute");
		 ApplicationSessionImpl applicationSession = getApplicationSession();
		 LogonAttributes logonAttributes = applicationSession.getLogonVO();
			
			
		String companyCode=logonAttributes.getCompanyCode();
		Page<BillingMatrixLovVO> billingMtxLovVos=null;	
		BillingMatrixLovForm form=(BillingMatrixLovForm)invocationContext.screenModel;
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		String billingMatrixCode="";
		if(form.getCode()!=null && form.getCode().trim().length()>0){
			billingMatrixCode=form.getCode().toUpperCase();
		}
		MailTrackingMRADelegate delegate=new MailTrackingMRADelegate();
		
		int pageno=Integer.parseInt(form.getDisplayPage());
		
		try{
			billingMtxLovVos=delegate.findBillingMatrixLov(companyCode,billingMatrixCode,pageno);
		}
		catch(BusinessDelegateException businessDelegateException){
			errors=handleDelegateException(businessDelegateException);
		}
		
		form.setBillingMatrixLovPage(billingMtxLovVos);
		  if( errors != null && errors.size() > 0 ){
	        	invocationContext.addAllError(errors);
	        }
		 invocationContext.target=SCREENLOAD_SUCCESS;
			log.exiting("ListBillingMatrixLov","execute");
			
	}
}

