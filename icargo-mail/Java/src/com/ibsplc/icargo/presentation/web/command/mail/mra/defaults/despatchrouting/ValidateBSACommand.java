	
	/*
	 * ValidateBSACommand.java Created on June 06, 2018
	 *
	 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
	 *
	 * This software is the proprietary information of IBS Software Services (P) Ltd.
	 * Use is subject to license terms.
	 */
	package com.ibsplc.icargo.presentation.web.command.mail.mra.defaults.despatchrouting;
	import com.ibsplc.icargo.business.mail.mra.defaults.vo.DSNRoutingVO;
	import com.ibsplc.icargo.business.shared.airline.vo.AirlineValidationVO;
	import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
	import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
	import com.ibsplc.icargo.framework.util.time.LocalDate;
	import com.ibsplc.icargo.framework.util.time.Location;
	import com.ibsplc.icargo.framework.web.command.BaseCommand;
	import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
	import com.ibsplc.icargo.framework.web.command.InvocationContext;
	import com.ibsplc.icargo.presentation.delegate.mail.mra.MailTrackingMRADelegate;
	import com.ibsplc.icargo.presentation.delegate.shared.airline.AirlineDelegate;
	import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.DSNRoutingSession;
	import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.DespatchRoutingForm;
	import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
	import com.ibsplc.xibase.util.log.Log;
	import com.ibsplc.xibase.util.log.factory.LogFactory;

	/**
	 * @author  A-8061
	 * Command class for ValidateBSACommand   
	 *
	 * Revision History
	 *
	 * Version      Date           Author          		    Description
	 *
	 *  0.1        Jun 06,2018     A-8061					Initial draft
	 */
	public class ValidateBSACommand extends BaseCommand {

		private Log log = LogFactory.getLogger("DespatchRouting ScreenloadCommand");
		private static final String CLASS_NAME = "ScreenLoadCommand";
		private static final String MODULE_NAME = "mailtracking.mra.defaults";
		private static final String SCREEN_ID = "mailtracking.mra.defaults.despatchrouting";
		
		/*
		 * Target mappings for succes 
		 */
		private static final String ACTION_SUCCESS = "screenload_success";
		
		/**
		 * @param invocationContext
		 * @exception CommandInvocationException
		 */
	    public void execute(InvocationContext invocationContext)
	            throws CommandInvocationException {

	    	log.entering(CLASS_NAME, "execute");
	    	DespatchRoutingForm form=(DespatchRoutingForm)invocationContext.screenModel;
	    	DSNRoutingSession  dsnRoutingSession = 
				(DSNRoutingSession)getScreenSession(MODULE_NAME, SCREEN_ID);
	    	form.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
	    	form.setAirport(getApplicationSession().getLogonVO().getStationCode());
	    	MailTrackingMRADelegate mailTrackingMRADelegate = new MailTrackingMRADelegate();
	    	LogonAttributes logonAttributes = getApplicationSession().getLogonVO();
	    	String bsaValidationStus="";
	    	DSNRoutingVO dSNRoutingVO =new DSNRoutingVO();
	    	

	    	if (form.getSelectedFlightCarrierCode() == null || form.getSelectedFlightCarrierCode().trim().length() == 0
				|| form.getSelectedFlightNumber() == null || form.getSelectedFlightNumber().trim().length() == 0
				|| form.getSelectedPol() == null || form.getSelectedPol().trim().length() == 0
				|| form.getSelectedPou() == null || form.getSelectedPou().trim().length() == 0
				|| form.getSelectedDepartureDate() == null || form.getSelectedDepartureDate().trim().length() == 0) {

	    		form.setBsaValidationStatus(bsaValidationStus);
				invocationContext.target = ACTION_SUCCESS;
				return;
	    	}
	    	
	    	
	    	
	    	AirlineValidationVO airlineValidationVO = null;			
			try {
				airlineValidationVO =
					new AirlineDelegate().validateAlphaCode(
							logonAttributes.getCompanyCode(), form.getSelectedFlightCarrierCode().toUpperCase());

			} catch (BusinessDelegateException e) {
				log.log(Log.INFO, "BusinessDelegateException", e);  	
			}
			
			
	    	
	    	dSNRoutingVO.setCompanyCode(logonAttributes.getCompanyCode());
	    	dSNRoutingVO.setFlightNumber(form.getSelectedFlightNumber());
	    	dSNRoutingVO.setPol(form.getSelectedPol().toUpperCase());
	    	dSNRoutingVO.setPou(form.getSelectedPou().toUpperCase());
	    	dSNRoutingVO.setDepartureDate(new LocalDate(LocalDate.NO_STATION,
					Location.NONE, false).setDate( form.getSelectedDepartureDate() ));
	    	
	    	dSNRoutingVO.setBlockSpaceType(form.getSelectedBlockSpaceType());
	    	dSNRoutingVO.setFlightCarrierCode(form.getSelectedFlightCarrierCode());
	    	
	    	if(airlineValidationVO!=null){
	    		dSNRoutingVO.setFlightCarrierId(airlineValidationVO.getAirlineIdentifier());
	    	}
	    	

	    	if(dSNRoutingVO!=null && logonAttributes.getOwnAirlineCode().equals(dSNRoutingVO.getFlightCarrierCode())){
	    		bsaValidationStus="nobsaforowncarrier";
	    		form.setBsaValidationStatus(bsaValidationStus);
	    		invocationContext.target = ACTION_SUCCESS;
	    		return;
	    	}

	    	try {
				 bsaValidationStus = mailTrackingMRADelegate.validateBSA(dSNRoutingVO);
			} catch (BusinessDelegateException e) {
				log.log(Log.INFO, "bsaValidationStus", bsaValidationStus);  
			}
	    	
	    	form.setBsaValidationStatus(bsaValidationStus);
			invocationContext.target = ACTION_SUCCESS;
			log.exiting(CLASS_NAME, "execute");
	    }

	}


