/*
 * DSNSelectLOVListCommand.java Created on Apr 24, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.presentation.web.command.mail.mra.defaults.despatchlov;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.DSNSelectLovVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.mra.MailTrackingMRADelegate;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.DSNSelectLovForm;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-2391
 *
 */
public class DSNSelectLOVListCommand extends BaseCommand{
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
		Page<DSNSelectLovVO> despatchLovVOs=null;	
		DSNSelectLovForm form=(DSNSelectLovForm)invocationContext.screenModel;
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		String dsnNum="";
		String dsnDate="";
		
		if(form.getCode()!=null && form.getCode().trim().length()>0){
			dsnNum=form.getCode().toUpperCase();
		}
		if(form.getDsnFilterDate()!=null && form.getDsnFilterDate().trim().length()>0){
			dsnDate=form.getDsnFilterDate().toUpperCase();
		}
		
		MailTrackingMRADelegate delegate=new MailTrackingMRADelegate();
		
		int pageno=Integer.parseInt(form.getDisplayPage());
		
		log.log(Log.INFO, "despatchLovVOs ", despatchLovVOs);
		if(despatchLovVOs!=null){
		form.setDespatchLovPage(despatchLovVOs);
		}
		else{
			log.log(Log.INFO,"despatchLovVOs null");
		}
		  if( errors != null && errors.size() > 0 ){
	        	invocationContext.addAllError(errors);
	        }
		 invocationContext.target=SCREENLOAD_SUCCESS;
			log.exiting("DespatchLOV","execute");
			
	}
}

