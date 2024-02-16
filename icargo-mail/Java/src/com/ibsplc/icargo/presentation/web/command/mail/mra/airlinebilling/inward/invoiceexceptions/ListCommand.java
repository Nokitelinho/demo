/*
 * ListCommand.java Created on Feb 20, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.airlinebilling.inward.invoiceexceptions;


import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.ExceptionInInvoiceFilterVO;
import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.ExceptionInInvoiceVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.mra.MailTrackingMRADelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.airlinebilling.inward.InvoiceExceptionsSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.airlinebilling.inward.InvoiceExceptionsForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author Sreekanth.V.G
 * Command Class  for InvoiceExceptions  screen.
 *
 * Revision History
 *
 * Version      Date           Author          		    Description
 *
 *  0.1         Feb 20, 2007   Sreekanth.V.G   			Initial draft
 *  
 */
public class ListCommand extends BaseCommand {
	/**
	 * Logger and the file name
	 */
	private Log log = LogFactory.getLogger("MRA AIRLINEBILLING INWARD");
	private static final String CLASS_NAME = "ListCommand";
	/**
	 * module name
	 *
	 */
	private static final String MODULE_NAME = "mailtracking.mra";
	/**
	 * screen id
	 *
	 */
	private static final String SCREENID = "mailtracking.mra.airlinebilling.inward.invoiceexceptions";
	/**
	 * LIST_SUCCESS Action
	 */
	private static final String LIST_SUCCESS = "list_success";
	/**
	 * LIST_FAILURE Action
	 */
	private static final String LIST_FAILURE = "list_failure";
	
	/**
	 * No results found
	 */
	private static final String NO_RESULTS_FOUND="mailtracking.mra.airlinebilling.inward.invoiceexceptions.noresultsfound";
	
	private static final String AIRLINECODE_MANDATORY="mailtracking.mra.airlinebilling.inward.invoiceexceptions.airlinecodemandatory";
	/**
	 * BLANK value
	 */
	private static final String BLANK = "";
	
	/**
	 * Scren Flag value invoiceexception
	 */
	private static final String FLAGVALUE= "invoiceexception";
	//Added for bug ICRD-100936 by A-5526 starts
	/**
	 * TO_INVOICEEXCEPTION /FROM_REJECTIONMEMO is for identifying the To screen / From screen flow 
	 */
	private static final String TO_INVOICEEXCEPTION = "cn66screenclose";
	private static final String FROM_REJECTIONMEMO= "RejectionMemo";
	private static final String FROM_EXCEPTIONDETAILS = "closeinvoiceexceptions";
	
	//Added for bug ICRD-100936 by A-5526 ends
	/**
	 *
	 * Execute method	 *
	 * @param invocationContext InvocationContext
	 * @throws CommandInvocationException
	 *
	 */
	public void execute(InvocationContext invocationContext)
	throws CommandInvocationException {
		log.entering(CLASS_NAME, "execute");
		
		InvoiceExceptionsForm form =
			(InvoiceExceptionsForm)invocationContext.screenModel;
		InvoiceExceptionsSession  session=	(InvoiceExceptionsSession)getScreenSession(
				MODULE_NAME, SCREENID);
		ExceptionInInvoiceFilterVO filterVO=null;
		Page <ExceptionInInvoiceVO>  exceptionInInvoiceVOs=null;
		Collection <ErrorVO>errors=new ArrayList<ErrorVO>();
		ErrorVO errorVO=null;
		MailTrackingMRADelegate deligate=new MailTrackingMRADelegate();
		/**
		 * Comapny code is get from logon attribute
		 */
		String companyCode=getLogonAttributevalues().getCompanyCode();
		form.setCn66CloseFlag("false");    
		
		/**
		 * if Filter Session is not null set values to Form
		 * This is for implementing close button in exception details screen
		 */	
		//Added for bug ICRD-100936 by A-5526 starts
		if(TO_INVOICEEXCEPTION.equals(form.getFromScreenFlag()) || FROM_REJECTIONMEMO.equals(form.getFromScreenFlag()) || FROM_EXCEPTIONDETAILS.equals(form.getFromScreenFlag()) ){    
			form.setCn66CloseFlag("true");                   
		}//Added for bug ICRD-100936 by A-5526 ends
		//Changed as part of bug ICRD-100245 by A-5526 ( if(!("true".equals(form.getCn66CloseFlag()))) is changed as below 
		if(("true".equals(form.getCn66CloseFlag())) && session.getExceptionInInvoiceFilterVO()!=null){        
		
			if(!FLAGVALUE.equals(form.getFromScreenFlag())){
				filterVO=session.getExceptionInInvoiceFilterVO();
				form=setFilterVlauesFromSession(form,filterVO);
			}
						
			filterVO=session.getExceptionInInvoiceFilterVO();	
		
		
		
		}
		/*else if(session.getExceptionInInvoiceFilterVO()!=null)
		{
			filterVO=session.getExceptionInInvoiceFilterVO();			
		}*/ 
		else{    
			filterVO=setFilterValues(form,companyCode);		
		}
	
		
		/**
		 * Filter values set here
		 */
		if(filterVO!=null)
		{
		filterVO.setCompanyCode(companyCode);
		
		log.log(Log.FINE, "The FIlterVO is--------------->", filterVO);
		if(BLANK.equals(filterVO.getAirlineCode())||filterVO.getAirlineCode()==null){
			errorVO = new ErrorVO(AIRLINECODE_MANDATORY);
			errors.add(errorVO);
			invocationContext.addAllError(errors);
			session.setExceptionInInvoiceVOs(null);
			form.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
			invocationContext.target = LIST_FAILURE;
			return;
		}
		filterVO.setPageNumber(Integer.parseInt(form.getDisplayPage()));
		}
		/**
		 * filetr vo is seting in to session
		 */
		session.setExceptionInInvoiceFilterVO(filterVO);
		/**
	   * Deliagte Call done here
		 */
		
		try{
			exceptionInInvoiceVOs=(Page<ExceptionInInvoiceVO>)deligate.findAirlineExceptionInInvoices(filterVO);
		}
		catch(BusinessDelegateException e){
			e.getMessage();
			handleDelegateException(e);
		}
		
		if(exceptionInInvoiceVOs!=null && exceptionInInvoiceVOs.size()>0){
			for(ExceptionInInvoiceVO vo:exceptionInInvoiceVOs){
				vo.setOperationFlag(ExceptionInInvoiceVO.OPERATION_FLAG_UPDATE);
				form.setContractCurrency(vo.getContractCurrency());
			}
			
		}
		else{
			errorVO = new ErrorVO(NO_RESULTS_FOUND);
			errors.add(errorVO);
			invocationContext.addAllError(errors);
			session.setExceptionInInvoiceVOs(null);
			session.setTotalRecords(0);
			form.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
			invocationContext.target = LIST_FAILURE;
			return;
		}
		form.setFromScreenFlag(FLAGVALUE);
		session.setExceptionInInvoiceVOs(exceptionInInvoiceVOs);
		form.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_DETAIL);
		invocationContext.target = LIST_SUCCESS;
		log.exiting(CLASS_NAME, "execute");
	}
	/**
	 *
	 * @return LogonAttributes
	 */
	private LogonAttributes  getLogonAttributevalues(){
		ApplicationSessionImpl applicationSessionImpl = getApplicationSession();
		
		return applicationSessionImpl.getLogonVO();
	}
	/**
	 * 
	 * @param form
	 * @param companyCode
	 * @return
	 */
	private  ExceptionInInvoiceFilterVO setFilterValues(InvoiceExceptionsForm form,
			String companyCode){
		ExceptionInInvoiceFilterVO filterVO=new ExceptionInInvoiceFilterVO();		
		filterVO.setCompanyCode(companyCode);
		if(!BLANK.equals(form.getAirlineCode())){
			filterVO.setAirlineCode(form.getAirlineCode().toUpperCase());
		}
		if(!BLANK.equals(form.getClearencePeriod())){
			filterVO.setClearancePeriod(form.getClearencePeriod());
		}
		if(!BLANK.equals(form.getExceptionStatus())){
			filterVO.setExceptionStatus(form.getExceptionStatus());
		}
		if(!BLANK.equals(form.getInvoiceNumber())){
			filterVO.setInvoiceNumber(form.getInvoiceNumber());
		}
		if(!BLANK.equals(form.getRejectionMemoNumber())){
			filterVO.setMemoCode(form.getRejectionMemoNumber());
		}
		if(!BLANK.equals(form.getContractCurrency())){
			filterVO.setContractCurrency(form.getContractCurrency());
		}
		if(!BLANK.equals(form.getMemoStatus())){
			filterVO.setMemoStatus(form.getMemoStatus());
		}
		
		return filterVO;
	}
	/**
	 * 
	 * @param form
	 * @param filterVO
	 * @return
	 */
	private InvoiceExceptionsForm setFilterVlauesFromSession(InvoiceExceptionsForm form,
			ExceptionInInvoiceFilterVO filterVO){	
		
		form.setAirlineCode(filterVO.getAirlineCode());
		form.setClearencePeriod(filterVO.getClearancePeriod());
		form.setExceptionStatus(filterVO.getExceptionStatus());
		form.setInvoiceNumber(filterVO.getInvoiceNumber());
		form.setRejectionMemoNumber(filterVO.getMemoCode());
		form.setContractCurrency(filterVO.getContractCurrency());
		form.setMemoStatus(filterVO.getMemoStatus());	
		
		return form;
	}
}
