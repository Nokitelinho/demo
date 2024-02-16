package com.ibsplc.icargo.business.products.defaults.proxy;

import java.rmi.RemoteException;
import java.util.Collection;
import java.util.Map;

import com.ibsplc.ibase.servicelocator.exception.ServiceNotAccessibleException;
import com.ibsplc.icargo.business.shared.commodity.CommodityBI;
import com.ibsplc.icargo.business.shared.commodity.CommodityBusinessException;
import com.ibsplc.icargo.business.shared.commodity.vo.CommodityValidationVO;
import com.ibsplc.icargo.framework.proxy.ProxyException;
import com.ibsplc.icargo.framework.proxy.SubSystemProxy;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;

/**
 * The Class SharedCommodityProxy.
 * Author A-6843
 */
public class SharedCommodityProxy extends SubSystemProxy{
	
	
	/**
	 * Validate commodity codes.
	 *
	 * @param companyCode the company code
	 * @param commodityCodes the commodity codes
	 * @return the map
	 * @throws SystemException the system exception
	 * @throws ProxyException the proxy exception
	 */
	public Map<String,CommodityValidationVO> validCommodityCodes(String companyCode, Collection<String> commodityCodes) 
			throws SystemException,ProxyException{
		CommodityBI commodityBI;
		try {
			commodityBI = (CommodityBI)getService("SHARED_COMMODITY");
			return  commodityBI.validCommodityCodes(companyCode, commodityCodes);
		} catch (ServiceNotAccessibleException e) {
			throw new SystemException(e.getMessage());
		}catch (CommodityBusinessException e) {
			throw new ProxyException(e);
		}catch (RemoteException e) {
			throw new SystemException(e.getMessage());
		}
	}

}
