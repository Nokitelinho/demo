/*
 * AcceptInvoiceCommand.java Created on July 25, 2007
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
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.airlinebilling.inward.InvoiceExceptionsSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.airlinebilling.inward.InvoiceExceptionsForm;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
/**
 * @author indu V.K.
 * Command Class  for AcceptInvoiceCommand  screen.
 *
 * Revision History
 *
 * Version      Date           Author          		    Description
 *
 *  0.1         July 25,2007   Indu V.K.   			Initial draft
 *  
 */
public class AcceptInvoiceCommand extends BaseCommand{
	private Log log = LogFactory.getLogger("AcceptInvoice ScreenloadCommand");
	private static final String MODULE_NAME = "mailtracking.mra";
	private static final String SCREEN_ID = "mailtracking.mra.airlinebilling.inward.invoiceexceptions";
	private static final String ACTION_SUCCESS = "screenload_success";
	private static final String ACCEPT_FAILURE = "accept_failure";
	private static final String EXCEPTION = "E";
	
	private static final String EXCEPTATUSNOTEXPCANNOTACCEPTED = "mailtracking.mra.airlinebilling.inward.invoiceexceptions.expsatusnotexp";


	/**
	 *
	 * Execute method	 
	 * @param invocationContext InvocationContext
	 * @throws CommandInvocationException
	 *
	 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	log.entering("Accept Invoice command","execute");
    	InvoiceExceptionsForm form =
			(InvoiceExceptionsForm)invocationContext.screenModel;
		InvoiceExceptionsSession  session=	(InvoiceExceptionsSession)getScreenSession(
				MODULE_NAME, SCREEN_ID);
    	//String popDespatchNumber="";
		/**
		 * selected row getting here
		 */
		String[] selectedRow=null;
		Collection <ErrorVO>errors=new ArrayList<ErrorVO>();
		Collection <ExceptionInInvoiceVO> exceptionInInvoiceVOs=session.getExceptionInInvoiceVOs();
		if(form.getRowId()!=null && form.getRowId().length>0){
			selectedRow=form.getRowId()[0].split(",");
		}
		/**
		 * here checking the EXCEPTION status is EXCEPTION
		 */
		if(exceptionInInvoiceVOs!=null && exceptionInInvoiceVOs.size()>0){
			if(selectedRow!=null && selectedRow.length>0){
				for(String s:selectedRow ){
					log.log(Log.FINE, "The selected row is -->", s);
				}
				errors=checkExceptionStatus(exceptionInInvoiceVOs,selectedRow,EXCEPTATUSNOTEXPCANNOTACCEPTED);
			}
		}		
		if(errors!=null && errors.size()>0){
			invocationContext.addAllError(errors);
			invocationContext.target = ACCEPT_FAILURE;
			return;
		}
		else{
	    session.setSelectedRows(selectedRow);
		}
    	invocationContext.target = ACTION_SUCCESS;
		log.exiting("ChangeStatusCommand", "execute");

    }

/**
 * 
 * @param exceptionInInvoiceVOs
 * @param selectedRow
 * @param errorMessage
 * @return
 */
public Collection<ErrorVO> checkExceptionStatus(Collection<ExceptionInInvoiceVO> exceptionInInvoiceVOs,
		String selectedRow[],String errorMessage){
	Collection <ErrorVO>errors=new ArrayList<ErrorVO>();
	ErrorVO errorVO=null;
	int count =0;
	int errorValue=0;
	log.entering(MODULE_NAME,"checkExceptionStatus");
	if(exceptionInInvoiceVOs!=null && exceptionInInvoiceVOs.size()>0){
		if(selectedRow!=null && selectedRow.length>0){
			for(ExceptionInInvoiceVO exceptionInInvoiceVO:exceptionInInvoiceVOs){
				for(String s:selectedRow){
					
					log.log(Log.FINE, "Selected row-->", s);
					log.log(Log.FINE, "count-->", count);
					if(s!=null && s.trim().length()>0){
					if(count==Integer.parseInt(s)){
						log.log(Log.FINE,
								"exceptionInInvoiceVO.getExceptionStatus()",
								exceptionInInvoiceVO.getExceptionStatus());
						if(EXCEPTION.equals(exceptionInInvoiceVO.getExceptionStatus())){
							errorValue++;
						}
					}						
				}
				}
				count++;
			}
		}
		log.log(Log.FINE, "Error value", errorValue);
		if(errorValue==0){
			errorVO = new ErrorVO(errorMessage);
			errors.add(errorVO);
		}
	}
	log.exiting(MODULE_NAME,"checkExceptionStatus");
	return errors;
}
}
