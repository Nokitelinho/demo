package com.ibsplc.neoicargo.mail.component.proxy.eproxy;

import java.util.Collection;
import java.util.HashMap;

import com.ibsplc.icargo.business.shared.defaults.fileupload.vo.FileUploadErrorLogVO;
import com.ibsplc.icargo.business.shared.defaults.generalconfig.vo.GeneralConfigurationFilterVO;
import com.ibsplc.icargo.business.shared.defaults.generalconfig.vo.GeneralConfigurationMasterVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.neoicargo.framework.ebl.api.EProductProxy;

@EProductProxy(module = "shared", submodule = "defaults", name = "sharedDefaultsEProxy")
public interface SharedDefaultsEProxy {
	HashMap<String, String> findSystemParameterByCodes(Collection<String> systemParameterCodes);

	HashMap<String, Collection<OneTimeVO>> findOneTimeValues(String companyCode, Collection<String> fieldTypes);

	Collection<GeneralConfigurationMasterVO> findGeneralConfigurationDetails(
			GeneralConfigurationFilterVO generalTimeMappingFilterVO);
	void saveFileUploadExceptions(Collection<FileUploadErrorLogVO> fileUploadErrorLogVOs);
}
