/*
 * SaveRepairCommand.java Created on Aug 1, 2005
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
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.uld.defaults.ULDDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.transaction.ListInvoiceSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.transaction.RepairInvoiceSession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.transaction.RepairInvoiceForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * This command class is invoked for viewing the detailed information of
 * selected ulds
 * 
 * @author A-2001
 */
public class SaveRepairCommand extends BaseCommand {
	/**
	 * Logger for View Invoice discripency
	 */
	private Log log = LogFactory.getLogger("Repair Invoice Discripency");
	private static final String MODULE = "uld.defaults";

	private static final String SCREENID =
		"uld.defaults.repairinvoice";
	
	private static final String MODULE_LISTINVOICE = "uld.defaults";

	private static final String SCREENID_LISTINVOICE =
		"uld.defaults.listinvoice";

	private static final String LIST_STATUS = "fromrepair";
    
	private static final String SAVE_SUCCESS = "save_success";
	private static final String SAVE_FAILURE = "save_failure";

    /**
	 * @param invocationContext
	 * @throws CommandInvocationException
	 * @return 
	 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		
    	RepairInvoiceSession repairInvoiceSession = 
    		(RepairInvoiceSession)getScreenSession(MODULE,SCREENID);
    	ListInvoiceSession listInvoiceSession = (ListInvoiceSession)getScreenSession(MODULE_LISTINVOICE,SCREENID_LISTINVOICE);
    	
    	RepairInvoiceForm repairInvoiceForm = (RepairInvoiceForm) invocationContext.screenModel;
    	Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
    	
    	if(repairInvoiceSession.getInvoiceRefNos() != null &&
    			repairInvoiceSession.getInvoiceRefNos().size() > 0) {
    		ArrayList<String> invoiceRefNos =repairInvoiceSession.getInvoiceRefNos();
        
        	int currentPage = Integer.parseInt(repairInvoiceForm.getCurrentPage());
        	String currentRefNo = invoiceRefNos.get(currentPage-1);
        	ArrayList<ULDRepairInvoiceDetailsVO> uldRepairCurrentDetailsVOs = 
        		new ArrayList<ULDRepairInvoiceDetailsVO>(repairInvoiceSession.getUldRepairInvoiceVO().
        					get(currentRefNo).getULDRepairInvoiceDetailsVOs());
           	errors = loadDetailVO(repairInvoiceForm, uldRepairCurrentDetailsVOs);
           	repairInvoiceSession.getUldRepairInvoiceVO().
			get(currentRefNo).setULDRepairInvoiceDetailsVOs(uldRepairCurrentDetailsVOs);
           	if(errors.size() > 0) {
           		repairInvoiceForm.setDisplayPage(repairInvoiceForm.getCurrentPage());
           		invocationContext.addAllError(errors);
           		invocationContext.target = SAVE_FAILURE;
           		return;
           	}
          	Collection<ULDRepairInvoiceVO> uldOuterCollection = 
           		repairInvoiceSession.getUldRepairInvoiceVO().values();
           	Collection<ULDRepairInvoiceDetailsVO> uldRepairDetailsVOs = 
           									new ArrayList<ULDRepairInvoiceDetailsVO>();
           	int collectionSize = uldOuterCollection.size();
           	for(ULDRepairInvoiceVO uldRepairInvoiceVO : uldOuterCollection) {
           		ArrayList<ULDRepairInvoiceDetailsVO> uldRepairInvoiceDetailsVOs =
           			new ArrayList<ULDRepairInvoiceDetailsVO>(
           					uldRepairInvoiceVO.getULDRepairInvoiceDetailsVOs());
           		uldRepairDetailsVOs.addAll(uldRepairInvoiceDetailsVOs);
            }
           try {
    			new ULDDefaultsDelegate().updateULDRepairInvoiceDetails(
    					 uldRepairDetailsVOs) ;
    		}
    		catch (BusinessDelegateException businessDelegateException) {
    			errors = handleDelegateException(businessDelegateException);
    		}
    		
	    	if(errors != null &&
					errors.size() > 0 ) {
					invocationContext.addAllError(errors);
					invocationContext.target = SAVE_FAILURE;
					return;
			}
	    	
	    	
    	}
    	repairInvoiceSession.setUldRepairDetailsVO(null);
    	repairInvoiceSession.setUldRepairInvoiceVO(null);
    	listInvoiceSession.setListStatus(LIST_STATUS);
       	invocationContext.target = SAVE_SUCCESS;
        
    }
   	
    private Collection<ErrorVO> loadDetailVO(RepairInvoiceForm repairInvoiceForm,
    		ArrayList<ULDRepairInvoiceDetailsVO> uldRepairDetailsVOs) {
		 Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		
		String waivedAmounts[] = repairInvoiceForm.getWaivedAmounts();
		String remarks[] = repairInvoiceForm.getRemarks();
		StringBuffer errUldNos = new StringBuffer("");
		int collectionSize = uldRepairDetailsVOs.size();
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
	    
}
