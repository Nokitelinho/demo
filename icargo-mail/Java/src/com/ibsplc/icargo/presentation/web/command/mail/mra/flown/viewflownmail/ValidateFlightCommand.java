/*
 * ValidateFlightCommand.java Created on Feb 14, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.presentation.web.command.mail.mra.flown.viewflownmail;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.icargo.business.flight.operation.vo.FlightFilterVO;
import com.ibsplc.icargo.business.flight.operation.vo.FlightValidationVO;
import com.ibsplc.icargo.business.shared.airline.vo.AirlineValidationVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.mra.MailTrackingMRADelegate;
import com.ibsplc.icargo.presentation.delegate.shared.airline.AirlineDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.flight.operation.DuplicateFlightSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.flown.ViewFlownMailSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.flown.ViewFlownMailForm;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-2449
 *
 */
public class ValidateFlightCommand extends BaseCommand{
	
	private Log log = LogFactory.getLogger("MRA FLOWN");
	
	private static final String CLASS_NAME = "ValidateFlightCommand";
	
	private static final String MODULE_NAME = "mailtracking.mra.flown";
	
	private static final String SCREENID = "mra.flown.viewflownmail";
	
	private static final String SCREENID_DUPLICATE_FLIGHTS = "flight.operation.duplicateflight";
	
	private static final String VALIDATE_SUCCESS = "action_success";
	
	private static final String VALIDATE_FAILURE = "action_failure";
	
	private static final String INVALID_CARRIERCODE = "mra.flown.msg.err.invalidcarriercode";
	
	private static final String INVALID_FLIGHT = "mra.flown.msg.err.invalidflight";
	
	
	/**
	 * Execute method
	 *
	 * @param invocationContext InvocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
	throws CommandInvocationException {
		log.entering(CLASS_NAME, "execute");
		ViewFlownMailSession session = (ViewFlownMailSession)getScreenSession(MODULE_NAME, SCREENID);
		DuplicateFlightSession duplicateFlightSession = 
			(DuplicateFlightSession)getScreenSession(MODULE_NAME,SCREENID_DUPLICATE_FLIGHTS); 
		
		ViewFlownMailForm form = (ViewFlownMailForm)invocationContext.screenModel;
		
		
		FlightFilterVO flightFilterVO = new FlightFilterVO();
		AirlineValidationVO airlineValidationVO = null;
		
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		
		if(invocationContext.getErrors() != null && invocationContext.getErrors().size() > 0){
			log.log(Log.INFO, "errors-------->", invocationContext.getErrors());
			invocationContext.target = VALIDATE_FAILURE;
			return;
		}
		else{
			log.log(log.INFO,"Entering validation for flight....");
			String companyCode = getApplicationSession().getLogonVO().getCompanyCode();
			
			try {
				airlineValidationVO = new AirlineDelegate().validateAlphaCode(
						companyCode,form.getCarrierCode());
			}
			catch(BusinessDelegateException businessDelegateException){
				errors = handleDelegateException(businessDelegateException);
			}
			
			
			if(airlineValidationVO == null){
				ErrorVO errorVO = new ErrorVO(INVALID_CARRIERCODE);
				errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
				errors.add(errorVO);
				invocationContext.addAllError(errors);
				invocationContext.target = "VALIDATE_FAILURE";
				return;
				
			}
			else{
				log.log(Log.INFO, "airlineValidationVO------->",
						airlineValidationVO);
				flightFilterVO.setCompanyCode(companyCode);
				flightFilterVO.setCarrierCode(form.getCarrierCode());
				flightFilterVO.setFlightNumber(form.getFlightNumber());
				
				LocalDate flightDate = new LocalDate(LocalDate.NO_STATION,Location.NONE,false);
				flightDate.setDate(form.getFlightDate());
				flightFilterVO.setLoadPlanFlightDate(flightDate);
				/*flightFilterVO.setFlightDate(flightDate);
				flightFilterVO.setStationCode(getApplicationSession().getLogonVO().getStationCode());
				*/
				flightFilterVO.setFlightCarrierId(airlineValidationVO.getAirlineIdentifier());
				flightFilterVO.setDirection(FlightFilterVO.OUTBOUND);
				MailTrackingMRADelegate delegate = new MailTrackingMRADelegate();
				
				Collection<FlightValidationVO> flightValidationVOs = new ArrayList<FlightValidationVO>();
				try{
					flightValidationVOs = delegate.validateFlight(flightFilterVO);
				}catch(BusinessDelegateException businessDelegateException){
					log.log(Log.FINE,"inside updateAirline caught busDelegateExc");
					handleDelegateException(businessDelegateException);
				}
				
				log.log(Log.INFO,
						"flightValidationVOs from ValidateFlightCommand--->",
						flightValidationVOs);
				if (flightValidationVOs == null || flightValidationVOs.size() <= 0) {
					log.log(Log.FINE, "flightValidationVOs is NULL");
					ErrorVO errorVO = new ErrorVO(INVALID_FLIGHT);
					errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
					errors.add(errorVO);
					invocationContext.addAllError(errors);
					invocationContext.target =VALIDATE_FAILURE;
					return;
				} 
				else if(flightValidationVOs.size() == 1){
					log.log(Log.FINE, "flightValidationVOs has one VO");
					
					FlightValidationVO flightValidationVO = 
						((ArrayList<FlightValidationVO>)flightValidationVOs).get(0);	
					
					session.setFlightDetails(flightValidationVO);
					log
							.log(
									Log.FINE,
									"flightValidationVO in validateflight command class ===",
									flightValidationVO);
					log.log(Log.FINE, "flightValidationVO  in session ",
							session.getFlightDetails());
					invocationContext.target =VALIDATE_SUCCESS;
				}
				else{
					duplicateFlightSession.setFlightValidationVOs((ArrayList<FlightValidationVO>)flightValidationVOs);
					duplicateFlightSession.setParentScreenId(SCREENID);
					duplicateFlightSession.setFlightFilterVO(flightFilterVO);
					log.log(Log.INFO, "duplicate VOs----->",
							flightValidationVOs);
					form.setDuplicateFlightFlag("Y");
					}
			}
		}
		
		log.exiting(CLASS_NAME, "execute");
	}
}






