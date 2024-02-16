/*
 * MandatoryCheckCommand.java Created on Feb 14, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */


package com.ibsplc.icargo.presentation.web.command.mail.mra.flown.viewflownmail;

import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.flown.ViewFlownMailForm;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author a-2449
 *
 */
public class MandatoryCheckCommand extends BaseCommand{
	
	private Log log = LogFactory.getLogger("MRA flown");
	private static final String ACTION_SUCCESS= "action_success";
	private static final String ACTION_FAILURE= "action_failure";
	private static final String CLASS_NAME = "MandatoryCheckCommand";
	private static final String MODULE_NAME = "mailtracking.mra.flown";
	private static final String SCREENID = "mra.flown.viewflownmail";
	
	private static final String CARRIERCODE_MANDATORY = "mra.flown.msg.err.carriercodemandatory";
	private static final String FLIGHTNUMBER_MANDATORY = "mra.flown.msg.err.flightnumbermandatory";
	private static final String FLIGHTDATE_MANDATORY = "mra.flown.msg.err.flightdatemandatory";
	private static final String ENTER_MANDATORY="mra.flown.msg.err.entermandatoryfields";
	
	/**
	 * Execute method
	 *
	 * @param invocationContext InvocationContext
	 * @throws CommandInvocationException
	 */
	
	public void execute(InvocationContext invocationContext)
	throws CommandInvocationException {
		log.entering(CLASS_NAME, "execute");
		
		/* ViewFlownMailSession session = 
			(ViewFlownMailSession)getScreenSession(MODULE_NAME, SCREENID); */
		ViewFlownMailForm form =
			(ViewFlownMailForm)invocationContext.screenModel;
		//ErrorVO error=null;  
		//Collection<ErrorVO> errors=new ArrayList<ErrorVO>();
		if(form.getCarrierCode() == null || form.getCarrierCode().trim().length() == 0){
			if(form.getFlightNumber() == null || form.getFlightNumber().trim().length() == 0){
				if(form.getFlightDate() == null || form.getFlightDate().trim().length() == 0){
					log.log(1,"error present");
					ErrorVO errorVO = new ErrorVO(ENTER_MANDATORY);
					errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
					invocationContext.addError(errorVO);
					return;	
				}
			}
		
		}
		log.log(Log.INFO, "Carrier Code from form------>", form.getCarrierCode());
		if(form.getCarrierCode() == null || form.getCarrierCode().trim().length() == 0){
			log.log(1,"error present");
			ErrorVO errorVO = new ErrorVO(CARRIERCODE_MANDATORY);
			errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
			invocationContext.addError(errorVO);
			//invocationContext.target=ACTION_FAILURE;
		
		}
		
		log.log(Log.INFO, "flight number from form----->", form.getFlightNumber());
		if(form.getFlightNumber() == null || form.getFlightNumber().trim().length() == 0){
			log.log(1,"error present");
			ErrorVO errorVO = new ErrorVO(FLIGHTNUMBER_MANDATORY);
			errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
			invocationContext.addError(errorVO);
			//invocationContext.target=ACTION_FAILURE;
			
		}
		
		log.log(Log.INFO, "flight date from form------>", form.getFlightDate());
		if(form.getFlightDate() == null || form.getFlightDate().trim().length() == 0){
			log.log(1,"error present");
			ErrorVO errorVO = new ErrorVO(FLIGHTDATE_MANDATORY);
			errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
			invocationContext.addError(errorVO);
			//invocationContext.target=ACTION_FAILURE;
			
		}
			
				
		//invocationContext.target=ACTION_SUCCESS;
		log.exiting(CLASS_NAME,"execute");
		return;
	}
	

}
