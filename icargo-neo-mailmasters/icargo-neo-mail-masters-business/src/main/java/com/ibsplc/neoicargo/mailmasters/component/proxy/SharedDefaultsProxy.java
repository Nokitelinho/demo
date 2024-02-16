package com.ibsplc.neoicargo.mailmasters.component.proxy;

import java.util.Collection;
import java.util.HashMap;
import java.rmi.RemoteException;
import com.ibsplc.neoicargo.framework.core.lang.BusinessException;
import com.ibsplc.neoicargo.framework.core.lang.SystemException;
import com.ibsplc.icargo.business.shared.defaults.fileupload.vo.FileUploadErrorLogVO;
import com.ibsplc.icargo.business.shared.defaults.generalconfig.vo.GeneralConfigurationFilterVO;
import com.ibsplc.icargo.business.shared.defaults.generalconfig.vo.GeneralConfigurationMasterVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import com.ibsplc.neoicargo.mailmasters.component.proxy.eproxy.SharedDefaultsEProxy;
import org.springframework.beans.factory.annotation.Autowired;

/** 
 * @author a-1936
 */
@Component
@Slf4j
public class SharedDefaultsProxy {
	@Autowired
	private SharedDefaultsEProxy sharedDefaultsEProxy;

	/** 
	* @author a-1936
	* @param systemParameterCodes
	* @return
	* @throws SystemException
	*/
	public HashMap<String, String> findSystemParameterByCodes(Collection<String> systemParameterCodes) {
		log.debug("Inside the FlightOperationsProxy" + " : " + "findSystemParameterByCodes" + " Entering");
		HashMap<String, String> systemParameterCodesMap = null;
		try {
			systemParameterCodesMap = sharedDefaultsEProxy.findSystemParameterByCodes(systemParameterCodes);
		} finally {
		}
		return systemParameterCodesMap;
	}

	public HashMap<String, Collection<OneTimeVO>> findOneTimeValues(
			String companyCode, Collection<String> fieldTypes){

		HashMap<String, Collection<OneTimeVO>> systemOneCodesMap = null;
		try {

			systemOneCodesMap = sharedDefaultsEProxy.findOneTimeValues(companyCode, fieldTypes);
		} finally {
		}
		return systemOneCodesMap;
	}
}
