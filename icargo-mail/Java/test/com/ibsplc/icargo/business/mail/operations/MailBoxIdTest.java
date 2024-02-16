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
import com.ibsplc.icargo.business.mail.operations.cache.MailEventCache;
import com.ibsplc.icargo.business.mail.operations.cache.PostalAdministrationCache;
import com.ibsplc.icargo.business.mail.operations.proxy.MailOperationsMRAProxy;
import com.ibsplc.icargo.business.mail.operations.proxy.SharedDefaultsProxy;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailAcceptanceVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailEventVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailboxIdVO;
import com.ibsplc.icargo.framework.feature.AbstractFeatureTest;
import com.ibsplc.icargo.framework.proxy.ProxyException;
import com.ibsplc.icargo.persistence.dao.mail.operations.MailTrackingDefaultsDAO;
import com.ibsplc.xibase.server.framework.cache.CacheFactory;
import com.ibsplc.xibase.server.framework.exceptions.BusinessException;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.EntityManagerMock;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;

public class MailBoxIdTest extends AbstractFeatureTest {

	private static final String MAIL_OPERATIONS = "mail.operations";
	private static final String COMPANY_CODE = "IBS";

	private MailBoxId spy;
	private MailTrackingDefaultsDAO dao;
	private MailEventCache mailEventCache;

	@Override
	public void setup() throws Exception {
		EntityManagerMock.mockEntityManager();
		spy = spy(new MailBoxId());
		dao = mock(MailTrackingDefaultsDAO.class);
		mailEventCache = spy(MailEventCache.class);
		doReturn(mailEventCache).when(CacheFactory.getInstance()).getCache(MailEventCache.ENTITY_NAME);
	}

	@Test
	public void updateTest1() throws SystemException, BusinessException, PersistenceException, FinderException {
		MailboxIdVO mailboxIdVo = new MailboxIdVO();
		mailboxIdVo.setMessagingEnabled("P");
		spy.update(mailboxIdVo);
	}
	
	
	@Test
	public void updateTest2() throws SystemException, BusinessException, PersistenceException, FinderException {
		MailboxIdVO mailboxIdVo = new MailboxIdVO();
		mailboxIdVo.setMessagingEnabled("P");
		mailboxIdVo.setMailEventVOs(new ArrayList());
		spy.update(mailboxIdVo);
	}
	
	
	

	@Test
	public void updateTest3() throws SystemException, BusinessException, PersistenceException, FinderException {
		MailboxIdVO mailboxIdVo = new MailboxIdVO();
		mailboxIdVo.setMessagingEnabled("P");
		Collection<MailEventVO> mailEvents = new ArrayList<>();
		MailEventVO mailEventVo = new MailEventVO();
		mailEventVo.setMailCategory("A");
		mailEventVo.setMailClass("1A");
		mailboxIdVo.setCompanyCode("AV");
		mailboxIdVo.setMailboxID("ID");
		mailEvents.add(mailEventVo);
		mailboxIdVo.setMailEventVOs(mailEvents);
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO("mail.operations");
		doReturn(null).when(dao).findMailEvent(any(MailEventPK.class));
		spy.update(mailboxIdVo);
	}
	
	@Test
	public void updateTest4() throws SystemException, BusinessException, PersistenceException, FinderException {
		MailboxIdVO mailboxIdVo = new MailboxIdVO();
		mailboxIdVo.setMessagingEnabled("P");
		Collection<MailEventVO> mailEvents = new ArrayList<>();
		MailEventVO mailEventVo = new MailEventVO();
		mailEventVo.setMailCategory("A");
		mailEventVo.setMailClass("1A");
		mailboxIdVo.setCompanyCode("AV");
		mailboxIdVo.setMailboxID("ID");
		mailEvents.add(mailEventVo);
		mailboxIdVo.setMailEventVOs(mailEvents);
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO("mail.operations");
		doReturn(new ArrayList<>()).when(dao).findMailEvent(any(MailEventPK.class));
		spy.update(mailboxIdVo);
	}
	
	@Test
	public void updateTest5() throws SystemException, BusinessException, PersistenceException, FinderException {
		MailboxIdVO mailboxIdVo = new MailboxIdVO();
		mailboxIdVo.setMessagingEnabled("Y");
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO("mail.operations");
		doReturn(null).when(dao).findMailEvent(any(MailEventPK.class));
		spy.update(mailboxIdVo);
	}
	
	@Test
	public void updateTest6() throws SystemException, BusinessException, PersistenceException, FinderException {
		MailboxIdVO mailboxIdVo = new MailboxIdVO();
		mailboxIdVo.setMessagingEnabled("P");
		Collection<MailEventVO> mailEvents = new ArrayList<>();
		MailEventVO mailEventVo = new MailEventVO();
		mailEventVo.setMailCategory("A");
		mailEventVo.setMailClass("1A");
		mailboxIdVo.setCompanyCode("AV");
		mailboxIdVo.setMailboxID("ID");
		mailEvents.add(mailEventVo);
		mailboxIdVo.setMailEventVOs(mailEvents);
		MailEvent mailEvent = new MailEvent();
		MailEventPK mailEventPK = new MailEventPK();
		mailEventPK.setMailCategory("A");
		mailEventPK.setMailSubClass("1A");
		mailEventPK.setCompanyCode("AV");
		mailEventPK.setMailboxId("ID");
		mailEvents.add(mailEventVo);
		mailEvent.setMailEventPK(mailEventPK);
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO("mail.operations");
		doReturn(mailEvents).when(dao).findMailEvent(any(MailEventPK.class));
		doReturn(mailEvent).when(PersistenceController.getEntityManager()).find(eq(MailEvent.class), any(MailEventPK.class));
		spy.update(mailboxIdVo);
	}
	
	
	
	@Test(expected=SystemException.class)
	public void updateTest7() throws SystemException, BusinessException, PersistenceException, FinderException {
		MailboxIdVO mailboxIdVo = new MailboxIdVO();
		mailboxIdVo.setMessagingEnabled("Y");
		doThrow(PersistenceException.class).when(PersistenceController.getEntityManager()).getQueryDAO("mail.operations");
		spy.update(mailboxIdVo);
	}	
	
}

