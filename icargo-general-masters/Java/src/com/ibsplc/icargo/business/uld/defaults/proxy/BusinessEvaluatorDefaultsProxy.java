/**
 *	Java file	: 	com.ibsplc.icargo.business.uld.defaults.proxy.BusinessEvaluatorDefaultsProxy.java
 *
 *	Created on	:	13-Dec-2022
 *
 *  Copyright 2019 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 *
 * 	This software is the proprietary information of Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
 * 	Use is subject to license terms.
 */
package com.ibsplc.icargo.business.uld.defaults.proxy;

import com.ibsplc.icargo.business.businessframework.businessevaluator.defaults.vo.BusinessEvaluatorFilterVO;
import com.ibsplc.icargo.business.businessframework.businessevaluator.defaults.vo.BusinessEvaluatorVO;
import com.ibsplc.icargo.framework.proxy.ProductProxy;
import com.ibsplc.icargo.framework.proxy.ProxyException;
import com.ibsplc.xibase.client.framework.delegate.Module;
import com.ibsplc.xibase.client.framework.delegate.SubModule;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.icargo.framework.feature.vo.FeatureConfigVO;

/**
 *	Java file	: 	com.ibsplc.icargo.business.uld.defaults.proxy.BusinessEvaluatorDefaultsProxy.java
 *	This class is used for invoking RMI of business framework module
 */
@Module("businessevaluator")
@SubModule("defaults")
public class BusinessEvaluatorDefaultsProxy extends  ProductProxy {
	
	/**
	 * 	Method		:	BusinessEvaluatorDefaultsProxy.evaluateBusinessRules
	 *	Added on 	:	13-Dec-2022
	 * 	Used for 	:	invoking evaluateBusinessRules RMI
	 *	Parameters	:	@param businessEvaluatorFilterVO
	 *	Parameters	:	@return
	 *	Parameters	:	@throws SystemException
	 *	Parameters	:	@throws ProxyException 
	 *	Return type	: 	BusinessEvaluatorVO
	 */
	public BusinessEvaluatorVO evaluateBusinessRules(BusinessEvaluatorFilterVO businessEvaluatorFilterVO) throws SystemException, ProxyException{
		return despatchRequest("evaluateBusinessRules", businessEvaluatorFilterVO);
	}

	/**
	 * 	Method		:	BusinessEvaluatorDefaultsProxy.fetchFeatureRules
	 *	Added on 	:	13-Dec-2022
	 * 	Used for 	:	invoking fetchFeatureRules RMI
	 *	Parameters	:	@param featureConfigVO
	 *	Parameters	:	@return
	 *	Parameters	:	@throws SystemException
	 *	Parameters	:	@throws ProxyException 
	 *	Return type	: 	FeatureConfigVO
	 */
	public FeatureConfigVO fetchFeatureRules(FeatureConfigVO featureConfigVO) throws SystemException, ProxyException {
		return despatchRequest("fetchFeatureRules", featureConfigVO);
	}
  
}
