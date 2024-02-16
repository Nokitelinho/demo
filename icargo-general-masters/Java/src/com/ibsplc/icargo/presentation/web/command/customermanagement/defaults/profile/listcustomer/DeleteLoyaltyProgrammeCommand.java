/*
 * DeleteLoyaltyProgrammeCommand.java Created on Aug 5, 2005
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
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.customermanagement.defaults.profile.ListCustomerSession;
import com.ibsplc.icargo.presentation.web.struts.form.customermanagement.defaults.profile.ListCustomerForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * 
 * @author A-2046
 * 
 */
public class DeleteLoyaltyProgrammeCommand extends AddLoyaltyProgrammeCommand {

	private static final String DELETE_SUCCESS = "delete_success";

	private static final String MODULENAME = "customermanagement.defaults";

	private static final String SCREENID = "customermanagement.defaults.customerlisting";

	private static final String BLANK = "";

/**
 * @param invocationContext
 * @throws CommandInvocationException
 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		Log log = LogFactory.getLogger("customerlisting");
		log.entering("customerlisting", "DELETECOMMAND");
		ListCustomerForm form = (ListCustomerForm) invocationContext.screenModel;
		ListCustomerSession session = getScreenSession(MODULENAME, SCREENID);
		
		String[] programme = form.getLoyaltyProgramme();
		String[] prgmFromDate = form.getProgramFromDate();
		String[] prgmToDate = form.getProgramToDate();
		String[] attachFromDate = form.getAttachFromDate();
		String[] attachToDate = form.getAttachToDate();
		String[] selected = form.getSelectedLoyalties();
		log.log(Log.FINE, "selectd length------------>" + selected.length);
		ArrayList<AttachLoyaltyProgrammeVO> loyaltyVOs = session
				.getLoyaltyVOs();
		Collection<AttachLoyaltyProgrammeVO> loyaltyVOsToRemove = new ArrayList<AttachLoyaltyProgrammeVO>();

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

			/*
			 * errors = validateDateFields(loyaltyVOs); if(errors != null &&
			 * errors.size() >0){ invocationContext.addAllError(errors);
			 * invocationContext.target = DELETE_FAILURE; return; }
			 * 
			 * errors = validateEndDates(loyaltyVOs); if(errors != null &&
			 * errors.size() >0){ invocationContext.addAllError(errors);
			 * invocationContext.target = DELETE_FAILURE; return; } errors =
			 * validateLoyaltyDates(loyaltyVOs,form); if(errors != null &&
			 * errors.size() >0){ invocationContext.addAllError(errors);
			 * invocationContext.target = DELETE_FAILURE; return; }
			 */
		}

		if (loyaltyVOs != null && loyaltyVOs.size() > 0) {
			int indexVal = 0;
			for (AttachLoyaltyProgrammeVO loyaltyVO : loyaltyVOs) {
				for (int i = 0; i < selected.length; i++) {
					if (indexVal == Integer.parseInt(selected[i])) {
						if (OPERATION_FLAG_INSERT.equals(loyaltyVO
								.getOperationFlag())) {
							loyaltyVOsToRemove.add(loyaltyVO);
						} else {
							loyaltyVO.setOperationFlag(OPERATION_FLAG_DELETE);
						}
					}
				}
				indexVal++;
			}
		}
		log.log(Log.FINE, "\n\n\nloyaltyVOs main collection" + loyaltyVOs);
		log.log(Log.FINE, "\n\n\nloyaltyVOs main collection"
				+ loyaltyVOsToRemove);
		if (loyaltyVOsToRemove != null && loyaltyVOsToRemove.size() > 0) {
			loyaltyVOs.removeAll(loyaltyVOsToRemove);
		}
		session.setLoyaltyVOs(loyaltyVOs);
		invocationContext.target = DELETE_SUCCESS;

	}
}
