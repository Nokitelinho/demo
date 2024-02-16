package com.ibsplc.icargo.presentation.web.command.mail.operations.ux.outbound;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import com.ibsplc.icargo.business.flight.operation.vo.FlightFilterVO;
import com.ibsplc.icargo.business.flight.operation.vo.FlightSegmentCapacitySummaryVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.icargo.framework.util.unit.UnitConstants;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.json.vo.ResponseVO;
import com.ibsplc.icargo.framework.web.spring.command.AbstractCommand;
import com.ibsplc.icargo.framework.web.spring.controller.ActionContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.model.mail.operations.OutboundModel;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.FlightCarrierFilter;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.FlightSegmentCapacitySummary;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.MailAcceptance;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.MailOutboundModelConverter;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

public class FetchFlightCapacityCommand extends AbstractCommand{
    private static final String OUTBOUND = "O";
    private static final String CONST_FLIGHT = "FLIGHT";

    private static final String FLIGHT_TBC_TBA = "flight_tba_tbc";
    private static final String ROUTE_SEPARATOR = "-";
	private Log log = LogFactory.getLogger("OPERATIONS MAIL OUTBOUND NEO");
	@Override
	public void execute(ActionContext actionContext) throws BusinessDelegateException,
			CommandInvocationException {
		  OutboundModel outboundModel = (OutboundModel)actionContext.getScreenModel();
		  LogonAttributes logonAttributes = getLogonAttribute();
		  List<MailAcceptance> mailAcceptanceList = outboundModel.getMailAcceptanceList();
		  FlightCarrierFilter flightcarrierfilter = null;
		  flightcarrierfilter = outboundModel.getFlightCarrierFilter();
		  SharedDefaultsDelegate sharedDefaultsDelegate = 
	    	      new SharedDefaultsDelegate();
		  Collection<FlightSegmentCapacitySummaryVO> flightCapacitySummaryVOs = null;
		  Collection<FlightFilterVO> flightFilterVOs = new ArrayList<FlightFilterVO>(); 
		  Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		  String parameterValue = null;
			String systemPar = "mailtracking.defaults.booking.categoryCode";
			Map<String, String> systemUnitCodes = new HashMap<String, String>();
			Collection<String> systemUnitValues = new ArrayList<String>();
			systemUnitValues.add(systemPar);
			try {
				systemUnitCodes=sharedDefaultsDelegate.findSystemParameterByCodes(systemUnitValues);
				parameterValue = systemUnitCodes.get(systemPar);
			} catch (BusinessDelegateException e) {
				e.getMessage();
				handleDelegateException(e);
			}
		  for(MailAcceptance acceptanceList :mailAcceptanceList) {
 	    	  String flightRoute=acceptanceList.getFlightRoute();
 	    	  Collection<String>  routes=splitRouteToSegments(flightRoute);
 	    	  for(String route:routes) {
 	    		  FlightFilterVO flightVO = new FlightFilterVO();
				  flightVO.setCompanyCode(acceptanceList.getCompanyCode());
				  flightVO.setFlightCarrierId(acceptanceList.getCarrierId());
				  flightVO.setFlightNumber(acceptanceList.getFlightNumber());
				  flightVO.setFlightSequenceNumber(acceptanceList.getFlightSequenceNumber());
				  LocalDate date = new LocalDate(flightcarrierfilter.getAirportCode(),Location.ARP,false);
				  date.setDate(acceptanceList.getFlightDate());
				  flightVO.setFlightDate(date);
				  Set<String> carrierCodes=null;
				  carrierCodes=new HashSet<String>();
				  carrierCodes.add(acceptanceList.getCompanyCode());
				  flightVO.setCarrierCodes(carrierCodes);
				  String[] orgDest = route.split(ROUTE_SEPARATOR);
				  flightVO.setOrigin(orgDest[0]);
				  flightVO.setDestination(orgDest[1]);
				  flightFilterVOs.add(flightVO);	
 	    	  }
			 
			  
		  }
		  MailTrackingDefaultsDelegate delegate = new MailTrackingDefaultsDelegate();
		  try
	        {
			  flightCapacitySummaryVOs = 
	        		  delegate.fetchFlightCapacityDetails(flightFilterVOs);
	        }
	        catch (BusinessDelegateException businessDelegateException)
	        {
	          errors = handleDelegateException(businessDelegateException);
	        }
		  
		  Collection<FlightSegmentCapacitySummary> flightSegmentCapacityDetails= MailOutboundModelConverter.constructFlightCapacitySummaryList(flightCapacitySummaryVOs);
			 
		  Set<String> flightkeySet = new HashSet<String>();
		  StringBuilder flightKey = null;
		  
		  HashMap<String,Collection<FlightSegmentCapacitySummary>> flightSegmentHashMap = new HashMap<String, Collection<FlightSegmentCapacitySummary>>();
			 for (FlightSegmentCapacitySummary segmentCapacityDetails :flightSegmentCapacityDetails) {
				 Collection<FlightSegmentCapacitySummary> segmentCapacityDetailsList = new ArrayList<FlightSegmentCapacitySummary> ();
				 flightKey = new StringBuilder(segmentCapacityDetails.getFlightCarrierIdentifier()).append(segmentCapacityDetails.getFlightNumber()).append(segmentCapacityDetails.getFlightDate().toDisplayDateOnlyFormat());
				 if(flightkeySet.isEmpty()) {
					 flightkeySet.add(flightKey.toString());
					 segmentCapacityDetailsList.add(segmentCapacityDetails);
					 flightSegmentHashMap.put(flightKey.toString(),segmentCapacityDetailsList);
				 }
				 else {
					 if(flightkeySet.contains(flightKey.toString())) {
						 Collection<FlightSegmentCapacitySummary> updatedSegmentCapacities = flightSegmentHashMap.get(flightKey.toString());
						 updatedSegmentCapacities.add(segmentCapacityDetails);
						 flightSegmentHashMap.put(flightKey.toString(),updatedSegmentCapacities);
						 
					 }
					 else {
						 flightkeySet.add(flightKey.toString());
						 segmentCapacityDetailsList.add(segmentCapacityDetails);
						 flightSegmentHashMap.put(flightKey.toString(),segmentCapacityDetailsList);
					 }
				 }
			
			    
			 }
			 Collection<FlightSegmentCapacitySummary> newflightSegmentCapacityDetails = null;
			 for (String key : flightSegmentHashMap.keySet()) {
				 newflightSegmentCapacityDetails = flightSegmentHashMap.get(key);
				 for(FlightSegmentCapacitySummary flightSegment : newflightSegmentCapacityDetails) {
					 boolean mailAllotmentPresent= false;
					 double totalUtilised =0;
					 double totalMailCapacity=0;
					 double totalMailCapcityUtilised=0;
					 double totalCargoCapacity = 0;
					 double totalCargoUtised=0;
					 double mailUtilisedFromFS=0;
					 double TotalFSUtililised =0;
					 double totalMailtoBeUtised=0;
					 double totalMailUtilisedfromFS=0;
					 double totalCFRWeight=0;
					 double totalMALweight=0;
					 
					 Collection<FlightSegmentCapacitySummary> allotments =flightSegment.getAllotments();
					//Start to Calculate the mail utilisation from FS. This happens in 2 cases:
					//1)Mail accepted without an allotment
					//2)Mail allotment is over utilised.
					 for( FlightSegmentCapacitySummary allotment : allotments) {
					     if(!allotment.getAllotmentId().equals("FS") && allotment.getCategoryCode().equals(parameterValue)) {
					    	 //This is the case where mail allotment is created
					    	   mailAllotmentPresent= true;
							   totalMailCapacity= totalMailCapacity + allotment.getTotalAllotmentWeight().getSystemValue();
							   totalMailCapcityUtilised= allotment.getMailUtised().getSystemValue();
							   //In case of mail over utilisation, we need to calculate capacity utilised from FS as well as from the allotment seperately.
							   if(totalMailCapcityUtilised>totalMailCapacity) {
								   //mail capacity utilised from FS
								   mailUtilisedFromFS = totalMailCapcityUtilised - totalMailCapacity;
								   //mail capacity utilised from the allotment
								   totalMailCapcityUtilised=totalMailCapacity;//ICRD-334630
							   }
					       }
					 }
					//End 
					 for( FlightSegmentCapacitySummary allotment : allotments) {
					   if(allotment.getAllotmentId().equals("FS")) {
						   flightSegment.setSegmentTotalWeight(allotment.getTotalWeight());
							   TotalFSUtililised = allotment.getConfirmedBookingWeight().getSystemValue();////ICRD-334630
							   
							   if(!mailAllotmentPresent && allotment.getMailUtised().getSystemValue()!=0) {
								   //This is the case where no mal allotment is created. The capacity will be utilised from FS 
								   totalMailUtilisedfromFS = allotment.getMailUtised().getSystemValue();
							   } else {
								   totalMailUtilisedfromFS=mailUtilisedFromFS;
							   }
							  // totalCargoUtised=totalCargoUtised + (TotalFSUtililised-totalMailUtilisedfromFS);ICRD-333822
							   totalMailCapcityUtilised=totalMailCapcityUtilised+totalMailUtilisedfromFS;//ICRD-334013
							   
							   
							   
					  }
					   else {
							   if(!allotment.getCategoryCode().equals(parameterValue)) {
							   totalCargoCapacity= totalCargoCapacity + allotment.getTotalAllotmentWeight().getSystemValue();
								  // totalCargoUtised = totalCargoUtised + (allotment.getTotalAllotmentWeight().getSystemValue() - allotment.getTotalAlotmentAvailableWeight().getSystemValue());ICRD-333822
								 
							   }
							 
						   }
						//   totalUtilised=totalCargoUtised + totalMailCapcityUtilised;ICRD-333822
					   
					   totalCFRWeight=totalCFRWeight+allotment.getConfirmedBookingWeight().getSystemValue();
					   totalMALweight=totalMALweight+allotment.getMailUtised().getSystemValue();
					 }
					 
					 if(totalCFRWeight!=0){
					 totalCargoUtised=totalCFRWeight-(totalMALweight/allotments.size());//ICRD-333822
					 }
					 totalUtilised=totalCargoUtised + totalMailCapcityUtilised;//ICRD-333822
					  
					 
					 
					 flightSegment.setTotalUtilised((new Measure(UnitConstants.WEIGHT,totalUtilised)));
					 flightSegment.setMailCapacity((new Measure(UnitConstants.WEIGHT,totalMailCapacity)));
					 flightSegment.setMailUtised((new Measure(UnitConstants.WEIGHT,totalMailCapcityUtilised)));
					 flightSegment.setCargoCapacity((new Measure(UnitConstants.WEIGHT,totalCargoCapacity)));
					 flightSegment.setCargoUtilised((new Measure(UnitConstants.WEIGHT,totalCargoUtised)));
					 //set all values totalUtilised,totalMailCapacity,totalMailCapcityUtilised, totalCargoCapacity, totalCargoUtised to flightSegment
				 }
			 }
			 ResponseVO responseVO = new ResponseVO();
			 List<OutboundModel> results = new ArrayList();
			 outboundModel.setFlightCapacityDetails(flightSegmentHashMap);
			 results.add(outboundModel);
		     responseVO.setResults(results);
		     responseVO.setStatus("success");
		     actionContext.setResponseVO(responseVO);
	}
	
	
	private Collection<String> splitRouteToSegments(String route) {
		Collection<String> segments = null;

		if (route != null && route.length() > 0) {

			/* Split route into statins and find ll possible segments */
			String[] segmentArray = route.split(ROUTE_SEPARATOR);
			int segmentSize = segmentArray.length;

			segments = new ArrayList<String>();

			int segmentArr = 1;
			/** Creating new Segments for given Route */
			for (int p = 0; p < segmentSize; p++) {
				if (segmentArr < segmentSize) {
					for (int q = segmentArr; q < segmentSize; q++) {
						if (!(segmentArray[p].equals(segmentArray[q]))) {
							segments.add(new StringBuilder().append(
									segmentArray[p]).append('-').append(
									segmentArray[q]).toString());
						}
					}
				}
				segmentArr++;
			}
		}

		return segments;
}
}
