/*
 * CloseRepairCommand.java Created on Aug 1, 2005
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
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.transaction.RepairInvoiceSession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.transaction.RepairInvoiceForm;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * This command class is invoked for viewing the detailed information of
 * selected ulds
 * 
 * @author A-2001
 */
public class CloseRepairCommand extends BaseCommand {
	/**
	 * Logger for View Invoice discripency
	 */
	private Log log = LogFactory.getLogger("Maintain Uld Discripency");
	private static final String MODULE = "uld.defaults";

	private static final String SCREENID =
		"uld.defaults.repairinvoice";
	
	
	
	private static final String CLOSE_SUCCESS = "close_success";
	private static final String CLOSE_FAILURE = "close_failure";
  
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
    	Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
    	Collection<ULDRepairInvoiceVO> uldOuterCollection = 
       		repairInvoiceSession.getUldRepairInvoiceVO().values();
      	int collectionSize = uldOuterCollection.size();
      	boolean isUpdated = false;
      	if(!("canClose").equals(repairInvoiceForm.getCloseStatus())) {
	      	if(!isUpdated(repairInvoiceForm, uldRepairCurrentDetailsVOs)) {
	      		for(ULDRepairInvoiceVO uldRepairInvoiceVO : uldOuterCollection) {
	           		ArrayList<ULDRepairInvoiceDetailsVO> uldRepairInvoiceDetailsVOs =
	           			new ArrayList<ULDRepairInvoiceDetailsVO>(
	           					uldRepairInvoiceVO.getULDRepairInvoiceDetailsVOs());
	           		for(ULDRepairInvoiceDetailsVO uldRepairInvoiceDetailsVO : uldRepairInvoiceDetailsVOs) {
	           			if(ULDRepairInvoiceDetailsVO.OPERATION_FLAG_UPDATE.equals(uldRepairInvoiceDetailsVO.getOperationFlag())
	           					) {
	           				isUpdated = true;
	           				break;
	           			}
	           			
	           		}
	           		if(isUpdated) {
	       				break;
	       			}
	           	}
	       		
	       	}
	      	else {
	      		isUpdated = true;
	      	}
	      	if(isUpdated) {
	      		ErrorVO error = new ErrorVO("uld.defaults.repairuld.unsaveddataexist");
	       		error.setErrorDisplayType(ErrorDisplayType.WARNING);
	       		errors.add(error);
	       		invocationContext.addAllError(errors);
	       		repairInvoiceForm.setCloseStatus("whetherToClose");
	       		invocationContext.target = CLOSE_FAILURE;
	       		return;
	      	}
      	}
      	repairInvoiceSession.setUldRepairDetailsVO(null);
    	repairInvoiceSession.setUldRepairInvoiceVO(null);
    	
    	invocationContext.target = CLOSE_SUCCESS;
    }

	private boolean isUpdated(RepairInvoiceForm repairInvoiceForm,
    		ArrayList<ULDRepairInvoiceDetailsVO> uldRepairDetailsVOs) {
		boolean isUpdated = false;
		String waivedAmounts[] = repairInvoiceForm.getWaivedAmounts();
		String remarks[] = repairInvoiceForm.getRemarks();
		int collectionSize = uldRepairDetailsVOs.size();
		for(int i = 0; i < collectionSize;i++) {
			if(waivedAmounts[i].trim().length() > 0) {
				
				if(!(Double.parseDouble(waivedAmounts[i]) > 
					uldRepairDetailsVOs.get(i).getActualAmount())) {
					
					if(Double.parseDouble(waivedAmounts[i]) != uldRepairDetailsVOs.get(i).getWaivedAmount()) {
						isUpdated = true;
					}
				}
				uldRepairDetailsVOs.get(i).setWaivedAmount(
						Double.parseDouble(waivedAmounts[i]));
			}
			else {
				if(uldRepairDetailsVOs.get(i).getWaivedAmount() != 0) {
					isUpdated = true;
	    			uldRepairDetailsVOs.get(i).setWaivedAmount(0);
				}
			}
			if(!(remarks[i].equals(uldRepairDetailsVOs.get(i).getRepairRemarks()))) {
				isUpdated = true;
				uldRepairDetailsVOs.get(i).setRepairRemarks(remarks[i]);
			}
			
		}
		return isUpdated;
	}
	
  
}
