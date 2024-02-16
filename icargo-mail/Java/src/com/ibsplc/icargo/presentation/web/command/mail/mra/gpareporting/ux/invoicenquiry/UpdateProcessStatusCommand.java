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
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-8464
 *
 */
public class UpdateProcessStatusCommand extends AbstractCommand {
	private Log log = LogFactory.getLogger("Mail MRA gpareporting invoic enquiry");
	private static final String UPDATE_SUCCESS_MESSAGE = "mail.mra.gpareporting.updatesuccess";
	private static final String OTHERS = "OTH";
	private static final String PROCESS_STATUS_INVALID_MESSAGE = "mail.mra.gpareporting.processstatusinvalid";
	
	
	 public void execute(ActionContext actionContext) throws BusinessDelegateException, CommandInvocationException {
			
			log.entering("UpdateProcessStatusCommand","execute");
			
			InvoicEnquiryModel invoicEnquiryModel = (InvoicEnquiryModel) actionContext.getScreenModel();
			ResponseVO responseVO = new ResponseVO();
			ArrayList<ErrorVO> errors = new ArrayList<ErrorVO>();	
			
			if (invoicEnquiryModel != null && invoicEnquiryModel.getSelectedInvoicDetails()!= null) {
				
				String toProcessStatus = invoicEnquiryModel.getToProcessStatus();
				log.log(Log.FINE, "invoicEnquiryModel.getSelectedInvoicDetails() not null");
				Collection<InvoicDetails> selectedInvoicDetails = invoicEnquiryModel.getSelectedInvoicDetails();
				//Added by A-7929 as part of IASCB-384 starts ---
				for(InvoicDetails invoicDetails:selectedInvoicDetails){
					if(!OTHERS.equals(invoicDetails.getMailbagInvoicProcessingStatus())){
						ErrorVO error=new ErrorVO(PROCESS_STATUS_INVALID_MESSAGE);
						error.setErrorDisplayType(ErrorDisplayType.ERROR);
						actionContext.addError(error);
						return;
						
					}
				}
				
				//Added by A-7929 as part of IASCB-384 ends ---
				
				Collection<InvoicDetailsVO> invoicDetailsVOs = MailMRAModelConverter.constructInvoicDetailVOs(selectedInvoicDetails);
		        
				try {
					MailTrackingMRADelegate mailTrackingMRADelegate = new MailTrackingMRADelegate();
					mailTrackingMRADelegate.updateProcessStatus(invoicDetailsVOs, toProcessStatus);
				} catch (BusinessDelegateException businessDelegateException) {
					errors = (ArrayList) handleDelegateException(businessDelegateException);
				}

			}
			if (errors != null && errors.size() > 0) {
				actionContext.addAllError(errors);
				return;
			}

			responseVO.setStatus("success");
			//Added by A-7929 as part of IASCB-384 starts ---
			ErrorVO error=new ErrorVO(UPDATE_SUCCESS_MESSAGE);
			error.setErrorDisplayType(ErrorDisplayType.INFO);
			actionContext.addError(error);
			//Added by A-7929 as part of IASCB-384 ends ---
			actionContext.setResponseVO(responseVO);
			log.exiting("UpdateProcessStatusCommand", "execute");
			
		}

}
