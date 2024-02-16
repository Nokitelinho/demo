/*
 * RefreshCommand.java Created on Apr 12, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.presentation.web.command.customermanagement.defaults.loyalty.maintainloyalty;

import java.util.ArrayList;

import com.ibsplc.icargo.business.customermanagement.defaults.loyalty.vo.LoyaltyParameterVO;
import com.ibsplc.icargo.business.customermanagement.defaults.loyalty.vo.LoyaltyProgrammeVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.customermanagement.defaults.loyalty.MaintainLoyaltySession;
import com.ibsplc.icargo.presentation.web.struts.form.customermanagement.defaults.loyalty.MaintainLoyaltyForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * This command class is used to ScreenLoadValueLOV
 * @author A-1862
 */
public class RefreshCommand extends BaseCommand {
	
	
	private Log log = LogFactory.getLogger("Maintain Loyalty");
	
	private static final String MODULE = "customermanagement.defaults";
	
	
	private static final String SCREENID =
		"customermanagement.defaults.maintainloyalty";
    
    private static final String REFRESH_SUCCESS = "refresh_success";
    
    private static final String PARAMETERVALUE_LOV = "VALUESLOV";
    
    

    /**
     * execute method
     * @param invocationContext
     * @throws CommandInvocationException
    */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	log.entering("RefreshCommand","execute");
		MaintainLoyaltyForm maintainLoyaltyForm = 
			(MaintainLoyaltyForm) invocationContext.screenModel;
		MaintainLoyaltySession maintainLoyaltySession = 
			(MaintainLoyaltySession)getScreenSession(MODULE,SCREENID);
		maintainLoyaltySession.setParameterVOsForLOV(null);
    	ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		    	
		String[] parameters = maintainLoyaltyForm.getParameter();
		String[] value = maintainLoyaltyForm.getValue();
		String[] operationFlags = maintainLoyaltyForm.getHiddenOpFlag();
		log.log(Log.INFO, "parameters----------", parameters);
		log.log(Log.INFO, "value----------", value);
		log.log(Log.INFO, "operationFlags----------", operationFlags);
		int index = 0;	
		ArrayList<LoyaltyParameterVO> loyaltyParameterVOs=new ArrayList<LoyaltyParameterVO>();
		for(String parameter : parameters){
			if(!LoyaltyParameterVO.OPERATION_FLAG_DELETE.equals(operationFlags[index])
					&& !"NOOP".equals(operationFlags[index])){
    			LoyaltyParameterVO loyaltyParameterVO = new LoyaltyParameterVO();
    			loyaltyParameterVO.setCompanyCode(logonAttributes.getCompanyCode());
    			loyaltyParameterVO.setLoyaltyProgrammeCode(maintainLoyaltySession.getLoyaltyProgrammeVO().getLoyaltyProgrammeCode());
    			loyaltyParameterVO.setParameterCode(parameter);
    			loyaltyParameterVO.setParameterValue(value[index]);
    			loyaltyParameterVO.setOperationFlag(operationFlags[index]);
    			loyaltyParameterVO.setSequenceNumber(String.valueOf(index));
    			if(LoyaltyParameterVO.OPERATION_FLAG_UPDATE.equals(operationFlags[index])){
    				loyaltyParameterVO.setChanged(true);
    			}
    			loyaltyParameterVOs.add(loyaltyParameterVO);
			}
			index++;
		}
		log.log(Log.INFO, "loyaltyParameterVOs----------", loyaltyParameterVOs);
		maintainLoyaltySession.setParameterVOsForDisplay(loyaltyParameterVOs);
		LoyaltyProgrammeVO loyaltyProgrammeVOsMain=maintainLoyaltySession.getLoyaltyProgrammeVO();
		loyaltyProgrammeVOsMain.setLoyaltyParameterVOs(loyaltyParameterVOs);
		maintainLoyaltySession.setLoyaltyProgrammeVO(loyaltyProgrammeVOsMain);
		maintainLoyaltySession.setParameterVOsForLOV(loyaltyParameterVOs);
		maintainLoyaltyForm.setScreenStatusValue(PARAMETERVALUE_LOV);
		invocationContext.target = REFRESH_SUCCESS;
		log.exiting("RefreshCommand", "exit");
       
    }
   
   

}
