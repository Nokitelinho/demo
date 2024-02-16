/*
 * ViewReserveAwbsCommand.java Created on Jan 9, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.stockcontrol.defaults.reserveawb;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.stockcontrol.defaults.ReserveAWBSession;
import com.ibsplc.icargo.presentation.web.struts.form.stockcontrol.defaults.ReserveAWBForm;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;

/**
 * @author A-1619
 *
 */
public class ViewReserveAwbsCommand extends BaseCommand {

	/**
	 * execute method 
	 * @param invocationContext
	 * @exception CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		ReserveAWBForm form = (ReserveAWBForm) invocationContext.screenModel;
		ReserveAWBSession session = getScreenSession("stockcontrol.defaults",
				"stockcontrol.defaults.cto.reservestock");
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		if (session.getRejectedDoc() != null
				&& session.getReservedDoc() != null
				&& session.getReorderLevel() != null
				&& ("Y").equals(session.getReorderLevel())) {
			session.removeReorderLevel();
			ErrorVO error = new ErrorVO(
					"stockcontrol.defaults.reservedrejectedreorder");
			Object[] data = new Object[2];
			data[0] = String.valueOf(session.getRejectedDoc());
			data[1] = String.valueOf(session.getReservedDoc());
			error.setErrorData(data);
			error.setErrorDisplayType(ErrorDisplayType.INFO);
			errors.add(error);
			invocationContext.addAllError(errors);
		} else if (session.getRejectedDoc() == null
				&& session.getReorderLevel() != null
				&& ("Y").equals(session.getReorderLevel())) {
			session.removeReorderLevel();
			ErrorVO error = new ErrorVO("stockcontrol.defaults.reorder");
			error.setErrorDisplayType(ErrorDisplayType.INFO);
			errors.add(error);
			invocationContext.addAllError(errors);
		} else if (session.getRejectedDoc() != null
				&& session.getReservedDoc() != null
				&& session.getReorderLevel() == null) {
			session.removeReorderLevel();
			ErrorVO error = new ErrorVO(
					"stockcontrol.defaults.reservedrejected");
			Object[] data = new Object[2];
			data[0] = String.valueOf(session.getRejectedDoc());
			data[1] = String.valueOf(session.getReservedDoc());
			error.setErrorData(data);
			error.setErrorDisplayType(ErrorDisplayType.INFO);
			errors.add(error);
			invocationContext.addAllError(errors);
		}

		form.setAirline(session.getAirline());
		form.setAwbType(session.getDocType());
		session.setReserveAWBVOs(session.getReserveAWBVOs());
		session.setAWBs(session.getReserveAWBVOs().getDocumentNumbers());
		invocationContext.target = "screenload_success";
	}

}
