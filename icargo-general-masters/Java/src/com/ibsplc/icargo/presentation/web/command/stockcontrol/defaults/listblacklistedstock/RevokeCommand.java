/*
 * RevokeCommand.java Created on Sep 21, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.presentation.web.command.stockcontrol.defaults.listblacklistedstock;

import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.struts.form.stockcontrol.defaults.ListBlackListedStockForm;
import com.ibsplc.icargo.presentation.web.session.interfaces.stockcontrol.defaults.ListBlackListedStockSession;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.BlacklistStockVO;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-1927
 *
 */

public class RevokeCommand extends BaseCommand {

	private Log log = LogFactory.getLogger("SYSTEM ENVIRONMENT");
	/**
	 * The execute method in BaseCommand
	 * @author A-1927
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */

	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {

		log.entering("RevokeCommand","execute");
		ListBlackListedStockForm listBlackListedStockForm = (ListBlackListedStockForm) invocationContext.screenModel;
		ListBlackListedStockSession session = (ListBlackListedStockSession)
									getScreenSession("stockcontrol.defaults","stockcontrol.defaults.listblacklistedstock");


		Page<BlacklistStockVO> blacklistStockVOs  =  session.getBlacklistStockVOs();
		BlacklistStockVO vo=new BlacklistStockVO();

		String[] blacklistCheck=listBlackListedStockForm.getBlacklistCheck();
		
		if(blacklistStockVOs!=null && blacklistCheck!=null){

			for(int i=0; i<blacklistCheck.length; i++){
			        vo = blacklistStockVOs.get(Integer.parseInt(blacklistCheck[i]));
					break;
			}
			session.setBlacklistStockVO((BlacklistStockVO)vo);

		}
		log.exiting("RevokeCommand","execute");
		invocationContext.target = "screenload_success";
	}
}

