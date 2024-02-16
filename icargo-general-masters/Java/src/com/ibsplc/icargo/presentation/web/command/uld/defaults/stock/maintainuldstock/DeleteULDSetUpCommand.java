/*
 * DeleteULDSetUpCommand.java Created on Dec 22, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services(P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.stock.maintainuldstock;

import java.util.ArrayList;

import com.ibsplc.icargo.business.uld.defaults.stock.vo.ULDStockConfigVO;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.stock.maintainuldstock.ListULDStockSetUpSession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.stock.MaintainULDStockForm;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;


/**
 * @author A-1862
 *
 */
public class DeleteULDSetUpCommand extends SelectNextULDSetUpCommand {

private Log log = LogFactory.getLogger("DeleteULDSetUpCommand");
	
private static final String MODULE = "uld.defaults";

private static final String SCREENID ="uld.defaults.maintainuldstock";
	/*
	 * Target mappings for succes and failure
	 */
	private static final String DELETE_SUCCESS = "deletedmg_success";
		
	/**
	 * The execute method for AddDiscCommand
	 * (non-Javadoc)
	 * @see com.ibsplc.icargo.framework.web.command.Command#execute
	 * (com.ibsplc.icargo.framework.web.command.InvocationContext)
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		
		MaintainULDStockForm actionForm = 
			(MaintainULDStockForm) invocationContext.screenModel;
    	ListULDStockSetUpSession listULDStockSession =
    		getScreenSession(MODULE, SCREENID);					
		
		
		ArrayList<ULDStockConfigVO> uldStockConfigVOs =
			(ArrayList<ULDStockConfigVO>)
								listULDStockSession.getULDStockConfigVOs();
		
		log.log(Log.FINE, "\n\nuldStockConfigVOs before delete ---> ",
				uldStockConfigVOs);
		if(uldStockConfigVOs != null && uldStockConfigVOs.size() > 0){
		    	int discIndex = Integer.parseInt(
		    			actionForm.getDmgdisplayPage())-1;		
		    	log.log(Log.FINE, "\n\nactionForm.getStation() ---> ",
						actionForm.getStationMain());
				log.log(Log.FINE, "\n\nactionForm.getAirline() ---> ",
						actionForm.getAirlineMain());
		uldStockConfigVOs.remove(discIndex);
    	int size = uldStockConfigVOs.size();
    	ULDStockConfigVO newULDStockConfigVO = 
	    		new ULDStockConfigVO();
	    	if (size <= 0) {
	    		
	    				
	    		actionForm.setFlag("NORECORDS");
	    		invocationContext.addError(
	    			new ErrorVO("uld.defaults.stock.msg.err.nomorerecords"));
	    	}
	    	int displayPage = Integer.parseInt(
	    			actionForm.getDmgdisplayPage());
	    	
	    	int totalRecords = 
	    		uldStockConfigVOs != null && uldStockConfigVOs.size() > 0 ?
	    				uldStockConfigVOs.size() : 0;
	    	if (displayPage != 1){
	    		displayPage = Integer.parseInt(
	    				actionForm.getDmgdisplayPage())-1;
	    	}
	    	actionForm.setDmgtotalRecords(String.valueOf(totalRecords));
	    	actionForm.setDmglastPageNum(String.valueOf(totalRecords));
	    	actionForm.setDmgdisplayPage(String.valueOf(displayPage));    	
	    	actionForm.setDmgcurrentPageNum(actionForm.getDmgdisplayPage());
	    	
	    	int selectedIndex = Integer.parseInt(
	    			actionForm.getDmgcurrentPageNum())-1;    	
	    	log.log(Log.FINE, "\n\nselectedIndex ---> ", selectedIndex);
			if(uldStockConfigVOs != null && 
	    			uldStockConfigVOs.size() > 0) {
	    		
	    		newULDStockConfigVO = 
	    			uldStockConfigVOs.get(selectedIndex);
	    		
		    } 
	    	log.log(Log.FINE, "\n\nuldStockConfigVOs after delete ---> ",
					uldStockConfigVOs);
			listULDStockSession.setULDStockConfigVOs(uldStockConfigVOs);
    		
	    	populateDmg(newULDStockConfigVO, actionForm);		
			} else {
				
				String air = "";
	        	if(actionForm.getAirlineMain()!=null &&
	        			actionForm.getAirlineMain().trim().length()>0){
	    		air = actionForm.getAirlineMain().toUpperCase();
	        	}
	    		String station = actionForm.getStationMain().toUpperCase();
	    		actionForm.setStationCode(station);
	    		if(actionForm.getAirlineMain() != null
	    				&& actionForm.getAirlineMain().trim().length() > 0) {
	    			actionForm.setFilterStatus("both");
	    		}
	    		actionForm.setAirlineCode(air);
	    		actionForm.setStationCode(station);
	    		
				actionForm.setFlag("NORECORDS");
	    		invocationContext.addError(
	    				new ErrorVO("uld.defaults.stock.msg.err.nomorerecords"));
	    	}
    	
    	invocationContext.target = 	DELETE_SUCCESS;
	}			
}
