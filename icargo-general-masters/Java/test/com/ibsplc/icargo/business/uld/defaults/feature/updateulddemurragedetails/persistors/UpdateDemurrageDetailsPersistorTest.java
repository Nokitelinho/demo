/**
 * Java file	: 	com.ibsplc.icargo.business.uld.defaults.feature.updateulddemurragedetails.UpdateULDDemurrageDetailsFeatureTest.java
 * Copyright 2023 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 *
 * 	This software is the proprietary information of Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
 * 	Use is subject to license terms.
 */
package com.ibsplc.icargo.business.uld.defaults.feature.updateulddemurragedetails.persistors;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.spy;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.Test;

import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDAgreementVO;
import com.ibsplc.icargo.business.uld.defaults.proxy.ULDDefaultsProxy;
import com.ibsplc.icargo.business.uld.defaults.transaction.ULDTransaction;
import com.ibsplc.icargo.business.uld.defaults.transaction.ULDTransactionPK;
import com.ibsplc.icargo.business.uld.defaults.transaction.vo.TransactionVO;
import com.ibsplc.icargo.business.uld.defaults.transaction.vo.ULDTransactionDetailsVO;
import com.ibsplc.icargo.framework.feature.AbstractFeatureTest;
import com.ibsplc.icargo.framework.floworchestration.context.ICargoSproutAdapter;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.CreateException;
import com.ibsplc.xibase.server.framework.persistence.EntityManagerMock;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;
/**
 * 
 *	Java file	: 	com.ibsplc.icargo.business.uld.defaults.feature.updateulddemurragedetails.persistors.UpdateDemurrageDetailsPersistorTest.java
 *	This class is used as test class for UpdateDemurrageDetailsPersistor
 */
public class UpdateDemurrageDetailsPersistorTest extends AbstractFeatureTest {
	TransactionVO transactionVO;
	ULDTransactionDetailsVO uldTransactionDetailsVO;
	Collection<ULDTransactionDetailsVO> transactionDetailsVOs;
	private UpdateDemurrageDetailsPersistor persistor;
	ULDDefaultsProxy uldDefaultsProxy;
	ULDAgreementVO agreementVO;
	ULDTransaction uldTransaction;
	
	@Override
	public void setup() throws Exception {
		transactionVO = new TransactionVO();
		uldTransaction = spy(ULDTransaction.class);
		uldTransactionDetailsVO = new ULDTransactionDetailsVO();
		transactionDetailsVOs = new ArrayList<>();
		EntityManagerMock.mockEntityManager();
		persistor = (UpdateDemurrageDetailsPersistor) ICargoSproutAdapter.getBean("UpdateULDDemurrageDetailsFeature.UpdateDemurrageDetailsPersistor");
	
		
	}
	@Test
	public void shouldPersistTransaction() throws SystemException, FinderException{
		ULDTransaction uldTransactionEntity = new ULDTransaction();
		ULDTransactionPK uldTransactionPK= new ULDTransactionPK();
		uldTransactionPK.setCompanyCode("DNAE");
		uldTransactionPK.setUldNumber("AKE12345EK");
		uldTransactionPK.setTransactionRefNumber(1);
		transactionDetailsVOs.add(uldTransactionDetailsVO);
		transactionVO.setUldTransactionDetailsVOs(transactionDetailsVOs);
		doReturn(uldTransactionEntity).when(PersistenceController.getEntityManager()).find(eq(ULDTransaction.class),
				any(ULDTransactionPK.class));
		persistor.persist(transactionVO);
	}
	@Test(expected = SystemException.class)
	public void shouldThrowSystemExceptionIfULDTransactionCannotBeFound() throws SystemException, CreateException, PersistenceException, FinderException{
		ULDTransactionPK uldTransactionPK= new ULDTransactionPK();
		uldTransactionPK.setCompanyCode("DNAE");
		uldTransactionPK.setUldNumber("AKE12345EK");
		uldTransactionPK.setTransactionRefNumber(1);
		transactionDetailsVOs.add(uldTransactionDetailsVO);
		transactionVO.setUldTransactionDetailsVOs(transactionDetailsVOs);
		doThrow(new FinderException("notFound")).when(PersistenceController.getEntityManager()).find(eq(ULDTransaction.class),
				any(ULDTransactionPK.class));
		persistor.persist(transactionVO);
	}
	@Test
	public void shouldPersistIfEvenIfTransactionDetailsIsNull() throws SystemException, FinderException{
		ULDTransactionPK uldTransactionPK= new ULDTransactionPK();
		uldTransactionPK.setCompanyCode("DNAE");
		uldTransactionPK.setUldNumber("AKE12345EK");
		uldTransactionPK.setTransactionRefNumber(1);
		transactionDetailsVOs.add(uldTransactionDetailsVO);
		transactionVO.setUldTransactionDetailsVOs(transactionDetailsVOs);
		doReturn(null).when(PersistenceController.getEntityManager()).find(eq(ULDTransaction.class),
				any(ULDTransactionPK.class));
		persistor.persist(transactionVO);
	}

}
