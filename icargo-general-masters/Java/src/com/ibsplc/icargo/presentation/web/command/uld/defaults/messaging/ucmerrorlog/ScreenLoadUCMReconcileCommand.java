/*
 * ScreenLoadUCMReconcileCommand.java Created on Jul 20, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P)Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.messaging.ucmerrorlog;

import com.ibsplc.icargo.business.uld.defaults.message.vo.ULDFlightMessageReconcileVO;
//import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.messaging.UCMErrorLogSession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.messaging.UCMErrorLogForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * 
 * @author A-2046
 * 
 */
public class ScreenLoadUCMReconcileCommand extends BaseCommand {
	/**
	 * Logger for UCM Error Log
	 */
	private Log log = LogFactory.getLogger("UCM Error Log");

	/**
	 * The Module Name
	 */
	private static final String MODULE = "uld.defaults";

	/**
	 * Screen Id of ucm error log
	 */
	private static final String SCREENID = "uld.defaults.ucmerrorlog";

	private static final String SCREENLOAD_SUCCESS = "screenload_success";

	private static final String SCREENLOAD_FAILURE = "screenload_failure";

	private static final String DUPOUT = "uld.defaults.duplicateucmoutexistsforstation";

	private static final String ULD_ERROR = "uld.defaults.ulderrorspresentinucm";

	/**
	 * execute method
	 * 
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		/**
		 * Obtain the logonAttributes
		 */
		ApplicationSessionImpl applicationSession = getApplicationSession();
		//Commented by Manaf for INT ULD510
		//LogonAttributes logonAttributes = applicationSession.getLogonVO();
		//String compCode = logonAttributes.getCompanyCode();
		UCMErrorLogSession ucmErrorLogSession = (UCMErrorLogSession) getScreenSession(
				MODULE, SCREENID);

		UCMErrorLogForm ucmErrorLogForm = (UCMErrorLogForm) invocationContext.screenModel;
		//newly added starts
		ULDFlightMessageReconcileVO uldFltMsgReconcileVOFirst = new ULDFlightMessageReconcileVO();
		uldFltMsgReconcileVOFirst=ucmErrorLogSession.getUCM1ReconcileVO();
		ucmErrorLogForm.setCarrierCode(uldFltMsgReconcileVOFirst.getCarrierCode());
		ucmErrorLogForm.setFlightNo(uldFltMsgReconcileVOFirst.getFlightNumber());
		if(uldFltMsgReconcileVOFirst.getFlightDate()!=null)
		 {
			ucmErrorLogForm.setFlightDate(uldFltMsgReconcileVOFirst.getFlightDate().toDisplayFormat());
			//newly added ends
			/*ULDFlightMessageReconcileVO uldFlightMessageReconcileVO1 = ucmErrorLogSession
					.getUCM1ReconcileVO();

			ULDFlightMessageReconcileVO uldFlightMessageReconcileVO2 = ucmErrorLogSession
					.getUCM2ReconcileVO();

			Collection<ULDFlightMessageReconcileDetailsVO> details1 = new ArrayList<ULDFlightMessageReconcileDetailsVO>();
			Collection<ULDFlightMessageReconcileDetailsVO> details2 = new ArrayList<ULDFlightMessageReconcileDetailsVO>();

			Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
			Collection<ErrorVO> errorVos = new ArrayList<ErrorVO>();
			ULDDefaultsDelegate delegate = new ULDDefaultsDelegate();

			Collection<ULDFlightMessageReconcileVO> reconcileVOs = null;
			Collection<String> ucmSequenceNos = new ArrayList<String>();
			ucmSequenceNos.add(uldFlightMessageReconcileVO1.getSequenceNumber());
			FlightFilterMessageVO filterVO = new FlightFilterMessageVO();
			populateFlightFilterVO(filterVO, ucmErrorLogSession);
			log.log(Log.FINE, "filter VO------------------->" + filterVO);
			if (uldFlightMessageReconcileVO2.getFlightNumber() != null
					&& uldFlightMessageReconcileVO2.getFlightNumber().trim()
							.length() > 0) {
				ucmSequenceNos
						.add(uldFlightMessageReconcileVO2.getSequenceNumber());
			}

			log.log(Log.FINE, "ucm sequence numbers--------->" + ucmSequenceNos);
			filterVO.setUcmSequenceNumbers(ucmSequenceNos);
			log.log(Log.FINE, "filter VO------------------->" + filterVO);

			try {

				reconcileVOs = delegate.listUCMsForComparison(filterVO);
			} catch (BusinessDelegateException ex) {
//printStackTrrace()();
			}

			if (reconcileVOs != null && reconcileVOs.size() > 0) {
				String seqNumber = ((ArrayList<ULDFlightMessageReconcileVO>) reconcileVOs)
						.get(0).getSequenceNumber();
				if (uldFlightMessageReconcileVO2.getFlightNumber() != null
						&& uldFlightMessageReconcileVO2.getFlightNumber().trim()
								.length() > 0) {
					if (uldFlightMessageReconcileVO1.getSequenceNumber().equals(
							seqNumber)) {

						details1 = ((ArrayList<ULDFlightMessageReconcileVO>) reconcileVOs)
								.get(0).getReconcileDetailsVOs();

						details2 = ((ArrayList<ULDFlightMessageReconcileVO>) reconcileVOs)
								.get(1).getReconcileDetailsVOs();
					} else {
						details1 = ((ArrayList<ULDFlightMessageReconcileVO>) reconcileVOs)
								.get(1).getReconcileDetailsVOs();
						details2 = ((ArrayList<ULDFlightMessageReconcileVO>) reconcileVOs)
								.get(0).getReconcileDetailsVOs();
					}
				} else {

					details1 = ((ArrayList<ULDFlightMessageReconcileVO>) reconcileVOs)
							.get(0).getReconcileDetailsVOs();

				}
				log.log(Log.FINE, "detailss1------------->" + details1);
				uldFlightMessageReconcileVO1.setReconcileDetailsVOs(details1);

				if (uldFlightMessageReconcileVO2.getFlightNumber() != null
						&& uldFlightMessageReconcileVO2.getFlightNumber().trim()
								.length() > 0) {
					uldFlightMessageReconcileVO2.setReconcileDetailsVOs(details2);
				}

			}
			if (uldFlightMessageReconcileVO2.getFlightNumber() == null
					|| uldFlightMessageReconcileVO2.getFlightNumber().trim()
							.length() == 0) {
				ucmErrorLogForm.setMismatchStatus("Y");
				filterVO.setPointOfUnloading(ucmErrorLogSession
						.getFlightFilterMessageVOSession().getAirportCode());
				filterVO.setMessageType("OUT");
				log.log(Log.FINE, "Filter vo to server mismatch----------------->"
						+ ucmErrorLogSession.getFlightFilterMessageVOSession());
				try {

					details2 = delegate.listUCMOUTForInOutMismatch(filterVO

					);

				} catch (BusinessDelegateException exception) {
					errors = handleDelegateException(exception);
				}

				if (errors != null && errors.size() > 0) {
					for (ErrorVO errorVo : errors) {
						if (DUPOUT.equals(errorVo.getErrorCode())) {
							ErrorVO error = new ErrorVO(DUPOUT, errorVo
									.getErrorData());
							errorVos.add(error);
						} else if (ULD_ERROR.equals(errorVo.getErrorCode())) {
							ErrorVO error = new ErrorVO(ULD_ERROR, errorVo
									.getErrorData());
							errorVos.add(error);
						}

					}
				}

				if (errorVos != null && errorVos.size() > 0) {
					invocationContext.addAllError(errorVos);
					invocationContext.target = SCREENLOAD_FAILURE;

				}
				log.log(Log.FINE, "Details VO From Mismatch Server Call-----_>"
						+ details2);
				uldFlightMessageReconcileVO2.setReconcileDetailsVOs(details2);

			}*/
		}

		ucmErrorLogForm.setFlightValidationStatus("Y");
		invocationContext.target = SCREENLOAD_SUCCESS;
	}

	/**
	 * 
	 * @param filterVO
	 */
	/*private void populateFlightFilterVO(FlightFilterMessageVO filterVO,
			UCMErrorLogSession session) {
		FlightFilterMessageVO filterVOSession = session
				.getFlightFilterMessageVOSession();
		filterVO.setAbsoluteIndex(filterVOSession.getAbsoluteIndex());
		filterVO.setAirportCode(filterVOSession.getAirportCode());
		filterVO.setCarrierCode(filterVOSession.getCarrierCode());
		filterVO.setCompanyCode(filterVOSession.getCompanyCode());
		filterVO.setFlightCarrierId(filterVOSession.getFlightCarrierId());
		filterVO.setFlightDate(filterVOSession.getFlightDate());
		filterVO.setFlightNumber(filterVOSession.getFlightNumber());
		filterVO.setLegSerialNumber(filterVOSession.getLegSerialNumber());
		filterVO.setFlightSequenceNumber(filterVOSession
				.getFlightSequenceNumber());
		filterVO.setMessageType(filterVOSession.getMessageType());
		filterVO.setPageNumber(filterVOSession.getPageNumber());
	}*/
}
