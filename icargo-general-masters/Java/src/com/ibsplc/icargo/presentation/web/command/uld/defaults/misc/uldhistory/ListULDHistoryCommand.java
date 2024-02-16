/*
 * ListULDHistoryCommand.java Created on Oct 18, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.presentation.web.command.uld.defaults.misc.uldhistory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Set;
import java.util.Map;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDHistoryVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.misc.ULDHistorySession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.misc.ULDHistoryForm;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.icargo.presentation.delegate.shared.uld.ULDDelegate;
import com.ibsplc.icargo.presentation.delegate.uld.defaults.ULDDefaultsDelegate;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;

/**
 * 
 * @author A-2619
 * 
 */

public class ListULDHistoryCommand extends BaseCommand {

	private Log log;

	private static final String SCREEN_ID = "uld.defaults.uldhistory";

	private static final String MODULE_NAME = "uld.defaults";

	private static final String LIST_SUCCESS = "uldhistory_list_success";

	private static final String LIST_FAILURE = "uldhistory_list_failure";

	public ListULDHistoryCommand() {
		log = LogFactory.getLogger("ListULDHistoryCommand");
	}

	/**
	 * @author A-2619
	 * 
	 * @param invocationContext
	 * @return
	 * @throws CommandInvocationException
	 */

	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		log.entering("ListULDHistoryCommand", "execute");
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		ULDHistoryVO uldHistoryVO = null;
		LocalDate fromDate = new LocalDate(LocalDate.NO_STATION, Location.NONE,
				false);
		LocalDate toDate = new LocalDate(LocalDate.NO_STATION, Location.NONE,
				false);
		LocalDate flightDate = new LocalDate(LocalDate.NO_STATION,
				Location.NONE, false);

		ULDHistoryForm uldHistoryForm = (ULDHistoryForm) invocationContext.screenModel;
		ApplicationSessionImpl applicationSessionImpl = getApplicationSession();
		LogonAttributes logonAttributes = applicationSessionImpl.getLogonVO();

		errors = validateForm(uldHistoryForm);
		if (errors != null && errors.size() > 0) {
			invocationContext.addAllError(errors);
			invocationContext.target = LIST_SUCCESS;
			return;
		}
		ULDHistorySession uldHistorySession = getScreenSession(MODULE_NAME,
				SCREEN_ID);

		// Added by Sever for handling PageAwareMultiMapper implementation
		HashMap<String, String> indexMap = null;
		HashMap<String, String> finalMap = null;
		if (uldHistorySession.getIndexMap() != null) {
			indexMap = uldHistorySession.getIndexMap();
		}

		if (indexMap == null) {
			log.log(Log.INFO, "INDEX MAP IS NULL");
			indexMap = new HashMap<String, String>();
			indexMap.put("1", "1");
		}

		int nAbsoluteIndex = 0;
		String displayPage = uldHistoryForm.getDisplayPage();
		String strAbsoluteIndex = indexMap.get(displayPage);
		if (strAbsoluteIndex != null) {
			nAbsoluteIndex = Integer.parseInt(strAbsoluteIndex);
		}
		log.log(Log.INFO, "displayPage~~~~~~~~~~>>" + displayPage);
		log.log(Log.INFO, "nAbsoluteIndex~~~~~~~~~~>>" + nAbsoluteIndex);
		// Server ends

		// validation of ULD Number begins
		if (uldHistoryForm.getUldNumber() != null
				&& uldHistoryForm.getUldNumber().trim().length() > 0) {
			try {
				new ULDDelegate().validateULD(logonAttributes.getCompanyCode(),
						uldHistoryForm.getUldNumber());
			} catch (BusinessDelegateException businessDelegateException) {
				errors = handleDelegateException(businessDelegateException);
			}

			if (errors != null && errors.size() > 0) {
				ErrorVO error = new ErrorVO("uld.defaults.uldhistory.invaliduldnumber");
				errors.add(error);
				invocationContext.addAllError(errors);
				invocationContext.target = LIST_FAILURE;
				return;
			}
		}

		// validation of ULD Number ends

		try {

			uldHistoryVO = new ULDHistoryVO();

			if (uldHistoryForm.getFromDate() != null
					&& uldHistoryForm.getFromDate().trim().length() != 0) {
				fromDate.setDate(uldHistoryForm.getFromDate());
				uldHistoryVO.setFromDate(fromDate);
			}
			if (uldHistoryForm.getToDate() != null
					&& uldHistoryForm.getToDate().trim().length() != 0) {
				toDate.setDate(uldHistoryForm.getToDate());
				uldHistoryVO.setToDate(toDate);
			}
			if (uldHistoryForm.getFlightDate() != null
					&& uldHistoryForm.getFlightDate().trim().length() != 0) {
				flightDate.setDate(uldHistoryForm.getFlightDate());
				uldHistoryVO.setFlightDate(flightDate);
			}
			uldHistoryVO.setCompanyCode(logonAttributes.getCompanyCode());
			uldHistoryVO.setUldNumber(uldHistoryForm.getUldNumber());
			uldHistoryVO.setUldStatus(uldHistoryForm.getStatus());
			uldHistoryVO.setCarrierCode(uldHistoryForm.getCarrierCode());
			uldHistoryVO.setFlightNumber(uldHistoryForm.getFlightNumber());
			uldHistoryVO.setFromStation(uldHistoryForm.getFromStation());
			uldHistoryVO.setPageNumber(Integer.parseInt(displayPage));

			log.log(Log.FINE, "uldHistoryVO built..." + uldHistoryVO);
			Page<ULDHistoryVO> uldHistoryVOs = new ULDDefaultsDelegate()
					.listULDHistory(uldHistoryVO);

			if (uldHistoryVOs == null
					|| (uldHistoryVOs != null && uldHistoryVOs.size() == 0)) {
				log.log(log.FINE, "-----------No record found-----------");
				uldHistorySession.setUldHistoryVOs(null);
				ErrorVO error = null;
				error = new ErrorVO(
						"uld.defaults.uldhistory.msg.err.norecordfound");
				error.setErrorDisplayType(ErrorDisplayType.ERROR);
				errors.add(error);
				invocationContext.addAllError(errors);
				invocationContext.target = LIST_FAILURE;
				return;
			} else {
				uldHistoryForm
						.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_DETAIL);
				uldHistorySession.setUldHistoryVOs(uldHistoryVOs);
			}

			log.log(Log.INFO,
					"uldHistoryVOs IN COMMAND CLASS IS ---------*****"
							+ uldHistoryVOs);
			invocationContext.target = LIST_SUCCESS;
		} catch (BusinessDelegateException businessDelegateException) {
			businessDelegateException.getMessage();
		}

		// Added by Server on handling PageAwareMultiMapper
		finalMap = indexMap;
		if (uldHistorySession.getUldHistoryVOs() != null) {
			finalMap = buildIndexMap(indexMap, uldHistorySession
					.getUldHistoryVOs());
		}
		uldHistorySession.setIndexMap(finalMap);
		log.log(Log.INFO, "GOING TO PUT FINAL MAP IN SESSION---------*****"
				+ finalMap);
		// Server ends

	}

	// Added by Server on handling PageAwareMultiMapper
	/**
	 * method to bulid the hashmap to maintain absoluteindex
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
		boolean isPageExits = false;
		Set<Map.Entry<String, String>> set = existingMap.entrySet();
		for (Map.Entry<String, String> entry : set) {
			String pageNum = entry.getKey();
			if (pageNum.equals(indexPage)) {
				isPageExits = true;
			}
		}
		if (!isPageExits) {
			finalMap.put(indexPage, String.valueOf(page.getAbsoluteIndex()));
		}
		return finalMap;
	}

	/**
	 * This method is used to do form validations
	 * 
	 * @param form
	 * @return
	 */
	private Collection<ErrorVO> validateForm(ULDHistoryForm form) {
		Collection<ErrorVO> errorsVos = new ArrayList<ErrorVO>();
		ErrorVO error = null;

		return errorsVos;
	}

}
