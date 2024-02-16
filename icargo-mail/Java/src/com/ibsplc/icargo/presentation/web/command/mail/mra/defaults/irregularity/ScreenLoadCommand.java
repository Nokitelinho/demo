
	/*
	 * ScreenLoadCommand.java Created on Sep 25, 2008
	 *
	 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
	 *
	 * This software is the proprietary information of IBS Software Services (P) Ltd.
	 * Use is subject to license terms.
	 */
	package com.ibsplc.icargo.presentation.web.command.mail.mra.defaults.irregularity;


	import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
	import com.ibsplc.icargo.framework.web.command.BaseCommand;
	import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
	import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.IrregularitySession;
	import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.MRAIrregularityForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
	import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

	/**
	 * Command class for screenload of proration log screen  
	 *
	 * Revision History
	 *
	 * Version      Date           Author          		    Description
	 *
	 *  0.1        Sep 25,2008     A-3229					Initial draft
	 */
	public class ScreenLoadCommand extends BaseCommand {

		private Log log = LogFactory.getLogger("Irregularity Log ScreenloadCommand");

		private static final String CLASS_NAME = "ScreenLoadCommand";

		private static final String MODULE_NAME = "mailtracking.mra.defaults";
		
		private static final String SCREEN_ID = "mailtracking.mra.defaults.irregularity";
		
		private static final String IRPSTATUS="mra.defaults.irpstatus";
		/*
		 * Target mappings for succes 
		 */
		private static final String ACTION_SUCCESS = "screenload_success";
		
		/**
		 * @param invocationContext
		 * @exception CommandInvocationException
		 */
	    public void execute(InvocationContext invocationContext)
	            throws CommandInvocationException {

	    	log.entering(CLASS_NAME, "execute");
	    	
	    	ApplicationSessionImpl applicationSession = getApplicationSession();
			LogonAttributes logonAttributes = applicationSession.getLogonVO();
	    	
			Collection<ErrorVO> errors=null;
	    
			IrregularitySession irregularitysession=getScreenSession(MODULE_NAME,SCREEN_ID);
	    	
	    	MRAIrregularityForm form=(MRAIrregularityForm)invocationContext.screenModel;
	    	
	    	form.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
	    	
	    	//ONE TIME DETAILS FOR IRPSTATUS
	    	
	    	SharedDefaultsDelegate sharedDefaultsDelegate = 
				new SharedDefaultsDelegate();
	    	
	    	Collection<String> clearingHouseValue = new ArrayList<String>();
	    	clearingHouseValue.add(IRPSTATUS);
	      	
			Map<String, Collection<OneTimeVO>> oneTimeValues = null;
			try {
				oneTimeValues =	sharedDefaultsDelegate.findOneTimeValues(
						logonAttributes.getCompanyCode(), 
						clearingHouseValue);
			} catch (BusinessDelegateException e) {
				errors = handleDelegateException(e); 
			}
	    	
			log.log(Log.INFO, "one time value----------", oneTimeValues);
			irregularitysession.setIrpStatus(new ArrayList<OneTimeVO>(oneTimeValues.get(IRPSTATUS)));
			irregularitysession.setIrregularityFilterVO(null);
			irregularitysession.setIrregularityVOs(null);
			//To Set default value
			form.setTotalFreightCharges("0.0");
			form.setTotal("0.0");
			
			LocalDate toDate=new LocalDate(logonAttributes.getStationCode(),Location.STN,true);
			form.setToDate(toDate.toDisplayDateOnlyFormat());
	     
	    	invocationContext.target = ACTION_SUCCESS;
	    	
			log.exiting(CLASS_NAME, "execute");
	    }

	}


	

