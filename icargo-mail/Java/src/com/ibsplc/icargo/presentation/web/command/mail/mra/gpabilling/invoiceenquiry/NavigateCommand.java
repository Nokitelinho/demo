/*
 * NavigateCommand.java Created on Sep 08
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.gpabilling.invoiceenquiry;

import java.util.ArrayList;
import java.util.Collection;


import com.ibsplc.icargo.business.mail.mra.defaults.vo.DSNPopUpVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.MaintainCCAFilterVO;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.CN66DetailsVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.DSNPopUpSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.MaintainCCASession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.gpabilling.GPABillingInvoiceEnquirySession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.gpabilling.GPABillingInvoiceEnquiryForm;

import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * This command class is invoked for viewing the detailed information of
 * selected CCAs
 * 
 * @author A-3108
 */
public class NavigateCommand extends BaseCommand {
	/**
	 * Logger and the file name
	 */
	private Log log = LogFactory.getLogger("MRA DEFAULTS");

	/**
	 * Class name
	 */

	private static final String CLASS_NAME = "NavigateCommand";
	
	/**
	 * Screen ID
	 */
	private static final String SCREEN_ID = "mailtracking.mra.gpabilling.gpabillinginvoiceenquiry";
	
	private static final String MODULE = "mailtracking.mra.gpabilling";

	/**
	 * 
	 * Module name
	 */
	private static final String MODULE_NAME_MAINTAIN_CCA = "mailtracking.mra.defaults";

	/**
	 * Screen ID
	 */
	private static final String MAINTAINCCA_SCREEN = "mailtracking.mra.defaults.maintaincca";


	private static final String TOMAINTAINCCA = "tomaintaincca";
	
	private static final String ISS_PARTY_AIRLINE = "A";
	
	/**
	 * Module name
	 */
	private static final String MODULE_MAINTAINCCA = "mailtracking.mra.defaults";

	/**
	 * DSN POPUP SCREENID
	 */
	private static final String DSNPOPUP_SCREENID = "mailtracking.mra.defaults.dsnselectpopup";
	

	

	/**
	 * Execute method
	 * 
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */

	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {

		log.entering(CLASS_NAME, "execute");
		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		GPABillingInvoiceEnquiryForm form =(GPABillingInvoiceEnquiryForm)invocationContext.screenModel;
		GPABillingInvoiceEnquirySession gPABillingInvoiceEnquirySession = (GPABillingInvoiceEnquirySession)getScreenSession(
				MODULE, SCREEN_ID);

		String counter = form.getCounter();
		Collection<CN66DetailsVO> cN66DetailsVOs = gPABillingInvoiceEnquirySession
				.getCN66VOs();
		ArrayList<CN66DetailsVO> cN66DetailsVOArraylist = new ArrayList<CN66DetailsVO>(
				cN66DetailsVOs);
		CN66DetailsVO cN66DetailsVO;
		log.log(Log.FINE, "inside *****<<<<counter>>>>----------  ", counter);
		cN66DetailsVO= cN66DetailsVOArraylist.get(Integer.parseInt(counter));
		log
				.log(Log.FINE, "Inside navigatecommand command... >>",
						cN66DetailsVO);
		MaintainCCASession maintainCCASession = (MaintainCCASession) getScreenSession(
				MODULE_NAME_MAINTAIN_CCA, MAINTAINCCA_SCREEN);
		MaintainCCAFilterVO maintainCCAFilterVO = new MaintainCCAFilterVO();
		if(cN66DetailsVO.getCcaRefNo()!=null && cN66DetailsVO.getCcaRefNo().trim().length()>0){
			maintainCCAFilterVO.setCompanyCode(cN66DetailsVO.getCompanyCode());
			//maintainCCAFilterVO.setDsnNumber(cN66DetailsVO.getDsn());
			//maintainCCAFilterVO.setDsnDate(cN66DetailsVO.getReceivedDate());
			//maintainCCAFilterVO.setIssuingParty("G");
			maintainCCAFilterVO.setCcaReferenceNumber(cN66DetailsVO.getCcaRefNo());
			maintainCCAFilterVO.setUsrCCANumFlg("N");
		}else{
			DSNPopUpSession dSNPopUpSession = getScreenSession(MODULE_MAINTAINCCA,DSNPOPUP_SCREENID);
			maintainCCAFilterVO.setCompanyCode(cN66DetailsVO.getCompanyCode());
			maintainCCAFilterVO.setBillingBasis(cN66DetailsVO.getBillingBasis());
			maintainCCAFilterVO.setDsnDate(cN66DetailsVO.getReceivedDate());
			maintainCCAFilterVO.setConsignmentDocNum(cN66DetailsVO.getConsDocNo());
			maintainCCAFilterVO.setConsignmentSeqNum(Integer.parseInt(cN66DetailsVO.getConsSeqNo()));
			maintainCCAFilterVO.setPartyCode("NZ");
			maintainCCAFilterVO.setIssuingParty(ISS_PARTY_AIRLINE);
			maintainCCAFilterVO.setUsrCCANumFlg("N");
			DSNPopUpVO dsnPopUpVO = new DSNPopUpVO();
			log.log(Log.FINE, "FilterVO from gpa enq screen----->",
					maintainCCASession.getMaintainCCAFilterVO());
			dsnPopUpVO.setBlgBasis(cN66DetailsVO.getBillingBasis());
			dsnPopUpVO.setDsnDate(cN66DetailsVO.getReceivedDate().toDisplayDateOnlyFormat());
			dsnPopUpVO.setCompanyCode(cN66DetailsVO.getCompanyCode());
			dsnPopUpVO.setCsgdocnum(cN66DetailsVO.getConsDocNo());
			dsnPopUpVO.setCsgseqnum(Integer.parseInt(cN66DetailsVO.getConsSeqNo()));
			dsnPopUpVO.setGpaCode(cN66DetailsVO.getGpaCode());
			dSNPopUpSession.setSelectedDespatchDetails(dsnPopUpVO);
		}
		
		log.log(Log.FINE,
				"Inside navigatecommand command..maintainCCAFilterVO. >>",
				maintainCCAFilterVO);
		maintainCCASession.setCCAFilterVO(maintainCCAFilterVO);
		invocationContext.target = TOMAINTAINCCA;
		log.exiting(CLASS_NAME, "execute");

	}

}
