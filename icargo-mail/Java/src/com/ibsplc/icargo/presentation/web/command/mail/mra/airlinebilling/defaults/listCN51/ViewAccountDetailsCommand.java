/*
 * ViewAccountDetailsCommand.java Created on May 29,2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.airlinebilling.defaults.listCN51;

import com.ibsplc.icargo.business.cra.accounting.vo.AccountingFilterVO;
import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.AirlineCN51FilterVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.cra.accounting.ListAccountingEntriesSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.airlinebilling.defaults.ListCN51ScreenSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.airlinebilling.defaults.ListCN51ScreenForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;


/**
 * @author A-2408
 *
 */
public class ViewAccountDetailsCommand extends BaseCommand {

	private Log log = LogFactory.getLogger("MailTracking:Mra:Defaults");
	
	private static final String MODULE_NAME = "mailtracking.mra";
	
	private static final String SCREEN_ID = "mailtracking.mra.airlinebilling.defaults.listCN51s";
	
	private static final String VIEW_ACC_SUCCESS = "view_success";
		
	private static final String ACC_MODULE_NAME = "cra.accounting";
	private static final String ACC_SCREEN_ID = "cra.accounting.listaccountingentries";
	private static final String INV_SCREENCN51="listcn51s";
	private static final String MRA="M";
	private static final String FUNCTION_POINT_MI="MI";
	private static final String FUNCTION_POINT_MO="MO";
	
	
	/* (non-Javadoc)
	 * @see com.ibsplc.icargo.framework.web.command.Command#execute(com.ibsplc.icargo.framework.web.command.InvocationContext)
	 */
	public void execute(InvocationContext invContext)
			throws CommandInvocationException {
		log.entering("ViewCN51DetailsCommand","execute");
		ListCN51ScreenForm cn51ScreenForm 
								= (ListCN51ScreenForm)invContext.screenModel;
		ListCN51ScreenSession cn51ScreenSession 
								= (ListCN51ScreenSession)getScreenSession(MODULE_NAME,SCREEN_ID);
		// setting the filterVO into the session for displaying the details back
		updateFilterVOInSession(cn51ScreenSession,cn51ScreenForm);
		
		ListAccountingEntriesSession accountingEntrySession 
		= getScreenSession(ACC_MODULE_NAME,ACC_SCREEN_ID);
		
		AirlineCN51FilterVO cn51filterVO = cn51ScreenSession.getAirlineCN51FilterVO();
		
		log.log(Log.FINE, "cn51filterVO...", cn51filterVO);
		AccountingFilterVO accountingFilterVO=new AccountingFilterVO();
		accountingFilterVO.setSubSystem(MRA);
		if("I".equals(cn51filterVO.getInterlineBillingType())){
		accountingFilterVO.setFunctionPoint(FUNCTION_POINT_MI);
		}
		else if("O".equals(cn51filterVO.getInterlineBillingType())){
			accountingFilterVO.setFunctionPoint(FUNCTION_POINT_MO);
		}
		
		if(cn51ScreenForm.getInvoiceNumber()!=null && cn51ScreenForm.getInvoiceNumber().length>0){
		log
				.log(Log.FINE, "InvoiceNumber...", cn51ScreenForm.getInvoiceNumber());
		accountingFilterVO.setInvoiceNumber(cn51ScreenForm.getInvoiceNumber()[0]);
		}
		accountingEntrySession.setAccountingFilterVO(accountingFilterVO);
		accountingEntrySession.setParentScreenFlag(INV_SCREENCN51);
		invContext.target = VIEW_ACC_SUCCESS;
		log.exiting("ViewCN51DetailsCommand","execute");
	}
	
	
	
	/**
	 * @param cn51ScreenSession
	 * @param cn51ScreenForm
	 */
	private void updateFilterVOInSession(ListCN51ScreenSession cn51ScreenSession,
										 ListCN51ScreenForm cn51ScreenForm ) {
		AirlineCN51FilterVO formFilterVO = new AirlineCN51FilterVO();
		if(cn51ScreenForm.getAirlineCode() != null && 
				cn51ScreenForm.getAirlineCode().length() > 0 ){
			formFilterVO.setAirlineCode(cn51ScreenForm.getAirlineCode().toUpperCase().trim());
		}		
		formFilterVO.setBilledDateFrom
					(new LocalDate(LocalDate.NO_STATION,Location.NONE,false)
							.setDate(cn51ScreenForm.getBlgFromDateStr()));
		formFilterVO.setBilledDateTo
					(new LocalDate(LocalDate.NO_STATION,Location.NONE,false)
							.setDate(cn51ScreenForm.getBlgToDateStr()));
		LogonAttributes logonAttributes = this.getApplicationSession().getLogonVO();
		formFilterVO.setCompanyCode(logonAttributes.getCompanyCode());		
		formFilterVO.setInterlineBillingType(cn51ScreenForm.getInterlineBlgType());		
		
		cn51ScreenSession.setAirlineCN51FilterVO(formFilterVO);
		
	}

}
