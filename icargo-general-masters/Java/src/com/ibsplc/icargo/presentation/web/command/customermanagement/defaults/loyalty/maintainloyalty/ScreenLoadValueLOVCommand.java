/*
 * ScreenLoadValueLOVCommand.java Created on Apr 12, 2006
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
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.customermanagement.defaults.loyalty.MaintainLoyaltySession;
import com.ibsplc.icargo.presentation.web.struts.form.customermanagement.defaults.loyalty.MaintainLoyaltyForm;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * This command class is used to ScreenLoadValueLOV
 * @author A-1862
 */
public class ScreenLoadValueLOVCommand extends BaseCommand {
	
	
	private Log log = LogFactory.getLogger("Maintain Loyalty");
	
	private static final String MODULE = "customermanagement.defaults";
	
	
	private static final String SCREENID =
		"customermanagement.defaults.maintainloyalty";
    
    private static final String SCREENLOAD_SUCCESS = "screenload_success";
    
    private static final String BLANK = "";
    

    /**
     * execute method
     * @param invocationContext
     * @throws CommandInvocationException
    */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
		
		MaintainLoyaltyForm maintainLoyaltyForm = 
			(MaintainLoyaltyForm) invocationContext.screenModel;
		MaintainLoyaltySession maintainLoyaltySession = 
			(MaintainLoyaltySession)getScreenSession(MODULE,SCREENID);
		maintainLoyaltySession.setParameterVOsForLOV(null);
		    	
		log.log(Log.FINE, "maintainLoyaltyForm.getParameterIndex()---> ",
				maintainLoyaltyForm.getParameterIndex());
		LoyaltyProgrammeVO loyaltyProgrammeVOsMain=maintainLoyaltySession.getLoyaltyProgrammeVO();
		ArrayList<LoyaltyParameterVO>  loyaltyParameterVOs = new ArrayList<LoyaltyParameterVO>();
		ArrayList<LoyaltyParameterVO> loyaltyParameterVOsMain=new ArrayList<LoyaltyParameterVO>(loyaltyProgrammeVOsMain.getLoyaltyParameterVOs());
		ArrayList<LoyaltyParameterVO> loyaltyParameterVOsTmp=maintainLoyaltySession.getParameterVOsForDisplay();
		LoyaltyParameterVO loyaltyParametervo=loyaltyParameterVOsTmp.get(Integer.parseInt(maintainLoyaltyForm.getParameterIndex()));
		
		if(BLANK.equals(loyaltyParametervo.getParameterCode()))
		{	
			log.log(Log.FINE, "maintainLoyaltyForm.getParameterInLOV()---> ",
					maintainLoyaltyForm.getParameterInLOV());
		loyaltyParametervo.setParameterCode(maintainLoyaltyForm.getParameterInLOV());
		for(LoyaltyParameterVO loyaltyparametervo:loyaltyParameterVOsMain)
		{
			if(BLANK.equals(loyaltyparametervo.getParameterCode()))
			{	
				log.log(Log.FINE, "IN VO IT IS BLANK BECAUSE IT WAS JUST INSERTED");
				loyaltyparametervo.setParameterCode(maintainLoyaltyForm.getParameterInLOV());
			}
		}
		
		}else
		{
			if(!loyaltyParametervo.getParameterCode().equals(maintainLoyaltyForm.getParameterInLOV()))
			{
				log.log(Log.FINE, "UPDATION DURING SCREENLOAD");
				String updatedParam=loyaltyParametervo.getParameterCode();
				loyaltyParametervo.setParameterCode(maintainLoyaltyForm.getParameterInLOV());
				for(LoyaltyParameterVO loyaltyparametervo:loyaltyParameterVOsMain)
				{
					if((loyaltyparametervo.getOperationFlag()!=null && !loyaltyparametervo.getOperationFlag().equals(AbstractVO.OPERATION_FLAG_DELETE))
							|| loyaltyparametervo.getOperationFlag()==null)
					{
					if(loyaltyparametervo.getParameterCode().equals(updatedParam))
					{	
						
						loyaltyparametervo.setParameterCode(maintainLoyaltyForm.getParameterInLOV());
					}}
				}
			}
			
			
		}
		log.log(Log.FINE, "loyaltyParametervo.getParameterCode()---> ",
				loyaltyParametervo.getParameterCode());
		log.log(Log.FINE, "loyaltyParameterVOsMain IN SCREENLOAD---> ",
				loyaltyParameterVOsMain);
		log.log(Log.FINE, "maintainLoyaltyForm.getParameterInLOV()---> ",
				maintainLoyaltyForm.getParameterInLOV());
		maintainLoyaltyForm.setParameterInLOV(loyaltyParametervo.getParameterCode());
		log.log(Log.FINE, "maintainLoyaltyForm.getParameterInLOV()---> ",
				maintainLoyaltyForm.getParameterInLOV());
		for(LoyaltyParameterVO loyaltyparametervo:loyaltyParameterVOsMain)
		{
			if((loyaltyparametervo.getOperationFlag()!=null && !loyaltyparametervo.getOperationFlag().equals(AbstractVO.OPERATION_FLAG_DELETE))
					|| loyaltyparametervo.getOperationFlag()==null)
			{
				if(loyaltyparametervo.getParameterCode().equals(loyaltyParametervo.getParameterCode()) &&
						!BLANK.equals(loyaltyparametervo.getParameterValue()))
				{
					loyaltyParameterVOs.add(loyaltyparametervo);
				}
			}
			
		}
		maintainLoyaltyForm.setCloseWindow(false);
		maintainLoyaltySession.setParameterVOsForLOV(loyaltyParameterVOs);
		
		
		invocationContext.target = SCREENLOAD_SUCCESS;
        
    }
   
   

}
