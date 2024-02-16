/*
 * DeleteCommand.java Created on Nov 30, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.defaults.maintainproratefactors;



import java.util.ArrayList;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.ProrationFactorVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.MaintainMraProrateFactorsSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.MaintainMraProrateFactorsForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * Command class for clearing
 * 
 * Revision History
 * 
 * Version Date Author Description
 * 
 * 0.1 Nov 30, 2006 Rani Rose John Initial draft
 */
public class DeleteCommand extends BaseCommand {
	/**
	 * Logger and the file name
	 */
	private Log log = LogFactory.getLogger("MAILTRACKING MRA DEFAULTS");

	private static final String CLASS_NAME = "DeleteCommand";

	private static final String MODULE_NAME = "mra.defaults";

	private static final String SCREEN_ID = "mailtracking.mra.defaults.maintainproratefactors";

	private static final String DELETE_SUCCESS = "delete_success";
	
	/**
	 * Execute method
	 * 
	 * @param invocationContext
	 *            InvocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		log.entering(CLASS_NAME, "execute");
		MaintainMraProrateFactorsForm maintainProrateFactorsForm = 
			(MaintainMraProrateFactorsForm) invocationContext.screenModel;
		MaintainMraProrateFactorsSession maintainProrateFactorsSession = 
			(MaintainMraProrateFactorsSession) getScreenSession(
				MODULE_NAME, SCREEN_ID);
		String[] row  = maintainProrateFactorsForm.getSelectedElements();
		String[] operationFlag = maintainProrateFactorsForm.getOperationFlag();
		ArrayList<ProrationFactorVO> prorateFactorVos =
			maintainProrateFactorsSession.getFactors();
		ProrationFactorVO prorateFactorVO = null;
		int index = 0;
		for(String selectedrow:row) {
			index = Integer.parseInt(selectedrow);
			log.log(Log.INFO, "selected row>>>>", index);
			prorateFactorVO = prorateFactorVos.get(index);
			if(operationFlag[index].equals(ProrationFactorVO.OPERATION_FLAG_INSERT)){
				prorateFactorVos.remove(prorateFactorVO);
				log.log(Log.INFO,"Deleting Inserted VO");
			}else {
				prorateFactorVO.setOperationFlag(ProrationFactorVO.OPERATION_FLAG_DELETE);
				log.log(Log.INFO,"Updating opretion Flag of existing vo");
			}
			
		}
		
		
		invocationContext.target = DELETE_SUCCESS;
		log.exiting(CLASS_NAME, "execute");
	}

	
}
