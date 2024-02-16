/*
 * SaveRateAuditCommand.java Created on Jul 17, 2008
 *
 * Copyright 2008 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.defaults.rateauditdetails;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.RateAuditVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.mra.MailTrackingMRADelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.RateAuditDetailsSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.RateAuditDetailsForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
/**
 * 
 * @author A-2391
 *
 */
public class SaveRateAuditCommand  extends BaseCommand {
	
	private Log log = LogFactory.getLogger("RATE AUDITDETAILS SaveRateAuditCommand");
	private static final String SAVE_SUCCESS = "save_success";
	private static final String SAVE_FAILURE = "save_failure";
	private static final String MODULE = "mailtracking.mra.defaults";
	private static final String SCREENID = "mailtracking.mra.defaults.rateauditdetails";
	private static final String UPDATE = "U";
	
	
	/**
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	 public void execute(InvocationContext invocationContext)
     throws CommandInvocationException {
		 Collection<ErrorVO> errors = null;
		 log.entering("SaveRateAuditCommand", "execute");
		 RateAuditDetailsForm rateAuditDetailsForm=(RateAuditDetailsForm)invocationContext.screenModel;
		 RateAuditDetailsSession rateAuditDetailsSession=getScreenSession(MODULE,SCREENID);	
		 MailTrackingMRADelegate delegate=new MailTrackingMRADelegate(); 
		 ApplicationSessionImpl applicationSession = getApplicationSession();
			LogonAttributes logonAttributes = applicationSession.getLogonVO();
		 RateAuditVO rateAuditVOInSession = new RateAuditVO();	
			
			String flg = rateAuditDetailsSession.getParChangeFlag();
			log.log(Log.FINE, "ParChangeFlag()----->>>",
					rateAuditDetailsSession.getParChangeFlag());
			if(rateAuditDetailsSession.getParChangeFlag()!=null&&rateAuditDetailsSession.getParChangeFlag().contains("P")||rateAuditDetailsSession.getParChangeFlag().contains("W")||rateAuditDetailsSession.getParChangeFlag().contains("C")){	
				errors = new ArrayList<ErrorVO>();
				ErrorVO errorVO = new ErrorVO("mailtracking.mra.defaults.rateauditdetails.msg.err.mustcomputetot");				
				errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
				errors.add(errorVO);
				invocationContext.addAllError(errors);
				invocationContext.target = SAVE_FAILURE;
				return;
			}
			
		
			 rateAuditVOInSession = rateAuditDetailsSession.getRateAuditVO();
			 try {
				 rateAuditVOInSession.setOperationFlag(UPDATE);
				 rateAuditVOInSession.setRaiseCCAFlag("N");
				delegate.saveRateAuditDetails(rateAuditVOInSession);
			} catch (BusinessDelegateException e) {
				// TODO Auto-generated catch block
				e.getMessage();
			}	
			rateAuditDetailsSession.setStatusinfo("SAVE");
			
			invocationContext.target = SAVE_SUCCESS;
		 log.exiting("SaveRateAuditCommand", "execute");		 
		 
		 
	 }


}
