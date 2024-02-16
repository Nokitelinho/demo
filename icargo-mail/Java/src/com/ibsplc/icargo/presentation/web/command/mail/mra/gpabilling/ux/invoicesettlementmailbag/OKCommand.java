/**
 *	Java file	: 	com.ibsplc.icargo.presentation.web.command.mail.mra.gpabilling.ux.invoicesettlementmailbag.OKCommand.java
 *
 *	Created by	:	A-7531
 *	Created on	:	08-May-2018
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


import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.SettlementDetailsVO;

import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.InvoiceSettlementVO;

import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;

import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.gpabilling.ux.InvoiceSettlementMailbagSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.gpabilling.ux.InvoiceSettlementMailbagForm;


import com.ibsplc.xibase.server.framework.persistence.query.Page;

import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 *	Java file	: 	com.ibsplc.icargo.presentation.web.command.mail.mra.gpabilling.ux.invoicesettlementmailbag.OKCommand.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-7531	:	08-May-2018	:	Draft
 */
public class OKCommand extends BaseCommand{

	private Log log = LogFactory.getLogger(" OKCommand");

	private static final String CLASS_NAME = "OKCommand";

	private static final String MODULE_NAME = "mailtracking.mra.gpabilling";
	private static final String SCREEN_ID = "mailtracking.mra.gpabilling.ux.invoicesettlementmailbaglevel";
	private static final String ACTION_SUCCESS = "ok_success";
	
	
	/**
	 *	Overriding Method	:	@see com.ibsplc.icargo.framework.web.command.Command#execute(com.ibsplc.icargo.framework.web.command.InvocationContext)
	 *	Added by 			: A-7531 on 10-May-2018
	 * 	Used for 	:
	 *	Parameters	:	@param invocationcontext
	 *	Parameters	:	@throws CommandInvocationException 
	 */
	@Override
	public void execute(InvocationContext invocationcontext)
			throws CommandInvocationException {
		// TODO Auto-generated method stub
		Log log = LogFactory.getLogger("MRA_GPABILLING");
    	log.entering(CLASS_NAME, "execute");
    	
    	InvoiceSettlementMailbagSession session = (InvoiceSettlementMailbagSession)getScreenSession(MODULE_NAME,SCREEN_ID);
    	InvoiceSettlementMailbagForm form=(InvoiceSettlementMailbagForm)invocationcontext.screenModel;
    	Page<InvoiceSettlementVO> invoiceSettlementVOs= session.getSelectedGPASettlementVO().iterator().next().getInvoiceSettlementVO();
    	Page<InvoiceSettlementVO> invoiceSettlemntVOsToList = new Page<InvoiceSettlementVO>(new ArrayList<InvoiceSettlementVO>(),0,0,0,0,0,false);
    	Collection<SettlementDetailsVO> settlementVOs= session.getSelectedGPASettlementVO().iterator().next().getSettlementDetailsVOs();
    	Collection<SettlementDetailsVO> newSettlementVO=new ArrayList<SettlementDetailsVO>();
    	GPASettlementVO newGpaVo=new GPASettlementVO();
    	InvoiceSettlementFilterVO filterVO=session.getInvoiceSettlementFilterVO();
    	Collection<GPASettlementVO>gpaVO=new ArrayList<GPASettlementVO>();
		String[] selectedRow = form.getCheck();		
	
		int row1 = 0;
		String settlementid=null;
		for(SettlementDetailsVO settlementVO:settlementVOs){
			
			for (int j = 0; j < selectedRow.length -1; j++) {
				if (row1 == Integer.parseInt(selectedRow[j])) {					
					settlementid=settlementVO.getSettlementId();
					
					break;
				}
				
			}
			row1++;	
			

		}	
		
		filterVO.setSettlementReferenceNumber(settlementid);
	
		
    	
    	form.setActionFlag("OK");
    	form.setPopupFlag(false);
    	invocationcontext.target = ACTION_SUCCESS;
		
	}

}
