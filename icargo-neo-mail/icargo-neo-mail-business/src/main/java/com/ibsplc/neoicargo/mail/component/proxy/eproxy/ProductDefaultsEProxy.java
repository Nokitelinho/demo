package com.ibsplc.neoicargo.mail.component.proxy.eproxy;

import java.util.Collection;
import com.ibsplc.icargo.business.products.defaults.vo.ProductVO;
import com.ibsplc.icargo.business.products.defaults.vo.ProductValidationVO;
import com.ibsplc.neoicargo.framework.ebl.api.EProductProxy;

@EProductProxy(module = "products", submodule = "defaults", name = "productDefaultsEProxy")
public interface ProductDefaultsEProxy {
	Collection<ProductValidationVO> findProductsByName(String companyCode, String productName);

	ProductVO findProductDetails(String companyCode, String product);
}
