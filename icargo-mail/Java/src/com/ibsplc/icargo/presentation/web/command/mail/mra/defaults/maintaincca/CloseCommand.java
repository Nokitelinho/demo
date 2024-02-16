/*
 * CloseCommand.java Created on Aug 1, 2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.defaults.maintaincca;

import static com.ibsplc.xibase.server.framework.vo.AbstractVO.FLAG_NO;

import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.MaintainCCASession;
import com.ibsplc.icargo.presentation.web.session.interfaces.workflow.defaults.MessageInboxSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.MRAMaintainCCAForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-3429
 *
 */
public class CloseCommand extends BaseCommand {

	private static final String MODULE = "mailtracking.mra.defaults";

	private static final String SCREENID = "mailtracking.mra.defaults.maintaincca";

	private static final String CLOSE_LISTCCA = "close_success";

	private static final String CLOSE_SCREEN = "close_maintaincca";
	private static final String CLOSE_SCREEN_TO_RATEAUDIT = "close_torateauditdetails";
	
	private static final String SCREENID_MSGINBOX = "workflow.defaults.messageinbox";	
	private static final String MODULE_MSGINBOX = "workflow.defaults";

	private static final String CLASS_NAME = "CloseCommand";

	private static final String INVOKE_SCREEN = "listCCA";

	private static final String MODULE_NAME = "mailtracking.mra.gpabilling";

	private static final String SCREENID_GPA = "mailtracking.mra.gpabilling.gpabillinginvoiceenquiry";
	private static final String CLOSE_SUCCESS_TOGPAINVOICEENQUIRY="gpainvoiceenquiry";
	
	private static final String CLOSE_SUCCESS_LISTCN="cn51cn66return";
	
	private static final String BLANK="";
	private static final String CLOSE_SUCCESS_TO_BILLINGENTRIES="close_correctionadvice_screen_success"; //Added by A-7929 as part of ICRD-132548 
	private static final String CLOSE_SUCCESS_TO_BILLINGENTRIESUX="navigate_billingentriesux_success"; 
	
	/**
	 * Screen ID
	 */
	private static final String MAINTAINCCA_SCREEN = "mailtracking.mra.defaults.maintaincca";

	private Log log = LogFactory.getLogger("CloseCommand :SUCCESS");
	//added as part of IASCB-2377
	private static final String MESSAGE_INBOX="close_to_messageinbox";
	
	private static final String LISTSTATUS_AFTER_ACTION = "LISTSTATUS_AFTER_ACTION";

	/**
	 * @author A-3429 execute method for Close
	 * @param invocationContext
	 * @exception CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {

		log.entering(CLASS_NAME, "execute");
		MRAMaintainCCAForm maintainCCAForm = (MRAMaintainCCAForm) invocationContext.screenModel;
		log
				.log(Log.FINE, "FROM SCREEN ----->", maintainCCAForm.getFromScreen());
		MaintainCCASession session = (MaintainCCASession) getScreenSession(
				MODULE_NAME, MAINTAINCCA_SCREEN);
		MessageInboxSession messageInboxSession = 
				getScreenSession(MODULE_MSGINBOX, SCREENID_MSGINBOX);	
			session.removeCCAdetailsVO();
			session.removeMaintainCCAFilterVO();
			session.removeCCAdetailsVOs();
			session.removeCCARefNumbers();
			session.removeRateAuditVO();
			session.removeUsrCCANumFlg();
			session.removeSurchargeCCAdetailsVOs();
			session.setFromScreen(BLANK);
			maintainCCAForm.setAutoratedFlag("N");
			maintainCCAForm.setAirlineCode(BLANK);
			maintainCCAForm.setIssueDate(BLANK);
			maintainCCAForm.setCcaNum(BLANK);
			maintainCCAForm.setCcaStatus(BLANK);
			maintainCCAForm.setDsnNumber(BLANK);
			maintainCCAForm.setDsnDate(BLANK);
			maintainCCAForm.setOrigin(BLANK);
			maintainCCAForm.setDestination(BLANK);
			maintainCCAForm.setOriginCode(BLANK);
			maintainCCAForm.setDestnCode(BLANK);
			maintainCCAForm.setCategory(BLANK);
			maintainCCAForm.setSubclass(BLANK);
			maintainCCAForm.setIssueParty("ARL");
			maintainCCAForm.setReason1(BLANK);
			maintainCCAForm.setReason2(BLANK);
			maintainCCAForm.setReason3(BLANK);
			maintainCCAForm.setReason4(BLANK);
			maintainCCAForm.setReason5(BLANK);
			maintainCCAForm.setBilfrmdate(BLANK);    
			maintainCCAForm.setBiltodate(BLANK);
			maintainCCAForm.setUsrCCANumFlg(BLANK);
			maintainCCAForm.setDsnPopupFlag(FLAG_NO);
			maintainCCAForm.setPopupon(BLANK);
			maintainCCAForm.setShowpopUP(BLANK);
			
			maintainCCAForm.setGpaCode(BLANK);
			maintainCCAForm.setDsnNumber(BLANK);
			maintainCCAForm.setAirlineCode(BLANK);
			maintainCCAForm.setIsAutoMca(BLANK);//ADDED BY A-7540
	
		
		if("RateAuditDetails".equals(maintainCCAForm.getFromScreen())){
			maintainCCAForm.setFromScreen("");
			invocationContext.target = CLOSE_SCREEN_TO_RATEAUDIT;
			
		}else if("listCCA".equals(maintainCCAForm.getFromScreen())){
			maintainCCAForm.setFromScreen("");
			invocationContext.target = CLOSE_LISTCCA;
		}else if("gpabillinginvoiceenquiry".equals(maintainCCAForm.getFromScreen())){
			maintainCCAForm.setFromScreen("");
			invocationContext.target = CLOSE_SUCCESS_TOGPAINVOICEENQUIRY;			    		
		}
		/**
		 * @author A-4810
		 * This code is added as part of icrd-13639 
		 * This code is to navigate back to the parent screen LISTCN51CN66 from the MCA screen
		 */
		else if("CN51CN66".equals(maintainCCAForm.getFromScreen())){
			maintainCCAForm.setFromScreen("");
			invocationContext.target = CLOSE_SUCCESS_LISTCN;			    		
		}
		//Added by A-7929 as part of ICRD-132548 starts----
		else if(("listbilling".equals(maintainCCAForm.getFromScreen())) || ("listbillingentries".equals(maintainCCAForm.getFromScreen()))){
			maintainCCAForm.setFromScreen("");
			invocationContext.target = CLOSE_SUCCESS_TO_BILLINGENTRIES;
		}
		//Added by A-7929 as part of ICRD-132548 ends----
		else if("listbillingentriesuxopenPopUp".equals(maintainCCAForm.getFromScreen())){
			invocationContext.target = CLOSE_SUCCESS_TO_BILLINGENTRIESUX;
		}
		//Added as part of IASCB-2377 starts
		else if(("messageinbox".equals(maintainCCAForm.getFromScreen())) ){
			maintainCCAForm.setFromScreen("");
			messageInboxSession.setListStatus(LISTSTATUS_AFTER_ACTION);
			invocationContext.target =  MESSAGE_INBOX;
		}
		//Added as part of IASCB-2377 ends
		else{
			invocationContext.target = CLOSE_SCREEN;
		}
		
				
		
		
		            //		else{
					//		
					//		ListCCASession listCCASession = getScreenSession("mailtracking.mra.defaults", 
					//											"mailtracking.mra.defaults.listcca");
					//		String screen = listCCASession.getCloseStatus();
					//		log.log(Log.FINE, "listCCAFilterVO in close ----->" + listCCASession.getCCAFilterVO());
					//		MaintainCCASession maintainCCASession = (MaintainCCASession) getScreenSession(
					//				MODULE, SCREENID);
					//		log.log(Log.INFO, "selected screen-->" + screen);
					//		maintainCCASession.removeAllAttributes();
					//
					//		GPABillingInvoiceEnquirySession gPABillingInvoiceEnquirySession=(GPABillingInvoiceEnquirySession)getScreenSession(
					//    			MODULE_NAME, SCREENID_GPA);
					//		if("gpabillinginvoiceenquiry".equals(maintainCCAForm.getFromScreen())){
					//    		invocationContext.target = CLOSE_SUCCESS_TOGPAINVOICEENQUIRY;
					//    		return;
					//    		}
					//		else
					//		if (INVOKE_SCREEN.equals(screen)) {
					//			log.log(Log.INFO, "-->" + screen);
					//			listCCASession.setCloseStatus(null);
					//			invocationContext.target = CLOSE_LISTCCA;
					//		} else if (!INVOKE_SCREEN.equals(screen)) {
					//			invocationContext.target = CLOSE_SCREEN;
					//		}
					//
					//	}
		
	}
}
