/**
 *	Java file	: 	com.ibsplc.icargo.business.mail.operations.feature.saveSendResditMessageDetails.SaveSendResditMessageDetailsFeatureTest.java
 *
 *	Created by	:	A-10647
 *	Created on	:	22-Mar-2022
 *
 *  Copyright 2022 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 *
 * 	This software is the proprietary information of Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
 * 	Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.operations.feature.savesendresditmessagedetails;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyCollectionOf;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doNothing;
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
import com.ibsplc.icargo.business.mail.operations.MailResditAddressPK;
import com.ibsplc.icargo.business.mail.operations.feature.savesendresditmessagedetails.persistors.SendResditMessageDetailsPersistor;
import com.ibsplc.icargo.business.mail.operations.proxy.SharedDefaultsProxy;
import com.ibsplc.icargo.business.mail.operations.vo.MailResditAddressVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailResditMessageDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagHistoryVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.framework.feature.AbstractFeatureTest;
import com.ibsplc.icargo.framework.floworchestration.context.ICargoSproutAdapter;
import com.ibsplc.icargo.persistence.dao.mail.operations.MailTrackingDefaultsDAO;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.CreateException;
import com.ibsplc.xibase.server.framework.persistence.EntityManagerMock;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.keygen.provider.Criterion;
import com.ibsplc.xibase.server.framework.util.keygen.KeyUtilInstance;
import com.ibsplc.xibase.server.framework.util.keygen.KeyUtilInstanceMock;

/**
 * Java file :
 * com.ibsplc.icargo.business.mail.operations.feature.saveSendResditMessageDetails.SaveSendResditMessageDetailsFeatureTest.java
 * Version : Name : Date : Updation
 * --------------------------------------------------- 0.1 : A-10647 :
 * 22-Mar-2022 : Draft
 */
public class SaveSendResditMessageDetailsFeatureTest extends AbstractFeatureTest {
	private SaveSendResditMessageDetailsFeature spy;
	private MailResditMessageDetailsVO mailResditMessageDetailsVO;
	private SendResditMessageDetailsPersistor sendResditMessageDetailsPersistor;
	private MailTrackingDefaultsDAO dao;
	private SharedDefaultsProxy sharedDefaultsProxy;
	private KeyUtilInstance keyUtils;

	/**
	 * Overriding Method : @see
	 * com.ibsplc.icargo.framework.feature.AbstractFeatureTest#setup() Added by :
	 * A-10647 on 22-Mar-2022 Used for : Parameters : @throws Exception
	 */
	@Override
	public void setup() throws Exception {
		spy = spy((SaveSendResditMessageDetailsFeature) ICargoSproutAdapter
				.getBean("mail.operations.savesendresditmessagedetailsfeature"));
		mailResditMessageDetailsVO = setUpMailResditMessageDetailsVO();
		EntityManagerMock.mockEntityManager();
		sendResditMessageDetailsPersistor = mockBean(
				SaveSendResditMessageDetailsFeatureConstants.SEND_RESDIT_MESSAGE_DETAILS_PERSISTOR,
				SendResditMessageDetailsPersistor.class);
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
	public void saveSendResditMessageDetails_WhenInvoked() throws Exception {
		mailResditMessageDetailsVO = setUpMailResditMessageDetailsVO();
		doNothing().when(sendResditMessageDetailsPersistor).persist(mailResditMessageDetailsVO);
		HashMap<String, String> systemParameterMap = null;
		doReturn(systemParameterMap).when(sharedDefaultsProxy).findSystemParameterByCodes(anyCollectionOf(String.class));
		doReturn("1").when(keyUtils).getKey(any(Criterion.class));
		doReturn(dao).when(PersistenceController.getEntityManager())
		.getQueryDAO(SaveSendResditMessageDetailsFeatureConstants.MAIL_OPERATIONS);
		spy.execute(mailResditMessageDetailsVO);
	}

	@Test()
	public void saveSendResditMessageDetails_WhenInvoked_Exception() throws Exception {
		mailResditMessageDetailsVO = setUpMailResditMessageDetailsVO();
		doThrow(FinderException.class).when(PersistenceController.getEntityManager()).find(eq(MailResditAddress.class),
				any(MailResditAddressPK.class));
		doReturn("1").when(keyUtils).getKey(any(Criterion.class));
		doReturn(dao).when(PersistenceController.getEntityManager())
		.getQueryDAO(SaveSendResditMessageDetailsFeatureConstants.MAIL_OPERATIONS);
		spy.execute(mailResditMessageDetailsVO);

	}

	@Test(expected = SystemException.class)
	public void saveSendResditMessageDetailsThrowsSystemException() throws Exception {
		mailResditMessageDetailsVO = setUpMailResditMessageDetailsVO();
		doThrow(CreateException.class).when(PersistenceController.getEntityManager())
				.persist(any(MailResditAddress.class));
		spy.execute(mailResditMessageDetailsVO);

	}
}
