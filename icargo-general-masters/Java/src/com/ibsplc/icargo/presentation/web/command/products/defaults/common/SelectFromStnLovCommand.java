/*
 * SelectFromStnLovCommand.java Created on Jun 27, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.products.defaults.common;


import java.util.ArrayList;
import java.util.Collection;
import com.ibsplc.icargo.business.shared.area.airport.vo.AirportLovVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.products.defaults.MaintainProductSessionInterface;
import com.ibsplc.icargo.presentation.web.struts.form.products.defaults.StationLovForm;
//import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * 
 * @author A-1754
 *
 */

public class SelectFromStnLovCommand extends BaseCommand {

	
	private Log log = LogFactory.getLogger("SelectFromStnLovCommand");
	/**
	 * Overriding the execute method of BaseCommand
	 * @param invocationContext
	 * @author A-1754
	 * @throws CommandInvocationException
	 */	
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		log.entering("SelectFromTransLovCommand","execute");
		StationLovForm form= (StationLovForm)invocationContext.screenModel;
		MaintainProductSessionInterface session = getScreenSession(
				"product.defaults", "products.defaults.maintainproduct");
		handleGetSelectedData(form,session);		
		invocationContext.target = "screenload_success";
		log.exiting("SelectFromTransLovCommand","execute");
		
	}
	
	private void handleGetSelectedData(StationLovForm form,
			MaintainProductSessionInterface session){
		String newSelected = form.getSaveSelectedValues(); 
		log.log(Log.INFO, "newSelected ------------->>> ", newSelected);
		String selectedStations[] = newSelected.split(",");
		
		//String[] selectedStations = form.getStationChecked();
		Collection<AirportLovVO> selected = new ArrayList<AirportLovVO>();
		
		Collection<AirportLovVO>  listInSession = session.getAllAirportLovVO();
		log
				.log(Log.INFO, "newSelected ------------->>> ", listInSession.size());
		for(int i=0;i<selectedStations.length;i++){
			for(AirportLovVO vo :listInSession ){
				if(vo.getAirportCode().equals(selectedStations[i])){					
					selected.add(vo);
				}			
			}
		}
		session.setSelectedStationLovVOs(selected);
		session.setStationLovVOs(null);
		session.removeALLAirportLovVO();
		session.setAllAirportLovVO(null);
		form.setSelectedData("Y");
		
	}
}
