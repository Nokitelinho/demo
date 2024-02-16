/*
 * IssueRejectionMemoCommand.java Created on Feb 20, 2007
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
import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.RejectionMemoVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.mra.MailTrackingMRADelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.airlinebilling.inward.InvoiceExceptionsSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.airlinebilling.inward.RejectionMemoSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.airlinebilling.inward.InvoiceExceptionsForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
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
public class IssueRejectionMemoCommand extends BaseCommand {
	/**
	 * Logger and the file name
	 */
	private Log log = LogFactory.getLogger("MRA AIRLINEBILLING INWARD");
	private static final String CLASS_NAME = "IssueRejectionMemoCommand";
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
	 * ISSUEREJECTION_SUCCESS Action
	 */
	private static final String MODULE_NAM = "mailtracking.mra.airlinebilling";
	/**
	 * screen id
	 *
	 */
	private static final String SCREEN_ID = "mailtracking.mra.airlinebilling.inward.rejectionmemo";
	
	private static final String ISSUEREJECTION_SUCCESS = "issuerejection_success";
	
	/**
	 * ISSUEREJECTION_SUCCESS Action
	 */
	private static final String ISSUEREJECTION_FAILURE = "issuerejection_failure";
	/**
	 * MEMOSTATUSNULLCANNOTACCEPTED
	 */
	private static final String EXCEPTATUSNOTEXPCANNOTISSUED = "mailtracking.mra.airlinebilling.inward.invoiceexceptions.memonotissued";
	/**
	 * BLANK
	 */
	private static final String BLANK = "";
	
	private static final String EXCEPTION = "E";
	
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
		ApplicationSessionImpl applicationSession = getApplicationSession();
		 LogonAttributes logonAttributes = applicationSession.getLogonVO();
		AcceptCommand command=new AcceptCommand();
		Collection <ExceptionInInvoiceVO> exceptionInInvoiceVOs=
			session.getExceptionInInvoiceVOs();
		ArrayList <ExceptionInInvoiceVO> tempInInvoiceVOs=new ArrayList<ExceptionInInvoiceVO>();
		
		//ExceptionInInvoiceVO expVO=new ExceptionInInvoiceVO();
		Collection <ErrorVO>errors=new ArrayList<ErrorVO>();
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
		 * This method is calling from Acceptance Command
		 * Same cehck is dere
		 * So reused the CODE 
		 * @param exceptionInInvoiceVOs
		 * @param selectedRow
		 * @param errorMessage
		 * @return
		 */
		if(exceptionInInvoiceVOs!=null && exceptionInInvoiceVOs.size()>0){
			if(selectedRow!=null && selectedRow.length>0){
				for(String s:selectedRow ){
					log.log(Log.FINE, "The selected row is -->", s);
					
				}
				errors=command.checkExceptionStatus(exceptionInInvoiceVOs,selectedRow,EXCEPTATUSNOTEXPCANNOTISSUED);
			}
		}
		if(errors!=null && errors.size()>0){
			invocationContext.addAllError(errors);
			invocationContext.target = ISSUEREJECTION_FAILURE;
			return;
		}
		
		if(exceptionInInvoiceVOs!=null && exceptionInInvoiceVOs.size()>0){
			int count=0;
			for(ExceptionInInvoiceVO vo :exceptionInInvoiceVOs){
				vo.setLastUpdatedUser(logonAttributes.getUserId());
			log.log(Log.FINE, "THe ccount", count);
			if(selectedRow!=null && selectedRow.length>0){
				for(String s:selectedRow ){
					log.log(Log.FINE, "THe seelcted row", s);
					if(count==Integer.parseInt(s)){
						log.log(Log.FINE, "THe seelcted row", s);
						if(EXCEPTION.equals(vo.getExceptionStatus())){
							tempInInvoiceVOs.add(vo);
						}
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
		 * 
		 */
		RejectionMemoVO vo=new RejectionMemoVO();
		try{
			vo=deligate.saveRejectionMemo(tempInInvoiceVOs.get(0));
		}
		catch(BusinessDelegateException e){
			e.getMessage();
			errors=handleDelegateException(e);
		}
		if(errors!=null && errors.size()>0){
			invocationContext.addAllError(errors);
			invocationContext.target = ISSUEREJECTION_FAILURE;
			return;
		}
		RejectionMemoSession rejsession = 
			(RejectionMemoSession)getScreenSession(MODULE_NAM, SCREEN_ID);
		rejsession.removeRejectionMemoVO();
		log.log(Log.INFO, "vo for session", vo);
		rejsession.setRejectionMemoVO(vo);
		invocationContext.target = ISSUEREJECTION_SUCCESS;
		log.exiting(CLASS_NAME, "execute");
	}
	
}
