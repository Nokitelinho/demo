package com.ibsplc.icargo.presentation.web.command.mail.operations.ux.outbound;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.ibsplc.icargo.business.flight.operation.vo.FlightFilterVO;
import com.ibsplc.icargo.business.flight.operation.vo.FlightValidationVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailAcceptanceVO;
import com.ibsplc.icargo.business.mail.operations.vo.OperationalFlightVO;
import com.ibsplc.icargo.business.shared.airline.vo.AirlineValidationVO;
import com.ibsplc.icargo.business.warehouse.defaults.vo.LocationEnquiryFilterVO;
import com.ibsplc.icargo.business.warehouse.defaults.vo.WarehouseVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.util.BeanHelper;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.json.PageResult;
import com.ibsplc.icargo.framework.web.json.vo.ResponseVO;
import com.ibsplc.icargo.framework.web.spring.command.AbstractCommand;
import com.ibsplc.icargo.framework.web.spring.controller.ActionContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.airline.AirlineDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.area.AreaDelegate;
import com.ibsplc.icargo.presentation.web.command.mail.operations.ux.mailbagenquiry.ValidateFlightCommand;
import com.ibsplc.icargo.presentation.web.model.mail.operations.OutboundModel;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.FlightCarrierFilter;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.MailAcceptance;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.MailOutboundModelConverter;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.Warehouse;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
public class ListMailOutboundCommand extends AbstractCommand{
    private static final String OUTBOUND = "O";
    private static final String CONST_FLIGHT = "FLIGHT";

    private static final String FLIGHT_TBC_TBA = "flight_tba_tbc";
    private static final String DCSREPORT_ONETIME = "operations.flthandling.dws.dcsreportingstatus";
    private static final String NO_CONTAINER_DETAILS = "mailtracking.defaults.mailacceptance.nocontainerdetails";
	private static final String NOT_SENT="Not Sent";
	private Log log = LogFactory.getLogger("OPERATIONS MAIL OUTBOUND NEO");
	@Override
	public void execute(ActionContext actionContext) throws BusinessDelegateException,
			CommandInvocationException {
		  OutboundModel outboundModel = (OutboundModel)actionContext.getScreenModel();
		  OperationalFlightVO operationalFlightVO = new OperationalFlightVO(); 
		  LogonAttributes logonAttributes = getLogonAttribute();
		  FlightFilterVO flightFilterVO= new FlightFilterVO();
		
		  Collection<ErrorVO> errors = new ArrayList();
		 // String displayPage = "1";
		  int flightDisplayPage=0;
		  int carrierDisplayPage=0;
		  ResponseVO responseVO = new ResponseVO();
		  List<OutboundModel> results = new ArrayList();
		  operationalFlightVO.setCompanyCode(logonAttributes.getCompanyCode());
		  String flightNum="";
		  FlightCarrierFilter flightcarrierfilter = null;
		  AreaDelegate areaDelegate = new AreaDelegate();
		  flightcarrierfilter = outboundModel.getFlightCarrierFilter();
		  if ((flightcarrierfilter.getFlightNumber()!= null) && (flightcarrierfilter.getFlightNumber().trim().length() > 0)) {
			  operationalFlightVO.setFlightNumber(flightcarrierfilter.getFlightNumber());
				 flightNum = flightcarrierfilter.getFlightNumber().toUpperCase();
		   }
		
	     	if (flightcarrierfilter.getAirportCode() != null &&flightcarrierfilter.getAirportCode().trim().length()>0) {
	     		try {
	     			 areaDelegate.validateAirportCode(  
	     					logonAttributes.getCompanyCode(),
	     					flightcarrierfilter.getAirportCode().toUpperCase());
	     		}catch (BusinessDelegateException businessDelegateException) {
	     			errors = handleDelegateException(businessDelegateException);
	     		}
	     		if (errors != null &&! errors.isEmpty()) {
	     			actionContext.addError(new ErrorVO("mailtracking.defaults.invalidupliftairport"));
		        	return;
	     		}
	     	}
	     	if (flightcarrierfilter.getDestination() != null && flightcarrierfilter.getDestination().trim().length()>0) {
	     		try {
	     			  areaDelegate.validateAirportCode(
	     					logonAttributes.getCompanyCode(),
	     					flightcarrierfilter.getDestination());
	     		}catch (BusinessDelegateException businessDelegateException) {
	     			errors = handleDelegateException(businessDelegateException);
	     		}
	     		if (errors != null && !errors.isEmpty()) {
	     			actionContext.addError(new ErrorVO("mailtracking.defaults.invalidairport",
			   				new Object[]{flightcarrierfilter.getDestination().toUpperCase()}));
		        	return;
	     		}
	     	}
	     	//Added as part of IASCB-45330
		  if ((flightcarrierfilter.getFlightDate()!= null) && (flightcarrierfilter.getFlightDate().trim().length() > 0))
	        {
	          
	          LocalDate date = new LocalDate(flightcarrierfilter.getAirportCode(),Location.ARP,false);
			  operationalFlightVO.setFlightDate((date.setDate(flightcarrierfilter.getFlightDate())));
	        }
		    LocalDate fromDate = new LocalDate("***", Location.NONE, false);
	        LocalDate toDate = new LocalDate("***", Location.NONE, false);
		   if ((flightcarrierfilter.getFromDate()!= null) && (flightcarrierfilter.getFromDate().trim().length() > 0))
	        {
				LocalDate date = new LocalDate(flightcarrierfilter.getAirportCode(),Location.ARP,false);
			   if(flightcarrierfilter.getFromTime()!=null && flightcarrierfilter.getFromTime().trim().length() > 0){
				   String fromDT=null;
				   fromDT = new StringBuilder(flightcarrierfilter.getFromDate()).append(" ") 
							.append(flightcarrierfilter.getFromTime()).append(":00").toString();
				   operationalFlightVO.setFromDate((date.setDateAndTime(fromDT)));
			   }else{
			    operationalFlightVO.setFromDate((date.setDate(flightcarrierfilter.getFromDate())));
	        }
	        }
	        if ((flightcarrierfilter.getToDate() != null) && (flightcarrierfilter.getToDate().trim().length() > 0))
	        {
	        	
	        	LocalDate date = new LocalDate(flightcarrierfilter.getAirportCode(),Location.ARP,false);
				   if(flightcarrierfilter.getToTime()!=null && flightcarrierfilter.getToTime().trim().length() > 0){
					   String toDT=null;
					   toDT = new StringBuilder(flightcarrierfilter.getToDate()).append(" ") 
								.append(flightcarrierfilter.getToTime()).append(":00").toString();
					   operationalFlightVO.setToDate((date.setDateAndTime(toDT)));
				   }else{
			    operationalFlightVO.setToDate((date.setDate(flightcarrierfilter.getToDate())));
				   }
	        }
	        if ((flightcarrierfilter.getAirportCode() != null) && (flightcarrierfilter.getAirportCode().trim().length() > 0))
	        {
	          
	        	operationalFlightVO.setPol(flightcarrierfilter.getAirportCode());
	        	
	        }
	        if (fromDate.isGreaterThan(toDate))
	        {
	        	actionContext.addError(new ErrorVO("mailtracking.defaults.searchconsignment.greaterdate"));
	        	return;
	        }
	        if ((flightcarrierfilter.getDestination()!= null) && (flightcarrierfilter.getDestination().trim().length() > 0))
	      	  
	        {
	        	operationalFlightVO.setPou(flightcarrierfilter.getDestination());
	        }
	        if ((flightcarrierfilter.getFlightOperationalStatus()!= null) && (flightcarrierfilter.getFlightOperationalStatus().trim().length() > 0))
	        {
	        	operationalFlightVO.setFlightOperationStatus(flightcarrierfilter.getFlightOperationalStatus());
	        }
	        operationalFlightVO.setRecordsPerPage(flightcarrierfilter.getRecordsPerPage());
			//if(CONST_FLIGHT.equalsIgnoreCase(flightcarrierfilter.getAssignTo())){
			
			
			
			
	        AirlineDelegate airlineDelegate = new AirlineDelegate();
	       
	        
	        FlightValidationVO flightValidationVO =new FlightValidationVO();
	       
	        
	    	if(flightcarrierfilter.getAssignTo()!=null || flightcarrierfilter.getAssignTo().trim().length()>0) { 
	        if(flightcarrierfilter.getAssignTo().equals("F")) {
	        	AirlineValidationVO airlineValidationVO = null;
	        	flightDisplayPage = flightcarrierfilter.getFlightDisplayPage();
	        	
	        	 Page<MailAcceptanceVO> mailAcceptanceVOs = null;
	        	 String flightCarrierCode = flightcarrierfilter.getCarrierCode().trim().toUpperCase();
	 	    	if (flightCarrierCode != null && !"".equals(flightCarrierCode)) {
	 	    		try {
	 	    			airlineValidationVO = airlineDelegate.validateAlphaCode(
	 	    					logonAttributes.getCompanyCode(),
	 	    					flightCarrierCode);

	 	    		}catch (BusinessDelegateException businessDelegateException) {
	 	    			errors = handleDelegateException(businessDelegateException);
	 	    		}
	 	    		if (errors != null && errors.size() > 0) {
	 	    			Object[] obj = {flightCarrierCode};
	 					ErrorVO errorVO = new ErrorVO(
	 						"mailtracking.defaults.invalidcarrier",obj);
	 	    			actionContext.addError(errorVO);
	 		        	return;
	 	    			
	 	    		}
	 	    		operationalFlightVO.setCarrierId(airlineValidationVO.getAirlineIdentifier());
	 	        
	 	        
	 	    	}
	     		if(flightcarrierfilter.getFlightNumber()!=null && flightcarrierfilter.getFlightNumber().trim().length()>0) {
	     			Collection<FlightValidationVO> flightValidationVOs = null;
	         		try {
	         			flightFilterVO=handleFlightFilterVO(operationalFlightVO,logonAttributes);
	         			
	         			flightFilterVO.setCarrierCode(flightCarrierCode);
	         			flightFilterVO.setFlightCarrierId(airlineValidationVO.getAirlineIdentifier());
	         			flightFilterVO.setFlightNumber(flightNum);
	         			operationalFlightVO.setFlightNumber(flightcarrierfilter.getFlightNumber());
	     				flightValidationVOs =new MailTrackingDefaultsDelegate().validateFlight(flightFilterVO);
	     				//added as part of IASCB-56622
	     				new ValidateFlightCommand().removeIncompatibleTruckFlight(flightValidationVOs);
	     				
	         		}catch (BusinessDelegateException businessDelegateException) {
	         			errors = handleDelegateException(businessDelegateException);
	         		}
	         		if (errors != null && errors.size() > 0) {
	 	    			Object[] obj = {flightCarrierCode};
	 					ErrorVO errorVO = new ErrorVO(
	 						"mailtracking.defaults.invalidflight",obj);
	 	    			actionContext.addError(errorVO);
	 		        	return;
	 	    			
	 	    		}
	         		if (flightValidationVOs == null || flightValidationVOs.size() <= 0) {
	         			/*Object[] obj = {flightcarrierfilter.getCarrierCode(),
	         					flightcarrierfilter.getFlightNumber(),
	         					flightcarrierfilter.getFlightDate().toString().substring(0,11)};*/
	        			//Added by A-7794 as part of ICRD-197439 ; to reset the containerVO 
	         			actionContext.addError(new ErrorVO("mail.operations.flightnotexists"));
	 		        	return;
	         		}
	         		if ( flightValidationVOs.size() == 1) {
	         			log.log(Log.FINE, "flightValidationVOs has one VO");
	         			try {
	         				for (FlightValidationVO flightValidVO : flightValidationVOs) {
	         					BeanHelper.copyProperties(flightValidationVO,
	         							flightValidVO);
	         					//carditEnquirySession.setFlightValidationVO(flightValidationVO);
	         					//break;
	         				}
	         			} catch (SystemException systemException) {
	         				systemException.getMessage();
	         			}
	         		}
	     		flightFilterVO.setFlightSequenceNumber(flightValidationVO.getFlightSequenceNumber());
	     		operationalFlightVO.setFlightSequenceNumber(flightValidationVO.getFlightSequenceNumber());
	     		operationalFlightVO.setFlightRoute(flightValidationVO.getFlightRoute());
	     		
	     		}
	     		if(flightcarrierfilter.getFlightStatus()!=null && flightcarrierfilter.getFlightStatus().size()>0) {
	     			operationalFlightVO.setFlightStatus(String.join(",", flightcarrierfilter.getFlightStatus()));
	     		}
	     		if(flightcarrierfilter.getOperatingReference()!=null && flightcarrierfilter.getOperatingReference().trim().length()>0) {
	     			operationalFlightVO.setOperatingReference(flightcarrierfilter.getOperatingReference());
	     		}
	     		//added as part of IASCB-72176
	     		operationalFlightVO.setMailFlightOnly(flightcarrierfilter.isMailFlightOnly());
	 	        MailTrackingDefaultsDelegate delegate = new MailTrackingDefaultsDelegate();
	 	       try
	 	        {
	 	          this.log.log(3, new Object[] { "FlightFilterVO ------------> ", flightFilterVO });
	 	          mailAcceptanceVOs = 
	 	        		  delegate.findOutboundFlightsDetails(operationalFlightVO, flightDisplayPage);
	 	        }
	 	        catch (BusinessDelegateException businessDelegateException)
	 	        {
	 	          errors = handleDelegateException(businessDelegateException);
	 	        }
	 	      if(mailAcceptanceVOs==null){
					actionContext.addError(new ErrorVO("mailtracking.defaults.noflightdetails"));
					return;
				}
				
	 	      //Passing route inorder to set Fligth Route in MailFlight, otherwise needs to validate flight in list Containers command
	 	      List<MailAcceptance> mailflights =MailOutboundModelConverter.constructMailFlights(mailAcceptanceVOs,flightCarrierCode);
	 	       // FlightValidation flightValidation =MailOutboundModelConverter.constructFlightValidation(flightValidationVO, logonAttributes);
	 	   //added by A-8893 for IASCB-27535 starts
	 	      SharedDefaultsDelegate sharedDefaultsDelegate = new SharedDefaultsDelegate();
	 			
	 	      
	 	   //added by A-8893 for IASCB-27535 ends
	 	      
	 	      PageResult<MailAcceptance> pageList= new PageResult<MailAcceptance>(mailAcceptanceVOs,mailflights);
	 			if(mailflights.size()>0) {
	 			outboundModel.setMailflightspage(pageList);
	 			}
	 			else {
	 				outboundModel.setMailflightspage(null);
	 			}
	        } else  { 
	        	
	        	Collection<ErrorVO> errorsMail = new ArrayList<ErrorVO>();
		    	AirlineValidationVO airlineValidationVO = null;
		    	 String carrierCode = flightcarrierfilter.getCarrierCode();
		     	if (carrierCode != null && !"".equals(carrierCode)) {
		     		try {
		     			airlineValidationVO = airlineDelegate.validateAlphaCode(
		     					logonAttributes.getCompanyCode(),
		     					carrierCode.toUpperCase());
		     		}catch (BusinessDelegateException businessDelegateException) {
		     			errors = handleDelegateException(businessDelegateException);
		     		}
		     		if (errors != null && errors.size() > 0) {
		     			errors = new ArrayList<ErrorVO>();
		     			actionContext.addError(new ErrorVO("mailtracking.defaults.invalidcarrier",
				   				new Object[]{carrierCode.toUpperCase()}));
			        	return;
		     		}
		     	}

		     	
	        	
	        	carrierDisplayPage = outboundModel.getFlightCarrierFilter().getCarrierDisplayPage();
	        	operationalFlightVO.setCarrierId(airlineValidationVO.getAirlineIdentifier());
	 	         Page<MailAcceptanceVO> mailAcceptanceVOs = null;
		 	     MailTrackingDefaultsDelegate delegate = new MailTrackingDefaultsDelegate();
		 	     try
		 	        {
		 	          this.log.log(3, new Object[] { "FlightFilterVO ------------> ", flightFilterVO });
		 	          mailAcceptanceVOs = 
		 	        		  delegate.findOutboundCarrierDetails(operationalFlightVO,carrierDisplayPage);
		 	        }
		 	        catch (BusinessDelegateException businessDelegateException)
		 	        {
		 	          errors = handleDelegateException(businessDelegateException);
		 	        }
		 	      //Commented the below code for ISACB-45716
		 	     /* if(mailAcceptanceVOs==null){
					actionContext.addError(new ErrorVO(NO_CONTAINER_DETAILS));
					return;
				  } */
		 			List<MailAcceptance> mailCarriers=MailOutboundModelConverter.constructMailCarriers(mailAcceptanceVOs);
		 	       // FlightValidation flightValidation =MailOutboundModelConverter.constructFlightValidation(flightValidationVO, logonAttributes);
		 			PageResult<MailAcceptance> pageList= new PageResult<MailAcceptance>(mailAcceptanceVOs,mailCarriers);
		 			
		 			if(mailCarriers.size()>0) {
		 			outboundModel.setMailcarrierspage(pageList);
		 			
			 			}
			 			else {
			 				
			 				  MailAcceptanceVO newMailAcceptanceVO = new MailAcceptanceVO();
			 				  newMailAcceptanceVO.setCompanyCode(logonAttributes.getCompanyCode());
			 				  newMailAcceptanceVO.setDestination(operationalFlightVO.getPou());
			 				  newMailAcceptanceVO.setFlightCarrierCode(flightcarrierfilter.getCarrierCode().trim().toUpperCase());
			 	    		  newMailAcceptanceVO.setCarrierCode(flightcarrierfilter.getCarrierCode().trim().toUpperCase());
			 	    		  newMailAcceptanceVO.setCarrierId(operationalFlightVO.getCarrierId());
			 	    		  newMailAcceptanceVO.setPol(operationalFlightVO.getPol());
			 	    		  newMailAcceptanceVO.setFlightNumber("-1");
			 	    		  newMailAcceptanceVO.setFlightSequenceNumber(-1);
			 	    		  newMailAcceptanceVO.setLegSerialNumber(-1);
			 	    		  Page<MailAcceptanceVO> newMailAcceptanceVOs = new Page<MailAcceptanceVO>(new ArrayList<MailAcceptanceVO>(),1, 1, 1, 1, 1,false,1);
			 	    		  newMailAcceptanceVOs.add(newMailAcceptanceVO);
			 	    		  List<MailAcceptance> newMailCarriers=MailOutboundModelConverter.constructMailCarriers(newMailAcceptanceVOs);
			 	    		  PageResult<MailAcceptance> newPageList= new PageResult<MailAcceptance>(newMailAcceptanceVOs,newMailCarriers);
			 				  outboundModel.setMailcarrierspage(newPageList);
			 			}
		 			
		 			
		 			
		 			
		 			
		 			
	        }
	        Collection<WarehouseVO> warehouseVOs =  findWarehouses(logonAttributes);
			String warehouseCode = "";
			if(warehouseVOs != null && warehouseVOs.size() > 0){
				for(WarehouseVO warehouseVO:warehouseVOs){
					warehouseCode = warehouseVO.getWarehouseCode();
					break;
				}
			}
			Collection<Warehouse> warehouses= new ArrayList<Warehouse>();
			warehouses=MailOutboundModelConverter.constructWarehouse(warehouseVOs);
			Collection<String> transactionCodes = new ArrayList<String>();
			transactionCodes.add("warehouse.defaults.defaultmaillocation");
			Map<String,Collection<String>> locationsMap = null;
			
			LocationEnquiryFilterVO filterVO=new LocationEnquiryFilterVO();
			filterVO.setCompanyCode(logonAttributes.getCompanyCode());
			filterVO.setAirportCode(logonAttributes.getAirportCode());
			filterVO.setWarehouseCode(warehouseCode);
			filterVO.setTransactionCodes(transactionCodes);
			try{
				locationsMap = new MailTrackingDefaultsDelegate().findWarehouseTransactionLocations(filterVO);
			}catch(BusinessDelegateException businessDelegateException){
				errors = handleDelegateException(businessDelegateException);
			}
			String warehouseLocation = "";
			for(String key:locationsMap.keySet()){
				Collection<String> locations = locationsMap.get(key);
				if(locations != null && locations.size() > 0){
					warehouseLocation = ((ArrayList<String>)locations).get(0);
				}
				break;
			}
			outboundModel.setWareHouses(warehouses);
			results.add(outboundModel);
	        responseVO.setResults(results);
	        responseVO.setStatus("success");
	        actionContext.setResponseVO(responseVO);
	    	}
		  
	    	}	
	
	 private FlightFilterVO handleFlightFilterVO(
			 OperationalFlightVO operationalFlightVO,
				LogonAttributes logonAttributes){

			FlightFilterVO flightFilterVO = new FlightFilterVO();

			flightFilterVO.setCompanyCode(logonAttributes.getCompanyCode());
			//Modified by A-7794 as part of ICRD-197439
			flightFilterVO.setStation(operationalFlightVO.getPol());
			flightFilterVO.setDirection(OUTBOUND);
			flightFilterVO.setActiveAlone(false);
			if(operationalFlightVO.getFlightDate()!=null){
			flightFilterVO.setStringFlightDate(operationalFlightVO.getFlightDate().toString().substring(0,11));
	 		flightFilterVO.setFlightDate(operationalFlightVO.getFlightDate());
			}
	 		//flightFilterVO.setFlightDate(date);
	 		return flightFilterVO;
		}
	 
	 public Collection<WarehouseVO> findWarehouses(LogonAttributes logonAttributes) {
			Collection<WarehouseVO> warehouseVOs = null;
			Collection<ErrorVO> errors = null;
			try{
				warehouseVOs = new MailTrackingDefaultsDelegate().findAllWarehouses(
						logonAttributes.getCompanyCode(),logonAttributes.getAirportCode());
			}catch(BusinessDelegateException businessDelegateException){
				errors = handleDelegateException(businessDelegateException);
			}
			return warehouseVOs;
		}
	}

