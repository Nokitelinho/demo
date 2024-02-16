/*
 * SaveCommand.java Created on Sep 30, 2010
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.residitrestrictonsetup;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.mail.operations.vo.PostalAdministrationVO;
import com.ibsplc.icargo.business.mail.operations.vo.ResiditRestrictonVO;
import com.ibsplc.icargo.business.shared.airline.vo.AirlineValidationVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.airline.AirlineDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.area.AreaDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.ResiditRestrictonSetUpSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.ResiditRestrictonSetUpForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-3108
 *
 */
public class SaveCommand extends BaseCommand {


	private Log log = LogFactory.getLogger("MailTracking,ResiditRestrictonSetUp");
	
	private static final String MODULE_NAME = "mail.operations";
	private static final String SCREEN_ID = "mailtracking.defaults.residitrestrictionsetup";
	
	private static final String SUCCESS = "save_success";
	private static final String FAILURE = "save_failure";
	
	private static final String FIELD_EMPTY = 
					"mailtracking.defaults.residitrestrictionsetup.msg.err.fieldEmpty";
	private static final String DUPLICATE_CODE = 
									"mailtracking.defaults.residitrestrictionsetup.duplicaterecords";
		
	private static final String SAVE_SUCCESS = 
		"mailtracking.defaults.residitrestrictionsetup.msg.info.savesuccess";
	
	private static final String INVALID_CARCOD ="mailtracking.defaults.residitrestrictionsetup.msg.err.invalidcarriercode";
	private static final String INVALID_PACOD ="mailtracking.defaults.residitrestrictionsetup.msg.err.invalidpacode";
	
	private static final String BLANK = "";
	
	
	/**
	 * This method overrides the executre method of BaseComand class
	 * @param invocationContext
	 * @return
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
									throws CommandInvocationException {
		
    	log.log(Log.FINE, "\n\n in the save command----------> \n\n");    	
		
		
    	ResiditRestrictonSetUpForm residitRestrictonSetUpForm =
						(ResiditRestrictonSetUpForm)invocationContext.screenModel;
    	ResiditRestrictonSetUpSession residitRestrictonSetUpSession = 
			getScreenSession(MODULE_NAME,SCREEN_ID);

		LogonAttributes logonAttributes = getApplicationSession().getLogonVO();
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		Collection<ErrorVO> methodErrors = new ArrayList<ErrorVO>();

		ArrayList<ResiditRestrictonVO> residitRestrictonVOs =residitRestrictonSetUpSession.getResiditRestrictonVOs();
    	if (residitRestrictonVOs == null) {
    		residitRestrictonVOs = new ArrayList<ResiditRestrictonVO>();
		}
     	residitRestrictonVOs = updateResiditRestrictonVOs(residitRestrictonVOs,
    			residitRestrictonSetUpForm,logonAttributes);
    	
    	AirlineDelegate airlineDelegate = new AirlineDelegate();
    	AirlineValidationVO airlineValidationVO = null;
		if(residitRestrictonVOs != null && residitRestrictonVOs.size()>0){
			for(ResiditRestrictonVO residitRestrictonVO:residitRestrictonVOs){
				
				if(residitRestrictonVO.getOperationFlag()!=null && 
						!residitRestrictonVO.getOperationFlag().equals(ResiditRestrictonVO.OPERATION_FLAG_DELETE)){
				
					if(residitRestrictonVO.getCarrierCode()==null || 
							BLANK.equals(residitRestrictonVO.getCarrierCode())){
					ErrorVO error = new ErrorVO(FIELD_EMPTY,new String[]{"Carrier Code"});
					errors.add(error);
				}else{
					airlineValidationVO = null;
	    			
	    			try {
		    			airlineValidationVO = airlineDelegate.validateAlphaCode(
		    					logonAttributes.getCompanyCode(),residitRestrictonVO.getCarrierCode());
		    		}catch (BusinessDelegateException businessDelegateException) {
		    			//errors = handleDelegateException(businessDelegateException);
		    		}
		    		if (airlineValidationVO==null ){
		    			Object[] obj = {residitRestrictonVO.getCarrierCode()};  
		    			errors.add(new ErrorVO(INVALID_CARCOD,obj));
		    			
			    		}else if(airlineValidationVO!=null){	    			
		    			residitRestrictonVO.setCarrierid(airlineValidationVO.getAirlineIdentifier());
			    		if(residitRestrictonVO.getCarrierName()==null ||
			    				BLANK.equals(residitRestrictonVO.getCarrierName())){
					         residitRestrictonVO.setCarrierName(airlineValidationVO.getAirlineName());
					       }
					    }
					}
				if(residitRestrictonVO.getAirportCode()!=null && 
						!(residitRestrictonVO.getAirportCode().equals(MailConstantsVO.ARP_ALL)) && 
								! (BLANK.equals(residitRestrictonVO.getAirportCode()))){
					
					try{
			    		new AreaDelegate()
			    		.validateAirportCode(logonAttributes.getCompanyCode(),residitRestrictonVO.getAirportCode());
			    	}catch(BusinessDelegateException businessDelegateException){
			    		errors = handleDelegateException(businessDelegateException);				    		
			    	}	
				}

				
				//@A-2928 : Commented as part of bug fix : 102474
//				if(BLANK.equals(residitRestrictonVO.getCarrierName())){
//					ErrorVO error = new ErrorVO(FIELD_EMPTY,new String[]{"Carrier Name"});
//					errors.add(error);
//				}
				
				if(residitRestrictonVO.getPaCode()!=null && 
						!residitRestrictonVO.getPaCode().equals(MailConstantsVO.POACOD_ALL)){
					
					PostalAdministrationVO paVO=null;
			    	
			    	try{
			    		paVO = new MailTrackingDefaultsDelegate()
			    		.findPACode(logonAttributes.getCompanyCode(),residitRestrictonVO.getPaCode());
			    	}
			    	catch(BusinessDelegateException businessDelegateException){
			    		errors = handleDelegateException(businessDelegateException);
			    		
			    	}
			    	if(residitRestrictonVO.getPaCode()!=null &&
			    			! BLANK.equals(residitRestrictonVO.getPaCode())){
			    	if (paVO==null ) {
		    			Object[] obj = {residitRestrictonVO.getPaCode()};
		    			errors.add(new ErrorVO(INVALID_PACOD,obj));				    	    			
		    		}
			    	}
					
				}
				}
				/*
				//@A-2928 : Added as part of Bugfix : 102474
				if(residitRestrictonVO.getCarrierName() == null 
						|| BLANK.equals(residitRestrictonVO.getCarrierName()&&  airlineValidationVO !=null)){
					residitRestrictonVO.setCarrierName(airlineValidationVO.getAirlineName());
				}
				*/
			}
		}
		
		if(errors != null && errors.size()>0){
			invocationContext.addAllError(errors);
			 residitRestrictonSetUpSession.setResiditRestrictonVOs(residitRestrictonVOs);
			invocationContext.target = FAILURE;
	    	return;
		}
		
    	
		
		errors = chkDuplicate(residitRestrictonVOs);
		if(errors != null && errors.size()>0) {
			invocationContext.addAllError(errors);
			residitRestrictonSetUpSession.setResiditRestrictonVOs(residitRestrictonVOs);
			invocationContext.target = FAILURE;
	    	return;
		}
		residitRestrictonSetUpSession.setResiditRestrictonVOs(residitRestrictonVOs);
		
		
		log
				.log(
						Log.FINE,
						"\n\n in the save command-----------> residitRestrictonVOs before save \n\n",
						residitRestrictonVOs);
		try {
			new MailTrackingDefaultsDelegate().saveResiditRestrictions(residitRestrictonVOs);
		}catch(BusinessDelegateException businessDelegateException) {
			methodErrors = handleDelegateException(businessDelegateException);
		
					if(methodErrors!=null && methodErrors.size()>0){
						
						for(ErrorVO errorVO:methodErrors){
							if(errorVO.getErrorCode()!=null){
								log	.entering("SaveCCommand","errorVO.getErrorCode"+errorVO.getErrorCode());
								errors.add(errorVO);

							 }
						}
						
					}
				
				
				
		}
		
		if(errors != null && errors.size()>0) {
			invocationContext.addAllError(errors);			
			invocationContext.target = FAILURE;
	    	return;
		}
		
		if(residitRestrictonVOs != null && residitRestrictonVOs.size()>0){
			ErrorVO error = new ErrorVO(SAVE_SUCCESS);
			errors.add(error);
			invocationContext.addAllError(errors);
		}
		residitRestrictonSetUpForm.setAirportCodeFilter(BLANK);
		residitRestrictonSetUpForm.setCarrierCodeFilter(BLANK);
		residitRestrictonSetUpForm.setPaCodeFilter(BLANK);
		
		residitRestrictonSetUpSession.setResiditRestrictonVOs(null);
		residitRestrictonSetUpSession.setAirportCode(null);
		residitRestrictonSetUpForm.setScreenStatusFlag
					(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
		invocationContext.target = SUCCESS;	
	}
	
	/**
     * Method to update the partnerCarrierVOs in session
     * @param partnerCarrierVOs
     * @param partnerCarriersForm
     * @param logonAttributes
     * @return
     */
    private ArrayList<ResiditRestrictonVO> updateResiditRestrictonVOs(
    		Collection<ResiditRestrictonVO> residitRestrictonVOs,
    		ResiditRestrictonSetUpForm residitRestrictonSetUpForm,
    		LogonAttributes logonAttributes) {
    	
    	log.entering("SaveCommand","updateResiditRestrictonVOs");
   
    	String[] oprflags = residitRestrictonSetUpForm.getOpFlag();
    	
    	int size = 0;
    	if(residitRestrictonVOs != null && residitRestrictonVOs.size() > 0){
			   size = residitRestrictonVOs.size();
    	}
    	Collection<ResiditRestrictonVO> newResiditRestrictonVOs = new ArrayList<ResiditRestrictonVO>();
    
    	
		for(int index=0; index<oprflags.length;index++){
			//if(index >= size){
				if(!"NOOP".equals(oprflags[index])){
					ResiditRestrictonVO residitRestrictonVO = new ResiditRestrictonVO();
			if(residitRestrictonSetUpForm.getCarrierCode()[index]!=null 
						&& ! (BLANK.equals(residitRestrictonSetUpForm.getCarrierCode()[index]))){
					residitRestrictonVO.setCompanyCode(logonAttributes.getCompanyCode());
					if(residitRestrictonSetUpForm.getAirportCode()[index]!=null && 
							!residitRestrictonSetUpForm.getAirportCode()[index].equals(BLANK)){
						residitRestrictonVO.setAirportCode(residitRestrictonSetUpForm.getAirportCode()[index].toUpperCase().trim());						
					}
					else{
						residitRestrictonVO.setAirportCode(MailConstantsVO.ARP_ALL);
					}
	    			residitRestrictonVO.setCarrierCode(residitRestrictonSetUpForm.getCarrierCode()[index].toUpperCase().trim());
	    			residitRestrictonVO.setCarrierName(residitRestrictonSetUpForm.getCarrierName()[index]);
	    			if(residitRestrictonSetUpForm.getPaCode()[index]!=null &&  
	    					!residitRestrictonSetUpForm.getPaCode()[index].equals(BLANK)){
	    				residitRestrictonVO.setPaCode(residitRestrictonSetUpForm.getPaCode()[index].toUpperCase().trim());
	    				
	    			}
	    			else{
	    				residitRestrictonVO.setPaCode(MailConstantsVO.POACOD_ALL);
	    			}
	    			residitRestrictonVO.setOperationFlag(residitRestrictonSetUpForm.getOpFlag()[index]);
	    			residitRestrictonVO.setLastUpdateUser(logonAttributes.getUserId().toUpperCase());
	    			newResiditRestrictonVOs.add(residitRestrictonVO);
		         	}else{		         		
							residitRestrictonVO.setAirportCode("");			
							residitRestrictonVO.setCarrierCode("");
							residitRestrictonVO.setCarrierName("");
							residitRestrictonVO.setPaCode("");
							residitRestrictonVO.setOperationFlag(residitRestrictonSetUpForm.getOpFlag()[index]);
			    			residitRestrictonVO.setLastUpdateUser(logonAttributes.getUserId().toUpperCase());
			    			newResiditRestrictonVOs.add(residitRestrictonVO);
							
						}        		
					   
				 
				}
			//}
		/*else{
				int count = 0;
				if(residitRestrictonVOs != null ){
				   for(ResiditRestrictonVO residitRestrictonVO:residitRestrictonVOs){
					   if(count == index){
						   if(!"NOOP".equals(oprflags[index])){
							   if(residitRestrictonSetUpForm.getAirportCode()[index]!=null && !residitRestrictonSetUpForm.getAirportCode()[index].equals(BLANK)){
									residitRestrictonVO.setAirportCode(residitRestrictonSetUpForm.getAirportCode()[index].toUpperCase().trim());
									try{
							    		new AreaDelegate().validateAirportCode(logonAttributes.getCompanyCode(),residitRestrictonVO.getAirportCode());
							    	}catch(BusinessDelegateException businessDelegateException){
							    		errors = handleDelegateException(businessDelegateException);				    		
							    	}	
							   }
								else
									residitRestrictonVO.setAirportCode(MailConstantsVO.ARP_ALL);  	
							   
				    			residitRestrictonVO.setCarrierCode(residitRestrictonSetUpForm.getCarrierCode()[index].toUpperCase().trim());
				    			
				    			AirlineValidationVO airlineValidationVO = null;
				    			
				    			try {
					    			airlineValidationVO = airlineDelegate.validateAlphaCode(
					    					logonAttributes.getCompanyCode(),residitRestrictonVO.getCarrierCode());
					    		}catch (BusinessDelegateException businessDelegateException) {
					    			errors = handleDelegateException(businessDelegateException);
					    		}
					    		if (airlineValidationVO==null  ){
					    			
					    			Object[] obj = {residitRestrictonVO.getCarrierCode()};  
					    			errors.add(new ErrorVO(INVALID_CARCOD,obj));
					    			
					    		}else if(airlineValidationVO!=null)
					    			residitRestrictonVO.setCarrierid(airlineValidationVO.getAirlineIdentifier());
					    		
				    			residitRestrictonVO.setCarrierName(residitRestrictonSetUpForm.getCarrierName()[index]);
				    			if(residitRestrictonSetUpForm.getPaCode()[index]!=null &&  !residitRestrictonSetUpForm.getPaCode()[index].equals(BLANK)){
				    				residitRestrictonVO.setPaCode(residitRestrictonSetUpForm.getPaCode()[index].toUpperCase().trim());
				    				PostalAdministrationVO paVO=null;
				    		    	
				    		    	try{
				    		    		paVO = new MailTrackingDefaultsDelegate().findPACode(logonAttributes.getCompanyCode(),residitRestrictonVO.getPaCode());
				    		    	}
				    		    	catch(BusinessDelegateException businessDelegateException){
				    		    		errors = handleDelegateException(businessDelegateException);
				    		    	}
				    		    	if (paVO==null ) {
				    		    		
				    	    			Object[] obj = {residitRestrictonVO.getPaCode()};
				    	    			errors.add(new ErrorVO(INVALID_PACOD,obj));				    	    			
				    	    		}
				    			}
				    			else
				    				residitRestrictonVO.setPaCode(MailConstantsVO.POACOD_ALL);
							   if("N".equals(oprflags[index])){
								   residitRestrictonVO.setOperationFlag(null);
							   }else{
								   residitRestrictonVO.setOperationFlag(oprflags[index]);
							   }
							   newResiditRestrictonVOs.add(residitRestrictonVO);
						   }
					   }
					   count++;
				   }
				}
			}*/
		}
    	log.log(Log.FINE, "Updated newResiditRestrictonVOs------------> ",
				newResiditRestrictonVOs);
		log.exiting("SaveCommand","updatePartnerCarrierVOs");
    	
    	return (ArrayList<ResiditRestrictonVO>)newResiditRestrictonVOs;    	
    }
    
	/**
     * Method to check duplicate value in partnerCarrierVOs
     * @param partnerCarrierVOs
     * @return
     */
    
    private Collection<ErrorVO> chkDuplicate(
					    		ArrayList<ResiditRestrictonVO> residitRestrictonVOs){
    	
    	log.entering("SaveCommand","chkDuplicate");
    	
    	Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		ErrorVO error = null;
		
		if(residitRestrictonVOs != null && residitRestrictonVOs.size()>0){
			int index = 0;
			String flag = "Y";
			HashSet<String> obj = new HashSet<String>();
			String duplicateRec="";
			for(ResiditRestrictonVO residitRestrictonVO:residitRestrictonVOs){
				index = 0;
				String carrierCode = "";
				String airportCode="";				
				String paCode="";				
				if(!ResiditRestrictonVO.OPERATION_FLAG_DELETE.equals(residitRestrictonVO.getOperationFlag())){
					carrierCode = residitRestrictonVO.getCarrierCode();
					airportCode=residitRestrictonVO.getAirportCode();
					paCode=residitRestrictonVO.getPaCode();
					
					for(ResiditRestrictonVO chkVO:residitRestrictonVOs){
						if(!ResiditRestrictonVO.OPERATION_FLAG_DELETE.equals(chkVO.getOperationFlag())){
							
							if(chkVO.getCarrierCode().equals(carrierCode) && chkVO.getAirportCode().equals(airportCode)
									&&chkVO.getPaCode().equals(paCode)){
								index++;
								duplicateRec=new StringBuilder().append(
										carrierCode).append("-").append(airportCode).append("-")
										.append(paCode).toString();
							}
						}
					}
				}
				
				if(index>1){
					log.log(Log.FINE, "**duplicate present*** index =", index);
					obj.add(carrierCode);
					flag = "N";
				}
				
			}
			Object[] destinationObject={duplicateRec};
			if(("N").equals(flag)) {	
				error = new ErrorVO(DUPLICATE_CODE,destinationObject);
				errors.add(error);
			}
		}
    	
    	log.exiting("SaveCommand","chkDuplicate");
    	
    	return errors;
    	
    }
    
	

}
