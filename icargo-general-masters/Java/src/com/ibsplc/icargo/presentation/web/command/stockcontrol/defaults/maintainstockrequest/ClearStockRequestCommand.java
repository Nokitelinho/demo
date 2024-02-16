
/*
 * ClearStockRequestCommand.java Created on Aug 26, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.presentation.web.command.stockcontrol.defaults.maintainstockrequest;

import static com.ibsplc.icargo.framework.util.time.LocalDate.CALENDAR_DATE_FORMAT;

import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.struts.form.stockcontrol.defaults.MaintainStockRequestForm;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockHolderPriorityVO;
import com.ibsplc.icargo.presentation.web.session.interfaces.stockcontrol.defaults.MaintainStockRequestSession;
import java.util.Collection;

import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import com.ibsplc.xibase.util.time.DateUtilities;

/**
 * @author A-1927
 *
 */
public class ClearStockRequestCommand extends BaseCommand {

	//Modified by A-1927 @ NRT on 01-Aug-2007 for NCA bug Fix starts
	private static final String AWB = "AWB";
	private static final String S = "S";
	//Modified by A-1927 @ NRT on 01-Aug-2007 for NCA bug Fix ends

	private Log log = LogFactory.getLogger("SYSTEM ENVIRONMENT");
	/**
	 * The execute method in BaseCommand
	 * @author A-1927
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */

	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {

		log.entering("ClearStockRequestCommand","execute");
		MaintainStockRequestForm maintainStockRequestForm = (MaintainStockRequestForm) invocationContext.screenModel;
		/**
		 * Added by A-4772 for ICRD-9882.Changed the 
		 * Screen id value as per standard for UISKC002
		 */
		MaintainStockRequestSession session =
			(MaintainStockRequestSession)getScreenSession("stockcontrol.defaults","stockcontrol.defaults.maintainstockrequest");
		ApplicationSessionImpl applicationSession = getApplicationSession();
	    LogonAttributes logonAttributes  =  applicationSession.getLogonVO();

		Collection<OneTimeVO> statusColl=session.getOneTimeStatus();
			for(OneTimeVO status:statusColl){
				if("N".equals(status.getFieldValue())) {
					maintainStockRequestForm.setStatus(status.getFieldDescription());
				}
			}


		maintainStockRequestForm.setReqRefNo("");
		maintainStockRequestForm.setReqStock("");
		maintainStockRequestForm.setAllocatedStock("");
		maintainStockRequestForm.setRemarks("");
		maintainStockRequestForm.setAppRejRemarks("");
		//Modified by A-1927 @ NRT on 01-Aug-2007 for NCA bug Fix starts
		//uncommented as part of icrd-4259 by A-5117
		maintainStockRequestForm.setDocType("");
		maintainStockRequestForm.setSubType("");
		maintainStockRequestForm.setPartnerAirline(false);
		maintainStockRequestForm.setAwbPrefix("");
		//commented as part of icrd-4259 by A-5117
		//maintainStockRequestForm.setDocType(AWB);
		//maintainStockRequestForm.setSubType(S);
		//Modified by A-1927 @ NRT on 01-Aug-2007 for NCA bug Fix ends
		maintainStockRequestForm.setCode("");
		maintainStockRequestForm.setStockHolderType("");
		maintainStockRequestForm.setLevel("");
		

		maintainStockRequestForm.setManual(false);
		maintainStockRequestForm.setStockHolder(null);
		//newly added line
		maintainStockRequestForm.setMode("I");
		Collection<StockHolderPriorityVO> stockHolderPriorityVO = session.getPrioritizedStockHolders();

        for(StockHolderPriorityVO stockHolderPriority:stockHolderPriorityVO){

      		 stockHolderPriority.setStockHolderCode(null);

        }

		String dateString = DateUtilities.getCurrentDate(CALENDAR_DATE_FORMAT);
		maintainStockRequestForm.setDateOfReq(dateString);

		log.entering("ClearStockRequestCommand","execute");
		invocationContext.target = "screenload_success";
	}
}

