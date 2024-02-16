package com.ibsplc.icargo.business.uld.defaults.event.mapper;

import com.ibsplc.icargo.business.flight.operation.vo.FlightVO;
import com.ibsplc.icargo.business.msgbroker.message.vo.ucm.*;
import com.ibsplc.icargo.business.uld.defaults.proxy.FlightOperationsProxy;
import com.ibsplc.icargo.business.warehouse.defaults.ramp.vo.RampHandlingUldVO;
import com.ibsplc.icargo.business.warehouse.defaults.ramp.vo.RampTransferVO;
import com.ibsplc.icargo.framework.feature.AbstractFeatureTest;
import com.ibsplc.icargo.framework.proxy.ProxyException;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;

public class SendUCMChannelMapperTest extends AbstractFeatureTest {

    private static final String UCM_MESSAGE_TYPE = "UCM";
    private static final String INCOMING_ULD_IDENTIFIER = "IN";
    private static final String OUTGOING_ULD_IDENTIFIER = "OUT";
    private SendUCMChannelMapper sendUCMChannelMapper;
    private FlightOperationsProxy flightOperationsProxy;
    private RampTransferVO rampTransferVO;

    @Override
    public void setup() {
        sendUCMChannelMapper = new SendUCMChannelMapper();
        flightOperationsProxy = mockProxy(FlightOperationsProxy.class);
        rampTransferVO = populateRampTransferVO();
    }

    private RampTransferVO populateRampTransferVO() {

        RampTransferVO rampTransferVO = new RampTransferVO();

        RampHandlingUldVO rampHandlingUldVO = new RampHandlingUldVO();
        rampHandlingUldVO.setRampHandlingType(RampHandlingUldVO.INBOUND_RAMPHANDLING);
        rampHandlingUldVO.setAirportCode("AP1");
        rampHandlingUldVO.setCompanyCode("CC1");
        rampHandlingUldVO.setCarrierId(111);
        rampHandlingUldVO.setFlightNumber("FN1");
        rampHandlingUldVO.setFlightSequenceNumber(1L);
        rampHandlingUldVO.setUldNumber("AKE01252EK");

        rampTransferVO.setRampHandlingUldVOs(Collections.singletonList(rampHandlingUldVO));

        return rampTransferVO;
    }

    private FlightVO populateFlightVo() {
        FlightVO flightVO = new FlightVO();
        flightVO.setFlightCarrierCode("CC1");
        flightVO.setFlightCarrierId(2);
        flightVO.setFlightSequenceNumber(12L);
        flightVO.setFlightNumber("9021");
        flightVO.setDepartureTimeAtOrigin(new LocalDate("CDG", Location.ARP, false));
        flightVO.setOrigin("CDG");
        flightVO.setDestination("MAA");
        flightVO.setRoute("AP1-AP2-AP3");

        return flightVO;
    }

    @Test
    public void mapRampTransferToPayloadTest() throws SystemException, ProxyException {
        FlightVO flightVO = populateFlightVo();
        doReturn(flightVO).when(flightOperationsProxy).findFlightDetails(anyString(), anyInt(), anyString(), anyLong());

        Collection<UCMMessageVO> ucmMessageVOs = sendUCMChannelMapper.mapRampTransferToPayload(rampTransferVO);

        assertEquals(1, ucmMessageVOs.size());
    }

    @Test
    public void getFlightKeyTest() throws SystemException, ProxyException {
        RampHandlingUldVO rampHandlingUldVO = new RampHandlingUldVO();
        rampHandlingUldVO.setRampHandlingType(RampHandlingUldVO.INBOUND_RAMPHANDLING);
        rampHandlingUldVO.setCompanyCode("SQ");
        rampHandlingUldVO.setCarrierId(123);
        rampHandlingUldVO.setFlightNumber("1234");
        rampHandlingUldVO.setFlightSequenceNumber(1L);

        List<RampHandlingUldVO> rampHandlingUldVOs = new ArrayList<>(rampTransferVO.getRampHandlingUldVOs());
        rampHandlingUldVOs.add(rampHandlingUldVO);
        rampTransferVO.setRampHandlingUldVOs(rampHandlingUldVOs);
        assertEquals(2, rampHandlingUldVOs.size());

        FlightVO flightVO = populateFlightVo();
        doReturn(flightVO).when(flightOperationsProxy).findFlightDetails(anyString(), anyInt(), anyString(), anyLong());
        Collection<UCMMessageVO> ucmMessageVOs = sendUCMChannelMapper.mapRampTransferToPayload(rampTransferVO);

        assertEquals(1, ucmMessageVOs.size());
    }

    @Test
    public void getUcmOutgoingULDHeaderVOTest() throws SystemException, ProxyException {
        FlightVO flightVO = populateFlightVo();
        doReturn(flightVO).when(flightOperationsProxy).findFlightDetails(anyString(), anyInt(), anyString(), anyLong());
        Collection<UCMMessageVO> ucmMessageVOs = sendUCMChannelMapper.mapRampTransferToPayload(rampTransferVO);

        long result = ucmMessageVOs.stream()
                .filter(e -> e.getUcmOutgoingULDHeaderVO().getUldIdentifier().equals(OUTGOING_ULD_IDENTIFIER))
                .count();

        assertEquals(1L, result);
    }

    @Test
    public void getUCMIncomingULDHeaderVOTest() throws SystemException, ProxyException {
        FlightVO flightVO = populateFlightVo();
        doReturn(flightVO).when(flightOperationsProxy).findFlightDetails(anyString(), anyInt(), anyString(), anyLong());
        Collection<UCMMessageVO> ucmMessageVOs = sendUCMChannelMapper.mapRampTransferToPayload(rampTransferVO);

        long result = ucmMessageVOs.stream()
                .filter(e -> e.getUcmIncomingULDHeaderVO().getUldIdentifier().equals(INCOMING_ULD_IDENTIFIER))
                .count();

        assertEquals(1L, result);
    }

    @Test
    public void getUcmMessageIdentifierVOTest() throws SystemException, ProxyException {
        FlightVO flightVO = populateFlightVo();
        doReturn(flightVO).when(flightOperationsProxy).findFlightDetails(anyString(), anyInt(), anyString(), anyLong());
        Collection<UCMMessageVO> ucmMessageVOs = sendUCMChannelMapper.mapRampTransferToPayload(rampTransferVO);

        long result = ucmMessageVOs.stream()
                .filter(e -> e.getUcmMessageIdentifierVO().getUcmMessageIdentifier().equals(UCM_MESSAGE_TYPE))
                .count();

        assertEquals(1L, result);
    }

    @Test
    public void getUcmFlightMovementVOTest() throws SystemException, ProxyException {
        FlightVO flightVO = populateFlightVo();
        doReturn(flightVO).when(flightOperationsProxy).findFlightDetails(anyString(), anyInt(), anyString(), anyLong());
        Collection<UCMMessageVO> ucmMessageVOs = sendUCMChannelMapper.mapRampTransferToPayload(rampTransferVO);

        long result = ucmMessageVOs.stream()
                .filter(e -> e.getAirportCode().equals("AP1"))
                .count();

        assertEquals(1L, result);
    }

    @Test
    public void getUcmFlightIdentificationVOTest() throws SystemException, ProxyException {
        FlightVO flightVO = populateFlightVo();
        doReturn(flightVO).when(flightOperationsProxy).findFlightDetails(anyString(), anyInt(), anyString(), anyLong());
        Collection<UCMMessageVO> ucmMessageVOs = sendUCMChannelMapper.mapRampTransferToPayload(rampTransferVO);

        long result = ucmMessageVOs.stream()
                .filter(e -> e.getUcmFlightIdentificationVO().getCarrierCode().equals("CC1")
                        && e.getUcmFlightIdentificationVO().getFirstFlightnumber().equals("FN1"))
                .count();

        assertEquals(1L, result);
    }

    @Test(expected = SystemException.class)
    public void getFlightRouteTest() throws SystemException, ProxyException {
        Collection<UCMMessageVO> ucmMessageVOs;
        FlightVO flightVO;
        boolean result;

        flightVO = populateFlightVo();
        flightVO.setRoute("-AP1");
        doReturn(flightVO).when(flightOperationsProxy).findFlightDetails(anyString(), anyInt(), anyString(), anyLong());
        ucmMessageVOs = sendUCMChannelMapper.mapRampTransferToPayload(rampTransferVO);
        result = ucmMessageVOs.stream()
                .anyMatch(e -> e.getAirportCode().equals("AP1") && e.getFlightRoute().equals("AP1"));

        assertTrue(result);

        flightVO = populateFlightVo();
        flightVO.setRoute("AP1");
        doReturn(flightVO).when(flightOperationsProxy).findFlightDetails(anyString(), anyInt(), anyString(), anyLong());
        ucmMessageVOs = sendUCMChannelMapper.mapRampTransferToPayload(rampTransferVO);
        result = ucmMessageVOs.stream()
                .anyMatch(e -> e.getAirportCode().equals("AP1") && e.getFlightRoute().equals("AP1"));

        assertTrue(result);

        flightVO = populateFlightVo();
        flightVO.setRoute("AP1-AP2");
        doReturn(flightVO).when(flightOperationsProxy).findFlightDetails(anyString(), anyInt(), anyString(), anyLong());
        ucmMessageVOs = sendUCMChannelMapper.mapRampTransferToPayload(rampTransferVO);
        result = ucmMessageVOs.stream()
                .anyMatch(e -> e.getFlightRoute().equals("AP1"));

        assertTrue(result);

        doThrow(new ProxyException(new SystemException("error"))).when(flightOperationsProxy)
                .findFlightDetails(anyString(), anyInt(), anyString(), anyLong());
        sendUCMChannelMapper.mapRampTransferToPayload(rampTransferVO);
    }

    @Test
    public void shouldContainValid_UCMIncomingULDDetailsVO() throws SystemException, ProxyException {
        FlightVO flightVO = populateFlightVo();
        doReturn(flightVO).when(flightOperationsProxy).findFlightDetails(anyString(), anyInt(), anyString(), anyLong());
        Collection<UCMMessageVO> ucmMessageVOs = sendUCMChannelMapper.mapRampTransferToPayload(rampTransferVO);

        long result = ucmMessageVOs.stream()
                .filter(e -> validateUCMIncomingULDDetailsVO(e.getUcmIncomingULDDetailsVOs()))
                .count();

        assertEquals(1L, result);
    }

    private boolean validateUCMIncomingULDDetailsVO(Collection<UCMIncomingULDDetailsVO> ucmIncomingULDDetailsVOs) {
        return ucmIncomingULDDetailsVOs.stream()
                .filter(e -> Objects.nonNull(e.getUldDetails()))
                .filter(e -> !e.getUldDetails().isEmpty())
                .anyMatch(e -> Objects.equals(e.getUldType(), "AKE")
                        && Objects.equals(e.getUldOwnerCode(), "EK")
                        && Objects.equals(e.getUldSerialNumber(), "01252"));
    }
}