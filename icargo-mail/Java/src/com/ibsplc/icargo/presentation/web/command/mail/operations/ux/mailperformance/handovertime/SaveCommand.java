/*
 * SaveCommand.java Created on Jul 05, 2018
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.ux.mailperformance.handovertime;

import java.util.ArrayList;
import java.util.Collection;
import com.ibsplc.icargo.business.mail.operations.vo.MailHandoverVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailSubClassVO;
import com.ibsplc.icargo.business.mail.operations.vo.OfficeOfExchangeVO;
import com.ibsplc.icargo.business.shared.area.airport.vo.AirportValidationVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.area.AreaDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.ux.MailPerformanceSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.ux.MailPerformanceForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-6986
 *
 */
public class SaveCommand extends BaseCommand{

	private Log log = LogFactory.getLogger("MAILOPERATIONS");
    private static final String MODULE_NAME = "mail.operations";
	private static final String SCREEN_ID = "mail.operations.ux.mailperformance";
	private static final String GPA_EMPTY = "mailtracking.defaults.ux.mailperformance.msg.err.gpaempty";
	private static final String AIRPORTCODE_EMPTY = "mailtracking.defaults.ux.mailperformance.msg.err.arpEmpty";
	private static final String MAILCLASS_EMPTY = "mailtracking.defaults.ux.mailperformance.msg.err.mailClassEmpty";
	private static final String EXCHANGEOFFICE_EMPTY = "mailtracking.defaults.ux.mailperformance.msg.err.exchangeOfficeEmpty";
	private static final String HANDOVER_EMPTY = "mailtracking.defaults.ux.mailperformance.msg.err.handoverEmpty";
	private static final String SAVE_SUCCESS = "mailtracking.defaults.ux.mailperformance.msg.info.savesuccess";
	private static final String SUCCESS = "save_success";
	private static final String FAILURE = "save_failure";
	//private static final String HANDOVERTIME_EMPTY ="mailtracking.defaults.ux.mailperformance.msg.err.handOverTimeEmpty";
    //private static final String FORMULA_ALREADY_EXISTS_WITH_SAME_CONFIGURATION = "mailtracking.defaults.ux.mailperformance.msg.err.duplicaterecord";
    private static final String FORMULA_ALREADY_EXISTS_WITH_SAME_AIRPORTCODE_AND_CLASS_AND_EXCHGOFFC = "mailtracking.defaults.ux.mailperformance.msg.err.formulaalreadyexistswithsameairportclassandexchgoffice";
	private static final String INVALIDMALSUBCLASS = "mailtracking.defaults.ux.mailperformance.msg.err.invalidsubclass";
	private static final String INVALIDEXGOFC = "mailtracking.defaults.ux.mailperformance.msg.err.invalidexchangeoffice";
	private static final String MALCLASS_AND_MALSUBCLASS_NEEDNOTBECONFIGUREDTOGETHER = "mailtracking.defaults.ux.mailperformance.msg.err.configureeitherclassorsubclass";
	private static final String CANNOTCONFIGUREMAILCLASSFORMILITARY = "mailtracking.defaults.ux.mailperformance.msg.err.cannotconfiguremailclassformilitary";
	private static final String INVALIDARPCOD = "mailtracking.defaults.ux.mailperformance.msg.err.invalidairportcode";
	
	
    public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		
		log.log(Log.FINE, "\n\n in the save command----------> \n\n");
		
		MailPerformanceForm mailPerformanceForm =
				(MailPerformanceForm)invocationContext.screenModel;
		MailPerformanceSession mailPerformanceSession = 
				getScreenSession(MODULE_NAME,SCREEN_ID);
		
		LogonAttributes logonAttributes = getApplicationSession().getLogonVO();
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		AirportValidationVO airportValidationVO = null;
		Collection<MailHandoverVO> mailHandoverVOs = mailPerformanceSession.getMailHandoverVOs();
		 Collection<MailSubClassVO> mailSubClassVOs = null;
		Collection<ErrorVO> validationErrors = new ArrayList<ErrorVO>();
		Page<OfficeOfExchangeVO> exchDstAirport = null;
		if(mailHandoverVOs == null){
			mailHandoverVOs = new ArrayList<MailHandoverVO>();
		}
		
		mailHandoverVOs = updateMailHandoverVOs(mailHandoverVOs,mailPerformanceForm,logonAttributes);
		
		if(mailHandoverVOs!=null&&mailHandoverVOs.size()>0){
			int count = 0;
			for(MailHandoverVO handoverVO : mailHandoverVOs){
				
				
				
				boolean hoAirportCodesError = false;
				boolean mailClassError = false;
				boolean exchangeOfficeError = false;
				boolean handoverTimesError = false;
				
				if(("").equals(handoverVO.getHoAirportCodes())){
					
					ErrorVO error = new ErrorVO(AIRPORTCODE_EMPTY);
					if(!hoAirportCodesError){
	  				errors.add(error);
	  				hoAirportCodesError = true;
	  				invocationContext.addAllError(errors);
					invocationContext.target = FAILURE;
			    	return;
			    	
				}}
				else if(("").equals(handoverVO.getMailClass())){
					if(("").equals(handoverVO.getMailSubClass())){
						ErrorVO error = new ErrorVO(MAILCLASS_EMPTY);
						if(!mailClassError){
		  				errors.add(error);
							mailClassError = true;
							invocationContext.addAllError(errors);
							invocationContext.target = FAILURE;
							return;
						}
					}
				}
					else if(("").equals(handoverVO.getExchangeOffice())){
						ErrorVO error = new ErrorVO(EXCHANGEOFFICE_EMPTY);
						if(!exchangeOfficeError){
		  				errors.add(error);
		  				exchangeOfficeError = true;
		  				invocationContext.addAllError(errors);
						invocationContext.target = FAILURE;
				    	return;
						}}
				
						else if(("").equals(handoverVO.getHandoverTimes())){
						ErrorVO error = new ErrorVO(HANDOVER_EMPTY);
						if(!handoverTimesError){
		  				errors.add(error);
		  				handoverTimesError = true;
		  				invocationContext.addAllError(errors);
						invocationContext.target = FAILURE;
				    	return;
				}}
					
					
					try{
						airportValidationVO = new AreaDelegate().validateAirportCode
	    						(logonAttributes.getCompanyCode(),
	    								handoverVO.getHoAirportCodes().toUpperCase().trim());
					
					}catch(BusinessDelegateException businessDelegateException){
						errors = handleDelegateException(businessDelegateException);
					}
					
                	try{
                		exchDstAirport = new MailTrackingDefaultsDelegate().findOfficeOfExchange(logonAttributes.getCompanyCode(),
                  				handoverVO.getExchangeOffice(), 1);
                		if(exchDstAirport==null)
                		{
                			ErrorVO error = new ErrorVO(INVALIDEXGOFC);
    						
    		  				errors.add(error);
    		  				
    		  				invocationContext.addAllError(errors);
    						invocationContext.target = FAILURE;
    				    	return;	
                		}
   					    				}catch (BusinessDelegateException businessDelegateException) {
   					 				
   					 					errors = handleDelegateException(businessDelegateException);
   					 				 
   							    	   return;
   					 				}
                	
                	  try {
                		  mailSubClassVOs=	    	 new MailTrackingDefaultsDelegate().findMailSubClassCodes(
  				    			logonAttributes.getCompanyCode(),handoverVO.getMailSubClass());
  				    	if(mailSubClassVOs==null)
  				    	{
  				    		ErrorVO error = new ErrorVO(INVALIDMALSUBCLASS);
  						
  			  				    errors.add(error);
						invocationContext.addAllError(errors);
						invocationContext.target = FAILURE;
				    	return;
  				    	
					}
  					}catch(BusinessDelegateException businessDelegateException) {
  				    	
  				    	errors = handleDelegateException(businessDelegateException);
		 				   invocationContext.addAllError(errors);
						 
				    	 
				}
			
		
	
			
		
	
			if(errors.size() == 0){
				validationErrors = validateHandOverConfiguration(mailHandoverVOs,handoverVO,count);
			}
			count++;
			}
		if(validationErrors != null ){
			for(ErrorVO errorVO : validationErrors){
				errors.add(errorVO);
			}
			if(errors != null && errors.size()>0) {
				invocationContext.addAllError(errors);
				mailPerformanceForm.setScreenFlag("hoRadiobtn");
				mailPerformanceForm.setStatusFlag("Save_fail");
				invocationContext.target = FAILURE;
		    	return;
			}
		
		
		}
			
		 
			
	
		try {
			new MailTrackingDefaultsDelegate().saveMailHandoverDetails(mailHandoverVOs);
		}catch(BusinessDelegateException businessDelegateException) {
			errors = handleDelegateException(businessDelegateException);
		}
		
		if(errors != null && errors.size()>0) {
			invocationContext.addAllError(errors);
			mailPerformanceForm.setScreenFlag("hoRadiobtn");
			mailPerformanceForm.setStatusFlag("Save_fail");
			invocationContext.target = FAILURE;
	    	return;
		}
		if(mailHandoverVOs != null && mailHandoverVOs.size()>0){
			log.log(Log.FINE, "\n\n <============= SAVE SUCCESS !!!  =============> \n\n");
			ErrorVO error = new ErrorVO(SAVE_SUCCESS);
			errors.add(error);
			mailPerformanceForm.setStatusFlag("Save_success");
			mailPerformanceForm.setScreenFlag("hoRadiobtn");
			invocationContext.addAllError(errors);
		}
		invocationContext.addError(new ErrorVO(SAVE_SUCCESS));
		mailPerformanceSession.removeMailHandoverVOs();
		
		mailPerformanceForm.setHoPaCode("");
		mailPerformanceForm.setHoAirport("");
		mailPerformanceForm.setHoMailClass("");
		mailPerformanceForm.setHoExchangeOffice("");
		mailPerformanceForm.setScreenFlag("hoRadiobtn");
		mailPerformanceForm.setScreenStatusFlag
					(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
		invocationContext.target = SUCCESS;	
		return;
	}}
	
	/**
     * Method to update the MailHandoverVOs in session
     * @param mailhandoverVOs
     * @param mailPerformanceForm
     * @param companyCode
     * @return
     */
	private Collection<MailHandoverVO> updateMailHandoverVOs(Collection<MailHandoverVO> mailHandoverVOs,
			MailPerformanceForm mailPerformanceForm,LogonAttributes logonAttributes){
		
		log.entering("SaveCommand","updateMailHandoverVOs");
    	
    	String[] oprflags = mailPerformanceForm.getHoOperationFlags();
    	
    	int size = 0;
    	if(mailHandoverVOs!=null && mailHandoverVOs.size()>0){
    		size = mailHandoverVOs.size();
    	}
    	Collection<MailHandoverVO> newMailHandoverVOs = new ArrayList<MailHandoverVO>();
    	
    	for(int index=0; index<oprflags.length;index++){
			if(index >= size){
				if(!"NOOP".equals(oprflags[index])){
					
					MailHandoverVO mailHandoverVO = new MailHandoverVO();
					mailHandoverVO.setCompanyCode(logonAttributes.getCompanyCode());
					mailHandoverVO.setGpaCode(mailPerformanceForm.getHoPaCode());
					mailHandoverVO.setHoAirportCodes(mailPerformanceForm.getHoAirportCodes()[index].toUpperCase().trim());
					mailHandoverVO.setMailClass(mailPerformanceForm.getHoMailClasses()[index]);
					if(mailPerformanceForm.getExchangeOffice()[index] !=null && mailPerformanceForm.getExchangeOffice()[index].length()==6){
						mailHandoverVO.setExchangeOffice(mailPerformanceForm.getExchangeOffice()[index].substring(0, 5));
					}else if(mailPerformanceForm.getExchangeOffice()[index] !=null && mailPerformanceForm.getExchangeOffice()[index].length()<6){
						mailHandoverVO.setExchangeOffice(mailPerformanceForm.getExchangeOffice()[index]);
					}
					mailHandoverVO.setHandoverTimes(makeHandoverTime(mailPerformanceForm.getHandoverTimes()[index]));
					mailHandoverVO.setHoOperationFlags(mailPerformanceForm.getHoOperationFlags()[index]);
					mailHandoverVO.setMailSubClass(mailPerformanceForm.getMailSubClass()[index]);
					//Added by A-7929 as part of IASCB-35577
					mailHandoverVO.setLastUpdateUser(logonAttributes.getUserId().toUpperCase());
					
					newMailHandoverVOs.add(mailHandoverVO);
					
				}
			}else{
				int count = 0;
				if(mailHandoverVOs!=null && mailHandoverVOs.size()>0){
					for(MailHandoverVO mailHandoverVO : mailHandoverVOs){
						if(count == index){
							if(!"NOOP".equals(oprflags[index])){
								mailHandoverVO.setHoAirportCodes(mailPerformanceForm.getHoAirportCodes()[index].
										toUpperCase().trim());
								mailHandoverVO.setMailClass(mailPerformanceForm.getHoMailClasses()[index]);
								if(mailPerformanceForm.getExchangeOffice()[index] !=null && mailPerformanceForm.getExchangeOffice()[index].length()==6){
									mailHandoverVO.setExchangeOffice(mailPerformanceForm.getExchangeOffice()[index].substring(1, 5));
								}else if(mailPerformanceForm.getExchangeOffice()[index] !=null && mailPerformanceForm.getExchangeOffice()[index].length()<6){
									mailHandoverVO.setExchangeOffice(mailPerformanceForm.getExchangeOffice()[index]);
								}
								mailHandoverVO.setHandoverTimes(makeHandoverTime(mailPerformanceForm.getHandoverTimes()[index]));
								mailHandoverVO.setMailSubClass(mailPerformanceForm.getMailSubClass()[index]);
								 if("N".equals(oprflags[index])){
									 mailHandoverVO.setHoOperationFlags(null);
								 }else{
									 mailHandoverVO.setHoOperationFlags(oprflags[index]);
								 }
									//Added by A-7929 as part of IASCB-35577
								mailHandoverVO.setLastUpdateUser(logonAttributes.getUserId().toUpperCase());
								newMailHandoverVOs.add(mailHandoverVO);
							}
						}
						count++;
					}
				}
			}
			
    	}
    	log.exiting("SaveCommand","updateMailHandoverVOs");
		
		return newMailHandoverVOs;
	}

	private Collection<ErrorVO> validateHandOverConfiguration(Collection<MailHandoverVO> newMailHandoverVOs,
			MailHandoverVO handoverVO1,int count) {
		
		log.log(Log.FINE, " Inside handover Validation ");
		ErrorVO error = null;
	    Object[] errorCodes = new Object[3];
	    Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
	   
	    int index = 0;
	   boolean both =false ;
		for(MailHandoverVO handOverVO : newMailHandoverVOs){
		
			if(index!=count){
				if((handOverVO.getHoAirportCodes().toUpperCase()).equals(handoverVO1.getHoAirportCodes()) &&
					(handOverVO.getMailClass().equals(handoverVO1.getMailClass())) &&
					(handOverVO.getExchangeOffice().equals(handoverVO1.getExchangeOffice())) 
					)
				{
				
					  error = new ErrorVO(FORMULA_ALREADY_EXISTS_WITH_SAME_AIRPORTCODE_AND_CLASS_AND_EXCHGOFFC);
			           
			            error.setErrorData(errorCodes);
	        	   
				}
				
				
			
			index++;
		}
			if ((handOverVO.getMailClass()!=null && handOverVO.getMailClass().trim().length()>0))
			{
				if ((handOverVO.getMailSubClass()!=null && handOverVO.getMailSubClass().trim().length()>0))
				{
				
				both=true ;
			}
			}
			if(	both==true )
			{
				  error = new ErrorVO(MALCLASS_AND_MALSUBCLASS_NEEDNOTBECONFIGUREDTOGETHER);
		           
		            error.setErrorData(errorCodes);	
			}
			if("7".equals(handOverVO.getMailClass()))
			{
				  error = new ErrorVO(CANNOTCONFIGUREMAILCLASSFORMILITARY);
		           
		            error.setErrorData(errorCodes);		
			}
			}	
			
		 if(error != null){
      	  	errors.add(error);
        }
		 return errors;
		}
	private String makeHandoverTime(String handoverTime){
		String modifiedTime = "";
		if(handoverTime!=null){
			String[] timeArray = handoverTime.split(":");
			if(timeArray!=null && timeArray.length>0){
				for(int i=0; i<timeArray.length; i++){
					modifiedTime = modifiedTime+timeArray[i];
				}
			}
		}
		return modifiedTime;
	}
}
			
