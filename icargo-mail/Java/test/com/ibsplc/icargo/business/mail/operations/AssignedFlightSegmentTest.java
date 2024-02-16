package com.ibsplc.icargo.business.mail.operations;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyCollectionOf;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.ibsplc.icargo.business.mail.operations.vo.AssignedFlightSegmentVO;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerAssignmentVO;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerVO;
import com.ibsplc.icargo.business.mail.operations.vo.DespatchDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailArrivalVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.business.mail.operations.vo.OnwardRoutingVO;
import com.ibsplc.icargo.business.mail.operations.vo.OperationalFlightVO;
import com.ibsplc.icargo.framework.feature.AbstractFeatureTest;
import com.ibsplc.icargo.framework.floworchestration.context.SpringAdapter;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.icargo.framework.util.unit.UnitConstants;
import com.ibsplc.icargo.persistence.dao.mail.operations.MailTrackingDefaultsDAO;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.EntityManagerMock;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;

public class AssignedFlightSegmentTest extends AbstractFeatureTest{
	
	private static final String MAIL_OPERATIONS = "mail.operations";
	private AssignedFlightSegment assignedFlightSegmentSpy;
	private Container containerEntity;
	private MailController mailControllerBean; 
	private ULDForSegment uldForSegmentEntity;
	private AssignedFlightSegmentVO assignedFlightSegmentVO;
	private MailTrackingDefaultsDAO dao;

	@Override
	public void setup() throws Exception {
		EntityManagerMock.mockEntityManager();
		assignedFlightSegmentVO = new AssignedFlightSegmentVO();
		assignedFlightSegmentVO.setCompanyCode(getCompanyCode());
		assignedFlightSegmentSpy = spy(new AssignedFlightSegment(assignedFlightSegmentVO));
		containerEntity = new Container();
		mailControllerBean = mockBean("mAilcontroller", MailController.class);
		uldForSegmentEntity = new ULDForSegment();
		dao = mock(MailTrackingDefaultsDAO.class);
	}
	
	@Test
	public void createContainer_Implementation() throws ContainerAssignmentException, DuplicateMailBagsException, SystemException, FinderException, PersistenceException {
		 mailControllerBean = (MailController) SpringAdapter.getInstance().getBean("mAilcontroller");
		 Collection<ContainerDetailsVO> segmentContainers = new ArrayList<>();
         MailArrivalVO mailArrivalVO = new MailArrivalVO();
         Collection<MailbagVO> exceptionMails = new ArrayList<>();
         Map<String,Collection<MailbagVO>> mailBagsMapForInventory = new HashMap<>();
		 Map<String,Collection<DespatchDetailsVO>> despatchesMapForInventory = new HashMap<>();
		 ContainerDetailsVO containerDetailsVO = new ContainerDetailsVO();
		 containerDetailsVO.setOperationFlag("I");
		 containerDetailsVO.setContainerNumber("AKE");
		 containerDetailsVO.setPou("CDG");
		 containerDetailsVO.setLegSerialNumber(1);
		 containerDetailsVO.setPol("DFW");
		 containerDetailsVO.setUldReferenceNo(1);
		 segmentContainers.add(containerDetailsVO);
		 mailArrivalVO.setScanned(true);
		 uldForSegmentEntity.setNumberOfBags(1);
		 doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
		 doThrow(FinderException.class).when(PersistenceController.getEntityManager()).find(eq(Container.class), any(ContainerPK.class));
		 doReturn(uldForSegmentEntity).when(PersistenceController.getEntityManager()).find(eq(ULDForSegment.class), any(ULDForSegmentPK.class));
		 ContainerVO containerVo = new ContainerVO();
			ContainerPK ContainerPK = new ContainerPK();
			Collection<ContainerVO> containerVos = new ArrayList<>();
			OperationalFlightVO operationalFlightVO = new OperationalFlightVO();
			operationalFlightVO.setFlightSequenceNumber(-1);
			HashMap<String, String> systemParameterMap = null;
			Collection<OnwardRoutingVO> onwardRoutings = new ArrayList<>();
			OnwardRoutingVO onwardRoutingVO = new OnwardRoutingVO();
			onwardRoutingVO.setCompanyCode(getCompanyCode());
			onwardRoutingVO.setOnwardFlightDate(new LocalDate("CDG", Location.ARP,true));
			onwardRoutings.add(onwardRoutingVO);
			containerVo.setOnwardRoutings(onwardRoutings);
			containerVo.setAssignedPort("CDG");
			containerVo.setReassignFlag(false);
			containerVo.setContainerNumber("AKE12345AV");
			containerVo.setOperationFlag("U");
			containerVo.setFromDeviationList(true);
			containerVo.setUldReferenceNo(1);
			containerVos.add(containerVo);
			containerEntity.setContainerPK(ContainerPK);
			containerEntity.setAcceptanceFlag("Y");
			containerEntity.setTransitFlag("Y");
			
			doReturn(containerEntity).when(PersistenceController.getEntityManager()).find(eq(Container.class), any(ContainerPK.class));
			
		 assignedFlightSegmentSpy.saveArrivalDetails(segmentContainers, mailArrivalVO,exceptionMails,mailBagsMapForInventory,despatchesMapForInventory);
		 assertThat(containerDetailsVO.getOperationFlag(),is("I"));
	}
	
	@Test
	public void saveArrivalDetails_Insertion() throws ContainerAssignmentException, DuplicateMailBagsException, SystemException, FinderException{
		 mailControllerBean = (MailController) SpringAdapter.getInstance().getBean("mAilcontroller");
		 Collection<ContainerDetailsVO> segmentContainers = new ArrayList<>();
         MailArrivalVO mailArrivalVO = new MailArrivalVO();
         Collection<MailbagVO> exceptionMails = new ArrayList<>();
         Map<String,Collection<MailbagVO>> mailBagsMapForInventory = new HashMap<>();
		 Map<String,Collection<DespatchDetailsVO>> despatchesMapForInventory = new HashMap<>();
		 ContainerDetailsVO containerDetailsVO = new ContainerDetailsVO();
		 containerDetailsVO.setOperationFlag("I");
		 containerDetailsVO.setContainerNumber("BULK");
		 segmentContainers.add(containerDetailsVO);
		 mailArrivalVO.setScanned(true);
		 uldForSegmentEntity.setNumberOfBags(1);
		 doReturn(uldForSegmentEntity).when(PersistenceController.getEntityManager()).find(eq(ULDForSegment.class), any(ULDForSegmentPK.class));
		 assignedFlightSegmentSpy.saveArrivalDetails(segmentContainers, mailArrivalVO,exceptionMails,mailBagsMapForInventory,despatchesMapForInventory);
		 assertThat(containerDetailsVO.getOperationFlag(),is("I"));
	}
	
	@Test
	public void saveArrivalDetails_ContainerAssignmentException() throws ContainerAssignmentException, DuplicateMailBagsException, SystemException, FinderException, PersistenceException{
		 Collection<ContainerDetailsVO> segmentContainers = new ArrayList<>();
         MailArrivalVO mailArrivalVO = new MailArrivalVO();
         Collection<MailbagVO> exceptionMails = new ArrayList<>();
         Map<String,Collection<MailbagVO>> mailBagsMapForInventory = new HashMap<>();
		 Map<String,Collection<DespatchDetailsVO>> despatchesMapForInventory = new HashMap<>();
		 ContainerAssignmentVO containerAsgVO = new ContainerAssignmentVO();
		 ContainerDetailsVO containerDetailsVO = new ContainerDetailsVO();
		 MailbagVO mailbagVO = new MailbagVO();
		 Collection<MailbagVO> mailDetails = new ArrayList<>();
		 mailbagVO.setCompanyCode(getCompanyCode());
		 mailDetails.add(mailbagVO);
		 containerDetailsVO.setOperationFlag("I");
		 containerDetailsVO.setContainerNumber("AKE");
		 containerDetailsVO.setPou("CDG");
		 containerDetailsVO.setLegSerialNumber(1);
		 containerDetailsVO.setMailDetails(mailDetails);
		 segmentContainers.add(containerDetailsVO);
		 mailArrivalVO.setScanned(true);
		 containerAsgVO.setAcceptanceFlag("Y");
		 containerAsgVO.setFlightSequenceNumber(-1);
		 doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
		 doReturn(containerEntity).when(PersistenceController.getEntityManager()).find(eq(Container.class), any(ContainerPK.class));
		 doReturn(containerAsgVO).when(dao).findContainerAssignment(any(String.class),any(String.class), any(String.class));
		 doReturn(uldForSegmentEntity).when(PersistenceController.getEntityManager()).find(eq(ULDForSegment.class), any(ULDForSegmentPK.class));
		 assignedFlightSegmentSpy.saveArrivalDetails(segmentContainers, mailArrivalVO,exceptionMails,mailBagsMapForInventory,despatchesMapForInventory);
	}
	
	@Test
	public void saveArrivalDetails_ContainerAssignmentException_WithoutMailDetails() throws ContainerAssignmentException, DuplicateMailBagsException, SystemException, FinderException, PersistenceException{
		 Collection<ContainerDetailsVO> segmentContainers = new ArrayList<>();
         MailArrivalVO mailArrivalVO = new MailArrivalVO();
         Collection<MailbagVO> exceptionMails = new ArrayList<>();
         Map<String,Collection<MailbagVO>> mailBagsMapForInventory = new HashMap<>();
		 Map<String,Collection<DespatchDetailsVO>> despatchesMapForInventory = new HashMap<>();
		 ContainerAssignmentVO containerAsgVO = new ContainerAssignmentVO();
		 ContainerDetailsVO containerDetailsVO = new ContainerDetailsVO();
		 Collection<MailbagVO> mailDetails = new ArrayList<>();
		 mailDetails = null;
		 containerDetailsVO.setOperationFlag("I");
		 containerDetailsVO.setContainerNumber("AKE");
		 containerDetailsVO.setPou("CDG");
		 containerDetailsVO.setLegSerialNumber(1);
		 containerDetailsVO.setMailDetails(mailDetails);
		 segmentContainers.add(containerDetailsVO);
		 mailArrivalVO.setScanned(true);
		 containerAsgVO.setAcceptanceFlag("Y");
		 containerAsgVO.setFlightSequenceNumber(-1);
		 doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
		 doReturn(containerAsgVO).when(dao).findContainerAssignment(any(String.class),any(String.class), any(String.class));
		 doReturn(containerEntity).when(PersistenceController.getEntityManager()).find(eq(Container.class), any(ContainerPK.class));
		 doReturn(uldForSegmentEntity).when(PersistenceController.getEntityManager()).find(eq(ULDForSegment.class), any(ULDForSegmentPK.class));
		 assignedFlightSegmentSpy.saveArrivalDetails(segmentContainers, mailArrivalVO,exceptionMails,mailBagsMapForInventory,despatchesMapForInventory);
		 assertNull(mailDetails);
	}
	
	@Test(expected=ContainerAssignmentException.class)
	public void saveArrivalDetails_ContainerAssignmentException_IsScannedFalse() throws DuplicateMailBagsException, SystemException, FinderException, PersistenceException, ContainerAssignmentException{
		 Collection<ContainerDetailsVO> segmentContainers = new ArrayList<>();
         MailArrivalVO mailArrivalVO = new MailArrivalVO();
         Collection<MailbagVO> exceptionMails = new ArrayList<>();
         Map<String,Collection<MailbagVO>> mailBagsMapForInventory = new HashMap<>();
		 Map<String,Collection<DespatchDetailsVO>> despatchesMapForInventory = new HashMap<>();
		 ContainerAssignmentVO containerAsgVO = new ContainerAssignmentVO();
		 ContainerDetailsVO containerDetailsVO = new ContainerDetailsVO();
		 containerDetailsVO.setOperationFlag("I");
		 containerDetailsVO.setContainerNumber("AKE");
		 containerDetailsVO.setPou("CDG");
		 containerDetailsVO.setLegSerialNumber(1);
		 containerDetailsVO.setTotalWeight(new Measure(UnitConstants.MAIL_WGT,10));
		 containerDetailsVO.setReceivedWeight(new Measure(UnitConstants.MAIL_WGT,10));
		 segmentContainers.add(containerDetailsVO);
		 mailArrivalVO.setScanned(false);
		 containerAsgVO.setAcceptanceFlag("Y");
		 containerAsgVO.setFlightSequenceNumber(-1);
		 doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
		 doReturn(containerAsgVO).when(dao).findContainerAssignment(any(String.class),any(String.class), any(String.class));
		 doReturn(containerEntity).when(PersistenceController.getEntityManager()).find(eq(Container.class), any(ContainerPK.class));
		 doReturn(uldForSegmentEntity).when(PersistenceController.getEntityManager()).find(eq(ULDForSegment.class), any(ULDForSegmentPK.class));
		 assignedFlightSegmentSpy.saveArrivalDetails(segmentContainers, mailArrivalVO,exceptionMails,mailBagsMapForInventory,despatchesMapForInventory);
	}
	
	@Test
	public void saveArrivalDetails_Updation() throws ContainerAssignmentException, DuplicateMailBagsException, SystemException, FinderException{
		 mailControllerBean = (MailController) SpringAdapter.getInstance().getBean("mAilcontroller");
		 Collection<ContainerDetailsVO> segmentContainers = new ArrayList<>();
         MailArrivalVO mailArrivalVO = new MailArrivalVO();
         Collection<MailbagVO> exceptionMails = new ArrayList<>();
         Map<String,Collection<MailbagVO>> mailBagsMapForInventory = new HashMap<>();
		 Map<String,Collection<DespatchDetailsVO>> despatchesMapForInventory = new HashMap<>();
		 ContainerDetailsVO containerDetailsVO = new ContainerDetailsVO();
		 containerDetailsVO.setContainerNumber("BULK");
		 containerDetailsVO.setOperationFlag("U");
		 containerDetailsVO.setPou("CDG");
		 containerDetailsVO.setLegSerialNumber(1);
		 segmentContainers.add(containerDetailsVO);
		 mailArrivalVO.setScanned(true);
		 doReturn(containerEntity).when(PersistenceController.getEntityManager()).find(eq(Container.class), any(ContainerPK.class));
		 doThrow(FinderException.class).when(PersistenceController.getEntityManager()).find(eq(ULDForSegment.class), any(ULDForSegmentPK.class));
		 assignedFlightSegmentSpy.saveArrivalDetails(segmentContainers, mailArrivalVO,exceptionMails,mailBagsMapForInventory,despatchesMapForInventory);
		 assertThat(containerDetailsVO.getContainerNumber(), is("BULK"));
	}
	
	@Test
	public void saveArrivalDetails_Updation_IsSavedTrue() throws ContainerAssignmentException, DuplicateMailBagsException, SystemException, FinderException{
		 mailControllerBean = (MailController) SpringAdapter.getInstance().getBean("mAilcontroller");
		 Collection<ContainerDetailsVO> segmentContainers = new ArrayList<>();
         MailArrivalVO mailArrivalVO = new MailArrivalVO();
         Collection<MailbagVO> exceptionMails = new ArrayList<>();
         Map<String,Collection<MailbagVO>> mailBagsMapForInventory = new HashMap<>();
		 Map<String,Collection<DespatchDetailsVO>> despatchesMapForInventory = new HashMap<>();
		 ContainerDetailsVO containerDetailsVO = new ContainerDetailsVO();
		 containerDetailsVO.setContainerNumber("BULK");
		 containerDetailsVO.setOperationFlag("U");
		 containerDetailsVO.setPou("CDG");
		 containerDetailsVO.setLegSerialNumber(1);
		 containerDetailsVO.setTotalWeight(new Measure(UnitConstants.MAIL_WGT,10));
		 containerDetailsVO.setReceivedWeight(new Measure(UnitConstants.MAIL_WGT,10));
		 segmentContainers.add(containerDetailsVO);
		 mailArrivalVO.setScanned(false);
		 doReturn(containerEntity).when(PersistenceController.getEntityManager()).find(eq(Container.class), any(ContainerPK.class));
		 doReturn(uldForSegmentEntity).when(PersistenceController.getEntityManager()).find(eq(ULDForSegment.class), any(ULDForSegmentPK.class));
		 assignedFlightSegmentSpy.saveArrivalDetails(segmentContainers, mailArrivalVO,exceptionMails,mailBagsMapForInventory,despatchesMapForInventory);
		 assertThat(containerDetailsVO.getContainerNumber(),is("BULK"));
	}

	@Test
	public void saveArrivalDetails_Updation_IsScannedTrue() throws ContainerAssignmentException, DuplicateMailBagsException, SystemException, FinderException{
		mailControllerBean = (MailController) SpringAdapter.getInstance().getBean("mAilcontroller");
		 Collection<ContainerDetailsVO> segmentContainers = new ArrayList<>();
         MailArrivalVO mailArrivalVO = new MailArrivalVO();
         Collection<MailbagVO> exceptionMails = new ArrayList<>();
         Map<String,Collection<MailbagVO>> mailBagsMapForInventory = new HashMap<>();
		 Map<String,Collection<DespatchDetailsVO>> despatchesMapForInventory = new HashMap<>();
		 ContainerDetailsVO containerDetailsVO = new ContainerDetailsVO();
		 containerDetailsVO.setContainerNumber("BULK");
		 containerDetailsVO.setOperationFlag("U");
		 containerDetailsVO.setPou("CDG");
		 containerDetailsVO.setLegSerialNumber(1);
		 segmentContainers.add(containerDetailsVO);
		 mailArrivalVO.setScanned(true);
		 doReturn(containerEntity).when(PersistenceController.getEntityManager()).find(eq(Container.class), any(ContainerPK.class));
		 doReturn(uldForSegmentEntity).when(PersistenceController.getEntityManager()).find(eq(ULDForSegment.class), any(ULDForSegmentPK.class));
		 assignedFlightSegmentSpy.saveArrivalDetails(segmentContainers, mailArrivalVO,exceptionMails,mailBagsMapForInventory,despatchesMapForInventory);
		 assertTrue(mailArrivalVO.isScanned());
	}
	
	@Test
	public void saveArrivalDetails_OperationFlagNull() throws ContainerAssignmentException, DuplicateMailBagsException, SystemException, FinderException{
		 Collection<ContainerDetailsVO> segmentContainers = new ArrayList<>();
         MailArrivalVO mailArrivalVO = new MailArrivalVO();
         Collection<MailbagVO> exceptionMails = new ArrayList<>();
         Map<String,Collection<MailbagVO>> mailBagsMapForInventory = new HashMap<>();
		 Map<String,Collection<DespatchDetailsVO>> despatchesMapForInventory = new HashMap<>();
		 ContainerDetailsVO containerDetailsVO = new ContainerDetailsVO();
		 containerDetailsVO.setOperationFlag(null);
		 segmentContainers.add(containerDetailsVO);
		 assignedFlightSegmentSpy.saveArrivalDetails(segmentContainers, mailArrivalVO,exceptionMails,mailBagsMapForInventory,despatchesMapForInventory);
		 assertNull(containerDetailsVO.getOperationFlag());
	}
}
