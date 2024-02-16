package com.ibsplc.icargo.business.xaddons.lh.mail.operations;


import com.ibsplc.icargo.business.xaddons.lh.mail.operations.vo.HbaMarkingVO;
import com.ibsplc.icargo.framework.feature.AbstractFeatureTest;
import com.ibsplc.icargo.framework.pojo.PojoGetSetTester;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.CreateException;
import com.ibsplc.xibase.server.framework.persistence.EntityManagerMock;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;


import org.junit.Test;


public class ContainerHBADetailsTest extends AbstractFeatureTest {
	private ContainerHBADetails containerHBADetails =null;
	private ContainerHBADetailsPK containerHBADetailsPKSpy;

	@Override
	public void setup() throws Exception {
		EntityManagerMock.mockEntityManager();
		containerHBADetails = spy( ContainerHBADetails.class);	
		containerHBADetailsPKSpy = spy(new ContainerHBADetailsPK());
		
	}
	 @Test
	    public void verifyContainerHBADetailsGettersSetter() throws Exception{
	          assertTrue(new PojoGetSetTester().testGettersAndSetters(ContainerHBADetails.class));
	    }
	 @Test
	    public void verifyFContainerHBADetailsAdditionalGettersSetter() throws Exception{
		 assertTrue(new PojoGetSetTester().testGettersAndSetters(ContainerHBADetailsPK.class));
	    }
	  @Test
			public void callingTheConstructorContainerHBADetailsPK() throws CreateException, SystemException {
				 new ContainerHBADetailsPK();
			}
@Test
public void ContainerHBADetails_isInvoked() {
		new ContainerHBADetails();
}
	
	@Test
	public void findContainerHBADetails() throws SystemException, FinderException {
		HbaMarkingVO hbaMarkingVO = new HbaMarkingVO();
		hbaMarkingVO.setCompanyCode("CC");
		hbaMarkingVO.setUldRefNo(1000);
		hbaMarkingVO.setHbaPosition("POS");
		hbaMarkingVO.setHbaType("TYP");
		containerHBADetails = new ContainerHBADetails(hbaMarkingVO);
		ContainerHBADetails.find(containerHBADetails.getContainerHBADetailsPK());
}
	
	@Test
	public void testFind() throws FinderException, SystemException{
		ContainerHBADetailsPK containerHBADetailsPK= new ContainerHBADetailsPK();
		containerHBADetailsPK.setCompanyCode("CC");
		containerHBADetailsPK.setUldReferenceNo(1000);
		doReturn(containerHBADetails).when(PersistenceController.getEntityManager()).find(eq(ContainerHBADetails.class), any(ContainerHBADetailsPK.class));
	    assertNotNull(ContainerHBADetails.find(containerHBADetailsPK));
	}
	
	
	@Test
	public void testPK() throws FinderException, SystemException{
		ContainerHBADetailsPK containerHBADetailsPK= new ContainerHBADetailsPK();
		containerHBADetailsPK.setCompanyCode("CC");
		containerHBADetailsPK.setUldReferenceNo(1000);
		ContainerHBADetailsPK containerHBADetailsPK1= new ContainerHBADetailsPK();
		containerHBADetailsPK1.setCompanyCode("CC");
		containerHBADetailsPK1.setUldReferenceNo(1000);
		containerHBADetailsPKSpy.setCompanyCode("CC");
		containerHBADetailsPKSpy.setUldReferenceNo(1000);
		assertNotNull(containerHBADetailsPKSpy.toString());
		assertTrue(containerHBADetailsPK.equals(containerHBADetailsPK1));
		assertFalse(containerHBADetailsPK.equals(containerHBADetailsPKSpy));
		assertFalse(containerHBADetailsPK.equals(null));
	}


}
