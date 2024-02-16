/*
 * UnitChangedCommand.java Created on Apr 12, 2006
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
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.customermanagement.defaults.loyalty.MaintainLoyaltySession;
import com.ibsplc.icargo.presentation.web.struts.form.customermanagement.defaults.loyalty.MaintainLoyaltyForm;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * This command class is used when Unit is Changed
 * @author A-1862
 */
public class UnitChangedCommand extends BaseCommand {
	
	
	private Log log = LogFactory.getLogger("Maintain Loyalty");
	
	private static final String MODULE = "customermanagement.defaults";
	
	
	private static final String SCREENID =
		"customermanagement.defaults.maintainloyalty";
    
    private static final String CHANGE_SUCCESS = "change_success";
    
    private static final String BLANK = "";
    

    /**
     * execute method
     * @param invocationContext
     * @throws CommandInvocationException
    */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	/**
		 * Obtain the logonAttributes
		 */
		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
				
		MaintainLoyaltyForm maintainLoyaltyForm = 
			(MaintainLoyaltyForm) invocationContext.screenModel;
		MaintainLoyaltySession maintainLoyaltySession = 
			(MaintainLoyaltySession)getScreenSession(MODULE,SCREENID);
		maintainLoyaltyForm.setCloseWindow(false);
		LoyaltyProgrammeVO loyaltyProgrammeVO=new LoyaltyProgrammeVO();
		
		loyaltyProgrammeVO=maintainLoyaltySession.getLoyaltyProgrammeVO();
		
    	log.log(Log.FINE,
				"\n\n\n\n loyaltyProgrammeVO BEFORE UNIT CHANGED ---> ",
				loyaltyProgrammeVO);
		if(maintainLoyaltyForm.getLoyaltyDesc()!=null &&
    			maintainLoyaltyForm.getLoyaltyDesc().length()!=0 )
    	{
    		loyaltyProgrammeVO.setLoyaltyProgrammeDesc(maintainLoyaltyForm.getLoyaltyDesc());
    	}
    	if(maintainLoyaltyForm.getEntryPoints()!=null &&
    			maintainLoyaltyForm.getEntryPoints().length()!=0 )
        {
    		loyaltyProgrammeVO.setEntryPoints(Double.parseDouble(maintainLoyaltyForm.getEntryPoints()));
        }
    	
    	if(maintainLoyaltyForm.getFromDate()!=null &&
    			maintainLoyaltyForm.getFromDate().length()!=0 )
        {
    		LocalDate fromDate = new LocalDate(logonAttributes.getStationCode(),Location.STN, false);
    		loyaltyProgrammeVO.setFromDate(fromDate.setDate(
    			maintainLoyaltyForm.getFromDate()));
        }
    	
    	if(maintainLoyaltyForm.getToDate()!=null &&
    			maintainLoyaltyForm.getToDate().length()!=0 )
        {
    		LocalDate toDate = new LocalDate(logonAttributes.getStationCode(),Location.STN, false);
    		loyaltyProgrammeVO.setToDate(toDate.setDate(
        			maintainLoyaltyForm.getToDate()));
        }
    	if(maintainLoyaltyForm.getExpiryPeriodValue()!=null &&
    			maintainLoyaltyForm.getExpiryPeriodValue().length()!=0 )
        {
    		loyaltyProgrammeVO.setExpiryPeriod(Double.parseDouble(maintainLoyaltyForm.getExpiryPeriodValue()));
        }
    	
		if(maintainLoyaltyForm.getExpiryPeriod()!=null &&
    			maintainLoyaltyForm.getExpiryPeriod().length()!=0 )
        {
    		loyaltyProgrammeVO.setExpiryDuration(maintainLoyaltyForm.getExpiryPeriod());
        }
    	
    	if(maintainLoyaltyForm.getAttribute()!=null &&
    			maintainLoyaltyForm.getAttribute().length()!=0 )
        {
    		loyaltyProgrammeVO.setAttibute(maintainLoyaltyForm.getAttribute());
        }
    	
    	if(maintainLoyaltyForm.getUnit()!=null &&
    			maintainLoyaltyForm.getUnit().length()!=0 )
        {
    		loyaltyProgrammeVO.setUnits(maintainLoyaltyForm.getUnit());
        }
    	if(maintainLoyaltyForm.getAmount()!=null &&
    			maintainLoyaltyForm.getAmount().length()!=0 )
        {
    		loyaltyProgrammeVO.setAmount(Double.parseDouble(maintainLoyaltyForm.getAmount()));
        }
    	if(maintainLoyaltyForm.getPoints()!=null &&
    			maintainLoyaltyForm.getPoints().length()!=0 )
        {
    		loyaltyProgrammeVO.setPoints(Double.parseDouble(maintainLoyaltyForm.getPoints()));
        }
    	
    	
//      UPDATE PARAMETER TABLE
		
     	
     	ArrayList<LoyaltyParameterVO> loyaltyParameterVOs= 
     		maintainLoyaltySession.getParameterVOsForDisplay() != null ?
			new ArrayList<LoyaltyParameterVO>
     			(maintainLoyaltySession.getParameterVOsForDisplay()) : 
			new ArrayList<LoyaltyParameterVO>();
     			
     ArrayList<LoyaltyParameterVO> loyaltyParameterVOMain= 
     	     		maintainLoyaltySession.getLoyaltyProgrammeVO().getLoyaltyParameterVOs() != null ?
     				new ArrayList<LoyaltyParameterVO>
     	     			(maintainLoyaltySession.getLoyaltyProgrammeVO().getLoyaltyParameterVOs()) : 
     				new ArrayList<LoyaltyParameterVO>();
			log.log(Log.FINE, "\n\n\n\n loyaltyParameterVOs ---> ",
					loyaltyParameterVOs);
			for(LoyaltyParameterVO loyaltyParameterVOTmp : 
				loyaltyParameterVOMain)
			{
				loyaltyParameterVOTmp.setChanged(false);
			}
    	
			if(loyaltyParameterVOs != null && 
	    			loyaltyParameterVOs.size() > 0){
	     		log.log(Log.FINE, "\n\n\n\n INSIDE LOOP " );
	    		String[] parameter = maintainLoyaltyForm.getParameter();
	    		String[] value = 
	    			maintainLoyaltyForm.getValue();
	    	   	    		
	    		
	    		int index = 0;	
	    		for(LoyaltyParameterVO loyaltyParameterVO : 
	    			loyaltyParameterVOs){
	    			if((loyaltyParameterVO.getOperationFlag()!=null &&
	    		    		!loyaltyParameterVO.getOperationFlag().
	    		    					equals(AbstractVO.OPERATION_FLAG_DELETE))
	    		    					
	    		    			|| loyaltyParameterVO.
	    		    								getOperationFlag()==null){
	    			if(!loyaltyParameterVO.getParameterCode().
	    						equalsIgnoreCase(parameter[index]) ||
	    					!loyaltyParameterVO.getParameterValue().
	    						equalsIgnoreCase(value[index]) ){
	    				log
								.log(
										Log.FINE,
										"\n\n\n\n loyaltyParameterVO.getParameterCode() ---> ",
										loyaltyParameterVO.getParameterCode());
						log.log(Log.FINE, "\n\n\n\n parameter[index] ---> ",
								parameter, index);
						for(LoyaltyParameterVO loyaltyParameterVOTmp : 
		    				loyaltyParameterVOMain)
		    			{
	    					if((loyaltyParameterVOTmp.getOperationFlag()!=null &&
	    	    		    		!loyaltyParameterVOTmp.getOperationFlag().
	    	    		    					equals(AbstractVO.OPERATION_FLAG_DELETE))
	    	    		    					
	    	    		    			|| loyaltyParameterVOTmp.
	    	    		    								getOperationFlag()==null){
	    						
	    						if(loyaltyParameterVOTmp.getParameterCode().equalsIgnoreCase(loyaltyParameterVO.getParameterCode()))
	    						{
	    							if(!loyaltyParameterVOTmp.isChanged()){
	    							loyaltyParameterVOTmp.setChanged(true);
	    							loyaltyParameterVOTmp.setParameterCode(parameter[index]);
	    							if(loyaltyParameterVOTmp.getOperationFlag()==null){
	    		    					
	    								loyaltyParameterVOTmp.setOperationFlag
	    			    									(AbstractVO.OPERATION_FLAG_UPDATE);
	    			    				   
	    			    			
	    			    				}
	    							}
	    						}
	    						
	    						
	    					}
	    					
		    			}
	    				
	    				
	    				
	    			if(loyaltyParameterVO.getOperationFlag()==null){
	    					
	    				loyaltyParameterVO.setOperationFlag
	    									(AbstractVO.OPERATION_FLAG_UPDATE);
	    				//loyaltyParameterVO.setLastUpdatedUser(logonAttributes.getUserId());     
	    			
	    				}
	    			String newparamcode="";
	    			if(BLANK.equals(loyaltyParameterVO.getParameterCode()) && 
	    					BLANK.equals(loyaltyParameterVO.getParameterValue()))
	    			{
	    				newparamcode=parameter[index];
	    				
	    			}
	    			
	    			
	    			for(LoyaltyParameterVO loyaltyParameterVOTmp : 
	    				loyaltyParameterVOMain)
	    			{
	    				
	    				if(loyaltyParameterVOTmp.getParameterCode().equalsIgnoreCase(loyaltyParameterVO.getParameterCode()))
	    				{
	    					if(loyaltyParameterVOTmp.getOperationFlag()==null){
	        					
	    						loyaltyParameterVOTmp.setOperationFlag
	    	    									(AbstractVO.OPERATION_FLAG_UPDATE);
	    	    				
	    	    			
	    	    				}
	    				}
	    				if(BLANK.equals(loyaltyParameterVOTmp.getParameterCode()) && BLANK.equals(loyaltyParameterVOTmp.getParameterValue()))
	    				{
	    					log.log(Log.FINE, "\n\n\n\n newparamcode ---> ",
									newparamcode);
							loyaltyParameterVOTmp.setParameterCode(newparamcode);
	    				}
	    				
	    			}
	    			
	    			}
	    			
	    			
	    			loyaltyParameterVO.setParameterCode(parameter[index]);
	    			loyaltyParameterVO.setParameterValue(value[index].toUpperCase());
	    			
	    			  			
	    			
	    			}
	    		
	    			index++;
	    			}
	    		}
	    	
	    	log.log(Log.FINE, "\n\n\n\n DISPLAY BEFEORE DELETE ---> ",
					loyaltyParameterVOs);
			log
					.log(
							Log.FINE,
							"\n\n\n\n loyaltyProgrammeVO BEFORE DELETE AFTER UPDATE PARAMETER ---> ",
							loyaltyProgrammeVO);
		log.log(Log.FINE, "\n\n\n\n maintainLoyaltyForm.getAttribute() ---> ",
				maintainLoyaltyForm.getAttribute());
		log.log(Log.FINE, "\n\n\n\n maintainLoyaltyForm.getUnit() ---> ",
				maintainLoyaltyForm.getUnit());
		if((maintainLoyaltySession.getAttributeValue()!=null && 
				maintainLoyaltyForm.getAttribute().equals(maintainLoyaltySession.getAttributeValue()))
				&&
		   (maintainLoyaltySession.getUnitValue()!=null && 
				   maintainLoyaltyForm.getUnit().equals(maintainLoyaltySession.getUnitValue())))
		   {
			loyaltyProgrammeVO.setAttibute(maintainLoyaltyForm.getAttribute());
			loyaltyProgrammeVO.setUnits(maintainLoyaltyForm.getUnit());
			loyaltyProgrammeVO.setAmount(Double.parseDouble(maintainLoyaltySession.getAmountValue()));
			loyaltyProgrammeVO.setPoints(Double.parseDouble(maintainLoyaltySession.getPointsValue()));
			
		   }
		else
		{
			loyaltyProgrammeVO.setAttibute(maintainLoyaltyForm.getAttribute());
			loyaltyProgrammeVO.setUnits(maintainLoyaltyForm.getUnit());
			loyaltyProgrammeVO.setAmount(0.0);
			loyaltyProgrammeVO.setPoints(0.0);
		}
		    	
    	invocationContext.target = CHANGE_SUCCESS;
        
    }
   
   


}
