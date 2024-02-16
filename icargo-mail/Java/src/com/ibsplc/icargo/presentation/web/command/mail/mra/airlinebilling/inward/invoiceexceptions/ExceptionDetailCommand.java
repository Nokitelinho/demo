/*
 * ExceptionDetailCommand.java Created on Feb 20, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.airlinebilling.inward.invoiceexceptions;


import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.ExceptionInInvoiceFilterVO;
import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.ExceptionInInvoiceVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.airlinebilling.inward.InvoiceExceptionsSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.airlinebilling.inward.InvoiceExceptionsForm;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
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
public class ExceptionDetailCommand extends BaseCommand {
	/**
	 * Logger and the file name
	 */
	private Log log = LogFactory.getLogger("MRA AIRLINEBILLING INWARD");
	private static final String CLASS_NAME = "ExceptionDetailCommand";
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
	private static final String EXCEPTIONDETAIL_SUCCESS = "exceptiondetail_success";
	
	/**
	 * BLANK value
	 */
	private static final String BLANK = "";
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
		Page <ExceptionInInvoiceVO> exceptionInInvoiceVOs=null;
		exceptionInInvoiceVOs=session.getExceptionInInvoiceVOs();
		ExceptionInInvoiceVO exceptionInInvoiceVO=null;
		ExceptionInInvoiceFilterVO filterVO=session.getExceptionInInvoiceFilterVO();
		int selectedRow=-1;		
		/**
		 * selected row getting here
		 */
		if(form.getRowId()!=null && form.getRowId().length>0){
			selectedRow=Integer.parseInt(form.getRowId()[0]);
		}
		if(exceptionInInvoiceVOs!=null && exceptionInInvoiceVOs.size()>0){
			int count=0; 
			if(selectedRow!=-1){
				for(ExceptionInInvoiceVO vo:exceptionInInvoiceVOs){
					if(count==selectedRow){
						exceptionInInvoiceVO=vo;
						break;
					}
					count++;
				}
			}
		}
		/**
		 * here set the from and to date to filter vo 
		 * this will populate the Airline exception table values
		 * from and to date is mandtoty in airline exception table
		 */
		if(exceptionInInvoiceVO!=null){
			filterVO.setFromdate(exceptionInInvoiceVO.getFromdate());
			filterVO.setTodate(exceptionInInvoiceVO.getToDate());			
		}
		log.log(Log.FINE, "The filter Vo--->", filterVO);
		session.setExceptionInInvoiceFilterVO(filterVO);
		form.setFromScreenFlag(BLANK);
		invocationContext.target = EXCEPTIONDETAIL_SUCCESS;
		log.exiting(CLASS_NAME, "execute");
	}
	
}
