/*
 * ClearCommand.java Created on Aug 26, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.presentation.web.command.stockcontrol.defaults.maintainstockholder;


import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import com.ibsplc.icargo.presentation.web.session.interfaces.stockcontrol.defaults.MaintainStockHolderSession;
import com.ibsplc.icargo.presentation.web.struts.form.stockcontrol.defaults.MaintainStockHolderForm;
import java.util.ArrayList;
import java.util.Collection;


/**
 *
 * @author A-1885
 *
 */
public class ClearCommand extends BaseCommand{
	private Log log = LogFactory.getLogger("SYSTEM ENVIRONMENT");
	private static final String SAVE_MODE = "save";
	private static final String NULL_STRING="";
	private static final String OPERATION_FLAG_INSERT = "I";
	//Added by A-1927 @ NRT on 19-Jul-2007 for NCA Bug Fix starts
	private static final String CONTROL_PRIVILEGE_ALL = "All";
	private static final String DEFAULT_DOCTYPE = "AWB";
	private static final String DEFAULT_DOCSUBTYPE = "S";
	//Added by A-1927 @ NRT on 19-Jul-2007 for NCA Bug Fix ends
	/**
	 * @param invocationContext
	 * @throws CommandInvocationException
	 * @return
	 */
	public void execute(InvocationContext invocationContext)
	throws CommandInvocationException {
			/**
			 * Added by A-4772 for ICRD-9882.Changed the 
			 * Screen id value as per standard for UISK009
			 */
		   	MaintainStockHolderSession session= getScreenSession( "stockcontrol.defaults","stockcontrol.defaults.maintainstockholder");
			session.setStockVO(null);
			StockVO stockVO=new StockVO();
			stockVO.setReorderLevel(0);
			stockVO.setReorderQuantity(0);
			stockVO.setAutoprocessQuantity(0);
			stockVO.setRemarks("");
			//Modified by A-1927 @ NRT on 01-Aug-2007 for NCA Bug Fix starts
			stockVO.setDocumentSubType(DEFAULT_DOCSUBTYPE);
			stockVO.setDocumentType(DEFAULT_DOCTYPE);
			//stockVO.setDocumentSubType("");
			//stockVO.setDocumentType("");
			//Modified by A-1927 @ NRT on 01-Aug-2007 for NCA Bug Fix ends
			stockVO.setStockApproverCode("");
			stockVO.setReorderAlertFlag(false);
			stockVO.setAutoRequestFlag(false);
			stockVO.setAirlineIdentifier(getApplicationSession().getLogonVO().getOwnAirlineIdentifier());
			stockVO.setOperationFlag(OPERATION_FLAG_INSERT);
			Collection<StockVO> stockVo=new ArrayList<StockVO>();
			stockVo.add(stockVO);
			session.setStockVO(stockVo);
			MaintainStockHolderForm maintainStockHolderForm=(MaintainStockHolderForm)invocationContext.screenModel;
			maintainStockHolderForm.setStockHolderType(NULL_STRING);
			maintainStockHolderForm.setStockHolderCode(NULL_STRING);
			maintainStockHolderForm.setStockHolderName(NULL_STRING);
			//Added by A-1927 @ NRT on 01-Aug-2007 for NCA Bug Fix starts
			maintainStockHolderForm.setControlPrivilege(CONTROL_PRIVILEGE_ALL);
			//maintainStockHolderForm.setControlPrivilege(NULL_STRING);
			//Added by A-1927 @ NRT on 01-Aug-2007 for NCA Bug Fix ends
			maintainStockHolderForm.setContact(NULL_STRING);
			session.setMode(SAVE_MODE);

			invocationContext.target = "clear_success";
		}


}