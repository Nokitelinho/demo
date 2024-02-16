package com.ibsplc.icargo.business.mail.operations.feature.saveactualweightcontainer.persistors;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;

import static org.mockito.Mockito.doThrow;
import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;

import org.junit.Test;

import com.ibsplc.icargo.business.mail.operations.Container;
import com.ibsplc.icargo.business.mail.operations.ContainerPK;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerAssignmentVO;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.framework.feature.AbstractFeatureTest;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.icargo.framework.util.unit.UnitConstants;
import com.ibsplc.icargo.persistence.dao.mail.operations.MailTrackingDefaultsDAO;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.EntityManagerMock;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;

public class SaveActualWeighInContainerPersistorTest extends AbstractFeatureTest{
	private SaveActualWeighInContainerPersistor saveActualWeighInContainerPersistor;
	private MailTrackingDefaultsDAO dao;
	private static final String MAIL_OPERATIONS = "mail.operations";
	@Override
	public void setup() throws Exception {
		EntityManagerMock.mockEntityManager();
		dao = mock(MailTrackingDefaultsDAO.class);
		saveActualWeighInContainerPersistor=spy(new SaveActualWeighInContainerPersistor());			
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO("mail.operations");
	}
	
	@Test
	public void shouldNotUpdateActualWeightForMailContainerWhenActualWeightIsNull() throws Exception {
		ContainerVO containerVO = new ContainerVO();
		containerVO.setContainerNumber("AKE12032QF");
		saveActualWeighInContainerPersistor.persist(containerVO);
	}

	@Test
	public void shouldNotUpdateActualWeightForMailContainerWhenContainerAssignmentNotFound() throws Exception {
		ContainerVO containerVO = new ContainerVO();
		containerVO.setContainerNumber("AKE12032QF");
		containerVO.setActualWeight(new Measure(UnitConstants.WEIGHT, 10));
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
		doReturn(null).when(dao).findLatestContainerAssignment(any(), any());
		saveActualWeighInContainerPersistor.persist(containerVO);
	}

	@Test
	public void shouldNotUpdateActualWeightForMailContainerWhenTransitFlagIsNo() throws SystemException, PersistenceException {
		ContainerVO containerVO = new ContainerVO();
		containerVO.setContainerNumber("AKE12032QF");
		containerVO.setActualWeight(new Measure(UnitConstants.WEIGHT, 10));
		containerVO.setTransitFlag("Y");;
		ContainerAssignmentVO containerAssignmentVO = new ContainerAssignmentVO();
		containerAssignmentVO.setTransitFlag(ContainerAssignmentVO.FLAG_NO);
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
		doReturn(containerAssignmentVO).when(dao).findLatestContainerAssignment(any(), any());
		saveActualWeighInContainerPersistor.persist(containerVO);
	}

	@Test
	public void shouldNotUpdateActualWeightForMailContainerWhenContainerTypeIsBULK() throws SystemException, PersistenceException {
		ContainerVO containerVO = new ContainerVO();
		containerVO.setContainerNumber("AKE12032QF");
		containerVO.setActualWeight(new Measure(UnitConstants.WEIGHT, 10));
		ContainerAssignmentVO containerAssignmentVO = new ContainerAssignmentVO();
		containerAssignmentVO.setTransitFlag(ContainerAssignmentVO.FLAG_YES);
		containerAssignmentVO.setContainerType(MailConstantsVO.BULK_TYPE);
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
		doReturn(containerAssignmentVO).when(dao).findLatestContainerAssignment(any(), any());
		saveActualWeighInContainerPersistor.persist(containerVO);
	}

	@Test
	public void shouldNotUpdateActualWeightForMailContainerWhenContainerIsNotFound() throws SystemException, PersistenceException, FinderException {
		ContainerVO containerVO = new ContainerVO();
		containerVO.setContainerNumber("AKE12032QF");
		containerVO.setActualWeight(new Measure(UnitConstants.WEIGHT, 10));
		ContainerAssignmentVO containerAssignmentVO = new ContainerAssignmentVO();
		containerAssignmentVO.setTransitFlag(ContainerAssignmentVO.FLAG_YES);
		containerAssignmentVO.setContainerType(MailConstantsVO.ULD_TYPE);
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
		doReturn(containerAssignmentVO).when(dao).findLatestContainerAssignment(any(), any());
		doReturn(null).when(PersistenceController.getEntityManager()).find(eq(Container.class), any(ContainerPK.class));
		saveActualWeighInContainerPersistor.persist(containerVO);
	}
	@Test()
	public void shouldNotUpdateActualWeightForMailContainerWhenContainerIsNotFoundAndThrowFinderException() throws SystemException, PersistenceException, FinderException{
		ContainerVO containerVO = new ContainerVO();
		containerVO.setContainerNumber("AKE12032QF");
		containerVO.setActualWeight(new Measure(UnitConstants.WEIGHT, 10));
		ContainerAssignmentVO containerAssignmentVO = new ContainerAssignmentVO();
		containerAssignmentVO.setTransitFlag(ContainerAssignmentVO.FLAG_YES);
		containerAssignmentVO.setContainerType(MailConstantsVO.ULD_TYPE);
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
		doReturn(containerAssignmentVO).when(dao).findLatestContainerAssignment(any(), any());
		doThrow(FinderException.class).when(PersistenceController.getEntityManager()).find(eq(Container.class), any(ContainerPK.class));
		saveActualWeighInContainerPersistor.persist(containerVO);
	}	

	@Test
	public void shouldUpdateActualWeightForMailContainer() throws SystemException, PersistenceException, FinderException{
		ContainerVO containerVO = new ContainerVO();
		containerVO.setContainerNumber("AKE12032QF");
		containerVO.setActualWeight(new Measure(UnitConstants.WEIGHT, 10));
		ContainerAssignmentVO containerAssignmentVO = new ContainerAssignmentVO();
		containerAssignmentVO.setTransitFlag(ContainerAssignmentVO.FLAG_YES);
		containerAssignmentVO.setContainerType(MailConstantsVO.ULD_TYPE);
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
		doReturn(containerAssignmentVO).when(dao).findLatestContainerAssignment(any(), any());
		ContainerPK containerPK = new ContainerPK();
		containerPK.setCompanyCode(getCompanyCode());
		containerPK.setCarrierId(1132);
		containerPK.setAssignmentPort("SYD");
		containerPK.setContainerNumber("AKE12032QF");
		containerPK.setFlightNumber("-1");
		containerPK.setFlightSequenceNumber(-1);
		containerPK.setLegSerialNumber(-1);
		Container container = new Container();
		container.setContainerPK(containerPK);
		doReturn(containerAssignmentVO).when(dao).findLatestContainerAssignment(any(), any());
		doReturn(container).when(PersistenceController.getEntityManager()).find(eq(Container.class),
				any(ContainerPK.class));
		saveActualWeighInContainerPersistor.persist(containerVO);
	}

}
