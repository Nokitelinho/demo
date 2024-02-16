package com.ibsplc.icargo.business.mail.operations.feature.saveconsignmentupload.validators;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.Test;

import com.ibsplc.icargo.business.mail.operations.MailBoxId;
import com.ibsplc.icargo.business.mail.operations.MailBoxIdPk;
import com.ibsplc.icargo.business.mail.operations.MailTrackingBusinessException;
import com.ibsplc.icargo.business.mail.operations.vo.ConsignmentDocumentVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailInConsignmentVO;
import com.ibsplc.icargo.framework.feature.AbstractFeatureTest;
import com.ibsplc.icargo.persistence.dao.mail.operations.MailTrackingDefaultsDAO;
import com.ibsplc.xibase.server.framework.persistence.EntityManagerMock;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;

public class PostalAuthorityCodeValidatorTest extends AbstractFeatureTest {

	private PostalAuthorityCodeValidator validator;
	private MailBoxId mailBoxId;
	private MailTrackingDefaultsDAO dao;

	@Override
	public void setup() throws Exception {
		validator = spy(new PostalAuthorityCodeValidator());
		mailBoxId = spy(new MailBoxId());
		EntityManagerMock.mockEntityManager();
		dao = mock(MailTrackingDefaultsDAO.class);
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO("mail.operations");
	}

	@Test
	public void shouldValidateAndNotThrowException_When_MailBoxIdIsPresent() throws Exception {
		ConsignmentDocumentVO consignmentDocumentVO = new ConsignmentDocumentVO();
		consignmentDocumentVO.setPaCode("DE101");
		consignmentDocumentVO.setCompanyCode(getCompanyCode());
		mailBoxId = spy(new MailBoxId());
		mailBoxId.setMailboxStatus("ACTIVE");
		mailBoxId.setMailboxOwner("PA");
		mailBoxId.setOwnerCode("DE101");
		doReturn(mailBoxId).when(PersistenceController.getEntityManager()).find(eq(MailBoxId.class),
				any(MailBoxIdPk.class));
		validator.validate(consignmentDocumentVO);
	}

	@Test(expected = MailTrackingBusinessException.class)
	public void shouldValidateAndThrowException_When_MailBoxIdIsPresentAndInActive() throws Exception {
		ConsignmentDocumentVO consignmentDocumentVO = new ConsignmentDocumentVO();
		consignmentDocumentVO.setPaCode("DE101");
		consignmentDocumentVO.setCompanyCode(getCompanyCode());
		mailBoxId.setMailboxStatus("INACTIVE");
		mailBoxId.setMailboxOwner("PA");
		mailBoxId.setOwnerCode("DE101");
		EntityManagerMock.mockEntityManager();
		doReturn(mailBoxId).when(PersistenceController.getEntityManager()).find(eq(MailBoxId.class),
				any(MailBoxIdPk.class));
		validator.validate(consignmentDocumentVO);
	}

	@Test(expected = MailTrackingBusinessException.class)
	public void shouldValidateAndThrowException_When_MailBoxIdIsPresentAndNotPA() throws Exception {
		ConsignmentDocumentVO consignmentDocumentVO = new ConsignmentDocumentVO();
		consignmentDocumentVO.setPaCode("DE101");
		consignmentDocumentVO.setCompanyCode(getCompanyCode());
		mailBoxId.setMailboxStatus("ACTIVE");
		mailBoxId.setMailboxOwner("AR");
		mailBoxId.setOwnerCode("DE101");
		doReturn(mailBoxId).when(PersistenceController.getEntityManager()).find(eq(MailBoxId.class),
				any(MailBoxIdPk.class));
		validator.validate(consignmentDocumentVO);
	}

	@Test(expected = MailTrackingBusinessException.class)
	public void shouldValidateAndThrowException_When_MailBoxIdIsNotPresent() throws Exception {
		ConsignmentDocumentVO consignmentDocumentVO = new ConsignmentDocumentVO();
		consignmentDocumentVO.setPaCode("DE101");
		consignmentDocumentVO.setCompanyCode(getCompanyCode());
		mailBoxId.setMailboxStatus("ACTIVE");
		mailBoxId.setMailboxOwner("AR");
		mailBoxId.setOwnerCode("DE101");
		doThrow(FinderException.class).when(PersistenceController.getEntityManager()).find(eq(MailBoxId.class),
				any(MailBoxIdPk.class));
		validator.validate(consignmentDocumentVO);
	}

	@Test
	public void shouldValidateAndNotThrowException_When_MailbagIdIsPresent() throws Exception {
		ConsignmentDocumentVO consignmentDocumentVO = new ConsignmentDocumentVO();
		MailInConsignmentVO mailInConsignmentVO = new MailInConsignmentVO();
		Collection<MailInConsignmentVO> mailInConsignmentVOs = new ArrayList<>();
		mailInConsignmentVO.setMailId("DEFRAAUSDFWAACA01200120001200");
		mailInConsignmentVOs.add(mailInConsignmentVO);
		consignmentDocumentVO.setPaCode("DE101");
		consignmentDocumentVO.setCompanyCode(getCompanyCode());
		consignmentDocumentVO.setMailInConsignment(mailInConsignmentVOs);
		doThrow(FinderException.class).when(PersistenceController.getEntityManager()).find(eq(MailBoxId.class),
				any(MailBoxIdPk.class));
		doReturn("DE101").when(dao).findPAForMailboxID(any(String.class), any(String.class), any(String.class));
		validator.validate(consignmentDocumentVO);
	}
	@Test(expected = MailTrackingBusinessException.class)
	public void shouldValidateAndNotThrowException_When_MailbagIdIsNotPresent() throws Exception {
		ConsignmentDocumentVO consignmentDocumentVO = new ConsignmentDocumentVO();
		Collection<MailInConsignmentVO> mailInConsignmentVOs = new ArrayList<>();
		consignmentDocumentVO.setPaCode("DE101");
		consignmentDocumentVO.setCompanyCode(getCompanyCode());
		consignmentDocumentVO.setMailInConsignment(mailInConsignmentVOs);
		doThrow(FinderException.class).when(PersistenceController.getEntityManager()).find(eq(MailBoxId.class),
				any(MailBoxIdPk.class));
		validator.validate(consignmentDocumentVO);
	}
	@Test(expected = MailTrackingBusinessException.class)
	public void shouldValidateAndNotThrowException_When_MailbagIdIsIncorrect() throws Exception {
		ConsignmentDocumentVO consignmentDocumentVO = new ConsignmentDocumentVO();
		MailInConsignmentVO mailInConsignmentVO = new MailInConsignmentVO();
		Collection<MailInConsignmentVO> mailInConsignmentVOs = new ArrayList<>();
		mailInConsignmentVO.setMailId("ABC");
		mailInConsignmentVOs.add(mailInConsignmentVO);
		consignmentDocumentVO.setPaCode("DE101");
		consignmentDocumentVO.setCompanyCode(getCompanyCode());
		consignmentDocumentVO.setMailInConsignment(mailInConsignmentVOs);
		doThrow(FinderException.class).when(PersistenceController.getEntityManager()).find(eq(MailBoxId.class),
				any(MailBoxIdPk.class));
		validator.validate(consignmentDocumentVO);
	}
	@Test(expected = MailTrackingBusinessException.class)
	public void shouldValidateAndNotThrowException_When_MailbagIdIsNull() throws Exception {
		ConsignmentDocumentVO consignmentDocumentVO = new ConsignmentDocumentVO();
		MailInConsignmentVO mailInConsignmentVO = new MailInConsignmentVO();
		Collection<MailInConsignmentVO> mailInConsignmentVOs = new ArrayList<>();
		mailInConsignmentVO.setMailId(null);
		mailInConsignmentVOs.add(mailInConsignmentVO);
		consignmentDocumentVO.setPaCode("DE101");
		consignmentDocumentVO.setCompanyCode(getCompanyCode());
		consignmentDocumentVO.setMailInConsignment(mailInConsignmentVOs);
		doThrow(FinderException.class).when(PersistenceController.getEntityManager()).find(eq(MailBoxId.class),
				any(MailBoxIdPk.class));
		validator.validate(consignmentDocumentVO);
	}	

}
