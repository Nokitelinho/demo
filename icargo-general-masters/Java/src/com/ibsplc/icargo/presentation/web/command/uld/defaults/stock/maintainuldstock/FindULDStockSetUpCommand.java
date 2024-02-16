/*
 * FindULDStockSetUpCommand.java Created on Dec 19, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.stock.maintainuldstock;

import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import java.util.Collection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.ibsplc.icargo.business.shared.airline.vo.AirlineValidationVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.business.uld.defaults.stock.vo.ULDStockConfigVO;
import com.ibsplc.icargo.business.uld.defaults.stock.vo.ULDStockConfigFilterVO;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.stock.MaintainULDStockForm;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.stock.maintainuldstock.ListULDStockSetUpSession;
import com.ibsplc.icargo.presentation.delegate.uld.defaults.ULDDefaultsDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.airline.AirlineDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.area.AreaDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;



/**
 * @author A-1496
 *
 */
public class FindULDStockSetUpCommand  extends BaseCommand {

	private static final String LIST_SUCCESS = "list_success";
	private static final String LIST_FAILURE = "list_failure";
    private static final String MODULE = "uld.defaults";
	private static final String SCREENID ="uld.defaults.maintainuldstock";
	private static final String ULDNATURE_ONETIME = "uld.defaults.uldnature";
	private Log log = LogFactory.getLogger("FindULDStockSetUpCommand");
	
    /**
     * execute method
     * @param invocationContext
     * @throws CommandInvocationException
    */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
           
            ApplicationSessionImpl applicationSession = getApplicationSession();
			LogonAttributes logonAttributes  =  applicationSession.getLogonVO();
			ListULDStockSetUpSession listULDStockSession = getScreenSession(MODULE, SCREENID);
			String companyCode = logonAttributes.getCompanyCode();
			MaintainULDStockForm maintainuldstockform = (MaintainULDStockForm) invocationContext.screenModel;
			maintainuldstockform.setListStatus("Y");
			listULDStockSession.setUldNature(maintainuldstockform.getUldNature());
			//Added by Tarun
			listULDStockSession.setOneTimeValues(getOneTimeValues());
			
			/*	maintainuldstockform.setScreenStatusFlag(
					ComponentAttributeConstants.SCREEN_STATUS_DETAIL);
			*/
			//ADDED BY AYSWARYA
			
			log.log(Log.FINE, "statusFlag ---> ", maintainuldstockform.getStatusFlag());
			log.log(Log.FINE, "statusFlag ---> ", maintainuldstockform.getStatusFlag());
			log.log(Log.FINE, "statusFlag ---> ", maintainuldstockform.getStatusFlag());
			if(("uld_def_add_dmg_success").equals(maintainuldstockform.getStatusFlag()) ||
					("uld_def_update_dmg_success").equals(maintainuldstockform.getStatusFlag())||
					("uld_def_delete_dmg_success").equals(maintainuldstockform.getStatusFlag()) ||
					("uld_def_add_rep_success").equals(maintainuldstockform.getStatusFlag()) ||
					("uld_def_update_rep_success").equals(maintainuldstockform.getStatusFlag())||
					("uld_def_delete_rep_success").equals(maintainuldstockform.getStatusFlag())){
				
				
				log.log(Log.FINE, "maintainuldstockform.getStatusFlag()---> ",
						maintainuldstockform.getStatusFlag());
				maintainuldstockform.setStatusFlag("");
				log.log(Log.FINE, "maintainuldstockform.getStationCode()---> ",
						maintainuldstockform.getStationCode());
				maintainuldstockform.setScreenStatusFlag(
		  				ComponentAttributeConstants.SCREEN_STATUS_DETAIL);
		    	invocationContext.target = LIST_SUCCESS;
				return;
			}
			
			
			ULDStockConfigFilterVO uldstockconfigfiltervo = new ULDStockConfigFilterVO();
			String ac ="";
			log.log(Log.INFO,
					"maintainuldstockform.getAirlineIdentifier()--------->",
					maintainuldstockform.getAirlineIdentifier());
			if(maintainuldstockform.getAirlineIdentifier()!=null &&
					maintainuldstockform.getAirlineIdentifier().trim().length()>0){
			ac = maintainuldstockform.getAirlineIdentifier().toUpperCase();
			}
			Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
//			ErrorVO error = null;
			uldstockconfigfiltervo.setCompanyCode(companyCode);
			String stn = maintainuldstockform.getStationCode().toUpperCase();
			uldstockconfigfiltervo.setStationCode(stn);
			
			//Added as part of ICRD-3639 by A-3767 on 16Aug11
			//Added as Part of ICRD-16039 by A-5125 on 26Jul2012
			uldstockconfigfiltervo.setUldNature(maintainuldstockform.getUldNature().trim());
			uldstockconfigfiltervo.setUldTypeCode(maintainuldstockform.getUldTypeCode());
			uldstockconfigfiltervo.setUldGroupCode(maintainuldstockform.getUldGroupCode());
			
			log.log(Log.INFO,
					"maintainuldstockform.getAirlineCode()--------->>>",
					maintainuldstockform.getAirlineCode());
			listULDStockSession.setAirLineCode(maintainuldstockform.getAirlineCode());
			log.log(Log.INFO,
					"listULDStockSession.getAirLineCode()--------->>>",
					listULDStockSession.getAirLineCode());
			//Added by Tarun on 04-2-08
			listULDStockSession.setOneTimeValues(getOneTimeValues());
			
			
			log.log(Log.INFO, "getMonitorViewByNature----",
					maintainuldstockform.getMonitorViewByNature());
			log.log(Log.INFO, "uldstockconfigfiltervo.getUldNature()",
					uldstockconfigfiltervo.getUldNature());
			log.log(Log.INFO, "uldstockconfigfiltervo.getViewByNatureFlag()",
					uldstockconfigfiltervo.getViewByNatureFlag());
			//uldstockconfigfiltervo.setAirlineCode(ac);
			errors = validateAirline(maintainuldstockform,companyCode,uldstockconfigfiltervo);
			if(errors!=null && errors.size() > 0) {
				listULDStockSession.removeULDStockDetails();
				maintainuldstockform.setLinkStatus("");
				invocationContext.addAllError(errors);
				invocationContext.target = LIST_FAILURE;
				return;
			}
			ULDDefaultsDelegate uldDefaultsDelegate = new ULDDefaultsDelegate();
			Collection<ULDStockConfigVO> uldstockvos = new ArrayList<ULDStockConfigVO>();
			Collection<ErrorVO> error = new ArrayList<ErrorVO>();
			try {
				log.log(Log.INFO, "before setting to delegate--------------->",
						uldstockconfigfiltervo);
				uldstockvos = uldDefaultsDelegate.listULDStockConfig(uldstockconfigfiltervo);
				log.log(Log.INFO, "getting from delegate--------------->",
						uldstockvos);
				

			}catch(BusinessDelegateException ex) 
			{
				ex.getMessage();
				error = handleDelegateException(ex);
			}
			
			maintainuldstockform.setAirlineIdentifier(ac);
			maintainuldstockform.setLinkStatus("E");
			listULDStockSession.setULDStockDetails(uldstockvos);
			log.log(Log.INFO,
					"maintainuldstockform.getAirlineCode()--------->>>",
					maintainuldstockform.getAirlineCode());
			listULDStockSession.setAirLineCode(maintainuldstockform.getAirlineCode());
			log.log(Log.INFO,
					"listULDStockSession.getAirLineCode()--------->>>",
					listULDStockSession.getAirLineCode());
			log
					.log(
							Log.INFO,
							"listULDStockSession.getULDStockDetails()------------------>",
							listULDStockSession.getULDStockDetails());
			//Added as part of ICRD-3639 by A-3767 on 16Aug11
			maintainuldstockform.setUldNature(uldstockconfigfiltervo.getUldNature());
			maintainuldstockform.setUldTypeCode(uldstockconfigfiltervo.getUldTypeCode());
			maintainuldstockform.setUldGroupCode(uldstockconfigfiltervo.getUldGroupCode());
			
			maintainuldstockform.setScreenStatusFlag(
					ComponentAttributeConstants.SCREEN_STATUS_DETAIL);
			if(uldstockvos == null  || uldstockvos.size()== 0){
				ErrorVO err = new ErrorVO("uld.defaults.nulllist");
				errors.add(err);
	 			invocationContext.addAllError(errors);
	 			invocationContext.target=LIST_SUCCESS;
	 			return;
			}
			//Added by A-2883
			//for enabling/disabling buttons 
			if(uldstockvos!=null && uldstockvos.size()>0){
				maintainuldstockform.setListStatus("N");
			}
			invocationContext.target=LIST_SUCCESS;


    }

	private Collection<ErrorVO> validateAirline(MaintainULDStockForm maintainuldstockform,
			String companyCode,ULDStockConfigFilterVO uldstockconfigfiltervo) {
			log.log(log.FINE,"!!!!!!!!!!!!!!!!!!!!!!! INSIDE VALIDATE FUN.");
			Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
			ErrorVO error = null;
			ApplicationSessionImpl applicationSession = getApplicationSession();
			LogonAttributes logonAttributes = applicationSession.getLogonVO();
			if("".equals(uldstockconfigfiltervo.getStationCode())) {				
				error = new ErrorVO("uld.defaults.stock.stationmandatory");
				error.setErrorDisplayType(ErrorDisplayType.ERROR);
				errors.add(error);
			}else{
				if(validateAirportCodes(uldstockconfigfiltervo.getStationCode().toUpperCase(),
			    		logonAttributes)!=null){
					errors.add(new ErrorVO(
							"uld.defaults.uldstocksetup.msg.err.stationinvalid",
							null));
				}
			}
				
			AirlineValidationVO airlineVO = null;
			if(maintainuldstockform.getAirlineCode() != null
					&& maintainuldstockform.getAirlineCode().trim().length() > 0) {
						//log.log(log.FINE,"!!!!!!!!!!!!!!!!!!!!!!! INSIDE IF."+companyCode+"   "+maintainuldstockform.getAirlineCode());
						Collection<ErrorVO> errorsAirline = null;
						try {
							airlineVO = new AirlineDelegate().validateAlphaCode(companyCode,(maintainuldstockform.getAirlineCode()).toUpperCase());
						}
						catch(BusinessDelegateException businessDelegateException) {
							errorsAirline = handleDelegateException(businessDelegateException);
		       			}
						if(errorsAirline != null &&
								errorsAirline.size() > 0) {
							errors.addAll(errorsAirline);
						}
			}

			if(airlineVO != null) {
				int airlineIdentifier = airlineVO.getAirlineIdentifier();
				uldstockconfigfiltervo.setAirlineIdentifier(airlineIdentifier);
			}
			return errors;
		}
	/**
	 * 
	 * @param station
	 * @param logonAttributes
	 * @return
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
	 private Collection<String> getOneTimeParameterTypes() {
	    	log.entering("ScreenLoadCommand","getOneTimeParameterTypes");
	    	ArrayList<String> parameterTypes = new ArrayList<String>();    	
	    	parameterTypes.add(ULDNATURE_ONETIME);    	
	    	log.exiting("ScreenLoadCommand","getOneTimeParameterTypes");
	    	return parameterTypes;    	
	    }
	 private HashMap<String, Collection<OneTimeVO>> getOneTimeValues(){
			log.entering("ScreenLoadCommand","getOneTimeValues");
			/*
			 * Obtain the logonAttributes
			 */
			ApplicationSessionImpl applicationSession = getApplicationSession();
			LogonAttributes logonAttributes = applicationSession.getLogonVO();
			/*
			 * the shared defaults delegate
			 */
			SharedDefaultsDelegate sharedDefaultsDelegate = 
				new SharedDefaultsDelegate();
			Map<String, Collection<OneTimeVO>> oneTimeValues = null;
			Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
			try {
				log.log(Log.FINE, "****inside try**************************",
						getOneTimeParameterTypes());
				oneTimeValues =	sharedDefaultsDelegate.findOneTimeValues(
						logonAttributes.getCompanyCode(), 
						getOneTimeParameterTypes());
			} catch (BusinessDelegateException businessDelegateException) {
				log.log(Log.FINE,"*****in the exception");
				businessDelegateException.getMessage();
				errors = handleDelegateException(businessDelegateException);
			}
			log.log(Log.INFO, "oneTimeValues ---> ", oneTimeValues);
			log.exiting("ScreenLoadCommand","getOneTimeValues");
			return (HashMap<String, Collection<OneTimeVO>>)oneTimeValues;
		}
}
