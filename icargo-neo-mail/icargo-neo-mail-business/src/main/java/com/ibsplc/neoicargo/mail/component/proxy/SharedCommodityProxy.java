package com.ibsplc.neoicargo.mail.component.proxy;

import java.util.Collection;
import java.util.HashMap;
import java.rmi.RemoteException;
import com.ibsplc.neoicargo.framework.core.lang.BusinessException;
import com.ibsplc.neoicargo.framework.core.lang.SystemException;
import com.ibsplc.icargo.business.shared.commodity.vo.CommodityValidationVO;
import org.springframework.stereotype.Component;
import com.ibsplc.neoicargo.framework.ebl.api.ServiceException;
import com.ibsplc.neoicargo.mail.component.proxy.eproxy.SharedCommodityEProxy;
import org.springframework.beans.factory.annotation.Autowired;

/** 
 * @author A-1936
 */
@Component
public class SharedCommodityProxy {
	@Autowired
	private SharedCommodityEProxy sharedCommodityEProxy;

	/** 
	* @author A-1936
	* @param companyCode
	* @param commodites
	* @return
	* @throws SystemException
	* @throws ProxyException
	* @since j2se1.5
	* @since j2ee1.4
	*/
	public HashMap<String, CommodityValidationVO> validateCommodityCodes(String companyCode,
			Collection<String> commodites) throws BusinessException {
		try {
			return sharedCommodityEProxy.validateCommodityCodes(companyCode, commodites);
		} catch (ServiceException e) {
			throw new BusinessException(e);
		}
	}
}
