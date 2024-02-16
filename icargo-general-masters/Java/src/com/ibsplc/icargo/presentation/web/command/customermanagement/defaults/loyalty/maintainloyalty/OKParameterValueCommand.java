/**
 * OKParameterValueCommand.java Created on APR 13,2006
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
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.customermanagement.defaults.loyalty.MaintainLoyaltySession;
import com.ibsplc.icargo.presentation.web.struts.form.customermanagement.defaults.loyalty.MaintainLoyaltyForm;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;


/**
 * This command class is used to ADD PARAMETER VALUES
 * @author A-1862
 */
public class OKParameterValueCommand extends BaseCommand {
	
	
	private Log log = LogFactory.getLogger("MAINTAIN LOYALTY");
	
	private static final String MODULE = "customermanagement.defaults";
	
	
	private static final String SCREENID =
		"customermanagement.defaults.maintainloyalty";
	private static final String OK_SUCCESS = "ok_success";
	private static final String OPERATION_FLAG_INS_DEL
	= "operation_flg_insert_delete";
	private static final String BLANK = "";
	

	/**
     * execute method
     * @param invocationContext
     * @throws CommandInvocationException
    */
    public void execute(InvocationContext invocationContext)throws 
    											CommandInvocationException {
    	log.entering("OKParameterValueCommand","execute");
	
		MaintainLoyaltyForm maintainLoyaltyForm = 
			(MaintainLoyaltyForm) invocationContext.screenModel;
		MaintainLoyaltySession maintainLoyaltySession = 
			(MaintainLoyaltySession)getScreenSession(MODULE,SCREENID);
		
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

      	    	
    	  	   									
     	
     	log.log(Log.FINE,
				"\n\n\n\n loyaltyParameterVOs AFETR ADD parameter ---> ",
				loyaltyParameterVOs);
		maintainLoyaltySession.setParameterVOsForLOV(loyaltyParameterVOs);
     	
     	LoyaltyProgrammeVO loyaltyProgrammeVOsMain=maintainLoyaltySession.getLoyaltyProgrammeVO();
     	ArrayList<LoyaltyParameterVO> loyaltyParameterVOsMain=new ArrayList<LoyaltyParameterVO>(loyaltyProgrammeVOsMain.getLoyaltyParameterVOs());
     	ArrayList<LoyaltyParameterVO> loyaltyParameterVOsToAdd=new ArrayList<LoyaltyParameterVO>();
     	log.log(Log.FINE, "\n\n\n\n loyaltyParameterVOsMain ---> ",
				loyaltyParameterVOsMain);
		ArrayList<LoyaltyParameterVO> loyaltyParameterVOstmp
		=new ArrayList<LoyaltyParameterVO>();
     	
     	for (LoyaltyParameterVO loyaltyParameterVOTmp :loyaltyParameterVOsMain) {
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
		loyaltyProgrammeVOsMain.setLoyaltyParameterVOs(loyaltyParameterVOstmp);
 		loyaltyParameterVOsMain=new ArrayList<LoyaltyParameterVO>(loyaltyProgrammeVOsMain.getLoyaltyParameterVOs());
 		 
 		 
     	for(LoyaltyParameterVO loyaltyparametervo:loyaltyParameterVOsMain)
		{
     		if(loyaltyparametervo.getOperationFlag()!=null && loyaltyparametervo.getOperationFlag().equals(AbstractVO.OPERATION_FLAG_INSERT)
     				&& BLANK.equals(loyaltyparametervo.getParameterValue()))
			{
     			loyaltyparametervo.setOperationFlag("INSERTAFTER");
			}
     		int size=loyaltyParameterVOs.size();
     		for(int i=0;i<size;i++){
			if((loyaltyparametervo.getOperationFlag()!=null && !loyaltyparametervo.getOperationFlag().equals(AbstractVO.OPERATION_FLAG_DELETE))
					|| loyaltyparametervo.getOperationFlag()==null)
			{
				log
						.log(
								Log.FINE,
								"\n\n\n\n maintainLoyaltyForm.getParameterInLOV() ---> ",
								maintainLoyaltyForm.getParameterInLOV());
				log.log(Log.FINE,
						"\n\n\n\n loyaltyparametervo.getParameterCode() ---> ",
						loyaltyparametervo.getParameterCode());
				log
						.log(
								Log.FINE,
								"\n\n\n\n loyaltyParameterVOs.get(i).getSequenceNumber() ---> ",
								loyaltyParameterVOs.get(i).getSequenceNumber());
				log
						.log(
								Log.FINE,
								"\n\n\n\n loyaltyparametervo.getSequenceNumber() ---> ",
								loyaltyparametervo.getSequenceNumber());
				if(loyaltyparametervo.getParameterCode().equals(maintainLoyaltyForm.getParameterInLOV())
						&& loyaltyparametervo.getSequenceNumber().equals(loyaltyParameterVOs.get(i).getSequenceNumber()))
				{
					log.log(Log.FINE, "\n\n\n\n INSIDE " );
					if(loyaltyParameterVOs.get(i).getOperationFlag()!=null && loyaltyParameterVOs.get(i).getOperationFlag().equals(AbstractVO.OPERATION_FLAG_DELETE))
					{
						log.log(Log.FINE, "\n\n\n\n OPERATION_FLAG_DELETE ---> " );
						loyaltyparametervo.setOperationFlag(AbstractVO.OPERATION_FLAG_DELETE);
					}
					if(loyaltyParameterVOs.get(i).getOperationFlag()!=null && loyaltyParameterVOs.get(i).getOperationFlag().equals(AbstractVO.OPERATION_FLAG_UPDATE))
					{
						log.log(Log.FINE, "\n\n\n\n OPERATION_FLAG_UPDATE ---> " );
						loyaltyparametervo.setParameterCode(maintainLoyaltyForm.getParameterInLOV());
						loyaltyparametervo.setParameterValue(loyaltyParameterVOs.get(i).getParameterValue());
						loyaltyparametervo.setOperationFlag(AbstractVO.OPERATION_FLAG_UPDATE);
					}
					if(loyaltyParameterVOs.get(i).getOperationFlag()!=null && loyaltyParameterVOs.get(i).getOperationFlag().equals(AbstractVO.OPERATION_FLAG_INSERT))
					{
						loyaltyParameterVOs.get(i).setOperationFlag("INSERTAFTER");
					}
					
				}
				
			}
			
		}}
     	
     	int voSize=loyaltyParameterVOs.size();
     	for(int i=0;i<voSize;i++){
     	if(loyaltyParameterVOs.get(i).getOperationFlag()!=null && loyaltyParameterVOs.get(i).getOperationFlag().equals(AbstractVO.OPERATION_FLAG_INSERT))
		{
			log.log(Log.FINE, "\n\n\n\n OPERATION_FLAG_INSERT ---> " );
			LoyaltyParameterVO loyaltyparametervonew=new LoyaltyParameterVO();
			loyaltyparametervonew.setLoyaltyProgrammeCode(loyaltyProgrammeVOsMain.getLoyaltyProgrammeCode());
			loyaltyparametervonew.setParameterCode(maintainLoyaltyForm.getParameterInLOV());
			loyaltyparametervonew.setParameterValue(loyaltyParameterVOs.get(i).getParameterValue());
			loyaltyparametervonew.setSequenceNumber(String.valueOf(populateSequence(loyaltyParameterVOsMain)));
			loyaltyparametervonew.setOperationFlag(AbstractVO.OPERATION_FLAG_INSERT);
			loyaltyParameterVOsToAdd.add(loyaltyparametervonew);
		}
     	}
     	int vos=loyaltyParameterVOs.size();
     	for(int i=0;i<vos;i++){
         	if("INSERTAFTER".equals(loyaltyParameterVOs.get(i).getOperationFlag()))
    		{
         		loyaltyParameterVOs.get(i).setOperationFlag(AbstractVO.OPERATION_FLAG_INSERT);
    		}
    		}
     	
     	loyaltyParameterVOsMain.addAll(loyaltyParameterVOsToAdd);
     	ArrayList<LoyaltyParameterVO> loyaltyParameterVOsFinalMain=new ArrayList<LoyaltyParameterVO>();
     	for(LoyaltyParameterVO loyaltyParameterVO:loyaltyParameterVOsMain)
     	{
     		if((!"INSERTAFTER".equals(loyaltyParameterVO.getOperationFlag()))
     				|| loyaltyParameterVO.getOperationFlag()==null )
    		{
     			loyaltyParameterVOsFinalMain.add(loyaltyParameterVO);
     		}
     		
     	}
     	log.log(Log.FINE, "\n\n\n\n loyaltyParameterVOsFinalMain ---> ",
				loyaltyParameterVOsFinalMain);
		loyaltyProgrammeVOsMain.setLoyaltyParameterVOs(loyaltyParameterVOsFinalMain);
     	maintainLoyaltySession.setLoyaltyProgrammeVO(loyaltyProgrammeVOsMain);
     	
     	// FOR UPDATING DISPLAY
     	
     	ArrayList<String> parameters=new ArrayList<String>();
		ArrayList<String> values=new ArrayList<String>();
		
		ArrayList<String> opFlag=new ArrayList<String>();
		
		
		if(loyaltyParameterVOsFinalMain!=null && loyaltyParameterVOsFinalMain.size()>0)
		{
		for(LoyaltyParameterVO loyaltyParameterVO : 
			loyaltyParameterVOsFinalMain){
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
			loyaltyParameterVOsFinalMain){
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
		
		maintainLoyaltyForm.setCloseWindow(true);
		
		invocationContext.target =  OK_SUCCESS;
		
    	
     }
    
    /**
     * 
     * @param loyaltyParameterVOs
     * @return
     */
    public int populateSequence(ArrayList<LoyaltyParameterVO> 
    					loyaltyParameterVOs) {
		log.entering("OKParameterValueCommand", "populateSequence");
		int dmgseq=0;
		for(LoyaltyParameterVO loyaltyParameterVO:loyaltyParameterVOs)
		{
		if(Integer.parseInt(loyaltyParameterVO.getSequenceNumber())>dmgseq)
		{
		dmgseq=Integer.parseInt(loyaltyParameterVO.getSequenceNumber());
		}
		}
		dmgseq=dmgseq+1;
		log.exiting("OKParameterValueCommand", "populateSequence");
		return dmgseq;
	
	}
}
