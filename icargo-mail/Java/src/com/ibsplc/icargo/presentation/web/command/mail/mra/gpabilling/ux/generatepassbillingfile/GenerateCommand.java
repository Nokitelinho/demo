/**
 *	Java file	: 	com.ibsplc.icargo.presentation.web.command.mail.mra.gpabilling.ux.generatepassbillingfile.GenerateCommand.java;
 *
 *	Created by	:	A-9084
 *	Created on	:	Jan 27, 2021
 *
 *  Copyright 2019 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 *
 * 	This software is the proprietary information of Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
 * 	Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.gpabilling.ux.generatepassbillingfile;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.cra.defaults.vo.InvoiceTransactionLogVO;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.GeneratePASSFilterVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.json.vo.ResponseVO;
import com.ibsplc.icargo.framework.web.spring.command.AbstractCommand;
import com.ibsplc.icargo.framework.web.spring.controller.ActionContext;
import com.ibsplc.icargo.presentation.delegate.mail.mra.MailTrackingMRADelegate;
import com.ibsplc.icargo.presentation.web.model.mail.mra.common.GPAPassFilter;
import com.ibsplc.icargo.presentation.web.model.mail.mra.common.MailMRAModelConverter;
import com.ibsplc.icargo.presentation.web.model.mail.mra.gpabilling.GeneratePASSBillingFileModel;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * 
 *	Java file	: 	com.ibsplc.icargo.presentation.web.command.mail.mra.gpabilling.ux.generatepassbillingfile.GenerateCommand.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-9084	:	27-Jan-2021	:	Draft
 */
public class GenerateCommand extends AbstractCommand {

	private Log log = LogFactory.getLogger("MRA MAIL");
	private static final String GENERATE_PASS_BILLING="PA";
	private static final String PASS_GENERATION_INITIATED="PASS Generation Initiated";
	private static final String MRA_SUBSYS="M";
	private static final String INV_GEN_STATUS="I";
	private static final String CONFRM_MSG="mail.mra.gpabilling.pass.initiated";
	private static final String MANDATORY_FILTER="mail.mra.gpabilling.pass.err.mandatoryfilter";
	private static final String FILENOTSELECTED="mail.mra.gpabilling.pass.err.filenotselected";
	
	@Override
	public void execute(ActionContext actionContext) throws BusinessDelegateException, 
	CommandInvocationException {
		this.log.entering("GeneratePASSCommand", "execute");
		GeneratePASSBillingFileModel generatepassbillingmodel = (GeneratePASSBillingFileModel)actionContext.getScreenModel();
		
		GPAPassFilter gPAPassFilter= generatepassbillingmodel.getPassFilter();
		GeneratePASSFilterVO passFilterVO = MailMRAModelConverter.constructPASSFilterVO(gPAPassFilter);
		
		if (isNullOrEmpty(passFilterVO.getPeriodNumber()) || passFilterVO.getBillingPeriodFrom() == null
				|| passFilterVO.getBillingPeriodTo() == null) {
			actionContext.addError(new ErrorVO(MANDATORY_FILTER));
			return;
		}
		if(passFilterVO.isAddNew() && (passFilterVO.getFileName()==null ||passFilterVO.getFileName().trim().length()==0 )){
			actionContext.addError(new ErrorVO(FILENOTSELECTED));
			return;
		}
		
		ResponseVO responseVO = new ResponseVO();
		MailTrackingMRADelegate delegate = new MailTrackingMRADelegate();
		LogonAttributes logonAttributes = getLogonAttribute();
		InvoiceTransactionLogVO invoiceTransactionLogVO = new InvoiceTransactionLogVO();
		invoiceTransactionLogVO.setCompanyCode(logonAttributes.getCompanyCode());
		invoiceTransactionLogVO.setInvoiceType(GENERATE_PASS_BILLING);
		invoiceTransactionLogVO.setTransactionDate ( new LocalDate(LocalDate.NO_STATION,Location.NONE,true));
   		invoiceTransactionLogVO.setPeriodFrom(passFilterVO.getBillingPeriodFrom());
   		invoiceTransactionLogVO.setPeriodTo(passFilterVO.getBillingPeriodTo());
   		invoiceTransactionLogVO.setInvoiceGenerationStatus(INV_GEN_STATUS);
   		invoiceTransactionLogVO.setStationCode(logonAttributes.getStationCode());
		invoiceTransactionLogVO.setRemarks(PASS_GENERATION_INITIATED);
		invoiceTransactionLogVO.setSubSystem(MRA_SUBSYS);
		invoiceTransactionLogVO.setTransactionTime(new LocalDate(LocalDate.NO_STATION,Location.NONE,true));
	    invoiceTransactionLogVO.setTransactionTimeUTC(new LocalDate(LocalDate.NO_STATION,Location.NONE,true));
	    invoiceTransactionLogVO.setUser(logonAttributes.getUserId());
    	try{
   		invoiceTransactionLogVO = delegate.initiateTransactionLogForInvoiceGeneration(invoiceTransactionLogVO);
	       }catch(BusinessDelegateException ex){
	    	   this.log.log(Log.SEVERE, ex.getMessage());
	    }
    	passFilterVO.setTransactionCode(invoiceTransactionLogVO.getTransactionCode());
    	passFilterVO.setInvoiceLogSerialNumber(invoiceTransactionLogVO.getSerialNumber());
    	passFilterVO.setCompanyCode(logonAttributes.getCompanyCode());
    	try{
    		delegate.generatePASSFile(passFilterVO);
	       }catch(BusinessDelegateException ex){
	    	   this.log.log(Log.SEVERE, ex.getMessage());
	    }
		ArrayList<GeneratePASSBillingFileModel> results = new ArrayList<>();
		results.add(generatepassbillingmodel);
		responseVO.setResults(results);
		responseVO.setStatus("success");
		
		ErrorVO info = new ErrorVO(CONFRM_MSG);
		info.setErrorDisplayType(ErrorDisplayType.INFO);
		
		actionContext.addError(info);		
	    actionContext.setResponseVO(responseVO);
	
	    
		log.exiting("GenerateCommand","execute");
	}
	
	private boolean isNullOrEmpty(String string){
		return (string==null || string.trim().length()==0);
	}

}
