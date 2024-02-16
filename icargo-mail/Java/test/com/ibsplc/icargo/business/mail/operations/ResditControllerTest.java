package com.ibsplc.icargo.business.mail.operations;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.Test;

import com.ibsplc.icargo.business.mail.operations.proxy.SharedDefaultsProxy;
import com.ibsplc.icargo.business.mail.operations.vo.MailResditVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailUploadVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.business.mail.operations.vo.ResditEventVO;
import com.ibsplc.icargo.framework.feature.AbstractFeatureTest;
import com.ibsplc.icargo.persistence.dao.mail.operations.MailTrackingDefaultsDAO;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.EntityManagerMock;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.keygen.provider.Criterion;
import com.ibsplc.xibase.server.framework.util.keygen.KeyUtilInstance;
import com.ibsplc.xibase.server.framework.util.keygen.KeyUtilInstanceMock;

public class ResditControllerTest extends AbstractFeatureTest {

	private static final String MAIL_OPERATIONS = "mail.operations";
	private ResditController spy;
	private MailTrackingDefaultsDAO dao;
	private SharedDefaultsProxy sharedDefaultsProxy;
	private KeyUtilInstance keyUtils;

	@Override
	public void setup() throws Exception {
		spy = spy(ResditController.class);
		dao = mock(MailTrackingDefaultsDAO.class);
		EntityManagerMock.mockEntityManager();
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
		sharedDefaultsProxy = mockProxy(SharedDefaultsProxy.class);
		KeyUtilInstanceMock.mockKeyUtilInstance();
		keyUtils = KeyUtilInstance.getInstance();
	}

	@Test
	public void shouldPopulateAndSaveMailResditVO() throws Exception {
		MailUploadVO mailUploadVO = new MailUploadVO();
		mailUploadVO.setCompanyCode(getCompanyCode());
		mailUploadVO.setScannedPort("SYD");
		MailbagVO mailbagVO = new MailbagVO();
		mailbagVO.setCompanyCode(getCompanyCode());
		Collection<String> systemParameters = new ArrayList<>();
		systemParameters.add("ABC");
		doReturn(null).when(sharedDefaultsProxy).findSystemParameterByCodes(systemParameters);
		doReturn("1").when(keyUtils).getKey(any(Criterion.class));
		spy.populateAndSaveMailResditVO(mailUploadVO, 1, 1, mailbagVO);
	}
	@Test
	public void shouldSetSenderAndRecipientIdentifier() throws Exception{
		MailResdit mailResdit = new MailResdit();
		mailResdit.setSenderIdentifier("A");
		mailResdit.setRecipientIdentifier("A");
		ResditEventVO resditEventVO = new ResditEventVO();
		resditEventVO.setSenderIdentifier(mailResdit.getSenderIdentifier());
		resditEventVO.setRecipientIdentifier(mailResdit.getRecipientIdentifier());
		spy.setSenderAndRecipientIdentifier(resditEventVO, mailResdit);
	}
	
	
	@Test
	public void flagResditsForSendResditsFromCarditEnquiry() throws Exception{
		MailbagVO mailbagVO=new MailbagVO();
		mailbagVO.setCompanyCode(getCompanyCode());
		mailbagVO.setMailbagId("FRCDGAAEDXBAAUN34411297000124");
		doReturn("1").when(keyUtils).getKey(any(Criterion.class));
		spy.flagResditsForSendResditsFromCarditEnquiry("32", "48",mailbagVO);
	}
	@Test
	public void canStampResdits_Test() throws FinderException, SystemException {
		MailResditVO mailResditVO=new MailResditVO();
		mailResditVO.setMailboxID("FR001");
		mailResditVO.setCompanyCode(getCompanyCode());
		mailResditVO.setResditFromCarditEnquiry(true);
		MailBoxIdPk mailBoxIdPk = new MailBoxIdPk();
		mailBoxIdPk.setMailboxCode("FR001");
		mailBoxIdPk.setCompanyCode(getCompanyCode());
		MailBoxId mailboxId=new MailBoxId();
		mailboxId.setMailboxIdPK(mailBoxIdPk);
		doReturn(mailboxId).when(PersistenceController.getEntityManager()).find(eq(MailBoxId.class), any(MailBoxIdPk.class));
		spy.canStampResdits(mailResditVO);
	}
	@Test
	public void canStampResditsFromCarditFalse_Test() throws FinderException, SystemException {
		MailResditVO mailResditVO=new MailResditVO();
		mailResditVO.setMailboxID("FR001");
		mailResditVO.setCompanyCode(getCompanyCode());
		mailResditVO.setResditFromCarditEnquiry(false);
		MailBoxIdPk mailBoxIdPk = new MailBoxIdPk();
		mailBoxIdPk.setMailboxCode("FR001");
		mailBoxIdPk.setCompanyCode(getCompanyCode());
		MailBoxId mailboxId=new MailBoxId();
		mailboxId.setMailboxIdPK(mailBoxIdPk);
		doReturn(mailboxId).when(PersistenceController.getEntityManager()).find(eq(MailBoxId.class), any(MailBoxIdPk.class));
		spy.canStampResdits(mailResditVO);
	}
}
