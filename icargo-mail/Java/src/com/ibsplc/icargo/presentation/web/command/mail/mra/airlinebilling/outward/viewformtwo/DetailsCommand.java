/*
 * DetailsCommand.java Created on July 22, 2008
 *
 * Copyright 2006 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.airlinebilling.outward.viewformtwo;


import java.util.ArrayList;
import java.util.Collection;
import com.ibsplc.icargo.business.cra.defaults.masters.vo.IATACalendarFilterVO;
import com.ibsplc.icargo.business.cra.defaults.masters.vo.IATACalendarVO;
import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.AirlineForBillingVO;
import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.InterlineFilterVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.mra.MailTrackingMRADelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.airlinebilling.outward.ViewFormTwoSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.airlinebilling.outward.ViewMailFormTwoForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 *
 * @author A-3434
 *
 */


public class DetailsCommand extends BaseCommand {
	/**
	 * Logger and the file name
	 */
	private  Log log = LogFactory.getLogger("MRA AIRLINEBILLING");

	private static final String MODULE_NAME = "mra.airlinebilling";

	private static final String CLASS_NAME = "DetailsCommand";

	private static final String SCREENID = "mailtracking.mra.airlinebilling.outward.viewform2";

	private static final String LIST_SUCCESS = "list_success";
	private static final String LIST_FAILURE = "list_failure";
	private static final String CLRPRD_INVALID= "mailtracking.mra.airlinebilling.outward.clrprdinvalid";
	private static final String CLRPRD_MANDATORY= "mra.airlinebilling.outward.viewformtwo.mandatoryclearanceperiod";
	private static final String ERROR_KEY_NORESUTLS_FOUND=
		"mra.airlinebilling.outward.viewformtwo.noresultfound";

	/**
	 * Execute method
	 *
	 * @param invocationContext InvocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
											throws CommandInvocationException {
		log.entering(CLASS_NAME, "execute");

		ApplicationSessionImpl applicationSessionImpl = getApplicationSession();
		LogonAttributes logonAttributes = applicationSessionImpl.getLogonVO();
		
		ViewMailFormTwoForm viewForm2Form =
			(ViewMailFormTwoForm) invocationContext.screenModel;
		ViewFormTwoSession viewForm2Session = (ViewFormTwoSession)getScreenSession(MODULE_NAME, SCREENID);
		
		MailTrackingMRADelegate mailTrackingMRADelegate =new MailTrackingMRADelegate();
		
		viewForm2Session.removeAirlineForBillingVOs();
		// Clearance Period From the Screen
		String clearancePeriod = viewForm2Form.getClearancePeriod();
		
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>(); 
		if(("").equals(viewForm2Form.getClearancePeriod()) &&
				(viewForm2Form.getClearancePeriod().trim().length()==0)){
			
			ErrorVO errorVo = new ErrorVO(CLRPRD_MANDATORY);
			invocationContext.addError(errorVo);
			invocationContext.target = LIST_FAILURE;
			return;
		}
		//else{
			 
		    
            IATACalendarFilterVO iatacalendarfiltervo = new IATACalendarFilterVO();
            iatacalendarfiltervo.setCompanyCode(logonAttributes.getCompanyCode());
            iatacalendarfiltervo.setClearancePeriod(viewForm2Form.getClearancePeriod());
            IATACalendarVO  iatacalendarvo = null;
            try{
			
			iatacalendarvo = mailTrackingMRADelegate.validateClearancePeriod(iatacalendarfiltervo);
			  log.log(Log.INFO, "iatacalendarvo obtained", iatacalendarvo);
            }catch(BusinessDelegateException businessDelegateException){
				 errors = handleDelegateException(businessDelegateException);
            }
            
            if(iatacalendarvo == null ){
            	log.log(log.INFO,"iatacalendarvo null");
            	
				errors.add(new ErrorVO(CLRPRD_INVALID));
	    		invocationContext.addAllError(errors);
	    		
				invocationContext.target = LIST_FAILURE;
				return;
            }
            else{
            	log.log(log.INFO,"iatacalendarvo!=null");
            }
		//}
		// Collection for setting the  Billing VOs
		Collection<AirlineForBillingVO> airlineForBillingVos ;
		InterlineFilterVO interlineFilterVO = null;
		
		
			interlineFilterVO = new InterlineFilterVO();
			interlineFilterVO.setCompanyCode(
					getApplicationSession().getLogonVO().getCompanyCode());
			interlineFilterVO.setIsFormTwo(true);
			interlineFilterVO.setClearancePeriod(
					clearancePeriod);
			log.log(Log.FINE, "\n\nFilter Vo to the server", interlineFilterVO);
			try{
				airlineForBillingVos =
					mailTrackingMRADelegate.findFormTwoDetails( interlineFilterVO);
				log.log(Log.FINE,"Got the collection");
			}catch(BusinessDelegateException businessDelegateException){
					 errors = handleDelegateException(businessDelegateException);
					invocationContext.addAllError(errors);
					invocationContext.target = LIST_FAILURE;
					return;
			}
			log.log(Log.FINE, "airline For Billing VOs from the server",
					airlineForBillingVos);
			if(airlineForBillingVos != null && airlineForBillingVos.size() > 0 ){

				viewForm2Session.setAirlineForBillingVOs(airlineForBillingVos);
				viewForm2Form.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_DETAIL);
				invocationContext.target = LIST_SUCCESS;
			}	else {
				ErrorVO errorVo = new ErrorVO(ERROR_KEY_NORESUTLS_FOUND);
				invocationContext.addError(errorVo);
				viewForm2Form.setScreenStatusFlag(
		  				ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
				invocationContext.target = LIST_FAILURE;
				return;
			}
		

		


		log.exiting(CLASS_NAME, "execute");
	}

	
}
