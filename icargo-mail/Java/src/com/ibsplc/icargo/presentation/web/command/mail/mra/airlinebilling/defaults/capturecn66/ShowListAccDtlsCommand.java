/*
 * ShowListAccDtlsCommand.java Created on Nov 06,2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.presentation.web.command.mail.mra.airlinebilling.defaults.capturecn66;

/**
 * 
 * @author a-3447
 */
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import com.ibsplc.icargo.business.cra.accounting.vo.AccountingFilterVO;
import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.AirlineCN66DetailsVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.cra.accounting.ListAccountingEntriesSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.airlinebilling.defaults.CaptureCN66Session;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.airlinebilling.defaults.CaptureCN66Form;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-3447
 * 
 */

/*
 * Revision History Revision Date Author Description
 * ==============================================================================
 * 0.1 08-Nov-08 A-3447 For integrating CaptureCN66 Screen with List Acc Entries
 * 
 * =============================================================================
 */
public class ShowListAccDtlsCommand extends BaseCommand {

	// --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

	/**
	 * Logger
	 */
	private Log log = LogFactory.getLogger("ShowListAccDtlsCommand");

	/**
	 * Class name
	 */

	private static final String CLASS_NAME = "ShowListAccDtlsCommand";

	/**
	 * Module Name
	 */

	private static final String MODULE_NAME = "mailtracking.mra.airlinebilling.defaults";

	/**
	 * Screen Id
	 */

	private static final String SCREEN_ID = "mailtracking.mra.airlinebilling.defaults.capturecn66";

	/**
	 * List Acc Entries
	 */

	private static final String LIST_ACC_ENTRIES = "list_success";

	/**
	 * Strings for SCREEN_ID ListAccountingEntries
	 */
	private static final String LISTACCOUNTING_SCREENID = "cra.accounting.listaccountingentries";

	/**
	 * Strings for MODULE NAME ListAccountingEntries
	 */

	private static final String LISTACCOUNTING_MODULENAME = "cra.accounting";
	private static final String  INWARD = "I";
	private static final String  OUTWARD = "O";

	// -----------------------------------------------------------------------------------------------------------------------------------------------------------

	/**
	 * @author a-3447 NOV 06-2008
	 * @param invocationContext
	 * @throws CommandInvocationException
	 *           Execute Method for showing Dsn Pop UP
	 */

	public void execute(InvocationContext invocationContext)
	throws CommandInvocationException {

		log.entering(CLASS_NAME, "--Execute method--");
		CaptureCN66Session session = (CaptureCN66Session) getScreenSession(
				MODULE_NAME, SCREEN_ID);
		ListAccountingEntriesSession accountingEntrySession = getScreenSession(
				LISTACCOUNTING_MODULENAME, LISTACCOUNTING_SCREENID);
		CaptureCN66Form form = (CaptureCN66Form) invocationContext.screenModel;		
		AirlineCN66DetailsVO airlineCN66DetailsVO=new AirlineCN66DetailsVO();
		HashMap<String, Collection<AirlineCN66DetailsVO>> cn66details = null;
		String counter = form.getRowCounter();
		log.log(Log.FINE, "counter-->>", counter);
		if (session.getCn66DetailsMap() != null
				&& session.getCn66DetailsMap().size() > 0) {
			cn66details = new HashMap<String, Collection<AirlineCN66DetailsVO>>(
					session.getCn66DetailsMap());
			ArrayList<Collection<AirlineCN66DetailsVO>> airlineCN66DetailsVOs = new ArrayList<Collection<AirlineCN66DetailsVO>>(
					cn66details.values());
			ArrayList<AirlineCN66DetailsVO> cn666DetailsVOs = new ArrayList<AirlineCN66DetailsVO>();
			int valsize = cn66details.values().size();
			log.log(Log.FINE, "valsize-->>", valsize);
			for (int i = 0; i < valsize; i++) {
				cn666DetailsVOs.addAll((ArrayList<AirlineCN66DetailsVO>) airlineCN66DetailsVOs.get(i));
			}
			airlineCN66DetailsVO=cn666DetailsVOs.get(Integer.parseInt(counter));	


		}
		log.log(Log.FINE, "selected Vo-->>", airlineCN66DetailsVO);
		AccountingFilterVO accountingFilterVO = populateAccountingFilterVO(airlineCN66DetailsVO,form);
		accountingEntrySession.setAccountingFilterVO(accountingFilterVO);
		invocationContext.target = LIST_ACC_ENTRIES;
	}

	/**
	 * @author A-3447
	 * @param invoiceInFormOneVO
	 * @return
	 * This method is used to populate necessary Filters to accounting Screen
	 */
	//@SuppressWarnings("static-access")
	private AccountingFilterVO populateAccountingFilterVO(
			AirlineCN66DetailsVO airlineCN66DetailsVO, CaptureCN66Form form) {
		log.entering("populateAccountingFilterVO", "method");
		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		AccountingFilterVO accountingFilterVO = new AccountingFilterVO();
		accountingFilterVO
		.setCompanyCode(airlineCN66DetailsVO.getCompanyCode());
		accountingFilterVO.setClearancePeriod(airlineCN66DetailsVO
				.getClearancePeriod());
		accountingFilterVO.setInvoiceNumber(airlineCN66DetailsVO
				.getInvoiceNumber());
		accountingFilterVO.setAirlineIdentifier(logonAttributes
				.getOwnAirlineIdentifier());
		if(INWARD.equals(form.getBillingType())){
			/**
			 * FOR INWARD
			 * */
		accountingFilterVO.setFunctionPoint(AirlineCN66DetailsVO.FUNCTIONPOINT_INWARDBILLING);
		}
		if(OUTWARD.equals(form.getBillingType())){
			/**
			 * 
			 * FOR OUTWARD
			 */
			accountingFilterVO.setFunctionPoint(AirlineCN66DetailsVO.FUNCTIONPOINT_OUTWARDBILLING);
			
		}
		accountingFilterVO.setSubSystem(AirlineCN66DetailsVO.MRA_SUBSYSTEM);
		accountingFilterVO.setMailBillingBasis(airlineCN66DetailsVO
				.getBillingBasis());
		log.log(Log.INFO, "Vos Returned --->>", accountingFilterVO);
		return accountingFilterVO;

	}

}
