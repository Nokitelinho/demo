package com.ibsplc.neoicargo.mail.component.proxy.eproxy;

import java.util.Collection;
import java.util.HashMap;

import com.ibsplc.icargo.business.flight.operation.vo.*;
import com.ibsplc.neoicargo.framework.ebl.api.EProductProxy;

@EProductProxy(module = "flight", submodule = "operation", name = "flightOperationsEProxy")
public interface FlightOperationsEProxy {
	Collection<FlightSegmentSummaryVO> findFlightSegments(String companyCode, int airlineId, String flightNumber,
			long flightSequenceNumber);

	Collection<FlightValidationVO> validateFlightForAirport(FlightFilterVO flightFilterVO);

	FlightSegmentValidationVO validateFlightSegment(FlightSegmentFilterVO segmentFilterVO);
	Collection<FlightSegmentCapacitySummaryVO> findFlightListings(FlightFilterVO filterVo);
	Collection<FlightSegmentCapacitySummaryVO> findSegmentCapacityDetailsForMail(
			Collection<FlightFilterVO> flightFilterVOs);
	Collection<FlightValidationVO> validateFlight(FlightFilterVO flightFilterVO);

	HashMap<String, Collection<FlightValidationVO>> validateFlightsForAirport(
			Collection<FlightFilterVO> flightFilterVOs);
	Collection<FlightValidationVO> getNonReferenceFlights(FlightFilterVO flightFilterVO);
}
