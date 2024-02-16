/*
 * BlgLineListCommand.java Created on Feb 28, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.defaults.maintainbillingmatrix;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.BillingLineFilterVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.BillingLineVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.mra.MailTrackingMRADelegate;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.MaintainBillingMatrixSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.BillingMatrixForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-2398
 * 
 */
public class BlgLineListCommand extends BaseCommand {

	private static final String MODULE = "mailtracking.mra.defaults";

	private static final String SCREENID = "mailtracking.mra.defaults.maintainbillingmatrix";

	private static final String LISTDETAILS_SUCCESS = "list_success";

	private static final String LISTDETAILS_FAILURE = "list_failure";

	private static final String KEY_RATE_STATUS = "mra.gpabilling.ratestatus";

	private static final String CLASS_NAME = "BlgLineListCommand";

	private Log log = LogFactory.getLogger("MRA_DEFAULTS");

	/**
	 * execute method
	 * 
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {

		log.entering(CLASS_NAME, "execute");
		BillingMatrixForm form = (BillingMatrixForm) invocationContext.screenModel;
		// BillingLineForm blgLineForm =
		// (BillingLineForm)invocationContext.screenModel;
		MaintainBillingMatrixSession session = (MaintainBillingMatrixSession) getScreenSession(
				MODULE, SCREENID);

		ApplicationSessionImpl applicationSessionImpl = getApplicationSession();
		LogonAttributes logonAttributes = applicationSessionImpl.getLogonVO();
		String companyCode = logonAttributes.getCompanyCode().toUpperCase();
		Page<BillingLineVO> billingLineDetails = null;
		BillingLineFilterVO blgLineFilterVO = new BillingLineFilterVO();
		blgLineFilterVO.setCompanyCode(companyCode);
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();

		errors.addAll(setOneTimeValues(session));
		// for PageAware MultiMap---starts

		if (invocationContext.getErrors() != null) {
			log.log(Log.FINE, "Erros....size..:", invocationContext.getErrors().size());
			invocationContext.target = LISTDETAILS_FAILURE;
			return;
		}

		HashMap<String, String> indexMap = null;
		HashMap<String, String> finalMap = null;
		if (session.getIndexMap() != null) {
			indexMap = session.getIndexMap();
		}
		if (indexMap == null) {
			log.log(Log.INFO, "INDEX MAP IS NULL");
			indexMap = new HashMap<String, String>();
			indexMap.put("1", "1");
		}
		int nAbsoluteIndex = 0;
		String displayPage = form.getDisplayPage();

		String strAbsoluteIndex = indexMap.get(displayPage);
		if (strAbsoluteIndex != null) {
			nAbsoluteIndex = Integer.parseInt(strAbsoluteIndex);
		}
		blgLineFilterVO.setAbsoluteIndex(nAbsoluteIndex);
		blgLineFilterVO.setPageNumber(Integer.parseInt(displayPage));
		// for PageAware MultiMap---ends
		
		if (form.getGpaCode() != null
				&& form.getGpaCode().trim().length() > 0) {
			blgLineFilterVO.setPoaCode(form.getGpaCode().trim().toUpperCase());
		}
		if (form.getAirlineCode() != null
				&& form.getAirlineCode().trim().length() > 0) {
			blgLineFilterVO.setAirlineCode(form.getAirlineCode().trim().toUpperCase());
		}
		if (form.getBlgMatrixID() != null
				&& form.getBlgMatrixID().trim().length() > 0) {
			blgLineFilterVO.setBillingMatrixId(form.getBlgMatrixID().trim()
					.toUpperCase());
		
			try {
				log.log(Log.FINE, "FilterVO is ....", blgLineFilterVO);
				billingLineDetails = new MailTrackingMRADelegate()
						.findBillingLineDetails(blgLineFilterVO);

			} catch (BusinessDelegateException businessDelegateException) {
				businessDelegateException.getMessage();
				errors = handleDelegateException(businessDelegateException);
			}
		}
		if (errors.size() == 0) {
/*			int count=0;
			for(BillingLineVO billVO: billingLineDetails){
				count++;
			}
			billingLineDetails.setTotalRecordCount(count);*/
			log.log(Log.FINE, "Page returned....", billingLineDetails);
			session.setBillingLineDetails(billingLineDetails);
			log.exiting(CLASS_NAME, "execute");
			invocationContext.target = LISTDETAILS_SUCCESS;
		} else {
			invocationContext.addAllError(errors);
			invocationContext.target = LISTDETAILS_FAILURE;
		}

		finalMap = indexMap;
		if (session.getBillingLineDetails() != null) {
			finalMap = buildIndexMap(indexMap, session.getBillingLineDetails());
		}
		session.setIndexMap(finalMap);

		log.log(Log.INFO, "GOING TO PUT FINAL MAP IN SESSION---------*****",
				finalMap);
		return;
	}

	/**
	 * Method to bulid the hashmap to maintain absoluteindex
	 * 
	 * @param existingMap
	 *            HashMap<String, String>
	 * @param page
	 *            Page
	 * @return HashMap<String, String>
	 */
	private HashMap<String, String> buildIndexMap(
			HashMap<String, String> existingMap, Page page) {
		HashMap<String, String> finalMap = existingMap;
		String indexPage = String.valueOf((page.getPageNumber() + 1));
		boolean pageExits = false;
		Set<Map.Entry<String, String>> set = existingMap.entrySet();
		for (Map.Entry<String, String> entry : set) {
			String pageNum = entry.getKey();
			if (pageNum.equals(indexPage)) {
				pageExits = true;
			}
		}
		if (!pageExits) {
			finalMap.put(indexPage, String.valueOf(page.getAbsoluteIndex()));
		}

		return finalMap;
	}

	private Collection<ErrorVO> setOneTimeValues(
			MaintainBillingMatrixSession session) {
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		Map oneTimeHashMap = null;
		Collection<String> oneTimeActiveStatusList = new ArrayList<String>();
		ApplicationSessionImpl applicationSessionImpl = getApplicationSession();
		LogonAttributes logonAttributes = applicationSessionImpl.getLogonVO();

		String companyCode = logonAttributes.getCompanyCode().toUpperCase();

		/** adding attributes to map for passing to SharedDefaultsDelegate */

		oneTimeActiveStatusList.add(KEY_RATE_STATUS);

		try {
			/** getting collections of OneTimeVOs */
			oneTimeHashMap = new SharedDefaultsDelegate().findOneTimeValues(
					companyCode, oneTimeActiveStatusList);
		} catch (BusinessDelegateException e) {
			e.getMessage();
			errors = handleDelegateException(e);
		}

		/** setting OneTime hashmap to session */
		// log.log(Log.INFO," the oneTimeHashMap after server call is
		// "+oneTimeHashMap);
		session
				.setOneTimeVOs((HashMap<String, Collection<OneTimeVO>>) oneTimeHashMap);

		return errors;
	}

}
