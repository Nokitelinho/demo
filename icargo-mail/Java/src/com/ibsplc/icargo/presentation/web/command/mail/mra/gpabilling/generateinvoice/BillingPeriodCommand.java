/*
 * BillingPeriodCommand.java Created on July 02, 2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.presentation.web.command.mail.mra.gpabilling.generateinvoice;

import java.util.ArrayList;
import java.util.Collection;
import java.util.StringTokenizer;



import com.ibsplc.icargo.business.mail.operations.vo.PostalAdministrationVO;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.GenerateInvoiceFilterVO;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.GenerateInvoiceVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.mra.MailTrackingMRADelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.gpabilling.GenerateInvoiceSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.gpabilling.GenerateGPABillingInvoiceForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;


/**
 * @author A-3222
 *
 */
public class BillingPeriodCommand extends BaseCommand {
	private Log log = LogFactory.getLogger("MRA GPABILLING");
	private static final String CLASS_NAME = "BillingPeriodCommand";
	private static final String ACTION_SUCCESS= "action_success";
	private static final String ACTION_FAILURE= "action_failure";

	private static final String MODULE_NAME = "mailtracking.mra.gpabilling";
	/**
	* Screen ID
	*/
	private static final String SCREENID = "mailtracking.mra.gpabilling.generateinvoice";

	private static final String GPACOD_MAND = "mailtracking.mra.gpabilling.generateinvoice.msg.err.gpacodemandatory";

	public void execute(InvocationContext invocationContext)
	throws CommandInvocationException {
		log.entering("BillingPeriodCommand", "execute");
		Collection<ErrorVO> errors =new ArrayList<ErrorVO>();
		GenerateGPABillingInvoiceForm form =
			(GenerateGPABillingInvoiceForm) invocationContext.screenModel;

		LogonAttributes logonAttributes = getApplicationSession().getLogonVO();
		GenerateInvoiceFilterVO generateInvoiceFilterVO=new GenerateInvoiceFilterVO() ;


		MailTrackingMRADelegate mailTrackingMRADelegate = new MailTrackingMRADelegate();

		try {

			
			generateInvoiceFilterVO.setCountryCode(form.getCountry().toUpperCase());
			generateInvoiceFilterVO.setCompanyCode(logonAttributes.getCompanyCode());
			log.log(Log.INFO, "the filter values are", generateInvoiceFilterVO);
			if ( form.getGpacode().trim().length() == 0) {
				log.log(Log.INFO,"mandatory ");
				errors.add(new ErrorVO(GPACOD_MAND));
				invocationContext.addAllError(errors);
				invocationContext.target = ACTION_FAILURE;
				log.exiting(CLASS_NAME, "execute");
				return;
			}
			else if(form.getGpacode()!=null &&(form.getGpacode()).trim().length()>0){
				/*
					* Validate the GpaCode  and obtain the GpaCode
				*/
				
				 errors = validateGpaCode(form,
							logonAttributes);
				 log.log(Log.FINE, "errors", errors);
				if(errors!=null&& errors.size()>0){
					invocationContext.addAllError(errors);
					invocationContext.target =ACTION_FAILURE;
					return;
					
				}
				
				
			}
			
			generateInvoiceFilterVO.setGpaCode(form.getGpacode().toUpperCase());
			
			Collection<String> billingPeriods = mailTrackingMRADelegate.
				findBillingPeriods(generateInvoiceFilterVO);

			log.log(Log.INFO, "the value at delegate is ", billingPeriods);
			LocalDate localdate = new LocalDate("***", Location.NONE, false);
			log.log(Log.INFO, "the system date  is ", localdate);
			String sysdate= localdate.toString().substring(2,11);
			log.log(Log.INFO, "the system date in string format is ", sysdate);
			String fromdate=null;
			String todate=null;
			String blngInd=null;
			GenerateInvoiceSession generateInvoiceSession=
				(GenerateInvoiceSession)getScreenSession(MODULE_NAME,SCREENID);
			Collection<GenerateInvoiceVO> generateInvoiceVOs=new ArrayList<GenerateInvoiceVO>();

			if(billingPeriods==null || billingPeriods.size()==0){
			generateInvoiceSession.setGenerateInvoiceVOs(null);
			log.log(Log.INFO,"no billingperiod exists error");
			errors.add(new ErrorVO("mailtracking.mra.gpabilling.generateinvoice.nobillingperiod"));
			invocationContext.addAllError(errors);
			invocationContext.target = ACTION_FAILURE;
			log.exiting(CLASS_NAME, "execute");
			return;
			}

			for(String billingPeriod:billingPeriods)
			{

				log.log(Log.INFO, "no billingperiod exists error",
						billingPeriod);
				GenerateInvoiceVO generateInvoiceVO = new GenerateInvoiceVO();
				StringTokenizer tokens = new StringTokenizer(billingPeriod,"$");

				String[] blgperiods = new String[3];
				int i = 0;
				while(tokens.hasMoreTokens()){
					blgperiods[i] =  tokens.nextToken();
					i++;
				}
				fromdate=blgperiods[0];
				log.log(Log.INFO, "fromdate..", blgperiods);
				todate=blgperiods[1];
				log.log(Log.INFO, "todate..", blgperiods);
				blngInd=blgperiods[2];
				log.log(Log.INFO, "blngInd..", blgperiods);
				generateInvoiceVO.setBillingPeriodFrom(new LocalDate(LocalDate.NO_STATION,Location.NONE, false).setDate(String.valueOf(fromdate.substring(0,11))));
				generateInvoiceVO.setBillingPeriodTo(new LocalDate(LocalDate.NO_STATION,Location.NONE, false).setDate(String.valueOf(todate.substring(0,11))));
				generateInvoiceVO.setBillingPeriodIndicator(String.valueOf(blngInd));
				log.log(Log.INFO, "vo display ", generateInvoiceVO);
				generateInvoiceVOs.add(generateInvoiceVO);
				log.log(Log.INFO, "collection display ", generateInvoiceVOs);

			}

			log.log(Log.INFO, "fromdate collection is ", generateInvoiceVOs);
			generateInvoiceSession.setGenerateInvoiceVOs(generateInvoiceVOs);
			log.log(Log.INFO, "from session is ", generateInvoiceSession.getGenerateInvoiceVOs());

		}
		catch(BusinessDelegateException businessDelegateException){

			log.log(Log.FINE,"inside  businessDelegateException");
	    	errors = handleDelegateException(businessDelegateException);
	    	invocationContext.addAllError(errors);
	    	invocationContext.target = ACTION_FAILURE;
	    	log.exiting(CLASS_NAME,"execute");
	    	return ;

		}

		invocationContext.target = ACTION_SUCCESS;
		log.exiting("BillingPeriodCommand", "execute");

	}
	/**
	 * Method to validate GpaCode
	 * @param GenerateGPABillingInvoiceForm
	 * @return Collection<ErrorVO>
	 */
	private Collection<ErrorVO> validateGpaCode(GenerateGPABillingInvoiceForm form,
			LogonAttributes logonAttributes) {
		
		String gpaCode=form.getGpacode();
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		if(gpaCode != null || ("".equals(gpaCode.trim()))){
			
//    	validate PA code
	  	log.log(Log.FINE, "Going To validate GPA code ...in command");
			try {
		  			PostalAdministrationVO postalAdministrationVO = new PostalAdministrationVO();
					postalAdministrationVO  = new MailTrackingMRADelegate().findPACode(
									logonAttributes.getCompanyCode(),gpaCode.toUpperCase());
					log.log(Log.FINE, "postalAdministrationVO",
							postalAdministrationVO);
					if(postalAdministrationVO == null) {
		  				Object[] obj = {gpaCode.toUpperCase()};
		  				errors.add(new ErrorVO("mailtracking.mra.gpabilling.gpacode.invalid",obj));
		  			}
		  	
			}catch (BusinessDelegateException businessDelegateException) {
				errors = handleDelegateException(businessDelegateException);
			}
		}
		return errors;
	}

}
