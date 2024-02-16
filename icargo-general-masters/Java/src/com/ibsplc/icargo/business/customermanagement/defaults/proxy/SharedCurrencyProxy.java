/**
 *
 */
package com.ibsplc.icargo.business.customermanagement.defaults.proxy;

import com.ibsplc.icargo.business.shared.currency.vo.CurrencyConvertorVO;
import com.ibsplc.icargo.framework.proxy.ProductProxy;
import com.ibsplc.icargo.framework.proxy.ProxyException;
import com.ibsplc.xibase.client.framework.delegate.Action;
import com.ibsplc.xibase.client.framework.delegate.Module;
import com.ibsplc.xibase.client.framework.delegate.SubModule;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-1883
 *
 */
@Module("shared")
@SubModule("currency")
public class SharedCurrencyProxy extends ProductProxy {

	private  Log log = LogFactory.getLogger("Customer Management");

	/**
	 * This method is used to find the Currency Conversion
	 * rate from one Currency to other
	 * @param companyCode
	 * @param fromCurrencyCode
	 * @param toCurrencyCode
	 * @return double
	 * @throws SystemException
	 * @throws ProxyException
	 */
	@Action("findCurrencyConversionRate")
	public  Double findConversionRate(CurrencyConvertorVO currencyConvertorVO )
	throws SystemException, ProxyException {
		log.entering("SharedCurrencyProxy","findCurrencyConversionRate");
		return despatchRequest("findConversionRate",currencyConvertorVO);
	}
}
