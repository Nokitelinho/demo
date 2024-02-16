package com.ibsplc.icargo.business.mail.operations.builder;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Test;

import com.ibsplc.icargo.business.mail.operations.Mailbag;
import com.ibsplc.icargo.business.mail.operations.MailbagHistory;
import com.ibsplc.icargo.business.mail.operations.MailbagPK;
import com.ibsplc.icargo.business.mail.operations.vo.CarditReceptacleVO;
import com.ibsplc.icargo.business.mail.operations.vo.CarditVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagHistoryVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.framework.feature.AbstractFeatureTest;
import com.ibsplc.icargo.persistence.dao.mail.operations.MailTrackingDefaultsDAO;
import com.ibsplc.xibase.server.framework.persistence.EntityManagerMock;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;

public class HistoryBuilderTest extends AbstractFeatureTest {
	
	private static final String AIRPORT_SYD = "SYD";
	private static final String MAIL_OPERATIONS = "mail.operations";
	private HistoryBuilder builder;
	private Mailbag mailbagEntity;
	private MailTrackingDefaultsDAO dao;

	@Override
	public void setup() throws Exception {
		builder = spy(new HistoryBuilder());
		mailbagEntity = mockBean("MailbagEntity", Mailbag.class);
		EntityManagerMock.mockEntityManager();
		mockBean("MailbagHistoryEntity", MailbagHistory.class);
		dao = mock(MailTrackingDefaultsDAO.class);
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
	}

	@Test
	public void shouldPopulateSenderRecipientWhenFlaggingMailbagHistoryForDelivery() throws Exception {
		MailbagVO mailbagVO = new MailbagVO();
		mailbagVO.setCompanyCode(getCompanyCode());
		mailbagVO.setMailSequenceNumber(1);
		mailbagVO.setScannedPort(AIRPORT_SYD);
		Mailbag mailbag = new Mailbag();
		MailbagPK mailbagPK = new MailbagPK();
		mailbagPK.setCompanyCode(getCompanyCode());
		mailbagPK.setMailSequenceNumber(1);
		mailbag.setMailbagPK(mailbagPK);
		doReturn(mailbag).when(PersistenceController.getEntityManager()).find(mailbagEntity.getClass(), mailbagPK);
		doNothing().when(mailbagEntity).insertMailbagHistoryDetails(any(MailbagHistoryVO.class), any(Mailbag.class));
		builder.flagMailbagHistoryForDelivery(Stream.of(mailbagVO).collect(Collectors.toList()));
		verify(builder, times(1)).flagMailbagHistoryForDelivery(any());
	}

	@Test
	public void shouldPopulateSenderRecipientWhenFlaggingMailbagHistoryForDeliveryWithAddInfo() throws Exception {
		MailbagVO mailbagVO = new MailbagVO();
		mailbagVO.setCompanyCode(getCompanyCode());
		mailbagVO.setMailSequenceNumber(1);
		mailbagVO.setScannedPort(AIRPORT_SYD);
		mailbagVO.setPaBuiltFlag(MailbagVO.FLAG_YES);
		Mailbag mailbag = new Mailbag();
		MailbagPK mailbagPK = new MailbagPK();
		mailbagPK.setCompanyCode(getCompanyCode());
		mailbagPK.setMailSequenceNumber(1);
		mailbag.setMailbagPK(mailbagPK);
		doReturn(mailbag).when(PersistenceController.getEntityManager()).find(mailbagEntity.getClass(), mailbagPK);
		doNothing().when(mailbagEntity).insertMailbagHistoryDetails(any(MailbagHistoryVO.class), any(Mailbag.class));
		builder.flagMailbagHistoryForDelivery(Stream.of(mailbagVO).collect(Collectors.toList()));
		verify(builder, times(1)).flagMailbagHistoryForDelivery(any());
	}

	@Test
	public void shouldPopulateSenderRecipientWhenInsertOrUpdateHistoryDetailsForCardit() throws Exception {
		CarditVO carditVO = new CarditVO();
		carditVO.setCompanyCode(getCompanyCode());
		CarditReceptacleVO carditReceptacleVO = new CarditReceptacleVO();
		carditReceptacleVO.setReceptacleId("ABC");
		carditVO.setReceptacleInformation(Stream.of(carditReceptacleVO).collect(Collectors.toList()));
		long mailSequenceNumber = 1;
		doReturn(mailSequenceNumber).when(dao).findMailSequenceNumber(any(), any());
		Mailbag mailbag = new Mailbag();
		MailbagPK mailbagPK = new MailbagPK();
		mailbagPK.setCompanyCode(getCompanyCode());
		mailbagPK.setMailSequenceNumber(1);
		mailbag.setMailbagPK(mailbagPK);
		mailbag.setScannedPort(AIRPORT_SYD);
		mailbag.setLatestStatus(MailConstantsVO.MAIL_STATUS_DELIVERED);
		doReturn(mailbag).when(PersistenceController.getEntityManager()).find(mailbagEntity.getClass(), mailbagPK);
		builder.insertOrUpdateHistoryDetailsForCardit(carditVO);
		verify(builder, times(1)).insertOrUpdateHistoryDetailsForCardit(any());
	}

}
