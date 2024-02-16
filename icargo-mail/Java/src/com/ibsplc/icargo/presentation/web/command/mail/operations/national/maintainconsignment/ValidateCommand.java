/**
 * 
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.national.maintainconsignment;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.ibsplc.icargo.business.flight.operation.vo.FlightFilterVO;
import com.ibsplc.icargo.business.flight.operation.vo.FlightValidationVO;
import com.ibsplc.icargo.business.mail.operations.vo.ConsignmentDocumentVO;
import com.ibsplc.icargo.business.mail.operations.vo.OperationalFlightVO;
import com.ibsplc.icargo.business.mail.operations.vo.RoutingInConsignmentVO;
import com.ibsplc.icargo.business.shared.airline.vo.AirlineValidationVO;
import com.ibsplc.icargo.business.shared.area.airport.vo.AirportValidationVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.airline.AirlineDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.area.AreaDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.ConsignmentSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.national.CaptureMailDocumentForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-3817
 *
 */
public class ValidateCommand extends BaseCommand{

	private Log log = LogFactory.getLogger("MAILTRACKING");	
	private static final String MODULE_NAME = "mail.operations";
	private static final String SCREEN_ID = "mailtracking.defaults.national.consignment";
	private static final String TARGET_SUCCESS= "success";
	private static final String TARGET_FAILURE= "success";
	private static final String VALIDATEACCP = "Y";
	private static final String OUTBOUND = "O";
	@Override
	public boolean breakOnInvocationFailure() {
		
		return true;
	}

	
	public void execute(InvocationContext invocationContext)
	throws CommandInvocationException {
		CaptureMailDocumentForm consignmentForm = (CaptureMailDocumentForm) invocationContext.screenModel;
		ConsignmentSession consignmentSession = getScreenSession(MODULE_NAME,
				SCREEN_ID);
		ConsignmentDocumentVO consignmentDocumentVO = null;
		consignmentDocumentVO = consignmentSession.getConsignmentDocumentVO();
		Collection<RoutingInConsignmentVO> routingInConsignmentVOs = null;
		Collection<ErrorVO> errorVOs = new ArrayList<ErrorVO>();
		boolean invalidFlightFlag = true;
		if(consignmentDocumentVO!= null){
			routingInConsignmentVOs = consignmentDocumentVO.getRoutingInConsignmentVOs();
			//check which will by pass after showing warning for deletion 
			//Added as part if icrd-15420 by A-4810
			
			if(!VALIDATEACCP.equals(consignmentForm.getValidateAcceptance())){
			if(consignmentDocumentVO != null){
			   
				validateConsignmentDetails(consignmentDocumentVO, errorVOs);
				if(errorVOs!= null && errorVOs.size() >0){
					//updateConsignmentDetailsForError(consignmentDocumentVO);
					invocationContext.addAllError(errorVOs);
					invocationContext.target = TARGET_FAILURE;
					return;
					
				}
				
				validatePorts(consignmentDocumentVO, errorVOs);
				if(errorVOs!= null && errorVOs.size() >0){
					//updateConsignmentDetailsForError(consignmentDocumentVO);
					invocationContext.addAllError(errorVOs);
					invocationContext.target = TARGET_FAILURE;
					return;
					
				}
			}	
				
   		       validateDuplicateFlightDetails(consignmentDocumentVO, errorVOs);
				if(errorVOs!= null && errorVOs.size() >0){
					invocationContext.addAllError(errorVOs);
					invocationContext.target = TARGET_FAILURE;
					return;
					
				}
				validateAirlineDetails(consignmentDocumentVO, errorVOs);
				if(errorVOs!= null && errorVOs.size() >0){
					invocationContext.addAllError(errorVOs);
					invocationContext.target = TARGET_FAILURE;
					return;
				}
				
				validateAcceptanceFlights(consignmentDocumentVO, errorVOs);
				if(errorVOs!= null && errorVOs.size() >0){
					invocationContext.addAllError(errorVOs);
					invocationContext.target = TARGET_FAILURE;
					return;
				}
				
				validateRoutingFlights(consignmentDocumentVO, errorVOs);
				if(errorVOs!= null && errorVOs.size() >0){
					invocationContext.addAllError(errorVOs);
					invocationContext.target = TARGET_FAILURE;
					return;
				}
				
				
				validateFlightDetails(consignmentDocumentVO, errorVOs);
				if(errorVOs!= null && errorVOs.size() >0){
					invocationContext.addAllError(errorVOs);
					invocationContext.target = TARGET_FAILURE;
					return;
				}
				
				validateSegmentDetails(consignmentDocumentVO, errorVOs);
				if(errorVOs!= null && errorVOs.size() >0){
					invocationContext.addAllError(errorVOs);
					invocationContext.target = TARGET_FAILURE;
					return;
				}
				
				
				validatePieceDetails(consignmentDocumentVO, errorVOs);
				if(errorVOs != null && errorVOs.size() >0){
					invocationContext.addAllError(errorVOs);
					invocationContext.target = TARGET_FAILURE;
					return;
					
				}
				
				
				//updateConsignmentDetails(consignmentDocumentVO);

			}
				}
		invocationContext.target = TARGET_SUCCESS;

	}
	
	/**
	 * 
	 * @param consignmentDocumentVO
	 * @param errorVOs
	 */
	private void validatePorts(ConsignmentDocumentVO consignmentDocumentVO,Collection<ErrorVO> errorVOs){

		Collection<RoutingInConsignmentVO> routingConsignemntDetails = consignmentDocumentVO.getRoutingInConsignmentVOs();
		Collection<RoutingInConsignmentVO> acceptanceConsignemntDetails = consignmentDocumentVO.getAcceptanceInfo();
		ErrorVO errorVO = null;
		AirportValidationVO   airportValidationVO = null;
	    
		if(RoutingInConsignmentVO.OPERATION_FLAG_INSERT.equals(consignmentDocumentVO.getOperationFlag())){
			airportValidationVO = validateairport(consignmentDocumentVO.getCompanyCode(), consignmentDocumentVO.getOrgin());
			if(airportValidationVO == null){				
				errorVO = new ErrorVO("mailtracking.defaults.national.maintainconsignment.notavalidairport",new Object[]{consignmentDocumentVO.getOrgin()});
				consignmentDocumentVO.setOrgin(null);
				errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
				errorVOs.add(errorVO);
				
			}

			airportValidationVO = validateairport(consignmentDocumentVO.getCompanyCode(), consignmentDocumentVO.getDestination());
			if(airportValidationVO == null){				
				errorVO = new ErrorVO("mailtracking.defaults.national.maintainconsignment.notavalidairport",new Object[]{consignmentDocumentVO.getDestination()});
				consignmentDocumentVO.setDestination(null);
				errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
				errorVOs.add(errorVO);
				
			}
			
		}
		
		if(acceptanceConsignemntDetails != null && acceptanceConsignemntDetails.size() >0){
			for(RoutingInConsignmentVO routingInConsignmentVO : acceptanceConsignemntDetails){
				if(RoutingInConsignmentVO.OPERATION_FLAG_INSERT.equals(routingInConsignmentVO.getOperationFlag()) ){

					airportValidationVO = validateairport(routingInConsignmentVO.getCompanyCode(), routingInConsignmentVO.getPol());
					if(airportValidationVO == null){
						errorVO = new ErrorVO("mailtracking.defaults.national.maintainconsignment.notavalidairport",new Object[]{routingInConsignmentVO.getPol()});
						errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
						errorVOs.add(errorVO);
						//routingInConsignmentVO.setInvalidFlightFlag(true);
					}else{
						//routingInConsignmentVO.setInvalidFlightFlag(true);
					}

					airportValidationVO = validateairport(routingInConsignmentVO.getCompanyCode(), routingInConsignmentVO.getPou());
					if(airportValidationVO == null){
						errorVO = new ErrorVO("mailtracking.defaults.national.maintainconsignment.notavalidairport",new Object[]{routingInConsignmentVO.getPou()});
						errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
						errorVOs.add(errorVO);
						//routingInConsignmentVO.setInvalidFlightFlag(true);
					}else{
						//routingInConsignmentVO.setInvalidFlightFlag(true);
					}
				}
				
			}
			
		}
		
		if(routingConsignemntDetails != null && routingConsignemntDetails.size() >0){			
			for(RoutingInConsignmentVO routingInConsignmentVO : routingConsignemntDetails){
				if(RoutingInConsignmentVO.OPERATION_FLAG_INSERT.equals(routingInConsignmentVO.getOperationFlag()) ){
					
					airportValidationVO = validateairport(routingInConsignmentVO.getCompanyCode(), routingInConsignmentVO.getPol());
					if(airportValidationVO == null){
						errorVO = new ErrorVO("mailtracking.defaults.national.maintainconsignment.notavalidairport",new Object[]{routingInConsignmentVO.getPol()});
						errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
						errorVOs.add(errorVO);
						//routingInConsignmentVO.setInvalidFlightFlag(true);
					}else{
						//routingInConsignmentVO.setInvalidFlightFlag(true);
					}

					airportValidationVO = validateairport(routingInConsignmentVO.getCompanyCode(), routingInConsignmentVO.getPou());
					if(airportValidationVO == null){
						errorVO = new ErrorVO("mailtracking.defaults.national.maintainconsignment.notavalidairport",new Object[]{routingInConsignmentVO.getPou()});
						errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
						errorVOs.add(errorVO);
						//routingInConsignmentVO.setInvalidFlightFlag(true);
					}else{
						//routingInConsignmentVO.setInvalidFlightFlag(true);
					}
				}
			}

		}
	}
	
	/**
	 * 
	 * @param companyCode
	 * @param airportCode
	 * @return
	 */
	private AirportValidationVO validateairport(String companyCode,String airportCode){
		AirportValidationVO   airportValidationVO = null;
		try{
			airportValidationVO= new AreaDelegate().validateAirportCode(companyCode, airportCode);
		}catch(BusinessDelegateException businessDelegateException){
			log.log(Log.FINE,  "BusinessDelegateException");
		}
		return airportValidationVO;
	}
	/**
	 * 
	 * @param consignmentDocumentVO
	 * @param errorVOs
	 */
	private void validateSegmentDetails(ConsignmentDocumentVO consignmentDocumentVO,Collection<ErrorVO> errorVOs){
		//Added for validating routing segment details
		Collection<RoutingInConsignmentVO> routingInConsignmentVOs  = consignmentDocumentVO.getRoutingInConsignmentVOs();
		if(routingInConsignmentVOs != null && routingInConsignmentVOs.size() >0){
			ErrorVO errorVO = null;
			for(RoutingInConsignmentVO routingInConsignmentVO : routingInConsignmentVOs){
				if(RoutingInConsignmentVO.OPERATION_FLAG_INSERT.equals(routingInConsignmentVO.getOperationFlag())
						&& routingInConsignmentVO.getSegmentSerialNumber() ==0){
					errorVO = new ErrorVO("mailtracking.defaults.national.maintainconsignment.invalidsegment");
					errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
					//routingInConsignmentVO.setInvalidFlightFlag(true);
					Object[] obj = {
							routingInConsignmentVO
							.getOnwardCarrierCode(),
							routingInConsignmentVO
							.getOnwardFlightNumber(),
							routingInConsignmentVO.getOnwardFlightDate().toDisplayDateOnlyFormat(),
							routingInConsignmentVO.getPol(),
							routingInConsignmentVO.getPou() };
					errorVO.setErrorData(obj);
					errorVOs.add(errorVO);
				}else{
					//routingInConsignmentVO.setInvalidFlightFlag(true);
				}
			}

		}
		
		// Added for validating segment details of acceptance part
		Collection<RoutingInConsignmentVO> acceptanceInfo  = consignmentDocumentVO.getAcceptanceInfo();
		if(acceptanceInfo != null && acceptanceInfo.size() >0){
			ErrorVO errorVO = null;
			for(RoutingInConsignmentVO routingInConsignmentVO : acceptanceInfo){
				if(RoutingInConsignmentVO.OPERATION_FLAG_INSERT.equals(routingInConsignmentVO.getOperationFlag())
						&& routingInConsignmentVO.getSegmentSerialNumber() ==0){
					errorVO = new ErrorVO("mailtracking.defaults.national.maintainconsignment.invalidsegment");
					errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
					//routingInConsignmentVO.setInvalidFlightFlag(true);
					Object[] obj = {
							routingInConsignmentVO
							.getOnwardCarrierCode(),
							routingInConsignmentVO
							.getOnwardFlightNumber(),
							routingInConsignmentVO.getOnwardFlightDate().toDisplayDateOnlyFormat(),
							routingInConsignmentVO.getPol(),
							routingInConsignmentVO.getPou() };
					errorVO.setErrorData(obj);
					errorVOs.add(errorVO);
				}else{
					//routingInConsignmentVO.setInvalidFlightFlag(true);
				}
			}

		}
		
		
	}
	/**
	 * 
	 * @param consignmentDocumentVO
	 * @param errorVOs
	 */
	private void validateAcceptanceFlights(ConsignmentDocumentVO consignmentDocumentVO,Collection<ErrorVO> errorVOs){

		Collection<RoutingInConsignmentVO> acceptanceConsignemntDetails = consignmentDocumentVO.getAcceptanceInfo();
		String consignmentOrigin = consignmentDocumentVO.getOrgin();
		if(acceptanceConsignemntDetails != null){
			ErrorVO errorVO = null;
			for(RoutingInConsignmentVO routingInConsignmentVO : acceptanceConsignemntDetails){
				if(RoutingInConsignmentVO.OPERATION_FLAG_INSERT.equals(routingInConsignmentVO.getOperationFlag())&& !consignmentOrigin.equals(routingInConsignmentVO.getPol())){
					errorVO = new ErrorVO("mailtracking.defaults.national.maintainconsignment.notanacceptanceflight");
					//routingInConsignmentVO.setInvalidFlightFlag(true);
					errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
					Object[] obj = {
							routingInConsignmentVO
							.getOnwardCarrierCode(),
							routingInConsignmentVO
							.getOnwardFlightNumber(),
							routingInConsignmentVO.getOnwardFlightDate().toDisplayDateOnlyFormat(),
							routingInConsignmentVO.getPol(),
							routingInConsignmentVO.getPou() };
					errorVO.setErrorData(obj);
					errorVOs.add(errorVO);

				}else{
					routingInConsignmentVO.setAcceptanceFlag(true);
					//routingInConsignmentVO.setInvalidFlightFlag(true);
				}
			}
		}

	}
	
	
	
	
	
	/**
	 * 
	 * @param consignmentDocumentVO
	 * @param errorVOs
	 */
	private void validateRoutingFlights(ConsignmentDocumentVO consignmentDocumentVO,Collection<ErrorVO> errorVOs){

		Collection<RoutingInConsignmentVO> routingConsignemntDetails = consignmentDocumentVO.getRoutingInConsignmentVOs();
		String consignmentOrigin = consignmentDocumentVO.getOrgin();
		String consignmentDestination = consignmentDocumentVO.getDestination();
		if(routingConsignemntDetails != null){
			ErrorVO errorVO = null;
			for(RoutingInConsignmentVO routingInConsignmentVO : routingConsignemntDetails){
				if(RoutingInConsignmentVO.OPERATION_FLAG_INSERT.equals(routingInConsignmentVO.getOperationFlag()) && consignmentOrigin.equals(routingInConsignmentVO.getPol())
						){
					errorVO = new ErrorVO("mailtracking.defaults.national.maintainconsignment.notanroutingflight");
					errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
					//routingInConsignmentVO.setInvalidFlightFlag(true);
					Object[] obj = {
							routingInConsignmentVO
							.getOnwardCarrierCode(),
							routingInConsignmentVO
							.getOnwardFlightNumber(),
							routingInConsignmentVO.getOnwardFlightDate().toDisplayDateOnlyFormat(),
							routingInConsignmentVO.getPol(),
							routingInConsignmentVO.getPou() };
					errorVO.setErrorData(obj);
					errorVOs.add(errorVO);

				}else{
					//routingInConsignmentVO.setInvalidFlightFlag(true);
				}
			}
		}

	}
	
	/**
	 * 
	 * @param routingInConsignmentVOs
	 * @param errorVOs
	 */
	private void validateConsignmentDetails(ConsignmentDocumentVO consignmentDocumentVO,Collection<ErrorVO> errorVOs){
		ErrorVO errorVO = null;
		if(consignmentDocumentVO != null){
			LogonAttributes logonAttributes = getApplicationSession().getLogonVO();
			if(ConsignmentDocumentVO.OPERATION_FLAG_INSERT.equals(consignmentDocumentVO.getOperationFlag())){
				if(consignmentDocumentVO.getOrgin() == null || consignmentDocumentVO.getOrgin().trim().length() <1){
					errorVO = new ErrorVO("mailtracking.defaults.national.maintainconsignment.originismandatory");
					errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
					errorVOs.add(errorVO);
				}else if(!consignmentDocumentVO.getAirportCode().equals(consignmentDocumentVO.getOrgin())){
					errorVO = new ErrorVO("mailtracking.defaults.national.maintainconsignment.invalidconsignmentorigin");
					errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
					errorVOs.add(errorVO);
					
				}if(consignmentDocumentVO.getDestination() == null ||consignmentDocumentVO.getDestination().trim().length() ==0 ){
					errorVO = new ErrorVO("mailtracking.defaults.national.maintainconsignment.dstismandatory");
					errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
					errorVOs.add(errorVO);
					
				}
				if(!validateFullDelete(consignmentDocumentVO)){
					errorVO = new ErrorVO("mailtracking.defaults.national.maintainconsignment.acceptanceinfoismandatory");
					errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
					errorVOs.add(errorVO);
					
				}
				if(!consignmentDocumentVO.getOrgin().equals(logonAttributes.getAirportCode())){
					errorVO = new ErrorVO("mailtracking.defaults.national.maintainconsignment.loginairportandoriginshouldbesame");
					errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
					errorVOs.add(errorVO);
				}
				
				
				
			}if(ConsignmentDocumentVO.OPERATION_FLAG_UPDATE.equals(consignmentDocumentVO.getOperationFlag()) && !validateFullDelete(consignmentDocumentVO)){
				errorVO = new ErrorVO("mailtracking.defaults.national.maintainconsignment.onlyconsignmentDeletionispossible");
				errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
				errorVOs.add(errorVO);
				
			}
			
			Collection<RoutingInConsignmentVO> acceptanceConsignemntDetails = consignmentDocumentVO.getAcceptanceInfo();
			if(acceptanceConsignemntDetails != null && acceptanceConsignemntDetails.size() >0){
			  for(RoutingInConsignmentVO routingInConsignmentVO : acceptanceConsignemntDetails){
				  if(RoutingInConsignmentVO.OPERATION_FLAG_INSERT.equals(routingInConsignmentVO.getOperationFlag())){
					    if(routingInConsignmentVO.getOnwardCarrierCode() == null
					    	|| routingInConsignmentVO.getOnwardFlightNumber() == null
					    	|| routingInConsignmentVO.getOnwardFlightDate() == null
					    	|| routingInConsignmentVO.getPol() == null
				            || routingInConsignmentVO.getPou() == null){
					    	  errorVO = new ErrorVO("mailtracking.defaults.national.maintainconsignment.detailsaremandatory");
					    	  errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
								errorVOs.add(errorVO);
					    }
					   
				  }
				  
			  }
				
			}else{
				errorVO = new ErrorVO("mailtracking.defaults.national.maintainconsignment.routingdetailsmandatory");
				errorVOs.add(errorVO);
			}
			
			
			Collection<RoutingInConsignmentVO> routingConsignmentDetails  = consignmentDocumentVO.getRoutingInConsignmentVOs();
			if(routingConsignmentDetails != null && routingConsignmentDetails.size() >0){
				for(RoutingInConsignmentVO routingInConsignmentVO : routingConsignmentDetails){
					if(RoutingInConsignmentVO.OPERATION_FLAG_INSERT.equals(routingInConsignmentVO.getOperationFlag())){
						if(routingInConsignmentVO.getOnwardCarrierCode() == null
								|| routingInConsignmentVO.getOnwardFlightNumber() == null
								|| routingInConsignmentVO.getOnwardFlightDate() == null
								|| routingInConsignmentVO.getPol() == null
								|| routingInConsignmentVO.getPou() == null){
							errorVO = new ErrorVO("mailtracking.defaults.national.maintainconsignment.detailsaremandatory");
							errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
							errorVOs.add(errorVO);
						}

					}

				}

			}
			
		}
		


	}
	
	
	
	/**
	 * 
	 * @param routingInConsignmentVOs
	 * @param errorVOs
	 */
	private void validateAirlineDetails(ConsignmentDocumentVO consignmentDocumentVO,Collection<ErrorVO> errorVOs){
		AirlineValidationVO airlineValidationVO ;
        
		/**
		 * for validating routing part
		 */
		Collection<RoutingInConsignmentVO> routingInConsignmentVOs  = consignmentDocumentVO.getRoutingInConsignmentVOs();
		if(routingInConsignmentVOs != null && routingInConsignmentVOs.size() >0){
			for(RoutingInConsignmentVO routingInConsignmentVO : routingInConsignmentVOs){
				airlineValidationVO= null;
				if(RoutingInConsignmentVO.OPERATION_FLAG_INSERT.equals(routingInConsignmentVO.getOperationFlag())){
					try{
						airlineValidationVO = new AirlineDelegate().validateAlphaCode(routingInConsignmentVO.getCompanyCode(), routingInConsignmentVO.getOnwardCarrierCode());
					}catch(BusinessDelegateException businessDelegateException){
						log.log(Log.FINE,  "BusinessDelegateException");
					}
					if(airlineValidationVO == null){
						ErrorVO errorVO = new ErrorVO("mailtracking.defaults.national.maintainconsignment.invalidairline");
						errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
						//routingInConsignmentVO.setInvalidFlightFlag(true);
						Object [] obj = new Object[]{
								routingInConsignmentVO.getOnwardCarrierCode()
						};
						errorVO.setErrorData(obj);

						errorVOs.add(errorVO);
					}else{
						routingInConsignmentVO.setOnwardCarrierId(airlineValidationVO.getAirlineIdentifier());
						//routingInConsignmentVO.setInvalidFlightFlag(true);
					}
				}
			}

		}
		
		/**
		 * for validating acceptance part
		 */
		Collection<RoutingInConsignmentVO> acceptanceConsignmentVOs  = consignmentDocumentVO.getAcceptanceInfo();
		if(acceptanceConsignmentVOs != null && acceptanceConsignmentVOs.size() >0){
			for(RoutingInConsignmentVO routingInConsignmentVO : acceptanceConsignmentVOs){
				airlineValidationVO= null;
				if(RoutingInConsignmentVO.OPERATION_FLAG_INSERT.equals(routingInConsignmentVO.getOperationFlag())){
					try{
						airlineValidationVO = new AirlineDelegate().validateAlphaCode(routingInConsignmentVO.getCompanyCode(), routingInConsignmentVO.getOnwardCarrierCode());
					}catch(BusinessDelegateException businessDelegateException){
						log.log(Log.FINE,  "BusinessDelegateException");
					}
					if(airlineValidationVO == null){
						ErrorVO errorVO = new ErrorVO("mailtracking.defaults.national.maintainconsignment.invalidairline");
						errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
						//routingInConsignmentVO.setInvalidFlightFlag(true);
						Object [] obj = new Object[]{
								routingInConsignmentVO.getOnwardCarrierCode()
						};
						errorVO.setErrorData(obj);

						errorVOs.add(errorVO);
					}else{
						routingInConsignmentVO.setOnwardCarrierId(airlineValidationVO.getAirlineIdentifier());
						//routingInConsignmentVO.setInvalidFlightFlag(true);
					}
				}
			}

		}




	}
	
	/**
	 * 
	 * @param routingInConsignmentVOs
	 * @param errorVOs
	 */
	private void validateFlightDetails(ConsignmentDocumentVO consignmentDocumentVO,Collection<ErrorVO> errorVOs){
		String routngFlag = "";
		Collection<FlightValidationVO> flightValidationVOs = null;
		FlightValidationVO  flightValidationVO = null;
		LogonAttributes logonAttributes = getApplicationSession().getLogonVO();
		//Added for routing part
		Collection<RoutingInConsignmentVO> routingInConsignmentVOs = consignmentDocumentVO.getRoutingInConsignmentVOs();
		if(routingInConsignmentVOs!= null && routingInConsignmentVOs.size() >0){
			for(RoutingInConsignmentVO routingInConsignmentVO : routingInConsignmentVOs){


				if(RoutingInConsignmentVO.OPERATION_FLAG_INSERT.equals(routingInConsignmentVO.getOperationFlag())){
					routngFlag ="Y";
					try{
						flightValidationVOs = new MailTrackingDefaultsDelegate().validateFlight(constructFlightFilterVO(routingInConsignmentVO, logonAttributes,routngFlag));
					}catch(BusinessDelegateException businessDelegateException){
						log.log(Log.FINE,  "BusinessDelegateException");
					}

					if(flightValidationVOs == null || flightValidationVOs.size() == 0){
						ErrorVO errorVO = new ErrorVO("mailtracking.defaults.national.maintainconsignment.invalidflight");
						errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
						//routingInConsignmentVO.setInvalidFlightFlag(true);
						Object[] obj = {
								routingInConsignmentVO
								.getOnwardCarrierCode(),
								routingInConsignmentVO
								.getOnwardFlightNumber(),
								routingInConsignmentVO.getOnwardFlightDate().toDisplayDateOnlyFormat(),
								routingInConsignmentVO.getPol(),
								routingInConsignmentVO.getPou() };
						errorVO.setErrorData(obj);
						errorVOs.add(errorVO);

					}else if(flightValidationVOs.size() >0){
						flightValidationVO  = flightValidationVOs.iterator().next();
						routingInConsignmentVO.setOnwardCarrierSeqNum(flightValidationVO.getFlightSequenceNumber());
						routingInConsignmentVO.setLegSerialNumber(flightValidationVO.getLegSerialNumber());
						routingInConsignmentVO.setSegmentSerialNumber(constructSegmentSerialNumber(flightValidationVO, routingInConsignmentVO));
						//routingInConsignmentVO.setInvalidFlightFlag(true);
					}

				}

			}
		}
		
		//Added for acceptance part
		Collection<RoutingInConsignmentVO> acceptanceRoutingInfo = consignmentDocumentVO.getAcceptanceInfo();
		if(acceptanceRoutingInfo != null && acceptanceRoutingInfo.size() >0){
			for(RoutingInConsignmentVO routingInConsignmentVO : acceptanceRoutingInfo){

         //modified by 	A-4810 for icrd-18334 for validation while updation of details 
				if(RoutingInConsignmentVO.OPERATION_FLAG_INSERT.equals(routingInConsignmentVO.getOperationFlag())||(RoutingInConsignmentVO.OPERATION_FLAG_UPDATE.equals(routingInConsignmentVO.getOperationFlag())&& (!"-1".equals(routingInConsignmentVO.getOnwardFlightNumber())) )){
					routngFlag ="";
					try{
						flightValidationVOs = new MailTrackingDefaultsDelegate().validateFlight(constructFlightFilterVO(routingInConsignmentVO,logonAttributes,routngFlag));
					}catch(BusinessDelegateException businessDelegateException){
						log.log(Log.FINE,  "BusinessDelegateException");
					}

					if(flightValidationVOs == null || flightValidationVOs.size() == 0){
						ErrorVO errorVO = new ErrorVO("mailtracking.defaults.national.maintainconsignment.invalidflight");
						errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
						//routingInConsignmentVO.setInvalidFlightFlag(true);
						Object[] obj = {
								routingInConsignmentVO
								.getOnwardCarrierCode(),
								routingInConsignmentVO
								.getOnwardFlightNumber(),
								routingInConsignmentVO.getOnwardFlightDate().toDisplayDateOnlyFormat(),
								routingInConsignmentVO.getPol(),
								routingInConsignmentVO.getPou() };
						errorVO.setErrorData(obj);
						errorVOs.add(errorVO);

					}else if(flightValidationVOs.size() >0){
						flightValidationVO  = flightValidationVOs.iterator().next();
						routingInConsignmentVO.setOnwardCarrierSeqNum(flightValidationVO.getFlightSequenceNumber());
						routingInConsignmentVO.setLegSerialNumber(flightValidationVO.getLegSerialNumber());
						routingInConsignmentVO.setSegmentSerialNumber(constructSegmentSerialNumber(flightValidationVO, routingInConsignmentVO));
						//routingInConsignmentVO.setInvalidFlightFlag(true);
					
					    //modified for icrd-17549 by A-4810 for validating whether the flights given in acceptence section are closed or not.
						OperationalFlightVO operationalFlightVO =  new OperationalFlightVO();		
						operationalFlightVO.setLegSerialNumber(flightValidationVO.getLegSerialNumber());
						operationalFlightVO.setFlightSequenceNumber(flightValidationVO.getFlightSequenceNumber());
						operationalFlightVO.setCarrierId(flightValidationVO.getFlightCarrierId());
						operationalFlightVO.setFlightStatus(flightValidationVO.getFlightStatus());
						operationalFlightVO.setAirportCode(logonAttributes.getAirportCode());
						operationalFlightVO.setPol(logonAttributes.getAirportCode());
						operationalFlightVO.setCompanyCode(flightValidationVO.getCompanyCode());
						operationalFlightVO.setFlightNumber(flightValidationVO.getFlightNumber());
						operationalFlightVO.setFlightRoute(flightValidationVO.getFlightRoute());
						operationalFlightVO.setPou(flightValidationVO.getLegDestination());
						operationalFlightVO.setCarrierCode(flightValidationVO.getCarrierCode());
						operationalFlightVO.setDirection(OUTBOUND);
						boolean isFlightClosed = false;
						try {

							isFlightClosed = new MailTrackingDefaultsDelegate().isFlightClosedForMailOperations(operationalFlightVO);

						}catch (BusinessDelegateException businessDelegateException) {
							//errors = handleDelegateException(businessDelegateException);
						}		
						
						if(isFlightClosed){		
							ErrorVO errorVO = new ErrorVO("mailtracking.defaults.national.maintainconsignment.acceptanceflightalreadyclosed");
							Object [] obj = {routingInConsignmentVO
									.getOnwardCarrierCode(),
									routingInConsignmentVO
									.getOnwardFlightNumber(),
									routingInConsignmentVO.getOnwardFlightDate().toDisplayDateOnlyFormat(),
									routingInConsignmentVO.getPol(),
									routingInConsignmentVO.getPou() };
									errorVO.setErrorData(obj);
									errorVOs.add(errorVO);
							
						}
						
												
						
						//modified for icrd -17549 ends.
					
					
					
					
					}

				}
			}
		}
		
		

	}
	
	
	/**
	 * 
	 * @param consignmentDocumentVO
	 * @param errorVOs
	 */
	private void validatePieceDetails(ConsignmentDocumentVO consignmentDocumentVO,Collection<ErrorVO> errorVOs){
	

		Collection<RoutingInConsignmentVO> routingInConsignmentVOs  = consignmentDocumentVO.getAcceptanceInfo();
		if(routingInConsignmentVOs != null){
			for(RoutingInConsignmentVO routingInConsignmentVO : routingInConsignmentVOs){


				if(RoutingInConsignmentVO.OPERATION_FLAG_INSERT.equals(routingInConsignmentVO.getOperationFlag())||
						RoutingInConsignmentVO.OPERATION_FLAG_UPDATE.equals(routingInConsignmentVO.getOperationFlag())	){

					if(routingInConsignmentVO.getNoOfPieces() ==0 || routingInConsignmentVO.getWeight().getRoundedSystemValue() == 0){//added by A-7371
						ErrorVO errorVO = new ErrorVO("mailtracking.defaults.national.maintainconsignment.piecesorweightzero");
						//routingInConsignmentVO.setInvalidFlightFlag(true);
						errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
						Object[] obj = {
								routingInConsignmentVO
								.getOnwardCarrierCode(),
								routingInConsignmentVO
								.getOnwardFlightNumber(),
								routingInConsignmentVO.getOnwardFlightDate().toDisplayDateOnlyFormat(),
								routingInConsignmentVO.getPol(),
								routingInConsignmentVO.getPou() };
						errorVO.setErrorData(obj);
						errorVOs.add(errorVO);

					}else{
						//routingInConsignmentVO.setInvalidFlightFlag(true);
					}
				}

			}
		}

		Collection<RoutingInConsignmentVO> acceptanceConsignmentVOs  = consignmentDocumentVO.getRoutingInConsignmentVOs();
		//Modified as part of bug-fix:icrd-15396
		if(acceptanceConsignmentVOs != null){
			for(RoutingInConsignmentVO routingInConsignmentVO : acceptanceConsignmentVOs){


				if(RoutingInConsignmentVO.OPERATION_FLAG_INSERT.equals(routingInConsignmentVO.getOperationFlag())||
						RoutingInConsignmentVO.OPERATION_FLAG_UPDATE.equals(routingInConsignmentVO.getOperationFlag())	){

					if(routingInConsignmentVO.getNoOfPieces() ==0 || routingInConsignmentVO.getWeight().getRoundedSystemValue() == 0){
						ErrorVO errorVO = new ErrorVO("mailtracking.defaults.national.maintainconsignment.piecesorweightzero");
						errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
						//routingInConsignmentVO.setInvalidFlightFlag(true);
						Object[] obj = {
								routingInConsignmentVO
								.getOnwardCarrierCode(),
								routingInConsignmentVO
								.getOnwardFlightNumber(),
								routingInConsignmentVO.getOnwardFlightDate().toDisplayDateOnlyFormat(),
								routingInConsignmentVO.getPol(),
								routingInConsignmentVO.getPou() };
						errorVO.setErrorData(obj);
						errorVOs.add(errorVO);

					}else{
						//routingInConsignmentVO.setInvalidFlightFlag(true);
					}
				}

			}
		}

	}
	
	
	/**
	 * 
	 * @param routingInConsignmentVO
	 * @param logonAttributes
	 * @param routngflag
	 * @return
	 */
	private FlightFilterVO constructFlightFilterVO(RoutingInConsignmentVO routingInConsignmentVO,LogonAttributes logonAttributes,String routngFlag){
		FlightFilterVO flightFilterVO = new FlightFilterVO();
		flightFilterVO.setCompanyCode(routingInConsignmentVO.getCompanyCode());
		flightFilterVO.setFlightCarrierId(routingInConsignmentVO.getOnwardCarrierId());
		flightFilterVO.setFlightDate(routingInConsignmentVO.getOnwardFlightDate());
		flightFilterVO.setDirection("O");
		//flightFilterVO.setAirportCode(routingInConsignmentVO.getPol());
		flightFilterVO.setFlightNumber(routingInConsignmentVO.getOnwardFlightNumber());
		//modified as part of bug fix :icrd-20140 by a-4810
		flightFilterVO.setCarrierCode(routingInConsignmentVO.getOnwardCarrierCode());
		if("Y".equals(routngFlag)){
		flightFilterVO.setStation(routingInConsignmentVO.getPol());	
		}
		else{
		flightFilterVO.setStation(logonAttributes.getAirportCode());
		}
		flightFilterVO.setCompanyCode(logonAttributes.getCompanyCode());
		return flightFilterVO;
		
		
	}
	
	/**
	 * 
	 * @param routingInConsignmentVOs
	 */
	private void validateDuplicateFlightDetails(
			ConsignmentDocumentVO consignmentDocumentVO,
			Collection<ErrorVO> errorVOs) {
		List<RoutingInConsignmentVO> consignmentVOs = (List<RoutingInConsignmentVO>) consignmentDocumentVO.getRoutingInConsignmentVOs();
		List<RoutingInConsignmentVO> acceptanceConsignmentVOs = (List<RoutingInConsignmentVO>) consignmentDocumentVO.getAcceptanceInfo();
		
		if(consignmentVOs != null){
			for (int i = 0; i < consignmentVOs.size() - 1; i++) {
				if (RoutingInConsignmentVO.OPERATION_FLAG_INSERT
						.equals(consignmentVOs.get(i))
						|| RoutingInConsignmentVO.OPERATION_FLAG_UPDATE
						.equals(consignmentVOs.get(i))) {
					for (int j = i + 1; j < consignmentVOs.size(); j++) {

						if (RoutingInConsignmentVO.OPERATION_FLAG_INSERT
								.equals(consignmentVOs.get(j))
								|| RoutingInConsignmentVO.OPERATION_FLAG_UPDATE
								.equals(consignmentVOs.get(j))) {

							if (constructFlightPK(consignmentVOs.get(i)).equals(
									constructFlightPK(consignmentVOs.get(j)))) {
								//consignmentVOs.get(j).setInvalidFlightFlag(true);
								ErrorVO errorVO = new ErrorVO(
								"mailtracking.defaults.national.maintainconsignment.duplicateflights");
								errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
								Object[] obj = {
										consignmentVOs.get(i)
										.getOnwardCarrierCode(),
										consignmentVOs.get(i)
										.getOnwardFlightNumber(),
										consignmentVOs.get(i).getOnwardFlightDate().toDisplayDateOnlyFormat(),
										consignmentVOs.get(i).getPol(),
										consignmentVOs.get(i).getPou() };
								errorVO.setErrorData(obj);

							}else{
								//consignmentVOs.get(j).setInvalidFlightFlag(true);
							}

						}
					}

				}
			}
		}
		
		
		if(acceptanceConsignmentVOs != null){
			for (int i = 0; i < acceptanceConsignmentVOs.size() - 1; i++) {
				if (RoutingInConsignmentVO.OPERATION_FLAG_INSERT
						.equals(acceptanceConsignmentVOs.get(i))
						|| RoutingInConsignmentVO.OPERATION_FLAG_UPDATE
						.equals(acceptanceConsignmentVOs.get(i))) {
					for (int j = i + 1; j < acceptanceConsignmentVOs.size(); j++) {

						if (RoutingInConsignmentVO.OPERATION_FLAG_INSERT
								.equals(acceptanceConsignmentVOs.get(j))
								|| RoutingInConsignmentVO.OPERATION_FLAG_UPDATE
								.equals(acceptanceConsignmentVOs.get(j))) {

							if (constructFlightPK(acceptanceConsignmentVOs.get(i)).equals(
									constructFlightPK(acceptanceConsignmentVOs.get(j)))) {
								ErrorVO errorVO = new ErrorVO(
								"mailtracking.defaults.national.maintainconsignment.duplicateflights");
								//acceptanceConsignmentVOs.get(j).setInvalidFlightFlag(true);
								errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
								Object[] obj = {
										acceptanceConsignmentVOs.get(i)
										.getOnwardCarrierCode(),
										acceptanceConsignmentVOs.get(i)
										.getOnwardFlightNumber(),
										acceptanceConsignmentVOs.get(i).getOnwardFlightDate().toDisplayDateOnlyFormat(),
										acceptanceConsignmentVOs.get(i).getPol(),
										acceptanceConsignmentVOs.get(i).getPou() };
								errorVO.setErrorData(obj);

							}else{
								//acceptanceConsignmentVOs.get(j).setInvalidFlightFlag(true);
							}

						}
					}

				}
			}
		}
		
	}
	
	/**
	 * 
	 * @param routingInConsignmentVO
	 * @return
	 */
	private String constructFlightPK(RoutingInConsignmentVO routingInConsignmentVO){
		StringBuilder stringBuilder = new StringBuilder();
		return stringBuilder.append(routingInConsignmentVO.getOnwardCarrierCode()).
		append(routingInConsignmentVO.getOnwardFlightNumber()).
		append(routingInConsignmentVO.getPol()).
		append(routingInConsignmentVO.getPou()).
		append(routingInConsignmentVO.getOnwardFlightDate()).toString();
		
		
		
	}
	
	/**
	 * 
	 * @param flightValidationVO
	 * @param routingInConsignmentVO
	 * @return
	 */
	private int constructSegmentSerialNumber(FlightValidationVO flightValidationVO,RoutingInConsignmentVO routingInConsignmentVO){
		String[] flightRoute = flightValidationVO.getFlightRoute().split("-");
		List<String> segments = new ArrayList<String>();
		
		for(int i =0;i <flightRoute.length-1;i++){
			for(int j = i+1; j <flightRoute.length;j++){
				segments.add(flightRoute[i].concat("~").concat(flightRoute[j]));
			}
		}

		for(int i =0; i < segments.size();i++){
			if(routingInConsignmentVO.getPol().concat("~").concat(routingInConsignmentVO.getPou()).equals(segments.get(i))){
				return i+1;

			}

			

		}
		return 0;


	}
	
	/**
	 * 
	 * @param consignmentDocumentVO
	 */
	private void updateConsignmentDetails(ConsignmentDocumentVO consignmentDocumentVO){
		if(consignmentDocumentVO != null){
			LogonAttributes logonAttributes = getApplicationSession().getLogonVO();
			for(RoutingInConsignmentVO routingInConsignmentVO : consignmentDocumentVO.getRoutingInConsignmentVOs()){
				if(RoutingInConsignmentVO.OPERATION_FLAG_UPDATE.equals(routingInConsignmentVO.getOperationFlag())
						||(RoutingInConsignmentVO.OPERATION_FLAG_DELETE.equals(routingInConsignmentVO.getOperationFlag()))
								|| (RoutingInConsignmentVO.OPERATION_FLAG_INSERT.equals(routingInConsignmentVO.getOperationFlag()))){
					if(logonAttributes.getAirportCode().equals(routingInConsignmentVO.getPol())){
						routingInConsignmentVO.setAcceptanceFlag(true);
					}
					
				}
				
			}
			
		}
		
		
		
	

}
	/**
	 * 
	 * @param consignmentDocumentVO
	 */
	private void updateConsignmentDetailsForError(ConsignmentDocumentVO consignmentDocumentVO){

		if(consignmentDocumentVO != null){

			if(consignmentDocumentVO.getAcceptanceInfo() != null && consignmentDocumentVO.getAcceptanceInfo().size() >0){
				for(RoutingInConsignmentVO routingInConsignmentVO : consignmentDocumentVO.getAcceptanceInfo()){
					//routingInConsignmentVO.setInvalidFlightFlag(true);

				}

			}

			if(consignmentDocumentVO.getRoutingInConsignmentVOs() != null && consignmentDocumentVO.getRoutingInConsignmentVOs().size() >0){
				for(RoutingInConsignmentVO routingInConsignmentVO : consignmentDocumentVO.getAcceptanceInfo()){
					//routingInConsignmentVO.setInvalidFlightFlag(true);

				}
			}

		}

	}
	
	/**
	 * 
	 */
	private boolean validateFullDelete(ConsignmentDocumentVO consignmentDocumentVO){
		int deleteCount  = 0;
		boolean flag = true;
		if(consignmentDocumentVO != null && consignmentDocumentVO.getAcceptanceInfo() != null
				&& consignmentDocumentVO.getAcceptanceInfo().size() >0){
			 for(RoutingInConsignmentVO routingInConsignmentVO : consignmentDocumentVO.getAcceptanceInfo()){
				 if(RoutingInConsignmentVO.OPERATION_FLAG_DELETE.equals(routingInConsignmentVO.getOperationFlag())){
					 deleteCount = deleteCount+1;
					 
				 }
			 }
			 if(deleteCount == consignmentDocumentVO.getAcceptanceInfo().size()){
				return false;
			 }
			
		}
		return flag;
		
	}
}
