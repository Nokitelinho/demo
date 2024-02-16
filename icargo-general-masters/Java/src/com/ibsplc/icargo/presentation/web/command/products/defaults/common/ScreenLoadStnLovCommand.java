/*
 * ScreenLoadStnLovCommand.java Created on Jun 27, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.products.defaults.common;


import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.shared.area.airport.vo.AirportLovFilterVO;
import com.ibsplc.icargo.business.shared.area.airport.vo.AirportLovVO;

import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.area.AreaDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.products.defaults.MaintainProductSessionInterface;
import com.ibsplc.icargo.presentation.web.struts.form.products.defaults.StationLovForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * 
 * @author A-1754
 *
 */

public class ScreenLoadStnLovCommand extends BaseCommand {


	//private static final String COMPANY_CODE="AV";
	private Log log = LogFactory.getLogger("ScreenLoadStnLovCommand");
	/**
	 * Overriding the execute method of BaseCommand
	 * @param invocationContext
	 * @author A-1754
	 * @throws CommandInvocationException
	 */	
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		log.entering("ScreenLoadStnLovCommand","execute");
		StationLovForm stationLovForm = (StationLovForm)invocationContext.screenModel;
		MaintainProductSessionInterface session = getScreenSession(
				"product.defaults", "products.defaults.maintainproduct");		

		AirportLovFilterVO filterVO = new AirportLovFilterVO();
		filterVO.setAirportCode(upper(stationLovForm.getCode()));
		filterVO.setAirportName(stationLovForm.getDescription());
		filterVO.setCompanyCode(getApplicationSession().getLogonVO().getCompanyCode());
		Collection<AirportLovVO> allValues = null;
		
		if(session.getAllAirportLovVO()== null) {
			allValues = new ArrayList<AirportLovVO>();
		} else {
			allValues = session.getAllAirportLovVO(); 
		}
		log.log(Log.INFO, "allValues length-------b4-ssssss----- ", allValues.size());
		Collection<AirportLovVO> currentValues = findAirportLov(
						filterVO,Integer.parseInt(
								stationLovForm.getDisplayPage()));
		for(AirportLovVO airportLovVO : currentValues){
			
			boolean isPresent = false;
			for(AirportLovVO vo : allValues){
				if(vo.getAirportCode().equalsIgnoreCase(airportLovVO.getAirportCode())){
					isPresent = true;
					break;
				}
			}
			if(!isPresent){
				allValues.add(airportLovVO);
			}
			/*if(!allValues.contains(airportLovVO)) {
				allValues.add(airportLovVO);
			}*/
		}
		log.log(Log.INFO, "currentValues length ------------- ", currentValues.size());
		log.log(Log.INFO, "allValues length-------after------ ", allValues.size());
		session.setAllAirportLovVO(allValues);
			
		session.setStationLovVOs(findAirportLov(filterVO,Integer.parseInt(stationLovForm.getDisplayPage())));						
		//if(session.getNextAction()==null){
		 	session.setNextAction(stationLovForm.getNextAction());
		// }			
		//session.setStationLovVOs(page);
		
		invocationContext.target = "screenload_success";
		log.exiting("ScreenLoadStnLovCommand","execute");
		
	}
	
	/**
	 * The fucntion to get the Airport Lov Vos
	 * @param airportFilterVO
	 * @param pageNumber
	 * @return page
	 */
    private Page<AirportLovVO> findAirportLov(AirportLovFilterVO airportFilterVO, int pageNumber) {
    	Page<AirportLovVO> page = null;
        try{
        	page = new AreaDelegate().findAirportLov(airportFilterVO,pageNumber); 
        }catch(BusinessDelegateException businessDelegateException){
        	log.log(Log.FINE,"<-----------------BusinessDelegateException------------->");
        }
        return page;
    }
    
	/**
	 * To Convert the input to Caps
	 * @param input
	 * @return
	 */
    private String upper(String input){

		if(input!=null){
			return input.trim().toUpperCase();
		}else{
			return "";
		}
	}

    
}
