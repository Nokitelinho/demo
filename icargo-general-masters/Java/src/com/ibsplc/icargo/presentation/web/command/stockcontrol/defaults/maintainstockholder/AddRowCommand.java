/*
 * AddRowCommand.java Created on May 18, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.stockcontrol.defaults.maintainstockholder;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.stockcontrol.defaults.MaintainStockHolderSession;
import com.ibsplc.icargo.presentation.web.struts.form.stockcontrol.defaults.MaintainStockHolderForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-1870
 *
 */
public class AddRowCommand extends BaseCommand {

	private static final String OPERATION_FLAG_INSERT = "I";

	//Added by A-1927 @ NRT on 19-Jul-2007 for NCA Bug Fix starts
	private static final String DEFAULT_DOCTYPE = "AWB";

	private static final String DEFAULT_DOCSUBTYPE = "S";
	//Added by A-1927 @ NRT on 19-Jul-2007 for NCA Bug Fix ends

	private Log log = LogFactory.getLogger("SYSTEM ENVIRONMENT");
	/**
	 * The execute method in BaseCommand
	 * @author A-1754
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		//log.entering("AddRowCommand","execute");
		/**
		 * Added by A-4772 for ICRD-9882.Changed the 
		 * Screen id value as per standard for UISK009
		 */
		MaintainStockHolderSession session= getScreenSession( "stockcontrol.defaults","stockcontrol.defaults.maintainstockholder");
		MaintainStockHolderForm maintainStockHolderForm=(MaintainStockHolderForm)invocationContext.screenModel;
		StockVO stockVO=new StockVO();
		stockVO.setReorderLevel(0);
		stockVO.setReorderQuantity(0);
		stockVO.setAutoprocessQuantity(0);
		stockVO.setRemarks("");
		//Added by A-1927 @ NRT on 19-Jul-2007 for NCA Bug Fix starts
		stockVO.setDocumentSubType(DEFAULT_DOCSUBTYPE);
		stockVO.setDocumentType(DEFAULT_DOCTYPE);
		//Added by A-1927 @ NRT on 19-Jul-2007 for NCA Bug Fix ends
		stockVO.setStockApproverCode("");
		stockVO.setReorderAlertFlag(false);
		stockVO.setAutoRequestFlag(false);
		stockVO.setAirlineIdentifier(getApplicationSession().getLogonVO().getOwnAirlineIdentifier());
		stockVO.setOperationFlag(OPERATION_FLAG_INSERT);
		Collection<StockVO> stockVo=session.getStockVO();
		if(stockVo!=null){
			log.log(Log.FINE,"***********************StockVo is   not null*********");
			stockVo.add(stockVO);
			session.setStockVO(stockVo);
		}else{
			log.log(Log.FINE,"***********************StockVo is   null*********");
			Collection<StockVO> newList = new ArrayList<StockVO>();
			newList.add(stockVO);
			session.setStockVO(newList);
		}
		invocationContext.target = "screenload_success";
		//log.exiting("AddSegmentCommand","execute");


	}

}
