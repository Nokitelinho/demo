/* DeleteRowCommand.java Created on Apr 13,2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services(P)Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.customermanagement.defaults.loyalty.maintainloyalty;

import java.util.ArrayList;
import java.util.Collection;

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
 * 
 * @author A-1862
 */
public class DeleteRowCommand extends BaseCommand {
	
	
	
	private Log log = LogFactory.getLogger("DeleteParameterCommand");
	private static final String MODULE_NAME = "customermanagement.defaults";
    private static final String SCREEN_ID = "customermanagement.defaults.maintainloyalty";
	private static final String DELETE_SUCCESS = "deleteRow_success";
	private static final String OPERATION_FLAG_INS_DEL
	= "operation_flg_insert_delete";
	private static final String BLANK = "";
	/**
     * execute method
     * @param invocationContext
     * @throws CommandInvocationException
    */
    public void execute(InvocationContext invocationContext)throws CommandInvocationException {
    	log.entering("DeleteRowCommand","execute");
    	/**
		 * Obtain the logonAttributes
		 */
		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
	
		MaintainLoyaltyForm maintainLoyaltyForm = 
			(MaintainLoyaltyForm) invocationContext.screenModel;
		MaintainLoyaltySession maintainLoyaltySession = 
			(MaintainLoyaltySession)getScreenSession(MODULE_NAME,SCREEN_ID);
		maintainLoyaltyForm.setCloseWindow(false);
		LoyaltyProgrammeVO loyaltyProgrammeVO=new LoyaltyProgrammeVO();
		
		loyaltyProgrammeVO=maintainLoyaltySession.getLoyaltyProgrammeVO();
    	
    	log.log(Log.FINE,
				"\n\n\n\n loyaltyProgrammeVO BEFORE DELETE PARAMETER ---> ",
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
		Collection<LoyaltyParameterVO> loyaltyParameterVOFromSession = 
	loyaltyProgrammeVO.getLoyaltyParameterVOs();
		
    	Collection<LoyaltyParameterVO> loyaltyParameterVOForDisplay = 
    		maintainLoyaltySession.getParameterVOsForDisplay();
    	log.log(Log.FINE,
				"\n\n\n\n loyaltyParameterVOFromSession BEFore delete---> ",
				loyaltyParameterVOFromSession);
		log
				.log(
						Log.FINE,
						"\n\n\n\n maintainLoyaltySession.getParameterVOsForDisplay()BEFore delete---> ",
						maintainLoyaltySession.getParameterVOsForDisplay());
		ArrayList<LoyaltyParameterVO> loyaltyParameterVOstmp
		=new ArrayList<LoyaltyParameterVO>();
		
		String[] rowIds = maintainLoyaltyForm.getSelectedParameter();
		if (rowIds != null) {
			int index=0;
			 for (LoyaltyParameterVO loyaltyParameterVO :loyaltyParameterVOForDisplay) {
			for (int i = 0; i < rowIds.length; i++) {
				if (index == Integer.parseInt(rowIds[i])) {
					loyaltyParameterVO.setOperationFlag
						(AbstractVO.OPERATION_FLAG_DELETE);  
					log
							.log(
									Log.FINE,
									"\n\n\n\n loyaltyParameterVO.getParameterValue()---> ",
									loyaltyParameterVO.getParameterValue());
					for (LoyaltyParameterVO loyaltyParameterfromsession :loyaltyParameterVOFromSession) {
			                if(loyaltyParameterfromsession.getParameterCode().equalsIgnoreCase(loyaltyParameterVO.getParameterCode())){
		        		   if(loyaltyParameterfromsession.getOperationFlag()!=null && 
		        				!loyaltyParameterfromsession.getOperationFlag().equals
		        							(AbstractVO.OPERATION_FLAG_INSERT))
		        		   {
		        			   if(!BLANK.equals(loyaltyParameterVO.getParameterValue())){
		        			   loyaltyParameterfromsession.setOperationFlag
		        			   						(AbstractVO.OPERATION_FLAG_DELETE);  
		        			   
		        			  	        		   }}
		        		   if(loyaltyParameterfromsession.getOperationFlag()==null )
		        		   {
		        			   if(!BLANK.equals(loyaltyParameterVO.getParameterValue())){
		        			   loyaltyParameterfromsession.setOperationFlag
		   						(AbstractVO.OPERATION_FLAG_DELETE); 
		        			   }
		        			  
		        		   }
		        		   if(loyaltyParameterfromsession.getOperationFlag()!=null &&
		        				   loyaltyParameterfromsession.getOperationFlag().equals
		        								(AbstractVO.OPERATION_FLAG_INSERT))
		        		   {
		        			   if(!BLANK.equals(loyaltyParameterVO.getParameterValue())){
		        			   loyaltyParameterfromsession.setOperationFlag(OPERATION_FLAG_INS_DEL);
		        			   }else
		        			   {
		        				   if(BLANK.equals(loyaltyParameterfromsession.getParameterValue())){
				        			   loyaltyParameterfromsession.setOperationFlag(OPERATION_FLAG_INS_DEL);
				        			   }
		        			   }
		        			 
		        		   }
			                }
		        	   }
					 
					 
				}
				}
			index++;
			}
			
	    log.log(Log.FINE,
				"\n\n\n\n loyaltyParameterVOFromSession after delete---> ",
				loyaltyParameterVOFromSession);
		log
				.log(
						Log.FINE,
						"\n\n\n\n maintainLoyaltySession.getParameterVOsForDisplay()after delete---> ",
						maintainLoyaltySession.getParameterVOsForDisplay());
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
  
	
		 loyaltyProgrammeVO.setLoyaltyParameterVOs(loyaltyParameterVOstmp);
		 log.log(Log.FINE,
				"\n\n\n\n loyaltyProgrammeVO AFETR DELETE PARAMETER ---> ",
				loyaltyProgrammeVO);
		maintainLoyaltySession.setLoyaltyProgrammeVO(loyaltyProgrammeVO);
		 
		}
		
//	      FOR UPDATING DISPLAY
	     	
	     	LoyaltyProgrammeVO loyaltyProgrammeVOsMain=maintainLoyaltySession.getLoyaltyProgrammeVO();
	     	ArrayList<LoyaltyParameterVO> loyaltyParameterVOsMain=new ArrayList<LoyaltyParameterVO>(loyaltyProgrammeVOsMain.getLoyaltyParameterVOs());
	     	
	     	ArrayList<String> parameters=new ArrayList<String>();
			ArrayList<String> values=new ArrayList<String>();
			ArrayList<String> opFlag=new ArrayList<String>();
			
			
			
			if(loyaltyParameterVOsMain!=null && loyaltyParameterVOsMain.size()>0)
			{
			for(LoyaltyParameterVO loyaltyParameterVO : 
				loyaltyParameterVOsMain){
				if((loyaltyParameterVO.getOperationFlag()!=null && !loyaltyParameterVO.getOperationFlag().equals(AbstractVO.OPERATION_FLAG_DELETE))
						|| loyaltyParameterVO.getOperationFlag()==null)
				{	
			if(!parameters.contains(loyaltyParameterVO.getParameterCode()))
			{
				parameters.add(loyaltyParameterVO.getParameterCode());
				
			}}
			}
			log.log(Log.FINE, "parameters.size()---> ", parameters.size());
			int size=parameters.size();
			for(int i=0;i<size;i++){
				StringBuffer str = new StringBuffer();
				boolean isNull=false;
				
			for(LoyaltyParameterVO loyaltyParameterVO : 
				loyaltyProgrammeVO.getLoyaltyParameterVOs()){
				if(parameters.get(i).equals(loyaltyParameterVO.getParameterCode()))
				{
					if(loyaltyParameterVO.getOperationFlag()==null
							|| (loyaltyParameterVO.getOperationFlag()!=null &&
									loyaltyParameterVO.getOperationFlag().equals(AbstractVO.OPERATION_FLAG_UPDATE))
							//|| (loyaltyParameterVO.getOperationFlag()!=null &&
							//		loyaltyParameterVO.getOperationFlag().equals(AbstractVO.OPERATION_FLAG_DELETE))
									)
					{
						isNull=true;
					}
					if(loyaltyParameterVO.getOperationFlag()==null
							|| !(loyaltyParameterVO.getOperationFlag()!=null &&
									loyaltyParameterVO.getOperationFlag().equals(AbstractVO.OPERATION_FLAG_DELETE)))
					{
					str.append(loyaltyParameterVO.getParameterValue()).append(",");
					}
					
				}
				
			}
			if(isNull)
			{
				opFlag.add(i,"N");
			}
			else{
				opFlag.add(i,"I");
			}
			str.deleteCharAt(str.length()-1);
			
			values.add(str.toString());
			
			}
			log.log(Log.FINE, "values.size()---> ", values.size());
			log.log(Log.FINE, "opFlag.size()---> ", opFlag.size());
			ArrayList<LoyaltyParameterVO> loyaltyParameterVOsTmp=new ArrayList<LoyaltyParameterVO>();
			int paramSize=parameters.size();
			for(int i=0;i<paramSize;i++){
			{
				LoyaltyParameterVO loyaltyParameterVOTmp=new LoyaltyParameterVO();
				if(AbstractVO.FLAG_NO.equals(opFlag.get(i)))
				{
					loyaltyParameterVOTmp.setOperationFlag(null);
				}
				else
				{
					loyaltyParameterVOTmp.setOperationFlag(AbstractVO.OPERATION_FLAG_INSERT);
				}
				loyaltyParameterVOTmp.setParameterCode(parameters.get(i));
				loyaltyParameterVOTmp.setParameterValue(values.get(i));
				loyaltyParameterVOsTmp.add(loyaltyParameterVOTmp);
			}
			
			log.log(Log.FINE,
					"ParameterVOsForDisplay AFTER LIST IN MODIFY MODE---> ",
					loyaltyParameterVOsTmp);
			maintainLoyaltySession.setParameterVOsForDisplay(loyaltyParameterVOsTmp);
			}}
			if(values.size()==0 && parameters.size()==0)
			{
				maintainLoyaltySession.setParameterVOsForDisplay(null);
			}
			
			
			
    	invocationContext.target =  DELETE_SUCCESS;
     }
}
