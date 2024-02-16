/**
 *
 * @author A-9998
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.operations;
import static org.junit.Assert.*;


import static org.mockito.Mockito.spy;


import org.junit.Test;


import com.ibsplc.icargo.business.mail.operations.vo.MailScanDetailVO;

import com.ibsplc.icargo.framework.feature.AbstractFeatureTest;
import com.ibsplc.icargo.framework.pojo.PojoGetSetTester;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;


import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.CreateException;
import com.ibsplc.xibase.server.framework.persistence.EntityManagerMock;
import com.ibsplc.xibase.server.framework.persistence.FinderException;

import com.ibsplc.xibase.server.framework.persistence.PersistenceException;

public class MailScanDetailTest extends AbstractFeatureTest {
	private MailScanDetail mailScanDetailSpy;
	@Override
	public void setup() throws Exception {
		EntityManagerMock.mockEntityManager();
		mailScanDetailSpy = spy(MailScanDetail.class);
	}
	@Test
	public void  populateAttributesOfMailScanDetail() throws SystemException, PersistenceException, FinderException{
		
		
		MailScanDetailVO mailScanDetailVO = new MailScanDetailVO();
		 mailScanDetailVO.setScannedUser("ICOADMIN");
		 mailScanDetailVO.setAirport("DFW");
		 LocalDate scanDate = new LocalDate(LocalDate.NO_STATION,
					Location.NONE, false);
		 mailScanDetailVO.setScanDate(scanDate);
		 mailScanDetailVO.setFuntionPoint("validate");
		 mailScanDetailVO.setScanType("ACP");
		 mailScanDetailVO.setFlightCarrierCode("AA");
		 mailScanDetailVO.setFlightNumber("1236");
		 mailScanDetailVO.setFlightDate(new LocalDate(LocalDate.NO_STATION,Location.NONE,true));
		 mailScanDetailVO.setContainerNumber("AKE88675AA");
		 mailScanDetailVO.setFromCarrierCode("AA");
		 mailScanDetailVO.setFromFlightNumber("1133");
		 mailScanDetailVO.setFromFlightDate(new LocalDate(LocalDate.NO_STATION,Location.NONE,true));
		 mailScanDetailVO.setDeliveryFlag("N");
		 mailScanDetailVO.setOffloadReason("2");
		 mailScanDetailVO.setReturnFlag("N");
		 mailScanDetailVO.setDamageReason("N");
		 mailScanDetailVO.setContainerDestination("LAX");
		 mailScanDetailVO.setContainerPOU("LAX");
		 mailScanDetailVO.setContainerType("U");
		 mailScanDetailVO.setMalComapnyCode("IBS");
		 mailScanDetailVO.setScreeningUser("N");
		 mailScanDetailVO.setSecurityScreeningMethod("N");
		 mailScanDetailVO.setStgUnit("N");
		 mailScanDetailVO.setMailBagId("USDFWADEFRAAACA31200120001200");
		 mailScanDetailVO.setCompanyCode("IBS");
		 mailScanDetailSpy = new MailScanDetail(mailScanDetailVO);
		 MailScanDetail.find(mailScanDetailSpy.getMailScanDetailPK());
	}
	
	@Test
    public void verifyMailScanDetailisGettersSetter() throws Exception{
          assertTrue(new PojoGetSetTester().testGettersAndSetters(MailScanDetail.class));
    }
 @Test
    public void verifyMailScanDetailAdditionalGettersSetter() throws Exception{
	 assertTrue(new PojoGetSetTester().testGettersAndSetters(MailScanDetailPK.class));
    }
	
 @Test
	public void callingTheMailScanDetailPK() throws CreateException, SystemException {
		 new MailScanDetailPK();
	}
@Test
public void mailScanDetailIsInvoked() {
new MailScanDetail();
}
}
