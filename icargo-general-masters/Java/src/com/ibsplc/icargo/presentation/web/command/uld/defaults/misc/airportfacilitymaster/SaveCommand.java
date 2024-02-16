/*
 * SaveCommand.java Created on Dec 19, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.misc.airportfacilitymaster;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDAirportLocationVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.uld.defaults.ULDDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.misc.AirportFacilityMasterSession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.misc.AirportFacilityMasterForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;
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

	private static final String SCREENID = "uld.defaults.airportfacilitymaster";

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
		AirportFacilityMasterForm form = (AirportFacilityMasterForm) invocationContext.screenModel;
		AirportFacilityMasterSession session = getScreenSession(MODULE,
				SCREENID);		
		ULDDefaultsDelegate delegate = new ULDDefaultsDelegate();
		ArrayList<ULDAirportLocationVO> uldAirportLocationVOs = session
				.getULDAirportLocationVOs() != null ? new ArrayList<ULDAirportLocationVO>(
				session.getULDAirportLocationVOs())
				: new ArrayList<ULDAirportLocationVO>();
		ApplicationSessionImpl applicationSessionImpl = getApplicationSession();		
		LogonAttributes logonAttributes  =  applicationSessionImpl.getLogonVO();
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		log
				.log(Log.INFO, "uldAirportLocationVOs=======",
						uldAirportLocationVOs);
		// Update session with Form values
		updateSession(form, session);
		ArrayList<ULDAirportLocationVO> uldAirportLocationVOColl = new ArrayList<ULDAirportLocationVO>(session.getULDAirportLocationVOs());

		try {
			log.log(Log.INFO, "uldAirportLocationVOColl=======",
					uldAirportLocationVOColl);
			ArrayList<ULDAirportLocationVO> uldAirportLocationVOColTwo = new ArrayList<ULDAirportLocationVO>();
			String flag = "N";
			

			uldAirportLocationVOColTwo.addAll(uldAirportLocationVOColl);
			for (ULDAirportLocationVO uldAirportLocationVO1 : uldAirportLocationVOColl) {/*
				if (ULDAirportLocationVO.FLAG_NO.equals(flag)) {
					uldAirportLocationVOColTwo.remove(uldAirportLocationVO1);
				}
				for (ULDAirportLocationVO uldAirportLocationVO2 : uldAirportLocationVOColTwo) {
					if ((uldAirportLocationVO1.getFacilityCode()
							.equals(uldAirportLocationVO2.getFacilityCode()))
							&& (uldAirportLocationVO1.getContent()
									.equals(uldAirportLocationVO2.getContent()))) {
						uldAirportLocationVO2
								.setOperationFlag(ULDAirportLocationVO.OPERATION_FLAG_UPDATE);
						uldAirportLocationVO2
								.setLastUpdatedTime(uldAirportLocationVO1
										.getLastUpdatedTime());
						uldAirportLocationVO2
								.setLastUpdatedUser(uldAirportLocationVO1
										.getLastUpdatedUser());
						uldAirportLocationVO2
								.setSequenceNumber(uldAirportLocationVO1
										.getSequenceNumber());
						flag = "Y";
					}

				}
				if (flag.equals("N")) {
					uldAirportLocationVOColTwo.add(uldAirportLocationVO1);
				}

			*/}
			log.log(Log.FINE,
					"uldAirportLocationVOColTwo   before savingg--------->>",
					uldAirportLocationVOColTwo);
			delegate.saveULDAirportLocation(uldAirportLocationVOColTwo);
		} catch (BusinessDelegateException e) {
			e.getMessage();
			errors = handleDelegateException(e);
		}

		// errors is returned from Server side 
		// Save is not Success , add errors to invocation Context 
		Collection<ErrorVO> errs = new ArrayList<ErrorVO>();
		if (errors != null && errors.size() > 0) {
			for (ErrorVO error : errors) {
				log
						.log(Log.FINE, "Error Code--------->>", error.getErrorCode());
				if ("uld.defaults.defaultflagalreadyset".equals(error
						.getErrorCode())) {
					Object[] obj = error.getErrorData();
					Collection<OneTimeVO> col = session.getFacilityType();
					for (OneTimeVO vo : col) {
						log.log(Log.FINE, "%%%vo%%%%%", vo);
						if (obj[0].equals(vo.getFieldValue())) {
							obj[0] = vo.getFieldDescription();
						}
					}
					Collection<OneTimeVO> conCol = session.getContent();
					if (conCol != null) {
						for (OneTimeVO vo : conCol) {
							if (vo.getFieldValue().equals(obj[2])) {
								obj[2] = vo.getFieldDescription();
							}
						}
					}
				}
				if (("uld.defaults.facilitycodeinuse").equals(error.getErrorCode())) {
					Object[] obj = error.getErrorData();
					ArrayList<ULDAirportLocationVO> coll = new ArrayList<ULDAirportLocationVO>();
					if (uldAirportLocationVOColl != null
							|| uldAirportLocationVOColl.size() > 0) {
						for (ULDAirportLocationVO vo : uldAirportLocationVOColl) {
							if ((AbstractVO.OPERATION_FLAG_DELETE).equals(vo
									.getOperationFlag())) {
								vo.setOperationFlag(null);
							}
							coll.add(vo);
						}
					}
					session.setULDAirportLocationVOs(coll);
				}
			}
			invocationContext.addAllError(errors);
			invocationContext.addAllError(errs);
			invocationContext.target = SAVE_FAILURE;
			return;
		}

		// Save is success , Clear the screen after save 
		session.setULDAirportLocationVOs(null);
		//session.setAirportCode("");
		session.setFacilityTypeValue("");
		form.setAirportCode(logonAttributes.getAirportCode());
		form.setFacilityType("");
		form.setAfterList("");
		form.setWareHouseFlag("");

		ErrorVO error = new ErrorVO(
				"uld.defaults.airportfacilitymaster.savedsuccessfully");
		error.setErrorDisplayType(ErrorDisplayType.STATUS);
		errors = new ArrayList<ErrorVO>();
		errors.add(error);
		invocationContext.addAllError(errors);
		invocationContext.target = SAVE_SUCCESS;
	}

	private void updateSession(AirportFacilityMasterForm form,
			AirportFacilityMasterSession session) {

		ArrayList<ULDAirportLocationVO> uldAirportLocationVOs = session
				.getULDAirportLocationVOs() != null ? new ArrayList<ULDAirportLocationVO>(
				session.getULDAirportLocationVOs())
				: new ArrayList<ULDAirportLocationVO>();

		ApplicationSessionImpl applicationSessionImpl = getApplicationSession();
		LogonAttributes logonAttributes = applicationSessionImpl.getLogonVO();
		ArrayList<ULDAirportLocationVO> uldAirportLocationVOColl = new ArrayList<ULDAirportLocationVO>();
		String[] facilityCodes = form.getFacilityCode();
		String[] descriptions = form.getDescription();
		String[] operationFlags = form.getOperationFlag();
		String[] sequenceNumber = form.getSequenceNumber();
		String[] facility = form.getFacility();
		String[] content = form.getContent();
		String defaultFlag[] = form.getChkBoxFlag().split("-");
		if (facilityCodes.length - 1 >= 0) {
			int index = 0;
			for (String facilityCode : facilityCodes) {
				if (!"NOOP".equals(operationFlags[index])) {
					log.log(Log.INFO, "facilityCode=======>>>>", facility,
							index);
					log.log(Log.INFO, "operationFlags=======", operationFlags,
							index);
					log.log(Log.INFO, "content=======", content, index);
					ULDAirportLocationVO uldAirportLocationVO = new ULDAirportLocationVO();
					uldAirportLocationVO.setCompanyCode(logonAttributes
							.getCompanyCode());
					uldAirportLocationVO.setAirportCode(session
							.getAirportCode());
					uldAirportLocationVO.setFacilityType(facility[index]);
					// added by a-3278 for bug ULD660
					if (facility[index]
							.equals(uldAirportLocationVO.FACLITY_REPAIRDOC)) {
						uldAirportLocationVO
								.setContent(uldAirportLocationVO.FACLITY_REPAIRDOC);
					} else if (facility[index]
							.equals(uldAirportLocationVO.FACLITY_AGENTLOC)) {
						uldAirportLocationVO
								.setContent(uldAirportLocationVO.FACLITY_AGENTLOC);
					} else if (facility[index]
							.equals(uldAirportLocationVO.FACLITY_ULDDOC)) {
						uldAirportLocationVO
								.setContent(uldAirportLocationVO.FACLITY_ULDDOC);
					} else {
						uldAirportLocationVO.setContent(content[index]);
					}
					// added by a-3278 for bug ULD660
					if (!(ULDAirportLocationVO.OPERATION_FLAG_INSERT
							.equals(operationFlags[index]))) {
						uldAirportLocationVO
								.setLastUpdatedTime(uldAirportLocationVOs.get(
										index).getLastUpdatedTime());
						uldAirportLocationVO
								.setLastUpdatedUser(uldAirportLocationVOs.get(
										index).getLastUpdatedUser());
					} else {
						uldAirportLocationVO.setLastUpdatedUser(logonAttributes
								.getUserId());
					}
					if (sequenceNumber[index] != null
							&& sequenceNumber[index].length() > 0) {
						uldAirportLocationVO
								.setSequenceNumber(sequenceNumber[index]);
					}

					if (facilityCode != null
							&& facilityCode.trim().length() != 0) {
						uldAirportLocationVO.setFacilityCode(facilityCode
								.toUpperCase());
					}
					if (descriptions[index] != null
							&& descriptions[index].trim().length() != 0) {
						uldAirportLocationVO
								.setDescription(descriptions[index]);
					}
					uldAirportLocationVO
							.setOperationFlag(operationFlags[index]);
					if (("Y").equals(defaultFlag[index])) {
						uldAirportLocationVO.setDefaultFlag("Y");
					} else {
						uldAirportLocationVO.setDefaultFlag("N");
					}

					if (facilityCode != null
							&& facilityCode.trim().length() != 0) {
						uldAirportLocationVOColl.add(uldAirportLocationVO);
					}
					
				}
				index++;
			}
		}
		session.setULDAirportLocationVOs(uldAirportLocationVOColl);
	}
}
