/**
 *	Java file	: 	com.ibsplc.icargo.presentation.web.command.mail.mra.receivablemanagement.ux.advancepayment.OkBatchCommand.java
 *
 *	Created by	:	A-4809
 *	Created on	:	11-Jan-2022
 *
 *  Copyright 2022 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 *
 * 	This software is the proprietary information of Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
 * 	Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.receivablemanagement.ux.advancepayment;

import static com.ibsplc.xibase.server.framework.vo.ErrorDisplayType.ERROR;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import com.ibsplc.icargo.business.mail.mra.receivablemanagement.vo.PaymentBatchDetailsVO;
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
 *	Java file	: 	com.ibsplc.icargo.presentation.web.command.mail.mra.receivablemanagement.ux.advancepayment.OkBatchCommand.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-4809	:	11-Jan-2022	:	Draft
 */
public class OkBatchCommand extends AbstractCommand{
	private Log log = LogFactory.getLogger("Mail MRA AdvancePayment CreateCommand");
    private static final String STATUS_SUCCESS = "success";
    
    private static final String SCREEN_NAME="AdvancePayment";
    private static final String CLASS_NAME="OkBatchCommand";
    private static final String ERR_LISTMANDATORYFILTERS = "mra.mra.receivablemanagement.advancepaymentdetails.msg.err.listfiltersmandatory";
    private static final String INVALID_CURCODE = "mail.mra.receivablemanagement.advancepaymentdetails.msg.err.invalidcurrencycode";

	/**
	 *	Overriding Method	:	@see com.ibsplc.icargo.framework.web.spring.command.AbstractCommand#execute(com.ibsplc.icargo.framework.web.spring.controller.ActionContext)
	 *	Added by 			: A-4809 on 11-Jan-2022
	 * 	Used for 	:
	 *	Parameters	:	@param arg0
	 *	Parameters	:	@throws BusinessDelegateException
	 *	Parameters	:	@throws CommandInvocationException 
	 */
	@Override
	public void execute(ActionContext actionContext) throws BusinessDelegateException, CommandInvocationException {
		this.log.entering(SCREEN_NAME,CLASS_NAME);
		 LogonAttributes logonAttributes = getLogonAttribute();  
		 String companyCode = logonAttributes.getCompanyCode();
		 AdvancePaymentModel advancePaymentModel = (AdvancePaymentModel)actionContext.getScreenModel();
		 AddPaymentFilter details = advancePaymentModel.getAddPaymentFilter();
		 PaymentBatchDetailsVO batchVO = new PaymentBatchDetailsVO();
		 ResponseVO responseVO = new ResponseVO();
		 List<AdvancePaymentModel> results = new ArrayList<>();
		 if((details.getBatchAmt()== 0.0)&&isNullAndEmpty(details.getBatchCur())) {
     	    ErrorVO error =  new ErrorVO(ERR_LISTMANDATORYFILTERS);
						error.setErrorDisplayType(ERROR);
						actionContext.addError(error);
	  				   	responseVO.setStatus(STATUS_SUCCESS);
	  					actionContext.setResponseVO(responseVO);
	  					return;
		 }
		 Collection<ErrorVO> errors = validateCurrencyCod(details.getBatchCur(),companyCode);
			if (errors != null && !errors.isEmpty()) {
  			    ErrorVO error =  new ErrorVO(INVALID_CURCODE);
					error.setErrorDisplayType(ErrorDisplayType.ERROR);				
					actionContext.addError(error);
				    responseVO.setStatus(STATUS_SUCCESS);
					actionContext.setResponseVO(responseVO);
					return;				
  			}
			Money batchAmount =null;
			try {
				 batchAmount =  CurrencyHelper.getMoney(details.getBatchCur());
				 batchAmount.setAmount(details.getBatchAmt());
			} catch (CurrencyException e) {
				log.log(Log.FINE,"Inside CurrencyException.. ");
			}
			
			batchVO.setBatchAmount(batchAmount);  
			batchVO.setBatchID(details.getBatchID());
			batchVO.setCompanyCode(companyCode);
			batchVO.setPaCode(details.getPoaCod()); 
			batchVO.setRemarks(details.getRemarks());
			batchVO.setBatchCurrency(details.getBatchCur()); 
			batchVO.setBatchDate(new LocalDate(LocalDate.NO_STATION,Location.NONE,false).setDate(details.getBatchDate())); 
			batchVO.setLstUpdatedUser(logonAttributes.getUserId());
			batchVO.setLstUpdatedTime(new LocalDate(LocalDate.NO_STATION,Location.NONE,false));
			
			new MailTrackingMRADelegate().updateBatchAmount(batchVO);
		 
		 results.add(advancePaymentModel); 
		 responseVO.setResults(results);
		 responseVO.setStatus(STATUS_SUCCESS);
		 actionContext.setResponseVO(responseVO);	
		this.log.exiting(SCREEN_NAME,CLASS_NAME);
		
	}
	
	/**
	 * 	Method		:	OkBatchCommand.isNullAndEmpty
	 *	Added by 	:	A-4809 on 11-Jan-2022
	 * 	Used for 	:
	 *	Parameters	:	@param s
	 *	Parameters	:	@return 
	 *	Return type	: 	boolean
	 */
	  private boolean isNullAndEmpty(String s) {
	        return Objects.isNull(s)|| s.trim().isEmpty();
	    }
	  
	  /**
	   * 	Method		:	OkBatchCommand.validateCurCod
	   *	Added by 	:	A-4809 on 11-Jan-2022
	   * 	Used for 	:
	   *	Parameters	:	@param batchCur
	   *	Parameters	:	@param companyCode
	   *	Parameters	:	@return 
	   *	Return type	: 	Collection<ErrorVO>
	   */
		private Collection<ErrorVO> validateCurrencyCod(String batchCur, String companyCode) {
			try {
				new CurrencyDelegate().validateCurrency(companyCode, batchCur.toUpperCase());
			} catch (BusinessDelegateException businessDelegateException) {
				log.log(Log.FINE, "getCurCode not valid....");
				businessDelegateException.getMessage();
				return handleDelegateException(businessDelegateException);
			}
			return null;
		}

}
