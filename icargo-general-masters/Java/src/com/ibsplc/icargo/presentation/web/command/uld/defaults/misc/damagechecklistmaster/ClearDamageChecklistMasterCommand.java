/*
 * ClearDamageChecklistMasterCommand.java Created on May 5, 2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.misc.damagechecklistmaster;

import java.util.ArrayList;
import java.util.Collection;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.misc.DamageChecklistMasterSession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.misc.DamageChecklistMasterForm;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-3459
 *
 */
public class ClearDamageChecklistMasterCommand extends BaseCommand{
	
	private static final String SCREEN_ID = "uld.defaults.damagechecklistmaster";
	private static final String MODULE_NAME = "uld.defaults";
	private static final String SCREENLOAD_SUCCESS = "screenload_success";
	private static final String CLEAR_SUCCESS= "clear_success";
	private Log log = LogFactory.getLogger("clearcommand");
	private static final String BLANK=null;
	private static final String SAVE_SUCCESS="save_success";
	 /**
	   * execute method
	   * @param invocationContext
	   * @throws CommandInvocationException
	   */
	
	public void execute(InvocationContext invocationContext) throws CommandInvocationException {
		
		
		log.entering("clearcommand","Entering");
		DamageChecklistMasterForm damageChecklistMasterForm = 
			(DamageChecklistMasterForm) invocationContext.screenModel;
        DamageChecklistMasterSession damageChecklistMasterSession  = 
			getScreenSession(MODULE_NAME, SCREEN_ID);
        Collection<OneTimeVO> section = new ArrayList<OneTimeVO>();
		OneTimeVO oneTimeVO = new OneTimeVO();
		oneTimeVO.setFieldValue(BLANK);
		oneTimeVO.setFieldDescription(BLANK);
		section.add(oneTimeVO);	
		damageChecklistMasterForm.setSection(BLANK);
		if(SAVE_SUCCESS.equals(damageChecklistMasterForm.getStatusFlag())) {
			ErrorVO error = new ErrorVO("uld.defaults.damagechecklistmaster.savedsuccessfully");
			error.setErrorDisplayType(ErrorDisplayType.STATUS);
	     	invocationContext.addError(error);   		
		}
		
		damageChecklistMasterSession.setULDDamageChecklistVO(null);
		damageChecklistMasterForm.setStatusFlag("screenload_success");
		damageChecklistMasterForm.setDisableButtons("Y");
		damageChecklistMasterForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
		invocationContext.target = CLEAR_SUCCESS;
		log.exiting("clearcommand","EXITING");

	}
}