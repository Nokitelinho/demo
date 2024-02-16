package com.ibsplc.icargo.business.uld.defaults.event.mapper;

import com.ibsplc.icargo.business.flight.operation.vo.FlightVO;
import com.ibsplc.icargo.business.msgbroker.message.vo.ucm.UCMFlightIdentificationVO;
import com.ibsplc.icargo.business.msgbroker.message.vo.ucm.UCMFlightMovementVO;
import com.ibsplc.icargo.business.msgbroker.message.vo.ucm.UCMIncomingULDDetailsVO;
import com.ibsplc.icargo.business.msgbroker.message.vo.ucm.UCMIncomingULDHeaderVO;
import com.ibsplc.icargo.business.msgbroker.message.vo.ucm.UCMMessageIdentifierVO;
import com.ibsplc.icargo.business.msgbroker.message.vo.ucm.UCMMessageVO;
import com.ibsplc.icargo.business.msgbroker.message.vo.ucm.UCMOutgoingULDHeaderVO;
import com.ibsplc.icargo.business.msgbroker.message.vo.ucm.ULDDetailsVO;
import com.ibsplc.icargo.business.uld.defaults.event.constants.SendUCMChannelConstants;
import com.ibsplc.icargo.business.uld.defaults.proxy.FlightOperationsProxy;
import com.ibsplc.icargo.business.warehouse.defaults.ramp.vo.RampHandlingUldVO;
import com.ibsplc.icargo.business.warehouse.defaults.ramp.vo.RampTransferVO;
import com.ibsplc.icargo.framework.bean.BeanConversion;
import com.ibsplc.icargo.framework.bean.BeanConverterRegistry;
import com.ibsplc.icargo.framework.bean.ElementType;
import com.ibsplc.icargo.framework.feature.Proxy;
import com.ibsplc.icargo.framework.proxy.ProxyException;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@BeanConverterRegistry
public class SendUCMChannelMapper {

    @BeanConversion(
            from = RampTransferVO.class,
            to = UCMMessageVO.class,
            toType = ElementType.LIST,
            group = {"WAREHOUSE_DEFAULTS_SAVERAMPTRANSFER_EVENT"}
    )
    public Collection<UCMMessageVO> mapRampTransferToPayload(RampTransferVO rampTransferVO) throws SystemException {
        Map<String, UCMMessageVO> ucmMessageVOMap = new HashMap<>();

        for (RampHandlingUldVO rampHandlingUldVO : rampTransferVO.getRampHandlingUldVOs()) {
            FlightVO flightVO = getFlightDetails(rampHandlingUldVO);
            String flightKey = getFlightKey(flightVO);
            if (!ucmMessageVOMap.containsKey(flightKey)) {
                UCMMessageVO uCMMessageVO = getUCMMessageVO(rampHandlingUldVO, flightVO);
                ucmMessageVOMap.put(flightKey, uCMMessageVO);
            }
        }

        return !ucmMessageVOMap.isEmpty() ? new ArrayList<>(ucmMessageVOMap.values()) : null;
    }

    private FlightVO getFlightDetails(RampHandlingUldVO rampHandlingUldVO) throws SystemException {
        FlightOperationsProxy flightOperationsProxy = Proxy.getInstance().get(FlightOperationsProxy.class);
        FlightVO flightVO;
        try {
            flightVO = flightOperationsProxy.findFlightDetails(rampHandlingUldVO.getCompanyCode(), rampHandlingUldVO.getCarrierId(),
                    rampHandlingUldVO.getFlightNumber(), rampHandlingUldVO.getFlightSequenceNumber());
        } catch (ProxyException e) {
            throw new SystemException("getFlightDetails error", e);
        }

        return flightVO;
    }

    private UCMMessageVO getUCMMessageVO(RampHandlingUldVO rampHandlingUldVO, FlightVO flightVO) {
        UCMMessageVO uCMMessageVO = new UCMMessageVO();
        uCMMessageVO.setTransactionId(SendUCMChannelConstants.TXN_CDS_PRINT);
        uCMMessageVO.setCompanyCode(rampHandlingUldVO.getCompanyCode());
        uCMMessageVO.setMessageStandard(SendUCMChannelConstants.MESSAGE_STANDARD_AHM);
        uCMMessageVO.setMessageType(SendUCMChannelConstants.MESSAGE_TYPE_UCM);
        uCMMessageVO.setAirportCode(rampHandlingUldVO.getAirportCode());
        uCMMessageVO.setStationCode(rampHandlingUldVO.getAirportCode());
        uCMMessageVO.setCarrierCode(flightVO.getFlightCarrierCode());
        uCMMessageVO.setUcmFlightIdentificationVO(getUcmFlightIdentificationVO(rampHandlingUldVO, flightVO));
        uCMMessageVO.setUcmFlightMovementVO(getUcmFlightMovementVO(rampHandlingUldVO));
        uCMMessageVO.setUcmMessageIdentifierVO(getUcmMessageIdentifierVO());
        uCMMessageVO.setUcmIncomingULDHeaderVO(getUcmIncomingULDHeaderVO());
        uCMMessageVO.setUcmIncomingULDDetailsVOs(getUcmIncomingULDDetailsVOs(rampHandlingUldVO));
        uCMMessageVO.setUcmOutgoingULDHeaderVO(getUcmOutgoingULDHeaderVO());
        uCMMessageVO.setFlightRoute(getFlightRoute(rampHandlingUldVO, flightVO));

        return uCMMessageVO;
    }

    private Collection<UCMIncomingULDDetailsVO> getUcmIncomingULDDetailsVOs(RampHandlingUldVO rampHandlingUldVO) {
        UCMIncomingULDDetailsVO ucmIncomingULDDetailsVO = new UCMIncomingULDDetailsVO();
        String uldNumber = rampHandlingUldVO.getUldNumber();
        int uldNumberLength = uldNumber.length();

        String uldType = uldNumber.substring(0, 3);
        String uldOwnerCode = uldNumber.substring(uldNumberLength - 2);
        String uldSerialNumber = uldNumber.substring(3, uldNumberLength - 2);

        ucmIncomingULDDetailsVO.setUldType(uldType);
        ucmIncomingULDDetailsVO.setUldSerialNumber(uldSerialNumber);
        ucmIncomingULDDetailsVO.setUldOwnerCode(uldOwnerCode);
        ucmIncomingULDDetailsVO.setAirportCode(rampHandlingUldVO.getAirportCode());

        ULDDetailsVO uldDetails = new ULDDetailsVO();
        uldDetails.setUldType(uldType);
        uldDetails.setUldSerialNumber(uldSerialNumber);
        uldDetails.setUldOwnerCode(uldOwnerCode);
        uldDetails.setAirportCode(rampHandlingUldVO.getAirportCode());

        ucmIncomingULDDetailsVO.setUldDetails(Collections.singletonList(uldDetails));

        return Collections.singletonList(ucmIncomingULDDetailsVO);
    }

    private String getFlightKey(FlightVO flightVO) {
        return flightVO.getFlightCarrierId() + "~" + flightVO.getFlightNumber() + "~" + flightVO.getFlightSequenceNumber();
    }

    private String getFlightRoute(RampHandlingUldVO rampHandlingUldVO, FlightVO flightVO) {
        List<String> flightRoute = new ArrayList<>();

        if (Objects.nonNull(flightVO) && Objects.nonNull(flightVO.getRoute())) {
            List<String> airports = Arrays.asList(flightVO.getRoute().split("-"));
            if (!airports.isEmpty()) {
                flightRoute = airports.stream()
                        .filter(e -> !e.isEmpty())
                        .filter(e -> e.equals(rampHandlingUldVO.getAirportCode()))
                        .collect(Collectors.toList());
            }
        }

        return !flightRoute.isEmpty() ? String.join("-", flightRoute) : "";
    }

    private UCMOutgoingULDHeaderVO getUcmOutgoingULDHeaderVO() {
        UCMOutgoingULDHeaderVO ucmOutgoingULDHeaderVO = new UCMOutgoingULDHeaderVO();
        ucmOutgoingULDHeaderVO.setUldIdentifier(SendUCMChannelConstants.OUTGOING_ULD_IDENTIFIER);

        return ucmOutgoingULDHeaderVO;
    }

    private UCMIncomingULDHeaderVO getUcmIncomingULDHeaderVO() {
        UCMIncomingULDHeaderVO ucmIncomingULDHeaderVO = new UCMIncomingULDHeaderVO();
        ucmIncomingULDHeaderVO.setUldIdentifier(SendUCMChannelConstants.INCOMING_ULD_IDENTIFIER);

        return ucmIncomingULDHeaderVO;
    }

    private UCMMessageIdentifierVO getUcmMessageIdentifierVO() {
        UCMMessageIdentifierVO ucmMessageIdentifierVO = new UCMMessageIdentifierVO();
        ucmMessageIdentifierVO.setUcmMessageIdentifier(SendUCMChannelConstants.MESSAGE_TYPE_UCM);

        return ucmMessageIdentifierVO;
    }

    private UCMFlightMovementVO getUcmFlightMovementVO(RampHandlingUldVO rampHandlingUldVO) {
        UCMFlightMovementVO ucmFlightMovementVO = new UCMFlightMovementVO();
        ucmFlightMovementVO.setAirportCode(rampHandlingUldVO.getAirportCode());

        return ucmFlightMovementVO;
    }

    private UCMFlightIdentificationVO getUcmFlightIdentificationVO(RampHandlingUldVO rampHandlingUldVO, FlightVO flightVO) {
        UCMFlightIdentificationVO ucmFlightIdentificationVO = new UCMFlightIdentificationVO();
        ucmFlightIdentificationVO.setCarrierCode(flightVO.getFlightCarrierCode());
        ucmFlightIdentificationVO.setFirstFlightnumber(rampHandlingUldVO.getFlightNumber());
        ucmFlightIdentificationVO.setFlightSequenceNumber(rampHandlingUldVO.getFlightSequenceNumber());

        return ucmFlightIdentificationVO;
    }
}
