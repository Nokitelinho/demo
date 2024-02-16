/*
 * ClearCommand.java Created on Aug 01, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.messaging.generatescm;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.msgbroker.message.ListMessageSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.messaging.GenerateSCMSession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.messaging.GenerateSCMForm;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-1862
 *
 */
public class ClearCommand extends BaseCommand {

	private Log log = LogFactory.getLogger("Generate SCM");

	private static final String SCREENID = "uld.defaults.generatescm";

	private static final String MODULE_NAME = "uld.defaults";

	private static final String CLEAR_SUCCESS= "clear_success";

	private static final String BLANK = "";
	
	private static final String MSGMODULE_NAME = "msgbroker.message";
	
	private static final String MSGSCREEN_ID = "msgbroker.message.listmessages";
	

    /**
     * @param invocationContext
     * @return 
     * @throws CommandInvocationException
    */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	/**
		 * Obtain the logonAttributes
		 */
		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		
    	GenerateSCMSession generateSCMSession = 
			(GenerateSCMSession)getScreenSession(MODULE_NAME,SCREENID);		
    	generateSCMSession.removeAllAttributes();		
    	ListMessageSession msgsession = 
			getScreenSession( MSGMODULE_NAME,MSGSCREEN_ID);
    	msgsession.removeAllAttributes();
		GenerateSCMForm generateSCMForm = 
			(GenerateSCMForm) invocationContext.screenModel;
		
		generateSCMForm.setPageURL("");
		generateSCMForm.setListStatus("");
		generateSCMForm.setScmAirline("");		
		
		generateSCMForm.setScmStockCheckTime(BLANK);
		generateSCMForm.setMsgFlag(BLANK);
		generateSCMForm.setScmMessageSendingFlag(BLANK);
		 //Added by A-6344 as a part of ICRD-55460 starts
		generateSCMForm.setUldNumberStock(BLANK);
		generateSCMForm.setUldStatus("");
		generateSCMForm.setDynamicQuery(BLANK);
		 //Added by A-6344 as a part of ICRD-55460 end
		
        //Added by A-3045 as a part of defaulting airline code in page starts
		Collection<ErrorVO> error = new ArrayList<ErrorVO>();
    	log.log(Log.FINE, "logonAttributes.getCompanyCode()------->",
				logonAttributes.getCompanyCode());
		log.log(Log.FINE, "logonAttributes.getUserId()     ------->",
				logonAttributes.getUserId());
			/*try {
			airlineCode = new ULDDefaultsDelegate()
					.findDefaultAirlineCode(logonAttributes.getCompanyCode(),logonAttributes.getUserId());
		} catch (BusinessDelegateException businessDelegateException) {
//printStackTrrace()();
			error = handleDelegateException(businessDelegateException);
		}
		if(airlineCode != null && airlineCode.trim().length() > 0){
			generateSCMForm.setScmAirline(airlineCode); 
		}else{*/
			if(logonAttributes.isAirlineUser()){
				generateSCMForm.setScmAirline(logonAttributes.getOwnAirlineCode()); 
			}
		//}
       //Added by A-3045 as a part of defaulting airline code in page ends
		
		generateSCMForm.setScmAirport(logonAttributes.getAirportCode());
		//added by T-1927 for the BUG icrd-30652
		LocalDate stockCheckDate = new LocalDate(generateSCMForm.getScmAirport(),Location.ARP,true);
		log.log(Log.FINE, "stockCheckDate ------------------>", stockCheckDate);
		generateSCMForm.setScmStockCheckDate(stockCheckDate.toDisplayDateOnlyFormat());
		LocalDate stockCheck = new LocalDate(generateSCMForm.getScmAirport(),Location.ARP,true);
		String stockCheckTim=stockCheck.toDisplayFormat("HH:mm");
		log.log(Log.FINE, "\n\n\n\n current time", stockCheckTim);
		//	generateSCMForm.setScmStockCheckTime(stockCheckTim);				
		//Commented by a-3045 for bug 26529 
		//generateSCMForm.setAirportDisable("GHA");   
		invocationContext.addAllError(error);
		//added by a-3045 for bug20936 starts
		generateSCMForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
		//added by a-3045 for bug20936 ends
		invocationContext.target =CLEAR_SUCCESS;
    }




}
