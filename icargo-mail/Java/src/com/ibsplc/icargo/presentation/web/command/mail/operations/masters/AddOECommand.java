/*
 * AddOECommand.java Created on July 28, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.masters;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.mail.operations.vo.OfficeOfExchangeVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.OfficeOfExchangeMasterSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.OfficeOfExchangeMasterForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-2047
 *
 */
public class AddOECommand extends BaseCommand {

	private static final String SUCCESS = "add_success";
//	private static final String FAILURE = "add_failure";
	
	private Log log = LogFactory.getLogger("AddOECommand");
	
	private static final String MODULE_NAME = "mail.operations";
	private static final String SCREEN_ID = 
							"mailtracking.defaults.masters.officeofexchange";
	
	
	/**
	 * This method overrides the executre method of BaseComand class
	 * @param invocationContext
	 * @return
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
									throws CommandInvocationException {
    	log.log(Log.FINE, "\n\n in the add command----------> \n\n");
    	
    	ApplicationSessionImpl applicationSessionImpl = getApplicationSession();
		LogonAttributes logonAttributes = applicationSessionImpl.getLogonVO();
		String companyCode = logonAttributes.getCompanyCode();
    	
    	OfficeOfExchangeMasterForm oeMasterForm =
					(OfficeOfExchangeMasterForm)invocationContext.screenModel;
    	OfficeOfExchangeMasterSession oeSession = 
										getScreenSession(MODULE_NAME,SCREEN_ID);

    	
 //   	Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
    	
	    Collection<OfficeOfExchangeVO> oeVOs = 
	    									oeSession.getOfficeOfExchangeVOs();
	    
    	if (oeVOs == null) {
    		oeVOs = new ArrayList<OfficeOfExchangeVO>();
		}
    	
    	String status = oeMasterForm.getStatus();
    	
    	if(("ADD").equals(status)){
    		OfficeOfExchangeVO officeOfExchangeVO = new OfficeOfExchangeVO();
    		officeOfExchangeVO.setCountryCode("");
    		officeOfExchangeVO.setCityCode("");
    		officeOfExchangeVO.setOfficeCode("");
    		officeOfExchangeVO.setCodeDescription("");
    		officeOfExchangeVO.setPoaCode("");
    		officeOfExchangeVO.setAirportCode("");
    		officeOfExchangeVO.setActive(false);
    		officeOfExchangeVO.setCode("");
    		officeOfExchangeVO.setCompanyCode(companyCode);
    		officeOfExchangeVO.setOperationFlag
    								(OfficeOfExchangeVO.OPERATION_FLAG_INSERT);

    		oeSession.setOfficeOfExchangeVO(officeOfExchangeVO);
    		oeMasterForm.setPopUpStatus("SHOW");
    		invocationContext.target = SUCCESS;
    		return;
    	}else if(("UPDATE").equals(status)){
    		String[] rowIds = oeMasterForm.getRowId();
    		
    		int num = 0;
        	
    		for(OfficeOfExchangeVO officeOfExchangeVO:oeVOs) {
        		for(int i = 0; i < rowIds.length; i++){
        			if(num == Integer.parseInt(rowIds[i])){
        				oeSession.setOfficeOfExchangeVO(officeOfExchangeVO);
        			}
        		}
        		num++;
        	}
    		oeMasterForm.setPopUpStatus("SHOW");
    		invocationContext.target = SUCCESS;
    		return;
    	}
    	    	
 
	}
	

}
