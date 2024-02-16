/*
 * CloseProrationCommand.java Created on Aug 08, 2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.defaults.viewproration;

import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.airlinebilling.defaults.ListInterlineBillingEntriesSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.MRAViewProrationSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.MRAViewProrationForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;



/**
 * @author A-2554
 *
 */
public class CloseProrationCommand extends BaseCommand {

	private  Log log = LogFactory.getLogger("MRA DEFAULTS");
	private static final String SCREENID_MAILPRO ="mailtracking.mra.defaults.mailproration";
	private static final String MODULE_NAME = "mailtracking.mra.defaults";
	private static final String CLOSE_SUCCESS = "close_success";
	/**
	 * added by A-3229 for AirNZ CR 882
	 */
	private static final String CLOSE_LOGSUCCESS="close_logsuccess";
	private static final String CLOSE_TORATEAUDITDETAILS = "close_torateauditdetails";
	private static final String FROM_RATEAUDITDETAILS ="fromRateAuditDetails";
	private static final String DSNPOPUP_SCREENID ="mailtracking.mra.defaults.dsnselectpopup";
	private static final String BLANK ="";
	private static final String SCREENID = "mailtracking.mra.defaults.rateauditdetails";
	private static final String SCREENID_VIEW_PRORATION ="mailtracking.mra.defaults.viewproration";
	private static final String FROM_INTERLINEBILLING ="fromInterLineBilling";
	private static final String INTERLINEBILLING ="interlinebilling";
	private static final String FROM_DESPATCHENQUIRY ="fromDespatchEnquiry";
	private static final String CLOSE_TOINTERLINEBILLING = "close_interlinebilling";
	private static final String FROM_PRORATIONLOG="prorationlog";
	private static final String CLOSE_DESPATCHSUCCESS = "close_despatchsuccess";
	private static final String MODULE_AIRLINE = "mailtracking.mra.airlinebilling";
	private static final String SCREEN_ID_AIRLINE = "mailtracking.mra.airlinebilling.defaults.listinterlinebillingentries";
	private static final String TO_INTERLINE = "toInterLineBilling";
	private static final String PARENTSCREEN_ID_RATEAUDITDETAILS = "rateAuditDetails";
	private static final String FROM_CRAPOSTINGDETAILS="fromCraPostingDetails";
	private static final String CLOSE_CRAPOSTINGDETAILS="close_crapostingdetails";
	private static final String FROM_MANUAL="ManualProration";
	public void execute(InvocationContext invocationContext) throws CommandInvocationException {
		// TODO Auto-generated method stub

		MRAViewProrationSession session = getScreenSession(MODULE_NAME,SCREENID_VIEW_PRORATION);
		ListInterlineBillingEntriesSession interlineBillingSession = (ListInterlineBillingEntriesSession) getScreenSession(
    			MODULE_AIRLINE, SCREEN_ID_AIRLINE);
		session.setProrationVOs(null);
		session.setPrimaryProrationVOs(null);
		session.setSecondaryProrationVOs(null);
		MRAViewProrationForm form=(MRAViewProrationForm)invocationContext.screenModel;
		form.setDispatch("");
		form.setConDocNo("");
		form.setFlightNo("");
		form.setFlightDate("");
		form.setOrigin("");
		form.setDest("");
		form.setGpa("");
		form.setGpaName("");
		form.setCategory("");
		form.setTotWt("");
		form.setCategory("");
		log.log(Log.INFO, "FromScreen...in view..", form.getFromScreen());
		log.log(Log.INFO, "ToScreen in session..", interlineBillingSession.getToScreen());
		if(PARENTSCREEN_ID_RATEAUDITDETAILS.equals(session.getParentScreenID())){
			invocationContext.target=CLOSE_TORATEAUDITDETAILS;
		}else
		if(FROM_RATEAUDITDETAILS.equals(form.getFromScreen())){
			invocationContext.target=CLOSE_TORATEAUDITDETAILS;
		}else if(INTERLINEBILLING.equals(form.getFromScreen())){
				
			interlineBillingSession.setCloseFlag(TO_INTERLINE);
			invocationContext.target=CLOSE_TOINTERLINEBILLING;
		}
		else if(FROM_INTERLINEBILLING.equals(interlineBillingSession.getToScreen())&&
			FROM_MANUAL.equals(form.getFromScreen())){
		interlineBillingSession.setCloseFlag(TO_INTERLINE);
		invocationContext.target=CLOSE_TOINTERLINEBILLING;
		interlineBillingSession.setToScreen(null);
		}else if(FROM_PRORATIONLOG.equalsIgnoreCase(form.getFromScreen())){
			invocationContext.target=CLOSE_LOGSUCCESS;
		}else if (FROM_DESPATCHENQUIRY.equalsIgnoreCase(form.getFromScreen())){
			invocationContext.target=CLOSE_DESPATCHSUCCESS;
		}else if (FROM_CRAPOSTINGDETAILS.equalsIgnoreCase(form.getFromScreen())){
			invocationContext.target=CLOSE_CRAPOSTINGDETAILS;
		}else{
			invocationContext.target=CLOSE_SUCCESS;
		}
		log.log(Log.FINE, "session value of filter vo",interlineBillingSession.getAirlineBillingFilterVO());
		}


	}


