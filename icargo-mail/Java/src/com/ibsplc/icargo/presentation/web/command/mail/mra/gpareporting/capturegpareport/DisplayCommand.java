/*
 * DisplayCommand.java Created on Feb 22, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.gpareporting.capturegpareport;

import static com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants.SCREEN_STATUS_DETAIL;
import static com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.ibsplc.icargo.business.mail.operations.vo.PostalAdministrationVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.ReportingPeriodFilterVO;
import com.ibsplc.icargo.business.mail.mra.gpareporting.vo.GPAReportingDetailsVO;
import com.ibsplc.icargo.business.mail.mra.gpareporting.vo.GPAReportingFilterVO;
import com.ibsplc.icargo.business.mail.mra.gpareporting.vo.GPAReportingFlightDetailsVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.mra.MailTrackingMRADelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.gpareporting.CaptureGPAReportSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.gpareporting.CaptureGPAReportForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * 
 * @author A-1739
 * 
 */
/*
 * Revision History
 * -------------------------------------------------------------------------
 * Revision Date Author Description
 * ------------------------------------------------------------------------- 0.1
 * Feb 22, 2007 a-2257 Created
 */
public class DisplayCommand extends BaseCommand {

	private Log log = LogFactory.getLogger("Mailtracking MRA");

	private static final String CLASS_NAME = "DisplayCommand";

	private static final String MODULE_NAME = "mailtracking.mra";

	private static final String SCREENID = "mailtracking.mra.gpareporting.capturegpareport";

	private static final String BLANK = "";

	private static final String PROCESS_OK = "mailtraking.mra.gpareport.capturegpareport.err.processok";

	/*
	 * Target mappings for succes and failure
	 */
	private static final String ACTION_SUCCESS = "action_success";

	private static final String STATUS_PROCESSED = "P";

	private static final String INVALID_REPORTING_PERIOD = "mailtracking.mra.gpareporting.invalidreportingperiodorgpacode";

	/**
	 * 
	 * TODO Purpose Mar 11, 2007, a-2257
	 * 
	 * @param invocationContext
	 * @throws CommandInvocationException
	 * @see com.ibsplc.icargo.framework.web.command.Command#execute(com.ibsplc.icargo.framework.web.command.InvocationContext)
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {

		log.entering(CLASS_NAME, "execute");

		CaptureGPAReportSession session = (CaptureGPAReportSession) getScreenSession(
				MODULE_NAME, SCREENID);

		CaptureGPAReportForm form = (CaptureGPAReportForm) invocationContext.screenModel;

		GPAReportingFilterVO gpaReportingFilterVO = session
				.getGPAReportingFilterVO();

		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();

		ErrorVO error = null;

		LogonAttributes logonAttributes = getApplicationSession().getLogonVO();

		String companyCode = logonAttributes.getCompanyCode();

		// Added for handling PageAwareMultiMapper implementation
		HashMap<String, String> indexMap = null;
		HashMap<String, String> finalMap = null;
		if (session.getIndexMap() != null) {
			//indexMap = session.getIndexMap();
			getIndexMap(session.getIndexMap(), invocationContext); //added by A-5203
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
		gpaReportingFilterVO.setAbsoluteIndex(nAbsoluteIndex);

		if (invocationContext.getErrors() != null
				&& invocationContext.getErrors().size() > 0) {

			log.log(Log.FINE, "Inside errors");

			for (ErrorVO errorVO : invocationContext.getErrors()) {
				if (!PROCESS_OK.equals(errorVO.getErrorCode())) {

					log.log(Log.FINE, "Processing not ok");
					form.setScreenStatusFlag(SCREEN_STATUS_SCREENLOAD);
					invocationContext.target = ACTION_SUCCESS;

					log.exiting(CLASS_NAME, "execute");

					return;
				}
			}

		}

		PostalAdministrationVO postalAdministrationVO = null;

		if (gpaReportingFilterVO.getPoaCode() != null
				&& gpaReportingFilterVO.getPoaCode().trim().length() > 0) {

			try {		
			postalAdministrationVO = new MailTrackingMRADelegate().findPostalAdminDetails(logonAttributes.getCompanyCode(),gpaReportingFilterVO.getPoaCode());						
								
			}catch(BusinessDelegateException businessDelegateException) {
				errors = handleDelegateException(businessDelegateException);
				// errorFlg=true;
			}
			if (postalAdministrationVO == null) {
				log
						.log(Log.INFO,
								"**************Invalid gpacode**************");
				error = new ErrorVO(
						"mailtraking.mra.gpareport.capturegpareport.gpacodeinvalid");
				error.setErrorDisplayType(ErrorDisplayType.ERROR);
				errors.add(error);
				invocationContext.addAllError(errors);
				form.setScreenStatusFlag(SCREEN_STATUS_SCREENLOAD);
				invocationContext.target = ACTION_SUCCESS;

				log.exiting(CLASS_NAME, "execute");

				return;
			} else {
				log.log(Log.INFO, "*******Valid gpacode*****",
						postalAdministrationVO);
				gpaReportingFilterVO.setPoaName(postalAdministrationVO
						.getPaName());
				gpaReportingFilterVO.setCountry(postalAdministrationVO
						.getCountryCode());
				form.setBasistype(postalAdministrationVO.getBasisType());
				form.setCurrencyCode(postalAdministrationVO.getSettlementCurrencyCode());
				// form.setBasistype("D");
			}
		}
		log.log(Log.FINE, "inside no error case");

		gpaReportingFilterVO.setCompanyCode(companyCode);
		gpaReportingFilterVO.setPageNumber(Integer.parseInt(displayPage));
		// Validating reporting period
		ReportingPeriodFilterVO reportingPeriodFilterVo = new ReportingPeriodFilterVO();
		reportingPeriodFilterVo.setCompanyCode(gpaReportingFilterVO
				.getCompanyCode());
		reportingPeriodFilterVo.setFromDate(gpaReportingFilterVO
				.getReportingPeriodFrom());
		reportingPeriodFilterVo.setToDate(gpaReportingFilterVO
				.getReportingPeriodTo());
		// reportingPeriodFilterVo.setCountryCode(gpaReportingFilterVO
			// .getCountry());
		reportingPeriodFilterVo.setGpaCode(gpaReportingFilterVO.getPoaCode());
		boolean isValid = false;
		try {
			isValid = new MailTrackingMRADelegate()
					.validateReportingPeriod(reportingPeriodFilterVo);
		} catch (BusinessDelegateException businessDelegateException) {
			handleDelegateException(businessDelegateException);
		}
		
		log.log(Log.INFO, "isValid---from cmd class", isValid);
		if (!isValid) {
			error = new ErrorVO(INVALID_REPORTING_PERIOD);
			error.setErrorDisplayType(ErrorDisplayType.ERROR);
			// error = new
			// ErrorVO("mailtraking.mra.gpareport.capturegpareport.gpacodeinvalid");
			error.setErrorDisplayType(ErrorDisplayType.ERROR);
			// errors.add(error);
			errors.add(error);
			invocationContext.addAllError(errors);
			form.setScreenStatusFlag(SCREEN_STATUS_SCREENLOAD);
			invocationContext.target = ACTION_SUCCESS;
			log.exiting(CLASS_NAME, "execute");
			return;
		}
		Collection<GPAReportingDetailsVO> gpaReportingDetailsVOs = new ArrayList<GPAReportingDetailsVO>();

		try {

			log.log(Log.FINE, "Inside Try");

			log.log(Log.FINE, "FilterVO to server>>>>> ", gpaReportingFilterVO);
			Page<GPAReportingDetailsVO> gpaReportingDetailsPage = new MailTrackingMRADelegate()
					.findGPAReportingDetails(gpaReportingFilterVO);

			log.log(Log.FINE, "Page from server>>>>> ", gpaReportingFilterVO);
			if (gpaReportingDetailsPage != null
					&& gpaReportingDetailsPage.size() > 0) {
				log.log(Log.FINE, "Page returned is not null",
						gpaReportingDetailsPage.size());
				form.setAllProcessed(GPAReportingDetailsVO.FLAG_YES);
				for (GPAReportingDetailsVO detailsVO : gpaReportingDetailsPage) {
					if (!STATUS_PROCESSED
							.equals(detailsVO.getReportingStatus())) {
						form.setAllProcessed(GPAReportingDetailsVO.FLAG_NO);
					}
					
					log
							.log(Log.FINE, "Collection from server>>>>> ",
									detailsVO);
					detailsVO.setCompanyCode(companyCode);
					gpaReportingDetailsVOs.add(detailsVO);
				}
				form.setScreenStatusFlag(SCREEN_STATUS_DETAIL);
			} else {

				log.log(Log.FINE, "Page returned is null");
				errors
						.add(new ErrorVO(
								"mailtrakinf.mra.gpareport.capturegpareport.nodetails"));
				form.setScreenStatusFlag(SCREEN_STATUS_DETAIL);
			}

			session.setGPAReportingDetailsPage(gpaReportingDetailsPage);
			// session.setGPAReportingDetailsVOs(gpaReportingDetailsVOs);
			if (gpaReportingDetailsVOs != null
					&& gpaReportingDetailsVOs.size() > 0) {
				for (GPAReportingDetailsVO gpaReportingDetailsVO : gpaReportingDetailsVOs) {

					StringBuffer flightDetails = new StringBuffer("");

					/**
					 * Setting date to String date field
					 */
					gpaReportingDetailsVO
							.setDsnDateForDisplay(gpaReportingDetailsVO
									.getDsnDate().toDisplayDateOnlyFormat());

					if (gpaReportingDetailsVO.getGpaReportingFlightDetailsVOs() != null
							&& gpaReportingDetailsVO
									.getGpaReportingFlightDetailsVOs().size() > 0) {

						for (GPAReportingFlightDetailsVO fltDetailsVO : gpaReportingDetailsVO
								.getGpaReportingFlightDetailsVOs()) {
							log.log(Log.FINE, "updating the fltDetailsVO");

							if (!(BLANK).equals(flightDetails.toString())
									&& flightDetails.length() > 0) {
								log.log(Log.FINE, "not first time");

								flightDetails.append(",");
								flightDetails.append("\n");
							}

							log.log(Log.FINE, "not first time", fltDetailsVO.getCarriageFrom());
							if (fltDetailsVO.getCarriageFrom() != null
									&& fltDetailsVO.getCarriageFrom().trim()
											.length() > 0) {
								flightDetails.append(fltDetailsVO
										.getCarriageFrom());
							} else {

								log.log(Log.FINE, "no getCarriageFrom ");
								flightDetails.append("");
							}
							flightDetails.append("-");

							if (fltDetailsVO.getCarriageTo() != null
									&& fltDetailsVO.getCarriageTo().trim()
											.length() > 0) {
								flightDetails.append(fltDetailsVO
										.getCarriageTo());
							} else {

								log.log(Log.FINE, "no getCarriageTo ");
								flightDetails.append("");
							}
							flightDetails.append(":");

							if (fltDetailsVO.getFlightCarrierCode() != null
									&& fltDetailsVO.getFlightCarrierCode()
											.trim().length() > 0) {
								flightDetails.append(fltDetailsVO
										.getFlightCarrierCode());
							} else {

								log.log(Log.FINE, "no getFlightCarrierCode ");
								flightDetails.append("");
							}
							flightDetails.append(" ");
							if (fltDetailsVO.getFlightNumber() != null
									&& fltDetailsVO.getFlightNumber().trim()
											.length() > 0) {
								flightDetails.append(fltDetailsVO
										.getFlightNumber());
							} else {

								log.log(Log.FINE, "no getFlightNumber ");
								flightDetails.append("");
							}

						}
						gpaReportingDetailsVO.setFlightDetails(flightDetails
								.toString());
					}

				}
			}
			log.log(Log.FINE, "Exiting Try");

		} catch (BusinessDelegateException businessDelegateException) {

			log.log(Log.FINE, "inside catch");
			businessDelegateException.getMessage();
			handleDelegateException(businessDelegateException);
		}

		// Added for handling PageAwareMultiMapper

		finalMap = indexMap;

		if (session.getGPAReportingDetailsPage() != null) {
			finalMap = buildIndexMap(indexMap, session
					.getGPAReportingDetailsPage());
		}
		//session.setIndexMap(finalMap);
		session.setIndexMap((HashMap<String,String>)super.setIndexMap(finalMap, invocationContext)); //added by A-5203
		log.log(Log.INFO, "GOING TO PUT FINAL MAP IN SESSION---------*****",
				finalMap);
		session.setGPAReportingFilterVO(gpaReportingFilterVO);
		log.log(Log.FINE, "final filter", session.getGPAReportingFilterVO());
		invocationContext.addAllError(errors);
		invocationContext.target = ACTION_SUCCESS;
		log.exiting("DisplayCommand", "execute");
	}

	/**
	 * // Added for handling PageAwareMultiMapper
	 * 
	 */

	/**
	 * 
	 * method to bulid the hashmap to maintain absoluteindex
	 * 
	 * @param existingMap
	 *            HashMap<String, String>
	 * @param page
	 *            Page
	 * @return HashMap<String, String>
	 * 
	 */
	private HashMap<String, String> buildIndexMap(
			HashMap<String, String> existingMap, Page page) {

		log.entering(CLASS_NAME, "buildIndexMap");

		HashMap<String, String> finalMap = existingMap;
		String indexPage = String.valueOf((page.getPageNumber() + 1));
		boolean isPageExist = false;
		Set<Map.Entry<String, String>> set = existingMap.entrySet();

		for (Map.Entry<String, String> entry : set) {
			String pageNum = entry.getKey();
			if (pageNum.equals(indexPage)) {
				isPageExist = true;
			}
		}
		if (!isPageExist) {
			finalMap.put(indexPage, String.valueOf(page.getAbsoluteIndex()));
		}
		log.entering(CLASS_NAME, "buildIndexMap");
		return finalMap;
	}
}
