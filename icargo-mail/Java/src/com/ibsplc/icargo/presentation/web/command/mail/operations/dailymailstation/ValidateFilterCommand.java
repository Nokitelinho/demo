/*
 * ValidateFilterCommand.java Created on Jul 1 2016
 *
 * Copyright 2008 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.dailymailstation;
import java.util.ArrayList;
import java.util.Collection;
import com.ibsplc.icargo.business.shared.airline.vo.AirlineValidationVO;
import com.ibsplc.icargo.business.shared.area.airport.vo.AirportValidationVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.airline.AirlineDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.area.AreaDelegate;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.DailyMailStationForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
/**
 * @author A-5991
 *
 */
public class ValidateFilterCommand extends BaseCommand{


		private Log log = LogFactory.getLogger("DailyMailStation");
		private static final String GENERATE_FAILURE = "generate_failure";
		private static final String GENERATE_SUCCESS = "generate_success";
		private static final String MODULENAME = "mail.operations";
		private static final String SCREENID = "mailtracking.defaults.DailyMailStation";
		private static final String PRODUCTCODE = "mail";
		private static final String SUBPRODUCTCODE = "operations";

		 /**
	     * Screen id
	     */
	    private static final String SCREEN_ID_FLIGHT = "flight.operation.maintainflight";

	    /**
	     * Module name
	     */
	    private static final String MODULE_NAME_FLIGHT =  "flight.operation";
	    /**
	     *  Status of flag
	     */
	    private static final String OUTBOUND = "O";

		/**
		 * execute method
		 * @param invocationContext
		 * @throws CommandInvocationException
		*/
		public void execute(InvocationContext invocationContext) throws CommandInvocationException {


		    	log.log(Log.FINE, "Inside ValidateReportCommand---------->  ");
				ApplicationSessionImpl applicationSession = getApplicationSession();
				LogonAttributes logonAttributes  =  applicationSession.getLogonVO();
				DailyMailStationForm form =(DailyMailStationForm)invocationContext.screenModel;

				Collection<ErrorVO> errorsMail = new ArrayList<ErrorVO>();
				Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
				String companyCode = logonAttributes.getCompanyCode().toUpperCase();

				/*if((form.getFlightDate()==null)||("".equals(form.getFlightDate()))){
					errorsMail.add(new ErrorVO("mailtracking.defaults.dailymailstation.msg.err.datemandatory"));
					invocationContext.addAllError(errorsMail);
	     			invocationContext.target = GENERATE_FAILURE;
	     			form.setValidreport("INVALID");
	     			return;
				}*/
				//Commented FlightDate and Added flightFromDate and FlightToDate by A-6991 for CR_ICRD-197259 Starts
				if((form.getFlightFromDate()==null)||("".equals(form.getFlightFromDate()))){
					errorsMail.add(new ErrorVO("mailtracking.defaults.dailymailstation.msg.err.datemandatory"));//Modified by A-7531 for icrd-216959
					invocationContext.addAllError(errorsMail);
	     			invocationContext.target = GENERATE_FAILURE;
	     			form.setValidreport("INVALID");
	     			return;
				}
	     			if((form.getFlightToDate()==null)||("".equals(form.getFlightToDate()))){
						errorsMail.add(new ErrorVO("mailtracking.defaults.dailymailstation.msg.err.datemandatory"));//Modified by A-7531 for icrd-216959
						invocationContext.addAllError(errorsMail);
		     			invocationContext.target = GENERATE_FAILURE;
		     			form.setValidreport("INVALID");
		     			return;
				}


		     	//Commented FlightDate and Added flightFromDate and FlightToDate by A-6991 for CR_ICRD-197259 Starts


					if((form.getCarrierCode()!=null)&&(!"".equals(form.getCarrierCode()))){
						if((form.getFlightNumber()==null)||("".equals(form.getFlightNumber()))){
							errorsMail.add(new ErrorVO("mailtracking.defaults.dailymailstation.msg.err.fltnummandatory"));
							invocationContext.addAllError(errorsMail);
			     			invocationContext.target = GENERATE_FAILURE;
			     			form.setValidreport("INVALID");
			     			return;
						}
					}

					if((form.getFlightNumber()!=null)&&(!"".equals(form.getFlightNumber()))){
						if((form.getCarrierCode()==null)||("".equals(form.getCarrierCode()))){
							errorsMail.add(new ErrorVO("mailtracking.defaults.dailymailstation.msg.err.carriercodemandatory"));
							invocationContext.addAllError(errorsMail);
			     			invocationContext.target = GENERATE_FAILURE;
			     			form.setValidreport("INVALID");
			     			return;
						}
					}

//					if((((form.getCarrierCode()==null)&&(form.getFlightNumber()==null))
//							|| ("".equals(form.getCarrierCode())&&("".equals(form.getFlightNumber()))))
//							&& ((form.getDestination()==null)||("".equals(form.getDestination())))){
//						errorsMail.add(new ErrorVO("mailtracking.defaults.dailymailstation.msg.err.bothcannotbenull"));
//						invocationContext.addAllError(errorsMail);
//		     			invocationContext.target = GENERATE_FAILURE;
//		     			form.setValidreport("INVALID");
//		     			return;
//				   }


				//Setting Default Fields to hidden form attributes
				  form.setAccompanyCode(companyCode);
			      form.setAcorigin(logonAttributes.getStationCode());
			    //----setting flight date after validation to hidden form attributes
			      //form.setAcfilghtDate(form.getFlightDate());
			      //Commented AcfilghtDate and Added AcfilghtFromDate,AcfilghtToDate by A-6991 for CR_ICRD-197259
			      form.setAcfilghtFromDate(form.getFlightFromDate());
			      form.setAcfilghtToDate(form.getFlightToDate());
				//-------------------Validating and getting all the additional features for filterVO-----------------------------

			      //-----set up all delegates

			      AirlineDelegate airlineDelegate = new AirlineDelegate();
			      AreaDelegate areaDelegate = new AreaDelegate();

			    //-------Validate Airport if Destination is given
			    	String destination=form.getDestination();
			    	AirportValidationVO airportValidationVO = null;
				     	if (destination != null && !"".equals(destination)) {
				     		try {
				     			airportValidationVO = areaDelegate.validateAirportCode(logonAttributes.getCompanyCode(),destination.toUpperCase());
				     		}catch (BusinessDelegateException businessDelegateException) {
				     			errors = handleDelegateException(businessDelegateException);
				     		}
				     		if (errors != null && errors.size() > 0) {
				     			form.setValidreport("INVALID");
				     			errorsMail.add(new ErrorVO("mailtracking.defaults.invalidairport",
						   				new Object[]{destination.toUpperCase()}));
				     		}else{

				     			//add Destination to hidden form attributes
				     			form.setAcdestination(destination.toUpperCase());
				     			form.setValidreport("OK");
						    	invocationContext.target = GENERATE_SUCCESS;
				     		}

				     	}else
				     	{
				     		//------Destination Filter Given as Empty
				     		form.setAcdestination("");
							form.setValidreport("OK");
							invocationContext.target = GENERATE_SUCCESS;
				     	}


				     	if (errorsMail != null && errorsMail.size() > 0) {
				     		invocationContext.addAllError(errorsMail);
			     			invocationContext.target = GENERATE_FAILURE;
			     			form.setValidreport("INVALID");
			     			return;
				     	}

			    //------------Validate Flight if Flight number is given

			    	/*******************************************************************
					 *  validate Airline using airline code from the form
					 ******************************************************************/

			    	AirlineValidationVO airlineValidationVO = null;
				    String flightCarrierCode = form.getCarrierCode().trim().toUpperCase();
			    	if (flightCarrierCode != null && !"".equals(flightCarrierCode)) {
			    		try {
			    			airlineValidationVO = airlineDelegate.validateAlphaCode(logonAttributes.getCompanyCode(),flightCarrierCode);

			    		}catch (BusinessDelegateException businessDelegateException) {
			    			errors = handleDelegateException(businessDelegateException);
			    		}
			    		if (errors != null && errors.size() > 0) {
			    			errors = new ArrayList<ErrorVO>();
			    			Object[] obj = {flightCarrierCode};
							ErrorVO errorVO = new ErrorVO("mailtracking.defaults.invalidcarrier",obj);
							errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
							errors.add(errorVO);
			    			invocationContext.addAllError(errors);
			    			invocationContext.target = GENERATE_FAILURE;
			    			form.setValidreport("INVALID");
			    			return;
			    		}

			    		//--------set Carrier Code fltnum and carid to hidden form attributes
			    		form.setAccarrierCode(flightCarrierCode);
			    		form.setAcflightNumber(form.getFlightNumber().trim());
						form.setAcflightCarrireID(airlineValidationVO.getAirlineIdentifier());
						form.setValidreport("OK");
			    		form.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
						invocationContext.target = GENERATE_SUCCESS;
					}else{
						//----flight number filter given as empty
						form.setAccarrierCode("");
		    			form.setAcflightNumber("");
						form.setAcflightCarrireID(0);
						form.setAcflightSeqNumber(0);
						form.setValidreport("OK");
						invocationContext.target = GENERATE_SUCCESS;
		    		}

		}

}