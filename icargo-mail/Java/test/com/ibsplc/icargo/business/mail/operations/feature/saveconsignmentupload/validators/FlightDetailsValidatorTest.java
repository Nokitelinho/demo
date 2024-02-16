package com.ibsplc.icargo.business.mail.operations.feature.saveconsignmentupload.validators;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.Test;

import com.ibsplc.icargo.business.flight.operation.vo.FlightFilterVO;
import com.ibsplc.icargo.business.flight.operation.vo.FlightValidationVO;
import com.ibsplc.icargo.business.mail.operations.MailTrackingBusinessException;
import com.ibsplc.icargo.business.mail.operations.proxy.FlightOperationsProxy;
import com.ibsplc.icargo.business.mail.operations.proxy.SharedAirlineProxy;
import com.ibsplc.icargo.business.mail.operations.vo.ConsignmentDocumentVO;
import com.ibsplc.icargo.business.mail.operations.vo.RoutingInConsignmentVO;
import com.ibsplc.icargo.business.shared.airline.vo.AirlineValidationVO;
import com.ibsplc.icargo.framework.feature.AbstractFeatureTest;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;

public class FlightDetailsValidatorTest extends AbstractFeatureTest {

	private FlightDetailsValidator validator;
	private FlightOperationsProxy flightOperationProxy;
	private SharedAirlineProxy sharedAirlineProxy;
	
	@Override
	public void setup() throws Exception {
		validator = spy(new FlightDetailsValidator());
		flightOperationProxy = mockProxy(FlightOperationsProxy.class);
		sharedAirlineProxy = mockProxy(SharedAirlineProxy.class);

	}
	private ConsignmentDocumentVO setUpConsignmentDocumentVO(){
		ConsignmentDocumentVO consignmentDocumentVO = new ConsignmentDocumentVO();
		Collection<RoutingInConsignmentVO> routingInConsignmentVOs = new ArrayList<>();
		RoutingInConsignmentVO routingInConsignmentVO = new RoutingInConsignmentVO();
		routingInConsignmentVO.setCompanyCode(getCompanyCode());
		routingInConsignmentVO.setOnwardCarrierCode(getCompanyCode());
		routingInConsignmentVO.setOnwardFlightNumber("123");
		routingInConsignmentVO.setOnwardFlightDate(new LocalDate(LocalDate.NO_STATION, Location.NONE, false));
		routingInConsignmentVOs.add(routingInConsignmentVO);
		consignmentDocumentVO.setRoutingInConsignmentVOs(routingInConsignmentVOs);
		return consignmentDocumentVO;
	}
	@Test
	public void shouldValidateAndNotThrowException_When_FlightDetailsPresent() throws Exception{
		ConsignmentDocumentVO consignmentDocumentVO=setUpConsignmentDocumentVO();
		AirlineValidationVO airlineValidationVO = new AirlineValidationVO();
		airlineValidationVO.setAirlineIdentifier(1134);
		Collection<FlightValidationVO> flightValidationVOs = new ArrayList<>();
		FlightValidationVO flightValidationVO = new FlightValidationVO();
		flightValidationVO.setFlightCarrierId(1134);
		flightValidationVOs.add(flightValidationVO);
		doReturn(airlineValidationVO).when(sharedAirlineProxy).validateAlphaCode(any(String.class), any(String.class));
		doReturn(flightValidationVOs).when(flightOperationProxy).validateFlight(any(FlightFilterVO.class));
		validator.validate(consignmentDocumentVO);
	}
	@Test
	public void shouldValidateAndNotThrowException_When_MultipleFlightDetailsPresent() throws Exception{
		ConsignmentDocumentVO consignmentDocumentVO=setUpConsignmentDocumentVO();
		RoutingInConsignmentVO routingInConsignmentVO = new RoutingInConsignmentVO();
		routingInConsignmentVO.setCompanyCode(getCompanyCode());
		routingInConsignmentVO.setOnwardCarrierCode(getCompanyCode());
		routingInConsignmentVO.setOnwardFlightNumber("234");
		routingInConsignmentVO.setOnwardFlightDate(new LocalDate(LocalDate.NO_STATION, Location.NONE, false));
		consignmentDocumentVO.getRoutingInConsignmentVOs().add(routingInConsignmentVO);
		AirlineValidationVO airlineValidationVO = new AirlineValidationVO();
		airlineValidationVO.setAirlineIdentifier(1134);
		Collection<FlightValidationVO> flightValidationVOs = new ArrayList<>();
		FlightValidationVO flightValidationVO = new FlightValidationVO();
		flightValidationVO.setFlightCarrierId(1134);
		flightValidationVOs.add(flightValidationVO);
		doReturn(airlineValidationVO).when(sharedAirlineProxy).validateAlphaCode(any(String.class), any(String.class));
		doReturn(flightValidationVOs).when(flightOperationProxy).validateFlight(any(FlightFilterVO.class));
		validator.validate(consignmentDocumentVO);
	}	
	@Test(expected = MailTrackingBusinessException.class)
	public void shouldValidateAndThrowException_When_InvalidCarrierCodeIsPresent() throws Exception{
		ConsignmentDocumentVO consignmentDocumentVO=setUpConsignmentDocumentVO();
		doReturn(null).when(sharedAirlineProxy).validateAlphaCode(any(String.class), any(String.class));
		validator.validate(consignmentDocumentVO);
	}
	@Test(expected = MailTrackingBusinessException.class)
	public void shouldValidateAndThrowException_When_InvalidFlightNumberIsPresent() throws Exception{
		ConsignmentDocumentVO consignmentDocumentVO=setUpConsignmentDocumentVO();
		AirlineValidationVO airlineValidationVO = new AirlineValidationVO();
		airlineValidationVO.setAirlineIdentifier(1134);
		doReturn(airlineValidationVO).when(sharedAirlineProxy).validateAlphaCode(any(String.class), any(String.class));
		doReturn(null).when(flightOperationProxy).validateFlight(any(FlightFilterVO.class));
		validator.validate(consignmentDocumentVO);
	}
	@Test(expected = MailTrackingBusinessException.class)
	public void shouldValidateAndThrowException_When_EmptyFlightDetailsArePresent() throws Exception{
		ConsignmentDocumentVO consignmentDocumentVO=setUpConsignmentDocumentVO();
		AirlineValidationVO airlineValidationVO = new AirlineValidationVO();
		airlineValidationVO.setAirlineIdentifier(1134);
		Collection<FlightValidationVO> flightValidationVOs = new ArrayList<>();
		doReturn(airlineValidationVO).when(sharedAirlineProxy).validateAlphaCode(any(String.class), any(String.class));
		doReturn(flightValidationVOs).when(flightOperationProxy).validateFlight(any(FlightFilterVO.class));
		validator.validate(consignmentDocumentVO);
	}	
	@Test(expected = MailTrackingBusinessException.class)
	public void shouldValidateAndThrowException_When_FlightDateIsNotPresent() throws Exception{
		ConsignmentDocumentVO consignmentDocumentVO=setUpConsignmentDocumentVO();
		consignmentDocumentVO.getRoutingInConsignmentVOs().iterator().next().setOnwardFlightDate(null);
		consignmentDocumentVO.getRoutingInConsignmentVOs().iterator().next().setOnwardFlightNumber("13@");
		consignmentDocumentVO.setOperation("INBOUND");
		AirlineValidationVO airlineValidationVO = new AirlineValidationVO();
		airlineValidationVO.setAirlineIdentifier(1134);
		doReturn(airlineValidationVO).when(sharedAirlineProxy).validateAlphaCode(any(String.class), any(String.class));
		validator.validate(consignmentDocumentVO);
	}	
	@Test(expected = MailTrackingBusinessException.class)
	public void shouldValidateAndThrowException_When_FlightDateIsNull() throws Exception{
		ConsignmentDocumentVO consignmentDocumentVO=setUpConsignmentDocumentVO();
		consignmentDocumentVO.getRoutingInConsignmentVOs().iterator().next().setOnwardFlightDate(null);
		consignmentDocumentVO.getRoutingInConsignmentVOs().iterator().next().setOnwardFlightNumber("13a");
		consignmentDocumentVO.setOperation("");
		AirlineValidationVO airlineValidationVO = new AirlineValidationVO();
		airlineValidationVO.setAirlineIdentifier(1134);
		doReturn(airlineValidationVO).when(sharedAirlineProxy).validateAlphaCode(any(String.class), any(String.class));
		validator.validate(consignmentDocumentVO);
	}	
	@Test(expected = MailTrackingBusinessException.class)
	public void shouldValidateAndThrowException_When_FlightDateIsIncorrect() throws Exception{
		ConsignmentDocumentVO consignmentDocumentVO=setUpConsignmentDocumentVO();
		consignmentDocumentVO.getRoutingInConsignmentVOs().iterator().next().setOnwardFlightDate(null);
		consignmentDocumentVO.getRoutingInConsignmentVOs().iterator().next().setOnwardFlightNumber("13A");
		consignmentDocumentVO.setOperation("OUTBOUND");
		AirlineValidationVO airlineValidationVO = new AirlineValidationVO();
		airlineValidationVO.setAirlineIdentifier(1134);
		doReturn(airlineValidationVO).when(sharedAirlineProxy).validateAlphaCode(any(String.class), any(String.class));
		validator.validate(consignmentDocumentVO);
	}		
	

}
