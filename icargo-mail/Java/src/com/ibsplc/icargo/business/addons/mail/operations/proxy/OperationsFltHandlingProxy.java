
package com.ibsplc.icargo.business.addons.mail.operations.proxy;

import java.util.Collection;

import com.ibsplc.icargo.business.operations.flthandling.cto.vo.CTOShipmentManifestVO;
import com.ibsplc.icargo.business.operations.flthandling.vo.ManifestFilterVO;
import com.ibsplc.icargo.business.operations.flthandling.vo.UldManifestVO;
import com.ibsplc.icargo.business.operations.shipment.vo.ShipmentValidationVO;
import com.ibsplc.icargo.framework.proxy.ProductProxy;
import com.ibsplc.icargo.framework.proxy.ProxyException;
import com.ibsplc.xibase.client.framework.delegate.Module;
import com.ibsplc.xibase.client.framework.delegate.SubModule;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
/**
 * 
 *	Java file	: 	com.ibsplc.icargo.business.addons.mail.operations.proxy.OperationsFltHandlingProxy.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	Ashil M N	:	23-Sep-2021	:	Draft
 */
@Module("operations")
@SubModule("flthandling")
public class OperationsFltHandlingProxy extends ProductProxy {

	/**
	 * 
	 * 	Method		:	OperationsFltHandlingProxy.findManifestShipmentDetails
	 *	Added by 	:	Ashil M N on 23-Sep-2021
	 * 	Used for 	:
	 *	Parameters	:	@param manifestFilterVO
	 *	Parameters	:	@return
	 *	Parameters	:	@throws SystemException 
	 *	Return type	: 	Collection<UldManifestVO>
	 */
	public Collection<UldManifestVO> findManifestShipmentDetails(ManifestFilterVO manifestFilterVO)
			throws SystemException {
		Collection<UldManifestVO> uldManifestVOs = null;

		try {
			uldManifestVOs = despatchRequest("findManifestShipmentDetails", manifestFilterVO);
		} catch (ProxyException e) {
			throw new SystemException(SystemException.SERVICE_NOT_ACCESSIBLE, e);
		}

		return uldManifestVOs;
	}

	/**
	 * 
	 * 	Method		:	OperationsFltHandlingProxy.findShipmentForImportManifest
	 *	Added by 	:	Ashil M N on 23-Sep-2021
	 * 	Used for 	:
	 *	Parameters	:	@param manifestFilterVO
	 *	Parameters	:	@param shipmentValidationVO
	 *	Parameters	:	@return
	 *	Parameters	:	@throws SystemException 
	 *	Return type	: 	CTOShipmentManifestVO
	 */
	public CTOShipmentManifestVO findShipmentForImportManifest(ManifestFilterVO manifestFilterVO,
			ShipmentValidationVO shipmentValidationVO) throws SystemException {
		CTOShipmentManifestVO manifestVO = null;
		try {
			manifestVO = despatchRequest("findShipmentForImportManifest", manifestFilterVO, shipmentValidationVO);
		} catch (ProxyException e) {
			throw new SystemException(SystemException.SERVICE_NOT_ACCESSIBLE, e);
		}
		return manifestVO;
	}

}
