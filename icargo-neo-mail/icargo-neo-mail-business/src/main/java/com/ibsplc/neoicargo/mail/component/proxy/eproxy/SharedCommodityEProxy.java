package com.ibsplc.neoicargo.mail.component.proxy.eproxy;

import java.util.Collection;
import java.util.HashMap;
import com.ibsplc.icargo.business.shared.commodity.vo.CommodityValidationVO;
import com.ibsplc.neoicargo.framework.ebl.api.EProductProxy;

@EProductProxy(module = "shared", submodule = "commodity", name = "sharedCommodityEProxy")
public interface SharedCommodityEProxy {
	HashMap<String, CommodityValidationVO> validateCommodityCodes(String companyCode, Collection<String> commodites);
}
