package com.ibsplc.icargo.business.mail.operations;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyCollectionOf;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.doNothing;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import org.junit.Test;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.RateAuditVO;
import com.ibsplc.icargo.business.mail.operations.proxy.MailOperationsMRAProxy;
import com.ibsplc.icargo.business.mail.operations.proxy.SharedDefaultsProxy;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailAcceptanceVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.framework.feature.AbstractFeatureTest;
import com.ibsplc.icargo.framework.proxy.ProxyException;
import com.ibsplc.icargo.persistence.dao.mail.operations.MailTrackingDefaultsDAO;
import com.ibsplc.xibase.server.framework.exceptions.BusinessException;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.EntityManagerMock;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;

public class MailAcceptanceTest extends AbstractFeatureTest {

	private static final String MAIL_OPERATIONS = "mail.operations";
	private static final String COMPANY_CODE = "IBS";

	private MailAcceptance spy;
	private MailTrackingDefaultsDAO dao;
	private MailAcceptance mock;
	private Container containerEntity;
	private SharedDefaultsProxy sharedDefaultsProxy;
	private MailOperationsMRAProxy mailOperationsMRAProxy;
	@Override
	public void setup() throws Exception {
		EntityManagerMock.mockEntityManager();
		spy = spy(new MailAcceptance());
		dao = mock(MailTrackingDefaultsDAO.class);
		mock = mock(MailAcceptance.class);
		containerEntity = new Container();
		sharedDefaultsProxy = mockProxy(SharedDefaultsProxy.class);
		mailOperationsMRAProxy = mockProxy(MailOperationsMRAProxy.class);
	}

	@Test
	public void getMailBags_Success_Test() throws SystemException, BusinessException, PersistenceException {

		MailAcceptanceVO mailAcceptanceVO = new MailAcceptanceVO();
		Collection<MailbagVO> mailbagVOs = new ArrayList<>();
		;
		Collection<ContainerDetailsVO> containerDetailsVOs = new ArrayList<>();
		ContainerDetailsVO containerDetailsVO = new ContainerDetailsVO();
		MailbagVO mailbagVO = new MailbagVO();
		mailbagVO.setOperationalFlag("I");
		mailbagVO.setPaCode("US101");
		mailbagVOs.add(mailbagVO);
		mailAcceptanceVO.setCompanyCode(COMPANY_CODE);
		mailAcceptanceVO.setShowWarning("Y");
		containerDetailsVO.setDestination("FRA");
		containerDetailsVO.setMailDetails(mailbagVOs);
		containerDetailsVOs.add(containerDetailsVO);
		mailAcceptanceVO.setCarrierId(1234);
		mailAcceptanceVO.setFlightNumber("9898");
		mailAcceptanceVO.setFlightCarrierCode("AV");
		String scanWavedAirport = "CDG";
		mailAcceptanceVO.setMailSource("MTK060");
		mailAcceptanceVO.setMailDataSource("MTK060");
		mailAcceptanceVO.setMessageVersion("1");
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);

		doReturn(scanWavedAirport).when(dao).checkScanningWavedDest(any(MailbagVO.class));

		spy.getMailBags(mailAcceptanceVO, containerDetailsVOs);
	}
	
	
		@Test
	public void getMailBags_Success_Without_ShowWarningValue_Test() throws SystemException, BusinessException, PersistenceException {

		MailAcceptanceVO mailAcceptanceVO = new MailAcceptanceVO();
		Collection<MailbagVO> mailbagVOs = new ArrayList<>();
		;
		Collection<ContainerDetailsVO> containerDetailsVOs = new ArrayList<>();
		ContainerDetailsVO containerDetailsVO = new ContainerDetailsVO();
		MailbagVO mailbagVO = new MailbagVO();
		mailbagVO.setOperationalFlag("I");
		mailbagVO.setPaCode("US101");
		mailbagVOs.add(mailbagVO);
		mailAcceptanceVO.setCompanyCode(COMPANY_CODE);
		containerDetailsVO.setDestination("FRA");
		containerDetailsVO.setMailDetails(mailbagVOs);
		containerDetailsVOs.add(containerDetailsVO);
		mailAcceptanceVO.setCarrierId(1234);
		mailAcceptanceVO.setFlightNumber("9898");
		mailAcceptanceVO.setFlightCarrierCode("AV");
		String scanWavedAirport = "CDG";
		mailAcceptanceVO.setMailSource("MTK060");
		mailAcceptanceVO.setMailDataSource("MTK060");
		mailAcceptanceVO.setMessageVersion("1");
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);

		doReturn(scanWavedAirport).when(dao).checkScanningWavedDest(any(MailbagVO.class));

		spy.getMailBags(mailAcceptanceVO, containerDetailsVOs);
	}
		@Test
		public void saveAcceptanceDetails_sucess() throws SystemException, FlightClosedException, DuplicateMailBagsException, PersistenceException, FinderException, ProxyException {
			MailAcceptanceVO mailAcceptanceVO = new MailAcceptanceVO();
			Collection<ContainerDetailsVO> containerDetailsVOs = new ArrayList<>();
			ContainerDetailsVO containerDetailsVO = new ContainerDetailsVO();
			Collection<MailbagVO> mailbagVOs = new ArrayList<>();
			MailbagVO mailbagVO = new MailbagVO();
			mailbagVO.setOperationalFlag("I");
			mailbagVO.setPaCode("US101");
			mailbagVOs.add(mailbagVO);
			containerDetailsVO.setDestination("FRA");
			containerDetailsVO.setMailDetails(mailbagVOs);
			containerDetailsVO.setOperationFlag("I");
			containerDetailsVOs.add(containerDetailsVO);
			mailAcceptanceVO.setFlightNumber("1000");
			mailAcceptanceVO.setFlightSequenceNumber(-1);
			mailAcceptanceVO.setCompanyCode(COMPANY_CODE);
			mailAcceptanceVO.setFlightStatus("dep");
			mailAcceptanceVO.setContainerDetails(containerDetailsVOs);
			doReturn(mailbagVOs).when(mock).getMailBags(mailAcceptanceVO, containerDetailsVOs);
			String scanWavedAirport = "CDG";
			doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
			doReturn(scanWavedAirport).when(dao).checkScanningWavedDest(any(MailbagVO.class));
			ContainerPK ContainerPK = new ContainerPK();
			containerEntity.setContainerPK(ContainerPK);
			doReturn(containerEntity).when(PersistenceController.getEntityManager()).find(eq(Container.class), any(ContainerPK.class));
			HashMap<String, String> systemParameterMap = new HashMap();
			systemParameterMap.put("mail.operations.mldsendingrequired", "N");
			systemParameterMap.put("mailtracking.defaults.importsmailstomra", "Y");
			doReturn(systemParameterMap).when(sharedDefaultsProxy).findSystemParameterByCodes(anyCollectionOf(String.class));
			doNothing().when(mailOperationsMRAProxy).importMailProvisionalRateData(anyCollectionOf(RateAuditVO.class));
			spy.saveAcceptanceDetails(mailAcceptanceVO, mailbagVOs);
		}
		@Test(expected=SystemException.class)
		public void saveAcceptanceDetails_throwsSystemException() throws SystemException, FlightClosedException, DuplicateMailBagsException, PersistenceException, FinderException, ProxyException {
			MailAcceptanceVO mailAcceptanceVO = new MailAcceptanceVO();
			Collection<ContainerDetailsVO> containerDetailsVOs = new ArrayList<>();
			ContainerDetailsVO containerDetailsVO = new ContainerDetailsVO();
			Collection<MailbagVO> mailbagVOs = new ArrayList<>();
			MailbagVO mailbagVO = new MailbagVO();
			mailbagVO.setOperationalFlag("I");
			mailbagVO.setPaCode("US101");
			mailbagVOs.add(mailbagVO);
			containerDetailsVO.setDestination("FRA");
			containerDetailsVO.setMailDetails(mailbagVOs);
			containerDetailsVO.setOperationFlag("I");
			containerDetailsVOs.add(containerDetailsVO);
			mailAcceptanceVO.setFlightNumber("1000");
			mailAcceptanceVO.setFlightSequenceNumber(-1);
			mailAcceptanceVO.setCompanyCode(COMPANY_CODE);
			mailAcceptanceVO.setFlightStatus("dep");
			mailAcceptanceVO.setContainerDetails(containerDetailsVOs);
			doReturn(mailbagVOs).when(mock).getMailBags(mailAcceptanceVO, containerDetailsVOs);
			String scanWavedAirport = "CDG";
			doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
			doReturn(scanWavedAirport).when(dao).checkScanningWavedDest(any(MailbagVO.class));
			ContainerPK ContainerPK = new ContainerPK();
			containerEntity.setContainerPK(ContainerPK);
			doReturn(containerEntity).when(PersistenceController.getEntityManager()).find(eq(Container.class), any(ContainerPK.class));
			HashMap<String, String> systemParameterMap = new HashMap();
			systemParameterMap.put("mail.operations.mldsendingrequired", "N");
			systemParameterMap.put("mailtracking.defaults.importsmailstomra", "Y");
			doReturn(systemParameterMap).when(sharedDefaultsProxy).findSystemParameterByCodes(anyCollectionOf(String.class));
			SystemException systemException = new SystemException("");
			doThrow(ProxyException.class).when(mailOperationsMRAProxy).importMailProvisionalRateData(anyCollectionOf(RateAuditVO.class));
			spy.saveAcceptanceDetails(mailAcceptanceVO, mailbagVOs);
	}
}

