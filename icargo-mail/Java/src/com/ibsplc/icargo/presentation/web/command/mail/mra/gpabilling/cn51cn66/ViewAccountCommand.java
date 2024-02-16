		/*
 * ViewAccountCommand.java Created on May 25, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.gpabilling.cn51cn66;


import com.ibsplc.icargo.business.cra.accounting.vo.AccountingFilterVO;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.CN51CN66FilterVO;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.CN66DetailsVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.cra.accounting.ListAccountingEntriesSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.gpabilling.ListCN51CN66Session;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.gpabilling.ListCN51CN66Form;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-2270
 *
 */
public class ViewAccountCommand extends BaseCommand{
	
	
	private  Log log = LogFactory.getLogger("MRA GPABILLING");

	private static final String CLASS_NAME = "ViewAccountCommand";

	private static final String MODULE_NAME = "mailtracking.mra.gpabilling";

	private static final String SCREENID = "mailtracking.mra.gpabilling.listcn51cn66";
	
	/*
	 * Strings for SCREEN_ID and MODULE_NAME of ListAccountingEntries
	 */
	private static final String LISTACCOUNTING_SCREENID = "cra.accounting.listaccountingentries";
	
	private static final String LISTACCOUNTING_MODULENAME = "cra.accounting";
	
	private static final String FUNCTION_PT_GPABILLING = "GB";
	
	private static final String SUB_SYSTEM = "M";

	private static final String FROM_LISTCN51CN66 = "listCN51CN66";
	
	private static final String VIEW_SUCCESS = "view_success";

	

	
	/**
	 * Method to implement passing of selected vo to details screen
	 *
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {

    	log.entering(CLASS_NAME,"execute");
    	
    	AccountingFilterVO accountingFilterVO = new AccountingFilterVO();
    	
    	ListCN51CN66Form listCN51CN66Form = (ListCN51CN66Form)invocationContext.screenModel;
    	
    	ListCN51CN66Session listCN51CN66Session = 
    		   (ListCN51CN66Session)getScreenSession(MODULE_NAME,SCREENID);

    	CN51CN66FilterVO cN51CN66FilterVO = populateFilterVO(listCN51CN66Form);
    	
    	listCN51CN66Session.setCN51CN66FilterVO(cN51CN66FilterVO);
    	
    	if (listCN51CN66Form.getSelectedRow() != null && listCN51CN66Form.getSelectedRow().length() != 0) {
			String[] selectedRow = listCN51CN66Form.getSelectedRow().split(",");
			CN66DetailsVO cn66DetailsVO = listCN51CN66Session.getCN66VOs().get(Integer.parseInt(selectedRow[0]));
			accountingFilterVO.setDespatchNo(cn66DetailsVO.getBillingBasis());
		}
    	
    	ListAccountingEntriesSession accountingEntrySession 
						= getScreenSession(LISTACCOUNTING_MODULENAME, LISTACCOUNTING_SCREENID);
    	accountingFilterVO.setCompanyCode(getApplicationSession().getLogonVO().getCompanyCode());
		accountingFilterVO.setInvoiceNumber(listCN51CN66Form.getInvoiceNumber());
		accountingFilterVO.setFunctionPoint(FUNCTION_PT_GPABILLING);
		accountingFilterVO.setSubSystem(SUB_SYSTEM);
       accountingEntrySession.setAccountingFilterVO(accountingFilterVO);
       accountingEntrySession.setParentScreenFlag(FROM_LISTCN51CN66);
    	
     
    	
    	invocationContext.target = VIEW_SUCCESS;
    	return;
    }

    private CN51CN66FilterVO populateFilterVO(ListCN51CN66Form form){
    	CN51CN66FilterVO cN51CN66FilterVO = new CN51CN66FilterVO();
    	cN51CN66FilterVO.setCompanyCode(
    			getApplicationSession().getLogonVO().getCompanyCode());
    	if(form.getGpaCode()!=null &&
				form.getGpaCode().trim().length()>0){
    		cN51CN66FilterVO.setGpaCode(form.getGpaCode());
    	}
    	if(form.getGpaName()!=null &&
				form.getGpaName().trim().length()>0){
    		cN51CN66FilterVO.setGpaName(form.getGpaName());
    	}
    	cN51CN66FilterVO.setInvoiceNumber(form.getInvoiceNumber());
    	if (form.getCategory() != null && form.getCategory().trim().length() > 0) {
    		cN51CN66FilterVO.setCategory(form.getCategory().toUpperCase());

		}else{
			cN51CN66FilterVO.setCategory("");//added by A-9092 for IASCB-81849
		}
		
		if (form.getOrigin() != null && form.getOrigin().trim().length() > 0) {
			cN51CN66FilterVO.setOrgin(form.getOrigin().toUpperCase());

		}else{
			cN51CN66FilterVO.setOrgin("");//added by A-9092 for IASCB-81849
		}
		
		if (form.getDestination() != null && form.getDestination().trim().length() > 0) {
			cN51CN66FilterVO.setDestination(form.getDestination().toUpperCase());

		}else{
			cN51CN66FilterVO.setDestination("");//added by A-9092 for IASCB-81849
		}
		if (form.getDsnNumber() != null && form.getDsnNumber().trim().length() > 0) {
			cN51CN66FilterVO.setDsnNumber(form.getDsnNumber());
		}else{
			cN51CN66FilterVO.setDsnNumber("");//added by A-9092 for IASCB-81849 
		}
    	return cN51CN66FilterVO;
    	
    }
   }