package com.ibsplc.icargo.business.mail.operations.feature.saveconsignmentupload.enrichers;

import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.junit.Test;

import com.ibsplc.icargo.business.flight.operation.vo.FlightValidationVO;
import com.ibsplc.icargo.business.mail.operations.feature.saveconsignmentupload.SaveConsignmentUploadFeatureConstants;
import com.ibsplc.icargo.business.mail.operations.vo.ConsignmentDocumentVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailInConsignmentVO;
import com.ibsplc.icargo.business.mail.operations.vo.RoutingInConsignmentVO;
import com.ibsplc.icargo.framework.feature.AbstractFeatureTest;
import com.ibsplc.icargo.framework.floworchestration.context.ICargoSproutAdapter;
import com.ibsplc.icargo.framework.util.parameter.ParameterUtil;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.util.unit.UnitConstants;
import com.ibsplc.icargo.framework.util.unit.cache.UnitCache;
import com.ibsplc.icargo.framework.util.unit.vo.UnitConversionNewVO;
import com.ibsplc.xibase.server.framework.cache.CacheFactory;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;

public class ConsignmentDetailsEnricherTest extends AbstractFeatureTest {

	private static final String COMPANY_CODE = "IBS";
	private ConsignmentDetailsEnricher enricher;
	private ConsignmentDocumentVO consignmentDocumentVO;
	private UnitCache unitCache;
	private UnitConversionNewVO unitConversionNewVO;

	@Override
	public void setup() throws Exception {
		enricher = spy((ConsignmentDetailsEnricher) (ICargoSproutAdapter
				.getBean(SaveConsignmentUploadFeatureConstants.CONSIGNMENT_DETAILS)));
		consignmentDocumentVO =setUpConsignmentDocumentVO();
		unitCache = spy(UnitCache.class);
		doReturn(unitCache).when(CacheFactory.getInstance()).getCache(UnitCache.NAME);
		unitConversionNewVO = setUpUnitConversionVO();
		doReturn("US001").when(ParameterUtil.getInstance()).getSystemParameterValue(any(String.class));
		doReturn("DE101").when(enricher)
				.getContextObject(SaveConsignmentUploadFeatureConstants.SAVE_CONSIGNMENT_UPLOAD_PACODE);
		doReturn("FRA").when(enricher)
				.getContextObject(SaveConsignmentUploadFeatureConstants.SAVE_CONSIGNMENT_UPLOAD_ORIGIN);
		doReturn("DFW").when(enricher)
				.getContextObject(SaveConsignmentUploadFeatureConstants.SAVE_CONSIGNMENT_UPLOAD_DESTINATION);
		
	}
	private UnitConversionNewVO setUpUnitConversionVO() {
		UnitConversionNewVO unitConversionNewVO = new UnitConversionNewVO();
		unitConversionNewVO.setCompanyCode(COMPANY_CODE);
		unitConversionNewVO.setFromUnit("H");
		unitConversionNewVO.setToUnit(UnitConstants.WEIGHT_UNIT_KILOGRAM);
		unitConversionNewVO.setUnitType(UnitConstants.MAIL_WGT);
		return unitConversionNewVO;
	}
	private ConsignmentDocumentVO setUpConsignmentDocumentVO() {
		ConsignmentDocumentVO consignmentDocumentVO = new ConsignmentDocumentVO();
		consignmentDocumentVO.setCompanyCode(COMPANY_CODE);
		consignmentDocumentVO.setConsignmentNumber("DEUS123456");
		consignmentDocumentVO.setPaCode("DE101");
		consignmentDocumentVO.setType("CN38");
		consignmentDocumentVO.setConsignmentDate(new LocalDate(LocalDate.NO_STATION, Location.NONE, false));
		consignmentDocumentVO.setOperation("OUTBOUND");
		MailInConsignmentVO mailInConsignmentVO = new MailInConsignmentVO();
		Collection<MailInConsignmentVO> mailInConsignmentVOs = new ArrayList<>();
		mailInConsignmentVO.setMailId("DEFRAAUSDFWAACA01200120001200");
		mailInConsignmentVOs.add(mailInConsignmentVO);
		consignmentDocumentVO.setMailInConsignmentVOs(new Page<MailInConsignmentVO>((ArrayList<MailInConsignmentVO>)mailInConsignmentVOs, 0, 0, 0, 0, 0, false));
		Collection<RoutingInConsignmentVO> routingInConsignmentVOs = new ArrayList<>();
		RoutingInConsignmentVO routingInConsignmentVO = new RoutingInConsignmentVO();
		routingInConsignmentVO.setOnwardFlightNumber("0123");
		routingInConsignmentVO.setOnwardCarrierCode(COMPANY_CODE);
		routingInConsignmentVO.setPol("FRA");
		routingInConsignmentVO.setPou("DFW");
		routingInConsignmentVO.setCompanyCode(COMPANY_CODE);
		routingInConsignmentVO.setConsignmentNumber("DEUS123456");
		routingInConsignmentVOs.add(routingInConsignmentVO);
		consignmentDocumentVO.setRoutingInConsignmentVOs(routingInConsignmentVOs);
		return consignmentDocumentVO;
	}	
	private Map<String, Collection<FlightValidationVO>> setUpFlightMap() {
		Map<String, Collection<FlightValidationVO>> flightMap = new HashMap<>();
		Collection<FlightValidationVO>flightValidationVOs = new ArrayList<>();
		FlightValidationVO flightValidationVO = new FlightValidationVO();
		flightValidationVO.setFlightCarrierId(1134);
		flightValidationVO.setFlightNumber("0123");
		flightValidationVO.setFlightSequenceNumber(1);
		flightValidationVOs.add(flightValidationVO);
		flightMap.put("AVDEUS123456FRADFW0123", flightValidationVOs);
		return flightMap;
	}	

	@Test
	public void shouldEnrichConsignmentDetailsForInternationalMail() throws SystemException {
		Map<String, Collection<FlightValidationVO>> flightMap = setUpFlightMap();
		doReturn(flightMap).when(enricher)
		.getContextObject(SaveConsignmentUploadFeatureConstants.SAVE_CONSIGNMENT_UPLOAD_FLIGHT_VALIDATION_MAP);
		doReturn(unitConversionNewVO).when(unitCache). getUnitConversionForToUnit (any(String.class), any(String.class), any(String.class), any(Double.class));
		enricher.enrich(consignmentDocumentVO);
		assertTrue(Objects.nonNull(consignmentDocumentVO.getAirportCode()));
	}
	@Test
	public void shouldEnrichConsignmentDetailsForDomesticMail() throws SystemException {
		Map<String, Collection<FlightValidationVO>> flightMap = setUpFlightMap();
		consignmentDocumentVO.getMailInConsignmentVOs().iterator().next().setMailId("43FQCI17BC04");
		doReturn("US001").when(enricher)
		.getContextObject(SaveConsignmentUploadFeatureConstants.SAVE_CONSIGNMENT_UPLOAD_PACODE);
		consignmentDocumentVO.getMailInConsignmentVOs().iterator().next().setPaCode("US001");
		doReturn(flightMap).when(enricher)
		.getContextObject(SaveConsignmentUploadFeatureConstants.SAVE_CONSIGNMENT_UPLOAD_FLIGHT_VALIDATION_MAP);
		doReturn(unitConversionNewVO).when(unitCache). getUnitConversionForToUnit (any(String.class), any(String.class), any(String.class), any(Double.class));
		enricher.enrich(consignmentDocumentVO);
		assertTrue(Objects.nonNull(consignmentDocumentVO.getAirportCode()));
	}	
	@Test
	public void shouldNotEnrichConsignmentDetails_When_MailDetailsIsNull() throws SystemException {
		Map<String, Collection<FlightValidationVO>> flightMap = setUpFlightMap();
		consignmentDocumentVO.setMailInConsignmentVOs(null);
		doReturn(flightMap).when(enricher)
		.getContextObject(SaveConsignmentUploadFeatureConstants.SAVE_CONSIGNMENT_UPLOAD_FLIGHT_VALIDATION_MAP);
		enricher.enrich(consignmentDocumentVO);
		assertTrue(Objects.isNull(consignmentDocumentVO.getMailInConsignmentVOs()));
	}	
	@Test
	public void shouldNotEnrichConsignmentDetails_When_MailDetailsIsEmpty() throws SystemException {
		Map<String, Collection<FlightValidationVO>> flightMap = setUpFlightMap();
		consignmentDocumentVO.setMailInConsignmentVOs(new Page<>());
		doReturn(flightMap).when(enricher)
		.getContextObject(SaveConsignmentUploadFeatureConstants.SAVE_CONSIGNMENT_UPLOAD_FLIGHT_VALIDATION_MAP);
		enricher.enrich(consignmentDocumentVO);
		assertTrue(consignmentDocumentVO.getMailInConsignmentVOs().size()==0);
	}	
	@Test
	public void shouldNotEnrichConsignmentDetails_When_MailbagIdIsNull() throws SystemException {
		Map<String, Collection<FlightValidationVO>> flightMap = new HashMap<>();
		consignmentDocumentVO.getMailInConsignmentVOs().iterator().next().setMailId(null);
		doReturn(flightMap).when(enricher)
		.getContextObject(SaveConsignmentUploadFeatureConstants.SAVE_CONSIGNMENT_UPLOAD_FLIGHT_VALIDATION_MAP);
		enricher.enrich(consignmentDocumentVO);
		assertTrue(Objects.isNull(consignmentDocumentVO.getMailInConsignmentVOs().iterator().next().getMailCategoryCode()));
	}
	@Test
	public void shouldNotEnrichConsignmentDetails_When_RoutingDetailIsNull() throws SystemException {
		Map<String, Collection<FlightValidationVO>> flightMap = setUpFlightMap();
		consignmentDocumentVO.setRoutingInConsignmentVOs(null);
		doReturn(flightMap).when(enricher)
		.getContextObject(SaveConsignmentUploadFeatureConstants.SAVE_CONSIGNMENT_UPLOAD_FLIGHT_VALIDATION_MAP);
		doReturn(unitConversionNewVO).when(unitCache). getUnitConversionForToUnit (any(String.class), any(String.class), any(String.class), any(Double.class));
		enricher.enrich(consignmentDocumentVO);
		assertTrue(Objects.isNull(consignmentDocumentVO.getRoutingInConsignmentVOs()));
	}
	@Test
	public void shouldNotEnrichConsignmentDetails_When_FlightMapIsNull() throws SystemException {
		doReturn(null).when(enricher)
		.getContextObject(SaveConsignmentUploadFeatureConstants.SAVE_CONSIGNMENT_UPLOAD_FLIGHT_VALIDATION_MAP);
		doReturn("").when(enricher)
		.getContextObject(SaveConsignmentUploadFeatureConstants.SAVE_CONSIGNMENT_UPLOAD_PACODE);
		consignmentDocumentVO.setOperation("INBOUND");
		enricher.enrich(consignmentDocumentVO);
		assertTrue(Objects.isNull(consignmentDocumentVO.getRoutingInConsignmentVOs().iterator().next().getPaCode()));
	}
	@Test
	public void shouldNotEnrichConsignmentDetails_When_PACodeIsNull() throws SystemException {
		Map<String, Collection<FlightValidationVO>> flightMap = setUpFlightMap();
		consignmentDocumentVO.setMailInConsignmentVOs(null);
		doReturn(flightMap).when(enricher)
		.getContextObject(SaveConsignmentUploadFeatureConstants.SAVE_CONSIGNMENT_UPLOAD_FLIGHT_VALIDATION_MAP);
		doReturn(null).when(enricher)
		.getContextObject(SaveConsignmentUploadFeatureConstants.SAVE_CONSIGNMENT_UPLOAD_PACODE);
		consignmentDocumentVO.setOperation(null);
		consignmentDocumentVO.setRoutingInConsignmentVOs(new ArrayList<>());
		enricher.enrich(consignmentDocumentVO);
		assertTrue(Objects.isNull(consignmentDocumentVO.getMailInConsignmentVOs()));
	}	

}
