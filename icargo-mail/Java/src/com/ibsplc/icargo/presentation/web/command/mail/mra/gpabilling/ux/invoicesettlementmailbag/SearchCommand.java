/**
 *	Java file	: 	com.ibsplc.icargo.presentation.web.command.mail.mra.gpabilling.ux.invoicesettlementmailbag.SearchCommand.java
 *
 *	Created by	:	A-7531
 *	Created on	:	15-May-2018
 *
 *  Copyright 2018 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 *
 * 	This software is the proprietary information of Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
 * 	Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.gpabilling.ux.invoicesettlementmailbag;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.GPASettlementVO;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.InvoiceSettlementFilterVO;

import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.mra.MailTrackingMRADelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.gpabilling.ux.InvoiceSettlementMailbagSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.gpabilling.ux.InvoiceSettlementMailbagForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;

import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 *	Java file	: 	com.ibsplc.icargo.presentation.web.command.mail.mra.gpabilling.ux.invoicesettlementmailbag.SearchCommand.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-7531	:	15-May-2018	:	Draft
 */
public class SearchCommand  extends BaseCommand{
	private Log log = LogFactory.getLogger(" SearchCommand");

	private static final String CLASS_NAME = "SearchCommand";
	private static final String SCREENLOAD_SUCCESS ="search_success";
	private static final String MODULE_NAME = "mailtracking.mra.gpabilling";
	private static final String SCREEN_ID = "mailtracking.mra.gpabilling.ux.invoicesettlementmailbaglevel";
	private static final String CREATE = "CREATE";
	private static final String SEARCH = "SEARCH";

	/**
	 *	Overriding Method	:	@see com.ibsplc.icargo.framework.web.command.Command#execute(com.ibsplc.icargo.framework.web.command.InvocationContext)
	 *	Added by 			: A-7531 on 15-May-2018
	 * 	Used for 	:
	 *	Parameters	:	@param invocationcontext
	 *	Parameters	:	@throws CommandInvocationException 
	 */
	@Override
	public void execute(InvocationContext invocationcontext)
			throws CommandInvocationException {
		Log log = LogFactory.getLogger("MRA_GPABILLING");
    	log.entering(CLASS_NAME, "execute");
    	InvoiceSettlementMailbagSession session = (InvoiceSettlementMailbagSession)getScreenSession(MODULE_NAME,SCREEN_ID);
    	InvoiceSettlementMailbagForm form=(InvoiceSettlementMailbagForm)invocationcontext.screenModel;
    	InvoiceSettlementFilterVO invoiceFiletrVO = session.getInvoiceSettlementFilterVO();
    	
    	String mailbagid =form.getMailbagId();
    	String status=form.getInvoiceStatus();
    	
    	
    	Collection<GPASettlementVO>gpaVO=new ArrayList<GPASettlementVO>();
    	String displayPage = form.getDisplayPage();
	    int displayPageCount = Integer.parseInt(displayPage);
	    String defaultSize =form.getDefaultPageSize();
	    int defaultPageSize = Integer.parseInt(defaultSize);
	    
	    invoiceFiletrVO.setPageNumber(displayPageCount);
	    invoiceFiletrVO.setDefaultPageSize(defaultPageSize);
	    invoiceFiletrVO.setTotalRecords(session.getTotalRecords());
	    invoiceFiletrVO.setMailbagID(mailbagid);
	    invoiceFiletrVO.setSettlementStatus(status);
		invoiceFiletrVO.setActionFlag(SEARCH);
    	
    	//MODIFIED FOR ICRD-299771
    	
		if ((CREATE.equals(form.getActionFlag())
				|| (SEARCH.equals(form.getActionFlag()) && (invoiceFiletrVO.getSettlementReferenceNumber() == null
						|| invoiceFiletrVO.getSettlementReferenceNumber().trim().length() <= 0)))) {
    	
    	try {
    		gpaVO=new MailTrackingMRADelegate().findUnsettledMailbags(invoiceFiletrVO);
    
			} catch (BusinessDelegateException e) {
				
				log.log(Log.SEVERE, "execption",e.getMessage());
			}
		} else if ("OK".equals(form.getActionFlag()) || SEARCH.equals(form.getActionFlag())) {
    	
    	try {
    		gpaVO=new MailTrackingMRADelegate().findSettledMailbags(invoiceFiletrVO);

    		
			} catch (BusinessDelegateException e) {
				
				log.log(Log.SEVERE, "execption",e.getMessage());
			}
    	}
		else {
			session.setSelectedGPASettlementVO(session.getSelectedGPASettlementVO());
		}
    	session.setSelectedGPASettlementVO(gpaVO);
		form.setActionFlag(form.getActionFlag().equals(CREATE)?CREATE:"OK");
    	form.setPopupFlag(false);
		if (!gpaVO.isEmpty() && gpaVO.iterator().next().getInvoiceSettlementVO() != null
				&& !gpaVO.iterator().next().getInvoiceSettlementVO().isEmpty()) {
    	session.setTotalRecords(gpaVO.iterator().next().getInvoiceSettlementVO().size());
    	}
    	invocationcontext.target = SCREENLOAD_SUCCESS;
	}

}
