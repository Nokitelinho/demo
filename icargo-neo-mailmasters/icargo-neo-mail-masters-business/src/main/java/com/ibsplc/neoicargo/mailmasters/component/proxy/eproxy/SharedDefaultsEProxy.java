package com.ibsplc.neoicargo.mailmasters.component.proxy.eproxy;

import java.util.Collection;
import java.util.HashMap;

import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.neoicargo.framework.ebl.api.EProductProxy;

@EProductProxy(module = "shared", submodule = "defaults", name = "sharedDefaultsEProxy")
public interface SharedDefaultsEProxy {
	HashMap<String, String> findSystemParameterByCodes(Collection<String> systemParameterCodes);

	HashMap<String, Collection<OneTimeVO>> findOneTimeValues(String companyCode, Collection<String> fieldTypes);
}
