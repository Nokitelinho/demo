package com.ibsplc.neoicargo.mail.component.proxy.eproxy;

import com.ibsplc.icargo.business.shared.generalmastergrouping.vo.GeneralMasterGroupFilterVO;
import com.ibsplc.icargo.business.shared.generalmastergrouping.vo.GeneralMasterGroupVO;
import com.ibsplc.neoicargo.framework.core.lang.SystemException;
import com.ibsplc.neoicargo.framework.ebl.api.EProductProxy;

import java.util.Collection;

@EProductProxy(module = "shared", submodule = "generalmastergrouping", name = "sharedGeneralMasterGroupingEProxy")
public interface SharedGeneralMasterGroupingEProxy {
	public GeneralMasterGroupVO listGeneralMasterGroup(GeneralMasterGroupFilterVO filterVO);

	public Collection<String> findGroupNamesOfEntity(
			GeneralMasterGroupFilterVO generalMasterGroupFilterVO) ;


}
