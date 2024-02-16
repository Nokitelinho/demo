/*
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of
 * IBS Software Services (P) Ltd.
 *
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.uld.defaults.event.mapper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

import com.ibsplc.icargo.business.operations.shipment.cto.vo.CTOAcceptanceVO;
import com.ibsplc.icargo.business.operations.shipment.cto.vo.ShipmentInULDAcceptanceVO;
import com.ibsplc.icargo.business.operations.shipment.vo.ULDAcceptanceVO;
import com.ibsplc.icargo.business.uld.defaults.message.vo.FlightDetailsVO;
import com.ibsplc.icargo.business.uld.defaults.message.vo.ULDInFlightVO;
import com.ibsplc.icargo.framework.bean.BeanConversion;
import com.ibsplc.icargo.framework.bean.BeanConverterRegistry;
import com.ibsplc.icargo.framework.bean.ElementType;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;

/**
 * 
 * @author A-7900
 *
 */
@BeanConverterRegistry
public class UpdateULDForOperationsChannelMapper {

	private static final String HYPHEN = "-";
	private static final String EMPTY_SPACE = " ";
	public static final String OPERATIONS = "operations";
	public static final String ACCEPTANCE = "AC";
	public static final String DIRECTION_OUTBOUND = "E";

	@BeanConversion(from = CTOAcceptanceVO.class, to = FlightDetailsVO.class, toType = ElementType.LIST, group = {
			"OPERATIONS_SHIPMENT_SAVEACCEPTANCE_EVENT" })
	public Collection<FlightDetailsVO> mapAcceptanceDetailsToPayload(CTOAcceptanceVO ctoAcceptanceVO) {
		Collection<FlightDetailsVO> flightDetailsVOs = null;
		if (Objects.nonNull(ctoAcceptanceVO.getShipmentInULDAcceptanceDetails())
				&& !ctoAcceptanceVO.getShipmentInULDAcceptanceDetails().isEmpty()) {
			for (ShipmentInULDAcceptanceVO shipmentInULDAcceptanceVO : ctoAcceptanceVO
					.getShipmentInULDAcceptanceDetails()) {
				if (ShipmentInULDAcceptanceVO.OPERATION_FLAG_INSERT
						.equals(shipmentInULDAcceptanceVO.getOperationFlag())) {
					if (Objects.isNull(flightDetailsVOs)) {
						flightDetailsVOs = new ArrayList<>();
					}
					FlightDetailsVO flightDetailsVO = new FlightDetailsVO();
					mapULDAcceptanceDetails(shipmentInULDAcceptanceVO, flightDetailsVO);
					setAgentInfoToUldInFlightVO(ctoAcceptanceVO, flightDetailsVO); //Added by A-5799 for IASCB-21969
					flightDetailsVOs.add(flightDetailsVO);
				}
			}
		}
		return flightDetailsVOs;
	}

	/**
	 * @param shipmentInULDAcceptanceVO
	 * @param flightDetailsVO
	 */
	private void mapULDAcceptanceDetails(ShipmentInULDAcceptanceVO shipmentInULDAcceptanceVO,
			FlightDetailsVO flightDetailsVO) {
		flightDetailsVO.setDirection(DIRECTION_OUTBOUND);
		flightDetailsVO.setCarrierCode(shipmentInULDAcceptanceVO.getCarrierCode());
		flightDetailsVO.setFlightCarrierIdentifier(shipmentInULDAcceptanceVO.getCarrierId());
		flightDetailsVO.setCompanyCode(shipmentInULDAcceptanceVO.getCompanyCode());
		if (Objects.nonNull(shipmentInULDAcceptanceVO.getFlightDate())) {
			flightDetailsVO.setFlightDate(new LocalDate(shipmentInULDAcceptanceVO.getAirportCode(),
					Location.ARP, shipmentInULDAcceptanceVO.getFlightDate(), false));
		}
		flightDetailsVO.setSubSystem(OPERATIONS);
		flightDetailsVO.setFlightNumber(shipmentInULDAcceptanceVO.getFlightNumber());
		flightDetailsVO.setFlightSequenceNumber(shipmentInULDAcceptanceVO.getFlightSequenceNumber());
		flightDetailsVO.setLegSerialNumber(shipmentInULDAcceptanceVO.getLegSerialNumber());
		flightDetailsVO.setTransactionDate(
				new LocalDate(shipmentInULDAcceptanceVO.getAirportCode(), Location.ARP, true));
		flightDetailsVO.setUldInFlightVOs(new ArrayList<ULDInFlightVO>());
		ULDInFlightVO uldInFlightVO = new ULDInFlightVO();
		uldInFlightVO.setPointOfLading(shipmentInULDAcceptanceVO.getAirportCode());
		uldInFlightVO.setPointOfUnLading(shipmentInULDAcceptanceVO.getPou());
		uldInFlightVO.setUldNumber(shipmentInULDAcceptanceVO.getUldNumber());
		flightDetailsVO.getUldInFlightVOs().add(uldInFlightVO);
		if (Objects.nonNull(flightDetailsVO.getCarrierCode())
				&& Objects.nonNull(flightDetailsVO.getFlightNumber())
				&& Objects.nonNull(flightDetailsVO.getFlightDate())) {
			
			StringBuilder uldRemarks = new StringBuilder(flightDetailsVO.getCarrierCode())
					.append(EMPTY_SPACE)
					.append(flightDetailsVO.getFlightNumber())
					.append(EMPTY_SPACE)
					.append(flightDetailsVO.getFlightDate().toDisplayDateOnlyFormat())
					.append(HYPHEN)
					.append("ULD Acceptance");
			
			flightDetailsVO.setRemark(uldRemarks.toString()); 
		}
		flightDetailsVO.setAction(ACCEPTANCE);
	}
	
	/**
	 * Added by A-5799 for IASCB-21969
	 */
	private void setAgentInfoToUldInFlightVO(CTOAcceptanceVO ctoAcceptanceVO, FlightDetailsVO flightDetailsVO){
		if(flightDetailsVO!=null && flightDetailsVO.getUldInFlightVOs()!=null &&
				flightDetailsVO.getUldInFlightVOs().size()>0){
			for(ULDInFlightVO uldInFlightVO: flightDetailsVO.getUldInFlightVOs()){
				uldInFlightVO.setAgentCode(ctoAcceptanceVO.getAgentCode());
				uldInFlightVO.setAgentName(ctoAcceptanceVO.getAgentName());
			}
		}
	}
	
	/**
	 * 
	 * @param uldcceptanceVO
	 * @return
	 * @throws SystemException 
	 */
	@BeanConversion(from = ULDAcceptanceVO.class, 
			to = FlightDetailsVO.class, 
			toType = ElementType.LIST, 
			group = {"OPERATIONS_SHIPMENT_SAVEULDACCEPTANCE_EVENT" })
	public Collection<FlightDetailsVO> mapULDAcceptanceVOToPayload(ULDAcceptanceVO uldcceptanceVO) {
		Collection<FlightDetailsVO> flightDetailsVOs = new ArrayList<>();
		FlightDetailsVO flightDetailsVO = new FlightDetailsVO();
		mapULDAcceptanceDetailsToFlightDetailsVO(uldcceptanceVO, flightDetailsVO);
		setAgentInfoToUldInFlightVOFromULDAcceptanceVO(uldcceptanceVO, flightDetailsVO);
		flightDetailsVOs.add(flightDetailsVO);
		return flightDetailsVOs;
	}
	
	/**
	 * 
	 * @param uldAcceptanceVO
	 * @param flightDetailsVO
	 */
	private void mapULDAcceptanceDetailsToFlightDetailsVO(ULDAcceptanceVO uldAcceptanceVO,
			FlightDetailsVO flightDetailsVO) {
		flightDetailsVO.setDirection(DIRECTION_OUTBOUND);
		flightDetailsVO.setCarrierCode(uldAcceptanceVO.getCarrierCode());
		flightDetailsVO.setFlightCarrierIdentifier(uldAcceptanceVO.getCarrierId());
		flightDetailsVO.setCompanyCode(uldAcceptanceVO.getCompanyCode());
		if (Objects.nonNull(uldAcceptanceVO.getFlightDate())) {
			flightDetailsVO.setFlightDate(new LocalDate(uldAcceptanceVO.getAirportCode(),
					Location.ARP, uldAcceptanceVO.getFlightDate(), false));
		}
		flightDetailsVO.setSubSystem(OPERATIONS);
		flightDetailsVO.setFlightNumber(uldAcceptanceVO.getFlightNumber());
		flightDetailsVO.setFlightSequenceNumber(uldAcceptanceVO.getFlightSequenceNumber());
		flightDetailsVO.setLegSerialNumber(uldAcceptanceVO.getLegSerialNumber());
		flightDetailsVO.setTransactionDate(
				new LocalDate(uldAcceptanceVO.getAirportCode(), Location.ARP, true));
		flightDetailsVO.setUldInFlightVOs(new ArrayList<ULDInFlightVO>());
		ULDInFlightVO uldInFlightVO = new ULDInFlightVO();
		uldInFlightVO.setPointOfLading(uldAcceptanceVO.getAirportCode());
		uldInFlightVO.setPointOfUnLading(uldAcceptanceVO.getPou());
		uldInFlightVO.setUldNumber(uldAcceptanceVO.getUldNumber());
		flightDetailsVO.getUldInFlightVOs().add(uldInFlightVO);
		if (Objects.nonNull(flightDetailsVO.getCarrierCode())
				&& Objects.nonNull(flightDetailsVO.getFlightNumber())
				&& Objects.nonNull(flightDetailsVO.getFlightDate())) {
			
			StringBuilder uldRemarks = new StringBuilder(flightDetailsVO.getCarrierCode())
					.append(EMPTY_SPACE)
					.append(flightDetailsVO.getFlightNumber())
					.append(EMPTY_SPACE)
					.append(flightDetailsVO.getFlightDate().toDisplayDateOnlyFormat())
					.append(HYPHEN)
					.append("ULD Acceptance");
			
			flightDetailsVO.setRemark(uldRemarks.toString()); 
		}
		flightDetailsVO.setAction(ACCEPTANCE);
	}
	
	/**
	 * 
	 * @param uldAcceptanceVO
	 * @param flightDetailsVO
	 */
	private void setAgentInfoToUldInFlightVOFromULDAcceptanceVO(ULDAcceptanceVO uldAcceptanceVO, FlightDetailsVO flightDetailsVO){
		if(Objects.nonNull(flightDetailsVO.getUldInFlightVOs()) && !flightDetailsVO.getUldInFlightVOs().isEmpty()){
			CTOAcceptanceVO ctoAcceptanceVO = uldAcceptanceVO.getShipmentAcceptanceDetails().iterator().next();
			for(ULDInFlightVO uldInFlightVO: flightDetailsVO.getUldInFlightVOs()){
				uldInFlightVO.setAgentCode(ctoAcceptanceVO.getAgentCode());
				uldInFlightVO.setAgentName(ctoAcceptanceVO.getAgentName());
			}
		}
	}

}
