/*
 * UpdateDemerageAmountCommand.java Created on Dec 19, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.transaction.generatetransactioninvoice;

import java.util.ArrayList;

import com.ibsplc.icargo.business.uld.defaults.transaction.vo.ULDTransactionDetailsVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.transaction.GenerateTransactionInvoiceSession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.transaction.GenerateTransactionInvoiceForm;

/**
 * @author A-1496
 *
 */
public class UpdateDemerageAmountCommand  extends BaseCommand {
	
	private static final String MODULE = "uld.defaults";
	
	private static final String SCREENID =
		"uld.defaults.generatetransactioninvoice";	
	private static final String UPDATE_SUCCESS = "update_success";
   
    /**
	 * @param invocationContext
	 * @throws CommandInvocationException
	 * @return 
	 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	GenerateTransactionInvoiceSession generateTransactionInvoiceSession =
    		(GenerateTransactionInvoiceSession)getScreenSession(MODULE,SCREENID);
    	GenerateTransactionInvoiceForm generateTransactionInvoiceForm = (GenerateTransactionInvoiceForm)invocationContext.screenModel;
		ArrayList<ULDTransactionDetailsVO> uldTransactionDetailsVOs =
			generateTransactionInvoiceSession.getUldTransactionDetailsVO();
		int prevIndex = generateTransactionInvoiceForm.getPrevIndex();
		ULDTransactionDetailsVO uldTransactionDetailsVO = 
				uldTransactionDetailsVOs.get(prevIndex);
		updateUldTransactionDetails(
				generateTransactionInvoiceForm,uldTransactionDetailsVO);
		populateFormWithNewDetails(
				generateTransactionInvoiceForm, uldTransactionDetailsVOs);
		invocationContext.target = UPDATE_SUCCESS;
    }

	private void populateFormWithNewDetails(
			GenerateTransactionInvoiceForm generateTransactionInvoiceForm, 
			ArrayList<ULDTransactionDetailsVO> uldTransactionDetailsVOs) {
		String uldNumber = generateTransactionInvoiceForm.getUldNumber();
		String[] originalDemAmt = 
    		generateTransactionInvoiceForm.getHiddenDmgAmt().split(",");
    	String[] originalWaived = 
    		generateTransactionInvoiceForm.getHiddenWaiver().split(",");
		for(ULDTransactionDetailsVO uldTransactionDetailsVO 
						: uldTransactionDetailsVOs) {
			if(uldTransactionDetailsVO.getUldNumber().equals(uldNumber)) {
				generateTransactionInvoiceForm.setDemAmt(Double.toString(
						uldTransactionDetailsVO.getDemurrageAmount()));
				generateTransactionInvoiceForm.setWaiver(Double.toString(
						uldTransactionDetailsVO.getWaived()));
				generateTransactionInvoiceForm.setPrevIndex(
						uldTransactionDetailsVOs.indexOf(uldTransactionDetailsVO));
				generateTransactionInvoiceForm.setOriginalDmgAmt(originalDemAmt[
	                    generateTransactionInvoiceForm.getPrevIndex()]);
				generateTransactionInvoiceForm.setOriginalWaiver(originalWaived[
				        generateTransactionInvoiceForm.getPrevIndex()]);
				break;
			}
		}
	}

	/*private void updateUldTransactionDetails(
			GenerateTransactionInvoiceForm generateTransactionInvoiceForm,
			ULDTransactionDetailsVO uldTransactionDetailsVO) {
		double newDemerageAmt = 0.0;
		double oldDemerageAmt = uldTransactionDetailsVO.getDemurrageAmount();
		double newWaiverAmt = 0.0;
		double oldWaiverAmt = uldTransactionDetailsVO.getWaived();
		
		if(generateTransactionInvoiceForm.getWaiver() != null
				&& generateTransactionInvoiceForm.getWaiver().trim().length() > 0) {
			newWaiverAmt = Double.parseDouble(
					generateTransactionInvoiceForm.getWaiver());
			uldTransactionDetailsVO.setWaived(newWaiverAmt);
		}
		else {
			uldTransactionDetailsVO.setWaived(0.0);
		}
		newDemerageAmt = oldDemerageAmt + (oldWaiverAmt - newWaiverAmt);		
		uldTransactionDetailsVO.setDemurrageAmount(newDemerageAmt);		
	} */   
	
	private void updateUldTransactionDetails(
			GenerateTransactionInvoiceForm generateTransactionInvoiceForm,
			ULDTransactionDetailsVO uldTransactionDetailsVO) {
		
		if(generateTransactionInvoiceForm.getWaiver() != null
				&& generateTransactionInvoiceForm.getWaiver().trim().length() > 0) {
			double newWaiverAmt = Double.parseDouble(
					generateTransactionInvoiceForm.getWaiver());
			uldTransactionDetailsVO.setWaived(newWaiverAmt);
		}
		else {
			uldTransactionDetailsVO.setWaived(0.0);
		}
		double newDemerageAmt = Double.parseDouble(
				generateTransactionInvoiceForm.getDemAmt());		
		uldTransactionDetailsVO.setDemurrageAmount(newDemerageAmt);		
	} 
  
}
