package com.ibsplc.icargo.business.mail.operations.event.mapper;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.Test;

import com.ibsplc.icargo.business.mail.operations.proxy.MailOperationsProxy;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailArrivalVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailResditVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.business.mail.operations.vo.OperationalFlightVO;
import com.ibsplc.icargo.framework.feature.AbstractFeatureTest;
import com.ibsplc.icargo.framework.floworchestration.context.ICargoSproutAdapter;
import com.ibsplc.icargo.framework.proxy.ProxyException;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;

public class StampResditMessageEventMapperTest extends AbstractFeatureTest {

	private OperationalFlightVO operationalFlightVO;
	private StampResditMessageEventMapper stampResditMessageEventMapper;
	MailOperationsProxy mailOperationsProxy;
	private MailArrivalVO mailArrivalVO;

	@Override
	public void setup() throws Exception {
		operationalFlightVO = setUpOperationalFlightVO();
		stampResditMessageEventMapper = (StampResditMessageEventMapper) ICargoSproutAdapter
				.getBean("mail.operations.stampResditMessageEventMapper");
		mailOperationsProxy = mockProxy(MailOperationsProxy.class);
		mailArrivalVO =  new MailArrivalVO();
	}

	private OperationalFlightVO setUpOperationalFlightVO() {
		operationalFlightVO = new OperationalFlightVO();
		operationalFlightVO.setCompanyCode(getCompanyCode());
		operationalFlightVO.setFlightNumber("0023");
		operationalFlightVO.setFlightSequenceNumber(1);
		operationalFlightVO.setFlightDate(new LocalDate(LocalDate.NO_STATION, Location.NONE, false));
		operationalFlightVO.setCarrierCode(getCompanyCode());
		operationalFlightVO.setLegSerialNumber(1);
		return operationalFlightVO;
	}

	@Test
	public void shouldMapLostResditDetailsToPayload() throws Exception {
		Collection<ContainerDetailsVO> containerDetailsVOs = new ArrayList<>();
		Collection<MailbagVO> mailbagVOs = new ArrayList<>();
		ContainerDetailsVO containerDetailsVO = new ContainerDetailsVO();
		MailbagVO mailbagVO = new MailbagVO();
		mailbagVO.setCompanyCode(getCompanyCode());
		mailbagVO.setMailbagId("DEFRAAUSDFWAACA01200120001200");
		mailbagVO.setPaCode("DE101");
		doReturn("DE101").when(mailOperationsProxy).findMailboxIdFromConfig(mailbagVO);
		mailbagVO.setArrivedFlag(MailConstantsVO.FLAG_NO);
		mailbagVO.setScannedPort("FRA");
		mailbagVOs.add(mailbagVO);
		containerDetailsVO.setMailDetails(mailbagVOs);
		containerDetailsVO.setCompanyCode(getCompanyCode());
		containerDetailsVO.setContainerNumber("AKE1234AV");
		containerDetailsVOs.add(containerDetailsVO);
		doReturn(containerDetailsVOs).when(mailOperationsProxy)
				.findArrivalDetailsForReleasingMails(operationalFlightVO);
		Collection<MailResditVO> mailResditVOs = stampResditMessageEventMapper
				.mapLostResditDetailsToPayload(operationalFlightVO);
		assertNotNull(mailResditVOs);
	}

	@Test
	public void shouldNotMapLostResditDetailsToPayload_WhenMailbagVOIsEmpty() throws Exception {
		Collection<ContainerDetailsVO> containerDetailsVOs = new ArrayList<>();
		ContainerDetailsVO containerDetailsVO = new ContainerDetailsVO();
		containerDetailsVO.setMailDetails(new ArrayList<>());
		containerDetailsVO.setCompanyCode(getCompanyCode());
		containerDetailsVO.setContainerNumber("AKE1234AV");
		containerDetailsVOs.add(containerDetailsVO);
		doReturn(containerDetailsVOs).when(mailOperationsProxy)
				.findArrivalDetailsForReleasingMails(operationalFlightVO);
		Collection<MailResditVO> mailResditVOs = stampResditMessageEventMapper
				.mapLostResditDetailsToPayload(operationalFlightVO);
		assertNull(mailResditVOs);
	}

	@Test
	public void shouldNotMapLostResditDetailsToPayload_WhenContainerDetailsIsNull() throws Exception {
		doReturn(null).when(mailOperationsProxy).findArrivalDetailsForReleasingMails(operationalFlightVO);
		Collection<MailResditVO> mailResditVOs = stampResditMessageEventMapper
				.mapLostResditDetailsToPayload(operationalFlightVO);
		assertNull(mailResditVOs);
	}

	@Test
	public void shouldNotMapLostResditDetailsToPayload_WhenContainerDetailsIsEmpty() throws Exception {
		Collection<ContainerDetailsVO> containerDetailsVOs = new ArrayList<>();
		doReturn(containerDetailsVOs).when(mailOperationsProxy)
				.findArrivalDetailsForReleasingMails(operationalFlightVO);
		Collection<MailResditVO> mailResditVOs = stampResditMessageEventMapper
				.mapLostResditDetailsToPayload(operationalFlightVO);
		assertNull(mailResditVOs);
	}

	@Test(expected = SystemException.class)
	public void shouldThrowSystemExceptionWhenArrivalDetailsCausesProxyException() throws Exception {
		doThrow(ProxyException.class).when(mailOperationsProxy)
				.findArrivalDetailsForReleasingMails(operationalFlightVO);
		stampResditMessageEventMapper
				.mapLostResditDetailsToPayload(operationalFlightVO);
	}

	@Test
	public void shouldDoNothingWhenFindMailboxDetailsCausesProxyException() throws Exception {
		Collection<ContainerDetailsVO> containerDetailsVOs = new ArrayList<>();
		Collection<MailbagVO> mailbagVOs = new ArrayList<>();
		ContainerDetailsVO containerDetailsVO = new ContainerDetailsVO();
		MailbagVO mailbagVO = new MailbagVO();
		mailbagVO.setCompanyCode(getCompanyCode());
		mailbagVO.setMailbagId("DEFRAAUSDFWAACA01200120001200");
		mailbagVO.setPaCode("DE101");
		doThrow(ProxyException.class).when(mailOperationsProxy).findMailboxIdFromConfig(mailbagVO);
		mailbagVO.setArrivedFlag(MailConstantsVO.FLAG_NO);
		mailbagVO.setResditEventDate(new LocalDate(LocalDate.NO_STATION, Location.NONE, true));
		mailbagVO.setScannedPort("FRA");
		mailbagVOs.add(mailbagVO);
		containerDetailsVO.setMailDetails(mailbagVOs);
		containerDetailsVO.setCompanyCode(getCompanyCode());
		containerDetailsVO.setContainerNumber("AKE1234AV");
		containerDetailsVOs.add(containerDetailsVO);
		doReturn(containerDetailsVOs).when(mailOperationsProxy)
				.findArrivalDetailsForReleasingMails(operationalFlightVO);
		Collection<MailResditVO> mailResditVOs = stampResditMessageEventMapper
				.mapLostResditDetailsToPayload(operationalFlightVO);
		assertNotNull(mailResditVOs);
	}

	@Test
	public void shouldDoNothingWhenFindMailboxDetailsCausesSystemException() throws Exception {
		Collection<ContainerDetailsVO> containerDetailsVOs = new ArrayList<>();
		Collection<MailbagVO> mailbagVOs = new ArrayList<>();
		ContainerDetailsVO containerDetailsVO = new ContainerDetailsVO();
		MailbagVO mailbagVO = new MailbagVO();
		mailbagVO.setCompanyCode(getCompanyCode());
		mailbagVO.setMailbagId("DEFRAAUSDFWAACA01200120001200");
		mailbagVO.setPaCode("DE101");
		doThrow(SystemException.class).when(mailOperationsProxy).findMailboxIdFromConfig(mailbagVO);
		mailbagVO.setArrivedFlag(MailConstantsVO.FLAG_NO);
		mailbagVO.setScannedPort("FRA");
		mailbagVO.setScannedDate(new LocalDate(LocalDate.NO_STATION, Location.NONE, true));
		mailbagVOs.add(mailbagVO);
		containerDetailsVO.setMailDetails(mailbagVOs);
		containerDetailsVO.setCompanyCode(getCompanyCode());
		containerDetailsVO.setContainerNumber("AKE1234AV");
		containerDetailsVOs.add(containerDetailsVO);
		doReturn(containerDetailsVOs).when(mailOperationsProxy)
				.findArrivalDetailsForReleasingMails(operationalFlightVO);
		Collection<MailResditVO> mailResditVOs = stampResditMessageEventMapper
				.mapLostResditDetailsToPayload(operationalFlightVO);
		assertNotNull(mailResditVOs);
	}
	@Test
	public void shouldGetCurrentDateWhenScannedPortIsNull() throws Exception {
		Collection<ContainerDetailsVO> containerDetailsVOs = new ArrayList<>();
		Collection<MailbagVO> mailbagVOs = new ArrayList<>();
		ContainerDetailsVO containerDetailsVO = new ContainerDetailsVO();
		MailbagVO mailbagVO = new MailbagVO();
		mailbagVO.setCompanyCode(getCompanyCode());
		mailbagVO.setMailbagId("DEFRAAUSDFWAACA01200120001200");
		mailbagVO.setPaCode("DE101");
		doThrow(SystemException.class).when(mailOperationsProxy).findMailboxIdFromConfig(mailbagVO);
		mailbagVO.setArrivedFlag(MailConstantsVO.FLAG_NO);
		mailbagVO.setScannedPort(null);
		mailbagVOs.add(mailbagVO);
		containerDetailsVO.setMailDetails(mailbagVOs);
		containerDetailsVO.setCompanyCode(getCompanyCode());
		containerDetailsVO.setContainerNumber("AKE1234AV");
		containerDetailsVOs.add(containerDetailsVO);
		doReturn(containerDetailsVOs).when(mailOperationsProxy)
				.findArrivalDetailsForReleasingMails(operationalFlightVO);
		Collection<MailResditVO> mailResditVOs = stampResditMessageEventMapper
				.mapLostResditDetailsToPayload(operationalFlightVO);
		assertNotNull(mailResditVOs);
	}	
	@Test
	public void shouldGetCurrentDateWhenScannedPortIsEmpty() throws Exception {
		Collection<ContainerDetailsVO> containerDetailsVOs = new ArrayList<>();
		Collection<MailbagVO> mailbagVOs = new ArrayList<>();
		ContainerDetailsVO containerDetailsVO = new ContainerDetailsVO();
		MailbagVO mailbagVO = new MailbagVO();
		mailbagVO.setCompanyCode(getCompanyCode());
		mailbagVO.setMailbagId("DEFRAAUSDFWAACA01200120001200");
		mailbagVO.setPaCode("DE101");
		doThrow(SystemException.class).when(mailOperationsProxy).findMailboxIdFromConfig(mailbagVO);
		mailbagVO.setArrivedFlag(MailConstantsVO.FLAG_NO);
		mailbagVO.setScannedPort("");
		mailbagVOs.add(mailbagVO);
		containerDetailsVO.setMailDetails(mailbagVOs);
		containerDetailsVO.setCompanyCode(getCompanyCode());
		containerDetailsVO.setContainerNumber("AKE1234AV");
		containerDetailsVOs.add(containerDetailsVO);
		doReturn(containerDetailsVOs).when(mailOperationsProxy)
				.findArrivalDetailsForReleasingMails(operationalFlightVO);
		Collection<MailResditVO> mailResditVOs = stampResditMessageEventMapper
				.mapLostResditDetailsToPayload(operationalFlightVO);
		assertNotNull(mailResditVOs);
	}	
	
	private MailArrivalVO setUpmailArrivalVO() {

		MailArrivalVO mailArrivalVO = new MailArrivalVO();
		mailArrivalVO.setCompanyCode(getCompanyCode());
		mailArrivalVO.setFlightNumber("1234");
		mailArrivalVO.setCarrierId(1134);
		mailArrivalVO.setFlightSequenceNumber(1);
		return mailArrivalVO;
	}

	@Test
	public void shouldMapFoundResditDetailsToPayload() throws Exception {
		Collection<MailbagVO> mailbagVOs = new ArrayList<>();
		MailbagVO mailbagVO = new MailbagVO();
		mailbagVO.setCompanyCode(getCompanyCode());
		mailbagVO.setMailbagId("DEFRAAUSDFWAACA01200120001200");
		mailbagVO.setPaCode("DE101");
		mailbagVO.setFlightNumber("1234");
		mailbagVO.setContainerNumber("AKE14467AV");
		mailbagVO.setFlightSequenceNumber(10);
		mailbagVO.setOoe("FRCDGA");
		mailbagVO.setDoe("DEFRAA");
		mailbagVO.setOrigin("CDG");
		mailbagVO.setDestination("FRA");
		mailbagVO.setMailCategoryCode("A");
		mailbagVO.setMailSubclass("CA");
		mailbagVO.setCarrierCode(getCompanyCode());
		mailbagVOs.add(mailbagVO);
		doReturn("DE101").when(mailOperationsProxy).findMailboxIdFromConfig(mailbagVO);
		mailbagVO.setArrivedFlag(MailConstantsVO.FLAG_NO);
		mailbagVO.setScannedPort("FRA");

		doReturn(mailbagVOs).when(mailOperationsProxy).getFoundArrivalMailBags(mailArrivalVO);
		Collection<MailResditVO> mailResditVOs = stampResditMessageEventMapper
				.mapFoundResditDetailsToPayload(mailArrivalVO);
		assertNotNull(mailResditVOs);

	}

	@Test
	public void shouldNotMapFoundResditDetailsToPayload_WhenMailbagVOIsEmpty() throws Exception {

		Collection<MailbagVO> mailbagVOs = new ArrayList<>();

		doReturn(mailbagVOs).when(mailOperationsProxy).getFoundArrivalMailBags(mailArrivalVO);
		Collection<MailResditVO> mailResditVOs = stampResditMessageEventMapper
				.mapFoundResditDetailsToPayload(mailArrivalVO);
		assertNull(mailResditVOs);

	}

	@Test
	public void shouldNotMapFoundResditDetailsToPayload_WhenMailbagVOsIsNull() throws Exception {
		doReturn(null).when(mailOperationsProxy).getFoundArrivalMailBags(mailArrivalVO);
		Collection<MailResditVO> mailResditVOs = stampResditMessageEventMapper
				.mapFoundResditDetailsToPayload(mailArrivalVO);
		assertNull(mailResditVOs);
	}

	@Test(expected = SystemException.class)
	public void shouldThrowSystemExceptionWhenFoundResditDetailsCausesProxyException() throws Exception {
		doThrow(ProxyException.class).when(mailOperationsProxy).getFoundArrivalMailBags(mailArrivalVO);
		stampResditMessageEventMapper.mapFoundResditDetailsToPayload(mailArrivalVO);
	}

	@Test
	public void shouldDoNothingWhenGetMailboxDetailsCausesProxyException() throws Exception {

		Collection<MailbagVO> mailbagVOs = new ArrayList<>();

		MailbagVO mailbagVO = new MailbagVO();
		mailbagVO.setCompanyCode(getCompanyCode());
		mailbagVO.setMailbagId("DEFRAAUSDFWAACA01200120001200");
		mailbagVO.setPaCode("DE101");
		doThrow(ProxyException.class).when(mailOperationsProxy).findMailboxIdFromConfig(mailbagVO);
		mailbagVO.setArrivedFlag(MailConstantsVO.FLAG_NO);
		mailbagVO.setResditEventDate(new LocalDate(LocalDate.NO_STATION, Location.NONE, true));
		mailbagVO.setScannedPort("FRA");
		mailbagVOs.add(mailbagVO);

		doReturn(mailbagVOs).when(mailOperationsProxy).getFoundArrivalMailBags(mailArrivalVO);
		Collection<MailResditVO> mailResditVOs = stampResditMessageEventMapper
				.mapFoundResditDetailsToPayload(mailArrivalVO);
		assertNotNull(mailResditVOs);
	}

	@Test
	public void shouldDoNothingWhenGetMailboxDetailsCausesSystemException() throws Exception {

		Collection<MailbagVO> mailbagVOs = new ArrayList<>();

		MailbagVO mailbagVO = new MailbagVO();
		mailbagVO.setCompanyCode(getCompanyCode());
		mailbagVO.setMailbagId("DEFRAAUSDFWAACA01200120001200");
		mailbagVO.setPaCode("DE101");
		doThrow(SystemException.class).when(mailOperationsProxy).findMailboxIdFromConfig(mailbagVO);
		mailbagVO.setArrivedFlag(MailConstantsVO.FLAG_NO);
		mailbagVO.setScannedPort("FRA");
		mailbagVO.setScannedDate(new LocalDate(LocalDate.NO_STATION, Location.NONE, true));
		mailbagVOs.add(mailbagVO);

		doReturn(mailbagVOs).when(mailOperationsProxy).getFoundArrivalMailBags(mailArrivalVO);
		Collection<MailResditVO> mailResditVOs = stampResditMessageEventMapper
				.mapFoundResditDetailsToPayload(mailArrivalVO);
		assertNotNull(mailResditVOs);
	}
	
	@Test
	public void shouldGetCurrentDateForFoundMailWhenScannedPortIsNull() throws Exception {		
		Collection<MailbagVO> mailbagVOs = new ArrayList<>();
		MailbagVO mailbagVO = new MailbagVO();
		mailbagVO.setCompanyCode(getCompanyCode());
		mailbagVO.setMailbagId("DEFRAAUSDFWAACA01200120001200");
		mailbagVO.setPaCode("DE101");
		doThrow(SystemException.class).when(mailOperationsProxy).findMailboxIdFromConfig(mailbagVO);
		mailbagVO.setArrivedFlag(MailConstantsVO.FLAG_NO);
		mailbagVO.setScannedPort(null);
		mailbagVOs.add(mailbagVO);
		
		doReturn(mailbagVOs).when(mailOperationsProxy)
				.getFoundArrivalMailBags(mailArrivalVO);
		Collection<MailResditVO> mailResditVOs = stampResditMessageEventMapper
				.mapFoundResditDetailsToPayload(mailArrivalVO);
		assertNotNull(mailResditVOs);
	}
	
	@Test
	public void shouldGetCurrentDateForFoundWhenScannedPortIsEmpty() throws Exception {		
		Collection<MailbagVO> mailbagVOs = new ArrayList<>();		
		MailbagVO mailbagVO = new MailbagVO();
		mailbagVO.setCompanyCode(getCompanyCode());
		mailbagVO.setMailbagId("DEFRAAUSDFWAACA01200120001200");
		mailbagVO.setPaCode("DE101");
		doThrow(SystemException.class).when(mailOperationsProxy).findMailboxIdFromConfig(mailbagVO);
		mailbagVO.setArrivedFlag(MailConstantsVO.FLAG_NO);
		mailbagVO.setScannedPort("");
		mailbagVOs.add(mailbagVO);
		
		doReturn(mailbagVOs).when(mailOperationsProxy)
				.getFoundArrivalMailBags(mailArrivalVO);
		Collection<MailResditVO> mailResditVOs = stampResditMessageEventMapper
				.mapFoundResditDetailsToPayload(mailArrivalVO);
		assertNotNull(mailResditVOs);
	}
	
	@Test
	public void shouldNotMapFoundResditDetailsToPayload_WhenIsFoundResditSentIsTrue() throws Exception {
		MailArrivalVO arrivalVo = new MailArrivalVO();
		arrivalVo.isFoundResditSent();
		arrivalVo.setFoundResditSent(true);
		Collection<MailResditVO> mailResditVOs = stampResditMessageEventMapper
				.mapFoundResditDetailsToPayload(arrivalVo);
		assertNull(mailResditVOs);

	}

}
