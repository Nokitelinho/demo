/*
 * DeleteCommand.java Created on Feb 20, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.airlinebilling.inward.invoiceexceptions;


import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.ExceptionInInvoiceVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.mra.MailTrackingMRADelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.airlinebilling.inward.InvoiceExceptionsSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.airlinebilling.inward.InvoiceExceptionsForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 *@author Sreekanth.V.G
 * Command Class  for InvoiceExceptions  screen.
 *
 * Revision History
 *
 * Version      Date           Author          		    Description
 *
 *  0.1         Feb 20, 2007   Sreekanth.V.G   			Initial draft
 *  
 */
public class DeleteCommand extends BaseCommand {
	/**
	 * Logger and the file name
	 */
	private Log log = LogFactory.getLogger("MRA AIRLINEBILLING INWARD");
	private static final String CLASS_NAME = "DeleteCommand";
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
	 * SCREENLOAD_SUCCESS Action
	 */
	private static final String DELETE_SUCCESS = "delete_success";
	
	private static final String DELETE_FAILURE ="delete_failure";
	
	private static final String MEMOEXCEPTION = "B";
	
	private static final String MEMONOTBILLCANNOTACCEPTED = "mailtracking.mra.airlinebilling.inward.invoiceexceptions.billablecanbedeleted";
	
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
		Collection <ExceptionInInvoiceVO> exceptionInInvoiceVOs=
			session.getExceptionInInvoiceVOs();
		Collection <ExceptionInInvoiceVO> tempInInvoiceVOs=new ArrayList<ExceptionInInvoiceVO>();
		Collection <ErrorVO> errors= null;
		String selectedRow[]=null;		
		MailTrackingMRADelegate deligate=new MailTrackingMRADelegate();
		/**
		 * selected row getting here
		 */
		if(form.getRowId()!=null && form.getRowId().length>0){
			selectedRow=form.getRowId();
		}
		/**
		 * here checking the EXCEPTION status is EXCEPTION
		 */
//		if(exceptionInInvoiceVOs!=null && exceptionInInvoiceVOs.size()>0){
//			if(selectedRow!=null && selectedRow.length>0){
//				for(String s:selectedRow ){
//					log.log(Log.FINE,"The selected row is -->"+ s);
//				}
				errors=checkMemoStatus(exceptionInInvoiceVOs,selectedRow);
//			}
//	/
		if(errors !=null && errors.size()>0){
			invocationContext.addAllError(errors);
			invocationContext.target = DELETE_FAILURE;
			return;
		}
		
		
		if(exceptionInInvoiceVOs!=null && exceptionInInvoiceVOs.size()>0){
			int count=0;
			for(ExceptionInInvoiceVO vo :exceptionInInvoiceVOs){
		
			log.log(Log.FINE, "THe ccount", count);
			if(selectedRow!=null && selectedRow.length>0){
				for(String s:selectedRow ){
					log.log(Log.FINE, "THe seelcted row", s);
					if(count==Integer.parseInt(s)){
						log.log(Log.FINE, "THe seelcted row", s);
						tempInInvoiceVOs.add(vo);
					}					
				}
				count++;
			}
			}
		}
		for(ExceptionInInvoiceVO vo:tempInInvoiceVOs){
			log.log(Log.FINE, "THe finel vo are", vo);
		}
		/**
		 * Deliagte Call done here
		 */
		try{
			deligate.deleteRejectionMemo(tempInInvoiceVOs);
		}
		catch(BusinessDelegateException e){
			e.getMessage();
			handleDelegateException(e);
		}
		
		invocationContext.target = DELETE_SUCCESS;
		log.exiting(CLASS_NAME, "execute");
	}
	
	private Collection<ErrorVO>checkMemoStatus(Collection<ExceptionInInvoiceVO> exceptionInInvoiceVOs,
			String selectedRow[]){
		Collection <ErrorVO>errors=null;
		ErrorVO errorVO=null;
		int count =0;
		int errorValue=0;
		log.entering(CLASS_NAME,"checkMemoStatus");
		if(exceptionInInvoiceVOs!=null && exceptionInInvoiceVOs.size()>0){
			if(selectedRow!=null && selectedRow.length>0){
				for(ExceptionInInvoiceVO exceptionInInvoiceVO:exceptionInInvoiceVOs){
					for(String s:selectedRow){		
						log.log(Log.FINE, "Integer.parseInt(s)", s);
						log.log(Log.FINE, "count", count);
						if(count==Integer.parseInt(s)){
							if(MEMOEXCEPTION.equals(exceptionInInvoiceVO.getMemoStatus())){
								errorValue++;
								
							}
						}						
						
					}
					count++;
				}
			}
			
		}
		log.log(Log.FINE, "Error value", errorValue);
		if(errorValue==0){
			errorVO = new ErrorVO(MEMONOTBILLCANNOTACCEPTED);
			errors = new ArrayList<ErrorVO>();
			errors.add(errorVO);
		}
		log.exiting(CLASS_NAME,"checkMemoStatus");
		
		return errors;
	}
}
