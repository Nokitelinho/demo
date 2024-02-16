/*
 * ListConsignmentCommand.java Created on July 1, 2016
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.ux.consignment;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.ibsplc.icargo.business.mail.operations.vo.ConsignmentDocumentVO;
import com.ibsplc.icargo.business.mail.operations.vo.ConsignmentFilterVO;
import com.ibsplc.icargo.business.mail.operations.vo.PostalAdministrationVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.util.unit.UnitConstants;
import com.ibsplc.icargo.framework.util.unit.UnitException;
import com.ibsplc.icargo.framework.util.unit.UnitFormatter;
import com.ibsplc.icargo.framework.util.unit.vo.UnitRoundingVO;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.json.vo.ResponseVO;
import com.ibsplc.icargo.framework.web.spring.command.AbstractCommand;
import com.ibsplc.icargo.framework.web.spring.controller.ActionContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.model.mail.operations.MaintainConsignmentModel;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.Consignment;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.ConsignmentRouting;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.MailOperationsModelConverter;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-5991
 *
 */

public class ListConsignmentCommand extends AbstractCommand {

	private Log log = LogFactory.getLogger("MAILOPERATIONS");

	private static final String MODULE_NAME = "mail.operations";
	private static final String SCREEN_ID = "mail.operations.ux.consignment";
	private static final String MODULE_AIRLINE = "mail.mra.airlinebilling";

	private static final String AIRLINE_SCREEN_ID = "mailtracking.mra.airlinebilling.defaults.listinterlinebillingentries";

	private static final String STATUS_NO_RESULTS = "mailtracking.defaults.consignment.status.noresultsfound";
	/**
	 * DUMMY_FLIGHT_NO.
	 */
	private static final String DUMMY_FLIGHT_NO = "-1";

	/**
	 * This method overrides the execute method of BaseComand class
	 * 
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(ActionContext actionContext) throws CommandInvocationException {

		log.entering("ListConsignmentCommand", "execute");
		MaintainConsignmentModel maintainConsignmentModel = (MaintainConsignmentModel) actionContext.getScreenModel();

		/*
		 * ConsignmentSession consignmentSession = getScreenSession(MODULE_NAME,
		 * SCREEN_ID); ListInterlineBillingEntriesSession billingSession =
		 * (ListInterlineBillingEntriesSession) getScreenSession(
		 * MODULE_AIRLINE, AIRLINE_SCREEN_ID);
		 */
		LogonAttributes logonAttributes = getApplicationSession().getLogonVO();
		ArrayList results = new ArrayList();
		ResponseVO responseVO = new ResponseVO();
		/*
		 * Getting OneTime values
		 */
		Map<String, Collection<OneTimeVO>> oneTimes = findOneTimeDescription(logonAttributes.getCompanyCode());
		if (oneTimes != null) {
			List<String> sortedOnetimes;
			Collection<OneTimeVO> catVOs = oneTimes.get("mailtracking.defaults.mailcategory");
			Collection<OneTimeVO> hniVOs = oneTimes.get("mailtracking.defaults.highestnumbermail");
			Collection<OneTimeVO> rsnVOs = oneTimes.get("mailtracking.defaults.registeredorinsuredcode");
			Collection<OneTimeVO> mailClassVOs = oneTimes.get("mailtracking.defaults.mailclass");
			Collection<OneTimeVO> typeVOs = oneTimes.get("mailtracking.defaults.consignmentdocument.type");
			// Added as part of CRQ ICRD-103713 by A-5526
			Collection<OneTimeVO> subTypeVOs = oneTimes.get("mailtracking.defaults.consignmentdocument.subtype");
			Collection<OneTimeVO> mailServiceLevels = oneTimes.get("mail.operations.mailservicelevels");
			Collection<OneTimeVO> transportStageVOs = oneTimes.get("mail.operations.cardittransporttype");
			log.log(Log.FINE, "*******Getting OneTimeVOs***catVOs***", catVOs.size());
			log.log(Log.FINE, "*******Getting OneTimeVOs***rsnVOs***", rsnVOs.size());
			log.log(Log.FINE, "*******Getting OneTimeVOs***hniVOs***", hniVOs.size());
			log.log(Log.FINE, "*******Getting OneTimeVOs***hniVOs***", mailClassVOs.size());
			log.log(Log.FINE, "*******Getting OneTimeVOs***typeVOs***", typeVOs.size());
			log.log(Log.FINE, "*******Getting OneTimeVOs***typeVOs***", subTypeVOs.size());
			if (hniVOs != null && !hniVOs.isEmpty()) {
				sortedOnetimes = new ArrayList<String>();
				for (OneTimeVO hniVo : hniVOs) {
					sortedOnetimes.add(hniVo.getFieldValue());
				}
				Collections.sort(sortedOnetimes);

				int i = 0;
				for (OneTimeVO hniVo : hniVOs) {
					hniVo.setFieldValue(sortedOnetimes.get(i++));
				}
			}
			if (rsnVOs != null && !rsnVOs.isEmpty()) {
				sortedOnetimes = new ArrayList<String>();
				for (OneTimeVO riVo : rsnVOs) {
					sortedOnetimes.add(riVo.getFieldValue());
				}
				Collections.sort(sortedOnetimes);

				int i = 0;
				for (OneTimeVO riVo : rsnVOs) {
					riVo.setFieldValue(sortedOnetimes.get(i++));
				}
			}
			maintainConsignmentModel.setOneTimeCat(catVOs);
			maintainConsignmentModel.setOneTimeRSN(rsnVOs);
			maintainConsignmentModel.setOneTimeHNI(hniVOs);
			maintainConsignmentModel.setOneTimeMailClass(mailClassVOs);
			maintainConsignmentModel.setOneTimeType(typeVOs);
			// Added as part of CRQ ICRD-103713 by A-5526
			maintainConsignmentModel.setOneTimeSubType(subTypeVOs);
			maintainConsignmentModel.setOneTimeMailServiceLevel(mailServiceLevels);
			maintainConsignmentModel.setOneTimeTransportStageQualifier(transportStageVOs);
		}

		List<ErrorVO> errors = new ArrayList<ErrorVO>();
		maintainConsignmentModel.setConsignment(null);
		// Added for Unit Component
		UnitRoundingVO unitRoundingVO = new UnitRoundingVO();
		maintainConsignmentModel.setWeightRoundingVO(unitRoundingVO);
		setUnitComponent(logonAttributes.getStationCode(), maintainConsignmentModel);

		maintainConsignmentModel.setSelectMail(null);
		/*
		 * if ("fromInterLineBilling".equals(billingSession.getFromScreen())) {
		 * log.log(Log.FINE, "FromScreen....", billingSession.getFromScreen());
		 * ConsignmentFilterVO consignmentFilterVO =
		 * consignmentSession.getConsignmentFilterVO();
		 * listConsignmentModel.setConDocNo(consignmentFilterVO.
		 * getConsignmentNumber());
		 * consignmentForm.setPaCode(consignmentFilterVO.getPaCode());
		 * consignmentForm.setFromScreen(billingSession.getFromScreen());
		 * listConsignmentModel.setDisplayPage("1");
		 * listConsignmentModel.setLastPageNum("0");
		 * billingSession.setFromScreen(BLANK); }
		 */

		errors = validateForm(maintainConsignmentModel, logonAttributes);
		if (errors != null && errors.size() > 0) {
			actionContext.addAllError(errors);
			maintainConsignmentModel.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
		} else {
			ConsignmentFilterVO consignmentFilterVO = new ConsignmentFilterVO();
			consignmentFilterVO.setCompanyCode(logonAttributes.getCompanyCode());
			consignmentFilterVO.setConsignmentNumber(maintainConsignmentModel.getConDocNo().toUpperCase());
			consignmentFilterVO.setPaCode(maintainConsignmentModel.getPaCode().toUpperCase());
			consignmentFilterVO.setPageNumber(Integer.parseInt(maintainConsignmentModel.getDisplayPage()));
			consignmentFilterVO.setPageSize(maintainConsignmentModel.getPageSize());
			if ("Y".equals(maintainConsignmentModel.getCountTotalFlag()) && maintainConsignmentModel.getTotalRecords() != 0) {
				consignmentFilterVO.setTotalRecords(maintainConsignmentModel.getTotalRecords());
			} else {
				consignmentFilterVO.setTotalRecords(-1);
			}
			ConsignmentDocumentVO consignmentDocumentVO = null;
			Consignment consignment = new Consignment();
			try {
				consignmentDocumentVO = new MailTrackingDefaultsDelegate()
						.findConsignmentDocumentDetails(consignmentFilterVO);
				MailOperationsModelConverter mailOperationsModelConverter = new MailOperationsModelConverter();
				consignment = mailOperationsModelConverter
						.convertConsDocVOToConsDocModel(consignmentDocumentVO,maintainConsignmentModel.getOneTimeMailServiceLevel());

			} catch (BusinessDelegateException businessDelegateException) {
				businessDelegateException.getMessageVO().getErrors();
				handleDelegateException(businessDelegateException);
			}
			if(consignmentFilterVO.getPaCode().equals("US001")){
				consignment.setDomestic(true);
			}

			log.log(Log.FINE, "consignmentDocumentVO ===>>>>", consignmentDocumentVO);
			log.log(Log.FINE, "consignment ===>>>>", consignment);
			if (consignment == null) {
				consignment = new Consignment();
				consignment.setOperationFlag("I");
				maintainConsignmentModel.setNewRoutingFlag("Y");
				actionContext.addError(new ErrorVO(STATUS_NO_RESULTS));
				maintainConsignmentModel.setDisableListSuccess("N");
				log.log(Log.FINE, "consignment IS NULL");
			} else {
				maintainConsignmentModel.setDisableListSuccess("Y");
				consignment.setOperationFlag("U");
				log.log(Log.FINE, "consignment IS not NULL", consignment);
				maintainConsignmentModel.setDirection(consignment.getOperation());
				int totalRecords = 0;
				if (consignment.getMailsInConsignment() != null) {
					log.log(Log.FINE, "consignment ===>>>>", consignment);
					totalRecords = consignment.getMailsInConsignmentPage().getResults().size();
					maintainConsignmentModel.setTotalRecords(totalRecords);
				} else {
					maintainConsignmentModel.setTotalRecords(totalRecords);
				}
			}
			consignment.setConsignmentNumber(maintainConsignmentModel.getConDocNo().toUpperCase());
			consignment.setPaCode(maintainConsignmentModel.getPaCode().toUpperCase());
			if (consignment.getConsignmentRouting() != null) {
				resetFlightNumber(consignment.getConsignmentRouting());
			}
			maintainConsignmentModel.setConsignment(consignment);
			maintainConsignmentModel.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_DETAIL);
			results.add(maintainConsignmentModel);
			responseVO.setResults(results);
			responseVO.setStatus("success");
			actionContext.setResponseVO(responseVO);
		}

		log.exiting("ListConsignmentCommand", "execute");
	}

	/**
	 * Method to validate form.
	 * 
	 * @param consignmentForm
	 * @return Collection<ErrorVO>
	 */
	private List<ErrorVO> validateForm(MaintainConsignmentModel MaintainConsignmentModel, LogonAttributes logonAttributes) {
		String conDocNo = MaintainConsignmentModel.getConDocNo();
		String paCode = MaintainConsignmentModel.getPaCode();
		List<ErrorVO> errors = new ArrayList<ErrorVO>();

		if (conDocNo == null || ("".equals(conDocNo.trim()))) {
			errors.add(new ErrorVO("mailtracking.defaults.consignment.msg.err.condocnoempty"));
		}
		if (paCode == null || ("".equals(paCode.trim()))) {
			errors.add(new ErrorVO("mailtracking.defaults.consignment.msg.err.pacodeempty"));
		} else {
			// validate PA code
			log.log(Log.FINE, "Going To validate PA code ...in command");
			try {
				PostalAdministrationVO postalAdministrationVO = new PostalAdministrationVO();
				postalAdministrationVO = new MailTrackingDefaultsDelegate().findPACode(logonAttributes.getCompanyCode(),
						paCode.toUpperCase());
				if (postalAdministrationVO == null) {
					Object[] obj = { paCode.toUpperCase() };
					errors.add(new ErrorVO("mailtracking.defaults.consignment.pacode.invalid", obj));
				}

			} catch (BusinessDelegateException businessDelegateException) {
				errors = (List<ErrorVO>) handleDelegateException(businessDelegateException);
			}
		}
		return errors;
	}

	/**
	 * A-3251
	 * 
	 * @param stationCode
	 * @param mailAcceptanceSession
	 * @return
	 */
	private void setUnitComponent(String stationCode, MaintainConsignmentModel maintainConsignmentModel) {
		UnitRoundingVO unitRoundingVO = null;
		try {
			log.log(Log.FINE, "station code is ----------->>", stationCode);
			unitRoundingVO = UnitFormatter.getUnitRoundingForUnitCode(UnitConstants.MAIL_WGT,
					UnitConstants.WEIGHT_MAIL_UNIT);
			log.log(Log.FINE, "unit vo for wt--in session---", unitRoundingVO);
			maintainConsignmentModel.setWeightRoundingVO(unitRoundingVO);

		} catch (UnitException unitException) {
			unitException.getErrorCode();
		}
	}

	/**
	 * This method will be invoked at the time of screen load
	 * 
	 * @param companyCode
	 * @return Map<String, Collection<OneTimeVO>>
	 */
	public Map<String, Collection<OneTimeVO>> findOneTimeDescription(String companyCode) {
		Map<String, Collection<OneTimeVO>> oneTimes = null;
		// Collection<ErrorVO> errors = null;
		try {
			Collection<String> fieldValues = new ArrayList<String>();
			fieldValues.add("mailtracking.defaults.registeredorinsuredcode");
			fieldValues.add("mailtracking.defaults.mailcategory");
			fieldValues.add("mailtracking.defaults.highestnumbermail");
			fieldValues.add("mailtracking.defaults.mailclass");
			fieldValues.add("mailtracking.defaults.consignmentdocument.type");
			// Added as part of CRQ ICRD-103713 by A-5526
			fieldValues.add("mailtracking.defaults.consignmentdocument.subtype");
			fieldValues.add("mail.operations.mailservicelevels");
			fieldValues.add("mail.operations.cardittransporttype");
			oneTimes = new SharedDefaultsDelegate().findOneTimeValues(companyCode, fieldValues);
		} catch (BusinessDelegateException businessDelegateException) {
			// errors = handleDelegateException(businessDelegateException);
			handleDelegateException(businessDelegateException);
		}
		return oneTimes;
	}

	/**
	 * For resetting the flight number.
	 * 
	 * @param routingInConsignmentVOs
	 */
	private void resetFlightNumber(Collection<ConsignmentRouting> consignmentRoutingColl) {
		for (ConsignmentRouting consignmentRouting : consignmentRoutingColl) {
			if (DUMMY_FLIGHT_NO.equals(consignmentRouting.getOnwardFlightNumber())) {
				consignmentRouting.setOnwardFlightNumber("");
			}
		}
	}

}
