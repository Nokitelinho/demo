/**
 * Java file	: 	com.ibsplc.icargo.business.uld.defaults.feature.uldcount.uldCountFeature
 * Copyright 2022 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 *
 * 	This software is the proprietary information of Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
 * 	Use is subject to license terms.
 */
package com.ibsplc.icargo.business.uld.defaults.feature.uldcount;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;

import com.ibsplc.icargo.business.uld.defaults.message.vo.ULDFlightMessageReconcileVO;
import com.ibsplc.icargo.framework.feature.AbstractFeature;
import com.ibsplc.icargo.framework.feature.Feature;
import com.ibsplc.icargo.framework.feature.FeatureComponent;
import com.ibsplc.icargo.framework.feature.vo.FeatureConfigVO;
import com.ibsplc.xibase.server.framework.exceptions.BusinessException;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

@FeatureComponent("ULDCountFeature")
@Feature(exception=BusinessException.class)
/**
 * 
 *	Java file	: 	com.ibsplc.icargo.business.uld.defaults.feature.uldcount.ULDCountFeature.java
 *	This class is used for getting UCM Reported Count
 */
public class ULDCountFeature extends AbstractFeature<ULDFlightMessageReconcileVO>{
  
	@Autowired 
	private static final Log LOGGER = LogFactory.getLogger(ULDCountFeature.class.getSimpleName()); 
	private static final String FEATURE_ID = "GETUCMCNT"; 
	private static final String PERFORM="perform";
	private static final String ENRICHER="ULDCountFeature.UCMCountEnricher"; 
	/**
      *  
      *	Overriding Method	:	@see com.ibsplc.icargo.framework.feature.AbstractFeature#fetchFeatureConfig(com.ibsplc.xibase.server.framework.vo.AbstractVO)
      *	Added on 			: 05-Jul-2022
      * 	Used for 	: for invoke fetchFeatureConfig()
      *	Parameters	:	@param uldFlightMessageReconcileVO
      *	Parameters	:	@return
      */
	@Override
	protected FeatureConfigVO fetchFeatureConfig(ULDFlightMessageReconcileVO uldFlightMessageReconcileVO) {
		return getBECConfigurationForValidatorsEnrichersAndInvokers();
	}
/**
 * 
 * 	Method		:	ULDCountFeature.getBECConfigurationForValidatorsEnrichersAndInvokers
 *	Added on 	:	05-Jul-2022
 * 	Used for 	:   invoke getBECConfigurationForValidatorsEnricher
 *	Parameters	:	@return 
 *	Return type	: 	FeatureConfigVO
 */
	private FeatureConfigVO getBECConfigurationForValidatorsEnrichersAndInvokers() {
		FeatureConfigVO featureConfigVO = new FeatureConfigVO();
		featureConfigVO.setFeatureId(FEATURE_ID);		
		featureConfigVO.setEnricherId (Arrays.asList(ENRICHER)); 
		return featureConfigVO; 
	}  
/**
 * 
 *	Overriding Method	:	@see com.ibsplc.icargo.framework.feature.AbstractFeature#perform(com.ibsplc.xibase.server.framework.vo.AbstractVO)
 *	Added on 			: 05-Jul-2022
 * 	Used for 	:   invoke perform()
 *	Parameters	:	@param uldFlightMessageReconcileVO
 *	Parameters	:	@return
 *	Parameters	:	@throws SystemException
 *	Parameters	:	@throws BusinessException
 */
	@SuppressWarnings("unchecked") 
	@Override
	protected ULDFlightMessageReconcileVO perform(ULDFlightMessageReconcileVO uldFlightMessageReconcileVO) throws SystemException, BusinessException {
		LOGGER.entering(ULDCountFeature.class.getSimpleName(), PERFORM);
		LOGGER.exiting(ULDCountFeature.class.getSimpleName(), PERFORM); 
		
		return uldFlightMessageReconcileVO; 
	}
}
