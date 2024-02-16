/*
 * SurchargeDetailsConnamd.java Created on Jul 16, 2015
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.presentation.web.command.mail.mra.gpabilling.billingentries;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.DocumentBillingDetailsVO;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.CN51CN66FilterVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.gpabilling.GPABillingEntriesSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.gpabilling.SurChargeBillingDetailSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.gpabilling.GPABillingEntriesForm;
import com.ibsplc.xibase.server.framework.persistence.query.Page;

/**
 * @author A-5255 
 * @version	0.1, Jul 16, 2015
 * 
 *
 */
/**
 * Revision History
 * Revision 	 Date      	     Author			Description
 * 0.1		Jul 16, 2015	     A-5255		First draft
 */

public class SurchargeDetailsCommand extends BaseCommand{

	/**
	  * @author A-5255
	  * @param arg0
	  * @throws CommandInvocationException
	  * @see com.ibsplc.icargo.framework.web.command.Command#execute(com.ibsplc.icargo.framework.web.command.InvocationContext)
	  */
	private static final String MODULE_NAME = "mailtracking.mra.gpabilling";
	private static final String SCREENID = "mailtracking.mra.gpabilling.billingentries.listgpabillingentries";
	private static final String SURCHARGESCREENID = "mailtracking.mra.gpabilling.surcharge.surchargepopup";
	private static final String ACTION_SUCCESS = "success";
	@Override
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		GPABillingEntriesForm form=(GPABillingEntriesForm)invocationContext.screenModel;
		GPABillingEntriesSession gpaSession =
				(GPABillingEntriesSession)getScreenSession(
						MODULE_NAME, SCREENID);
		SurChargeBillingDetailSession surchargeSession =
				(SurChargeBillingDetailSession)getScreenSession(
						MODULE_NAME, SURCHARGESCREENID);
		String select=form.getSelectedRow();
		CN51CN66FilterVO cn51cn66FilterVO=null;
    	Page<DocumentBillingDetailsVO> gpaBillingVos=gpaSession.getGpaBillingDetails();
    	int selectedIndex=0;
    	if(select!=null && !"".equals(select)){
    		selectedIndex=Integer.parseInt(select);
    		if(gpaBillingVos!=null && gpaBillingVos.size()>0){
    			DocumentBillingDetailsVO documentBillingDetailsVO=	gpaBillingVos.get(selectedIndex);
    			if(documentBillingDetailsVO!=null){
    				cn51cn66FilterVO=new CN51CN66FilterVO();
    				cn51cn66FilterVO.setCompanyCode(documentBillingDetailsVO.getCompanyCode());
    				cn51cn66FilterVO.setInvoiceNumber(documentBillingDetailsVO.getInvoiceNumber());
    				if(documentBillingDetailsVO.getSerialNumber()!=null)
    				{
    					cn51cn66FilterVO.setMailSeqnum(documentBillingDetailsVO.getMailSequenceNumber());
    				}//modified by a-7871 for ICRD-214766
    				else
    					{
    					cn51cn66FilterVO.setSequenceNumber(0);
    					}
    				cn51cn66FilterVO.setBillingBasis(documentBillingDetailsVO.getBillingBasis());
    				cn51cn66FilterVO.setGpaCode(documentBillingDetailsVO.getGpaCode());
    				cn51cn66FilterVO.setConsigneeDocumentNumber(documentBillingDetailsVO.getCsgDocumentNumber());
    				cn51cn66FilterVO.setConsigneeSequenceNumber(documentBillingDetailsVO.getCsgSequenceNumber());
    				cn51cn66FilterVO.setMcaNumber(documentBillingDetailsVO.getCcaRefNumber());
    				surchargeSession.setGpaBillingFilterVO(cn51cn66FilterVO);
    			}
    		}
	
    	}
    	invocationContext.target = ACTION_SUCCESS;
	}

}
