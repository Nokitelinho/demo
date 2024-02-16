/*
 * SaveCommand.java Created on Sep 1, 2010
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.misc.uldserviceabilitymaster;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDServiceabilityVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.uld.defaults.ULDDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.misc.ULDServiceabilityMasterSession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.misc.ULDServiceabilityForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-2052
 *
 */
public class SaveCommand extends BaseCommand {
	/**
	 * Logger for Maintain Damage Report
	 */
	private Log log = LogFactory.getLogger("SaveCommand");

	private static final String SCREENID = "uld.defaults.uldserviceability";

	private static final String MODULE = "uld.defaults";

	private static final String SAVE_SUCCESS = "save_success";

	private static final String SAVE_FAILURE = "save_failure";

	/**
	 * execute method
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		ULDServiceabilityForm form = (ULDServiceabilityForm) invocationContext.screenModel;
		ULDServiceabilityMasterSession session = getScreenSession(MODULE,
				SCREENID);
		ULDDefaultsDelegate delegate = new ULDDefaultsDelegate();
		ArrayList<ULDServiceabilityVO> uldServiceabilityVOs = session
				.getULDServiceabilityVOs() != null ? new ArrayList<ULDServiceabilityVO>(
				session.getULDServiceabilityVOs())
				: new ArrayList<ULDServiceabilityVO>();
		ApplicationSessionImpl applicationSessionImpl = getApplicationSession();
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		log.log(Log.INFO, "uldServiceabilityVOs=======", uldServiceabilityVOs);
		// Update session with Form values
		updateSession(form, session);
		ArrayList<ULDServiceabilityVO> uldServiceabilityVOColl = new ArrayList<ULDServiceabilityVO>(
				session.getULDServiceabilityVOs());

		try {
			log.log(Log.INFO, "uldServiceabilityVOColl=======>>>>>",
					uldServiceabilityVOColl);
			delegate.saveULDServiceability(uldServiceabilityVOColl);
		} catch (BusinessDelegateException e) {
			e.getMessage();
			errors = handleDelegateException(e);
		}
		Collection<ErrorVO> errs = new ArrayList<ErrorVO>();
		if (errors != null && errors.size() > 0) {
			for (ErrorVO error : errors) {
				log
						.log(Log.FINE, "Error Code--------->>", error.getErrorCode());
			}
			invocationContext.addAllError(errors);
			invocationContext.addAllError(errs);
			invocationContext.target = SAVE_FAILURE;
			return;
		}
		// Save is success , Clear the screen after save 
		session.setULDServiceabilityVOs(null);
		session.setPartyTypeValue("");
		form.setPartyType("");
		form.setAfterList("");

		ErrorVO error = new ErrorVO(
				"uld.defaults.uldserviceability.savedsuccessfully");
		error.setErrorDisplayType(ErrorDisplayType.STATUS);
		errors = new ArrayList<ErrorVO>();
		errors.add(error);
		invocationContext.addAllError(errors);
		invocationContext.target = SAVE_SUCCESS;
	}

	private void updateSession(ULDServiceabilityForm form,  
			ULDServiceabilityMasterSession session) {

		ArrayList<ULDServiceabilityVO> uldServiceabilityVOs = session
				.getULDServiceabilityVOs() != null ? new ArrayList<ULDServiceabilityVO>(
				session.getULDServiceabilityVOs())
				: new ArrayList<ULDServiceabilityVO>();

		ApplicationSessionImpl applicationSessionImpl = getApplicationSession();
		LogonAttributes logonAttributes = applicationSessionImpl.getLogonVO();
		ArrayList<ULDServiceabilityVO> uldServiceabilityVOColl = new ArrayList<ULDServiceabilityVO>();
		String[] facilityCodes = form.getServiceCode();
		String[] descriptions = form.getServiceDescription();
		String[] operationFlags = form.getOperationFlag();
		String[] sequenceNumber = form.getSequenceNumber();
		if (facilityCodes.length - 1 >= 0) {
			int index = 0;
			for (String facilityCode : facilityCodes) {
				if (!"NOOP".equals(operationFlags[index])) {
					ULDServiceabilityVO uldServiceabilityVO = new ULDServiceabilityVO();
					uldServiceabilityVO.setCompanyCode(logonAttributes
							.getCompanyCode());
					uldServiceabilityVO.setPartyType(form.getPartyType());

					if (!(ULDServiceabilityVO.OPERATION_FLAG_INSERT
							.equals(operationFlags[index]))) {
						uldServiceabilityVO
								.setLastUpdatedTime(uldServiceabilityVOs.get(
										index).getLastUpdatedTime());
						uldServiceabilityVO
								.setLastUpdatedUser(uldServiceabilityVOs.get(
										index).getLastUpdatedUser());
					} else {
						uldServiceabilityVO.setLastUpdatedUser(logonAttributes
								.getUserId());
					}
					if (sequenceNumber[index] != null
							&& sequenceNumber[index].length() > 0) {
						uldServiceabilityVO
								.setSequenceNumber(sequenceNumber[index]);
					}
					if (facilityCode != null
							&& facilityCode.trim().length() != 0) {
						uldServiceabilityVO.setCode(facilityCode.toUpperCase());
					}
					if (descriptions[index] != null
							&& descriptions[index].trim().length() != 0) {
						uldServiceabilityVO.setDescription(descriptions[index]);
					}
					uldServiceabilityVO.setOperationFlag(operationFlags[index]);

					if (facilityCode != null
							&& facilityCode.trim().length() != 0) {
						uldServiceabilityVOColl.add(uldServiceabilityVO);
					}
				}
				index++;
			}
		}
		session.setULDServiceabilityVOs(uldServiceabilityVOColl);
	}
}
