package com.ibsplc.icargo.business.mail.operations.feature.saveconsignmentupload;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.Test;

import com.ibsplc.icargo.business.flight.operation.vo.FlightFilterVO;
import com.ibsplc.icargo.business.flight.operation.vo.FlightValidationVO;
import com.ibsplc.icargo.business.mail.operations.MailTrackingBusinessException;
import com.ibsplc.icargo.business.mail.operations.MailbagAlreadyAcceptedException;
import com.ibsplc.icargo.business.mail.operations.feature.saveconsignmentupload.enrichers.ConsignmentDetailsEnricher;
import com.ibsplc.icargo.business.mail.operations.feature.saveconsignmentupload.enrichers.ExistingConsignmentDetailsEnricher;
import com.ibsplc.icargo.business.mail.operations.feature.saveconsignmentupload.validators.FlightDetailsValidator;
import com.ibsplc.icargo.business.mail.operations.feature.saveconsignmentupload.validators.PostalAuthorityCodeValidator;
import com.ibsplc.icargo.business.mail.operations.proxy.FlightOperationsProxy;
import com.ibsplc.icargo.business.mail.operations.proxy.SharedAirlineProxy;
import com.ibsplc.icargo.business.mail.operations.vo.ConsignmentDocumentVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailInConsignmentVO;
import com.ibsplc.icargo.business.mail.operations.vo.RoutingInConsignmentVO;
import com.ibsplc.icargo.business.shared.airline.vo.AirlineValidationVO;
import com.ibsplc.icargo.framework.feature.AbstractFeatureTest;
import com.ibsplc.icargo.framework.floworchestration.context.ICargoSproutAdapter;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;

public class SaveConsignmentUploadFeatureTest extends AbstractFeatureTest {

	private SaveConsignmentUploadFeature spy;
	private ConsignmentDocumentVO consignmentDocumentVO;
	private PerformSaveConsignmentDocument performSaveConsignmentDocument;
	private PostalAuthorityCodeValidator postalAuthorityCodeValidator;
	private FlightDetailsValidator flightDetailsValidator;
	private ConsignmentDetailsEnricher consignmentDetailsEnricher;
	private ExistingConsignmentDetailsEnricher existingConsignmentDetailsEnricher;
	private FlightOperationsProxy flightOperationProxy;
	private SharedAirlineProxy sharedAirlineProxy;

	@Override
	public void setup() throws Exception {
		spy = spy((SaveConsignmentUploadFeature) ICargoSproutAdapter.getBean("mail.operations.saveconsignmentuploadfeature"));
		performSaveConsignmentDocument = mockBean("mail.operations.performSaveConsignmentDocument",PerformSaveConsignmentDocument.class);
		consignmentDocumentVO = setUpConsignmentDocumentVO();
		flightOperationProxy = mockProxy(FlightOperationsProxy.class);
		sharedAirlineProxy = mockProxy(SharedAirlineProxy.class);
		postalAuthorityCodeValidator = mockBean(SaveConsignmentUploadFeatureConstants.PA_CODE_VALIDATOR, PostalAuthorityCodeValidator.class);
		flightDetailsValidator = mockBean(SaveConsignmentUploadFeatureConstants.FLIGHT_DETAILS_VALIDATOR, FlightDetailsValidator.class);
		consignmentDetailsEnricher = mockBean(SaveConsignmentUploadFeatureConstants.CONSIGNMENT_DETAILS, ConsignmentDetailsEnricher.class);
		existingConsignmentDetailsEnricher = mockBean(SaveConsignmentUploadFeatureConstants.EXISTING_CONSIGNMENT_DETAILS, ExistingConsignmentDetailsEnricher.class);		
	}
	private ConsignmentDocumentVO setUpConsignmentDocumentVO() {
		ConsignmentDocumentVO consignmentDocumentVO = new ConsignmentDocumentVO();
		consignmentDocumentVO.setCompanyCode(getCompanyCode());
		consignmentDocumentVO.setConsignmentNumber("DEUS123456");
		consignmentDocumentVO.setPaCode("DE101");
		consignmentDocumentVO.setType("CN38");
		consignmentDocumentVO.setConsignmentDate(new LocalDate(LocalDate.NO_STATION, Location.NONE, false));
		MailInConsignmentVO mailInConsignmentVO = new MailInConsignmentVO();
		Collection<MailInConsignmentVO> mailInConsignmentVOs = new ArrayList<>();
		mailInConsignmentVO.setMailId("DEFRAAUSDFWAACA01200120001200");
		mailInConsignmentVOs.add(mailInConsignmentVO);
		consignmentDocumentVO.setMailInConsignment(mailInConsignmentVOs);
		Collection<RoutingInConsignmentVO> routingInConsignmentVOs = new ArrayList<>();
		RoutingInConsignmentVO routingInConsignmentVO = new RoutingInConsignmentVO();
		routingInConsignmentVO.setOnwardFlightNumber("123");
		routingInConsignmentVO.setOnwardCarrierCode(getCompanyCode());
		routingInConsignmentVO.setCompanyCode(getCompanyCode());
		routingInConsignmentVO.setOnwardFlightDate(new LocalDate(LocalDate.NO_STATION, Location.NONE, false));
		routingInConsignmentVOs.add(routingInConsignmentVO);
		consignmentDocumentVO.setRoutingInConsignmentVOs(routingInConsignmentVOs);
		return consignmentDocumentVO;
	}

	@Test()
	public void shouldInvokeAllValidtorsAndEnrichersSuccessfully_When_FeatureIsExecuted() throws Exception {
		AirlineValidationVO airlineValidationVO = new AirlineValidationVO();
		airlineValidationVO.setAirlineIdentifier(1134);
		Collection<FlightValidationVO> flightValidationVOs = new ArrayList<>();
		FlightValidationVO flightValidationVO = new FlightValidationVO();
		flightValidationVO.setFlightCarrierId(1134);
		flightValidationVOs.add(flightValidationVO);		
		doNothing().when(performSaveConsignmentDocument).perform(consignmentDocumentVO);
		doReturn(airlineValidationVO).when(sharedAirlineProxy).validateAlphaCode(any(String.class), any(String.class));
		doReturn(flightValidationVOs).when(flightOperationProxy).validateFlight(any(FlightFilterVO.class));
		spy.execute(consignmentDocumentVO);
		verify(postalAuthorityCodeValidator, times(1)).validate(consignmentDocumentVO);
		verify(flightDetailsValidator, times(1)).validate(consignmentDocumentVO);
		verify(consignmentDetailsEnricher, times(1)).enrich(consignmentDocumentVO);
		verify(existingConsignmentDetailsEnricher, times(1)).enrich(consignmentDocumentVO);
	}
	@Test()
	public void shouldRemoveExistingConsignment_When_ExistingConsignmentDetailsIsPresent() throws Exception {
		AirlineValidationVO airlineValidationVO = new AirlineValidationVO();
		airlineValidationVO.setAirlineIdentifier(1134);
		Collection<FlightValidationVO> flightValidationVOs = new ArrayList<>();
		FlightValidationVO flightValidationVO = new FlightValidationVO();
		flightValidationVO.setFlightCarrierId(1134);
		flightValidationVOs.add(flightValidationVO);
		ConsignmentDocumentVO existingConsignmentDocumentVO = new ConsignmentDocumentVO();
		Collection<ConsignmentDocumentVO> existingConsignmentDocumentVOs = new ArrayList<>();
		existingConsignmentDocumentVOs.add(existingConsignmentDocumentVO);
		consignmentDocumentVO.setExistingConsignmentDocumentVOs(existingConsignmentDocumentVOs);
		doNothing().when(performSaveConsignmentDocument).perform(consignmentDocumentVO.getExistingConsignmentDocumentVOs().iterator().next());
		doReturn(airlineValidationVO).when(sharedAirlineProxy).validateAlphaCode(any(String.class), any(String.class));
		doReturn(flightValidationVOs).when(flightOperationProxy).validateFlight(any(FlightFilterVO.class));
		spy.execute(consignmentDocumentVO);
		verify(postalAuthorityCodeValidator, times(1)).validate(consignmentDocumentVO);
		verify(flightDetailsValidator, times(1)).validate(consignmentDocumentVO);
		verify(consignmentDetailsEnricher, times(1)).enrich(consignmentDocumentVO);
		verify(existingConsignmentDetailsEnricher, times(1)).enrich(consignmentDocumentVO);
	}	
	@Test()
	public void shouldNotPerformSaveConsignmentUpload_When_ExistingConsignmentDetailsIsEmpty() throws Exception {
		AirlineValidationVO airlineValidationVO = new AirlineValidationVO();
		airlineValidationVO.setAirlineIdentifier(1134);
		Collection<FlightValidationVO> flightValidationVOs = new ArrayList<>();
		FlightValidationVO flightValidationVO = new FlightValidationVO();
		flightValidationVO.setFlightCarrierId(1134);
		flightValidationVOs.add(flightValidationVO);
		Collection<ConsignmentDocumentVO> existingConsignmentDocumentVOs = new ArrayList<>();
		consignmentDocumentVO.setExistingConsignmentDocumentVOs(existingConsignmentDocumentVOs);
		doReturn(airlineValidationVO).when(sharedAirlineProxy).validateAlphaCode(any(String.class), any(String.class));
		doReturn(flightValidationVOs).when(flightOperationProxy).validateFlight(any(FlightFilterVO.class));
		spy.execute(consignmentDocumentVO);
		verify(postalAuthorityCodeValidator, times(1)).validate(consignmentDocumentVO);
		verify(flightDetailsValidator, times(1)).validate(consignmentDocumentVO);
		verify(consignmentDetailsEnricher, times(1)).enrich(consignmentDocumentVO);
		verify(existingConsignmentDetailsEnricher, times(1)).enrich(consignmentDocumentVO);
	}	
	@Test(expected = MailTrackingBusinessException.class)
	public void shouldThrowBusinessException_When_PerformSaveConsignmentDocumentFails() throws Exception {
		AirlineValidationVO airlineValidationVO = new AirlineValidationVO();
		airlineValidationVO.setAirlineIdentifier(1134);
		Collection<FlightValidationVO> flightValidationVOs = new ArrayList<>();
		FlightValidationVO flightValidationVO = new FlightValidationVO();
		flightValidationVO.setFlightCarrierId(1134);
		flightValidationVOs.add(flightValidationVO);		
		doReturn(airlineValidationVO).when(sharedAirlineProxy).validateAlphaCode(any(String.class), any(String.class));
		doReturn(flightValidationVOs).when(flightOperationProxy).validateFlight(any(FlightFilterVO.class));
		doThrow(MailbagAlreadyAcceptedException.class).when(performSaveConsignmentDocument).perform(consignmentDocumentVO);
		spy.execute(consignmentDocumentVO);
	}
	@Test(expected = MailTrackingBusinessException.class)
	public void shouldThrowBusinessException_When_PerformSaveConsignmentDocumentFailsForExistingConsignment() throws Exception {
		AirlineValidationVO airlineValidationVO = new AirlineValidationVO();
		airlineValidationVO.setAirlineIdentifier(1134);
		Collection<FlightValidationVO> flightValidationVOs = new ArrayList<>();
		FlightValidationVO flightValidationVO = new FlightValidationVO();
		flightValidationVO.setFlightCarrierId(1134);
		flightValidationVOs.add(flightValidationVO);	
		ConsignmentDocumentVO existingConsignmentDocumentVO = new ConsignmentDocumentVO();
		Collection<ConsignmentDocumentVO> existingConsignmentDocumentVOs = new ArrayList<>();
		existingConsignmentDocumentVOs.add(existingConsignmentDocumentVO);
		consignmentDocumentVO.setExistingConsignmentDocumentVOs(existingConsignmentDocumentVOs);		
		doReturn(airlineValidationVO).when(sharedAirlineProxy).validateAlphaCode(any(String.class), any(String.class));
		doReturn(flightValidationVOs).when(flightOperationProxy).validateFlight(any(FlightFilterVO.class));
		doThrow(MailbagAlreadyAcceptedException.class).when(performSaveConsignmentDocument).perform(consignmentDocumentVO.getExistingConsignmentDocumentVOs().iterator().next());
		spy.execute(consignmentDocumentVO);
	}	
}
