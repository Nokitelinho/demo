/*
 * NavigateRepairInvoiceCommand.java Created on Aug 1, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.transaction.repairinvoice;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDRepairInvoiceDetailsVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDRepairInvoiceVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.uld.defaults.ULDDefaultsDelegate;
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
public class NavigateRepairInvoiceCommand extends BaseCommand {
	/**
	 * Logger for View Invoice discripency
	 */
	private Log log = LogFactory.getLogger("Repair Invoice Discripency");
	private static final String MODULE = "uld.defaults";

	private static final String SCREENID =
		"uld.defaults.repairinvoice";
	private static final String NAVIGATION_SUCCESS = "navigation_success";
	private static final String NAVIGATION_FAILURE = "navigation_failure";

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
    	RepairInvoiceForm repairInvoiceForm = (RepairInvoiceForm) invocationContext.screenModel;
    	Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
    	int displayPage = Integer.parseInt(repairInvoiceForm.getDisplayPage());
    	
    	if(repairInvoiceSession.getInvoiceRefNos() != null &&
    			repairInvoiceSession.getInvoiceRefNos().size() > 0) {
    		ArrayList<ULDRepairInvoiceDetailsVO> uldRepairDetailsVOs = null;
    		ArrayList<String> invoiceRefNos =repairInvoiceSession.getInvoiceRefNos();
        	ArrayList<String> invoiceDates = repairInvoiceSession.getInvoiceDate();
        	ArrayList<String> invoiceToCodes = repairInvoiceSession.getInvoicedTo();
        	ArrayList<String> invoiceToNames = repairInvoiceSession.getInvoiceToName();
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
           		invocationContext.target = NAVIGATION_FAILURE;
           		return;
           	}
	    	String displayRefNo = invoiceRefNos.get(displayPage-1);
	    	ULDRepairInvoiceVO uldRepairInvoiceVO = null;
	    	HashMap<String,ULDRepairInvoiceVO> uldRepairInvoiceVOs =
	    		repairInvoiceSession.getUldRepairInvoiceVO();
	    	
	    		log.log(Log.FINE,
						"*************************DISPLAY_PAGE========",
						displayPage);
			log.log(Log.FINE, "*************************DISPLAY_RefNo========",
					displayRefNo);
			if(repairInvoiceSession.getUldRepairInvoiceVO().get(displayRefNo) == null ) {
	    		try {
	    			uldRepairInvoiceVO = 
	    					new ULDDefaultsDelegate().findRepairInvoiceDetails(
	    					 logonAttributes.getCompanyCode(),
	    					 displayRefNo) ;
	    		}
	    		catch (BusinessDelegateException businessDelegateException) {
	    			errors = handleDelegateException(businessDelegateException);
	    		}
	    		if(uldRepairInvoiceVO.getULDRepairInvoiceDetailsVOs() != null) {
	    			uldRepairDetailsVOs = new ArrayList<ULDRepairInvoiceDetailsVO>(
	    					uldRepairInvoiceVO.getULDRepairInvoiceDetailsVOs());
	    		}
	    		resetInvoiceAmount(uldRepairInvoiceVO.getULDRepairInvoiceDetailsVOs());
	    		uldRepairInvoiceVOs.put(displayRefNo,uldRepairInvoiceVO);
	    	}
	    	else {
	    		uldRepairInvoiceVO = repairInvoiceSession.getUldRepairInvoiceVO().
												get(displayRefNo);
	    		uldRepairDetailsVOs =
	    			new ArrayList<ULDRepairInvoiceDetailsVO>(repairInvoiceSession.getUldRepairInvoiceVO().
        					get(displayRefNo).getULDRepairInvoiceDetailsVOs());
	    	}
	    	log.log(Log.FINE, "*************************REPAIRVOS========",
					uldRepairInvoiceVOs);
			log.log(Log.FINE,
					"*************************NEWTRANSACTIONVOS========",
					uldRepairDetailsVOs);
			if(errors != null &&
					errors.size() > 0 ) {
					invocationContext.addAllError(errors);
					invocationContext.target = NAVIGATION_FAILURE;
					return;
			}
	    	loadRepairInvoiceForm(repairInvoiceForm,invoiceRefNos,invoiceDates,
    				invoiceToCodes,invoiceToNames,displayPage);
	    	loadFormTotals(repairInvoiceForm,uldRepairInvoiceVO);
	    	repairInvoiceSession.setUldRepairDetailsVO(uldRepairDetailsVOs);
	    	
	    	
    	}
       	invocationContext.target = NAVIGATION_SUCCESS;
        
    }
    private void resetInvoiceAmount(Collection<ULDRepairInvoiceDetailsVO> uldRepairDetailsVOs) {
    	for(ULDRepairInvoiceDetailsVO uldRepairInvoiceDetailsVO : uldRepairDetailsVOs) {
    		uldRepairInvoiceDetailsVO.setInvoicedAmount(-1);
    	}
    	
    } 
	private void loadFormTotals(RepairInvoiceForm repairInvoiceForm, ULDRepairInvoiceVO uldRepairInvoiceVO) {
		if(uldRepairInvoiceVO != null) {
			if(uldRepairInvoiceVO.getTotalamount() != null ) {
				repairInvoiceForm.setTotalAmount(uldRepairInvoiceVO.getTotalamount());
			}
			else {
				repairInvoiceForm.setTotalAmount("");
			}
			if(uldRepairInvoiceVO.getTotalwaived() != null ) {
				repairInvoiceForm.setTotalWaived(uldRepairInvoiceVO.getTotalwaived());
			}
			else {
				repairInvoiceForm.setTotalWaived("");
			}
			if(uldRepairInvoiceVO.getTotalinvocied() != null ) {
				repairInvoiceForm.setTotalInvoiced(uldRepairInvoiceVO.getTotalinvocied());
			}
			else {
				repairInvoiceForm.setTotalInvoiced("");
			}
		}
		else {
			repairInvoiceForm.setTotalAmount("");
			repairInvoiceForm.setTotalWaived("");
			repairInvoiceForm.setTotalInvoiced("");
		}
	}

	private void loadRepairInvoiceForm(RepairInvoiceForm repairInvoiceForm, 
    		ArrayList<String> invoiceRefNos, ArrayList<String> invoiceDates,
    		ArrayList<String> invoiceToCodes, ArrayList<String> invoiceToNames,
    		int displayPage) {
    	repairInvoiceForm.setInvoiceRefNo(invoiceRefNos.get(displayPage-1));
    	repairInvoiceForm.setInvoicedDate(invoiceDates.get(displayPage-1));
    	repairInvoiceForm.setInvoicedToCode(invoiceToCodes.get(displayPage-1));
    	repairInvoiceForm.setName(invoiceToNames.get(displayPage-1));
    	repairInvoiceForm.setCurrentPage(Integer.toString(displayPage));
    	
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
