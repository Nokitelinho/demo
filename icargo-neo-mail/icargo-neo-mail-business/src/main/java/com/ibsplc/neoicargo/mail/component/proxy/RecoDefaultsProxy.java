package com.ibsplc.neoicargo.mail.component.proxy;

import java.util.Collection;
import com.ibsplc.neoicargo.framework.core.lang.BusinessException;
import com.ibsplc.neoicargo.framework.core.lang.SystemException;
import com.ibsplc.icargo.business.reco.defaults.vo.ShipmentDetailsVO;
import com.ibsplc.icargo.business.reco.defaults.vo.EmbargoDetailsVO;
import com.ibsplc.icargo.business.reco.defaults.vo.EmbargoFilterVO;
import com.ibsplc.neoicargo.framework.ebl.api.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import com.ibsplc.neoicargo.mail.component.proxy.eproxy.RecoDefaultsEProxy;
import org.springframework.beans.factory.annotation.Autowired;

/** 
 * @author A-4810
 */
@Component
@Slf4j
public class RecoDefaultsProxy {
	@Autowired
	private RecoDefaultsEProxy recoDefaultsEProxy;
	private static final String MODULE = "RecoDefaultsProxy";

	public Collection<EmbargoDetailsVO> checkForEmbargo(Collection<ShipmentDetailsVO> shipmentDetailsVos) {
		log.debug(MODULE + " : " + "checkEmbargoForMail" + " Entering");
		try {
			return recoDefaultsEProxy.checkForEmbargo(shipmentDetailsVos);
		} catch(ServiceException e){
			log.error(e.getMessage());
			return null;
		}
	}

	/** 
	* @author A-8353
	* @return
	* @throws SystemException
	*/
	public boolean checkAnyEmbargoExists(EmbargoFilterVO embargoFilterVO) {
		log.debug(MODULE + " : " + "checkAnyEmbargoExists for the transaction." + " Entering");
		return recoDefaultsEProxy.checkAnyEmbargoExists(embargoFilterVO);
	}
}
