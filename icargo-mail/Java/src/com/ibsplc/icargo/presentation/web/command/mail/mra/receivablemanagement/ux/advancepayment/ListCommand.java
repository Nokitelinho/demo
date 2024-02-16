/**
 *	Java file	: 	com.ibsplc.icargo.presentation.web.command.mail.mra.receivablemanagement.ux.advancepayment.ListCommand.java
 *
 *	Created by	:	A-4809
 *	Created on	:	21-Oct-2021
 *
 *  Copyright 2021 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 *
 * 	This software is the proprietary information of Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
 * 	Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.receivablemanagement.ux.advancepayment;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.ibsplc.icargo.business.mail.mra.receivablemanagement.vo.PaymentBatchDetailsVO;
import com.ibsplc.icargo.business.mail.mra.receivablemanagement.vo.PaymentBatchFilterVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.json.PageResult;
import com.ibsplc.icargo.framework.web.json.vo.ResponseVO;
import com.ibsplc.icargo.framework.web.spring.command.AbstractCommand;
import com.ibsplc.icargo.framework.web.spring.controller.ActionContext;
import com.ibsplc.icargo.presentation.delegate.mail.mra.MailTrackingMRADelegate;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.model.mail.mra.common.MailMRAModelConverter;
import com.ibsplc.icargo.presentation.web.model.mail.mra.common.PaymentBatchDetails;
import com.ibsplc.icargo.presentation.web.model.mail.mra.common.PaymentBatchFilter;
import com.ibsplc.icargo.presentation.web.model.mail.mra.receivablemanagement.AdvancePaymentModel;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 *	Java file	: 	com.ibsplc.icargo.presentation.web.command.mail.mra.receivablemanagement.ux.advancepayment.ListCommand.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-4809	:	21-Oct-2021	:	Draft
 */
public class ListCommand extends AbstractCommand{
	
	private Log log = LogFactory.getLogger("Mail MRA AdvancePayment ListCommand");
    private static final String STATUS_SUCCESS = "success";
    private static final String NO_DATA_FOUND = "mail.mra.receivablemanagement.advancepayment.nodata";
    private static final String FROM_DATE_MANDATORY="mail.mra.receivablemanagement.advancepayment.fromdatemandatory";
    private static final String TO_DATE_MANDATORY="mail.mra.receivablemanagement.advancepayment.todatemandatory";
    private static final String GREATERDATE="mail.mra.receivablemanagement.advancepayment.greaterdate";
    private static final String KEY_BATCH_STATUS_ONETIME = "mail.mra.receivablemanagement.batchstatus";

	/**
	 *	Overriding Method	:	@see com.ibsplc.icargo.framework.web.spring.command.AbstractCommand#execute(com.ibsplc.icargo.framework.web.spring.controller.ActionContext)
	 *	Added by 			: A-4809 on 21-Oct-2021
	 * 	Used for 	:
	 *	Parameters	:	@param actionContext
	 *	Parameters	:	@throws BusinessDelegateException
	 *	Parameters	:	@throws CommandInvocationException 
	 */
	@Override
	public void execute(ActionContext actionContext) throws BusinessDelegateException, CommandInvocationException {
		this.log.entering("AdvancePayment", "ListCommand");
		 LogonAttributes logonAttributes = getLogonAttribute();  
		 String companyCode = logonAttributes.getCompanyCode();
		 AdvancePaymentModel advancePaymentModel = (AdvancePaymentModel)actionContext.getScreenModel();
		 SharedDefaultsDelegate sharedDefaultsDelegate = new SharedDefaultsDelegate();
		 ResponseVO responseVO = new ResponseVO();
		 List<AdvancePaymentModel> results = new ArrayList<AdvancePaymentModel>();
		 PaymentBatchFilter paymentBatchFilter = (PaymentBatchFilter)advancePaymentModel.getPaymentBatchFilter();
		 Page<PaymentBatchDetailsVO> paymentBatchDetailsVOs = null;
		 Map<String, Collection<OneTimeVO>> oneTimeValues = null;
		 oneTimeValues = sharedDefaultsDelegate.findOneTimeValues(companyCode,getOneTimeParameterTypes());
		 if(isNullAndEmpty(paymentBatchFilter.getFromDate())){
	        	actionContext.addError(new ErrorVO(FROM_DATE_MANDATORY));
	        	return;	
		 }
		if(isNullAndEmpty(paymentBatchFilter.getToDate())){
        	actionContext.addError(new ErrorVO(TO_DATE_MANDATORY));
        	return;	
		}
		LocalDate fromDate = new LocalDate(LocalDate.NO_STATION, Location.NONE, false);
		LocalDate toDate = new LocalDate(LocalDate.NO_STATION, Location.NONE, false);
		fromDate.setDate(paymentBatchFilter.getFromDate());
		toDate.setDate(paymentBatchFilter.getToDate());
        if (fromDate.isGreaterThan(toDate)){
        	actionContext.addError(new ErrorVO(GREATERDATE));
        	return;	
        }	 
		
        PaymentBatchFilterVO paymentBatchFilterVO = updateFilterVO(paymentBatchFilter,logonAttributes);
		int pageNumber = Integer.parseInt(paymentBatchFilter.getDisplayPage());
		paymentBatchFilterVO.setPageNumber(pageNumber);
        paymentBatchDetailsVOs = new MailTrackingMRADelegate().listPaymentBatchDetails(paymentBatchFilterVO);
        
        if(Objects.isNull(paymentBatchDetailsVOs)){ 
        	actionContext.addError(new ErrorVO(NO_DATA_FOUND));
        	return;
        }
        
        
        
        
        
        ArrayList<PaymentBatchDetails> batchDetails = MailMRAModelConverter.constructPaymentBatchDetails(paymentBatchDetailsVOs, oneTimeValues);
        PageResult<PaymentBatchDetails> paymentBatchDetails = new PageResult<PaymentBatchDetails>(paymentBatchDetailsVOs, batchDetails);
        advancePaymentModel.setPaymentBatchFilter(paymentBatchFilter);  
        advancePaymentModel.setPaymentBatchDetails(paymentBatchDetails);
        results.add(advancePaymentModel);
		responseVO.setResults(results);
		responseVO.setStatus(STATUS_SUCCESS);
		actionContext.setResponseVO(responseVO);	      		
		this.log.exiting("AdvancePayment", "ListCommand");
        
	}
		/**
	 * 	Method		:	ListCommand.updateFilterVO
	 *	Added by 	:	A-4809 on 11-Nov-2021
	 * 	Used for 	:
	 *	Parameters	:	@param paymentBatchFilter
	 *	Parameters	:	@param logonAttributes
	 *	Parameters	:	@return 
	 *	Return type	: 	PaymentBatchFilterVO
	 */
	private PaymentBatchFilterVO updateFilterVO(PaymentBatchFilter paymentBatchFilter,
			LogonAttributes logonAttributes) {
		PaymentBatchFilterVO filterVO = new PaymentBatchFilterVO();
		LocalDate fromDate = new LocalDate(LocalDate.NO_STATION, Location.NONE, false);
		LocalDate toDate = new LocalDate(LocalDate.NO_STATION, Location.NONE, false);
			fromDate.setDate(paymentBatchFilter.getFromDate().toUpperCase());
			toDate.setDate(paymentBatchFilter.getToDate().toUpperCase());
		filterVO.setCompanyCode(logonAttributes.getCompanyCode());
		filterVO.setBatchFrom(fromDate);
		filterVO.setBatchTo(toDate);
		filterVO.setPaCode(paymentBatchFilter.getPaCode());
		filterVO.setBatchStatus(paymentBatchFilter.getBatchStatus());
		filterVO.setTotalRecordCount(paymentBatchFilter.getTotalRecords());
		filterVO.setDefaultPageSize(Integer.parseInt(paymentBatchFilter.getDefaultPageSize()));
		return filterVO;
	}
		/**
		 * 	Method		:	ListCommand.isNullAndEmpty
		 *	Added by 	:	A-4809 on 21-Oct-2021
		 * 	Used for 	:
		 *	Parameters	:	@param s
		 *	Parameters	:	@return 
		 *	Return type	: 	boolean
		 */
	  private boolean isNullAndEmpty(String s) {
	        return Objects.isNull(s)|| s.trim().isEmpty();
	    }
	  /**
	   * 	Method		:	ListCommand.getOneTimeParameterTypes
	   *	Added by 	:	A-4809 on 11-Nov-2021
	   * 	Used for 	:
	   *	Parameters	:	@return 
	   *	Return type	: 	Collection<String>
	   */
		private Collection<String> getOneTimeParameterTypes() {
			Collection<String> parameterTypes = new ArrayList();
		    parameterTypes.add(KEY_BATCH_STATUS_ONETIME);
		    return parameterTypes;
	    }
}

