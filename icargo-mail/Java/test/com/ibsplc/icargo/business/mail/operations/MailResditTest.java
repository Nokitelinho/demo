package com.ibsplc.icargo.business.mail.operations;

import com.ibsplc.icargo.framework.feature.AbstractFeatureTest;
import com.ibsplc.icargo.framework.pojo.PojoGetSetTester;
import com.ibsplc.xibase.server.framework.persistence.EntityManagerMock;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.spy;

import org.junit.Test;

public class MailResditTest extends AbstractFeatureTest{

	private MailResdit mailResditSpy;
	@Override
	public void setup() throws Exception {
		EntityManagerMock.mockEntityManager();
		mailResditSpy=spy(new MailResdit());
	}

	
	@Test
	public void verifyMailResditGettersSetter() throws Exception {
		assertTrue(new PojoGetSetTester().testGettersAndSetters(MailResdit.class));
	}
}
