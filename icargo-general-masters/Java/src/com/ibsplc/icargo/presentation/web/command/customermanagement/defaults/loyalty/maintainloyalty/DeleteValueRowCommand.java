/* DeleteValueRowCommand.java Created on Apr 13,2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services(P)Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.customermanagement.defaults.loyalty.maintainloyalty;

import java.util.ArrayList;

import com.ibsplc.icargo.business.customermanagement.defaults.loyalty.vo.LoyaltyParameterVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.customermanagement.defaults.loyalty.MaintainLoyaltySession;
import com.ibsplc.icargo.presentation.web.struts.form.customermanagement.defaults.loyalty.MaintainLoyaltyForm;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;


/**
 * 
 * @author A-1862
 */
public class DeleteValueRowCommand extends BaseCommand {
	
	
	
	private Log log = LogFactory.getLogger("DeleteParameterCommand");
	private static final String MODULE_NAME = "customermanagement.defaults";
    private static final String SCREEN_ID = "customermanagement.defaults.maintainloyalty";
	private static final String DELETE_SUCCESS = "deleteRow_success";
	private static final String OPERATION_FLAG_INS_DEL
	= "operation_flg_insert_delete";

	/**
     * execute method
     * @param invocationContext
     * @throws CommandInvocationException
    */
    public void execute(InvocationContext invocationContext)throws CommandInvocationException {
    	log.entering("DeleteValueRowCommand","execute");
			
		MaintainLoyaltyForm maintainLoyaltyForm = 
			(MaintainLoyaltyForm) invocationContext.screenModel;
		MaintainLoyaltySession maintainLoyaltySession = 
			(MaintainLoyaltySession)getScreenSession(MODULE_NAME,SCREEN_ID);
		maintainLoyaltyForm.setCloseWindow(false);
		ArrayList<LoyaltyParameterVO> loyaltyParameterVOs=new ArrayList<LoyaltyParameterVO>();
		
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
    	ArrayList<LoyaltyParameterVO> loyaltyParameterVOFromSession=maintainLoyaltySession.getParameterVOsForLOV();
     
		ArrayList<LoyaltyParameterVO> loyaltyParameterVOstmp
		=new ArrayList<LoyaltyParameterVO>();
		
		String[] rowIds = maintainLoyaltyForm.getSelectedValue();
		if (rowIds != null) {
			int index=0;
			 for (LoyaltyParameterVO loyaltyParameterVO :loyaltyParameterVOFromSession) {
			for (int i = 0; i < rowIds.length; i++) {
				if (index == Integer.parseInt(rowIds[i])) {
					
	                
	        		   if(loyaltyParameterVO.getOperationFlag()!=null && 
	        				!loyaltyParameterVO.getOperationFlag().equals
	        							(AbstractVO.OPERATION_FLAG_INSERT))
	        		   {
	        			   loyaltyParameterVO.setOperationFlag
	        			   						(AbstractVO.OPERATION_FLAG_DELETE);  
	        			   //loyaltyParameterVO.setLastUpdatedUser(logonAttributes.getUserId());     
	        			  	        		   }
	        		   if(loyaltyParameterVO.getOperationFlag()==null )
	        		   {
	        			   loyaltyParameterVO.setOperationFlag
	        			   						(AbstractVO.OPERATION_FLAG_DELETE);  
	        			  // loyaltyParameterVO.setLastUpdatedUser(logonAttributes.getUserId());     
	        			  
	        		   }
	        		   if(loyaltyParameterVO.getOperationFlag()!=null &&
	        				   loyaltyParameterVO.getOperationFlag().equals
	        								(AbstractVO.OPERATION_FLAG_INSERT))
	        		   {
	        			   loyaltyParameterVO.setOperationFlag(OPERATION_FLAG_INS_DEL);
	        			 
	        		   }
	        		  
	        	   }
	        	   }index++;
	               }}
		
		 for (LoyaltyParameterVO loyaltyParameterVOTmp :loyaltyParameterVOFromSession) {
      	   if(loyaltyParameterVOTmp.getOperationFlag()!=null && 
      			   !loyaltyParameterVOTmp.getOperationFlag()
      			   				.equals(OPERATION_FLAG_INS_DEL))
      	   {
      		 loyaltyParameterVOstmp.add(loyaltyParameterVOTmp);
      	   }
      	   if(loyaltyParameterVOTmp.getOperationFlag()==null )
  		   {
      		 loyaltyParameterVOstmp.add(loyaltyParameterVOTmp);
  		   }
         }
  
	
		
		 log.log(Log.FINE,
				"\n\n\n\n loyaltyParameterVOstmp AFETR DELETE PARAMETER ---> ",
				loyaltyParameterVOstmp);
		maintainLoyaltySession.setParameterVOsForLOV(loyaltyParameterVOstmp);
		
    	invocationContext.target =  DELETE_SUCCESS;
     }
}
