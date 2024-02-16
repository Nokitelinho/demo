/**
 *	Java file	: 	com.ibsplc.icargo.business.uld.defaults.feature.updateulddemurragedetails.enrichers.DemurrageDetailsEnricher.java
 *
 *	Created on	:	24-Apr-2023
 *
 *  Copyright 2023 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 *
 * 	This software is the proprietary information of Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
 * 	Use is subject to license terms.
 */
package com.ibsplc.icargo.business.uld.defaults.feature.updateulddemurragedetails.enrichers;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;

import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDAgreementVO;
import com.ibsplc.icargo.business.uld.defaults.proxy.ULDDefaultsProxy;
import com.ibsplc.icargo.business.uld.defaults.transaction.vo.TransactionVO;
import com.ibsplc.icargo.business.uld.defaults.transaction.vo.ULDTransactionDetailsVO;
import com.ibsplc.icargo.framework.feature.Enricher;
import com.ibsplc.icargo.framework.feature.FeatureComponent;
import com.ibsplc.icargo.framework.feature.Proxy;
import com.ibsplc.icargo.framework.proxy.ProxyException;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

@FeatureComponent("UpdateULDDemurrageDetailsFeature.DemurrageDetailsEnricher") 
public class DemurrageDetailsEnricher extends Enricher<TransactionVO> {
	@Autowired
	Proxy proxy;
	
	private static final Log LOGGER = LogFactory.getLogger(DemurrageDetailsEnricher.class.getSimpleName());
	/**
	 * 
	 *	Overriding Method	:	@see com.ibsplc.icargo.framework.feature.Enricher#enrich(java.lang.Object)
	 *	Added on 			: 20-Apr-2023
	 * 	Used for 	:	enriching agreement details
	 *	Parameters	:	@param transactionListVO
	 *	Parameters	:	@throws SystemException
	 */
	@Override
	public void enrich(TransactionVO transactionVO) throws SystemException {
		transactionVO.getUldTransactionDetailsVOs().stream()
		.forEach(detailVO->populateDemurrageDetails(detailVO, findBestFitULDAgreement(detailVO)));
	}
	/**
	 * 
	 * 	Method		:	DemurrageDetailsEnricher.populateDemurrageDetails
	 *	Added on 	:	20-Apr-2023
	 * 	Used for 	:
	 *	Parameters	:	@param detailVO
	 *	Parameters	:	@param uldAgreementVO 
	 *	Return type	: 	void
	 */
	private void populateDemurrageDetails(ULDTransactionDetailsVO detailVO, ULDAgreementVO uldAgreementVO) {
		if(Objects.nonNull(uldAgreementVO)){
			detailVO.setAgreementNumber(uldAgreementVO.getAgreementNumber());
			detailVO.setDemurrageRate(uldAgreementVO.getDemurrageRate());
			detailVO.setDemurrageFrequency(uldAgreementVO.getDemurrageFrequency());
			detailVO.setFreeLoanPeriod(uldAgreementVO.getFreeLoanPeriod());
			detailVO.setTaxes(uldAgreementVO.getTax());
			detailVO.setCurrency(uldAgreementVO.getCurrency());	
		}
	}
	/**
	 * 
	 * 	Method		:	DemurrageDetailsEnricher.findBestFitULDAgreement
	 *	Added on 	:	20-Apr-2023
	 * 	Used for 	:	proxy call to uld defaults
	 *	Parameters	:	@param uldTransactionDetailsVO
	 *	Parameters	:	@return 
	 *	Return type	: 	ULDAgreementVO
	 */
	private ULDAgreementVO findBestFitULDAgreement(ULDTransactionDetailsVO uldTransactionDetailsVO) {
		try {
			return proxy.get(ULDDefaultsProxy.class).findBestFitULDAgreement(uldTransactionDetailsVO);
		} catch (ProxyException | SystemException e) {
			LOGGER.log(Log.SEVERE, e);
		}
		return null;
	}

}
