package com.ibsplc.neoicargo.mailmasters.component.proxy;

import com.ibsplc.neoicargo.framework.core.lang.SystemException;
import com.ibsplc.neoicargo.mailmasters.component.proxy.eproxy.SharedCommodityEProxy;
import com.ibsplc.neoicargo.mailmasters.component.proxy.eproxy.SharedDefaultsEProxy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashMap;

import com.ibsplc.icargo.business.shared.commodity.vo.CommodityValidationVO;

/** 
 * @author a-1936
 */
@Component
@Slf4j
public class SharedCommodityProxy {
	@Autowired
	private SharedCommodityEProxy sharedCommodityEProxy;

	/** 
	* @author a-1936
	* @return
	* @throws SystemException
	*/
	public HashMap<String,CommodityValidationVO> validateCommodityCodes( String companyCode,Collection<String>
			commodites) {
		log.debug("Inside the sharedCommodityProxy" + " : " + "validateCommodityCodes" + " Entering");
		HashMap<String, CommodityValidationVO> commodityCodesMap = null;
		try {
			commodityCodesMap = sharedCommodityEProxy.validateCommodityCodes(companyCode,commodites);
		} finally {
		}
		return commodityCodesMap;
	}

}
