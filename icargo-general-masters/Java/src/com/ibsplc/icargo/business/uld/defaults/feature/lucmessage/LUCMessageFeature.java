/**
 *	Java file	: 	com.ibsplc.icargo.business.uld.defaults.feature.lucmessage.LUCMessageFeature.java
 *
 *	Created on	:	13-Dec-2022
 *
 *  Copyright 2019 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 *
 * 	This software is the proprietary information of Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
 * 	Use is subject to license terms.
 */
package com.ibsplc.icargo.business.uld.defaults.feature.lucmessage;



import java.util.ArrayList;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;

import com.ibsplc.icargo.business.uld.defaults.ULDDefaultsBusinessException;
import com.ibsplc.icargo.business.uld.defaults.proxy.BusinessEvaluatorDefaultsProxy;
import com.ibsplc.icargo.business.uld.defaults.transaction.vo.TransactionVO;
import com.ibsplc.icargo.framework.feature.AbstractFeature;
import com.ibsplc.icargo.framework.feature.Feature;
import com.ibsplc.icargo.framework.feature.FeatureComponent;
import com.ibsplc.icargo.framework.feature.Proxy;
import com.ibsplc.icargo.framework.feature.vo.FeatureConfigVO;
import com.ibsplc.icargo.framework.proxy.ProxyException;
import com.ibsplc.xibase.server.framework.exceptions.BusinessException;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 *	Java file	: 	com.ibsplc.icargo.business.uld.defaults.feature.lucmessage.LUCMessageFeature.java
 *	This class is used for	performing LUC message feature
 *	Enricher and validators are currently not configurable in BEV screen.
 *	BEV configuration will be added during code re-factoring
 */
@FeatureComponent("LUCMessageFeature")
@Feature(exception = ULDDefaultsBusinessException.class,
	event="LUC_MESSAGE_FEATURE")
public class LUCMessageFeature extends AbstractFeature<TransactionVO> {

	private static final Log LOGGER = LogFactory.getLogger("ULD DEFAULTS");
	
	public static final String FEATURE_ID = "ULDLUCMSG"; 
	
	@Autowired
	private Proxy proxyInstance;
	
	@Override
	protected FeatureConfigVO fetchFeatureConfig(TransactionVO transactionVO) {
		return getBEVFeatureConfiguration();
	}

	@Override
	protected <R> R perform(TransactionVO transactionVO) throws SystemException, BusinessException {
		LOGGER.entering(this.getClass().getCanonicalName(), "perform");
		
		LOGGER.exiting(this.getClass().getCanonicalName(), "perform");
		return null;
	}

	/**
	 * 	Method		:	LUCMessageFeature.getBEVFeatureConfiguration
	 *	Added on 	:	13-Dec-2022
	 * 	Used for 	:	
	 *	Parameters	:	@return 
	 *	Return type	: 	FeatureConfigVO
	 *	hard coded configuration could be removed during re-factoring
	 */
	private FeatureConfigVO getBEVFeatureConfiguration() {
		
		FeatureConfigVO featureConfigVO = new FeatureConfigVO();
		featureConfigVO.setFeatureId(FEATURE_ID);		
		try {
			featureConfigVO = proxyInstance.get(BusinessEvaluatorDefaultsProxy.class).fetchFeatureRules(featureConfigVO);
		} catch (ProxyException | SystemException e) {
			LOGGER.log(Log.SEVERE, e.getMessage(), e);
		}
		if (Objects.isNull(featureConfigVO.getEnricherId())) {
			featureConfigVO.setEnricherId(new ArrayList<String>());
		}
		if (Objects.isNull(featureConfigVO.getValidatorIds())) {
			featureConfigVO.setValidatorIds(new ArrayList<String>());
		}
		featureConfigVO.getEnricherId().add("LUCMessageFeature.NonCarrierIDEnricher");
		featureConfigVO.getEnricherId().add("LUCMessageFeature.AgentPartyTypeDetailsEnricher");
		
		featureConfigVO.getValidatorIds().add("LUCMessageFeature.ULDFormatValidator");
		return featureConfigVO;
	}


}
