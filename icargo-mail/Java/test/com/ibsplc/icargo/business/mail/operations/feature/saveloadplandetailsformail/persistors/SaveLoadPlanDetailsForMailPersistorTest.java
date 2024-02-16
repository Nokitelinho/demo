package com.ibsplc.icargo.business.mail.operations.feature.saveloadplandetailsformail.persistors;

import org.junit.Test;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.doThrow;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.mail.operations.ConsignmentDocument;
import com.ibsplc.icargo.business.mail.operations.ConsignmentDocumentPK;
import com.ibsplc.icargo.business.mail.operations.FlightLoadPlanContainer;
import com.ibsplc.icargo.business.mail.operations.FlightLoadPlanContainerPK;
import com.ibsplc.icargo.business.mail.operations.vo.FlightLoadPlanContainerVO;
import com.ibsplc.icargo.framework.feature.AbstractFeatureTest;
import com.ibsplc.icargo.persistence.dao.mail.operations.MailTrackingDefaultsDAO;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.CreateException;
import com.ibsplc.xibase.server.framework.persistence.EntityManagerMock;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;

public class SaveLoadPlanDetailsForMailPersistorTest  extends AbstractFeatureTest{

	private SaveLoadPlanDetailsForMailPersistor saveLoadPlanDetailsForMailPersistor;
	private FlightLoadPlanContainer flightLoadPlanContainerEntity;
	private MailTrackingDefaultsDAO dao;
	private static final String MAIL_OPERATIONS = "mail.operations";


	@Override
	public void setup() throws Exception {
		saveLoadPlanDetailsForMailPersistor =spy(new SaveLoadPlanDetailsForMailPersistor());
		EntityManagerMock.mockEntityManager();
		dao = mock(MailTrackingDefaultsDAO.class);


	}
	@Test
	public void persistLoadPanForInsert() throws SystemException, PersistenceException {
		FlightLoadPlanContainerVO flightLoadPlanContainerVO = new FlightLoadPlanContainerVO();
		flightLoadPlanContainerVO.setCompanyCode("IBS");
		flightLoadPlanContainerVO.setFlightNumber("AK456");
		flightLoadPlanContainerVO.setFlightSequenceNumber(12);
		flightLoadPlanContainerVO.setSegOrigin("CDG");
		flightLoadPlanContainerVO.setSegDestination("DXB");
		flightLoadPlanContainerVO.setLoadPlanVersion(1);
		flightLoadPlanContainerVO.setContainerType("ST");
		flightLoadPlanContainerVO.setUldReferenceNo(546);
		flightLoadPlanContainerVO.setSegSerialNumber(1);
		flightLoadPlanContainerVO.setPlannedPieces(3);
		flightLoadPlanContainerVO.setPlannedWeight(23.1);
		flightLoadPlanContainerVO.setPlanStatus("I");
		flightLoadPlanContainerVO.setUldNumber("6789");
		flightLoadPlanContainerVO.setOperationFlag("I");
		flightLoadPlanContainerEntity = new FlightLoadPlanContainer(flightLoadPlanContainerVO);
		Collection<FlightLoadPlanContainerVO> loadPlanVOs = new ArrayList();
		loadPlanVOs.add(flightLoadPlanContainerVO);
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
		doReturn(loadPlanVOs).when(dao).findPreviousLoadPlanVersionsForContainer(flightLoadPlanContainerVO);
		saveLoadPlanDetailsForMailPersistor.persist(flightLoadPlanContainerVO);
	}
	@Test
	public void flightLoadPlanContainer_ExceptionHandling() throws SystemException, CreateException, PersistenceException{
		FlightLoadPlanContainerVO flightLoadPlanContainerVO = new FlightLoadPlanContainerVO();
		flightLoadPlanContainerVO.setCompanyCode("IBS");
		flightLoadPlanContainerVO.setFlightNumber("AK456");
		flightLoadPlanContainerVO.setFlightSequenceNumber(12);
		flightLoadPlanContainerVO.setSegOrigin("CDG");
		flightLoadPlanContainerVO.setSegDestination("DXB");
		flightLoadPlanContainerVO.setLoadPlanVersion(1);
		flightLoadPlanContainerVO.setContainerType("ST");
		flightLoadPlanContainerVO.setUldReferenceNo(546);
		flightLoadPlanContainerVO.setSegSerialNumber(1);
		flightLoadPlanContainerVO.setPlannedPieces(3);
		flightLoadPlanContainerVO.setPlannedWeight(23.1);
		flightLoadPlanContainerVO.setPlanStatus("I");
		flightLoadPlanContainerVO.setUldNumber("6789");
		flightLoadPlanContainerVO.setOperationFlag("I");
		flightLoadPlanContainerVO.setPlannedPosition("F");
		flightLoadPlanContainerVO.setPlannedVolume(12.34);
		CreateException createException= new CreateException();
		Collection<FlightLoadPlanContainerVO> loadPlanVOs = new ArrayList();
		loadPlanVOs.add(flightLoadPlanContainerVO);
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
		doReturn(loadPlanVOs).when(dao).findPreviousLoadPlanVersionsForContainer(flightLoadPlanContainerVO);
		doThrow(createException).when(PersistenceController.getEntityManager()).persist(any(FlightLoadPlanContainerVO.class));
		saveLoadPlanDetailsForMailPersistor.persist(flightLoadPlanContainerVO);	}
	@Test
	public void persistLoadPanForUpdate() throws SystemException, PersistenceException, FinderException {
		FlightLoadPlanContainerVO flightLoadPlanContainerVO = new FlightLoadPlanContainerVO();
		flightLoadPlanContainerVO.setCompanyCode("IBS");
		flightLoadPlanContainerVO.setFlightNumber("AK456");
		flightLoadPlanContainerVO.setFlightSequenceNumber(12);
		flightLoadPlanContainerVO.setSegOrigin("CDG");
		flightLoadPlanContainerVO.setSegDestination("DXB");
		flightLoadPlanContainerVO.setLoadPlanVersion(1);
		flightLoadPlanContainerVO.setContainerType("ST");
		flightLoadPlanContainerVO.setUldReferenceNo(546);
		flightLoadPlanContainerVO.setSegSerialNumber(1);
		flightLoadPlanContainerVO.setPlannedPieces(3);
		flightLoadPlanContainerVO.setPlannedWeight(23.1);
		flightLoadPlanContainerVO.setPlanStatus("I");
		flightLoadPlanContainerVO.setUldNumber("6789");
		flightLoadPlanContainerVO.setOperationFlag("U");
		flightLoadPlanContainerEntity = new FlightLoadPlanContainer(flightLoadPlanContainerVO);
		doReturn(flightLoadPlanContainerEntity).when(PersistenceController.getEntityManager()).find(eq(FlightLoadPlanContainer.class),
				any(FlightLoadPlanContainerPK.class));
		Collection<FlightLoadPlanContainerVO> loadPlanVOs = new ArrayList();
		loadPlanVOs.add(flightLoadPlanContainerVO);
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
		doReturn(loadPlanVOs).when(dao).findPreviousLoadPlanVersionsForContainer(flightLoadPlanContainerVO);
		saveLoadPlanDetailsForMailPersistor.persist(flightLoadPlanContainerVO);
	}
	@Test
	public void persistLoadPanForUpdate_WithNoFlightLoadPlanContainer() throws SystemException, PersistenceException, FinderException {
		FlightLoadPlanContainerVO flightLoadPlanContainerVO = new FlightLoadPlanContainerVO();
		flightLoadPlanContainerVO.setCompanyCode("IBS");
		flightLoadPlanContainerVO.setFlightNumber("AK456");
		flightLoadPlanContainerVO.setFlightSequenceNumber(12);
		flightLoadPlanContainerVO.setSegOrigin("CDG");
		flightLoadPlanContainerVO.setSegDestination("DXB");
		flightLoadPlanContainerVO.setLoadPlanVersion(1);
		flightLoadPlanContainerVO.setContainerType("ST");
		flightLoadPlanContainerVO.setUldReferenceNo(546);
		flightLoadPlanContainerVO.setSegSerialNumber(1);
		flightLoadPlanContainerVO.setPlannedPieces(3);
		flightLoadPlanContainerVO.setPlannedWeight(23.1);
		flightLoadPlanContainerVO.setPlanStatus("I");
		flightLoadPlanContainerVO.setUldNumber("6789");
		flightLoadPlanContainerVO.setOperationFlag("U");
		flightLoadPlanContainerEntity = new FlightLoadPlanContainer(flightLoadPlanContainerVO);
		doReturn(null).when(PersistenceController.getEntityManager()).find(eq(FlightLoadPlanContainer.class),
				any(FlightLoadPlanContainerPK.class));
		Collection<FlightLoadPlanContainerVO> loadPlanVOs = new ArrayList();
		loadPlanVOs.add(flightLoadPlanContainerVO);
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
		doReturn(loadPlanVOs).when(dao).findPreviousLoadPlanVersionsForContainer(flightLoadPlanContainerVO);
		saveLoadPlanDetailsForMailPersistor.persist(flightLoadPlanContainerVO);
	}
	@Test
	public void persistLoadPanForDelete() throws SystemException, PersistenceException, FinderException {
		FlightLoadPlanContainerVO flightLoadPlanContainerVO = new FlightLoadPlanContainerVO();
		flightLoadPlanContainerVO.setCompanyCode("IBS");
		flightLoadPlanContainerVO.setFlightNumber("AK456");
		flightLoadPlanContainerVO.setFlightSequenceNumber(12);
		flightLoadPlanContainerVO.setSegOrigin("CDG");
		flightLoadPlanContainerVO.setSegDestination("DXB");
		flightLoadPlanContainerVO.setLoadPlanVersion(1);
		flightLoadPlanContainerVO.setContainerType("ST");
		flightLoadPlanContainerVO.setUldReferenceNo(546);
		flightLoadPlanContainerVO.setSegSerialNumber(1);
		flightLoadPlanContainerVO.setPlannedPieces(3);
		flightLoadPlanContainerVO.setPlannedWeight(23.1);
		flightLoadPlanContainerVO.setPlanStatus("I");
		flightLoadPlanContainerVO.setUldNumber("6789");
		flightLoadPlanContainerVO.setOperationFlag("D");
		flightLoadPlanContainerEntity = new FlightLoadPlanContainer(flightLoadPlanContainerVO);
		doReturn(flightLoadPlanContainerEntity).when(PersistenceController.getEntityManager()).find(eq(FlightLoadPlanContainer.class),
				any(FlightLoadPlanContainerPK.class));
		Collection<FlightLoadPlanContainerVO> loadPlanVOs = new ArrayList();
		loadPlanVOs.add(flightLoadPlanContainerVO);
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
		doReturn(loadPlanVOs).when(dao).findPreviousLoadPlanVersionsForContainer(flightLoadPlanContainerVO);
		saveLoadPlanDetailsForMailPersistor.persist(flightLoadPlanContainerVO);
	}
	@Test
	public void persistLoadPanWithEmptyConatinerVO() throws SystemException, PersistenceException {
		FlightLoadPlanContainerVO flightLoadPlanContainerVO = null;
		saveLoadPlanDetailsForMailPersistor.persist(flightLoadPlanContainerVO);
	}
	@Test
	public void persistLoadPanForUpdate_throwFinderException() throws SystemException, PersistenceException, FinderException {
		FlightLoadPlanContainerVO flightLoadPlanContainerVO = new FlightLoadPlanContainerVO();
		flightLoadPlanContainerVO.setCompanyCode("IBS");
		flightLoadPlanContainerVO.setFlightNumber("AK456");
		flightLoadPlanContainerVO.setFlightSequenceNumber(12);
		flightLoadPlanContainerVO.setSegOrigin("CDG");
		flightLoadPlanContainerVO.setSegDestination("DXB");
		flightLoadPlanContainerVO.setLoadPlanVersion(1);
		flightLoadPlanContainerVO.setContainerType("ST");
		flightLoadPlanContainerVO.setUldReferenceNo(546);
		flightLoadPlanContainerVO.setSegSerialNumber(1);
		flightLoadPlanContainerVO.setPlannedPieces(3);
		flightLoadPlanContainerVO.setPlannedWeight(23.1);
		flightLoadPlanContainerVO.setPlanStatus("I");
		flightLoadPlanContainerVO.setUldNumber("6789");
		flightLoadPlanContainerVO.setOperationFlag("U");
		flightLoadPlanContainerEntity = new FlightLoadPlanContainer(flightLoadPlanContainerVO);
		doThrow(FinderException.class).when(PersistenceController.getEntityManager()).find(eq(FlightLoadPlanContainer.class),
				any(FlightLoadPlanContainerPK.class));
		Collection<FlightLoadPlanContainerVO> loadPlanVOs = new ArrayList();
		loadPlanVOs.add(flightLoadPlanContainerVO);
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
		doReturn(loadPlanVOs).when(dao).findPreviousLoadPlanVersionsForContainer(flightLoadPlanContainerVO);
		saveLoadPlanDetailsForMailPersistor.persist(flightLoadPlanContainerVO);
	}
	@Test
	public void updateLoadPlanStatus_throwFinderException() throws SystemException, PersistenceException, FinderException {
		FlightLoadPlanContainerVO flightLoadPlanContainerVO = new FlightLoadPlanContainerVO();
		flightLoadPlanContainerVO.setCompanyCode("IBS");
		flightLoadPlanContainerVO.setFlightNumber("AK456");
		flightLoadPlanContainerVO.setFlightSequenceNumber(12);
		flightLoadPlanContainerVO.setSegOrigin("CDG");
		flightLoadPlanContainerVO.setSegDestination("DXB");
		flightLoadPlanContainerVO.setLoadPlanVersion(1);
		flightLoadPlanContainerVO.setContainerType("ST");
		flightLoadPlanContainerVO.setUldReferenceNo(546);
		flightLoadPlanContainerVO.setSegSerialNumber(1);
		flightLoadPlanContainerVO.setPlannedPieces(3);
		flightLoadPlanContainerVO.setPlannedWeight(23.1);
		flightLoadPlanContainerVO.setPlanStatus("I");
		flightLoadPlanContainerVO.setUldNumber("6789");
		flightLoadPlanContainerVO.setOperationFlag("D");
		flightLoadPlanContainerEntity = new FlightLoadPlanContainer(flightLoadPlanContainerVO);
		doThrow(FinderException.class).when(PersistenceController.getEntityManager()).find(eq(FlightLoadPlanContainer.class),
				any(FlightLoadPlanContainerPK.class));
		Collection<FlightLoadPlanContainerVO> loadPlanVOs = new ArrayList();
		loadPlanVOs.add(flightLoadPlanContainerVO);
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
		doReturn(loadPlanVOs).when(dao).findPreviousLoadPlanVersionsForContainer(flightLoadPlanContainerVO);
		saveLoadPlanDetailsForMailPersistor.persist(flightLoadPlanContainerVO);
	}
	@Test
	public void persistLoadPlan_WithNoOperationFlag() throws SystemException, PersistenceException {
		FlightLoadPlanContainerVO flightLoadPlanContainerVO = new FlightLoadPlanContainerVO();
		flightLoadPlanContainerVO.setCompanyCode("IBS");
		flightLoadPlanContainerVO.setFlightNumber("AK456");
		flightLoadPlanContainerVO.setFlightSequenceNumber(12);
		flightLoadPlanContainerVO.setSegOrigin("CDG");
		flightLoadPlanContainerVO.setSegDestination("DXB");
		flightLoadPlanContainerVO.setLoadPlanVersion(1);
		flightLoadPlanContainerVO.setContainerType("ST");
		flightLoadPlanContainerVO.setUldReferenceNo(546);
		flightLoadPlanContainerVO.setSegSerialNumber(1);
		flightLoadPlanContainerVO.setPlannedPieces(3);
		flightLoadPlanContainerVO.setPlannedWeight(23.1);
		flightLoadPlanContainerVO.setPlanStatus("I");
		flightLoadPlanContainerVO.setUldNumber("6789");
		flightLoadPlanContainerEntity = new FlightLoadPlanContainer(flightLoadPlanContainerVO);
		Collection<FlightLoadPlanContainerVO> loadPlanVOs = new ArrayList();
		loadPlanVOs.add(flightLoadPlanContainerVO);
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
		doReturn(loadPlanVOs).when(dao).findPreviousLoadPlanVersionsForContainer(flightLoadPlanContainerVO);
		saveLoadPlanDetailsForMailPersistor.persist(flightLoadPlanContainerVO);
	}
	

	@Test
	public void persistLoadPanForInsert_WithPlanStatusDelete() throws SystemException, PersistenceException {
		FlightLoadPlanContainerVO flightLoadPlanContainerVO = new FlightLoadPlanContainerVO();
		flightLoadPlanContainerVO.setCompanyCode("IBS");
		flightLoadPlanContainerVO.setFlightNumber("AK456");
		flightLoadPlanContainerVO.setFlightSequenceNumber(12);
		flightLoadPlanContainerVO.setSegOrigin("CDG");
		flightLoadPlanContainerVO.setSegDestination("DXB");
		flightLoadPlanContainerVO.setLoadPlanVersion(1);
		flightLoadPlanContainerVO.setContainerType("ST");
		flightLoadPlanContainerVO.setUldReferenceNo(546);
		flightLoadPlanContainerVO.setSegSerialNumber(1);
		flightLoadPlanContainerVO.setPlannedPieces(3);
		flightLoadPlanContainerVO.setPlannedWeight(23.1);
		flightLoadPlanContainerVO.setPlanStatus("I");
		flightLoadPlanContainerVO.setUldNumber("6789");
		flightLoadPlanContainerVO.setOperationFlag("I");
		flightLoadPlanContainerVO.setPlanStatus("D");
		flightLoadPlanContainerEntity = new FlightLoadPlanContainer(flightLoadPlanContainerVO);
		Collection<FlightLoadPlanContainerVO> loadPlanVOs = new ArrayList();
		loadPlanVOs.add(flightLoadPlanContainerVO);
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
		doReturn(loadPlanVOs).when(dao).findPreviousLoadPlanVersionsForContainer(flightLoadPlanContainerVO);
		saveLoadPlanDetailsForMailPersistor.persist(flightLoadPlanContainerVO); 
	}
}
