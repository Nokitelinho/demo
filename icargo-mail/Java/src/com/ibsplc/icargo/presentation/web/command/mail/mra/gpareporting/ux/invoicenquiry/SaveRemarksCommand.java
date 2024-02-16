/*
 * SaveRemarksCommand.java Created on Nov 20 2018
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.gpareporting.ux.invoicenquiry;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.mail.mra.gpareporting.vo.InvoicDetailsVO;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.json.vo.ResponseVO;
import com.ibsplc.icargo.framework.web.spring.command.AbstractCommand;
import com.ibsplc.icargo.framework.web.spring.controller.ActionContext;
import com.ibsplc.icargo.presentation.delegate.mail.mra.MailTrackingMRADelegate;
import com.ibsplc.icargo.presentation.web.model.mail.mra.common.InvoicDetails;
import com.ibsplc.icargo.presentation.web.model.mail.mra.common.MailMRAModelConverter;
import com.ibsplc.icargo.presentation.web.model.mail.mra.gpareporting.InvoicEnquiryModel;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-8464
 *
 */
public class SaveRemarksCommand extends AbstractCommand{

	
	 private Log log = LogFactory.getLogger("Mail MRA gpareporting invoic enquiry");
		
	 public void execute(ActionContext actionContext) throws BusinessDelegateException, CommandInvocationException {
			
			log.entering("SaveRemarksCommand","execute");
			InvoicEnquiryModel invoicEnquiryModel = (InvoicEnquiryModel) actionContext.getScreenModel();
			ResponseVO responseVO = new ResponseVO();
			ArrayList<ErrorVO> errors = new ArrayList<ErrorVO>();	
			
			if (invoicEnquiryModel != null && invoicEnquiryModel.getSelectedInvoicDetails()!= null) {
				
				log.log(Log.FINE, "invoicEnquiryModel.getSelectedInvoicDetail() not null");
				ArrayList<InvoicDetails> selectedInvoicDetails =  new ArrayList<InvoicDetails>(invoicEnquiryModel.getSelectedInvoicDetails());
				InvoicDetails selectedInvoicDetail = selectedInvoicDetails.get(0);
				InvoicDetailsVO invoicDetailsVO = MailMRAModelConverter.constructInvoicDetailVO(selectedInvoicDetail);
		        
				try {
					MailTrackingMRADelegate mailTrackingMRADelegate = new MailTrackingMRADelegate();
					mailTrackingMRADelegate.saveRemarkDetails(invoicDetailsVO);
				} catch (BusinessDelegateException businessDelegateException) {
					errors = (ArrayList) handleDelegateException(businessDelegateException);
				}

			}
			if (errors != null && errors.size() > 0) {
				actionContext.addAllError(errors);
				return;
			}

			responseVO.setStatus("success");
			actionContext.setResponseVO(responseVO);
			log.exiting("SaveRemarksCommand", "execute");

			
		}
	 
}
