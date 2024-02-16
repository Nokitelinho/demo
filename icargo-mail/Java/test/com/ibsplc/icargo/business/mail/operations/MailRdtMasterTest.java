package com.ibsplc.icargo.business.mail.operations;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyCollectionOf;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.ibsplc.icargo.business.mail.operations.vo.AssignedFlightSegmentVO;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerAssignmentVO;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerVO;
import com.ibsplc.icargo.business.mail.operations.vo.DespatchDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailArrivalVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailRdtMasterVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.business.mail.operations.vo.OnwardRoutingVO;
import com.ibsplc.icargo.business.mail.operations.vo.OperationalFlightVO;
import com.ibsplc.icargo.business.mail.operations.vo.RdtMasterFilterVO;
import com.ibsplc.icargo.framework.feature.AbstractFeatureTest;
import com.ibsplc.icargo.framework.floworchestration.context.SpringAdapter;
import com.ibsplc.icargo.framework.pojo.PojoGetSetTester;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.icargo.framework.util.unit.UnitConstants;
import com.ibsplc.icargo.persistence.dao.mail.operations.MailTrackingDefaultsDAO;
import com.ibsplc.icargo.persistence.dao.mail.operations.MailTrackingDefaultsSqlDAO;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.CreateException;
import com.ibsplc.xibase.server.framework.persistence.EntityManagerMock;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;

public class MailRdtMasterTest extends AbstractFeatureTest{
	
	
	private static final String ORIGIN = "CDG";
	private static final String DESTINATION = "DXB";
	private static final String MAIL_OPERATIONS = "mail.operations";
	private MailRdtMaster mailRdtMasterSpy;
	private MailTrackingDefaultsDAO dao;
	
	
	
	Collection<MailRdtMasterVO> mailRdtMasterVOs;
	MailRdtMasterVO mailRdtMasterVO;
	MailRdtMasterPK mailRdtMasterPK;
	RdtMasterFilterVO filterVO;

	@Override
	public void setup() throws Exception { 
		
		EntityManagerMock.mockEntityManager();
		mailRdtMasterVO = new MailRdtMasterVO();
		mailRdtMasterSpy = spy(new MailRdtMaster(mailRdtMasterVO));
		dao = mock(MailTrackingDefaultsDAO.class);
		
		
	}
	
	
	 @Test
	    public void verifyMailRdtMasterisGettersSetter() throws Exception{
	          assertTrue(new PojoGetSetTester().testGettersAndSetters(MailRdtMaster.class));
	    }

	
	@Test
	public void populateAtrributes_Test() throws SystemException {
		MailRdtMasterVO mailRdtMasterVO = new MailRdtMasterVO();
		mailRdtMasterVO.setCompanyCode("AV");
		mailRdtMasterVO.setAirportCodes(DESTINATION);
		mailRdtMasterVO.setOriginAirportCodes(ORIGIN);
		
		
		
		
	}
	
	
}
