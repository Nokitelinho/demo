/*
 * AddRateLineCommand.java Created on Jan 22, 2007
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
import java.util.stream.Collectors;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.RateCardVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.RateLineVO;
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
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-1556
 *
 */
public class AddRateLineCommand extends BaseCommand {
	private Log log = LogFactory.getLogger("OriginDestination ScreenloadCommand");
	private static final String CLASS_NAME = "POPup ScreenLoadCommand";

	private static final String MODULE_NAME = "mailtracking.mra.defaults";
	private static final String SCREEN_ID = "mailtracking.mra.defaults.upuratecard.maintainupuratecard";
	private static final String ACTION_SUCCESS = "screenload_success";
	private static final String ACTION_FAILURE = "screenload_failure";
	private static final String BLANK = "";
	private static final String TKM_VALUES_SAME="mailtracking.mra.defaults.msg.err.tkmdetailssame";
	private static final String KEY_BILLING_TYPE_ONETIME = "mra.gpabilling.ratestatus";
	private static final String KEY_CARD_LEVEL_ONETIME = "mail.mra.ratecar.orgdstlevel";

	/*Done by Indu for Add Rate Line Pop up*/
	
	/**
	 * Execute method
	 *
	 * @param invocationContext InvocationContext
	 * @throws CommandInvocationException
	 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	log.entering("AddRateLineCommand","execute");
    	MaintainUPURateCardSession session=null;
    	session=(MaintainUPURateCardSession) getScreenSession(MODULE_NAME,SCREEN_ID);
   		MaintainUPURateCardForm form=(MaintainUPURateCardForm)invocationContext.screenModel;
   		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		ErrorVO error=null;
   		/*for setting an empty row at pop up load*/
   		
   		String companyCode = getApplicationSession().getLogonVO().getCompanyCode();
   		session.removeNewRateLineDetails();
   		ArrayList<RateLineVO> newratelinevos=new ArrayList<RateLineVO>();
		RateLineVO newratelinevo=new RateLineVO();
		newratelinevo.setCompanyCode(companyCode);
		newratelinevo.setOperationFlag(RateLineVO.OPERATION_FLAG_INSERT);
		newratelinevo.setOrigin("");
		newratelinevo.setDestination("");
		newratelinevos.add(newratelinevo);
		Page<RateLineVO> ratelinevopage =new Page<RateLineVO>(newratelinevos,1,0,1,0,0,false);
		session.setNewRateLineDetails(ratelinevopage);
		
		Map oneTimeHashMap 								= null;
		Collection<String> oneTimeActiveStatusList 		= new ArrayList<String>();
		oneTimeActiveStatusList.add(KEY_BILLING_TYPE_ONETIME);
		oneTimeActiveStatusList.add(KEY_CARD_LEVEL_ONETIME);
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
		Collection<OneTimeVO> levelOneTimeVOs = (Collection<OneTimeVO>)oneTimeHashMap.get(KEY_CARD_LEVEL_ONETIME);
		if(levelOneTimeVOs != null && !levelOneTimeVOs.isEmpty()){
			levelOneTimeVOs = levelOneTimeVOs.stream().sorted((vo1,vo2) -> vo2.getFieldValue().compareTo(vo1.getFieldValue())).collect(Collectors.toList());
			oneTimeHashMap.remove(KEY_CARD_LEVEL_ONETIME);
			oneTimeHashMap.put(KEY_CARD_LEVEL_ONETIME, levelOneTimeVOs);
		}
		session.setOneTimeVOs( (HashMap<String, Collection<OneTimeVO>>)oneTimeHashMap);
		
		 LocalDate fDate=new LocalDate(LocalDate.NO_STATION,Location.NONE,false);
		 LocalDate tDate=new LocalDate(LocalDate.NO_STATION,Location.NONE,false);
   		 RateCardVO ratecardvo=new RateCardVO();
   	     Double mdf=1.0;
   		ArrayList<RateLineVO> ratelinevos=null;
   		if(form.getSvTkm().trim().length()>0 && form.getSalTkm().trim().length()>0 && 
   				form.getAirmialTkm().trim().length()>0) {
	   		if(Double.parseDouble(form.getSvTkm())==Double.parseDouble(form.getSalTkm()) || 
	   				Double.parseDouble(form.getSvTkm())==Double.parseDouble(form.getAirmialTkm())){
	   			//form.setScreenStatus("new");
				//form.setStatus("New");
				error=new ErrorVO(TKM_VALUES_SAME);  
				error.setErrorDisplayType(ErrorDisplayType.WARNING);
				errors.add(error);
	   			}
   		}
   		if(!BLANK.equals(form.getRateCardId()) && form.getRateCardId().trim().length()>0){

   		 ratecardvo.setRateCardID(form.getRateCardId());
   	    }
   		if(!BLANK.equals(form.getDescription()) && form.getDescription().trim().length()>0){

      		 ratecardvo.setRateCardDescription(form.getDescription());
      	    }
   		if(!BLANK.equals(form.getValidFrom()) && form.getValidFrom().trim().length()>0){
			log.log(Log.INFO,"from date not null");
            fDate.setDate(form.getValidFrom());
            ratecardvo.setValidityStartDate(fDate);
   		}
   		if(!BLANK.equals(form.getValidTo()) && form.getValidTo().trim().length()>0){
			log.log(Log.INFO,"to date not null");
            tDate.setDate(form.getValidTo());
            ratecardvo.setValidityEndDate(tDate);
   		}
    	 if(!BLANK.equals(form.getMialDistFactor()) && form.getMialDistFactor().trim().length()>0){

    		 mdf=Double.parseDouble(form.getMialDistFactor());

    		 ratecardvo.setMailDistanceFactor(mdf);
    	 }
    	 else{

    		 ratecardvo.setMailDistanceFactor(mdf);
    	 }
    	 if(!BLANK.equals(form.getSvTkm()) && form.getSvTkm().trim().length()>0){

    		 ratecardvo.setCategoryTonKMRefOne(Double.parseDouble(form.getSvTkm()));
    	 }
    	 if(!BLANK.equals(form.getStatus()) && form.getStatus().trim().length()>0){

    		 ratecardvo.setRateCardStatus(form.getStatus());
    	 }
    	 if(!BLANK.equals(form.getSalTkm()) && form.getSalTkm().trim().length()>0){

    		 ratecardvo.setCategoryTonKMRefTwo(Double.parseDouble(form.getSalTkm()));
    	 }
    	 if(!BLANK.equals(form.getAirmialTkm()) && form.getAirmialTkm().trim().length()>0){

    		 ratecardvo.setCategoryTonKMRefThree(Double.parseDouble(form.getAirmialTkm()));
    	 }
    	 
    	 String fromDate[]=null;
    	 String toDate[]=null;
    	 if(form.getValidFromRateLine()!=null && form.getValidFromRateLine().length>0){
    		 fromDate=form.getValidFromRateLine();
    	 }
    	 if(form.getValidToRateLine()!=null && form.getValidToRateLine().length>0){
    		 toDate=form.getValidToRateLine();
    	 }

    	/*for updating dates if there is any change*/
    	 
    	 if(session.getRateLineDetails()!=null && session.getRateLineDetails().size()>0){
    		 Page<RateLineVO> fromSessionRates=null;//Added for ICRD-154268
    		 fromSessionRates=session.getRateLineDetails();
    		 ratelinevos=new ArrayList<RateLineVO>(session.getRateLineDetails());
    		 int size=ratelinevos.size();
    		   for(int i=0;i<size;i++) {
    			  RateLineVO ratelinevo=ratelinevos.get(i);
    			  if(fromDate!=null && toDate!=null){
    			  if (fromDate[i]!=null && toDate[i]!=null ){
    				  if((!fromDate[i].equals(ratelinevo.getValidityStartDate().toString()))||(!toDate[i].equals(ratelinevo.getValidityEndDate().toString()))){

    					  if( ratelinevo.getOperationFlag()==null){
    					  ratelinevo.setOperationFlag(RateLineVO.OPERATION_FLAG_UPDATE);
    					  }
    				  }
    				  ratelinevos.set(i,ratelinevo);

    			  }
    			 }
    		   }
    		      int ss=ratelinevos.size();
				  Page<RateLineVO> oldratespage=new Page<RateLineVO>(ratelinevos,fromSessionRates.getPageNumber(),fromSessionRates.getDefaultPageSize(),ss,
						  fromSessionRates.getStartIndex(),fromSessionRates.getEndIndex(),false);
				  session.setRateLineDetails(oldratespage);
    	 }
    	 session.setRateCardDetails(ratecardvo);
    	 if(errors!=null && errors.size()>0){
 			log.log(Log.FINE,"!!!inside errors!= null");
 			invocationContext.addAllError(errors);
 			invocationContext.target=ACTION_FAILURE;
 			return;
 		}    
    	invocationContext.target = ACTION_SUCCESS;
		log.exiting("AddRateLineConmmand", "execute");
    }

}
