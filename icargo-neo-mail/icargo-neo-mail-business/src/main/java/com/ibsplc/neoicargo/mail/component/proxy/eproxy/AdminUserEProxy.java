package com.ibsplc.neoicargo.mail.component.proxy.eproxy;

import com.ibsplc.icargo.business.admin.user.vo.UserVO;
import com.ibsplc.neoicargo.framework.ebl.api.EProductProxy;

@EProductProxy(module = "admin", submodule = "user", name = "adminUserEProxy")
public interface AdminUserEProxy {
	UserVO findUserDetails(String companyCode, String userCode);
}
