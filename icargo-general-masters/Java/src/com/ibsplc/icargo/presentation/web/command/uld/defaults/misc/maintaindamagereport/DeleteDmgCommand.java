/*
 * DeleteDmgCommand.java Created on Feb 09,2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services(P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.
											misc.maintaindamagereport;


import java.util.ArrayList;

import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDDamageVO;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults
										.misc.MaintainDamageReportSession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.misc
											.MaintainDamageReportForm;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;


/**
 * @author A-1862
 *
 */
public class DeleteDmgCommand extends SelectNextDmgCommand {

	private Log log = LogFactory.getLogger("Maintain Damage Report");
	
	private static final String MODULE = "uld.defaults";

	/**
	 * Screen Id of maintain damage report screen
	 */
	private static final String SCREENID =
		"uld.defaults.maintaindamagereport";
	/**
	 * Target mappings for succes and failure
	 */
	private static final String DELETE_SUCCESS = "deletedmg_success";
		
	/**
	 * The execute method for DeleteDmgCommand
	 * (non-Javadoc)
	 * @see com.ibsplc.icargo.framework.web.command.Command#execute
	 * (com.ibsplc.icargo.framework.web.command.InvocationContext)
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		

		MaintainDamageReportForm actionForm = (MaintainDamageReportForm)
												invocationContext.screenModel;
		MaintainDamageReportSession maintainDamageReportSession =
			(MaintainDamageReportSession)getScreenSession(MODULE,
					SCREENID);
		ArrayList<ULDDamageVO> uldDamageVOs =
			(ArrayList<ULDDamageVO>)
								maintainDamageReportSession.getULDDamageVOs();
		log.log(Log.FINE, "\n\nuldDamageVOs before delete ---> ", uldDamageVOs);
		if(uldDamageVOs != null && uldDamageVOs.size() > 0){
		    	int discIndex = Integer.parseInt(
		    			actionForm.getDmgdisplayPage())-1;
		
	    uldDamageVOs.remove(discIndex);
    	int size = uldDamageVOs.size();
    	ULDDamageVO newuldDamageVO = 
	    		new ULDDamageVO();
	    	if (size <= 0) {
	    		actionForm.setFlag("NORECORDS");
	    		invocationContext.addError(
			new ErrorVO("uld.defaults.maintainDmgRep.msg.err.nomorerecords"));
	    	}
	    	int displayPage = Integer.parseInt(
	    			actionForm.getDmgdisplayPage());
	    	
	    	int totalRecords = 
	    		uldDamageVOs != null && uldDamageVOs.size() > 0 ?
	    				uldDamageVOs.size() : 0;
	    	if (displayPage != 1){
	    		displayPage = Integer.parseInt(
	    				actionForm.getDmgdisplayPage())-1;
	    	}
	    	actionForm.setDmgtotalRecords(String.valueOf(totalRecords));
	    	actionForm.setDmglastPageNum(String.valueOf(totalRecords));
	    	actionForm.setDmgdisplayPage(
	    			String.valueOf(displayPage));    	
	    	actionForm.setDmgcurrentPageNum(
	    			actionForm.getDmgdisplayPage());
	    	
	    	int selectedIndex = Integer.parseInt(
	    			actionForm.getDmgcurrentPageNum())-1;    	
	    	log.log(Log.FINE, "\n\nselectedIndex ---> ", selectedIndex);
			if(uldDamageVOs != null && 
	    			uldDamageVOs.size() > 0) {
	    		
	    		newuldDamageVO = 
	    			uldDamageVOs.get(selectedIndex);
	    		
		    } 
	    	log.log(Log.FINE, "\n\nuldDamageVOs after delete ---> ",
					uldDamageVOs);
			maintainDamageReportSession.setULDDamageVOs(uldDamageVOs);
    		
	    	populateDmg(newuldDamageVO, actionForm);		
			} else {
				actionForm.setFlag("NORECORDS");
	    		invocationContext.addError(
			new ErrorVO("uld.defaults.maintainDmgRep.msg.err.nomorerecords"));
	    	}
    	
    	invocationContext.target = 	DELETE_SUCCESS;
	}			
}
