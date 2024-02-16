/*
 * ShowSummaryCommand.java Created on Aug 1, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.transaction.repairinvoice;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDRepairInvoiceDetailsVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDRepairInvoiceVO;
import com.ibsplc.icargo.framework.util.text.TextFormatter;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.transaction.RepairInvoiceSession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.transaction.RepairInvoiceForm;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * This command class is invoked for viewing the detailed information of
 * selected ulds
 * 
 * @author A-2001
 */
public class ShowSummaryCommand extends BaseCommand {
	/**
	 * Logger for View Invoice discripency
	 */
	private Log log = LogFactory.getLogger("Maintain Uld Discripency");
	private static final String MODULE = "uld.defaults";

	private static final String SCREENID =
		"uld.defaults.repairinvoice";
	
	private static final String SHOWSUMMARY_SUCCESS = "showsummary_success";
	private static final String SHOWSUMMARY_FAILURE = "showsummary_failure";
  
   
    /**
     * @param invocationContext
     * @return 
     * @throws CommandInvocationException
    */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	
		RepairInvoiceSession repairInvoiceSession = (RepairInvoiceSession)getScreenSession(MODULE,SCREENID);
    	RepairInvoiceForm repairInvoiceForm = (RepairInvoiceForm) invocationContext.screenModel;
    	int currentPage = Integer.parseInt(repairInvoiceForm.getCurrentPage());
    	ArrayList<String> invoiceRefNos =repairInvoiceSession.getInvoiceRefNos();
    	String currentRefNo = invoiceRefNos.get(currentPage-1);
    	ArrayList<ULDRepairInvoiceDetailsVO> uldRepairCurrentDetailsVOs = 
    		new ArrayList<ULDRepairInvoiceDetailsVO>(repairInvoiceSession.getUldRepairInvoiceVO().
    					get(currentRefNo).getULDRepairInvoiceDetailsVOs());
       	Collection<ErrorVO> errors = loadDetailVO(repairInvoiceForm, uldRepairCurrentDetailsVOs);
       	repairInvoiceSession.getUldRepairInvoiceVO().
		get(currentRefNo).setULDRepairInvoiceDetailsVOs(uldRepairCurrentDetailsVOs);
    	if(errors.size() > 0) {
    		repairInvoiceForm.setDisplayPage(repairInvoiceForm.getCurrentPage());
       		invocationContext.addAllError(errors);
       		invocationContext.target = SHOWSUMMARY_FAILURE;
       		return;
       	}
    	ULDRepairInvoiceVO uldRepairInvoiceVO = repairInvoiceSession.getUldRepairInvoiceVO().get(currentRefNo);
       	calculateAmounts(repairInvoiceForm,currentPage);
       invocationContext.target = SHOWSUMMARY_SUCCESS;
    }
   /* private void loadDetailVO(RepairInvoiceForm repairInvoiceForm,
    		ArrayList<ULDRepairInvoiceDetailsVO> uldRepairDetailsVOs) {
    	String waivedAmounts[] = repairInvoiceForm.getWaivedAmounts();
    	String remarks[] = repairInvoiceForm.getRemarks();
    	int collectionSize = uldRepairDetailsVOs.size();
    	for(int i = 0; i < collectionSize;i++) {
    		if(waivedAmounts[i].trim().length() > 0) {
    			uldRepairDetailsVOs.get(i).setWaivedAmount(
    					Double.parseDouble(waivedAmounts[i]));
    			if(Double.parseDouble(waivedAmounts[i]) != uldRepairDetailsVOs.get(i).getActualAmount()) {
					uldRepairDetailsVOs.get(i).setOperationFlag(ULDRepairInvoiceDetailsVO.OPERATION_FLAG_UPDATE);
				}
    		}
    		else {
    			if(uldRepairDetailsVOs.get(i).getActualAmount() != 0) {
	    			uldRepairDetailsVOs.get(i).setOperationFlag(ULDRepairInvoiceDetailsVO.OPERATION_FLAG_UPDATE);
	    			uldRepairDetailsVOs.get(i).setWaivedAmount(0);
    			}
    		}
    		if(!(remarks[i].equals(uldRepairDetailsVOs.get(i).getRepairRemarks()))) {
    			uldRepairDetailsVOs.get(i).setOperationFlag(ULDRepairInvoiceDetailsVO.OPERATION_FLAG_UPDATE);
    			uldRepairDetailsVOs.get(i).setRepairRemarks(remarks[i]);
    		}
    		
		}
	}*/

	private Collection<ErrorVO> loadDetailVO(RepairInvoiceForm repairInvoiceForm,
    		ArrayList<ULDRepairInvoiceDetailsVO> uldRepairDetailsVOs) {
		 Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		
		String waivedAmounts[] = repairInvoiceForm.getWaivedAmounts();
		String remarks[] = repairInvoiceForm.getRemarks();
		int collectionSize = uldRepairDetailsVOs.size();
		StringBuffer errUldNos = new StringBuffer("");
		for(int i = 0; i < collectionSize;i++) {
			if(waivedAmounts[i].trim().length() > 0) {
				
				
				if(Double.parseDouble(waivedAmounts[i]) > 
					uldRepairDetailsVOs.get(i).getActualAmount()) {

					if(("").equals(errUldNos.toString())) {
						errUldNos.append(uldRepairDetailsVOs.get(i).getUldNumber().toUpperCase());
					}
					else {
						errUldNos.append(" , ");
						errUldNos.append(uldRepairDetailsVOs.get(i).getUldNumber().toUpperCase());
					}
				}
				else {
					if(Double.parseDouble(waivedAmounts[i]) != uldRepairDetailsVOs.get(i).getWaivedAmount()) {
						uldRepairDetailsVOs.get(i).setOperationFlag(ULDRepairInvoiceDetailsVO.OPERATION_FLAG_UPDATE);
					}
				}
				uldRepairDetailsVOs.get(i).setWaivedAmount(
						Double.parseDouble(waivedAmounts[i]));
			}
			else {
				if(uldRepairDetailsVOs.get(i).getWaivedAmount() != 0) {
	    			uldRepairDetailsVOs.get(i).setOperationFlag(ULDRepairInvoiceDetailsVO.OPERATION_FLAG_UPDATE);
	    			uldRepairDetailsVOs.get(i).setWaivedAmount(0);
				}
			}
			if(!(remarks[i].equals(uldRepairDetailsVOs.get(i).getRepairRemarks()))) {
				uldRepairDetailsVOs.get(i).setOperationFlag(ULDRepairInvoiceDetailsVO.OPERATION_FLAG_UPDATE);
				uldRepairDetailsVOs.get(i).setRepairRemarks(remarks[i]);
			}
			
		}
		if(!(("").equals(errUldNos.toString()))) {
			ErrorVO error = new ErrorVO("uld.defaults.repairinvoice.waivedgreaterthanactual",new Object[]{
					errUldNos.toString()});
			errors.add(error);
		}
		return errors;
	}
	/*private Collection<ErrorVO> calculateAmounts(RepairInvoiceForm repairInvoiceForm) {
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
    	
    	double actualAmount = 0;
    	double waivedAmount = 0;
    	double invoicedAmount = 0;
    	//int i=0;
    	String invoicedAmounts[] = repairInvoiceForm.getInvoicedAmounts();
    	String waivedAmounts[] = repairInvoiceForm.getWaivedAmounts();
    	String actualAmounts[] = repairInvoiceForm.getActualAmounts();
		for(int i = 0; i < waivedAmounts.length;i++) {
		
			actualAmount = actualAmount + Double.parseDouble(actualAmounts[i]);
			if(waivedAmounts[i].trim().length() > 0) {
				waivedAmount = waivedAmount + Double.parseDouble(waivedAmounts[i]);
				invoicedAmounts[i] = Double.toString(
						Double.parseDouble(actualAmounts[i]) - Double.parseDouble(waivedAmounts[i]));
				if(Double.parseDouble(invoicedAmounts[i]) < 0) {
					ErrorVO error = new ErrorVO("uld.defaults.repairinvoice.waivedgreaterthanactual",new Object[]{
							actualAmounts[i],waivedAmounts[i]});
					errors.add(error);
				}
			}
			else {
				invoicedAmounts[i] = actualAmounts[i];
			}
			invoicedAmount = invoicedAmount + Double.parseDouble(invoicedAmounts[i]);
		}
		if(errors.size() == 0) {
			
			repairInvoiceForm.setInvoicedAmounts(invoicedAmounts);
			repairInvoiceForm.setTotalAmount(Double.toString(actualAmount));
			repairInvoiceForm.setTotalWaived(Double.toString(waivedAmount));
			repairInvoiceForm.setTotalInvoiced(Double.toString(invoicedAmount));
		}
		return errors;
	}*/
	
	private void calculateAmounts(RepairInvoiceForm repairInvoiceForm, int currentPage
										) {
		log.entering("ShowSummaryCommand", "calculateAmounts");
		RepairInvoiceSession repairInvoiceSession = (RepairInvoiceSession)getScreenSession(MODULE,SCREENID);
    	
    	double actualAmount = 0;
    	double waivedAmount = 0;
    	double invoicedAmount = 0;
    	//int i=0;
    	ArrayList<String> invoiceRefNos =repairInvoiceSession.getInvoiceRefNos();
    	String currentRefNo = invoiceRefNos.get(currentPage-1);
    	ULDRepairInvoiceVO uldRepairInvoiceVO = repairInvoiceSession.getUldRepairInvoiceVO().get(currentRefNo);
        ArrayList<ULDRepairInvoiceDetailsVO> uldRepairCurrentDetailsVOs = 
    		new ArrayList<ULDRepairInvoiceDetailsVO>(uldRepairInvoiceVO.getULDRepairInvoiceDetailsVOs());
    	String waivedAmounts[] = repairInvoiceForm.getWaivedAmounts();
    	String actualAmounts[] = repairInvoiceForm.getActualAmounts();
		for(int i = 0; i < waivedAmounts.length;i++) {
		
			actualAmount = actualAmount + Double.parseDouble(actualAmounts[i]);
			if(waivedAmounts[i].trim().length() > 0) {
				waivedAmount = waivedAmount + Double.parseDouble(waivedAmounts[i]);
				String invoicedamounttext = TextFormatter.formatDouble(
						(Double.parseDouble(actualAmounts[i]) - Double.parseDouble(waivedAmounts[i])),4);
				uldRepairCurrentDetailsVOs.get(i).setInvoicedAmount(Double.parseDouble(invoicedamounttext));
				
			}
			else {
				uldRepairCurrentDetailsVOs.get(i).setInvoicedAmount(
						uldRepairCurrentDetailsVOs.get(i).getActualAmount());
			}
			invoicedAmount = invoicedAmount + uldRepairCurrentDetailsVOs.get(i).getInvoicedAmount();
			
		}
			//repairInvoiceForm.setInvoicedAmounts(invoicedAmounts);
			uldRepairInvoiceVO.setTotalamount(TextFormatter.formatDouble(actualAmount,4));
			uldRepairInvoiceVO.setTotalwaived(TextFormatter.formatDouble(waivedAmount,4));
			uldRepairInvoiceVO.setTotalinvocied(TextFormatter.formatDouble(invoicedAmount,4));
			//Modified by A-7359 for ICRD-248560 starts here
			repairInvoiceForm.setTotalAmount(uldRepairInvoiceVO.getTotalamount());
			repairInvoiceForm.setTotalWaived(uldRepairInvoiceVO.getTotalwaived());
			repairInvoiceForm.setTotalInvoiced(uldRepairInvoiceVO.getTotalinvocied());
			//Modified by A-7359 for ICRD-248560 ends here
			log.exiting("ShowSummaryCommand", "calculateAmounts");
	}
  
}
