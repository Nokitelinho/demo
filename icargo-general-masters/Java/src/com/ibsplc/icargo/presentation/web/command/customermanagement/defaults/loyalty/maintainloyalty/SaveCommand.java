/*
 * SaveCommand.java Created on Apr 11,2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services(P)Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.customermanagement.defaults.loyalty.maintainloyalty;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import com.ibsplc.icargo.business.customermanagement.defaults.loyalty.vo.LoyaltyAttributeVO;
import com.ibsplc.icargo.business.customermanagement.defaults.loyalty.vo.LoyaltyParameterVO;
import com.ibsplc.icargo.business.customermanagement.defaults.loyalty.vo.LoyaltyProgrammeVO;
import com.ibsplc.icargo.business.customermanagement.defaults.loyalty.vo.ParameterDescriptionVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.customermanagement.defaults.CustomerMgmntDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.customermanagement.defaults.loyalty.MaintainLoyaltySession;
import com.ibsplc.icargo.presentation.web.struts.form.customermanagement.defaults.loyalty.MaintainLoyaltyForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;


/**
 * 
 * @author A-1862
 */
public class SaveCommand extends BaseCommand {
	
	
	private Log log = LogFactory.getLogger("Maintain Loyalty");
	/**
	 * The Module Name
	 */
	private static final String MODULE = "customermanagement.defaults";
	
	private static final String SCREENID =
		"customermanagement.defaults.maintainloyalty";
	private static final String SAVE_SUCCESS = "save_success";
	private static final String SAVEFROM_LIST_SUCCESS = "savefrom_list_success";
	private static final String SAVE_FAILURE = "save_failure";
	/**
	 * PARAMETER_ALREADY_EXISTS
	 */
	public static final String PARAMETER_ALREADY_EXISTS = 
		"customermanagement.defaults.parameterAlreadyExists";
	/**
	 * CUSTOMERS_ALREADY_ATTACHED
	 */
	public static final String CUSTOMERS_ALREADY_ATTACHED = 
		"customermanagement.defaults.customersAlreadyAttached";
	private static final String BLANK = "";

	/**
     * execute method
     * @param invocationContext
     * @throws CommandInvocationException
    */
    public void execute(InvocationContext invocationContext)throws 
    											CommandInvocationException {
    	log.entering("SaveCommand","execute");
    
    	ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		
		MaintainLoyaltyForm maintainLoyaltyForm = 
			(MaintainLoyaltyForm) invocationContext.screenModel;
		MaintainLoyaltySession maintainLoyaltySession = 
			(MaintainLoyaltySession)getScreenSession(MODULE,SCREENID);
		maintainLoyaltyForm.setCloseWindow(false);
		LoyaltyProgrammeVO loyaltyProgrammeVO=new LoyaltyProgrammeVO();
		
		loyaltyProgrammeVO=maintainLoyaltySession.getLoyaltyProgrammeVO();
    	
		log
				.log(
						Log.FINE,
						"\n\n\n\n loyaltyProgrammeVO BEFORE SAVE ********************* ---> ",
						loyaltyProgrammeVO);
		if(maintainLoyaltyForm.getLoyaltyDesc()!=null &&
    			maintainLoyaltyForm.getLoyaltyDesc().length()!=0 )
    	{
    		loyaltyProgrammeVO.setLoyaltyProgrammeDesc(maintainLoyaltyForm.getLoyaltyDesc().toUpperCase());
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
	    						log
										.log(
												Log.FINE,
												"\n\n\n\n loyaltyParameterVOTmp.getParameterCode() ---> ",
												loyaltyParameterVOTmp.getParameterCode());
								log
										.log(
												Log.FINE,
												"\n\n\n\n loyaltyParameterVO.getParameterCode() ---> ",
												loyaltyParameterVO.getParameterCode());
								if(loyaltyParameterVOTmp.getParameterCode().equalsIgnoreCase(loyaltyParameterVO.getParameterCode()))
	    						{
	    							log
											.log(
													Log.FINE,
													"\n\n\n\n loyaltyParameterVOTmp.isChanged() ---> ",
													loyaltyParameterVOTmp.isChanged());
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
			log
					.log(
							Log.FINE,
							"\n\n\n\n maintainLoyaltySession.getLoyaltyProgrammeVO() ---> ",
							maintainLoyaltySession.getLoyaltyProgrammeVO());
			loyaltyProgrammeVO=maintainLoyaltySession.getLoyaltyProgrammeVO();
	    	
	    	
     	
     	   if(loyaltyProgrammeVO.getFromDate().after(loyaltyProgrammeVO.getToDate()))
     	   {
     		  ErrorVO error = null;
     		  error = new ErrorVO(
				 "customermgmt.defaults.maintainloyalty.msg.err.fromdategreater");
     		  error.setErrorDisplayType(ErrorDisplayType.ERROR);
     		  invocationContext.addError(error);
     		  invocationContext.target =  SAVE_FAILURE;
              return;
     		  
     	   }
     	
     	
     	log.log(Log.FINE,
				"\n\n\n\n *maintainLoyaltyForm.isDateChanged()* ---> ",
				maintainLoyaltyForm.isDateChanged());
		if(maintainLoyaltyForm.isDateChanged()){
     		loyaltyProgrammeVO.setDateChanged(true);
     	}
     	else{
     		loyaltyProgrammeVO.setDateChanged(false);
     	}
     	if(loyaltyProgrammeVO.getLoyaltyParameterVOs()!=null && loyaltyProgrammeVO.getLoyaltyParameterVOs().size()>0){
     	
     	ArrayList<LoyaltyParameterVO> loyaltyParameterVOsMain=new ArrayList<LoyaltyParameterVO>(loyaltyProgrammeVO.getLoyaltyParameterVOs());
     	ArrayList<ParameterDescriptionVO> parameterDescriptionVOsMain=new ArrayList<ParameterDescriptionVO>(maintainLoyaltySession.getParameter());
     	String checkFlag="";
     	String desc="";
     	for(LoyaltyParameterVO loyaltyParameterVO:loyaltyParameterVOsMain)
     	{
     		loyaltyParameterVO.setCompanyCode(loyaltyProgrammeVO.getCompanyCode());
     		for(ParameterDescriptionVO parameterDescriptionVO:parameterDescriptionVOsMain){
     		if(loyaltyParameterVO.getParameterCode().equals(parameterDescriptionVO.getParameter())){
     			log
						.log(
								Log.FINE,
								"\n\n\n\n parameterDescriptionVO.getCheckNumberField() ---> ",
								parameterDescriptionVO.getCheckNumberField());
				checkFlag=parameterDescriptionVO.getCheckNumberField();	
     			desc=parameterDescriptionVO.getParameterDesc();
     			break;
     		}
     		}
     		log.log(Log.FINE, "\n\n\n\n checkFlag ---> ", checkFlag);
			if(AbstractVO.FLAG_YES.equalsIgnoreCase(checkFlag)){
     			try{
     			double i=Double.parseDouble(loyaltyParameterVO.getParameterValue());
     			}
     			catch (NumberFormatException businessDelegateException) {     				
//printStackTrrace()();  
    				 ErrorVO error = null;
    				 
    	     		  error = new ErrorVO(
    					 "customermgmt.defaults.maintainloyalty.msg.err.incorrectformat",new Object[] {desc});
    	     		  error.setErrorDisplayType(ErrorDisplayType.ERROR);
    	     		  invocationContext.addError(error);
    	     		  invocationContext.target =  SAVE_FAILURE;
    	              return;
    				
    			}
     		}
     	}
     	}
     	log
				.log(
						Log.FINE,
						"\n\n\n\n ********loyaltyProgrammeVO FOR SAVE before setting unit********* ---> ",
						loyaltyProgrammeVO);
		Map<String, Collection<LoyaltyAttributeVO>> attributeValues = maintainLoyaltySession.getAttributeValues();
     	String actualUnit="";
     	for(String loy:attributeValues.keySet()){
     		if(loy.equals(loyaltyProgrammeVO.getAttibute())){
			
			Collection<LoyaltyAttributeVO> laVO=attributeValues.get(loy);
			for(LoyaltyAttributeVO loyaltyAttributeVO:laVO){
				if(loyaltyAttributeVO.getUnitDescription().equals(loyaltyProgrammeVO.getUnits()))
				{
					actualUnit=loyaltyAttributeVO.getUnit();
				}
			}
     		}
		}
     	log.log(Log.FINE, "\n\n\n\n actualUnit*** ---> ", actualUnit);
		loyaltyProgrammeVO.setUnits(actualUnit);
     	
     	log
				.log(
						Log.FINE,
						"\n\n\n\n ***************loyaltyProgrammeVO FOR SAVE ********* ---> ",
						loyaltyProgrammeVO);
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
     	 try {
     		new CustomerMgmntDefaultsDelegate().createLoyaltyProgramme(loyaltyProgrammeVO);
			} catch (BusinessDelegateException businessDelegateException) {
			
//printStackTrrace()();
//printStackTrrace()();
				errors = handleDelegateException(businessDelegateException);
			}
			Collection<ErrorVO> errorVOs = new ArrayList<ErrorVO>();
			
		if(errors != null && errors.size() > 0){
			log.log(Log.FINE, "<------errors.size()------->", errors.size());
			log.log(Log.FINE, "<------errors------->", errors);
				for(ErrorVO errorVO : errors) {
					log.log(Log.FINE, "ErrorVO from exception is", errorVO.getErrorCode().toString());
					if ((errorVO.getErrorCode().toString()
													.equals(PARAMETER_ALREADY_EXISTS)))
							{
						log.log(Log.FINE,"<------Failure Response From Server PARAMETER_ALREADY_EXISTS------->");
						log.log(Log.FINE,
								"<------errorVO.getErrorData().length------->",
								errorVO.getErrorData());
						log.log(Log.FINE,
								"<------errorVO.getErrorData().length------->",
								errorVO.getErrorData().length);
							errorVOs.add(errorVO);
						
							}
					if ((errorVO.getErrorCode().toString()
							.equals(CUSTOMERS_ALREADY_ATTACHED)))
							{
						log.log(Log.FINE,"<------Failure Response From Server CUSTOMERS_ALREADY_ATTACHED------->");
							
							errorVOs.add(errorVO);
						
							}
					
				}	
				log.log(Log.FINE, "<------errorVOs.size()------->", errorVOs.size());
			if ( errorVOs != null && errorVOs.size() > 0 ){
	  			invocationContext.addAllError(errorVOs);
	  			invocationContext.target=SAVE_FAILURE;
	  			return ;
	  	} }else{
	  		ErrorVO error = new ErrorVO("customermanagement.loyalty.loyaltyprogramsavedsuccessfully");
	  		
	  		errorVOs.add(error);
	  		log.log(Log.INFO, "%%%%%%%%%   errorVOs", errorVOs);
			maintainLoyaltyForm.setLoyaltyName("");
			maintainLoyaltyForm.setLoyaltyDesc("");
			maintainLoyaltyForm.setEntryPoints("");
			maintainLoyaltyForm.setFromDate("");
			maintainLoyaltyForm.setToDate("");
			maintainLoyaltyForm.setExpiryPeriod("");
			maintainLoyaltyForm.setExpiryPeriodValue("");
			maintainLoyaltyForm.setStatus("");
			maintainLoyaltyForm.setAttribute("");
			maintainLoyaltyForm.setUnit("");
			maintainLoyaltyForm.setAmount("");
			maintainLoyaltyForm.setPoints("");
	  		
	  		/*maintainLoyaltyForm.setAmount("");
	  		maintainLoyaltyForm.setDescription("");
	  		maintainLoyaltyForm.setEntryPoints("");
	  		maintainLoyaltyForm.setExpiryPeriod("");
	  		maintainLoyaltyForm.setFromDate("");
	  		maintainLoyaltyForm.setLoyaltyName("");
	  		maintainLoyaltyForm.setLoyaltyDesc("");
	  		maintainLoyaltyForm.setLoyaltyName("");
	  		maintainLoyaltyForm.setToDate("");
	  		*/
	  		/*
	  		maintainLoyaltySession.removeAllAttributes();
	  		maintainLoyaltySession.removeParentScreenId();
	  		*/
			maintainLoyaltySession.setLoyaltyProgrammeVO(null);
			maintainLoyaltySession.setLoyaltyProgrammeLovVOs(null);	
	    	//maintainLoyaltyForm.setScreenStatusFlag("SCREENLOAD");
			maintainLoyaltyForm.setScreenStatusValue("SCREENLOAD");
	    	maintainLoyaltySession.setAttributeValue(null);
			maintainLoyaltySession.setUnitValue(null);
			maintainLoyaltySession.setAmountValue(null);
			maintainLoyaltySession.setPointsValue(null);
			maintainLoyaltySession.setParameterVOsForDisplay(null);
			maintainLoyaltySession.setParameterVOsForLOV(null);
			maintainLoyaltyForm.setCloseWindow(false);
			
		  	invocationContext.addAllError(errorVOs);
		  	invocationContext.target=SAVE_SUCCESS;
		  	return ;
	  	}
		
			
			
     	
     	log.log(Log.FINE, "maintainLoyaltySession.getPageURL()",
				maintainLoyaltySession.getPageURL());
		maintainLoyaltyForm.setSaved(true);
        if(maintainLoyaltySession.getPageURL()!=null){
        	
       	 	invocationContext.target=SAVEFROM_LIST_SUCCESS;
       	 	return;
        }
        else{
       	 	invocationContext.target =  SAVE_SUCCESS;
            return;
            }
		
    	
     }
}
