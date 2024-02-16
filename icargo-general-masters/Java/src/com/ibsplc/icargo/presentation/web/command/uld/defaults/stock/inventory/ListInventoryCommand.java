/*
 * ListInventoryCommand.java Created on May 27, 2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.presentation.web.command.uld.defaults.stock.inventory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.ibsplc.icargo.business.flight.schedule.vo.FlightScheduleFilterVO;
import com.ibsplc.icargo.business.uld.defaults.stock.vo.InventoryULDVO;
import com.ibsplc.icargo.business.uld.defaults.stock.vo.ULDInventoryDetailsVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.area.AreaDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.uld.ULDDelegate;
import com.ibsplc.icargo.presentation.delegate.uld.defaults.ULDDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.stock.ULDInventorySession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.stock.InventoryULDForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import com.ibsplc.xibase.util.time.DateUtilities;
import static com.ibsplc.xibase.server.framework.vo.AbstractVO.OPERATION_FLAG_INSERT;
/**
 * 
 * @author a-2883
 *
 */
public class ListInventoryCommand extends BaseCommand {

	private Log log = LogFactory.getLogger("ULDMANAGEMENT");
	private static final String SCREEN_ID = 
			"uld.defaults.stock.inventoryuld";
	private static final String MODULE_NAME = "uld.defaults";
	
	private static final String FLAG_FLIGHT = "FLIGHT";
	
	private static final String FLAG_D = "D";
	
	public void execute(InvocationContext invocationContext) throws CommandInvocationException {
		log.entering("ListInventoryCommand", "execute");
		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes  =  applicationSession.getLogonVO();
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		ULDInventorySession session =
			getScreenSession(MODULE_NAME, SCREEN_ID);
		InventoryULDForm form = 
			(InventoryULDForm)invocationContext.screenModel;
		InventoryULDVO vo = new InventoryULDVO();
		ULDDefaultsDelegate delegate = new ULDDefaultsDelegate();
		
		//setting session as null  
		if(form.getFlightPlanFlag() != null && 
		    		FLAG_D.equals(form.getFlightPlanFlag())){
		    	session.setListInventoryULDDetails(null);
		    	session.setDisplayInventoryDetails(null);
		    	session.setInventoryPageFlag(null);
		    	form.setFlightPlanFlag("");
		    	form.setDisplayPage("1");
		 }
		if(session.getStatusFlag() != null &&
				("fromUpdate").equals(session.getStatusFlag())){
			log.log(Log.FINE, " \n #$$$$$$$$$$$$$$$$$$$$insideup");
			session.setStatusFlag(null);
			if(session.getInventoryPageFlag()!=null){
				log.log(Log.FINE, " \n #$$$$$$$$$$$$$$$$$$$$insideupdatexx",
						session.getInventoryPageFlag());
				form.setDisplayPage(session.getInventoryPageFlag());
				List<InventoryULDVO> flightResult =session.getListInventoryULDDetails();
	    		List<InventoryULDVO> display = new ArrayList<InventoryULDVO>();
				InventoryULDVO newvo = new InventoryULDVO();
				newvo=flightResult.get(Integer.parseInt(session.getInventoryPageFlag())-1);
				display.add(newvo);
				form.setLastPageNum(String.valueOf(flightResult.size()));
				session.setDisplayInventoryDetails((ArrayList<InventoryULDVO>)display);
			}
			invocationContext.target = "success";
			return;
		}
		
	    //For Listing Flight Details
	    if(form.getFlightPlanFlag() != null && 
	    		FLAG_FLIGHT.equals(form.getFlightPlanFlag())){
	    	session.setStatusFlag(null);
			form.setFlightPlanFlag("");
	    	if(session.getListInventoryULDDetails() ==  null){
	    		log.log(Log.FINE, " \n ifffffff!!!!");	
	    		List<InventoryULDVO> flightResult = new ArrayList<InventoryULDVO>();
	    		List<InventoryULDVO> newflightResult = new ArrayList<InventoryULDVO>();
				FlightScheduleFilterVO filterVO = new FlightScheduleFilterVO();
				 if(form.getFromDate() != null && 
							form.getFromDate().trim().length() >0){
						LocalDate fromDate = new LocalDate(logonAttributes.getStationCode(),Location.STN,false);
						filterVO.setFromDate(fromDate.setDate(form.getFromDate()));
					}
					if(form.getToDate() != null &&
							form.getToDate().trim().length()>0){
						LocalDate toDate = new LocalDate(logonAttributes.getStationCode(),Location.STN,false);
						filterVO.setToDate(toDate.setDate(form.getToDate()));
					}
					if(form.getAirportCode()!=null && form.getAirportCode().trim().length() > 0){
						
						//errors = validateAirportCode(form.getAirportCode(),logonAttributes.getCompanyCode());
						filterVO.setOrigin(form.getAirportCode());
					}
					if(form.getUldType() != null && form.getUldType().trim().length() > 0){
						//errors = validateULDCode(form.getUldType(), logonAttributes.getCompanyCode());
						filterVO.setUldUnit(form.getUldType());
					}
					errors = validateForm(form,logonAttributes);
					
					if(errors.size() > 0){
						invocationContext.addAllError(errors);
						invocationContext.target = "failure";
						return;
					}
					filterVO.setCompanyCode(logonAttributes.getCompanyCode());
					//filterVO.setUldUnit(vo.getUldType());
				try {
					flightResult = delegate.findFlightLegULDDetails(filterVO);
				} catch (BusinessDelegateException e) {
					errors = handleDelegateException(e);		
				}
				if(flightResult != null && flightResult.size() > 0){
					
					for(InventoryULDVO vos : flightResult){
						vos.setOpFlag(OPERATION_FLAG_INSERT);
						if(vos.getUldInventoryDetailsVOs().size() > 0){
							for(ULDInventoryDetailsVO cvos : vos.getUldInventoryDetailsVOs()){
								cvos.setOpFlag(OPERATION_FLAG_INSERT);
							}
						}
						newflightResult.add(vos);
					}
					
					List<InventoryULDVO> display = new ArrayList<InventoryULDVO>();
					InventoryULDVO newvo = new InventoryULDVO();
					newvo = newflightResult.get(0);
					display.add(newvo);
					form.setLastPageNum(String.valueOf(newflightResult.size()));
					session.setDisplayInventoryDetails((ArrayList<InventoryULDVO>)display);
					session.setListInventoryULDDetails((ArrayList<InventoryULDVO>)newflightResult);
				}else{
					errors.add(new ErrorVO("uld.defaults.noinventorydetailsrecordsfound"));
					invocationContext.addAllError(errors);
					invocationContext.target = "failure";
					return;
				}
			}
	    	invocationContext.target = "success";
			return;
		}else{
			//For List Inventory Details
			vo.setCompanyCode(logonAttributes.getCompanyCode());
		    if(form.getFromDate() != null && 
					form.getFromDate().trim().length() >0){
				LocalDate fromDate = new LocalDate(logonAttributes.getStationCode(),Location.STN,false);
				vo.setFromDate(fromDate.setDate(form.getFromDate()));
			}
			if(form.getToDate() != null &&
					form.getToDate().trim().length()>0){
				LocalDate toDate = new LocalDate(logonAttributes.getStationCode(),Location.STN,false);
				vo.setToDate(toDate.setDate(form.getToDate()));
			}
			if(form.getAirportCode()!=null && form.getAirportCode().trim().length() > 0){
				log.log(Log.FINE, " \n #$@#$@#$@#vvvv");
				//errors = validateAirportCode(form.getAirportCode(),logonAttributes.getCompanyCode());
				vo.setAirportCode(form.getAirportCode());
			}
			if(form.getUldType() != null && form.getUldType().trim().length() > 0){
				//errors = validateULDCode(form.getUldType(), logonAttributes.getCompanyCode());
				vo.setUldType(form.getUldType());
			}
			form.setFlightPlanFlag("");
			vo.setDisplayPage(Integer.parseInt(form.getDisplayPage()));
			
			errors = validateForm(form,logonAttributes);
			if(errors.size() > 0){
				invocationContext.addAllError(errors);
				invocationContext.target = "failure";
				return;
			}
			
			List<InventoryULDVO> inventoryVOs =null;
			try {
				inventoryVOs = (List<InventoryULDVO>)delegate.listInventoryDetails(vo);
				
			} catch (BusinessDelegateException ex) {
				errors = handleDelegateException(ex);		
			}
			if(inventoryVOs == null || inventoryVOs .size() == 0 ){
				session.setStatusFlag(null);
				if(errors.size() > 0){
					invocationContext.addAllError(errors);
					invocationContext.target = "failure";
				}else{
					form.setFlightPlanFlag("Y");
				}
			}else{
				session.setListInventoryULDDetails((ArrayList<InventoryULDVO>)inventoryVOs);
				List<InventoryULDVO> disvo =new ArrayList<InventoryULDVO>();
				InventoryULDVO vovalue = new InventoryULDVO();
				vovalue = inventoryVOs.get(0);
				disvo.add(vovalue);
				session.setDisplayInventoryDetails((ArrayList<InventoryULDVO>)disvo);
			}
			if(errors.size() > 0){
				invocationContext.addAllError(errors);
				invocationContext.target = "failure";
			}else{
				invocationContext.target = "success";
			}
		}
	}
	
	
	
	private Collection<ErrorVO> validateForm(InventoryULDForm form ,
			LogonAttributes logonAttributes) {

		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		
		//validating From Date Less than To Date
		if(form.getFromDate() != null && form.getFromDate().length() > 0 &&
				form.getToDate() != null && form.getToDate().length() >0){
			if (DateUtilities.isGreaterThan(form.getFromDate(), form
					.getToDate(), "dd-MMM-yyyy")) {
				ErrorVO errorVO = new ErrorVO(
				"uld.defaults.stock.inventory.fromdategreaterthantodate");
				errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
				errors.add(errorVO);
			}
		}
		
		if(form.getUldType() != null && form.getUldType().trim().length() > 0){
			//ULDDefaultsDelegate delegate = new ULDDefaultsDelegate();
			ULDDelegate delegate = new ULDDelegate();
			//ULDTypeValidationVO uldValidationVO = null;
			Collection<String> coll = new ArrayList<String>();
			coll.add(form.getUldType());
			try {
				 delegate.validateULDTypeCodes(logonAttributes.getCompanyCode().toUpperCase(),
						coll);
			} catch (BusinessDelegateException e) {
				ErrorVO errorVO = new ErrorVO(
				"uld.defaults.stock.inventory.invaliduld");
				errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
				errors.add(errorVO);
			}
		}
	
		if(form.getAirportCode()!=null && form.getAirportCode().trim().length() > 0){
			AreaDelegate delegate = new AreaDelegate();
			try {
				delegate.validateAirportCode(logonAttributes.getCompanyCode().toUpperCase(),
						form.getAirportCode().toUpperCase());
			} catch (BusinessDelegateException e) {
				ErrorVO errorVO = new ErrorVO(
					"uld.defaults.stock.inventory.invalidairport");
					errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
					errors.add(errorVO);
			}
		}
		
	return errors;
	}
	
	
}
