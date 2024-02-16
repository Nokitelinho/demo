/**
 * 
 */
package com.ibsplc.icargo.business.mail.mra.proxy;

import com.ibsplc.icargo.business.businessframework.businessevaluator.defaults.vo.BusinessEvaluatorFilterVO;
import com.ibsplc.icargo.business.businessframework.businessevaluator.defaults.vo.BusinessEvaluatorVO;
import com.ibsplc.icargo.framework.proxy.ProductProxy;
import com.ibsplc.icargo.framework.proxy.ProxyException;
import com.ibsplc.xibase.client.framework.delegate.Module;
import com.ibsplc.xibase.client.framework.delegate.SubModule;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;

/**
 * @author A-6986
 *
 */
@Module("businessevaluator")
@SubModule("defaults")
public class BusinessEvaluatorDefaultsProxy extends  ProductProxy {

/**
 * @author A-6986
 * @param businessValidationFilterVO
 * @return
 * @throws SystemException
 * @throws ProxyException
 */
public BusinessEvaluatorVO evaluateBusinessRules(BusinessEvaluatorFilterVO businessEvaluatorFilterVO) throws SystemException, ProxyException{
			
		return despatchRequest("evaluateBusinessRules", businessEvaluatorFilterVO);
	}
  
}
