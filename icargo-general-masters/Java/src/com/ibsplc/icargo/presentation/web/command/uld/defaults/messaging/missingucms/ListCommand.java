/*
 * ListCommand.java Created on Jul 30, 2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.messaging.missingucms;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.shared.airline.vo.AirlineValidationVO;
import com.ibsplc.icargo.business.uld.defaults.message.vo.ULDFlightMessageReconcileVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.airline.AirlineDelegate;
import com.ibsplc.icargo.presentation.delegate.uld.defaults.ULDDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.messaging.MissingUCMListSession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.messaging.MissingUCMListForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import com.ibsplc.xibase.util.time.DateUtilities;

/**
 * @author A-3459
 *
 */
public class ListCommand extends BaseCommand{
	
	private static final String LIST_SUCCESS = "list_success";
	private static final String LIST_FAILURE = "list_failure";
    private static final String SCREENID = "uld.defaults.missingucmlist";
    private static final String MODULE = "uld.defaults";
	private Log log = LogFactory.getLogger("ListCommand");
	private static final String CHECKBOX_CONST = "on";
	/**
     * execute method
     * @param invocationContext
     * @throws CommandInvocationException
    */
	public void execute(InvocationContext invocationContext)
     	throws CommandInvocationException {
		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes  =  applicationSession.getLogonVO();
		log.entering("ListCommand---------------->>>>","Entering");
		MissingUCMListForm missingUCMListForm = (MissingUCMListForm) invocationContext.screenModel;
		MissingUCMListSession missingUCMListSession=(MissingUCMListSession)getScreenSession(MODULE, SCREENID);
		ULDFlightMessageReconcileVO uldFlightMessageReconcileVO=new ULDFlightMessageReconcileVO();
    	log.log(Log.FINE,
				"------UcmInMissed from form---------------------->>",
				missingUCMListForm.getUcmIn());
		log.log(Log.FINE,
				"------UcmOutMissed from form---------------------->>",
				missingUCMListForm.getUcmOut());
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
    	String companyCode=logonAttributes.getCompanyCode();
    	String carrierCode=missingUCMListForm.getCarrierCode();
    	uldFlightMessageReconcileVO.setCompanyCode(logonAttributes.getCompanyCode());
    	uldFlightMessageReconcileVO.setPageNumber(Integer.parseInt(missingUCMListForm.getDisplayPage()));
    	if(missingUCMListForm.getCarrierCode()!= null && missingUCMListForm.getCarrierCode().trim().length() > 0 ){
    		if(validateAirlineCode(companyCode,carrierCode,uldFlightMessageReconcileVO)==null){
    			uldFlightMessageReconcileVO.setCarrierCode(missingUCMListForm.getCarrierCode());
    		}else{
    			ErrorVO error = new ErrorVO("uld.defaults.missingucmlist.invalidairline",new Object[]{missingUCMListForm.getCarrierCode()});
    			errors.add(error);
    			log.log(Log.FINE, "inside else", errors);
				invocationContext.addAllError(errors);
     			invocationContext.target=LIST_FAILURE;
     			return;
    		}
    	}
    	if(missingUCMListForm.getFlightNumber()!= null && missingUCMListForm.getFlightNumber().trim().length() > 0){
    		uldFlightMessageReconcileVO.setFlightNumber(missingUCMListForm.getFlightNumber());
    	}
    	if(missingUCMListForm.getFromDate()!= null && missingUCMListForm.getFromDate().trim().length() > 0){
    		LocalDate fDate = new LocalDate(logonAttributes.getAirportCode(),Location.ARP,false);
    		uldFlightMessageReconcileVO.setFromDate(fDate.setDate(missingUCMListForm.getFromDate()));
    	}
    	if(missingUCMListForm.getToDate()!= null && missingUCMListForm.getToDate().trim().length() > 0 ){
    		LocalDate tDate = new LocalDate(logonAttributes.getAirportCode(),Location.ARP,false);
    		uldFlightMessageReconcileVO.setToDate((tDate.setDate(missingUCMListForm.getToDate())));
		}
    	if(missingUCMListForm.getOrigin()!= null && missingUCMListForm.getOrigin().trim().length() > 0 ){
    		uldFlightMessageReconcileVO.setOrigin(missingUCMListForm.getOrigin());
		}
    	if(missingUCMListForm.getDestination()!= null && missingUCMListForm.getDestination().trim().length() > 0 ){
    		uldFlightMessageReconcileVO.setDestination(missingUCMListForm.getDestination());
    	}
    	if(CHECKBOX_CONST.equals(missingUCMListForm.getUcmOut())){
    		uldFlightMessageReconcileVO.setUcmOutMissed(ULDFlightMessageReconcileVO.UCMOUT_MISSED);
    	} else {
			uldFlightMessageReconcileVO.setUcmOutMissed(null);
		}
    	log.log(Log.INFO, "UcmOut missed in the form", missingUCMListForm.getUcmOut());
		if(CHECKBOX_CONST.equals(missingUCMListForm.getUcmIn())){
    		uldFlightMessageReconcileVO.setUcmInMissed(ULDFlightMessageReconcileVO.UCMIN_MISSED);
    	} else {
			uldFlightMessageReconcileVO.setUcmInMissed(null);
		}
    	log.log(Log.INFO, "UcmIn missed in the form", missingUCMListForm.getUcmIn());
    	uldFlightMessageReconcileVO.setTotalRecords(-1);
        if ((missingUCMListSession.getTotalRecords() != null) && (missingUCMListSession.getTotalRecords().intValue() != 0))
        {
        	uldFlightMessageReconcileVO.setTotalRecords(missingUCMListSession.getTotalRecords().intValue());
        }
        
		ULDDefaultsDelegate delegate = new ULDDefaultsDelegate();
		Page<ULDFlightMessageReconcileVO> uldFlightMessageReconcileVOs = null;
		log.log(Log.INFO, "uldFlightMessageReconcileVO",
				uldFlightMessageReconcileVO);
		Collection<ErrorVO> error = new ArrayList<ErrorVO>();
		errors = validateForm(missingUCMListForm,
				logonAttributes.getCompanyCode());
		if (errors != null && errors.size() > 0) {
 			invocationContext.addAllError(errors);
 			invocationContext.target = LIST_FAILURE;
 			return;
			}	
		
		try{
			uldFlightMessageReconcileVOs = delegate.findMissingUCMs(uldFlightMessageReconcileVO);
 			
 		}catch(BusinessDelegateException exception){
 			exception.getMessage();
 			error = handleDelegateException(exception);
 		}
 		if(uldFlightMessageReconcileVOs != null && uldFlightMessageReconcileVOs.size()>0){
 	 		log
					.log(
							Log.INFO,
							"uldFlightMessageReconcileVOs---------------------------->>",
							uldFlightMessageReconcileVOs);
			missingUCMListSession.setULDFlightMessageReconcileDetailsVOs(uldFlightMessageReconcileVOs);
			missingUCMListSession.setTotalRecords(uldFlightMessageReconcileVOs.getTotalRecordCount());
 	 		invocationContext.target=LIST_SUCCESS;
 	 	}else{
 			missingUCMListSession.setULDFlightMessageReconcileDetailsVOs(null);
 			ErrorVO errorVO = new ErrorVO("uld.defaults.missingucmlist.norecordsfound");
 			errors.add(errorVO);
 			invocationContext.addAllError(errors);
 			invocationContext.target=LIST_FAILURE;
 			return;
 		}	
	}
	/**
	 * @param companyCode
	 * @param uldFlightMessageReconcileVO
	 * @param carrierCode
	 * @return
	 */

	public Collection<ErrorVO> validateAirlineCode(
			String companyCode, String carrierCode, ULDFlightMessageReconcileVO uldFlightMessageReconcileVO) {
		Collection<ErrorVO> errors = null;
		log.log(Log.FINE, "inside validatecarrierCode--------------->>>>",
				carrierCode);
		AirlineValidationVO airlineValidationVO = null;
		AirlineDelegate airlineDelegate = new AirlineDelegate();
		if (carrierCode != null && carrierCode.trim().length() > 0) {
			log.log(Log.FINE, "inside validatecarrierCode --------------->>>>",
					carrierCode);
			try {
				airlineValidationVO = airlineDelegate.validateAlphaCode(
						companyCode, carrierCode.toUpperCase());

			} catch (BusinessDelegateException businessDelegateException) {
				errors = handleDelegateException(businessDelegateException);
				log.log(Log.FINE, "inside catch");
			}
			if (airlineValidationVO != null) {
				uldFlightMessageReconcileVO.setFlightCarrierIdentifier(airlineValidationVO
						.getAirlineIdentifier());
			}
		}
		log.log(Log.FINE, "inside errors", errors);
		return errors;
	}
	/**
     * 
     * @param missingUCMListForm
     * @param companyCode
     * @return
     */
	private Collection<ErrorVO> validateForm(
			MissingUCMListForm missingUCMListForm,String companyCode) {
    		log.log(Log.FINE,"*****************inside validate form**************");
    			Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
    			ErrorVO error = null;
    			
    			if (((!("").equals(missingUCMListForm.getFromDate())) && missingUCMListForm.getFromDate()!= null)
    					&& ((!("").equals(missingUCMListForm.getToDate())) && missingUCMListForm.getToDate()!= null)) {
    				if (!missingUCMListForm.getFromDate().equals(
    						missingUCMListForm.getToDate())) {
    					if (!DateUtilities.isLessThan(missingUCMListForm.getFromDate(), missingUCMListForm.getToDate(),
    							"dd-MMM-yyyy")) {
    						error= new ErrorVO(
    								"uld.defaults.missingucmlist.fromdatelesserthantodate");
    						error.setErrorDisplayType(ErrorDisplayType.INFO);
    						errors.add(error);
    					}
    				}
    			}	
    			return errors;
    			
    }
}
