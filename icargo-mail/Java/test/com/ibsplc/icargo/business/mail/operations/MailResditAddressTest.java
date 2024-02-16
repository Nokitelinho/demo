package com.ibsplc.icargo.business.mail.operations;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.spy;

import org.junit.Test;

import com.ibsplc.icargo.business.mail.operations.vo.MailResditAddressVO;
import com.ibsplc.icargo.framework.feature.AbstractFeatureTest;
import com.ibsplc.icargo.framework.pojo.PojoGetSetTester;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.CreateException;
import com.ibsplc.xibase.server.framework.persistence.EntityManagerMock;
import com.ibsplc.xibase.server.framework.persistence.FinderException;

public class MailResditAddressTest extends AbstractFeatureTest {

	private MailResditAddress mailResditAddress;
	private MailResditAddressVO mailResditAddressVO;
	private MailResditAddressPK mailResditAddressPKSpy;

	@Override
	public void setup() throws Exception {
		mailResditAddress = spy(new MailResditAddress());
		EntityManagerMock.mockEntityManager();
		setMailResditAddressVO();
		mailResditAddressPKSpy = spy(new MailResditAddressPK());
	}

	private void setMailResditAddressVO() {
		mailResditAddressVO = new MailResditAddressVO();
		mailResditAddressVO.setCompanyCode(getCompanyCode());
		mailResditAddressVO.setVersion("Test");
		mailResditAddressVO.setMode("Test");
		mailResditAddressVO.setAddress("Test");
		mailResditAddressVO.setEnvelopeAddress("Test");
		mailResditAddressVO.setEnvelopeCode("Test");
		mailResditAddressVO.setParticipantInterfaceSystem("Infys");
		mailResditAddressVO.setParticipantName("Test");
		mailResditAddressVO.setParticipantType("Test");
		mailResditAddressVO.setMessageType("IFTSTA");
		mailResditAddressVO.setAirportCode("CDG");
		mailResditAddressVO.setCountryCode("CDG");

	}

	@Test
	public void verifyMailResditAddressGettersSetter() throws Exception {
		assertTrue(new PojoGetSetTester().testGettersAndSetters(MailResditAddress.class));
	}

	@Test
	public void verifyFMailResditAddressAdditionalGettersSetter() throws Exception {
		assertTrue(new PojoGetSetTester().testGettersAndSetters(MailResditAddressPK.class));
	}

	@Test
	public void callingTheConstructorMailResditAddressPK() throws CreateException, SystemException {
		new MailResditAddressPK();
	}

	@Test
	public void MailResditAddress_Test() throws SystemException {
		new MailResditAddress(mailResditAddressVO);

	}
	
	@Test
	public void testPK() throws FinderException, SystemException{
		MailResditAddressPK mailResditAddressPK= new MailResditAddressPK();
		mailResditAddressPK.setCompanyCode("AV");
		mailResditAddressPK.setMessageAddressSequenceNumber(1000);
		MailResditAddressPK mailResditAddressPK1= new MailResditAddressPK();
		mailResditAddressPK1.setCompanyCode(getCompanyCode());
		mailResditAddressPK1.setMessageAddressSequenceNumber(1000);
		mailResditAddressPKSpy.setCompanyCode("AV");
		mailResditAddressPKSpy.setMessageAddressSequenceNumber(1000);
		assertNotNull(mailResditAddressPKSpy.toString());
		//assertTrue(mailResditAddressPK.equals(mailResditAddressPK1));
		assertFalse(mailResditAddressPK.equals(mailResditAddressPKSpy));
		assertFalse(mailResditAddressPK.equals(null));
	}

}
