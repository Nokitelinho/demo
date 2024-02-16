/*
 * CreateULDStockSetUpCommand.java Created on Feb 6, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.stock.maintainuldstock;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import com.ibsplc.icargo.business.shared.airline.vo.AirlineValidationVO;
import com.ibsplc.icargo.business.shared.uld.vo.ULDTypeValidationVO;
import com.ibsplc.icargo.business.uld.defaults.stock.vo.ULDStockConfigVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.airline.AirlineDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.area.AreaDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.uld.ULDDelegate;
import com.ibsplc.icargo.presentation.delegate.uld.defaults.ULDDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.stock.maintainuldstock.ListULDStockSetUpSession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.stock.MaintainULDStockForm;
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
public class CreateULDStockSetUpCommand extends BaseCommand {

		private Log log = LogFactory.getLogger("CreateULDStockSetUpCommand");
		/*
		 * The Module Name
		 */
		private static final String MODULE = "uld.defaults";
		
		private static final String SCREENID ="uld.defaults.maintainuldstock";
		
		private static final String ULD_DEF_ADD_DMG = 
			"uld_def_add_dmg";
		/*
		 * Constants for Status Flag
		 */
		private static final String ACTION_SUCCESS = "action_success";
		/**
		 * The execute method for AddDamageDetailsCommand
		 * (non-Javadoc)
		 * @see com.ibsplc.icargo.framework.web.command.Command#
		 * execute(com.ibsplc.icargo.framework.web.command.InvocationContext)
		 * @param invocationContext
		 * @throws CommandInvocationException
		 */
		public void execute(InvocationContext invocationContext)
				throws CommandInvocationException {
	
			/*
			 * Obtain the logonAttributes
			 */
			ApplicationSessionImpl applicationSession = getApplicationSession();
			LogonAttributes logonAttributes = applicationSession.getLogonVO();
			
			MaintainULDStockForm actionForm = 
				(MaintainULDStockForm) invocationContext.screenModel;
	    	ListULDStockSetUpSession listULDStockSession =
	    		getScreenSession(MODULE, SCREENID);
			/*
			 * Validate for client errors
			 */
			Collection<ErrorVO> errors = null;
			errors = validateForm
					(actionForm,logonAttributes.getCompanyCode());
			
			if(errors!=null && errors.size() > 0 ) {
				invocationContext.addAllError(errors);
				actionForm.setScreenStatusFlag(
		  				ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
		    	invocationContext.target = ACTION_SUCCESS;
				return;
			}
			if(listULDStockSession!=null){	
				ArrayList<ULDStockConfigVO> uldStockConfigVO = 
					listULDStockSession.getULDStockDetails()!= null ?
		   			new ArrayList<ULDStockConfigVO>
		           	(listULDStockSession.getULDStockDetails()) : 
		   			new ArrayList<ULDStockConfigVO>();		            
			
	    
				log.log(Log.FINE, "\n\n\n\n uldStockConfigVO ---> ",
						uldStockConfigVO);
			}	
			if(("uld_def_mod_dmg").equals(actionForm.getStatusFlag())){ 
	    		ArrayList<ULDStockConfigVO> modifiedULDStockConfigVO = null;
	    		if(listULDStockSession!=null){	
		    		if(listULDStockSession.getULDStockConfigVOs()!= null && listULDStockSession.getULDStockConfigVOs().size() > 0){
		    			 modifiedULDStockConfigVO = 
							new ArrayList<ULDStockConfigVO>(listULDStockSession.getULDStockConfigVOs()) ;
		    		}
	    		}
	    		log
						.log(
								Log.FINE,
								"\n\n  modifiedULDStockConfigVO before MODIFY OK Command ---> ",
								modifiedULDStockConfigVO);
				if(modifiedULDStockConfigVO!=null && modifiedULDStockConfigVO.size()!=0){
	   			int displayIndex =
	   				Integer.parseInt(actionForm.getDmgcurrentPageNum())-1;
	   			
	   			ULDStockConfigVO uLDStockConfigVO = modifiedULDStockConfigVO.get(displayIndex);
	   			updateULDDamageVO(uLDStockConfigVO, actionForm,logonAttributes);
	   			Collection<ErrorVO> errorsmod = null;
	   			errorsmod=validateMandatory(uLDStockConfigVO);
	   			if(errorsmod != null && errorsmod.size() > 0) {
	   				log.log(Log.FINE, "exception");
	   				actionForm.setDmgdisplayPage(actionForm.getDmgcurrentPageNum());
	   				invocationContext.addAllError(errorsmod);			
	   				invocationContext.target = ACTION_SUCCESS;
	   				return;
	   			}
	   		
	   			log
						.log(
								Log.FINE,
								"\n\n modifiedULDStockConfigVO after MODIFY OK Command ---> ",
								modifiedULDStockConfigVO);
				listULDStockSession.setULDStockConfigVOs(modifiedULDStockConfigVO);
		    	
	   			int displayPage = Integer.parseInt(actionForm.getDmgdisplayPage())-1;
	   			ULDStockConfigVO uLDStockConfigvo = 
	   				modifiedULDStockConfigVO.get(displayPage);
	   			populateDmg(uLDStockConfigvo, actionForm);
	   			actionForm.setDmgcurrentPageNum(actionForm.getDmgdisplayPage());
				
	   			}		        
			   
log.log(Log.FINE,
						"\n\n ULDStockConfigVO in SESSION AFTER MODIFY---> ",
						listULDStockSession.getULDStockDetails());
	   		}else
	    	{
	   			MaintainULDStockForm form = 
					(MaintainULDStockForm) invocationContext.screenModel;
	   			ArrayList<ULDStockConfigVO> uldStockConfigVOs =
	   				(ArrayList<ULDStockConfigVO>)
	   									listULDStockSession.getULDStockConfigVOs();
	   			log.log(Log.FINE,
						"\n\n uldStockConfigVOs before OK Command ---> ",
						uldStockConfigVOs);
				if(uldStockConfigVOs!=null && uldStockConfigVOs.size()!=0){
	   			int displayIndex =
	   				Integer.parseInt(form.getDmgcurrentPageNum())-1;
	   			
	   			ULDStockConfigVO uLDStockConfigVO = uldStockConfigVOs.get(displayIndex);
	   			updateULDDamageVO(uLDStockConfigVO, form,logonAttributes);
	   			Collection<ErrorVO> errorsadd = null;
	   			errorsadd=validateMandatory(uLDStockConfigVO);
	   			if(errorsadd != null && errorsadd.size() > 0) {
	   				log.log(Log.FINE, "exception");
	   				form.setDmgdisplayPage(form.getDmgcurrentPageNum());
	   				invocationContext.addAllError(errorsadd);			
	   				invocationContext.target = ACTION_SUCCESS;
	   				return;
	   			}
	   		
	   			log.log(Log.FINE,
						"\n\n uldStockConfigVOs after OK Command ---> ",
						uldStockConfigVOs);
				listULDStockSession.setULDStockConfigVOs(uldStockConfigVOs);
		    	
	   			int displayPage = Integer.parseInt(form.getDmgdisplayPage())-1;
	   			ULDStockConfigVO uLDStockConfigvo = 
	   				uldStockConfigVOs.get(displayPage);
	   			populateDmg(uLDStockConfigvo, form);
	   			form.setDmgcurrentPageNum(form.getDmgdisplayPage());	   			
	   			ArrayList<ULDStockConfigVO> uLDStockConfigVOFromSessionVOs=
	   				(ArrayList<ULDStockConfigVO>)listULDStockSession.getULDStockDetails();
	   			log
						.log(
								Log.FINE,
								"\n\n ULDStockConfigVOFromSessionVOs from session ---> ",
								uLDStockConfigVOFromSessionVOs);
				log.log(Log.FINE, "\n\n ULDStockConfigVOFromSessionVOs",
						uLDStockConfigVOFromSessionVOs);
				log
						.log(
								Log.FINE,
								"\n\n listULDStockSession.getULDStockConfigVOs from session after ADD---> ",
								listULDStockSession.getULDStockConfigVOs());
	   			
	   			}
	   			//listULDStockSession.setULDStockConfigVOs(null);
	   			
			}
	    	
			errors = saveUldStock(actionForm, listULDStockSession,logonAttributes);
			
			if(errors!=null && errors.size() > 0 ) {
				invocationContext.addAllError(errors);
				actionForm.setScreenStatusFlag(
		  				ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
		    	invocationContext.target = ACTION_SUCCESS;
				return;
			}
	    	
	    	if(ULD_DEF_ADD_DMG.equals(actionForm.getStatusFlag()))
	    	{
	    		actionForm.setStatusFlag("uld_def_add_dmg_success");
	    	} else {
	    		actionForm.setStatusFlag("uld_def_update_dmg_success");
	    	}
	    	invocationContext.target = ACTION_SUCCESS;
		}
		
		private Collection<ErrorVO> saveUldStock(MaintainULDStockForm actionForm,
				ListULDStockSetUpSession listULDStockSession,
				LogonAttributes logonAttributes) {
			ArrayList<ULDStockConfigVO> uldStockConfigVOs = 
				listULDStockSession.getULDStockDetails()!= null ?
	   			new ArrayList<ULDStockConfigVO>
	           	(listULDStockSession.getULDStockDetails()) : 
	   			new ArrayList<ULDStockConfigVO>();
			Collection<ULDStockConfigVO> uldstockvos = new ArrayList<ULDStockConfigVO>();
			Collection<ULDStockConfigVO> dupUldstockvos = new ArrayList<ULDStockConfigVO>();
			uldstockvos = (Collection<ULDStockConfigVO>)listULDStockSession.getULDStockConfigVOs();
			AirlineValidationVO airlineValidationVO = null;
			ULDDefaultsDelegate uldDefaultsDelegate = new ULDDefaultsDelegate();
			Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
			ErrorVO error = null;
			log.log(Log.INFO, "uldstockvos from session-------------->",
					uldstockvos);
				if(uldstockvos != null){
					
				log.log(Log.INFO, "uldstockvos------->", uldstockvos);
				for(ULDStockConfigVO vo:uldstockvos) {
					//if(vo.getOperationFlag()!=null){
					if(!(AbstractVO.OPERATION_FLAG_DELETE).equals(vo.getOperationFlag())){
					log.log(log.FINE,"SaveULDStockSetUpCommand----------inside for loop------------------->");
					String airlineCode = vo.getAirlineCode();
				
						airlineValidationVO = validateAlphaAWBPrefix(
								logonAttributes.getCompanyCode(),airlineCode);				
								
						if (airlineValidationVO !=  null){
							vo.setAirlineIdentifier(
									airlineValidationVO.getAirlineIdentifier());
					}
						/*if(!dupUldstockvos.contains(vo)) {
							log.log(log.FINE,"SaveULDStockSetUpCommand---------if dupUldstockvos is  not ther-------------------->");
							dupUldstockvos.add(vo);
						}
						else {
							log.log(log.FINE,"SaveULDStockSetUpCommand---------inside else loop-------------------->");
							error = new ErrorVO("uld.defaults.uldstocksetup.msg.err.duplicateexits");
			    			errors.add(error);	
			    			invocationContext.addAllError(errors);
			    			invocationContext.target=SAVE_FAILURE;
							
						}*/
						if(!("uld_def_mod_dmg").equals(actionForm.getStatusFlag())){
							boolean isDuplicateExists = false;
							log.log(Log.INFO, "dupUldstockvos------->",
									dupUldstockvos);
							for(ULDStockConfigVO dupUld:dupUldstockvos) {
								log.log(Log.INFO, "dupl vo------->", vo);
								log
										.log(Log.INFO, "dupl dupUld------->",
												dupUld);
								if(dupUld.getAirlineCode().equals(vo.getAirlineCode())&&
										dupUld.getStationCode().equals(vo.getStationCode())&&
										dupUld.getUldTypeCode().equals(vo.getUldTypeCode())&&
										dupUld.getUldNature().equals(vo.getUldNature())) {
									log.log(log.FINE,"SaveULDStockSetUpCommand---------inside else loop--error exits------------------>");
									error = new ErrorVO("uld.defaults.uldstocksetup.msg.err.duplicateexits");
					    			errors.add(error);	
					    			
					    			isDuplicateExists = true;
									break;
								}
							}
							if(isDuplicateExists) {
								break;
							}
							else {
								//Check for duplicate with the server vos
								for(ULDStockConfigVO dupUld:uldStockConfigVOs) {
									log.log(Log.INFO, "dupl vo------->", vo);
									log.log(Log.INFO, "dupl dupUld------->",
											dupUld);
									if(dupUld.getAirlineCode().equals(vo.getAirlineCode())&&
											dupUld.getStationCode().equals(vo.getStationCode())&&
											dupUld.getUldTypeCode().equals(vo.getUldTypeCode())&&
											dupUld.getUldNature().equals(vo.getUldNature())) {
										log.log(log.FINE,"SaveULDStockSetUpCommand---------inside else loop--error exits------------------>");
										error = new ErrorVO("uld.defaults.uldstocksetup.msg.err.duplicateexits");
						    			errors.add(error);	
						    			
						    			isDuplicateExists = true;
										break;
									}
								}
								if(isDuplicateExists) {
									break;
								}
								//isDuplicateExists = false;
								log.log(log.FINE,"SaveULDStockSetUpCommand---------if dupUldstockvos is  not ther------no error-------------->");
								dupUldstockvos.add(vo);
							}
						}
					}
				}
	    }
			
			if( (errors == null || errors.size() == 0) && uldstockvos != null) {
					for(ULDStockConfigVO vo:uldstockvos) {
						if((AbstractVO.OPERATION_FLAG_DELETE).equals(vo.getOperationFlag())){
							log.log(log.FINE,"if errors is null && flag is delete-------------------->");
							dupUldstockvos.add(vo);
						}
						if(!AbstractVO.OPERATION_FLAG_INSERT.equals(vo.getOperationFlag())){
							log.log(log.FINE,"if errors is null && flag is delete-------------------->");
							//dupUldstockvos.add(vo);
							vo.setOperationFlag(AbstractVO.OPERATION_FLAG_UPDATE);
						}
					}
					log.log(log.FINE,"SaveULDStockSetUpCommand---------if errors is  null-------------------->");
					Collection<ErrorVO> err = new ArrayList<ErrorVO>();
				try {
					log.log(Log.INFO, "before setting to delegate---------->",
							uldstockvos);
					uldDefaultsDelegate.saveULDStockConfig(uldstockvos);
				}catch(BusinessDelegateException ex) 
				{
					ex.getMessage();
					err = handleDelegateException(ex);
				} 
				errors.addAll(err);
				//now added
				listULDStockSession.removeULDStockConfigVOs();
				listULDStockSession.removeULDStockDetails();
				actionForm.setStation("");
				actionForm.setAirline("");
				//actionForm.setStationCode("");
				//actionForm.setAirlineCode("");
				actionForm.setLinkStatus("");
				listULDStockSession.setAirLineCode("");
				//actionForm.setCreateStatus("success");
				
				if(logonAttributes.isAirlineUser()){
		    		listULDStockSession.setAirLineCode(logonAttributes.getOwnAirlineCode());
		    		actionForm.setStkDisableStatus("airline");
		    	}
		    	else{
		    		if(actionForm.getStationCode()==null && actionForm.getStationCode().trim().length()==0){
		    			actionForm.setStationCode(logonAttributes.getAirportCode());
		    		}
		    		actionForm.setStkDisableStatus("GHA");
		    	} 			
				
			}
			else{
				if(logonAttributes.isAirlineUser()){
		    		listULDStockSession.setAirLineCode(logonAttributes.getOwnAirlineCode());
		    		actionForm.setStkDisableStatus("airline");
		    	}
		    	else{
		    		actionForm.setStationCode(logonAttributes.getAirportCode());
		    		actionForm.setStkDisableStatus("GHA");
		    	} 
				
				
			}
			return errors;
		}
		
		private AirlineValidationVO validateAlphaAWBPrefix(String compCode,String ownerId){
			AirlineValidationVO airlineValidationVO = null;
			AirlineDelegate airlineDelegate = new AirlineDelegate();  
			Collection<ErrorVO> error = new ArrayList<ErrorVO>();
			try {
			airlineValidationVO = airlineDelegate.validateAlphaCode(compCode,ownerId);
			}catch(BusinessDelegateException businessDelegateException){
			businessDelegateException.getMessage();
			error = handleDelegateException(businessDelegateException);
			}
			return airlineValidationVO;
	    }

/**
 * 
 * @param uldStockConfigVOs
 * @return
 */
		 public long populateSequence(ArrayList<ULDStockConfigVO> uldStockConfigVOs) {
	    	log.entering("CreateULDStockSetUpCommand", "populateSequence");
	    	long dmgseq=0;
	    	for(ULDStockConfigVO uldStockConfigVO:uldStockConfigVOs)
	    	{
	    		if(uldStockConfigVO.getSequenceNumber()>dmgseq)
	    		{
	    			dmgseq=uldStockConfigVO.getSequenceNumber();
	    		}
	    	}
	    	dmgseq=dmgseq+1;
	    	log.exiting("CreateULDStockSetUpCommand", "populateSequence");
	    	return dmgseq;
	    }
	
/**
 * 
 * @param actionForm
 * @param companyCode
 * @return
 */
		private Collection<ErrorVO> validateForm(MaintainULDStockForm actionForm
				, String companyCode){
			log.entering("CreateULDStockSetUpCommand", "validateForm");
			ApplicationSessionImpl applicationSession = getApplicationSession();
			LogonAttributes logonAttributes = applicationSession.getLogonVO();
			Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
			ErrorVO error = null;

			if(validateAirlineCodes(actionForm.getAirlineCode().toUpperCase(),logonAttributes)!=null){
				errors.add(new ErrorVO(
						"shared.airline.invalidairline",new Object[]{actionForm.getAirlineCode().toUpperCase()}));
			}

			if(actionForm.getMaximumQty()==null ||
					actionForm.getMaximumQty().trim().length()==0){
    			error = new ErrorVO("uld.defaults.uldstocksetup.msg.err.maxqtyisnull");
    			errors.add(error);		
    		}
			
			if((actionForm.getMinimumQty()==null ||actionForm.getMinimumQty().trim().length()==0 )){
    			error = new ErrorVO("uld.defaults.uldstocksetup.msg.err.minqtyisnull");
    			errors.add(error);		
    		}
			
			if(actionForm.getMaximumQty()!=null && actionForm.getMaximumQty().trim().length()>0 &&
					actionForm.getMinimumQty()!=null && actionForm.getMinimumQty().trim().length()>0){
				if(Integer.parseInt(actionForm.getMaximumQty())<Integer.parseInt(actionForm.getMinimumQty())){
					error = new ErrorVO("uld.defaults.uldstocksetup.msg.err.maxlessthanmin");
	    			errors.add(error);
				}
			}
			
			if(actionForm.getUldTypeCode()== null || 
					actionForm.getUldTypeCode().trim().length() == 0){
				// This "operations.shipments.documentnumber.mandatory" errorcode 
				// is mapped in errortags.xml as servercode with corresponding clientcode.
				// This client code have message entry in message resources property file.
				 error = new ErrorVO(
						 "uld.defaults.stock.uldtypemandatory");
				 error.setErrorDisplayType(ErrorDisplayType.ERROR);
				errors.add(error);
			}
			else {
					
				Collection<String> uldType = new ArrayList<String>();
				uldType.add(actionForm.getUldTypeCode().toUpperCase());
				Collection<ErrorVO> errorsUldType = null;
				try {
					Map<String,ULDTypeValidationVO> voreturned=new ULDDelegate().validateULDTypeCodes(logonAttributes.getCompanyCode(),
							uldType);
					log.log(Log.INFO, "voreturned---->>", voreturned);
				} catch (BusinessDelegateException businessDelegateException) {
					errorsUldType = handleDelegateException(businessDelegateException);
				}
				if(errorsUldType != null &&
						errorsUldType.size() > 0) {
					errors.addAll(errorsUldType);
				}
			}
					
			if(actionForm.getStationCode()== null || 
					actionForm.getStationCode().trim().length() == 0){
				 error = new ErrorVO(
						 "uld.defaults.stock.msg.err.stnmandatory");
				 error.setErrorDisplayType(ErrorDisplayType.ERROR);
				errors.add(error);
				
			}else{
				if(validateAirportCodes(actionForm.getStationCode().toUpperCase(),
			    		logonAttributes)!=null){
					errors.add(new ErrorVO(
							"uld.defaults.uldstocksetup.msg.err.stationinvalid",
							null));
					}}
			log.exiting("CreateULDStockSetUpCommand", "validateForm");
			return errors;
		}
		
		private Collection<ErrorVO> validateMandatory(ULDStockConfigVO uLDStockConfigVO){
			log.entering("CreateULDStockSetUpCommand", "validateForm");
			ApplicationSessionImpl applicationSession = getApplicationSession();
			LogonAttributes logonAttributes = applicationSession.getLogonVO();
			Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
			ErrorVO error = null;
			if(uLDStockConfigVO.getStationCode()== null || 
					uLDStockConfigVO.getStationCode().trim().length() == 0){
				 error = new ErrorVO(
						 "uld.defaults.stock.msg.err.stnmandatory");
				 error.setErrorDisplayType(ErrorDisplayType.ERROR);
				errors.add(error);
				
			}else{
				if(validateAirportCodes(uLDStockConfigVO.getStationCode().toUpperCase(),
			    		logonAttributes)!=null){
					errors.add(new ErrorVO(
							"uld.defaults.uldstocksetup.msg.err.stationinvalid",
							null));
		}}

		log.exiting("CreateULDStockSetUpCommand", "validateForm");
		return errors;
		}
		/**
		 * 
		 * @param uldStockConfigVO
		 * @param actionForm
		 * @param logonAttributes
		 */
		public void updateULDDamageVO
		(ULDStockConfigVO uldStockConfigVO,
				MaintainULDStockForm actionForm,LogonAttributes logonAttributes) {
		
		log.entering("CreateULDStockSetUpCommand", "updateULDDamageVO");
		if(uldStockConfigVO != null) {
			uldStockConfigVO.setCompanyCode(logonAttributes.getCompanyCode());
			uldStockConfigVO.setAirlineCode(actionForm.getAirlineCode().toUpperCase());
			uldStockConfigVO.setStationCode(actionForm.getStationCode().toUpperCase());
			uldStockConfigVO.setUldTypeCode(actionForm.getUldTypeCode().toUpperCase());
			uldStockConfigVO.setUldNature(actionForm.getUldNature().toUpperCase());
			uldStockConfigVO.setMaxQty(Integer.valueOf(actionForm.getMaximumQty()));
			uldStockConfigVO.setMinQty(Integer.valueOf(actionForm.getMinimumQty()));
			uldStockConfigVO.setLastUpdatedUser(logonAttributes.getUserId());
			// Added by Preet on 1st Apr for AirNZ 448 --starts
			uldStockConfigVO.setUldGroupCode(actionForm.getUldGroupCode());
			uldStockConfigVO.setRemarks(actionForm.getRemarks());
			if (actionForm.getDwellTime()!=null && actionForm.getDwellTime().trim().length()>0){
				uldStockConfigVO.setDwellTime(Integer.parseInt(actionForm.getDwellTime()));
			}else{
				uldStockConfigVO.setDwellTime(0);
			}
			log.log(Log.FINE, "\n\nactionForm.getStatusFlag() ---> ",
					actionForm.getStatusFlag());
			log.log(Log.FINE, "\n\nactionForm.getFlag() ---> ", actionForm.getFlag());
			log.log(Log.FINE, "\n\n uldStockConfigVO.getOperationFlag() ---> ",
					uldStockConfigVO.getOperationFlag());
			if(("Updated").equals(actionForm.getFlag()))
				{if(uldStockConfigVO.getOperationFlag()==null)
            	{
					uldStockConfigVO.setOperationFlag
            		(AbstractVO.OPERATION_FLAG_UPDATE);
					actionForm.setStatusFlag("uld_def_mod_dmg");
            	}
			}
		}
		log.exiting("CreateULDStockSetUpCommand", "updateULDDamageVO");
	}
		/**
		 * 
		 * @param airlineCode
		 * @param logonAttributes
		 * @return
		 */
		   public Collection<ErrorVO> validateAirlineCodes(
		    		String airlineCode,
		    		LogonAttributes logonAttributes){
		    	log.entering("Command", "validateAirportCodes");
		    	log.log(Log.FINE, " airlineCode ---> ", airlineCode);
				Collection<ErrorVO> errors = null;
		    	
		    	try {
					AirlineDelegate delegate = new AirlineDelegate();
					delegate.validateAlphaCode(logonAttributes.getCompanyCode(),airlineCode);			

		    	} catch (BusinessDelegateException e) {
					e.getMessage();
					log.log(Log.FINE, " Error Airline ---> ", e.getMessageVO().getErrorType());
					errors = handleDelegateException(e);
				}
			    	return errors;
		    }
		  /**
		   * 
		   * @param uldStockConfigVO
		   * @param actionForm
		   */
		public void populateDmg(
				ULDStockConfigVO uldStockConfigVO,
				MaintainULDStockForm actionForm) {
	    	log.entering("CreateULDStockSetUpCommand", "populateDmg");
	    	if (uldStockConfigVO!=null) {
	    		actionForm.setAirlineCode(uldStockConfigVO.getAirlineCode());
		    	actionForm.setStationCode(uldStockConfigVO.getStationCode());
	    		actionForm.setUldTypeCode(uldStockConfigVO.getUldTypeCode());
	    		actionForm.setUldNature(uldStockConfigVO.getUldNature());
	    		actionForm.setMaximumQty(String.valueOf(uldStockConfigVO.getMaxQty()));
	    		actionForm.setMinimumQty(String.valueOf(uldStockConfigVO.getMinQty()));
	    		
	    	}
	    	log.exiting("CreateULDStockSetUpCommand", "populateDmg");
	    }
		/**
	     * @param station
	     * @param logonAttributes
	     * @return errors
	     */
	   public Collection<ErrorVO> validateAirportCodes(
	    		String station,
	    		LogonAttributes logonAttributes){
	    	log.entering("Command", "validateAirportCodes");
	    	log.log(Log.FINE, " Station ---> ", station);
			Collection<ErrorVO> errors = null;
	    	
	    	try {
				AreaDelegate delegate = new AreaDelegate();
				delegate.validateAirportCode(
						logonAttributes.getCompanyCode(),station);			

	    	} catch (BusinessDelegateException e) {
				e.getMessage();
				log.log(Log.FINE, " Error Airport ---> ", e.getMessageVO().getErrorType());
				errors = handleDelegateException(e);
			}
			log.exiting("Command", "validateAirportCodes");
	    	return errors;
	    }
}
