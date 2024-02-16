/*
 * ScreenReloadCommand.java Created on Jan 22, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.defaults.maintainratecard;


import com.ibsplc.icargo.business.mail.mra.defaults.vo.RateCardVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.MaintainUPURateCardSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.MaintainUPURateCardForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
/**
 * @author A-2391
 *
 */
public class ScreenReloadCommand extends BaseCommand{
	private Log log = LogFactory.getLogger("Maintain UPURateCard ScreenloadCommand");
	private static final String CLASS_NAME = "ScreenLoadCommand";

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
		 log.entering(CLASS_NAME, "execute");
	    	
	    	MaintainUPURateCardSession session=null;
	    	session=(MaintainUPURateCardSession) getScreenSession(MODULE_NAME,SCREEN_ID);
	    	MaintainUPURateCardForm form=(MaintainUPURateCardForm)invocationContext.screenModel;
			RateCardVO ratecardvo=session.getRateCardDetails();
			form.setRateCardId(ratecardvo.getRateCardID());
			form.setStatus(ratecardvo.getRateCardStatus());
			
			if(ratecardvo.getRateCardDescription()!=null && ratecardvo.getRateCardDescription().trim().length()>0){
				form.setDescription(ratecardvo.getRateCardDescription());
			}
			if(ratecardvo.getValidityStartDate()!=null){
				String fromdat=ratecardvo.getValidityStartDate().toDisplayDateOnlyFormat();
				form.setValidFrom(fromdat);
			}
			if(ratecardvo.getValidityEndDate()!=null){
				String todat=ratecardvo.getValidityEndDate().toDisplayDateOnlyFormat();
				form.setValidTo(todat);
			}
			
			String str=String.valueOf(ratecardvo.getMailDistanceFactor());
			form.setMialDistFactor(str);
			str=String.valueOf(ratecardvo.getCategoryTonKMRefOne());
			form.setSvTkm(str);
			str=String.valueOf(ratecardvo.getCategoryTonKMRefTwo());
			form.setSalTkm(str);
			str=String.valueOf(ratecardvo.getCategoryTonKMRefThree());
			form.setAirmialTkm(str);
	    	invocationContext.target = ACTION_SUCCESS;
			log.exiting(CLASS_NAME, "execute");
	 }
	 }


