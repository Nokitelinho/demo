/*
 * ClearScreenCommand.java Created on Aug 26, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.stockcontrol.defaults.liststockrequest;

import static com.ibsplc.icargo.framework.util.time.LocalDate.CALENDAR_DATE_FORMAT;

import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;



import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockHolderPriorityVO;

import com.ibsplc.icargo.presentation.web.struts.form.stockcontrol.defaults.ListStockRequestForm;
import com.ibsplc.icargo.presentation.web.session.interfaces.stockcontrol.defaults.ListStockRequestSession;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import com.ibsplc.xibase.util.time.DateUtilities;

import java.util.Collection;


/**
 * @author A-1952
 *
 */
public class ClearScreenCommand extends BaseCommand {
	//Added by A-1927 @ NRT on 01-Aug-2007 for NCA Bug Fix starts
	private static final String AWB = "AWB";
	private static final String S = "S";
	//Added by A-1927 @ NRT on 01-Aug-2007 for NCA Bug Fix ends
	private Log log = LogFactory.getLogger("SYSTEM ENVIRONMENT");

	/**
	 * The execute method in BaseCommand
	 *
	 * @author A-1952
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {

    	log.entering("ClearScreenCommand", "execute");
		ListStockRequestForm listStockRequestForm = (ListStockRequestForm) invocationContext.screenModel;
    	ListStockRequestSession session =
    		getScreenSession("stockcontrol.defaults", "stockcontrol.defaults.liststockrequest");

    	String dateString = DateUtilities.getCurrentDate(CALENDAR_DATE_FORMAT);
    	listStockRequestForm.setToDate(dateString);
        listStockRequestForm.setFromDate(dateString);
		//Modified by A-1927 @ NRT on 01-Aug-2007 for NCA Bug Fix starts
		//listStockRequestForm.setDocType("");
		listStockRequestForm.setManual(false);
		listStockRequestForm.setReqRefNo("");
		listStockRequestForm.setStatus("");
		listStockRequestForm.setCode("");
		listStockRequestForm.setLevel("");
		//listStockRequestForm.setSubType("");
		//docType and subType set to null as part of icrd-4259 by A-5117
		listStockRequestForm.setDocType("");
		listStockRequestForm.setSubType("");
		
		//Added by A-5117 for the ICRD-4259
		listStockRequestForm.setAwbPrefix("");
		listStockRequestForm.setPartnerAirline(false);
		
		 //Modified by A-1927 @ NRT on 01-Aug-2007 for NCA Bug Fix ends
		listStockRequestForm.setStockHolderType("");

		listStockRequestForm.setStockholderCode(null);
		Collection<StockHolderPriorityVO> stockHolderPriorityVO = session.getPrioritizedStockHolders();

        for(StockHolderPriorityVO stockHolderPriority:stockHolderPriorityVO){

      		 stockHolderPriority.setStockHolderCode(null);

        }
		session.setPageStockRequestVO(null);
		session.setCollectionStockRequestVO(null);
		log.exiting("ClearScreenCommand", "execute");
		invocationContext.target = "screenload_success";
    }

}
