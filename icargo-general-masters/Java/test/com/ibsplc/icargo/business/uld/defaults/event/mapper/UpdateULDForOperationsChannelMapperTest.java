/*
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of
 * IBS Software Services (P) Ltd.
 *
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.uld.defaults.event.mapper;


import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doReturn;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

import org.junit.Test;

import com.ibsplc.icargo.business.operations.shipment.cto.vo.CTOAcceptanceVO;
import com.ibsplc.icargo.business.operations.shipment.cto.vo.ShipmentInULDAcceptanceVO;
import com.ibsplc.icargo.business.operations.shipment.vo.ULDAcceptanceVO;
import com.ibsplc.icargo.business.uld.defaults.message.vo.FlightDetailsVO;
import com.ibsplc.icargo.framework.feature.AbstractFeatureTest;
import com.ibsplc.icargo.framework.floworchestration.context.ICargoSproutAdapter;
import com.ibsplc.icargo.framework.util.parameter.ParameterUtil;
import com.ibsplc.icargo.framework.util.parameter.ParameterUtilMock;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;

/**
 * 
 * @author A-7900
 *
 */
public class UpdateULDForOperationsChannelMapperTest extends AbstractFeatureTest {

	UpdateULDForOperationsChannelMapper updateULDForOperationsChannelMapper;
	FlightDetailsVO flightDetailsVO;
	ShipmentInULDAcceptanceVO shipmentInULDAcceptanceVO;
	Collection<ShipmentInULDAcceptanceVO> shipmentInULDAcceptanceVOs;
	CTOAcceptanceVO ctoAcceptanceVO;
	Collection<FlightDetailsVO> flightDetailsVOs;
	ULDAcceptanceVO uldAcceptanceVO;

	public static final java.lang.String OPERATION_FLAG_INSERT = "I";
	public static final java.lang.String OPERATION_FLAG_DELETE = "D";

	@Override
	public void setup() throws Exception {

		updateULDForOperationsChannelMapper = (UpdateULDForOperationsChannelMapper) ICargoSproutAdapter
				.getBean(UpdateULDForOperationsChannelMapper.class);
		populateCTOAcceptanceVO();
		populateFlightDetailsVO();
		populateShipmentInULDAcceptanceVO();
		ParameterUtilMock.mockParameterUtil();
		uldAcceptanceVO = new ULDAcceptanceVO();

	}

	private void populateCTOAcceptanceVO() {
		ctoAcceptanceVO = new CTOAcceptanceVO();
		ctoAcceptanceVO.setCompanyCode("AV");
	}

	private void populateShipmentInULDAcceptanceVO() {
		shipmentInULDAcceptanceVOs = new ArrayList<>();
		shipmentInULDAcceptanceVO = new ShipmentInULDAcceptanceVO();
		shipmentInULDAcceptanceVO.setCompanyCode("AV");
		shipmentInULDAcceptanceVO.setOperationFlag(OPERATION_FLAG_INSERT);
		shipmentInULDAcceptanceVO.setFlightNumber("1122");
		shipmentInULDAcceptanceVO.setAirportCode("CDG");
		shipmentInULDAcceptanceVO.setCarrierCode("EK");
		shipmentInULDAcceptanceVO.setFlightDate(
				new LocalDate(shipmentInULDAcceptanceVO.getAirportCode(), Location.ARP, false).setDate("21-Aug-2019"));
		shipmentInULDAcceptanceVOs.add(shipmentInULDAcceptanceVO);
		ctoAcceptanceVO.setShipmentInULDAcceptanceDetails(shipmentInULDAcceptanceVOs);
	}

	private void populateFlightDetailsVO() {
		flightDetailsVOs = new ArrayList<>();
		flightDetailsVO = new FlightDetailsVO();
		flightDetailsVO.setCompanyCode("AV");
		flightDetailsVO.setFlightNumber("1122");
		flightDetailsVO.setCarrierCode("EK");
		flightDetailsVO.setCurrentAirport("CDG");
		flightDetailsVO.setFlightDate(
				new LocalDate(flightDetailsVO.getCurrentAirport(), Location.ARP, false).setDate("21-Aug-2019"));
		flightDetailsVOs.add(flightDetailsVO);
	}

	@Test
	public void shouldMapFlightDetailVOWithCTOAcceptanceVODetails_When_ShipmentInULDAcceptanceDetailsAreCaptured()
			throws Exception {
		shipmentInULDAcceptanceVOs = new ArrayList<>();
		ShipmentInULDAcceptanceVO shipmentInULDAcceptanceVOFirst = new ShipmentInULDAcceptanceVO();
		shipmentInULDAcceptanceVOFirst.setCompanyCode("AV");
		shipmentInULDAcceptanceVOFirst.setAirportCode("CDG");
		shipmentInULDAcceptanceVOFirst.setOperationFlag(OPERATION_FLAG_INSERT);
		shipmentInULDAcceptanceVOs.add(shipmentInULDAcceptanceVOFirst);
		ShipmentInULDAcceptanceVO shipmentInULDAcceptanceVOSecond = new ShipmentInULDAcceptanceVO();
		shipmentInULDAcceptanceVOSecond.setCompanyCode("AV");
		shipmentInULDAcceptanceVOSecond.setAirportCode("CDG");
		shipmentInULDAcceptanceVOSecond.setOperationFlag(OPERATION_FLAG_INSERT);
		shipmentInULDAcceptanceVOs.add(shipmentInULDAcceptanceVOSecond);
		ctoAcceptanceVO.setShipmentInULDAcceptanceDetails(shipmentInULDAcceptanceVOs);
		flightDetailsVOs = updateULDForOperationsChannelMapper
				.mapAcceptanceDetailsToPayload(ctoAcceptanceVO);
		assertTrue(flightDetailsVOs != null && !flightDetailsVOs.isEmpty());
	}

	@Test
	public void shouldNotMapFlightDetailVOWithCTOAcceptanceVODetails_When_ShipmentInULDAcceptanceDetailsAreNotCaptured()
			throws Exception {
		ctoAcceptanceVO.setShipmentInULDAcceptanceDetails(null);
		flightDetailsVOs = updateULDForOperationsChannelMapper
				.mapAcceptanceDetailsToPayload(ctoAcceptanceVO);
		assertTrue(Objects.isNull(flightDetailsVOs));
	}

	@Test
	public void shouldNotMapFlightDetailVOWithCTOAcceptanceVODetails_When_ShipmentInULDAcceptanceDetailsAreNotCaptured_Provided_ShipmentInULDAcceptanceDetailsAreEmpty()
			throws Exception {
		shipmentInULDAcceptanceVOs = new ArrayList<>();
		ctoAcceptanceVO.setShipmentInULDAcceptanceDetails(shipmentInULDAcceptanceVOs);
		flightDetailsVOs = updateULDForOperationsChannelMapper
				.mapAcceptanceDetailsToPayload(ctoAcceptanceVO);
		assertTrue(Objects.isNull(flightDetailsVOs));
	}
	
	@Test
	public void shouldNotMapFlightDetailVOWithCTOAcceptanceVODetails_When_ULDAcceptanceOperationIsOtherThanInsertion()
			throws Exception {
		shipmentInULDAcceptanceVOs = new ArrayList<>();
		shipmentInULDAcceptanceVO = new ShipmentInULDAcceptanceVO();
		shipmentInULDAcceptanceVO.setCompanyCode("AV");
		shipmentInULDAcceptanceVO.setOperationFlag(OPERATION_FLAG_DELETE);
		shipmentInULDAcceptanceVO.setFlightNumber("1122");
		shipmentInULDAcceptanceVO.setAirportCode("CDG");
		shipmentInULDAcceptanceVO.setCarrierCode("EK");
		shipmentInULDAcceptanceVO.setFlightDate(
				new LocalDate(shipmentInULDAcceptanceVO.getAirportCode(), Location.ARP, false).setDate("21-Aug-2019"));
		shipmentInULDAcceptanceVOs.add(shipmentInULDAcceptanceVO);
		ctoAcceptanceVO.setShipmentInULDAcceptanceDetails(shipmentInULDAcceptanceVOs);
		flightDetailsVOs = updateULDForOperationsChannelMapper
				.mapAcceptanceDetailsToPayload(ctoAcceptanceVO);
		assertTrue(Objects.isNull(flightDetailsVOs));
	}

	@Test
	public void shouldMapFlightDateInFlightDetailVO_When_FlightDateIsCapturedInInput() throws Exception {
		shipmentInULDAcceptanceVOs = new ArrayList<>();
		shipmentInULDAcceptanceVO = new ShipmentInULDAcceptanceVO();
		shipmentInULDAcceptanceVO.setAirportCode("CDG");
		shipmentInULDAcceptanceVO.setOperationFlag(OPERATION_FLAG_INSERT);
		shipmentInULDAcceptanceVO.setFlightDate(
				new LocalDate(shipmentInULDAcceptanceVO.getAirportCode(), Location.ARP, false).setDate("21-Aug-2019"));
		shipmentInULDAcceptanceVOs.add(shipmentInULDAcceptanceVO);
		ctoAcceptanceVO.setShipmentInULDAcceptanceDetails(shipmentInULDAcceptanceVOs);
		flightDetailsVOs = updateULDForOperationsChannelMapper
				.mapAcceptanceDetailsToPayload(ctoAcceptanceVO);
		assertTrue(Objects.nonNull(flightDetailsVOs.iterator().next().getFlightDate()));
	}

	@Test
	public void shouldNotMapFlightDateInFlightDetailVO_When_FlightDateIsNotCapturedInInput() throws Exception {
		shipmentInULDAcceptanceVOs = new ArrayList<>();
		shipmentInULDAcceptanceVO = new ShipmentInULDAcceptanceVO();
		shipmentInULDAcceptanceVO.setAirportCode("CDG");
		shipmentInULDAcceptanceVO.setOperationFlag(OPERATION_FLAG_INSERT);
		shipmentInULDAcceptanceVO.setFlightDate(null);
		shipmentInULDAcceptanceVOs.add(shipmentInULDAcceptanceVO);
		ctoAcceptanceVO.setShipmentInULDAcceptanceDetails(shipmentInULDAcceptanceVOs);
		flightDetailsVOs = updateULDForOperationsChannelMapper
				.mapAcceptanceDetailsToPayload(ctoAcceptanceVO);
		assertTrue(Objects.isNull(flightDetailsVOs.iterator().next().getFlightDate()));
	}
	/*
	 * Remarks should get constructed in format Carrier code<Empty Space>Flight Number<Empty Space>Flight Date<Hyphen>'ULD Acceptance'
	 */
	@Test
	public void shouldMapRemarksInFlightDetailVO_When_FlightDetailsArePresentWithULDACceptanceDetails() throws Exception {
		flightDetailsVOs = updateULDForOperationsChannelMapper
				.mapAcceptanceDetailsToPayload(ctoAcceptanceVO);
		assertTrue(Objects.equals("EK 1122 21-Aug-2019-ULD Acceptance", flightDetailsVOs.iterator().next().getRemark()));
	}

	@Test
	public void shouldNotMapRemarksInFlightDetailVO_When_CarrierCodeIsNotPresentWithULDAcceptanceDetails()
			throws Exception {
		shipmentInULDAcceptanceVOs = new ArrayList<>();
		shipmentInULDAcceptanceVO = new ShipmentInULDAcceptanceVO();
		shipmentInULDAcceptanceVO.setCompanyCode("AV");
		shipmentInULDAcceptanceVO.setOperationFlag(OPERATION_FLAG_INSERT);
		shipmentInULDAcceptanceVO.setFlightNumber("1122");
		shipmentInULDAcceptanceVO.setAirportCode("CDG");
		shipmentInULDAcceptanceVO.setCarrierCode(null);
		shipmentInULDAcceptanceVO.setFlightDate(
				new LocalDate(shipmentInULDAcceptanceVO.getAirportCode(), Location.ARP, false).setDate("21-Aug-2019"));
		shipmentInULDAcceptanceVOs.add(shipmentInULDAcceptanceVO);
		ctoAcceptanceVO.setShipmentInULDAcceptanceDetails(shipmentInULDAcceptanceVOs);
		flightDetailsVOs = updateULDForOperationsChannelMapper
				.mapAcceptanceDetailsToPayload(ctoAcceptanceVO);
		assertTrue(Objects.isNull(flightDetailsVOs.iterator().next().getRemark()));
	}

	@Test
	public void shouldNotMapRemarksInFlightDetailVO_When_FlightNumberIsNotPresentWithULDAcceptanceDetails()
			throws Exception {
		shipmentInULDAcceptanceVOs = new ArrayList<>();
		shipmentInULDAcceptanceVO = new ShipmentInULDAcceptanceVO();
		shipmentInULDAcceptanceVO.setCompanyCode("AV");
		shipmentInULDAcceptanceVO.setOperationFlag(OPERATION_FLAG_INSERT);
		shipmentInULDAcceptanceVO.setFlightNumber(null);
		shipmentInULDAcceptanceVO.setAirportCode("CDG");
		shipmentInULDAcceptanceVO.setCarrierCode("EK");
		shipmentInULDAcceptanceVO.setFlightDate(
				new LocalDate(shipmentInULDAcceptanceVO.getAirportCode(), Location.ARP, false).setDate("21-Aug-2019"));
		shipmentInULDAcceptanceVOs.add(shipmentInULDAcceptanceVO);
		ctoAcceptanceVO.setShipmentInULDAcceptanceDetails(shipmentInULDAcceptanceVOs);
		flightDetailsVOs = updateULDForOperationsChannelMapper
				.mapAcceptanceDetailsToPayload(ctoAcceptanceVO);
		assertTrue(Objects.isNull(flightDetailsVOs.iterator().next().getRemark()));
	}

	@Test
	public void shouldNotMapRemarksInFlightDetailVO_When_FligtDateIsNotPresentWithULDAcceptanceDetails()
			throws Exception {
		shipmentInULDAcceptanceVOs = new ArrayList<>();
		shipmentInULDAcceptanceVO = new ShipmentInULDAcceptanceVO();
		shipmentInULDAcceptanceVO.setCompanyCode("AV");
		shipmentInULDAcceptanceVO.setOperationFlag(OPERATION_FLAG_INSERT);
		shipmentInULDAcceptanceVO.setFlightNumber("1122");
		shipmentInULDAcceptanceVO.setAirportCode("CDG");
		shipmentInULDAcceptanceVO.setCarrierCode("EK");
		shipmentInULDAcceptanceVO.setFlightDate(null);
		shipmentInULDAcceptanceVOs.add(shipmentInULDAcceptanceVO);
		ctoAcceptanceVO.setShipmentInULDAcceptanceDetails(shipmentInULDAcceptanceVOs);
		flightDetailsVOs = updateULDForOperationsChannelMapper
				.mapAcceptanceDetailsToPayload(ctoAcceptanceVO);
		assertTrue(Objects.isNull(flightDetailsVOs.iterator().next().getRemark()));
	}
	
	@Test
	public void shouldMapFlightDetailVOWithULDAcceptanceVO_When_ULDAcceptanceVOIsCaptured_And_UMSCallRequiredSystemParameter_Is_Y()
			throws Exception {
		uldAcceptanceVO.setCompanyCode("AV");
		uldAcceptanceVO.setAirportCode("CDG");
		uldAcceptanceVO.setOperationFlag(OPERATION_FLAG_INSERT);
		Collection<CTOAcceptanceVO> ctoAcceptanceVOs = new ArrayList<>();
		CTOAcceptanceVO ctoAcceptanceVO = new CTOAcceptanceVO();
		ctoAcceptanceVOs.add(ctoAcceptanceVO);
		uldAcceptanceVO.setShipmentAcceptanceDetails(ctoAcceptanceVOs);
		flightDetailsVOs = updateULDForOperationsChannelMapper.mapULDAcceptanceVOToPayload(uldAcceptanceVO);
		assertTrue(Objects.nonNull(flightDetailsVOs));
	}
	
	
	
	
	
	
	@Test
	public void shouldMapRemarksInFlightDetailVO_When_FlightDetailsArePresentWithULDACceptanceVO() throws Exception {
		uldAcceptanceVO.setCompanyCode("AV");
		uldAcceptanceVO.setOperationFlag(OPERATION_FLAG_INSERT);
		uldAcceptanceVO.setFlightNumber("1122");
		uldAcceptanceVO.setAirportCode("CDG");
		uldAcceptanceVO.setCarrierCode("EK");
		uldAcceptanceVO.setFlightDate(
				new LocalDate(uldAcceptanceVO.getAirportCode(), Location.ARP, false).setDate("16-Jun-2020"));
		Collection<CTOAcceptanceVO> ctoAcceptanceVOs = new ArrayList<>();
		CTOAcceptanceVO ctoAcceptanceVO = new CTOAcceptanceVO();
		ctoAcceptanceVOs.add(ctoAcceptanceVO);
		uldAcceptanceVO.setShipmentAcceptanceDetails(ctoAcceptanceVOs);
		flightDetailsVOs = updateULDForOperationsChannelMapper
				.mapULDAcceptanceVOToPayload(uldAcceptanceVO);
		assertTrue(Objects.equals("EK 1122 16-Jun-2020-ULD Acceptance", flightDetailsVOs.iterator().next().getRemark()));
	}

	@Test
	public void shouldNotMapRemarksInFlightDetailVO_When_CarrierCodeIsNotPresentWithULDAcceptanceVO()
			throws Exception {
		uldAcceptanceVO.setCompanyCode("AV");
		uldAcceptanceVO.setOperationFlag(OPERATION_FLAG_INSERT);
		uldAcceptanceVO.setFlightNumber("1122");
		uldAcceptanceVO.setAirportCode("CDG");
		uldAcceptanceVO.setCarrierCode(null);
		uldAcceptanceVO.setFlightDate(
				new LocalDate(uldAcceptanceVO.getAirportCode(), Location.ARP, false).setDate("21-Aug-2019"));
		Collection<CTOAcceptanceVO> ctoAcceptanceVOs = new ArrayList<>();
		CTOAcceptanceVO ctoAcceptanceVO = new CTOAcceptanceVO();
		ctoAcceptanceVOs.add(ctoAcceptanceVO);
		uldAcceptanceVO.setShipmentAcceptanceDetails(ctoAcceptanceVOs);
		flightDetailsVOs = updateULDForOperationsChannelMapper
				.mapULDAcceptanceVOToPayload(uldAcceptanceVO);
		assertTrue(Objects.isNull(flightDetailsVOs.iterator().next().getRemark()));
	}

	@Test
	public void shouldNotMapRemarksInFlightDetailVO_When_FlightNumberIsNotPresentWithULDAcceptanceVO()
			throws Exception {
		uldAcceptanceVO.setCompanyCode("AV");
		uldAcceptanceVO.setOperationFlag(OPERATION_FLAG_INSERT);
		uldAcceptanceVO.setFlightNumber(null);
		uldAcceptanceVO.setAirportCode("CDG");
		uldAcceptanceVO.setCarrierCode("EK");
		uldAcceptanceVO.setFlightDate(
				new LocalDate(uldAcceptanceVO.getAirportCode(), Location.ARP, false).setDate("21-Aug-2019"));
		Collection<CTOAcceptanceVO> ctoAcceptanceVOs = new ArrayList<>();
		CTOAcceptanceVO ctoAcceptanceVO = new CTOAcceptanceVO();
		ctoAcceptanceVOs.add(ctoAcceptanceVO);
		uldAcceptanceVO.setShipmentAcceptanceDetails(ctoAcceptanceVOs);
		flightDetailsVOs = updateULDForOperationsChannelMapper
				.mapULDAcceptanceVOToPayload(uldAcceptanceVO);
		assertTrue(Objects.isNull(flightDetailsVOs.iterator().next().getRemark()));
	}

	@Test
	public void shouldNotMapRemarksInFlightDetailVO_When_FligtDateIsNotPresentWithULDAcceptanceVO()
			throws Exception {
		uldAcceptanceVO.setCompanyCode("AV");
		uldAcceptanceVO.setOperationFlag(OPERATION_FLAG_INSERT);
		uldAcceptanceVO.setFlightNumber("1122");
		uldAcceptanceVO.setAirportCode("CDG");
		uldAcceptanceVO.setCarrierCode("EK");
		uldAcceptanceVO.setFlightDate(null);
		Collection<CTOAcceptanceVO> ctoAcceptanceVOs = new ArrayList<>();
		CTOAcceptanceVO ctoAcceptanceVO = new CTOAcceptanceVO();
		ctoAcceptanceVOs.add(ctoAcceptanceVO);
		uldAcceptanceVO.setShipmentAcceptanceDetails(ctoAcceptanceVOs);
		flightDetailsVOs = updateULDForOperationsChannelMapper
				.mapULDAcceptanceVOToPayload(uldAcceptanceVO);
		assertTrue(Objects.isNull(flightDetailsVOs.iterator().next().getRemark()));
	}
	
}
