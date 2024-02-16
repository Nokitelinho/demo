/*
 * RateCardLOVListCommand.java Created on Feb 02, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.defaults.maintainratecard;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.RateCardLovVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.mra.MailTrackingMRADelegate;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.RateCardLOVForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-2408
 *
 */
public class RateCardLOVListCommand extends BaseCommand{
	private static final String SCREENLOAD_SUCCESS = "screenload_success";
	private Log log = LogFactory.getLogger(	"MRA RateCardLOV ListCommand");

	/**
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
     throws CommandInvocationException {
		 log.entering("ListAccountLOVCommand","execute");
		 ApplicationSessionImpl applicationSession = getApplicationSession();
			LogonAttributes logonAttributes = applicationSession.getLogonVO();
			RateCardLOVForm form=(RateCardLOVForm)invocationContext.screenModel;

			Page<RateCardLovVO> rateCardLovVOs=null;
			String companyCode=logonAttributes.getCompanyCode();
			String rateCardId="";
			if(form.getCode()!=null && form.getCode().trim().length()>0){
			rateCardId=form.getCode().toUpperCase();
			}
			MailTrackingMRADelegate delegate=new MailTrackingMRADelegate();


			int pageno=Integer.parseInt(form.getDisplayPage());

			try{
				rateCardLovVOs=delegate.findRateCardLov(companyCode,rateCardId,pageno);
			}
			catch(BusinessDelegateException businessDelegateException){
				handleDelegateException(businessDelegateException);
			}
			//System.out.println("RATECARDs"+rateCardLovVOs);

			form.setRateCardLovPage(rateCardLovVOs);




		/* form.setFormCount("1");
		 form.setMultiselect("N");
		 form.setSelectedValues("1");
		 form.setIndex("0");*/
		 invocationContext.target=SCREENLOAD_SUCCESS;
			log.exiting("ListAccountLOVCommand","execute");

	 }
}