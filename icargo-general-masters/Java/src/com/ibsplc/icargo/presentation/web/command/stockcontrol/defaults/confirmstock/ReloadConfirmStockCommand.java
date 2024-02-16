/*
 * ReloadConfirmStockCommand.java Created on Jun 14, 2012
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.stockcontrol.defaults.confirmstock;

import java.util.Collection;

import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockRequestFilterVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.TransitStockVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.stockcontrol.defaults.StockControlDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.stockcontrol.defaults.ConfirmStockSession;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-2881
 *
 */
public class ReloadConfirmStockCommand extends BaseCommand {
	
	private Log log = LogFactory.getLogger("STOCKCONTROL DEFAULTS");
	
	private static final String MODULE="stockcontrol.defaults";
	
	private static final String SUB_MODULE="stockcontrol.defaults.confirmstock";
	
	private static final String ACTION_SUCCESS="action_success";
	
	private static final String ACTION_FAILURE="action_failure";
	

	/* (non-Javadoc)
	 * @see com.ibsplc.icargo.framework.web.command.Command#execute(com.ibsplc.icargo.framework.web.command.InvocationContext)
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		log.entering("ReloadConfirmStockCommand", "execute");
		ConfirmStockSession session = getScreenSession(MODULE, SUB_MODULE);
		StockRequestFilterVO stockRequestFilterVO = null;
		Collection<TransitStockVO> transitStockVOs = null;

		stockRequestFilterVO = session.getFilterDetails();

		try {
			if (stockRequestFilterVO != null) {
				transitStockVOs = new StockControlDefaultsDelegate()
						.findTransitStocks(stockRequestFilterVO);
			}

		} catch (BusinessDelegateException e) {
			log
					.log(Log.SEVERE,
							"Business Delegate Exception thrown from StockControl Defaults");
		}
		if (transitStockVOs != null && transitStockVOs.size() > 0) {
			session.setTransitStockVOs(transitStockVOs);
			invocationContext.target = ACTION_SUCCESS;
		} else {
			ErrorVO error = new ErrorVO("stockcontrol.defaults.norecordsfound");
			error.setErrorDisplayType(ErrorDisplayType.ERROR);
			invocationContext.addError(error);
			session.setTransitStockVOs(null);
			invocationContext.target = ACTION_FAILURE;

		}
		log.exiting("ReloadConfirmStockCommand", "execute");
	}

}
