package com.ibsplc.icargo.business.addons.mail.operations.event.mapper;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyCollectionOf;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import org.junit.Test;

import com.ibsplc.icargo.business.addons.mail.operations.proxy.SharedDefaultsProxy;
import com.ibsplc.icargo.business.operations.flthandling.cto.ramp.vo.RampHandlingAWBVO;
import com.ibsplc.icargo.business.operations.flthandling.cto.ramp.vo.RampHandlingULDVO;
import com.ibsplc.icargo.business.operations.flthandling.cto.ramp.vo.RampHandlingVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.feature.AbstractFeatureTest;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.EntityManagerMock;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;

public class MailAWBChannelMapperTest extends AbstractFeatureTest {

	private MailAWBChannelMapper spy;
	private SharedDefaultsProxy sharedDefaultsProxy;

	@Override
	public void setup() throws Exception {
		EntityManagerMock.mockEntityManager();
		spy = spy(new MailAWBChannelMapper());
		sharedDefaultsProxy = mockProxy(SharedDefaultsProxy.class);

	}

	@Test
	public void mapRampHandlingVOToMailFlightSummaryVORampTransfer_Success_Test()
			throws SystemException, PersistenceException {
		RampHandlingVO rampHandlingVO = new RampHandlingVO();

		Collection<RampHandlingULDVO> rampHandlingULDVOs = new ArrayList<RampHandlingULDVO>();

		RampHandlingULDVO ramp = new RampHandlingULDVO();
		ramp.setUldNumber("AKE12345AV");

		LogonAttributes logonAttributes = new LogonAttributes();
		logonAttributes.setCompanyCode(getCompanyCode());
		logonAttributes.setAirportCode("CDG");
		logonAttributes.setAirlineIdentifier(1076);
		Map<String, Collection<OneTimeVO>> oneTimeMap = null;

		Collection<String> parameterTypes = new ArrayList<>();
		parameterTypes.add("mailtracking.defaults.mailscccode");

		Collection<RampHandlingAWBVO> rampHandlingAWBVOs = new ArrayList<>();

		RampHandlingAWBVO rampHandlingAWBVO = new RampHandlingAWBVO();
		rampHandlingAWBVO.setShipmentPrefix("134");
		rampHandlingAWBVO.setMasterDocumentNumber("30002011");
		rampHandlingAWBVO.setOwnerId(1);
		rampHandlingAWBVO.setSequenceNumber(1);
		rampHandlingAWBVO.setDuplicateNumber(1);
		rampHandlingAWBVO.setStatedPieces(20);
		rampHandlingAWBVO.setAwbDestination("DXB");
		rampHandlingAWBVO.setSccCode("MAL,NSC");

		rampHandlingAWBVOs.add(rampHandlingAWBVO);
		ramp.setAwbDetails(rampHandlingAWBVOs);
		rampHandlingULDVOs.add(ramp);
		rampHandlingVO.setUldDeails(rampHandlingULDVOs);

		doReturn(oneTimeMap).when(sharedDefaultsProxy).findOneTimeValues(any(String.class),
				anyCollectionOf(String.class));

		spy.mapRampHandlingVOToMailFlightSummaryVORampTransfer(rampHandlingVO);
	}

	@Test
	public void mapRampHandlingVOToMailFlightSummaryVORampTransfer_Awb_Null_Test()
			throws SystemException, PersistenceException {
		RampHandlingVO rampHandlingVO = new RampHandlingVO();

		Collection<RampHandlingULDVO> rampHandlingULDVOs = new ArrayList<RampHandlingULDVO>();

		RampHandlingULDVO ramp = new RampHandlingULDVO();
		ramp.setUldNumber("AKE12345AV");
		rampHandlingULDVOs.add(ramp);

		rampHandlingVO.setUldDeails(rampHandlingULDVOs);
		LogonAttributes logonAttributes = new LogonAttributes();
		logonAttributes.setCompanyCode(getCompanyCode());
		logonAttributes.setAirportCode("CDG");
		logonAttributes.setAirlineIdentifier(1076);
		Map<String, Collection<OneTimeVO>> oneTimeMap = null;

		Collection<String> parameterTypes = new ArrayList<>();
		parameterTypes.add("mailtracking.defaults.mailscccode");

		doReturn(oneTimeMap).when(sharedDefaultsProxy).findOneTimeValues(any(String.class),
				anyCollectionOf(String.class));

		spy.mapRampHandlingVOToMailFlightSummaryVORampTransfer(rampHandlingVO);
	}

	@Test
	public void mapRampHandlingVOToMailFlightSummaryVORampTransfer_Empty_Value_Test()
			throws SystemException, PersistenceException {
		RampHandlingVO rampHandlingVO = new RampHandlingVO();

		Collection<RampHandlingULDVO> rampHandlingULDVOs = new ArrayList<RampHandlingULDVO>();

		RampHandlingULDVO ramp = new RampHandlingULDVO();
		ramp.setUldNumber("AKE12345AV");
		rampHandlingULDVOs.add(ramp);

		rampHandlingVO.setUldDeails(rampHandlingULDVOs);
		LogonAttributes logonAttributes = new LogonAttributes();
		logonAttributes.setCompanyCode(getCompanyCode());
		logonAttributes.setAirportCode("CDG");
		logonAttributes.setAirlineIdentifier(1076);
		Map<String, Collection<OneTimeVO>> oneTimeMap = null;

		Collection<String> parameterTypes = new ArrayList<>();
		parameterTypes.add("mailtracking.defaults.mailscccode");

		Collection<RampHandlingAWBVO> rampHandlingAWBVOs = new ArrayList<>();

		ramp.setAwbDetails(rampHandlingAWBVOs);

		doReturn(oneTimeMap).when(sharedDefaultsProxy).findOneTimeValues(any(String.class),
				anyCollectionOf(String.class));

		spy.mapRampHandlingVOToMailFlightSummaryVORampTransfer(rampHandlingVO); 
	}

}
