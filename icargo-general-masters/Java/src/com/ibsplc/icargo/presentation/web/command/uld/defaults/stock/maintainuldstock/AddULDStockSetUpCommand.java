/*
 * AddULDStockSetUpCommand.java Created on Dec 28, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services(P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.stock.maintainuldstock;

import java.util.ArrayList;
import java.util.Collection;
import com.ibsplc.icargo.business.shared.airline.vo.AirlineValidationVO;
import com.ibsplc.icargo.business.shared.area.airport.vo.AirportValidationVO;
import com.ibsplc.icargo.business.uld.defaults.stock.vo.ULDStockConfigVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.stock.maintainuldstock.ListULDStockSetUpSession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.stock.MaintainULDStockForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;
import com.ibsplc.icargo.presentation.delegate.shared.airline.AirlineDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.area.AreaDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.uld.ULDDelegate;


/**
 * @author A-1862
 *
 */
public class AddULDStockSetUpCommand extends BaseCommand {

	private Log log = LogFactory.getLogger("AddULDStockSetUpCommand");

	/**
	 * Screen Id of maintain damage report screen
	 */
	private static final String MODULE = "uld.defaults";
	
	private static final String SCREENID ="uld.defaults.maintainuldstock";
	/*
	 * The constant for actions - assign success
	 */
	private static final String ADDDMG_SUCCESS = "adddmg_success";
		
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
		String companyCode= logonAttributes.getCompanyCode();
		
		MaintainULDStockForm actionForm = 
			(MaintainULDStockForm) invocationContext.screenModel;
    	ListULDStockSetUpSession listULDStockSession =
    		getScreenSession(MODULE, SCREENID);
    	String station = actionForm.getStation().toUpperCase();
		int displayIndex = Integer.parseInt(
				actionForm.getDmgcurrentPageNum())-1;
				
		AirlineValidationVO airlineValidationVO = null;
		
		ArrayList<ULDStockConfigVO> uldStockConfigVOs =
			(ArrayList<ULDStockConfigVO>)
								listULDStockSession.getULDStockConfigVOs();
		log.log(Log.FINE, "\n\nuldStockConfigVOs before add ---> ",
				uldStockConfigVOs);
		SelectNextULDSetUpCommand command = new SelectNextULDSetUpCommand();
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>(); 
		if(uldStockConfigVOs != null && uldStockConfigVOs.size() > 0){
			ULDStockConfigVO uldStockConfigVO = 
				uldStockConfigVOs.get(displayIndex);
			command.updateULDStockSetUpVO
						(uldStockConfigVO, actionForm,logonAttributes);
			
			errors=validateMandatory(uldStockConfigVO);
		if(errors != null && errors.size() > 0) {
			log.log(Log.FINE, "exception");
			int displayPageNum = Integer.parseInt(actionForm.getDmgdisplayPage());	
			log.log(Log.FINE, "\n\n\n**DisplayPage val-->", displayPageNum);
			actionForm.setDmgdisplayPage(actionForm.getDmgcurrentPageNum());
			invocationContext.addAllError(errors);	
			
			invocationContext.target = ADDDMG_SUCCESS;
			return;
		}
		}
		

		ULDStockConfigVO newULDStockConfigVO = new ULDStockConfigVO();
		if(uldStockConfigVOs != null ) {
			try{
				airlineValidationVO = validateAlphaAWBPrefix(station,logonAttributes.getCompanyCode());				
				}catch(BusinessDelegateException businessDelegateException){
					errors = handleDelegateException(businessDelegateException);
				}			
				if (airlineValidationVO !=  null){
					newULDStockConfigVO.setAirlineIdentifier(
							airlineValidationVO.getAirlineIdentifier());
			}
				if(actionForm.getAirlineCode()!=null && actionForm.getAirlineCode().trim().length()>0 ){
	    			newULDStockConfigVO.setAirlineCode(actionForm.getAirlineCode().toUpperCase());
	    		}
			//newULDStockConfigVO.setAirlineIdentifier();
			newULDStockConfigVO.setOperationFlag(AbstractVO.OPERATION_FLAG_INSERT);
			newULDStockConfigVO.setStationCode(station);
			newULDStockConfigVO.setCompanyCode(companyCode);
			newULDStockConfigVO.setSequenceNumber(populateSequence(uldStockConfigVOs));
			newULDStockConfigVO.setLastUpdatedUser(logonAttributes.getUserId());
			uldStockConfigVOs.add(newULDStockConfigVO);
			
		} 
		log.log(Log.FINE, "\n\nuldStockConfigVOs after add ---> ",
				uldStockConfigVOs);
		listULDStockSession.setULDStockConfigVOs(uldStockConfigVOs);
		
		command.populateDmg(newULDStockConfigVO, actionForm);

		int totalRecords = 0;
    	if(uldStockConfigVOs != null) {
	    	for(ULDStockConfigVO uldStockConfigVO : uldStockConfigVOs) {
	    		totalRecords++;
	    		}
	    }
     	actionForm.setDmgtotalRecords(String.valueOf(totalRecords));
    	actionForm.setDmglastPageNum(actionForm.getDmgtotalRecords());
    	actionForm.setDmgdisplayPage(actionForm.getDmgtotalRecords());
    	actionForm.setDmgcurrentPageNum(actionForm.getDmgdisplayPage());
     	
		invocationContext.target = ADDDMG_SUCCESS;
	}
	/**
	 * 
	 * @param uldStockConfigVOs
	 * @return
	 */
	 public long populateSequence(ArrayList<ULDStockConfigVO> uldStockConfigVOs) {
	    	log.entering("AddULDStockSetUpCommand", "populateDiscDetail");
	    	long resolutionseq=0;
	    	for(ULDStockConfigVO uldStockConfigVO:uldStockConfigVOs)
	    	{
	    		if(uldStockConfigVO.getSequenceNumber()>resolutionseq)
	    		{
	    		resolutionseq=uldStockConfigVO.getSequenceNumber();
	    		}
	    	}
	    	resolutionseq=resolutionseq+1;
	    	log.exiting("AddULDStockSetUpCommand", "populateDiscDetail");
    	return resolutionseq;
    	
    }

	 /**
	  * 
	  * @param uLDStockConfigVO
	  * @return
	  */
	 private Collection<ErrorVO> validateMandatory(ULDStockConfigVO uLDStockConfigVO){
			log.entering("AddULDStockSetUpCommand", "validateForm");
			ApplicationSessionImpl applicationSession = getApplicationSession();
			LogonAttributes logonAttributes = applicationSession.getLogonVO();
			Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
			ErrorVO error = null;
			
				if(validateAirportCodes(uLDStockConfigVO.getStationCode(),logonAttributes)!=null){
					errors.add(new ErrorVO("uld.defaults.uldstocksetup.msg.err.stationinvalid",	null));
				}
				
				if(validateAirlineCodes(uLDStockConfigVO.getAirlineCode(),logonAttributes)!=null){
					errors.add(new ErrorVO("shared.airline.invalidairline",	new Object[]{uLDStockConfigVO.getAirlineCode()}));
				}

				log.log(Log.INFO,
						"uLDStockConfigVO.getMaxQty()-------------->",
						uLDStockConfigVO.getMaxQty());
				log.log(Log.INFO,
						"uLDStockConfigVO.getMinQty()-------------->",
						uLDStockConfigVO.getMinQty());
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
				/*if(validateULDTypeCodes(uLDStockConfigVO.getUldTypeCode(),logonAttributes)!=null){
					errors.add(new ErrorVO("uld.defaults.uldstocksetup.msg.err.invaliduldtype",	null));
				}*/
				
		log.exiting("AddULDStockSetUpCommand", "validateForm");
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
			AirportValidationVO airportValidationVO = null;
	    	Collection<ErrorVO> errors = null;
	    	
	    	try {
				AreaDelegate delegate = new AreaDelegate();
				airportValidationVO = delegate.validateAirportCode(
						logonAttributes.getCompanyCode(),station);			

	    	} catch (BusinessDelegateException e) {
				e.getMessage();
				log.log(Log.FINE, " Error Airport ---> ", e.getMessageVO().getErrorType());
				errors = handleDelegateException(e);
			}
		    	return errors;
	    }
	 
	   private AirlineValidationVO validateAlphaAWBPrefix(String compCode,String ownerId)
		throws BusinessDelegateException{
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
	/*	private AirlineValidationVO validateAWBPrefix(String awbPrefix,
				String compCode) 
					throws BusinessDelegateException{
				AirlineValidationVO airlineValidationVO = null;
				AirlineDelegate airlineDelegate = new AirlineDelegate();  
				try {
				airlineValidationVO = airlineDelegate.validateNumericCode(
				compCode,awbPrefix);
				}catch(BusinessDelegateException businessDelegateException){
//printStackTrrace()();
				}
				return airlineValidationVO;
	}*/
	   
	
}
