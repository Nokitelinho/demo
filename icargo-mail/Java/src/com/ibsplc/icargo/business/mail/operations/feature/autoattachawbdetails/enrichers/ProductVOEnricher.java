package com.ibsplc.icargo.business.mail.operations.feature.autoattachawbdetails.enrichers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import com.ibsplc.icargo.business.products.defaults.vo.ProductVO;
import com.ibsplc.icargo.business.products.defaults.vo.ProductValidationVO;
import com.ibsplc.icargo.business.mail.operations.feature.autoattachawbdetails.AutoAttachAWBDetailsFeatureConstants;
import com.ibsplc.icargo.business.mail.operations.proxy.ProductDefaultsProxy;
import com.ibsplc.icargo.business.mail.operations.proxy.SharedDefaultsProxy;
import com.ibsplc.icargo.business.mail.operations.vo.MailManifestDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.OperationalFlightVO;
import com.ibsplc.icargo.framework.feature.Enricher;
import com.ibsplc.icargo.framework.feature.FeatureComponent;
import com.ibsplc.icargo.framework.feature.Proxy;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;

@FeatureComponent(AutoAttachAWBDetailsFeatureConstants.PRODUCT_ENRICHER)
public class ProductVOEnricher extends Enricher<MailManifestDetailsVO> {
	@Autowired
	Proxy proxy;

	@Override
	public void enrich(MailManifestDetailsVO mailManifestDetailsVO) throws SystemException {
		OperationalFlightVO operationalFlightVO = mailManifestDetailsVO.getOperationalFlightVO();
		if(Objects.isNull(operationalFlightVO)){
			return;
		}	
		String product = findSystemParameterValue(AutoAttachAWBDetailsFeatureConstants.MAIL_AWB_PRODUCT);
		Collection<ProductValidationVO> productVOs = null;
		ProductVO productVO;
		ProductValidationVO productValidationVO = null;
		
		String companyCode = operationalFlightVO.getCompanyCode();
		productVOs = new ProductDefaultsProxy().findProductsByName(companyCode, product);

		if (productVOs != null && !productVOs.isEmpty()) {

			productValidationVO = productVOs.iterator().next();

			productVO = new ProductDefaultsProxy().findProductDetails(companyCode,
					productValidationVO.getProductCode());
			mailManifestDetailsVO.setProductVO(productVO);
		}
		
	}

	public String findSystemParameterValue(String syspar) throws SystemException {
		String sysparValue = null;
		ArrayList<String> systemParameters = new ArrayList<>();
		systemParameters.add(syspar);
		HashMap<String, String> systemParameterMap = Proxy.getInstance().get(SharedDefaultsProxy.class)
				.findSystemParameterByCodes(systemParameters);
		if (systemParameterMap != null) {
			sysparValue = systemParameterMap.get(syspar);
		}
		return sysparValue;
	}

}
