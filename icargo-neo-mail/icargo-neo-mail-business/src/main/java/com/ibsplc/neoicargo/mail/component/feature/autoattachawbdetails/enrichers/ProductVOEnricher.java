package com.ibsplc.neoicargo.mail.component.feature.autoattachawbdetails.enrichers;

import com.ibsplc.icargo.business.products.defaults.vo.ProductVO;
import com.ibsplc.icargo.business.products.defaults.vo.ProductValidationVO;
import com.ibsplc.neoicargo.framework.core.lang.BusinessException;
import com.ibsplc.neoicargo.framework.orchestration.Enricher;
import com.ibsplc.neoicargo.mail.component.feature.autoattachawbdetails.AutoAttachAWBDetailsFeatureConstants;
import com.ibsplc.neoicargo.mail.component.proxy.ProductDefaultsProxy;
import com.ibsplc.neoicargo.mail.component.proxy.SharedDefaultsProxy;
import com.ibsplc.neoicargo.mail.util.NeoMastersServiceUtils;
import com.ibsplc.neoicargo.mail.vo.MailManifestDetailsVO;
import com.ibsplc.neoicargo.mail.vo.OperationalFlightVO;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;

@Component(AutoAttachAWBDetailsFeatureConstants.PRODUCT_ENRICHER)
public class ProductVOEnricher extends Enricher<MailManifestDetailsVO> {

	@Autowired
	SharedDefaultsProxy sharedDefaultsProxy;

	@Autowired
	private ProductDefaultsProxy productDefaultsProxy;

	@Autowired
	private NeoMastersServiceUtils neoMastersServiceUtils;

	@Override
	public void enrich(MailManifestDetailsVO mailManifestDetailsVO) {
		OperationalFlightVO operationalFlightVO = mailManifestDetailsVO.getOperationalFlightVO();
		if(Objects.isNull(operationalFlightVO)){
			return;
		}
		String product = null;
		try {
			product = findSystemParameterValue(AutoAttachAWBDetailsFeatureConstants.MAIL_AWB_PRODUCT);
		} catch (SystemException e) {
			e.printStackTrace();
		}
		Collection<ProductValidationVO> productVOs = null;
		ProductVO productVO;
		ProductValidationVO productValidationVO = null;
		
		String companyCode = operationalFlightVO.getCompanyCode();
		productVOs = neoMastersServiceUtils.findProductsByName(companyCode, product);

		if (productVOs != null && !productVOs.isEmpty()) {

			productValidationVO = productVOs.iterator().next();

			productVO = productDefaultsProxy.findProductDetails(companyCode,
					productValidationVO.getProductCode());
			mailManifestDetailsVO.setProductVO(productVO);
		}
		
	}

	public String findSystemParameterValue(String syspar) throws SystemException {
		String sysparValue = null;
		ArrayList<String> systemParameters = new ArrayList<>();
		systemParameters.add(syspar);
		Map<String, String> systemParameterMap = null;
		try {
			systemParameterMap = neoMastersServiceUtils.findSystemParameterByCodes(systemParameters);
		} catch (BusinessException e) {
			return sysparValue;
		}
		if (systemParameterMap != null) {
			sysparValue = systemParameterMap.get(syspar);
		}
		return sysparValue;
	}

}
