/*
 * ListCommand.java Created on Mar 7, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.defaults.mailproration;

import static com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants.SCREEN_STATUS_DETAIL;
import static com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD;
import static com.ibsplc.xibase.server.framework.vo.ErrorDisplayType.ERROR;

import java.util.ArrayList;
import java.util.Collection;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.ProrationDetailsVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.ProrationFilterVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.mra.MailTrackingMRADelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.airlinebilling.defaults.ListInterlineBillingEntriesSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.MailProrationSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.MailProrationForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author Ruby Abraham
 * Command class for listing 
 *
 * Revision History
 *
 * Version      Date           Author          		    Description
 *
 *  0.1         Mar 7, 2007  Ruby Abraham	 		Initial draft
 */
public class ListCommand extends BaseCommand {
	/**
	 * Logger and the file name
	 */
	
	private Log log = LogFactory.getLogger("MAILTRACKING MRA DEFAULTS");
	
	
	private static final String CLASS_NAME = "ListCommand";
	
	private static final String MODULE_NAME = "mailtracking.mra.defaults";
	
	private static final String SCREEN_ID = "mailtracking.mra.defaults.mailproration";
	private static final String MODULE_AIRLINE = "mailtracking.mra.airlinebilling";

	private static final String AIRLINE_SCREEN_ID = "mailtracking.mra.airlinebilling.defaults.listinterlinebillingentries";
	
	private static final String LIST_SUCCESS = "list_success";
	
	private static final String LIST_FAILURE = "list_failure";
		
	
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
		ListInterlineBillingEntriesSession billingSession = (ListInterlineBillingEntriesSession) getScreenSession(
    			MODULE_AIRLINE, AIRLINE_SCREEN_ID);
		MailProrationForm mailProrationForm=(MailProrationForm)invocationContext.screenModel;
		
		
		if(invocationContext.getErrors() != null &&
				invocationContext.getErrors().size()>0){
			mailProrationForm.setScreenStatusFlag(SCREEN_STATUS_SCREENLOAD);
			mailProrationSession.removeProrationVOs();
			invocationContext.target = LIST_FAILURE;
			
		}
		else{							
							
				ProrationFilterVO prorationFilterVO = mailProrationSession.getProrationFilterVO();	
				if ("fromInterLineBilling".equals(billingSession.getFromScreen())){
					mailProrationForm.setConsigneeDocNo(prorationFilterVO.getConsigneeDocumentNumber());
					mailProrationForm.setDsn(prorationFilterVO.getDespatchSerialNumber());
					
				}
				log.log(Log.FINE, "ProrationFilterVO", prorationFilterVO);
				Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		        
		        Collection<ProrationDetailsVO> prorationDetails = null;
		        
				if(errors != null && errors.size() > 0){
					log.log(Log.FINE,"!!!!!!!inside errors!= null");
				}
				else{
					log.log(Log.FINE,"!!!inside errors== null");					 
			            
			    	try{
			    		MailTrackingMRADelegate mailTrackingMRADelegate = new MailTrackingMRADelegate();
			    		
			    		prorationDetails = mailTrackingMRADelegate.displayProrationDetails(prorationFilterVO);
						log.log(Log.FINE, "PRORATIONVOs from Server is----->",
								prorationDetails);
									
					}catch(BusinessDelegateException businessDelegateException){
				    		log.log(Log.FINE,"inside try...caught businessDelegateException");
				        	businessDelegateException.getMessage();
				        	handleDelegateException(businessDelegateException);
					}  		
						
					if(prorationDetails == null ||prorationDetails.size() == 0){
							log.log(Log.FINE,"!!!inside resultList== null");
							
							ErrorVO errorVO = new ErrorVO(
									"mailtracking.mra.defaults.mailproration.msg.err.noprorationdetails");
							errorVO.setErrorDisplayType(ERROR);
							errors.add(errorVO);						
							mailProrationSession.removeProrationVOs();
							mailProrationForm.setScreenStatusFlag(SCREEN_STATUS_SCREENLOAD);
								
					}
					if(errors != null && errors.size() > 0){
						log.log(Log.FINE,"!!!inside errors!= null");
						invocationContext.addAllError(errors);					
						invocationContext.target = LIST_FAILURE;
					}else{
						log.log(Log.FINE,"!!!inside resultList!= null");						
						mailProrationSession.setProrationVOs((ArrayList<ProrationDetailsVO>)prorationDetails);
						
						ProrationDetailsVO  prorationDetailsVO = mailProrationSession.getProrationVOs().get(0);
						log.log(Log.FINE,
								"The Proration Details VO----------->",
								prorationDetailsVO);
						mailProrationForm.setOrgExgOffice(prorationDetailsVO.getOriginExchangeOffice());
						mailProrationForm.setDestExgOffice(prorationDetailsVO.getDestinationExchangeOffice());
						mailProrationForm.setPostalAuthorityCode(prorationDetailsVO.getPostalAuthorityCode());
						mailProrationForm.setPostalAuthorityName(prorationDetailsVO.getPostalAuthorityName());
						mailProrationForm.setMailCategorycode(prorationDetailsVO.getMailCategoryCode());
						mailProrationForm.setMailSubclass(prorationDetailsVO.getMailSubclass());
						
						mailProrationForm.setScreenStatusFlag(SCREEN_STATUS_DETAIL);	
						invocationContext.target = LIST_SUCCESS;		       
					}					
			
			}
		}
				log.exiting(CLASS_NAME, "execute");
	}
}
