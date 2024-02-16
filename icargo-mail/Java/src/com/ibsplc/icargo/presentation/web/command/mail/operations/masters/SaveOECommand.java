/*
 * SaveOECommand.java Created on June 19, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.masters;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.mail.operations.vo.OfficeOfExchangeVO;
import com.ibsplc.icargo.business.shared.area.airport.vo.AirportValidationVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.OfficeOfExchangeMasterSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.OfficeOfExchangeMasterForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.icargo.presentation.delegate.shared.area.AreaDelegate;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
/**
 * @author A-2047
 *
 */
public class SaveOECommand extends BaseCommand {

	private static final String SUCCESS = "save_success";
	private static final String FAILURE = "save_failure";
	private Log log = LogFactory.getLogger("Mailtracking,SaveOECommand");
	
	private static final String MODULE_NAME = "mail.operations";
	private static final String SCREEN_ID = 
							"mailtracking.defaults.masters.officeofexchange";
	
	private static final String EXG_OFC_EMPTY = 
						"mailtracking.defaults.oemaster.msg.err.exchangeOfficeEmpty";
	private static final String CITY_EMPTY = 
							"mailtracking.defaults.oemaster.msg.err.cityEmpty";
	private static final String PA_EMPTY = 
		"mailtracking.defaults.oemaster.msg.err.paEmpty";
	private static final String COUNTRY_EMPTY =
			"mailtracking.defaults.oemaster.msg.err.countryEmpty";
	//private static final String ARPCOD_INVALID = "mailtracking.defaults.oemaster.msg.err.arpcodEmpty";
		
/*	private static final String DUPLICATE_CODE = 
							"mailtracking.defaults.oemaster.msg.err.dupeCode";
	private static final String SAVE_SUCCESS = 
						"mailtracking.defaults.oemaster.msg.info.savesuccess";
	private static final String DELETE_SUCCESS = 
		"mailtracking.defaults.oemaster.msg.info.deletesuccess";*/
	
	
	/**
	 * This method overrides the executre method of BaseComand class
	 * @param invocationContext
	 * @return
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
									throws CommandInvocationException {  
		
    	log.log(Log.FINE, "\n\n in the save command----------> \n\n");
    
    	OfficeOfExchangeMasterForm oeMasterForm =
					(OfficeOfExchangeMasterForm)invocationContext.screenModel;
		OfficeOfExchangeMasterSession oeSession = 
										getScreenSession(MODULE_NAME,SCREEN_ID);
    	
    	ApplicationSessionImpl applicationSessionImpl = getApplicationSession();
		LogonAttributes logonAttributes = applicationSessionImpl.getLogonVO();
	    
	    OfficeOfExchangeVO oeVO = oeSession.getOfficeOfExchangeVO();
	    
	    MailTrackingDefaultsDelegate delegate = 
	    								new MailTrackingDefaultsDelegate();
	    
    	
	    Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
	    
	    if(oeMasterForm.getCode() == null || oeMasterForm.getCode().length() == 0) {
			ErrorVO error = new ErrorVO(EXG_OFC_EMPTY);
			errors.add(error);
		}
		if(oeMasterForm.getCityCode()== null || oeMasterForm.getCityCode().length() == 0) {
			ErrorVO error = new ErrorVO(CITY_EMPTY);
			errors.add(error);
		}
		if(oeMasterForm.getPoaCode() == null || oeMasterForm.getPoaCode().length() == 0 ) {
			ErrorVO error = new ErrorVO(PA_EMPTY);
			errors.add(error);
		}
		if(oeMasterForm.getCountryCode() == null || oeMasterForm.getCountryCode().length() == 0 ) {
			ErrorVO error = new ErrorVO(COUNTRY_EMPTY);
			errors.add(error);
		}
		//Added by A-5945 for ICRD 71956
		  if(oeMasterForm.getAirportCode()!=null && oeMasterForm.getAirportCode().trim().length()>0){
			  AirportValidationVO airportValidationVO = null;
			  AreaDelegate areaDelegate = new AreaDelegate();  
				
		    	String airportCode = oeMasterForm.getAirportCode(); 
				try {                           
		            airportValidationVO = areaDelegate.validateAirportCode(
		                            logonAttributes.getCompanyCode(),airportCode.toUpperCase());
		    }catch (BusinessDelegateException businessDelegateException) {
		        errors = handleDelegateException(businessDelegateException);    
		    }  
		    log.log(Log.FINE, "\n\n Airport VAlidation Vo----------> ", airportValidationVO);
		  }		 
		  //A-5945 ends
		 
		if(errors != null && errors.size()>0) {
			invocationContext.addAllError(errors);
			
			invocationContext.target = FAILURE;
	    	return;
		}
		if (oeVO == null) {
    		oeVO = new OfficeOfExchangeVO();
		}else {
			oeVO = updateVO(oeVO,oeMasterForm,logonAttributes);
		}
		 
	    
		log.log(Log.FINE, "\n\n oeVO----------> ", oeVO);
		Collection<OfficeOfExchangeVO> oeVOs =
										new ArrayList<OfficeOfExchangeVO>();
		oeVOs.add(oeVO);
		
		//Added by A-5945 for ICRD 71956
		try {
			delegate.saveOfficeOfExchange(oeVOs);
		}catch(BusinessDelegateException businessDelegateException) {
			errors = handleDelegateException(businessDelegateException);
		}
		 //A-5945 ends
		if(errors != null && errors.size()>0) {
			invocationContext.addAllError(errors);
//			oeSession.setOfficeOfExchangeVOs(oeVOs);
			invocationContext.target = FAILURE;
	    	return;
		}
		
		if(("DELETE").equals(oeMasterForm.getStatus())){
//			ErrorVO error = new ErrorVO(DELETE_SUCCESS);
//			errors.add(error);
			String offExchange = new StringBuilder()
			 .append("D")
			 .append("-")
			 .append(oeVO.getCode()).toString();
			oeMasterForm.setOoeInfo(offExchange);
		}else{			
			String offExchange = new StringBuilder()
								 .append("S")
								 .append("-")
								 .append(oeVO.getCode()).toString(); 
			oeMasterForm.setOoeInfo(offExchange);
//			Object[] obj = {offExchange};
//			ErrorVO error = new ErrorVO(SAVE_SUCCESS,obj);
//			errors.add(error);
		}
		invocationContext.addAllError(errors);
		oeMasterForm.setOfficeOfExchange("");
		oeMasterForm.setStatus("");
		oeMasterForm.setPopUpStatus("CLOSE");
//		oeSession.setOfficeOfExchangeVO(null);
		invocationContext.target = SUCCESS;	

	}
	
	/**
     * Method to update the OfficeOfExchangeVO in session
     * @param officeOfExchangeVO
     * @param officeOfExchangeMasterForm
     * @param logonAttributes
     * @return
     */
    private OfficeOfExchangeVO updateVO(
		    		OfficeOfExchangeVO officeOfExchangeVO,
		    		OfficeOfExchangeMasterForm officeOfExchangeMasterForm,
		    		LogonAttributes logonAttributes) {
    	
    	log.entering("SaveOECommand","updateVO");
    	
    	
    	String exchangeOffice = null;
    	String country = null;
    	String office = null;
    	if(officeOfExchangeMasterForm.getCode() != null && 
    			officeOfExchangeMasterForm.getCode().trim().length() > 0) {
    		exchangeOffice = officeOfExchangeMasterForm.getCode().toUpperCase().trim();   		
    		officeOfExchangeVO.setCode(exchangeOffice);
    		if(officeOfExchangeMasterForm.getCode().length() == 6){
    		country = exchangeOffice.toUpperCase().trim().substring(0, 2);
    		office = exchangeOffice.toUpperCase().trim().substring(5, 6);
        //commented by A-7815 as part of IASCB-50368
		//officeOfExchangeVO.setCountryCode(country);
		officeOfExchangeVO.setOfficeCode(office);
    	}  
    	}
    	//added by A-7815 as part of IASCB-50368, review by soncy
    	if(officeOfExchangeMasterForm.getCountryCode()!=null && 
    			officeOfExchangeMasterForm.getCountryCode().trim().length()>0) {
    		officeOfExchangeVO.setCountryCode(officeOfExchangeMasterForm.getCountryCode());
    	}
		officeOfExchangeVO.setCompanyCode(logonAttributes.getCompanyCode());
/*		officeOfExchangeVO.setCode(exchangeOffice);
		officeOfExchangeVO.setCountryCode(country);
		officeOfExchangeVO.setOfficeCode(office);*/
		officeOfExchangeVO.setCityCode(
				officeOfExchangeMasterForm.getCityCode().toUpperCase().trim());
		officeOfExchangeVO.setPoaCode(
				officeOfExchangeMasterForm.getPoaCode().toUpperCase().trim());
		//Added by A-5945 for ICRD-71956 starts
		officeOfExchangeVO.setAirportCode(officeOfExchangeMasterForm.getAirportCode());
		//a-5945 ends
		//Added as part of CRQ ICRD-111886 by A-5526 starts
		officeOfExchangeVO.setMailboxId(officeOfExchangeMasterForm.getMailboxId());
		//Added as part of CRQ ICRD-111886 by A-5526 ends
		officeOfExchangeVO.setCodeDescription(
				officeOfExchangeMasterForm.getCodeDescription());
		if(("true").equals(officeOfExchangeMasterForm.getActive())) {
			officeOfExchangeVO.setActive(true);
		}else {
			officeOfExchangeVO.setActive(false);
		}
		if(("ADD").equals(officeOfExchangeMasterForm.getStatus())){
			officeOfExchangeVO.setOperationFlag
									(OfficeOfExchangeVO.OPERATION_FLAG_INSERT);
			officeOfExchangeVO.setLastUpdateUser(logonAttributes.getUserId().toUpperCase());
		}else if(("DELETE").equals(officeOfExchangeMasterForm.getStatus())){
			officeOfExchangeVO.setOperationFlag
									(OfficeOfExchangeVO.OPERATION_FLAG_DELETE);
		}else {
			officeOfExchangeVO.setOperationFlag
									(OfficeOfExchangeVO.OPERATION_FLAG_UPDATE);
		}
		

    	log.exiting("SaveOECommand","updateVO");
    	
    	return officeOfExchangeVO;    	
    }

}

