package com.ibsplc.icargo.business.mail.operations;


import com.ibsplc.icargo.business.mail.operations.vo.FlightLoadPlanContainerVO;
import com.ibsplc.icargo.framework.feature.AbstractFeatureTest;
import com.ibsplc.icargo.framework.pojo.PojoGetSetTester;
import com.ibsplc.icargo.persistence.dao.mail.operations.MailTrackingDefaultsDAO;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.CreateException;
import com.ibsplc.xibase.server.framework.persistence.EntityManagerMock;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;

import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;


import org.junit.Test;
import org.mockito.Mockito;





public class FlightLoadPlanContainerTest extends AbstractFeatureTest {
	private FlightLoadPlanContainer flightLoadPlanContainer =null;

	private MailTrackingDefaultsDAO dao;
	private static final String MAIL_OPERATIONS = "mail.operations";

	@Override
	public void setup() throws Exception {
		EntityManagerMock.mockEntityManager();
		FlightLoadPlanContainerVO flightLoadPlanContainerVO = new FlightLoadPlanContainerVO();
		dao = mock(MailTrackingDefaultsDAO.class);
		flightLoadPlanContainer = spy( FlightLoadPlanContainer.class);	
		
	}
	 @Test
	    public void verifyFlightLoadPlanContainerisGettersSetter() throws Exception{
	          assertTrue(new PojoGetSetTester().testGettersAndSetters(FlightLoadPlanContainer.class));
	    }
	 @Test
	    public void verifyFlightLoadPlanContainerAdditionalGettersSetter() throws Exception{
		 assertTrue(new PojoGetSetTester().testGettersAndSetters(FlightLoadPlanContainerPK.class));
	    }
	  @Test
			public void callingTheConstructorFlightLoadPlanContainerPK() throws CreateException, SystemException {
				 new FlightLoadPlanContainerPK();
			}
@Test
public void flightLoadPlanContainer_isInvoked() {
		new FlightLoadPlanContainer();
}
	
	@Test
	public void findFlightLoadPlanContainer() throws SystemException, FinderException {
		FlightLoadPlanContainerVO flightLoadPlanContainerVO = new FlightLoadPlanContainerVO();
		flightLoadPlanContainerVO.setCompanyCode("IBS");
		flightLoadPlanContainerVO.setFlightNumber("AK456");
		flightLoadPlanContainerVO.setFlightSequenceNumber(12);
		flightLoadPlanContainerVO.setSegOrigin("CDG");
		flightLoadPlanContainerVO.setSegDestination("DXB");
		flightLoadPlanContainerVO.setLoadPlanVersion(1);
		flightLoadPlanContainerVO.setContainerType("ST");
		flightLoadPlanContainerVO.setUldReferenceNo(546);
		flightLoadPlanContainerVO.setSegSerialNumber(1);
		flightLoadPlanContainerVO.setPlannedPieces(3);
		flightLoadPlanContainerVO.setPlannedWeight(23.1);
		flightLoadPlanContainerVO.setPlanStatus("I");
		flightLoadPlanContainerVO.setUldNumber("6789");
		flightLoadPlanContainerVO.setOperationFlag("D");
		flightLoadPlanContainerVO.setPlannedPosition("F");
		flightLoadPlanContainerVO.setPlannedVolume(12.34);
		flightLoadPlanContainer = new FlightLoadPlanContainer(flightLoadPlanContainerVO);
		FlightLoadPlanContainer.find(flightLoadPlanContainer.getFlightLoadPlanContainerPK());
}
	@Test
	public void findPreviousLoadPlanVersionsForContainer() throws SystemException, PersistenceException {
		FlightLoadPlanContainerVO flightLoadPlanContainerVO = new FlightLoadPlanContainerVO();
		flightLoadPlanContainerVO.setCompanyCode("IBS");
		flightLoadPlanContainerVO.setFlightNumber("AK456");
		flightLoadPlanContainerVO.setFlightSequenceNumber(12);
		flightLoadPlanContainerVO.setSegOrigin("CDG");
		flightLoadPlanContainerVO.setSegDestination("DXB");
		flightLoadPlanContainerVO.setLoadPlanVersion(1);
		flightLoadPlanContainerVO.setContainerType("ST");
		flightLoadPlanContainerVO.setUldReferenceNo(546);
		flightLoadPlanContainerVO.setSegSerialNumber(1);
		flightLoadPlanContainerVO.setPlannedPieces(3);
		flightLoadPlanContainerVO.setPlannedWeight(23.1);
		flightLoadPlanContainerVO.setPlanStatus("I");
		flightLoadPlanContainerVO.setUldNumber("6789");
		flightLoadPlanContainerVO.setOperationFlag("U");
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
		FlightLoadPlanContainer.findPreviousLoadPlanVersionsForContainer(flightLoadPlanContainerVO);
	}
	@Test(expected=SystemException.class)
	public void flightLoadPlanContainer_PresistenceException() throws SystemException, CreateException, PersistenceException{
		FlightLoadPlanContainerVO flightLoadPlanContainerVO = new FlightLoadPlanContainerVO();
		flightLoadPlanContainerVO.setCompanyCode("IBS");
		flightLoadPlanContainerVO.setFlightNumber("AK456");
		flightLoadPlanContainerVO.setFlightSequenceNumber(12);
		flightLoadPlanContainerVO.setSegOrigin("CDG");
		flightLoadPlanContainerVO.setSegDestination("DXB");
		flightLoadPlanContainerVO.setLoadPlanVersion(1);
		flightLoadPlanContainerVO.setContainerType("ST");
		flightLoadPlanContainerVO.setUldReferenceNo(546);
		flightLoadPlanContainerVO.setSegSerialNumber(1);
		flightLoadPlanContainerVO.setPlannedPieces(3);
		flightLoadPlanContainerVO.setPlannedWeight(23.1);
		flightLoadPlanContainerVO.setPlanStatus("I");
		flightLoadPlanContainerVO.setUldNumber("6789");
		flightLoadPlanContainerVO.setOperationFlag("D");
		doThrow(PersistenceException.class).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
		flightLoadPlanContainer.findPreviousLoadPlanVersionsForContainer(flightLoadPlanContainerVO);
	
	}
	@Test
	public void flightLoadPlanContainer_update() throws SystemException, PersistenceException {
		FlightLoadPlanContainerVO flightLoadPlanContainerVO = new FlightLoadPlanContainerVO();
		flightLoadPlanContainerVO.setCompanyCode("IBS");
		flightLoadPlanContainerVO.setFlightNumber("AK456");
		flightLoadPlanContainerVO.setFlightSequenceNumber(12);
		flightLoadPlanContainerVO.setSegOrigin("CDG");
		flightLoadPlanContainerVO.setSegDestination("DXB");
		flightLoadPlanContainerVO.setLoadPlanVersion(1);
		flightLoadPlanContainerVO.setContainerType("ST");
		flightLoadPlanContainerVO.setUldReferenceNo(546);
		flightLoadPlanContainerVO.setSegSerialNumber(1);
		flightLoadPlanContainerVO.setPlannedPieces(3);
		flightLoadPlanContainerVO.setPlannedWeight(23.1);
		flightLoadPlanContainerVO.setPlanStatus("I");
		flightLoadPlanContainerVO.setUldNumber("6789");
		flightLoadPlanContainerVO.setOperationFlag("U");
		flightLoadPlanContainer.update(flightLoadPlanContainerVO);
	
	}
}
