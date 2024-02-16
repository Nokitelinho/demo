/**
 *	Java file	: 	com.ibsplc.icargo.business.uld.defaults.feature.createuldloantransaction.SaveULDLoanTransactionFeature.java
 *
 *	Created on	:	24-Apr-2023
 *
 *  Copyright 2023 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 *
 * 	This software is the proprietary information of Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
 * 	Use is subject to license terms.
 */
package com.ibsplc.icargo.business.uld.defaults.feature.createuldloantransaction;

import java.util.Collection;

import com.ibsplc.icargo.business.uld.defaults.ULDController;
import com.ibsplc.icargo.business.uld.defaults.ULDDefaultsBusinessException;
import com.ibsplc.icargo.business.uld.defaults.transaction.vo.TransactionVO;
import com.ibsplc.icargo.framework.feature.AbstractFeature;
import com.ibsplc.icargo.framework.feature.Feature;
import com.ibsplc.icargo.framework.feature.FeatureComponent;
import com.ibsplc.icargo.framework.feature.vo.FeatureConfigVO;
import com.ibsplc.icargo.framework.floworchestration.context.SpringAdapter;
import com.ibsplc.xibase.server.framework.exceptions.BusinessException;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;

/**
 * 
 *	Java file	: 	com.ibsplc.icargo.business.uld.defaults.feature.createuldloantransaction.SaveULDLoanTransactionFeature.java
 *	This class is used for as a feature that performs ULD transaction save
 */
@FeatureComponent("SaveULDLoanTransactionFeature")
@Feature(exception = ULDDefaultsBusinessException.class,
	event="SAVE_ULD_TRANSACTION")
public class SaveULDLoanTransactionFeature extends AbstractFeature<TransactionVO> {
	/**
	 * 
	 *	Overriding Method	:	@see com.ibsplc.icargo.framework.feature.AbstractFeature#fetchFeatureConfig(com.ibsplc.xibase.server.framework.vo.AbstractVO)
	 *	Added on 			: 24-Apr-2023
	 * 	Used for 	:	adding configurations as per requirement
	 *	Parameters	:	@param arg0
	 *	Parameters	:	@return
	 */
	@Override
	protected FeatureConfigVO fetchFeatureConfig(TransactionVO arg0) {
		return new FeatureConfigVO();
	}
	/**
	 * 
	 *	Overriding Method	:	@see com.ibsplc.icargo.framework.feature.AbstractFeature#perform(com.ibsplc.xibase.server.framework.vo.AbstractVO)
	 *	Added on 			: 24-Apr-2023
	 * 	Used for 	:	performing feature functionality
	 *	Parameters	:	@param transactionVO
	 *	Parameters	:	@return
	 *	Parameters	:	@throws SystemException
	 *	Parameters	:	@throws BusinessException
	 */
	@Override
	protected Collection<ErrorVO> perform(TransactionVO transactionVO) throws SystemException, BusinessException {
		ULDController uldController = (ULDController) SpringAdapter.getInstance().getBean("ULDController");
		return uldController.createULDLoanTransaction(transactionVO);
	}

}
