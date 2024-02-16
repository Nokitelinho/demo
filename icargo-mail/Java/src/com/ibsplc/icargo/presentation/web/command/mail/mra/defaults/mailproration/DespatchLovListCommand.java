/*
 * DespatchLovListCommand.java Created on Apr 24, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.presentation.web.command.mail.mra.defaults.mailproration;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.DespatchLovVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.mra.MailTrackingMRADelegate;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.DespatchLovForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-2408
 *
 */
public class DespatchLovListCommand extends BaseCommand{
	private static final String SCREENLOAD_SUCCESS = "screenload_success";
	private Log log = LogFactory.getLogger(	"MRA DespatchLOV ListCommand");

	/**
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
     throws CommandInvocationException {
		 log.entering("DespatchLOV","execute");
		 ApplicationSessionImpl applicationSession = getApplicationSession();
		 LogonAttributes logonAttributes = applicationSession.getLogonVO();
			
			
		String companyCode=logonAttributes.getCompanyCode();
		Page<DespatchLovVO> despatchLovVOs=null;	
		DespatchLovForm form=(DespatchLovForm)invocationContext.screenModel;
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		String gpaCode="";
		String dsn="";
		String despatch="";
		if(form.getCode()!=null && form.getCode().trim().length()>0){
			dsn=form.getCode().toUpperCase();
		}
		if(form.getDespatchFilter()!=null && form.getDespatchFilter().trim().length()>0){
			despatch=form.getDespatchFilter().toUpperCase();
		}
		if(form.getGpaCodeFilter()!=null && form.getGpaCodeFilter().trim().length()>0){
			gpaCode=form.getGpaCodeFilter().toUpperCase();
		}
		MailTrackingMRADelegate delegate=new MailTrackingMRADelegate();
		
		int pageno=Integer.parseInt(form.getDisplayPage());
		
		try{
			despatchLovVOs=delegate.findDespatchLov(companyCode,dsn,despatch,gpaCode,pageno);
		}
		catch(BusinessDelegateException businessDelegateException){
			errors=handleDelegateException(businessDelegateException);
		}
		
		form.setDespatchLovPage(despatchLovVOs);
		  if( errors != null && errors.size() > 0 ){
	        	invocationContext.addAllError(errors);
	        }
		 invocationContext.target=SCREENLOAD_SUCCESS;
			log.exiting("DespatchLOV","execute");
			
	}
}

