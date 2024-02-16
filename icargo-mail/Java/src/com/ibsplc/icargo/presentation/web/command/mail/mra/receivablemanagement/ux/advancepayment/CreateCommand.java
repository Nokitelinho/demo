/**
 *	Java file	: 	com.ibsplc.icargo.presentation.web.command.mail.mra.receivablemanagement.ux.advancepayment.CreateCommand.java
 *
 *	Created by	:	A-4809
 *	Created on	:	10-Nov-2021
 *
 *  Copyright 2021 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 *
 * 	This software is the proprietary information of Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
 * 	Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.receivablemanagement.ux.advancepayment;

import static com.ibsplc.xibase.server.framework.vo.ErrorDisplayType.ERROR;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.MRAConstantsVO;
import com.ibsplc.icargo.business.mail.mra.receivablemanagement.vo.PaymentBatchDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.PostalAdministrationDetailsVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.util.currency.CurrencyException;
import com.ibsplc.icargo.framework.util.currency.CurrencyHelper;
import com.ibsplc.icargo.framework.util.currency.Money;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.json.vo.ResponseVO;
import com.ibsplc.icargo.framework.web.spring.command.AbstractCommand;
import com.ibsplc.icargo.framework.web.spring.controller.ActionContext;
import com.ibsplc.icargo.presentation.delegate.mail.mra.MailTrackingMRADelegate;
import com.ibsplc.icargo.presentation.delegate.shared.currency.CurrencyDelegate;
import com.ibsplc.icargo.presentation.web.model.mail.mra.common.AddPaymentFilter;
import com.ibsplc.icargo.presentation.web.model.mail.mra.receivablemanagement.AdvancePaymentModel;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 *	Java file	: 	com.ibsplc.icargo.presentation.web.command.mail.mra.receivablemanagement.ux.advancepayment.CreateCommand.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-4809	:	10-Nov-2021	:	Draft
 */
public class CreateCommand extends AbstractCommand{
	private Log log = LogFactory.getLogger("Mail MRA AdvancePayment CreateCommand");
    private static final String STATUS_SUCCESS = "success";
	
	private static final String INVALID_CURCODE = "mail.mra.receivablemanagement.advancepaymentdetails.msg.err.invalidcurrencycode";
	private static final String DUPLICATE_RECORD = "mail.mra.receivablemanagement.advancepaymentdetails.msg.err.duplicateexists";
	private static final String DUPLICATE_EXISTS = "Duplicate Exists";
	private static final String ERR_LISTMANDATORYFILTERS = "mra.mra.receivablemanagement.advancepaymentdetails.msg.err.listfiltersmandatory";
	private static final String INVALID_PACODE ="mail.mra.receivablemanagement.advancepaymentdetails.msg.err.gpanotconfigured";
	/**
	 *	Overriding Method	:	@see com.ibsplc.icargo.framework.web.spring.command.AbstractCommand#execute(com.ibsplc.icargo.framework.web.spring.controller.ActionContext)
	 *	Added by 			: A-4809 on 10-Nov-2021
	 * 	Used for 	:
	 *	Parameters	:	@param arg0
	 *	Parameters	:	@throws BusinessDelegateException
	 *	Parameters	:	@throws CommandInvocationException 
	 */
	@Override
	public void execute(ActionContext actionContext) throws BusinessDelegateException, CommandInvocationException {
		this.log.entering("AdvancePayment", "CreateCommand");
		 LogonAttributes logonAttributes = getLogonAttribute();  
		 String companyCode = logonAttributes.getCompanyCode();
		 AdvancePaymentModel advancePaymentModel = (AdvancePaymentModel)actionContext.getScreenModel();
		 ResponseVO responseVO = new ResponseVO();
		 List<AdvancePaymentModel> results = new ArrayList<AdvancePaymentModel>();
		 PaymentBatchDetailsVO advancePaymentVO = new PaymentBatchDetailsVO();
         AddPaymentFilter details = advancePaymentModel.getAddPaymentFilter();
         Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
        
        results.add(advancePaymentModel);
 		 
         
         if(!(checkNull(details.getPoaCod())&& checkNull(details.getBatchID())&&  checkNull(details.getBatchCur()) && checkNull(details.getBatchDate())&& (details.getBatchAmt()!= 0 )) ) 
        		
        	  {
					 
        	 
        	    ErrorVO error =  new ErrorVO(ERR_LISTMANDATORYFILTERS);
						 error.setErrorDisplayType(ERROR);
						 actionContext.addError(error);
	  				   	responseVO.setStatus(STATUS_SUCCESS);
	  					actionContext.setResponseVO(responseVO);
	  					return;
						
				  }
			  
			  
			
		  
         
         
         
         
         PostalAdministrationDetailsVO postalAdministrationDetailsVO = new PostalAdministrationDetailsVO();
			
      			postalAdministrationDetailsVO.setCompanyCode(logonAttributes.getCompanyCode());
      			postalAdministrationDetailsVO.setPoaCode(details.getPoaCod());
      			postalAdministrationDetailsVO.setParCode("BLGINFO");
      			postalAdministrationDetailsVO.setValidFrom(new LocalDate(LocalDate.NO_STATION,Location.NONE,false).setDate(details.getBatchDate()));
      			
      			PostalAdministrationDetailsVO pADetailsVO = null;
      			try {
      				pADetailsVO = new MailTrackingMRADelegate().validatePoaDetailsForBilling(postalAdministrationDetailsVO);
      				 log.log(Log.INFO,"validatePoaDetailsForBilling---[pADetailsVO]-->>",
      								pADetailsVO);
      			} catch (BusinessDelegateException e) {
      				log.log(Log.SEVERE,"validatePoaDetailsForBilling---FAILED-->>");
      				e.getMessage();
      			}
          
      			if(pADetailsVO==null){
      				
      		        	actionContext.addError(new ErrorVO(INVALID_PACODE));		
      					return;
      		        }
					
         
        
      			Collection<ErrorVO> errs = validateCurCod(details.getBatchCur(),logonAttributes.getCompanyCode());
      			if (errs != null && errs.size() > 0) {
      			    ErrorVO error =  new ErrorVO(INVALID_CURCODE);
  					error.setErrorDisplayType(ErrorDisplayType.ERROR);				
  					actionContext.addError(error);
  				    responseVO.setStatus(STATUS_SUCCESS);
  					actionContext.setResponseVO(responseVO);
  					return;				
      				//return;
      			}
      			
      			
      			
         Money btchTotAmt = null;
         if (advancePaymentModel !=null)
         {
        	    try {
        	    	btchTotAmt = CurrencyHelper.getMoney(details.getBatchCur());
        	    	btchTotAmt.setAmount(details.getBatchAmt());
					} catch (CurrencyException e) {

					log.log(Log.FINE,"Inside CurrencyException.. ");
				}
        	     
				 advancePaymentVO.setBatchAmount(btchTotAmt);  
        		 advancePaymentVO.setBatchStatus(details.getBatchStatus());
        		 advancePaymentVO.setBatchID(details.getBatchID());
        		 advancePaymentVO.setCompanyCode(companyCode);
        		 advancePaymentVO.setPaCode(details.getPoaCod()); 
        		 advancePaymentVO.setRemarks(details.getRemarks());
        		 advancePaymentVO.setBatchCurrency(details.getBatchCur()); 
        		 advancePaymentVO.setBatchStatus(MRAConstantsVO.BATCH_STATUS_NEW);
        		 advancePaymentVO.setSource(MRAConstantsVO.MAILSOURCE);
        		 advancePaymentVO.setBatchDate(new LocalDate(LocalDate.NO_STATION,Location.NONE,false).setDate(details.getBatchDate())); 
        		 advancePaymentVO.setLstUpdatedUser(logonAttributes.getUserId());
        		 advancePaymentVO.setLstUpdatedTime(new LocalDate(LocalDate.NO_STATION,Location.NONE,false));
		 
        		 
         }
        
         try{
 			new MailTrackingMRADelegate().saveAdvancePaymentDetails(advancePaymentVO);

 		}
 		catch (BusinessDelegateException businessDelegateException) {
 			errors=handleDelegateException(businessDelegateException);

 		}
         
         
        
         
         if (errors != null && errors.size() > 0) {
        	 for(ErrorVO e: errors){
        		if( DUPLICATE_EXISTS.equals(e.getErrorCode()))
        		{
        			ErrorVO error =  new ErrorVO(DUPLICATE_RECORD);
  					error.setErrorDisplayType(ErrorDisplayType.ERROR);				
  					actionContext.addError(error);
  				    responseVO.setStatus(STATUS_SUCCESS);
  					actionContext.setResponseVO(responseVO);
  					return;					
        		}
        	 }
				actionContext.addError((ErrorVO) errors);
				return;
			}	
			
			
			
         
         
		responseVO.setResults(results);
		responseVO.setStatus(STATUS_SUCCESS);
		
		
		actionContext.setResponseVO(responseVO);	      		
		this.log.exiting("AdvancePayment", "CreateCommand");
	}
	private boolean checkNull(String filter) {
		Boolean check=false;
		if(filter != null && filter.length() > 0)
		{
			check=true;
			return check;
		}
		
			return check;	
		
		
	}
	/**
	 * 
	 * @param batchCur
	 * @param companyCode
	 * @return
	 */
	private Collection<ErrorVO> validateCurCod(String batchCur, String companyCode) {
		try {
			new CurrencyDelegate().validateCurrency(companyCode, batchCur
					.toUpperCase());
		} catch (BusinessDelegateException businessDelegateException) {
			log.log(Log.FINE, "getCurCode not valid....");
			businessDelegateException.getMessage();
			return handleDelegateException(businessDelegateException);
		}
		return null;
	}
	

}
