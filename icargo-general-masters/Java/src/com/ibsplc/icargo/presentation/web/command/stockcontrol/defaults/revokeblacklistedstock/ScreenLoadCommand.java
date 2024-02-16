/**
 * ScreenLoadCommand.java Created on Sep 21, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.presentation.web.command.stockcontrol.defaults.revokeblacklistedstock;

import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.stockcontrol.defaults.ListBlackListedStockSession;
import com.ibsplc.icargo.presentation.web.struts.form.stockcontrol.defaults.RevokeBlackListedStockForm;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.BlacklistStockVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import com.ibsplc.xibase.server.framework.persistence.query.Page;

/**
 * @author A-1927
 *
 */

public class ScreenLoadCommand extends BaseCommand {

	private Log log = LogFactory.getLogger("SYSTEM ENVIRONMENT");
	private static final String SUCCESS = "N";
	/**
	 * The execute method in BaseCommand
	 * @author A-1927
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */

	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {

		log.entering("ScreenLoadCommand","execute");
		RevokeBlackListedStockForm revokeBlackListedStockForm = (RevokeBlackListedStockForm) invocationContext.screenModel;
		ListBlackListedStockSession session = (ListBlackListedStockSession) getScreenSession(
				"stockcontrol.defaults",
				"stockcontrol.defaults.listblacklistedstock");

		Page<BlacklistStockVO> blacklistStockVOs  =  session.getBlacklistStockVOs();
		BlacklistStockVO vo=new BlacklistStockVO();

		if(revokeBlackListedStockForm.getCheckbox()!=null){
			vo = blacklistStockVOs.get(Integer.parseInt(revokeBlackListedStockForm.getCheckbox()));

			session.setBlacklistStockVO((BlacklistStockVO)vo);

		}

		BlacklistStockVO blacklistStockVO = (BlacklistStockVO) session
				.getBlacklistStockVO();

		if (blacklistStockVO != null) {

			revokeBlackListedStockForm.setDocType(blacklistStockVO
					.getDocumentType());
			revokeBlackListedStockForm.setSubType(blacklistStockVO
					.getDocumentSubType());
			revokeBlackListedStockForm.setRangeFrom(blacklistStockVO
					.getRangeFrom());
			revokeBlackListedStockForm
					.setRangeTo(blacklistStockVO.getRangeTo());
			revokeBlackListedStockForm
					.setRemarks(blacklistStockVO.getRemarks());

		}
		revokeBlackListedStockForm.setRevokeSuccessful(SUCCESS);
		log.exiting("ScreenLoadCommand","execute");
		invocationContext.target = "screenload_success";

	}

}