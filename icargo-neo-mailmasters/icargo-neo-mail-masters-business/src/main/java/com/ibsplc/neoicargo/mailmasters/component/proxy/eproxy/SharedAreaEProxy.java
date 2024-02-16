package com.ibsplc.neoicargo.mailmasters.component.proxy.eproxy;

import java.util.Collection;
import java.util.Map;
import com.ibsplc.icargo.business.shared.area.city.vo.CityVO;
import com.ibsplc.icargo.business.shared.area.country.vo.CountryVO;
import com.ibsplc.neoicargo.framework.ebl.api.EProductProxy;

@EProductProxy(module = "shared", submodule = "area", name = "sharedAreaEProxy")
public interface SharedAreaEProxy {
	Map<String, CityVO> validateCityCodes(String companyCode, Collection<String> cityCodes);

	Map<String, CountryVO> validateCountryCodes(String companyCode, Collection<String> countryCodes);
}
