/*
 * ClearListULDCommand.java Created on Aug 1, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.listuld;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.uld.defaults.vo.ULDListFilterVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.ListULDSession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.ListULDForm;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * This command class is invoked for clearing the screen
 *
 * @author A-1347
 */
public class ClearListULDCommand extends BaseCommand {
	
	private static final String MODULE = "uld.defaults";

	private static final String SCREENID =
		"uld.defaults.listuld";
	private static final String CLEAR_SUCCESS = "clear_success";
    private Log log = LogFactory.getLogger("Maintain Uld Discripency");
    private static final String BLANKSPACE = "false";
    /**
	 * @param invocationContext
	 * @throws CommandInvocationException
	 * @return 
	 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	/**
		 * Obtain the logonAttributes
		 */
		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		log.entering("ClearListULDCommand","ClearListULDCommand");
    	ListULDForm listULDForm = (ListULDForm) invocationContext.screenModel;
		ListULDSession listULDSession = (ListULDSession)getScreenSession(MODULE,SCREENID);
		clearForm(listULDForm);
		listULDSession.setIsListed(false);
		listULDSession.setListFilterVO(null);
		listULDSession.setListStatus("");
       	listULDSession.setListDisplayPage(null);
       	listULDForm.setScreenStatusFlag(
					ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
       	ULDListFilterVO uldListFilterVO = new ULDListFilterVO();
    	
       	//Added by Tarun as a part of defaulting airline code in page (ANACR - 1471)
    	Collection<ErrorVO> error = new ArrayList<ErrorVO>();
    	//removed by nisha on 29Apr08
    	
			if(logonAttributes.isAirlineUser()){
	    		//uldListFilterVO.setAirlineCode(logonAttributes.getOwnAirlineCode());
	    		//uldListFilterVO.setCurrentStation(logonAttributes.getAirportCode());
	    		listULDForm.setDisableStatus("airline");
	    	}
	    	else{
	    		uldListFilterVO.setCurrentStation(logonAttributes.getAirportCode());
	    		listULDForm.setDisableStatus("GHA");
	    	}
			//Added by a-3069 for CR1503
			//For bug ICRD-2154 =============
			//uldListFilterVO.setCurrentStation(logonAttributes.getAirportCode());
			//listULDForm.setCurrentStation(logonAttributes.getAirportCode());
			//===============================
		//Added by Tarun as a part of defaulting airline code in page (ANACR - 1471) ends
    	listULDSession.setListFilterVO(uldListFilterVO);
    	invocationContext.addAllError(error);
    	invocationContext.target = CLEAR_SUCCESS;

    }
    
	private void clearForm(ListULDForm listULDForm) {
		listULDForm.setUldNumber("");
		listULDForm.setUldGroupCode("");
		listULDForm.setUldTypeCode("");
		listULDForm.setAirlineCode("");
		listULDForm.setCurrentStation("");
		listULDForm.setOverallStatus("");
		listULDForm.setCleanlinessStatus("");
		listULDForm.setOwnerStation("");
		listULDForm.setLocation("");
		listULDForm.setDamageStatus("");
		listULDForm.setManufacturer("");
		listULDForm.setLocation("");
		listULDForm.setUldRangeFrom("");
		listULDForm.setUldRangeTo("");
		listULDForm.setLastMovementDate("");
		listULDForm.setUldNature("");
		listULDForm.setLevelValue("");
		listULDForm.setOalUldOnly(BLANKSPACE);
		
	}
}
