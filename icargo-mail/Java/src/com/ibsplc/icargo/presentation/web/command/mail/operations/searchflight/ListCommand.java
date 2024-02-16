/*
 * ListCommand.java Created on Jul 09, 2009
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.searchflight;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.ibsplc.icargo.business.flight.operation.vo.FlightValidationVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.mail.operations.vo.OperationalFlightVO;
import com.ibsplc.icargo.business.shared.airline.vo.AirlineValidationVO;
import com.ibsplc.icargo.business.shared.area.airport.vo.AirportValidationVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.airline.AirlineDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.area.AreaDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.SearchFlightSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.SearchFlightForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-3817
 * 
 */
public class ListCommand extends BaseCommand {
	
	private Log log = LogFactory.getLogger("MAILTRACKING_DEFAULTS");
	
	private static final String SCREENID = "mailtracking.defaults.searchflight";

	private static final String MODULENAME = "mail.operations";
	
	private static final String LIST_SUCCESS = "list_success";
	
	private static final String LIST_FAILURE = "list_failure";
	
	private static final String PARAMETER_KEY_MAILSTA = "mailtracking.defaults.flightstatus";
	private static final String PARAMETER_KEY_FLIGHTSTA = "flight.operation.flightlegstatus";
	

	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		SearchFlightForm form = (SearchFlightForm) invocationContext.screenModel;
		SearchFlightSession session = (SearchFlightSession) getScreenSession(
				MODULENAME, SCREENID);
		OperationalFlightVO operationalFlightVO = null;
		Page<OperationalFlightVO> operationalFlightVOs = null;
		Collection<FlightValidationVO> flightValidationVOs = null;
		Collection<ErrorVO> errorVOs = null;
		ErrorVO errorVO=null;
		AirlineValidationVO airlineValidationVO = null;
		AirportValidationVO airportValidationVO=null;
		String airportCode=null;
		HashMap<String, String> indexMap = null;
		HashMap<String, String> finalMap = null;
		LogonAttributes logonAttributes = getApplicationSession().getLogonVO();
		String companyCode = getApplicationSession().getLogonVO().getCompanyCode();
		session.setOperationalFlightVOs(null);
		
		/**
		 * Direction 
		 * I-MailArrivalScreen
		 * O-MailManifestScreen
		 */
		
		/**
		 *  Populates filter VO if coming from search flightScreen
		 */
		if("ON".equals(form.getListStatus())){
			
			errorVOs=validateForm(form, errorVOs);
			if(errorVOs!=null && errorVOs.size()>0){
				invocationContext.addAllError(errorVOs);
				invocationContext.target=LIST_FAILURE;
				return;
			}  if(!"ON".equals(form.getFromReconciliation())){
				operationalFlightVO=populateOperationalFlightVO(operationalFlightVO, form);
				session.setOperationalFlightVO(operationalFlightVO);
			}
			else{
				operationalFlightVO=session.getOperationalFlightVO();
			}
		}
		/**
		 *  Populates filter VO if coming from arrival or manifest Screen
		 */
		else{
			operationalFlightVO = session.getOperationalFlightVO();
			
			
		}
		
		if (session.getIndexMap()!=null){
			indexMap = session.getIndexMap();
		}
		if (indexMap == null) {
			
			indexMap = new HashMap<String, String> ();
			indexMap.put("1", "1");
		}

		int nAbsoluteIndex = 0;

		String strAbsoluteIndex = (String)indexMap.get(form.getDisplayPage());
		if(strAbsoluteIndex != null){
			nAbsoluteIndex = Integer.parseInt(strAbsoluteIndex);
		}
		operationalFlightVO.setAbsoluteIndex(nAbsoluteIndex);
		form.setAbsoluteIndex(String.valueOf(nAbsoluteIndex));
		operationalFlightVO.setPageNumber(Integer.parseInt(form.getDisplayPage()));	
		
		
		operationalFlightVO.setTotalRecords(-1);
		
	    /**
	     * for giving the page number
	     */
		//operationalFlightVO.setDisplayPage(Integer.parseInt(form.getDisplayPage()));
		/**
		 * for airline and flight validation
		 */
//		if ((operationalFlightVO.getCarrierCode() != null)
//				&& (operationalFlightVO.getFlightNumber() != null)
//				&& (operationalFlightVO.getFlightDate() != null)) {
//			AirlineValidationVO airlineValidationVO = null;
//			FlightFilterVO flightFilterVO = null;
//			String companyCode = getApplicationSession().getLogonVO()
//					.getCompanyCode();
//			String carrierCode = operationalFlightVO.getCarrierCode();
//			airlineValidationVO = validateAirline(airlineValidationVO,
//					companyCode, carrierCode,errorVOs);
//             
//			if(errorVOs!=null && errorVOs.size()>0){
//				invocationContext.addAllError(errorVOs);
//				invocationContext.target=LIST_FAILURE;
//				return;
//			}
//			if (airlineValidationVO != null) {
//				int carrierId = airlineValidationVO.getAirlineIdentifier();
//				flightFilterVO = populateFlightFilterVO(operationalFlightVO,
//						logonAttributes, carrierId, flightFilterVO);
//				flightValidationVOs = validateFlight(flightFilterVO,
//						flightValidationVOs, errorVOs, invocationContext);
//				if (errorVOs != null && errorVOs.size() > 0) {
//					invocationContext.addAllError(errorVOs);
//					invocationContext.target=LIST_FAILURE;
//					return;
//				}
//                if(flightValidationVOs.size()==1){
//                	for(FlightValidationVO flightValidationVO:flightValidationVOs){
//                		operationalFlightVO.setFlightNumber(flightValidationVO.getFlightNumber());
//                		operationalFlightVO.setFlightSequenceNumber(flightValidationVO.getFlightSequenceNumber());
//                		operationalFlightVO.setLegSerialNumber(flightValidationVO.getLegSerialNumber());
//                	}
//                }
//			}
//		}
		  /**
		   * For carrier validation
		   */
		  if(operationalFlightVO.getCarrierCode()!=null && operationalFlightVO.getCarrierCode().trim().length()>0){
			  String carrierCode = operationalFlightVO.getCarrierCode();
			  airlineValidationVO = validateAirline(airlineValidationVO,
					  logonAttributes.getCompanyCode(), carrierCode,errorVOs);
			  if(airlineValidationVO==null){
				  Object []obj={carrierCode};
				  errorVO=new ErrorVO("mailtracking.defaults.searchflight.invalidairline",obj);
				  errorVOs.add(errorVO);
				  invocationContext.addAllError(errorVOs);
				  invocationContext.target=LIST_FAILURE;
					return;
			  }
			  
			  operationalFlightVO.setCarrierId(airlineValidationVO.getAirlineIdentifier());
			  
		  }
		  /**
		   * For airport Validation
		   */
		  if(MailConstantsVO.OPERATION_INBOUND.equals(operationalFlightVO.getDirection())){
			  airportCode=operationalFlightVO.getPou();
			  
		  }
		  if(MailConstantsVO.OPERATION_OUTBOUND.equals(operationalFlightVO.getDirection())){
			  airportCode=operationalFlightVO.getPol();
		  }
		  if(airportCode!=null && airportCode.trim().length()>0){
			  airportValidationVO= validateAirport(companyCode, airportCode, errorVOs, airportValidationVO);
			  Object []obj={airportCode};
			  if(airportValidationVO==null){
				 errorVO=new ErrorVO("mailtracking.defaults.searchflight.invalidairport",obj);
				 errorVOs.add(errorVO);
				 invocationContext.addAllError(errorVOs);
					invocationContext.target=LIST_FAILURE;
					return;
			  }
			 
		  }
		try {
			operationalFlightVOs = new MailTrackingDefaultsDelegate()
					.findMailFlightDetails(operationalFlightVO);
		} catch (BusinessDelegateException businessDelegateException) {
			log.log(Log.FINE,  "BusinessDelegateException");
		}
		if(operationalFlightVOs==null ||operationalFlightVOs.size()<1){
			errorVO=new ErrorVO("mailtracking.defaults.searchflight.nodatafound");
			errorVOs=new ArrayList<ErrorVO>();
			errorVOs.add(errorVO);
			invocationContext.addAllError(errorVOs);
		}
		if(operationalFlightVOs!=null){
		finalMap = indexMap;
		finalMap = buildIndexMap(indexMap, operationalFlightVOs);
		session.setIndexMap(finalMap);
		//log.log(Log.FINE, "finalMap --->>"+finalMap);
		}
		Collection<String> parameters = new ArrayList<String>();
		parameters.add(PARAMETER_KEY_MAILSTA);
		parameters.add(PARAMETER_KEY_FLIGHTSTA);
		session.setOneTimeVOs(getOnetimes(companyCode, parameters));
		session.setOperationalFlightVOs(operationalFlightVOs);
		populateForm(session, form, operationalFlightVO,logonAttributes);
		invocationContext.target=LIST_SUCCESS;

	}

	/**
	 * 
	 * @param operationalFlightVO
	 * @param form
	 * @return
	 */
	private OperationalFlightVO populateOperationalFlightVO(
			OperationalFlightVO operationalFlightVO, SearchFlightForm form) {
		operationalFlightVO = new OperationalFlightVO();
		LocalDate localDate = null;
		LocalDate depFromDate = null;
		LocalDate depToDate = null;
		LocalDate arrFromDate = null;
		LocalDate arrToDate = null;
		if (form.getCarrierCode() != null
				&& form.getCarrierCode().trim().length() > 0) {
			operationalFlightVO.setCarrierCode(form.getCarrierCode());

		}
		if (form.getFlightNumber() != null
				&& form.getFlightNumber().trim().length() > 0) {
			operationalFlightVO.setFlightNumber(form.getFlightNumber());
		}
		if (form.getArrivalPort() != null
				&& form.getArrivalPort().trim().length() > 0) {
			operationalFlightVO.setPou(form.getArrivalPort());
		}
		
		if (form.getDepartingPort() != null
				&& form.getDepartingPort().trim().length() > 0) {
			operationalFlightVO.setPol(form.getDepartingPort());
		}
		if (form.getArrivalDate() != null
				&& form.getArrivalDate().trim().length() > 0) {
			localDate = new LocalDate(LocalDate.NO_STATION, Location.NONE,
					false);
			operationalFlightVO.setFlightDate(localDate.setDate(form
					.getArrivalDate()));
		}
		if (form.getDepartureDate() != null
				&& form.getDepartureDate().trim().length() > 0) {
			localDate = new LocalDate(LocalDate.NO_STATION, Location.NONE,
					false);
			operationalFlightVO.setFlightDate(localDate.setDate(form
					.getDepartureDate()));
		}
		if (form.getDepFromDate() != null
				&& form.getDepFromDate().trim().length() > 0) {
			depFromDate = new LocalDate(LocalDate.NO_STATION, Location.NONE,
					false);
			operationalFlightVO.setDepFromDate(depFromDate.setDate(form
					.getDepFromDate()));
		}
		if (form.getDepToDate() != null
				&& form.getDepToDate().trim().length() > 0) {
			depToDate = new LocalDate(LocalDate.NO_STATION, Location.NONE,
					false);
			operationalFlightVO.setDepToDate(depToDate.setDate(form
					.getDepToDate()));
		}
		if (form.getArrFromDate() != null
				&& form.getArrFromDate().trim().length() > 0) {
			arrFromDate = new LocalDate(LocalDate.NO_STATION, Location.NONE,
					false);
			operationalFlightVO.setArrFromDate(arrFromDate.setDate(form
					.getArrFromDate()));
		}
		if (form.getArrToDate() != null
				&& form.getArrToDate().trim().length() > 0) {
			arrToDate = new LocalDate(LocalDate.NO_STATION, Location.NONE,
					false);
			operationalFlightVO.setArrToDate(arrToDate.setDate(form
					.getArrToDate()));
		}
		if(form.getMailStatus()!=null && form.getMailStatus().trim().length()>0){
			operationalFlightVO.setMailStatus(form.getMailStatus());
		}
		operationalFlightVO.setDirection(form.getFromScreen());
		return operationalFlightVO;
	}

	/**
	 * To validate Airline
	 * 
	 * @param airlineValidationVO
	 * @param companyCode
	 * @param carrierCode
	 * @return
	 */
	private AirlineValidationVO validateAirline(
			AirlineValidationVO airlineValidationVO, String companyCode,
			String carrierCode,Collection<ErrorVO> errorVOs) {
		try {
			airlineValidationVO = new AirlineDelegate().validateAlphaCode(
					companyCode, carrierCode);
		} catch (BusinessDelegateException businessDelegateException) {
			errorVOs = handleDelegateException(businessDelegateException);
		}
		return airlineValidationVO;
	}

	

	/**
	 * To validate form
	 * @param form
	 * @param errorVOs
	 * @return
	 */
	private Collection<ErrorVO> validateForm(SearchFlightForm form,Collection<ErrorVO> errorVOs){
		 errorVOs=new ArrayList<ErrorVO>();
		ErrorVO errorVO=null;
//		if(form.getFlightNumber()==null || form.getFlightNumber().trim().length()<1){
//			errorVO=new ErrorVO("mailtracking.defaults.searchflight.noflightnumber");
//			errorVOs.add(errorVO);
//		}
		if(form.getFromScreen().equals(MailConstantsVO.OPERATION_INBOUND)){
			if(form.getArrivalPort()==null || form.getArrivalPort().trim().length()<1){
				errorVO=new ErrorVO("mailtracking.defaults.searchflight.noarrport");
				errorVOs.add(errorVO);
			}
		}
		if(form.getFromScreen().equals(MailConstantsVO.OPERATION_OUTBOUND)){
			if(form.getDepartingPort()==null || form.getDepartingPort().trim().length()<1){
				errorVO=new ErrorVO("mailtracking.defaults.searchflight.nodepport");
				errorVOs.add(errorVO);
			}
		}
		return errorVOs;
	}
	/**
	 * 
	 * @param session
	 * @param form
	 * @param operationalFlightVO
	 * @return
	 */
	private SearchFlightForm populateForm(SearchFlightSession session,SearchFlightForm form,OperationalFlightVO operationalFlightVO,LogonAttributes logonAttributes){
		if(operationalFlightVO.getFlightNumber()!=null){
			form.setFlightNumber(operationalFlightVO.getFlightNumber());
		}
		//String carrierCode=logonAttributes.getOwnAirlineCode();
		if(operationalFlightVO.getCarrierCode()!=null && operationalFlightVO.getCarrierCode().trim().length()>0){
			form.setCarrierCode(operationalFlightVO.getCarrierCode());
		}
		/*else{
			form.setCarrierCode(carrierCode);
		}*/
		
		if(MailConstantsVO.OPERATION_INBOUND.equals(operationalFlightVO.getDirection())){
			form.setArrivalPort(operationalFlightVO.getPou());
			
			if(operationalFlightVO.getFlightDate()!=null){
				form.setArrivalDate(operationalFlightVO.getFlightDate().toDisplayDateOnlyFormat());
				form.setArrFromDate(operationalFlightVO.getFlightDate().toDisplayDateOnlyFormat());
				form.setArrToDate(operationalFlightVO.getFlightDate().toDisplayDateOnlyFormat());
			}
			form.setPort(logonAttributes.getAirportCode());
		}
		if(MailConstantsVO.OPERATION_OUTBOUND.equals(operationalFlightVO.getDirection())){
			form.setDepartingPort(operationalFlightVO.getPol());
			if(operationalFlightVO.getFlightDate()!=null){
				form.setDepartureDate(operationalFlightVO.getFlightDate().toDisplayDateOnlyFormat());
				form.setDepFromDate(operationalFlightVO.getFlightDate().toDisplayDateOnlyFormat());
				form.setDepToDate(operationalFlightVO.getFlightDate().toDisplayDateOnlyFormat());
			}
		}
//		if(operationalFlightVO.getMailStatus()!=null && operationalFlightVO.getMailStatus().trim().length()>0){
//			form.setMailStatus(operationalFlightVO.getMailStatus());
//		}
		return form;
	}
	/**
	 * To validate Airport
	 * @param CompanyCode
	 * @param airportCode
	 * @param errorVOs
	 * @param airportValidationVO
	 * @return
	 */
   private AirportValidationVO validateAirport(String CompanyCode,String airportCode,Collection<ErrorVO> errorVOs,AirportValidationVO airportValidationVO){
	   try{	
		  
		   airportValidationVO=new AreaDelegate().validateAirportCode(CompanyCode, airportCode);
		   }
	   catch (BusinessDelegateException businessDelegateException) {
		   log.log(Log.FINE,  "BusinessDelegateException");
		   
	}
	 
	 return   airportValidationVO;
   }

	/**
	 * The method to obtain the onetime values. The method will call the
	 * sharedDefaults delegate and returns the map of requested onetimes
	 * 
	 * @return oneTimeValues
	 */
	private HashMap<String, Collection<OneTimeVO>> getOnetimes(
			String companyCode, Collection<String> parameters) {
		Map<String, Collection<OneTimeVO>> oneTimeMap = null;
		Collection<ErrorVO> errors = null;
		try {
			oneTimeMap = new SharedDefaultsDelegate().findOneTimeValues(
					companyCode, parameters);
		} catch (BusinessDelegateException businessDelegateException) {
			errors = handleDelegateException(businessDelegateException);
		}
		return (HashMap<String, Collection<OneTimeVO>>) oneTimeMap;
	}
	
	/**
    *
    * @param existingMap
    * @param page
    * @return
    */
   private HashMap<String, String> buildIndexMap(
			HashMap<String, String> existingMap, Page page) {

		//log.log(Log.FINE, "in buildIndexMap");

		HashMap<String, String> finalMap = existingMap;
		String indexPage = String.valueOf((page.getPageNumber() + 1));
		boolean isPageExist = false;
		Set<Map.Entry<String, String>> set = existingMap.entrySet();

		for (Map.Entry<String, String> entry : set) {
			String pageNum = entry.getKey();
			if (pageNum.equals(indexPage)) {
				isPageExist = true;
			}
		}
		if (!isPageExist) {
			finalMap.put(indexPage, String.valueOf(page.getAbsoluteIndex()));
		}
		//log.log(Log.FINE, "out buildIndexMap");
		return finalMap;
	}
}
