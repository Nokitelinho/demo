/*
 * ListDamageReportCommand.java Created on Jul 12, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P)Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.misc.maintaindamagereport;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.ibsplc.icargo.business.shared.currency.vo.CurrencyVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.business.uld.defaults.message.vo.ULDFlightMessageReconcileDetailsVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDDamageFilterVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDDamageRepairDetailsVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDDamageVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDRepairVO;
import com.ibsplc.icargo.business.uld.defaults.vo.ULDVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.currency.CurrencyDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.delegate.uld.defaults.ULDDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.misc.MaintainDamageReportSession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.misc.MaintainDamageReportForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-1862
 *
 */

public class ListDamageReportCommand extends BaseCommand {
	
	private Log log = LogFactory.getLogger("LIST Damage Report");
	
	private static final String MODULE = "uld.defaults";

	/**
	 * Screen Id of maintain damage report screen
	 */
	private static final String SCREENID =
		"uld.defaults.maintaindamagereport";
    
	private static final String LIST_SUCCESS = "list_success";
	private static final String LIST_FAILURE = "list_failure";
	private static final String DAMAGESTATUS_ONETIME = "uld.defaults.damageStatus";
	private static final String OVERALLSTATUS_ONETIME = "uld.defaults.operationalStatus";
	private static final String REPAIRSTATUS_ONETIME = "uld.defaults.repairStatus";
	private static final String DAMAGECODE_ONETIME = 
    									"uld.defaults.damagecode";
	private static final String POSITION_ONETIME = "uld.defaults.position";
	private static final String SEVERITY_ONETIME = "uld.defaults.damageseverity";
	private static final String REPAIRHEAD_ONETIME = 
    									"uld.defaults.repairhead";
	private static final String FACILITYTYPE_ONETIME = "uld.defaults.facilitytypes";
	private static final String PARTYTYPE_ONETIME = "uld.defaults.PartyType";
	private static final String SECTION_ONETIME="uld.defaults.section";
    

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
		
		MaintainDamageReportForm maintainDamageReportForm = 
			(MaintainDamageReportForm) invocationContext.screenModel;
		MaintainDamageReportSession maintainDamageReportSession = 
			(MaintainDamageReportSession)getScreenSession(MODULE,SCREENID);
		maintainDamageReportForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
		SharedDefaultsDelegate sharedDefaultsDelegate =
			new SharedDefaultsDelegate();
		Map<String, Collection<OneTimeVO>> oneTimeValues = null;
		Collection<String> systemparameterCodes = new  ArrayList<String>();
		Map<String,String> map = new HashMap<String,String>();
		systemparameterCodes.add(ULDVO.NON_OPERATIONAL_DAMAGE_STATUS);
		try {
			map = sharedDefaultsDelegate.findSystemParameterByCodes(systemparameterCodes);
		} catch (BusinessDelegateException businessDelegateException) {
			log.log(Log.SEVERE, "System Parameter for Damage Status not found ");
		} 
		if(map!=null){
			maintainDamageReportSession.setNonOperationalDamageCodes(map.get(ULDVO.NON_OPERATIONAL_DAMAGE_STATUS));
		}
		Collection<ErrorVO> exception = new ArrayList<ErrorVO>();
		try {
		oneTimeValues =	sharedDefaultsDelegate.findOneTimeValues(
		compCode, getOneTimeParameterTypes());
		log.log(Log.FINE, "The One time values frm List Command ",
				oneTimeValues.get(OVERALLSTATUS_ONETIME));
		} catch (BusinessDelegateException e) {
		e.getMessage();
		exception = handleDelegateException(e);
		}
		maintainDamageReportSession.setOneTimeValues(
		(HashMap<String, Collection<OneTimeVO>>)oneTimeValues);
		populateCurrency(maintainDamageReportSession);
        log
				.log(Log.FINE, "\n xxxxxx", maintainDamageReportForm.getAllChecked());
		log.log(Log.FINE, "\n xxxxxx-------->>>", maintainDamageReportForm.getScreenReloadStatus());
		maintainDamageReportForm.setPicturePresent("false");
		
		log.log(Log.FINE, "statusFlag ---> ", maintainDamageReportForm.getStatusFlag());
		if("uld_def_add_dmg_success".equals(maintainDamageReportForm.getStatusFlag())||"uld_def_update_dmg_success".equals(maintainDamageReportForm.getStatusFlag())){
			maintainDamageReportForm.setScreenReloadStatus("reload");//added by A-5844 for ICRD-124065
		}
	//added by nisha for bug fix on 03 Mar 08
      if("uld_def_delete_dmg_success".equals(maintainDamageReportForm.getStatusFlag())){
    	  if(!"".equals(maintainDamageReportForm.getAllChecked()) && ("Y").equals(maintainDamageReportForm.getAllChecked()))
			{log.log(Log.FINE, "-------------------->>",
					maintainDamageReportForm.getAllChecked());
			maintainDamageReportForm.setRepairStatus("R");
			}
      }
		if(("uld_def_add_dmg_success").equals(maintainDamageReportForm.getStatusFlag()) ||
				("uld_def_update_dmg_success").equals(maintainDamageReportForm.getStatusFlag())||
				("uld_def_add_rep_success").equals(maintainDamageReportForm.getStatusFlag()) ||
				("uld_def_update_rep_success").equals(maintainDamageReportForm.getStatusFlag())||
				("uld_def_delete_rep_success").equals(maintainDamageReportForm.getStatusFlag())){
			log.log(Log.FINE, "maintainDamageReportForm.getStatusFlag()---> ",
					maintainDamageReportForm.getStatusFlag());
			//added by a-3045 for bug 17989 starts
			ULDDamageRepairDetailsVO uldDmgRprDetailsVO = maintainDamageReportSession.getULDDamageVO();
			Collection<ULDDamageVO> uldDmgVOs = uldDmgRprDetailsVO.getUldDamageVOs();
			Collection<ULDDamageVO> newUldDmgVOs = new ArrayList<ULDDamageVO>();
			log.log(Log.FINE, "uldDmgRprDetailsVO.getDamageStatus(); ",
					uldDmgRprDetailsVO.getDamageStatus());
			log.log(Log.FINE, "maintainDamageReportForm.getStatusFlag()---> ",
					maintainDamageReportForm.getStatusFlag());
			if("uld_def_add_dmg_success".
					equals(maintainDamageReportForm.getStatusFlag())){
				log.log(Log.FINE, "uldDmgRprDetailsVO.getDamageStatus(); ",
						uldDmgRprDetailsVO.getDamageStatus());
				uldDmgRprDetailsVO.setDamageStatus("D");
				maintainDamageReportSession.setULDDamageVO(uldDmgRprDetailsVO);				
			}else if(("uld_def_update_dmg_success").equals(maintainDamageReportForm.getStatusFlag())){
				int closedCount = 0;
				log.log(Log.FINE, "uldDmgVOs", uldDmgVOs);
				if(uldDmgVOs != null){
					for(ULDDamageVO vo : uldDmgVOs){
						if(vo.getOperationFlag()==null 
								|| (AbstractVO.OPERATION_FLAG_INSERT).equals(vo.getOperationFlag())
								|| ("U").equals(vo.getOperationFlag())){
							newUldDmgVOs.add(vo);					
						}
					}
					for(ULDDamageVO vo : newUldDmgVOs){
						if(vo.isClosed()){
							closedCount++;							
						}
					}
					log.log(Log.FINE, "newUldDmgVOs", newUldDmgVOs);
					log.log(Log.FINE, "newUldDmgVOs.size()", newUldDmgVOs.size());
					log.log(Log.FINE, "closedCount", closedCount);
					if(closedCount == newUldDmgVOs.size()){
						uldDmgRprDetailsVO.setDamageStatus("N");
						maintainDamageReportSession.setULDDamageVO(uldDmgRprDetailsVO);	
					}else{
						uldDmgRprDetailsVO.setDamageStatus("D");
						maintainDamageReportSession.setULDDamageVO(uldDmgRprDetailsVO);	
					}
				}
			}
			log.log(Log.FINE, "maintainDamageReportForm.getStatusFlag()---> ",
					maintainDamageReportForm.getStatusFlag());
			//added by a-3045 for bug 17989 starts
			maintainDamageReportForm.setStatusFlag("");
			if(!"".equals(maintainDamageReportForm.getAllChecked()) && ("Y").equals(maintainDamageReportForm.getAllChecked()))
			{log.log(Log.FINE, "-------------------->>",
					maintainDamageReportForm.getAllChecked());
			maintainDamageReportForm.setRepairStatus("R");
			}	
			maintainDamageReportForm.setScreenStatusFlag(ComponentAttributeConstants.COMPONENT_TYPE_DETAIL);
	    	invocationContext.target = LIST_SUCCESS;
			return;
		}
		
		if(("fromulderrorlog").equals(maintainDamageReportSession.getPageURL()))
		{
			ULDFlightMessageReconcileDetailsVO uldFlightMessageReconcileDetailsVO=maintainDamageReportSession.getULDFlightMessageReconcileDetailsVO();
			maintainDamageReportForm.setUldNumber(uldFlightMessageReconcileDetailsVO.getUldNumber());
			maintainDamageReportForm.setPageURL("fromulderrorlog");	
			maintainDamageReportSession.setPageURL("fromulderrorlog");
		} 
		
		
		/**
		 * Validate for client errors
		 */
		Collection<ErrorVO> errors = null;
		log.log(Log.FINE, "maintainDamageReportForm.getUldNumber() ---> ",
				maintainDamageReportForm.getUldNumber());
		//added by saritha
		if(maintainDamageReportSession.getUldNumber() != null 
				&& maintainDamageReportSession.getUldNumber().trim().length() > 0){
			log.log(Log.FINE, "Session UldNumber ---> ",
					maintainDamageReportSession.getUldNumber());
			maintainDamageReportForm.setUldNumber(maintainDamageReportSession.getUldNumber());
			maintainDamageReportSession.removeUldNumber();
		}else{
		errors = validateForm
					(maintainDamageReportForm,logonAttributes.getCompanyCode());
		}
		if(errors!=null && errors.size() > 0 ) {
			maintainDamageReportForm.setScreenStatusValue("SCREENLOAD");
			invocationContext.addAllError(errors);
			invocationContext.target = LIST_FAILURE;
			return;
		}
		
		maintainDamageReportSession.setULDDamagePictureVO(null);
		maintainDamageReportSession.setSavedULDDamageVO(null);
		maintainDamageReportSession.setULDDamageVO(null);
		maintainDamageReportSession.setULDRepairVOs(null);
		maintainDamageReportSession.setULDDamageVOs(null);
		maintainDamageReportSession.setRefNo(null);
//		log.log(Log.FINE, "maintainDamageReportSession.getUldNumbers() ---> " + maintainDamageReportSession.getUldNumbers().size());
		
		ULDDamageFilterVO uldDamageFilterVO=new ULDDamageFilterVO();
		uldDamageFilterVO.setCompanyCode(logonAttributes.getCompanyCode());
			
		uldDamageFilterVO.setUldNumber
						(maintainDamageReportForm.getUldNumber().toUpperCase());
		
		log.log(Log.FINE, "uldDamageFilterVO ---> ", uldDamageFilterVO);
		ULDDamageRepairDetailsVO uldDamageRepairDetailsVO= 
											new  ULDDamageRepairDetailsVO();
		Collection<ErrorVO> exc = new ArrayList<ErrorVO>();
		try {
			/*uldDamageRepairDetailsVO= new ULDDefaultsDelegate()
									.findULDDamageDetails(uldDamageFilterVO) ;*/
			uldDamageRepairDetailsVO= new ULDDefaultsDelegate()
					.findULDDamageRepairDetails(uldDamageFilterVO);
		}
		catch (BusinessDelegateException businessDelegateException) {
			businessDelegateException.getMessage();
			exc = handleDelegateException(businessDelegateException);
		}
		
		log.log(Log.FINE, "uldDamageRepairDetailsVO ---> ",
				uldDamageRepairDetailsVO);
		if(uldDamageRepairDetailsVO == null ) {
				log.log(Log.ALL,"uldDamageRepairDetailsVO is null");
				maintainDamageReportForm.setScreenStatusValue("SCREENLOAD");
			  //  maintainDamageReportForm.setScreenStatusFlag("SCREENLOAD");
			    invocationContext.addError(new ErrorVO(
                    "uld.defaults.maintainDmgRep.msg.err.noresults",null));
			    if(errors!=null && errors.size() > 0 ) {
			    	invocationContext.addAllError(errors);
			    }
				invocationContext.target = LIST_FAILURE;
				return;
		}
		log.log(Log.FINE, "maintainDamageReportForm.getAllChecked()------->>",
				maintainDamageReportForm.getAllChecked());
		/*if(!"".equals(maintainDamageReportForm.getAllChecked()) && maintainDamageReportForm.getAllChecked()!=null){
			log.log(Log.FINE,"REPAIRED STATUS------->>"+maintainDamageReportForm.getAllChecked());
			if(maintainDamageReportForm.getAllChecked().equalsIgnoreCase("Y")){
				uldDamageRepairDetailsVO.setRepairStatus("Repaired");
			}
		}*/
		uldDamageRepairDetailsVO.setLastUpdatedUser
												(logonAttributes.getUserId());
		uldDamageRepairDetailsVO.setCompanyCode
											(logonAttributes.getCompanyCode());
		uldDamageRepairDetailsVO.setUldNumber
						(maintainDamageReportForm.getUldNumber().toUpperCase());
		log.log(Log.ALL, "damage status is", uldDamageRepairDetailsVO.getDamageStatus());
		/* Added by A-2883 for defaulting damage status to damaged */
		maintainDamageReportForm.setDamageStatusFlag("");
		//added by T-1927 for bug ICRD-24990
		if(uldDamageRepairDetailsVO.getDamageStatus()==null && uldDamageRepairDetailsVO.getUldDamageVOs()==null){
			maintainDamageReportForm.setDamageStatusFlag("N");
			//commented by a-3045 for bug17959 starts
			//uldDamageRepairDetailsVO.setDamageStatus("D");
			//commented by a-3045 for bug17959 ends
			
		}
		maintainDamageReportSession.setULDDamageVO(uldDamageRepairDetailsVO);
		
		
		maintainDamageReportForm.setSaveStatus("");
		maintainDamageReportForm.setScreenStatusValue("LIST");
		//maintainDamageReportForm.setScreenStatusFlag("LIST");
		if(uldDamageRepairDetailsVO.getUldDamageVOs()!=null && uldDamageRepairDetailsVO.getUldDamageVOs().size()>0){
			maintainDamageReportForm.setScreenStatusValue("DMGPRESENTLIST");
			//maintainDamageReportForm.setScreenStatusFlag("DMGPRESENTLIST");
		}
		//Added by A-7978 for ICRD-252003 starts here
		ArrayList<ULDRepairVO> uLDRepairVOs = 
	       		maintainDamageReportSession.getULDDamageVO().
	       										getUldRepairVOs() != null ?
	   			new ArrayList<ULDRepairVO>
	           	(maintainDamageReportSession.getULDDamageVO().getUldRepairVOs()) : 
	   			new ArrayList<ULDRepairVO>();
	          //Added by A-7978 for ICRD-252003 ends here
		maintainDamageReportForm.setStatusFlag("LIST");
		//maintainDamageReportForm.setTotAmt("");
		//Added by A-7978 for ICRD-252003 starts here
		double totalAmt=0.0;
		for(ULDRepairVO uldRepairVO:uLDRepairVOs){
			totalAmt=totalAmt+uldRepairVO.getDisplayAmount();
	}
		
		maintainDamageReportForm.setTotAmt(String.valueOf(totalAmt));
		//Added by A-7978 for ICRD-252003 ends here
	
	log.log(Log.FINE, "\n from screen value ", maintainDamageReportForm.getFromScreen());
		if("SAVE".equals(maintainDamageReportForm.getFromScreen())){
 			maintainDamageReportForm.setFromScreen("");
 			maintainDamageReportForm.setScreenReloadStatus("");
 		ErrorVO error  = new ErrorVO("uld.defaults.maintainDmgRep.msg.err.savedsuccesfully");
		error.setErrorDisplayType(ErrorDisplayType.INFO);
		invocationContext.addError(error);
 		}
 		maintainDamageReportForm.setScreenStatusFlag(ComponentAttributeConstants.COMPONENT_TYPE_DETAIL);
		invocationContext.target = LIST_SUCCESS;
        
    }
    /**
	 * @param maintainDamageReportForm
	 * @param companyCode 
	 * @return errors
	 */
    
	private Collection<ErrorVO> validateForm
	(MaintainDamageReportForm maintainDamageReportForm, String companyCode){
		log.entering("ListDamageReportCommand", "validateForm");
		
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		ErrorVO error = null;
		if(maintainDamageReportForm.getUldNumber()== null || 
				maintainDamageReportForm.getUldNumber().trim().length() == 0){
			 error = new ErrorVO(
					 "uld.defaults.maintainDmgRep.msg.err.uldnomandatory");
			 error.setErrorDisplayType(ErrorDisplayType.ERROR);
			errors.add(error);
		}
		else {
			if(maintainDamageReportForm.getUldNumber()!= null &&
					maintainDamageReportForm.getUldNumber().trim().length() >0)
			{
				try {
					
					new ULDDefaultsDelegate().validateULDFormat(companyCode,
						maintainDamageReportForm.getUldNumber().toUpperCase());
				
				} catch (BusinessDelegateException businessDelegateException) {
				
					errors = handleDelegateException(businessDelegateException);
				}
			}
		}
		log.exiting("ListDamageReportCommand", "validateForm");
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
    	
    	parameterTypes.add(DAMAGESTATUS_ONETIME);
    	parameterTypes.add(OVERALLSTATUS_ONETIME);
    	parameterTypes.add(REPAIRSTATUS_ONETIME);
    	parameterTypes.add(DAMAGECODE_ONETIME);
    	parameterTypes.add(POSITION_ONETIME);
    	parameterTypes.add(SEVERITY_ONETIME);
    	parameterTypes.add(REPAIRHEAD_ONETIME);
    	parameterTypes.add(FACILITYTYPE_ONETIME);
    	parameterTypes.add(PARTYTYPE_ONETIME);
    	parameterTypes.add(SECTION_ONETIME);
    	
    	
    	log.exiting("ScreenLoadCommand","getOneTimeParameterTypes");
    	return parameterTypes;    	
    }

    private void populateCurrency
			(MaintainDamageReportSession maintainDamageReportSession) {
		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		
		ArrayList<CurrencyVO> currencies = null;
		Collection<ErrorVO> excep = new ArrayList<ErrorVO>();
		try {
			currencies = (ArrayList<CurrencyVO>)new CurrencyDelegate()
			.findAllCurrencyCodes(logonAttributes.getCompanyCode());
		} catch (BusinessDelegateException businessDelegateException) {
			excep = handleDelegateException(businessDelegateException);
		}
		
		maintainDamageReportSession.setCurrencies(currencies);
		
	}
}
