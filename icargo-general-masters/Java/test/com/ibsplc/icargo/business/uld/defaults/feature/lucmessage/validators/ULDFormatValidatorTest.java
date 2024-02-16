
/**
 *	Java file	: 	com.ibsplc.icargo.business.uld.defaults.feature.lucmessage.validators.ULDFormatValidatorTest.java
 *
 *	Created on	:	13-Dec-2022
 *
 *  Copyright 2019 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 *
 * 	This software is the proprietary information of Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
 * 	Use is subject to license terms.
 */
package com.ibsplc.icargo.business.uld.defaults.feature.lucmessage.validators;

import static org.mockito.Mockito.spy;

import java.util.ArrayList;
import java.util.Collection;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;



import org.junit.Test;

import com.ibsplc.icargo.business.uld.defaults.InvalidULDFormatException;
import com.ibsplc.icargo.business.uld.defaults.ULDController;
import com.ibsplc.icargo.business.uld.defaults.transaction.vo.TransactionVO;
import com.ibsplc.icargo.business.uld.defaults.transaction.vo.ULDTransactionDetailsVO;
import com.ibsplc.icargo.framework.feature.AbstractFeatureTest;
import com.ibsplc.icargo.framework.floworchestration.context.ICargoSproutAdapter;
import com.ibsplc.xibase.server.framework.exceptions.BusinessException;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;

/**
 *	Java file	: 	com.ibsplc.icargo.business.uld.defaults.feature.lucmessage.validators.ULDFormatValidatorTest.java
 *	This class is used for
 */
public class ULDFormatValidatorTest extends AbstractFeatureTest{

	private ULDController uldController;
	ULDFormatValidator validator;
	TransactionVO transactionVO;
	@Override
	public void setup() throws Exception {
		uldController = mockBean("ULDController", ULDController.class);
		validator = spy((ULDFormatValidator) ICargoSproutAdapter.getBean("LUCMessageFeature.ULDFormatValidator"));
		transactionVO = new TransactionVO();
		Collection<ULDTransactionDetailsVO> uldTransactionDetailsVOs = new ArrayList<>();
		transactionVO.setUldTransactionDetailsVOs(uldTransactionDetailsVOs);
		
	}
	
	@Test
	public void shouldReturnSuccessWhenULDFormatIsValid() throws SystemException, BusinessException {
		transactionVO.setCompanyCode("AV");
		transactionVO.setTransactionStation("DXB");
		ULDTransactionDetailsVO transactionDetailVO = new ULDTransactionDetailsVO();
		transactionDetailVO.setLUCMessageRequired(true);
		transactionDetailVO.setPartyType(ULDTransactionDetailsVO.AGENT);
		transactionDetailVO.setUldNumber("AKE1234AF");
		transactionVO.getUldTransactionDetailsVOs().add(transactionDetailVO);
		doReturn(true).when(uldController).validateULDFormat(any(), any());
		validator.validate(transactionVO);
	}
	
	@Test(expected=BusinessException.class)
	public void shouldThrowBusinessExceptionWhenULDFormatIsNotValid() throws SystemException, BusinessException {
		transactionVO.setCompanyCode("AV");
		transactionVO.setTransactionStation("DXB");
		ULDTransactionDetailsVO transactionDetailVO = new ULDTransactionDetailsVO();
		transactionDetailVO.setLUCMessageRequired(true);
		transactionDetailVO.setPartyType(ULDTransactionDetailsVO.AGENT);
		transactionDetailVO.setUldNumber("CART1");
		transactionVO.getUldTransactionDetailsVOs().add(transactionDetailVO);
		doThrow(InvalidULDFormatException.class).when(uldController).validateULDFormat(any(), any());
		validator.validate(transactionVO);
	}
	
	

	
}
