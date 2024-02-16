/*
 * CloseListUldDiscrepancyCommand.java Created on Dec 19, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.discrepancy;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDDiscrepancyVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.uld.defaults.ULDDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.ListUldDiscrepancySession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.ListULDDiscrepanciesForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-2052
 * 
 */
public class CloseListUldDiscrepancyCommand extends BaseCommand {
	
	private static final String SCREENID = "uld.defaults.listulddiscrepancies";
	
	private static final String MODULE = "uld.defaults";
	
	private Log log = LogFactory.getLogger("CloseListUldDiscrepancyCommand");
	
	private static final String CLOSE_SUCCESS = "close_success";
	
	/**
	 * execute method
	 * 
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
	throws CommandInvocationException {
		log.entering("CloseListUldDiscrepancyCommand", "ENTRY");
		ListULDDiscrepanciesForm form = 
			(ListULDDiscrepanciesForm) invocationContext.screenModel;
		ListUldDiscrepancySession session = getScreenSession(MODULE, SCREENID);
		Page<ULDDiscrepancyVO> pageUldDiscrepancyVO = null;
		ULDDiscrepancyVO uldDiscrepancyVO = new ULDDiscrepancyVO();
		Collection<ULDDiscrepancyVO> uldDiscrepancyVOs = 
			new ArrayList<ULDDiscrepancyVO>();
		
		String[] checkedRow = form.getRowId();
		int checkedRowSize = 0;
		if (checkedRow != null && checkedRow.length > 0) {
			checkedRowSize = checkedRow.length;
		}
		if (checkedRowSize != 0) {
			pageUldDiscrepancyVO = session.getULDDiscrepancyVODetails();
			for (int i = 0; i < checkedRowSize; i++) {
				if (pageUldDiscrepancyVO != null) {
					int idx = Integer.parseInt(checkedRow[i]);
					uldDiscrepancyVO = pageUldDiscrepancyVO.get(idx);
					uldDiscrepancyVO
					.setOperationFlag(ULDDiscrepancyVO.OPERATION_FLAG_DELETE);
					uldDiscrepancyVOs.add(uldDiscrepancyVO);
					saveULDDiscrepencyDetails(uldDiscrepancyVOs);
				}
			}
		}
		log.exiting("CloseListUldDiscrepancyCommand", "EXIT");
		invocationContext.target = CLOSE_SUCCESS;
	}
	/**
	 * 
	 * @param uldDiscrepancyVOs
	 */
	private void saveULDDiscrepencyDetails(Collection<ULDDiscrepancyVO> uldDiscrepancyVOs) {
		ULDDefaultsDelegate delegate = new ULDDefaultsDelegate();
		Collection<ErrorVO> errors = null;
		try {
			log.log(Log.INFO, "\n\nCollection to be saved--------------->",
					uldDiscrepancyVOs);
			delegate.saveULDDiscrepencyDetails(uldDiscrepancyVOs);
		} catch (BusinessDelegateException e) {
			errors = handleDelegateException(e);
			e.getMessage();
		}
	}
}
