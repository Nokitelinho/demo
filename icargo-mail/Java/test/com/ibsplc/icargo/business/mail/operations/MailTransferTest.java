package com.ibsplc.icargo.business.mail.operations;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.spy;
import static org.mockito.Matchers.anyCollectionOf;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Matchers.eq;
import static org.mockito.Matchers.any;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import org.junit.Test;

import com.ibsplc.icargo.business.flight.operation.vo.FlightFilterVO;
import com.ibsplc.icargo.business.flight.operation.vo.FlightValidationVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.RateAuditDetailsVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.RateAuditVO;
import com.ibsplc.icargo.business.mail.operations.proxy.FlightOperationsProxy;
import com.ibsplc.icargo.business.mail.operations.proxy.MailOperationsMRAProxy;
import com.ibsplc.icargo.business.mail.operations.proxy.SharedDefaultsProxy;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.business.mail.operations.vo.OperationalFlightVO;
import com.ibsplc.icargo.framework.feature.AbstractFeatureTest;
import com.ibsplc.icargo.framework.proxy.ProxyException;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.EntityManagerMock;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;

public class MailTransferTest extends AbstractFeatureTest{
	
	private MailTransfer mailTransferSpy;
	private SharedDefaultsProxy sharedDefaultsProxy;
	private MailController mailControllerBean;
	private AssignedFlight assignedFlightEntity;
	private AssignedFlightSegment assignedFlightSegmentEntity;
	private ULDForSegment uldForSegmentEntity;
	private Container containerEntity;
	private FlightOperationsProxy flightOperationsProxy; 
	private FlightValidationVO flightValidationVO;
    private MailbagInULDForSegment mailbagInULDForSegmententity;
	private static final String CONTAINER_NUMBER = "AKE00910AV";
	private static final String FLIGHT_ROUTE = "FRA-CDG";
	private MailOperationsMRAProxy mailOperationsMRAProxy;
	@Override
	public void setup() throws Exception {
		mailTransferSpy = spy(new MailTransfer());
		sharedDefaultsProxy = mockProxy(SharedDefaultsProxy.class);
		mailControllerBean = mockBean("mAilcontroller", MailController.class);
		assignedFlightEntity = new AssignedFlight();
		EntityManagerMock.mockEntityManager();
		assignedFlightSegmentEntity = new AssignedFlightSegment();
		uldForSegmentEntity = new ULDForSegment();
		containerEntity = new Container();
		flightOperationsProxy = mockProxy(FlightOperationsProxy.class);
		mailbagInULDForSegmententity = new MailbagInULDForSegment();
		mailOperationsMRAProxy = mockProxy(MailOperationsMRAProxy.class);
	}
	
	@Test
	public void collectContainerAuditDetails_Flights() throws SystemException, InvalidFlightSegmentException, ULDDefaultsProxyException, CapacityBookingProxyException, MailBookingException, FinderException{
		ContainerPK containerPK = new ContainerPK();
		containerEntity.setContainerPK(containerPK);
		OperationalFlightVO operationalFlightVO = new OperationalFlightVO();
		operationalFlightVO.setFlightNumber("1000");
		operationalFlightVO.setPol("CDG");
		Collection<ContainerVO> containerVOs = new ArrayList<>();
		ContainerVO containerVO = new ContainerVO();
		containerVO.setCompanyCode(getCompanyCode());
		containerVOs.add(containerVO);
		HashMap<String, String> systemParameterMap = null;
		ULDForSegmentPK uLDForSegmentPK = new ULDForSegmentPK();
		uLDForSegmentPK.setUldNumber("AKE1234AV");
		uldForSegmentEntity.setUldForSegmentPk(uLDForSegmentPK);
		doReturn(systemParameterMap).when(sharedDefaultsProxy).findSystemParameterByCodes(anyCollectionOf(String.class));
		doReturn(assignedFlightEntity).when(PersistenceController.getEntityManager()).find(eq(AssignedFlight.class), any(AssignedFlightPK.class));
		doReturn(assignedFlightSegmentEntity).when(PersistenceController.getEntityManager()).find(eq(AssignedFlightSegment.class), any(AssignedFlightSegmentPK.class));
		doReturn(uldForSegmentEntity).when(PersistenceController.getEntityManager()).find(eq(ULDForSegment.class), any(ULDForSegmentPK.class));
		doReturn(containerEntity).when(PersistenceController.getEntityManager()).find(eq(Container.class), any(ContainerPK.class));
		mailTransferSpy.transferContainers(containerVOs, operationalFlightVO, null);
		assertThat(operationalFlightVO.getFlightNumber(),is("1000"));
	}
	
	@Test
	public void collectContainerAuditDetails_Carriers() throws SystemException, InvalidFlightSegmentException, ULDDefaultsProxyException, CapacityBookingProxyException, MailBookingException, FinderException{
		ContainerPK containerPK = new ContainerPK();
		containerEntity.setContainerPK(containerPK);
		OperationalFlightVO operationalFlightVO = new OperationalFlightVO();
		operationalFlightVO.setFlightNumber("-1");
		operationalFlightVO.setPol("CDG");
		Collection<ContainerVO> containerVOs = new ArrayList<>();
		ContainerVO containerVO = new ContainerVO();
		containerVO.setCompanyCode(getCompanyCode());
		containerVOs.add(containerVO);
		HashMap<String, String> systemParameterMap = null;
		ULDForSegmentPK uLDForSegmentPK = new ULDForSegmentPK();
		uLDForSegmentPK.setUldNumber("AKE1234AV");
		uldForSegmentEntity.setUldForSegmentPk(uLDForSegmentPK);
		doReturn(systemParameterMap).when(sharedDefaultsProxy).findSystemParameterByCodes(anyCollectionOf(String.class));
		doReturn(assignedFlightEntity).when(PersistenceController.getEntityManager()).find(eq(AssignedFlight.class), any(AssignedFlightPK.class));
		doReturn(assignedFlightSegmentEntity).when(PersistenceController.getEntityManager()).find(eq(AssignedFlightSegment.class), any(AssignedFlightSegmentPK.class));
		doReturn(uldForSegmentEntity).when(PersistenceController.getEntityManager()).find(eq(ULDForSegment.class), any(ULDForSegmentPK.class));
		doReturn(containerEntity).when(PersistenceController.getEntityManager()).find(eq(Container.class), any(ContainerPK.class));
		mailTransferSpy.transferContainers(containerVOs, operationalFlightVO, null);
		assertThat(operationalFlightVO.getFlightNumber(),is("-1"));
	}
	@Test
	public void saveMailbagsInboundDtlsForTransfer_test()throws SystemException, FinderException {
		Collection<MailbagVO> mailbagVOs=new ArrayList<>();
		ContainerVO containerVO = new ContainerVO();
		MailbagVO mailbagVO = new MailbagVO();
		LocalDate currentDate = new LocalDate(LocalDate.NO_STATION, Location.NONE, true);
		LocalDate std = new LocalDate("DFW", Location.ARP, true);
		Collection<FlightValidationVO> flightFilterVOs = new ArrayList<>();
		FlightValidationVO flightFilterVO = new FlightValidationVO();
		flightFilterVO.setFlightStatus(FlightValidationVO.FLT_STATUS_ACTIVE);
		flightFilterVO.setLegOrigin("FRA");
		flightFilterVO.setFlightRoute(FLIGHT_ROUTE);
		flightFilterVO.setCompanyCode("AV");
		flightFilterVO.setFlightDate(currentDate);
		flightFilterVO.setStd(std);
		flightFilterVO.setFlightNumber("0005");
		flightFilterVO.setFlightSequenceNumber(3);
		flightFilterVO.setLegSerialNumber(1);
		flightFilterVOs.add(flightFilterVO);
		FlightFilterVO flightFilterVos = new FlightFilterVO();
		flightFilterVos.setFlightNumber("0005");
		mailbagVO.setCompanyCode("AV");
		mailbagVO.setCarrierId(1134);
		mailbagVO.setFlightNumber("0005");
		mailbagVO.setFlightSequenceNumber(3);
		mailbagVO.setLegSerialNumber(0);
		 mailbagVO.setSegmentSerialNumber(1);
		mailbagVO.setMailSequenceNumber(13108);
		mailbagVOs.add(mailbagVO);

		containerVO.setFromDeviationList(true);
		containerVO.setAssignedPort("CDG");
		containerVO.setContainerNumber(CONTAINER_NUMBER);
				
		AssignedFlightPK assignedFlightPk = new AssignedFlightPK();
		assignedFlightPk.setAirportCode("CDG");
		assignedFlightPk.setCarrierId(1134);
		assignedFlightPk.setCompanyCode("AV");
		assignedFlightPk.setFlightNumber("0005");
		assignedFlightPk.setFlightSequenceNumber(3);
		assignedFlightPk.setLegSerialNumber(1);
		assignedFlightEntity.setAssignedFlightPk(assignedFlightPk);
		AssignedFlightSegmentPK assignedFlightSegmentPK = new AssignedFlightSegmentPK();
		assignedFlightSegmentPK.setCarrierId(1134);
		assignedFlightSegmentPK.setCompanyCode("AV");
		assignedFlightSegmentPK.setFlightNumber("0005");
		assignedFlightSegmentPK.setFlightSequenceNumber(3);
		assignedFlightSegmentPK.setSegmentSerialNumber(1);
		assignedFlightSegmentEntity.setAssignedFlightSegmentPK(assignedFlightSegmentPK);
		MailbagInULDForSegmentPK mailbagInULDForSegmentPK = new MailbagInULDForSegmentPK();
		mailbagInULDForSegmentPK.setCarrierId(1134);
		mailbagInULDForSegmentPK.setCompanyCode("AV");
		mailbagInULDForSegmentPK.setFlightNumber("0005");
		mailbagInULDForSegmentPK.setFlightSequenceNumber(3);
		mailbagInULDForSegmentPK.setSegmentSerialNumber(1);
		mailbagInULDForSegmentPK.setUldNumber(CONTAINER_NUMBER);
		mailbagInULDForSegmententity.setMailbagInULDForSegmentPK(mailbagInULDForSegmentPK);
		ULDForSegmentPK uldForSegmentPk = new ULDForSegmentPK();
		uldForSegmentPk.setCarrierId(1134);
		uldForSegmentPk.setCompanyCode("AV");
		uldForSegmentPk.setFlightNumber("0005");
		uldForSegmentPk.setFlightSequenceNumber(3);
		uldForSegmentPk.setSegmentSerialNumber(1);
		uldForSegmentPk.setUldNumber(CONTAINER_NUMBER);
		uldForSegmentEntity.setUldForSegmentPk(uldForSegmentPk);
		doReturn(flightFilterVOs).when(flightOperationsProxy).validateFlightForAirport(any(FlightFilterVO.class));
		doReturn(assignedFlightEntity).when(PersistenceController.getEntityManager()).find(eq(AssignedFlight.class), any(AssignedFlightPK.class));
		doReturn(assignedFlightSegmentEntity).when(PersistenceController.getEntityManager()).find(eq(AssignedFlightSegment.class), any(AssignedFlightSegmentPK.class));
		doReturn(uldForSegmentEntity).when(PersistenceController.getEntityManager()).find(eq(ULDForSegment.class), any(ULDForSegmentPK.class));
		doReturn(mailbagInULDForSegmententity).when(PersistenceController.getEntityManager()).find(eq(MailbagInULDForSegment.class), any(MailbagInULDForSegmentPK.class));
		doReturn(containerEntity).when(PersistenceController.getEntityManager()).find(eq(Container.class), any(ContainerPK.class));
		mailTransferSpy.saveMailbagsInboundDtlsForTransfer(mailbagVOs, containerVO);
		
	}
	@Test
	public void saveMailbagsInboundDtlsForTransfer_VoNull_test()throws SystemException, FinderException {
		Collection<MailbagVO> mailbagVOs=new ArrayList<>();
		ContainerVO containerVO = new ContainerVO();
		MailbagVO mailbagVO = new MailbagVO();
		Collection<FlightValidationVO> flightFilterVOs = null;
		FlightFilterVO flightFilterVos = new FlightFilterVO();
		flightFilterVos.setFlightNumber("0005");
		mailbagVO.setCompanyCode("AV");
		mailbagVO.setCarrierId(1134);
		mailbagVO.setFlightNumber("0005");
		mailbagVO.setFlightSequenceNumber(3);
		mailbagVO.setLegSerialNumber(1);
		mailbagVO.setSegmentSerialNumber(1);
		mailbagVO.setMailSequenceNumber(13108);
		mailbagVOs.add(mailbagVO);
		containerVO.setFromDeviationList(true);
		containerVO.setAssignedPort("CDG");
		containerVO.setContainerNumber(CONTAINER_NUMBER);
		AssignedFlightPK assignedFlightPk = new AssignedFlightPK();
		assignedFlightPk.setAirportCode("CDG");
		assignedFlightPk.setCarrierId(1134);
		assignedFlightPk.setCompanyCode("AV");
		assignedFlightPk.setFlightNumber("0005");
		assignedFlightPk.setFlightSequenceNumber(3);
		assignedFlightPk.setLegSerialNumber(1);
		assignedFlightEntity.setAssignedFlightPk(assignedFlightPk);
		AssignedFlightSegmentPK assignedFlightSegmentPK = new AssignedFlightSegmentPK();
		assignedFlightSegmentPK.setCarrierId(1134);
		assignedFlightSegmentPK.setCompanyCode("AV");
		assignedFlightSegmentPK.setFlightNumber("0005");
		assignedFlightSegmentPK.setFlightSequenceNumber(3);
		assignedFlightSegmentPK.setSegmentSerialNumber(1);
		assignedFlightSegmentEntity.setAssignedFlightSegmentPK(assignedFlightSegmentPK);
		MailbagInULDForSegmentPK mailbagInULDForSegmentPK = new MailbagInULDForSegmentPK();
		mailbagInULDForSegmentPK.setCarrierId(1134);
		mailbagInULDForSegmentPK.setCompanyCode("AV");
		mailbagInULDForSegmentPK.setFlightNumber("0005");
		mailbagInULDForSegmentPK.setFlightSequenceNumber(3);
		mailbagInULDForSegmentPK.setSegmentSerialNumber(1);
		mailbagInULDForSegmentPK.setUldNumber(CONTAINER_NUMBER);
		mailbagInULDForSegmententity.setMailbagInULDForSegmentPK(mailbagInULDForSegmentPK);
		ULDForSegmentPK uldForSegmentPk = new ULDForSegmentPK();
		uldForSegmentPk.setCarrierId(1134);
		uldForSegmentPk.setCompanyCode("AV");
		uldForSegmentPk.setFlightNumber("0005");
		uldForSegmentPk.setFlightSequenceNumber(3);
		uldForSegmentPk.setSegmentSerialNumber(1);
		uldForSegmentPk.setUldNumber(CONTAINER_NUMBER);
		uldForSegmentEntity.setUldForSegmentPk(uldForSegmentPk);
		doReturn(flightFilterVOs).when(flightOperationsProxy).validateFlightForAirport(any(FlightFilterVO.class));
		doReturn(assignedFlightEntity).when(PersistenceController.getEntityManager()).find(eq(AssignedFlight.class), any(AssignedFlightPK.class));
		doReturn(assignedFlightSegmentEntity).when(PersistenceController.getEntityManager()).find(eq(AssignedFlightSegment.class), any(AssignedFlightSegmentPK.class));
		doReturn(uldForSegmentEntity).when(PersistenceController.getEntityManager()).find(eq(ULDForSegment.class), any(ULDForSegmentPK.class));
		doReturn(mailbagInULDForSegmententity).when(PersistenceController.getEntityManager()).find(eq(MailbagInULDForSegment.class), any(MailbagInULDForSegmentPK.class));
		doReturn(containerEntity).when(PersistenceController.getEntityManager()).find(eq(Container.class), any(ContainerPK.class));
		mailTransferSpy.saveMailbagsInboundDtlsForTransfer(mailbagVOs, containerVO);
	}
	@Test
	public void saveMailbagsInboundDtlsForTransfer_else_test()throws SystemException, FinderException {Collection<MailbagVO> mailbagVOs=new ArrayList<>();
	ContainerVO containerVO = new ContainerVO();
	MailbagVO mailbagVO = new MailbagVO();
	LocalDate currentDate = new LocalDate(LocalDate.NO_STATION, Location.NONE, true);
	LocalDate std = new LocalDate("DFW", Location.ARP, true);
	Collection<FlightValidationVO> flightValidationVos= new ArrayList<>();
	Collection<FlightValidationVO> flightFilterVOs = new ArrayList<>();
	FlightValidationVO flightFilterVO = new FlightValidationVO();
	flightFilterVO.setFlightStatus(FlightValidationVO.FLT_STATUS_ACTIVE);
	flightFilterVO.setLegOrigin("FRA");
	flightFilterVO.setFlightRoute(FLIGHT_ROUTE);
	flightFilterVO.setCompanyCode("AV");
	flightFilterVO.setFlightDate(currentDate);
	flightFilterVO.setStd(std);
	flightFilterVO.setFlightNumber("0005");
	flightFilterVO.setFlightSequenceNumber(3);
	flightFilterVO.setLegSerialNumber(1);
	flightFilterVOs.add(flightFilterVO);
	mailbagVO.setCompanyCode("AV");
	mailbagVO.setCarrierId(1134);
	mailbagVO.setFlightNumber("0005");
	mailbagVO.setLegSerialNumber(0);
	mailbagVO.setSegmentSerialNumber(1);
	mailbagVO.setMailSequenceNumber(13108);
	mailbagVOs.add(mailbagVO);
	containerVO.setFromDeviationList(true);
	containerVO.setAssignedPort("CDG");
	containerVO.setContainerNumber(CONTAINER_NUMBER);
	flightValidationVos.add(flightValidationVO);
	AssignedFlightPK assignedFlightPk = new AssignedFlightPK();
	assignedFlightPk.setAirportCode("CDG");
	assignedFlightPk.setCarrierId(1134);
	assignedFlightPk.setCompanyCode("AV");
	assignedFlightPk.setFlightNumber("0005");
	assignedFlightPk.setFlightSequenceNumber(3);
	assignedFlightPk.setLegSerialNumber(1);
	assignedFlightEntity.setAssignedFlightPk(assignedFlightPk);
	AssignedFlightSegmentPK assignedFlightSegmentPK = new AssignedFlightSegmentPK();
	assignedFlightSegmentPK.setCarrierId(1134);
	assignedFlightSegmentPK.setCompanyCode("AV");
	assignedFlightSegmentPK.setFlightNumber("0005");
	assignedFlightSegmentPK.setFlightSequenceNumber(3);
	assignedFlightSegmentPK.setSegmentSerialNumber(1);
	assignedFlightSegmentEntity.setAssignedFlightSegmentPK(assignedFlightSegmentPK);
	MailbagInULDForSegmentPK mailbagInULDForSegmentPK = new MailbagInULDForSegmentPK();
	mailbagInULDForSegmentPK.setCarrierId(1134);
	mailbagInULDForSegmentPK.setCompanyCode("AV");
	mailbagInULDForSegmentPK.setFlightNumber("0005");
	mailbagInULDForSegmentPK.setFlightSequenceNumber(3);
	mailbagInULDForSegmentPK.setSegmentSerialNumber(1);
	mailbagInULDForSegmentPK.setUldNumber(CONTAINER_NUMBER);
	mailbagInULDForSegmententity.setMailbagInULDForSegmentPK(mailbagInULDForSegmentPK);
	ULDForSegmentPK uldForSegmentPk = new ULDForSegmentPK();
	uldForSegmentPk.setCarrierId(1134);
	uldForSegmentPk.setCompanyCode("AV");
	uldForSegmentPk.setFlightNumber("0005");
	uldForSegmentPk.setFlightSequenceNumber(3);
	uldForSegmentPk.setSegmentSerialNumber(1);
	uldForSegmentPk.setUldNumber(CONTAINER_NUMBER);
	uldForSegmentEntity.setUldForSegmentPk(uldForSegmentPk);
	doReturn(flightFilterVOs).when(flightOperationsProxy).validateFlightForAirport(any(FlightFilterVO.class));
	doReturn(assignedFlightEntity).when(PersistenceController.getEntityManager()).find(eq(AssignedFlight.class), any(AssignedFlightPK.class));
	doReturn(assignedFlightSegmentEntity).when(PersistenceController.getEntityManager()).find(eq(AssignedFlightSegment.class), any(AssignedFlightSegmentPK.class));
	doReturn(uldForSegmentEntity).when(PersistenceController.getEntityManager()).find(eq(ULDForSegment.class), any(ULDForSegmentPK.class));
	doReturn(mailbagInULDForSegmententity).when(PersistenceController.getEntityManager()).find(eq(MailbagInULDForSegment.class), any(MailbagInULDForSegmentPK.class));
	doReturn(containerEntity).when(PersistenceController.getEntityManager()).find(eq(Container.class), any(ContainerPK.class));
	mailTransferSpy.saveMailbagsInboundDtlsForTransfer(mailbagVOs, containerVO);
	}
	@Test
	public void saveMailbagsInboundDtlsForTransfer_legSernum0_test()throws SystemException, FinderException {
		Collection<MailbagVO> mailbagVOs=new ArrayList<>();
	ContainerVO containerVO = new ContainerVO();
	MailbagVO mailbagVO = new MailbagVO();
	LocalDate currentDate = new LocalDate(LocalDate.NO_STATION, Location.NONE, true);
	LocalDate std = new LocalDate("DFW", Location.ARP, true);
	Collection<FlightValidationVO> flightValidationVos= new ArrayList<>();
	Collection<FlightValidationVO> flightFilterVOs = new ArrayList<>();
	FlightValidationVO flightFilterVO = new FlightValidationVO();
	flightFilterVO.setFlightStatus(FlightValidationVO.FLT_STATUS_ACTIVE);
	flightFilterVO.setLegOrigin("FRA");
	flightFilterVO.setFlightRoute(FLIGHT_ROUTE);
	flightFilterVO.setCompanyCode("AV");
	flightFilterVO.setFlightDate(currentDate);
	flightFilterVO.setStd(std);
	flightFilterVO.setFlightNumber("0005");
	flightFilterVO.setFlightSequenceNumber(3);
	flightFilterVO.setLegSerialNumber(1);
	flightFilterVOs.add(flightFilterVO);
	FlightFilterVO flightFilterVos = new FlightFilterVO();
	flightFilterVos.setFlightNumber("0005");
	mailbagVO.setCompanyCode("AV");
	mailbagVO.setCarrierId(1134);
	mailbagVO.setFlightNumber("0005");
	mailbagVO.setFlightSequenceNumber(3);
	mailbagVO.setLegSerialNumber(1);
	mailbagVO.setSegmentSerialNumber(1);
	mailbagVO.setMailSequenceNumber(13108);
	mailbagVOs.add(mailbagVO);
	containerVO.setFromDeviationList(true);
	containerVO.setAssignedPort("CDG");
	containerVO.setContainerNumber(CONTAINER_NUMBER);
	flightValidationVos.add(flightValidationVO);
	AssignedFlightPK assignedFlightPk = new AssignedFlightPK();
	assignedFlightPk.setAirportCode("CDG");
	assignedFlightPk.setCarrierId(1134);
	assignedFlightPk.setCompanyCode("AV");
	assignedFlightPk.setFlightNumber("0005");
	assignedFlightPk.setFlightSequenceNumber(3);
	assignedFlightPk.setLegSerialNumber(1);
	assignedFlightEntity.setAssignedFlightPk(assignedFlightPk);
	AssignedFlightSegmentPK assignedFlightSegmentPK = new AssignedFlightSegmentPK();
	assignedFlightSegmentPK.setCarrierId(1134);
	assignedFlightSegmentPK.setCompanyCode("AV");
	assignedFlightSegmentPK.setFlightNumber("0005");
	assignedFlightSegmentPK.setFlightSequenceNumber(3);
	assignedFlightSegmentPK.setSegmentSerialNumber(1);
	assignedFlightSegmentEntity.setAssignedFlightSegmentPK(assignedFlightSegmentPK);
	MailbagInULDForSegmentPK mailbagInULDForSegmentPK = new MailbagInULDForSegmentPK();
	mailbagInULDForSegmentPK.setCarrierId(1134);
	mailbagInULDForSegmentPK.setCompanyCode("AV");
	mailbagInULDForSegmentPK.setFlightNumber("0005");
	mailbagInULDForSegmentPK.setFlightSequenceNumber(3);
	mailbagInULDForSegmentPK.setSegmentSerialNumber(1);
	mailbagInULDForSegmentPK.setUldNumber(CONTAINER_NUMBER);
	mailbagInULDForSegmententity.setMailbagInULDForSegmentPK(mailbagInULDForSegmentPK);
	ULDForSegmentPK uldForSegmentPk = new ULDForSegmentPK();
	uldForSegmentPk.setCarrierId(1134);
	uldForSegmentPk.setCompanyCode("AV");
	uldForSegmentPk.setFlightNumber("0005");
	uldForSegmentPk.setFlightSequenceNumber(3);
	uldForSegmentPk.setSegmentSerialNumber(1);
	uldForSegmentPk.setUldNumber(CONTAINER_NUMBER);
	uldForSegmentEntity.setUldForSegmentPk(uldForSegmentPk);
	doReturn(flightFilterVOs).when(flightOperationsProxy).validateFlightForAirport(any(FlightFilterVO.class));
	doReturn(assignedFlightEntity).when(PersistenceController.getEntityManager()).find(eq(AssignedFlight.class), any(AssignedFlightPK.class));
	doReturn(assignedFlightSegmentEntity).when(PersistenceController.getEntityManager()).find(eq(AssignedFlightSegment.class), any(AssignedFlightSegmentPK.class));
	doReturn(uldForSegmentEntity).when(PersistenceController.getEntityManager()).find(eq(ULDForSegment.class), any(ULDForSegmentPK.class));
	doReturn(mailbagInULDForSegmententity).when(PersistenceController.getEntityManager()).find(eq(MailbagInULDForSegment.class), any(MailbagInULDForSegmentPK.class));
	doReturn(containerEntity).when(PersistenceController.getEntityManager()).find(eq(Container.class), any(ContainerPK.class));
	mailTransferSpy.saveMailbagsInboundDtlsForTransfer(mailbagVOs, containerVO);
	}
	@Test
	public void saveMailbagsInboundDtlsForTransfer_empty_test()throws SystemException, FinderException {
		Collection<MailbagVO> mailbagVOs=new ArrayList<>();
	ContainerVO containerVO = new ContainerVO();
	MailbagVO mailbagVO = new MailbagVO();
	Collection<FlightValidationVO> flightFilterVOs = new ArrayList<>();
	FlightFilterVO flightFilterVos = new FlightFilterVO();
	flightFilterVos.setFlightNumber("0005");
	mailbagVO.setCompanyCode("AV");
	mailbagVO.setCarrierId(1134);
	mailbagVO.setFlightNumber("0005");
	mailbagVO.setFlightSequenceNumber(3);
	mailbagVO.setLegSerialNumber(1);
	mailbagVO.setSegmentSerialNumber(1);
	mailbagVO.setMailSequenceNumber(13108);
	mailbagVOs.add(mailbagVO);
	containerVO.setFromDeviationList(true);
	containerVO.setAssignedPort("CDG");
	containerVO.setContainerNumber(CONTAINER_NUMBER);
	AssignedFlightPK assignedFlightPk = new AssignedFlightPK();
	assignedFlightPk.setAirportCode("CDG");
	assignedFlightPk.setCarrierId(1134);
	assignedFlightPk.setCompanyCode("AV");
	assignedFlightPk.setFlightNumber("0005");
	assignedFlightPk.setFlightSequenceNumber(3);
	assignedFlightPk.setLegSerialNumber(1);
	assignedFlightEntity.setAssignedFlightPk(assignedFlightPk);
	AssignedFlightSegmentPK assignedFlightSegmentPK = new AssignedFlightSegmentPK();
	assignedFlightSegmentPK.setCarrierId(1134);
	assignedFlightSegmentPK.setCompanyCode("AV");
	assignedFlightSegmentPK.setFlightNumber("0005");
	assignedFlightSegmentPK.setFlightSequenceNumber(3);
	assignedFlightSegmentPK.setSegmentSerialNumber(1);
	assignedFlightSegmentEntity.setAssignedFlightSegmentPK(assignedFlightSegmentPK);
	MailbagInULDForSegmentPK mailbagInULDForSegmentPK = new MailbagInULDForSegmentPK();
	mailbagInULDForSegmentPK.setCarrierId(1134);
	mailbagInULDForSegmentPK.setCompanyCode("AV");
	mailbagInULDForSegmentPK.setFlightNumber("0005");
	mailbagInULDForSegmentPK.setFlightSequenceNumber(3);
	mailbagInULDForSegmentPK.setSegmentSerialNumber(1);
	mailbagInULDForSegmentPK.setUldNumber(CONTAINER_NUMBER);
	mailbagInULDForSegmententity.setMailbagInULDForSegmentPK(mailbagInULDForSegmentPK);
	ULDForSegmentPK uldForSegmentPk = new ULDForSegmentPK();
	uldForSegmentPk.setCarrierId(1134);
	uldForSegmentPk.setCompanyCode("AV");
	uldForSegmentPk.setFlightNumber("0005");
	uldForSegmentPk.setFlightSequenceNumber(3);
	uldForSegmentPk.setSegmentSerialNumber(1);
	uldForSegmentPk.setUldNumber(CONTAINER_NUMBER);
	uldForSegmentEntity.setUldForSegmentPk(uldForSegmentPk);
	doReturn(flightFilterVOs).when(flightOperationsProxy).validateFlightForAirport(any(FlightFilterVO.class));
	doReturn(assignedFlightEntity).when(PersistenceController.getEntityManager()).find(eq(AssignedFlight.class), any(AssignedFlightPK.class));
	doReturn(assignedFlightSegmentEntity).when(PersistenceController.getEntityManager()).find(eq(AssignedFlightSegment.class), any(AssignedFlightSegmentPK.class));
	doReturn(uldForSegmentEntity).when(PersistenceController.getEntityManager()).find(eq(ULDForSegment.class), any(ULDForSegmentPK.class));
	doReturn(mailbagInULDForSegmententity).when(PersistenceController.getEntityManager()).find(eq(MailbagInULDForSegment.class), any(MailbagInULDForSegmentPK.class));
	doReturn(containerEntity).when(PersistenceController.getEntityManager()).find(eq(Container.class), any(ContainerPK.class));
	mailTransferSpy.saveMailbagsInboundDtlsForTransfer(mailbagVOs, containerVO);
	}
	@Test
	public void importMailProvisionalRateDataForTransferContainer() throws SystemException, InvalidFlightSegmentException, ULDDefaultsProxyException, CapacityBookingProxyException, MailBookingException, FinderException, ProxyException{
		ContainerPK containerPK = new ContainerPK();
		containerEntity.setContainerPK(containerPK);
		OperationalFlightVO operationalFlightVO = new OperationalFlightVO();
		operationalFlightVO.setFlightNumber("1000");
		operationalFlightVO.setPol("CDG");
		Collection<ContainerVO> containerVOs = new ArrayList<>();
		ContainerVO containerVO = new ContainerVO();
		containerVO.setCompanyCode(getCompanyCode());
		containerVOs.add(containerVO);
		HashMap<String, String> systemParameterMap = null;
		ULDForSegmentPK uLDForSegmentPK = new ULDForSegmentPK();
		uLDForSegmentPK.setUldNumber("AKE1234AV");
		uldForSegmentEntity.setUldForSegmentPk(uLDForSegmentPK);
		doReturn(systemParameterMap).when(sharedDefaultsProxy).findSystemParameterByCodes(anyCollectionOf(String.class));
		doReturn(assignedFlightEntity).when(PersistenceController.getEntityManager()).find(eq(AssignedFlight.class), any(AssignedFlightPK.class));
		doReturn(assignedFlightSegmentEntity).when(PersistenceController.getEntityManager()).find(eq(AssignedFlightSegment.class), any(AssignedFlightSegmentPK.class));
		doReturn(uldForSegmentEntity).when(PersistenceController.getEntityManager()).find(eq(ULDForSegment.class), any(ULDForSegmentPK.class));
		doReturn(containerEntity).when(PersistenceController.getEntityManager()).find(eq(Container.class), any(ContainerPK.class));
		Collection<RateAuditVO> rateAuditVOs = new ArrayList();
		Collection<RateAuditDetailsVO> rateAuditDetailsVOs = new ArrayList();
		RateAuditVO auditVO = new RateAuditVO();
		auditVO.setCompanyCode("IBS");
		auditVO.setMailSequenceNumber(234567);
		RateAuditDetailsVO auditDetail = new RateAuditDetailsVO();
		auditDetail.setSource("TRA");
		rateAuditDetailsVOs.add(auditDetail);
		auditVO.setRateAuditDetails(rateAuditDetailsVOs);
		rateAuditVOs.add(auditVO);
		doReturn(rateAuditVOs).when(mailControllerBean).createRateAuditVOs(any(OperationalFlightVO.class), any(ContainerVO.class), anyCollectionOf(MailbagVO.class), any(String.class), any(Boolean.class));
		HashMap<String, String> parameterMap = new HashMap();
		parameterMap.put("mailtracking.defaults.importsmailstomra", "Y");
		doReturn(parameterMap).when(sharedDefaultsProxy).findSystemParameterByCodes(anyCollectionOf(String.class));
		doNothing().when(mailOperationsMRAProxy).importMailProvisionalRateData(anyCollectionOf(RateAuditVO.class));
		mailTransferSpy.transferContainers(containerVOs, operationalFlightVO, null);
		assertThat(operationalFlightVO.getFlightNumber(),is("1000"));
	}
	@Test
	public void importMailProvisionalRateDataForTransferContainer_WithImportNotEnabled() throws SystemException, InvalidFlightSegmentException, ULDDefaultsProxyException, CapacityBookingProxyException, MailBookingException, FinderException, ProxyException{
		ContainerPK containerPK = new ContainerPK();
		containerEntity.setContainerPK(containerPK);
		OperationalFlightVO operationalFlightVO = new OperationalFlightVO();
		operationalFlightVO.setFlightNumber("1000");
		operationalFlightVO.setPol("CDG");
		Collection<ContainerVO> containerVOs = new ArrayList<>();
		ContainerVO containerVO = new ContainerVO();
		containerVO.setCompanyCode(getCompanyCode());
		containerVOs.add(containerVO);
		HashMap<String, String> systemParameterMap = null;
		ULDForSegmentPK uLDForSegmentPK = new ULDForSegmentPK();
		uLDForSegmentPK.setUldNumber("AKE1234AV");
		uldForSegmentEntity.setUldForSegmentPk(uLDForSegmentPK);
		doReturn(systemParameterMap).when(sharedDefaultsProxy).findSystemParameterByCodes(anyCollectionOf(String.class));
		doReturn(assignedFlightEntity).when(PersistenceController.getEntityManager()).find(eq(AssignedFlight.class), any(AssignedFlightPK.class));
		doReturn(assignedFlightSegmentEntity).when(PersistenceController.getEntityManager()).find(eq(AssignedFlightSegment.class), any(AssignedFlightSegmentPK.class));
		doReturn(uldForSegmentEntity).when(PersistenceController.getEntityManager()).find(eq(ULDForSegment.class), any(ULDForSegmentPK.class));
		doReturn(containerEntity).when(PersistenceController.getEntityManager()).find(eq(Container.class), any(ContainerPK.class));
		Collection<RateAuditVO> rateAuditVOs = new ArrayList();
		Collection<RateAuditDetailsVO> rateAuditDetailsVOs = new ArrayList();
		RateAuditVO auditVO = new RateAuditVO();
		auditVO.setCompanyCode("IBS");
		auditVO.setMailSequenceNumber(234567);
		RateAuditDetailsVO auditDetail = new RateAuditDetailsVO();
		auditDetail.setSource("TRA");
		rateAuditDetailsVOs.add(auditDetail);
		auditVO.setRateAuditDetails(rateAuditDetailsVOs);
		rateAuditVOs.add(auditVO);
		doReturn(rateAuditVOs).when(mailControllerBean).createRateAuditVOs(any(OperationalFlightVO.class), any(ContainerVO.class), anyCollectionOf(MailbagVO.class), any(String.class), any(Boolean.class));
		HashMap<String, String> parameterMap = new HashMap();
		parameterMap.put("mailtracking.defaults.importsmailstomra", "N");
		doReturn(parameterMap).when(sharedDefaultsProxy).findSystemParameterByCodes(anyCollectionOf(String.class));
		doNothing().when(mailOperationsMRAProxy).importMailProvisionalRateData(anyCollectionOf(RateAuditVO.class));
		mailTransferSpy.transferContainers(containerVOs, operationalFlightVO, null);
		assertThat(operationalFlightVO.getFlightNumber(),is("1000"));
	}
	@Test
	public void importMailProvisionalRateDataForTransferContainer_WithNoAuditVO() throws SystemException, InvalidFlightSegmentException, ULDDefaultsProxyException, CapacityBookingProxyException, MailBookingException, FinderException, ProxyException{
		ContainerPK containerPK = new ContainerPK();
		containerEntity.setContainerPK(containerPK);
		OperationalFlightVO operationalFlightVO = new OperationalFlightVO();
		operationalFlightVO.setFlightNumber("1000");
		operationalFlightVO.setPol("CDG");
		Collection<ContainerVO> containerVOs = new ArrayList<>();
		ContainerVO containerVO = new ContainerVO();
		containerVO.setCompanyCode(getCompanyCode());
		containerVOs.add(containerVO);
		HashMap<String, String> systemParameterMap = null;
		ULDForSegmentPK uLDForSegmentPK = new ULDForSegmentPK();
		uLDForSegmentPK.setUldNumber("AKE1234AV");
		uldForSegmentEntity.setUldForSegmentPk(uLDForSegmentPK);
		doReturn(systemParameterMap).when(sharedDefaultsProxy).findSystemParameterByCodes(anyCollectionOf(String.class));
		doReturn(assignedFlightEntity).when(PersistenceController.getEntityManager()).find(eq(AssignedFlight.class), any(AssignedFlightPK.class));
		doReturn(assignedFlightSegmentEntity).when(PersistenceController.getEntityManager()).find(eq(AssignedFlightSegment.class), any(AssignedFlightSegmentPK.class));
		doReturn(uldForSegmentEntity).when(PersistenceController.getEntityManager()).find(eq(ULDForSegment.class), any(ULDForSegmentPK.class));
		doReturn(containerEntity).when(PersistenceController.getEntityManager()).find(eq(Container.class), any(ContainerPK.class));
		Collection<RateAuditVO> rateAuditVOs = null;
		doReturn(rateAuditVOs).when(mailControllerBean).createRateAuditVOs(any(OperationalFlightVO.class), any(ContainerVO.class), anyCollectionOf(MailbagVO.class), any(String.class), any(Boolean.class));
		HashMap<String, String> parameterMap = new HashMap();
		parameterMap.put("mailtracking.defaults.importsmailstomra", "Y");
		doReturn(parameterMap).when(sharedDefaultsProxy).findSystemParameterByCodes(anyCollectionOf(String.class));
		doNothing().when(mailOperationsMRAProxy).importMailProvisionalRateData(anyCollectionOf(RateAuditVO.class));
		mailTransferSpy.transferContainers(containerVOs, operationalFlightVO, null);
		assertThat(operationalFlightVO.getFlightNumber(),is("1000"));
	}
	@Test
	public void importMailProvisionalRateDataForTransferContainer_WithEmptyAuditVO() throws SystemException, InvalidFlightSegmentException, ULDDefaultsProxyException, CapacityBookingProxyException, MailBookingException, FinderException, ProxyException{
		ContainerPK containerPK = new ContainerPK();
		containerEntity.setContainerPK(containerPK);
		OperationalFlightVO operationalFlightVO = new OperationalFlightVO();
		operationalFlightVO.setFlightNumber("1000");
		operationalFlightVO.setPol("CDG");
		Collection<ContainerVO> containerVOs = new ArrayList<>();
		ContainerVO containerVO = new ContainerVO();
		containerVO.setCompanyCode(getCompanyCode());
		containerVOs.add(containerVO);
		HashMap<String, String> systemParameterMap = null;
		ULDForSegmentPK uLDForSegmentPK = new ULDForSegmentPK();
		uLDForSegmentPK.setUldNumber("AKE1234AV");
		uldForSegmentEntity.setUldForSegmentPk(uLDForSegmentPK);
		doReturn(systemParameterMap).when(sharedDefaultsProxy).findSystemParameterByCodes(anyCollectionOf(String.class));
		doReturn(assignedFlightEntity).when(PersistenceController.getEntityManager()).find(eq(AssignedFlight.class), any(AssignedFlightPK.class));
		doReturn(assignedFlightSegmentEntity).when(PersistenceController.getEntityManager()).find(eq(AssignedFlightSegment.class), any(AssignedFlightSegmentPK.class));
		doReturn(uldForSegmentEntity).when(PersistenceController.getEntityManager()).find(eq(ULDForSegment.class), any(ULDForSegmentPK.class));
		doReturn(containerEntity).when(PersistenceController.getEntityManager()).find(eq(Container.class), any(ContainerPK.class));
		Collection<RateAuditVO> rateAuditVOs = new ArrayList();
		doReturn(rateAuditVOs).when(mailControllerBean).createRateAuditVOs(any(OperationalFlightVO.class), any(ContainerVO.class), anyCollectionOf(MailbagVO.class), any(String.class), any(Boolean.class));
		HashMap<String, String> parameterMap = new HashMap();
		parameterMap.put("mailtracking.defaults.importsmailstomra", "Y");
		doReturn(parameterMap).when(sharedDefaultsProxy).findSystemParameterByCodes(anyCollectionOf(String.class));
		doNothing().when(mailOperationsMRAProxy).importMailProvisionalRateData(anyCollectionOf(RateAuditVO.class));
		mailTransferSpy.transferContainers(containerVOs, operationalFlightVO, null);
		assertThat(operationalFlightVO.getFlightNumber(),is("1000"));
	}
	@Test(expected=SystemException.class)
	public void importMailProvisionalRateDataForTransferContainer_throwaexception() throws SystemException, InvalidFlightSegmentException, ULDDefaultsProxyException, CapacityBookingProxyException, MailBookingException, FinderException, ProxyException{
		ContainerPK containerPK = new ContainerPK();
		containerEntity.setContainerPK(containerPK);
		OperationalFlightVO operationalFlightVO = new OperationalFlightVO();
		operationalFlightVO.setFlightNumber("1000");
		operationalFlightVO.setPol("CDG");
		Collection<ContainerVO> containerVOs = new ArrayList<>();
		ContainerVO containerVO = new ContainerVO();
		containerVO.setCompanyCode(getCompanyCode());
		containerVOs.add(containerVO);
		HashMap<String, String> systemParameterMap = null;
		ULDForSegmentPK uLDForSegmentPK = new ULDForSegmentPK();
		uLDForSegmentPK.setUldNumber("AKE1234AV");
		uldForSegmentEntity.setUldForSegmentPk(uLDForSegmentPK);
		doReturn(systemParameterMap).when(sharedDefaultsProxy).findSystemParameterByCodes(anyCollectionOf(String.class));
		doReturn(assignedFlightEntity).when(PersistenceController.getEntityManager()).find(eq(AssignedFlight.class), any(AssignedFlightPK.class));
		doReturn(assignedFlightSegmentEntity).when(PersistenceController.getEntityManager()).find(eq(AssignedFlightSegment.class), any(AssignedFlightSegmentPK.class));
		doReturn(uldForSegmentEntity).when(PersistenceController.getEntityManager()).find(eq(ULDForSegment.class), any(ULDForSegmentPK.class));
		doReturn(containerEntity).when(PersistenceController.getEntityManager()).find(eq(Container.class), any(ContainerPK.class));
		Collection<RateAuditVO> rateAuditVOs = new ArrayList();
		Collection<RateAuditDetailsVO> rateAuditDetailsVOs = new ArrayList();
		RateAuditVO auditVO = new RateAuditVO();
		auditVO.setCompanyCode("IBS");
		auditVO.setMailSequenceNumber(234567);
		RateAuditDetailsVO auditDetail = new RateAuditDetailsVO();
		auditDetail.setSource("TRA");
		rateAuditDetailsVOs.add(auditDetail);
		auditVO.setRateAuditDetails(rateAuditDetailsVOs);
		rateAuditVOs.add(auditVO);
		doReturn(rateAuditVOs).when(mailControllerBean).createRateAuditVOs(any(OperationalFlightVO.class), any(ContainerVO.class), anyCollectionOf(MailbagVO.class), any(String.class), any(Boolean.class));
		HashMap<String, String> parameterMap = new HashMap();
		parameterMap.put("mailtracking.defaults.importsmailstomra", "Y");
		doReturn(parameterMap).when(sharedDefaultsProxy).findSystemParameterByCodes(anyCollectionOf(String.class));
		doThrow(ProxyException.class).when(mailOperationsMRAProxy).importMailProvisionalRateData(anyCollectionOf(RateAuditVO.class));
		mailTransferSpy.transferContainers(containerVOs, operationalFlightVO, null);
	}
}