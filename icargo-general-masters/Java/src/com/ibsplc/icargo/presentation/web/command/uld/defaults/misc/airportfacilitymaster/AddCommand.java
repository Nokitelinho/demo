/*
 * AddCommand.java Created on Feb 06,2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services(P)Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.misc.airportfacilitymaster;

import java.util.ArrayList;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDAirportLocationVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.misc.AirportFacilityMasterSession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.misc.AirportFacilityMasterForm;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;


/**
 * This command class is used to list the damage reports. 
 * @author A-2052
 */
public class AddCommand extends BaseCommand {
	
	/**
	 * Logger for Maintain Damage Report
	 */
	private Log log = LogFactory.getLogger("AddCommand");
	private static final String SCREENID = "uld.defaults.airportfacilitymaster";
    private static final String MODULE = "uld.defaults";
	private static final String ADD_SUCCESS = "addRow_success";
	

	/**
	 * execute method 
	 * @param invocationContext 
	 * @throws CommandInvocationException
	 */
    public void execute(InvocationContext invocationContext)throws CommandInvocationException {
    	log.entering("AddCommand","execute");
    	/**
		 * Obtain the logonAttributes
		 */
		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		String  compCode = logonAttributes.getCompanyCode();
    	AirportFacilityMasterForm form = 
			(AirportFacilityMasterForm) invocationContext.screenModel;
		AirportFacilityMasterSession session = 
			getScreenSession(MODULE, SCREENID);	  
		ArrayList<ULDAirportLocationVO> uldAirportLocationVOColl = 
			new ArrayList<ULDAirportLocationVO>();
        ArrayList<ULDAirportLocationVO> uldAirportLocationVOs = 
				session.getULDAirportLocationVOs()!= null ?
				new ArrayList<ULDAirportLocationVO>(session.getULDAirportLocationVOs()) : 
				new ArrayList<ULDAirportLocationVO>();
     	if(uldAirportLocationVOs != null && uldAirportLocationVOs.size() > 0){
    		String[] facilityCode = form.getFacilityCode();
    		String[] description = form.getDescription();
    		String defaultFlag[] = form.getChkBoxFlag().split("-");
    		if(facilityCode.length - 1>=0){	    	
	    		int index = 0;	
	    		for(ULDAirportLocationVO uldAirportLocationVO : uldAirportLocationVOs){		 			
	    			if((uldAirportLocationVO.getOperationFlag()!=null &&
	    		    		!uldAirportLocationVO.getOperationFlag().equals(AbstractVO.OPERATION_FLAG_DELETE))    		    					
	    		    			|| uldAirportLocationVO.getOperationFlag()==null){     			
    					if(!uldAirportLocationVO.getFacilityCode().equals(facilityCode[index]) ||
		    					!uldAirportLocationVO.getDescription().equals(description[index]) ||
		    					!(		((("Y").equals(uldAirportLocationVO.getDefaultFlag())) &&
		    							(("Y").equals(defaultFlag[index])))||
		    							((("N").equals(uldAirportLocationVO.getDefaultFlag())) &&
		    	    							(("N").equals(defaultFlag[index]))))){

	    										
			    				if(uldAirportLocationVO.getOperationFlag()==null)
			    				{
			    					uldAirportLocationVO.setOperationFlag(AbstractVO.OPERATION_FLAG_UPDATE);
			    				}
	    					
		    			}
		    			if(facilityCode[index] != null && facilityCode[index].trim().length() != 0){
		    				uldAirportLocationVO.setFacilityCode(facilityCode[index].toUpperCase());
		    			}
		    			if(description[index] != null && description[index].trim().length() != 0){
		    				uldAirportLocationVO.setDescription(description[index]);
		    			}
	    				if(("Y").equals(defaultFlag[index])){
    						uldAirportLocationVO.setDefaultFlag("Y");
    					}else{	
    						uldAirportLocationVO.setDefaultFlag("N");
    					}			    						
	    			}
	    			uldAirportLocationVOColl.add(uldAirportLocationVO);
	    			index++;	    			
	    		}
	      	}
     	}
     	ULDAirportLocationVO newULDAirportLocationVO = new ULDAirportLocationVO();
     	newULDAirportLocationVO.setOperationFlag(AbstractVO.OPERATION_FLAG_INSERT);
     	newULDAirportLocationVO.setCompanyCode(compCode);
     	newULDAirportLocationVO.setFacilityCode("");
     	newULDAirportLocationVO.setDescription("");
     	newULDAirportLocationVO.setDefaultFlag("N");    	
     	uldAirportLocationVOColl.add(newULDAirportLocationVO);    
     	session.setULDAirportLocationVOs(uldAirportLocationVOColl);		
		invocationContext.target =  ADD_SUCCESS;     	
    }
}
