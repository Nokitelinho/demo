/*
 * ClearCommand.java Created on Mar 26, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.presentation.web.command.mail.mra.gpabilling.invoicesettlement;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.gpabilling.InvoiceSettlementSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.gpabilling.InvoiceSettlementForm;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;


/**
 * @author A-2408
 *
 */
public class ClearCommand extends BaseCommand {
	private static final String MODULE_NAME = "mailtracking.mra.gpabilling";

    private static final String SCREEN_ID ="mailtracking.mra.gpabilling.invoicesettlement";

    private static final String SCREENLOAD_SUCCESS ="screenload_success";

    private static final String BLANK="";

    private static final String CLASS_NAME = "ClearCommand";

    private static final String SAVE_SUCCESS="mailtracking.mra.airlinebilling.msg.err.invsettlesavesuccess";
    private static final String TRUE="true";
    /**
	 * execute method
	 *
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
    public void execute(InvocationContext invocationContext)
    throws CommandInvocationException{
    	Log log = LogFactory.getLogger("MRA_GPABILLING");
    	log.entering(CLASS_NAME, "execute");
    	InvoiceSettlementSession session=(InvoiceSettlementSession)getScreenSession(MODULE_NAME,SCREEN_ID);
    	InvoiceSettlementForm form=(InvoiceSettlementForm)invocationContext.screenModel;
    	Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
    	ErrorVO errorVO=null;
    	
    	session.removeInvoiceSettlementVOs();
    	session.removeInvoiceSettlementHistoryVOs();
    	//session.removeAllAttributes(); 
    	form.setFrmPopUp(BLANK);	//added by a-5133 as part of ICRD-23808
    	form.setGpaCodeFilter(BLANK);
		form.setInvRefNumberFilter(BLANK);
		form.setFromDate(BLANK);
		form.setToDate(BLANK);
		form.setSettlementStatusFilter(BLANK);
		form.setInvoiceStatusFilter(BLANK);
		form.setGpaNameFilter(BLANK);
		form.setChequeNumberFilter(BLANK);
		form.setSettlementReferenceNumber(BLANK);
		form.setSettleCurrency(BLANK);
		form.setSettlementDate(BLANK);
		form.setSettlementStatusFilter(BLANK);
		session.setSelectedGPASettlementVOs(null);		
		session.setGPASettlementVOs(null);
		session.setGPASettlementVO(null);
		session.setInvoiceSettlementFilterVO(null); 
		session.setInvoiceSettlementDetailVOs(null);
		if(TRUE.equals(session.getFromSave())){
			session.setFromSave(BLANK);
			errorVO=new ErrorVO(SAVE_SUCCESS);
			errors.add(errorVO);
			invocationContext.addAllError(errors);
		}
		form.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
    	invocationContext.target = SCREENLOAD_SUCCESS;
		log.exiting(CLASS_NAME, "execute");

    }
}
