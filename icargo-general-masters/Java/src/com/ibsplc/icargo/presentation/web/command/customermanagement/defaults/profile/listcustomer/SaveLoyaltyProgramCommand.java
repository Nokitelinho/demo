/*
 * SaveLoyaltyProgramCommand.java Created on Aug 5, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.customermanagement.defaults.profile.listcustomer;

import static com.ibsplc.xibase.server.framework.vo.AbstractVO.OPERATION_FLAG_DELETE;
import static com.ibsplc.xibase.server.framework.vo.AbstractVO.OPERATION_FLAG_INSERT;
import static com.ibsplc.xibase.server.framework.vo.AbstractVO.OPERATION_FLAG_UPDATE;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.customermanagement.defaults.loyalty.vo.AttachLoyaltyProgrammeVO;
import com.ibsplc.icargo.framework.session.ApplicationSession;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.customermanagement.defaults.CustomerMgmntDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.customermanagement.defaults.profile.ListCustomerSession;
import com.ibsplc.icargo.presentation.web.struts.form.customermanagement.defaults.profile.ListCustomerForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * 
 * @author A-2046
 * 
 */
public class SaveLoyaltyProgramCommand extends AddLoyaltyProgrammeCommand {

	private static final String LIST_SUCCESS = "list_success";

	private static final String SAVE_FAILURE = "save_failure";

	private static final String MODULENAME = "customermanagement.defaults";

	private static final String SCREENID = "customermanagement.defaults.customerlisting";

	private static final String BLANK = "";

	private Log log = LogFactory.getLogger("customerlisting");

/**
 * @param invocationContext
 * @throws CommandInvocationException
 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		log.entering("customerlisting", "SaveLoyaltyProgramCommand");
		ListCustomerForm form = (ListCustomerForm) invocationContext.screenModel;
		ListCustomerSession session = getScreenSession(MODULENAME, SCREENID);
		ErrorVO errorVO = null;
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		String[] programme = form.getLoyaltyProgramme();
		String[] prgmFromDate = form.getProgramFromDate();
		String[] prgmToDate = form.getProgramToDate();
		String[] attachFromDate = form.getAttachFromDate();
		String[] attachToDate = form.getAttachToDate();
		ApplicationSession appSession = getApplicationSession();
		ArrayList<AttachLoyaltyProgrammeVO> loyaltyVOs = session
				.getLoyaltyVOs();
		log.log(Log.FINE, "\n SizeofVO", loyaltyVOs);
		if (loyaltyVOs != null && loyaltyVOs.size() > 0) {
			int index = 0;
			for (AttachLoyaltyProgrammeVO programVO : loyaltyVOs) {
				if (!OPERATION_FLAG_DELETE.equals(programVO.getOperationFlag())
						&& !OPERATION_FLAG_INSERT.equals(programVO
								.getOperationFlag())) {
					if (!programVO.getFromDate().toDisplayDateOnlyFormat()
							.equalsIgnoreCase(attachFromDate[index])
							|| !programVO.getToDate().toDisplayDateOnlyFormat()
									.equalsIgnoreCase(attachToDate[index])) {
						programVO.setOperationFlag(OPERATION_FLAG_UPDATE);
					}

				}

				programVO.setLoyaltyProgrammeCode(programme[index]);
				if (!BLANK.equals(prgmFromDate[index])) {
					LocalDate pgmFromDate = new LocalDate(getApplicationSession().getLogonVO().getStationCode(),Location.STN, false);
					pgmFromDate.setDate(prgmFromDate[index]);
					programVO.setLoyaltyFromDate(pgmFromDate);
				}
				if (!BLANK.equals(prgmToDate[index])) {
					LocalDate pgmToDate = new LocalDate(getApplicationSession().getLogonVO().getStationCode(),Location.STN, false);
					pgmToDate.setDate(prgmToDate[index]);
					programVO.setLoyaltyToDate(pgmToDate);
				}
				if (!BLANK.equals(attachFromDate[index])) {
					LocalDate attachFrmDate = new LocalDate(getApplicationSession().getLogonVO().getStationCode(),Location.STN, false);
					attachFrmDate.setDate(attachFromDate[index]);
					programVO.setFromDate(attachFrmDate);
				}

				if (!BLANK.equals(attachToDate[index])) {
					LocalDate attachedToDate = new LocalDate(getApplicationSession().getLogonVO().getStationCode(),Location.STN, false);
					attachedToDate.setDate(attachToDate[index]);
					programVO.setToDate(attachedToDate);
				}
				index++;

			}
		} else {
			errorVO = new ErrorVO(
					"customermanagement.defaults.msg.err.addatleastonerow");
			errors.add(errorVO);
			invocationContext.addAllError(errors);
			invocationContext.target = SAVE_FAILURE;
			return;

		}
		
		errors = validateEndDates(loyaltyVOs);
		if (errors != null && errors.size() > 0) {
			invocationContext.addAllError(errors);
			invocationContext.target = SAVE_FAILURE;
			return;
		}

		errors = validateDateFields(loyaltyVOs);
		if (errors != null && errors.size() > 0) {
			invocationContext.addAllError(errors);
			invocationContext.target = SAVE_FAILURE;
			return;
		}

	
		errors = validateLoyaltyDates(loyaltyVOs, form,appSession);
		if (errors != null && errors.size() > 0) {
			invocationContext.addAllError(errors);
			invocationContext.target = SAVE_FAILURE;
			return;
		}

		CustomerMgmntDefaultsDelegate delegate = new CustomerMgmntDefaultsDelegate();
		Collection<AttachLoyaltyProgrammeVO> loyaltyVOToSave = new ArrayList<AttachLoyaltyProgrammeVO>();
		for (AttachLoyaltyProgrammeVO loyaltyVO : loyaltyVOs) {
			if ("N".equals(loyaltyVO.getGroupFlag())) {
				loyaltyVOToSave.add(loyaltyVO);
			}
		}
		// for entry points validation
		ArrayList<String> programmes = new ArrayList<String>();
		log.log(Log.FINE, "Main Collection-------------->", loyaltyVOs);
		log.log(Log.FINE, "\n\n\nvos for saving-------------->",
				loyaltyVOToSave);
		for (AttachLoyaltyProgrammeVO loyaltyVO1 : loyaltyVOToSave) {
			int index = 0;
			for (AttachLoyaltyProgrammeVO loyaltyVO2 : loyaltyVOs) {
				if (!OPERATION_FLAG_DELETE
						.equals(loyaltyVO1.getOperationFlag())
						&& !OPERATION_FLAG_DELETE.equals(loyaltyVO2
								.getOperationFlag())) {
					if (loyaltyVO1.getLoyaltyProgrammeCode().equals(
							loyaltyVO2.getLoyaltyProgrammeCode())) {
						index++;

					}
					if (index > 1) {
						programmes.add(loyaltyVO1.getLoyaltyProgrammeCode());
					}
				}
			}
		}
		log.log(Log.FINE, "\n\n\nprogrammes list_--------------->", programmes);
		for (AttachLoyaltyProgrammeVO loyaltyVO1 : loyaltyVOToSave) {
			if (OPERATION_FLAG_INSERT.equals(loyaltyVO1.getOperationFlag())) {
				if (programmes != null && programmes.size() > 0) {
					if (!programmes.contains(loyaltyVO1
							.getLoyaltyProgrammeCode())) {
						loyaltyVO1.setEntryPoints(true);
						log.log(Log.FINE, "-----inside if---");
					}
				} else {
					log.log(Log.FINE, "\n\n\n-----inside else---");
					loyaltyVO1.setEntryPoints(true);
				}
			}
		}

		log.log(Log.FINE, "vos for save------------>", loyaltyVOToSave);
		try {
			delegate.saveLoyaltyPgmToCustomers(loyaltyVOToSave);
		} catch (BusinessDelegateException ex) {
//printStackTrrace()();
			errors = handleDelegateException(ex);
		}
		session.setLoyaltyVOs(null);
		form.setSaveLoyaltyFlag("Y");
		invocationContext.target = LIST_SUCCESS;
	}
}
