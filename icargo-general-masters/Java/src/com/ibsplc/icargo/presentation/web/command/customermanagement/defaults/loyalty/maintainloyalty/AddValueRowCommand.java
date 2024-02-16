/**
 * AddValueRowCommand.java Created on APR 13,2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services(P)Ltd.
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
import com.ibsplc.xibase.server.framework.vo.AbstractVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;


/**
 * This command class is used to ADD VALUE
 * @author A-1862
 */
public class AddValueRowCommand extends BaseCommand {
	
	
	private Log log = LogFactory.getLogger("MAINTAIN LOYALTY");
	
	private static final String MODULE = "customermanagement.defaults";
	
	
	private static final String SCREENID =
		"customermanagement.defaults.maintainloyalty";
	private static final String ADD_SUCCESS = "addRow_success";
	
	

	/**
     * execute method
     * @param invocationContext
     * @throws CommandInvocationException
    */
    public void execute(InvocationContext invocationContext)throws 
    											CommandInvocationException {
    	log.entering("AddValueCommand","execute");
    
    	ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		
		MaintainLoyaltyForm maintainLoyaltyForm = 
			(MaintainLoyaltyForm) invocationContext.screenModel;
		MaintainLoyaltySession maintainLoyaltySession = 
			(MaintainLoyaltySession)getScreenSession(MODULE,SCREENID);
		maintainLoyaltyForm.setCloseWindow(false);
		
		log.log(Log.FINE,
				"\n\n\n\n maintainLoyaltyForm.getParameterInLOV() ADD---> ",
				maintainLoyaltyForm.getParameterInLOV());
		ArrayList<LoyaltyParameterVO> loyaltyParameterVOs=new ArrayList<LoyaltyParameterVO>();
		LoyaltyProgrammeVO loyaltyProgrammeVOsMain=maintainLoyaltySession.getLoyaltyProgrammeVO();
     	ArrayList<LoyaltyParameterVO> loyaltyParameterVOsMain=new ArrayList<LoyaltyParameterVO>(loyaltyProgrammeVOsMain.getLoyaltyParameterVOs());
     	
		
		loyaltyParameterVOs=maintainLoyaltySession.getParameterVOsForLOV();
    	
    	log.log(Log.FINE,
				"\n\n\n\n loyaltyParameterVOs BEFORE ADD PARAMETER ---> ",
				loyaltyParameterVOs);
		if(loyaltyParameterVOs != null && 
    			loyaltyParameterVOs.size() > 0){
     		log.log(Log.FINE, "\n\n\n\n INSIDE LOOP " );
    		
    		String[] value = 
    			maintainLoyaltyForm.getValueInLov();
    	   	    		
    		
    		int index = 0;	
    		for(LoyaltyParameterVO loyaltyParameterVO : 
    			loyaltyParameterVOs){
    			
    			maintainLoyaltyForm.setParameterInLOV(loyaltyParameterVO.getParameterCode());
    			if((loyaltyParameterVO.getOperationFlag()!=null &&
    		    		!loyaltyParameterVO.getOperationFlag().
    		    					equals(AbstractVO.OPERATION_FLAG_DELETE))
    		    					
    		    			|| loyaltyParameterVO.
    		    								getOperationFlag()==null){
    			if(!loyaltyParameterVO.getParameterValue().
    						equalsIgnoreCase(value[index]) ){
    			if(loyaltyParameterVO.getOperationFlag()==null){
    					
    				loyaltyParameterVO.setOperationFlag
    									(AbstractVO.OPERATION_FLAG_UPDATE);
    				//loyaltyParameterVO.setLastUpdatedUser(logonAttributes.getUserId());     
    			
    				}
    			}
    			loyaltyParameterVO.setParameterCode(maintainLoyaltyForm.getParameterInLOV());
    			loyaltyParameterVO.setParameterValue(value[index].toUpperCase());
    			
    			  			
    			
    			}
    		
    			index++;
    			}
    		}
    	
    	
    	LoyaltyParameterVO newLoyaltyParameterVO =new LoyaltyParameterVO();
    	newLoyaltyParameterVO.setOperationFlag
     										(AbstractVO.OPERATION_FLAG_INSERT);
    	log.log(Log.FINE,
				"\n\n\n\n maintainLoyaltyForm.getParameterInLOV()---> ",
				maintainLoyaltyForm.getParameterInLOV());
		newLoyaltyParameterVO.setLoyaltyProgrammeCode(loyaltyProgrammeVOsMain.getLoyaltyProgrammeCode());
    	newLoyaltyParameterVO.setParameterCode(maintainLoyaltyForm.getParameterInLOV());
    	newLoyaltyParameterVO.setSequenceNumber(String.valueOf(populateSequence(loyaltyParameterVOsMain)));
    	newLoyaltyParameterVO.setCompanyCode(logonAttributes.getCompanyCode());
    	//newLoyaltyParameterVO.setLastUpdatedUser(logonAttributes.getUserId());     	
    	
    	newLoyaltyParameterVO.setParameterValue("");
    	
    	
    	loyaltyParameterVOs.add(newLoyaltyParameterVO);
    	
     	   									
     	
     	log.log(Log.FINE,
				"\n\n\n\n loyaltyParameterVOs AFETR ADD parameter ---> ",
				loyaltyParameterVOs);
		maintainLoyaltySession.setParameterVOsForLOV(loyaltyParameterVOs);
		invocationContext.target =  ADD_SUCCESS;
		
    	
     }
    
    /**
     * 
     * @param loyaltyParameterVOs
     * @return
     */
    public int populateSequence(ArrayList<LoyaltyParameterVO> 
    					loyaltyParameterVOs) {
			log.entering("AddValueRowCommand", "populateSequence");
			int dmgseq=0;
			for(LoyaltyParameterVO loyaltyParameterVO:loyaltyParameterVOs)
			{
			if(Integer.parseInt(loyaltyParameterVO.getSequenceNumber())>dmgseq)
			{
			dmgseq=Integer.parseInt(loyaltyParameterVO.getSequenceNumber());
			}
			}
			dmgseq=dmgseq+1;
			log.exiting("AddValueRowCommand", "populateSequence");
			return dmgseq;
	
	}
}
