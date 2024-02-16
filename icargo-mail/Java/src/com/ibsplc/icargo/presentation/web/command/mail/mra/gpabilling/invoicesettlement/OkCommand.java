/**
 * OkCommand.java Created on Mar 30, 2012
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.gpabilling.invoicesettlement;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.GPASettlementVO;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.InvoiceSettlementFilterVO;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.InvoiceSettlementVO;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.SettlementDetailsVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.gpabilling.InvoiceSettlementSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.gpabilling.InvoiceSettlementForm;
import com.ibsplc.xibase.server.framework.persistence.query.Page;

/**
 * @author a-4823
 *
 */
public class OkCommand extends BaseCommand{

	private static final String MODULE_NAME = "mailtracking.mra.gpabilling";
	private static final String SCREEN_ID ="mailtracking.mra.gpabilling.invoicesettlement";      
	private static final String SCREENLOAD_SUCCESS ="screenload_success";
	private static final String EXIT ="exit";

	/**
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
	throws CommandInvocationException {

		InvoiceSettlementSession session=(InvoiceSettlementSession)getScreenSession(MODULE_NAME,SCREEN_ID);
		InvoiceSettlementForm form=(InvoiceSettlementForm)invocationContext.screenModel;
		Collection<GPASettlementVO> gpaSettlementVOs = session.getGPASettlementVOs();
		Page<GPASettlementVO> gpaSettlemntVOsToList = new Page<GPASettlementVO>(new ArrayList<GPASettlementVO>(),0,0,0,0,0,false);
		InvoiceSettlementFilterVO invoice= session.getInvoiceSettlementFilterVO();

		String[] selectedRow = form.getCheck();	
		int row=Integer.parseInt(selectedRow[0]);
		//Modified by A-7794 as part of ICRD-257781
		List<String> settlementIds = new ArrayList<>();
		invoice.setSettlementReferenceNumber(form.getSettlementId()[row]);
		/*int row = 0;
		for(GPASettlementVO vo : gpaSettlementVOs){
			for(SettlementDetailsVO settlDet : vo.getSettlementDetailsVOs()){
				for (int j = 0; j < selectedRow.length -1; j++) {
					if (row == Integer.parseInt(selectedRow[j])) {	
						if(!settlementIds.contains(vo)){
						gpaSettlemntVOsToList.add(vo);
						settlementIds.add(vo.getSettlementId());
						invoice.setSettlementReferenceNumber(vo.getSettlementId());

						}
					}
				}
				row++;
			}
			//Added by A-7794 as part of ICRD-257781
			int count = 0;
			if(gpaSettlemntVOsToList.size() > 0){
			if(gpaSettlemntVOsToList.iterator().next().getInvoiceSettlementVOs() != null && 
					gpaSettlemntVOsToList.iterator().next().getInvoiceSettlementVOs().size() >1){
				count = gpaSettlemntVOsToList.iterator().next().getInvoiceSettlementVOs().size();
				for(InvoiceSettlementVO invSetlVO : gpaSettlemntVOsToList.iterator().next().getInvoiceSettlementVOs()){
					if(invSetlVO.getInvSerialNumber() != count){
						gpaSettlemntVOsToList.remove(invSetlVO);
					}
				}
			}
			}*/
			//Added by A-7794 as part of ICRD-257781 - End
		//}
		session.setSelectedGPASettlementVOs(gpaSettlemntVOsToList);
		form.setAvailableSettlement(EXIT);
		form.setCreateFlag("");
		invocationContext.target = SCREENLOAD_SUCCESS;

	}

}
