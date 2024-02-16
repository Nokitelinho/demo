/*
 * RejectInvoiceCommand.java Created on 08-08-08
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.airlinebilling.inward.captureinvoice;

import java.util.ArrayList;
import java.util.Collection;
import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.AirlineCN51FilterVO;
import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.AirlineCN51SummaryVO;
import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.RejectionMemoVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.airlinebilling.inward.CaptureInvoiceSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.airlinebilling.inward.RejectionMemoSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.airlinebilling.inward.CaptureMailInvoiceForm;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;


 /**
  * @author a-3447
  */

/**
 * Command Class  for InvoiceExceptions  screen.
 *
 * Revision History
 *
 * Version      Date           Author          		    Description
 *
 *  0.1         Aug 05, 2008   Muralee(a-3447)		  Initial draft
 *
 */
public class RejectInvoiceCommand extends BaseCommand {
	/**
	 * Logger and the file name
	 */
	private Log log = LogFactory.getLogger("MRA AIRLINEBILLING INWARD");
	/**
	 *
	 * Class name
	 */
	private static final String CLASS_NAME = "RejectInvoiceCommand--";

	/**
	 * Module name
	 */

	private static final String MODULE_NAME = "mailtracking.mra.airlinebilling";

	/**
	 * Screen Id
	 */
	private static final String SCREEN_ID = "mailtracking.mra.airlinebilling.inward.captureinvoice";

	/**
	 * reject Success Action
	 */
	private static final String REJECT_SUCCESS = "reject_success";

	/**
	 * clear Success Action
	 */
	private static final String REJECT_FAILURE = "reject_failure";


	/**
	 * Blank valus
	 */
	private static final String REJECTED = "R";


	/**
	 * module name
	 *
	 */
	private static final String REJECTION_MODULE_NAME = "mailtracking.mra.airlinebilling";
	/**
	 * screen id
	 *
	 */
	private static final String SCREENID = "mailtracking.mra.airlinebilling.inward.rejectionmemo";
	/**
	 * other indicator for rejection memo screen
	 */
	private static final String TICKED="Y";


	/**
	 * Reason for Rejection
	 */

	private static final String REASON="Invoice Rejected due to the Difference in Form-1 and Invoice";
	/**
	 * Zero
	 */
	private static final double ZERO=0.0;


	/**
	 *@author a-3447
	 * Execute method
	 * @param invocationContext InvocationContext
	 * @throws CommandInvocationException
	 *
	 */
		public void execute(InvocationContext invocationContext)
		throws CommandInvocationException {
			log.entering(CLASS_NAME, "execute");
			CaptureMailInvoiceForm captureInvoiceForm = (CaptureMailInvoiceForm) invocationContext.screenModel;
			CaptureInvoiceSession captureInvoiceSession=(CaptureInvoiceSession)getScreenSession(
					MODULE_NAME, SCREEN_ID);
			RejectionMemoSession session =
				(RejectionMemoSession)getScreenSession(REJECTION_MODULE_NAME, SCREENID);

			Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
			RejectionMemoVO rejectionMemoVO=new RejectionMemoVO();
			AirlineCN51SummaryVO airlineCN51SummaryVO = captureInvoiceSession.getAirlineCN51SummaryVO();
			AirlineCN51FilterVO filterVO=captureInvoiceSession.getFilterVo();
				airlineCN51SummaryVO.setInvStatus(REJECTED);			
				rejectionMemoVO.setOtherIndicator(TICKED);
				rejectionMemoVO.setRemarks(REASON);
				rejectionMemoVO.setCompanycode(airlineCN51SummaryVO.getCompanycode());				
				/*String companyCode = rejectionMemoVO.getCompanycode();
				int airlineIdentifier = rejectionMemoVO.getAirlineIdentifier();
				String interlineBillingType = rejectionMemoVO.getInterlinebillingtype();
				String invoiceNumber = rejectionMemoVO.getInvoicenumber();
				String clearancePeriod = rejectionMemoVO.getClearanceperiod();*/
				String invoiceno=airlineCN51SummaryVO.getInvoicenumber();
				rejectionMemoVO.setClearanceperiod(airlineCN51SummaryVO.getClearanceperiod());
				rejectionMemoVO.setInterlinebillingtype(airlineCN51SummaryVO.getInterlinebillingtype());
				rejectionMemoVO.setInvoiceNumber(invoiceno);
				rejectionMemoVO.setAirlineIdentifier(airlineCN51SummaryVO.getAirlineidr());							
				rejectionMemoVO.setAirlineCode(airlineCN51SummaryVO.getAirlinecode());				
				log.log(Log.FINE, "-rejectionMemoVO>>", rejectionMemoVO);
				rejectionMemoVO.setBillingCurrencyCode(airlineCN51SummaryVO.getListingCurrency());
				rejectionMemoVO.setBillingBilledAmount(airlineCN51SummaryVO.getNetAmount());
				rejectionMemoVO.setContractAcceptedAmount(ZERO);
				rejectionMemoVO.setContractRejectedAmount(airlineCN51SummaryVO.getNetAmount().getAmount());
				//rejectionMemoSession.setRejectionMemoVO(rejectionMemoVO);
				session.setRejectionMemoVO(rejectionMemoVO);
				invocationContext.target = REJECT_SUCCESS;


		}
	}
