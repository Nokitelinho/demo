/*
 * ViewProrationCommand.java Created on Jul 17, 2008
 *
 * Copyright 2008 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.defaults.rateauditdetails;


import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.ProrationFilterVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.RateAuditDetailsVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.RateAuditFilterVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.RateAuditVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.MRAViewProrationSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.RateAuditDetailsSession;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
/**
 * 
 * @author A-2391
 *
 */
public class ViewProrationCommand  extends BaseCommand {
	
	private Log log = LogFactory.getLogger("RATE AUDITDETAILS  ViewProrationCommand");
	private static final String SCREENLOAD_SUCCESS = "screenload_success";
	private static final String MODULE_NAME = "mailtracking.mra.defaults";
	private static final String DSNPOPUP_SCREENID ="mailtracking.mra.defaults.dsnselectpopup";
	private static final String SCREENID_MAILPRO ="mailtracking.mra.defaults.mailproration";
	private static final String SCREENID = "mailtracking.mra.defaults.rateauditdetails";
	private static final String SCREENID_VIEW_PRORATION ="mailtracking.mra.defaults.viewproration";
	private static final String RECIEVEABLE = "R";
	private static final String PARENTSCREEN_ID_RATEAUDITDETAILS = "rateAuditDetails";
	/**
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	 public void execute(InvocationContext invocationContext)
     throws CommandInvocationException {
		 
		 log.entering("ViewProrationCommand", "execute");
		 MRAViewProrationSession mailProrationSession = getScreenSession(MODULE_NAME,SCREENID_VIEW_PRORATION);

			RateAuditDetailsSession session=getScreenSession(MODULE_NAME,SCREENID);
			RateAuditFilterVO rateAuditFilterVO=session.getRateAuditFilterVO();
			log.log(Log.INFO, "rateAuditFilterVO--in view----> ",
					rateAuditFilterVO);
			ProrationFilterVO prorationFilterVO = new ProrationFilterVO();
			
			prorationFilterVO.setCompanyCode(rateAuditFilterVO.getCompanyCode());
			prorationFilterVO.setDespatchSerialNumber(rateAuditFilterVO.getBillingBasis());	
			prorationFilterVO.setBillingBasis(rateAuditFilterVO.getBillingBasis());
			prorationFilterVO.setConsigneeDocumentNumber(rateAuditFilterVO.getCsgDocNum());			
			prorationFilterVO.setConsigneeSequenceNumber(rateAuditFilterVO.getCsgSeqNum());
			prorationFilterVO.setPoaCode(rateAuditFilterVO.getGpaCode());
			
			RateAuditVO rateAuditVO=session.getRateAuditVO();
			
			//commented by A-3229
			/*	if(rateAuditVO.getDsnDate()!=null){  					
				prorationFilterVO.setFlightDate(rateAuditVO.getDsnDate());				
				}*/
			Collection<RateAuditDetailsVO>  rateAuditDetailsVOs = new ArrayList<RateAuditDetailsVO>();
			
			rateAuditDetailsVOs = rateAuditVO.getRateAuditDetails();
			
			//Commented by A-3229
			
			/*	for(RateAuditDetailsVO rateAuditDetailsVO : rateAuditDetailsVOs){				
			if(rateAuditDetailsVO.getPayFlag()!=null && RECIEVEABLE.equals(rateAuditDetailsVO.getPayFlag())){		
			if(rateAuditDetailsVO.getCarrierCode()!=null){
				prorationFilterVO.setCarrierCode(rateAuditDetailsVO.getCarrierCode());
				}
			if(rateAuditDetailsVO.getFlightno()!=null){
				prorationFilterVO.setFlightNumber(rateAuditDetailsVO.getFlightno());
			}
			if(rateAuditDetailsVO.getFlightDate()!=null){
				prorationFilterVO.setFlightDate(rateAuditDetailsVO.getFlightDate());
			}
			}
			}*/
			mailProrationSession.setProrationFilterVO(prorationFilterVO);
			mailProrationSession.setParentScreenID(PARENTSCREEN_ID_RATEAUDITDETAILS);
			log.log(Log.INFO, "prorationFilterVO---in view---> ",
					prorationFilterVO);
			log.exiting(" ViewProrationCommand", "execute");
		 
		 invocationContext.target = SCREENLOAD_SUCCESS;
		 
	 }


}

