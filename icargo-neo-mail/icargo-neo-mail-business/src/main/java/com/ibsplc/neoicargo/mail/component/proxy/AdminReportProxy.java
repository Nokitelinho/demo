package com.ibsplc.neoicargo.mail.component.proxy;

import com.ibsplc.icargo.business.admin.report.vo.ReportPublishJobVO;
import com.ibsplc.icargo.business.admin.user.vo.UserVO;
import com.ibsplc.neoicargo.framework.core.lang.BusinessException;
import com.ibsplc.neoicargo.mail.component.proxy.eproxy.AdminReportEProxy;
import com.ibsplc.neoicargo.mail.component.proxy.eproxy.AdminUserEProxy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/** 
 * Java file	: 	com.ibsplc.icargo.business.mail.operations.proxy.AdminUserProxy.java Version		:	Name	:	Date			:	Updation --------------------------------------------------- 0.1		:	A-4809	:	10-May-2022	:	Draft
 */
@Component
@Slf4j
public class AdminReportProxy {
	@Autowired
	private AdminReportEProxy adminReportEProxy;

	public void publishReport(ReportPublishJobVO reportPublishJobVO){
		adminReportEProxy.publishReport(reportPublishJobVO);
	}
}
