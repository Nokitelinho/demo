package com.ibsplc.neoicargo.mail.component.proxy;

import com.ibsplc.neoicargo.framework.core.lang.BusinessException;
import com.ibsplc.neoicargo.framework.core.lang.SystemException;
import com.ibsplc.icargo.business.admin.user.vo.UserVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import com.ibsplc.neoicargo.mail.component.proxy.eproxy.AdminUserEProxy;
import org.springframework.beans.factory.annotation.Autowired;

/** 
 * Java file	: 	com.ibsplc.icargo.business.mail.operations.proxy.AdminUserProxy.java Version		:	Name	:	Date			:	Updation --------------------------------------------------- 0.1		:	A-4809	:	10-May-2022	:	Draft
 */
@Component
@Slf4j
public class AdminUserProxy {
	@Autowired
	private AdminUserEProxy adminUserEProxy;
	private static final String MODULE_NAME = "AdminUserProxy";

	/** 
	* Method		:	AdminUserProxy.findUserDetails Added by 	:	A-4809 on 10-May-2022 Used for 	: Parameters	:	@param companyCode Parameters	:	@param userCode Parameters	:	@return Parameters	:	@throws ProxyException Parameters	:	@throws SystemException  Return type	: 	UserVO
	*/
	public UserVO findUserDetails(String companyCode, String userCode) throws BusinessException {
		log.debug(MODULE_NAME + " : " + "findUserDetails" + " Entering");
		return adminUserEProxy.findUserDetails(companyCode, userCode);
	}
}
