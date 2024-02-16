/**
 * Java file	: 	com.ibsplc.icargo.business.uld.defaults.feature.createuldloantransaction.SaveULDLoanTransactionFeatureTest.java
 * Copyright 2023 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 *
 * 	This software is the proprietary information of Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
 * 	Use is subject to license terms.
 */
package com.ibsplc.icargo.business.uld.defaults.feature.createuldloantransaction;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.Objects;

import org.junit.Test;
import org.mockito.ArgumentCaptor;

import com.ibsplc.icargo.business.uld.defaults.ULDController;
import com.ibsplc.icargo.business.uld.defaults.transaction.vo.TransactionVO;
import com.ibsplc.icargo.framework.feature.AbstractFeatureTest;
import com.ibsplc.icargo.framework.floworchestration.context.ICargoSproutAdapter;
import com.ibsplc.xibase.server.framework.exceptions.BusinessException;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
/**
 * 
 *	Java file	: 	com.ibsplc.icargo.business.uld.defaults.feature.createuldloantransaction.SaveULDLoanTransactionFeatureTest.java
 *	This class is used as test class for SaveULDLoanTransactionFeature
 */
public class SaveULDLoanTransactionFeatureTest extends AbstractFeatureTest {
	
	
	SaveULDLoanTransactionFeature feature;
	TransactionVO transactionVO;
	 private ULDController uldController;
	 
	 
	@Override
	public void setup() throws Exception {
		feature = (SaveULDLoanTransactionFeature)ICargoSproutAdapter.getBean("SaveULDLoanTransactionFeature");
		transactionVO = new TransactionVO();
		 uldController = mockBean("ULDController", ULDController.class);
	}
	@Test
	public void shouldInvokeFeatureClass() throws BusinessException, SystemException{
		transactionVO.setFromPartyCode("EK");
		ArgumentCaptor<TransactionVO> captor=ArgumentCaptor.forClass(TransactionVO.class);
		feature.execute(transactionVO);
		verify(uldController,times(1)).createULDLoanTransaction(captor.capture());
		TransactionVO trnVO = captor.getValue();
		assertTrue(Objects.equals("EK", trnVO.getFromPartyCode()));
		
	}

}
