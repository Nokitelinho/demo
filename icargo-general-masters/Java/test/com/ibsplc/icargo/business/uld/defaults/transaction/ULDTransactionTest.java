/**
 *	Java file	: 	com.ibsplc.icargo.business.uld.defaults.transaction.ULDTransactionTest.java
 *
 *	Created on	:	23-Dec-2021
 *
 *  Copyright 2021 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 *
 * 	This software is the proprietary information of Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
 * 	Use is subject to license terms.
 */
package com.ibsplc.icargo.business.uld.defaults.transaction;

import static org.mockito.Mockito.spy;

import java.util.Objects;

import org.junit.Test;

import com.ibsplc.icargo.business.uld.defaults.transaction.vo.ULDTransactionDetailsVO;
import com.ibsplc.icargo.framework.feature.AbstractFeatureTest;
import com.ibsplc.icargo.framework.pojo.PojoGetSetTester;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.EntityManagerMock;
/**
 * 
 *	Java file	: 	com.ibsplc.icargo.business.uld.defaults.transaction.ULDTransactionTest.java
 *	This class is used for testing ULDTransaction.java
 */
public class ULDTransactionTest extends AbstractFeatureTest {
	private static final String ULD_NUMBER = "AKE85894AV";
	private static final String COMPANY_CODE = "AV";
	ULDTransactionDetailsVO uldTransactionDetailsVO;
	ULDTransaction uldTransaction;
	@Override
	public void setup() throws Exception {
		EntityManagerMock.mockEntityManager();
		uldTransaction =  spy(ULDTransaction.class);
		uldTransactionDetailsVO = new ULDTransactionDetailsVO(); 
		uldTransactionDetailsVO.setUldNumber(ULD_NUMBER);
		uldTransactionDetailsVO.setCompanyCode(COMPANY_CODE);
		uldTransactionDetailsVO.setTransactionDate(new LocalDate(LocalDate.NO_STATION, Location.NONE, false));
		uldTransaction.populatePk(uldTransactionDetailsVO);
		
	}
	@Test
	public void shouldPopulateAttributesWhenLeaseEndDateIsNotNull() throws SystemException{
		uldTransactionDetailsVO.setLeaseEndDate(new LocalDate(LocalDate.NO_STATION, Location.NONE, false));
		uldTransaction.populateAttributes(uldTransactionDetailsVO);
		assert(Objects.equals(ULD_NUMBER,uldTransaction.getUldTransactionPK().getUldNumber()));
	}
	@Test
	public void shouldPopulateAttributesWhenLeaseEndDateIsNull() throws SystemException{
		uldTransactionDetailsVO.setLeaseEndDate(null);
		uldTransaction.populateAttributes(uldTransactionDetailsVO);
		assert(Objects.equals(ULD_NUMBER,uldTransaction.getUldTransactionPK().getUldNumber()));
	}
	@Test
	public void shouldUpdateTransaction() throws SystemException{
		uldTransactionDetailsVO.setLastUpdateTime(new LocalDate(LocalDate.NO_STATION, Location.NONE, false));
		uldTransactionDetailsVO.setReturnDate(new LocalDate(LocalDate.NO_STATION, Location.NONE, false));
		uldTransaction.update(uldTransactionDetailsVO);
		assert(Objects.equals(ULD_NUMBER,uldTransaction.getUldTransactionPK().getUldNumber()));
	}
	@Test
    public void verifyULDTransactionGettersSetter() throws Exception{
          new PojoGetSetTester().testGettersAndSetters(ULDTransaction.class);
    } 
}
