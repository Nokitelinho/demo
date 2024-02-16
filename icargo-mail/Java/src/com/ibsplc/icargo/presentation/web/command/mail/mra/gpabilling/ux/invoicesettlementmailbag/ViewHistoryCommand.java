/**
 *	Java file	: 	com.ibsplc.icargo.presentation.web.command.mail.mra.gpabilling.ux.invoicesettlementmailbag.ViewHistoryCommand.java
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



import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.InvoiceSettlementFilterVO;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.InvoiceSettlementHistoryVO;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.InvoiceSettlementVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.mra.MailTrackingMRADelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.gpabilling.ux.InvoiceSettlementMailbagSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.gpabilling.ux.InvoiceSettlementMailbagForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 *	Java file	: 	com.ibsplc.icargo.presentation.web.command.mail.mra.gpabilling.ux.invoicesettlementmailbag.ViewHistoryCommand.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-7531	:	08-May-2018	:	Draft
 */
public class ViewHistoryCommand extends BaseCommand{

	private static final String MODULE_NAME = "mailtracking.mra.gpabilling";

    private static final String SCREEN_ID ="mailtracking.mra.gpabilling.ux.invoicesettlementmailbaglevel";
    
    private static final String SCREENLOAD_SUCCESS ="screenload_success";
    
    private static final String SCREENLOAD_FAILED ="screenload_failure";
    
    private static final String CLASS_NAME = "ViewHistoryCommand";
    
	/**
	 *	Overriding Method	:	@see com.ibsplc.icargo.framework.web.command.Command#execute(com.ibsplc.icargo.framework.web.command.InvocationContext)
	 *	Added by 			: A-7531 on 11-May-2018
	 * 	Used for 	:
	 *	Parameters	:	@param arg0
	 *	Parameters	:	@throws CommandInvocationException 
	 */
	@Override
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
	
		Log log = LogFactory.getLogger("MRA_GPABILLING");
    	log.entering(CLASS_NAME, "execute");
    	Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
    	InvoiceSettlementMailbagSession session = (InvoiceSettlementMailbagSession)getScreenSession(MODULE_NAME,SCREEN_ID);
    	InvoiceSettlementMailbagForm form=(InvoiceSettlementMailbagForm)invocationContext.screenModel;
    	String companyCode = getApplicationSession().getLogonVO().getCompanyCode();
    	InvoiceSettlementFilterVO invoiceFiletrVO = session.getInvoiceSettlementFilterVO();
     	Page<InvoiceSettlementVO> invoiceSettlementVOs= session.getSelectedGPASettlementVO().iterator().next().getInvoiceSettlementVO();
     	String [] selectedRow= form.getSelectedIndex();
    	int row = 0;
    	
    	for(InvoiceSettlementVO invoiceSettlementVO: invoiceSettlementVOs){
    		if (row == Integer.parseInt(selectedRow[0])) {	
    			invoiceFiletrVO.setCompanyCode(invoiceSettlementVO.getCompanyCode());
    			invoiceFiletrVO.setGpaCode(invoiceSettlementVO.getGpaCode());
    			invoiceFiletrVO.setMailsequenceNum(invoiceSettlementVO.getMailsequenceNum());	
    			invoiceFiletrVO.setMailbagID(invoiceSettlementVO.getMailbagID());
    			if(invoiceSettlementVO.getMcaRefnum()!=null){
    			invoiceFiletrVO.setMcaRefnumber(invoiceSettlementVO.getMcaRefnum());
    			}
    			break;
    					}
    		row++;
    	}
    	
    		
    	Collection<InvoiceSettlementHistoryVO>invoiceSettlementHistoryVO = null;
    	
    	if(invoiceFiletrVO!=null){
    		try{
    			invoiceSettlementHistoryVO=new MailTrackingMRADelegate().findMailbagSettlementHistory(invoiceFiletrVO);
    		}catch(BusinessDelegateException e) {
    			
    			log.log(Log.SEVERE, "execption",e.getMessage());
    		}
    	
	}
    	if(invoiceSettlementHistoryVO!=null && invoiceSettlementHistoryVO.size()>0){
    		for(InvoiceSettlementHistoryVO invoiceSettlementHistory:invoiceSettlementHistoryVO){
    			invoiceFiletrVO.setMcaRefnumber(invoiceSettlementHistory.getMcaRefNum());
    		}
    		session.setInvoiceSettlementHistoryVO(invoiceSettlementHistoryVO);
    		
    	}
    	if("REMARK".equals(form.getActionFlag())){ 
    		String remark ="";
    		String newRemark="";
    		row = Integer.parseInt(selectedRow[0]);
    		if(invoiceSettlementVOs!=null) {
				for (int i = 0; i < invoiceSettlementVOs.size(); i++) {
					if(i == Integer.parseInt(selectedRow[0])) {
						InvoiceSettlementVO invoiceSettlementVO = (InvoiceSettlementVO) invoiceSettlementVOs.toArray()[i];
						remark = invoiceSettlementVO.getRemarks();
					}
				}
			}
    		if(invoiceSettlementHistoryVO!=null){
    			for (int i = 0; i < invoiceSettlementHistoryVO.size(); i++) {
    				if(i == Integer.parseInt(selectedRow[0])) {
    					InvoiceSettlementHistoryVO vo = (InvoiceSettlementHistoryVO) invoiceSettlementHistoryVO.toArray()[i];
    					newRemark=vo.getRemarks();
    				}
    			}
    		}
    		if(remark!=null && remark.trim().length()>0){
    			form.setRemarks(new String[]{remark}); 
    		} else if(newRemark!=null&& newRemark.trim().length()>0) {
    			form.setRemarks(new String[]{newRemark});
    		}
    		else{
    			form.setRemarks(new String[]{""}); 
    		}
    	
    	}
    	form.setActionFlag(form.getActionFlag());
    	form.setPopupFlag(false);
    	invocationContext.target = SCREENLOAD_SUCCESS;
		log.exiting(CLASS_NAME, "execute");
}
}