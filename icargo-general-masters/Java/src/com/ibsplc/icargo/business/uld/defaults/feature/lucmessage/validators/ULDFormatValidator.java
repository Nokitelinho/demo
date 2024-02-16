/**
 *	Java file	: 	com.ibsplc.icargo.business.uld.defaults.feature.lucmessage.validators.ULDFormatValidator.java
 *
 *	Created on	:	13-Dec-2022
 *
 *  Copyright 2019 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 *
 * 	This software is the proprietary information of Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
 * 	Use is subject to license terms.
 */
package com.ibsplc.icargo.business.uld.defaults.feature.lucmessage.validators;

import java.util.stream.Collectors;

import com.ibsplc.icargo.business.uld.defaults.ULDController;
import com.ibsplc.icargo.business.uld.defaults.transaction.vo.TransactionVO;
import com.ibsplc.icargo.business.uld.defaults.transaction.vo.ULDTransactionDetailsVO;
import com.ibsplc.icargo.framework.feature.FeatureComponent;
import com.ibsplc.icargo.framework.feature.Validator;
import com.ibsplc.icargo.framework.floworchestration.context.SpringAdapter;
import com.ibsplc.xibase.server.framework.exceptions.BusinessException;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 *	Java file	: 	com.ibsplc.icargo.business.uld.defaults.feature.lucmessage.validators.ULDFormatValidator.java
 *	This class is used for	validating ULD format
 */
@FeatureComponent("LUCMessageFeature.ULDFormatValidator")
public class ULDFormatValidator extends Validator<TransactionVO>  {

	
	private static final Log LOGGER = LogFactory.getLogger("ULD DEFAULTS");
	@Override
	public void validate(TransactionVO transactionVO) throws BusinessException, SystemException {
		LOGGER.entering(this.getClass().getCanonicalName(), "validate");
		
		for (ULDTransactionDetailsVO transactionDetailsVO : transactionVO.getUldTransactionDetailsVOs().stream()
				.filter(item -> (item.isLUCMessageRequired())).collect(Collectors.toList())) {
			getULDController().validateULDFormat(transactionDetailsVO.getCompanyCode(), transactionDetailsVO.getUldNumber());
		}
	}

	
	
	private ULDController getULDController() throws SystemException {
		return (ULDController) SpringAdapter.getInstance().getBean("ULDController");
	}
}
