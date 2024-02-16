package com.ibsplc.icargo.business.mail.operations.feature.savesendresditmessagedetails.persistors;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyCollectionOf;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import org.junit.Test;

import com.ibsplc.icargo.business.mail.operations.MailResditAddress;
import com.ibsplc.icargo.business.mail.operations.feature.savesendresditmessagedetails.SaveSendResditMessageDetailsFeatureConstants;
import com.ibsplc.icargo.business.mail.operations.proxy.SharedDefaultsProxy;
import com.ibsplc.icargo.business.mail.operations.vo.MailResditAddressVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailResditMessageDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagHistoryVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.framework.feature.AbstractFeatureTest;
import com.ibsplc.icargo.persistence.dao.mail.operations.MailTrackingDefaultsDAO;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.CreateException;
import com.ibsplc.xibase.server.framework.persistence.EntityManagerMock;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.keygen.provider.Criterion;
import com.ibsplc.xibase.server.framework.util.keygen.KeyUtilInstance;
import com.ibsplc.xibase.server.framework.util.keygen.KeyUtilInstanceMock;

public class SendResditMessageDetailsPersistorTest extends AbstractFeatureTest{
	
	private SendResditMessageDetailsPersistor sendResditMessageDetailsPersistor;
	private MailResditMessageDetailsVO mailResditMessageDetailsVO;
	private MailTrackingDefaultsDAO dao;
	private SharedDefaultsProxy sharedDefaultsProxy;
	private KeyUtilInstance keyUtils;

	@Override
	public void setup() throws Exception {
		EntityManagerMock.mockEntityManager();
		sendResditMessageDetailsPersistor=spy(new SendResditMessageDetailsPersistor());	
		mailResditMessageDetailsVO = setUpMailResditMessageDetailsVO();
		dao = mock(MailTrackingDefaultsDAO.class);
		sharedDefaultsProxy = mockProxy(SharedDefaultsProxy.class);
		KeyUtilInstanceMock.mockKeyUtilInstance();
		keyUtils = KeyUtilInstance.getInstance();
		
	}
	
	private MailResditMessageDetailsVO setUpMailResditMessageDetailsVO() {
		MailResditMessageDetailsVO mailResditMessageDetailsVO = new MailResditMessageDetailsVO();
		
		Collection<MailResditAddressVO> mailResditAddressVOs = new ArrayList<>();
		MailResditAddressVO mailResditAddressVO = new MailResditAddressVO();
		mailResditAddressVO.setCompanyCode("AV");
		mailResditAddressVO.setInterfaceSystem("ICS");
		mailResditAddressVO.setMode("L");
		mailResditAddressVO.setAddress("Test");
		mailResditAddressVO.setEnvelopeCode("ICOXML");
		mailResditAddressVO.setEnvelopeAddress("ABCD~P");
		mailResditAddressVO.setInterfaceSystem("ICS");
		mailResditAddressVO.setParticipantName("HQMAIL");
		mailResditAddressVO.setParticipantType("AGENT");
		mailResditAddressVO.setAirportCode("FRA");
		mailResditAddressVO.setCountryCode("DE");
		mailResditAddressVOs.add(mailResditAddressVO);

		Collection<MailbagVO> selectedMailbags = new ArrayList<>();
		MailbagVO mailbagVO = new MailbagVO();
		mailbagVO.setCompanyCode("AV");
		mailbagVO.setPaCode("FR001");
		mailbagVO.setSequenceNumber(14772);
		mailbagVO.setUpliftAirport("CDG");
		mailbagVO.setMailbagId("FRCDGADEFRAAAUA23270160190100");
		mailbagVO.setCarrierId(1134);
		mailbagVO.setFlightNumber("6688");
		mailbagVO.setFlightSequenceNumber(16);
		mailbagVO.setContainerNumber("AKE34334AV");
		mailbagVO.setMailboxId("FR001");
		mailbagVO.setLastUpdateUser("AV");
		mailbagVO.setMalseqnum(14772);
		
		Collection<MailbagHistoryVO> mailbagHistories =new ArrayList<>();
		MailbagHistoryVO mailbagHistory=new MailbagHistoryVO();
		mailbagHistory.setAdditionalInfo("true");
		mailbagHistory.setEventCode("48");
		mailbagHistories.add(mailbagHistory);
		mailbagVO.setMailbagHistories(mailbagHistories);
		
		selectedMailbags.add(mailbagVO);
		
		List<String> selectedResdits = new ArrayList<>();
		mailResditMessageDetailsVO.setMailbagVO(selectedMailbags);
		mailResditMessageDetailsVO.setSelectedResdits(selectedResdits);
		mailResditMessageDetailsVO.setMailResditAddressVO(mailResditAddressVOs);
		return mailResditMessageDetailsVO;
	}

	@Test()
	public void saveSendResditMessageDetails() throws Exception {
		HashMap<String, String> systemParameterMap = null;
		doReturn(systemParameterMap).when(sharedDefaultsProxy).findSystemParameterByCodes(anyCollectionOf(String.class));
		doReturn("1").when(keyUtils).getKey(any(Criterion.class));
		doReturn(dao).when(PersistenceController.getEntityManager())
		.getQueryDAO(SaveSendResditMessageDetailsFeatureConstants.MAIL_OPERATIONS);
		sendResditMessageDetailsPersistor.persist(mailResditMessageDetailsVO);
	}
	
	@Test(expected = SystemException.class)
	public void saveSendResditMessageDetailsThrowsSystemException() throws Exception {
		mailResditMessageDetailsVO = setUpMailResditMessageDetailsVO();
		doThrow(CreateException.class).when(PersistenceController.getEntityManager())
				.persist(any(MailResditAddress.class));
		sendResditMessageDetailsPersistor.persist(mailResditMessageDetailsVO);

	}
	
	@Test()
	public void saveSendResditMessageDetailsResditEventsIsNull() throws Exception {
		mailResditMessageDetailsVO.setSelectedResdits(new ArrayList<>());
		HashMap<String, String> systemParameterMap = null;
		doReturn(systemParameterMap).when(sharedDefaultsProxy).findSystemParameterByCodes(anyCollectionOf(String.class));
		doReturn("1").when(keyUtils).getKey(any(Criterion.class));
		doReturn(dao).when(PersistenceController.getEntityManager())
		.getQueryDAO(SaveSendResditMessageDetailsFeatureConstants.MAIL_OPERATIONS);
		sendResditMessageDetailsPersistor.persist(mailResditMessageDetailsVO);
	}
	
	@Test()
	public void saveSendResditMessageDetailsResditEventsNotEmpty() throws Exception {
		List<String> selectedResdits = new ArrayList<>();
		selectedResdits.add("74");
		mailResditMessageDetailsVO.setSelectedResdits(selectedResdits);
		HashMap<String, String> systemParameterMap = null;
		doReturn(systemParameterMap).when(sharedDefaultsProxy).findSystemParameterByCodes(anyCollectionOf(String.class));
		doReturn("1").when(keyUtils).getKey(any(Criterion.class));
		doReturn(dao).when(PersistenceController.getEntityManager())
		.getQueryDAO(SaveSendResditMessageDetailsFeatureConstants.MAIL_OPERATIONS);
		sendResditMessageDetailsPersistor.persist(mailResditMessageDetailsVO);
	}
	
	@Test()
	public void saveSendResditMessageDetailsAddInfFalse() throws Exception {
		mailResditMessageDetailsVO.getMailbagVO().forEach(mail->{
			mail.getMailbagHistories().forEach(his->{
				his.setAdditionalInfo("false");
				his.setEventCode(null);
			});
		});
		HashMap<String, String> systemParameterMap = null;
		doReturn(systemParameterMap).when(sharedDefaultsProxy).findSystemParameterByCodes(anyCollectionOf(String.class));
		doReturn("1").when(keyUtils).getKey(any(Criterion.class));
		doReturn(dao).when(PersistenceController.getEntityManager())
		.getQueryDAO(SaveSendResditMessageDetailsFeatureConstants.MAIL_OPERATIONS);
		sendResditMessageDetailsPersistor.persist(mailResditMessageDetailsVO);
	}
	@Test()
	public void saveSendResditMessageDetailsResditEventCdIsNull() throws Exception {
		mailResditMessageDetailsVO.getMailbagVO().forEach(mail->{
			mail.getMailbagHistories().forEach(his->{
				his.setAdditionalInfo("true");
				his.setEventCode(null);
			});
		});
		HashMap<String, String> systemParameterMap = null;
		doReturn(systemParameterMap).when(sharedDefaultsProxy).findSystemParameterByCodes(anyCollectionOf(String.class));
		doReturn("1").when(keyUtils).getKey(any(Criterion.class));
		doReturn(dao).when(PersistenceController.getEntityManager())
		.getQueryDAO(SaveSendResditMessageDetailsFeatureConstants.MAIL_OPERATIONS);
		sendResditMessageDetailsPersistor.persist(mailResditMessageDetailsVO);
	}
	@Test()
	public void saveSendResditMessageDetailsMailBagHistoriesAreEmpty() throws Exception {
		mailResditMessageDetailsVO.getMailbagVO().forEach(mail->{
			mail.setMailbagHistories(new ArrayList<>());
		});
		HashMap<String, String> systemParameterMap = null;
		doReturn(systemParameterMap).when(sharedDefaultsProxy).findSystemParameterByCodes(anyCollectionOf(String.class));
		doReturn("1").when(keyUtils).getKey(any(Criterion.class));
		doReturn(dao).when(PersistenceController.getEntityManager())
		.getQueryDAO(SaveSendResditMessageDetailsFeatureConstants.MAIL_OPERATIONS);
		sendResditMessageDetailsPersistor.persist(mailResditMessageDetailsVO);
	}
	@Test()
	public void saveSendResditMessageDetailsMailBagHistoriesAreNull() throws Exception {
		mailResditMessageDetailsVO.getMailbagVO().forEach(mail->{
			mail.setMailbagHistories(null);
		});
		HashMap<String, String> systemParameterMap = null;
		doReturn(systemParameterMap).when(sharedDefaultsProxy).findSystemParameterByCodes(anyCollectionOf(String.class));
		doReturn("1").when(keyUtils).getKey(any(Criterion.class));
		doReturn(dao).when(PersistenceController.getEntityManager())
		.getQueryDAO(SaveSendResditMessageDetailsFeatureConstants.MAIL_OPERATIONS);
		sendResditMessageDetailsPersistor.persist(mailResditMessageDetailsVO);
	}
}
