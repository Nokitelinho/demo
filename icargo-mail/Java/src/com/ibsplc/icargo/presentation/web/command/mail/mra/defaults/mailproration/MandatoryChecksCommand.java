/*
 * MandatoryChecksCommand.java Created on Mar 7,2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services(P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.defaults.mailproration;


import static com.ibsplc.xibase.server.framework.vo.ErrorDisplayType.ERROR;
import java.util.ArrayList;
import java.util.Collection;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.ProrationFilterVO;
import com.ibsplc.icargo.business.shared.airline.vo.AirlineValidationVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.airline.AirlineDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.MailProrationSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.MailProrationForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author Ruby  Abraham
 * Command class for checking for mandatory fields.
 *
 * Revision History
 *
 * Version      Date           Author          		    Description
 *
 *  0.1        Mar 7,2007   Ruby  Abraham 		        Initial draft
 */
public class MandatoryChecksCommand extends BaseCommand {
	/**
	 * Logger and the file name
	 */
	
	private Log log = LogFactory.getLogger("MAILTRACKING MRA DEFAULTS");
	
	private static final String CLASS_NAME = "MandatoryChecksCommand";
	
	private static final String MODULE_NAME = "mailtracking.mra.defaults";
	
	private static final String SCREEN_ID = "mailtracking.mra.defaults.mailproration";
	
	/**
	 * Execute method
	 *
	 * @param invocationContext InvocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
											throws CommandInvocationException {
		log.entering(CLASS_NAME, "execute");
		
		MailProrationSession  mailProrationSession = 
			(MailProrationSession)getScreenSession(MODULE_NAME, SCREEN_ID);
		
		
		MailProrationForm mailProrationForm=(MailProrationForm)invocationContext.screenModel;
			
		Collection<ErrorVO> errors=null;
		
		ProrationFilterVO prorationFilterVO = mailProrationSession.getProrationFilterVO();
		
		errors = validateForm(mailProrationForm,mailProrationSession,prorationFilterVO);
		if(errors != null && errors.size()>0){
			invocationContext.addAllError(errors);				
		}
		
		
		errors = validateFlight(mailProrationForm,mailProrationSession,prorationFilterVO);
		if(errors != null && errors.size()>0){
			invocationContext.addAllError(errors);				
		}
		
		
		log.exiting(CLASS_NAME, "execute");
	}
	
	/**
	 * 
	 * @param mailProrationSession
	 * @param prorationFilterVO
	 * @return Collection<ErrorVO>
	 */
	private Collection<ErrorVO> validateForm(MailProrationForm mailProrationForm,MailProrationSession  mailProrationSession,ProrationFilterVO prorationFilterVO){
	
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		log.entering("MandatoryChecksCommand","validateForm");
		ErrorVO error = null;						
		
		if( (mailProrationForm.getDsn() == null) ||
				mailProrationForm.getDsn().trim().length() == 0){
			error = new ErrorVO("mailtracking.mra.defaults.mailproration.msg.err.dsn.mandatory");
			error.setErrorDisplayType(ERROR);
			errors.add(error);			
		}				

		log.exiting("MandatoryChecksCommand","validateForm");
		return errors;
	}
	
	/**
	 * 
	 * @param mailProrationForm
	 * @param mailProrationSession
	 * @param prorationFilterVO
	 * @return
	 */
	private Collection<ErrorVO> validateFlight(MailProrationForm mailProrationForm,
								MailProrationSession  mailProrationSession,ProrationFilterVO prorationFilterVO){
		
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		
		ApplicationSessionImpl applicationSession = getApplicationSession();
        LogonAttributes logonAttributes  =  applicationSession.getLogonVO();
        String companyCode=logonAttributes.getCompanyCode().toUpperCase();
        
        int flightCarrierId =0;
        
		if(mailProrationForm.getFlightCarrierCode() != null &&
				mailProrationForm.getFlightCarrierCode().trim().length() > 0){
			AirlineValidationVO airlineValidationVO = null;
		    AirlineDelegate airlineDelegate = new AirlineDelegate();
		  try{
		    	airlineValidationVO = airlineDelegate.validateAlphaCode(companyCode,mailProrationForm.getFlightCarrierCode().toUpperCase());
		    	
		    }catch(BusinessDelegateException businessDelegateException){
		    	log.log(Log.FINE,"inside updateAirlinecaught busDelegateExc");
		    	businessDelegateException.getMessage();
		    	handleDelegateException(businessDelegateException);
		    }
		    
		    if(airlineValidationVO == null){
		    	ErrorVO error = new ErrorVO("mailtracking.mra.defaults.mailproration.msg.err.invalidairline");
				error.setErrorDisplayType(ERROR);
				errors.add(error);
		    }
		    else{	
		    	flightCarrierId=airlineValidationVO.getAirlineIdentifier();
		       	prorationFilterVO.setFlightCarrierIdentifier(flightCarrierId);
		    }
		    
		}
		
		log.exiting("MandatoryChecksCommand","validateFlight");
		return errors;
		
}
	}