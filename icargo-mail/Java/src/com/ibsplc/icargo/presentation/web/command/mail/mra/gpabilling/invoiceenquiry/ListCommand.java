
/*
 * ListCommand.java Created on July 7, 2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.gpabilling.invoiceenquiry;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.CN51SummaryVO;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.CN66DetailsVO;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.GpaBillingInvoiceEnquiryFilterVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.mra.MailTrackingMRADelegate;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.gpabilling.GPABillingInvoiceEnquirySession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.gpabilling.GPABillingInvoiceEnquiryForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-3434
 * 
 *
 */
public class ListCommand extends BaseCommand {

	private static final String MODULE = "mailtracking.mra.gpabilling";

	private static final String SCREENID = "mailtracking.mra.gpabilling.gpabillinginvoiceenquiry";

	private static final String LIST_SUCCESS = "list_success";

	private static final String LIST_FAILURE = "list_failure";

	private static final String CLASS_NAME = "ListCommand";

	private static final String KEY_NO_RESULTS_FOUND = "mailtracking.mra.gpabilling.cn51cn66.noresultsfound";

	private static final String KEY_NO_INVNUM = "mailtracking.mra.gpabilling.cn51cn66.noinvnum";
	private static final String STATUS_ONETIME = "mra.gpabilling.invoicestatus";
	
	

	private Log log = LogFactory.getLogger("MRA_GPABILLING");

	/**
	 * execute method
	 *
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
	throws CommandInvocationException {

		log.entering(CLASS_NAME, "execute");
		double totalBilledAmt=0.0;
		GPABillingInvoiceEnquiryForm form =(GPABillingInvoiceEnquiryForm)invocationContext.screenModel;
		GPABillingInvoiceEnquirySession session = (GPABillingInvoiceEnquirySession)getScreenSession(
				MODULE, SCREENID);
		/*
		 * clearing all session variables
		 */
		session.setCN51SummaryVO(null);
		session.setCN66DetailsVO(null);
		session.setCN66VOs(null);
		/*
		 * clearing all session variables ends 
		 */
		ApplicationSessionImpl applicationSessionImpl = getApplicationSession();
		LogonAttributes logonAttributes = applicationSessionImpl.getLogonVO();
		String companyCode = logonAttributes.getCompanyCode();
		Map<String, String> billingStatusMap = new HashMap<String, String>();
		SharedDefaultsDelegate sharedDefaultsDelegate=new SharedDefaultsDelegate();
		
		Map<String, Collection<OneTimeVO>> oneTimeValues = null;
 		Collection<String> parameterTypes = new ArrayList<String>();
 		parameterTypes.add("mra.gpabilling.billingstatus");
 		parameterTypes.add("mra.gpabilling.invoicestatus");
 	 	
 		try {
 			oneTimeValues =  sharedDefaultsDelegate.findOneTimeValues(
 					companyCode, parameterTypes);
 			log.log(Log.FINE, " One Time Values---->>", oneTimeValues);

 		} catch (BusinessDelegateException e) {
 			handleDelegateException(e);

 		}
 		session.setOneTimeMap((HashMap<String, Collection<OneTimeVO>>) oneTimeValues);
		if(session.getOneTimeMap()!=null){
			
		for (Collection<OneTimeVO> oneTimeVos : session.getOneTimeMap()
				.values()) {
			for (OneTimeVO oneTimeVo : oneTimeVos) {
				if (STATUS_ONETIME.equals(oneTimeVo.getFieldType())) {
					billingStatusMap.put(oneTimeVo.getFieldValue(),
							oneTimeVo.getFieldDescription());
				}
			}
		}
		}
		Collection<CN66DetailsVO> cN66DetailsVOs = null;
		GpaBillingInvoiceEnquiryFilterVO filterVO 	= new GpaBillingInvoiceEnquiryFilterVO();
		CN51SummaryVO cN51SummaryVO	= new CN51SummaryVO();
		Collection<ErrorVO> errors 	= new ArrayList<ErrorVO>();
		
		//session.removeCN66VOs();
		if(session.getGpaBillingInvoiceEnquiryFilterVO()==null){
			errors=validateForm(form,companyCode);
			if(errors!=null && errors.size()>0){			
				invocationContext.addAllError(errors);
				form.setDisableButton("Y");
				invocationContext.target=LIST_FAILURE;
				return;
			}
			else{
		//session.removeAllAttributes();		
		filterVO.setCompanyCode(companyCode);
		filterVO.setInvoiceNumber(form.getInvoiceNo());
			}
		if(form.getGpaCode()!=null&&form.getGpaName().trim().length()>0){
		filterVO.setGpaCode(form.getGpaCode());
		}
			
		
		session.setGpaBillingInvoiceEnquiryFilterVO(filterVO);
		}
		else{		
			log.log(Log.FINE, "else--->>", filterVO);
			filterVO=session.getGpaBillingInvoiceEnquiryFilterVO();
			form.setInvoiceNo(filterVO.getInvoiceNumber());
			
		}
		
		
		
		
		
		/*
		 * calling MailTrackingMRADelegate
		 */
		try {
			log.log(Log.FINE,"Inside try : Calling findGpaBillingInvoiceEnquiryDetails");
			 cN51SummaryVO = new MailTrackingMRADelegate().findGpaBillingInvoiceEnquiryDetails(filterVO);
			log.log(Log.FINE, "cN51SummaryVO from Server:--> ", cN51SummaryVO);

		} catch (BusinessDelegateException businessDelegateException) {
			handleDelegateException(businessDelegateException);
			log.log(Log.FINE, "caught");
		}
		if(cN51SummaryVO != null){
		cN51SummaryVO.setInvoiceStatus(billingStatusMap
				.get(cN51SummaryVO.getInvoiceStatus()));
		cN66DetailsVOs =cN51SummaryVO.getCn66details();
		if(cN66DetailsVOs != null && cN66DetailsVOs.size() > 0){
			for(CN66DetailsVO cN66DetailsVO:cN66DetailsVOs){
				if(cN66DetailsVO.getActualAmount()!=null){
					log.log(Log.INFO, "cN66DetailsVO.getActualAmount()==>>",
							cN66DetailsVO.getActualAmount());
					BigDecimal bigDecimalActualamount = new BigDecimal(cN66DetailsVO.getActualAmount().getAmount()).setScale(2,RoundingMode.HALF_UP);
	   				log.log(Log.INFO, "bigDecimalamount.doubleValuein==>>)",
							bigDecimalActualamount.doubleValue());
					totalBilledAmt=totalBilledAmt+(bigDecimalActualamount.doubleValue());
				}
			}
		}
		form.setFrmDate(cN51SummaryVO.getBillingPeriodFrom());
		form.setToDate(cN51SummaryVO.getBillingPeriodTo());
		if(cN51SummaryVO.getBilledDate()!=null){
		form.setInvoiceDate(cN51SummaryVO.getBilledDate().toDisplayFormat());
		}
		form.setInvoiceStatus(cN51SummaryVO.getInvoiceStatus());
		form.setCurrency(cN51SummaryVO.getContractCurrencyCode());
		form.setTotalAmount(totalBilledAmt);
		
		}
		if(cN66DetailsVOs != null && cN66DetailsVOs.size() > 0){

			/**
			 * @author a-3447 for int bug fix starts 
			 */
			
			if(("Finalised".equals(cN51SummaryVO.getInvoiceStatus())||
					("Direct-Finalised".equals(cN51SummaryVO.getInvoiceStatus())))){ 
				log.log(Log.FINE, " errorss");
				form.setDisableButton("Y");
    			
    		}
			else{
				form.setDisableButton("N");
			}
			/**
			 * @author a-3447 for int bug fix ends 
			 */
    		
    		form.setShowPopup("true");
			session.setCN66VOs(cN66DetailsVOs);
			session.setCN51SummaryVO(cN51SummaryVO);
			form.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_DETAIL);
    		invocationContext.target = LIST_SUCCESS;

    	}
		else{

    		errors.add(new ErrorVO(KEY_NO_RESULTS_FOUND));
    		invocationContext.addAllError(errors);
    		form.setDisableButton("Y");
			invocationContext.target = LIST_FAILURE;

    	}

	}
	
	  /**
     * Validating the Filter parameters
     * @param maintainCCCollectorForm
     * @param companyCode
     * @return
     */
    private Collection<ErrorVO> validateForm(
    		GPABillingInvoiceEnquiryForm form , String companyCode){
    	
    	log.entering(CLASS_NAME,"validateForm");
    	Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		ErrorVO cn51ErrorVO = null;
		
		String invNumber = form.getInvoiceNo();
		
		
		if(invNumber == null ||("").equals(invNumber.trim())){
			cn51ErrorVO= new ErrorVO(KEY_NO_INVNUM);
				if(errors == null){
					errors = new ArrayList<ErrorVO>();
				}
				errors.add(cn51ErrorVO);			
		}  
		log.exiting(CLASS_NAME,"validateForm");
        return errors;
    }
    
	}	

