/**
 *	Java file	: 	com.ibsplc.icargo.business.mail.operations.feature.saveSecurityDetails.SaveSecurityDetailsFeatureTest.java
 *
 *	Created by	:	A-10647
 *	Created on	:	22-Mar-2022
 *
 *  Copyright 2022 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 *
 * 	This software is the proprietary information of Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
 * 	Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.operations.feature.savesecuritydetails;

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

import com.ibsplc.icargo.business.mail.operations.ConsignmentScreeningDetails;
import com.ibsplc.icargo.business.mail.operations.ConsignmentScreeningDetailsPK;
import com.ibsplc.icargo.business.mail.operations.feature.savesecuritydetails.persistors.SecurityDetailPersistor;
import com.ibsplc.icargo.business.mail.operations.vo.ConsignmentScreeningVO;
import com.ibsplc.icargo.framework.feature.AbstractFeatureTest;
import com.ibsplc.icargo.framework.floworchestration.context.ICargoSproutAdapter;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.icargo.framework.util.unit.UnitConstants;
import com.ibsplc.icargo.persistence.dao.mail.operations.MailTrackingDefaultsDAO;
import com.ibsplc.xibase.server.framework.persistence.EntityManagerMock;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;


/**
 *	Java file	: 	com.ibsplc.icargo.business.mail.operations.feature.saveSecurityDetails.SaveSecurityDetailsFeatureTest.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-10647	:	22-Mar-2022	:	Draft
 */
public class SaveSecurityDetailsFeatureTest extends AbstractFeatureTest{
	private SaveSecurityDetailsFeature spy;
	private ConsignmentScreeningVO consignmentScreeningVO;
	private SecurityDetailPersistor securityDetailPersistor;
	private MailTrackingDefaultsDAO dao;




	/**
	 *	Overriding Method	:	@see com.ibsplc.icargo.framework.feature.AbstractFeatureTest#setup()
	 *	Added by 			: A-10647 on 22-Mar-2022
	 * 	Used for 	:
	 *	Parameters	:	@throws Exception 
	 */
	@Override
	public void setup() throws Exception {
		spy = spy((SaveSecurityDetailsFeature) ICargoSproutAdapter.getBean("mail.operations.savesecuritydetailsfeature"));
		consignmentScreeningVO = setUpConsignmentScreeningVO();
		EntityManagerMock.mockEntityManager();
		securityDetailPersistor =mockBean(SaveSecurityDetailsFeatureConstants.SECURITY_DETAILS_PERSISTOR, SecurityDetailPersistor.class);
		dao = mock(MailTrackingDefaultsDAO.class);
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(SaveSecurityDetailsFeatureConstants.MODULE_SUBMODULE);
		
	}
	private ConsignmentScreeningVO setUpConsignmentScreeningVO() {
		ConsignmentScreeningVO consignmentScreeningVO = new ConsignmentScreeningVO();
		consignmentScreeningVO.setCompanyCode("IBS");
		consignmentScreeningVO.setSerialNumber(11);
		consignmentScreeningVO.setPaCode("FR001");
		consignmentScreeningVO.setConsignmentNumber("CDG10782d");
		consignmentScreeningVO.setConsignmentSequenceNumber(2);
		consignmentScreeningVO.setSourceIndicator("IFSUM");
		consignmentScreeningVO.setSecurityReasonCode("CS");
		consignmentScreeningVO.setScreenLevelValue("M");
		consignmentScreeningVO.setStatedWeight(new Measure(UnitConstants.MAIL_WGT,0));
		return consignmentScreeningVO;
	}
	@Test()
	public void saveSecurityDetails_WhenInvoked() throws Exception {
		consignmentScreeningVO =setUpConsignmentScreeningVO();
		doNothing().when(securityDetailPersistor).persist(consignmentScreeningVO);
		spy.execute(consignmentScreeningVO);
		verify(spy, times(1)).perform(any(ConsignmentScreeningVO.class));
	}
	@Test()
	public void saveSecurityDetails_Exception() throws Exception {
		consignmentScreeningVO= setUpConsignmentScreeningVO();
		doThrow(FinderException.class).when(PersistenceController.getEntityManager()).find(eq(ConsignmentScreeningDetails.class), any(ConsignmentScreeningDetailsPK.class));
		spy.execute(consignmentScreeningVO);

	}

}
