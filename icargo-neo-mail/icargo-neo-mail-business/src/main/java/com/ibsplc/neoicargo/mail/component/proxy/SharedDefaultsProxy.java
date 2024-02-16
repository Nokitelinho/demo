package com.ibsplc.neoicargo.mail.component.proxy;

import com.ibsplc.icargo.business.shared.defaults.fileupload.vo.FileUploadErrorLogVO;
import com.ibsplc.icargo.business.shared.defaults.generalconfig.vo.GeneralConfigurationFilterVO;
import com.ibsplc.icargo.business.shared.defaults.generalconfig.vo.GeneralConfigurationMasterVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.neoicargo.framework.core.lang.BusinessException;
import com.ibsplc.neoicargo.framework.core.lang.SystemException;
import com.ibsplc.neoicargo.framework.core.util.ContextUtil;
import com.ibsplc.neoicargo.framework.ebl.api.ServiceException;
import com.ibsplc.neoicargo.mail.component.proxy.eproxy.SharedDefaultsEProxy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/** 
 * @author a-1936
 */
@Component
@Slf4j
public class SharedDefaultsProxy {
	@Autowired
	private SharedDefaultsEProxy sharedDefaultsEProxy;

	public HashMap<String, String> findSystemParameterByCodes(Collection<String> systemParameterCodes) {
		log.debug("Inside the SharedDefaultsProxy" + " : " + "findSystemParameterByCodes" + " Entering");
		var proxy = ContextUtil.getInstance().getBean(SharedDefaultsEProxy.class);
		try {
			return proxy.findSystemParameterByCodes(systemParameterCodes);
		} catch (Exception e) {
			throw new SystemException("Exception in: findSystemParameterByCodes", e.getMessage());
		}
	}

	public HashMap<String, Collection<OneTimeVO>> findOneTimeValues(String companyCode, Collection<String> fieldTypes)
			throws BusinessException {
		try {
			return sharedDefaultsEProxy.findOneTimeValues(companyCode, fieldTypes);
		} catch (ServiceException se) {
			throw new BusinessException(se);
		}
	}

	public Collection<GeneralConfigurationMasterVO> findGeneralConfigurationDetails(
			GeneralConfigurationFilterVO generalTimeMappingFilterVO) {
		try {
			return sharedDefaultsEProxy.findGeneralConfigurationDetails(generalTimeMappingFilterVO);
		} finally {
		}
	}
	public void saveFileUploadExceptions(Collection<FileUploadErrorLogVO> fileUploadErrorLogVOs)
			throws SystemException{

		sharedDefaultsEProxy.saveFileUploadExceptions(fileUploadErrorLogVOs);
	}

	void test(List<String> list) {
		var re = Optional.ofNullable(list).orElse(Collections.emptyList()).stream().map(e->e+"").collect(Collectors.toUnmodifiableList());
	}

	void test(Map<String,String> list) {
		var re = Optional.ofNullable(list).orElse(Collections.emptyMap()).entrySet().stream().map(e->e+"").collect(Collectors.toUnmodifiableList());
		var re1 = Optional.ofNullable(list).orElse(Collections.emptyMap()).entrySet().stream().map(Map.Entry::getValue).filter(k->k.equals("22")).collect(Collectors.toUnmodifiableList());
		var re2 = Optional.ofNullable(list).orElse(Collections.emptyMap()).keySet().stream().map(e->e.toLowerCase()).filter(k->k.equals("22")).collect(Collectors.toUnmodifiableList());
	}
}
