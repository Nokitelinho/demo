/*
 * ScreenLoadCommand.java Created on June 11, 2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.airlinebilling.inward.captureinvoice;
/**
 * @author a-3447
 */

import static com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD;

import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.util.unit.UnitConstants;
import com.ibsplc.icargo.framework.util.unit.UnitException;
import com.ibsplc.icargo.framework.util.unit.UnitFormatter;
import com.ibsplc.icargo.framework.util.unit.vo.UnitRoundingVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.airlinebilling.inward.CaptureInvoiceSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.airlinebilling.inward.CaptureMailInvoiceForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author a-3447
 * 
 */
public class ScreenLoadCommand extends BaseCommand {
	/**
	 * Logger for the class
	 */
	private Log log = LogFactory.getLogger("CaptureInvoice ScreenloadCommand");

	/**
	 * 
	 * Class name
	 */
	private static final String CLASS_NAME = "ScreenLoadCommand";

	/**
	 * Module name
	 */

	private static final String MODULE_NAME = "mailtracking.mra.airlinebilling";

	/**
	 * Screen Id
	 */
	private static final String SCREEN_ID = "mailtracking.mra.airlinebilling.inward.captureinvoice";

	/**
	 * Target mappings for succes and failure
	 */
	private static final String ACTION_SUCCESS = "screenload_success";

	/**@author a-3447
	 * @param invocationContext
	 * @exception CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		log.entering(CLASS_NAME, "execute");
		CaptureInvoiceSession captureInvoiceSession = (CaptureInvoiceSession) getScreenSession(
				MODULE_NAME, SCREEN_ID);
		  ApplicationSessionImpl applicationSession = getApplicationSession();
		   LogonAttributes logonAttributes = applicationSession.getLogonVO();
		   UnitRoundingVO unitRoundingVO = new UnitRoundingVO();
		   captureInvoiceSession.setWeightRoundingVO(unitRoundingVO);				
			setUnitComponent(logonAttributes.getStationCode(),captureInvoiceSession);	
		CaptureMailInvoiceForm captureInvoiceForm = (CaptureMailInvoiceForm) invocationContext.screenModel;
		captureInvoiceSession.removeAirlineCN51SummaryVO();
		captureInvoiceSession.removeFilterVo();
		//captureInvoiceSession.removeAllAttributes();
		captureInvoiceSession.removeFilterVo();
		captureInvoiceForm.setScreenStatusFlag(SCREEN_STATUS_SCREENLOAD);
		log.log(Log.INFO, "captureInvoiceForm ---> ", captureInvoiceForm.getScreenStatusFlag());
		invocationContext.target = ACTION_SUCCESS;
		log.exiting(CLASS_NAME, "execute");
	}
	 private void setUnitComponent(String stationCode,
			 CaptureInvoiceSession captureInvoiceSession){
			UnitRoundingVO unitRoundingVO = null;
			try{
				//Modified as part of bug ICRD-101743 by A-5526 
				unitRoundingVO=UnitFormatter.getUnitRoundingForUnitCode(UnitConstants.WEIGHT, UnitConstants.WEIGHT_UNIT_DEFAULT);	      		
				
				captureInvoiceSession.setWeightRoundingVO(unitRoundingVO);			
				
			   }catch(UnitException unitException) {
					unitException.getErrorCode();
			   }
		}  
}
