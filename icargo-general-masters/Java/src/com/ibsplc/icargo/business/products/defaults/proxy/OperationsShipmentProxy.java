package com.ibsplc.icargo.business.products.defaults.proxy;

import java.util.Collection;

import com.ibsplc.icargo.business.operations.shipment.vo.ProductPerformanceFilterVO;
import com.ibsplc.icargo.business.operations.shipment.vo.ProductPerformanceVO;

import com.ibsplc.icargo.framework.proxy.ProductProxy;
import com.ibsplc.icargo.framework.proxy.ProxyException;
import com.ibsplc.xibase.client.framework.delegate.Action;
import com.ibsplc.xibase.client.framework.delegate.Module;
import com.ibsplc.xibase.client.framework.delegate.SubModule;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-1747
 *
 */
@Module("operations")
@SubModule("shipment")

public class OperationsShipmentProxy extends ProductProxy{

	private Log log = LogFactory.getLogger("PRODUCTS");

    /**
	 * @author A-1747
	 * @param productPerformanceFilterVO
	 * @return Collection<ProductPerformanceVO>
	 * @throws SystemException
	 * @throws ProxyException
	 */
	@Action("findProductPerformanceDetailsForReport")
	public Collection<ProductPerformanceVO> findProductPerformanceDetailsForReport(
			ProductPerformanceFilterVO productPerformanceFilterVO)
	throws  ProxyException ,SystemException {
		log.entering("OperationsShipmentProxy","getProductPerformanceDetailsForReport");
		return despatchRequest("findProductPerformanceDetailsForReport",productPerformanceFilterVO);
	}
}
