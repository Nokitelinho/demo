package com.ibsplc.icargo.business.xaddons.lh.mail.operations.feature.savehbadetails;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.Test;

import com.ibsplc.icargo.business.xaddons.lh.mail.operations.ContainerHBADetails;
import com.ibsplc.icargo.business.xaddons.lh.mail.operations.ContainerHBADetailsPK;
import com.ibsplc.icargo.business.xaddons.lh.mail.operations.feature.savehbadetails.persistors.HBADetailsPersistor;
import com.ibsplc.icargo.business.xaddons.lh.mail.operations.vo.HbaMarkingVO;
import com.ibsplc.icargo.framework.feature.AbstractFeatureTest;
import com.ibsplc.icargo.framework.floworchestration.context.ICargoSproutAdapter;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.CreateException;
import com.ibsplc.xibase.server.framework.persistence.EntityManagerMock;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;


/**
 *	Java file	: 	com.ibsplc.icargo.business.xaddons.lh.mail.operations.feature.savehbadetails.SaveHBADetailsFeatureTest.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	203168	:	18-Oct-2022	:	Draft
 */
public class SaveHBADetailsFeatureTest extends AbstractFeatureTest{
	private SaveHBADetailsFeature spy;
	private HbaMarkingVO hbaMarkingVO;
	private HBADetailsPersistor hbaDetailsPersistor;
	private ContainerHBADetails containerHBADetails;
	
	/**
	 *	Overriding Method	:	@see com.ibsplc.icargo.framework.feature.AbstractFeatureTest#setup()
	 *	Added by 			: A-10647 on 22-Mar-2022
	 * 	Used for 	:
	 *	Parameters	:	@throws Exception 
	 */
	@Override
	public void setup() throws Exception {
		spy = spy((SaveHBADetailsFeature) ICargoSproutAdapter.getBean("xaddons.mail.operations.savehbadetailsfeature"));
		hbaMarkingVO = setUpHbaMarkingVO();
		EntityManagerMock.mockEntityManager();
		hbaDetailsPersistor =mockBean(SaveHBADetailsFeatureConstants.HBA_DETAILS_PERSISTOR, HBADetailsPersistor.class);
		containerHBADetails = mock(ContainerHBADetails.class);
		
	}
	private HbaMarkingVO setUpHbaMarkingVO() {
		HbaMarkingVO hbaMarkingVO = new HbaMarkingVO();
		hbaMarkingVO.setCompanyCode("LH");
		hbaMarkingVO.setUldRefNo(1000);
		hbaMarkingVO.setHbaPosition("LD");
		hbaMarkingVO.setHbaType("P");
		hbaMarkingVO.setOperationFlag("I");
		return hbaMarkingVO;
	}
	@Test
	public void saveHBADetailsWhenInvoked() throws Exception {
		hbaMarkingVO =setUpHbaMarkingVO();
		doNothing().when(hbaDetailsPersistor).persist(hbaMarkingVO);
		spy.execute(hbaMarkingVO);
		verify(spy, times(1)).perform(any(HbaMarkingVO.class));
	}
	
	@Test
	public void saveHBADetailsUpdate() throws Exception {
		hbaMarkingVO =setUpHbaMarkingVO();
		hbaMarkingVO.setOperationFlag("U");
		ContainerHBADetails containerHbaDetailsReturned = new ContainerHBADetails();
		doReturn(containerHbaDetailsReturned).when(PersistenceController.getEntityManager()).find(eq(ContainerHBADetails.class), any(ContainerHBADetailsPK.class));
		doNothing().when(containerHBADetails).update(hbaMarkingVO);
		spy.execute(hbaMarkingVO);

	}
	
	@Test(expected=SystemException.class)
	public void saveHBADetailsUpdateThrowsException() throws Exception {
		hbaMarkingVO =setUpHbaMarkingVO();
		hbaMarkingVO.setOperationFlag("U");
		ContainerHBADetails containerHbaDetailsReturned = new ContainerHBADetails();
		doThrow(FinderException.class).when(PersistenceController.getEntityManager()).find(eq(ContainerHBADetails.class), any(ContainerHBADetailsPK.class));
		spy.execute(hbaMarkingVO);

	}

        @Test(expected=SystemException.class)
	public void saveHBADetailsInsertThrowsException() throws Exception {
		hbaMarkingVO =setUpHbaMarkingVO();
		hbaMarkingVO.setOperationFlag("I");
		doThrow(CreateException.class).when(PersistenceController.getEntityManager()).persist(any(ContainerHBADetails.class));
		spy.execute(hbaMarkingVO);

	}
	
        @Test()
	public void saveHBADetailsDelete() throws Exception {
		hbaMarkingVO =setUpHbaMarkingVO();
		hbaMarkingVO.setOperationFlag("D");
		ContainerHBADetails containerHbaDetailsReturned = new ContainerHBADetails();
		doReturn(containerHbaDetailsReturned).when(PersistenceController.getEntityManager()).find(eq(ContainerHBADetails.class), any(ContainerHBADetailsPK.class));
		doNothing().when(containerHBADetails).remove();
		spy.execute(hbaMarkingVO);

	}
        @Test()
    	public void saveHBADetailsDeleteWithNullDetails() throws Exception {
    		hbaMarkingVO =setUpHbaMarkingVO();
    		hbaMarkingVO.setOperationFlag("D");
    		doReturn(null).when(PersistenceController.getEntityManager()).find(eq(ContainerHBADetails.class), any(ContainerHBADetailsPK.class));
    		doNothing().when(containerHBADetails).remove();
    		spy.execute(hbaMarkingVO);

    	}
    	@Test(expected=SystemException.class)
    	public void saveHBADetailsDeleteThrowsException() throws Exception {
    		hbaMarkingVO =setUpHbaMarkingVO();
    		hbaMarkingVO.setOperationFlag("D");
    		doThrow(FinderException.class).when(PersistenceController.getEntityManager()).find(eq(ContainerHBADetails.class), any(ContainerHBADetailsPK.class));
    		spy.execute(hbaMarkingVO);

    	}

}
