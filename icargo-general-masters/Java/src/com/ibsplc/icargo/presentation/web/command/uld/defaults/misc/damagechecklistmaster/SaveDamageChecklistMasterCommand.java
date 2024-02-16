/*
 * SaveDamageChecklistMasterCommand.java Created on May 5, 2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.misc.damagechecklistmaster;


import java.util.ArrayList;
import java.util.Collection;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDDamageChecklistVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.uld.defaults.ULDDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.misc.DamageChecklistMasterSession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.misc.DamageChecklistMasterForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
/**
 * @author A-3459
 *
 */

 public class SaveDamageChecklistMasterCommand extends BaseCommand {
	
	 private static final String SAVE_SUCCESS = "save_success";

	 private static final String SAVE_FAILURE = "save_failure";

	private static final String MODULE_NAME = "uld.defaults"; 
	
	
	 //boolean hasToSave=false;
	private static final String SCREEN_ID = "uld.defaults.damagechecklistmaster";
	private Log log = LogFactory.getLogger("SaveCommand");
	
	/**
	 * execute method
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		
		log.entering("Save Command", "ULD DamageChecklistMaster");
		DamageChecklistMasterForm damageChecklistMasterForm = (DamageChecklistMasterForm) invocationContext.screenModel;
		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		 boolean hasToSave=false;
		DamageChecklistMasterSession damageChecklistMasterSession= getScreenSession(MODULE_NAME, SCREEN_ID);
		Collection<ULDDamageChecklistVO>uldDamageChecklistVOsFromSession=new ArrayList<ULDDamageChecklistVO>();
		uldDamageChecklistVOsFromSession=damageChecklistMasterSession.getULDDamageChecklistVO();
		log.log(Log.FINE, "FromSession", uldDamageChecklistVOsFromSession);
		Collection<ULDDamageChecklistVO>uldDamageChecklistVOsFromForm=getTableDetails(damageChecklistMasterForm, logonAttributes);
		Collection<ULDDamageChecklistVO>uldDamageChecklistVOsToSave=new ArrayList<ULDDamageChecklistVO>();
		Collection<ErrorVO> errors=new ArrayList<ErrorVO>();
		// check mandatory fields
		//errors=validateMandatory(uldDamageChecklistVOsFromForm);
		if (errors != null && errors.size() > 0) {
			invocationContext.addAllError(errors);		
			
			//damageChecklistMasterSession.setULDDamageChecklistVO(uldDamageChecklistVOsFromForm);
			invocationContext.target = SAVE_FAILURE;
			return;
		}
		
		uldDamageChecklistVOsToSave=createVosToSave(uldDamageChecklistVOsFromForm,uldDamageChecklistVOsFromSession);
		
		
		

		if (!(uldDamageChecklistVOsToSave == null
				|| uldDamageChecklistVOsToSave.size() == 0)) {
			for(ULDDamageChecklistVO uldDamageCheck :uldDamageChecklistVOsToSave){
				if((uldDamageCheck.getOperationFlag()!=null)&&((uldDamageCheck.getOperationFlag().equals(ULDDamageChecklistVO.OPERATION_FLAG_INSERT))
						||(uldDamageCheck.getOperationFlag().equals(ULDDamageChecklistVO.OPERATION_FLAG_DELETE))||
						(uldDamageCheck.getOperationFlag().equals(ULDDamageChecklistVO.OPERATION_FLAG_UPDATE)))){
					hasToSave=true;
					log.log(Log.FINE, "going to delete from db", hasToSave);
					break;
				}
			}
				if(!hasToSave){
					ErrorVO errorvo = new ErrorVO("uld.defaults.nodatatosave");
					errorvo.setErrorDisplayType(ErrorDisplayType.ERROR);
					if (errors == null) {
						errors = new ArrayList<ErrorVO>();
					}
					errors.add(errorvo);
					invocationContext.addAllError(errors);					
					invocationContext.target = SAVE_FAILURE;
					return;
				}
		}
		else
		{
			ErrorVO error = new ErrorVO("uld.defaults.nothing");
			error.setErrorDisplayType(ErrorDisplayType.ERROR);
			errors.add(error);
	     	invocationContext.addAllError(errors);  
		}

		
		
		
		try {
			log.log(Log.FINE, "entering the delegate method",
					uldDamageChecklistVOsToSave);
			new ULDDefaultsDelegate()
					.saveULDDamageChecklistMaster(uldDamageChecklistVOsToSave);
		}
		catch (BusinessDelegateException businessDelegateException) {
			errors = handleDelegateException(businessDelegateException);
		}
		if(errors != null && errors.size() > 0) {
			invocationContext.target =  SAVE_FAILURE;
		} else {
			damageChecklistMasterSession.removeULDDamageChecklistVO();
			log.exiting("savecommand", "save successful");
			
			invocationContext.target = SAVE_SUCCESS; 
		}
		
		 
		 
	}
	/**
	 * @param damageChecklistMasterForm
	 * @param logonAttributes
	 * @return
	 */
	private Collection <ULDDamageChecklistVO> getTableDetails(DamageChecklistMasterForm  damageChecklistMasterForm, LogonAttributes logonAttributes){
		Collection<ULDDamageChecklistVO>uldDamageChecklistVOs=new ArrayList<ULDDamageChecklistVO>();
		//ULDDamageChecklistVO damageChecklistVO=new ULDDamageChecklistVO();
		int rowsPresent=damageChecklistMasterForm.getTableSection().length-1;
		log.log(Log.FINE, "rowsPresent", rowsPresent);
		for(int index=0;index<rowsPresent;index++){
			
			log.log(Log.FINE, "index", index);
			log.log(Log.FINE, "getTableSection", damageChecklistMasterForm.getTableSection(), index);
			log.log(Log.FINE, "getDescription", damageChecklistMasterForm.getDescription(), index);
			log.log(Log.FINE, "getNoofPoints", damageChecklistMasterForm.getNoOfPoints(), index);
			log.log(Log.FINE, "damageChecklistMasterForm.getSequenceNumber()",
					damageChecklistMasterForm.getSequenceNumber());
			log.log(Log.FINE, "getHiddenOperationFlag",
					damageChecklistMasterForm.getHiddenOperationFlag(), index);
			if(!("NOOP".equals(damageChecklistMasterForm.getHiddenOperationFlag()[index]))){
			ULDDamageChecklistVO damageChecklistVO=new ULDDamageChecklistVO();
				damageChecklistVO.setCompanyCode(logonAttributes.getCompanyCode());
			if(!(damageChecklistMasterForm.getTableSection()[index].trim().length()==0 && 
					damageChecklistMasterForm.getTableSection()[index]==null)){
				damageChecklistVO.setSection(damageChecklistMasterForm.getTableSection()[index]);
				log.log(Log.FINE, "damageChecklistVO", damageChecklistVO.getSection());
			}
			if(!(damageChecklistMasterForm.getDescription()[index].trim().length()==0 && 
					damageChecklistMasterForm.getDescription()[index]==null)){
				damageChecklistVO.setDescription(damageChecklistMasterForm.getDescription()[index]);
			}
			if(Integer.parseInt(damageChecklistMasterForm.getNoOfPoints()[index])>=0){
				damageChecklistVO.setNoOfPoints(Integer.parseInt(damageChecklistMasterForm.getNoOfPoints()[index]));
			}
			if(( damageChecklistMasterForm.getHiddenOperationFlag()[index])!=null &&
					damageChecklistMasterForm.getHiddenOperationFlag()[index].trim().length()!=0 ){
			damageChecklistVO.setOperationFlag(damageChecklistMasterForm.getHiddenOperationFlag()[index]);
			}
			if(damageChecklistMasterForm.getSequenceNumber()!=null && damageChecklistMasterForm.getSequenceNumber().length>index){
				damageChecklistVO.setSequenceNumber(damageChecklistMasterForm.getSequenceNumber()[index]);
			}
			
				//log.log(Log.FINE,"setOperationFlag"+damageChecklistVO.getOperationFlag());
			uldDamageChecklistVOs.add(damageChecklistVO);
			}
			
		}
		
		log.log(Log.FINE, "damageChecklistVO returning from getTableDetails",
				uldDamageChecklistVOs);
		return uldDamageChecklistVOs;
	}
	
	
	
	/**
	 * @param uldDamageChecklistVOs
	 * @return
	 */
	/*private Collection<ErrorVO> validateMandatory(Collection<ULDDamageChecklistVO>uldDamageChecklistVOs){
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		for(ULDDamageChecklistVO damageChecklistVO:uldDamageChecklistVOs){
		if(damageChecklistVO.getSection()==null|| "".equals( damageChecklistVO.getSection())){
			
			errors.add(new ErrorVO(
					"uld.defaults.section", null));
		}
		if(damageChecklistVO.getDescription()==null || "".equals(damageChecklistVO.getDescription())){
			
			errors.add(new ErrorVO(
					"uld.defaults.description", null));
		}
		if(damageChecklistVO.getNoOfPoints()<= 0){
			
			errors.add(new ErrorVO(
					"uld.defaults.noofpoints", null));
		}
		}
		
		return errors;
	}*/
	
	/**
	 * @param uldDamageChecklistVOsFromForm
	 * @param uldDamageChecklistVOsFromSession
	 * @return
	 */
	private Collection<ULDDamageChecklistVO> createVosToSave(Collection<ULDDamageChecklistVO> uldDamageChecklistVOsFromForm,
			Collection<ULDDamageChecklistVO> uldDamageChecklistVOsFromSession){
		log.entering("entering the method:", "createVosToSave");
		if(uldDamageChecklistVOsFromSession==null){
			uldDamageChecklistVOsFromSession= new ArrayList<ULDDamageChecklistVO>();
		}
		Collection<ULDDamageChecklistVO> uldDamageCheckListVOToSave=new ArrayList<ULDDamageChecklistVO>();
		boolean isFoundInSession=false;
		log.log(Log.FINE, "the values in session outside  :",
				uldDamageChecklistVOsFromSession);
		log.log(Log.FINE, "the values current VO from form outside  :",
				uldDamageChecklistVOsFromForm);
		for(ULDDamageChecklistVO uldDamageChecklistVO:uldDamageChecklistVOsFromForm){ 
			log.log(Log.FINE, "uldDamageChecklistVOsFromSession-------->\n",
					uldDamageChecklistVOsFromSession);
			if(uldDamageChecklistVOsFromSession.size()>0 && uldDamageChecklistVOsFromSession != null){
			for(ULDDamageChecklistVO uldDamageVOFromSession:uldDamageChecklistVOsFromSession){
				log.log(Log.FINE,
						"uldDamageVOFromSession.getOperationFlag()-------->\n",
						uldDamageVOFromSession.getOperationFlag());
				if(!ULDDamageChecklistVO.OPERATION_FLAG_INSERT.equals(uldDamageVOFromSession.getOperationFlag())){
				 isFoundInSession=false;
				log.log(Log.FINE, "uldDamageVOFromSession.getSection()-->",
						uldDamageVOFromSession.getSection());
				log.log(Log.FINE,
						"uldDamageVOFromSession.getDescription()--->",
						uldDamageVOFromSession.getDescription());
				log.log(Log.FINE, "uldDamageChecklistVO.getSection()-->",
						uldDamageChecklistVO.getSection());
				log.log(Log.FINE, "uldDamageChecklistVO.getDescription()-->",
						uldDamageChecklistVO.getDescription());
				log.log(Log.FINE,
						"uldDamageVOFromSession.getOperationFlag()-->",
						uldDamageVOFromSession.getOperationFlag());
				log.log(Log.FINE,
						"uldDamageChecklistVO.getSequenceNumber()-->",
						uldDamageChecklistVO.getSequenceNumber());
				log.log(Log.FINE,
						"uldDamageVOFromSession.getSequenceNumber()-->",
						uldDamageVOFromSession.getSequenceNumber());
				if(uldDamageVOFromSession.getSection().equals(uldDamageChecklistVO.getSection())&&
						 uldDamageVOFromSession.getSequenceNumber().equals(uldDamageChecklistVO.getSequenceNumber())){
					isFoundInSession=true;
					log.log(Log.FINE, "isFoundInSession", isFoundInSession);
					if(ULDDamageChecklistVO.OPERATION_FLAG_DELETE.equals(uldDamageChecklistVO.getOperationFlag())){
						log.log(Log.FINE,"deleting the the rows");
						uldDamageVOFromSession.setOperationFlag(ULDDamageChecklistVO.OPERATION_FLAG_DELETE);
						uldDamageCheckListVOToSave.add(uldDamageVOFromSession);
						break;
					}
				 					
					if(!(uldDamageVOFromSession.getDescription().equals(uldDamageChecklistVO.getDescription()))){
						uldDamageVOFromSession.setDescription(uldDamageChecklistVO.getDescription());
						uldDamageVOFromSession.setOperationFlag(ULDDamageChecklistVO.OPERATION_FLAG_UPDATE);
						log.log(Log.FINE, "updating description");
					}
					if(!(uldDamageVOFromSession.getNoOfPoints()==(uldDamageChecklistVO.getNoOfPoints()))){
						uldDamageVOFromSession.setNoOfPoints(uldDamageChecklistVO.getNoOfPoints());
						uldDamageVOFromSession.setOperationFlag(ULDDamageChecklistVO.OPERATION_FLAG_UPDATE);
						log.log(Log.FINE, "updating noof points");
					}
					uldDamageCheckListVOToSave.add(uldDamageVOFromSession);
					log.log(Log.FINE, "inside if ", uldDamageCheckListVOToSave);
					break;
				 }	
			}
				
			}
		}
			
			
			if(!(isFoundInSession )){
				log.log(Log.FINE, "values not found insession---------->",
						isFoundInSession);
				ULDDamageChecklistVO damageChecklistVO=new ULDDamageChecklistVO();
				damageChecklistVO.setSection(uldDamageChecklistVO.getSection());
				damageChecklistVO.setDescription(uldDamageChecklistVO.getDescription());
				damageChecklistVO.setNoOfPoints(uldDamageChecklistVO.getNoOfPoints());
				damageChecklistVO.setCompanyCode(uldDamageChecklistVO.getCompanyCode());
				damageChecklistVO.setOperationFlag(ULDDamageChecklistVO.OPERATION_FLAG_INSERT);	
				uldDamageChecklistVOsFromSession.add(damageChecklistVO);
				uldDamageCheckListVOToSave.add(damageChecklistVO);
			}
		
		}
		log.log(Log.FINE, "VOs to b saved", uldDamageCheckListVOToSave);
		return uldDamageCheckListVOToSave;
	}
}
