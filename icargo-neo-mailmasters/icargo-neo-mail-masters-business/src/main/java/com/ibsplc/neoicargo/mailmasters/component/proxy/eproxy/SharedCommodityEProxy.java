package com.ibsplc.neoicargo.mailmasters.component.proxy.eproxy;

import com.ibsplc.icargo.business.shared.commodity.vo.CommodityValidationVO;
import com.ibsplc.neoicargo.framework.ebl.api.EProductProxy;

import java.util.Collection;
import java.util.HashMap;

@EProductProxy(module = "shared", submodule = "commodity", name = "sharedCommodityEProxy")
public interface SharedCommodityEProxy {
	HashMap<String, CommodityValidationVO>  validateCommodityCodes( String companyCode,Collection<String>
			commodites);
}
