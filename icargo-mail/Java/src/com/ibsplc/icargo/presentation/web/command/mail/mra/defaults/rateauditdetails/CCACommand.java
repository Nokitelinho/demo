/*
 * CCACommand.java Created on Jul 17, 2008
 *
 * Copyright 2008 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.defaults.rateauditdetails;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.DSNPopUpVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.MaintainCCAFilterVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.DSNPopUpSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.MaintainCCASession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.RateAuditDetailsForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
/**
 * 
 * @author A-2391
 *
 */
public class CCACommand  extends BaseCommand {
	
	private Log log = LogFactory.getLogger("RATE AUDITDETAILS CCACommand");
	private static final String CCA_SUCCESS = "cca_success";
	/**
	 * Module name
	 */
	private static final String MODULE_NAME = "mailtracking.mra.defaults";

	/**
	 * Screen ID
	 */
	private static final String MAINTAINCCA_SCREEN = "mailtracking.mra.defaults.maintaincca";
	/**
	 * DSN POPUP SCREENID
	 */
	private static final String DSNPOPUP_SCREENID = "mailtracking.mra.defaults.dsnselectpopup";
	
	private static final String NOT_USRCCA = "N";
	private static final String ISS_PARTY_AIRLINE = "A";
	
	
	/**
	 * *
	 * 
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	 public void execute(InvocationContext invocationContext)
     throws CommandInvocationException {
		 
		 log.entering("CCACommand", "execute");
		 
		 RateAuditDetailsForm rateAuditDetailsForm=(RateAuditDetailsForm)invocationContext.screenModel;
		 
		 MaintainCCASession maintainCCASession = 
				(MaintainCCASession) getScreenSession(MODULE_NAME, MAINTAINCCA_SCREEN);
		 

			ApplicationSessionImpl applicationSession = getApplicationSession();
			LogonAttributes logonAttributes = applicationSession.getLogonVO();
		 
		    DSNPopUpSession dSNPopUpSession = getScreenSession(MODULE_NAME,DSNPOPUP_SCREENID);
			DSNPopUpVO dsnPopUpVO = dSNPopUpSession.getSelectedDespatchDetails();
			
			MaintainCCAFilterVO maintainCCAFilterVO = new MaintainCCAFilterVO();
			
			
			maintainCCAFilterVO.setCompanyCode(dsnPopUpVO.getCompanyCode());
			maintainCCAFilterVO.setDsnNumber(dsnPopUpVO.getBlgBasis());			
			maintainCCAFilterVO.setDsnDate(new LocalDate(LocalDate.NO_STATION,Location.NONE,false).setDate(dsnPopUpVO.getDsnDate()));
			maintainCCAFilterVO.setConsignmentDocNum(dsnPopUpVO.getCsgdocnum());
			maintainCCAFilterVO.setConsignmentSeqNum(dsnPopUpVO.getCsgseqnum());
			maintainCCAFilterVO.setPOACode(dsnPopUpVO.getGpaCode());
			maintainCCAFilterVO.setUsrCCANumFlg(NOT_USRCCA);
				
			//on rate audit an actual entry is made in ccadtl for own airline so need to fetch that data while going to maintain cca
			maintainCCAFilterVO.setPartyCode(logonAttributes.getOwnAirlineCode());
			maintainCCAFilterVO.setIssuingParty(ISS_PARTY_AIRLINE);
		 
		    maintainCCASession.setCCAFilterVO(maintainCCAFilterVO);
		    maintainCCASession.removeCCAdetailsVO();			
			maintainCCASession.removeCCAdetailsVOs();
			maintainCCASession.removeCCARefNumbers();
		 
		 log.exiting("CCACommand", "execute");
		 
		 invocationContext.target = CCA_SUCCESS;
		 
	 }


}
