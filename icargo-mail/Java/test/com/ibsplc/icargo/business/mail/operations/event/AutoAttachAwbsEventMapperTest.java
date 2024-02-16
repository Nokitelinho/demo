package com.ibsplc.icargo.business.mail.operations.event;

import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Matchers.any;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import static org.mockito.Mockito.mock;

import org.junit.Test;

import com.ibsplc.icargo.business.flight.operation.vo.FlightMVTVO;
import com.ibsplc.icargo.business.flight.operation.vo.FlightSegmentSummaryVO;
import com.ibsplc.icargo.business.mail.operations.proxy.FlightOperationsProxy;
import com.ibsplc.icargo.business.mail.operations.proxy.SharedAreaProductProxy;
import com.ibsplc.icargo.business.mail.operations.vo.OperationalFlightVO;
import com.ibsplc.icargo.business.shared.area.airport.vo.AirportValidationVO;
import com.ibsplc.icargo.framework.event.EventConstants.ParameterMap;
import com.ibsplc.icargo.framework.event.vo.EventVO;
import com.ibsplc.icargo.framework.feature.AbstractFeatureTest;
import com.ibsplc.icargo.framework.proxy.ProxyException;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;

public class AutoAttachAwbsEventMapperTest extends AbstractFeatureTest {
	
	private AutoAttachAwbsEventMapper autoAttachAwbsEventMapper;
	private FlightOperationsProxy flightOperationsProxy;
	private SharedAreaProductProxy sharedAreaProductProxy;

	@Override
	public void setup() throws Exception {
		
		autoAttachAwbsEventMapper = spy(AutoAttachAwbsEventMapper.class);
		flightOperationsProxy = mockProxy(FlightOperationsProxy.class);
		sharedAreaProductProxy = mockProxy(SharedAreaProductProxy.class);
	}
	
	@Test
	public void saveMvt() throws SystemException, ProxyException {
		Object[] parameters = new Object[2];
		FlightMVTVO flightMVTVO = new FlightMVTVO();
		flightMVTVO.setLegOrigin("ORIGIN");
		flightMVTVO.setATD(new LocalDate(LocalDate.NO_STATION, Location.NONE, false));
		parameters[0] = flightMVTVO;
		String eventType = "";
		Object payload = new Object[2];
		String module = "";
		String subModule = "";
		EventVO eventVO = new EventVO(eventType, payload, module, subModule);	
		String destCountryCodes = "";
		OperationalFlightVO operationalFlightVO = new OperationalFlightVO();
		
		Collection<FlightSegmentSummaryVO> flightSegmentSummaryVOs = new ArrayList<>();
		FlightSegmentSummaryVO flightSegmentSummaryVO = new FlightSegmentSummaryVO();
		flightSegmentSummaryVO.setSegmentOrigin("ORIGIN");
		flightSegmentSummaryVO.setSegmentDestination("DESTINATION");
		flightSegmentSummaryVOs.add(flightSegmentSummaryVO);
		
		AirportValidationVO airportValidationVO=new AirportValidationVO();
		airportValidationVO.setCountryCode("CNTCOD");
		
		doReturn(flightSegmentSummaryVOs).when(flightOperationsProxy).findFlightSegments(any(String.class), any(Integer.class), any(String.class), any(Long.class));
		doReturn(airportValidationVO).when(sharedAreaProductProxy).validateAirportCode(any(String.class), any(String.class));
		
		autoAttachAwbsEventMapper.attachAwbViaMvt(parameters, eventVO, destCountryCodes, operationalFlightVO);
	}
	
	@Test
	public void checkAndUpdateOperationalFlightVO() {
		String eventType = "";
		Object payload = new Object[2];
		String module = "";
		String subModule = "";
		EventVO eventVO = new EventVO(eventType, payload, module, subModule);
		String destCountryCodes = "DESTINATION";
		OperationalFlightVO operationalFlightVO = new OperationalFlightVO();
		FlightMVTVO flightMVTVO = new FlightMVTVO();
		String legOrginCountry = "ORIGIN";
		String legDestCountry = "DESTN";
		autoAttachAwbsEventMapper.checkAndUpdateOperationalFlightVO(eventVO, destCountryCodes, operationalFlightVO, flightMVTVO, legOrginCountry, legDestCountry);
	}
	
	@Test
	public void checkAndUpdateOperationalFlightVOWhenDestCountryCodesNotContainLegDestCountry() {
		String eventType = "";
		Object payload = new Object[2];
		String module = "";
		String subModule = "";
		EventVO eventVO = new EventVO(eventType, payload, module, subModule);
		String destCountryCodes = "DESTINATION";
		OperationalFlightVO operationalFlightVO = new OperationalFlightVO();
		FlightMVTVO flightMVTVO = new FlightMVTVO();
		String legOrginCountry = "ORIGIN";
		String legDestCountry = "DESTINATION";
		autoAttachAwbsEventMapper.checkAndUpdateOperationalFlightVO(eventVO, destCountryCodes, operationalFlightVO, flightMVTVO, legOrginCountry, legDestCountry);
	}
	
	@Test
	public void checkAndUpdateOperationalFlightVOWhenLegOrginCountryIsNull() {
		String eventType = "";
		Object payload = new Object[2];
		String module = "";
		String subModule = "";
		EventVO eventVO = new EventVO(eventType, payload, module, subModule);
		String destCountryCodes = "DESTINATION";
		OperationalFlightVO operationalFlightVO = new OperationalFlightVO();
		FlightMVTVO flightMVTVO = new FlightMVTVO();
		String legOrginCountry = null;
		String legDestCountry = "DESTINATION";
		autoAttachAwbsEventMapper.checkAndUpdateOperationalFlightVO(eventVO, destCountryCodes, operationalFlightVO, flightMVTVO, legOrginCountry, legDestCountry);
	}
	
	@Test
	public void checkAndUpdateOperationalFlightVOWhenLegDestCountryIsNull() {
		String eventType = "";
		Object payload = new Object[2];
		String module = "";
		String subModule = "";
		EventVO eventVO = new EventVO(eventType, payload, module, subModule);
		String destCountryCodes = "DESTINATION";
		OperationalFlightVO operationalFlightVO = new OperationalFlightVO();
		FlightMVTVO flightMVTVO = new FlightMVTVO();
		String legOrginCountry = "ORIGIN";
		String legDestCountry = null;
		autoAttachAwbsEventMapper.checkAndUpdateOperationalFlightVO(eventVO, destCountryCodes, operationalFlightVO, flightMVTVO, legOrginCountry, legDestCountry);
	}
	
	@Test
	public void shouldNotSaveMvtIfFlightMVTVOIsNull() {
		Object[] parameters = new Object[2];
		parameters[0] = null;
		String eventType = "";
		Object payload = new Object[2];
		String module = "";
		String subModule = "";
		EventVO eventVO = new EventVO(eventType, payload, module, subModule);	
		String destCountryCodes = "";
		OperationalFlightVO operationalFlightVO = new OperationalFlightVO();
		
		AirportValidationVO airportValidationVO=new AirportValidationVO();
		airportValidationVO.setCountryCode("CNTCOD");
		
		autoAttachAwbsEventMapper.attachAwbViaMvt(parameters, eventVO, destCountryCodes, operationalFlightVO);
	}
	
	@Test
	public void shouldNotSaveMvtIfATDIsNull() throws SystemException {
		Object[] parameters = new Object[2];
		FlightMVTVO flightMVTVO = new FlightMVTVO();
		flightMVTVO.setLegOrigin("ORIGIN");
		parameters[0] = flightMVTVO;
		String eventType = "";
		Object payload = new Object[2];
		String module = "";
		String subModule = "";
		EventVO eventVO = new EventVO(eventType, payload, module, subModule);	
		String destCountryCodes = "";
		OperationalFlightVO operationalFlightVO = new OperationalFlightVO();
		
		Collection<FlightSegmentSummaryVO> flightSegmentSummaryVOs = new ArrayList<>();
		FlightSegmentSummaryVO flightSegmentSummaryVO = new FlightSegmentSummaryVO();
		flightSegmentSummaryVO.setSegmentOrigin("ORIGIN");
		flightSegmentSummaryVO.setSegmentDestination("DESTINATION");
		flightSegmentSummaryVOs.add(flightSegmentSummaryVO);
		
		AirportValidationVO airportValidationVO=new AirportValidationVO();
		airportValidationVO.setCountryCode("CNTCOD");
		
		doReturn(flightSegmentSummaryVOs).when(flightOperationsProxy).findFlightSegments(any(String.class), any(Integer.class), any(String.class), any(Long.class));
		
		autoAttachAwbsEventMapper.attachAwbViaMvt(parameters, eventVO, destCountryCodes, operationalFlightVO);
	}
	
	@Test
	public void attachAwbViaMvt() throws SystemException {
		Object[] parameters = new Object[2];
		FlightMVTVO flightMVTVO = new FlightMVTVO();
		parameters[0] = flightMVTVO;
		String eventType = "";
		Object payload = new Object[2];
		String module = "";
		String subModule = "";
		EventVO eventVO = new EventVO(eventType, payload, module, subModule);	
		String destCountryCodes = "";
		OperationalFlightVO operationalFlightVO = new OperationalFlightVO();
		
		doThrow(new SystemException("unknown")).when(flightOperationsProxy).findFlightSegments(any(String.class), any(Integer.class), any(String.class), any(Long.class));
		
		autoAttachAwbsEventMapper.attachAwbViaMvt(parameters, eventVO, destCountryCodes, operationalFlightVO);
	}
	
	@Test
	public void shouldNotAttachAwbViaMvtWhenFlightSegmentSummaryVOsIsNull() throws SystemException {
		Object[] parameters = new Object[2];
		FlightMVTVO flightMVTVO = new FlightMVTVO();
		parameters[0] = flightMVTVO;
		String eventType = "";
		Object payload = new Object[2];
		String module = "";
		String subModule = "";
		EventVO eventVO = new EventVO(eventType, payload, module, subModule);	
		String destCountryCodes = "";
		OperationalFlightVO operationalFlightVO = new OperationalFlightVO();
		
		doReturn(null).when(flightOperationsProxy).findFlightSegments(any(String.class), any(Integer.class), any(String.class), any(Long.class));
		
		autoAttachAwbsEventMapper.attachAwbViaMvt(parameters, eventVO, destCountryCodes, operationalFlightVO);
	}

}
