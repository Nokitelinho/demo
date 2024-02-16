/*
 * DeleteRepCommand.java Created on Feb 08,2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services(P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.
											misc.maintaindamagereport;


import java.util.ArrayList;

import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDRepairVO;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.misc
												.MaintainDamageReportSession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.misc
													.MaintainDamageReportForm;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;


/**
 * @author A-1862
 *
 */
public class DeleteRepCommand extends SelectNextRepCommand {

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
	private static final String DELETE_SUCCESS = "deleterep_success";
		
	/**
	 * The execute method for DeleteRepCommand
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
		ArrayList<ULDRepairVO> uldRepairVOs =
			(ArrayList<ULDRepairVO>)
								maintainDamageReportSession.getULDRepairVOs();
		ArrayList<String> refnos =
			(ArrayList<String>)
								maintainDamageReportSession.getRefNo();
		log.log(Log.FINE, "\n\nuldDamageVOs before delete ---> ", uldRepairVOs);
		log.log(Log.FINE, "\n\nrefnos before delete ---> ", refnos);
		if(uldRepairVOs != null && uldRepairVOs.size() > 0){
		    	int discIndex = Integer.parseInt(
		    			actionForm.getRepdisplayPage())-1;
		
	    uldRepairVOs.remove(discIndex);
	    refnos.remove(discIndex);
    	int size = uldRepairVOs.size();
    	ULDRepairVO newuldRepairVO = 
	    		new ULDRepairVO();
    	String newString = null;
	    	if (size <= 0) {
	    		actionForm.setFlag("NORECORDS");
	    		invocationContext.addError(
	  		new ErrorVO("uld.defaults.maintainDmgRep.msg.err.nomorerecords"));
	    	}
	    	int displayPage = Integer.parseInt(
	    			actionForm.getRepdisplayPage());
	    	
	    	int totalRecords = 
	    		uldRepairVOs != null && uldRepairVOs.size() > 0 ?
	    				uldRepairVOs.size() : 0;
	    	if (displayPage != 1){
	    		displayPage = Integer.parseInt(
	    				actionForm.getRepdisplayPage())-1;
	    	}
	    	actionForm.setReptotalRecords(String.valueOf(totalRecords));
	    	actionForm.setReplastPageNum(String.valueOf(totalRecords));
	    	actionForm.setRepdisplayPage(
	    			String.valueOf(displayPage));    	
	    	actionForm.setRepcurrentPageNum(
	    			actionForm.getRepdisplayPage());
	    	
	    	int selectedIndex = Integer.parseInt(
	    			actionForm.getRepcurrentPageNum())-1;    	
	    	log.log(Log.FINE, "\n\nselectedIndex ---> ", selectedIndex);
			if(uldRepairVOs != null && 
	    			uldRepairVOs.size() > 0) {
	    		
	    		newString = 
	    			refnos.get(selectedIndex);
	    		newuldRepairVO = 
	    			uldRepairVOs.get(selectedIndex);
	    		
		    } 
	    	log.log(Log.FINE, "\n\nuldDamageVOs after delete ---> ",
					uldRepairVOs);
			log.log(Log.FINE, "\n\nrefnos after delete ---> ", refnos);
			maintainDamageReportSession.setULDRepairVOs(uldRepairVOs);
	    	maintainDamageReportSession.setRefNo(refnos);
			populateRep(newuldRepairVO,newString,actionForm);	
			} else {
				actionForm.setFlag("NORECORDS");
	    		invocationContext.addError(
			new ErrorVO("uld.defaults.maintainDmgRep.msg.err.nomorerecords"));
	    	}
    	
    	invocationContext.target = 	DELETE_SUCCESS;
	}			
}
