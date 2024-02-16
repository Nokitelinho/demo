/*
 * SaveGroupRemarksCommand.java Created on Nov 20 2018
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */


package com.ibsplc.icargo.presentation.web.command.mail.mra.gpareporting.ux.invoicenquiry;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import com.ibsplc.icargo.business.mail.mra.gpareporting.vo.InvoicFilterVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.json.vo.ResponseVO;
import com.ibsplc.icargo.framework.web.spring.command.AbstractCommand;
import com.ibsplc.icargo.framework.web.spring.controller.ActionContext;
import com.ibsplc.icargo.presentation.delegate.mail.mra.MailTrackingMRADelegate;
import com.ibsplc.icargo.presentation.web.model.mail.mra.common.InvoicFilter;
import com.ibsplc.icargo.presentation.web.model.mail.mra.gpareporting.InvoicEnquiryModel;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-8464
 *
 */
public class SaveGroupRemarksCommand  extends AbstractCommand{

	 private Log log = LogFactory.getLogger("Mail MRA gpareporting invoic enquiry");
		
	 public void execute(ActionContext actionContext) throws BusinessDelegateException, CommandInvocationException {
			
			log.entering("SaveGroupRemarksCommand","execute");
			
			InvoicEnquiryModel invoicEnquiryModel = (InvoicEnquiryModel) actionContext.getScreenModel();
			ResponseVO responseVO = new ResponseVO();
			ArrayList<ErrorVO> errors = new ArrayList<ErrorVO>();	
			
				InvoicFilterVO invoicEnquiryFilterVO = new InvoicFilterVO();
				InvoicFilter invoicEnquiryFilterFromUI = (InvoicFilter)invoicEnquiryModel.getInvoicFilter();
				
				LogonAttributes logonAttributes = (LogonAttributes) getLogonAttribute();
				
				List<ErrorVO> erros=(List<ErrorVO>)updateFilterVO(invoicEnquiryFilterFromUI, invoicEnquiryFilterVO, logonAttributes);
		        if (erros!=null && erros.size()>0 && CollectionUtils.isNotEmpty(erros)){        	
		        	actionContext.addAllError(erros);
		        	return; 
		        }
		        
				try {
					MailTrackingMRADelegate mailTrackingMRADelegate = new MailTrackingMRADelegate();
					invoicEnquiryFilterVO.setProcessStatusFilter(invoicEnquiryModel.getToProcessStatus());
					mailTrackingMRADelegate.saveGroupRemarkDetails(invoicEnquiryFilterVO,invoicEnquiryModel.getGroupRemarks());
				} catch (BusinessDelegateException businessDelegateException) {
					errors = (ArrayList) handleDelegateException(businessDelegateException);
				}

			
			if (errors != null && errors.size() > 0) {
				actionContext.addAllError(errors);
				return;
			}

			responseVO.setStatus("success");
			actionContext.setResponseVO(responseVO);
			log.exiting("SaveGroupRemarksCommand", "execute");
			
		}
	 
		private Collection<ErrorVO> updateFilterVO(InvoicFilter invoicEnquiryFilterFromUI, InvoicFilterVO invoicEnquiryFilterVO, LogonAttributes logonAttributes)
	    {
		
		ArrayList<ErrorVO> errors = new ArrayList<>();
		//error conditions can be added as per reqmt
		try{
		invoicEnquiryFilterVO.setFromDate(invoicEnquiryFilterFromUI.getFromDate());
		invoicEnquiryFilterVO.setGpaCode(invoicEnquiryFilterFromUI.getGpaCode());
		invoicEnquiryFilterVO.setInvoicId(invoicEnquiryFilterFromUI.getInvoicId());
		invoicEnquiryFilterVO.setMailbagId(invoicEnquiryFilterFromUI.getMailbagId());
		invoicEnquiryFilterVO.setToDate(invoicEnquiryFilterFromUI.getToDate());
		invoicEnquiryFilterVO.setSelectedProcessStatus(invoicEnquiryFilterFromUI.getSelectedProcessStatus());
		invoicEnquiryFilterVO.setSelectedClaimRange(invoicEnquiryFilterFromUI.getSelectedClaimRange());
		invoicEnquiryFilterVO.setOrg(invoicEnquiryFilterFromUI.getOrg());
		invoicEnquiryFilterVO.setDest(invoicEnquiryFilterFromUI.getDest());
		invoicEnquiryFilterVO.setMailSubClass(invoicEnquiryFilterFromUI.getMailSubClass());
		invoicEnquiryFilterVO.setTotalRecords(invoicEnquiryFilterFromUI.getTotalRecords());
		invoicEnquiryFilterVO.setDefaultPageSize(invoicEnquiryFilterFromUI.getDefaultPageSize());
		invoicEnquiryFilterVO.setCmpcod(logonAttributes.getCompanyCode());
		invoicEnquiryFilterVO.setProcessStatusFilter(invoicEnquiryFilterFromUI.getProcessStatusFilter());
		invoicEnquiryFilterVO.setInvoicfilterStatus(invoicEnquiryFilterFromUI.getInvoicfilterStatus());
		}
		catch(Exception e){
			errors.add(new ErrorVO(e.getMessage()));
		}
			return  errors;
	    }
}
