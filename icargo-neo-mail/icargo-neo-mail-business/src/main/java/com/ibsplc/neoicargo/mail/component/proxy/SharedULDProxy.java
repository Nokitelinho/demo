package com.ibsplc.neoicargo.mail.component.proxy;

import com.ibsplc.icargo.business.shared.uld.vo.ULDPositionFilterVO;
import com.ibsplc.icargo.business.shared.uld.vo.ULDPositionVO;
import com.ibsplc.icargo.business.shared.uld.vo.ULDValidationFilterVO;
import com.ibsplc.icargo.business.shared.uld.vo.ULDValidationVO;
import com.ibsplc.neoicargo.framework.core.lang.SystemException;
import com.ibsplc.neoicargo.framework.core.lang.error.ErrorVO;
import com.ibsplc.neoicargo.framework.core.util.ContextUtil;
import com.ibsplc.neoicargo.framework.ebl.api.ServiceException;
import com.ibsplc.neoicargo.framework.util.unit.Quantity;
import com.ibsplc.neoicargo.mail.component.proxy.eproxy.SharedULDEProxy;
import com.ibsplc.neoicargo.mail.exception.SharedProxyException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;

/** 
 * @author A-1883Revision History ------------------------------------------------------------------------- Revision 		Date 					Author 		Description -------------------------------------------------------------------------  0.1     		   Mar 16, 2007			  	 A-1883		Created
 */
@Component
@Slf4j
public class SharedULDProxy {
	@Autowired
	private SharedULDEProxy sharedULDEProxy;
	/** 
	* The ErrorCode in Mail, when the ULDType  is invalid
	*/
	private static final String INVALID_ULDTYPE = "mailtracking.defaults.invaliduldType";

	/** 
	* Validates whether a uld is a valid type or not
	* @param companyCode
	* @param uldNumber
	* @return ULDValidationVO
	* @throws SystemException
	* @throws SharedProxyException
	*/
	public ULDValidationVO validateULD(String companyCode, String uldNumber) throws SharedProxyException {
		log.debug("SharedULDProxy" + " : " + "validateULD" + " Entering");
		ULDValidationVO uldValidationVO = null;
		try {
			uldValidationVO = ContextUtil.getInstance().getBean(SharedULDEProxy.class).validateULD(companyCode, uldNumber);
		} catch (ServiceException ex) {
			Collection<ErrorVO> errors = ex.getErrors();
			for (ErrorVO errorVO : errors) {
				if (SharedProxyException.INVALID_ULDTYPE.equals(errorVO.getErrorCode())) {
					throw new SharedProxyException(INVALID_ULDTYPE);
				}
			}
		}
		log.debug("SharedULDProxy" + " : " + "validateULD" + " Exiting");
		return uldValidationVO;
	}

	public Quantity findULDTareWeight(ULDValidationFilterVO ULDValidationFilterVO) throws SharedProxyException {
		Quantity tareWeight = null;
		try {
			tareWeight = sharedULDEProxy.findULDTareWeight(ULDValidationFilterVO);
		} catch (ServiceException se) {
			throw new SharedProxyException(se.getMessage());
		}
		return tareWeight;
	}

	public Collection<ULDPositionVO> findULDPosition(Collection<ULDPositionFilterVO> filterVOs)
			throws SystemException,SharedProxyException{
		Collection<ULDPositionVO> uldPositionVos=null;

		try {
			uldPositionVos = sharedULDEProxy.findULDPosition(filterVOs);
		} catch (ServiceException se) {
			for(ErrorVO errorVO : se.getErrors()) {
				if("shared.uld.aircraftincompatible".
						equals(errorVO.getErrorCode())){
					throw new SharedProxyException("shared.uld.aircraftincompatible");
				}
			}

	}
		return uldPositionVos;
	}
}
