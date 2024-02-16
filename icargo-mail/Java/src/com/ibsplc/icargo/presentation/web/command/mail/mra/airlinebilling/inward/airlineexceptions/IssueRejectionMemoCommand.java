/*
 * IssueRejectionMemoCommand.java Created on Sep 29, 2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.airlinebilling.inward.airlineexceptions;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.AirlineExceptionsVO;
import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.RejectionMemoVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.util.currency.CurrencyException;
import com.ibsplc.icargo.framework.util.currency.CurrencyHelper;
import com.ibsplc.icargo.framework.util.currency.Money;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.airlinebilling.inward.AirlineExceptionsSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.airlinebilling.inward.RejectionMemoSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.airlinebilling.inward.AirlineExceptionsForm;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * Command Class for List Exception Details screen.
 * 
 * Revision History
 * 
 * Version Date Author Description
 * 
 * 0.1 Sep 29, 2008 Deepthi Initial draft
 * 
 */
public class IssueRejectionMemoCommand extends BaseCommand {
	/**
	 * Logger and the file name
	 */
	private Log log = LogFactory.getLogger("MRA AIRLINEBILLING INWARD");

	private static final String CLASS_NAME = "IssueRejectionMemoCommand";

	/**
	 * module name
	 * 
	 */
	private static final String MODULE_NAME = "mailtracking.mra.airlinebilling";

	/**
	 * screen id
	 * 
	 */
	private static final String SCREENID = "mailtracking.mra.airlinebilling.inward.airlineexceptions";

	/**
	 * module name for rejection memo Screen
	 * 
	 */
	private static final String REJECTION_MODULE_NAME = "mailtracking.mra.airlinebilling";

	/**
	 * screen id for Rejection memo Screen
	 * 
	 */
	private static final String REJECTION_SCREENID = "mailtracking.mra.airlinebilling.inward.rejectionmemo";

	/**
	 * ISSUEREJECTION_FAILURE Action
	 */
	private static final String ISSUEREJECTION_SUCCESS = "issuerejection_success";

	/**
	 * ISSUEREJECTION_SUCCESS Action
	 */
	private static final String ISSUEREJECTION_FAILURE = "issuerejection_failure";

	/**
	 * MEMOSTATUSNULLCANNOTACCEPTED
	 */
	private static final String EXCEPTATUSNOTEXPCANNOTISSUED = "mailtracking.mra.airlinebilling.inward.airlineexceptions.memonotissued";

	/**
	 * BLANK
	 */
	private static final String BLANK = "";

	private static final String EXCEPTION = "E";
	
	private static final String BLGCURCOD = "USD";

	/**
	 * 
	 * Execute method *
	 * 
	 * @param invocationContext
	 *            InvocationContext
	 * @throws CommandInvocationException
	 * 
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		log.entering(CLASS_NAME, "execute");
		double zero=0.0;
		Money billedAmt=null;
		Money acceptedAmt=null;
		Money rejectedAmt=null;
		AirlineExceptionsForm form = (AirlineExceptionsForm) invocationContext.screenModel;

		AirlineExceptionsSession airLineExceptionsSession = (AirlineExceptionsSession) getScreenSession(
				MODULE_NAME, SCREENID);
		RejectionMemoSession rejectionMemoSession = (RejectionMemoSession) getScreenSession(
				REJECTION_MODULE_NAME, REJECTION_SCREENID);
		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		Page<AirlineExceptionsVO> airlineExceptionsVOs=null; 
		airlineExceptionsVOs = airLineExceptionsSession
				.getAirlineExceptionsVOs();
		ArrayList<AirlineExceptionsVO> tempExceptionsVOs = new ArrayList<AirlineExceptionsVO>();

		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		String selectedRow[] = null;
		log.log(Log.FINE, "The selected row is12-->");
		/**
		 * selected row getting here
		 */
		if (form.getSelectedRows() != null && form.getSelectedRows().length > 0) {
			selectedRow = form.getSelectedRows();
		}
		log.log(Log.FINE, "The selected row is1111 -->", selectedRow);
		/**
		 * here checking the EXCEPTION status is EXCEPTION This method is
		 * calling from Acceptance Command Same cehck is dere So reused the CODE
		 * 
		 * @param exceptionInInvoiceVOs
		 * @param selectedRow
		 * @param errorMessage
		 * @return
		 */
		if (airlineExceptionsVOs != null && airlineExceptionsVOs.size() > 0) {
			if (selectedRow != null && selectedRow.length > 0) {
				for (String s : selectedRow) {
					log.log(Log.FINE, "The selected row is -->", s);

				}
				errors = checkExceptionStatus(airlineExceptionsVOs,
						selectedRow, EXCEPTATUSNOTEXPCANNOTISSUED);
			}
		}
		if (errors != null && errors.size() > 0) {
			invocationContext.addAllError(errors);
			invocationContext.target = ISSUEREJECTION_FAILURE;
			return;
		}

		if (airlineExceptionsVOs != null && airlineExceptionsVOs.size() > 0) {
			int count = 0;
			for (AirlineExceptionsVO vo : airlineExceptionsVOs) {
				vo.setLastUpdatedUser(logonAttributes.getUserId());
				log.log(Log.FINE, "THe ccount", count);
				if (selectedRow != null && selectedRow.length > 0) {
					for (String s : selectedRow) {
						log.log(Log.FINE, "THe seelcted row", s);
						if (count == Integer.parseInt(s)) {
							log.log(Log.FINE, "THe seelcted row", s);
							if (EXCEPTION.equals(vo.getExceptionStatus())) {
								tempExceptionsVOs.add(vo);
							}
						}
					}
					count++;
				}
			}
		}
		double exchgRate = 1.0;
		for (AirlineExceptionsVO vo : tempExceptionsVOs) {
			log.log(Log.FINE, "THe finel vo are", vo);
		}

		AirlineExceptionsVO airlineExceptionsVO = tempExceptionsVOs.get(0);
		RejectionMemoVO rejectionMemoVO = new RejectionMemoVO();
		rejectionMemoVO.setCompanycode(airlineExceptionsVO.getCompanyCode());
		rejectionMemoVO
				.setInvoiceNumber(airlineExceptionsVO.getInvoiceNumber());
		rejectionMemoVO.setAirlineCode(airlineExceptionsVO.getAirlineCode());
		rejectionMemoVO.setAirlineIdentifier(airlineExceptionsVO
				.getAirlineIdentifier());
		rejectionMemoVO
				.setExceptionCode(airlineExceptionsVO.getExceptionCode());
		rejectionMemoVO.setClearanceperiod(airlineExceptionsVO
				.getClearancePeriod());
		rejectionMemoVO.setInterlinebillingtype(airlineExceptionsVO
				.getInterlineBlgType());
		rejectionMemoVO.setSerialNumber(String.valueOf(airlineExceptionsVO
				.getSerialNumber()));
		/*rejectionMemoVO.setContractCurrencyCode(airlineExceptionsVO
				.getContactCurrency());*/
		rejectionMemoVO.setBillingCurrencyCode(airlineExceptionsVO
				.getBillingCurrency());
		rejectionMemoVO.setContractBilledAmount(zero);
		rejectionMemoVO.setContractAcceptedAmount(zero);
		rejectionMemoVO.setContractRejectedAmount(zero);
		try{
			billedAmt=CurrencyHelper.getMoney(BLGCURCOD);
			billedAmt.setAmount(airlineExceptionsVO.getReportedAmt());
			acceptedAmt=CurrencyHelper.getMoney(BLGCURCOD);
			acceptedAmt.setAmount(airlineExceptionsVO.getProvAmt());
			rejectedAmt=CurrencyHelper.getMoney(BLGCURCOD);
			rejectedAmt.setAmount((airlineExceptionsVO.getReportedAmt()-airlineExceptionsVO.getProvAmt()));
			rejectionMemoVO.setBillingBilledAmount(billedAmt);
			rejectionMemoVO.setBillingAcceptedAmount(acceptedAmt);
			rejectionMemoVO.setBillingRejectedAmount(rejectedAmt);
		}catch(CurrencyException currencyException){
			log.log(Log.INFO,"CurrencyException found");
		}

		rejectionMemoVO.setMemoCode(airlineExceptionsVO.getMemCode());
		rejectionMemoVO.setContractBillingExchangeRate(zero);
		rejectionMemoVO.setProvisionalAmount(airlineExceptionsVO.getProvAmt());
		rejectionMemoVO.setDsn(airlineExceptionsVO.getBillingBasis());
		rejectionMemoVO.setBillingBasis(airlineExceptionsVO.getBillingBasis());
		rejectionMemoVO.setCsgDocNum(airlineExceptionsVO.getCsgDocNum());
		rejectionMemoVO.setCsgSeqNum(airlineExceptionsVO.getCsgSeqNum());
		rejectionMemoVO.setPoaCode(airlineExceptionsVO.getPoaCode());
		rejectionMemoVO.setInwardInvoiceDate(airlineExceptionsVO.getLastUpdatedTime());
		rejectionMemoVO.setInwardInvoiceNumber(airlineExceptionsVO.getInvoiceNumber());
		rejectionMemoVO.setOrigin(airlineExceptionsVO.getOrigin());
		rejectionMemoVO.setDestination(airlineExceptionsVO.getDestination());
		rejectionMemoVO.setSectorFrom(airlineExceptionsVO.getSectorFrom());
		rejectionMemoVO.setSectorTo(airlineExceptionsVO.getSectoTo());
		rejectionMemoSession.setRejectionMemoVO(rejectionMemoVO);
		log.log(Log.FINE, "session-->", rejectionMemoSession.getRejectionMemoVO());
		invocationContext.target = ISSUEREJECTION_SUCCESS;
		log.exiting(CLASS_NAME, "execute");
	}

	/**
	 * 
	 * @param AirlineExceptionsVO
	 * @param selectedRow
	 * @param errorMessage
	 * @return Collection<ErrorVO>
	 */
	public Collection<ErrorVO> checkExceptionStatus(
			Collection<AirlineExceptionsVO> airlineExceptionsVOs,
			String selectedRow[], String errorMessage) {
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		ErrorVO errorVO = null;
		int count = 0;
		int errorValue = 0;
		log.entering(CLASS_NAME, "checkExceptionStatus");
		if (airlineExceptionsVOs != null && airlineExceptionsVOs.size() > 0) {
			if (selectedRow != null && selectedRow.length > 0) {
				for (AirlineExceptionsVO airlineExceptionsVO : airlineExceptionsVOs) {
					for (String s : selectedRow) {
						log.log(Log.FINE, "Selected row-->", s);
						log.log(Log.FINE, "count-->", count);
						if (s != null && s.trim().length() > 0) {
							if (count == Integer.parseInt(s)) {
								log
										.log(
												Log.FINE,
												"exceptionInInvoiceVO.getExceptionStatus()",
												airlineExceptionsVO
														.getExceptionStatus());
								if (EXCEPTION.equals(airlineExceptionsVO
										.getExceptionStatus())) {
									errorValue++;
								}
							}
						}
					}
					count++;
				}
			}
			log.log(Log.FINE, "Error value", errorValue);
			if (errorValue == 0) {
				errorVO = new ErrorVO(errorMessage);
				errors.add(errorVO);
			}
		}
		log.exiting(CLASS_NAME, "checkExceptionStatus");
		return errors;
	}
}
