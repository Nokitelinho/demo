	
	/*
	 * ScreenloadCommand.java Created on Sep 03, 2008
	 *
	 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
	 *
	 * This software is the proprietary information of IBS Software Services (P) Ltd.
	 * Use is subject to license terms.
	 */
	package com.ibsplc.icargo.presentation.web.command.mail.mra.defaults.despatchrouting;


	import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;

	import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
	import com.ibsplc.icargo.framework.web.command.BaseCommand;
	import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
	import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.DSNRoutingSession;
	import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.DespatchRoutingForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
	import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

	/**
	 * @author  A-3229
	 * Command class for screenload of manualproration screen  
	 *
	 * Revision History
	 *
	 * Version      Date           Author          		    Description
	 *
	 *  0.1        Sep 03,2008     A-3229					Initial draft
	 */
	public class ScreenloadCommand extends BaseCommand {

		private Log log = LogFactory.getLogger("DespatchRouting ScreenloadCommand");

		private static final String CLASS_NAME = "ScreenLoadCommand";

		private static final String MODULE_NAME = "mailtracking.mra.defaults";
		
		private static final String SCREEN_ID = "mailtracking.mra.defaults.despatchrouting";
		
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
	    	
	    	DespatchRoutingForm form=(DespatchRoutingForm)invocationContext.screenModel;
	   	
	    	DSNRoutingSession  dsnRoutingSession = 
				(DSNRoutingSession)getScreenSession(MODULE_NAME, SCREEN_ID);
	    	form.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
	    	form.setAirport(getApplicationSession().getLogonVO().getStationCode());
	    	form.setExactMailSource("TEMP");
	    	//Added by A-8176 for ICRD-225429 starts
	    	SharedDefaultsDelegate defaultsDelegate = new SharedDefaultsDelegate();
	    	LogonAttributes logonAttributes = getApplicationSession().getLogonVO();
	    	String companyCode = logonAttributes.getCompanyCode();
	    	Map<String, Collection<OneTimeVO>> hashMap = 
	    		      new HashMap<String, Collection<OneTimeVO>>();
	    	 Collection<String> oneTimeList = new ArrayList();
	    	 oneTimeList.add("mailtracking.defaults.agreementtype");
	    	 oneTimeList.add("cra.proration.blockspacetype");
	    	 oneTimeList.add("mail.mra.defaults.mailsource");
	    	 oneTimeList.add("mail.mra.defaults.mailsource");  
	    	 oneTimeList.add("mail.mra.defaults.weightunit");
	    	 oneTimeList.add("mailtracking.mra.routingsegmentsource");//Added by Manish for IASCB-40970
	    	 
	    	 try
	    	    {
	    	      hashMap = defaultsDelegate.findOneTimeValues(companyCode, 
	    	        oneTimeList);
	    	    }
	    	    catch (BusinessDelegateException localBusinessDelegateException3)
	    	    {
	    	      this.log.log(7, "onetime fetch exception");
	    	    }
	    	Collection<OneTimeVO> agreementTypes = (Collection<OneTimeVO>)hashMap.get("mailtracking.defaults.agreementtype");
	    	if(agreementTypes!=null)
	    	{
	    	   log.log(Log.INFO,"Sizeee----", agreementTypes.size());
	    	for(OneTimeVO list: agreementTypes) {
	    		log.log(Log.INFO,"LIST----------",list.getFieldDescription());
	    	}
	    	}
	        dsnRoutingSession.setAgreementTypes((ArrayList<OneTimeVO>)agreementTypes);
	        
	        Collection<OneTimeVO> blockSpaceTypes = (Collection<OneTimeVO>)hashMap.get("cra.proration.blockspacetype");

	        dsnRoutingSession.setBlockSpaceTypes((ArrayList<OneTimeVO>)blockSpaceTypes);
	        //Added by A-7794 as part of ICRD-232299
	        Collection<OneTimeVO> mailSources = (Collection<OneTimeVO>)hashMap.get("mail.mra.defaults.mailsource");
	        dsnRoutingSession.setMailSources((ArrayList<OneTimeVO>)mailSources);
	      //Added by A-8176 for ICRD-225429 ends

	        dsnRoutingSession.removeDSNRoutingFilterVO();
	    	dsnRoutingSession.removeDSNRoutingVOs();
	    
	    	log.log(Log.INFO, "Airport---------------", form.getAirport());
			invocationContext.target = ACTION_SUCCESS;
			log.exiting(CLASS_NAME, "execute");
	    }

	}


