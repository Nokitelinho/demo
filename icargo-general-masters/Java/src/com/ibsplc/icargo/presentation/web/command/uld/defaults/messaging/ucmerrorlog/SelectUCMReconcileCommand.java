/*
 * SelectUCMReconcileCommand.java Created on Jul 20, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P)Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.messaging.ucmerrorlog;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

import com.ibsplc.icargo.business.uld.defaults.message.vo.FlightFilterMessageVO;
import com.ibsplc.icargo.business.uld.defaults.message.vo.ULDFlightMessageReconcileDetailsVO;
import com.ibsplc.icargo.business.uld.defaults.message.vo.ULDFlightMessageReconcileVO;
//import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.uld.defaults.ULDDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.messaging.UCMErrorLogSession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.messaging.UCMErrorLogForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * 
 * @author A-2046
 * 
 */
public class SelectUCMReconcileCommand extends BaseCommand {
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
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		ULDFlightMessageReconcileVO uldFltMsgReconcileVOFirst = new ULDFlightMessageReconcileVO();
		ULDFlightMessageReconcileVO uldFltMsgReconcileVOSecond = new ULDFlightMessageReconcileVO();
		String[] selectedRows = ucmErrorLogForm.getSelectedUCMErrorLog();
		log.log(Log.FINE, "Selected ucms------------>", selectedRows);
		Page<ULDFlightMessageReconcileVO> uldFlightMessageReconcileVOs = ucmErrorLogSession
				.getULDFlightMessageReconcileVOs();
		if (selectedRows.length == 2) {
			uldFltMsgReconcileVOFirst = uldFlightMessageReconcileVOs
					.get(Integer.parseInt(selectedRows[0]));

			uldFltMsgReconcileVOSecond = uldFlightMessageReconcileVOs
					.get(Integer.parseInt(selectedRows[1]));
			if (!uldFltMsgReconcileVOFirst.getErrorCode().equals(
					uldFltMsgReconcileVOSecond.getErrorCode())) {
				ErrorVO error = new ErrorVO(
						"uld.defaults.messaging.ucmerrorlog.selectsameerrorcode");
				errors.add(error);
			
			}
			//newly added starts
			if((!uldFltMsgReconcileVOFirst.getCarrierCode().equals(uldFltMsgReconcileVOSecond.getCarrierCode())||
					(!uldFltMsgReconcileVOFirst.getFlightNumber().equals(uldFltMsgReconcileVOSecond.getFlightNumber()))||
					(!uldFltMsgReconcileVOFirst.getFlightDate().equals(uldFltMsgReconcileVOSecond.getFlightDate()))||
					(!uldFltMsgReconcileVOFirst.getMessageType().equals(uldFltMsgReconcileVOSecond.getMessageType()))||
					(!uldFltMsgReconcileVOFirst.getAirportCode().equals(uldFltMsgReconcileVOSecond.getAirportCode())))){
				ErrorVO error = new ErrorVO(
				"uld.defaults.messaging.ucmerrorlog.selectidentificalflight");
		errors.add(error);
			}
			//newly added ends
			if(errors!=null && errors.size()>0){
				invocationContext.addAllError(errors);
				invocationContext.target = SCREENLOAD_SUCCESS;
				return;
			}

		} else if (selectedRows.length == 1) {
			uldFltMsgReconcileVOFirst = uldFlightMessageReconcileVOs
					.get(Integer.parseInt(selectedRows[0]));

		}
		log.log(Log.FINE, "Recocile VO1--------->", uldFltMsgReconcileVOFirst);
		log.log(Log.FINE, "Recocile VO2--------->", uldFltMsgReconcileVOSecond);
		Collection<ULDFlightMessageReconcileDetailsVO> detailsFirst = new ArrayList<ULDFlightMessageReconcileDetailsVO>();
		Collection<ULDFlightMessageReconcileDetailsVO> detailsSecond = new ArrayList<ULDFlightMessageReconcileDetailsVO>();

		Collection<ErrorVO> errorVos = new ArrayList<ErrorVO>();
		ULDDefaultsDelegate delegate = new ULDDefaultsDelegate();

		Collection<ULDFlightMessageReconcileVO> reconcileVOs = null;
		Collection<String> ucmSequenceNos = new ArrayList<String>();
		ucmSequenceNos.add(uldFltMsgReconcileVOFirst.getSequenceNumber());
		FlightFilterMessageVO filterVO = new FlightFilterMessageVO();
		populateFlightFilterVO(filterVO, ucmErrorLogSession);
		//for populating filter vo starts
		filterVO.setCarrierCode(uldFltMsgReconcileVOFirst.getCarrierCode());
		filterVO.setAirportCode(uldFltMsgReconcileVOFirst.getAirportCode());
		filterVO.setFlightDate(uldFltMsgReconcileVOFirst.getFlightDate());
		filterVO.setFlightNumber(uldFltMsgReconcileVOFirst.getFlightNumber());
		filterVO.setLegSerialNumber(uldFltMsgReconcileVOFirst.getLegSerialNumber());
		filterVO.setFlightSequenceNumber(uldFltMsgReconcileVOFirst.getFlightSequenceNumber());
		filterVO.setMessageType(uldFltMsgReconcileVOFirst.getMessageType());
		filterVO.setFlightCarrierId(uldFltMsgReconcileVOFirst.getFlightCarrierIdentifier());
		log.log(Log.FINE, "filter VO------------------->", filterVO);
		if (uldFltMsgReconcileVOSecond.getFlightNumber() != null
				&& uldFltMsgReconcileVOSecond.getFlightNumber().trim()
						.length() > 0) {
			ucmSequenceNos
					.add(uldFltMsgReconcileVOSecond.getSequenceNumber());
		}

		log.log(Log.FINE, "ucm sequence numbers--------->", ucmSequenceNos);
		filterVO.setUcmSequenceNumbers(ucmSequenceNos);
		log.log(Log.FINE, "filter VO------------------->", filterVO);
		Collection<ErrorVO> err = new ArrayList<ErrorVO>();
		try {

			reconcileVOs = delegate.listUCMsForComparison(filterVO);
		} catch (BusinessDelegateException ex) {
			ex.getMessage();
			err = handleDelegateException(ex);
		}

		if (reconcileVOs != null && reconcileVOs.size() > 0) {
			String seqNumber = ((ArrayList<ULDFlightMessageReconcileVO>) reconcileVOs)
					.get(0).getSequenceNumber();
			if (uldFltMsgReconcileVOSecond.getFlightNumber() != null
					&& uldFltMsgReconcileVOSecond.getFlightNumber().trim()
							.length() > 0) {
				if (uldFltMsgReconcileVOFirst.getSequenceNumber().equals(
						seqNumber)) {

					detailsFirst = ((ArrayList<ULDFlightMessageReconcileVO>) reconcileVOs)
							.get(0).getReconcileDetailsVOs();

					detailsSecond = ((ArrayList<ULDFlightMessageReconcileVO>) reconcileVOs)
							.get(1).getReconcileDetailsVOs();
				} else {
					detailsFirst = ((ArrayList<ULDFlightMessageReconcileVO>) reconcileVOs)
							.get(1).getReconcileDetailsVOs();
					detailsSecond = ((ArrayList<ULDFlightMessageReconcileVO>) reconcileVOs)
							.get(0).getReconcileDetailsVOs();
				}
			} else {

				detailsFirst = ((ArrayList<ULDFlightMessageReconcileVO>) reconcileVOs)
						.get(0).getReconcileDetailsVOs();

			}
			log.log(Log.FINE, "detailss1------------->", detailsFirst);
			uldFltMsgReconcileVOFirst.setReconcileDetailsVOs(detailsFirst);

			if (uldFltMsgReconcileVOSecond.getFlightNumber() != null
					&& uldFltMsgReconcileVOSecond.getFlightNumber().trim()
							.length() > 0) {
				uldFltMsgReconcileVOSecond.setReconcileDetailsVOs(detailsSecond);
			}

		}
		if (uldFltMsgReconcileVOSecond.getFlightNumber() == null
				|| uldFltMsgReconcileVOSecond.getFlightNumber().trim()
						.length() == 0) {
			ucmErrorLogForm.setMismatchStatus("Y");
			filterVO.setPointOfUnloading(ucmErrorLogSession
					.getFlightFilterMessageVOSession().getAirportCode());
			filterVO.setMessageType("OUT");
			log.log(Log.FINE, "Filter vo to server mismatch----------------->",
					filterVO);
			try {

				detailsSecond = delegate.listUCMOUTForInOutMismatch(filterVO

				);

			} catch (BusinessDelegateException exception) {
				errors = handleDelegateException(exception);
				log.log(Log.FINE, "\n\n\nINSIDE BUSINESS DELEGATE EXCEPTION",
						errors.size());
			}

			if (errors != null && errors.size() > 0) {
				for (ErrorVO errorVo : errors) {
					log.log(Log.FINE, "\n\n\n\nERRROR CODE----------------->",
							errorVo.getErrorCode());
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
			log.log(Log.FINE,
					"\n\n\n\nERRROR VOS CONSTRUCTED----------------->",
					errorVos.size());
			if (errorVos != null && errorVos.size() > 0) {
				invocationContext.addAllError(errorVos);
				log.log(Log.FINE,
						"\n\n\n\nTARGET SCREENLOAD FAILURE----------------->",
						errorVos.size());
				invocationContext.target = SCREENLOAD_FAILURE;
				ucmErrorLogForm.setReconcileStatus("");
				return;

			}

			// added for showing comma separated out sequence numbers
			if (detailsSecond != null && detailsSecond.size() > 0) {
				StringBuilder sequenceNumberList = new StringBuilder();
				String sequenceNumber = "";
				HashSet<String> sequenceNumbers = new HashSet<String>();
				for (ULDFlightMessageReconcileDetailsVO reconcileDtlsVO : detailsSecond) {
					sequenceNumbers.add(reconcileDtlsVO.getSequenceNumber());
				}
				for (String seqNum : sequenceNumbers) {
					sequenceNumberList.append(seqNum);
					sequenceNumberList.append(",");
				}
				sequenceNumber = sequenceNumberList.substring(0,
						sequenceNumberList.length() - 1);
				log.log(Log.FINE, "\n\n\n\nSequence Number to set",
						sequenceNumber);
				uldFltMsgReconcileVOSecond.setSequenceNumber(sequenceNumber);

			}

			log.log(Log.FINE, "Details VO From Mismatch Server Call-----_>",
					detailsSecond);
			uldFltMsgReconcileVOSecond.setReconcileDetailsVOs(detailsSecond);

		}

		ucmErrorLogSession.setUCM1ReconcileVO(uldFltMsgReconcileVOFirst);
		ucmErrorLogSession.setUCM2ReconcileVO(uldFltMsgReconcileVOSecond);
		ucmErrorLogForm.setReconcileStatus("Y");
		invocationContext.target = SCREENLOAD_SUCCESS;
	}

	/**
	 * 
	 * @param filterVO
	 * @param session
	 */
	private void populateFlightFilterVO(FlightFilterMessageVO filterVO,
			UCMErrorLogSession session) {
		FlightFilterMessageVO filterVOSession = session
				.getFlightFilterMessageVOSession();
		filterVO.setAbsoluteIndex(filterVOSession.getAbsoluteIndex());
		//filterVO.setAirportCode(filterVOSession.getAirportCode());
		//filterVO.setCarrierCode(filterVOSession.getCarrierCode());
		filterVO.setCompanyCode(filterVOSession.getCompanyCode());
		//filterVO.setFlightCarrierId(filterVOSession.getFlightCarrierId());
		//filterVO.setFlightDate(filterVOSession.getFlightDate());
		//filterVO.setFlightNumber(filterVOSession.getFlightNumber());
		//filterVO.setLegSerialNumber(filterVOSession.getLegSerialNumber());
		//filterVO.setFlightSequenceNumber(filterVOSession
		//		.getFlightSequenceNumber());
	//	filterVO.setMessageType(filterVOSession.getMessageType());
		filterVO.setPageNumber(filterVOSession.getPageNumber());
		
			
		}
		
	}

