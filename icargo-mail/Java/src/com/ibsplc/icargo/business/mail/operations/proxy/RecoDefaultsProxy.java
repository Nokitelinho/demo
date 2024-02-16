/**
 * RecoDefaultsProxy Created on Nov 4, 2014
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */


package com.ibsplc.icargo.business.mail.operations.proxy;
import java.util.Collection;
import com.ibsplc.icargo.business.reco.defaults.vo.ShipmentDetailsVO;
import com.ibsplc.icargo.business.reco.defaults.vo.EmbargoDetailsVO;
import com.ibsplc.icargo.business.reco.defaults.vo.EmbargoFilterVO;
import com.ibsplc.icargo.framework.proxy.ProductProxy;
import com.ibsplc.icargo.framework.proxy.ProxyException;
import com.ibsplc.xibase.client.framework.delegate.Module;
import com.ibsplc.xibase.client.framework.delegate.SubModule;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
/**
 * 
 * @author A-4810
 *
 */

@Module("reco")
@SubModule("defaults")
public class RecoDefaultsProxy extends ProductProxy  {
	private Log log = LogFactory.getLogger("MAILTRACKING_DEFAULTS");

	private static final String MODULE = "RecoDefaultsProxy";
	
	public Collection<EmbargoDetailsVO> checkForEmbargo(
			Collection<ShipmentDetailsVO> shipmentDetailsVos) throws  SystemException{
		log.entering(MODULE, "checkEmbargoForMail");
		try {
			return despatchRequest("checkForEmbargo",shipmentDetailsVos );
		} catch (ProxyException proxyException) {
			throw new SystemException(proxyException.getMessage(),
					proxyException);
		}
	}
	/**
	 * @author A-8353
	 * @param shipmentDetailsVos
	 * @return
	 * @throws SystemException
	 */
	public boolean  checkAnyEmbargoExists(
			 EmbargoFilterVO embargoFilterVO ) throws  SystemException{
		log.entering(MODULE, "checkAnyEmbargoExists for the transaction.");
		try {
			return despatchRequest("checkAnyEmbargoExists",embargoFilterVO );
		} catch (ProxyException proxyException) {
			throw new SystemException(proxyException.getMessage(),
					proxyException);
		}
	}
}
