/*
 * AcceptInvoiceOKCommand.java Created on July 25, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.airlinebilling.inward.invoiceexceptions;



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
 * @author Indu V.K.
 * OK Command Class  for AcceptInvoiceCommand  screen.
 *
 * Revision History
 *
 * Version      Date           Author          		    Description
 *
 *  0.1         July 25,2007   Indu V.K.   			Initial draft
 *  
 */
public class AcceptInvoiceOKCommand extends BaseCommand{
	private Log log = LogFactory.getLogger("AirlineBilling");
	private static final String MODULE_NAME = "mailtracking.mra";
	private static final String SCREEN_ID = "mailtracking.mra.airlinebilling.inward.invoiceexceptions";
	private static final String ACTION_SUCCESS = "screenload_success";

	/**
	 * @param invocationContext
	 * @exception CommandInvocationException
	 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	log.entering("AcceptInvoiceOkCommand","execute");
    	InvoiceExceptionsForm form =
			(InvoiceExceptionsForm)invocationContext.screenModel;
		InvoiceExceptionsSession  session=	(InvoiceExceptionsSession)getScreenSession(
				MODULE_NAME, SCREEN_ID);
		Page<ExceptionInInvoiceVO> exceptionInInvoiceVOs=null;
		exceptionInInvoiceVOs=session.getExceptionInInvoiceVOs();

		String[] select=null;
    	if(session.getSelectedRows()!=null && session.getSelectedRows().length>0){
    		select=session.getSelectedRows();
    		}
    	form.setScreenStatus("ok");
    	if(select!=null && select.length>0){
    		form.setScreenStatus("ok");
    	for(int i=0;i<select.length;i++){
    		
    		if(form.getPopupRemarks()!=null && form.getPopupRemarks().trim().length()>0){

    			if(select[i]!=null && select[i].trim().length()>0 ) {
					exceptionInInvoiceVOs.get(Integer.parseInt(select[i])).setRemark(form.getPopupRemarks());
				}
    		}
    	}
    	}
    	session.setExceptionInInvoiceVOs(exceptionInInvoiceVOs);
    	invocationContext.target = ACTION_SUCCESS;
		log.exiting("AcceptInvoiceOkCommand", "execute");

    }

}
