/**
 *	Java file	: 	com.ibsplc.icargo.presentation.web.command.mail.mra.gpabilling.ux.gpabillingenquiry.ChangeBillingStatusSaveCommand.java
 *
 *	Created by	:	A-4809
 *	Created on	:	Mar 14, 2019
 *
 *  Copyright 2019 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 *
 * 	This software is the proprietary information of Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
 * 	Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.gpabilling.ux.gpabillingenquiry;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List; 

import com.ibsplc.icargo.business.mail.mra.defaults.vo.DocumentBillingDetailsVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.MRAConstantsVO;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.CN66DetailsVO;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.GPABillingDetailsVO;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.GPABillingStatusVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.json.vo.ResponseVO;
import com.ibsplc.icargo.framework.web.spring.command.AbstractCommand;
import com.ibsplc.icargo.framework.web.spring.controller.ActionContext;
import com.ibsplc.icargo.presentation.delegate.mail.mra.MailTrackingMRADelegate;
import com.ibsplc.icargo.presentation.web.model.mail.mra.common.GPABillingEntryDetails;
import com.ibsplc.icargo.presentation.web.model.mail.mra.common.GPABillingEntryFilter;
import com.ibsplc.icargo.presentation.web.model.mail.mra.common.MailMRAModelConverter;
import com.ibsplc.icargo.presentation.web.model.mail.mra.gpabilling.GPABillingEnquiryModel;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;

/**
 *	Java file	: 	com.ibsplc.icargo.presentation.web.command.mail.mra.gpabilling.ux.gpabillingenquiry.ChangeBillingStatusSaveCommand.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-4809	:	Mar 14, 2019	:	Draft
 */
public class ChangeBillingStatusSaveCommand extends AbstractCommand{
	
	private static final String KEY_STATUS_SAME="mailtracking.mra.gpabilling.enteredsamestatus";
	private static final String STATUS_SUCCESS = "success";
	private static final String WITHDRAWN_CANT_CHNG_TO_OH="mailtracking.mra.defaults.changestatus.withdrawntooh";
	private static final String REMARKS_MANDATORY="mailtracking.mra.defaults.changestatus.remarksmandatory";
	private static final String WITHDRAWN="WITHDRAWN";

	@Override
	public void execute(ActionContext actionContext) throws BusinessDelegateException, CommandInvocationException {
		MailTrackingMRADelegate mailTrackingMRADelegate=new MailTrackingMRADelegate ();
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		 LogonAttributes logonAttributes = getLogonAttribute();  
		 ResponseVO responseVO = new ResponseVO();
		 List<GPABillingEnquiryModel> results = new ArrayList<GPABillingEnquiryModel>();
		 GPABillingEnquiryModel gpaBillingEnquiryModel = (GPABillingEnquiryModel)actionContext.getScreenModel();
		 GPABillingEntryFilter gpaBillingEntryFilter = (GPABillingEntryFilter)gpaBillingEnquiryModel.getGpaBillingEntryFilter();
		 Collection<GPABillingEntryDetails> selectedBilling = gpaBillingEnquiryModel.getSelectedBillingDetails();
		 Collection<DocumentBillingDetailsVO> selectedDetailsVOs = MailMRAModelConverter.constructDocumentBillingDetailsVOs(selectedBilling, logonAttributes);
		 String billingStatus = gpaBillingEnquiryModel.getBillingStatus();
		 String remarks = gpaBillingEnquiryModel.getRemarks();
		 if(remarks==null || remarks.isEmpty()){
	        	actionContext.addError(new ErrorVO(REMARKS_MANDATORY));
	        	return;	 
		 }
		 if(selectedDetailsVOs!=null && !selectedDetailsVOs.isEmpty()){
   			for(DocumentBillingDetailsVO documentBillingDetailsVO:selectedDetailsVOs){
   				documentBillingDetailsVO.setIntblgType(MRAConstantsVO.BLGTYPE_GPA); 
   			   if (MRAConstantsVO.ONHOLD.equalsIgnoreCase(billingStatus) && 
   					WITHDRAWN.equalsIgnoreCase(documentBillingDetailsVO.getBillingStatus())){
    	        	actionContext.addError(new ErrorVO(WITHDRAWN_CANT_CHNG_TO_OH));
    	        	return;	
   			   }   
   			   if(billingStatus!=null && !billingStatus.isEmpty()){
   				documentBillingDetailsVO.setBillingStatus(billingStatus);
   			   }
   			   if(remarks!=null && !remarks.isEmpty()){
   				documentBillingDetailsVO.setRemarks(remarks);
   			   }else{
   				documentBillingDetailsVO.setRemarks("");
   			   }
   			}
   			}
			try {
					mailTrackingMRADelegate.changeStatus(selectedDetailsVOs);;

			} catch (BusinessDelegateException e) {
				errors = handleDelegateException(e);
			}	
			gpaBillingEnquiryModel.setGpaBillingEntryFilter(gpaBillingEntryFilter);
			results.add(gpaBillingEnquiryModel);
			responseVO.setResults(results);
			responseVO.setStatus(STATUS_SUCCESS);
			actionContext.setResponseVO(responseVO);	
	}

}
