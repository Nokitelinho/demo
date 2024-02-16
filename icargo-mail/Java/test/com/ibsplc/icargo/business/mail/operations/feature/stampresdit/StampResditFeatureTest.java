package com.ibsplc.icargo.business.mail.operations.feature.stampresdit;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyCollectionOf;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.ibsplc.icargo.business.mail.operations.ResditController;
import com.ibsplc.icargo.business.mail.operations.feature.stampresdit.persistors.MailResditPersistor;
import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailResditVO;
import com.ibsplc.icargo.framework.feature.AbstractFeatureTest;
import com.ibsplc.icargo.framework.floworchestration.context.ICargoSproutAdapter;
import com.ibsplc.icargo.framework.util.parameter.ParameterUtil;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.persistence.dao.mail.operations.MailTrackingDefaultsDAO;
import com.ibsplc.xibase.server.framework.persistence.EntityManagerMock;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.keygen.provider.Criterion;
import com.ibsplc.xibase.server.framework.util.keygen.KeyUtilInstance;
import com.ibsplc.xibase.server.framework.util.keygen.KeyUtilInstanceMock;

public class StampResditFeatureTest extends AbstractFeatureTest {

	private static final String MAIL_OPERATIONS = "mail.operations";

	private StampResditFeature spy;
	private MailResditVO mailResditVO;
	private MailResditPersistor mailResditPersistor;
	private ResditController resditController;
	private KeyUtilInstance keyUtils;
	private MailTrackingDefaultsDAO dao;

	@Override
	public void setup() throws Exception {
		spy = spy((StampResditFeature) ICargoSproutAdapter.getBean(StampResditFeatureConstants.STAMP_RESDIT_FEATURE));
		mailResditVO = setUpMailResditVO();	
		mailResditPersistor = mockBean(StampResditFeatureConstants.MAIL_RESDIT_PERSISTOR, MailResditPersistor.class);
		resditController = mockBean("resditController", ResditController.class);
		EntityManagerMock.mockEntityManager();
		dao = mock(MailTrackingDefaultsDAO.class);
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
		KeyUtilInstanceMock.mockKeyUtilInstance();
		keyUtils = KeyUtilInstance.getInstance();
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
	public void verifyStampResditFeatureIsInvoked() throws Exception {
		Map<String, String> parameterMap = new HashMap<>();
		parameterMap.put(MailConstantsVO.RESDIT_STAMPING_PRECHECK_ENABLED, MailConstantsVO.FLAG_YES);
		doReturn(parameterMap).when(ParameterUtil.getInstance())
				.findSystemParameterByCodes(anyCollectionOf(String.class));
		doReturn(true).when(resditController).canStampResdits(mailResditVO);
		doReturn(null).when(dao).findMailboxId(mailResditVO);
		doReturn("1").when(keyUtils).getKey(any(Criterion.class));
		doNothing().when(mailResditPersistor).persist(any());
		spy.execute(mailResditVO);
		verify(spy, times(1)).perform(any(MailResditVO.class));
	}

}
