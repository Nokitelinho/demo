package com.ibsplc.icargo.business.uld.defaults.feature.returnuld;


import java.util.Arrays;

import com.ibsplc.icargo.business.uld.defaults.CurrencyConversionException;
import com.ibsplc.icargo.business.uld.defaults.DimensionConversionException;
import com.ibsplc.icargo.business.uld.defaults.MessageConfigException;
import com.ibsplc.icargo.business.uld.defaults.ULDController;
import com.ibsplc.icargo.business.uld.defaults.ULDDefaultsBusinessException;
import com.ibsplc.icargo.business.uld.defaults.transaction.vo.TransactionListVO;
import com.ibsplc.icargo.framework.feature.AbstractFeature;
import com.ibsplc.icargo.framework.feature.Feature;
import com.ibsplc.icargo.framework.feature.FeatureComponent;
import com.ibsplc.icargo.framework.feature.vo.FeatureConfigVO;
import com.ibsplc.icargo.framework.floworchestration.context.SpringAdapter;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
/**
 * 
 *	Java file	: 	com.ibsplc.icargo.business.uld.defaults.feature.returnuld.DemurrageFeature.java
 *	This class is used for setting currency for uldTransactionDetailVO
 */
@FeatureComponent("SaveReturnTransactionFeature")
@Feature(exception=ULDDefaultsBusinessException.class)
public class SaveReturnTransactionFeature extends AbstractFeature<TransactionListVO>{
	
	private static final String FEATURE_ID = "SAVULDRTNTXN"; 
	
	@Override
	protected FeatureConfigVO fetchFeatureConfig(TransactionListVO arg0) {
		return getBECConfigurationForValidatorsEnrichersAndInvokers();
	}

	private FeatureConfigVO getBECConfigurationForValidatorsEnrichersAndInvokers() {
		FeatureConfigVO featureConfigVO = new FeatureConfigVO();
		featureConfigVO.setFeatureId(FEATURE_ID);		
		featureConfigVO.setEnricherId(Arrays.asList("SaveReturnTransactionFeature.DemurrageEnricher"));
		return featureConfigVO; 
	}
/**
 * 
 *	Overriding Method	:	@see com.ibsplc.icargo.framework.feature.AbstractFeature#perform(com.ibsplc.xibase.server.framework.vo.AbstractVO)
 *	Added on 			: 29-Nov-2022
 * 	Used for 	:   perform
 *	Parameters	:	@param transactionListVO
 *	Parameters	:	@return
 *	Parameters	:	@throws SystemException
 *	Parameters	:	@throws BusinessException
 * @throws DimensionConversionException 
 * @throws CurrencyConversionException 
 * @throws MessageConfigException 
 */
	@Override
	protected <R> R perform(TransactionListVO transactionListVO) throws SystemException, ULDDefaultsBusinessException, MessageConfigException, CurrencyConversionException, DimensionConversionException {

		ULDController uldController = (ULDController) SpringAdapter.getInstance().getBean("ULDController");
		uldController.saveReturnTransaction(transactionListVO);
		return null;
		
	}
}
