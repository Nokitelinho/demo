/*
 * SelectNextULDSetUpCommand.java Created on Dec 23, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P)Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.stock.maintainuldstock;


import java.util.ArrayList;
import java.util.Collection;

import java.util.Map;
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
public class SelectNextULDSetUpCommand extends BaseCommand {

private Log log = LogFactory.getLogger("SelectNextULDSetUpCommand");
	
private static final String MODULE = "uld.defaults";

private static final String SCREENID ="uld.defaults.maintainuldstock";
/*
	/**
	 * Target constants
	 */
	private static final String OPR_ACTION_SUCCESS
		= "uld.defaults.maintaindmg";
		
	/**
	 * The execute method for AddDiscCommand
	 * (non-Javadoc)
	 * @see com.ibsplc.icargo.framework.web.command.Command#execute
	 * (com.ibsplc.icargo.framework.web.command.InvocationContext)
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		
		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		//String station=logonAttributes.getStationCode();
		
		MaintainULDStockForm actionForm = 
			(MaintainULDStockForm) invocationContext.screenModel;
    	ListULDStockSetUpSession listULDStockSession =
    		getScreenSession(MODULE, SCREENID);						
		
    	String station= actionForm.getStationCode().toUpperCase();
		ArrayList<ULDStockConfigVO> uldStockConfigVOs =
			(ArrayList<ULDStockConfigVO>)
								listULDStockSession.getULDStockConfigVOs();
		
		log.log(Log.FINE, "\n\nuldStockConfigVOs before select next ---> ",
				uldStockConfigVOs);
		int displayIndex =
			Integer.parseInt(actionForm.getDmgcurrentPageNum())-1;
		ULDStockConfigVO uldStockConfigVO = 
			uldStockConfigVOs.get(displayIndex);
		
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>(); 
		
		
		updateULDStockSetUpVO(uldStockConfigVO, actionForm,logonAttributes);
		
		errors=validateMandatory(uldStockConfigVO);
		if(errors != null && errors.size() > 0) {
			log.log(Log.FINE, "exception");
			invocationContext.addAllError(errors);	
							
			int displayPageNum = Integer.parseInt(actionForm.getDmgdisplayPage());	
			log.log(Log.FINE, "\n\n\n**DisplayPage val-->", displayPageNum);
			actionForm.setDmgdisplayPage(actionForm.getDmgcurrentPageNum());
						
			invocationContext.target = OPR_ACTION_SUCCESS;
			return;
		}
	
		log.log(Log.FINE, "\n\nuldStockConfigVOs after select next ---> ",
				uldStockConfigVOs);
		listULDStockSession.setULDStockConfigVOs(uldStockConfigVOs);

		int displayPage = Integer.parseInt(actionForm.getDmgdisplayPage())-1;
		ULDStockConfigVO uLDStockConfigVO = uldStockConfigVOs.get(displayPage);
		populateDmg(uLDStockConfigVO, actionForm);

		actionForm.setDmgcurrentPageNum(actionForm.getDmgdisplayPage());
		invocationContext.target = OPR_ACTION_SUCCESS;
	}

	/**
	 * 
	 * @param uldStockConfigVO
	 * @param actionForm
	 * @param logonAttributes
	 */
	public void updateULDStockSetUpVO
			(ULDStockConfigVO uldStockConfigVO,
					MaintainULDStockForm actionForm,LogonAttributes logonAttributes) {
		log.entering("SelectNextCommand", "updateShipmentDiscrepancyDetailsVO");
		//	 Added by Manaf  for INT ULD477 starts
		validateForm(actionForm,logonAttributes.getCompanyCode(),logonAttributes);
		Collection<ErrorVO> errors =  new ArrayList<ErrorVO>(); 
		if(errors!=null && errors.size() > 0 ) {
			InvocationContext invocationContext=new InvocationContext();
			invocationContext.addAllError(errors);
			actionForm.setScreenStatusFlag(
	  				ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
	    	invocationContext.target = OPR_ACTION_SUCCESS;
			return;
		}
		//	Added by Manaf  for INT ULD477 ends
		if(uldStockConfigVO != null) {
			uldStockConfigVO.setCompanyCode(logonAttributes.getCompanyCode());
			uldStockConfigVO.setAirlineCode(actionForm.getAirlineCode().toUpperCase());
			uldStockConfigVO.setStationCode(actionForm.getStationCode().toUpperCase());
			uldStockConfigVO.setUldTypeCode(actionForm.getUldTypeCode().toUpperCase());
			uldStockConfigVO.setUldNature(actionForm.getUldNature().toUpperCase());
			uldStockConfigVO.setMaxQty(Integer.parseInt(actionForm.getMaximumQty()));
			uldStockConfigVO.setMinQty(Integer.parseInt(actionForm.getMinimumQty()));
			uldStockConfigVO.setRemarks(actionForm.getRemarks());
			uldStockConfigVO.setLastUpdatedUser(logonAttributes.getUserId());
			
			// Added by Preet on 2 nd Apr for AirNZ 448 starts
			uldStockConfigVO.setUldGroupCode(actionForm.getUldGroupCode());
			if (actionForm.getDwellTime()!=null && actionForm.getDwellTime().trim().length()>0){
				uldStockConfigVO.setDwellTime(Integer.parseInt(actionForm.getDwellTime()));
			}else{
				uldStockConfigVO.setDwellTime(0);
			}
			// Added by Preet on 2 nd Apr for AirNZ 448 ends
			
			
			log.log(Log.FINE, "\n\nactionForm.getStatusFlag() ---> ",
					actionForm.getStatusFlag());
			log.log(Log.FINE, "\n\nactionForm.getFlag() ---> ", actionForm.getFlag());
			log.log(Log.FINE, "\n\nuldDamageVO.getOperationFlag() ---> ",
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
		log.exiting("SelectNextCommand", "updateShipmentDiscrepancyDetailsVO");
	}
	/**
	 * 
	 * @param uldStockConfigVO
	 * @param actionForm
	 */
	  public void populateDmg(
			  ULDStockConfigVO uldStockConfigVO,
			  MaintainULDStockForm actionForm) {
    	log.entering("SelectNextCommand", "populateShipmentDiscrepancyDetail");

    	if (uldStockConfigVO!=null) {
    		String air = "";
        	if(actionForm.getAirlineMain()!=null &&
        			actionForm.getAirlineMain().trim().length()>0){
        		uldStockConfigVO.setAirlineCode(actionForm.getAirlineMain().toUpperCase());
        	}
    		String station = actionForm.getStationMain().toUpperCase();
    		actionForm.setStationCode(station);
    		uldStockConfigVO.setStationCode(station);
    		if(actionForm.getAirlineMain() != null
    				&& actionForm.getAirlineMain().trim().length() > 0) {
    			actionForm.setFilterStatus("both");
    		}
    		actionForm.setAirlineCode(air);
    		actionForm.setStationCode(station);
    		actionForm.setUldNature(uldStockConfigVO.getUldNature());
    		actionForm.setAirlineCode(uldStockConfigVO.getAirlineCode());
    		actionForm.setStationCode(uldStockConfigVO.getStationCode());
    		actionForm.setUldTypeCode(uldStockConfigVO.getUldTypeCode());
    		actionForm.setUldNature(uldStockConfigVO.getUldNature());
    		actionForm.setMaximumQty(String.valueOf(uldStockConfigVO.getMaxQty()));
    		actionForm.setMinimumQty(String.valueOf(uldStockConfigVO.getMinQty()));
    		if (uldStockConfigVO.getRemarks() != null) {
    			actionForm.setRemarks(uldStockConfigVO.getRemarks());
    		}else {
    			actionForm.setRemarks("");
    		}
    		
    		// Added by Preet on 2 nd Apr for AirNZ 448 starts
    		//   Added by Manaf  for INT ULD494 starts
    		if(uldStockConfigVO.getDwellTime()>0 && uldStockConfigVO.getDwellTime()!=0){
    			actionForm.setDwellTime(String.valueOf(uldStockConfigVO.getDwellTime()));
    		}else{
      			actionForm.setDwellTime("");
      		}
    		//  Added by Manaf  for INT ULD494 ends
      		if(uldStockConfigVO.getUldGroupCode()!=null && uldStockConfigVO.getUldGroupCode().trim().length()>0){
      			actionForm.setUldGroupCode(uldStockConfigVO.getUldGroupCode().toUpperCase());
      		}else{
      			actionForm.setUldGroupCode("");
      		}
			// Added by Preet on 2 nd Apr for AirNZ 448 ends
    		
    	}
    	log.exiting("SelectNextCommand", "populateShipmentDiscrepancyDetail");
    }
	  private Collection<ErrorVO> validateMandatory(ULDStockConfigVO uLDStockConfigVO){
			log.entering("AddDamageDetailsCommand", "validateForm");
			ApplicationSessionImpl applicationSession = getApplicationSession();
			LogonAttributes logonAttributes = applicationSession.getLogonVO();
			Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
			ErrorVO error = null;
			
			if(validateAirportCodes(uLDStockConfigVO.getStationCode(),logonAttributes)!=null){
				errors.add(new ErrorVO("uld.defaults.uldstocksetup.msg.err.stationinvalid",	null));
			}
			
			if(validateAirlineCodes(uLDStockConfigVO.getAirlineCode(),logonAttributes)!=null){
				errors.add(new ErrorVO("uld.defaults.uldstocksetup.msg.err.invalidairline",	null));
			}
			//Added by Manaf  for INT ULD478 starts
			/*	
			if((uLDStockConfigVO.getMaxQty()==0)||
					(String.valueOf(uLDStockConfigVO.getMaxQty()).trim().length()==0)
					||(uLDStockConfigVO.getMaxQty()<1)){
    			error = new ErrorVO("uld.defaults.uldstocksetup.msg.err.maxqtyisnull");
    			errors.add(error);		
    		}
    		*/
			//Added by Manaf  for INT ULD478 ends
			/*			
			if(uLDStockConfigVO.getMinQty()==0||
					(String.valueOf(uLDStockConfigVO.getMaxQty()).trim().length()==0)){
    			error = new ErrorVO("uld.defaults.uldstocksetup.msg.err.minqtyisnull");
    			errors.add(error);		
    		}
			*/
			if(uLDStockConfigVO.getMaxQty()<uLDStockConfigVO.getMinQty()){
				error = new ErrorVO("uld.defaults.uldstocksetup.msg.err.maxlessthanmin");
    			errors.add(error);
			}
			
			if(uLDStockConfigVO.getUldTypeCode()== null || 
					uLDStockConfigVO.getUldTypeCode().trim().length() == 0){
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
				uldType.add(uLDStockConfigVO.getUldTypeCode());
				Collection<ErrorVO> errorsUldType = null;
				try {
					new ULDDelegate().validateULDTypeCodes(logonAttributes.getCompanyCode(),
							uldType);
				} catch (BusinessDelegateException businessDelegateException) {
					errorsUldType = handleDelegateException(businessDelegateException);
				}
				if(errorsUldType != null &&
						errorsUldType.size() > 0) {
					errors.addAll(errorsUldType);
				}
			}
			/*

			if(validateULDTypeCodes(uLDStockConfigVO.getUldTypeCode(),logonAttributes)!=null){
				errors.add(new ErrorVO("uld.defaults.uldstocksetup.msg.err.invaliduldtype",	null));
			}
				*/

		log.exiting("AddDamageDetailsCommand", "validateForm");
		return errors;
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
			
	    	return errors;
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
	   
	 /*  public Collection<ErrorVO> validateULDTypeCodes(
	    		String uldtypecode,
	    		LogonAttributes logonAttributes){
	    	log.entering("Command", "validateAirportCodes");
	    	log.log(Log.FINE, " uldtypecode ---> " + uldtypecode);
	    	Collection uldType = new ArrayList();
	    	//uldType.add(uLDStockConfigVO.)
	    	Collection<ErrorVO> errors = null;
	    	
	    	try {
				ULDDelegate delegate = new ULDDelegate();
				delegate.validateULDTypeCodes(logonAttributes.getCompanyCode(),uldType);			

	    	} catch (BusinessDelegateException e) {
//printStackTrrace()();
				log.log(Log.FINE, " Error Airline ---> " + e.getMessageVO().getErrorType());
				errors = handleDelegateException(e);
			}
		    	return errors;
	    }*/
	   // Added by Manaf  for INT ULD477 starts
	   /** @param actionForm
		   * @param companyCode
		   * @return
		   */
		  		private Collection<ErrorVO> validateForm(MaintainULDStockForm actionForm
		  				, String companyCode,LogonAttributes  logonAttributes){
		  			log.entering("SelectNextULDSetUpCommand", "validateForm");
		  			
		  			Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		  			ErrorVO error = null;

		  			if(validateAirlineCodes(actionForm.getAirlineCode().toUpperCase(),logonAttributes)!=null){
		  				errors.add(new ErrorVO(
		  						"shared.airline.invalidairline",new Object[]{actionForm.getAirlineCode().toUpperCase()}));
		  			}

		  			if(actionForm.getMaximumQty()==null ||
		  					actionForm.getMaximumQty().trim().length()==0 ||("").equals(actionForm.getMaximumQty())){
		      			error = new ErrorVO("uld.defaults.uldstocksetup.msg.err.maxqtyisnull");
		      			errors.add(error);		
		      		}
		  			
		  			if(actionForm.getMinimumQty()==null ||actionForm.getMinimumQty().trim().length()==0 || ("").equals(actionForm.getMinimumQty())){
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
		  			log.exiting("SelectNextULDSetUpCommand", "validateForm");
		  			return errors;
		  		}
		  		//	Added by Manaf  for INT ULD477 ends
}
