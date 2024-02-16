/*
 * ScreenLoadSegmentExceptionsCommand.java Created on Jul 20, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P)Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.misc.uldpoolowners;

import java.util.Collection;
import java.util.List;

import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDPoolOwnerVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDPoolSegmentExceptionsVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.misc.ULDPoolOwnersSession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.misc.SegmentExceptionsForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * 
 * @author A-3429
 * 
 */
public class ScreenLoadSegmentExceptionsCommand extends BaseCommand {

	private static final String SCREENLOAD_SUCCESS = "screenload_success";

	private static final String MODULE_NAME = "uld.defaults";

	private static final String SCREEN_ID = "uld.defaults.uldpoolowners";

	private Log log = LogFactory.getLogger("Segment Exceptions");

	/**
	 * execute method
	 * 
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		log.entering("ScreenLoadCommand", "SEGMENT EXCEPTIONS");
		SegmentExceptionsForm form = (SegmentExceptionsForm) invocationContext.screenModel;
		ULDPoolOwnersSession session = getScreenSession(MODULE_NAME, SCREEN_ID);
		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		int selectedRow = Integer.parseInt(form.getSelectedRow());
		log.log(Log.INFO, "selected row", form.getSelectedRow());
		session.setUldPoolSegmentVos(null);
		if (session.getUldPoolOwnerVO() != null) {
			List<ULDPoolOwnerVO> poolOwnerVOs = (List<ULDPoolOwnerVO>) session
					.getUldPoolOwnerVO();
			log.log(Log.INFO, "poolOwnerVo", poolOwnerVOs);
			int length = poolOwnerVOs.size();
			log.log(Log.INFO, "length", length);
			ULDPoolOwnerVO poolOwnerVO = poolOwnerVOs.get(selectedRow);
			log.log(Log.INFO, "poolOwnerVo", poolOwnerVO);
			if (poolOwnerVO.getPoolSegmentsExceptionsVOs() != null
					&& poolOwnerVO.getPoolSegmentsExceptionsVOs().size() != 0) {
				log.log(Log.INFO, "inside exceptionsVOS loop");
				Collection<ULDPoolSegmentExceptionsVO> exceptionsVO = poolOwnerVO
						.getPoolSegmentsExceptionsVOs();
				log.log(Log.INFO, "exceptionsVO12345", exceptionsVO);
				session.setUldPoolSegmentVos(exceptionsVO);

			} else {
				session.setUldPoolSegmentVos(null);
			}

		}
		log.exiting("ScreenLoadSegmentExceptionsCommand", "execute");
		invocationContext.target = SCREENLOAD_SUCCESS;
	}

}
