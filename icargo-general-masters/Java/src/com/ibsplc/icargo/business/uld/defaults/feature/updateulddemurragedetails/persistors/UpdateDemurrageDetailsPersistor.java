/**
 *	Java file	: 	com.ibsplc.icargo.business.uld.defaults.feature.updateulddemurragedetails.persistors.UpdateDemurrageDetailsPersistor.java
 *
 *	Created on	:	24-Apr-2023
 *
 *  Copyright 2023 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 *
 * 	This software is the proprietary information of Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
 * 	Use is subject to license terms.
 */
package com.ibsplc.icargo.business.uld.defaults.feature.updateulddemurragedetails.persistors;

import java.util.Objects;

import com.ibsplc.icargo.business.uld.defaults.transaction.ULDTransaction;
import com.ibsplc.icargo.business.uld.defaults.transaction.vo.TransactionVO;
import com.ibsplc.icargo.business.uld.defaults.transaction.vo.ULDTransactionDetailsVO;
import com.ibsplc.icargo.framework.feature.FeatureComponent;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
/**
 * 
 *	Java file	: 	com.ibsplc.icargo.business.uld.defaults.feature.updateulddemurragedetails.persistors.UpdateDemurrageDetailsPersistor.java
 *	This class is used as the persistor for performing UpdateULDDemurrageDetailsFeature
 */
@FeatureComponent("UpdateULDDemurrageDetailsFeature.UpdateDemurrageDetailsPersistor")
public class UpdateDemurrageDetailsPersistor {
	
	/**
	 * 
	 * 	Method		:	UpdateDemurrageDetailsPersistor.persist
	 *	Added on 	:	20-Apr-2023
	 * 	Used for 	:
	 *	Parameters	:	@param transactionVO
	 *	Parameters	:	@throws SystemException 
	 *	Return type	: 	void
	 */
	public void persist(TransactionVO transactionVO) throws SystemException {
		for(ULDTransactionDetailsVO detailVO : transactionVO.getUldTransactionDetailsVOs()){
			findAndUpdateULDTransaction(detailVO);
		}
	}
	/**
	 * 
	 * 	Method		:	UpdateDemurrageDetailsPersistor.findAndUpdateULDTransaction
	 *	Added on 	:	20-Apr-2023
	 * 	Used for 	:	finding and updating ULDTransaction
	 *	Parameters	:	@param detailVO
	 *	Parameters	:	@throws SystemException 
	 *	Return type	: 	void
	 */
	private void findAndUpdateULDTransaction(ULDTransactionDetailsVO detailVO) throws SystemException {
		ULDTransaction uldTransaction = null; 
		try {
			 uldTransaction = ULDTransaction.find(
					 detailVO.getCompanyCode(),
					 detailVO.getUldNumber(),
					 detailVO.getTransactionRefNumber());
		} catch (SystemException e) {
			throw new SystemException("not transaction found", e);
		}
		if(Objects.nonNull(uldTransaction)){
			uldTransaction.setAgreementNumber(detailVO.getAgreementNumber());
			uldTransaction.setCurrency(detailVO.getCurrency());
			uldTransaction.setTaxes(detailVO.getTaxes());
			uldTransaction.setDemurrageRate(detailVO.getDemurrageRate());
			uldTransaction.setDemurrageFrequency(detailVO.getDemurrageFrequency());
			uldTransaction.setFreeLoanPeriod(detailVO.getFreeLoanPeriod());
		}
		
	}

}
