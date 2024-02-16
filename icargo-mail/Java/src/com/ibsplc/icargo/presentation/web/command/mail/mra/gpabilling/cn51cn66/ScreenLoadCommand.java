/*
 * ScreenLoadCommand.java Created on Dec 28, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.gpabilling.cn51cn66;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.CN51CN66FilterVO;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.CN51CN66VO;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.CN51DetailsVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.gpabilling.ListCN51CN66Session;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.gpabilling.ListCN51CN66Form;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-1556
 *
 */
public class ScreenLoadCommand extends BaseCommand {

	private static final String MODULE_NAME = "mailtracking.mra.gpabilling";

	private static final String SCREENID = "mailtracking.mra.gpabilling.listcn51cn66";

	private static final String SCREENLOADETAILS_SUCCESS = "screenload_success";

	private static final String CLASS_NAME = "ScreenLoadCommand";

	private static final String CATEGORY_ONETIME = "mailtracking.defaults.mailcategory";

	private static final String BILLINGSTATUS_ONETIME = "mailtracking.mra.gpabilling.gpabillingstatus";

	private static final String FROM_LIST_ACCOUNTING_SCREEN = "from_accountingScreen";

	private static final String SYS_PARA_ACC_ENTRY = "cra.accounting.isaccountingenabled";

	/**
	 * Added as part of ICRD-189966
	 * Parameter code for system parameter -Override Rounding value in MRA
	 */
	private static final String SYS_PARA_OVERRIDE_ROUNDING = "mailtracking.mra.overrideroundingvalue";

	private Log log = LogFactory.getLogger("MRA_gpaBILLING");
	/*
	 * Parameter code for system parameter - level for data import to mra
	 */
	private static final String LEVEL_FOR_DATA_IMPORT_TO_MRA="mra.defaults.levelfordataimporttomra";

	/**
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {

		Log log = LogFactory.getLogger("MRA_GPABILLING");
		log.entering(CLASS_NAME, "execute");
		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		String companyCode = logonAttributes.getCompanyCode();
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		ListCN51CN66Form listCN51CN66Form = (ListCN51CN66Form) invocationContext.screenModel;
		listCN51CN66Form.setAirlineCode(getApplicationSession().getLogonVO()
				.getOwnAirlineCode());
		CN51CN66VO cN51CN66Vo = null;
		ListCN51CN66Session session = (ListCN51CN66Session) getScreenSession(
				MODULE_NAME, SCREENID);
		Map<String, Collection<OneTimeVO>> oneTimeValues = fetchOneTimeDetails(companyCode);
		Map<String, String> systemParameterValues = null;
		try {

 			systemParameterValues=new SharedDefaultsDelegate().findSystemParameterByCodes(getSystemParameterTypes());
 		} catch (BusinessDelegateException e) {

 			errors=handleDelegateException( e );

 		}
 		for (Map.Entry<String, String> entry : systemParameterValues.entrySet()) {
 			listCN51CN66Form.setParameterValue(entry.getValue());

    		}
		session = updateOneTimeInSession(session, oneTimeValues);

		if (!FROM_LIST_ACCOUNTING_SCREEN.equals(listCN51CN66Form
				.getFromScreen())) {
			listCN51CN66Form
					.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
			session.setCN51CN66VO(null);
			session.setCN66VOs(null);
			session.setCN51DetailsVOs(null);
			listCN51CN66Form.setBtnStatus("N");
			listCN51CN66Form.setSaveBtnStatus("N");
			listCN51CN66Form.setFileName("");
			listCN51CN66Form.setGpaType("");
			listCN51CN66Form.setInvStatusDesc("");
		} else {
			populateFormFields(session.getCN51CN66FilterVO(), listCN51CN66Form);
			cN51CN66Vo = session.getCN51CN66VO();
			//Modified by A-7794 as part of ICRD-239212
			if (cN51CN66Vo != null && null != cN51CN66Vo.getCn51DetailsVOs()) {
				String invoiceStatus ="";
				for (CN51DetailsVO cn51VO : cN51CN66Vo.getCn51DetailsVOs()) {
					if(cn51VO.getInvoiceStatus() !=null && cn51VO.getInvoiceStatus().trim().length() > 0 &&
							invoiceStatus.trim().length() ==0){
						invoiceStatus = cn51VO.getInvoiceStatus();
					}
					if (cn51VO.getTotalBilledAmount() != null) {

						listCN51CN66Form.setTotalBilledAmount(cn51VO
								.getTotalBilledAmount().getAmount());
					}

				}
				if(invoiceStatus != null && invoiceStatus.trim().length() > 0){
					listCN51CN66Form.setInvoiceStatus(invoiceStatus);
				}
			}

		}
		// code for acc entry sys para starts
		Collection<String> systemParameterCodes = new ArrayList<String>();

		systemParameterCodes.add(SYS_PARA_ACC_ENTRY);
		systemParameterCodes.add(SYS_PARA_OVERRIDE_ROUNDING);//Added as part of ICRD-189966

		Map<String, String> systemParameters = null;

		try {

			systemParameters = new SharedDefaultsDelegate()
					.findSystemParameterByCodes(systemParameterCodes);

		} catch (BusinessDelegateException e) {
			e.getMessage();
			errors = handleDelegateException(e);
		}
		String accountingEnabled = null;
		if (systemParameters != null && systemParameters.size() > 0) {
			accountingEnabled = (systemParameters.get(SYS_PARA_ACC_ENTRY));
		}
		log.log(Log.INFO, "IS acc enabled--->", accountingEnabled);
		if ("N".equals(accountingEnabled)) {
			listCN51CN66Form.setAccEntryFlag("N");
		} else {
			listCN51CN66Form.setAccEntryFlag("Y");
		}

		//code for acc entry sys para ends
		//Added as part of ICRD-189966 starts
		session.setSystemparametres((HashMap<String, String>)systemParameters);//Added as part of ICRD-189966
		//Added as part of ICRD-189966 ends
		if (errors != null && errors.size() > 0) {
			invocationContext.addAllError(errors);
		}
		invocationContext.target = SCREENLOADETAILS_SUCCESS;
		log.exiting(CLASS_NAME, "execute");

	}

	/*
	 *
	 */
	private void populateFormFields(CN51CN66FilterVO filterVo,
			ListCN51CN66Form form) {
		if (filterVo.getGpaCode() != null
				&& filterVo.getGpaCode().trim().length() > 0) {
			form.setGpaCode(filterVo.getGpaCode());
		}
		if (filterVo.getGpaName() != null
				&& filterVo.getGpaName().trim().length() > 0) {
			form.setGpaName(filterVo.getGpaName());
		}
		if (filterVo.getInvoiceNumber() != null
				&& filterVo.getInvoiceNumber().trim().length() > 0) {
			form.setInvoiceNumber(filterVo.getInvoiceNumber());
		}
		if(filterVo.getCategory()!=null && filterVo.getCategory().trim().length() > 0)
		{
			form.setCategory(filterVo.getCategory());//added by A-9092 for IASCB-81849
		}
	}

	/**
	 * Helper method to get the one time data.
	 * @param companyCode String
	 * @return Map<String, Collection<OneTimeVO>>
	 */
	private Map<String, Collection<OneTimeVO>> fetchOneTimeDetails(
			String companyCode) {

		log.entering(CLASS_NAME, "fetchOneTimeDetails");
		Map<String, Collection<OneTimeVO>> hashMap = new HashMap<String, Collection<OneTimeVO>>();
		Collection<String> oneTimeList = buildOneTimeList();
		try {
			SharedDefaultsDelegate sharedDefaultsDelegate = new SharedDefaultsDelegate();
			hashMap = sharedDefaultsDelegate.findOneTimeValues(companyCode,
					oneTimeList);
		} catch (BusinessDelegateException businessDelegateException) {
			businessDelegateException.getMessage();
			handleDelegateException(businessDelegateException);
		}

		log.exiting(CLASS_NAME, "fetchOneTimeDetails");
		return hashMap;
	}

	/**
	 * Helper method to build the list of required one time values.
	 *
	 * @return Collection<String>
	 */
	private Collection<String> buildOneTimeList() {

		log.entering(CLASS_NAME, "buildOneTimeList");
		Collection<String> oneTimeList = new ArrayList<String>();
		oneTimeList.add(CATEGORY_ONETIME);
		oneTimeList.add(BILLINGSTATUS_ONETIME);
		log.exiting(CLASS_NAME, "buildOneTimeList");
		return oneTimeList;
	}

	/**
	 * Method to update the one time values in session.
	 *
	 * @param session
	 * @param hashMap
	 * @return MaintainFlightScheduleSessionInterface
	 */

	private ListCN51CN66Session updateOneTimeInSession(
			ListCN51CN66Session session,
			Map<String, Collection<OneTimeVO>> hashMap) {

		log.entering(CLASS_NAME, "updateOneTimeInSession");

		session.setOneTimeVOs((HashMap<String, Collection<OneTimeVO>>) hashMap);

		log.exiting(CLASS_NAME, "updateOneTimeInSession");
		return session;
	}
	 private Collection<String> getSystemParameterTypes(){

	    	ArrayList<String> systemparameterTypes = new ArrayList<String>();

	    	systemparameterTypes.add(LEVEL_FOR_DATA_IMPORT_TO_MRA);


	    	return systemparameterTypes;
	      }
}
