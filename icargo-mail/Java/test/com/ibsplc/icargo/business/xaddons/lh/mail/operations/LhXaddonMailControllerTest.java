package com.ibsplc.icargo.business.xaddons.lh.mail.operations;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.Collection;

import org.junit.Test;
import org.mockito.Mockito;

import com.ibsplc.icargo.business.mail.operations.vo.DespatchDetailsVO;
import com.ibsplc.icargo.business.xaddons.lh.mail.operations.feature.savehbadetails.SaveHBADetailsFeature;
import com.ibsplc.icargo.business.xaddons.lh.mail.operations.feature.savehbadetails.SaveHBADetailsFeatureConstants;
import com.ibsplc.icargo.business.xaddons.lh.mail.operations.proxy.MailOperationsProxy;
import com.ibsplc.icargo.business.xaddons.lh.mail.operations.vo.HbaMarkingVO;
import com.ibsplc.icargo.framework.feature.AbstractFeatureTest;
import com.ibsplc.icargo.persistence.dao.xaddons.lh.mail.operations.LhXaddonsMailOperationsDAO;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.EntityManagerMock;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;

public class LhXaddonMailControllerTest extends AbstractFeatureTest {
	private LhXaddonMailController lhXaddonMailController;
	private SaveHBADetailsFeature saveHBADetailsFeature;
	private HbaMarkingVO hbaMarkingVO;
	private MailOperationsProxy mailOperationsProxy;
	private LhXaddonsMailOperationsDAO lhXaddonsMailOperationsDAO;

	@Override
	public void setup() throws Exception {
		EntityManagerMock.mockEntityManager();
		lhXaddonMailController = spy(LhXaddonMailController.class);
		saveHBADetailsFeature = mockBean(SaveHBADetailsFeatureConstants.SAVE_HBA_DETAILS_FEATURE,
				SaveHBADetailsFeature.class);
		mailOperationsProxy = mockProxy(MailOperationsProxy.class);
		lhXaddonsMailOperationsDAO = Mockito.mock(LhXaddonsMailOperationsDAO.class);
		EntityManagerMock.mockEntityManager();
	}

	private HbaMarkingVO setUpHbaMarkingVO() {
		HbaMarkingVO hbaMarkingVO = new HbaMarkingVO();
		hbaMarkingVO.setCompanyCode("LH");
		hbaMarkingVO.setUldRefNo(1000);
		hbaMarkingVO.setHbaPosition("LD");
		hbaMarkingVO.setHbaType("P");
		hbaMarkingVO.setOperationFlag("I");
		return hbaMarkingVO;
	}

	@Test
	public void testMarkHba() throws Exception {
		hbaMarkingVO = setUpHbaMarkingVO();
		lhXaddonMailController.markHba(hbaMarkingVO);
		doNothing().when(mailOperationsProxy).updateIntFlgAsNForMailBagsInConatiner(any(HbaMarkingVO.class));
		verify(saveHBADetailsFeature, times(1)).execute(any(HbaMarkingVO.class));
	}

	@Test
	public void testFindHbaDetails() throws Exception {
		hbaMarkingVO = setUpHbaMarkingVO();
		ContainerHBADetails containerHbaDetailsReturned = new ContainerHBADetails();
		doReturn(containerHbaDetailsReturned).when(PersistenceController.getEntityManager())
				.find(eq(ContainerHBADetails.class), any(ContainerHBADetailsPK.class));
		lhXaddonMailController.findHbaDetails(hbaMarkingVO);
	}

	@Test
	public void testFindHbaDetailsThrowsException() throws Exception {
		hbaMarkingVO = setUpHbaMarkingVO();
		doThrow(FinderException.class).when(PersistenceController.getEntityManager())
				.find(eq(ContainerHBADetails.class), any(ContainerHBADetailsPK.class));
		assertEquals(hbaMarkingVO, lhXaddonMailController.findHbaDetails(hbaMarkingVO));
	}

	@Test
	public void findDespatchDetailsTest() throws Exception {
		Collection<DespatchDetailsVO> returnDespatchDetails = null;
		doReturn(lhXaddonsMailOperationsDAO).when(PersistenceController.getEntityManager())
				.getQueryDAO(any(String.class));
		doReturn(returnDespatchDetails).when(lhXaddonsMailOperationsDAO).findDespatchDetails(any());
		lhXaddonMailController.findDespatchDetails(null);

	}
	@Test(expected = SystemException.class)
	public void findDespatchDetailsExceptionTest() throws Exception {
		
		doThrow(PersistenceException.class).when(PersistenceController.getEntityManager())
				.getQueryDAO(any(String.class));
		lhXaddonMailController.findDespatchDetails(null);

	}

}
