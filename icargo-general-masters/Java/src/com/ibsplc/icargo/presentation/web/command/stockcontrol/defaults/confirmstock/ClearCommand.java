/*
 * ClearCommand.java Created on Feb 14, 2012
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.stockcontrol.defaults.confirmstock;

import java.util.Collection;

import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockHolderPriorityVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.stockcontrol.defaults.ConfirmStockSession;
import com.ibsplc.icargo.presentation.web.struts.form.stockcontrol.defaults.ConfirmStockForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-4443
 *
 */
public class ClearCommand extends BaseCommand {

	
	private static final String AWB = "AWB";
	private static final String S = "S";
	

	private Log log = LogFactory.getLogger("SYSTEM ENVIRONMENT");


	/**
	 * The execute method in BaseCommand
	 *
	 * @author A-4443
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		log.entering("ClearCommand", "execute");

		ConfirmStockForm confirmStockForm = (ConfirmStockForm) invocationContext.screenModel;
		ConfirmStockSession session = getScreenSession("stockcontrol.defaults",
				"stockcontrol.defaults.confirmstock");
		confirmStockForm.setManual(false);
		//commented by T-1917 as part of bug ICRD-14899 starts
		//confirmStockForm.setDocType("AWB");
		//confirmStockForm.setDocType("");
		confirmStockForm.setDocSubType("");
		confirmStockForm.setLevel("");
		confirmStockForm.setStockHolderCode("");
		confirmStockForm.setDocType("");
		//Bug ID : ICRD-17497 - Jeena - Setting S as the default subdoctype when Clear button is clicked
		confirmStockForm.setDocSubType("");
		//confirmStockForm.setDocType(S);
		//commented by T-1917 as part of bug ICRD-14899 ends
		confirmStockForm.setStockHolderCode(null);
		confirmStockForm.setStockHolderType(null);
		confirmStockForm.setFromDate(null);
		confirmStockForm.setToDate(null);
		confirmStockForm.setOperation(null); 
		Collection<StockHolderPriorityVO> stockHolderPriorityVO = session
				.getPrioritizedStockHolders();

		for (StockHolderPriorityVO stockHolderPriority : stockHolderPriorityVO) {

			stockHolderPriority.setStockHolderCode(null);

		}
		session.setFilterDetails(null);
		session.setPageStockRequestVO(null);
		session.setTransitStockVOs(null);
		invocationContext.target = "screenload_success";
	}

}
