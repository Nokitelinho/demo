/**
 *	Java file	: 	com.ibsplc.icargo.presentation.web.command.mail.mra.gpabilling.ux.gpabillingenquiry.ReRateCommand.java
 *
 *	Created by	:	A-4809
 *	Created on	:	Feb 19, 2019
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

import com.ibsplc.icargo.business.cra.defaults.vo.InvoiceTransactionLogVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.DocumentBillingDetailsVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.MRAConstantsVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.json.vo.ResponseVO;
import com.ibsplc.icargo.framework.web.spring.command.AbstractCommand;
import com.ibsplc.icargo.framework.web.spring.controller.ActionContext;
import com.ibsplc.icargo.presentation.delegate.mail.mra.MailTrackingMRADelegate;
import com.ibsplc.icargo.presentation.web.model.mail.mra.common.GPABillingEntryDetails;
import com.ibsplc.icargo.presentation.web.model.mail.mra.common.MailMRAModelConverter;
import com.ibsplc.icargo.presentation.web.model.mail.mra.gpabilling.GPABillingEnquiryModel;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 *	Java file	: 	com.ibsplc.icargo.presentation.web.command.mail.mra.gpabilling.ux.gpabillingenquiry.ReRateCommand.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-4809	:	Feb 19, 2019	:	Draft
 */
public class ReRateCommand extends AbstractCommand{
	
	private Log log = LogFactory.getLogger("MAIL MRA GPABILLING");
	private static final String ERR_BILLED="mailtracking.mra.gpabilling.billingentries.msg.err.billed";
	private static final String ERR_WITHRDRAW="mailtracking.mra.gpabilling.billingentries.msg.err.withdraw";
	private static final String ERR_PROFOMA="mailtracking.mra.gpabilling.billingentries.msg.err.profomabilled";
	private static final String ERR_RERATE="mailtracking.mra.gpabilling.billingentries.msg.err.rerate";
	private static final String ERR_ONHOLD="mailtracking.mra.gpabilling.billingentries.msg.err.onhold";
	private static final String ERR_MCA="mailtracking.mra.gpabilling.billingentries.msg.err.mca";
	private static final String NOMAILSSELECTED="mailtracking.mra.gpabilling.billingentries.msg.err.nomailselected";
	private static final String STATUS = "Re-rating of mailbags is initiated";
	private static final String STATUS_SUCCESS="RERATE_SUCCESS";
	private static final String STATUS_BILLING_ONHOLD = "ON HOLD";
	
	@Override
	public void execute(ActionContext actionContext) throws BusinessDelegateException, CommandInvocationException {
		this.log.entering("ReRateCommand", "execute");
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		LogonAttributes logonAttributes = getLogonAttribute();
		GPABillingEnquiryModel gpaBillingEnquiryModel = (GPABillingEnquiryModel)actionContext.getScreenModel();
		ResponseVO responseVO = new ResponseVO();
		List<GPABillingEnquiryModel> results = new ArrayList<GPABillingEnquiryModel>();
		Collection<GPABillingEntryDetails> selectedBilling = gpaBillingEnquiryModel.getSelectedBillingDetails();
		Collection<DocumentBillingDetailsVO> selectedDetailsVOs = MailMRAModelConverter.constructDocumentBillingDetailsVOs(selectedBilling, logonAttributes);
    	log.log(Log.INFO, "selectedDetailsVOs....in ReRateCommand....",
    			selectedDetailsVOs); 
    	if(selectedDetailsVOs!=null && !selectedDetailsVOs.isEmpty()){
    		for(DocumentBillingDetailsVO vo : selectedDetailsVOs){
    			vo.setPoaCode(vo.getGpaCode());
    			vo.setScreenID("MRA079"); 
    			if(MRAConstantsVO.BILLED.equals(vo.getBillingStatus())){
    	        	actionContext.addError(new ErrorVO(ERR_BILLED));
    	        	return;	    				
    			}else if(MRAConstantsVO.WITHDRAWN.equals(vo.getBillingStatus())){
    	        	actionContext.addError(new ErrorVO(ERR_WITHRDRAW));
    	        	return;	
    			}else if(MRAConstantsVO.PROFOMABILLED.equals(vo.getBillingStatus())){
    	        	actionContext.addError(new ErrorVO(ERR_PROFOMA));
    	        	return;	
    			}else if(MRAConstantsVO.TOBERERATED.equals(vo.getBillingStatus())){
    	        	actionContext.addError(new ErrorVO(ERR_RERATE));
    	        	return;	
    			}else if(MRAConstantsVO.ONHOLD.equals(vo.getBillingStatus())){
    	        	actionContext.addError(new ErrorVO(ERR_ONHOLD));
    	        	return;	
    			} else if(this.STATUS_BILLING_ONHOLD.equals(vo.getBillingStatus())){ // changed for ICRD-342551 ; MRAConstantsVO.ONHOLD.equals(vo.getBillingStatus())
    	        	actionContext.addError(new ErrorVO(ERR_ONHOLD));
    	        	return;	
    			}else if(vo.getCcaRefNumber()!=null && !vo.getCcaRefNumber().isEmpty()){
    	        	actionContext.addError(new ErrorVO(ERR_MCA));
    	        	return;
    			} 
    		}		
    		InvoiceTransactionLogVO invoiceTransactionLogVO = new InvoiceTransactionLogVO();
    		invoiceTransactionLogVO.setCompanyCode(logonAttributes.getCompanyCode());
    		invoiceTransactionLogVO.setInvoiceType(MRAConstantsVO.TOBERERATED);
    		invoiceTransactionLogVO.setTransactionDate ( new LocalDate(LocalDate.NO_STATION,Location.NONE,true));
    		invoiceTransactionLogVO.setInvoiceGenerationStatus(InvoiceTransactionLogVO.OPERATION_FLAG_INSERT);
    		invoiceTransactionLogVO.setStationCode(logonAttributes.getStationCode());
    		invoiceTransactionLogVO.setRemarks(STATUS);
    		invoiceTransactionLogVO.setSubSystem(MRAConstantsVO.SUBSYSTEM);
    		invoiceTransactionLogVO.setTransactionTime( new LocalDate(LocalDate.NO_STATION,Location.NONE,true));
    		invoiceTransactionLogVO.setTransactionTimeUTC( new LocalDate(LocalDate.NO_STATION,Location.NONE,true));
    		invoiceTransactionLogVO.setPeriodFrom(new LocalDate(LocalDate.NO_STATION, Location.NONE,true));
    		invoiceTransactionLogVO.setPeriodTo(new LocalDate(LocalDate.NO_STATION, Location.NONE,true));
    		invoiceTransactionLogVO.setUser(logonAttributes.getUserId());
    		invoiceTransactionLogVO.getSerialNumber();
    		invoiceTransactionLogVO.getTransactionCode();  
    		MailTrackingMRADelegate delegate = new MailTrackingMRADelegate();
    		try{
    			invoiceTransactionLogVO = delegate.initiateTransactionLogForInvoiceGeneration( invoiceTransactionLogVO );
    		}catch(BusinessDelegateException ex){
    			errors = this.handleDelegateException(ex);
    			log.log(Log.SEVERE, "execption",errors);
    		}    		
    		
    		String txnlogInfo = new StringBuilder(invoiceTransactionLogVO.getTransactionCode()).append("-").append(invoiceTransactionLogVO.getSerialNumber()).toString();
    		try {
    			delegate.reRateMailbags(selectedDetailsVOs,txnlogInfo);
    		} catch (BusinessDelegateException e) {
    			errors = this.handleDelegateException(e);
    			log.log(Log.SEVERE, "execption",e.getMessage());
    		}  
			ErrorVO error = new ErrorVO(
					"mailtracking.mra.gpabilling.billingentries.msg.info.datasavedsuccessfully");
			error.setErrorDisplayType(ErrorDisplayType.INFO);
			errors.add(error);
			results.add(gpaBillingEnquiryModel);
			responseVO.setResults(results);
			responseVO.setStatus(STATUS_SUCCESS);
			actionContext.addError(error); 
			actionContext.setResponseVO(responseVO);	
    	}else{
        	actionContext.addError(new ErrorVO(NOMAILSSELECTED));
        	return;
    	}
		
	}

}
