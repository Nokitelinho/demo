/*
 * ChageStatusCommand.java Created on Dec 15, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.defaults.maintainratecard;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.RateCardVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.MaintainUPURateCardSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.MaintainUPURateCardForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-1556
 *
 */
public class ChageStatusCommand extends BaseCommand {
	private Log log = LogFactory.getLogger("ChangeStatusPopup ScreenloadCommand");

	private static final String CLASS_NAME = "POPup ScreenLoadCommand";
	private static final String KEY_BILLING_TYPE_ONETIME = "mra.gpabilling.ratestatus";
	private static final String MODULE_NAME = "mailtracking.mra.defaults";
	private static final String SCREEN_ID = "mailtracking.mra.defaults.upuratecard.maintainupuratecard";
	private static final String ACTION_SUCCESS = "screenload_success";
	private static final String BLANK = "";
	/**
	 * Execute method
	 *
	 * @param invocationContext InvocationContext
	 * @throws CommandInvocationException
	 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	log.entering("ChangeStatusCommand","execute");
    	
    	MaintainUPURateCardSession session=null;
    	MaintainUPURateCardForm form=(MaintainUPURateCardForm)invocationContext.screenModel;
   		
    	session=(MaintainUPURateCardSession) getScreenSession(MODULE_NAME,SCREEN_ID);
   		
   		String companyCode = getApplicationSession().getLogonVO().getCompanyCode();
   		Map oneTimeHashMap 								= null;
	Collection<String> oneTimeActiveStatusList 		= new ArrayList<String>();
		oneTimeActiveStatusList.add(KEY_BILLING_TYPE_ONETIME);
		Collection<ErrorVO> errors=null;
		try {
			/** getting collections of OneTimeVOs */
			oneTimeHashMap = new SharedDefaultsDelegate().findOneTimeValues(companyCode, oneTimeActiveStatusList);
			
		} catch (BusinessDelegateException e) {
			
    		e.getMessage();
    		errors=handleDelegateException( e );
		}
		if(errors != null && errors.size() > 0  ){
	   			invocationContext.addAllError(errors);
	   			invocationContext.target = ACTION_SUCCESS;
	   			
	   			return;
	   		}

		 LocalDate fDate=new LocalDate(LocalDate.NO_STATION,Location.NONE,false);
		 LocalDate tDate=new LocalDate(LocalDate.NO_STATION,Location.NONE,false);


  		RateCardVO ratecardvo=new RateCardVO();
  		 Double mdf=1.0;
		if(form.getRateCardId()!=null && form.getRateCardId().trim().length()>0){

	   		 ratecardvo.setRateCardID(form.getRateCardId());
	   	    }
	   		if(form.getDescription()!=null && form.getDescription().trim().length()>0){

	      		 ratecardvo.setRateCardDescription(form.getDescription());
	      	    }
	   		if(form.getValidFrom()!=null && form.getValidFrom().trim().length()>0){
				log.log(Log.INFO,"from date not null");
	            fDate.setDate(form.getValidFrom());
	            ratecardvo.setValidityStartDate(fDate);
	   		}
	   		if(form.getValidTo()!=null && form.getValidTo().trim().length()>0){
				log.log(Log.INFO,"to date not null");
	            tDate.setDate(form.getValidTo());
	            ratecardvo.setValidityEndDate(tDate);
	   		}
	    	 if(form.getMialDistFactor()!=null && form.getMialDistFactor().trim().length()>0){

	    		 mdf=Double.parseDouble(form.getMialDistFactor());

	    		 ratecardvo.setMailDistanceFactor(mdf);
	    	 }
	    	 else{

	    		 ratecardvo.setMailDistanceFactor(mdf);
	    	 }
	    	 if(form.getSvTkm()!=null && form.getSvTkm().trim().length()>0){

	    		 ratecardvo.setCategoryTonKMRefOne(Double.parseDouble(form.getSvTkm()));
	    	 }
	    	 if(form.getStatus()!=null && form.getStatus().trim().length()>0){

	    		ratecardvo.setRateCardStatus(form.getStatus());
	    	 }
	    	 if(form.getSalTkm()!=null && form.getSalTkm().trim().length()>0){

	    		 ratecardvo.setCategoryTonKMRefTwo(Double.parseDouble(form.getSalTkm()));
	    	 }
	    	 if(form.getAirmialTkm()!=null && form.getAirmialTkm().trim().length()>0){

	    		 ratecardvo.setCategoryTonKMRefThree(Double.parseDouble(form.getAirmialTkm()));
	    	 }
	    	 
	    	 
	    	 
		if(form.getFromPage()!=null&& form.getFromPage().trim().length()>0){
			
		    form.setFromPage(form.getFromPage());
		}
		if(form.getStatus()!=null&& form.getStatus().trim().length()>0){
			
		    form.setStatus(form.getStatus());
		}
		if(session.getRateCardDetails()!=null){
			if(session.getRateCardDetails().getRateCardStatus()!=null){
				form.setStatus(session.getRateCardDetails().getRateCardStatus());
				if(("CANCELLED").equalsIgnoreCase(session.getRateCardDetails().getRateCardStatus())){
	   				  
	   					form.setStatus("C");
	   				}
	   				if(("INACTIVE").equalsIgnoreCase(session.getRateCardDetails().getRateCardStatus())){
	   				 
	   					form.setStatus("I");
	   				}
	   				if(("ACTIVE").equalsIgnoreCase(session.getRateCardDetails().getRateCardStatus())){
	   				 
	   					form.setStatus("A");
	   				}
	   				if(("NEW").equalsIgnoreCase(session.getRateCardDetails().getRateCardStatus())){
	      				 
	      					form.setStatus("N");
	      				}
	   				if(("EXPIRED").equalsIgnoreCase(session.getRateCardDetails().getRateCardStatus())){
	      				 
	      					form.setStatus("E");
	      				}
				
			}
		}
		 session.setRateCardDetails(ratecardvo);
		session.setOneTimeVOs( (HashMap<String, Collection<OneTimeVO>>)oneTimeHashMap);
    	invocationContext.target = ACTION_SUCCESS;
		log.exiting("ChangeStatusCommand", "execute");

    }

}
