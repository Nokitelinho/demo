/*
 * UldExternalMovementHistoryCommand.java Created on Feb 1, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services(P) Ltd.
 * Use is subject to license terms.
 * 
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.misc.uldmovementhistory;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDMovementFilterVO;
import com.ibsplc.icargo.business.uld.defaults.vo.ULDNumberVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.uld.defaults.ULDDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.misc.ListULDMovementSession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.misc.ListULDMovementForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import com.ibsplc.xibase.util.time.DateUtilities;

/**
 * This command class is invoked on the list button the ULDMovementHistory
 * screen
 * 
 * @author A-3093
 */
public class UldExternalMovementHistoryCommand extends BaseCommand {

	private Log log = LogFactory.getLogger("ULD_MOVEMENT_HISTORY");

	private static final String MODULE = "uld.defaults";

	private static final String SCREENID = "uld.defaults.misc.listuldmovement";

	private static final String SCREEN_SUCCESS = "screen_success";

	private static final String SCREEN_FAILURE = "screen_failure";

	private static final String LISTSTATUS = "recorduld";

	/**
	 * execute method
	 * 
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {

		log.entering("UldExternalMovementHistoryCommand", "execute");
		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		String companyCode = logonAttributes.getCompanyCode().toUpperCase();
		ListULDMovementForm listULDMovementForm = (ListULDMovementForm) invocationContext.screenModel;
		ListULDMovementSession listUldMovementSession = getScreenSession(
				MODULE, SCREENID);
		ULDMovementFilterVO uldMovementFilterVO = new ULDMovementFilterVO();
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();

		log.log(Log.INFO, "companyCode---->", companyCode);
		log.log(Log.INFO, "companyCode---->", listULDMovementForm.getUldNumber());
		listULDMovementForm.setTransactionFlag("Y");

		ULDNumberVO uldNumberVO = new ULDNumberVO();

		uldMovementFilterVO.setCompanyCode(companyCode);
		uldMovementFilterVO.setUldNumber(listULDMovementForm.getUldNumber()
				.toUpperCase());
		if ((listULDMovementForm.getFromDate() != null)
				&& (listULDMovementForm.getFromDate().trim().length() > 0)) {
			LocalDate fromdate = new LocalDate(LocalDate.NO_STATION,
					Location.NONE, false);
			fromdate.setDate(listULDMovementForm.getFromDate());
			uldMovementFilterVO.setFromDate(fromdate);
		}
			if((listULDMovementForm.getToDate() != null)
			&& (listULDMovementForm.getToDate().trim().length() > 0)){
			LocalDate todate = new LocalDate(LocalDate.NO_STATION,
					Location.NONE, false);
			todate.setDate(listULDMovementForm.getToDate());
			uldMovementFilterVO.setToDate(todate);

		}
		
		log.log(Log.FINE, "uldNumberVOuldNumberVOuldNumberVO11-->",
				uldMovementFilterVO);
		errors = validateForm(listULDMovementForm, listUldMovementSession,
				companyCode);
		
		if(errors!=null && errors.size()>0){
			invocationContext.addAllError(errors);
			invocationContext.target = SCREEN_FAILURE;
			listULDMovementForm.setIsUldValid(ULDMovementFilterVO.FLAG_NO);	
			listULDMovementForm.setTransactionFlag(ULDMovementFilterVO.FLAG_NO);
			listUldMovementSession.setUldMovementFilterVO(uldMovementFilterVO);
			return;
		}else{
			listULDMovementForm.setIsUldValid(ULDMovementFilterVO.FLAG_YES);
			listULDMovementForm.setTransactionFlag(ULDMovementFilterVO.FLAG_YES);
		}

		try {
			uldNumberVO = new ULDDefaultsDelegate()
					.findULDHistoryCounts(uldMovementFilterVO);
		} catch (BusinessDelegateException businessDelegateException) {
			log.log(Log.FINE, "\n\n\ninside handle delegate exception");
			errors = handleDelegateException(businessDelegateException);
		}

		log.log(Log.FINE, "uldNumberVOuldNumberVOuldNumberVO-->", uldNumberVO);
		listUldMovementSession.setUldMovementFilterVO(uldMovementFilterVO);
		listUldMovementSession.setTotalRecords(uldNumberVO.getNoOfMovements());
		if (uldNumberVO != null) {
			listULDMovementForm.setNoOfLoanTxns(uldNumberVO.getNoOfLoanTxns());
			listULDMovementForm
					.setNoOfMovements(uldNumberVO.getNoOfMovements());
			listULDMovementForm.setNoOfTimesDmged(uldNumberVO
					.getNoOfTimesDmged());
			listULDMovementForm.setNoOfTimesRepaired(uldNumberVO
					.getNoOfTimesRepaired());
		}

		if (errors != null && errors.size() > 0) {
			invocationContext.addAllError(errors);
			invocationContext.target = SCREEN_FAILURE;
			log.log(Log.INFO, "invocionContxtarg-", invocationContext.target);
			return;
		} else {
			invocationContext.target = SCREEN_SUCCESS;
		}
	}

	private Collection<ErrorVO> validateForm(
			ListULDMovementForm listULDMovementForm,
			ListULDMovementSession listUldMovementSession, String companyCode) {
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		log.entering("ListULDMovementHistoryCommand", "validateForm");
		ErrorVO error = null;
		if (listULDMovementForm.getUldNumber() == null
				|| listULDMovementForm.getUldNumber().trim().length() == 0) {
			error = new ErrorVO("uld.defaults.listuldmovement.uldno.mandatory");
			error.setErrorDisplayType(ErrorDisplayType.ERROR);
			errors.add(error);
		} else {
			if (listULDMovementForm.getUldNumber() != null
					&& listULDMovementForm.getUldNumber().trim().length() > 0) {
				try {
					new ULDDefaultsDelegate().validateULDFormat(companyCode,
							listULDMovementForm.getUldNumber().toUpperCase());

				} catch (BusinessDelegateException businessDelegateException) {

					errors = handleDelegateException(businessDelegateException);
				}
			}
		}
		/*if (listUldMovementSession.getUldMovementFilterVO() != null) {
			if ((listUldMovementSession.getUldMovementFilterVO().getFromDate() != null)
					&& (listUldMovementSession.getUldMovementFilterVO()
							.getToDate() == null)) {
				error = new ErrorVO(
						"uld.defaults.listuldmovement.todate.mandatory");
				error.setErrorDisplayType(ErrorDisplayType.ERROR);
				errors.add(error);
			}
			if ((listUldMovementSession.getUldMovementFilterVO().getToDate() != null)
					&& (listUldMovementSession.getUldMovementFilterVO()
							.getFromDate() == null)) {
				error = new ErrorVO(
						"uld.defaults.listuldmovement.fromdate.mandatory");
				error.setErrorDisplayType(ErrorDisplayType.ERROR);
				errors.add(error);
			}
		}*/
		
		if (((!("").equals(listULDMovementForm.getFromDate())) && listULDMovementForm
				.getFromDate() != null)
				&& ((!("").equals(listULDMovementForm.getToDate())) && listULDMovementForm
						.getToDate() != null)) {
			if (!listULDMovementForm.getFromDate().equals(
					listULDMovementForm.getToDate())) {
				if (!DateUtilities.isLessThan(
						listULDMovementForm.getFromDate(), listULDMovementForm
								.getToDate(), "dd-MMM-yyyy")) {
					ErrorVO errorVO = new ErrorVO(
							"uld.defaults.listuldmovement.fromdateearliertodate");
					errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
					errors.add(errorVO);
				}
			}
		}

		log.exiting("ListULDMovementHistoryCommand", "validateForm");
		return errors;
	}

}
