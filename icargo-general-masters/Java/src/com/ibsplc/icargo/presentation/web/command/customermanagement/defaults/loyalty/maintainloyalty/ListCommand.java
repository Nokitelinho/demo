/*
 * ListCommand.java Created on Apr 12, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.presentation.web.command.customermanagement.defaults.loyalty.maintainloyalty;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import com.ibsplc.icargo.business.customermanagement.defaults.loyalty.vo.LoyaltyAttributeVO;
import com.ibsplc.icargo.business.customermanagement.defaults.loyalty.vo.LoyaltyParameterVO;
import com.ibsplc.icargo.business.customermanagement.defaults.loyalty.vo.LoyaltyProgrammeFilterVO;
import com.ibsplc.icargo.business.customermanagement.defaults.loyalty.vo.LoyaltyProgrammeVO;
import com.ibsplc.icargo.business.customermanagement.defaults.loyalty.vo.ParameterDescriptionVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.customermanagement.defaults.CustomerMgmntDefaultsDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.customermanagement.defaults.loyalty.MaintainLoyaltySession;
import com.ibsplc.icargo.presentation.web.struts.form.customermanagement.defaults.loyalty.MaintainLoyaltyForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * This command class is used to list the Maintain Loyalty
 * @author A-1862
 */
public class ListCommand extends BaseCommand {
	
	
	private Log log = LogFactory.getLogger("Maintain Loyalty");
	
	private static final String MODULE = "customermanagement.defaults";
	
	
	private static final String SCREENID =
		"customermanagement.defaults.maintainloyalty";
    
    private static final String LIST_SUCCESS = "list_success";
    private static final String LIST_FAILURE = "list_failure";
    
    private static final String EXPIRYPERIOD_ONETIME = "customer.addloyaltyparams.periodforredumption";
    private static final String STATUS_ONETIME = "customer.addloyaltyparams.activestatus";
    private static final String STATUS_CREATE = "CREATE";
    private static final String PARAMETERS_ONETIME = "customer.addloyaltyparams.parameter";

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
		String  compCode = logonAttributes.getCompanyCode();
		
		MaintainLoyaltyForm maintainLoyaltyForm = 
			(MaintainLoyaltyForm) invocationContext.screenModel;
		MaintainLoyaltySession maintainLoyaltySession = 
			(MaintainLoyaltySession)getScreenSession(MODULE,SCREENID);
		maintainLoyaltyForm.setCloseWindow(false);
		
		
		//populate the one times
		
		SharedDefaultsDelegate sharedDefaultsDelegate =
			new SharedDefaultsDelegate();
		Map<String, Collection<OneTimeVO>> oneTimeValues = null;
		
		try {
		oneTimeValues =	sharedDefaultsDelegate.findOneTimeValues(
		compCode, getOneTimeParameterTypes());
		} catch (BusinessDelegateException e) {
//printStackTrrace()();
		handleDelegateException( e);
		}
		maintainLoyaltySession.setOneTimeValues(
		(HashMap<String, Collection<OneTimeVO>>)oneTimeValues);
		
		CustomerMgmntDefaultsDelegate customerMgmntDefaultsDel =
			new CustomerMgmntDefaultsDelegate();
		Map<String, Collection<LoyaltyAttributeVO>> attributeValues = null;
		LinkedHashMap<String, Collection<String>> att=new LinkedHashMap<String, Collection<String>>();
		
		try {
			attributeValues =	customerMgmntDefaultsDel.listLoyaltyAttributeDetails(compCode);
		} catch (BusinessDelegateException e) {
//printStackTrrace()();
		handleDelegateException( e);
		}
		
		maintainLoyaltySession.setAttributeValues(
		(HashMap<String, Collection<LoyaltyAttributeVO>>)attributeValues);
		
		for(String loy:attributeValues.keySet()){
			Collection<String> string=new ArrayList<String>();
			Collection<LoyaltyAttributeVO> laVO=attributeValues.get(loy);
			for(LoyaltyAttributeVO loyaltyAttributeVO:laVO){
				
				string.add(loyaltyAttributeVO.getUnitDescription());
			}
			att.put(loy,string);
		}
		maintainLoyaltySession.setAttributeString(
				(HashMap<String, Collection<String>>)att);
		
		Collection<ParameterDescriptionVO> parameter = findParameters(compCode);
		maintainLoyaltySession.setParameter((ArrayList<ParameterDescriptionVO>)parameter);
		
		//after populating the one times
		 	    	
		log.log(Log.FINE, "maintainLoyaltySession.getPageURL()",
				maintainLoyaltySession.getPageURL());
		log.log(Log.FINE, "maintainLoyaltyForm.isNavigate()",
				maintainLoyaltyForm.isNavigate());
		log.log(Log.FINE, "maintainLoyaltyForm.isSaved()", maintainLoyaltyForm.isSaved());
		if
    		(
        			(maintainLoyaltySession.getPageURL()!=null && maintainLoyaltyForm.isSaved()) ||
        			(maintainLoyaltySession.getPageURL()!=null && !maintainLoyaltyForm.isNavigate())
    	      )
    	      {	
    		ArrayList<String> loyaltyNames = maintainLoyaltySession.getLoyaltyNames();
        	
    	 log.log(Log.FINE, "Current Page--------------->", Integer.parseInt(maintainLoyaltyForm.getDisplayPage()));
		String loyaltyName = loyaltyNames.get(Integer.parseInt(maintainLoyaltyForm.getDisplayPage())-1);
     	
     	 log.log(Log.FINE, "loyaltyName--------------->", loyaltyName);
		maintainLoyaltyForm.setLoyaltyName(loyaltyName);
    	
     	maintainLoyaltyForm.setPageURL(maintainLoyaltySession.getPageURL());
     	maintainLoyaltyForm.setSaved(false);
    	
    	}
    	maintainLoyaltyForm.setNavigate(false);
    	
    		
    	
		/**
		 * Validate for client errors
		 */
		Collection<ErrorVO> errors = null;
		if(!(STATUS_CREATE.equals(maintainLoyaltyForm.getStatusNew()))){
		errors = validateForm
					(maintainLoyaltyForm);
		
		if(errors!=null && errors.size() > 0 ) {
			maintainLoyaltyForm.setScreenStatusFlag(
					ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
			maintainLoyaltyForm.setScreenStatusValue("SCREENLOAD");
			invocationContext.addAllError(errors);
			invocationContext.target = LIST_FAILURE;
			return;
		}
		}
		LoyaltyProgrammeFilterVO loyaltyProgrammeFilterVO=new LoyaltyProgrammeFilterVO();
		loyaltyProgrammeFilterVO.setCompanyCode(logonAttributes.getCompanyCode());
		loyaltyProgrammeFilterVO.setLoyaltyProgrammeCode(maintainLoyaltyForm.getLoyaltyName().toUpperCase());
		CustomerMgmntDefaultsDelegate customerMgmntDefaultsDelegate=new CustomerMgmntDefaultsDelegate();
		
		try {
			ArrayList<LoyaltyProgrammeVO> loyaltyProgrammeVOs=new ArrayList<LoyaltyProgrammeVO>();
			Collection<LoyaltyProgrammeVO> loyaltyProgrammevos=(customerMgmntDefaultsDelegate.listLoyaltyProgramme(loyaltyProgrammeFilterVO));
			if(loyaltyProgrammevos!=null){
				loyaltyProgrammeVOs=(ArrayList<LoyaltyProgrammeVO>)loyaltyProgrammevos;
			}
				log.log(Log.FINE, "loyaltyProgrammeVOs  AFTER LIST ---> ",
						loyaltyProgrammeVOs);
				if(loyaltyProgrammeVOs==null || loyaltyProgrammeVOs.size()==0)
				{
					if(!(STATUS_CREATE.equals(maintainLoyaltyForm.getStatusNew()))){
						maintainLoyaltyForm.setStatusNew(STATUS_CREATE);
						ErrorVO error = new ErrorVO(
						 "customermgmt.defaults.maintainloyalty.msg.wrn.newloyalty");
						 error.setErrorDisplayType(ErrorDisplayType.WARNING);
						 errors.add(error);
						 invocationContext.addAllError(errors);
						invocationContext.target = LIST_FAILURE;
						return;
					}
					log.log(Log.FINE, "statusnew ---> ", maintainLoyaltyForm.getStatusNew());
					if(STATUS_CREATE.equals(maintainLoyaltyForm.getStatusNew())){
						LoyaltyProgrammeVO loyaltyProgrammeVO=new LoyaltyProgrammeVO();
						
						loyaltyProgrammeVO.setActiveStatus("A");					
						loyaltyProgrammeVO.setOperationFlag(AbstractVO.OPERATION_FLAG_INSERT);
						loyaltyProgrammeVO.setCompanyCode(logonAttributes.getCompanyCode());
						loyaltyProgrammeVO.setLastUpdatedUser(logonAttributes.getUserId());
						loyaltyProgrammeVO.setLoyaltyProgrammeCode(maintainLoyaltyForm.getLoyaltyName().toUpperCase());
						log
								.log(
										Log.FINE,
										"loyaltyProgrammeVO  AFTER LIST IN INSERT MODE---> ",
										loyaltyProgrammeVO);
						//maintainLoyaltyForm.setScreenStatusFlag("INSERT_MODE");
						maintainLoyaltyForm.setScreenStatusValue("INSERT_MODE");
						maintainLoyaltyForm.setStatusNew(STATUS_CREATE);
						
						
						maintainLoyaltySession.setAttributeValue(null);
						maintainLoyaltySession.setUnitValue(null);
						maintainLoyaltySession.setAmountValue(null);
						maintainLoyaltySession.setPointsValue(null);
						
						maintainLoyaltySession.setLoyaltyProgrammeVO(loyaltyProgrammeVO);
					}
					
					
					
				}else
				{
					maintainLoyaltyForm.setScreenStatusFlag(
							ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
					
					LoyaltyProgrammeVO loyaltyProgrammeVO=loyaltyProgrammeVOs.get(0);
					
					loyaltyProgrammeVO.setOperationFlag(AbstractVO.OPERATION_FLAG_UPDATE);
					
					
					// to update attribute unit
					Map<String, Collection<LoyaltyAttributeVO>> AttributeValues = maintainLoyaltySession.getAttributeValues();
			     	String actualUnitDesc="";
			     	for(String loy:AttributeValues.keySet()){
			     		if(loy.equals(loyaltyProgrammeVO.getAttibute())){
						
						Collection<LoyaltyAttributeVO> laVO=AttributeValues.get(loy);
						for(LoyaltyAttributeVO loyaltyAttributeVO:laVO){
							if(loyaltyAttributeVO.getUnit().equals(loyaltyProgrammeVO.getUnits()))
							{
								actualUnitDesc=loyaltyAttributeVO.getUnitDescription();
							}
						}
			     		}
					}
			     	log.log(Log.FINE, "\n\n\n\n actualUnitDesc*** ---> ",
							actualUnitDesc);
					loyaltyProgrammeVO.setUnits(actualUnitDesc);
			     	log.log(Log.FINE, "\n\n\n\n after setting units*** ---> ",
							loyaltyProgrammeVO);
					maintainLoyaltySession.setLoyaltyProgrammeVO(loyaltyProgrammeVO);
			     	
			     	
					ArrayList<String> parameters=new ArrayList<String>();
					ArrayList<String> opFlag=new ArrayList<String>();
					ArrayList<String> values=new ArrayList<String>();
					
					
					
					
					if(loyaltyProgrammeVO.getLoyaltyParameterVOs()!=null && loyaltyProgrammeVO.getLoyaltyParameterVOs().size()>0)
					{
					for(LoyaltyParameterVO loyaltyParameterVO : 
						loyaltyProgrammeVO.getLoyaltyParameterVOs()){
						loyaltyParameterVO.setLoyaltyProgrammeCode(loyaltyProgrammeVO.getLoyaltyProgrammeCode());
						loyaltyParameterVO.setCompanyCode(logonAttributes.getCompanyCode());
						
					if(!parameters.contains(loyaltyParameterVO.getParameterCode()))
					{
						parameters.add(loyaltyParameterVO.getParameterCode());
						
					}
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
									|| (loyaltyParameterVO.getOperationFlag()!=null &&
											loyaltyParameterVO.getOperationFlag().equals(AbstractVO.OPERATION_FLAG_DELETE))
											)
							{
								isNull=true;
							}
							str.append(loyaltyParameterVO.getParameterValue()).append(",");
							
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
						
						loyaltyParameterVOTmp.setOperationFlag(opFlag.get(i));
						loyaltyParameterVOTmp.setParameterCode(parameters.get(i));
						loyaltyParameterVOTmp.setParameterValue(values.get(i));
						loyaltyParameterVOsTmp.add(loyaltyParameterVOTmp);
					}
					
					log
							.log(
									Log.FINE,
									"ParameterVOsForDisplay AFTER LIST IN MODIFY MODE---> ",
									loyaltyParameterVOsTmp);
					maintainLoyaltySession.setParameterVOsForDisplay(loyaltyParameterVOsTmp);
					}}
					log
							.log(
									Log.FINE,
									"loyaltyProgrammeVO  AFTER LIST IN MODIFY MODE---> ",
									loyaltyProgrammeVO);
					maintainLoyaltyForm.setScreenStatusValue("MODIFY_MODE");
					
					maintainLoyaltySession.setAttributeValue(loyaltyProgrammeVO.getAttibute());
					maintainLoyaltySession.setUnitValue(loyaltyProgrammeVO.getUnits());
					maintainLoyaltySession.setAmountValue(String.valueOf(loyaltyProgrammeVO.getAmount()));
					maintainLoyaltySession.setPointsValue(String.valueOf(loyaltyProgrammeVO.getPoints()));
					
					maintainLoyaltySession.setLoyaltyProgrammeVO(loyaltyProgrammeVO);

				}
		}
		catch (BusinessDelegateException businessDelegateException) {
//printStackTrrace()();
				handleDelegateException( businessDelegateException);
		}
    
			        
			
		
		
		    	
		
		
	
		invocationContext.target = LIST_SUCCESS;
        
    }
    /**
	 * @param maintainLoyaltyForm
	 * @param companyCode 
	 * @return errors
	 */
    
	private Collection<ErrorVO> validateForm
	(MaintainLoyaltyForm maintainLoyaltyForm){
		log.entering("ListLoyaltyCommand", "validateForm");
		
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		ErrorVO error = null;
		if(maintainLoyaltyForm.getLoyaltyName()== null || 
				maintainLoyaltyForm.getLoyaltyName().trim().length() == 0){
			 error = new ErrorVO(
					 "customermgmt.defaults.maintainloyalty.msg.err.loyaltynamemandatory");
			 error.setErrorDisplayType(ErrorDisplayType.ERROR);
			errors.add(error);
		}
		
		
		log.exiting("ListLoyaltyCommand", "validateForm");
		return errors;
	}
	
	/**
	 * Method to populate the collection of
	 * onetime parameters to be obtained
     * @return parameterTypes
     */
    private Collection<String> getOneTimeParameterTypes() {
    	log.entering("ScreenLoadCommand","getOneTimeParameterTypes");
    	ArrayList<String> parameterTypes = new ArrayList<String>();
    	
    	parameterTypes.add(EXPIRYPERIOD_ONETIME);
    	parameterTypes.add(STATUS_ONETIME);
    	parameterTypes.add(PARAMETERS_ONETIME);
    	
    	    	
    	
    	log.exiting("ScreenLoadCommand","getOneTimeParameterTypes");
    	return parameterTypes;    	
    }

    /**
	 * 
	 * @param companyCode
	 * @return
	 */
	private Collection<ParameterDescriptionVO> findParameters(String companyCode){
		CustomerMgmntDefaultsDelegate customerMgmntDefaultsDelegate =
			new CustomerMgmntDefaultsDelegate();
		Collection<ParameterDescriptionVO> param = new ArrayList<ParameterDescriptionVO>();
		try{
			param = customerMgmntDefaultsDelegate.findParameterDetails(companyCode);
		}catch(BusinessDelegateException exception){
//printStackTrrace()();
			handleDelegateException(exception);
		}
		return param;
	}
	
}
