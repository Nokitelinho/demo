package com.ibsplc.neoicargo.mail.component.proxy.eproxy;

import com.ibsplc.icargo.business.admin.report.vo.ReportPublishJobVO;
import com.ibsplc.icargo.business.admin.user.vo.UserVO;
import com.ibsplc.neoicargo.framework.ebl.api.EProductProxy;

@EProductProxy(module = "admin", submodule = "report", name = "adminReportEProxy")
public interface AdminReportEProxy {
	void publishReport(ReportPublishJobVO reportPublishJobVO);
}
