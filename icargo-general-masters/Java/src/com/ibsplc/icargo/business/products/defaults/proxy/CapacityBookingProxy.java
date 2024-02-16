/*
 * CapacityProxy.java Created on Jul 6, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.products.defaults.proxy;

import java.util.Collection;

import com.ibsplc.icargo.business.capacity.booking.vo.BookingVO;
import com.ibsplc.icargo.framework.proxy.ProductProxy;
import com.ibsplc.icargo.framework.proxy.ProxyException;
import com.ibsplc.xibase.client.framework.delegate.Action;
import com.ibsplc.xibase.client.framework.delegate.Module;
import com.ibsplc.xibase.client.framework.delegate.SubModule;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-1358
 *
 */
@Module("capacity")
@SubModule("booking")
public class CapacityBookingProxy extends ProductProxy {
	private Log log = LogFactory.getLogger("PRODUCTS");

    /**
	 * @author A-1883
	 * @param companyCode
	 * @param productCode
	 * @param priority
	 * @param transportMode
	 * @param sccCode
	 * @return Collection<BookingVO>
	 * @throws SystemException
	 * @throws ProxyException
	 */
	@Action("findBookingDetailsForProduct")
	public Collection<BookingVO> findBookingDetailsForProduct
	( String companyCode,String productCode,String priority,String transportMode, String sccCode)
	throws  ProxyException ,SystemException {
		log.entering("CapacityBookingProxy","findBookingDetailsForProduct");
		return despatchRequest("findBookingDetailsForProduct",companyCode,productCode ,
				priority ,transportMode ,sccCode);
	}

}
