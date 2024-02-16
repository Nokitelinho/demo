/*
 * ScreenLoadRecordULDMovementCommand.java Created on Aug 1, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.misc.recorduldmovement;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.delegate.uld.defaults.ULDDefaultsDelegate;

import com.ibsplc.icargo.presentation.web.session.impl.uld.defaults.misc.RecordUldMovementSessionImpl;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.misc.RecordUldMovementSession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.misc.RecordULDMovementForm;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.business.uld.defaults.message.vo.ULDFlightMessageReconcileDetailsVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDMovementVO;



/**
 * This command class is invoked on the start up of the RecordULDMovement screen
 *  
 * @author A-1347
 */
public class ScreenLoadRecordULDMovementCommand extends BaseCommand {  
    
	private static final String SCREENLOAD_SUCCESS = "screenload_success";    
    private  Log log=LogFactory.getLogger("ULD _DEFAULTS");
    private static final String MODULE = "uld.defaults"; 
    
    private static final String SCREEN_NAME="screenLoad";
	
	
	private static final String SCREENID =
		"uld.defaults.misc.recorduldmovement";
    
	private static final String CONTENT_ONETIME="uld.defaults.contentcodes";
	private static final String PAGE_URL = "fromScmUldReconcile";
	
	
    /**
     * @param invocationContext
     * @throws CommandInvocationException
     */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	    log.log(Log.FINE,"ScreenLoadRecordULDMovementCommand");
    	    log.log(Log.FINE,"ScreenLoadRecordULDMovementCommand");
    	    ApplicationSessionImpl applicationSession = getApplicationSession();
    		LogonAttributes logonAttributes = applicationSession.getLogonVO();
    		String  compCode = logonAttributes.getCompanyCode();
    		
    	    RecordUldMovementSession  session = (RecordUldMovementSession)getScreenSession( "uld.defaults","uld.defaults.misc.recorduldmovement");
    	    RecordULDMovementForm recordULDMovementForm =(RecordULDMovementForm)invocationContext.screenModel; 
    	    
    	    if(("fromulderrorlog").equals(session.getPageURL()))
    		{
    	    	ULDFlightMessageReconcileDetailsVO uldFlightMessageReconcileDetailsVO=session.getULDFlightMessageReconcileDetailsVO();
    	    	recordULDMovementForm.setPageurl("fromulderrorlog");
    	    	recordULDMovementForm.setUpdateCurrentStation(true);
    	    	recordULDMovementForm.setCurrentStation(uldFlightMessageReconcileDetailsVO.getAirportCode());
    	    	session.setPageURL(null);
    			
    		}else if(("fromulderrorlogMessage").equals(session.getPageURL())){
    			recordULDMovementForm.setPageurl("fromulderrorlogMessage");
    			ULDFlightMessageReconcileDetailsVO uldFlightMessageReconcileDetailsVO=session.getULDFlightMessageReconcileDetailsVO();
    			recordULDMovementForm.setUpdateCurrentStation(true);
    	    	recordULDMovementForm.setCurrentStation(uldFlightMessageReconcileDetailsVO.getAirportCode());
    	    	session.setPageURL(null);
    			ErrorVO error = null;
    			error = new ErrorVO("uld.defaults.ulderrorlog.msg.warning.notintransit");
    			error.setErrorDisplayType(ErrorDisplayType.WARNING);
    			invocationContext.addError(error);	
    			
    		}else if(session.getPageURL()!=null&&PAGE_URL.equals(session.getPageURL())){
    			recordULDMovementForm.setPageurl(PAGE_URL);
    			recordULDMovementForm.setUpdateCurrentStation(true);
    			session.setPageURL(null);
    		}
    		
    		else   		
    		{   			
    			
    			if(("fromulderrorlogMessage").equals(recordULDMovementForm.getPageurl()) &&
					("true").equals(recordULDMovementForm.getMessageStatus())){
    				log.log(Log.FINE,"\n\n\n\n\n MESSAGE IS TRUE");
    				
    				//to set set the flight date as blank
    				Collection<ULDMovementVO> uldMovementVos=session.getULDMovementVOs();
    				for(ULDMovementVO uldMovementVO:uldMovementVos){
    					uldMovementVO.setFlightDateString("");
    				}
    				session.setULDMovementVOs(uldMovementVos);
    				//to set set the flight date as blank
    				
    				ULDFlightMessageReconcileDetailsVO uldFlightMessageReconcileDetailsVO=session.getULDFlightMessageReconcileDetailsVO();
    				recordULDMovementForm.setMessageStatus("");	
    				ULDDefaultsDelegate uldDefaultsDelegate =  new ULDDefaultsDelegate();
    				Collection<ErrorVO> error = new ArrayList<ErrorVO>();
    				try {
    				uldDefaultsDelegate.updateULDTransitStatus(compCode,uldFlightMessageReconcileDetailsVO.getUldNumber());
    				} catch (BusinessDelegateException businessDelegateException) {
    					businessDelegateException.getMessage();
    					error = handleDelegateException(businessDelegateException);
    				}
    				 recordULDMovementForm.setScreenStatusFlag(
    	    	  				ComponentAttributeConstants.SCREEN_STATUS_DETAIL);
    				invocationContext.target = SCREENLOAD_SUCCESS;
    				return;
    				
    			}//else{
    			session.setULDFlightMessageReconcileDetailsVO(null);
    			session.setPageURL(null); 	
    	    
    	    Collection<ULDMovementVO> uldMovementVos = new ArrayList<ULDMovementVO>();
    	    String[] uldNumbers=null;
    	    Collection<String> coll= null;
    	    log.log(Log.FINE, "the screenName is >>>>>>>>>>>>>>>",
					recordULDMovementForm.getScreenName());
			log.log(Log.FINE, "the screenName is >>>>>>>>>>>>>>>",
					recordULDMovementForm.getScreenName());
			log.log(Log.FINE, "the screenName is >>>>>>>>>>>>>>>",
					recordULDMovementForm.getScreenName());
			if(recordULDMovementForm.getScreenName()==null||
    	    	recordULDMovementForm.getScreenName().trim().length()==0){
    	    	log.log(Log.FINE,"Inside the check for the screen");
    	    	recordULDMovementForm.setScreenName(SCREEN_NAME);
    	    	log
						.log(
								Log.FINE,
								"the screenName inside the if statement is >>>>>>>>>>>>>>>",
								recordULDMovementForm.getScreenName());
    	    }
    	    
    	    
    	    if(recordULDMovementForm.getUldNumbers()!=null && recordULDMovementForm.getUldNumbers().trim().length()>0){
    	    	 coll = new ArrayList<String>();
    	    	  uldNumbers=recordULDMovementForm.getUldNumbers().split(",");
    	          for(int i=0;i<uldNumbers.length;i++){
    	    	  coll.add(uldNumbers[i]);
    	      }
    	    }
    	    session.setULDNumbers(coll);
    	    ULDMovementVO uldMovementVo = new ULDMovementVO();
    	    uldMovementVo.setFlightDateString("");
    	    //added by ayswarya
    	    
    	    if(("ulddiscrepancy").equals(recordULDMovementForm.getDiscrepancyStatus())){
    	    	 recordULDMovementForm.setFlagForUldDiscrepancy("ulddiscrepancy");
    	    	 log.log(Log.INFO,
						"recordULDMovementForm.getDiscrepancyCode()---->>>>",
						recordULDMovementForm.getDiscrepancyCode());
				uldMovementVo.setDiscrepancyCode(recordULDMovementForm.getDiscrepancyCode());
    	    	 log
						.log(
								Log.INFO,
								"recordULDMovementForm.getFlagForUldDiscrepancy()---->>>>",
								recordULDMovementForm.getFlagForUldDiscrepancy());
			if(recordULDMovementForm.getPointOfLadings()!=null && recordULDMovementForm.getPointOfLadings().trim().length()>0){
    	    	uldMovementVo.setPointOfLading(recordULDMovementForm.getPointOfLadings());
    	    }
    	    
    	    if(recordULDMovementForm.getPointOfUnLadings()!=null && recordULDMovementForm.getPointOfUnLadings().trim().length()>0){
    	    	uldMovementVo.setPointOfUnLading(recordULDMovementForm.getPointOfUnLadings());
    	    }
    	    uldMovementVo.setDummyMovement(true);
    	    uldMovementVo.setCurrentStation(recordULDMovementForm.getCurrentStation());
    	    recordULDMovementForm.setUpdateCurrentStation(true);
    	    
    	    log.log(Log.FINE, "uldMovementVo----------->>>", uldMovementVo);
			log
					.log(
							Log.FINE,
							"recordULDMovementForm.getUpdateCurrentStation()----------->>>",
							recordULDMovementForm.getUpdateCurrentStation());
    	    
    	    }
    	    //Added by A-2408 for bugfix starts
    	    uldMovementVo.setContent("X");
    	    //ends
    	    //ends
    	    uldMovementVos.add(uldMovementVo);
    	    session.setULDMovementVOs(uldMovementVos);
    			//}
    		}
    	    Collection<String> onetimeColl= new ArrayList<String>();
    	    Map<String,Collection<OneTimeVO>> oneTimeValues=null;
    	    onetimeColl.add(CONTENT_ONETIME);
            RecordUldMovementSessionImpl recordUldMovementSessionImpl = getScreenSession(MODULE, SCREENID);
            Collection<ErrorVO> err = new ArrayList<ErrorVO>();
		   try{
    	     oneTimeValues = new SharedDefaultsDelegate().findOneTimeValues(logonAttributes.getCompanyCode(),onetimeColl);
		   }catch(BusinessDelegateException ex){
			 ex.getMessage();
			 err = handleDelegateException(ex);
		   }
    	  Collection<OneTimeVO> contentOneTimeValues = oneTimeValues.get(CONTENT_ONETIME);
    	  if(contentOneTimeValues!=null && contentOneTimeValues.size()>0){
    		 recordUldMovementSessionImpl.setOneTimeContent(contentOneTimeValues);	
    		 recordULDMovementForm.setScreenStatusFlag(
    	  				ComponentAttributeConstants.SCREEN_STATUS_DETAIL);
    	  invocationContext.target = SCREENLOAD_SUCCESS;
    	
    	
    }}
}
