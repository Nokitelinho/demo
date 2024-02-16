package com.ibsplc.neoicargo.mail.component.proxy;

import java.util.ArrayList;
import java.util.Collection;
import com.ibsplc.neoicargo.framework.core.lang.BusinessException;
import com.ibsplc.neoicargo.framework.core.lang.SystemException;
import com.ibsplc.icargo.business.products.defaults.vo.ProductVO;
import com.ibsplc.icargo.business.products.defaults.vo.ProductValidationVO;
import org.springframework.stereotype.Component;
import com.ibsplc.neoicargo.mail.component.proxy.eproxy.ProductDefaultsEProxy;
import org.springframework.beans.factory.annotation.Autowired;

/** 
 * Java file	: 	com.ibsplc.icargo.business.mail.operations.proxy.ProductDefaultsProxy.java Version		:	Name	:	Date			:	Updation --------------------------------------------------- 0.1		:	A-7531	:	24-Apr-2019	:	Draft
 */
@Component
public class ProductDefaultsProxy {
	@Autowired
	private ProductDefaultsEProxy productDefaultsEProxy;

	public Collection<ProductValidationVO> findProductsByName(String companyCode, String productName) {
		Collection<ProductValidationVO> productValidationVOs = new ArrayList<ProductValidationVO>();
		productValidationVOs = productDefaultsEProxy.findProductsByName(companyCode, productName);
		return productValidationVOs;
	}

	/** 
	* Method		:	ProductDefaultsProxy.findProductDetails Added by 	:	A-7531 on 24-Apr-2019 Used for 	: Parameters	:	@param companyCode Parameters	:	@param product Parameters	:	@return  Return type	: 	ProductVO
	* @throws SystemException
	*/
	public ProductVO findProductDetails(String companyCode, String product) {
		ProductVO productVO = new ProductVO();
		productVO = productDefaultsEProxy.findProductDetails(companyCode, product);
		return productVO;
	}
}
