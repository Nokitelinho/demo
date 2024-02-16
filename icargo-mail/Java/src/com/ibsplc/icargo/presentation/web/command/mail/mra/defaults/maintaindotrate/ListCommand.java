/*
 * ListCommand.java Created on Aug 03, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.defaults.maintaindotrate;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.MailDOTRateFilterVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.MailDOTRateVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.mra.MailTrackingMRADelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.MaintainDOTRateSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.MaintainDOTRateForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-2408
 *
 */
public class ListCommand extends BaseCommand{
	
	private Log log = LogFactory.getLogger("MaintainDOTRate ScreenloadCommand");

	private static final String CLASS_NAME = "ListCommand";

	private static final String MODULE_NAME = "mailtracking.mra.defaults";
	
	private static final String SCREEN_ID = "mailtracking.mra.defaults.maintaindotrate";
	
	private static final String ACTION_SUCCESS = "screenload_success";
	
	private static final String DETAILS_FAILURE="screenload_failure";
	
	private static final String NO_RESULTS_FOUND="mailtracking.mra.defaults.maintaindotrate.noresultsfound";
	/**
	 * @param invocationContext
	 * @exception CommandInvocationException
	 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	log.entering(CLASS_NAME, "execute");
    	
    	String companyCode = getApplicationSession().getLogonVO().getCompanyCode();
    	Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
    	ErrorVO errorVO=null;
    	MaintainDOTRateForm form=(MaintainDOTRateForm)invocationContext.screenModel;
    	MaintainDOTRateSession session=null;
   		session=(MaintainDOTRateSession) getScreenSession(MODULE_NAME,SCREEN_ID);
   		MailDOTRateFilterVO filterVO=new MailDOTRateFilterVO();
   		
   		filterVO.setCompanyCode(companyCode);
   		String sectorOrigin=form.getSectorOriginCode();
   		String destinationCode=form.getSectorDestinationCode();
   		String gcm=form.getGreatCircleMiles();
   		String rateCode=form.getRateCodeFilter();
   		
   		if(sectorOrigin!=null && sectorOrigin.trim().length()>0){
   			filterVO.setSectorOriginCode(sectorOrigin.trim().toUpperCase());
   		}
   		if(destinationCode!=null && destinationCode.trim().length()>0){
   			filterVO.setSectorDestinationCode(destinationCode.trim().toUpperCase());
   		}
   		if(gcm!=null && gcm.trim().length()>0){
   			filterVO.setGcm(Integer.parseInt(gcm.trim()));
   		}
   		if(rateCode!=null && rateCode.trim().length()>0){
   			filterVO.setRateCode(rateCode.trim().toUpperCase());
   		}
   		log.log(Log.INFO, "filter VO to server---->", filterVO);
		ArrayList<MailDOTRateVO> mailDOTRateVOs=null;
   		//delegate call
   		try{
   			mailDOTRateVOs=(ArrayList<MailDOTRateVO>) new MailTrackingMRADelegate().findDOTRateDetails(filterVO);
   		}catch(BusinessDelegateException businessDelegateException){
   			errors=handleDelegateException(businessDelegateException);
   		}
   		log.log(Log.INFO, "listed VOs---->", mailDOTRateVOs);
		if(mailDOTRateVOs !=null && mailDOTRateVOs.size()>0){
   			session.setMailDOTRateVOs(mailDOTRateVOs);
   			form.setScreenFlag("list");
   		}else{
   			errorVO=new ErrorVO(NO_RESULTS_FOUND);
   			form.setScreenFlag("list");
			errors.add(errorVO);
   		}
   		if(errors!=null && errors.size()>0){
          	 log.log(Log.FINE,"!!!inside errors!= null");
   				invocationContext.addAllError(errors);
   				session.removeMailDOTRateVOs();

   				invocationContext.target=DETAILS_FAILURE;
   				return;
   			}
    	invocationContext.target = ACTION_SUCCESS;
		log.exiting(CLASS_NAME, "execute");
    }
}
