/*
 * AddULDDetailsCommand.java Created on Jul 20, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P)Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.messaging.ucminout;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.flight.operation.vo.FlightValidationVO;
import com.ibsplc.icargo.business.uld.defaults.message.vo.ULDFlightMessageReconcileDetailsVO;
import com.ibsplc.icargo.business.uld.defaults.message.vo.ULDFlightMessageReconcileVO;
import com.ibsplc.icargo.framework.session.ApplicationSession;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.uld.defaults.ULDDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.messaging.UCMINOUTSession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.messaging.UCMINOUTForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 *
 * @author A-2046
 *
 */
public class AddULDDetailsCommand extends BaseCommand {

	private static final String ADD_SUCCESS = "add_success";

	private static final String ADD_FAILURE = "add_failure";

	private static final String MODULE_NAME = "uld.defaults";

	private static final String SCREEN_ID = "uld.defaults.ucminoutmessaging";

	private static final String BLANK = "";

	private static final String MANUAL = "MAN";
	private Log log = LogFactory.getLogger("ULD_MESSAGING");

	/**
	 * execute method
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {

		log.entering("ADD ULD Details Command", "UCM IN OUT");
		UCMINOUTForm form = (UCMINOUTForm) invocationContext.screenModel;
		UCMINOUTSession session = getScreenSession(MODULE_NAME, SCREEN_ID);
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		ApplicationSession appSession = getApplicationSession();
		ULDFlightMessageReconcileVO reconcileVO = session
				.getMessageReconcileVO();
		FlightValidationVO flightValidationVO = session.getFlightValidationVOSession();
		ArrayList<ULDFlightMessageReconcileDetailsVO> reconcileDetails = (ArrayList<ULDFlightMessageReconcileDetailsVO>) reconcileVO
				.getReconcileDetailsVOs();

		if (reconcileDetails != null && reconcileDetails.size() > 0) {
			updateULDDetails(reconcileDetails, form,reconcileVO);
			errors = validateULDDetails(reconcileDetails, form);

			if (errors != null && errors.size() > 0) {
				invocationContext.addAllError(errors);
				invocationContext.target = ADD_FAILURE;
				return;

			}
			Collection<String> uldNumbersForValidation = new ArrayList<String>();
			for (ULDFlightMessageReconcileDetailsVO uldDetailsVO : reconcileDetails) {
				uldNumbersForValidation.add(uldDetailsVO.getUldNumber());
			}
			Collection<String> invalidUlds = null;

			invalidUlds = validateULDFormat(appSession, uldNumbersForValidation);
			log.log(Log.FINE, "invalid uldsss--------->", invalidUlds);
			ErrorVO error = null;

			if (invalidUlds != null && invalidUlds.size() > 0) {
				int size=invalidUlds.size();
				for (int i = 0; i < size; i++) {
					error = new ErrorVO(
							"uld.defaults.ucminout.msg.err.invaliduldformat",
							new Object[] { ((ArrayList<String>) invalidUlds)
									.get(i) });
					errors.add(error);
				}

				invocationContext.addAllError(errors);
				invocationContext.target = ADD_FAILURE;
				return;
			}

			if ("OUT".equals(session.getMessageStatus())) {
				if (form.getDestination() == null
						|| form.getDestination().trim().length() == 0) {

					 errors = validateDestinationForUlds(reconcileDetails,form,flightValidationVO);
					 if(errors!=null&&errors.size()>0){
						 invocationContext.addAllError(errors);
						 invocationContext.target=ADD_FAILURE;
						 return;
					 }

				}
			}

		} else {
			reconcileDetails = new ArrayList<ULDFlightMessageReconcileDetailsVO>();
		}
		ULDFlightMessageReconcileDetailsVO detailsVO = new ULDFlightMessageReconcileDetailsVO();
		detailsVO
				.setOperationFlag(ULDFlightMessageReconcileVO.OPERATION_FLAG_INSERT);
		detailsVO.setCompanyCode(getApplicationSession().getLogonVO()
				.getCompanyCode());
		detailsVO.setUldNumber(BLANK);
		if (form.getDestination() != null
				&& form.getDestination().trim().length() > 0) {
			detailsVO.setPou(form.getDestination().toUpperCase());
		}
		if ("IN".equals(session.getMessageStatus())) {
			detailsVO.setPou(form.getOrigin().toUpperCase());
		}

		detailsVO.setContent(BLANK);
		reconcileDetails.add(detailsVO);
		log.log(Log.FINE,
				"reconcileDetails after addition------------------->",
				reconcileDetails);
		reconcileVO.setReconcileDetailsVOs(reconcileDetails);
		session.setMessageReconcileVO(reconcileVO);
		form.setAddStatus("Y");
		invocationContext.target = ADD_SUCCESS;

	}

/**
 *
 * @param reconcileDetails
 * @param form
 * @param reconcileVO
 */
	public void updateULDDetails(
			ArrayList<ULDFlightMessageReconcileDetailsVO> reconcileDetails,
			UCMINOUTForm form,ULDFlightMessageReconcileVO reconcileVO) {
		log.log(Log.FINE,
				"updateULDDetails++reconcileDetails------ENTER------------>>",
				reconcileDetails);
		String[] uldNumbers = form.getUldNumbers();
		log.log(Log.FINE, "damage status-------------->", form.getDamagedStatus());
		String[] damageCodes = form.getDamagedStatus().split(",");
		String[] pou = form.getPou();
		String[] contents = form.getContentInd();
		String[] opFlag = form.getHiddenOpFlag();
		String[] uldSource = form.getUldSource();
		if(reconcileDetails == null) {
				reconcileDetails = new ArrayList<ULDFlightMessageReconcileDetailsVO>();
				reconcileVO.setReconcileDetailsVOs(reconcileDetails);
		}

				if(opFlag != null) {
					int noOfElements = reconcileDetails.size();
					for(int i = noOfElements - 1; i >=0; i-- ) {
						log.log(Log.FINE, "\n opFlag[i] 1------->>", opFlag, i);
						if("NOOP".equals(opFlag[i])){
							reconcileDetails.remove(i);
						}
						else {
							ULDFlightMessageReconcileDetailsVO vo = reconcileDetails.get(i);
							vo.setUldNumber(uldNumbers[i].toUpperCase());
							if(contents != null && uldSource != null && i<uldSource.length && MANUAL.equals(uldSource[i])){ // Modified as part of bug ICRD-238795
							vo.setContent(contents[i]);
							} // Modified as part of bug ICRD-238795 ends
							vo.setPou(pou[i].toUpperCase());
							vo.setDamageStatus(damageCodes[i]);
							if("Y".equals(form.getUcmVOStatus())){
								if(form.getOrigin().toUpperCase().equals(pou[i])){
									vo.setMessageType("IN");
								}else{
									vo.setMessageType("OUT");
								}
							}
							vo.setOperationFlag(opFlag[i]);
							vo.setUldFlightStatus("T");//ICRD-2268 while saving  setting to Transit state first time
							if(reconcileVO.getSequenceNumber()!=null){
								vo.setSequenceNumber(reconcileVO.getSequenceNumber());
							}
						}
					}
					log.log(Log.INFO, "opFlag.length------>>", opFlag.length);
					for(int i = noOfElements; i < opFlag.length - 1 ; i++) {
						log.log(Log.FINE, "\n opFlag[i] 2------->>", opFlag, i);
						if(!"NOOP".equals(opFlag[i])){
								ULDFlightMessageReconcileDetailsVO vo = new ULDFlightMessageReconcileDetailsVO();
								vo.setUldNumber(uldNumbers[i].toUpperCase());
								if(contents != null){
								vo.setContent(contents[i]);
								}
								vo.setPou(pou[i].toUpperCase());
								vo.setDamageStatus(damageCodes[i]);
								if("Y".equals(form.getUcmVOStatus())){
									if(form.getOrigin().toUpperCase().equals(pou[i])){
										vo.setMessageType("IN");
									}else{
										vo.setMessageType("OUT");
									}
								}
								vo.setOperationFlag(opFlag[i]);
								if(reconcileVO.getSequenceNumber()!=null){
									vo.setSequenceNumber(reconcileVO.getSequenceNumber());
								}
								vo.setUldFlightStatus("T");//ICRD-2268 while saving  setting to Transit state first time
								reconcileDetails.add(vo);
							}
					}

		}
		log.log(Log.FINE,
				"updateULDDetails++reconcileDetails------EXIT------------>>",
				reconcileDetails);

	}

	/**
	 *
	 * @param reconcileDetails
	 * @param form
	 * @return
	 */

	public Collection<ErrorVO> validateULDDetails(
			Collection<ULDFlightMessageReconcileDetailsVO> reconcileDetails,
			UCMINOUTForm form) {
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		ErrorVO error = null;
		String[] uldNumbers = form.getUldNumbers();
		String[] opFlags = form.getOperationFlag();
		String[] opFlag = form.getHiddenOpFlag();
		int index = 0;

		for (ULDFlightMessageReconcileDetailsVO detailsVO : reconcileDetails) {
			int noOfOccurance = 0;

			if (!ULDFlightMessageReconcileDetailsVO.OPERATION_FLAG_DELETE
					.equals(detailsVO.getOperationFlag())) {

				for (int i = 0; i < uldNumbers.length; i++) {
					if(!"NOOP".equals(opFlag[i])){
					if (!ULDFlightMessageReconcileDetailsVO.OPERATION_FLAG_DELETE
							.equals(opFlags[i])) {
						if (detailsVO.getUldNumber().equalsIgnoreCase(
								uldNumbers[i])) {
							noOfOccurance++;

						}

					}
					}
				}
			}
			if (noOfOccurance > 1) {
				error = new ErrorVO(
						"uld.defaults.ucminout.msg.err.duplicateuldnumber");
				errors.add(error);
				return errors;

			}

			index++;
		}

		return errors;
	}

	/**
	 *
	 * @param originalValue
	 * @param formValue
	 * @return
	 */
	private boolean hasValueChanged(String originalValue, String formValue) {
		if (originalValue != null) {
			if (originalValue.equalsIgnoreCase(formValue)) {
				return false;
			}

		}
		return true;
	}

	/**
	 *
	 * @param appSession
	 * @param uldNumbers
	 * @return
	 */
	public Collection<String> validateULDFormat(ApplicationSession appSession,
			Collection<String> uldNumbers) {
		ULDDefaultsDelegate delegate = new ULDDefaultsDelegate();
		Collection<String> invalidUlds = null;
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		try {
			invalidUlds = delegate.validateMultipleULDFormats(appSession
					.getLogonVO().getCompanyCode(), uldNumbers);
		} catch (BusinessDelegateException ex) {
			ex.getMessage();
			errors = handleDelegateException(ex);
		}
		return invalidUlds;

	}

/**
 *
 * @param reconcileDetails
 * @param form
 * @param flightValidationVO
 * @return
 */

	public Collection<ErrorVO> validateDestinationForUlds(
			Collection<ULDFlightMessageReconcileDetailsVO> reconcileDetails,
			UCMINOUTForm form, FlightValidationVO flightValidationVO) {
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		String route = flightValidationVO.getFlightRoute();
		log.log(Log.FINE,
				"validateDestinationForUld+Route-------------------->", route);
		for (ULDFlightMessageReconcileDetailsVO detailsVO : reconcileDetails) {
			log.log(Log.FINE, "validateDestinationForUld------>", detailsVO);
			StringBuilder dest = new StringBuilder();
			if (!route.contains(dest.append("-").append(
					detailsVO.getPou().toUpperCase()))) {
				ErrorVO error = new ErrorVO(
						"uld.defaults.msg.err.ucminout.invaliddestinationforuld");
				errors.add(error);
				return errors;
			}
		}
		return errors;
	}

}
