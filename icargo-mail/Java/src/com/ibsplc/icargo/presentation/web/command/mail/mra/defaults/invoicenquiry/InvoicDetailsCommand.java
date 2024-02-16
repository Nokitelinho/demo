/*
 * InvoicDetailsCommand.java Created on Aug 01, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.defaults.invoicenquiry;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.MailInvoicEnquiryDetailsVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.InvoicEnquiryDetailsSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.InvoicEnquirySession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.InvoicEnquiryForm;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-2270
 *
 */
public class InvoicDetailsCommand extends BaseCommand{
	
	
	private  Log log = LogFactory.getLogger("MRA DEFAULTS");

	private static final String CLASS_NAME = "InvoicDetailsCommand";

	private static final String MODULE_NAME = "mailtracking.mra.defaults";

	private static final String SCREENID = "mailtracking.mra.defaults.invoicenquiry";
	
	/*
	 * Strings for SCREEN_ID and MODULE_NAME of parent Screen 
	 */
	private static final String DTLS_SCREENID = "mailtracking.mra.defaults.invoicenquirydetails";
	private static final String DTLS_MODULENAME = "mailtracking.mra.defaults";
	
	/*
	 * Strings for SCREEN_ID and MODULE_NAME for InvoicEnquiryDetails Screen
	 */	
	private static final String VIEW_SUCCESS = "view_success";
	private static final String VIEW_FAILURE = "view_failure";
	
	private static final String  KEY_NO_ROW_SELECTED = "mailtracking.mra.defaults.invoicenquirydetails.norowssel";
	
	
	/**
	 * Method to implement passing of selected vo to details screen
	 *
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {

    	log.entering(CLASS_NAME,"execute");
    	
    	Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
    	
    	InvoicEnquiryForm form = (InvoicEnquiryForm)invocationContext.screenModel;
    	InvoicEnquirySession session = (InvoicEnquirySession)getScreenSession(MODULE_NAME,SCREENID);
    	
    	InvoicEnquiryDetailsSession	 detailsSession = (InvoicEnquiryDetailsSession)getScreenSession(DTLS_MODULENAME,DTLS_SCREENID);
    	String[] rowId = form.getRowId();
    	MailInvoicEnquiryDetailsVO selectedMailInvoicEnquiryDetailsVO = null;
    	
    	if(rowId!=null && form.getRowId().length!=0){
			String selectedRow=form.getRowId()[0];
			log.log(Log.INFO, "selectedRow========>>", selectedRow);
			int index=0;
			for(MailInvoicEnquiryDetailsVO mailInvoicEnquiryDetailsVO : session.getMailInvoicEnquiryDetailsVOs()){
				if(index==Integer.parseInt(selectedRow)){
					selectedMailInvoicEnquiryDetailsVO = mailInvoicEnquiryDetailsVO;
					log.log(Log.FINER, "selected VO is ",
							selectedMailInvoicEnquiryDetailsVO);
					detailsSession.setMailInvoicEnquiryDetailsVO(selectedMailInvoicEnquiryDetailsVO);
					 }
				index++;
			
			}
			invocationContext.target = VIEW_SUCCESS;
		}else{
    		errors.add(new ErrorVO(KEY_NO_ROW_SELECTED));
        	invocationContext.addAllError(errors);
        	invocationContext.target = VIEW_FAILURE;
    	}
    		return;
    }
    
   
	

}
