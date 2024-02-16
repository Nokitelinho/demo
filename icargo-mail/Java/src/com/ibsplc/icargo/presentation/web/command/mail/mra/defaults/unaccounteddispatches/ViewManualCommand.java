
/*
 * ViewManualCommand.java Created on March 19, 2009
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.defaults.unaccounteddispatches;




import java.util.ArrayList;
import com.ibsplc.icargo.framework.util.currency.CurrencyException;
import com.ibsplc.icargo.framework.util.currency.CurrencyHelper;
import com.ibsplc.icargo.framework.util.currency.Money;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.UnaccountedDispatchesForm;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.presentation.web.session.interfaces.cra.accounting.ManualAccountingEntrySession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.UnaccountedDispatchesSession;
import com.ibsplc.icargo.business.cra.accounting.vo.AccountingEntryDetailsVO;
import com.ibsplc.icargo.business.cra.accounting.vo.AccountingEntryVO;
import com.ibsplc.icargo.business.cra.accounting.vo.AccountingFilterVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.UnaccountedDispatchesDetailsVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.UnaccountedDispatchesVO;


/**
 * @author A-3434
 *
 */
public class ViewManualCommand extends BaseCommand {

	private Log log = LogFactory.getLogger("MRA_DEFAULTS");

	private static final String CLASS_NAME = "ViewManualCommand";

	private static final String MODULE_NAME = "mailtracking.mra.defaults";

	private static final String SCREEN_ID = "mailtracking.mra.defaults.unaccounteddispatches";

	private static final String SCREENLOADETAILS_SUCCESS = "success";
	private static final String MODULE_NAME_CRA = "cra.accounting";
	private static final String SCREEN_ID_CRA = "cra.accounting.manualaccountingentry";

	/**
	 * execute method
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
	throws CommandInvocationException {
		log.entering(CLASS_NAME, "execute");
		
		UnaccountedDispatchesSession  unaccountedDispatchesSession = 
			(UnaccountedDispatchesSession) getScreenSession(MODULE_NAME, SCREEN_ID);
		/*
		 * Obtaining accountingEntrySession
		 */
		ManualAccountingEntrySession manualAccountingSession 
									= getScreenSession(MODULE_NAME_CRA,SCREEN_ID_CRA);
		
		UnaccountedDispatchesForm unaccountedDispatchesForm = 
			(UnaccountedDispatchesForm)invocationContext.screenModel;
		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		String companyCode = logonAttributes.getCompanyCode();
		UnaccountedDispatchesVO unaccountedDispatchesVO =null;
		Page<UnaccountedDispatchesDetailsVO> unaccountedDispatchesDetailsVOs=null;
		Money tempMoney = null;
		String carrierCode= null;
		String flightNo= null;
		ArrayList<AccountingEntryDetailsVO> deatailsList=new  ArrayList<AccountingEntryDetailsVO>();
		String select= unaccountedDispatchesForm.getSelectedDispatch();
		log.log(Log.FINE, "Select : ", select);
		UnaccountedDispatchesDetailsVO unaccountedDispatchesDetailsVO=null;
		unaccountedDispatchesVO = unaccountedDispatchesSession.getUnaccountedDispatchesVO();
		if(unaccountedDispatchesVO!=null){
		unaccountedDispatchesDetailsVOs=unaccountedDispatchesVO.getUnaccountedDispatchesDetails();
		}
		if(unaccountedDispatchesDetailsVOs!=null && unaccountedDispatchesDetailsVOs.size()>0){
		ArrayList<UnaccountedDispatchesDetailsVO> unaccountedDispatchesDetailsVOArraylist = new ArrayList<UnaccountedDispatchesDetailsVO>(
				unaccountedDispatchesDetailsVOs);
		
		unaccountedDispatchesDetailsVO= unaccountedDispatchesDetailsVOArraylist.get(Integer.parseInt(select));
    	log.log(Log.FINE, "Inside NavigateCommand ... >>",
				unaccountedDispatchesDetailsVO);
		}
		AccountingFilterVO filterVO=new AccountingFilterVO();
		AccountingEntryVO entryVo=new AccountingEntryVO ();
		AccountingEntryDetailsVO creditVo=new  AccountingEntryDetailsVO();
		AccountingEntryDetailsVO debitVo=new  AccountingEntryDetailsVO();
		log.log(Log.INFO, "detailsVO.getProratedNetAmountNZD() ",
				unaccountedDispatchesDetailsVO.getProratedAmt());
		try {
			tempMoney = CurrencyHelper.getMoney("NZD");
			tempMoney.setAmount(0.0);
		} catch (CurrencyException e) {
			// TODO Auto-generated catch block
			e.getErrorCode();
		}
		/*
		 * if amount is -ve,it should shown as +ve
		 * in manual accounting entry screen.
		 */
		if(unaccountedDispatchesDetailsVO.getProratedAmt()!=null && unaccountedDispatchesDetailsVO.getProratedAmt().getAmount()<0){
			try {
			Money prorateAmtMoney = CurrencyHelper.getMoney("NZD");
			
			prorateAmtMoney.setAmount(Math.abs(unaccountedDispatchesDetailsVO.getProratedAmt().getAmount()));
			unaccountedDispatchesDetailsVO.setProratedAmt(prorateAmtMoney);
			}
			catch (CurrencyException e) {
				// TODO Auto-generated catch block
				e.getErrorCode();
			}
		}
		creditVo.setDebit(unaccountedDispatchesDetailsVO.getProratedAmt());
		creditVo.setCredit(tempMoney);
		debitVo.setDebit(tempMoney);
		debitVo.setCredit(unaccountedDispatchesDetailsVO.getProratedAmt());
		deatailsList.add(creditVo);
		deatailsList.add(debitVo);
		entryVo.setBaseCurrency("NZD");
		entryVo.setAccountingEntryDetailsVos(deatailsList);
		
		
		
		filterVO.setCompanyCode(logonAttributes.getCompanyCode());
		filterVO.setAirlineIdentifier(logonAttributes.getOwnAirlineIdentifier());
		filterVO.setFunctionPoint("MRA");
		filterVO.setSubSystem("M");
		filterVO.setMasterDocumentNumber(unaccountedDispatchesDetailsVO.getDsn());
		
		if(unaccountedDispatchesDetailsVO.getFlightNumber()!= null && 
				unaccountedDispatchesDetailsVO.getFlightNumber().trim().length()>0){
			
			carrierCode=unaccountedDispatchesDetailsVO.getFlightNumber().substring(0, 2);
			flightNo=unaccountedDispatchesDetailsVO.getFlightNumber().substring(2);
			log.log(Log.INFO, "carrierCode....>", carrierCode);
			log.log(Log.INFO, "flightNo....>", flightNo);
		}
		
		filterVO.setFltnum(flightNo);
		filterVO.setFltcarCode(carrierCode);
		
		if(unaccountedDispatchesDetailsVO.getFlightDate()!=null){
		String flightDate=unaccountedDispatchesDetailsVO.getFlightDate();
		filterVO.setFromDate(new LocalDate
				(LocalDate.NO_STATION,Location.NONE,false).setDate(flightDate));
		}
		
		log.log(Log.INFO, "filterVO....>", filterVO);
		manualAccountingSession.setAccountingFilterVO(filterVO);
		manualAccountingSession.setAccountingEntryVO(entryVo);
		log.log(Log.INFO, "manualAccountingSession.setAccountingEntryVO ",
				manualAccountingSession.getAccountingEntryVO());
		invocationContext.target = SCREENLOADETAILS_SUCCESS;
	
			
		}
		
	
}
