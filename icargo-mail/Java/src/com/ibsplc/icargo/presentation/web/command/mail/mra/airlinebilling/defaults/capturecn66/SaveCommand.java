/*
 * SaveCommand.java Created on Jan 22, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.airlinebilling.defaults.capturecn66;

import static com.ibsplc.xibase.server.framework.vo.AbstractVO.OPERATION_FLAG_INSERT;
import static com.ibsplc.xibase.server.framework.vo.AbstractVO.OPERATION_FLAG_UPDATE;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.AirlineCN66DetailsVO;
import com.ibsplc.icargo.framework.util.currency.CurrencyException;
import com.ibsplc.icargo.framework.util.currency.CurrencyHelper;
import com.ibsplc.icargo.framework.util.currency.Money;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.mra.MailTrackingMRADelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.airlinebilling.defaults.CaptureCN66Session;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.airlinebilling.defaults.CaptureCN66Form;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-2408
 * 
 */

public class SaveCommand extends BaseCommand {

	private Log log = LogFactory.getLogger("AirLineBillingInward ListCommand");

	private static final String CLASS_NAME = "SaveCommand";

	private static final String MODULE_NAME = "mailtracking.mra.airlinebilling.defaults";

	private static final String SCREEN_ID = "mailtracking.mra.airlinebilling.defaults.capturecn66";

	private static final String ACTION_SUCCESS = "screenload_success";

	private static final String ACTION_FAILURE = "screenload_failure";

	private static final String NO_DATA_FOR_SAVE = "mailtracking.mra.airlinebilling.msg.err.nodateforsave";

	private static final String SCREENSTATUS_SCREENLOAD = "screenload";

	private static final String EMPTY_STRING = "";

	private static final String SAVE_SUCCESS = "mailtracking.mra.airlinebilling.msg.err.cn66savesuccess";

	/**
	 * @param invocationContext
	 * @exception CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		log.entering(CLASS_NAME, "execute");
		String dou="0.00";
		CaptureCN66Session session = (CaptureCN66Session) getScreenSession(
				MODULE_NAME, SCREEN_ID);
		CaptureCN66Form form = (CaptureCN66Form) invocationContext.screenModel;
		ErrorVO error = null;
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		String companyCode = getApplicationSession().getLogonVO()
				.getCompanyCode();
		int airlineIdr = form.getAirlineIdentifier();
		String invoiceNumber = form.getInvoiceRefNo();
		String clearancePeriod = form.getClearancePeriod();
		String billingType = form.getBillingType();	//Added by A-6245 for ICRD-98364
		MailTrackingMRADelegate delegate = new MailTrackingMRADelegate();

		HashMap<String, Collection<AirlineCN66DetailsVO>> cn66details = null;
		Page<AirlineCN66DetailsVO> cN66DtlsPageVOs=null;
		//ArrayList<AirlineCN66DetailsVO> cn66s = new ArrayList<AirlineCN66DetailsVO>();
		if (session.getAirlineCN66DetailsVOs() != null
				&& session.getAirlineCN66DetailsVOs().size() > 0) {
			cN66DtlsPageVOs = session.getAirlineCN66DetailsVOs();
			log.log(Log.FINE, "final Collection===>>>", cN66DtlsPageVOs);
	
			/*int valsize = cn66details.values().size();
			for (int i = 0; i < valsize; i++) {
				cn66s.addAll((ArrayList<AirlineCN66DetailsVO>) vos.get(i));
			}*/
		}
		if (cN66DtlsPageVOs != null && cN66DtlsPageVOs.size() > 0) {
			for (AirlineCN66DetailsVO cn : cN66DtlsPageVOs) {
				//cn.setInterlineBillingType("I");
				cn.setInterlineBillingType(billingType);	//Modified by A-6245 for ICRD-98364
				cn.setAirlineIdentifier(airlineIdr);
				cn.setCompanyCode(companyCode);
				cn.setInvoiceNumber(invoiceNumber);
				cn.setClearancePeriod(clearancePeriod);
				cn.setLastUpdatedUser(getApplicationSession().getLogonVO()
						.getUserId());
				//cn.setOperationFlag(OPERATION_FLAG_INSERT);
				cn.setCarrierCode(form.getAirlineCode());
				if (OPERATION_FLAG_INSERT.equals(cn.getOperationFlag())
						|| OPERATION_FLAG_UPDATE.equals(cn.getOperationFlag())) {
					cn.setDespatchStatus("N");
				}
			}
		}
		log.log(Log.INFO, "vos for save", cN66DtlsPageVOs);
		for(AirlineCN66DetailsVO airlineCN66DetailsVO:cN66DtlsPageVOs){
			
			if(airlineCN66DetailsVO.getOperationFlag()==null){
				airlineCN66DetailsVO.setOperationFlag(AirlineCN66DetailsVO.OPERATION_FLAG_UPDATE);
			}
		}
		
		/*if (form.getScreenStatus() != null
				&& form.getScreenStatus().trim().length() > 0) {
			if ("list".equals(form.getScreenStatus())) {
				error = new ErrorVO(NO_DATA_FOR_SAVE);
				errors.add(error);
			}
		}*/
		/*if(cN66DtlsPageVOs==null){
			error = new ErrorVO(NO_DATA_FOR_SAVE);
			errors.add(error);
		}*/
		if (errors != null && errors.size() > 0) {
			log.log(Log.FINE, "!!!inside errors!= null");
			invocationContext.addAllError(errors);
			invocationContext.target = ACTION_FAILURE;
			return;
		} else {
			try {
				delegate.saveCN66Details(cN66DtlsPageVOs);
			} catch (BusinessDelegateException businessDelegateException) {
				errors = handleDelegateException(businessDelegateException);
			}
		}
		if (errors != null && errors.size() > 0) {
			invocationContext.addAllError(errors);
			invocationContext.target = ACTION_FAILURE;
			return;
		} else {
			session.removeCn66DetailsMap();
			session.removeCn66Details();
			session.removeCn66DetailsModifiedMap();
			session.removeKeyValues();
			session.removePreviousCn66Details();
			session.removemodifiedCn66Details();
			session.removeAirlineCN66DetailsVOs();
			form.setScreenStatus(SCREENSTATUS_SCREENLOAD);
			form.setInvoiceRefNo(EMPTY_STRING);
			form.setClearancePeriod(EMPTY_STRING);
			form.setAirlineCode(EMPTY_STRING);
			form.setCategory(EMPTY_STRING);
			form.setCarriageFromFilter(EMPTY_STRING);
			form.setCarriageToFilter(EMPTY_STRING);
			form.setDespatchStatusFilter(EMPTY_STRING);
			form.setBillingType(EMPTY_STRING);
			form.setBlgCurCode(EMPTY_STRING);
			form.setNetSummaryWeight(dou);//Modified for ICRD-100961
			form.setNetChargeMoneyDisp(EMPTY_STRING);
			form.setLinkStatus(EMPTY_STRING);
			form.setNetSummaryAmount(null);
			try{
	   			Money money = CurrencyHelper.getMoney("USD");
	   			money.setAmount(0.0D);
	   			form.setNetChargeMoney(money);
			}
			catch(CurrencyException currencyException){
   				log.entering(CLASS_NAME,"CurrencyException");
   			}
			error = new ErrorVO(SAVE_SUCCESS);
			errors.add(error);
			invocationContext.addAllError(errors);
		}
		invocationContext.target = ACTION_SUCCESS;
		log.exiting(CLASS_NAME, "execute");
	}
}
