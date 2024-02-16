/*
 * CloseCommand.java Created on Mar 26, 2018
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.misc.updatemultipleulddetails;

import java.util.Collection;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDDamageRepairDetailsVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.misc.UpdateMultipleULDDetailsSession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.misc.UpdateMultipleULDDetailsForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
/**
 * 
 *	Java file	: 	com.ibsplc.icargo.presentation.web.command.uld.defaults.misc.updatemultipleulddetails.CloseCommand.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-8176	:	26-Mar-2018	:	Draft
 */
public class CloseCommand extends BaseCommand{

	private Log log = LogFactory.getLogger("Update Multiple ULD damage status");

	/**
	 * The Module Name
	 */
	private static final String MODULE = "uld.defaults";
	/**
	 * Screen Id of Update Multiple ULD Damage screen
	 */
	private static final String SCREEN_ID = "uld.defaults.updatemultipleulddetails";
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		
		UpdateMultipleULDDetailsForm updateMultipleULDDetailsForm = (UpdateMultipleULDDetailsForm)invocationContext.screenModel;
    	UpdateMultipleULDDetailsSession updateMultipleULDDetailsSession = getScreenSession(MODULE, SCREEN_ID);
    	Collection<ULDDamageRepairDetailsVO> damagevos =updateMultipleULDDetailsSession.getULDDamageRepairDetailsVOs();
    	boolean canSave = false;
    	int count=0;
    	for(ULDDamageRepairDetailsVO vos:damagevos){
    	if ((vos.getDamageStatus() != null && !vos.getDamageStatus().equals(
						updateMultipleULDDetailsForm.getDamagedStatus()[count]))
				|| (vos.getOverallStatus() != null && !vos.getOverallStatus()
						.equals(updateMultipleULDDetailsForm.getOperationalStatus()[count]))
				) {
			canSave = true;
		}
    	count++;
    	}
    	updateMultipleULDDetailsSession.removeAllAttributes();
    	invocationContext.target = "close_success";
	}

}
