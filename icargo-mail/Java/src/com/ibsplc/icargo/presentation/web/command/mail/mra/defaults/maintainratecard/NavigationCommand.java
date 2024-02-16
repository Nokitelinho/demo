/*
 * NavigationCommand.java Created on Jan 22, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.defaults.maintainratecard;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.RateLineFilterVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.RateLineVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.mra.MailTrackingMRADelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.MaintainUPURateCardSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.MaintainUPURateCardForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-2391
 *
 */
public class NavigationCommand extends BaseCommand{
private Log log = LogFactory.getLogger("Navigation Command");

	private static final String CLASS_NAME = "Navigation";

	private static final String MODULE_NAME = "mailtracking.mra.defaults";
	private static final String SCREEN_ID = "mailtracking.mra.defaults.upuratecard.maintainupuratecard";
	private static final String ACTION_SUCCESS = "screenload_success";
	/**
	 * Execute method
	 *
	 * @param invocationContext InvocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
    throws CommandInvocationException {
		log.entering("NavigationCommand","execute");
    	Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
    	MaintainUPURateCardSession session=null;
    	session=(MaintainUPURateCardSession) getScreenSession(MODULE_NAME,SCREEN_ID);
   		
   		MaintainUPURateCardForm form=(MaintainUPURateCardForm)invocationContext.screenModel;
   		
   		String companyCode = getApplicationSession().getLogonVO().getCompanyCode();
   		Page<RateLineVO> ratelinevopage=null;
   		RateLineFilterVO filtervo=new RateLineFilterVO();
   		filtervo.setCompanyCode(companyCode);
   		String displayPage=form.getDisplayPage();
   		
   		String ratecardId=form.getRateCardId();
   		int pagenum=Integer.parseInt(displayPage);
   		filtervo.setPageNumber(pagenum);
   		filtervo.setRateCardID(ratecardId);

     try{


    	  ratelinevopage=new MailTrackingMRADelegate().findRateLineDetails(filtervo);
   			}
   		catch (BusinessDelegateException businessDelegateException) {
			errors=handleDelegateException(businessDelegateException);

		}
   		if(errors != null && errors.size() > 0  ){
   			invocationContext.addAllError(errors);
   			invocationContext.target = ACTION_SUCCESS;
   			
   			return;
   		}
   		session.setRateLineDetails(ratelinevopage);
   		invocationContext.target = ACTION_SUCCESS;
		log.exiting("NavigationCommand", "execute");
	}
	}


