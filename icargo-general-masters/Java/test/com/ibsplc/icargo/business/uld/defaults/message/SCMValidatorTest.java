/**
*    Java file    :     com.ibsplc.icargo.business.uld.defaults.message/SCMValidatorTest.java
*
*    Created on    :    Jan 12, 2023
*
*  Copyright 2019 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
*
*     This software is the proprietary information of Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
*     Use is subject to license terms.
*/
package com.ibsplc.icargo.business.uld.defaults.message;

import static org.mockito.Matchers.any;
import java.util.ArrayList;
import java.util.Collection;
import org.junit.Test;
import com.ibsplc.icargo.business.uld.defaults.ULDObjectDAO;
import com.ibsplc.icargo.business.uld.defaults.message.vo.ULDSCMReconcileDetailsVO;
import com.ibsplc.icargo.business.uld.defaults.message.vo.ULDSCMReconcileVO;
import com.ibsplc.icargo.business.uld.defaults.misc.ULDDiscrepancy;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDDiscrepancyVO;
import com.ibsplc.icargo.framework.feature.AbstractFeatureTest;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.EntityManagerMock;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;
import static org.mockito.Mockito.*;

public class SCMValidatorTest extends AbstractFeatureTest {
		
	  private ULDObjectDAO uldObjectDAO;
	 	private SCMValidator scmValidator;
		ULDSCMReconcileVO reconcileVO;
		Collection<ULDDiscrepancy> uLDDiscrepancys;
		ULDDiscrepancy uldDiscrepancy;
		Collection<ULDSCMReconcileDetailsVO> reconcileDetailsVOs;
		ULDSCMReconcileDetailsVO uldSCMReconcileDetailsVO;
		ULDSCMReconcileDetailsVO detailsVO;
		ULDDiscrepancyVO uldDiscrepancyVO;
		Collection<ULDDiscrepancyVO> uldDiscrepancyVOs;
		
	 @Override
	  public void setup() throws Exception {
		 scmValidator = mockBean("scmValidator", SCMValidator.class);
		 scmValidator = new SCMValidator();
		 EntityManagerMock.mockEntityManager();
		 reconcileVO = new ULDSCMReconcileVO();
		 uLDDiscrepancys = new ArrayList<>();
		 reconcileDetailsVOs = new ArrayList<>();
		 detailsVO = new ULDSCMReconcileDetailsVO();
		 uldDiscrepancyVO = new ULDDiscrepancyVO();
		 uldSCMReconcileDetailsVO = new ULDSCMReconcileDetailsVO();
		 uldObjectDAO = mock(ULDObjectDAO.class);
		 uldDiscrepancyVOs = new ArrayList<>();
	 }
	 @Test
	 public void shouldChangeDiscrepancyDetails()
	            throws  SystemException,  PersistenceException {
	 
		 reconcileVO.setCompanyCode("DNAE");
		 reconcileVO.setAirportCode("CDG");
		 uldSCMReconcileDetailsVO.setAirportCode("CDG");
		 uldSCMReconcileDetailsVO.setCompanyCode("DNAE");
		 uldSCMReconcileDetailsVO.setErrorCode("ERR2");
		 reconcileDetailsVOs.add(uldSCMReconcileDetailsVO);
		 detailsVO.setErrorCode("ERR2");
		 detailsVO.setCompanyCode("DNAE");
		 detailsVO.setUldNumber("AKE82011EK");	
		 reconcileVO.setReconcileDetailsVOs(reconcileDetailsVOs);
		 reconcileVO.setStockCheckDate(new LocalDate("CDG", Location.ARP, true));
		 doReturn(uldObjectDAO).when(PersistenceController.getEntityManager()).getObjectQueryDAO("uld.defaults");
		 doReturn(new ArrayList<>()).when(uldObjectDAO).findULDDiscrepanciesObjects(any(),any(),any());	

		 scmValidator.changeDiscrepancyDetails(reconcileVO);
	 }
}
