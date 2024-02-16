/**
 *	Java file	: 	com.ibsplc.icargo.business.uld.defaults.feature.updateulddemurragedetails.UpdateULDDemurrageDetailsFeature.java
 *
 *	Created on	:	24-Apr-2023
 *
 *  Copyright 2023 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 *
 * 	This software is the proprietary information of Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
 * 	Use is subject to license terms.
 */
package com.ibsplc.icargo.business.uld.defaults.feature.updateulddemurragedetails;

import org.springframework.beans.factory.annotation.Autowired;


import com.ibsplc.icargo.business.uld.defaults.ULDDefaultsBusinessException;
import com.ibsplc.icargo.business.uld.defaults.feature.updateulddemurragedetails.persistors.UpdateDemurrageDetailsPersistor;
import com.ibsplc.icargo.business.uld.defaults.transaction.vo.TransactionVO;
import com.ibsplc.icargo.framework.feature.AbstractFeature;
import com.ibsplc.icargo.framework.feature.Feature;
import com.ibsplc.icargo.framework.feature.FeatureComponent;
import com.ibsplc.icargo.framework.feature.Proxy;
import com.ibsplc.icargo.framework.feature.vo.FeatureConfigVO;
import com.ibsplc.icargo.framework.floworchestration.context.ICargoSproutAdapter;
import com.ibsplc.icargo.framework.proxy.ProxyException;
import com.ibsplc.xibase.server.framework.exceptions.BusinessException;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import com.ibsplc.icargo.business.uld.defaults.proxy.BusinessEvaluatorDefaultsProxy;
/**
 * 
 *	Java file	: 	com.ibsplc.icargo.business.uld.defaults.feature.updateulddemurragedetails.UpdateULDDemurrageDetailsFeature.java
 *	This class is used for updating demurrage details of a transaction
 */
@FeatureComponent("UpdateULDDemurrageDetailsFeature")
@Feature(exception=ULDDefaultsBusinessException.class)
public class UpdateULDDemurrageDetailsFeature extends AbstractFeature<TransactionVO> {
	public static final String FEATURE_NAME = "UPDULDDEM";
	@Autowired
	private Proxy proxyInstance;
	private static final Log LOGGER = LogFactory.getLogger(UpdateULDDemurrageDetailsFeature.class.getSimpleName());
	
	@Override
	protected FeatureConfigVO fetchFeatureConfig(TransactionVO arg0) {
		FeatureConfigVO featureConfigVO = new FeatureConfigVO();
		featureConfigVO.setFeatureId(FEATURE_NAME);		
		try {
			featureConfigVO = proxyInstance.get(BusinessEvaluatorDefaultsProxy.class).fetchFeatureRules(featureConfigVO);
			} catch (ProxyException | SystemException e) {
			LOGGER.log(Log.SEVERE, e);
		}
		return featureConfigVO;
	}
	/**
	 * 
	 *	Overriding Method	:	@see com.ibsplc.icargo.framework.feature.AbstractFeature#perform(com.ibsplc.xibase.server.framework.vo.AbstractVO)
	 *	Added on 			: 20-Apr-2023
	 * 	Used for 	:	performing feature functionality
	 *	Parameters	:	@param transactionVO
	 *	Parameters	:	@return
	 *	Parameters	:	@throws SystemException
	 *	Parameters	:	@throws BusinessException
	 */
	@Override
	protected Void perform(TransactionVO transactionVO) throws SystemException, BusinessException {
		UpdateDemurrageDetailsPersistor updateDemurrageDetailsPersistor =
				(UpdateDemurrageDetailsPersistor)ICargoSproutAdapter.getBean("UpdateULDDemurrageDetailsFeature.UpdateDemurrageDetailsPersistor");
		updateDemurrageDetailsPersistor.persist(transactionVO);
		return null;
	}

}
