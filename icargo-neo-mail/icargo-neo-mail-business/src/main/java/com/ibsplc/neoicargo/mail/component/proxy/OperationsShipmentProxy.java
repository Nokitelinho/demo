package com.ibsplc.neoicargo.mail.component.proxy;

import com.ibsplc.icargo.business.operations.shipment.vo.HAWBVO;
import com.ibsplc.icargo.business.operations.shipment.vo.ShipmentDetailFilterVO;
import com.ibsplc.icargo.business.operations.shipment.vo.ShipmentDetailVO;
import com.ibsplc.icargo.business.operations.shipment.vo.ShipmentValidationVO;
import com.ibsplc.icargo.business.shared.airline.vo.AirlineValidationVO;
import com.ibsplc.neoicargo.framework.core.lang.BusinessException;
import com.ibsplc.neoicargo.framework.core.lang.SystemException;
import com.ibsplc.neoicargo.framework.core.lang.error.ErrorVO;
import com.ibsplc.neoicargo.mail.component.proxy.eproxy.OperationsShipmentEProxy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author a-1883The Proxy Class for the Operations -Shipment
 */
@Component
@Slf4j
public class OperationsShipmentProxy {
	@Autowired
	private OperationsShipmentEProxy operationsShipmentEProxy;
	public static final String OPERATIONSHIPMENTPROXY = "OperationsShipmentProxy";
	public static final String SAVESHIPMENTDETAIL = "saveShipmentDetails";

	/**
	 * @author a-1883
	 * @param shipmentDetailVO
	 * @return ShipmentValidationVO
	 * @throws SystemException
	 */
	public ShipmentValidationVO saveShipmentDetails(ShipmentDetailVO shipmentDetailVO)  throws SystemException  {
		log.debug(OPERATIONSHIPMENTPROXY + " : " + SAVESHIPMENTDETAIL + " Entering");
		try {
			return operationsShipmentEProxy.saveShipmentDetails(shipmentDetailVO);
		} catch (SystemException proxyException) {
			if (proxyException.getErrors() != null && !proxyException.getErrors().isEmpty()
					&& "operations.shipment.msg.err.bookingmandatory"
					.equals(((ArrayList<ErrorVO>) proxyException.getErrors()).get(0).getErrorCode())) {
				throw new SystemException("mailtracking.defaults.err.bookingmandatory");
			} else 	if (proxyException.getErrors() != null && !proxyException.getErrors().isEmpty()
					&& ("products.defaults.productnotexists"
					.equals(((ArrayList<ErrorVO>) proxyException.getErrors()).get(0).getErrorCode())||
					"operations.shipment.product.invalid"
							.equals(((ArrayList<ErrorVO>) proxyException.getErrors()).get(0).getErrorCode()))) {
				throw new SystemException("mailtracking.defaults.err.productnotexists");
			}
			else {
				throw new SystemException(proxyException.getMessage());
			}
		}
	}

	/**
	 * @return ShipmentMasterDetailsVO
	 * @throws SystemException
	 */
	public ShipmentDetailVO findShipmentDetails(ShipmentDetailFilterVO shipmentDetailFilterVO) {
		log.debug(OPERATIONSHIPMENTPROXY + " : " + "findShipmentDetails" + " Entering");
		return operationsShipmentEProxy.findShipmentDetails(shipmentDetailFilterVO);
	}

	/**
	 * @author a-1936
	 * @param shipmentValidationVO
	 * @param source
	 * @throws SystemException
	 */
	public Collection<String> deleteAWB(ShipmentValidationVO shipmentValidationVO, String source)
			throws BusinessException {
		log.debug(OPERATIONSHIPMENTPROXY + " : " + "deleteAWB" + " Entering");
		return operationsShipmentEProxy.deleteAWB(shipmentValidationVO, source);
	}

	public AirlineValidationVO validateNumericCode(String companyCode, String shipmentPrefix) throws BusinessException {
		log.debug(OPERATIONSHIPMENTPROXY + " : " + "validateNumericCode" + " Entering");
		return operationsShipmentEProxy.validateNumericCode(companyCode, shipmentPrefix);
	}

	public Collection<ShipmentValidationVO> saveHAWBDetails(ShipmentValidationVO shipmentValidationVO,
															Collection<HAWBVO> hawbs, boolean isConsolStatusTobeChanged) {
		log.debug(OPERATIONSHIPMENTPROXY + " : " + "saveHAWBDetails" + " Entering");
		Collection<ShipmentValidationVO> shipmentValidationVOs = new ArrayList<>();
		shipmentValidationVOs = operationsShipmentEProxy.saveHAWBDetails(shipmentValidationVO, hawbs,
				isConsolStatusTobeChanged);
		return shipmentValidationVOs;
	}

	public void saveShipmentDetailsAsync(ShipmentDetailVO shipmentDetailVO){
		operationsShipmentEProxy.saveShipmentDetailsAsync(shipmentDetailVO);
	}
}
