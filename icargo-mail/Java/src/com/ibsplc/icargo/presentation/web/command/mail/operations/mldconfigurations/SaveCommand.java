/*
 * SaveCommand.java Created on December 16, 2015
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.mldconfigurations;

import com.ibsplc.icargo.business.mail.operations.vo.MLDConfigurationVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;

import com.ibsplc.icargo.business.shared.airline.vo.AirlineValidationVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.airline.AirlineDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.area.AreaDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.MLDConfigurationSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.MLDConfigurationForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import java.util.ArrayList;
import java.util.Collection;

/**
 * 
 * @author A-5526
 * 
 */
public class SaveCommand extends BaseCommand {
	private Log log = LogFactory.getLogger("MAILTRACKING");
	private static final String SUCCESS = "save_success";
	private static final String FAILURE = "save_failure";
	private static final String MODULE_NAME = "mail.operations";
	private static final String CLASS_NAME = "SaveCommand";
	private static final String SCREEN_ID = "mailtracking.defaults.mldconfiguration";
	private static final String SAVE_SUCCESS_MESSAGE = "mailtracking.defaults.mldconfiguration.msg.info.savesuccess";
	private static final String SELECT_ANY_ONE_MODE = "mailtracking.defaults.mldconfiguration.error.info.selectanyonemode";
	private static final String CARRIER_AND_AIRPORT_MANDATORY_FOR_SAVE ="mailtracking.defaults.mldconfiguration.msg.err.carrierandairportmandatory";
	private static final String INVALID_AIRPORTCODE ="mailtracking.defaults.mldconfiguration.msg.err.invalidport";
	private static final String INVALID_CARRIERCODE ="mailtracking.defaults.mldconfiguration.msg.err.invalidcarrier";
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		this.log.entering(CLASS_NAME, "execute");

		MLDConfigurationSession mldConfigurationSession = getScreenSession(
				MODULE_NAME, SCREEN_ID);

		MLDConfigurationForm form = (MLDConfigurationForm) invocationContext.screenModel;

		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		String companyCode = logonAttributes.getCompanyCode();
		MailTrackingDefaultsDelegate delegate = new MailTrackingDefaultsDelegate();
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		Collection<MLDConfigurationVO> mldConfigurationVOsFromForm = new ArrayList<MLDConfigurationVO>();
		Collection<MLDConfigurationVO> mldConfigurationVOsToSave = new ArrayList<MLDConfigurationVO>();

		for (int i = 0; i < form.getOperationFlag().length; i++) {
			if (!"NOOP".equals(form.getOperationFlag()[i])) {
				MLDConfigurationVO mldConfigurationVO = populateVO(form,
						companyCode, i, errors);
				if (mldConfigurationVO != null) {
					mldConfigurationVOsFromForm.add(mldConfigurationVO);
				}
			}
		}
		if ((errors != null) && (errors.size() > 0)) {
			//Added for bug ICRD-152616 by A-5526 starts
			
		for(MLDConfigurationVO mLDConfigurationVO:mldConfigurationVOsFromForm){
			mLDConfigurationVO.setOperationFlag(MailConstantsVO.FLAG_NO);    
		}
		//Added for bug ICRD-152616 by A-5526 ends
			mldConfigurationSession
					.setMLDConfigurationVOs(mldConfigurationVOsFromForm);

			invocationContext.addAllError(errors);
			invocationContext.target = FAILURE;
			return;
		}
		if ((mldConfigurationVOsFromForm != null)
				&& (mldConfigurationVOsFromForm.size() > 0)) {
			errors = validateMandatoryField(mldConfigurationVOsFromForm);
		}
		if ((errors != null) && (errors.size() > 0)) {
			mldConfigurationSession
					.setMLDConfigurationVOs(mldConfigurationVOsFromForm);

			invocationContext.addAllError(errors);
			invocationContext.target = FAILURE;
			return;
		}

		for (MLDConfigurationVO mLDConfigurationVo : mldConfigurationVOsFromForm) {
			// if (("D".equals(mLDConfigurationVo.getOperationFlag())) ||
			// ("I".equals(mLDConfigurationVo.getOperationFlag())))
			// {
			mldConfigurationVOsToSave.add(mLDConfigurationVo);
			// }
		}

		if ((mldConfigurationVOsToSave != null)
				&& (mldConfigurationVOsToSave.size() > 0)) {
			errors = validateVO(mldConfigurationVOsToSave, companyCode);
		}
		if ((errors != null) && (errors.size() > 0)) {
			mldConfigurationSession
					.setMLDConfigurationVOs(mldConfigurationVOsFromForm);

			invocationContext.addAllError(errors);
			invocationContext.target = FAILURE;
			return;
		}
		try {

			if (mldConfigurationVOsToSave != null
					&& mldConfigurationVOsToSave.size() > 0) {
				delegate.saveMLDConfigurations(mldConfigurationVOsToSave);
			}
		} catch (BusinessDelegateException businessDelegateException) {
			//businessDelegateException.printStackTrace();
			errors = handleDelegateException(businessDelegateException);
			invocationContext.addAllError(errors);
			mldConfigurationSession
					.setMLDConfigurationVOs(mldConfigurationVOsFromForm);

			invocationContext.target = FAILURE;
			return;
		}
		form.setCarrier("");
		form.setAirport("");
		mldConfigurationSession.setMLDConfigurationVOs(null);
		mldConfigurationSession.removeAllAttributes();
		ErrorVO errorVO = new ErrorVO(
				SAVE_SUCCESS_MESSAGE);
		errorVO.setErrorDisplayType(ErrorDisplayType.INFO);
		errors.add(errorVO);
		invocationContext.addAllError(errors);
		this.log.exiting("SaveCommand", "execute");
		invocationContext.target = SUCCESS;
	}

	public MLDConfigurationVO populateVO(MLDConfigurationForm form,
			String companyCode, int rowCount, Collection<ErrorVO> errors) {
		MLDConfigurationVO mldConfigurationVO = new MLDConfigurationVO();
		mldConfigurationVO.setCompanyCode(companyCode);
		boolean anyModeSelected = false;
		if ((form.getCarrierCode()[rowCount] != null)
				&& (form.getCarrierCode()[rowCount].trim().length() > 0)) {
			mldConfigurationVO.setCarrierCode(form.getCarrierCode()[rowCount]);
		}
		if ((form.getAirportCode()[rowCount] != null)
				&& (form.getAirportCode()[rowCount].trim().length() > 0)) {
			mldConfigurationVO.setAirportCode(form.getAirportCode()[rowCount]);
		}

		if ((form.getIsAllocatedCheck() != null)
				&& (form.getIsAllocatedCheck().trim().length() > 0)) {

			String[] isAllocatedFlag = form.getIsAllocatedCheck().split("-");
			if ((isAllocatedFlag[rowCount] != null)
					&& (isAllocatedFlag[rowCount].trim().length() > 0)) {
				if (MailConstantsVO.FLAG_YES.equals(isAllocatedFlag[rowCount]))
					{
					anyModeSelected = true;
					}
				mldConfigurationVO
						.setAllocatedRequired(isAllocatedFlag[rowCount]);
			} else {
				mldConfigurationVO.setAllocatedRequired(MailConstantsVO.FLAG_NO);
			}
		}
		if ((form.getIsUpliftedCheck() != null)
				&& (form.getIsUpliftedCheck().trim().length() > 0)) {
			String[] isUpliftedFlag = form.getIsUpliftedCheck().split("-");
			if ((isUpliftedFlag[rowCount] != null)
					&& (isUpliftedFlag[rowCount].trim().length() > 0)) {
				if (MailConstantsVO.FLAG_YES.equals(isUpliftedFlag[rowCount]))
					{
					anyModeSelected = true;
					}
				mldConfigurationVO
						.setUpliftedRequired(isUpliftedFlag[rowCount]);
			} else {
				mldConfigurationVO.setUpliftedRequired(MailConstantsVO.FLAG_NO);
			}
		}
		if ((form.getIsdlvCheck() != null)
				&& (form.getIsdlvCheck().trim().length() > 0)) {
			String[] isDeliveredFlag = form.getIsdlvCheck().split("-");
			if ((isDeliveredFlag[rowCount] != null)
					&& (isDeliveredFlag[rowCount].trim().length() > 0)) {
				if (MailConstantsVO.FLAG_YES.equals(isDeliveredFlag[rowCount]))
					{
					anyModeSelected = true;
					}
				mldConfigurationVO
						.setDeliveredRequired(isDeliveredFlag[rowCount]);
			} else {
				mldConfigurationVO.setDeliveredRequired(MailConstantsVO.FLAG_NO);
			}
		}
		if ((form.getIsHndcheck() != null)
				&& (form.getIsHndcheck().trim().length() > 0)) {
			String[] isHndFlag = form.getIsHndcheck().split("-");
			if ((isHndFlag[rowCount] != null)
					&& (isHndFlag[rowCount].trim().length() > 0)) {
				if (MailConstantsVO.FLAG_YES.equals(isHndFlag[rowCount]))
					{
					anyModeSelected = true;
					}
				mldConfigurationVO.sethNDRequired(isHndFlag[rowCount]);
			} else {
				mldConfigurationVO.sethNDRequired(MailConstantsVO.FLAG_NO);
			}
		}
		if ((form.getIsReceivedCheck() != null)
				&& (form.getIsReceivedCheck().trim().length() > 0)) {
			String[] isRcvdFlag = form.getIsReceivedCheck().split("-");
			if ((isRcvdFlag[rowCount] != null)
					&& (isRcvdFlag[rowCount].trim().length() > 0)) {
				if (MailConstantsVO.FLAG_YES.equals(isRcvdFlag[rowCount]))
					{
					anyModeSelected = true;
					}
				mldConfigurationVO.setReceivedRequired(isRcvdFlag[rowCount]);
			} else {
				mldConfigurationVO.setReceivedRequired(MailConstantsVO.FLAG_NO);
			}
		}
		
		
		
		//Added for CRQ ICRD-135130 by A-8061 starts
		mldConfigurationVO.setMldversion(form.getMldversion());

		if ((form.getIsStagedCheck() != null)
				&& (form.getIsStagedCheck().trim().length() > 0)) {
			String[] isStagedFlag = form.getIsStagedCheck().split("-");
			if ((isStagedFlag[rowCount] != null)
					&& (isStagedFlag[rowCount].trim().length() > 0)) {
				if (MailConstantsVO.FLAG_YES.equals(isStagedFlag[rowCount]))
					{
					anyModeSelected = true;
					}
				mldConfigurationVO.setStagedRequired(isStagedFlag[rowCount]);
			} else {
				mldConfigurationVO.setStagedRequired(MailConstantsVO.FLAG_NO);
			}
		}

		if ((form.getIsNestedCheck() != null)
				&& (form.getIsNestedCheck().trim().length() > 0)) {
			String[] isNstdFlag = form.getIsNestedCheck().split("-");
			if ((isNstdFlag[rowCount] != null)
					&& (isNstdFlag[rowCount].trim().length() > 0)) {
				if (MailConstantsVO.FLAG_YES.equals(isNstdFlag[rowCount]))
					{
					anyModeSelected = true;
					}
				mldConfigurationVO.setNestedRequired(isNstdFlag[rowCount]);
			} else {
				mldConfigurationVO.setNestedRequired(MailConstantsVO.FLAG_NO);
			}
		}
		if ((form.getIsReceivedFromFightCheck() != null)
				&& (form.getIsReceivedFromFightCheck().trim().length() > 0)) {
			String[] isRcvdFFCFlag = form.getIsReceivedFromFightCheck().split("-");
			if ((isRcvdFFCFlag[rowCount] != null)
					&& (isRcvdFFCFlag[rowCount].trim().length() > 0)) {
				if (MailConstantsVO.FLAG_YES.equals(isRcvdFFCFlag[rowCount]))
					{
					anyModeSelected = true;
					}
				mldConfigurationVO.setReceivedFromFightRequired(isRcvdFFCFlag[rowCount]);
			} else {
				mldConfigurationVO.setReceivedFromFightRequired(MailConstantsVO.FLAG_NO);
			}
		}

		if ((form.getIsTransferredFromOALcheck() != null)
				&& (form.getIsTransferredFromOALcheck().trim().length() > 0)) {
			String[] isTraFromOALFlag = form.getIsTransferredFromOALcheck().split("-");
			if ((isTraFromOALFlag[rowCount] != null)
					&& (isTraFromOALFlag[rowCount].trim().length() > 0)) {
				if (MailConstantsVO.FLAG_YES.equals(isTraFromOALFlag[rowCount]))
					{
					anyModeSelected = true;
					}
				mldConfigurationVO.setTransferredFromOALRequired(isTraFromOALFlag[rowCount]);
			} else {
				mldConfigurationVO.setTransferredFromOALRequired(MailConstantsVO.FLAG_NO);
			}
		}
		
		if ((form.getIsReceivedFromOALcheck() != null)
				&& (form.getIsReceivedFromOALcheck().trim().length() > 0)) {
			String[] isRcvdFrmOALFlag = form.getIsReceivedFromOALcheck().split("-");
			if ((isRcvdFrmOALFlag[rowCount] != null)
					&& (isRcvdFrmOALFlag[rowCount].trim().length() > 0)) {
				if (MailConstantsVO.FLAG_YES.equals(isRcvdFrmOALFlag[rowCount]))
					{
					anyModeSelected = true;
					}
				mldConfigurationVO.setReceivedFromOALRequired(isRcvdFrmOALFlag[rowCount]);
			} else {
				mldConfigurationVO.setReceivedFromOALRequired(MailConstantsVO.FLAG_NO);
			}
		}

		if ((form.getIsReturnedCheck() != null)
				&& (form.getIsReturnedCheck().trim().length() > 0)) {
			String[] isRetFlag = form.getIsReturnedCheck().split("-");
			if ((isRetFlag[rowCount] != null)
					&& (isRetFlag[rowCount].trim().length() > 0)) {
				if (MailConstantsVO.FLAG_YES.equals(isRetFlag[rowCount]))
					{
					anyModeSelected = true;
					}
				mldConfigurationVO.setReturnedRequired(isRetFlag[rowCount]);
			} else {
				mldConfigurationVO.setReturnedRequired(MailConstantsVO.FLAG_NO);
			}
		}
		//Added for CRQ ICRD-135130 by A-8061 end
		
		
		if ((form.getOperationFlag()[rowCount] != null)
				&& (form.getOperationFlag()[rowCount].trim().length() > 0)) {
			mldConfigurationVO
					.setOperationFlag(form.getOperationFlag()[rowCount]);
		}
		if (!anyModeSelected) {
			mldConfigurationVO
					.setOperationFlag(MailConstantsVO.OPERATION_FLAG_INSERT);
			ErrorVO errorVO = new ErrorVO(
					SELECT_ANY_ONE_MODE);
			errorVO.setErrorDisplayType(ErrorDisplayType.INFO);
			errors.add(errorVO);

		}
		return mldConfigurationVO;
	}

	private Collection<ErrorVO> validateMandatoryField(
			Collection<MLDConfigurationVO> mldConfigurationVOs) {
		log.entering(CLASS_NAME, "validateMandatoryField");
		boolean isCarrierCodeEmpty = false;
		boolean isPortEmpty = false;

		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		for (MLDConfigurationVO mldConfigurationVO : mldConfigurationVOs) {
			
			if (((!isCarrierCodeEmpty) && ((mldConfigurationVO.getCarrierCode() == null) || (mldConfigurationVO
					.getCarrierCode().trim().length() == 0)))
					|| ((!isPortEmpty) && ((mldConfigurationVO.getAirportCode() == null) || (mldConfigurationVO
							.getAirportCode().trim().length() == 0)))) {
				isCarrierCodeEmpty = true;
				ErrorVO error = new ErrorVO(
						CARRIER_AND_AIRPORT_MANDATORY_FOR_SAVE);

				error.setErrorDisplayType(ErrorDisplayType.ERROR);
				mldConfigurationVO
						.setOperationFlag(MailConstantsVO.OPERATION_FLAG_INSERT);
				errors.add(error);
			}
			
		}
		return errors;
	}

	private Collection<ErrorVO> validateVO(
			Collection<MLDConfigurationVO> mldConfigurationVOs,
			String companyCode) {
		this.log.log(3, "inside validateVO");
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();

		AreaDelegate areaDelegate = new AreaDelegate();
		AirlineDelegate airlineDelegate = new AirlineDelegate();
		for (MLDConfigurationVO mldConfigurationVO : mldConfigurationVOs) {

			Collection<String> airportCodesToValidate = new ArrayList<String>();

			StringBuilder errorToDisplay = null;
			if ((mldConfigurationVO.getCarrierCode() != null)
					&& (mldConfigurationVO.getCarrierCode().trim().length() > 0)) {
				if (errorToDisplay == null)
					{
						errorToDisplay = new StringBuilder().append(mldConfigurationVO.getCarrierCode());
					}

			}
			if ((mldConfigurationVO.getAirportCode() != null)
					&& (mldConfigurationVO.getAirportCode().trim().length() > 0)) {
				if (errorToDisplay == null)
					{
						errorToDisplay = new StringBuilder().append(mldConfigurationVO.getAirportCode());
					}
				else {
					errorToDisplay = errorToDisplay.append("-").append(
							mldConfigurationVO.getAirportCode());
				}
			}
			if (errorToDisplay == null)
				{
				errorToDisplay = new StringBuilder().append(" is invalid");
				}
			else {
				errorToDisplay = errorToDisplay.append(" is invalid");
			}
			this.log.log(3, "going to validate Acceptance port ");
			Collection<ErrorVO> errs = new ArrayList<ErrorVO>();
			try {
				airportCodesToValidate.add(mldConfigurationVO.getAirportCode());
				areaDelegate.validateAirportCodes(companyCode,
						airportCodesToValidate);
			} catch (BusinessDelegateException businessDelegateException) {
				errs = handleDelegateException(businessDelegateException);
			}
			if (errs != null && errs.size() > 0) {
				for (ErrorVO errovo : errs) {
					if ("shared.area.invalidairport".equals(errovo
							.getErrorCode())) {
						ErrorVO err = new ErrorVO(
								INVALID_AIRPORTCODE,
								new Object[] { errorToDisplay.toString() });
						mldConfigurationVO
								.setOperationFlag(MailConstantsVO.OPERATION_FLAG_INSERT);
						errors.add(err);
					}

				}

			}

			this.log.log(3, "going to validate Airline ");
			try {
				AirlineValidationVO airlineValidationVO = airlineDelegate
						.validateAlphaCode(companyCode,
								mldConfigurationVO.getCarrierCode());

				mldConfigurationVO.setCarrierIdentifier(airlineValidationVO
						.getAirlineIdentifier());
			} catch (BusinessDelegateException businessDelegateException) {
				Collection<ErrorVO> errss = handleDelegateException(businessDelegateException);
				if ((errss != null) && (errss.size() > 0)) {
					for (ErrorVO errovo : errss) {
						if ("shared.airline.invalidairline".equals(errovo
								.getErrorCode())) {
							ErrorVO err = new ErrorVO(
									INVALID_CARRIERCODE,
									new Object[] { errorToDisplay.toString() });
							mldConfigurationVO
									.setOperationFlag(MailConstantsVO.OPERATION_FLAG_INSERT);
							errors.add(err);
						}
					}
				}
			}

		}

		return errors;
	}
}