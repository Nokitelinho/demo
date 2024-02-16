package com.ibsplc.icargo.business.mail.operations;

import com.ibsplc.icargo.framework.feature.AbstractFeatureTest;
import com.ibsplc.icargo.framework.pojo.PojoGetSetTester;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.EntityManagerMock;
import com.ibsplc.xibase.server.framework.persistence.FinderException;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.spy;

import org.junit.Test;

public class MailHistoryRemarksTest extends AbstractFeatureTest{
	
	private MailHistoryRemarks mailHistoryRemarksSpy;
	private MailHistoryRemarksPK mailHistoryRemarksPKSpy;

	@Override
	public void setup() throws Exception {

		mailHistoryRemarksSpy = spy(new MailHistoryRemarks());
		mailHistoryRemarksPKSpy = spy(new MailHistoryRemarksPK());
		mailHistoryRemarksSpy.setMailSequenceNumber(12);
		mailHistoryRemarksSpy.setRemarks("Remarks");
		mailHistoryRemarksSpy.setUserName("ACF");
		mailHistoryRemarksSpy.setMailHistoryRemarksPK(new MailHistoryRemarksPK());
		EntityManagerMock.mockEntityManager();
		
	}
	@Test
	public void verifyCreditNoteDetailsEntityGettersSetter() throws Exception{
		new PojoGetSetTester().testGettersAndSetters(MailHistoryRemarks.class);
	}
	@Test
	public void verifyCreditNoteDetailsEntityPK() throws Exception{
		new PojoGetSetTester().testGettersAndSetters(MailHistoryRemarksPK.class);
	}
	@Test
	public void MailHistoryRemarks_Test() {
		new MailHistoryRemarks();
	}
	@Test
	public void MailHistoryRemarksPK_Test() {
		new MailHistoryRemarksPK();
	}
	@Test
	public void testPK() throws FinderException, SystemException{
		MailHistoryRemarksPK mailHistoryRemarksPK= new MailHistoryRemarksPK();
		MailHistoryRemarksPK mailHistoryRemarksPK1= new MailHistoryRemarksPK();
		MailHistoryRemarksPK mailHistoryRemarksPK2= new MailHistoryRemarksPK();
		
		mailHistoryRemarksPK.setCompanyCode("IBS");
		mailHistoryRemarksPK.setSerialNumber(1);
		mailHistoryRemarksPK1.setCompanyCode("IBS");
		mailHistoryRemarksPK1.setSerialNumber(1);
		mailHistoryRemarksPK2.setCompanyCode("XYZ");
		mailHistoryRemarksPK2.setSerialNumber(2);
		assertNotNull(mailHistoryRemarksPKSpy.toString());
		assertTrue(mailHistoryRemarksPK.equals(mailHistoryRemarksPK1));
		assertFalse(mailHistoryRemarksPK.equals(mailHistoryRemarksPK2));
		assertFalse(mailHistoryRemarksPK.equals(null));
	}
}
