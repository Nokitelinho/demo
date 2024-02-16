/*
 * SelectRestrnOriginCommand.java Created on Oct 29, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.products.defaults.maintainsubproduct;

import java.util.ArrayList;
import java.util.Collection;
import com.ibsplc.icargo.business.products.defaults.vo.RestrictionStationVO;
import com.ibsplc.icargo.business.shared.area.airport.vo.AirportLovVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.business.products.defaults.vo.ProductVO;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.products.defaults.MaintainProductSessionInterface;
import com.ibsplc.icargo.presentation.web.session.interfaces.products.defaults.MaintainSubProductSessionInterface;
import com.ibsplc.icargo.presentation.web.struts.form.products.defaults.MaintainSubProductForm;

/**
 * 
 * @author A-1754
 *
 */
public class SelectRestrnOriginCommand extends BaseCommand {

	private static final String RESTRICT_FLAG = "Restrict";
	/**
	 * Overriding the execute method of BaseCommand
	 * @param invocationContext
	 * @author A-1754
	 * @throws CommandInvocationException
	 */
		public void execute(InvocationContext invocationContext)
		throws CommandInvocationException {
		MaintainSubProductForm maintainSubProductForm= (MaintainSubProductForm)invocationContext.screenModel;
		MaintainProductSessionInterface session = getScreenSession(
				"product.defaults", "products.defaults.maintainproduct");		
		MaintainSubProductSessionInterface maintainSubProductSessionInterface = getScreenSession(
				"product.defaults", "products.defaults.maintainsubproducts");		
		Collection<RestrictionStationVO> listAlreadySelected =maintainSubProductSessionInterface.getStationVOs();
		Collection<AirportLovVO> stations = session.getSelectedStationLovVOs();
		session.removeSelectedStationLovVOs();
		session.removeNextAction();
		if(stations!=null){ 
			Collection<RestrictionStationVO> selectedListFromLov
							= getNewlySelectedStations(stations,true,maintainSubProductForm);
				
			if(listAlreadySelected==null){
				maintainSubProductSessionInterface.setStationVOs(selectedListFromLov);
			}else{
						
			Collection<RestrictionStationVO> newSetOfList =
									new ArrayList<RestrictionStationVO>(listAlreadySelected);
			
			for(RestrictionStationVO newVO : selectedListFromLov){
				boolean isPresent = false;
				for(RestrictionStationVO oldVO : listAlreadySelected){
					if(!ProductVO.OPERATION_FLAG_DELETE.equals(oldVO.getOperationFlag())){
					if(oldVO.getIsOrigin()){
						if(oldVO.getStation().equals(newVO.getStation())){
							isPresent = true;
						}
					}
					}
				}
				if(!isPresent){
					newSetOfList.add(newVO);
				}
			}
			maintainSubProductSessionInterface.setStationVOs(newSetOfList);
			}	
					
		}	
		resetLovAction(maintainSubProductForm);
		invocationContext.target = "screenload_success";
	
		}
	    /**
	     * The function converts the VO seleced from the LOV to Product Specific VO
	     * @param stations
	     * @param isOrigin
	     * @param maintainSubProductForm
	     * @return Collection<RestrictionStationVO>
	     */
	    private Collection<RestrictionStationVO> getNewlySelectedStations(
	    		Collection<AirportLovVO> stations,boolean isOrigin,MaintainSubProductForm maintainSubProductForm){
	    	Collection<RestrictionStationVO> restrictedStations =  new ArrayList<RestrictionStationVO>();
	    	if(stations!=null){
	    	for(AirportLovVO vo : stations){
	    		RestrictionStationVO stationVO = new RestrictionStationVO();
	    		stationVO.setStation(vo.getAirportCode());
	    		stationVO.setIsOrigin(isOrigin);
	    		stationVO.setOperationFlag(ProductVO.OPERATION_FLAG_INSERT);
	    		if(isOrigin){
	    			String originRestriction = maintainSubProductForm.getOriginStatus();
	    			if(originRestriction.equals(RESTRICT_FLAG)){
	    				stationVO.setIsRestricted(true);
	    			}else{
	    				stationVO.setIsRestricted(false);
	    			}
	    		}else{
	    			String destnRestriction = maintainSubProductForm.getDestinationStatus();
	    			if(destnRestriction.equals(RESTRICT_FLAG)){
	    				stationVO.setIsRestricted(true);
	    			}else{
	    				stationVO.setIsRestricted(false);
	    			}
	    			
	    		}
	    		restrictedStations.add(stationVO);
	    	}
	    	}
	    	return restrictedStations;
	    	
	    }
	  
	    /**
	     * The function to reset the form fileds used to display the LOV
	     *@param  maintainSubProductForm
	     *@return void
	     *@author A-1754
	     *@exception none
	     */
	    private void resetLovAction(MaintainSubProductForm maintainSubProductForm){
	    	maintainSubProductForm.setLovAction("");
	    	maintainSubProductForm.setNextAction("");
	    	maintainSubProductForm.setParentSession("");
	    }
		    
	}
