package com.ibsplc.icargo.business.mail.operations.feature.stampresdit.persistors;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.Test;

import com.ibsplc.icargo.business.mail.operations.MailResdit;
import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailResditVO;
import com.ibsplc.icargo.framework.feature.AbstractFeatureTest;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.persistence.dao.mail.operations.MailTrackingDefaultsDAO;
import com.ibsplc.xibase.server.framework.persistence.EntityManagerMock;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.keygen.provider.Criterion;
import com.ibsplc.xibase.server.framework.util.keygen.KeyUtilInstance;
import com.ibsplc.xibase.server.framework.util.keygen.KeyUtilInstanceMock;

public class MailResditPersistorTest extends AbstractFeatureTest {

	private static final String MAIL_OPERATIONS = "mail.operations";
	private MailResditVO mailResditVO;
	private MailResditPersistor mailResditPersistor;
	private KeyUtilInstance keyUtils;
	private MailTrackingDefaultsDAO dao;

	@Override
	public void setup() throws Exception {
		mailResditVO = setUpMailResditVO();
		EntityManagerMock.mockEntityManager();
		dao = mock(MailTrackingDefaultsDAO.class);
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
		KeyUtilInstanceMock.mockKeyUtilInstance();
		keyUtils = KeyUtilInstance.getInstance();
		mailResditPersistor = spy(MailResditPersistor.class);

	}

	private MailResditVO setUpMailResditVO() {
		MailResditVO mailResditVO = new MailResditVO();
		mailResditVO.setCompanyCode(getCompanyCode());
		mailResditVO.setMailId("GBLHRADEFRAAACA12000200002000");
		mailResditVO.setMailSequenceNumber(1000);
		mailResditVO.setEventAirport("FRA");
		mailResditVO.setEventCode("49");
		mailResditVO.setCarrierId(1001);
		mailResditVO.setFlightNumber("1234");
		mailResditVO.setFlightSequenceNumber(1234);
		mailResditVO.setSegmentSerialNumber(1);
		mailResditVO.setPaOrCarrierCode("AV");
		mailResditVO.setResditSentFlag(MailConstantsVO.FLAG_NO);
		mailResditVO.setProcessedStatus(MailConstantsVO.FLAG_NO);
		mailResditVO.setUldNumber("AKE1234AV");
		mailResditVO.setEventDate(new LocalDate(LocalDate.NO_STATION, Location.NONE, true));
		mailResditVO.setMailboxID("US101");
		return mailResditVO;
	}

	@Test
	public void shouldPersistMailResdit() throws Exception {
		doNothing().when(PersistenceController.getEntityManager()).persist(isA(MailResdit.class));
		doReturn(null).when(dao).findMailboxId(mailResditVO);
		doReturn("1").when(keyUtils).getKey(any(Criterion.class));
		mailResditPersistor.persist(mailResditVO);
		verify(PersistenceController.getEntityManager(), times(1)).persist(isA(MailResdit.class));
	}

}
